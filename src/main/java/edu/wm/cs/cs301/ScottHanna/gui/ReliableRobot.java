package edu.wm.cs.cs301.ScottHanna.gui;



import edu.wm.cs.cs301.ScottHanna.generation.CardinalDirection;
import edu.wm.cs.cs301.ScottHanna.generation.Floorplan;
import edu.wm.cs.cs301.ScottHanna.generation.Maze;
import edu.wm.cs.cs301.ScottHanna.gui.Constants.UserInput;

/**
 * Collaborators: Control (from which it also gets information about the maze),
 * ReliableSensor, Wizard, wall follower.
 * 
 * Responsibilities: turns (left, right, and around), adds sensors, 
 * gets distance to walls using sensors, moves (steps and jumps), stores energy
 * levels and directly affects them when moving, will stop when the robot runs out of energy.
 * 
 * 
 * @author Scott Hanna
 */


public class ReliableRobot implements Robot{
	/**
	 * Private class fields
	 */
	private PlayAnimationActivity controller;
	private Maze maze;
	private Floorplan floorplan;
	private float energylvl=3500;
	private float singlerotationenergy=3;
	private float stepenergy=6;
	private float jumpenergy=40;
	private int odometerreading=0;
	protected DistanceSensor Forward=null;
	protected DistanceSensor Backward=null;
	protected DistanceSensor Left=null;
	protected DistanceSensor Right=null;
	
	/**
	 * Sets the Controller the Robot uses to get info about the maze. 
	 * Also sets maze and floorplan to be used later
	 * 
	 * @param controller that is passed in when the Robot is first initiated in Control.
	 */
	@Override
	public void setController(PlayAnimationActivity controller) {
		
		this.controller=controller;
		maze=controller.getMaze();
		floorplan=maze.getFloorplan();
		//Check if the sensors are initiated, meaning wallfollower is used
		//If they are set the maze, if not don't
		if(Forward!=null) {
			Forward.setMaze(maze);
			}
		if(Backward!=null) {
			Backward.setMaze(maze);
		}
		if(Right!=null) {
			Right.setMaze(maze);
		}
		if(Left!=null) {
			Left.setMaze(maze);
		}
	}
	

	
	/**
	 * adds a sensor to the robot, that points either forward, back, left, right. 
	 * 
	 * @param mountedDirection either, forward, back, left, right.
	 * @param sensor that is either reliable or not. 
	 */
	@Override
	public void addDistanceSensor(DistanceSensor sensor, Direction mountedDirection) {
		//assigns sensor to correct direction

		if(mountedDirection==Direction.FORWARD) {
			Forward=sensor;
			Forward.setSensorDirection(mountedDirection);
		
		}
		else if(mountedDirection==Direction.BACKWARD) {
			Backward=sensor;
			Backward.setSensorDirection(mountedDirection);
		
		}
		else if(mountedDirection==Direction.RIGHT) {
			Right=sensor;
			Right.setSensorDirection(mountedDirection);
			
		}
		else {
			Left=sensor;
			Left.setSensorDirection(mountedDirection);
		
			}
		}
		//Unreliable sensor not implemented yet
	
		
		
	
	
	/**
	 * Returns sensors, used for checking if sensor is operational
	 */
	public DistanceSensor getsensor(Direction d) {
		if(d==Direction.FORWARD) {
			return Forward;
		}
		else if(d==Direction.FORWARD) {
			return Backward;
		}
		else if(d==Direction.FORWARD) {
			return Left;
		}
		else{
			return Right;
		}
	}
	/**
	 * Gets the current position of the robot using the control object.
	 *
	 * @return the current position of the robot in a list
	 * @throws Exception if position is outside of the maze
	 */
	@Override
	public int[] getCurrentPosition() throws Exception {
		int[]position=controller.getCurrentPosition();
		if(position[0]<0||position[1]<0||position[0]>maze.getWidth()+1||position[1]>maze.getHeight()+1) {
			throw new Exception("Position out of Maze");
		}
		else {
			return position;}
	}
	/**
	 * Gets the current direction of the robot using the control object.
	 * 
	 * @return current direction robot is facing
	 */
	@Override
	public CardinalDirection getCurrentDirection() {
		
		return controller.getCurrentDirection();
	}
	
	/**
	 * Gets the current battery of the robot by accessing the battery level field
	 * 
	 * @return the battery level 
	 */
	@Override
	public float getBatteryLevel() {
		
		return energylvl;
	}
	/**
	 * And then is used to adjust the battery level when an operation is performed by
	 * accessing the battery level field directly.  
	 * 
	 * @param level adjusted energy level after an operation is performed
	 */
	@Override
	public void setBatteryLevel(float level) {
		energylvl=level;
		
	}
	
	

	/**
	 * @return 12 e.g. 4 * the amount it takes to turn 90 degrees
	 * 
	 */
	@Override
	public float getEnergyForFullRotation() {
		
		return 4*singlerotationenergy;
	}
	/**
	 * @return 6 e.g. the amount it takes to walk one step
	 * 
	 */
	@Override
	public float getEnergyForStepForward() {
	
		return stepenergy;
	}

	/**
	 * @returns the odometer reading by accessing the odometer field directly. 
	 * 
	 */
	@Override
	public int getOdometerReading() {
		
		return odometerreading;
	}
	/**
	 * resets odometer to 0, by accessing the field directly.
	 * 
	 */
	@Override
	public void resetOdometer() {
		odometerreading=0;
		
	}
	public void setOdometer(int x) {
		odometerreading=x;
	}
	public PlayAnimationActivity getController(){
		return controller;
	}
	
	
	/**
	 * checks if robot has stopped using has stopped, if so dont do move
	 * Turns the robot the given direction by using the control method Keypressed.
	 * affects the energy level of the robot using get,set odometer level by 3
	 * 
	 * @param turn is the direction to turn, if left pass in 'h' to keypressed etc. 
	 */
	@Override
	
	public void rotate(Turn turn) {
		//Checks that we have enough energy for rotations 
		if(energylvl<3&&(turn==Turn.LEFT||turn==Turn.RIGHT)) {
			energylvl=0;
		}
		if(energylvl<6&&turn==Turn.AROUND) {
			energylvl=0;
		}
		//checks for no energy
		if(hasStopped()==true) {}
		else {
			DistanceSensor temp=new ReliableSensor();
			if (turn==Turn.RIGHT) { 
				//Calls handlekeyboardinput to turn
				controller.handleUserInput(UserInput.RIGHT, 0);
				
				//Sets the change for the battery level
				float change=getBatteryLevel()-singlerotationenergy;
				setBatteryLevel(change);
				//When rotates left, forward sensor becomes right, right becomes back, left becomes forward, back becomes left
				temp=Forward;
				Forward=Right;
				Right=Backward;
				Backward=Left;
				Left=temp;
				
				
			}
			else if (turn==Turn.LEFT) {
				//Calls handlekeyboardinput to turn
				controller.handleUserInput(UserInput.LEFT, 0);
				//Sets the change for the battery level
				float change=getBatteryLevel()-singlerotationenergy;
				setBatteryLevel(change);
				//When rotates right, forward sensor becomes left, right becomes forward, left becomes backward, back becomes right
				temp=Forward;
				Forward=Left;
				Left=Backward;
				Backward=Right;
				Right=temp;
			}
			else if (turn==Turn.AROUND) {
				//Calls handlekeyboardinput to turn
				controller.handleUserInput(UserInput.RIGHT, 0);
				controller.handleUserInput(UserInput.RIGHT, 0);
				//Sets the change for the battery level
				float change=getBatteryLevel()-singlerotationenergy*2;
				setBatteryLevel(change);
				//When rotates around, forward sensor becomes Backward, Back becoems forward, left becomes Right, right becomes left
				temp=Forward;
				Forward=Backward;
				Backward=temp;
				temp=Left;
				Left=Right;
				Right=temp;
				
				
			}
			
		}
		
		
		
	}
	
	/**
	 * checks if robot has stopped using has stopped , if so dont do move
	 * Moves forward, assuming no wall, by using control method keypressed with parameter k
	 * affects the energy level of the robot using get,set odometer level by 6*distance 
	 * 
	 * @param distance, number of time to call keypressed
	 */
	@Override
	public void move(int distance) {
		
		//Uses for loop to go correct distance
		for(int d=0;d<distance;d++) {
			//checks if there is enough energy to move
			if(energylvl<6) {
				energylvl=0;
			}
			//checks for crash
			int[]pos=controller.getCurrentPosition();
			if (getCurrentDirection()==CardinalDirection.South) {
				if( maze.hasWall(pos[0], pos[1], CardinalDirection.South)==true){
				energylvl=0;
			}
			}
			//checks for crash
			if (getCurrentDirection()==CardinalDirection.North) {
				if( maze.hasWall(pos[0], pos[1], CardinalDirection.North)==true){
				energylvl=0;
			}
			}
			//checks for no energy
			if(hasStopped()==true) {
				
			}
			else {
				//Calls handlekeyboardinput to move				
				controller.handleUserInput(UserInput.UP, 0);
				odometerreading+=1;
				float change=getBatteryLevel()-stepenergy;
				setBatteryLevel(change);}
			
		}
		
	}
	/**
	 * checks if robot has stopped using has stopped , if so dont do move
	 * Moves forward, no matter what, by using control method keypressed with parameter w
	 * affects the energy level of the robot using get,set odometer level by 40
	 * 
	 * 
	 */

	@Override
	
	public void jump() {
		//THIS GETS INVERTED!!!, i.e. it jumps back
		//Checks if there is enough energy for jump
		if(energylvl<40) {
			energylvl=0;
		}
		//checks if the robot has stopped
		if(hasStopped()==true) {
			
		}
		else {
			//Uses handlekeyboardinput to jump
			controller.handleUserInput(UserInput.JUMP, 0);
			odometerreading+=1;
			//Computes battery level after jump
			float change=getBatteryLevel()-jumpenergy;
			setBatteryLevel(change);
			
		}
		
		
	}
	/**
	 * checks if robot is at exit by accessing maze and through that floorplan.
	 * 
	 * @return true if robot is at exit, false otherwise
	 */
	@Override
	public boolean isAtExit() {
		try {
			
			int[]position=getCurrentPosition();
			int x= position[0];
			int y= position[1];
			//checks if the current position is the exit position
			if (floorplan.isExitPosition(x, y)==true) {
				return true;
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
		return false;
	}
	/**
	 * checks if robot in a room by accessing maze and throught that floorplan. 
	 * 
	 * @return true if robot is at exit, false otherwise
	 */
	@Override
	public boolean isInsideRoom() {
		try {
			int[]position=getCurrentPosition();
			int x= position[0];
			int y= position[1];
			//checks if the current position is in the room
			if (floorplan.isInRoom(x, y)==true) {
				return true;
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * checks if robot is trying to go through a wall and not jump, or if it has a lack of energy for the move
	 * 
	 * @return true if at wall and not jumping, or no energy, false otherwise
	 */
	@Override
	public boolean hasStopped() {
		//checks if robot has stopped
		if (getBatteryLevel()<=0) {

			communicatestop();
			return true;
		}
		return false;
	}
	/**
	 * gets the distance to a wall using a sensor, passing in the given direction 
	 * @param direction specifies the direction of interest
	 * @return number of steps towards obstacle if obstacle is visible 
	 * in a straight line of sight, Integer.MAX_VALUE otherwise
	 * @throws UnsupportedOperationException if robot has no sensor in this direction
	 * or the sensor exists but is currently not operational
	 */
	@Override
	public int distanceToObstacle(Direction direction) throws UnsupportedOperationException {
		//puts the powersupply in an array 
		float [] powersupply= {energylvl};
		//Checks forward Direction
		if(direction==Direction.FORWARD) {
			//ensures there is a sensor in forward direction
			if(Forward==null) {
				throw new UnsupportedOperationException("No sensor in forward direction");		
				} 
			
			else {
				try {
					//Uses sensor method to get the distance to the object, adjusts energy level for sensing
					int distance=Forward.distanceToObstacle(getCurrentPosition(), getCurrentDirection(), powersupply);
					energylvl-=1;
					return distance;
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
		}
		else if(direction==Direction.BACKWARD) {
			//ensures there is a sensor in backward direction
			if(Backward==null) {
				throw new UnsupportedOperationException("No sensor in backward direction");		
				}
			else {
				try {
					CardinalDirection dir;
					//Adjusts the direction to check for backward
					if(getCurrentDirection()==CardinalDirection.South) {
						dir=CardinalDirection.North;
					}
					else if(getCurrentDirection()==CardinalDirection.North) {
						dir=CardinalDirection.South;
					}
					else if(getCurrentDirection()==CardinalDirection.West) {
						dir=CardinalDirection.East;
					}
					else {
						dir=CardinalDirection.West;
					}
					//Uses sensor method to get the distance to the object, adjusts energy level for sensing
					int distance=Backward.distanceToObstacle(getCurrentPosition(), dir, powersupply);
					energylvl-=1;
					return distance;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else if(direction==Direction.RIGHT) {
			//ensures there is a sensor in right direction
			if (Right==null) {
				throw new UnsupportedOperationException("No sensor in right direction");
			}
			else {
				try {
					CardinalDirection dir;
					//Adjusts the direction to check for right, adjusted for inversion 
					if(getCurrentDirection()==CardinalDirection.South) {
						dir=CardinalDirection.East;
					}
					else if(getCurrentDirection()==CardinalDirection.North) {
						dir=CardinalDirection.West;
					}
					else if(getCurrentDirection()==CardinalDirection.West) {
						dir=CardinalDirection.South;
					}
					else {
						dir=CardinalDirection.North;
					}
					//Uses sensor method to get the distance to the object, adjusts energy level for sensing
					int distance=Right.distanceToObstacle(getCurrentPosition(), dir, powersupply);
					energylvl-=1;
					return distance;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else  {
			//ensures there is a sensor in left direction
			if (Left==null) {
				throw new UnsupportedOperationException("No sensor in left direction");
			}
			else {
				try {
					CardinalDirection dir;
					//Adjusts the direction to check for left, adjusted for inversion 
					if(getCurrentDirection()==CardinalDirection.South) {
						dir=CardinalDirection.West;
					}
					else if(getCurrentDirection()==CardinalDirection.North) {
						dir=CardinalDirection.East;
					}
					else if(getCurrentDirection()==CardinalDirection.West) {
						dir=CardinalDirection.North;
					}
					else {
						dir=CardinalDirection.South;
					}
					//Uses sensor method to get the distance to the object, adjusts energy level for sensing
					int distance=Left.distanceToObstacle(getCurrentPosition(), dir, powersupply);
					energylvl-=1;
					return distance;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return 0;
	}
	
	/**
	 * uses a sensor to see if the exit is unblocked at indicated direcion 
	 * @param direction specifies the direction of interest
	 * @return true if exit is visible, false otherwise
	 * @throws UnsupportedOperationException if robot has no sensor in this direction
	 * or the sensor exists but is currently not operational
	 */
	
	@Override
	public boolean canSeeThroughTheExitIntoEternity(Direction direction) throws UnsupportedOperationException {
		//puts energylvl in array for sensor method
		float [] powersupply= {energylvl};
		if(direction==Direction.FORWARD) {
			//ensures there is a sensor in Forward direction
			if(Forward==null) {
				throw new UnsupportedOperationException("No sensor in forward direction");		
				}
			else {
				try {
					//Gets distance to obstacle, adjusts energylvl accordingly
					int distance=Forward.distanceToObstacle(getCurrentPosition(), getCurrentDirection(), powersupply);
					energylvl-=1;
					//if distance is 10000, no obstacle detected and return true, else false
					if(distance==10000) {
						return true;
					}
					else {
						return false;
					}
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
		}
		else if(direction==Direction.BACKWARD) {
			//ensures there is a sensor in backward direction
			if(Backward==null) {
				throw new UnsupportedOperationException("No sensor in backward direction");		
				}
			else {
				try {
					CardinalDirection dir;
					//Adjusts the direction to check for backward 
					if(getCurrentDirection()==CardinalDirection.South) {
						dir=CardinalDirection.North;
					}
					else if(getCurrentDirection()==CardinalDirection.North) {
						dir=CardinalDirection.South;
					}
					else if(getCurrentDirection()==CardinalDirection.West) {
						dir=CardinalDirection.East;
					}
					else {
						dir=CardinalDirection.West;
					}
					//Gets distance to obstacle, adjusts energylvl accordingly
					int distance=Backward.distanceToObstacle(getCurrentPosition(), dir, powersupply);
					energylvl-=1;
					//if distance is 10000, no obstacle detected and return true, else false
					if(distance==10000) {
						return true;
					}
					else {
						return false;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else if(direction==Direction.RIGHT) {
			//ensures there is a sensor in right direction
			if (Right==null) {
				throw new UnsupportedOperationException("No sensor in right direction");
			}
			else {
				try {
					CardinalDirection dir;
					//Adjusts the direction to check for right, adjusted for inversion 
					if(getCurrentDirection()==CardinalDirection.South) {
						dir=CardinalDirection.East;
					}
					else if(getCurrentDirection()==CardinalDirection.North) {
						dir=CardinalDirection.West;
					}
					else if(getCurrentDirection()==CardinalDirection.West) {
						dir=CardinalDirection.South;
					}
					else {
						dir=CardinalDirection.North;
					}
					//Gets distance to obstacle, adjusts energylvl accordingly
					int distance=Right.distanceToObstacle(getCurrentPosition(), dir, powersupply);
					energylvl-=1;
					//if distance is 10000, no obstacle detected and return true, else false
					if(distance==10000) {
						return true;
					}
					else {
						return false;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else  {
			//ensures there is a sensor in left direction
			if (Left==null) {
				throw new UnsupportedOperationException("No sensor in left direction");
			}
			else {
				try {
					CardinalDirection dir;
					//Adjusts the direction to check for left, adjusted for inversion 
					if(getCurrentDirection()==CardinalDirection.South) {
						dir=CardinalDirection.West;
					}
					else if(getCurrentDirection()==CardinalDirection.North) {
						dir=CardinalDirection.East;
					}
					else if(getCurrentDirection()==CardinalDirection.West) {
						dir=CardinalDirection.North;
					}
					else {
						dir=CardinalDirection.South;
					}
					//Gets distance to obstacle, adjusts energylvl accordingly
					int distance=Left.distanceToObstacle(getCurrentPosition(), dir, powersupply);
					energylvl-=1;
					//if distance is 10000, no obstacle detected and return true, else false
					if(distance==10000) {
						return true;
					}
					else {
						return false;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	/**
	 * only for unreliable robot
	 */

	@Override
	public void startFailureAndRepairProcess(Direction direction, int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Reliable robot, no threads");
		
	}
	/**
	 * only for unreliable robot
	 */

	@Override
	public void stopFailureAndRepairProcess(Direction direction) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Reliable robot, no threads");
		
	}
	
	/**
	 * stops sensor
	 * @param sensor
	 */
	@Override
	public void stopsensor(DistanceSensor sensor) {
		
	}
	@Override
	public void communicatestop(){
		controller.changeActivitytolosing();
	}

	@Override
	public void communicatesensorissue(DistanceSensor sensor) {


	}

}
