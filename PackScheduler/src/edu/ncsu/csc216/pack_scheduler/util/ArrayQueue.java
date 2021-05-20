package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * Creates an Array Queue
 * @author nmaco
 * @author atzep
 *
 * @param <E> element
 */
public class ArrayQueue<E> implements Queue<E> {
	/** the Arraylist of elements */
	private ArrayList<E> list;

	/** the capacity of the queue */
	private int capacity;

	/**
	 * Constructs a Queue using an ArrayList
	 * @param capacity the max capcity of the ArrayList
	 */
	public ArrayQueue(int capacity){
		list = new ArrayList<E>();
		setCapacity(capacity);
	}

	/**
	 * Adds element to the queue
	 * @param element the element being added
	 */
	public void enqueue(E element){
		if(list.size() >= capacity){
			throw new IllegalArgumentException();
		}
		else{
			list.add(element);
		}
	}

	/**
	 * removes element from the queue
	 * @return the removed element
	 */
	public E dequeue(){
		if(list.isEmpty()){
			throw new NoSuchElementException();
		} else{
			return list.remove(0);
		}
	}
	
	/**
	 * Method to remove specific element from list but keep order in tact
	 * @param element the element to be removed from the list
	 * @return boolean value stating if the element was removed 
	 */
	public boolean remove(E element) {
		if (list.contains(element)) {
			list.remove(element);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns size of queue
	 * @return the size of the queue
	 */
	public int size(){
		return list.size();
	}

	/**
	 * Method to check if queue is empty
	 * @return boolean value stating if queue is empty or not
	 */
	public boolean isEmpty(){
		if(list.size() == 0){
			return true;
		}
		return false;
	}

	/**
	 * Method to set capacity of queue. Throws IllegalArgumentException if capcity
	 * is less than zero or less than the list size
	 * @param capacity the capacity
	 */
	public void setCapacity(int capacity){
		if(capacity < 0){
			throw new IllegalArgumentException();
		}
		if(capacity < list.size()){
			throw new IllegalArgumentException();
		}
		else{
			this.capacity = capacity;
		}
	}
}