package edu.wm.cs.cs301.ScottHanna.generation;

import java.util.logging.Logger;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

//WORKING ON REMOVING LOOPS FROM MAZE, CURRENT IDEA IS TO CHECK IF A WALL
//THAT WOULD BE REMOVED WOULD LEAD TO A CELL THAT IS IN THAT SAME FOREST

public class MazeBuilderBoruvka extends MazeBuilder implements Runnable{
	private HashMap<String,Integer> wallboardweights=new HashMap<>();
	private ArrayList<String> wallboardnames= new ArrayList<String>();
	private ArrayList<ArrayList<int[]>> Forest= new ArrayList<ArrayList<int[]>>();
	//private ArrayList<int[]>forestadded=new ArrayList<int[]>();
	//private ArrayList<ArrayList<int[]>> temporaryMST= new ArrayList<ArrayList<int[]>>();
	private Map<String,Integer>candidates=new HashMap<String,Integer>();
	
	


	
	private static final Logger LOGGER = Logger.getLogger(MazeBuilderBoruvka.class.getName());

	public MazeBuilderBoruvka() {
		super();
		LOGGER.config("Using Boruvka's algorithm to generate maze.");
	}
	//Create Dictionary and arraylist
	
	/* FOR GENERATEPATHWAYS
	 * First call the ADDWALLNAMESSTOLISTOFWALLBOARDNAMES to add all the wallboardnames to the arraylist
	 * Next call assignrandomweight and assign the wall:weight key-value pair to the dictionary
	 * Then use Boruvka's algorithm to eliminate the correct walls use deleteWallBoards
	 * Everytime a wall is deleted between to cells those cells are added to a forest which eventuall become a MST
	 * Once that is done add the new trees to an array and repeat boruvka's algorithm until
	 * the correct number of walls are left
	 * Then we can use that floor plan to build the maze
	 */
	@Override
	protected void generatePathways() {
		/* Traverse through all the cells and add their cheapest walls to a list
		 * Make sure no wall is added twice e.g. 0+0+east and 1+0+west
		 */
		//Adds wall board names (x,y,direction) to wallboardnames
		AddWallnamestoListofWallBoardnames();
		//randomly assigns weights based on wallboardnames
		setrandomweights();
		//List of walls to break 
		
		//creates forests for each cell
		for(int x=0;x<width;x++) {
			for (int y=0;y<height;y++) {
				createforests(x,y);
			}}
		
		Map<String,Integer>temporary=new HashMap<String,Integer>();
		int [] weights= {1000000000,1000000000,1000000000,1000000000};
		for(String str:new ArrayList<String>(wallboardnames) ){
			String [] wallcomp=str.split("\\+");
			int x=Integer.parseInt(wallcomp[0]);
			int y=Integer.parseInt(wallcomp[1]);
			String dir=wallcomp[2];
			
			//puts the wallboards for each cell in a temp hashmap so the min can be chosen
			if(temporary.isEmpty()) {
				if (dir.equals("North")) {
					temporary.put(str,getEdgeWeight(x,y,CardinalDirection.North));
				}
				else if (dir.equals("South")) {
					temporary.put(str,getEdgeWeight(x,y,CardinalDirection.South));
									
								}
				else if (dir.equals("West")) {
					temporary.put(str,getEdgeWeight(x,y,CardinalDirection.West));
					
				}
				else {
					temporary.put(str,getEdgeWeight(x,y,CardinalDirection.East));
				}
				
				weights[0]=wallboardweights.get(str);
			}
			
			
			else if (temporary.containsKey(wallcomp[0]+"+"+wallcomp[1]+"+"+"East")||temporary.containsKey(wallcomp[0]+"+"+wallcomp[1]+"+"+"North")||temporary.containsKey(wallcomp[0]+"+"+wallcomp[1]+"+"+"West")||temporary.containsKey(wallcomp[0]+"+"+wallcomp[1]+"+"+"South")) {
				if (dir.equals("North")) {
					temporary.put(str,getEdgeWeight(x,y,CardinalDirection.North));
				}
				else if (dir.equals("South")) {
					temporary.put(str,getEdgeWeight(x,y,CardinalDirection.South));
									
								}
				else if (dir.equals("West")) {
					temporary.put(str,getEdgeWeight(x,y,CardinalDirection.West));
					
				}
				else {
					temporary.put(str,getEdgeWeight(x,y,CardinalDirection.East));
				}
				for(int q=0;q<4;q++) {
					if(weights[q]==1000000000) {
						weights[q]=wallboardweights.get(str);
						break;
					}
				}
			}
			else {
				//Temporary about to be erased for new cell
				//gets the minimum weight
				//adds that wall and list to candidates
				int min=getMin(weights);
				for(String key:temporary.keySet()) {
					if(temporary.get(key)==min) {
						wallboardnames.remove(key);
						wallboardweights.remove(key);
						candidates.put(key, min);
						break;
					}
				}
				temporary.clear();
				temporary.put(str,wallboardweights.get(str));
				weights[0]=wallboardweights.get(str);
				weights[1]=1000000000;
				weights[2]=1000000000;
				weights[3]=1000000000;
			}
			String tempx=Integer.toString(width-1);
			String tempy=Integer.toString(height-1);
			if (candidates.size()==width*height-1&&weights[1]==wallboardweights.get(tempx+"+"+tempy+"+"+"West")) {
				int min=getMin(weights);
				for(String key:temporary.keySet()) {
					if(temporary.get(key)==min) {
						wallboardnames.remove(key);
						wallboardweights.remove(key);
						candidates.put(key, min);
						break;
					}
				}
				temporary.clear();
			}
			
			}
		
		
		ArrayList<String> candtodelete=new ArrayList<String>();
		//making sure no wall appears twice in candidates
		for(String key:candidates.keySet()) {
			
			String [] wallcomp=key.split("\\+");
			int x=Integer.parseInt(wallcomp[0]);
			int y=Integer.parseInt(wallcomp[1]);
			String direction=wallcomp[2];
	
			int tempprevx=x-1;
			int tempprevy=y-1;
			
			if(direction.equals("West")&&(candidates.containsKey(tempprevx+"+"+wallcomp[1]+"+"+"East"))) {
				wallboardnames.remove(key);
				wallboardweights.remove(key);
				candtodelete.add(key);
				
				
				}
			else if((direction.equals("North")&&(candidates.containsKey(wallcomp[0]+"+"+tempprevy+"+"+"South")))) {
				wallboardnames.remove(key);
				wallboardweights.remove(key);
				candtodelete.add(key);
				
				}
			if(direction.equals("West")) {
				String tempx=Integer.toString(tempprevx);
				String tempy=Integer.toString(y);
				String name=tempx+"+"+tempy+"+"+"East";
				if (wallboardnames.contains(name)) {
					wallboardnames.remove(name);
					wallboardweights.remove(name);
				}
			}
			else if(direction.equals("East")) {
				String tempx=Integer.toString(x+1);
				String tempy=Integer.toString(y);
				String name=tempx+"+"+tempy+"+"+"West";
				if (wallboardnames.contains(name)) {
					wallboardnames.remove(name);
					wallboardweights.remove(name);
				}
			}
			else if(direction.equals("North")) {
				String tempx=Integer.toString(x);
				String tempy=Integer.toString(y-1);
				String name=tempx+"+"+tempy+"+"+"South";
				if (wallboardnames.contains(name)) {
					wallboardnames.remove(name);
					wallboardweights.remove(name);
				}
			}
			else if(direction.equals("South")) {
				String tempx=Integer.toString(x);
				String tempy=Integer.toString(y+1);
				String name=tempx+"+"+tempy+"+"+"North";
				if (wallboardnames.contains(name)) {
					wallboardnames.remove(name);
					wallboardweights.remove(name);
				}
			}
		
			
		}
		//Something to fix, it should not add a wall to candidates that would create a loop
		//make sure bottom right corner doesn't get add twice
		for(String value:candtodelete) {
			candidates.remove(value);
		}
		/* Use the list from above and delete those wallboards from the maze
		 * 
		 */
		int numtrees=width*height;
		while (numtrees>1) {
			
			//deleting the walls
			//Combine trees to forests when deleting wall
			Map<String, Integer>copy=new HashMap<String,Integer>();
			for(Map.Entry<String,Integer>entry:candidates.entrySet()) {
				copy.put(entry.getKey(), entry.getValue());
			}
			
			for(String key:copy.keySet()) {
			
				String [] wallcomp=key.split("\\+");
				int x=Integer.parseInt(wallcomp[0]);
				int y=Integer.parseInt(wallcomp[1]);
				String direction=wallcomp[2];
				//creating wallboard to pass to deletewallboard
				Wallboard cur;
				int[] cell= {x,y};
				int[] cellnorth= {x,y-1};
				int[] cellsouth= {x,y+1};
				int[] celleast= {x+1,y};
				int[] cellwest= {x-1,y};
				ArrayList<int[]>temptree=new ArrayList<int[]>();
				for(ArrayList<int[]>tree:Forest) {
					for(int[]arr:tree) {
						if(arr[0]==cell[0]&&arr[1]==cell[1]) {
							temptree=tree;
						}
					}
				}
				
				if (direction.equals("North")) {
					for(int[]arr1:temptree) {
						if(arr1[0]==cellnorth[0]&&arr1[1]==cellnorth[1]) {}
						else {
							cur=new Wallboard(x,y,CardinalDirection.North);
							floorplan.deleteWallboard(cur);	
							numtrees-=1;
						}
					}
				
					
					
				}
				else if (direction.equals("South")) {
					if(temptree.contains(cellsouth)) {
						
					}
					else {
						cur=new Wallboard(x,y,CardinalDirection.South);
						floorplan.deleteWallboard(cur);	
						numtrees-=1;
					}
									
								}
				else if (direction.equals("West")) {
					if(temptree.contains(cellwest)) {
						
					}
					else {
						cur=new Wallboard(x,y,CardinalDirection.West);
						floorplan.deleteWallboard(cur);	
						numtrees-=1;
					}
					
				}
				else {
					if(temptree.contains(celleast)) {
						
					}
					else {
						cur=new Wallboard(x,y,CardinalDirection.East);
						floorplan.deleteWallboard(cur);	
						numtrees-=1;
					}	
				}
				
				
				if (direction.equals("North")) {
					combineforests(x,y,CardinalDirection.North);
				}
				else if (direction.equals("South")) {
					combineforests(x,y,CardinalDirection.South);
									
								}
				else if (direction.equals("West")) {
					combineforests(x,y,CardinalDirection.West);;
					
				}
				else {
					combineforests(x,y,CardinalDirection.East);
				
				}
				
					
				
				
				
				
				
			}
			candidates.clear();
			setnewcandidatesfromforests(Forest);
			
			
			}}
		

	//DONT LET A WALL BE DESTORYED INSIDE ITS OWN TREE
	private void setnewcandidatesfromforests(ArrayList<ArrayList<int[]>> forest) {
		
		for(ArrayList<int[]>tree:forest) {
			ArrayList<String>possiblecandidates=new ArrayList<String>();
			Map<String,Integer>temporary=new HashMap<String,Integer>();
			
			for(int[]arr:tree) {
				int x=arr[0];
				int y=arr[1];
				String x1=Integer.toString(x);
				String y1=Integer.toString(y);
				String eastname=x1+"+"+y1+"+"+"East";
				String westname=x1+"+"+y1+"+"+"West";
				String northname=x1+"+"+y1+"+"+"North";
				String southname=x1+"+"+y1+"+"+"South";
				String[] arr1= {eastname,westname,northname,southname};
				for(String str:arr1) {
				if(wallboardnames.contains(str)) {
					possiblecandidates.add(str);
					
				}
				}
			
			}
			for(String str:possiblecandidates) {
				String [] wallcomp=str.split("\\+");
				int x=Integer.parseInt(wallcomp[0]);
				int y=Integer.parseInt(wallcomp[1]);
				String dir=wallcomp[2];
				
			
				
				if (dir.equals("North")) {
					temporary.put(str,getEdgeWeight(x,y,CardinalDirection.North));
				}
				else if (dir.equals("South")) {
					temporary.put(str,getEdgeWeight(x,y,CardinalDirection.South));
									
								}
				else if (dir.equals("West")) {
					temporary.put(str,getEdgeWeight(x,y,CardinalDirection.West));
					
				}
				else if (dir.equals("East")) {
					temporary.put(str,getEdgeWeight(x,y,CardinalDirection.East));
				}
				
			}
			int min=2000000000;
			for(String key:temporary.keySet()) {
				if(temporary.get(key)<min) {
					min=temporary.get(key);
				}
			}
			for(String key:temporary.keySet()) {
				if(temporary.get(key)==min) {
					candidates.put(key,min);				
				}
			}
			
			}
		ArrayList<String>candtodelete=new ArrayList<String>();
		for(String key:candidates.keySet()) {
			
			String [] wallcomp=key.split("\\+");
			int x=Integer.parseInt(wallcomp[0]);
			int y=Integer.parseInt(wallcomp[1]);
			String direction=wallcomp[2];
	
			int tempprevx=x-1;
			int tempprevy=y-1;
			
			if(direction.equals("West")&&(candidates.containsKey(tempprevx+"+"+wallcomp[1]+"+"+"East"))) {
				wallboardnames.remove(key);
				wallboardweights.remove(key);
				candtodelete.add(key);
				
				
				}
			else if((direction.equals("North")&&(candidates.containsKey(wallcomp[0]+"+"+tempprevy+"+"+"South")))) {
				wallboardnames.remove(key);
				wallboardweights.remove(key);
				candtodelete.add(key);
				
				}
			if(direction.equals("West")) {
				String tempx=Integer.toString(tempprevx);
				String tempy=Integer.toString(y);
				String name=tempx+"+"+tempy+"+"+"East";
				if (wallboardnames.contains(name)) {
					wallboardnames.remove(name);
					wallboardweights.remove(name);
				}
			}
			else if(direction.equals("East")) {
				String tempx=Integer.toString(x+1);
				String tempy=Integer.toString(y);
				String name=tempx+"+"+tempy+"+"+"West";
				if (wallboardnames.contains(name)) {
					wallboardnames.remove(name);
					wallboardweights.remove(name);
				}
			}
			else if(direction.equals("North")) {
				String tempx=Integer.toString(x);
				String tempy=Integer.toString(y-1);
				String name=tempx+"+"+tempy+"+"+"South";
				if (wallboardnames.contains(name)) {
					wallboardnames.remove(name);
					wallboardweights.remove(name);
				}
			}
			else if(direction.equals("South")) {
				String tempx=Integer.toString(x);
				String tempy=Integer.toString(y+1);
				String name=tempx+"+"+tempy+"+"+"North";
				if (wallboardnames.contains(name)) {
					wallboardnames.remove(name);
					wallboardweights.remove(name);
				}
			}
		
			
		}
		for(String value:candtodelete) {
			candidates.remove(value);
		}
	}
	
	private void combineforests(int x,int y,CardinalDirection dir) {
		int forestsize=Forest.size();
		ArrayList<ArrayList<int[]>>temp=new ArrayList<ArrayList<int[]>>();
		for(ArrayList<int[]>trees:new ArrayList<>(Forest)) {	
			for(int[]arr:trees) {
				if (arr[0]==x&&arr[1]==y) {
					if (dir==CardinalDirection.North) {
						temp.add(trees);
						temp.add(getsarrlist(x,y-1));
						
						Forest.remove(trees);
						
						Forest.remove(getsarrlist(x,y-1));
						
						
						
					}
					else if (dir==CardinalDirection.South) {
						temp.add(trees);
						temp.add(getsarrlist(x,y+1));
						Forest.remove(trees);
						Forest.remove(getsarrlist(x,y+1));
						
					}
					else if (dir==CardinalDirection.West) {
						temp.add(trees);
						temp.add(getsarrlist(x-1,y));
						Forest.remove(trees);
						
						Forest.remove(getsarrlist(x-1,y));
						
						
					}
					else {
						temp.add(trees);
						temp.add(getsarrlist(x+1,y));
						Forest.remove(trees);
					
						Forest.remove(getsarrlist(x+1,y));
						
						
					}
				}
				
			}}
		ArrayList<int[]>arr1=temp.get(0);
		ArrayList<int[]>arr2=temp.get(1);
		arr1.addAll(arr2);
		Forest.add(arr1);
		
		
	}
	private ArrayList<int[]> getsarrlist(int x, int y){
		for(ArrayList<int[]>trees:new ArrayList<>(Forest)) {	
			for(int[]arr:trees) {
				if(arr[0]==x&&arr[1]==y) {
					return trees;
				}
			}}
		return null;
		
	}
	
	

		
	private void setrandomweights() {
		for(String str:wallboardnames) {
			String [] wallcomp=str.split("\\+");
			int x=Integer.parseInt(wallcomp[0]);
			int y=Integer.parseInt(wallcomp[1]);
			String dir=wallcomp[2];
			if (dir.equals("North")) {
				assignrandomweight(x,y,CardinalDirection.North);
			}
			else if (dir.equals("South")){
				assignrandomweight(x,y,CardinalDirection.South);
			}
			else if (dir.equals("West")) {
				assignrandomweight(x,y,CardinalDirection.West);
			}
			else{
				assignrandomweight(x,y,CardinalDirection.East);
			}
		}
	}
		
		

	
	//creates trees for the MST which are contained in an arraylist
	private void createforests(int x, int y){
		ArrayList<int[]> tree=new ArrayList<int[]>();
		int[]cell= {x,y};
			
		tree.add(cell);
		Forest.add(tree);
		
	
		
		
	}

	
	/* FOR GETEDGEWEIGHT
	 * grab the weight from the dictionary using the x,y and direction to get the key
	 * return the weight
	 */
	public int getEdgeWeight(int x, int y, CardinalDirection cd) 
	{
		String x1=Integer.toString(x);
		String y1=Integer.toString(y);
		String direction;
		if(cd==CardinalDirection.North) {
			direction="North";
		}
		else if(cd==CardinalDirection.South) {
			direction="South";
		}
		else if(cd==CardinalDirection.East) {
			direction="East";
		}
		else {
			direction="West";
		}
		String wallname=x1+"+"+y1+"+"+direction;;
		
		
		return wallboardweights.get(wallname);
	}
	
	/* FOR ADDWALLSTOLISTOFWALLBOARDS
	 * iterate through the maze using nested for loops 
	 * Add the walls that can be removed to an arraylist
	 * You can tell if a wall can be removed using the canteardown method from floorplan
	 * WORKS AS INTENDED
	 */
	private void AddWallnamestoListofWallBoardnames() {
		for(int x=0;x<width;x++) {
			for (int y=0;y<height;y++) {
				
				Wallboard = new Wallboard(x, y, CardinalDirection.East) ;
				
				for (CardinalDirection cd : CardinalDirection.values()) {
					Wallboard.setLocationDirection(x, y, cd);
					if (floorplan.canTearDown(Wallboard)) // 
					{
						String x1=Integer.toString(x);
						String y1=Integer.toString(y);
						String direction;
						if(cd==CardinalDirection.North) {
							direction="North";
						}
						else if(cd==CardinalDirection.South) {
							direction="South";
						}
						else if(cd==CardinalDirection.East) {
							direction="East";
						}
						else {
							direction="West";
						}
						String wallname=x1+"+"+y1+"+"+direction;
						wallboardnames.add(wallname);
						
					}
				}
				Wallboard=null;
				}}
		
	}
	// exclusively used in updateListOfWallboards
	Wallboard Wallboard; // reuse a wallboard in updateListOfWallboards to avoid repeated object instantiation

	/* FOR ASSIGNRANDOMWEIGHT
	 * Choose a weight randomly using a random number generator 
	 * Assign it to a wall then add that wall as a key and that weight as the value
	 * A wall can be referenced by two cells
	 * so using conditionals, if a wall appears twice, make sure that both keys are deleted
	 * WORKS AS INTENDED
	 */
	private void assignrandomweight(int x, int y, CardinalDirection cd) {
		String x1=Integer.toString(x);
		String y1=Integer.toString(y);
		String direction;
		if(cd==CardinalDirection.North) {
			direction="North";
		}
		else if(cd==CardinalDirection.South) {
			direction="South";
		}
		else if(cd==CardinalDirection.East) {
			direction="East";
		}
		else {
			direction="West";
		}
		Random rand = new Random();
		String wallname=x1+"+"+y1+"+"+direction;
		
		String x3=Integer.toString(x-1);
		String y3=Integer.toString(y-1);
		if (direction.equals("North")) {
			if(wallboardweights.containsKey(x1+"+"+y3 +"+"+"South")) {
				String wallname2=x1+"+"+y3 +"+"+"South";
				wallboardweights.put(wallname, wallboardweights.get(wallname2));
				}
			else {
				int randomweight = rand.nextInt(100*width*height);
				wallboardweights.put(wallname, randomweight);
			}
			}
			
				
		else if (direction.equals("South")) {
			int randomweight = rand.nextInt(100*width*height);
			wallboardweights.put(wallname, randomweight);
		}
			
		
		else if (direction.equals("East")) {
			int randomweight = rand.nextInt(100*width*height);
			wallboardweights.put(wallname, randomweight);
		}
		
		else  {
			if(wallboardweights.containsKey(x3+"+"+y1 +"+"+"East")) {
				String wallname2=x3+"+"+y1 +"+"+"East";
				wallboardweights.put(wallname, wallboardweights.get(wallname2));
				}
			else {
				int randomweight = rand.nextInt(100*width*height);
				wallboardweights.put(wallname, randomweight);
			}
		}
		
		
	}
	//Taken from https://beginnersbook.com/2014/07/java-finding-minimum-and-maximum-values-in-an-array/
	 private static int getMin(int[] inputArray){ 
		    int minValue = inputArray[0]; 
		    for(int i=1;i<inputArray.length;i++){ 
		      if(inputArray[i] < minValue){ 
		        minValue = inputArray[i]; 
		      } 
		    } 
		    return minValue; 
		  } 

}
	/* NOTES FROM PRIM IMPLEMENTATION
	 * GENERATE PATHWAYS IN PRIM: Method generates pathways in the maze,
	 * mark cells as visited if they are part of the spanning tree,
	 * When a new cell is added we add all of its wallboards that can be torn down.
	 * A wall can be torn down the adjacent cell is not marked the wall between (x,y)
	 * and that neighbor is not a border wall 
	 * 
	 * INITMST: Creates the MST, creates an initial list of wallboards,
	 * chooses a random starting cell, if a cell is in a room move,
	 * Adds cell to MST using ADDCELLTOMST, returns array_list
	 * 
	 * ADDCELLTOMST: Marks the given cell as visited,
	 * add its wallboards that lead to cells outside of the MST to the list of candidates
	 * (unless they are borderwalls) using updateListOfWallboards.
	 * 
	 * UPDATELISTOFWALLBOARDS: Adds wallboards to the list of wallboards that could be removed to get to new cells.
	 * For the given cell it checks all 4 directions and for directions where a wall can be removed,
	 * it add them to the list
	 * 
	 * EXTRACTWALLBOARDFROMCANDIDATESET: Pick a random position in the list of candidates,
	 * remove the candidate from the list and return it
	 */
	
	
	/* WHAT I NEED TO DO FOR BORUVKA IMPLEMENTATION:
	 * Wallboards need to have randomly assigned weights, I think the best way to do this is a dictionary
	 * The nice part about this  is that if two cells point to the same wall those keys can have the same value
	 * 
	 * For Boruvka All of the cells start out as individual trees and build forest, if the wall in between two
	 * cells is the lowest weighted around that cell you destroy it, assuming you can
	 *
	 * 
	 * 
	 */


 