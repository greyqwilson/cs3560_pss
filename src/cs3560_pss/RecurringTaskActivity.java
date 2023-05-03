package cs3560_pss;

public class RecurringTaskActivity extends TaskActivity{
    private int frequency;
    private int startDate;
    private int endDate;
    private String[] types = 
    		{
    		"Class", "Study", "Sleep", 
    		"Exercise", "Work", "Meal"
    		};

    
    public RecurringTaskActivity(String name, int startTime, int endTime, int date, int frequency, int startDate, int endDate){
      super(name, startTime, endTime, date);
      this.frequency = frequency;
      this.startDate = startDate;
      this.endDate = endDate;
    }
}
