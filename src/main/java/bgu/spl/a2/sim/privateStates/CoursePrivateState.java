package bgu.spl.a2.sim.privateStates;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import bgu.spl.a2.PrivateState;

/**
 * this class describe course's private state
 */
public class CoursePrivateState extends PrivateState{

	private Integer availableSpots;
	private Integer registered;
	private List<String> regStudents;
	private List<String> prequisites;
	
	/**
 	 * Implementors note: you may not add other constructors to this class nor
	 * you allowed to add any other parameter to this constructor - changing
	 * this may cause automatic tests to fail..
	 */
	public CoursePrivateState() {
		regStudents = new LinkedList<String>();
		prequisites = new LinkedList<String>();
		availableSpots = new Integer(0);
		registered = new Integer(0);
		this.setHistory(new LinkedList<String>());


	}

	public void setAvailableSpots(Integer availableSpots) {
		this.availableSpots = availableSpots;
	}

	public void setPrequisites(List<String> prequisites) {
		this.prequisites = prequisites;
	}

	private void setRegistered(Integer registered) {
		this.registered = registered;
	}

	public void addRegStudent(String regStudents) {
		if(!regStudents.contains(regStudents)) {
			this.regStudents.add(regStudents);
			setRegistered(registered.intValue()+1);
		}
	}

	public Integer getAvailableSpots() {
		return availableSpots;
	}

	public Integer getRegistered() {
		return registered;
	}

	public List<String> getRegStudents() {
		return regStudents;
	}

	public List<String> getPrequisites() {
		return prequisites;
	}

	public void RemoveStudent (String studentname){
		if(regStudents.remove(studentname))
			setRegistered(registered.intValue()-1);
	}
}
