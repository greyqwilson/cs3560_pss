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

    public void editTask(String name, int startTime, int endTime, int date){
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
    }
    
    

    
}
