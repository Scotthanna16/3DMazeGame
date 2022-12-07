package edu.wm.cs.cs301.ScottHanna.gui;


import edu.wm.cs.cs301.ScottHanna.generation.*;
import edu.wm.cs.cs301.ScottHanna.gui.Robot.Direction;
import edu.wm.cs.cs301.ScottHanna.gui.Robot.Turn;

/**
 * Collaborators: Control (from which it also gets information about the maze),
 * Reliable robot. 
 * 
 * Responsibilities: keeps track of total energy consumptions so when the game finishes 
 * it can be displayed, keeps track of number of cells traversed to be displayed at the end,
 * calculates the fastest way to get to exit, and relays that info to robot so it can move.
 * 
 * 
 * @author Scott Hanna
 */

public class Wizard implements RobotDriver{
	
	private Robot robot;
	private Maze maze;
	private Floorplan floorplan;
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
	/**
	 * By accessing the floorplan through maze, it calculates and follows the fastest way to the exit
	 * will continue to do so assuming the robot has energy and the exit exists
	 * @return true if driver successfully reaches the exit, false otherwise
	 * @throws Exception thrown if robot stopped due to some problem, e.g. lack of energy
	 */
	@Override
	public boolean drive2Exit() throws Exception {
		
		//while driveexit equals true, iterate
		while(driveexit==true) {
			
			//gets current position of robot
			int[]position=robot.getCurrentPosition();
			//gets what the new position should be
			int[]positionprime=maze.getNeighborCloserToExit(position[0], position[1]);
			//gets current direction robot faces
			CardinalDirection curdir=robot.getCurrentDirection();
			//if the current direction is north
			if(curdir==CardinalDirection.North) {
				//already facing correct direction, can move
				if(positionprime[1]==position[1]-1) {
					//moves robot
					drive1Step2Exit();
				}
				//facing opposite of desired direction
				else if(positionprime[1]==position[1]+1) {
					//turn around
					robot.rotate(Turn.AROUND);
					//moves robot
					drive1Step2Exit();
				}//robot facing left of desired direction
				else if(positionprime[0]==position[0]-1) {
					//turn right
					robot.rotate(Turn.RIGHT);
					//moves robot
					drive1Step2Exit();
				}
				//robot facing right of desired direction
				else if(positionprime[0]==position[0]+1) {
					//turn left
					robot.rotate(Turn.LEFT);
					//moves robot
					drive1Step2Exit();
				}
				
			}
			//if the current direction is south
			else if(curdir==CardinalDirection.South) {
				//already facing correct direction, can move
				if(positionprime[1]==position[1]+1) {
					//moves robot
					drive1Step2Exit();
				}
				//facing opposite of desired direction
				else if(positionprime[1]==position[1]-1) {
					//turn around
					robot.rotate(Turn.AROUND);
					//moves robot
					drive1Step2Exit();
				}//robot facing left of desired direction
				else if(positionprime[0]==position[0]+1) {
					//turn right
					robot.rotate(Turn.RIGHT);
					//moves robot
					drive1Step2Exit();
				}
				//robot facing right of desired  direction
				else if(positionprime[0]==position[0]-1) {
					//turn left
					robot.rotate(Turn.LEFT);
					//moves robot
					drive1Step2Exit();
				}
				
			}
			//if the current direction is west
			else if(curdir==CardinalDirection.West) {
				//already facing correct direction, can move
				if(positionprime[0]==position[0]-1) {
					//moves robot
					drive1Step2Exit();
				}
				//facing opposite of desired  direction
				else if(positionprime[0]==position[0]+1) {
					//turn around
					robot.rotate(Turn.AROUND);
					//moves robot
					drive1Step2Exit();
				}
				//robot facing right of desired direction
				else if(positionprime[1]==position[1]-1) {
					//turn left
					robot.rotate(Turn.LEFT);
					//moves robot
					drive1Step2Exit();
				}//robot facing left of desired direction
				else if(positionprime[1]==position[1]+1) {
					//turn right
					robot.rotate(Turn.RIGHT);
					//moves robot
					drive1Step2Exit();
				}
				
			}
			//if the current direction is east
			else {
				//already facing correct direction, can move
				if(positionprime[0]==position[0]+1) {
					//moves robot
					drive1Step2Exit();
				}
				//facing opposite of desired  direction
				else if(positionprime[0]==position[0]-1) {
					//turn around
					robot.rotate(Turn.AROUND);
					//moves robot
					drive1Step2Exit();
				}
				//robot facing right of desired direction
				else if(positionprime[1]==position[1]+1) {
					//turn left
					robot.rotate(Turn.LEFT);
					//moves robot
					drive1Step2Exit();
				}//robot facing left of desired direction
				else if(positionprime[1]==position[1]-1) {
					//turn right
					robot.rotate(Turn.RIGHT);
					//moves robot
					drive1Step2Exit();
				}
				
			}
			
		
			
			
		}
		if(robot.getBatteryLevel()>=6) {
			robot.move(1);
			return true;
		}
		return false;
	}
	
	/**
	 * Uses the robot object to follow the fastest way determined by drive2exit
	 * At the exit position, it rotates the robot 
	 * such that if faces the exit in its forward direction
	 * and returns false. 
	 * 
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
