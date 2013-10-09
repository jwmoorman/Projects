package proj4;

	// QuadraticProbing Hash table class
	//
	// CONSTRUCTION: an approximate initial size or default of 101
	//
	// ******************PUBLIC OPERATIONS*********************
	// bool insert( x )       --> Insert x
	// bool remove( x )       --> Remove x
	// bool contains( x )     --> Return true if x is present
	// void makeEmpty( )      --> Remove all items


	/**
	 * Probing table implementation of hash tables.
	 * Note that all "matching" is based on the equals method.
	 * @author Mark Allen Weiss
	 */
	public class QuadraticProbingHashTable<AnyType> 
	{
	    /**
	     * Construct the hash table.
	     */
	    public QuadraticProbingHashTable( )
	    {
	        this( DEFAULT_TABLE_SIZE );
	    }

	    /**
	     * Construct the hash table.
	     * @param size the approximate initial size.
	     */
	    public QuadraticProbingHashTable( int size )
	    {
	        allocateArray( size );
	        makeEmpty( );
	        collisions = 0;
	    }

	    /**
	     * overridden toString method
	     * @return s
	     */
	    public String toString(){
	    	String s = "The number of entries is: "+ this.currentSize;
	    	s += "\nThe size of the table is " + array.length;
	    	s += "\nThe array is " + ((double) this.currentSize/array.length) + "% full";
	    	s += "\nThere were " + this.collisions + " collisions";
	    	s += this.favoriteMove();
	    	return s;
	    }
	    
	    /**
	     * Insert into the hash table. If the item is
	     * already present, do nothing.
	     * @param x the item to insert.
	     */
	    private void insert( AnyType x )
	    {
	    	// Insert x as active
	        int currentPos = findPos( x );
	        if( isActive( currentPos ) )
	            return;

	        array[ currentPos ] = new HashEntry<AnyType>( x, true );

	            // Rehash; see Section 5.5
	        if( ++currentSize > array.length / 2)
	            rehash( );
	    }

	    /**
	     * Expand the hash table.
	     */
	    private void rehash( )
	    {
	        HashEntry<AnyType> [ ] oldArray = array;

	            // Create a new double-sized, empty table
	        allocateArray( nextPrime( 2 * oldArray.length ) );
	        currentSize = 0;

	            // Copy table over
	        for( int i = 0; i < oldArray.length; i++ )
	            if( oldArray[ i ] != null && oldArray[ i ].isActive )
	                insert( oldArray[ i ].element );
	    }

	    /**
	     * Method that performs quadratic probing resolution.
	     * Assumes table is at least half empty and table length is prime.
	     * @param x the item to search for.
	     * @return the position where the search terminates.
	     */
	    private int findPos( AnyType x )
	    {
	        int offset = 1;
	        int currentPos = myhash( x );
	        
	        while( array[ currentPos ] != null &&
	                !array[ currentPos ].element.equals( x ) )
	        {
	        	collisions++;
	            currentPos += offset;  // Compute ith probe
	            offset += 2;
	            if( currentPos >= 5000 )
	                currentPos -= 5000;
	        }
	        
	        return currentPos;
	    }

	    /**
	     * Remove from the hash table.
	     * @param x the item to remove.
	     */
	    public void remove( AnyType x )
	    {
	        int currentPos = findPos( x );
	        if( isActive( currentPos ) )
	            array[ currentPos ].isActive = false;
	    }

	    
	    /**
	     * adds information to the hash element and adds the element if not already contained.
	     * @param x
	     * @param outcome
	     */
	    public void update(AnyType x, int outcome){
	    	if(!this.contains(x)){
	    		this.insert(x);
	    	}
	    	int currentPos = findPos( x );
	        if(isActive(currentPos)){
	        	if (outcome > 0){
	        		array[currentPos].wins++;
	        	}
	        	else if (outcome < 0){
	        		array[currentPos].losses++;
	        	}
	        	else{
	        		array[currentPos].ties++;
	        	}
	        	}
	        array[currentPos].total++;
	    	}
	    
	    /**
	     * returns a string containing the favorite first move of the smart computer
	     * @return s
	     */
	    private String favoriteMove(){
	    	int total = 0;
	    	int won = 0;
	    	AnyType move = null;
	    	for(int i = 0; i < array.length; i++){
	    		if (array[i] != null){
	    		if (array[i].firstMove == true){
	    			if (array[i].total > total){
	    				total = array[i].total;
	    				won = array[i].wins;
	    				move = array[i].element;
	    			}
	    		}
	    		}
	    	}
	    	String s = "\nFavorite first move was\n" + move.toString() + "Which won " + won + " of " + total + " times";
	    	s += " which is " + ((double)won/total);
	    	return s;
	    }
	    
	    /**
	     * changes the hash entry of first to keep track of all first moves made by smart
	     * @param x
	     */
	    public void first(AnyType x){
	    	int currentPos = findPos( x );
	        if(isActive(currentPos)){
	        	array[currentPos].firstMove = true;
	        }
	    }
	    /**
	     * Find an item in the hash table.
	     * @param x the item to search for.
	     * @return the matching item.
	     */
	    public boolean contains( AnyType x )
	    {
	        int currentPos = findPos( x );
	        return isActive(currentPos);
	    }
	    /**
	     * Find an item in the hash table.
	     * @param x the item to search for.
	     * @return the matching item.
	     */
	    public AnyType getElement( AnyType x )
	    {
	        int currentPos = findPos( x );
	        if(isActive(currentPos)){
	        	return array[currentPos].element;
	        }
	        return null;
	    }
	    /**
	     * returns wins caused by a hash entry
	     * @param x
	     * @return wins
	     */
	    public int getWins( AnyType x )
	    {
	        int currentPos = findPos( x );
	        if(isActive(currentPos)){
	        	return array[currentPos].wins;
	        }
	        return 0;
	    }
	    
	    /**
	     * returns total uses of a hash entry
	     * @param x
	     * @return total
	     */
	    public int getTotal( AnyType x )
	    {
	        int currentPos = findPos( x );
	        if(isActive(currentPos)){
	        	return array[currentPos].total;
	        }
	        return 0;
	    }
	    

	    /**
	     * Return true if currentPos exists and is active.
	     * @param currentPos the result of a call to findPos.
	     * @return true if currentPos is active.
	     */
	    private boolean isActive( int currentPos )
	    {
	        return array[ currentPos ] != null && array[ currentPos ].isActive;
	    }

	    /**
	     * Make the hash table logically empty.
	     */
	    public void makeEmpty( )
	    {
	        currentSize = 0;
	        for( int i = 0; i < array.length; i++ )
	            array[ i ] = null;
	    }
	    
	    /**
	     * private method that returns the hashval of the entry
	     * @param x
	     * @return hashVal
	     */
	    private int myhash( AnyType x )
	    {
	        int hashVal = x.hashCode( );

	        hashVal %= 5000;
	        if( hashVal < 0 )
	            hashVal += 5000;

	        return hashVal;
	    }
	    
	    private static class HashEntry<AnyType>
	    {
	    	public int wins;			//wins caused by this entry
	    	public int losses;			//losses caused by this entry
	    	public int total;			//total uses of this entry
	    	public int ties;			//ties caused by this entry
	        public AnyType  element;   	// the element
	        public boolean isActive;  	// false if marked deleted
	        public boolean firstMove;	//true if move is a first move

	        @SuppressWarnings("unused")
			public HashEntry( AnyType e )
	        {
	            this( e, true );
	        }
	        public HashEntry( AnyType e, boolean i )
	        {
	            element  = e;
	            isActive = i;
	            wins = 0;
	            losses = 0;
	            ties = 0;
	            firstMove = false;
	        }
	    }

	    private static final int DEFAULT_TABLE_SIZE = 5000;

	    private HashEntry<AnyType> [ ] array; // The array of elements
	    private int currentSize;              // The number of occupied cells
	    private int collisions;
	    /**
	     * Internal method to allocate array.
	     * @param arraySize the size of the array.
	     */
	     @SuppressWarnings("unchecked")
	    private void allocateArray( int arraySize )
	    {
	        array = new HashEntry[ nextPrime( arraySize ) ];
	    }

	    /**
	     * Internal method to find a prime number at least as large as n.
	     * @param n the starting number (must be positive).
	     * @return a prime number larger than or equal to n.
	     */
	    private static int nextPrime( int n )
	    {
	        if( n <= 0 )
	            n = 3;

	        if( n % 2 == 0 )
	            n++;

	        for( ; !isPrime( n ); n += 2 )
	            ;

	        return n;
	    }

	    /**
	     * Internal method to test if a number is prime.
	     * Not an efficient algorithm.
	     * @param n the number to test.
	     * @return the result of the test.
	     */
	    private static boolean isPrime( int n )
	    {
	        if( n == 2 || n == 3 )
	            return true;

	        if( n == 1 || n % 2 == 0 )
	            return false;

	        for( int i = 3; i * i <= n; i += 2 )
	            if( n % i == 0 )
	                return false;

	        return true;
	    }


	    // Simple main
	    public static void main( String [ ] args )
	    {
	        QuadraticProbingHashTable<String> H = new QuadraticProbingHashTable<String>( );

	        final int NUMS = 400000;
	        final int GAP  =   37;

	        System.out.println( "Checking... (no more output means success)" );


	        for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
	            H.insert( ""+i );
	        for( int i = 1; i < NUMS; i+= 2 )
	            H.remove( ""+i );

	        for( int i = 2; i < NUMS; i+=2 )
	            if( !H.contains( ""+i ) )
	                System.out.println( "Find fails " + i );

	        for( int i = 1; i < NUMS; i+=2 )
	        {
	            if( H.contains( ""+i ) )
	                System.out.println( "OOPS!!! " +  i  );
	        }
	    }

	}
