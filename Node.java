/**
 * This class is responsible for storing the parsed csv file's contents into Nodes,
 * which are then inserted into a BinarySearchTree. Only the names and deathrates are stored,
 * along with addresses to each nodes right and left children. Lastly, a uniform print format
 * is utilized for displaying the information neatly to the user.
 * 
 */
public class Node
{
	
	String stateName;
	double DR;
	Node leftChild;
	Node rightChild;
	
	public String toString()
	{
		return String.format("%-20s", stateName) + String.format("%.2f", DR);
	}
}
