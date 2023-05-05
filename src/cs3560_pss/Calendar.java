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
		for (int i = 0; i < month - 1; i++) {
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
		return this.numOfDaysFrom2020(endDate) - this.numOfDaysFrom2020(startDate);
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
			this.createYear(TaskActivity.getYear(endDate));
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

				// iterate from the beginning of the month of the start date OR the beginning of
				// the current year if we have wrapped to another year
				// to either the end // of the year OR the end date if we're in the same year
				for (int currentDay = (currentYearIndex == startYearIndex ? startDayIndex
						: 0); currentDay <= (currentYearIndex == endYearIndex ? endDayIndex : 364); currentDay++) {

					// if the current number of days can be divided by frequency, then there should
					// be an iteration of the recurring task on this day
					if (currentNumDays % frequency == 0) {

						// if there isn't a schedule conflict, add it to the schedule
						Schedule schedule = scheduleList.get(currentYearIndex).get(currentDay);
						if (!checkForConflict(task)) {
							schedule.addTask(task);

							// need to figure out how to account for tasks wrapping into other days
							double taskDuration = task.getDuration();

							// get the amount of time left in the day
							double timeLeft = 24 - task.getStartTime();

							// if the amount of time left in the day is less than the duration of the task
							// then we have to insert the remainder of the task into the next day(s)
							if (timeLeft < taskDuration) {

								// if the current day is the last day of the year, then the task will wrap into
								// the next year
								if (currentDay == 364) {
									// first check if the next year has a sub arraylist available
									this.createYear(currentYearIndex + 1 + 2020);
									// then we insert the remainder of that task into the beginning of the next year
									// this should also be the endTime since wrapping into another day would make
									// the start time of the remainder 0
									double durationRemainder = taskDuration - timeLeft;

									int newYear = currentYearIndex + 1 + 2020;
									int newDate = Integer.parseInt(String.valueOf(newYear) + "0101");

									// create a task with the remaining duration, the new date, and same types/name
									RecurringTaskActivity remainderTask = new RecurringTaskActivity(name, typeString, 0,
											durationRemainder, newDate, frequency, startDate, endDate);
									// insert it into the next year at the beginning
									Schedule nextSchedule = this.scheduleList.get(currentYearIndex + 1).get(0);
									nextSchedule.addTask(remainderTask);
								}

								// otherwise, create a task with the remaining duration, the new date, and same
								// types/name
								// and then insert it into the next day
								else {
									// year is still the same
									// month and day of of month may be different

									// calculate new month from number of days from beginning of year of the next
									// date (based the method on num of days from 1-365 so need to add 2 to the
									// current day index)
									int newMonth = this.calculateMonthFromDays(currentNumDays + 2);

									// calculate new day from number of days from beginning of year of the next date
									// (add 2 for same reason)
									int newDayOfMonth = this.calculateDayOfMonthFromNumDays(currentNumDays + 2);

									// format them into strings, add a 0 to the number if they are single digits

									String newMonthString = (newMonth < 10 ? "0" : "") + String.valueOf(newMonth);
									String newDayOfMonthString = (newDayOfMonth < 10 ? "0" : "")
											+ String.valueOf(newDayOfMonth);
									String currentYearString = String.valueOf(currentYearIndex + 2020);

									int newDate = Integer
											.parseInt(currentYearString + newMonthString + newDayOfMonthString);

									// get remaining duration, this still also be the new end time since the start
									// time will be 0
									double durationRemainder = taskDuration - timeLeft;

									// create a task with the remaining duration, the new date, and same types/name
									RecurringTaskActivity remainderTask = new RecurringTaskActivity(name, typeString, 0,
											durationRemainder, newDate, frequency, startDate, endDate);

								}

							}
						}

						else {
							// Revert tasks notify user

						}

					}

					// increment number of current days since start date
					currentNumDays++;
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

			// get the index of the year within the schedule 2d arraylist

			int yearIndex = year - 2020;

			// get specific schedule of day within the specific year
			Schedule schedule = scheduleList.get(yearIndex).get(specificDateIndex);

			// if there's no conflict, add it to the schedule
			if (!checkForConflict(task)) {
				schedule.addTask(task);
			}

			else {
				// error TODO --Conflict try a different time
			}

		}

		else if (isAnti) {
			// create anti task
			AntiTaskActivity task = new AntiTaskActivity(name, typeString, startTime, endTime, date);
			// check if the start time given, duration, and date matches a
			// recurringTaskActivity

			// get the position of the task's year, schedule, and index within the schedule

			Integer[] taskPositions = this.searchTaskPositionByTime(date, startTime, task.getDuration());

			// if the positions array is empty, then the task doesn't exist
			if (taskPositions.length == 0) {
				// return error
			}

			// get the schedule, task list, and task at that position
			Schedule targetSchedule = this.scheduleList.get(taskPositions[0]).get(taskPositions[1]);
			ArrayList<TaskActivity> targetTaskList = targetSchedule.getTaskList();
			TaskActivity targetTask = targetTaskList.get(taskPositions[2]);

			// if the task isn't recurring, then return an error
			if (!targetTask.isRecurringTask()) {
				// return error
			}

			// if the task is a recurring task, consider this edge case:
			// it could be potentially targeting the remainder of a recurring task that has
			// wrapped over to the next day
			// we only need to consider the possibility of this edge case when the targeted
			// task is the first task of a schedule
			// if it is, then we take a look at the schedule before the current one
			if (taskPositions[2] == 0) {

				// take a look at schedule before this one to see if there's a wrapping
				// recurring task

				// if we are at the beginning of the year, then we need to look at the last
				// schedule of the year before
				if (taskPositions[1] == 0) {
					// if we're in 2020, there is no last year, so we're fine

					// otherwise, get the last task of the last schedule of the last year
					if (TaskActivity.getYear(date) > 2020) {

						int lastYearIndex = TaskActivity.getYear(date) - 2020 - 1;

						ArrayList<TaskActivity> lastTaskList = this.scheduleList.get(lastYearIndex).get(364)
								.getTaskList();

						// if the task list is nonempty
						if (lastTaskList.size() > 0) {
							TaskActivity lastTask = lastTaskList.get(lastTaskList.size() - 1);

							// get remainder of last day starting from last task start time

							double remainderOfLastDay = 24 - lastTask.getStartTime();

							// get duration

							double lastTaskDuration = lastTask.getDuration();

							// if the last task is recurring AND the duration is larger than the remainder
							// of the day
							// then we are targeting the second half of a recurring task, so display error

							if (lastTask.isRecurringTask() && lastTaskDuration > remainderOfLastDay) {
								// return error
							}

						}
						// if it is empty, then there's no previous task to look at

					}

				}
				// if we're not on the first day of the year, then we can just take a look at
				// the schedule of the previous day
				else {
					
					//get the last day's task list
					ArrayList<TaskActivity> lastTaskList = this.scheduleList.get(taskPositions[0]).get(taskPositions[1] -1).getTaskList();
					
					// if the task list is nonempty, grab the last task
					if(lastTaskList.size() > 0)
					{
								TaskActivity lastTask = lastTaskList.get(lastTaskList.size()-1);
								
								// get remainder of last day starting from last task start time

								double remainderOfLastDay = 24 - lastTask.getStartTime();

								// get duration

								double lastTaskDuration = lastTask.getDuration();

								// if the last task is recurring AND the duration is larger than the remainder
								// of the day
								// then we are targeting the second half of a recurring task, so display error

								if (lastTask.isRecurringTask() && lastTaskDuration > remainderOfLastDay) {
									// return error
								}
					}
					
				}

			}
			//At this point we have confirmed that we're not targeting just the second half of a
			// recurring task

			
			
			// we need to consider whether the targeted recurring task is wrapping into the
			// next day or not

			
			// if it isn't, then we just replace the current target with the anti task
			if(task.getDuration() <= ( 24 - startTime)) {
				//initiaze the sub array list year if it doesn't exist
				this.createYear(TaskActivity.getYear(date));
				
				//remove task from schedule
				targetTaskList.remove(taskPositions[2]);
				
				//add task to schedule
				targetSchedule.addTask(task);
				
			}
			// if it is, then we need to first remove the current target and replace it with
			// the anti task with the same duration and time
			else {
				this.createYear(TaskActivity.getYear(date));
				
				//remove task from schedule
				targetTaskList.remove(taskPositions[2]);
				
				//add task to schedule
				targetSchedule.addTask(task);
			}
			// then we go into the next day's schedule and remove the first task with
			// replace it with an anti task of the same duration and time

			// if the start time given, duration, or date doesn't match
			// recurringTaskActivity, then return error
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

	// this method implements searching for a task based on the date and start time,
	// and return an array including the index of
	// the year in the schedule list, the index of the Schedule within that year,
	// and the index of the task within that Schedule's tasklist

	public Integer[] searchTaskPositionByTime(int date, double startTime, double duration) {

		// get the year
		int year = TaskActivity.getYear(date);
		int month = TaskActivity.getMonth(date);
		int day = TaskActivity.getDay(date);

		// get year index
		int yearIndex = year - 2020;

		// if the year is not within the schedule list, return empty array
		if (yearIndex >= this.scheduleList.size()) {
			return new Integer[] {};
		}

		// get index of day within year
		int beginningOfMonth = this.calculateMonthBeginning(month);
		int dayIndex = beginningOfMonth + day - 1;

		// get schedule and its tasks
		Schedule schedule = this.scheduleList.get(yearIndex).get(dayIndex);
		ArrayList<TaskActivity> scheduleTasks = schedule.getTaskList();

		// search for index of task with matching starttime and duration

		// iterate through task list
		for (int i = 0; i < scheduleTasks.size(); i++) {

			// get the current task
			TaskActivity currentTask = scheduleTasks.get(i);

			// if current task duration and start time matches, then return array of
			// year index, schedule index, and task index
			if (currentTask.getDuration() == duration && currentTask.getStartTime() == startTime) {

				return new Integer[] { yearIndex, dayIndex, i };
			}
		}

		// at this point we have finished searching through and have not found a
		// matching task
		// so return empty array
		return new Integer[] {};

	}

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