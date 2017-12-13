package bgu.spl.a2;

import java.util.List;

/**
 * an abstract class that represents private states of an actor
 * it holds actions that the actor has executed so far 
 * IMPORTANT: You can not add any field to this class.
 */
public abstract class PrivateState {
	
	// holds the actions' name what were executed
	private List<String> history;

	public synchronized List<String> getLogger(){
		return history;
	}
	
	/**
	 * add an action to the records
	 *  
	 * @param actionName
	 */
	public synchronized void addRecord(String actionName){
		history.add(actionName);
	}
	
	
}
