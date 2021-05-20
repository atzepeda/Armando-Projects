package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * Creates the LinkedQueue
 * @author nmaco
 * @author atzep
 * @param <E> element
 */
public class LinkedQueue<E> implements Queue<E> {
	/** the linked list */
	private LinkedAbstractList<E> list;

	/** the linked queue capacity */
	private int capacity;

	/**
	 * Constructs the linkedqueue
	 * @param capacity the capacity
	 */
	public LinkedQueue(int capacity){
		list = new LinkedAbstractList<E>(capacity);
		setCapacity(capacity);
	}

	/**
	 * Adds an element to the queue
	 * @param element being added
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
	 * Removes an element from the queue
	 * @return the removed element
	 */
	public E dequeue(){
		if(list.isEmpty()){
			throw new NoSuchElementException();
		}
		else{
			return list.remove(0);
		}
	}

	/**
	 * The size of the queue
	 * @return the size
	 */
	public int size(){
		return list.size();
	}

	/**
	 * Checks if the queue is empty
	 * @return true or false
	 */
	public boolean isEmpty(){
		if(list.size() == 0){
			return true;
		}
		return false;
	}

	/**
	 * Sets the capacity of the queue
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