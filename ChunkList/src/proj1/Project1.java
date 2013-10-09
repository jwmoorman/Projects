/*@
 *
 *
 *
 *
 * @version Sep 23, 2011
 * @author Jonathan Moorman <jonmoo1@umbc.edu>
 * @project CMSC 202 - Spring 2011 - Project 1
 * @section 02
 */
package proj1;

import java.util.ArrayList;
import java.util.Iterator;
import java.io.*;



/**
 * File: Project1.java
 * Author: Jonathan Moorman
 * Date: Sep 23, 2011
 * Section: 2
 * Email: jonmoo1@umbc.edu
 */
public class Project1 {
	public static ChunkList<Integer>[] chunklists;
	public static int nodeLength;
	
	/**
	 * Method to perform a union of 2 chunklists and saves the result in the array on chunklists
	 * @param index1
	 * @param index2
	 * @param resultIndex
	 */
	public static void union(int index1, int index2, int resultIndex) {
		Integer temp;
		ChunkList<Integer> unionResult = new ChunkList<Integer>(nodeLength, resultIndex);
		Iterator<Integer> iter = chunklists[index1].iterator();
		while(iter.hasNext()) {
			temp = iter.next();
			unionResult.insert(temp);
		}
		iter = chunklists[index2].iterator();
		while(iter.hasNext()) {
			temp = iter.next();
			unionResult.insert(temp);
		}
		chunklists[resultIndex] = unionResult;
	}
	
	/**
	 * Method to perform a intersection of 2 chunklists and saves the result in the array on chunklists
	 * @param index1
	 * @param index2
	 * @param resultIndex
	 */
	public static void intersect(int index1, int index2, int resultIndex) {
		Integer temp;
		ChunkList<Integer> intersectResult = new ChunkList<Integer>(nodeLength, resultIndex);
		Iterator<Integer> iter = chunklists[index1].iterator();
		while(iter.hasNext()) {
			temp = iter.next();
			if(chunklists[index2].contains(temp)) {
				intersectResult.insert(temp);
			}
		}
		chunklists[resultIndex] = intersectResult;
	}
	
	
	public static void difference(int index1, int index2, int resultIndex){
		Integer temp;
		ChunkList<Integer> differenceResult = new ChunkList<Integer>(nodeLength, resultIndex);
		Iterator<Integer> iter1 = chunklists[index1].iterator();
		while(iter1.hasNext()){
			temp = iter1.next();
			if(!chunklists[index2].contains(temp)) {
				differenceResult.insert(temp);
			}
		}
		Iterator<Integer> iter2 = chunklists[index2].iterator();
		while(iter2.hasNext()){
			temp = iter2.next();
			if(!chunklists[index1].contains(temp)) {
				differenceResult.insert(temp);
			}
		}
	}
	public static String printGreeting(){
		String string = "hello please enter command exactly as shown.\n";
		string += "INSERT <ChunkList-array-index> <value> \n";
		string += "FIND <ChunkList-array-index> <value> \n";
		string += "REMOVE <ChunkList-array-index> <value> \n";
		string += "UNION <result-index> <first-operand-index> <second-operand-index> \n";
		string += "INTERSECTION <result-index> <first-operand-index> <second-operand-index> \n";
		string += "DIFFERENCE <result index> <first operand index> <second operand index> \n";
		string += "PRINT <ChunkList-array-index> \n";
		string += "NODES <ChunkList-array-index> \n";
		string += "RPRINT <ChunkList-array-index>\n";
		string += "QUIT\n";
		return string;
	}
	/**
	 * Main
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		// TODO capture command line args to set the chunklists[] size and open the file
		try{
			if (args.length != 3){
				throw new Exception("invalid command line");
			}
			System.out.println(printGreeting());
			FileReader in = new FileReader(args[2]);
			BufferedReader bR = new BufferedReader(in);
			@SuppressWarnings("unchecked")
			ChunkList<Integer>[] anArray = new ChunkList[Integer.parseInt(args[0])];
			for (int i = 0; i <Integer.parseInt(args[0]);i ++){
				anArray[i] = new ChunkList<Integer>(Integer.parseInt(args[1]) + 1, i);
			}
			
			String command = bR.readLine();
			command.toUpperCase();
			while(command.contains("#")||command.contains(" ")){
				String command = bR.readLine();
				command.toUpperCase();
			}
			while(!command.equals("QUIT")){
				if(command.equals("INSERT")){
					int index = bR.read();
					int value = bR.read();
					anArray[index].insert(value);
				}
				else if(command.equals("FIND")){
					Integer index = bR.read();
					Integer value = bR.read();
					System.out.println(anArray[index].find(value));
				}
				else if(command.equals("REMOVE")){
					int index = bR.read();
					int value = bR.read();
					anArray[index].remove(value);
				}
				else if(command.equals("UNION")){
					int result = bR.read();
					int index1 = bR.read();
					int index2 = bR.read();
					union(index1, index2, result);
					System.out.println(anArray[result].toString());
				}
				else if(command.equals("INTERSECTION")){
					int result = bR.read();
					int index1 = bR.read();
					int index2 = bR.read();
					intersect(index1, index2, result);
					System.out.println(anArray[result].toString());
				}
				else if(command.equals("DIFFERENCE")){
					int result = bR.read();
					int index1 = bR.read();
					int index2 = bR.read();
					difference(index1, index2, result);
					System.out.println(anArray[result].toString());
				}
				else if(command.equals("PRINT")){
					int index = bR.read();
					System.out.println(anArray[index].toString());
				}
				else if(command.equals("NODES")){
					int index = bR.read();
					System.out.println(anArray[index].nodes(index));
				}
				else if(command.equals("RPRINT")){
					int index = bR.read();
					System.out.println("RPRINT not coded");
				}
				command = bR.readLine();
				command.toUpperCase();
				
			}
			
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
}
