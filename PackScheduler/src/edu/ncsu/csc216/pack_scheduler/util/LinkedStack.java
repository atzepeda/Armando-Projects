package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * Creates the LinkedStack
 * @author nmaco
 * @author atzep
 * @param <E> element
 */
public class LinkedStack<E> implements Stack<E> {
	/** the linked list */
	private LinkedAbstractList<E> list;
	/** the stack capacity */
	private int capacity;
	
	/**
	 * Constructs the linkedstack
	 * @param capacity the capacity
	 */
	public LinkedStack(int capacity){
		list = new LinkedAbstractList<E>(capacity);
		setCapacity(capacity);
	}
	
	/**
	 * Adds the elements to the stack
	 * @param element being added
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
	 * The size of the stack
	 * @return the size
	 */
	public int size(){
		return list.size();
	}
	
	/**
	 * Checks if the stack is empty
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
