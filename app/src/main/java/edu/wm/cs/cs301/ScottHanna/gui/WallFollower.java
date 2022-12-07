package edu.wm.cs.cs301.ScottHanna.gui;

import edu.wm.cs.cs301.ScottHanna.generation.CardinalDirection;
import edu.wm.cs.cs301.ScottHanna.generation.Floorplan;
import edu.wm.cs.cs301.ScottHanna.generation.Maze;
import edu.wm.cs.cs301.ScottHanna.gui.Robot.Direction;
import edu.wm.cs.cs301.ScottHanna.gui.Robot.Turn;

/**
 * Collaborators: Control (from which it also gets information about the maze),
 * Reliable robot, unreliable robot.
 * 
 * Responsibilities: keeps track of total energy consumptions so when the game finishes 
 * it can be displayed, keeps track of number of cells traversed to be displayed at the end,
 * Follows the left hand walls in order to exit the maze. 
 * 
 * @author Scott Hanna
 */

public class WallFollower implements RobotDriver{
	
	Robot robot;
	Maze maze;
	Floorplan floorplan;
	private boolean driveexit=true;

	/**
	 * Assigns a robot platform to the driver. 
	 * The driver uses a robot to perform, this method provides it with this necessary information.
	 * This is done when robot and robot driver are created in Stateplaying
	 * @param r robot to operate
	 */

	@Override
	public void setRobot(Robot r) {
		robot=r;
		
	}
	/**
	 * returns robot for testing
	 */
	public Robot getrobot(){
		return robot;
	}
	
	/**
	 * returns maze for testing
	 */
	public Maze getmaze() {
		return maze;
	}
	/**
	 * set the maze by storing it in the private maze field
	 * also sets floorplan
	 * @param maze the maze for this game
	 * @throws IllegalArgumentException if parameter is null
	 */
	@Override
	public void setMaze(Maze maze) {
		if(maze==null) {
			throw new IllegalArgumentException("Maze is null");
		}
		this.maze=maze;
		floorplan=maze.getFloorplan();
		
	}
	
	
	
	//////////////if left is unreliable, make it first, the the third sensor must be right
	//////////////if Forward is unreliable, make it second, the the third sensor must be back
	
	
	
	/**
	 * By using the sensors the robot follows the left wall
	 * If there is no wall on the left hand side, rotate left and move forward in this direction
	 * If there is a wall in front rotate right
	 * If a sensor fails, rotate to a working one
	 * check if the sensor is sleeping using thread.getstate 
	 * calls drive1step2exit to move
	 * @return true if driver successfully reaches the exit, false otherwise
	 * @throws Exception thrown if robot stopped due to some problem, e.g. lack of energy
	 */
	public boolean drive2Exit() throws Exception {
		//If the robot starts in a room, move left until it is next to a wall
		if(robot.isInsideRoom()) {
			try {
			//if this condition hits the robot already has a wall left, no adjustment needed
			if(robot.distanceToObstacle(Direction.LEFT)==0) {
				
			}
			else {
				try {
				//While the robot doesn't have a wall on its left, move in the left direction(Left of original Direction)
				while(robot.distanceToObstacle(Direction.LEFT)!=0) {
					robot.rotate(Turn.LEFT);
					robot.move(1);
					robot.rotate(Turn.RIGHT);
				}}
				//If the left sensor fails when in the while loop, turn around and use the right sensor
				catch(Exception e1){
					robot.rotate(Turn.AROUND);
					//While the robot doesn't have a wall on its Right, move in the right direction(Left of original Direction)
					while(robot.distanceToObstacle(Direction.RIGHT)!=0) {
						robot.rotate(Turn.RIGHT);
						robot.move(1);
						robot.rotate(Turn.LEFT);
					}
				}				
			}
			//Original condition failed bc left sensor isn't operational, 
			//do the same things but turn around and use right sensor
			} catch(Exception e) {
				robot.rotate(Turn.AROUND);
				//if this condition hits the robot already has a wall right, no adjustment needed
				if(robot.distanceToObstacle(Direction.RIGHT)==0) {
					
				}
				else {
					try {
					//While the robot doesn't have a wall on its right, move in the right direction(Left of original Direction)
					while(robot.distanceToObstacle(Direction.RIGHT)!=0) {
						robot.rotate(Turn.RIGHT);
						robot.move(1);
						robot.rotate(Turn.LEFT);
					}}
					//If the right sensor fails when in the while loop, turn around and use the right sensor
					catch(Exception e1){
						robot.rotate(Turn.AROUND);
						//While the robot doesn't have a wall on its Left, move in the left direction(Left of original Direction)
						while(robot.distanceToObstacle(Direction.LEFT)!=0) {
							robot.rotate(Turn.LEFT);
							robot.move(1);
							robot.rotate(Turn.RIGHT);
						}
					}				
				}
				
			}
			
		}
		//Account for if exit position si not in a dead end, use canseethroughexit
		
		//Move while not at exit
		while(driveexit==true) {
			CardinalDirection cd=robot.getCurrentDirection();
			
			//try to sense, catch exception if there is an exception turn around to right
			try {
				//checks to see if there is a wall to the left
				if(robot.distanceToObstacle(Direction.LEFT)==0) {
					try {
						if(robot.distanceToObstacle(Direction.FORWARD)==0) {
							//turn right, wait until next iteration to move
							robot.rotate(Turn.RIGHT);
						}
						else {
							//no wall in front, move forward
							drive1Step2Exit();
						}
					}
					catch(Exception e1){
						//Forward sensor not working, so back must be
						robot.rotate(Turn.AROUND);
						if(robot.distanceToObstacle(Direction.BACKWARD)==0) {
							//wall in front of original direction, turn back around and turn right
							robot.rotate(Turn.AROUND);
							robot.rotate(Turn.RIGHT);
						}
						else {
							//no wall in front, move forward
							robot.rotate(Turn.AROUND);
							drive1Step2Exit();
						} 
						
					} 
					}
				else {
					robot.rotate(Turn.LEFT);
					drive1Step2Exit();
				}
			
				}
			//left sensor not working, right sensor must be
			catch(Exception e) {
				//turn around to use right sensor
				robot.rotate(Turn.AROUND);
				//checks to see if there is a wall to the right
				try {
				if(robot.distanceToObstacle(Direction.RIGHT)==0) {
					try {
						if(robot.distanceToObstacle(Direction.BACKWARD)==0) {
							//turn left, wait until next iteration to move
							robot.rotate(Turn.LEFT);
						}
						else {
							//Turn around and move in original direction 
							robot.rotate(Turn.AROUND);
							drive1Step2Exit();
						}
					}
					catch(Exception e1){
						//backward sensor not working, so forward must be
						robot.rotate(Turn.AROUND);
						if(robot.distanceToObstacle(Direction.FORWARD)==0) {
							//wall in front of original direction, turn back around and turn right
							
							robot.rotate(Turn.RIGHT);
						}
						else {
							//no wall in front, move forward
							
							drive1Step2Exit();
						}
						
					}
					}
				
				
			}catch(Exception e3) {
				//Turn around to original direction
				if(robot.getCurrentDirection().equals(cd)) {}
				else {
				robot.rotate(Turn.AROUND);}
				//This is the Plan B implementation
				Thread.sleep(3000);
			}
				}
		
			
		}
		while(robot.isAtExit()) {
		//make sure robot is facing exit,
		try {
			//If robot can see through exit facing forward
			if(robot.canSeeThroughTheExitIntoEternity(Direction.FORWARD)) {
				if(robot.getBatteryLevel()>=6) {
					robot.move(1);
					return true;
				}
			}}
		//if forward sensor fails, turn around 
		catch(Exception e) {
			robot.rotate(Turn.AROUND);
			try {
			if(robot.canSeeThroughTheExitIntoEternity(Direction.BACKWARD)) {
				if(robot.getBatteryLevel()>=12) {
					robot.rotate(Turn.AROUND);
					robot.move(1);
					return true;
				}
			}}
			catch(Exception e1) {
				robot.rotate(Turn.AROUND);
			}
			}
		
		//RIGHT OF ORIGINAL DIRECTION
		//Turn right and check that direction
		robot.rotate(Turn.RIGHT);
		try {
			//If robot can see through exit facing forward
			if(robot.canSeeThroughTheExitIntoEternity(Direction.FORWARD)) {
				if(robot.getBatteryLevel()>=6) {
					robot.move(1);
					return true;
				}
			}}
		//if forward sensor fails, turn around 
		catch(Exception e) {
			robot.rotate(Turn.AROUND);
			try {
			if(robot.canSeeThroughTheExitIntoEternity(Direction.BACKWARD)) {
				if(robot.getBatteryLevel()>=12) {
					robot.rotate(Turn.AROUND);
					robot.move(1);
					return true;
				}
			}}
			catch(Exception e1) {
				robot.rotate(Turn.AROUND);
			}
			}
		//BACK OF ORIGINAL DIRECTION
		//Turn right and check that direction
		robot.rotate(Turn.RIGHT);
		try {
			//If robot can see through exit facing forward
			if(robot.canSeeThroughTheExitIntoEternity(Direction.FORWARD)) {
				if(robot.getBatteryLevel()>=6) {
					robot.move(1);
					return true;
				}
			}}
		//if forward sensor fails, turn around 
		catch(Exception e) {
			robot.rotate(Turn.AROUND);
			try {
			if(robot.canSeeThroughTheExitIntoEternity(Direction.BACKWARD)) {
				if(robot.getBatteryLevel()>=12) {
					robot.rotate(Turn.AROUND);
					robot.move(1);
					return true;
				}
			}}
			catch(Exception e1) {
				robot.rotate(Turn.AROUND);
			}}
		//LEFT OF ORIGINAL DIRECTION
		//Turn right and check that direction
		robot.rotate(Turn.RIGHT);
		try {
			//If robot can see through exit facing forward
			if(robot.canSeeThroughTheExitIntoEternity(Direction.FORWARD)) {
				if(robot.getBatteryLevel()>=6) {
					robot.move(1);
					return true;
				}
			}}
		//if forward sensor fails, turn around 
		catch(Exception e) {
			robot.rotate(Turn.AROUND);
			try {
			if(robot.canSeeThroughTheExitIntoEternity(Direction.BACKWARD)) {
				if(robot.getBatteryLevel()>=12) {
					robot.rotate(Turn.AROUND);
					robot.move(1);
					return true;
				}
			}}
			catch(Exception e1) {
				robot.rotate(Turn.AROUND);
			}}}

		return false;
	}
	/**
	 * If there is a wall in front rotate right
	 * check if the sensor is sleeping using thread.getstate
	 * If a sensor fails, rotate to a working one 
	 * Use robot.isatexit to tells if the robot is at the exit position
	 * moves using robot.move
	 * @return true if it moved the robot to an adjacent cell, false otherwise
	 * @throws Exception thrown if robot stopped due to some problem, e.g. lack of energy
	 */
	@Override
	public boolean drive1Step2Exit() throws Exception {
		//Moves robot 
			robot.move(1);
			if(robot.hasStopped()==true) {
				throw new Exception("Robot has stopped");
			}
			int []curpo=robot.getCurrentPosition();
			CardinalDirection curdir=robot.getCurrentDirection();
			//Checks if robot is at exit
			if(floorplan.isExitPosition(curpo[0], curpo[1])) {
				//If the current direction is north, checks if the robot is already facing the correct direction, if not rotate it
				if(curdir==CardinalDirection.North) {
					
					if(floorplan.hasNoWall(curpo[0], curpo[1], CardinalDirection.North)) {
						driveexit=false;
						return false;
					}
					else if((floorplan.hasNoWall(curpo[0], curpo[1], CardinalDirection.West))) {
						driveexit=false;
						robot.rotate(Turn.RIGHT);
						return false;
					}
					else if((floorplan.hasNoWall(curpo[0], curpo[1], CardinalDirection.East))) {
						driveexit=false;
						robot.rotate(Turn.LEFT);
						return false;
					}
				}
				//If the current direction is south, checks if the robot is already facing the correct direction, if not rotate it
				else if(curdir==CardinalDirection.South) {
					if(floorplan.hasNoWall(curpo[0], curpo[1], CardinalDirection.South)) {
						driveexit=false;
						return false;
					}
					else if(floorplan.hasNoWall(curpo[0], curpo[1], CardinalDirection.West)) {
						driveexit=false;
						robot.rotate(Turn.LEFT);
						return false;
					}
					else if(floorplan.hasNoWall(curpo[0], curpo[1], CardinalDirection.East)) {
						driveexit=false;
						robot.rotate(Turn.RIGHT);
						return false;
					}
				}
				//If the current direction is west, checks if the robot is already facing the correct direction, if not rotate it
				else if(curdir==CardinalDirection.West) {
					if(floorplan.hasNoWall(curpo[0], curpo[1], CardinalDirection.West)) {
						driveexit=false;
						return false;
					}
					else if(floorplan.hasNoWall(curpo[0], curpo[1], CardinalDirection.South)) {
						driveexit=false;
						robot.rotate(Turn.RIGHT);
						return false;
					}
					else if(floorplan.hasNoWall(curpo[0], curpo[1], CardinalDirection.North)) {
						driveexit=false;
						robot.rotate(Turn.LEFT);
						return false;
					}
				}
				//If the current direction is east, checks if the robot is already facing the correct direction, if not rotate it
				else {
					if(floorplan.hasNoWall(curpo[0], curpo[1], CardinalDirection.East)) {
						driveexit=false;
						return false;
					}
					else if(floorplan.hasNoWall(curpo[0], curpo[1], CardinalDirection.North)) {
						driveexit=false;
						robot.rotate(Turn.RIGHT);
						return false;
					}
					else if(floorplan.hasNoWall(curpo[0], curpo[1], CardinalDirection.West)) {
						driveexit=false;
						robot.rotate(Turn.LEFT);
						return false;
					}
				}
			}
			return true;
		}
	
	
	
	
	
	
	
	
	
	

	/**
	 * subtracts the robots energy level at the end of the journey from 3500
	 * which gives the energy used, accesses the robots energy level by calling robot.getbatterylevel
	 * @return 3500 - robot.getbatterylevel
	 */
	@Override
	public float getEnergyConsumption() {
		// TODO Auto-generated method stub
		return 3500-robot.getBatteryLevel();
	}
	/**
	 * accesses the robots odometer reading by calling robot.getodometerreading
	 * @return robot.getodometerreading
	 */

	@Override
	public int getPathLength() {
		// TODO Auto-generated method stub
		return robot.getOdometerReading();
	}
}
