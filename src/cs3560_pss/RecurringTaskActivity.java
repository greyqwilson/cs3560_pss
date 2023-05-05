package cs3560_pss;

public class RecurringTaskActivity extends TaskActivity{
    private int frequency;
    private int startDate;
    private int endDate;

    
    public RecurringTaskActivity(String name, String type, double startTime, double duration, int date, int frequency, int startDate, int endDate){
      super(name, type, startTime, duration, date);
      this.frequency = frequency;
      this.startDate = startDate;
      this.endDate = endDate;
    }
    
}
