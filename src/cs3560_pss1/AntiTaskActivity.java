package cs3560_pss1;

public class AntiTaskActivity extends TaskActivity{
   private RecurringTaskActivity recurringTaskLink;

    
    public AntiTaskActivity(String name, String type, double startTime, double duration, int date /*,RecurringTaskActivity recurringTask*/){
      super(name, type, startTime, duration, date);
    }
    
    
    
    public void setRecurring(RecurringTaskActivity task) {
    	this.recurringTaskLink = task;
    }
    
    public RecurringTaskActivity getRecurring() {
    	return this.recurringTaskLink;
    }
    
}
