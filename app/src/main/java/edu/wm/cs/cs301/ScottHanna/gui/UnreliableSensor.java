package edu.wm.cs.cs301.ScottHanna.gui;

import edu.wm.cs.cs301.ScottHanna.generation.CardinalDirection;

/**
 * Collaborators: Control (from which it also gets information about the maze),
 * Unreliable robot.
 * 
 * Responsibilities: gets distance to wall at given direction, affects energy of 
 * Robot for sensing, fails to work, hence, unreliable.
 * 
 * @author Scott Hanna
 */

public class UnreliableSensor extends ReliableSensor implements Runnable{
	/**
	 * Has a boolean variable isworking which is true when the robot is working
	 * false otherwise
	 */
	
	private boolean isworking=false;
	private boolean runthread;
	private long betfailtime;
	private long repairtime;
	private boolean firsttime=true;
	private int count;
	/**
	 * If isworking is false, call repair
	 * else call fail
	 */
	
	int countrepairs=0;
	int countfails=0;
	/**
	 * Used for testing 
	 * @return countrepairs
	 */
	public int getcountrepairs() {
		return countrepairs;
	}
	
	/**
	 * Used for testing 
	 * @return countfails
	 */
	public int getcountfails() {
		return countfails;
	}

	
	
	@Override 
	public void run() {
		//repairs the sensor if it is failing
		//will run while robot is not at exit
		//starts the sensor working, then after 4 seconds breaks it
		//after 2 seconds of being broken, it fixes the sensor
		while(runthread==true) {
			if (firsttime=true) {
				if(isworking==false) {
					repair();
					try {
						Thread.sleep(betfailtime+1300*count);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//fails the sensor if it is working
				else {
					fail();
					try {
						Thread.sleep(repairtime);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
				firsttime=false;
			}
			
			
			if(isworking==false) {
				repair();
				try {
					Thread.sleep(betfailtime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//fails the sensor if it is working
			else {
				fail();
				try {
					Thread.sleep(repairtime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
			}
	
	}
		

	
	
	/**
	 * Starts the failure of the unreliable sensor and repairs it
	 * turns isworkingtofalse
	 */
	public void fail() {
		isworking=false;
		countfails+=1;
	}
	
	/**
	 * Ends the failure process, i.e. the sensor can continue working
	 * turns is working to true
	 */
	public void repair() {
		isworking=true;
		countrepairs+=1;
	}
	/**
	 * only used in testing
	 */
	public boolean getrunthread() {
		return runthread;
	}

	/**
	 * @return false bc sensor is unreliable
	 */
	@Override
	public boolean isreliable() {
		
		return false;
	}
	
	
	/**
	 * @return iswroking, if the sensor is working
	 */
	public boolean isworking() {
		return isworking;
	}
	
	/**
	 * calculates distance to wall by using the floorplan accessed by maze. 
	 * @param currentPosition is the current location as (x,y) coordinates
	 * @param currentDirection specifies the direction of the robot
	 * @param powersupply is an array of length 1, whose content is modified 
	 * to account for the power consumption for sensing
	 * @return number of steps towards obstacle if obstacle is visible 
	 * in a straight line of sight, Integer.MAX_VALUE otherwise.
	 * 
	 * Will check first if the sensor is working by checking the field isworking, if it is not
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
		//Checks if the sensor is working, if it is, then it works the same as reliable sensor
		if(isworking==true) {
			return super.distanceToObstacle(currentPosition, currentDirection, powersupply);
		}
		//Throws an exception bc the sensor is failing but the sensor was called on 
		else {
			throw new Exception("Sensor is unopertational");
		}
				}
	
	/**
	 * Starts failure are repairs process
	 * @param meanTimeBetweenFailures is 4 seconds
	 * @param meanTimeToRepair is 2 seconds
	 */

	@Override
	public void startFailureAndRepairProcess(int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException {
		//Sets the fail time and repair time
		betfailtime=meanTimeToRepair*1000;
		repairtime=meanTimeBetweenFailures*1000;
		//makes boolean flag true
		runthread=true;
		
		
		
		
	}
	/**
	 * only for unreliable sensor
	 */

	@Override
	public void stopFailureAndRepairProcess() throws UnsupportedOperationException {
		//sets runthread to false, killing the thread
		runthread=false;
		
	}
	
	public void setcount(int count) {
		this.count=count;
	}

}
