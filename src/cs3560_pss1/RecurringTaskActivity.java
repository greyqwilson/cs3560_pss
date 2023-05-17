package cs3560_pss1;

public class RecurringTaskActivity extends TaskActivity{
    private int frequency;
    private int startDate;
    private int endDate;

    
    //takes in base parameters for TaskActivity, takes in additional parameters for frequency, startDate, endDate
    public RecurringTaskActivity(String name, String type, double startTime, double duration, int date, int frequency, int startDate, int endDate){
      super(name, type, startTime, duration, date);
      this.frequency = frequency;
      this.startDate = startDate;
      this.endDate = endDate;
    }
    
    
    //returns start date
    public int getStartDate() {
    	return this.startDate;
    }
    
    //returns end date
    public int getEndDate() {
    	return this.endDate;
    }
    
    //returns frequency
    public int getFrequency() {
    	return this.frequency;
    }
}
