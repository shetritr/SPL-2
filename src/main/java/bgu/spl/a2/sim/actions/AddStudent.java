package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class AddStudent extends Action {
    private String studentname;
    private long Department;

    public AddStudent(String studentname,long Department){
        this.Department = Department;
        this.studentname = studentname;
    }



    @Override
    protected void start() {
          if(((DepartmentPrivateState) privateState).AddStudent(studentname)) {
              StudentPrivateState student = new StudentPrivateState();
              student.setSignature(Department);
              MyPool.submit(null,studentname,student);
          }
    }
}
