package proj4;

import java.util.LinkedList;


public class Project4
{
    public static int smartWins;				//keeps track of total smart wins
    public static int dumbWins;					//keeps track of total dumb wins
    public static int ties;						//keeps track of total ties
    public static int total;					//keeps track of total games
    public static boolean history;				//history flag boolean
    public static boolean display;				//display flag boolean
    public static boolean save;					//save flag boolean
    
    public static void main( String [ ] args )
    {
    	int games = 1;
    	smartWins = 0;
    	dumbWins = 0;
    	ties = 0;
    	total = 0;
    	history = false;
    	display = false;
    	save = false;
    	LinkedList<String> commands = new LinkedList<String>();
    	for (int i = 0; i < args.length; i++){
    		String arg = args[i].substring(1);
    		if (!arg.equals("h") && !arg.equals("d") && !arg.equals("s")){
    			games = Integer.parseInt(arg);
    		}
    		else{
    			commands.add(arg);
    		}
    	}
    	for (int i = 0; i < commands.size(); i++){
    		if (commands.get(i).toLowerCase().equals("h")){
    			history = true;
    		}
    		if (commands.get(i).toLowerCase().equals("d")){
    			display = true;
    		}
    		if (commands.get(i).toLowerCase().equals("s")){
    			save = true;
    		}
    	}
    	QuadraticProbingHashTable<TicTacToe> q = new QuadraticProbingHashTable<TicTacToe>();
    	for (int i = 0; i < games; i++){
    		if (history == true){
    			System.out.println("Game Number: " + i);
    		}
    		int run = TicTacToe.run(q, history);
    		total++;
    		if (run > 0){
    			smartWins++;
    		}
    		else if (run == 0){
    			ties++;
    			}
    		else{
    			dumbWins++;
    		}
    		if (history == true){
    			System.out.println("Dumb computer has won " + dumbWins + " games");
    			System.out.println("Smart computer has won " + smartWins + " games");
    		}
    	}
    
    	System.out.println("Final Report:\n\nSmart winning percentage is " + (100 * smartWins)/ total);
    	System.out.println("Dumb winning percentage is " + (100 * dumbWins)/ total);
    	System.out.println(q.toString());
    	}

   


}