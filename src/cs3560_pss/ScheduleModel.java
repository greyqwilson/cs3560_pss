package cs3560_pss;

import java.util.ArrayList;

public class ScheduleModel {
    ArrayList<Schedule> scheduleList;

    public ScheduleModel() {
        scheduleList = new ArrayList<Schedule>();
    }

    public TaskActivity createTask(String name) {
		return null;
        
    }

    public void deleteTask(String name) {

    }

    public TaskActivity editTask(String name) {
		return null;

    }

    public ArrayList<Schedule> getScheduleList() {
        return this.scheduleList;
    }

}
