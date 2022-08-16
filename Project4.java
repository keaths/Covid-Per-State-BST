/**
 * COP 3530: Project 4 - Binary Search Tree
 * <p>
 * This class is responsible for displaying the results of the user's menu choice,
 * as well as parsing the States4.csv file into seperate nodes, which are then inserted by name
 * and is viewable to the user by any choice of the menu. It successfully performs
 * each request, with included handling of improper request entry.
 * 
 * @author <Keath Sawdo>
 * @version <3/27/21>
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Project4 {

	public static void main(String[] args)
	{
		System.out.println("COP3530 Project 4");
		System.out.println("Instructor: Xudong Liu\n");
		
		Scanner scan = new Scanner(System.in);
		System.out.print("Please enter the file name: ");
		String fileName = scan.next();
		
		Scanner inFile = null;
		try
		{																					
			inFile = new Scanner(new File(fileName));
		}
		catch (FileNotFoundException e)
		{
			System.out.println("\nCould not open file!");
			System.exit(1);
		}
		
		inFile.useDelimiter(",|\n");
		inFile.nextLine();
		
		BinarySearchTree stateTree = new BinarySearchTree();
		int i = 0;
		String menuChoice = null;
		String header = String.format("%-20s", "Name") + "Death Rate\n----------------------------------------";
		String spacer = ("\n-------------------------------------------------------------------------------------");
		while(inFile.hasNext())
		{
			String name = inFile.next();
			String capital = inFile.next();
			String region = inFile.next();
			int USHouse = inFile.nextInt();
			int population = inFile.nextInt();											
			int cases = inFile.nextInt();
			Double caseRate = (double) cases/population * 100000;		//unprovided attributes are directly calculated based off of related attribute values, no methods.
			int deaths = inFile.nextInt();
			Double deathRate = (double) deaths/population * 100000;
			Double caseFatalityRate = (double) deaths/cases;
			int medianHouseholdIncome = inFile.nextInt();
			double crimeRate = Double.parseDouble(inFile.next());
			stateTree.insert(name, deathRate);							//Nodes name and deathrate are inserted into the BinarySearchTree, will be sorted according to State name.
			i++;
		}
		do
		{
			System.out.println("\nThere were " + i + " records read to build a binary search tree.\n");
			System.out.println("1) Print tree inorder\n2) Print tree preorder\n3) Print tree postorder\n"
					+ "4) Delete a state for a given name\n5) Search and print a state and its path for a given name\n"						//menu of options displayed to user
					+ "6) Print bottom states ragarding DR\n7) Print top states regarding DR\n8) Exit");
			System.out.print("\nEnter your choice: ");
			menuChoice = scan.next();
			boolean num = menuChoice.matches("-?\\d+(\\.\\d+)?");																			//error correction for String regex if the user's entry is not identified as an integer
			while(num == false || Integer.parseInt(menuChoice) > 8 || Integer.parseInt(menuChoice) < 1)
			{
				System.out.print("Invalid Entry. Please enter a valid menu option: ");
				menuChoice = scan.next();
				num = menuChoice.matches("-?\\d+(\\.\\d+)?");
			}
			switch(menuChoice)
			{
				case "1":
				{
					
					System.out.print("\nInorder traversal");											//displays the tree via Inorder traversal
					System.out.println("\n" + header);
					stateTree.printInorder(stateTree.root);
					System.out.println(spacer);
					break;
				}
				case "2":
				{
					System.out.print("\nPreorder traversal");
					System.out.println("\n" + header);													//displays the tree via Preorder traversal
					stateTree.printPreorder(stateTree.root);
					System.out.println(spacer);
					break;
				}
				case "3":																				//displays the tree via Postorder traversal
				{
					System.out.print("\nPostorder traversal");
					System.out.println("\n" + header);
					stateTree.printPostorder(stateTree.root);
					System.out.println(spacer);
					break;
				}
				case "4":
				{
					System.out.print("\nPlease enter a state you'd like to delete: ");
					String delState = scan.next();
					Double dr = stateTree.find(delState);												//before deleting the state, find method is utilized to see if state exists within BST
					if(dr != 0.0)
					{																					//if find does not return 0.0 (false), state was found, deleted, and a notification is displayed.
						stateTree.delete(delState);
						System.out.println(delState + " was deleted");
						System.out.println(spacer);
					}
					stateTree.path.clear();																//path of searching state is cleared for further use.
					break;
				}
				case "5":
				{
					System.out.print("\nPlease enter a state you'd like to search: ");
					String stateSearch = scan.next();
					Double dr = stateTree.find(stateSearch);
					if(dr != 0.0)																							//if find does not return 0.0 (false), state DR is displayed, along with it's path.
					{
						System.out.printf("\n" + stateSearch + " is found with death rate of %.2f\n", dr);
						System.out.print("Path to " + stateSearch + " is: ");
						for(i = 0; i < stateTree.path.size(); i++)
						{
							System.out.print(stateTree.path.get(i));
						}
						System.out.println(spacer);
					}
					stateTree.path.clear();																				//path of searching state is cleared for further use.
					break;
				}
				case "6":
				{
					stateTree.sortArray();																			//array is sorted according to DR
					System.out.print("Enter the number of states: ");
					String stateNum = scan.next();
					boolean isNum = stateNum.matches("-?\\d+(\\.\\d+)?");											//entry is checked for validation as an integer via String regex
					while(isNum == false)
					{
						System.out.print("Invalid entry. Please enter a valid entry: ");							
						stateNum = scan.next();
						isNum = stateNum.matches("-?\\d+(\\.\\d+)?");
						System.out.println();
					}
					System.out.println("\nBottom " + stateNum + " states regarding DR:");							//if String is parsable as an integer, the bottom states are displayed
					System.out.println(header);
					stateTree.printBottomStates(Integer.parseInt(stateNum));
					System.out.println(spacer);
					break;
				}
				
				case "7":
				{
					stateTree.sortArray();																			//array is sorted according to DR
					System.out.print("Enter the number of states: ");
					String stateNum = scan.next();
					boolean isNum = stateNum.matches("-?\\d+(\\.\\d+)?");											//entry is checked for validation as an integer via String regex
					while(isNum == false)
					{
						System.out.print("Invalid entry. Please enter a valid entry: ");
						stateNum = scan.next();
						isNum = stateNum.matches("-?\\d+(\\.\\d+)?");
						System.out.println();
					}
					System.out.println("\nTop " + stateNum + " states regarding DR:");								//if String is parsable as an integer, the top states are displayed
					System.out.println(header);
					stateTree.printTopStates(Integer.parseInt(stateNum));
					System.out.println(spacer);
					break;
				}
				case "8":
				{
																												
				}
			}
		}while(!menuChoice.equals("8"));
		if(menuChoice.equals("8"))
		{
			System.out.println("goodbye!");																			//goodbye message to user if exit is chosen.
		}
	}
}
