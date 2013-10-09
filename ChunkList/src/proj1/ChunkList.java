package proj1;
/*@
 *
 *
 *
 *
 * @version Sep 29, 2011
 * @author Jonathan Moorman <jonmoo1@umbc.edu>
 * @project CMSC 202 - Spring 2011 - Project 1
 * @section 02
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * File: ChunkList.java
 * Author: Jonathan Moorman
 * Date: Sep 29, 2011
 * Section: 2
 * Email: jonmoo1@umbc.edu
 * @param <T>
 */
public class ChunkList<T extends Comparable<T>> implements Iterable<T> {
	private LinkedList<ArrayList<T>> chunkList;
	private int nodeCount;
	private int nodeLength;
	private int index;
	
	/**
	 * Constructor
	 */
	public ChunkList() {
		clear();
	}
	
	/**
	 * Constructor
	 * @param length
	 */
	public ChunkList(int length, int index){
		this.chunkList = new LinkedList<ArrayList<T>>();
		this.nodeCount = 1;
		this.nodeLength = length;
		this.chunkList.add(new ArrayList<T>());
		this.index = index;
	}
	
	/**
	 * Clear the chunklist
	 */
	public void clear() {
		this.nodeCount = 0;
		this.chunkList = new LinkedList<ArrayList<T>>();
	}
	
	public static void main(String[] args){
		ChunkList<Integer> t = new ChunkList<Integer>(6, 0);
		t.insert(2);
		t.insert(3);
		t.insert(1);
		t.insert(4);
		t.insert(7);
		t.insert(8);
		t.insert(9);
		System.out.println(t.find(9));
		System.out.println(t.nodes(0));
		t.remove(1);
		t.remove(2);
		t.remove(8);
		t.insert(10);
		t.insert(11);
		System.out.println(t.toString());
		System.out.println(t.nodes(0));
		
	}
	/**
	 * Method to insert an item into the chunklist
	 * @param value
	 */
	public boolean insert(T value) {
		// if the item isn't already contained try adding it to an existing chunk else return false
		
		if(!this.contains(value)) {
			int counter = 0;
			for(ArrayList<T> y : chunkList) {
				counter++;
			if (y.size() == 0){			//if the chunkList was empty
				y.add(value);
				return true;			//returns true if insert was successful
			}
			if(y.size() == nodeLength) {
				continue;
				} 
				else {
					if (value.compareTo(y.get(y.size()-1)) < 0){		//compares the values to be sure the value goes in the right node
						y.add(value);
						Collections.sort(y);
						return true;
					}
				}
				if (nodeCount == counter){				// failsafe.  if the value is the largest entered so far
					y.add(value);
					return true;
				}
			}
					// successfully added the item to an existing chunk
		
		
			// every chunk in the list is full so split the last chunk in the list and add a new chunk
		int index;
		T y;
		chunkList.getLast().add(value);
		Collections.sort(chunkList.element());
		ArrayList<T> newChunk = new ArrayList<T>();
		ArrayList<T> temp = chunkList.getLast();
		
		while(temp.size() > (nodeLength/ 2) + 1) {
			index = temp.size();
			y = temp.get(index-1);
			temp.remove(index-1);
			newChunk.add(y);
		}
		Collections.sort(newChunk);			//sorts the new chunk
		chunkList.add(newChunk);			//add the chunk to the chunkList
		nodeCount++;
		return true;	// created a new chunk and split the last chunk to keep node size constraint
		}
		else {
			return false; // item is already contained. do nothing
		}
	}
	/**
	 * Method the find a value within the chunkList returns a string describing the outcome of the find method
	 * @param value
	 * @return String
	 */
	public String find(T value){
		int nodeCount = 0;
		for (ArrayList<T> t : chunkList){
			if (t.contains(value)){
				for (int i = 0; i < t.size();i++){
					if (t.get(i).compareTo(value) == 0){
						return "value is located at node: " + nodeCount + " at position: " + i;
					}
				}
			}
			
		nodeCount++;
		}
		return "not contained in the chunkList";
		
	}
	/**
	 * Method to remove items from the chunklist without breaking the min nodelength constraint
	 * @param item
	 * @return boolean
	 */
	public boolean remove(T item) {
		for(ArrayList<T> t: chunkList) {
			for(T i: t) {
				if(i.equals(item)) {
					t.remove(item);
					// if the removal of an item breaks the min nodelength constraint consolidate
					if(t.size() < nodeLength / 2 && chunkList.size() != 1) {
						ArrayList<T> tempArr = t;
						// removed the chunk that breaks the constraint and add re-add all of it's items
						chunkList.remove(chunkList.indexOf(t));
						nodeCount--;
						for(T temp : tempArr) {
							this.insert(temp);
						}
					}
					return true;	// successfully added the new item
				}
			}
		}
		return false;	// item was not contained in the chunklist
	}
	/**
	 * chunkList's toString method
	 * @return String
	 */
	public String toString(){
		String string = "ChunkList" + this.index + ": ";
		if (chunkList.getFirst().isEmpty()){
			string += "empty";
		}
		else{
			for(ArrayList<T> t: chunkList){
				string += t;
		}	}
		return string;
	}
	/**
	 * Method to show how many nodes are contained and their values.  returns a string describing the chunkList
	 * @param index
	 * @return String
	 */
	public String nodes(int index){
		String string = "ChunkList " + index + " contains " + nodeCount + " node(s)\n";
		int nodeCount = 0;
		for (ArrayList<T> t : chunkList){
			string += "node" + nodeCount + " : " + t.toString() + "\n";
			nodeCount++;
			
		}
		return string;
	}
	/**
	 * Method to find if an item is contained in the chunklist
	 * @param item
	 * @return boolean
	 */
	public boolean contains(T item) {
		for(ArrayList<T> t: chunkList) {
			if(t.contains(item))
				return true;
		}
		return false;
	}
	
	/**
	 * My java iter
	 */
	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return new ArrayListIterator();
	}
	private class ArrayListIterator implements Iterator<T>{
		private Iterator<ArrayList<T>> bigIter;
		private Iterator<T> smallIter;
		private ArrayList<T> currentArrayList;
		private T last;
		
		/**
		 * Constructor
		 */
		public ArrayListIterator() {
			bigIter = chunkList.iterator();
			currentArrayList = bigIter.next();
			smallIter = currentArrayList.iterator();
		}
		
		/**
		 * checks to see if there is are objects left in the collection to iterate over
		 */
		public boolean hasNext() {
			if(bigIter.hasNext() || smallIter.hasNext()) {
				return true;
			}
			return false;
		}
		
		/**
		 * gets the next object in the collection
		 */
		public T next() {
			if(smallIter.hasNext()) {
				last = smallIter.next();
				return last;
			} else {
				currentArrayList = bigIter.next();
				smallIter = currentArrayList.iterator();
				last = smallIter.next();
				return last;
			}
		}
		
		/**
		 * removes the last element returned by the iterator from the chunkList
		 */
		public void remove() {
			currentArrayList.remove(last);
		}
		
	}
}
