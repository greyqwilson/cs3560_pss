package cs3560_pss;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.text.StringCharacterIterator;

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

    public void displayTasksforDay() {
      TaskActivity[] tasks;
      tasks = calendar.getTasksForDay(currentDay);
      for (int i=0; i<tasks.length; i++){
        System.out.print(formatTaskPrintout((String.valueOf(tasks[i].getStartTime())),
                                            tasks[i].getName(),
                                            tasks[i].getType(), 
                                            "Description:",
                                            String.valueOf(tasks[i].getEndTime())));
      }
    }
    public void displayTasksforWeek() {
      Schedule[] weekSchedule;
      weekSchedule = calendar.getTasksForWeek(currentWeek);

      TaskActivity[] tasks;
      for (int i=0; i<7; i++) {
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

    private static String formatTaskLine(String text, String matchLengthString){
      return text.concat(" ".repeat(matchLengthString.length()-text.length() - 2) + "|\n");
    }

    //Create a string representation of a task
    private static String formatTaskPrintout(String startTime, String title, String type, String description, String endTime){
        String separator = ("*----------------------------------------*\n");
        //Start time    ~~Title~~
        String titleLine = String.format("|%-7s%3s~~%s~~", startTime, " ", title);
        titleLine = formatTaskLine(titleLine, separator);
        
        String typeLine = "|     (" + type + ")";
        typeLine = formatTaskLine(typeLine, separator);
        
        String descriptionLine = "";
        String descBody = "";
        int descLines = description.length() / separator.length() + 1;
        StringCharacterIterator iterator = new StringCharacterIterator(description);
        if (iterator.first() != StringCharacterIterator.DONE) {
            descriptionLine = descriptionLine.concat("|     -" + String.valueOf(iterator.first()));
            for (int i=0; i<=descLines; i++){
                if (i != 0)
                    descriptionLine = descriptionLine.concat("|     ");
                char c;
                for (int j=0; j<33; j++){
                    c = iterator.next();
                    if (c != StringCharacterIterator.DONE)
                        descriptionLine = descriptionLine.concat(String.valueOf(c));
                    else
                        j = 34;
                }
                descriptionLine = formatTaskLine(descriptionLine, separator);
                descBody = descBody.concat(descriptionLine);
                descriptionLine = "";
            }
        }
        else{
            descBody = "|";
            descBody = formatTaskLine(descBody, separator);
        }
        //descriptionLine = formatTaskLine(descriptionLine, separator);
        
        String endTimeLine = "|" + endTime;
        endTimeLine = formatTaskLine(endTimeLine, separator);
        String s = separator + titleLine + typeLine + descBody + endTimeLine + separator;
        return s;
    }

  }
  
  // Probably not staying
  // \/ \/ \/ \/
  class Menu {
    String title;
    ArrayList<Menu> options;
    ArrayList<String> optionDescription;
    Menu previousMenu;
    
  public void testMenu(){
    Menu main = new Menu("Main Menu");
    Menu yearView = new Menu("Year View");
    main.addMenuOption(yearView, "Year view");
    main.linkPreviousMenu(null);
  }

  public Menu(String newTitle){
    this.title = newTitle;
  }

  public void addMenuOption(Menu newMenuOption, String description){
    options.add(newMenuOption);
    optionDescription.add(description);
    
  }

  public void removeMenuOption(int index){
    options.remove(index);
    optionDescription.remove(index);
  }

  public void linkPreviousMenu(Menu prev){
    this.previousMenu = prev;
  }

  public void changeTitle(String newTitle){
    this.title = newTitle;
  }

}

class MenuOption {
  String description;

}