package bgu.spl.a2.sim.privateStates;

import java.util.HashMap;
import java.util.LinkedList;

import bgu.spl.a2.PrivateState;

/**
 * this class describe student private state
 */
public class StudentPrivateState extends PrivateState{

	private HashMap<String, Integer> grades;
	private long signature;
	
	/**
 	 * Implementors note: you may not add other constructors to this class nor
	 * you allowed to add any other parameter to this constructor - changing
	 * this may cause automatic tests to fail..
	 */
	public StudentPrivateState() {
		grades = new HashMap<String, Integer>();
		signature = new Long(0);
		this.setHistory(new LinkedList<String>());

	}

	public HashMap<String, Integer> getGrades() {
		return grades;
	}

	public long getSignature() {
		return signature;
	}

	public void setGrades(HashMap<String, Integer> grades) {
		this.grades = grades;
	}

	public void addGrades(String coursename,Integer coursegrade){
		grades.put(coursename,coursegrade);
	}

	public void setSignature(long signature) {
		this.signature = signature;
	}

	public void RemoveGrade(String coursename){
		grades.remove(coursename);
	}

}
