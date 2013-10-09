package proj2;
// BinarySearchTree class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x
// boolean contains( x )  --> Return true if x is present
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

// your package statement here
/**
 * Implements an unbalanced binary search tree.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
/**
 * * @version Oct 23, 2011
 * @author Jonathan Moorman <jonmoo1@umbc.edu>
 * @project CMSC 341 - Spring 2011 - Project 2
 */
public class BinarySearchTree<AnyType extends Comparable<? super AnyType>>
{
	private static boolean removed;
	public static void main(String[] args){
		BinarySearchTree<Integer> t = new BinarySearchTree<Integer>();
		t.insert(9);
		t.insert(3);
		t.insert(15);
		t.insert(5);
		t.insert(1);
		t.insert(0);
		t.insert(2);
		t.insert(6);
		t.insert(69);
		t.insert(39);
		t.insert(345);
		t.insert(66);
		t.insert(76);
		t.insert(534);
		t.insert(11);
		t.insert(10);
		t.insert(12);
		t.printTree();
		System.out.println(t.toString(4));
		System.out.println(t.rank(125125));
		System.out.println(t.nthElement(12));
		System.out.println(t.median());
		System.out.println(t.isPerfect());
	}
	private static int counter;
    /**
     * Construct the tree.
     */
    public BinarySearchTree( )
    {
    	root = null;
    }

    /**
     * Insert into the tree; duplicates are ignored.
     * @param x the item to insert.
     */
    public void insert( AnyType x )
    {
        root = insert( x, root );
    }

    /**
     * Remove from the tree. Nothing is done if x is not found.
     * @param x the item to remove.
     */
    public boolean remove( AnyType x )
    {
    	removed = true;
        root = remove( x, root );
        return removed;
    }

    /**
     * Find the smallest item in the tree.
     * @return smallest item or null if empty.
     */
    public AnyType findMin( )
    {
        if( isEmpty( ) )
            throw new UnderflowException( );
        return findMin( root ).element;
    }

    /**
     * Find the largest item in the tree.
     * @return the largest item of null if empty.
     */
    public AnyType findMax( )
    {
        if( isEmpty( ) )
            throw new UnderflowException( );
        return findMax( root ).element;
    }

    /**
     * Find an item in the tree.
     * @param x the item to search for.
     * @return true if not found.
     */
    public boolean contains( AnyType x )
    {
        return contains( x, root );
    }
    /**
     * Find if the tree is perfect
     * @return true if perfect
     */
    public boolean isPerfect(){
    	if (root == null){
    		return true;
    	}
    	else{
    		if (isPerfect(root) >= 0){
    			return true;
    		}
    		else{
    			return false;
    		}
    	}
    }
    /**
     * Make the tree logically empty.
     */
    public void makeEmpty( )
    {
        root = null;
    }

    /**
     * Test if the tree is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return root == null;
    }

    /**
     * Print the tree contents in sorted order.
     */
    public void printTree( )
    {
        if( isEmpty( ) )
            System.out.println( "Empty tree" );
        else
            printTree( root );
    }
    /**
     * Finds the median of the tree
     * @return median
     */
    public AnyType median(){
    	if (root != null){
    	return this.nthElement((root.size()+ 1)/ 2);
    	}
    	else{
    		return null;
    	}
    	
    }
    /**
     * returns the nth element of an in-order traversal
     * @param n
     * @return element of nth node
     */
    public AnyType nthElement(int n){
    	n--;
    	if (root == null){
    		return null;
    	}
    	if (n < 0 || n >= root.size()){
    		return null;
    	}
    	BinaryNode<AnyType> x = nthElement(root, n);
    	if (x != null){
    	return x.element;
    	}
    	else{
    		return null;
    	}
    }
    	
    /**
     * generates the level-order output
     * @param nrLevels
     * @return output
     */
    public String toString(int nrLevels){
    	String levelStr = "";
    	String fullStr = "";
    	for (int i = 1; i <= nrLevels; i++){
    		counter = 0;
    		fullStr += "Level " + i + ":\n";
    		fullStr += print(i, levelStr, root) + "\n";
    	}
    	return fullStr;
    }
    /**
     * Internal function to make each level into a string and return it
     * @param level
     * @param str
     * @param n
     * @return string describing the tree
     */
    private String print(int level, String str, BinaryNode<AnyType> n){
    if (n == null){
    	return "";
    }
    if (level == 1){
    	counter++;
    	if (counter % 6 == 0){
    		return n.toString() + "\n";
    	}
    	else{
    		return n.toString();
    	}
    	
    	
    }
    else{
    	return print(level - 1, str, n.left) + print(level - 1, str, n.right);
    }
    }
    /**
	 * generates the rank of the node an returns a negative number if it does not exist
	 * @param element x
	 * @return rank
	 */
	public int rank(AnyType x){ 
		if (root != null){
			return rank(x, root);
		}
		else{
			return -1000000;
		}
	}
	/**
	 * Internal method to test is tree is perfect
	 * @param x
	 * @return depth of node
	 */
	private int isPerfect(BinaryNode<AnyType> x){
		if (x == null){
			return 0;
		}
		int l = isPerfect(x.left);
	
		if (l != -1 && l == isPerfect(x.right)){
			return l+1;
		}
		else{
			return -1;
		}		
	}

	/**
     * Internal method to insert into a subtree.
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<AnyType> insert( AnyType x, BinaryNode<AnyType> t )
    {
        if( t == null )
            return new BinaryNode<AnyType>( x, null, null );
        
        int compareResult = x.compareTo( t.element );
        if( compareResult < 0 ){
            t.left = insert( x, t.left );
            t.left.parent = t;
        }
        else if( compareResult > 0 ){
            t.right = insert( x, t.right );
            t.right.parent = t;
        }    
        else
            ;  // Duplicate; do nothing
        return t;
    }

    /**
     * Internal method to remove from a subtree.
     * @param x the item to remove.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<AnyType> remove( AnyType x, BinaryNode<AnyType> t )
    {
        if( t == null ){
        	removed = false;
        	return t;   // Item not found; do nothing
        }
        int compareResult = x.compareTo( t.element );
            
        if( compareResult < 0 ){
            t.left = remove( x, t.left );
        }
        else if( compareResult > 0 ){
            t.right = remove( x, t.right );
        }
        else if( t.left != null && t.right != null ) // Two children
        {
            t.element = findMin( t.right ).element;
            t.right = remove( t.element, t.right );
        }
        else
            t = ( t.left != null ) ? t.left : t.right;
        return t;
    }

    /**
     * Internal method to find the smallest item in a subtree.
     * @param t the node that roots the subtree.
     * @return node containing the smallest item.
     */
    private BinaryNode<AnyType> findMin( BinaryNode<AnyType> t )
    {
        if( t == null )
            return null;
        else if( t.left == null )
            return t;
        return findMin( t.left );
    }

    /**
     * Internal method to find the largest item in a subtree.
     * @param t the node that roots the subtree.
     * @return node containing the largest item.
     */
    private BinaryNode<AnyType> findMax( BinaryNode<AnyType> t )
    {
        if( t != null )
            while( t.right != null )
                t = t.right;

        return t;
    }

    /**
     * Internal method to find an item in a subtree.
     * @param x is item to search for.
     * @param t the node that roots the subtree.
     * @return node containing the matched item.
     */
    private boolean contains( AnyType x, BinaryNode<AnyType> t )
    {
        if( t == null )
            return false;
            
        int compareResult = x.compareTo( t.element );
            
        if( compareResult < 0 )
            return contains( x, t.left );
        else if( compareResult > 0 )
            return contains( x, t.right );
        else
            return true;    // Match
    }
    /**
     * Internal method to return the rank of a given element
     * @param x, the element
     * @param n, the node
     * @return rank of the correct node
     */
    private int rank(AnyType x, BinaryNode<AnyType> n){
    	if (n == null){
    		return -1000000;
    	}
    	int a = x.compareTo(n.element);
    	if (a < 0){
    		if (n.left == null){
    			return -1000000;
    		}
    		else{
    			return rank(x, n.left);
    		}
    	}
    	else if (a > 0){
    		if (n.right == null){
    			return -1000000;
    		}
    		else if (n.left == null){
    			return 1 + rank (x, n.right);
    		}
    		else{
    			return 1 + n.left.size() + rank(x, n.right);
    		}
    	}
    	else{
    		if (n.left == null){
    		return 1;
    		}
    		else{
    			return 1 + n.left.size();
    		}
    		
    	}
    }
    /**
     * Internal method to find the node at a specific rank
     * @param n the node
     * @param x the rank
     * @return node at the specified value
     */
    private BinaryNode<AnyType> nthElement(BinaryNode<AnyType> n, int x){
    	if (n == null){
    		return null;
    	}
    	int a = 0;
    	if (n.left != null){
    		a = n.left.size();
    	}
    	if (a > x){
    		return nthElement(n.left, x);
    	}
    	else if (a < x){
    		return nthElement(n.right, x - a - 1);
    	}
    	else{
    		return n;
    	}
    }
    /**
     * Internal method to print a subtree in sorted order.
     * @param t the node that roots the subtree.
     */
    private void printTree( BinaryNode<AnyType> t )
    {
        if( t != null )
        {
            printTree( t.left );
            System.out.println( t.element );
            printTree( t.right );
        }
    }

    // Basic node stored in unbalanced binary search trees
    private static class BinaryNode<AnyType>
    {
            // Constructors
        @SuppressWarnings("unused")
		BinaryNode( AnyType theElement )
        {
            this( theElement, null, null);
        }

        BinaryNode( AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt )
        {
            element  = theElement;
            left     = lt;
            right    = rt;
        }
        public String toString(){
        	String str = "(" + this.element + ", " + this.size() + ", ";
        	if (parent == null){
        		str += "NULL)";
        	}
        	else {
        		str += parent.element + ")";
        	}
        	return str;
        }
        /**
         * method to find the size of a tree under a certain node
         * @return size of tree
	 	*/
        public int size(){
        	int lSize = 0;
        	int rSize = 0;
        	if (this.left != null){
			lSize = this.left.size();
        	}
        	if (this.right != null){
			rSize = this.right.size();
        	}
			return lSize + rSize + 1;
		}

		AnyType element;            // The data in the node
        BinaryNode<AnyType> left;   // Left child
        BinaryNode<AnyType> right;  // Right child
        BinaryNode<AnyType> parent;
    }


      /** The tree root. */
    private BinaryNode<AnyType> root;

}
