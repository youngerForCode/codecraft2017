package com.cacheserverdeploy.deploy.graph;

import java.util.Arrays;
import java.util.Comparator;

public class PriorityQueue <T>{
	
    private static final int DEFAULT_INITIAL_CAPACITY = 11;
    transient Object[] queue; // non-private to simplify nested class access

	private int size = 0;
    private final Comparator<? super T> comparator;
    transient int modCount = 0; // non-private to simplify nested class access
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    
    public PriorityQueue() {
        this(DEFAULT_INITIAL_CAPACITY, null);
    }
    public PriorityQueue(int initialCapacity,
    		Comparator<? super T> comparator) {
    // Note: This restriction of at least one is not actually needed,
    // but continues for 1.5 compatibility
    	if (initialCapacity < 1)
    		throw new IllegalArgumentException();
    	this.queue = new Object[initialCapacity];
    	this.comparator = comparator;
    }  
    
    private void grow(int minCapacity) {
        int oldCapacity = queue.length;
        // Double size if small; else grow by 50%
        int newCapacity = oldCapacity + ((oldCapacity < 64) ?
                                         (oldCapacity + 2) :
                                         (oldCapacity >> 1));
        // overflow-conscious code
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        queue = Arrays.copyOf(queue, newCapacity);
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
            Integer.MAX_VALUE :
            MAX_ARRAY_SIZE;
    }
    
    public boolean add(T e) {
        return offer(e);
    }
    
    public boolean offer(T e) {
        if (e == null)
            throw new NullPointerException();
        modCount++;
        int i = size;
        if (i >= queue.length)
            grow(i + 1);
        size = i + 1;
        if (i == 0)
            queue[0] = e;
        else
            siftUp(i, e);
        return true;
    }

    
    public int size() {
        return size;
    }
    
    public void clear() {
        modCount++;
        for (int i = 0; i < size; i++)
            queue[i] = null;
        size = 0;
    }

    @SuppressWarnings("unchecked")
    public T poll() {
        if (size == 0)
            return null;
        int s = --size;
        modCount++;
        T result = (T) queue[0];
        T x = (T) queue[s];
        queue[s] = null;
        if (s != 0)
            siftDown(0, x);
        return result;
    }
    
    @SuppressWarnings("unchecked")
    public T peek() {
        return (size == 0) ? null : (T) queue[0];
    }
    
    
    public boolean remove(Object o) {
        int i = indexOf(o);
        if (i == -1)
            return false;
        else {
            removeAt(i);
            return true;
        }
    }
    
    public boolean decrease(T o){
    	int i = indexOf(o);
    	if(i == -1) 
    		return false;
    	siftUp(i, o);
    	return true;
    }
    
    @SuppressWarnings("unchecked")
    private T removeAt(int i) {
        // assert i >= 0 && i < size;
        modCount++;
        int s = --size;
        if (s == i) // removed last element
            queue[i] = null;
        else {
            T moved = (T) queue[s];
            queue[s] = null;
            siftDown(i, moved);
            if (queue[i] == moved) {
                siftUp(i, moved);
                if (queue[i] != moved)
                    return moved;
            }
        }
        return null;
    }
    
    private int indexOf(Object o) {
    	if (o != null) {
    		for (int i = 0; i < size; i++)
    			if (o.equals(queue[i]))
    				return i;
    	}
    	return -1;
    }
    
    public boolean contains(Object o) {
    	return indexOf(o) != -1;
    }
    
    @SuppressWarnings("unchecked")
    private void heapify() {
        for (int i = (size >>> 1) - 1; i >= 0; i--)
            siftDown(i, (T) queue[i]);
    }

    private void siftDown(int k, T x) {
        if (comparator != null)
            siftDownUsingComparator(k, x);
        else
            siftDownComparable(k, x);
    }
    
    @SuppressWarnings("unchecked")
    private void siftDownUsingComparator(int k, T x) {
        int half = size >>> 1;
        while (k < half) {
            int child = (k << 1) + 1;
            Object c = queue[child];
            int right = child + 1;
            if (right < size &&
                comparator.compare((T) c, (T) queue[right]) > 0)
                c = queue[child = right];
            if (comparator.compare(x, (T) c) <= 0)
                break;
            queue[k] = c;
            k = child;
        }
        queue[k] = x;
    }
    
    @SuppressWarnings("unchecked")
    private void siftDownComparable(int k, T x) {
        Comparable<? super T> key = (Comparable<? super T>)x;
        int half = size >>> 1;        // loop while a non-leaf
        while (k < half) {
            int child = (k << 1) + 1; // assume left child is least
            Object c = queue[child];
            int right = child + 1;
            if (right < size &&
                ((Comparable<? super T>) c).compareTo((T) queue[right]) > 0)
                c = queue[child = right];
            if (key.compareTo((T) c) <= 0)
                break;
            queue[k] = c;
            k = child;
        }
        queue[k] = key;
    }
    
    private void siftUp(int k, T x) {
        if (comparator != null)
            siftUpUsingComparator(k, x);
        else
            siftUpComparable(k, x);
    }

    @SuppressWarnings("unchecked")
    private void siftUpComparable(int k, T x) {
        Comparable<? super T> key = (Comparable<? super T>) x;
        while (k > 0) {
            int parent = (k - 1) >>> 1;
            Object e = queue[parent];
            if (key.compareTo((T) e) >= 0)
                break;
            queue[k] = e;
            k = parent;
        }
        queue[k] = key;
    }

    @SuppressWarnings("unchecked")
    private void siftUpUsingComparator(int k, T x) {
        while (k > 0) {
            int parent = (k - 1) >>> 1;
            Object e = queue[parent];
            if (comparator.compare(x, (T) e) >= 0)
                break;
            queue[k] = e;
            k = parent;
        }
        queue[k] = x;
    }
}

class TestPriorityQueue{
	public static void test(){
		PriorityQueue<Integer> pQueue  = new PriorityQueue();
		System.out.println("add 0 to 9 ");
		for (int i = 0; i < 10; i++) {
			pQueue.add(9-i);
		}
		System.out.println("the minimum :" +pQueue.poll());
	}
	
}