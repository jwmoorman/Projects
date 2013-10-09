/**
 *
 *
 *
 *
 * @version Oct 23, 2011
 * @author Jonathan Moorman <jonmoo1@umbc.edu>
 * @project CMSC 341 - Spring 2011 - Project 2
 */
package proj2;

import java.util.Scanner;
import java.io.*;




/**
 * File: Project1.java
 * Author: Jonathan Moorman
 * Date: Sep 23, 2011
 * Section: 2
 * Email: jonmoo1@umbc.edu
 */
public class Project2 {


	/**
	 * Main
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		try{
			if (args.length != 2){
				throw new Exception("invalid command line");
			}
			File inFile = new File(args[0]);
			Scanner input= new Scanner(inFile);
			
			BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
			int value = Integer.parseInt(input.next());
			tree.insert(value);
			while(input.hasNext()){
				value = Integer.parseInt(input.next());
				tree.insert(value);
			}
			input.close();
			
			File comFile = new File(args[1]);
			Scanner commands = new Scanner(comFile);

			while(commands.hasNext()){
				String command = commands.next();
				command = command.toUpperCase();

				while(command.startsWith("#")||command.startsWith(" ")){
					commands.nextLine();
					command = commands.next();
					command = command.toUpperCase();
				}
				if(command.equals("PRINT")){
					int levels = Integer.parseInt(commands.next());
					System.out.println(command + " " + levels);
					System.out.println(tree.toString(levels));
				}
				else if(command.equals("RANK")){
					int index =Integer.parseInt(commands.next());
					System.out.println(command + " " + index);
					int i = tree.rank(index);
					if (i < 0){
						System.out.println("Invalid");
					}
					else{
						System.out.println(i);
					}
				}
				else if(command.equals("NTH")){
					int index = Integer.parseInt(commands.next());
					System.out.println(command + " " + index);
					
					Integer i = tree.nthElement(index);
					if (i == null){
						System.out.println("Invalid");
					}
					else{
						System.out.println(i);
					}
				}
				else if(command.equals("MEDIAN")){
					System.out.println(command);
					Integer n = tree.median();
					if (n == null){
						System.out.println("Invalid");
					}
					else{
						System.out.println(n);
					}
					
				}
				else if(command.equals("REMOVE")){
					int index = Integer.parseInt(commands.next());
					System.out.println(command + " " + index);
					boolean b = tree.remove(index);
					if (b == true){
						System.out.println("removed");
					}
					else{
						System.out.println("Invalid");
					}
					
				}
				else if(command.equals("PERFECT")){
					
					System.out.println(command + ":" + tree.isPerfect());
				}
				else if(command.equals("COMPLETE")){
					System.out.println(command + ": Did not create complete methods");
				}
				
				}
				
				
			
			
			commands.close();
			
		}
		catch(Exception e){
			System.out.println(e);
		}
	
	}
}
