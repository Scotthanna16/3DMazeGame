package edu.wm.cs.cs301.ScottHanna.gui;

import edu.wm.cs.cs301.ScottHanna.generation.CardinalDirection;
import edu.wm.cs.cs301.ScottHanna.generation.Floorplan;
import edu.wm.cs.cs301.ScottHanna.generation.Maze;
import edu.wm.cs.cs301.ScottHanna.gui.Robot.Direction;

/**
 * Collaborators: Control (from which it also gets information about the maze),
 * Robot.
 * 
 * Responsibilities: gets distance to wall at given direction, affects energy of 
 * Robot for sensing.
 * 
 * @author Scott Hanna
 */

public class ReliableSensor implements DistanceSensor {
	
	private Maze maze;
	private Floorplan floorplan;
	private float sensingcost=1;
	private Direction d;
	private int height;
	private int width;
	/**
	 * calculates distance to wall by using the floorplan accessed by maze. 
	 * @param currentPosition is the current location as (x,y) coordinates
	 * @param currentDirection specifies the direction of the robot
	 * @param powersupply is an array of length 1, whose content is modified 
	 * to account for the power consumption for sensing
	 * @return number of steps towards obstacle if obstacle is visible 
	 * in a straight line of sight, Integer.MAX_VALUE otherwise.
	 * @throws Exception with message 
	 * SensorFailure if the sensor is currently not operational
	 * PowerFailure if the power supply is insufficient for the operation
	 * @throws IllegalArgumentException if any parameter is null
	 * or if currentPosition is outside of legal range
	 * ({@code currentPosition[0] < 0 || currentPosition[0] >= width})
	 * ({@code currentPosition[1] < 0 || currentPosition[1] >= height}) 
	 * @throws IndexOutOfBoundsException if the powersupply is out of range
	 * ({@code powersupply < 0}) 
	 */
	@Override
	public int distanceToObstacle(int[] currentPosition, CardinalDirection currentDirection, float[] powersupply)
			throws Exception {
		//Checks if there is an issue with the parameters, if so throw exception
		if(currentPosition==null) {
			throw new IllegalArgumentException("Current Position is null");
		}
		if(currentDirection==null) {
			throw new IllegalArgumentException("Current Direction is null");
		}
		if(powersupply==null) {
			throw new IllegalArgumentException("PowerSupply is null");
		}
		if(powersupply[0]<0) {
			throw new IndexOutOfBoundsException("PowerSupply less than 0");
		}
		if(powersupply[0]==0) {
			throw new Exception("Powersupply is 0");
		} 
		if (powersupply!=null&&currentDirection!=null&&currentPosition!=null) {
			//gets the components of the position and initilizes step to 0
			int x=currentPosition[0];
			int y=currentPosition[1];
			//steps will be the distance to the objects
			int steps=0;
			//Checks if current direction is north
			if(currentDirection==CardinalDirection.North) {
				//used for loop iteration
				boolean hitwall=false;
				while(hitwall==false) {
					//checks if there is a wall at the current position in the current direction
					if(floorplan.hasWall(x, y, CardinalDirection.North)==true) {
						//if there is a wall, stop iterating and return distance to obstacle(steps)
						hitwall=true;
						return steps;
					}
					else {
						//if there is no wall, continue in current direction, increment steps
						y-=1;
						steps+=1;
					}
					if(hitwall==false&&y<0) {
						//In the north if the y<0 it is out of the maze so no wall was ever detected
						//meaning must be looking out exit
						return 10000;
					}
					
				}
				
			}
			//Checks if current direction is South
			else if(currentDirection==CardinalDirection.South) {
				//used for loop iteration
				boolean hitwall=false;
				while(hitwall==false) {
					//checks if there is a wall at the current position in the current direction
					if(floorplan.hasWall(x, y, CardinalDirection.South)==true) {
						//if there is a wall, stop iterating and return distance to obstacle(steps)
						hitwall=true;
						return steps;
					}
					else {
						//if there is no wall, continue in current direction, increment steps
						y+=1;
						steps+=1;
					}
					if(hitwall==false&&y>=height) {
						//In the south if the y>height it is out of the maze so no wall was ever detected
						//meaning must be looking out exit
						return 10000;
					}
					
				}
			}
			//Checks if current direction is west
			else if(currentDirection==CardinalDirection.West) {
				//used for loop iteration
				boolean hitwall=false;
				while(hitwall==false) {
					//checks if there is a wall at the current position in the current direction
					if(floorplan.hasWall(x, y, CardinalDirection.West)==true) {
						//if there is a wall, stop iterating and return distance to obstacle(steps)
						hitwall=true;
						return steps;
					}
					else {
						//if there is no wall, continue in current direction, increment steps
						x-=1;
						steps+=1;
					}
					if(hitwall==false&&x<0) {
						//In the west if the x<0 it is out of the maze so no wall was ever detected
						//meaning must be looking out exit
						return 10000;
					}
					
				}
				
			}
			//Current direction must be east
			else {
				//used for loop iteration
				boolean hitwall=false;
				//checks if there is a wall at the current position in the current direction
				while(hitwall==false) {
					if(floorplan.hasWall(x, y, CardinalDirection.East)==true) {
						//if there is a wall, stop iterating and return distance to obstacle(steps)
						hitwall=true;
						return steps;
					}
					else {
						//if there is no wall, continue in current direction, increment steps
						x+=1;
						steps+=1;
					}
					if(hitwall==false&&x>=width) {
						//In the east if the X>width it is out of the maze so no wall was ever detected
						//meaning must be looking out exit
						return 10000;
					}
					
				}
			}
		}
			
		return 0;
	
	}
	/**
	 * set the maze by storing it in the private maze field
	 * also set floorplan, height, and width
	 * @param maze the maze for this game
	 * @throws IllegalArgumentException if parameter is null
	 */
	@Override
	public void setMaze(Maze maze) {
		if(maze==null) {
			throw new IllegalArgumentException("Maze is Null");
		}
		else {
			this.maze=maze;
			floorplan=maze.getFloorplan();
			height=maze.getHeight();
			width=maze.getWidth();
		}
		
	}
	/**
	 * Gets the maze(only use for testing purposes)
	 */
	public Maze getMaze() {
		return maze;
		
	}
	/**
	 * sets sensor direction by accessing direction field directly 
	 */
	@Override
	public void setSensorDirection(Direction mountedDirection) {
		d=mountedDirection;
		
	}
	
	/**
	 * gets sensor direction (for testing purposes only)
	 */
	public Direction getSensorDirection() {
		return d;
		
	}
	/**
	 * gets the amount of energy consumed for using a sensor
	 * @return 1
	 */
	@Override
	public float getEnergyConsumptionForSensing() {
		// TODO Auto-generated method stub
		return sensingcost;
	}
	/**
	 * only for unreliable sensor
	 */

	@Override
	public void startFailureAndRepairProcess(int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Only for Unreliable Sensor");
		
	}
	/**
	 * only for unreliable sensor
	 */

	@Override
	public void stopFailureAndRepairProcess() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Only for Unreliable Sensor");
		
	}
	/**
	 * return true bc sensor is reliable
	 */
	@Override
	public boolean isreliable() {
		
		return true;
	}
	/**
	 * Changes runthread to stop sensor
	 * not used in reliable sensor
	 */
	@Override
	public void setcount(int count) {
		// TODO Auto-generated method stub
		
	}
	
	

}
