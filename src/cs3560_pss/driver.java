package cs3560_pss;

public class driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Calendar calendar = new Calendar();
		Display display = new Display(calendar);
		calendar.setDisplay(display);
		calendar.getScheduleModel().setCalendar(calendar);
		display.start();
	}

}
