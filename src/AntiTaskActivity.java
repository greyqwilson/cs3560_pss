public class AntiTaskActivity extends TaskActivity{
    private RecurringTaskActivity recurringTaskLink;

    
    public AntiTaskActivity(String name, int startTime, int endTime, int date, RecurringTaskActivity recurringTask){
      super(name, startTime, endTime, date);
        this.recurringTaskLink = recurringTask;
    }
}
