package cs3560_pss1;


public class TaskActivity{
    private String name; 
    private String type;
    private double startTime;
    private double duration;
    private int date;
    private TaskActivity firstHalf = null;
    private TaskActivity secondHalf = null;
    
    
    //stores type stringsn for recurring/transients, or anti tasks
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

    
    //takes in name, type, start time, duration, and date of a task
    public TaskActivity(String name, String type, double startTime, double duration, int date){
        this.name = name;
        this.type = type;
        this.startTime = startTime;
        this.duration = duration;
        this.date = date;
    }

    //gets duration of task
    public double getDuration(){
        return  this.duration;
    }
    
    //gets type string of task
    public String getType() {
    	return type;
    }
    
    
    //gets year int
    public static int getYear(int inputDate) {
    	return Integer.parseInt(String.valueOf(inputDate).substring(0,4));
    }
    
    // gets month int
    public static int getMonth(int inputDate) {
    	return Integer.parseInt(String.valueOf(inputDate).substring(4,6));
    }
    
    //gets day int
    public static int getDay(int inputDate) {
    	return Integer.parseInt(String.valueOf(inputDate).substring(6,8));
    }
    
    //gets start time double
    public double getStartTime() {
    	return this.startTime;
    }
    
    //gets end time double
    public double getEndTime() {
    	
    	//get the remainder of start time + duration divided by 24
    	
    	double endTime = (this.startTime + duration)%24;
    	
    	
    	return endTime;
    	
    }

    //gets name string
    public String getName(){
    	return this.name;
    }
    
    //gets date int
    public int getDate() {
    	return this.date;
    }
    

    
    //returns if task is recurring
    public boolean isRecurringTask(){
    	
    	for(int i = 0; i < 6; i++) {
    		if(this.type.toLowerCase().equals(recurringTaskTypes[i].toLowerCase())) {
    			return true;
    		}
    	}
    	
    	return false;
    	
    }
    
    //returns if task is transient
    public boolean isTransientTask() {
    	
    	for(int i = 0; i < 3; i++) {
    		if(this.type.toLowerCase().equals(transientTaskTypes[i].toLowerCase())) {
    			return true;
    		}
    	}
    	
    	return false;
    	
    }
    
    
    //return if task is anti
    
    public boolean isAntiTask() {
    	
    	if(this.type.toLowerCase().equals(antiTaskType.toLowerCase())) {
    		return true;
    	}
    	
    	return false;
    }

    
    //links the first half of a wrapping task to this task
    public void setFirstHalf(TaskActivity firstHalf) {
    	this.firstHalf = firstHalf;
    }
    
    //links the second half of a wrapping task to this task
    public void setSecondHalf(TaskActivity secondHalf) {
    	this.secondHalf = secondHalf;
    }


    //gets first half of a task or null
    public TaskActivity getFirstHalf() {
    	return this.firstHalf;
    }
    
    //gets second half of a task or null
    public TaskActivity getSecondHalf() {
    	return this.secondHalf;
    }


    
}
