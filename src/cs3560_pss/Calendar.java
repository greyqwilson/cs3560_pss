package cs3560_pss;
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

    public boolean createTask(TaskActivity task){
		return false;

    }

    public boolean deleteTask(TaskActivity task){
		return false;
        
    }

    public boolean searchTask(String name){
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