import java.util.*;

public class Schedule {
    private int date;
    private ArrayList <TaskActivity> taskList;

    public Schedule(int date){
        this.date = date;
        this.taskList = new ArrayList <TaskActivity>();
    }

    public Schedule(int date, ArrayList <TaskActivity> taskList){
        this.taskList = taskList.clone();
    }

    
    public void addTask(TaskActivity task){
        
    }

    public void deleteTask(TaskActivity task){
        
    }
    public void updateTask(TaskActivity task){
        
    }
}
