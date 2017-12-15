package bgu.spl.a2.sim.privateStates;

import java.util.LinkedList;
import java.util.List;

import bgu.spl.a2.PrivateState;

/**
 * this class describe department's private state
 */
public class DepartmentPrivateState extends PrivateState{
	private List<String> courseList;
	private List<String> studentList;
	
	/**
 	 * Implementors note: you may not add other constructors to this class nor
	 * you allowed to add any other parameter to this constructor - changing
	 * this may cause automatic tests to fail..
	 */
	public DepartmentPrivateState() {
		courseList = new LinkedList<String>();
		studentList = new LinkedList<String>();
		setHistory(new LinkedList<String>());
	}
	public boolean AddCourse(String course){
			return courseList.add(course);//if this list changed as a result of the call
	}
	public boolean AddStudent(String student){
			return studentList.add(student);//if this list changed as a result of the call
	}
	public void removeCours(String course){
		courseList.remove(course);
	}
	public void removeStudent(String student){
		studentList.remove(student);
	}

	public List<String> getCourseList() {
		return courseList;
	}

	public List<String> getStudentList() {
		return studentList;
	}
	
}
