package cs3560_pss;


public class TaskActivity{
    private String name; 
    private String type;
    private double startTime;
    private double duration;
    private int date;
    private TaskActivity firstHalf = null;
    private TaskActivity secondHalf = null;
    
    private String[] recurringTaskTypes = 
		{
		"Class", "Study", "Sleep", 
		"Exercise", "Work", "Meal"
		};
    
	private String[] transientTaskTypes = 
		{
			"Visit", "Shopping", "Appointment"	
		};
	
    private String antiTaskType = "Cancellation";

    public TaskActivity(String name, String type, double startTime, double duration, int date){
        this.name = name;
        this.type = type;
        this.startTime = startTime;
        this.duration = duration;
        this.date = date;
    }

    public double getDuration(){
        return  this.duration;
    }
    
    public String getType() {
    	return type;
    }
    
    public static int getYear(int inputDate) {
    	return Integer.parseInt(String.valueOf(inputDate).substring(0,4));
    }
    public static int getMonth(int inputDate) {
    	return Integer.parseInt(String.valueOf(inputDate).substring(4,6));
    }
    public static int getDay(int inputDate) {
    	return Integer.parseInt(String.valueOf(inputDate).substring(6,8));
    }
    
    public double getStartTime() {
    	return this.startTime;
    }
    public double getEndTime() {
    	
    	//get the remainder of start time + duration divided by 24
    	
    	double endTime = (this.startTime + duration)%24;
    	
    	
    	return endTime;
    	
    }

    public String getName(){
    	return this.name;
    }
    
    public int getDate() {
    	return this.date;
    }
    
    public void editTask(String name, int startTime, int duration, int date){
        this.name = name;
        this.startTime = startTime;
        this.duration = duration;
        this.date = date;
    }
    
    public boolean isRecurringTask(){
    	
    	for(int i = 0; i < 6; i++) {
    		if(this.type.toLowerCase().equals(recurringTaskTypes[i].toLowerCase())) {
    			return true;
    		}
    	}
    	
    	return false;
    	
    }
    
    public boolean isTransientTask() {
    	
    	for(int i = 0; i < 3; i++) {
    		if(this.type.toLowerCase().equals(transientTaskTypes[i].toLowerCase())) {
    			return true;
    		}
    	}
    	
    	return false;
    	
    }
    
    public boolean isAntiTask() {
    	
    	if(this.type.toLowerCase().equals(antiTaskType.toLowerCase())) {
    		return true;
    	}
    	
    	return false;
    }

    
    public void setFirstHalf(TaskActivity firstHalf) {
    	this.firstHalf = firstHalf;
    }
    
    public void setSecondHalf(TaskActivity secondHalf) {
    	this.secondHalf = secondHalf;
    }


    public TaskActivity getFirstHalf() {
    	return this.firstHalf;
    }
    
    public TaskActivity getSecondHalf() {
    	return this.secondHalf;
    }


    
}
