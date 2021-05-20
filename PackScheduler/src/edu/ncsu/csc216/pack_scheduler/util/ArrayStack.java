package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * Creates the ArrayStack
 * @author nmaco
 * @author atzep
 * @param <E> element
 */
public class ArrayStack<E> implements Stack<E> {
	/** The array list for the stack */
	private ArrayList<E> list;

	/** the capacity of the stack */
	private int capacity;

	/**
	 * Creates the ArrayStack
	 * @param capacity the capacity
	 */
	public ArrayStack(int capacity){
		list = new ArrayList<E>();
		setCapacity(capacity);
	}

	/**
	 * Pushes the element to the stack
	 * @param element the element being pushed
	 */
	public void push(E element){
		if(list.size() >= capacity){
			throw new IllegalArgumentException();
		}
		else{
			list.add(list.size(), element);
		}
	}

	/**
	 * Removes the element from the stack
	 * @return the removed element
	 */
	public E pop(){
		if(list.isEmpty()){
			throw new EmptyStackException();
		}
		else{
			return list.remove(list.size() - 1);
		}
	}

	/**
	 * Gets the size of the stack
	 * @return the stack size
	 */
	public int size(){
		return list.size();
	}

	/**
	 * Checks to see if the stack is empty
	 * @return true or false
	 */
	public boolean isEmpty(){
		if(list.size() == 0){
			return true;
		}
		return false;
	}

	/**
	 * Sets the capacity of the stack
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