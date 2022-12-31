package edu.wm.cs.cs301.ScottHanna.gui;

import edu.wm.cs.cs301.ScottHanna.gui.Robot.Direction;

/**
 * Collaborators: Control (from which it also gets information about the maze),
 * ReliableSensor, unreliablesensor, wall follower.
 * 
 * Responsibilities: turns (left, right, and around), adds sensors, 
 * gets distance to walls using sensors, moves (steps and jumps), stores energy
 * levels and directly affects them when moving, will stop when the robot runs out of energy.
 * sensors will fail occasionally, hence unreliable
 * 
 * 
 * @author Scott Hanna
 */
public class UnreliableRobot extends ReliableRobot{
	

	/**
	 * adds an unreliable sensor to the robot, that points either forward, back, left, right. 
	 * Each distance sensor that is unreliable has its own thread
	 * 
	 * This method is needed to start the thread using thread.start()
	 * It will then sleep the thread for 4000 miliseconds, using meanTimeBetweenFailures()
	 * it will then start the thread again
	 * then it will sleep the thread for 2000 miliseconds, using meanTimeToRepair()
	 * It will go through this loop as long as the robot is playing the game
	 * 
	 * for each unreliable sensor, if there is more than one 1300 milisecons will be added to the first sleep
	 * 
	 * @param mountedDirection either, forward, back, left, right.
	 * @param sensor that is either reliable or not. 
	 */
	
	
	/**
	@Override
	public void addDistanceSensor(DistanceSensor sensor, Direction mountedDirection) {
		//assigns sensor to correct direction
		if(sensor.isreliable()==true) {
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
		
		else {
			if(mountedDirection==Direction.FORWARD) {
				//creates unreliable sensor in forward direction
				UnreliableSensor f=new UnreliableSensor();
				Forward=f;
				
				Forward.setSensorDirection(mountedDirection);

				
				
				
	
			

			}
			else if(mountedDirection==Direction.BACKWARD) {
				//creates unreliable sensor in backward direction 
				UnreliableSensor f=new UnreliableSensor();
				Backward=f;
				
				Backward.setSensorDirection(mountedDirection);
			
				
				
				
			
				
			}
			else if(mountedDirection==Direction.RIGHT) {
				//creates unreliable sensor in right direction 
				UnreliableSensor f=new UnreliableSensor();
				Right=f;
				
				Right.setSensorDirection(mountedDirection);
			
				
			
				}
			
			else {
				//creates unreliable sensor in right direction 
				UnreliableSensor f=new UnreliableSensor();
				Left=f;
				
				Left.setSensorDirection(mountedDirection);
				
				
		
				
			}
		}
		
	}
	*/
	
	
	private int count=0;
	/**
	 * starts failure and repair process for each sensor,
	 * in reality delegates this to the unreliable sensor class
	 */

	@Override
	public void startFailureAndRepairProcess(Direction direction, int meanTimeBetweenFailures, int meanTimeToRepair)
	
			throws UnsupportedOperationException {
		//Passes this responsibility off to sensor
		if(direction==Direction.FORWARD) {
			try {
				
				Thread t1=new Thread((Runnable) Forward);
				Forward.startFailureAndRepairProcess(meanTimeBetweenFailures, meanTimeToRepair);
				Forward.setcount(count);
				count+=1;
			
				t1.start();
				
				}
			catch(Exception e) {
				throw new UnsupportedOperationException("Sensor is reliable");
			}
		}
		else if(direction==Direction.RIGHT) {
			try {
				Thread t1=new Thread((Runnable) Right);
				Right.startFailureAndRepairProcess(meanTimeBetweenFailures, meanTimeToRepair);
				Right.setcount(count);
				count+=1;
				t1.start();
				}
			catch(Exception e) {
				throw new UnsupportedOperationException("Sensor is reliable");
			}
		}
		else if(direction==Direction.BACKWARD) {
			try {
				Thread t1=new Thread((Runnable) Backward);
				Backward.startFailureAndRepairProcess(meanTimeBetweenFailures, meanTimeToRepair);
				Backward.setcount(count);
				count+=1;
				t1.start();
				}
			catch(Exception e) {
				throw new UnsupportedOperationException("Sensor is reliable");
			}
		}
		else {
			try {
				Thread t1=new Thread((Runnable) Left);
				Left.startFailureAndRepairProcess(meanTimeBetweenFailures, meanTimeToRepair);
				Left.setcount(count);
				count+=1;
				t1.start();
				}
			catch(Exception e) {
				throw new UnsupportedOperationException("Sensor is reliable");
			}
		}
		
	}
	/**
	 * stops failure and repair process for each sensor,
	 * in reality delegates this to the unreliable sensor class
	 */

	@Override
	public void stopFailureAndRepairProcess(Direction direction) throws UnsupportedOperationException {
		//Passes this responsibility off to sensor
		if(direction==Direction.FORWARD) {
			try {
				Forward.stopFailureAndRepairProcess();}
			catch(Exception e) {
				throw new UnsupportedOperationException("Sensor is reliable");
			}
		}
		else if(direction==Direction.RIGHT) {
			try {
				Right.stopFailureAndRepairProcess();}
			catch(Exception e) {
				throw new UnsupportedOperationException("Sensor is reliable");
			}
		}
		else if(direction==Direction.BACKWARD) {
			try {
				Backward.stopFailureAndRepairProcess();}
			catch(Exception e) {
				throw new UnsupportedOperationException("Sensor is reliable");
			} 
		}
		else {
			try {
				Left.stopFailureAndRepairProcess();}
			catch(Exception e) {
				throw new UnsupportedOperationException("Sensor is reliable");
			}
		}
		
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
		//checks if given sensor is working using the isworking method
		
		if(direction==Direction.FORWARD) {
			
			if(Forward instanceof UnreliableSensor) {
				if(((UnreliableSensor) Forward).isworking()==false) {
					throw new UnsupportedOperationException("Sensor not Operational");
				}
			}
		}
		else if(direction==Direction.BACKWARD) {
			
			if(Backward instanceof UnreliableSensor) {
				if(((UnreliableSensor) Backward).isworking()==false) {
					throw new UnsupportedOperationException("Sensor not Operational");
				}
			}
		}
		else if(direction==Direction.LEFT) {
			
			if(Left instanceof UnreliableSensor) {
				if(((UnreliableSensor) Left).isworking()==false) {
					throw new UnsupportedOperationException("Sensor not Operational");
				}
			}
		}
		else {
			
			if(Right instanceof UnreliableSensor) {
				if(((UnreliableSensor) Right).isworking()==false) {
					throw new UnsupportedOperationException("Sensor not Operational");
				}
			}
		}
		//If no exception is thrown, the sensor is operational
		//call the same method as in reliable robot
		return super.distanceToObstacle(direction);
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
		//checks if given sensor is working using the isworking method

		if(direction==Direction.FORWARD) {
			
			if(Forward instanceof UnreliableSensor) {
				if(((UnreliableSensor) Forward).isworking()==false) {
					throw new UnsupportedOperationException("Sensor not Operational");
				}
			}
		}
		else if(direction==Direction.BACKWARD) {
			
			if(Backward instanceof UnreliableSensor) {
				if(((UnreliableSensor) Backward).isworking()==false) {
					throw new UnsupportedOperationException("Sensor not Operational");
				}
			}
		}
		else if(direction==Direction.LEFT) {
			
			if(Left instanceof UnreliableSensor) {
				if(((UnreliableSensor) Left).isworking()==false) {
					throw new UnsupportedOperationException("Sensor not Operational");
				}
			}
		}
		else {
			
			if(Right instanceof UnreliableSensor) {
				if(((UnreliableSensor) Right).isworking()==false) {
					throw new UnsupportedOperationException("Sensor not Operational");
				}
			}
		}
		//If no exception is thrown, the sensor is operational
		//call the same method as in reliable robot
		return super.canSeeThroughTheExitIntoEternity(direction);
	}
	
	
}
