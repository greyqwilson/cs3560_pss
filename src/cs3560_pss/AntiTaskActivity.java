package cs3560_pss;

public class AntiTaskActivity extends TaskActivity{
    private RecurringTaskActivity recurringTaskLink;

    
    public AntiTaskActivity(String name, String type, int startTime, int endTime, int date, RecurringTaskActivity recurringTask){
      super(name, type, startTime, endTime, date);
        this.recurringTaskLink = recurringTask;
    }
    
    
}
