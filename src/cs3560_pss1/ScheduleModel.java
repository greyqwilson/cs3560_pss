package cs3560_pss1;

import java.util.ArrayList;

public class ScheduleModel {
	private ArrayList<ArrayList<Schedule>> scheduleList;
	private Calendar calendar;

	public ScheduleModel() {
		scheduleList = new ArrayList<ArrayList<Schedule>>();
	}

	public TaskActivity createTask(String name) {
		return null;

	}

	public void deleteTask(String name) {

	}

	public TaskActivity editTask(String name) {
		return null;

	}

	public ArrayList<ArrayList<Schedule>> getScheduleList() {
		return this.scheduleList;
	}

	public void updateScheduleList() {

		ArrayList<ArrayList<Schedule>> update = this.calendar.getScheduleList();

		ArrayList<ArrayList<Schedule>> copy = new ArrayList<ArrayList<Schedule>>();

		for (ArrayList<Schedule> year : update) {

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

						TaskActivity currentTaskRecurring = ((AntiTaskActivity) currentTask).getRecurring();

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

	// set calendar
	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	// get calendar
	public Calendar getCalendar() {
		return this.calendar;
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

}
