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
			RecurringTaskActivity task = new RecurringTaskActivity(name, typeString, startTime, duration, date,
					frequency, startDate, endDate);

			// get the year, month, and day separately

			int year = TaskActivity.getYear(task.getDate());
//			int month = TaskActivity.getMonth(task.getDate());
//			int day = TaskActivity.getDay(task.getDate());
//
//			// get the beginning schedule index of the month
//			int beginningOfMonth = this.calculateMonthBeginning(month);

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

									// check if there's a conflict for the remainder task
									if (!checkForConflict(remainderTask)) {
										nextSchedule.addTask(remainderTask);

									} else {
										// otherwise there is a conflict
										// return error, revert tasks
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

									} else {
										// otherwise there is a conflict
										// return error, revert tasks
									}
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
						}

						// otherwise, return error and revert tasks
						else {

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
						}

						// otherwise, return error and revert tasks
						else {

						}

					}

				}
			}

			else {
				// error TODO --Conflict try a different time
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

					// get the last day's task list
					ArrayList<TaskActivity> lastTaskList = this.scheduleList.get(taskPositions[0])
							.get(taskPositions[1] - 1).getTaskList();

					// if the task list is nonempty, grab the last task
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

				}

			}
			// At this point we have confirmed that we're not targeting just the second half
			// of a
			// recurring task

			// we need to consider whether the targeted recurring task is wrapping into the
			// next day or not

			// if it isn't, then we just replace the current target with the anti task
			if (task.getDuration() <= (24 - startTime)) {
				// initiaze the sub array list year if it doesn't exist
				this.createYear(TaskActivity.getYear(date));

				// remove task from schedule
				targetTaskList.remove(taskPositions[2]);

				// link anti task to recurring task removed
				task.setRecurring((RecurringTaskActivity) targetTask);

				// add task to schedule
				targetSchedule.addTask(task);

			}
			// if it is, then we need to first remove the current target and replace it with
			// the anti task with the same duration and time
			else {
				this.createYear(TaskActivity.getYear(date));

				// remove task from schedule
				targetTaskList.remove(taskPositions[2]);

				// link anti task to recurring task removed
				task.setRecurring((RecurringTaskActivity) targetTask);

				// add task to schedule
				targetSchedule.addTask(task);

				// then we go into the next day's schedule and remove the first task with
				// replace it with an anti task of the same duration and time

				// if we're at the end of the year, then we need to go into the next year, and
				// remove the first task of the first day

				if (taskPositions[1] == 364) {

					// get the index of the next year
					int nextYearIndex = taskPositions[0] + 1;

					// get the date of the first day of next year
					int newDate = Integer.parseInt(String.valueOf(nextYearIndex + 2020) + "0101");

					// get the schedule of the first day of the next year

					Schedule nextSchedule = this.scheduleList.get(nextYearIndex).get(0);

					// get task list of next schedule
					ArrayList<TaskActivity> nextTaskList = nextSchedule.getTaskList();

					// store reference to second half of recurring task to be removed
					RecurringTaskActivity secondRecurringHalf = (RecurringTaskActivity) nextTaskList.get(0);
					// remove the first task
					nextTaskList.remove(0);

					// create new anti task with the remainder of the duration
					double remainderDuration = task.getDuration() - (24 - startTime);

					// since it's wrapping into the next day, start time is 0
					// and it will have a new date
					AntiTaskActivity remainderTask = new AntiTaskActivity(name, typeString, 0, remainderDuration,
							newDate);

					// link the new anti task to the remainder recurring talk
					remainderTask.setRecurring(secondRecurringHalf);

					// add it to the schedule
					nextSchedule.addTask(remainderTask);
				}
				// otherwise, we just go to the next day

				else {

					// year is still the same
					// month and day of of month may be different

					// calculate new month from number of days from beginning of year of the next
					// date (based the method on num of days from 1-365 so need to add 2 to the
					// current day index)
					int newMonth = this.calculateMonthFromDays(taskPositions[1] + 2);

					// calculate new day from number of days from beginning of year of the next date
					// (add 2 for same reason)
					int newDayOfMonth = this.calculateDayOfMonthFromNumDays(taskPositions[1] + 2);

					// format them into strings, add a 0 to the number if they are single digits

					String newMonthString = (newMonth < 10 ? "0" : "") + String.valueOf(newMonth);
					String newDayOfMonthString = (newDayOfMonth < 10 ? "0" : "") + String.valueOf(newDayOfMonth);
					String currentYearString = String.valueOf(taskPositions[0] + 2020);

					int newDate = Integer.parseInt(currentYearString + newMonthString + newDayOfMonthString);

					// get remaining duration, this still also be the new end time since the start
					// time will be 0
					double durationRemainder = duration - (24 - startTime);

					// since it's wrapping into the next day, start time is 0
					// and it will have a new date
					AntiTaskActivity remainderTask = new AntiTaskActivity(name, typeString, 0, durationRemainder,
							newDate);

					// get the next day's schedule

					Schedule nextSchedule = this.scheduleList.get(taskPositions[0]).get(taskPositions[1] + 1);

					// get the next day's taskList
					ArrayList<TaskActivity> nextTaskList = nextSchedule.getTaskList();

					// store reference to second half of recurring task to be removed
					RecurringTaskActivity secondRecurringHalf = (RecurringTaskActivity) nextTaskList.get(0);

					// link the new anti task to the remainder recurring talk
					remainderTask.setRecurring(secondRecurringHalf);

					// remove the first task
					nextTaskList.remove(0);

					// add the new remainder task
					nextSchedule.addTask(remainderTask);
				}

			}

		}

		return false;
	}

	public boolean deleteTask(TaskActivity task) {

		boolean isRecurring = task.isRecurringTask();
		boolean isAnti = task.isAntiTask();
		boolean isTransient = task.isTransientTask();

		// if it's recurring, then we need to remove all the iterations
		if (isRecurring) {

			RecurringTaskActivity removeTask = (RecurringTaskActivity) task;

			// first we need to make sure that the recurring task we've been passed is the
			// second half of a
			// recurring task or not

			// if it is, then we need to change the reference to its first task half

			// get positions of current task
			Integer[] taskPositions = this.searchTaskPositionByTime(removeTask.getDate(), removeTask.getStartTime(),
					removeTask.getDuration());

			// get the schedule, task list of the task to be removed
			Schedule targetSchedule = this.scheduleList.get(taskPositions[0]).get(taskPositions[1]);
			ArrayList<TaskActivity> targetTaskList = targetSchedule.getTaskList();

			// we only need to consider the possibility of the current task being the second
			// half if the
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
					if (TaskActivity.getYear(removeTask.getDate()) > 2020) {

						int lastYearIndex = TaskActivity.getYear(removeTask.getDate()) - 2020 - 1;

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
							// then the task that was passed into the delete method was the second half
							// so set the task to be removed's reference to the first half

							if (lastTask.isRecurringTask() && lastTaskDuration > remainderOfLastDay) {
								removeTask = (RecurringTaskActivity) lastTask;
							}

						}
						// if the list is empty, then there's no previous task to look at

					}

				}
				// if we're not on the first day of the year, then we can just take a look at
				// the schedule of the previous day
				else {

					// get the last day's task list
					ArrayList<TaskActivity> lastTaskList = this.scheduleList.get(taskPositions[0])
							.get(taskPositions[1] - 1).getTaskList();

					// if the task list is nonempty, grab the last task
					if (lastTaskList.size() > 0) {
						TaskActivity lastTask = lastTaskList.get(lastTaskList.size() - 1);

						// get remainder of last day starting from last task start time

						double remainderOfLastDay = 24 - lastTask.getStartTime();

						// get duration

						double lastTaskDuration = lastTask.getDuration();

						// if the last task is recurring AND the duration is larger than the remainder
						// of the day
						// then the task that was passed into the delete method was the second half
						// so set the task to be removed's reference to the first half

						if (lastTask.isRecurringTask() && lastTaskDuration > remainderOfLastDay) {
							removeTask = (RecurringTaskActivity) lastTask;
						}
					}

				}

			}

			// get frequency
			int frequency = removeTask.getFrequency();

			int startDate = removeTask.getStartDate();
			int endDate = removeTask.getEndDate();

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

			int endDayIndex = endMonthIndex + endMonth - 1;

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
						Schedule schedule = scheduleList.get(currentYearIndex).get(currentDay);
						// get task list
						ArrayList<TaskActivity> taskList = schedule.getTaskList();

						// search for a Recurring Task with the same name, type, start time duration,
						// and its position
						RecurringTaskActivity searchRecurring = null;
						int recurringPosition = -1;

						// Search for a AntiTask with the same start time and duration, and its position
						AntiTaskActivity searchAnti = null;
						int antiPosition = -1;

						// start search
						for (int i = 0; i < taskList.size(); i++) {

							TaskActivity currentTask = taskList.get(i);

							// if current task matches recurring search, record reference and position
							if (currentTask.isRecurringTask() && currentTask.getName() == removeTask.getName()
									&& currentTask.getType() == removeTask.getType()
									&& currentTask.getStartTime() == removeTask.getStartTime()
									&& currentTask.getDuration() == removeTask.getDuration()) {

								searchRecurring = (RecurringTaskActivity) currentTask;
								recurringPosition = i;

							}

							// if current task matches anti search, record reference and position

							if (currentTask.isAntiTask() && currentTask.getStartTime() == removeTask.getStartTime()
									&& currentTask.getDuration() == removeTask.getDuration()) {

								searchAnti = (AntiTaskActivity) currentTask;
								antiPosition = i;

							}

						}

						// remove the recurring task or anti task, whichever exists
						if (antiPosition > -1) {
							taskList.remove(antiPosition);
						} else if (recurringPosition > -1) {
							taskList.remove(recurringPosition);
						}

						// check if the task wraps to the next day or not
						// if it does, go to the next day and remove

						if (removeTask.getDuration() > (24 - removeTask.getStartTime())) {

							// if we're on the last day of the year then we need to go to the next year's
							// first day
							if (currentDay == 364) {

								// get next year's first day's tasklist

								ArrayList<TaskActivity> nextTaskList = this.scheduleList.get(currentYearIndex + 1)
										.get(0).getTaskList();

								if (nextTaskList.size() > 0) {
									nextTaskList.remove(0);
								}

							}

							// otherwise, we can just go to the next day and remove the first task
							else {
								// get next day's tasklist

								ArrayList<TaskActivity> nextTaskList = this.scheduleList.get(currentYearIndex)
										.get(currentDay + 1).getTaskList();
								if (nextTaskList.size() > 0) {

									nextTaskList.remove(0);
								}
							}

						}

					}

					// increment number of days
					currentNumDays++;
				}
			}
		}
		// if it's anti task, then we need to remove the anti task and add back in the
		// recurring task it replaced
		if (isAnti) {

			AntiTaskActivity removeTask = (AntiTaskActivity) task;

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

			// get the task list of the current task
			ArrayList<TaskActivity> taskList = schedule.getTaskList();

			// check if the current task is the second half of a anti task or not
			// if it is, we need to update the task reference to the first half

			// the second half of a task, if it's there, is the first task of the task list
			if (taskList.get(0) == removeTask) {

				// take a look at the day before and see if the last task is the first half

				// if we're at the beginning of the year, look at the last day of last year
				// if the current year is 2020 then we don't need to look
				if (year > 2020) {

					int lastYearIndex = yearIndex - 1;

					// get last day of last year's last task

					ArrayList<TaskActivity> lastTaskList = this.scheduleList.get(lastYearIndex).get(364).getTaskList();
					if (lastTaskList.size() > 0) {

						TaskActivity lastTask = lastTaskList.get(lastTaskList.size() - 1);

						// if the last task is an anti task and the duration wraps to the next day,
						// then its the first half
						if (lastTask.isAntiTask() && lastTask.getDuration() > (24 - lastTask.getStartTime())) {

							// update task reference
							removeTask = (AntiTaskActivity) lastTask;
							// update the dates and indexes, schedule, taskList
							year = TaskActivity.getYear(removeTask.getDate());
							month = TaskActivity.getMonth(removeTask.getDate());
							day = TaskActivity.getDay(removeTask.getDate());
							yearIndex = year - 2020;
							beginningOfMonth = this.calculateMonthBeginning(month);
							dayIndex = beginningOfMonth + day - 1;
							schedule = this.scheduleList.get(yearIndex).get(dayIndex);
							taskList = schedule.getTaskList();
						}
					}

				}

				// otherwise just look at the day before to look for potential first half
				else {
					// get last day's last task

					ArrayList<TaskActivity> lastTaskList = this.scheduleList.get(yearIndex).get(dayIndex - 1)
							.getTaskList();
					if (lastTaskList.size() > 0) {

						TaskActivity lastTask = lastTaskList.get(lastTaskList.size() - 1);

						// if the last task is an anti task and the duration wraps to the next day,
						// then its the first half
						if (lastTask.isAntiTask() && lastTask.getDuration() > (24 - lastTask.getStartTime())) {

							// update task reference
							removeTask = (AntiTaskActivity) lastTask;
							// update the dates and indexes, schedule, taskList
							year = TaskActivity.getYear(removeTask.getDate());
							month = TaskActivity.getMonth(removeTask.getDate());
							day = TaskActivity.getDay(removeTask.getDate());
							yearIndex = year - 2020;
							beginningOfMonth = this.calculateMonthBeginning(month);
							dayIndex = beginningOfMonth + day - 1;
							schedule = this.scheduleList.get(yearIndex).get(dayIndex);
							taskList = schedule.getTaskList();
						}
					}
				}
			}

			// after affirming that we have the first half of the task

			// iterate through tasklist, remove task
			// search based on if task is anti task activity, has the same start time,
			// duration, name, type

			for (int i = 0; i < taskList.size(); i++) {

				TaskActivity currentTask = taskList.get(i);

				// if current task matches search, remove it
				if (currentTask.isAntiTask() && currentTask.getStartTime() == removeTask.getStartTime()
						&& currentTask.getDuration() == removeTask.getDuration()
						&& currentTask.getName() == removeTask.getName()
						&& currentTask.getType() == removeTask.getType()) {
					taskList.remove(i);
				}
			}

			// get the recurring activity linked to the anti task

			RecurringTaskActivity recurringLink = removeTask.getRecurring();

			// check for conflict, if there's a conflict then there was some transient tasks
			// overlapping
			// with the anti task, revert changes

			// otherwise, add the recurring task back in its place
			if (!checkForConflict(recurringLink)) {
				schedule.addTask(recurringLink);
			} else {
				// display error, revert changes
			}

			// if the anti task wraps to the next day, then we also need to remove that
			// remainder task
			if (removeTask.getDuration() > (24 - removeTask.getStartTime())) {

				// if the current day is the last day of the year, look to next year's first day
				if (month == 12 && day == 31) {

					// get the schedule of the first day of next year

					Schedule nextSchedule = this.scheduleList.get(yearIndex + 1).get(0);

					// get its tasklist

					ArrayList<TaskActivity> nextTaskList = nextSchedule.getTaskList();

					// get the first task
					AntiTaskActivity secondHalfAnti = (AntiTaskActivity) nextTaskList.get(0);

					// get the half of the recurring task activity that it's linked to
					RecurringTaskActivity secondHalfRecur = secondHalfAnti.getRecurring();

					// remove the first task
					nextTaskList.remove(0);

					// add the recurring task half back if there isn't a conflict
					// if there is a conflict then there were some overlapping transient tasks, so
					// display error and revert changes

					if (!checkForConflict(secondHalfRecur)) {
						nextSchedule.addTask(secondHalfRecur);
					} else {
						// display error, revert changes
					}

				}
				// otherwise we're at any other day in the year, so just check the next day
				else {
					// get the schedule of the next day

					Schedule nextSchedule = this.scheduleList.get(yearIndex).get(dayIndex + 1);

					// get its tasklist

					ArrayList<TaskActivity> nextTaskList = nextSchedule.getTaskList();

					// get the first task
					AntiTaskActivity secondHalfAnti = (AntiTaskActivity) nextTaskList.get(0);

					// get the half of the recurring task activity that it's linked to
					RecurringTaskActivity secondHalfRecur = secondHalfAnti.getRecurring();

					// remove the first task
					nextTaskList.remove(0);

					// add the recurring task half back if there isn't a conflict
					// if there is a conflict then there were some overlapping transient tasks, so
					// display error and revert changes

					if (!checkForConflict(secondHalfRecur)) {
						nextSchedule.addTask(secondHalfRecur);
					} else {
						// display error, revert changes
					}

				}

			}

		}
		// if it's transient, then we need to remove it
		if (isTransient) {
			TransientTaskActivity removeTask = (TransientTaskActivity) task;

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

			// get the task list of the current task
			ArrayList<TaskActivity> taskList = schedule.getTaskList();

			// check if the current task is the second half of a anti task or not
			// if it is, we need to update the task reference to the first half

			// the second half of a task, if it's there, is the first task of the task list
			if (taskList.get(0) == removeTask) {

				// take a look at the day before and see if the last task is the first half

				// if we're at the beginning of the year, look at the last day of last year
				// if the current year is 2020 then we don't need to look
				if (year > 2020) {

					int lastYearIndex = yearIndex - 1;

					// get last day of last year's last task

					ArrayList<TaskActivity> lastTaskList = this.scheduleList.get(lastYearIndex).get(364).getTaskList();

					if (lastTaskList.size() > 0) {
						TaskActivity lastTask = lastTaskList.get(lastTaskList.size() - 1);

						// if the last task is an anti task and the duration wraps to the next day,
						// then its the first half
						if (lastTask.isAntiTask() && lastTask.getDuration() > (24 - lastTask.getStartTime())) {

							// update task reference
							removeTask = (TransientTaskActivity) lastTask;
							// update the dates and indexes, schedule, taskList
							year = TaskActivity.getYear(removeTask.getDate());
							month = TaskActivity.getMonth(removeTask.getDate());
							day = TaskActivity.getDay(removeTask.getDate());
							yearIndex = year - 2020;
							beginningOfMonth = this.calculateMonthBeginning(month);
							dayIndex = beginningOfMonth + day - 1;
							schedule = this.scheduleList.get(yearIndex).get(dayIndex);
							taskList = schedule.getTaskList();
						}
					}

				}

				// otherwise just look at the day before to look for potential first half
				else {
					// get last day's last task

					ArrayList<TaskActivity> lastTaskList = this.scheduleList.get(yearIndex).get(dayIndex - 1)
							.getTaskList();

					if (lastTaskList.size() > 0) {
						TaskActivity lastTask = lastTaskList.get(lastTaskList.size() - 1);

						// if the last task is an anti task and the duration wraps to the next day,
						// then its the first half
						if (lastTask.isAntiTask() && lastTask.getDuration() > (24 - lastTask.getStartTime())) {

							// update task reference
							removeTask = (TransientTaskActivity) lastTask;
							// update the dates and indexes, schedule, taskList
							year = TaskActivity.getYear(removeTask.getDate());
							month = TaskActivity.getMonth(removeTask.getDate());
							day = TaskActivity.getDay(removeTask.getDate());
							yearIndex = year - 2020;
							beginningOfMonth = this.calculateMonthBeginning(month);
							dayIndex = beginningOfMonth + day - 1;
							schedule = this.scheduleList.get(yearIndex).get(dayIndex);
							taskList = schedule.getTaskList();
						}
					}
				}
					
			}

			// after affirming that we have the first half of the task
						// iterate through tasklist, remove task
						// search based on if task is transient task, has the same start time,
						// duration, name, type
			for (int i = 0; i < taskList.size(); i++) {

				TaskActivity currentTask = taskList.get(i);

				// if current task matches search, remove it
				if (currentTask.isTransientTask() && currentTask.getStartTime() == removeTask.getStartTime()
						&& currentTask.getDuration() == removeTask.getDuration()
						&& currentTask.getName() == removeTask.getName()
						&& currentTask.getType() == removeTask.getType()) {
					taskList.remove(i);
				}
			}
			
			
			//if the task wraps to the next day, then we need to go to the next schedule to remove the second half
			
			if(removeTask.getDuration() > (24 - removeTask.getStartTime())) {
				
				Schedule nextSchedule;
				//if we're at the end of the year we need to go to the first day of the next year
				if(month == 12 && day == 31) {
					
					// get the schedule of the first day of next year
					nextSchedule = this.scheduleList.get(yearIndex + 1).get(0);

				}
				
				//otherwise just go to the next day and remove the first task
				else {
					// get the schedule of the first day of next year
					nextSchedule = this.scheduleList.get(yearIndex).get(dayIndex  + 1);
				}
				
				// get its tasklist
				ArrayList<TaskActivity> nextTaskList = nextSchedule.getTaskList();
				
				// remove the first task
				nextTaskList.remove(0);
			}
			
		}

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