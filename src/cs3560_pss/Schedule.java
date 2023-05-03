package cs3560_pss;
import java.util.*;

public class Schedule {
    private int date;
    private ArrayList <TaskActivity> taskList;

    public Schedule(int date){
        this.date = date;
        this.taskList = new ArrayList <TaskActivity>();
    }

    public Schedule(int date, ArrayList <TaskActivity> taskList){
        this.taskList = (ArrayList<TaskActivity>) taskList.clone();
    }

    
    public void addTask(TaskActivity task){
		//new task's start and end time
		
		int newStart = task.getStartTime();
		int newEnd = newStart + task.getDuration();
		
		
    	//if the taskList is empty, just add it to the task list
    	if(taskList.size() == 0){
    		taskList.add(task);
    	} // if there is one task in the list
    	
    	else if (taskList.size() == 1) {
    		
    		//first task
			TaskActivity firstTask = taskList.get(0);
			
			//first task's start and end time
			int firstStart = firstTask.getStartTime();
			int firstEnd = firstStart + firstTask.getDuration();

			//if it starts and ends before the first task, add it before
			if(newStart < firstStart && newEnd <= firstStart) {
				taskList.add(0, task);
			}
			
			//otherwise, end it after
			else {
				taskList.add(1,task);
			}
    	}
    	
    	//if there is more than one task in the list
    	else {
    		
    		//boolean saying that the new task has been stored
    		boolean isStored = false;
    		
    		//iterate through task list
    		for( int i = 0; i < taskList.size()-1; i++) {
    			
    			//look at each pair of tasks in the task list

    			//first Task
    			TaskActivity firstTask = taskList.get(i);
    			//second Task
    			TaskActivity secondTask = taskList.get(i+1);
    			    			
    			//first task's start and end time
    			int firstStart = firstTask.getStartTime();
    			int firstEnd =  firstStart + firstTask.getDuration();

    	
    			//second task's start and end time
    			int secondStart = secondTask.getStartTime();
    			int secondEnd = secondStart + secondTask.getDuration();
    			
    			
    			//if it starts and ends before the first task, add it before the first
    			if(newStart < firstStart && newEnd <= firstStart) {
    				taskList.add(0, task);
    				isStored = true;
    			}
    			
    			// if the new task starts after the first task and ends before the second task, add it in between
    			if(newStart > firstEnd && newEnd <= secondStart) {
    				taskList.add(i + 1, task);
    				isStored = true;
    			}
    		
    		}
    		
    		
    		
    		//after we have finished going through each pair of tasks
    		//if the task hasn't been added yet, then that means it should be at the end of the list
    		
    		if(!isStored) {
    			taskList.add(taskList.size()-1, task);
    		}
    		
    		
    	}
    }

    public void deleteTask(TaskActivity task){
        
    	//iterate through task list
    	for (int i = 0; i < this.taskList.size(); i++) {
    		
    		//if the current task is equal to the key task, delete it
    		if(taskList.get(i) == task) {
    			this.taskList.remove(i);
    		}
    	}
    }
    public void updateTask(TaskActivity task){
        
    }
    
    
    
}
