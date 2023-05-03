package cs3560_pss;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Calendar {
    ArrayList<Schedule> scheduleList;
//    ArrayList<Integer>[][] monthAndDayList; //??
    Display display;

    public Calendar() {
        scheduleList = new ArrayList<Schedule>();
        //initialize monthAndDayList
        display = new Display(null);
    }

    public boolean createTask(String name, int startTime, int endTime, int date, String typeString, int frequency, int startDate, int endDate){
      if (typeString == "recurring"){
        RecurringTaskActivity task = new RecurringTaskActivity(name, startTime, endTime, date, frequency, startDate, endDate);
        
        for (int i=startDate; i<endDate; i+=frequency){
          Schedule schedule = scheduleList.get(i);
          if (checkForConflict(task) == true){
            schedule.addTask(task);
          }

          else {
            //Revert tasks notify user
            
          }

        }
      }

      else if (typeString == "transient"){
        TransientTaskActivity task = new TransientTaskActivity(name, startTime, endTime, date);
        Schedule schedule = scheduleList.get(date);
        if (checkForConflict(task) == true){
          schedule.addTask(task);
        }
        
        else{
          //error TODO --Conflict try a different time
        }

      }

      else if (typeString == "anti"){

      }

      return false;
    }

    public boolean deleteTask(TaskActivity task){
		return false;
        
    }

    public boolean searchTask(String name){
      for(Schedule s : scheduleList)
		return false;

    }

    public void loadSchedule(int date){

    }

    public void getTasksForDay(int date){

    }

    public void getTasksForWeek(int date){

    }

    public void getTasksForMonth(int date){

    }

    public void getTasks(){

    }

    public Schedule returnScheduleFile(int date){
		return null;

    }

    public boolean checkForConflict(TaskActivity task){
		return false;

    }

    public ArrayList<Schedule> getScheduleList() {
        return scheduleList;
    }

//    public ArrayList<Integer> getMonthDayList() {
//        return monthAndDayList;
//    }

    public Display getDisplay() {
        return display;
    }
}