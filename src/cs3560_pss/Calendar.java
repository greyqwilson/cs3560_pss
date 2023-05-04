package cs3560_pss;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Calendar {

	// Assuming the Calendar starts from 2020
	ArrayList<ArrayList<Schedule>> scheduleList;
//    ArrayList<Integer>[][] monthAndDayList; //??
	Display display;

	public Calendar() {
		scheduleList = new ArrayList<ArrayList<Schedule>>();
		// initialize monthAndDayList
		display = new Display(null);
	}

	// calculates the index of the day schedule that is the beginning of the input
	// month
	public int calculateMonthBeginning(int month) {
		int[] monthDays = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

		int monthBeginning = 0;
		for (int i = 0; i < month; i++) {
			monthBeginning += monthDays[i];
		}

		return monthBeginning;
	}

	// calculate the month from the number of days

	public int calculateMonthFromDays(int days) {

		// stores the number of days that have passed in the year up to the end of that
		// month
		int[] accumulatedDaysofMonths = { 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365 };

		// iterate through the array
		for (int i = 0; i < accumulatedDaysofMonths.length; i++) {

			// if the number of days is lower than the number of days gone by in the current
			// month
			// then the current month is returned
			if (days <= accumulatedDaysofMonths[i]) {
				return i + 1;
			}
		}

		// otherwise something has gone wrong
		return -1;
	}

	// calculate day of month from number of days

	public int calculateDayOfMonthFromNumDays(int days) {

		int dayOfMonth = days;

		int[] monthDays = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		// iterate through months
		for (int i = 0; i < monthDays.length; i++) {

			// subtract number of days in the current month from number of days until we
			// reach a number of days lower than the current month's
			if (dayOfMonth > monthDays[i]) {
				dayOfMonth -= monthDays[i];
			} else {
				break;
			}
		}

		return dayOfMonth;

	}

	// creates subarraylists in the schedule list for the year and fills them up
	// with schedules

	public void createYear(int year) {

		// Assuming we are starting from 2020

		// calculate the supposed index of the year

		int yearIndex = year - 2020;
		// if the year exists in the list

		if (yearIndex < this.scheduleList.size()) {
			// do nothing
		}
		// if the year doesn't exist and is just the next one that follows after the
		// last year in the list
		else if (yearIndex == this.scheduleList.size()) {
			// just add in another list the end
			this.scheduleList.add(new ArrayList<Schedule>());
			// add in 365 schedule for that year
			for (int k = 1; k <= 365; k++) {

				String yearString = String.valueOf(2020 + yearIndex);
				String monthString = String.valueOf(this.calculateMonthFromDays(k));
				String dayString = String.valueOf(this.calculateDayOfMonthFromNumDays(k));

				if (monthString.length() == 1) {
					monthString = "0" + monthString;
				}

				if (dayString.length() == 1) {
					dayString = "0" + dayString;
				}

				int resultDate = Integer.parseInt(yearString + monthString + dayString);

				this.scheduleList.get(this.scheduleList.size() - 1).add(new Schedule(resultDate));
			}
		}
		// if the year doesn't exist and there's a gap larger than 1 year from the year
		// at the end of list
		else if (yearIndex > this.scheduleList.size()) {

			// add in the all gap years and the new year
			for (int i = this.scheduleList.size() - 1; i < yearIndex; i++) {
				this.scheduleList.add(new ArrayList<Schedule>());

				// add in 365 schedule for that year
				for (int k = 1; k <= 365; k++) {

					String yearString = String.valueOf(2020 + yearIndex);
					String monthString = String.valueOf(this.calculateMonthFromDays(k));
					String dayString = String.valueOf(this.calculateDayOfMonthFromNumDays(k));

					if (monthString.length() == 1) {
						monthString = "0" + monthString;
					}

					if (dayString.length() == 1) {
						dayString = "0" + dayString;
					}

					int resultDate = Integer.parseInt(yearString + monthString + dayString);

					this.scheduleList.get(this.scheduleList.size() - 1).add(new Schedule(resultDate));
				}
			}

		} // otherwise you've chosen a year before 2020
		else {

			// notify user that years can't be before 2020
		}

	}

	// calculate number of days from one date to the next

	public int numDaysFromStartToEnd(int startDate, int endDate) {

		// subtracts number of days since 2020 of the end and start date, ex: 4/1/2020 -
		// 4/2/2020 = 1
		return this.numOfDaysFrom2020(startDate) - this.numOfDaysFrom2020(endDate);
	}

	// calculate number of days from beginning of 2020 to this date
	public int numOfDaysFrom2020(int date) {

		// get year, month, day of date
		int year = TaskActivity.getYear(date);
		int month = TaskActivity.getMonth(date);
		int day = TaskActivity.getDay(date);

		int numDays = 0;

		// calculate number of days from 2020 to the beginning of the current year

		numDays += (year - 2020) * 365;

		// stores the number of days that have passed in the year up to the end of that
		// month
		int[] accumulatedDaysofMonths = { 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365 };

		// add the number of days from the beginning of the current year to the
		// beginning of the current month
		// unless the current month is the first month, in which case its 0

		if (month > 1) {
			numDays += accumulatedDaysofMonths[month - 2];
		}

		// now add the number of days from the beginning of the month to the current day
		// of the month

		numDays += day;

		// we are done
		return numDays;

	}

	public boolean createTask(String name, int startTime, int endTime, int date, String typeString, int frequency,
			int startDate, int endDate) {

		String[] recurringTaskTypes = { "Class", "Study", "Sleep", "Exercise", "Work", "Meal" };

		String[] transientTaskTypes = { "Visit", "Shopping", "Appointment" };

		String antiTaskType = "Cancellation";

		boolean isRecurring = false;
		boolean isTransient = false;
		boolean isAnti = false;

		// check if it's recurring, transient, or anti task
		for (int i = 0; i < recurringTaskTypes.length; i++) {
			if (typeString.toLowerCase() == recurringTaskTypes[i].toLowerCase()) {
				isRecurring = true;
			}
		}

		for (int i = 0; i < transientTaskTypes.length; i++) {
			if (typeString.toLowerCase() == transientTaskTypes[i].toLowerCase()) {
				isTransient = true;
			}
		}

		if (typeString.toLowerCase() == antiTaskType.toLowerCase()) {
			isRecurring = true;
		}

		if (isRecurring) {
			RecurringTaskActivity task = new RecurringTaskActivity(name, typeString, startTime, endTime, date,
					frequency, startDate, endDate);

			// get the year, month, and day separately

			int year = TaskActivity.getYear(task.getDate());
			int month = TaskActivity.getMonth(task.getDate());
			int day = TaskActivity.getDay(task.getDate());

			// get the beginning schedule index of the month
			int beginningOfMonth = this.calculateMonthBeginning(month);

			// get the index of the schedule of the day in the month
			int specificDateIndex = beginningOfMonth + day - 1;

			// create the year sub arraylist in the schedule list if it doesn't exist
			// already
			this.createYear(year);

			// get the indexes of the year, and the schedule index of that day of month
			// within that year of the startDate

			int startYearIndex = TaskActivity.getYear(startDate) - 2020;

			int startMonthIndex = this.calculateMonthBeginning(TaskActivity.getMonth(startDate));

			int startDayIndex = startMonthIndex + TaskActivity.getDay(startDate) - 1;

			// get the indexes of the year, and the schedule index of that day of month
			// within that year of the endDate
			int endYearIndex = TaskActivity.getYear(endDate) - 2020;

			int endMonthIndex = this.calculateMonthBeginning(TaskActivity.getMonth(endDate));

			int endDayIndex = endMonthIndex + TaskActivity.getDay(endDate) - 1;

			// get number of days from start date to end date
			int numDaysFromStartToEnd = this.numDaysFromStartToEnd(startDate, endDate);

			// make a counter of the days we are iterating through to add tasks

			int currentNumDays = 0;

			// iterate from the year of start date to the year of the end date
			for (int currentYearIndex = startYearIndex; currentYearIndex <= endYearIndex; currentYearIndex++) {

				// iterate from the beginning of the month of the start date to either the end
				// of the year OR the end date if we're in the same year
				for (int currentDay = startDayIndex; currentDay <= (currentYearIndex == endYearIndex ? endDayIndex
						: 364); currentDay += frequency) {

					// if the current number of days can be divided by frequency, then there should
					// be an iteration of the recurring task on this day
					if (currentNumDays % frequency == 0) {

						// if there isn't a schedule conflict, add it to the schedule
						Schedule schedule = scheduleList.get(currentYearIndex).get(currentDay);
						if (!checkForConflict(task)) {
							schedule.addTask(task);

							// need to figure out how to account for tasks wrapping into other days
						}

						else {
							// Revert tasks notify user

						}

					}
					
					//increment number of current days since start date
					currentNumDays ++;
				}

			}

		}

		else if (isTransient) {
			TransientTaskActivity task = new TransientTaskActivity(name, typeString, startTime, endTime, date);
			
			// get the year, month, and day separately
			int year = TaskActivity.getYear(task.getDate());
			int month = TaskActivity.getMonth(task.getDate());
			int day = TaskActivity.getDay(task.getDate());

			// get the beginning schedule index of the month
			int beginningOfMonth = this.calculateMonthBeginning(month);

			// get the index of the schedule of the day in the month
			int specificDateIndex = beginningOfMonth + day - 1;

			// create the year sub arraylist in the schedule list if it doesn't exist
			// already
			this.createYear(year);
			
			//get the index of the year within the schedule 2d arraylist
			
			int yearIndex = year - 2020;
			
			
			//get specific schedule of day within the specific year
			Schedule schedule = scheduleList.get(yearIndex).get(specificDateIndex);
			
			//if there's no conflict, add it to the schedule
			if (!checkForConflict(task)) {
				schedule.addTask(task);
			}
			
			else {
				// error TODO --Conflict try a different time
			}

		}

		else if (isAnti) {

		}

		return false;
	}

	public boolean deleteTask(TaskActivity task) {
		return false;

	}

//	public boolean searchTask(String name) {
//		for (Schedule s : scheduleList)
//			return false;
//
//	}

	public void loadSchedule(int date) {

	}

	public void getTasksForDay(int date) {

	}

	public void getTasksForWeek(int date) {

	}

	public void getTasksForMonth(int date) {

	}

	public void getTasks() {

	}

	public Schedule returnScheduleFile(int date) {
		return null;

	}

	public boolean checkForConflict(TaskActivity task) {
		return false;

	}

	public ArrayList<ArrayList<Schedule>> getScheduleList() {
		return scheduleList;
	}

//    public ArrayList<Integer> getMonthDayList() {
//        return monthAndDayList;
//    }

	public Display getDisplay() {
		return display;
	}
}