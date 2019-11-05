package CS542_2019SP_Project_29_Wang_Song;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;


public class getfile {     
	private class Pair {
		// to use store the values of shortestPath and shortestDest
		public int[] shotestPath;                 // to use store the values of shortestPath
		public int[] shortestDest;                // to use store the values of shortestDest
	
		Pair(int[] shotestPath, int[] shortestDist) {     // to use store the values of shortestPath and shortestDest
			this.shotestPath = shotestPath;
			this.shortestDest = shortestDist;
		}
	}
	
	
	static int S_router;                          // Source Number
	static int D_router;                          // Destination Number
	static int Topology_Network_Matrix[][];       // Initialization of network topology matrix
	static BufferedReader Buffering = null;       // Setting a Buffer to store the file path
	static String Topology_File_Path = "";        // Setting up the network file path
	static int Matrix_number = 0;                 // Store the length of Matrix
	static String Check = null;                   // Setting up the value that used to check whether processing the first operation
	static String Read_Row[];                     // Getting the information about Row
	static String Recent_Row[];                   // Store the latest Row
	static String[] Interface;                    // In case2, to store the adjacency Router number
	static int matrixLength;                      // to store the length of network topology matrix
	static int Row_number = 0;                    // to store the number of row
	static String Row = "";                       // Initialization of Row value
	
	
	public int[][] take_file() {
			Scanner input = new Scanner(System.in);                                // get the information from what you type
			System.out.println("Please enter the file path in this line");
			Topology_File_Path = input.nextLine();                                 // from the next line
			try{
				Buffering = new BufferedReader(new FileReader(Topology_File_Path));// start with the BufferedReader method
				Read_Row = Buffering.readLine().trim().split("\\s+");           // reading the information from the file
				Matrix_number = Read_Row.length;                                // The number is as same as total router number
				Topology_Network_Matrix = new int[Matrix_number][Matrix_number];// put the value from matrix file into the variable
				Buffering.close();                                              // close the buffer
				BufferedReader buf_data = new BufferedReader(new FileReader(Topology_File_Path)); //use BufferedReader method to get every value
				while ((Row = buf_data.readLine()) != null) {                   // prevent the value that user enter is null
					Recent_Row = Row.trim().split("\\s+");                      // read the value for every row and assign to new variable
					for (int i = 0; i < Matrix_number; i++) {                   // assign the value from matrix into memory and
						Topology_Network_Matrix[Row_number][i] = Integer.parseInt(Recent_Row[i]);// change the value into integer
					}
					Row_number++;                                               // increasing the line number
				}
				for (int a = 0; a < Row_number; a++) {                            // getting the value of matrix length
					for (int b = 0; b < Topology_Network_Matrix[a].length; b++) { // getting the value of matrix length
						if (Topology_Network_Matrix[a].length == Row_number);     // prevent the cycle number above the matrix value
					}

				}
					System.out.println("The # of Routers = " + Matrix_number);       // print the matrix number
					System.out.println("********** The network topology is shown below: **********");  // showing the title for matrix
					for (int a = 1; a <= Matrix_number; a++) {
						System.out.print("\t" + "R" + (a));                          // show the router name in column in the matrix
					}
					System.out.println("\n");                                        //show the router name in row in the matrix
					for (int a = 1; a <= Matrix_number; a++) {
						System.out.print("R" + (a) + "\t");
						for (int b = 1; b <= Matrix_number; b++) {                      // enter every value about the cost and display in screen
							System.out.print(Topology_Network_Matrix[a-1][b-1] + "\t"); // show the whole network topology matrix
						}
						System.out.println();
					}
			} catch (Exception ex) {                                                 // throw out the exception
				System.out.println("Please enter the correct file path again!!!");  
			}
			return Topology_Network_Matrix;                                         // return the topology matrix to main.java and then will
			                                                                        // be used in following steps
	}
	public void GetForwardTable() {                               // the function to get forward table from network topology matrix
		Interface = new String[Topology_Network_Matrix.length];   // define interface variable and assign value
		for (int s = 0; s < Matrix_number; ++s) {                 // start cycle
			if (s == (Main.Source_router_number - 1)) {           // to check whether interface link is source router
				Interface[s] = "¡ª";
			} 
			else if(Topology_Network_Matrix[(Main.Source_router_number - 1)][s] == -1)  // to check whether two router no connection
			{
				Interface[s] = "10000";                                                 // and assign the infinite value
			}else 
			{
				Interface[s] = String.valueOf(Topology_Network_Matrix[(Main.Source_router_number - 1)][s]);   // change value to string genre
			}
		}
		System.out.println("Destination     Interface Link");
		for (int a = 0; a < Topology_Network_Matrix.length; a++) {
			System.out.println("   " + (a+1) + "                " + Interface[a]);      // output the value about matrix interface
		}
	}
	public Pair LeastPath(int Source_Router_Number, int[][] Network_Topology_Matrix) {  // find the neighboring node that has the least-cost path
		int Recent_Min = 0;                                                             // using Dijkstra algorithm 
		int Matrix_Value = Network_Topology_Matrix.length;                              // make the router number equals to a new variable
		int New_Net_Topology_Matrix[][] = new int[Matrix_Value][Matrix_Value];          // assignment matrix value to a new matrix
		for (int a = 0; a < Network_Topology_Matrix.length; a++) {                      // start with a loop statement
			for (int b = 0; b < Network_Topology_Matrix.length; b++) {
				New_Net_Topology_Matrix[a][b] = Network_Topology_Matrix[a][b];          // assignment statement
			}
		}

		int[] Net_Router = new int[Matrix_Value];                                       // assignment statement
		int[] Net_Cost = new int[Matrix_Value];
		boolean[] Used_Router = new boolean[Matrix_Value];

		final int MAX_VAL = 10000;                                                      // Define a variable with the maximum value
		for (int i = 0; i < Matrix_Value; i++) {                                        // Starting with a loop statement
			Net_Router[i] = Source_Router_Number;                                       // Source router number
			Net_Cost[i] = MAX_VAL;                                                      // Dm that is the least cost from source to m
			Used_Router[i] = false;
			for (int j = 0; j < Matrix_Value; j++) {
				if (Network_Topology_Matrix[i][j] == -1) {                              // To check whether the router is source router
					New_Net_Topology_Matrix[i][j] = MAX_VAL;                            // To check whether two routers have connection
				}
			}
		}
		Net_Cost[Source_Router_Number] = 0;                                             // restart the source router matrix value
		Used_Router[Source_Router_Number] = true;                                       // to check whether visited router
		int Recent_ind = Source_Router_Number;                                          // assign the new index
		for (int i = 0; i < Matrix_Value; i++) {                                        // start with a loop statement
			for (int j = 0; j < Matrix_Value; j++) {                                    // start with a loop statement
				if (!Used_Router[j] && Net_Cost[j] > Recent_Min + New_Net_Topology_Matrix[Recent_ind][j]) {
					Net_Cost[j] = Recent_Min + New_Net_Topology_Matrix[Recent_ind][j];  // assignment statement
					Net_Router[j] = Recent_ind;                                         // assignment statement 
				}
			}
			Used_Router[Recent_ind] = true;                                             // starting visited router and assign boolean value
			int[] retArray = findMinimum(Net_Cost, Used_Router);                        // use findMinimum method and return network cost and visited router
			Recent_Min = retArray[0];                                                   // update the array values
			Recent_ind = retArray[1];                                                   // update the array values
		}
		return new Pair(Net_Router, Net_Cost);                                          // return network router and network cost
	}
	
	// used to display the shortest distance and path from source and destination
	public void TakeCostLeastPath(int Network_Topology_Matrix[][], int Source_A_Number, int Destination_A_Number) {
		Pair Final_Value = LeastPath(Source_A_Number, Network_Topology_Matrix);
		// showing the shortest distance from Source to destination
		if (Final_Value.shortestDest[Destination_A_Number] == 10000) { // loop statement if the routers no connection
			System.out.println("\n There is no path from " + Source_A_Number + " to " + Destination_A_Number + "\n");
		} else {
			System.out.println("The Cost of Shortest distance from " + (Source_A_Number+1) + " to " + (Destination_A_Number+1) + " is: "
					+ Final_Value.shortestDest[Destination_A_Number]);
			// Print the shortest path from Source to destination
			System.out.println("Shortest path from " + (Source_A_Number+1) + " to " + (Destination_A_Number+1) + " is: (Source Router is in rightmost)");
			int currIndex = Destination_A_Number;
			System.out.print(currIndex+1);                                             // print the Destination Router Number
			do {                                                                       // Loop statement
				currIndex = Final_Value.shotestPath[currIndex];
				System.out.print("<--" + (currIndex+1));
			} while (currIndex != Source_A_Number);

			System.out.println();
		}
	}

	// Finds the minimum cost by comparing currMin and cost[i]
	private int[] findMinimum(int[] cost, boolean[] visited) {
		int currMin = 10000;                                   // define the recent minimum 
		int currMinIndex = 0;                                  // assignment statement
		for (int i = 0; i < cost.length; i++) {                // loop statement
			if (!visited[i] && currMin > cost[i]) {
				currMin = cost[i];
				currMinIndex = i;
			}
		}
		int[] retArray = new int[] { currMin, currMinIndex }; // returns the array with currMin and index of that router
		return retArray;                                      // set return value is whole array
	}
		
	
	public void getBroadcasting(int[][] matrix)               // broadcasting method and get the total value from source to destination
	{
		int weight[] = new int[Topology_Network_Matrix.length];  // calculate the router number in the topology matrix
		for(int i = 0; i < Topology_Network_Matrix.length;i++)   // start a loop statement and enter value into getTotalCost method
		{
			weight[i] = getTotalCost(i,matrix);
			System.out.println("The route:"+(i+1)+"total weight is "+weight[i]);
		}
		int min = 10000;                                         // assign infinite value
		int index = -1;
		for(int i = 0; i < weight.length;i++)                    // loop statement and set the index
		{
			if(min > weight[i])
			{
				min = weight[i];
				index = i;
			}
		}
		
		System.out.println("The best Router for Broadcasting is : "+ (index+1) + "\n"
				+ "The weight is:" + weight[index]);             // print the total cost for source router and destination
	}
	
	public int getTotalCost(int Source_Router_Number, int[][] Network_Topology_Matrix) { // get the total least cost from source to every other routers
		int Recent_Min = 0;
		int Matrix_Value = Network_Topology_Matrix.length;
		int New_Net_Topology_Matrix[][] = new int[Matrix_Value][Matrix_Value];
		int totalCost= 0;
		for (int a = 0; a < Network_Topology_Matrix.length; a++) {                       // restart read the topology matrix values 
			for (int b = 0; b < Network_Topology_Matrix.length; b++) {
				New_Net_Topology_Matrix[a][b] = Network_Topology_Matrix[a][b];           // assignment statement
			}
		}

		int[] Net_Router = new int[Matrix_Value];
		int[] Net_Cost = new int[Matrix_Value];
		boolean[] Used_Router = new boolean[Matrix_Value];

		final int MAX_VAL = 10000;                             // loop statement get cost least path and store in different variable
		for (int i = 0; i < Matrix_Value; i++) {
			Net_Router[i] = Source_Router_Number;
			Net_Cost[i] = MAX_VAL;                            // Initialization of every router cost is infinite
			Used_Router[i] = false;
			for (int j = 0; j < Matrix_Value; j++) {          // loop statement assign the router which has the value -1 to infinite value
				if (Network_Topology_Matrix[i][j] == -1) {    // in order to compare and know which is the least cost for adjacency router
					New_Net_Topology_Matrix[i][j] = MAX_VAL;
				}
			}
		}
		Net_Cost[Source_Router_Number] = 0;
		Used_Router[Source_Router_Number] = true;
		int Recent_ind = Source_Router_Number;               // Restarting assign the number of router to new recent index
		for (int i = 0; i < Matrix_Value; i++) {
			for (int j = 0; j < Matrix_Value; j++) {        // for loop statement and get the new value for every adjacency router with least-cost
				if (!Used_Router[j] && Net_Cost[j] > Recent_Min + New_Net_Topology_Matrix[Recent_ind][j]) {
					Net_Cost[j] = Recent_Min + New_Net_Topology_Matrix[Recent_ind][j];
					Net_Router[j] = Recent_ind;
				}
			}
			Used_Router[Recent_ind] = true;
			int[] retArray = findMinimum(Net_Cost, Used_Router);    // to use the findMinimum method and get the value of cost and visited router
			Recent_Min = retArray[0];
			Recent_ind = retArray[1];
		}
		
		for(int i = 0 ; i < Net_Cost.length; i++) 
		{
			totalCost = totalCost + Net_Cost[i];                  // assignment statement
		}
		return totalCost;
	}
		
}
