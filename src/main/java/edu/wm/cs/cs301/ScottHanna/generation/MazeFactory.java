package edu.wm.cs.cs301.ScottHanna.generation;

import java.util.logging.Logger;
import android.util.Log;

/**
 * This class encapsulates how a maze is generated. 
 * It takes orders to produce a maze, delegates it to the matching maze builder 
 * that computes and delivers the maze. This class operates the worker thread
 * to do the computation in the background. The maze builder classes contribute
 * a run method to execute on the worker thread.
 */
public class MazeFactory implements Factory {
	/**
	 * The logger is used to track execution and report issues.
	 */
	private static final Logger LOGGER = Logger.getLogger(MazeFactory.class.getName());

	// factory keeps track of the current order, takes at most one order at a time
	private Order currentOrder;
	// factory has a MazeBuilder to do the work
	// note that subclasses are instantiated for specific algorithms such as Prim's
	// according to the given order
	private MazeBuilder builder;
	// 
	private Thread buildThread; // computations are performed in own separated thread with this.run()
	
	//////////////////////// Constructor ////////////////////////////////////////
	/**
	 * Constructor for a randomized maze generation
	 */
	public MazeFactory(){
		// nothing to do
	}
	
	//////////////////////// Factory interface //////////////////////////////////
	@Override
	public boolean order(Order order) {
		// check if factory is busy
		if (null != buildThread && buildThread.isAlive()) {
			// order is currently processed, don't queue, just refuse
			Log.w("two orders","Refusing to take order, too busy with current order");
			return false;
		}
		// idle, so accept order
		currentOrder = order;
		// set builder according to order
		switch (order.getBuilder()) {
		case DFS :
			builder = new MazeBuilder();
			buildOrder();
			break;
		case Prim:
			builder = new MazeBuilderPrim();
			buildOrder();
			break;
		case Boruvka:
			builder = new MazeBuilderBoruvka();
			buildOrder();
			break;
		default:
			Log.w("Missing order","Missing implementation for requested algorithm: " + order.getBuilder());
			return false;
		}
		return true ;
	}
	@Override
	public void cancel() {
		Log.v("Order canceled","Received call to cancel current order");
		if (null != buildThread) {
			buildThread.interrupt() ;
			buildThread = null; // allow for next order to get through
		}
		else {
			Log.w("No thread","Received call to cancel current order, but there is no thread to stop");
		}
		// clean up happens in interrupt handling in run method
		builder = null;
		currentOrder = null;
	}
	@Override
	public void waitTillDelivered() {
		if (null != buildThread) {
			try {
				buildThread.join();
			} catch (Exception e) {
				Log.w("Error joining thread","Join synchronization with builder thread lead to an exception") ;
			}
		}
		else {
			Log.w("No Thread","Received call to wait for thread to finish, but there is no thread to wait for");
		}
		builder = null;
		currentOrder = null;
	}
	///////////////////////// private methods ///////////////////////////////////
	/**
	 * Provide the builder with necessary input and start its execution
	 */
	private void buildOrder() { 
		if (null == builder)
			return;
		Log.v("Background thread started","Starting background thread to build the ordered maze") ;
		builder.buildOrder(currentOrder);
		buildThread = new Thread(builder);
		buildThread.start();
	}
}
