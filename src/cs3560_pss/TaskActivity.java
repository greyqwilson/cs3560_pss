package cs3560_pss;


public class TaskActivity{
    private String name; 
    private String type;
    private int startTime;
    private int endTime;
    private int date;
    
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

    public TaskActivity(String name, String type, int startTime, int endTime, int date){
        this.name = name;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
    }

    public int getDuration(){
        return endTime - startTime;
    }
    
    public String getType() {
    	return type;
    }
    
    public int getYear() {
    	return Integer.parseInt(String.valueOf(date).substring(0,4));
    }
    public int getMonth() {
    	return Integer.parseInt(String.valueOf(date).substring(4,6));
    }
    public int getDay() {
    	return Integer.parseInt(String.valueOf(date).substring(6,8));
    }
    
    public int getStartTime() {
    	return this.startTime;
    }
    public int getEndTime() {
    	return this.endTime;
    }

    public String getName(){
    	return this.name;
    }
    
    public int getDate() {
    	return this.date;
    }
    
    public void editTask(String name, int startTime, int endTime, int date){
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
    }
    
    public boolean isRecurringTask(){
    	
    	for(int i = 0; i < 6; i++) {
    		if(this.type == recurringTaskTypes[i]) {
    			return true;
    		}
    	}
    	
    	return false;
    	
    }
    
    public boolean isTransientTask() {
    	
    	for(int i = 0; i < 3; i++) {
    		if(this.type == transientTaskTypes[i]) {
    			return true;
    		}
    	}
    	
    	return false;
    	
    }
    
    public boolean isAntiTask() {
    	
    	if(this.type == antiTaskType) {
    		return true;
    	}
    	
    	return false;
    }

    
}
