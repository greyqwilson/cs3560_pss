package cs3560_pss;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Calendar {

	// Assuming the Calendar starts from 2020
	ArrayList<ArrayList<Schedule>> scheduleList;
//    ArrayList<Integer>[][] monthAndDayList; //??
	Display display;

	// schedule model
	private ScheduleModel scheduleModel;

	public Calendar() {
		scheduleList = new ArrayList<ArrayList<Schedule>>();
		// initialize monthAndDayList
//		display = new Display(null);

		scheduleModel = new ScheduleModel();
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

	// calculate number of days from beginning of 2020 to this date, Jan 1 2020 = 1
	// day
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

	// calculate number of days from beginning of year
	public int numOfDaysFromYearBeginning(int date) {

		int month = TaskActivity.getMonth(date);
		int day = TaskActivity.getDay(date);
		// stores the number of days that have passed in the year up to the end of that
		// month
		int[] accumulatedDaysofMonths = { 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365 };

		int numDays = 0;

		if (month > 1) {
			numDays += accumulatedDaysofMonths[month - 2];
		}

		numDays += day;

		return numDays;

	}

	// creates a task and inserts it into a schedule's tasklist, takes in task
	// arguments
	public boolean createTask(String name, int startTime, int duration, int date, String typeString, int frequency,
			int startDate, int endDate) {

		String[] recurringTaskTypes = { "Class", "Study", "Sleep", "Exercise", "Work", "Meal" };

		String[] transientTaskTypes = { "Visit", "Shopping", "Appointment" };

		String antiTaskType = "Cancellation";

		boolean isRecurring = false;
		boolean isTransient = false;
		boolean isAnti = false;

		// check if it's recurring, transient, or anti task
		for (int i = 0; i < recurringTaskTypes.length; i++) {
			if (typeString.toLowerCase().equals(recurringTaskTypes[i].toLowerCase())) {
				isRecurring = true;
			}
		}

		for (int i = 0; i < transientTaskTypes.length; i++) {
		

			if (typeString.toLowerCase().equals(transientTaskTypes[i].toLowerCase())  ) {
				isTransient = true;
			}
		}

		if (typeString.toLowerCase().equals(antiTaskType.toLowerCase())) {
			isRecurring = true;
		}
		

		if (isRecurring) {
			
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
						
						
						String currentTaskYear = String.valueOf(currentYearIndex+ 2020) ;
						String currentTaskMonth =  (this.calculateMonthFromDays(currentDay + 1) < 10 ? "0" : "" ) +String.valueOf( this.calculateMonthFromDays(currentDay + 1));
						String currentTaskDay = ((currentDay + 1) < 10 ? "0" : "" ) +String.valueOf(currentDay + 1);
						int currentDate = Integer.parseInt( currentTaskYear + currentTaskMonth + currentTaskDay);
						
						
						
						RecurringTaskActivity task = new RecurringTaskActivity(name, typeString, startTime, duration, currentDate,
								frequency, startDate, endDate);

						// get the year, month, and day separately

						int year = TaskActivity.getYear(task.getDate());

						// create the year sub arraylist in the schedule list if it doesn't exist
						// already
						this.createYear(year);
						this.createYear(TaskActivity.getYear(endDate));
						
						
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

									// check if there's a conflict for the remainder task
									if (!checkForConflict(remainderTask)) {
										nextSchedule.addTask(remainderTask);

										// link the two tasks together

										remainderTask.setFirstHalf(task);
										task.setSecondHalf(remainderTask);

									} else {
										// otherwise there is a conflict
										// return error, revert tasks

										this.restoreList();
										return false;
									}

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

									// get the schedule of the next day
									Schedule nextSchedule = this.scheduleList.get(currentYearIndex).get(currentDay + 1);

									// check if there's a conflict for the remainder task
									if (!checkForConflict(remainderTask)) {
										nextSchedule.addTask(remainderTask);

										// link the two tasks together
										remainderTask.setFirstHalf(task);
										task.setSecondHalf(remainderTask);

									} else {
										// otherwise there is a conflict
										// return error, revert tasks
										this.restoreList();
										return false;
									}
								}

							}
							
							
							
						}

						else {
							// Revert tasks notify user
							this.restoreList();
							return false;
						}

					}

					// increment number of current days since start date
					currentNumDays++;
				}

			}

		}

		else if (isTransient) {
			TransientTaskActivity task = new TransientTaskActivity(name, typeString, startTime, duration, date);

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

				// check if task wraps to the next day

				if (duration > (24 - startTime)) {
					
					// if the current day is the last day of the year, december 31st
					if (month == 12 && day == 31) {

						// get the next year
						int nextYear = year + 1;
						// make sure the subarray list in the schedule list is initialized
						this.createYear(nextYear);

						// get index of next year
						int nextYearIndex = yearIndex + 1;

						// get new date, Jan 1st of next year
						int nextDate = Integer.parseInt(String.valueOf(nextYear) + "0101");

						// get duration remainder of task
						double durationRemainder = duration - (24 - startTime);

						// get Schedule of next year first day
						Schedule nextSchedule = this.scheduleList.get(nextYearIndex).get(0);

						// create remainder task, should start from beginning of day
						TransientTaskActivity remainderTask = new TransientTaskActivity(name, typeString, 0,
								durationRemainder, nextDate);

						// check for conflict, if it doesn't conflict add the task
						if (!checkForConflict(remainderTask)) {
							nextSchedule.addTask(remainderTask);
							// link the two tasks together

							remainderTask.setFirstHalf(task);
							task.setSecondHalf(remainderTask);
						}

						// otherwise, return error and revert tasks
						else {
							this.restoreList();
							return false;
						}

					}

					// if the current day is any other day, meaning we're in the same year
					else {

						// same year index

						// get number of days from beginning of year of next day
						int numberOfDaysOfNextDay = this.numOfDaysFromYearBeginning(date) + 1;

						// get new month
						int newMonth = this.calculateMonthFromDays(numberOfDaysOfNextDay);

						// get new day of month

						int newDay = this.calculateDayOfMonthFromNumDays(numberOfDaysOfNextDay);

						// new month string, add 0 to beginning if single digit
						String monthString = (newMonth < 10 ? "0" : "") + String.valueOf(newMonth);

						// new day string, add 0 to beginning if single digit
						String dayString = (newDay < 10 ? "0" : "") + String.valueOf(newDay);
						// get new date
						int newDate = Integer.parseInt(String.valueOf(year) + monthString + dayString);

						// get duration remainder of task
						double durationRemainder = duration - (24 - startTime);

						// get Schedule of next year first day
						Schedule nextSchedule = this.scheduleList.get(yearIndex + 1).get(0);

						// create remainder task, should start from beginning of day
						TransientTaskActivity remainderTask = new TransientTaskActivity(name, typeString, 0,
								durationRemainder, newDate);

						// check for conflict, if it doesn't conflict add the task
						if (!checkForConflict(remainderTask)) {
							nextSchedule.addTask(remainderTask);

							// link the two tasks together

							remainderTask.setFirstHalf(task);
							task.setSecondHalf(remainderTask);
						}

						// otherwise, return error and revert tasks
						else {
							this.restoreList();
							return false;
						}

					}

				}
			}

			else {
				// error TODO --Conflict try a different time
				this.restoreList();
				return false;
			}

		}

		else if (isAnti) {
			// create anti task
			AntiTaskActivity task = new AntiTaskActivity(name, typeString, startTime, duration, date);
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
				// return error, revert tasks
				this.restoreList();
				return false;
			}

			// if the target task is a recurring task, then we need to check whether its the
			// second half of a recurring task
			// if it is, display error and revert tasks
			if (targetTask.getFirstHalf() != null) {
				// display error
				this.restoreList();
				return false;
			}

			// At this point we have confirmed that we're not targeting just the second half
			// of a recurring task

			// we need to consider whether the targeted recurring task is wrapping into the
			// next day or not

			// if it isn't, then we just replace the current target with the anti task
			if (task.getDuration() <= (24 - startTime)) {

				// remove task from schedule
				targetSchedule.deleteTask(targetTask);

				// link anti task to recurring task removed
				task.setRecurring((RecurringTaskActivity) targetTask);

				// add task to schedule
				targetSchedule.addTask(task);

			}
			// if it is, then we need to first remove the current target and replace it with
			// the anti task with the same duration and time
			else {

				// remove task from schedule
				targetSchedule.deleteTask(targetTask);
				// link anti task to recurring task removed
				task.setRecurring((RecurringTaskActivity) targetTask);

				// add task to schedule
				targetSchedule.addTask(task);

				// get the second half of the target task

				TaskActivity secondHalf = targetTask.getSecondHalf();

				// get its year, month, day
				int secondDate = secondHalf.getDate();

				int secondYear = TaskActivity.getYear(secondDate);
				int secondMonth = TaskActivity.getMonth(secondDate);
				int secondDay = TaskActivity.getDay(secondDate);

				// get index of year, and day
				int secondYearIndex = secondYear - 2020;
				int secondBeginningMonth = this.calculateMonthBeginning(secondMonth);
				int secondDayIndex = secondBeginningMonth + secondDay - 1;

				// get the second half's schedule
				Schedule secondSchedule = this.scheduleList.get(secondYearIndex).get(secondDayIndex);

				// create anti task with remainder on the next day
				AntiTaskActivity remainderTask = new AntiTaskActivity(name, typeString, 0, secondHalf.getDuration(),
						secondDate);

				// link two anti task halves to each other
				remainderTask.setFirstHalf(task);
				task.setSecondHalf(remainderTask);

				// link the second anti task to the second half of the recurring task
				remainderTask.setRecurring((RecurringTaskActivity) secondHalf);

				// remove the second recurring half
				secondSchedule.deleteTask(secondHalf);

				// add the second anti half to the schedule
				secondSchedule.addTask(remainderTask);

			}

		} 
		//if the types dont match anything
		else {
			return false;
		}

		//at this point the tasks have been added successfully
		return true;
	}

	// takes in object reference to task, deletes task based on its type
	public boolean deleteTask(TaskActivity task) {

		TaskActivity removeTask = task;

		// if the input task is the second half of a task, then update the reference to
		// the first half
		if (removeTask.getFirstHalf() != null) {
			removeTask = removeTask.getFirstHalf();
		}

		boolean isRecurring = task.isRecurringTask();
		boolean isAnti = task.isAntiTask();
		boolean isTransient = task.isTransientTask();

		/*-----this is used only for anti and transient tasks------------------*/
		// get year, month, day of task
		int year = TaskActivity.getYear(removeTask.getDate());
		int month = TaskActivity.getMonth(removeTask.getDate());
		int day = TaskActivity.getDay(removeTask.getDate());

		// get year index, day index

		int yearIndex = year - 2020;
		int beginningOfMonth = this.calculateMonthBeginning(month);
		int dayIndex = beginningOfMonth + day - 1;

		// find the schedule of the task
		Schedule schedule = this.scheduleList.get(yearIndex).get(dayIndex);

		/*----------------------------------------------------------------------*/

		// if it's recurring, then we need to remove all the iterations
		if (isRecurring) {

			// first we need to make sure that the recurring task we've been passed is the
			// second half of a
			// recurring task or not

			// if it is, then we need to change the reference to its first task half
			// get frequency
			int frequency = ((RecurringTaskActivity) removeTask).getFrequency();

			int startDate = ((RecurringTaskActivity) removeTask).getStartDate();
			int endDate = ((RecurringTaskActivity) removeTask).getEndDate();

			// get year, month, day of start date
			int startYear = TaskActivity.getYear(startDate);
			int startMonth = TaskActivity.getMonth(startDate);
			int startDay = TaskActivity.getDay(startDate);

			// get year, month, day of end date
			int endYear = TaskActivity.getYear(endDate);
			int endMonth = TaskActivity.getMonth(endDate);
			int endDay = TaskActivity.getDay(endDate);

			// get the indexes of the year, and the schedule index of that day of month
			// within that year of the startDate

			int startYearIndex = startYear - 2020;

			int startMonthIndex = this.calculateMonthBeginning(startMonth);

			int startDayIndex = startMonthIndex + startDay - 1;

			// get the indexes of the year, and the schedule index of that day of month
			// within that year of the endDate
			int endYearIndex = endYear - 2020;

			int endMonthIndex = this.calculateMonthBeginning(endMonth);

			int endDayIndex = endMonthIndex + endDay - 1;

			// make a counter of the days we are iterating through to remove recurring task
			// iterations

			int currentNumDays = 0;

			// iterate from the year of start date to the year of the end date
			for (int currentYearIndex = startYearIndex; currentYearIndex <= endYearIndex; currentYearIndex++) {

				// iterate from the beginning of the month of the start date OR the beginning of
				// the current year if we have wrapped to another year
				// to either the end // of the year OR the end date if we're in the same year
				for (int currentDay = (currentYearIndex == startYearIndex ? startDayIndex
						: 0); currentDay <= (currentYearIndex == endYearIndex ? endDayIndex : 364); currentDay++) {

					// if the current number of days can be divided by frequency, then there should
					// be an iteration of the recurring task OR an anti task on this day
					if (currentNumDays % frequency == 0) {

						// get the current Schedule
						Schedule currentSchedule = scheduleList.get(currentYearIndex).get(currentDay);
						// get task list
						ArrayList<TaskActivity> recurringTaskList = currentSchedule.getTaskList();

						// search for a Recurring Task with the same name, type, start time duration,
						// and its position
						int recurringPosition = -1;

						// Search for a AntiTask with the same start time and duration, and its position
						int antiPosition = -1;

						// start search
						for (int i = 0; i < recurringTaskList.size(); i++) {

							TaskActivity currentTask = recurringTaskList.get(i);

							// if current task matches recurring search, record position
							if (currentTask.isRecurringTask() && currentTask.getName() == removeTask.getName()
									&& currentTask.getType() == removeTask.getType()
									&& currentTask.getStartTime() == removeTask.getStartTime()
									&& currentTask.getDuration() == removeTask.getDuration()) {

								recurringPosition = i;

							}

							// if current task matches anti search, record position

							if (currentTask.isAntiTask() && currentTask.getStartTime() == removeTask.getStartTime()
									&& currentTask.getDuration() == removeTask.getDuration()) {

								antiPosition = i;

							}

						}

						// get the references to the current recurring or anti task, and remove them
						// from list
						TaskActivity currentRecurring = null;
						TaskActivity currentAnti = null;

						if (recurringPosition > -1) {
							currentRecurring = recurringTaskList.get(recurringPosition);
							recurringTaskList.remove(recurringPosition);

						}

						if (antiPosition > -1) {
							currentAnti = recurringTaskList.get(antiPosition);
							recurringTaskList.remove(antiPosition);

						}

						// check if the task wraps to the next day or not
						// if it does, go to the next day and remove

						if (removeTask.getDuration() > (24 - removeTask.getStartTime())) {

							// get the second half of the current task

							TaskActivity secondHalf = null;

							if (recurringPosition > -1) {
								secondHalf = currentRecurring.getSecondHalf();
							}
							if (antiPosition > -1) {
								secondHalf = currentAnti.getSecondHalf();
							}

							// get its year, month, day
							int secondDate = secondHalf.getDate();

							int secondYear = TaskActivity.getYear(secondDate);
							int secondMonth = TaskActivity.getMonth(secondDate);
							int secondDay = TaskActivity.getDay(secondDate);

							// get index of year, and day
							int secondYearIndex = secondYear - 2020;
							int secondBeginningMonth = this.calculateMonthBeginning(secondMonth);
							int secondDayIndex = secondBeginningMonth + secondDay - 1;

							// get the second half's schedule
							Schedule secondSchedule = this.scheduleList.get(secondYearIndex).get(secondDayIndex);

							// remove the second half
							secondSchedule.deleteTask(secondHalf);

						}

					}

					// increment number of days
					currentNumDays++;
				}
			}
		}
		// if it's anti task, then we need to remove the anti task and add back in the
		// recurring task it replaced

		// if transient, then we just need to remove it
		if (isAnti || isTransient) {

			// remove the task from the schedule
			schedule.deleteTask(removeTask);

			// if the task is anti, get the recurring task it replaced
			RecurringTaskActivity recurringLink = null;

			if (isAnti) {
				recurringLink = ((AntiTaskActivity) removeTask).getRecurring();

				// check for conflict, if there's a conflict then there was some transient tasks
				// overlapping
				// with the anti task, revert changes

				// otherwise, add the recurring task back in its place
				if (!checkForConflict(recurringLink)) {
					schedule.addTask(recurringLink);
				} else {
					// display error, revert changes
					this.restoreList();
					return false;
				}
			}

			// if task wraps to the next day, then we also need to remove that
			// remainder task
			if (removeTask.getDuration() > (24 - removeTask.getStartTime())) {

				// get the second half of the task

				TaskActivity secondHalf = removeTask.getSecondHalf();

				// get its year, month, day
				int secondDate = secondHalf.getDate();

				int secondYear = TaskActivity.getYear(secondDate);
				int secondMonth = TaskActivity.getMonth(secondDate);
				int secondDay = TaskActivity.getDay(secondDate);

				// get index of year, and day
				int secondYearIndex = secondYear - 2020;
				int secondBeginningMonth = this.calculateMonthBeginning(secondMonth);
				int secondDayIndex = secondBeginningMonth + secondDay - 1;

				// get the second half's schedule
				Schedule secondSchedule = this.scheduleList.get(secondYearIndex).get(secondDayIndex);

				// remove second half from second schedule
				secondSchedule.deleteTask(secondHalf);

				// if the current task is an anti task, then we need to put back in the
				// recurring half as well
				TaskActivity secondRecurring;
				if (isAnti) {
					secondRecurring = recurringLink.getSecondHalf();
					// check for conflict, if there's a conflict then there was some transient tasks
					// overlapping
					// with the anti task, revert changes

					// otherwise, add the recurring task back in its place
					if (!checkForConflict(secondRecurring)) {
						schedule.addTask(secondRecurring);
					} else {
						// display error, revert changes
						this.restoreList();
						return false;
					}
				}

			}

		}

		//at this point the tasks have been deleted successfully
		return true;

	}

	// searches for task based on name
	public TaskActivity searchTask(String name) {
		for (ArrayList<Schedule> year : this.scheduleList) {

			for (Schedule day : year) {

				for (TaskActivity task : day.getTaskList()) {

					if (task.getName() == name) {
						return task;
					}
				}
			}
		}

		return null;

	}

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

		// search for index of task with matching start time and duration

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

	// returns array of tasks for a day
	public TaskActivity[] getTasksForDay(int date) {

		// get year, month, day
		int year = TaskActivity.getYear(date);
		int month = TaskActivity.getMonth(date);
		int day = TaskActivity.getDay(date);

		// if the year is below 2020, return null
		if (year < 2020) {
			return null;
		}

		// get index of year and day
		int yearIndex = year - 2020;
		int beginningOfMonth = this.calculateMonthBeginning(month);
		int dayIndex = beginningOfMonth + day - 1;

		// this is for in case they reference a year after 2020 but it doesn't exist
		this.createYear(year);

		
		//get task list
		ArrayList<TaskActivity> taskList = this.scheduleList.get(yearIndex).get(dayIndex).getTaskList();
		// return the task list of the day, format into array
		TaskActivity[] taskArray = new TaskActivity[taskList.size()];
		
		
		for(int i = 0; i <  taskList.size(); i++) {
			taskArray[i]  = taskList.get(i);
		}
		
		return taskArray;

	}

	// returns arraylist of arraylists containing tasks for a week
	public Schedule[] getTasksForWeek(int date) {
		//assume that the start of each year resets the day of week to sunday
		//get number of days from beginning of year
		int numOfDays = this.numOfDaysFromYearBeginning(date);
		//get the num of days of the ending of the week before the current one
		//this is also the index of the beginning of the current week
		int lastWeekEnding = numOfDays - numOfDays%7;
		
		//get index of current year
		int yearIndex = TaskActivity.getYear(date) - 2020;
		System.out.println(yearIndex);
		//Array of schedules to store week's tasks
		//is the size of the remainder of the year or 7 days if there's enough days left
		Schedule[] weekSchedules = new Schedule[((lastWeekEnding + 7) >= 365 ? 365 - lastWeekEnding : 7)];
		//current empty slot in array
		int arrPointer = 0;

		//gets tasks for up to the end of the current week or end of the year if there's not enough for 7 days
		for(int i = lastWeekEnding; i < ((lastWeekEnding + 7) >= 365 ? 365 : lastWeekEnding + 7); i++) {
			weekSchedules[arrPointer] = this.scheduleList.get(yearIndex).get(i);
			arrPointer++;
		}
		
		return weekSchedules;
		
		

	}

	// returns schedule array of month
	public Schedule[] getTasksForMonth(int date) {
		
		
		// get year, month, day
		int year = TaskActivity.getYear(date);
		int month = TaskActivity.getMonth(date);

		// if the year is below 2020, return null
		if (year < 2020) {
			return null;
		}

		// get index of year and beginning of month
		int yearIndex = year - 2020;
		int beginningOfMonth = this.calculateMonthBeginning(month);

		// num of days in each month
		int[] monthDays = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

		
		//month schedule array, size is of the num of days in month
		Schedule[] monthSchedules = new Schedule[monthDays[month-1]];
		

		// iterate from Schedules from beginning of month to end of month

		for (int i = 0; i < monthDays[month - 1]; i++) {

			monthSchedules[i] =this.scheduleList.get(yearIndex).get(beginningOfMonth + i);
		}

		return monthSchedules;
	}

	// ? is this needed?
	public void getTasks() {
	}

	public Schedule returnScheduleFile(int date) {
		return null;
	}

	// checks for conflict if we were to add the input task
	public boolean checkForConflict(TaskActivity task) {
		boolean isRecurring = task.isRecurringTask();
		boolean isAnti = task.isAntiTask();
		boolean isTransient = task.isTransientTask();

		// get date
		int date = task.getDate();

		// get year, month, day
		int year = TaskActivity.getYear(date);
		int month = TaskActivity.getMonth(date);
		int day = TaskActivity.getDay(date);

		// get task's start time and duration, and end time based on duration

		double taskDuration = task.getDuration();
		double taskStart = task.getStartTime();
		double taskEnd = taskStart + taskDuration;

		// get index of year and day
		int yearIndex = year - 2020;
		int beginningOfMonth = this.calculateMonthBeginning(month);
		int dayIndex = beginningOfMonth + day - 1;

		// get the tasklist of the schedule of the day
		Schedule schedule = this.scheduleList.get(yearIndex).get(dayIndex);

		ArrayList<TaskActivity> taskList = schedule.getTaskList();

		// recurring tasks can't overlap with other tasks
		// anti tasks can technically overlap with transient tasks,
		// but they are added right after a recurring task is removed,
		// so there shouldn't be any overlapping regardless
		if (isRecurring || isAnti) {

			// iterate through tasklist
			for (int i = 0; i < taskList.size(); i++) {

				// get start and end times of each task
				TaskActivity currentTask = taskList.get(i);
				double currentStart = currentTask.getStartTime();
				double currentEnd = currentStart + currentTask.getDuration();

				// if the task to be added's start time or end time is in between
				// any of the tasks start time and end time, or vice versa,
				// there's a conflict

				// the task can start when another tasks ends,
				// or end when another tasks starts

				// if new task's start or end time is in between another task's
				if ((taskStart >= currentStart && taskStart < currentEnd)
						|| (taskEnd > currentStart && taskEnd <= currentEnd)

						// or vice versa
						|| (currentStart >= taskStart && currentStart < taskEnd)
						|| (currentEnd > taskStart && currentEnd <= taskEnd)) {
					return true;
				}

			}

		}

		// can overlap with anti tasks
		else if (isTransient) {

			// iterate through tasklist
			for (int i = 0; i < taskList.size(); i++) {

				// get start and end times of each task
				TaskActivity currentTask = taskList.get(i);
				double currentStart = currentTask.getStartTime();
				double currentEnd = currentStart + currentTask.getDuration();

				// if current task is transient or recurring,
				// and the task to be added's start time or end time is in between
				// any of the tasks start time and end time, there's a conflict

				// the task can start when another tasks ends,
				// or end when another tasks starts
				if ((currentTask.isRecurringTask() || currentTask.isTransientTask())
						// if new task's start or end time is in between another task's
						&& (taskStart >= currentStart && taskStart < currentEnd)
						|| (taskEnd > currentStart && taskEnd <= currentEnd)

						// or vice versa
						|| (currentStart >= taskStart && currentStart < taskEnd)
						|| (currentEnd > taskStart && currentEnd <= taskEnd)) {
					return true;
				}

				// don't need to consider anti tasks since we can overlap, just need to consider
				// recurring/transient tasks

			}
		}

		// we have finished looking through the tasks in the task list and
		// have determined no conflict
		return false;

	}

	// method that copies tasks over
	public void restoreList() {
		// stores reference to schedule model's schedule list
		ArrayList<ArrayList<Schedule>> update = this.scheduleModel.getScheduleList();

		// new copy of schedule list
		ArrayList<ArrayList<Schedule>> copy = new ArrayList<ArrayList<Schedule>>();

		// iterate through years of schedule model's list
		for (ArrayList<Schedule> year : update) {

			// add new year to copy
			copy.add(new ArrayList<Schedule>());
			ArrayList<Schedule> currentYearCopy = copy.get(copy.size() - 1);

			// iterate through year's schedules
			for (Schedule schedule : year) {

				// add new schedule for current year
				currentYearCopy.add(new Schedule(schedule.getDate()));

				// current schedule copy
				Schedule currentScheduleCopy = currentYearCopy.get(currentYearCopy.size() - 1);
				// get tasklist
				ArrayList<TaskActivity> taskList = schedule.getTaskList();

				for (TaskActivity currentTask : taskList) {

					// new task copy
					TaskActivity taskCopy = null;

					// get parameters
					int date = currentTask.getDate();
					String name = currentTask.getName();
					String type = currentTask.getType();
					double duration = currentTask.getDuration();
					double startTime = currentTask.getStartTime();

					// if it's recurring, get the rest of the parameters
					if (currentTask.isRecurringTask()) {
						int startDate = ((RecurringTaskActivity) currentTask).getStartDate();
						int endDate = ((RecurringTaskActivity) currentTask).getEndDate();
						int frequency = ((RecurringTaskActivity) currentTask).getFrequency();

						// create recurring task copy
						taskCopy = new RecurringTaskActivity(name, type, startTime, duration, date, frequency,
								startDate, endDate);

						// add it to copy schedule
						currentScheduleCopy.addTask(taskCopy);

					}

					// if anti task

					if (currentTask.isAntiTask()) {
						// create anti task
						taskCopy = new AntiTaskActivity(name, type, startTime, duration, date);

						// create copy of recurring task link, link it to copy

						TaskActivity currentTaskRecurring = ((AntiTaskActivity) taskCopy).getRecurring();

						// get extra params
						String recurName = currentTaskRecurring.getName();
						String recurType = currentTaskRecurring.getType();
						double recurDuration = currentTaskRecurring.getDuration();
						double recurStartTime = currentTaskRecurring.getStartTime();
						int recurDate = currentTaskRecurring.getDate();
						int startDate = ((RecurringTaskActivity) currentTaskRecurring).getStartDate();
						int endDate = ((RecurringTaskActivity) currentTaskRecurring).getEndDate();
						int frequency = ((RecurringTaskActivity) currentTaskRecurring).getFrequency();

						// create copy
						RecurringTaskActivity recurringCopy = new RecurringTaskActivity(recurName, recurType,
								recurStartTime, recurDuration, recurDate, frequency, startDate, endDate);

						// link it
						((AntiTaskActivity) currentTask).setRecurring(recurringCopy);

						// add the copy to schedule
						currentScheduleCopy.addTask(taskCopy);

					}

					// if transient()
					if (currentTask.isTransientTask()) {

						// create copy
						taskCopy = new TransientTaskActivity(name, type, startTime, duration, date);

						// add it to list
						currentScheduleCopy.addTask(taskCopy);
					}

					// we need to consider the linking of two halves of tasks
					// if we encounter the second half of a task, then we will have created the
					// first half already
					// so we need to retrieve the first half, and link it and the current task copy
					// together

					if (currentTask.getFirstHalf() != null) {

						// get firstHalf
						TaskActivity firstHalf = currentTask.getFirstHalf();

						// get first half's basic parameter fields to search
						int firstHalfDate = firstHalf.getDate();
						String firstHalfName = firstHalf.getName();
						String firstHalfType = firstHalf.getType();
						double firstHalfDuration = firstHalf.getDuration();
						double firstHalfStartTime = firstHalf.getStartTime();

						// get the schedule index

						int firstHalfYear = TaskActivity.getYear(firstHalfDate);
						int firstHalfYearIndex = firstHalfYear - 2020;

						int firstHalfMonthBeginning = calculateMonthBeginning(TaskActivity.getMonth(firstHalfDate));
						int firstHalfDayIndex = firstHalfMonthBeginning + TaskActivity.getDay(firstHalfDate) - 1;

						// get schedule
						Schedule firstHalfSchedule = this.scheduleList.get(firstHalfYearIndex).get(firstHalfDayIndex);

						// get taskList
						ArrayList<TaskActivity> firstHalfTaskList = firstHalfSchedule.getTaskList();

						// store reference to first half
						TaskActivity firstHalfCopy = null;

						for (TaskActivity searchTask : firstHalfTaskList) {

							// if the tasks basic parameters match the first half
							if (searchTask.getDate() == firstHalfDate && searchTask.getName() == firstHalfName
									&& searchTask.getType() == firstHalfType
									&& searchTask.getDuration() == firstHalfDuration
									&& searchTask.getStartTime() == firstHalfStartTime

									// and if the current search task's type matches the first half task's
									&& ((firstHalf.isTransientTask() && searchTask.isTransientTask())
											|| (firstHalf.isRecurringTask() && searchTask.isRecurringTask())
											|| (firstHalf.isAntiTask() && searchTask.isAntiTask()))) {

								/// store reference and link the two halves
								firstHalfCopy = searchTask;

								firstHalfCopy.setSecondHalf(taskCopy);
								taskCopy.setFirstHalf(firstHalfCopy);

							}

						}

						// if it is anti task, then we also need to grab the recurring tasks that the
						// first and second half are linked to
						// and link those together
						if (firstHalfCopy.isAntiTask()) {

							// get first half's recurring task
							RecurringTaskActivity firstHalfCopyRecurring = ((AntiTaskActivity) firstHalfCopy)
									.getRecurring();

							// get second half's recurring task
							RecurringTaskActivity secondHalfCopyRecurring = ((AntiTaskActivity) taskCopy)
									.getRecurring();

							firstHalfCopyRecurring.setSecondHalf(secondHalfCopyRecurring);
							secondHalfCopyRecurring.setFirstHalf(firstHalfCopyRecurring);

						}

					}

				}
			}
		}

		// set the schedule list to point to copy
		this.scheduleList = copy;
	}

	// returns schedule list
	public ArrayList<ArrayList<Schedule>> getScheduleList() {
		return scheduleList;
	}

	public Display getDisplay() {
		return display;
	}
	public void setDisplay(Display display) {
		this.display = display;
	}
	

	public ScheduleModel getScheduleModel() {
		return this.scheduleModel;
	}

	public void setScheduleModel(ScheduleModel scheduleModel) {
		this.scheduleModel = scheduleModel;
	}
}