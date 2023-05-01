public class TransientTaskActivity extends TaskActivity{
    

    public TransientTaskActivity(String name, int startTime, int endTime, int date){
        super(name, startTime, endTime, date);
        this.frequency = frequency;
        this.startDate = startDate;
        this.endDate = endDate;
      }
}
