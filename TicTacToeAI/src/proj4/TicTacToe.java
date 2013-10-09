package proj4;

import java.util.ArrayList;
import java.util.Random;

class TicTacToe
{
    private static final String SMARTCOMPUTER = "o";
    private static final String DUMBCOMPUTER = "x";
    private static final String EMPTY = ".";
   
    public String[][] board = new String[3][3];

    /**
     * Constructor
     */
    public TicTacToe()
    {
        clearBoard();
    }
    
    /**
     * Returns the current tictactoe board
     * @return board
     */
    public String [] [] getBoard()
    {
        return board;
    }
    
    /**
     * Counts how many empty spaces are in the board
     * @return count
     */
    public int emptySpaces(){
    	int count = 0;
    	for (int x = 0 ; x < 3 ; x++){
    		for (int y = 0; y < 3; y++){
    			if (this.board[x][y].equals(EMPTY)){
    				count++;
    			}
    		}
    	}
    	return count;
    }
    /**
     * Returns a deep copy of the calling tictactoe object
     * @return board
     */
    public TicTacToe clone(){
    	TicTacToe board = new TicTacToe();
    	for (int x = 0 ; x < 3 ; x++){
    		for (int y = 0; y < 3; y++){
    			board.board[x][y] = this.board[x][y];
    		}
    	}
    	return board;
    }
    
    /**
     * Returns a string representation of the board
     * @return string
     */
    public String toString(){
    	String s = "";
    	for(int x = 0; x < 3; x++){
    		for (int y = 0; y < 3; y++){
    			s += board[x][y];
    		}
    		s += "\n";
    	}
    	return s;
    }


    /**
     * private function to play a random move by smart.
     * the random values have already been decided and are passed in
     * returns a boolean depending on success
     * @param x
     * @param y
     * @return boolean
     */
    private boolean playRandDumbMove(int x, int y)
    {
        if(!board[x][y].equals(EMPTY))
            return false;
       	board[x][y] = DUMBCOMPUTER;
       	return true;
    }
    /**
     * private function to play a random move by smart.
     * the random values have already been decided and are passed in
     * returns a boolean depending on success
     * @param x
     * @param y
     * @return boolean
     */
    private boolean playRandSmartMove(int x, int y)
    {
        if(!board[x][y].equals(EMPTY))
            return false;
       	board[x][y] = SMARTCOMPUTER;
       	return true;
    }
    
    /**
     * plays a smart move based on the hash table passed in and prints information depending on the history flag.
     * returns a boolean depending on success
     * @param q
     * @param h
     * @return boolean
     */
    public boolean playSmartMove(QuadraticProbingHashTable<TicTacToe> q, boolean h)
    {
    	TicTacToe[] t = new TicTacToe[this.emptySpaces()];
    	for (int i = 0; i < this.emptySpaces(); i++){
    		t[i] = this.clone();
    		t[i].insertFreePosition(i);
    	}
    	
    	double temp = 0.0;
    	TicTacToe next = null;
    	for (int i = 0; i < this.emptySpaces(); i++){
  
    		if (q.contains(t[i])){
    			int w = q.getWins(t[i]);
    			int tot = q.getTotal(t[i]);
    			if (temp < (double) w/tot){
    				temp = (double) w/tot;
    				next = t[i];
    			}
    		}
    	}
    	if (temp > 0.6){
    		this.board = next.board.clone();
    	}
    	else{
    		this.moveSmartRandom();
    	}
    	if (h == true){
    	System.out.println(this.toString() + "in the past this move led to a win " + (100 * temp) + "% of the time");
    	}
    	return true;
    }
        

    /**
     * clears the board
     */
    public void clearBoard()
    {
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                board[i][j] = EMPTY;
    }

    /**
     * returns a boolean telling if the board is full
     * @return boolean
     */
    public boolean isFull()
    {
    	for(int x = 0; x < 3; x++)
            for(int y = 0; y < 3; y++)
                if(board[x][y].equals(EMPTY))
                    return false;
        return true;
    }

    /**
     * Checks if the passed in player has won the game.
     * returns a boolean telling the result
     * @param player
     * @return boolean
     */
    public boolean isAWin(String player)
    {
        
        for(int x = 0; x < 3; x++){
        	if(board[x][0].equals(player) && board[x][1].equals(player) && board[x][2].equals(player)){
        		return true;
        	}
    	}
        for(int y = 0; y < 3; y++ ){
        	if(board[0][y].equals(player) && board[1][y].equals(player) && board[2][y].equals(player)){
        		return true;
        	}
        }

        if(board[1][1].equals(player) && board[2][2].equals(player) && board[0][0].equals(player)){
            return true;
        }

        if(board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player)){
            return true;
        }

        return false;
        
        }
    
    /**
     * private function that plays a move at x and y for the passed in player
     * returns a boolean depending on success
     * @param player
     * @param x
     * @param y
     * @return boolean
     */
    private boolean playMove(String player, int x, int y){
    	if(!board[x][y].equals(EMPTY))
            return false;
       	board[x][y] = player;
       	return true;
    }
    
    /**
     * private function that inserts a smart move at the specified position.
     * returns a boolean depending on success
     * @param position
     * @return boolean
     */
    private boolean insertFreePosition(int position){
    	int counter = -1;
    	int free = this.emptySpaces();
    	if (position < 0 || position > free)
    		return false;
    	for(int x = 0; x < 3; x++){
            for(int y = 0; y < 3; y++){
            	if (board[x][y].equals(EMPTY)){
            		counter++;
            	}
            	if (counter == position){
            		playMove(SMARTCOMPUTER,x,y);
            		return true;
            	}
            }
    	}
    	return false;
    }
    
    /**
     * plays a random smart move
     */
    public void moveSmartRandom(){
    	Random rand = new Random();
    	boolean b = this.playRandSmartMove(rand.nextInt(3), rand.nextInt(3));
		while (b == false){
			b = this.playRandSmartMove(rand.nextInt(3), rand.nextInt(3));
		}
    }
    
    /**
     * plays a random dumb move and prints info based on the history flag
     * @param h
     */
    public void moveDumbRandom(boolean h){
    	Random rand = new Random();
    	boolean b = this.playRandDumbMove(rand.nextInt(3), rand.nextInt(3));
		while (b == false){
			b = this.playRandDumbMove(rand.nextInt(3), rand.nextInt(3));
		}
		if (h == true){
			System.out.println(this.toString());
		}
    }
    
    @Override
    /**
     * overridden hashCode method
     */
	public int hashCode() {
		final int prime = 127;
		int result = 1;
		for (int x = 0; x < 3 ; x++){
			for (int y = 0 ; y < 3 ; y++){
		result = prime * result + (int)board[x][y].charAt(0);
			}
		}
		return result;
	}
    
    /**
     * overridden equals method
     * @param b
     * @return boolean
     */
    public boolean equals(Object b) { 
    	if (b instanceof TicTacToe){
        	for(int x = 0; x < 3; x++){
                for(int y = 0; y < 3; y++){
                	if (!((TicTacToe) b).getBoard()[x][y].equals(this.board[x][y])){
                		return false;
                	}
                }
               
            }
        	 return true;
    	}
    	return false;
        }
        	
    /**
     * simulates a game using the hashtable to drive the smart player's moves
     * returns an int that tells the result of the played game.
     * @param q
     * @param h
     * @return result
     */
    public static int run(QuadraticProbingHashTable<TicTacToe> q, boolean h){
    	
    	int moveCount = 0; 
    	ArrayList<TicTacToe> list = new ArrayList<TicTacToe>();
    	TicTacToe t = new TicTacToe();
    	while(!t.isFull() && !t.isAWin(DUMBCOMPUTER) && !t.isAWin(SMARTCOMPUTER)){
    		if (moveCount % 2 == 0){
    			if (h == true){
    				System.out.println("The dumb player 'x' plays");
    			}
    			t.moveDumbRandom(h);
    		}
    		
    		else{
    			if (h == true){
    				System.out.println("The smart player 'o' plays");
    			}
    			t.playSmartMove(q, h);
    			list.add(t.clone());
    			}
    		moveCount++;
        	}
    	
		
    	
    	if (t.isAWin(DUMBCOMPUTER)){
    		for (int i = 0; i < list.size(); i++){
    			q.update(list.get(i), -1);
    			if (i == 0){
    				q.first(list.get(0));
    			}
    			
    		}
    		if (h == true){
				System.out.println("The game has been won by O");
			}
    		return -1;
    	}
    	else if (t.isAWin(SMARTCOMPUTER)){
    		for (int i = 0; i < list.size(); i++){
    			q.update(list.get(i), 1);
    			if (i == 0){
    				q.first(list.get(0));
    			}
    			
    		}
    		if (h == true){
				System.out.println("The game has been won by X");
			}
    		return 1;
    	}
    	else{
    		for (int i = 0; i < list.size(); i++){
    			q.update(list.get(i), 0);
    			if (i == 0){
    				q.first(list.get(0));
    			}
    			
    		}
    		if (h == true){
				System.out.println("The game has ended in a tie");
			}
    		return 0;
    	} 
    }
}
