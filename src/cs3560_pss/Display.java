package cs3560_pss;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Display{

    private Calendar calendar;
    private int currentDay, currentWeek, currentMonth;
    final String SCHEDULE_DIR = ".\\schedules\\";
    

    public Display(Calendar calendar){
        this.calendar = calendar;
    }


    public TaskActivity searchTask(String taskName){
      TaskActivity task = calendar.searchTask(taskName);
		  return task;
    }

    public void loadScheduleFromFile() {
      Schedule schedFromFile;
      File file = new File(SCHEDULE_DIR);
      //Open file for reading
      try (FileReader fw = new FileReader(file)){
        
      }
      catch (IOException e){

      }

      //Copy relevant data to a schedule object
      //Pass into whoever takes it and display
      
    }

    public void writeScheduleToFile(){

    }

    public void displayTasksforDay(){
      TaskActivity[] tasks;
      tasks = calendar.getTasksForDay(currentDay);
      printTaskList(tasks);

    }
    public void displayTasksforWeek(){
      Schedule[] weekSchedule;
      weekSchedule = calendar.getTasksForWeek(currentWeek);

      TaskActivity[] tasks;
      for (int i=0; i<7; i++){
        tasks[i] = calendar.getTasksForDay(i);
        printTaskList(tasks);
      }
    }
    public void displayTasksforMonth(){
      Schedule[] monthSchedule = calendar.getTasksForMonth(currentMonth);
      TaskActivity[] tasks;
      for (int i=0; i<monthLength; i++){
        tasks[i] = calendar.getTasksForDay(i);

      }
    }

    public void printTaskList(TaskActivity[] taskList){
      for (TaskActivity task : taskList) {
        
      }
    }
}
