package CS542_2019SP_Project_29_Wang_Song;

import java.io.BufferedReader;               
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Scanner;


public class Main {
	public static int Network_topology[][];   // use to maintain the whole network matrix
	public static int Source_router_number;   // use to receive the router number in case 2
	public static int S_Router_number;        // use to receive the Source router number in case 3
	public static int D_Router_number;        // use to receive the Destination router number in case 3
		
	public static int[][] Add_New_Router()    // function to add new router in topology
	{
		int[][] New_Topology = new int[Network_topology.length+1][Network_topology.length+1];
		for(int a = 0; a < New_Topology.length; a++)                  // Initialization that assign every router value is -1
		{
			for(int b = 0; b < New_Topology.length;b++) {
				New_Topology[a][b] = -1;
			}
		}
		
		for(int a = 0; a < Network_topology.length; a++)            // loop statement and assign cost between routers to new variable
		{
			for(int b = 0; b < Network_topology.length;b++) {
				New_Topology[a][b] = Network_topology[a][b];
			}
		}
		
		return New_Topology;                                       // end the function and return the new topology matrix if use the function
	}
	
	public static void Update_Router(int New_R,int End_R, int Value)     // update edge weight
	{
		Network_topology[New_R-1][End_R-1] = Value;                      // assignment statement
	}

	public static void Show_New_Matrix() {                   // print the new matrix

		for(int a = 0; a < Network_topology.length ; a++) 
		{
			System.out.println("");
			for(int b = 0; b < Network_topology.length; b++)     // loop statement and show the new matrix
			{
				System.out.print(Network_topology[a][b]+",");
			}
		}
	}
	
	public static int[][] Del_Router()                        // Delete Router from end
	{
		int[][] New_Topology = new int[Network_topology.length-1][Network_topology.length-1];   
		for(int a = 0; a < Network_topology.length-1; a++) 
		{
			for(int b = 0; b < Network_topology.length-1;b++) {
				New_Topology[a][b] = Network_topology[a][b];
			}
		}
		return New_Topology;
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		boolean c = true;
		while(c) {                                            // show the main operation menu after end of the operation
		try {
			// Showing the operation menu and six opinions
			System.out.println("### Welcome Using Link-State Routing Algorithm and it was created by Song ###");
			System.out.println("Please choose one number of operation according to the following menu!");
			System.out.println("1.Input a Network Topology");
			System.out.println("2.Create a Forward Table");
			System.out.println("3.Paths from Source to Destination");
			System.out.println("4.Update Netwok Topology");
			System.out.println("5.Best Router for Broadcast");
			System.out.println("6.Exit");
			Scanner number = new Scanner(System.in);           // Get the number of operation from user
			int SelectedNO_menu = number.nextInt();
			Boolean check = false;                             // set a variable used to check whether user process the following steps before the first step
				switch (SelectedNO_menu) {                     // use switch method to show there are six case and choices
				case 1:
					// calls take_file() method from getfile() class to take input as
					// the text file and display adjacency matrix
					getfile f = new getfile();
					Network_topology = f.take_file();			// return the topology matrix to getfile.java	
					System.out.println();
					check = true;                               // to check whether user process the following steps before the first step
					break;
				case 2:
					if (check) {                                // to check whether user process the following steps before the first step
						System.out.println("Please process the first step and then start with the following steps!");
						break;
					} else {
						Source_router_number = 0;               // Initialization the source router number
						Scanner SR = new Scanner(System.in);
						System.out.println("Select a number of source router!");
						Source_router_number = SR.nextInt();
						if(Source_router_number <= 0 || Source_router_number > (getfile.Matrix_number + 1)) {   // to check whether the source router number above the maximum value
							System.out.println("Please enter the number of source router is less than total router number or more than or equal 0 \n");
						} else {
							// using the functionality of GetForwardTable in GetForwardTable class
							getfile a = new getfile();         // to use the getfile() class
							a.GetForwardTable();               // using the GetForwardTable function in getfile() class
						}
						break;	
					}
				case 3:
					if (check) {
						System.out.println("Please process the first step and then start with the following steps!");
						break;
					} else {
						System.out.println("Enter a number of Source Router!");
						Scanner SR1 = new Scanner(System.in);
						S_Router_number = SR1.nextInt();
						if(S_Router_number <= 0 || S_Router_number > (getfile.Matrix_number + 1)) {   // to check whether the source router number above the maximum value
							System.out.println("Please enter the number of Source router is less than total router number or more than or equal 0 \n");
						} else {
						System.out.println("Enter a number of Destination Router!");
						Scanner DR = new Scanner(System.in);
						D_Router_number = DR.nextInt();
						if(D_Router_number <= 0 || D_Router_number > (getfile.Matrix_number + 1)) {   // to check whether the source router number above the maximum value
							System.out.println("Please enter the number of Destination router is less than total router number or more than or equal 0 \n");
						} else {
						getfile b = new getfile();
						b.TakeCostLeastPath(Network_topology, (S_Router_number-1), (D_Router_number-1));   // set the return value contain topology matrix
						break;                                                                             // Source Router Number and Destination Router Number
					           }
						}
				        }
				case 4:
					if (check) {                   // to check whether user process the following steps before the first step
						System.out.println("Please process the first step and then start with the following steps!");
						break;
					} else {
					System.out.print("Please make a choice according to the following funcations:\n"
							+ "(1)Add a new router in the topology matrix\n"
							+ "(2)update a new edge in the topology matrix\n"
							+ "(3)delete a route in the topology matrix\n");
					Scanner s1 = new Scanner(System.in);
					if(s1.hasNextLine())          // get the variable from user in the next line
					{
						String op = s1.nextLine();
						if(op.equals("1")) 
						{
							Network_topology =Add_New_Router();
							Show_New_Matrix();   // using the Show_New_Matrix function
							System.out.println("\n");
						}else if(op.equals("2")){
							Scanner s2 = new Scanner(System.in);
							String edge = null;
							System.out.println("Please input a new router number and weight: r1,r2,weight");
							if(s2.hasNextLine()) 
							{
								edge = s2.nextLine();
							}else 
							{
								System.out.println("Please input the correct value");
								continue;
							}
							String[] s = edge.split(",");
							int z1 = Integer.valueOf(s[0]);
							int z2 = Integer.valueOf(s[1]);
							int z3 = Integer.valueOf(s[2]);
							Update_Router(z1,z2,z3);
							Show_New_Matrix(); // using the Show_New_Matrix function
							System.out.println("\n");
						}else if(op.equals("3")){
							Network_topology =Del_Router();
							Show_New_Matrix();
							System.out.println("\n");
						}
					}
					break;
					}
				case 5:
				{
					if (check) {                   // to check whether user process the following steps before the first step
						System.out.println("Please process the first step and then start with the following steps!");
						break;
					} else {
					getfile g = new getfile();
					g.getBroadcasting(Network_topology);
					break;
					}
				}
				case 6:
				{
					System.out.println("Exit!\n" + "Good Bye !!!\n" + "CS542_2019SP_Project_29_Wang_Song\n");
				     System.exit(0);
				}
				default :                         // to check whether user enter other things except the number of menu
				{
					System.out.println("###  Please input the correct number of menu!  ###");
					break;
				}
				}
		} catch(Exception ex) {
		System.out.println("###  Please input the correct number of menu!  ###");
		ex.printStackTrace();
	}  //D:\JAVA\Songcs542\topology.txt
		}
	}
}