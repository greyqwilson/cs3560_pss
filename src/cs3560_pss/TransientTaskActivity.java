package cs3560_pss;
public class TransientTaskActivity extends TaskActivity{
	
	private String[] taskTypes = 
		{
			"Visit", "Shopping", "Appointment"	
		};
    

    public TransientTaskActivity(String name, String type, double startTime, double duration, int date){
        super(name, type, startTime, duration, date);
      }
    
    public boolean isTransientTask(TaskActivity task) {
    	
    	for(int i = 0; i < 4; i++) {
    		if(task.getType() == taskTypes[i]) {
    			return true;
    		}
    	}
    	
    	return false;
    	
    }
    
}


