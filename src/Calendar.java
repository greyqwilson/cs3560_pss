package src;
import java.util.ArrayList;

public class Calendar {
    ArrayList<Schedule> scheduleList;
    ArrayList<Integer>[][] monthAndDayList; //??
    Display display;

    public Calendar() {
        scheduleList = new ArrayList<Schedule>();
        //initialize monthAndDayList
        display = new Display();
    }

    public boolean createTask(Task task){

    }

    public boolean deleteTask(Task task){
        
    }

    public boolean searchTask(String name){

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

    }

    public boolean checkForConflict(Task task){

    }

    public ArrayList<Schedule> getScheduleList() {
        return scheduleList;
    }

    public ArrayList<Integer> getMonthDayList() {
        return monthAndDayList;
    }

    public Display getDisplay() {
        return display;
    }
}