package cs3560_pss;


public class TaskActivity{
    private String name; 
    private int startTime;
    private int endTime;
    private int date;

    public TaskActivity(String name, int startTime, int endTime, int date){
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
    }

    public int calcDuration(){
        return endTime - startTime;
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
    
    

    
}
