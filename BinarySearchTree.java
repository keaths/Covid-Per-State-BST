/**
 * 
 * This class is responsible for performing the calculations from Project4's menu options.
 * Abilities inclued inorder, preorder, and postorder traversal, along with printing
 * a state's given path through the binary search tree, deleting a state from the tree, and 
 * finally printing the top and bottom (user desired amount) states according to the deathrate.
 * 
 */
import java.util.ArrayList;

public class BinarySearchTree 
{
	public Node root;
	public ArrayList <String> path = new ArrayList<>();
	public Node[] states = new Node[50];
	public Node highest;
	public Node lowest;
	public int i = 0;


	public BinarySearchTree()
	{
		root = null;
	}
	
	/**
	 * This method inserts the states based on their name, with the lower alphetical states
	 * residing within the left side of the BST, and the higher alphabetical states residing
	 * within the right side of the tree.
	 * 
	 * @param stateName is the name of each state Node, used for insertion sorting for the tree.
	 * @param DR is the deathrate for each state Node.
	 */
	public void insert(String stateName, Double DR)
	{
		Node newState = new Node();
		newState.stateName = stateName;
		newState.DR = DR;
		states[i] = newState;
		if(root == null)
		{
			root = newState;
			i++;
		}
		else
		{
			Node current = root;
			Node parent;
			while(true)
			{
				parent = current;
				if(stateName.compareTo(current.stateName)  < 0)
				{
					current = current.leftChild;
					if(current == null)
					{
						parent.leftChild = newState;
						i++;
						return;
					}
				}
				else
				{
					current = current.rightChild;
					if(current == null)
					{
						parent.rightChild = newState;
						i++;
						return;
					}
				}
			}
		}
		
	}
	
	
	/**
	 * This method is responsible for locating the target state for deletion. For error correction
	 * of the user entering a non-existing state, the search method is utilized before this one. This
	 * method successfully locates the desired state, and sets it's value to null, skipping the display
	 * of that Node when displaying the tree after the fact.
	 * 
	 * @param searchDelState
	 */
	public void delete(String searchDelState)
	{
		Node current = root;
		Node parent = root;
		boolean isLeftChild = true;
		
		while(!current.stateName.equals(searchDelState))
		{
			parent = current;
			if(searchDelState.compareTo(current.stateName) < 0)
			{
				isLeftChild = true;
				current = current.leftChild;
			}
			else
			{
				isLeftChild = false;
				current = current.rightChild;
			}
			if(current == null)
			{
				return;
			}
		}
		
		if(current.leftChild == null && current.rightChild == null)
		{
			if(current == root)
			{
				root = null;
			}
			else if(isLeftChild)
			{
				parent.leftChild = null;
			}
			else
			{
				parent.rightChild = null;
			}
		}
		
		else if(current.rightChild == null)
		{
			if(current == root)
			{
				root = current.leftChild;
			}
			else if(isLeftChild)
			{
				parent.leftChild = current.leftChild;
			}
			else
			{
				parent.rightChild = current.leftChild;
			}
		}
		
		else if(current.leftChild == null)
		{
			if(current == root)
			{
				root = current.rightChild;
			}
			else if(isLeftChild)
			{
				parent.leftChild = current.rightChild;
			}
			else
			{
				parent.rightChild = current.rightChild;
			}
		}
		
		else
		{
			Node successor = getSuccessor(current);
			if(current == root)
			{
				root = successor;
			}
			else if (isLeftChild)
			{
				parent.leftChild = successor;
			}
			else
			{
				parent.rightChild = successor;
			}
			successor.leftChild = current.leftChild;
		}
		return;
	}
	
	/**
	 * This method is called upon in the Delete method, where when a state is successfully deleted,
	 * it's successor must be located to replace that Node.
	 * 
	 * @param delNode is the Node to be deleted.
	 * @return
	 */
	public Node getSuccessor(Node delNode)
	{
		Node successorParent = delNode;
		Node successor = delNode;
		Node current = delNode.rightChild;
		while(current != null)
		{
			successorParent = successor;
			successor = current;
			current = current.leftChild;
		}
		
		if(successor != delNode.rightChild)
		{
			successorParent.leftChild = successor.rightChild;
		}
		return successor;
	}
	
	/**
	 * This method prints the BST via Inorder traversal, which gives the overall
	 * heirarchy regarding the designated value of each node. In this case, it's the 
	 * state's names, as can be seen when this method is utilized within Project4, 
	 * it's in alphabetical order.
	 * 
	 * @param localRoot is the root of the BST
	 */
	public void printInorder(Node localRoot)
	{

		if(localRoot != null)
		{
			printInorder(localRoot.leftChild);
			System.out.println(localRoot);
			printInorder(localRoot.rightChild);
		}
	}
	
	/**
	 * This method prints the BST via Preorder traversal, which displays the parsed information
	 * in an NLR manner, with the neutral Node being the highest priority, and the right child being
	 * the lowest priority, and the left child being directly in between. 
	 * 
	 * @param localRoot is the root of the BST
	 */
	public void printPreorder(Node localRoot)
	{
		if(localRoot != null)
		{
			System.out.println(localRoot);
			printPreorder(localRoot.leftChild);
			printPreorder(localRoot.rightChild);
		}
	}
	
	/**
	 * This method prints the BST via Postorder traversal, which displayes the parsed information
	 * in an LRN manner, with the left child being the highest priority, the Neutral node being the lowest,
	 * and the right child being directly in between.
	 * 
	 * @param localRoot is the root of the BST
	 */
	public void printPostorder(Node localRoot)
	{
		if(localRoot != null)
		{
			printPostorder(localRoot.leftChild);				
			printPostorder(localRoot.rightChild);
			System.out.println(localRoot);
		}
	}
	
	
	
	/**
	 * This method is responsible for locating a Node based on it's stateName, whether it be deleteting a Node
	 * or just finding a Node. For each node searched, a small string is added to a public String array to capture
	 * the path leading to that state. If current equals null, a message is displayed that the state could not be
	 * found, and returns 0.0, which is not handled within Project4.
	 * 
	 * @param searchState is the state that is being searched for
	 * @return the deathrate of the found state.
	 */
	public Double find(String searchState)
	{
		Node current = root;
		
		while(!current.stateName.equals(searchState))
		{
			if(searchState.compareTo(current.stateName) < 0)
			{
				current = current.leftChild;
				if(current != null) 
				{
				path.add(current.stateName + " -> ");
				}
				if(current == null)
				{
					System.out.println(searchState + " was not found");
					System.out.println("\n-------------------------------------------------------------------------------------");
					return 0.0;
				}
			}
			else
			{
				current = current.rightChild;
				if(current != null)
				{
				path.add(current.stateName + " -> ");
				}
				if(current == null)
				{
					System.out.println(searchState + " was not found");
					System.out.println("\n-------------------------------------------------------------------------------------");
					return 0.0;
				}
			}
		}

		return current.DR;
	}
	
	/**
	 * This method sorts the array of states via DR, for usage later in printing the top
	 * and bottom states.
	 * 
	 */
	public void sortArray()
	{
		 int inner;
		 int outer = 1;
		 while(outer < states.length)
		 {
			 Node temp = states[outer];
			 inner = outer - 1;
			 while(inner >= 0 && states[inner].DR > temp.DR)
			 {
				 states[inner + 1] = states[inner];
				 inner--;
			 }
			 states[inner+1] = temp;
			 outer++;
		 }
	}
	
	/**
	 * This method prints the top states descendingly based on user discretion.
	 * If any number is above the the amount of elements within the array, all states
	 * are automatically displayed.
	 * 
	 * @param c is the user desired amount of states to be displayed.
	 */
	public void printBottomStates(int c)
	{
		if(c < 49)
		{
			for(i = 49; i > 49-c; i--)
			{
				System.out.println(states[i]);
			}
		}
		else
		{
			for(i = 49; i > 0; i--)
			{
				System.out.println(states[i]);
			}
		}
	}
	
	/**
	 * This method prints the bottom states ascendingly based on user discretion.
	 * If any number is above the the amount of elements within the array, all states
	 * are automatically displayed.
	 * 
	 * @param c is the user desired amount of states to be displayed.
	 */
	public void printTopStates(int c)
	{
		if(c < 49)
		{
			for(i = 0; i < 0 + c; i++)
			{
				System.out.println(states[i]);
			}
		}
		else
		{
			for(i = 0; i < 49; i++)
			{
				System.out.println(states[i]);
			}
		}
	}
}
