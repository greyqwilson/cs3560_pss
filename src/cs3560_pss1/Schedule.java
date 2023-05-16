package cs3560_pss1;

import java.util.*;

public class Schedule {
	private int date;
	private ArrayList<TaskActivity> taskList;

	public Schedule(int date) {
		this.date = date;
		this.taskList = new ArrayList<TaskActivity>();
	}

	@SuppressWarnings("unchecked")
	public Schedule(int date, ArrayList<TaskActivity> taskList) {
		this.date = date;
		this.taskList = (ArrayList<TaskActivity>) taskList.clone();
	}

	public void addTask(TaskActivity task) {

		this.taskList.add(task);
	}

	// sorts tasklist in order of startTime increasing
	public ArrayList<TaskActivity> getOrderedTaskList() {
		ArrayList<TaskActivity> ordered = new ArrayList<TaskActivity>();

		// while taskList is not empty
		while (this.taskList.size() > 0) {

			// iterate through list to find earliest task and position
			int earliestTask = 0;
			TaskActivity earliest = this.taskList.get(0);
			for (int i = 1; i < taskList.size(); i++) {
				TaskActivity current = this.getTaskList().get(i);
				if (earliest.getStartTime() > current.getStartTime()) {
					earliest = current;
					earliestTask = i;
				}
			}

			// remove earliestTask

			this.taskList.remove(earliestTask);

			// add earliest task into ordered arraylist
			ordered.add(earliest);

		}

		// set the ordered list to be the tasklist
		this.taskList = ordered;

		return this.taskList;

	}

	public void deleteTask(TaskActivity task) {

		// iterate through task list
		for (int i = 0; i < this.taskList.size(); i++) {

			// if the current task is equal to the key task, delete it
			if (taskList.get(i) == task) {
				this.taskList.remove(i);
			}
		}
	}

	public void updateTask(TaskActivity task) {

	}

	public ArrayList<TaskActivity> getTaskList() {
		return this.taskList;
	}

	public int getDate() {
		return date;
	}

	public String getDateString() {
		return String.valueOf(date);
	}

}
