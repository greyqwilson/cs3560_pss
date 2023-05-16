
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

import org.json.JSONWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.text.StringCharacterIterator;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Display {

	private Calendar calendar;
	Integer currentYear;

	final String SCHEDULE_DIR = ".\\schedules\\";

	public Display(Calendar calendar) {
		this.calendar = calendar;

		currentYear = (this.calendar.scheduleList.size() > 0) ? 2020 : null;
	}

	public static void mainLoop() {
		System.out.println("\t~~Personal Schedule System~~");
		try (BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in))) {
			mainMenu(keyboard);

		} catch (IOException e) {

		}
	}

	public static void printMainMenu() {
		System.out.println("\t ~~Main Menu~~");
		System.out.println("1. Task Creator/Editor - Create a task, edit a task, or remove a task");
		System.out.println("2. Display today's schedule");
		System.out.println("3. Display this week's schedule");
		System.out.println("4. Display this month's schedule");
		System.out.println("5. Load a previously saved schedule");
		System.out.println("6. Save the current schedule");
		System.out.println("7. Add a year to the calendar");
		System.out.println("0. Exit");
	}

	public static void mainMenu(BufferedReader kb) throws IOException {
		String choice = "-1";
		while (!choice.matches("0")) {
			printMainMenu();
			System.out.println("Please select a menu option:");
			choice = kb.readLine();
			switch (choice) {
			case ("1"):
				taskMenu(kb);
				break;
			case ("2"):
				displayDayMenu(kb);
				break;
			case ("3"):
				displayWeekMenu(kb);
				break;
			case ("4"):
				displayMonthMenu(kb);
				break;
			case ("5"):
				loadFileMenu(kb);
				break;
			case ("6"):
				saveFileMenu(kb);
				break;
			case ("0"):
				System.out.println("Thank you for using PSS!");
				break;
			default:
				System.out.println("The menu option entered was not found.");
				break;
			}
		}
	}

	public static void printTaskMenu() {
		System.out.println("\t ~~Task Menu~~");
		System.out.println("1. Create a task");
		System.out.println("2. Edit a task");
		System.out.println("3. Delete a task");
		System.out.println("0. Return to the main menu");
	}

	public static void taskMenu(BufferedReader kb) throws IOException {
		String choice = "-1";
		while (!choice.matches("0")) {
			printTaskMenu();
			System.out.println("Please select a menu option:");
			choice = kb.readLine();
			switch (choice) {
			case ("1"):
				taskMenuCreate(kb);
				break;
			case ("2"):
				taskMenuEdit(kb);
				break;
			case ("3"):
				taskMenuDelete(kb);
				break;
			case ("0"):
				break;
			default:
				System.out.println("The menu option entered was not found.");
				break;
			}
		}
	}

	public static void taskMenuCreate(BufferedReader kb) throws IOException {
		System.out.println("\t --Creating a task--");
		System.out.println("Please enter the name of the task: ");
		String taskName = kb.readLine();
		if (taskName.strip().length() == 0) {
			return;
		}
	}

	public static void taskMenuEdit(BufferedReader kb) {
		System.out.println("Unimplemented");
	}

	public static void taskMenuDelete(BufferedReader kb) {
		System.out.println("Unimplemented");
	}

	public static void displayDayMenu(BufferedReader kb) {
		System.out.println("Unimplemented");
	}

	public static void displayWeekMenu(BufferedReader kb) {
		System.out.println("Unimplemented");
	}

	public static void displayMonthMenu(BufferedReader kb) {
		System.out.println("Unimplemented");
	}

	public static void loadFileMenu(BufferedReader kb) {
		System.out.println("Unimplemented");
	}

	public static void saveFileMenu(BufferedReader kb) {
		System.out.println("Unimplemented");
	}

	public TaskActivity searchTask(String taskName) {
		TaskActivity task = calendar.searchTask(taskName);
		return task;
	}

	public void loadScheduleFromFile() {
		Schedule schedFromFile;
		JSONParser parser = new JSONParser();

		/*
		 * TODO
		 * 
		 * 1.) Filepath Parameter 2.) Conditional Statements for different task types
		 * 3.) Return type? a.) Attributes b.) Tasks c.) Array of Tasks 4.) Make sure
		 * everyone can run it.
		 * 
		 */

		try {
			JSONArray a = (JSONArray) parser.parse(new FileReader("20200101.json")); // need a method for filepath

			for (int i = 0; i < a.size(); i++) {
				JSONObject task = (JSONObject) a.get(i);

				String taskName = (String) task.get("Name");

				String taskType = (String) task.get("Type");

				Double taskStartDate = (Double) task.get("StartDate");

				Double taskStartTime = (Double) task.get("StartTime");

				Double taskDuration = (Double) task.get("Duration");

				Long taskEndDate = (Long) task.get("EndDate");

				Long taskFrequency = (Long) task.get("Frequency");

				System.out.println(taskName);
				System.out.println(taskType);
				System.out.println(taskStartDate);
				System.out.println(taskStartTime);
				System.out.println(taskDuration);
				System.out.println(taskEndDate);
				System.out.println(taskFrequency);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Copy relevant data to a schedule object
		// Pass into whoever takes it and display

	}

	public void writeScheduleToFile(TaskActivity[] tasks) {
		//JSONObject jsonScheduleObj = new JSONObject();
		//ArrayList<TaskActivity> tasks = schedule.getTaskList();
		JSONArray jsonTaskObj = new JSONArray();
		for (TaskActivity task : tasks) {
			JSONObject jsonObj = new JSONObject();
			if (task.isRecurringTask()) {
				jsonObj.put("Name", task.getName());
				jsonObj.put("Type", task.getType());
				jsonObj.put("StartTime", task.getStartTime());
				jsonObj.put("Duration", task.getDuration());
				jsonObj.put("Date", task.getDate());
				jsonObj.put("StartDate", ((RecurringTaskActivity) task).getStartDate());
				jsonObj.put("EndDate", ((RecurringTaskActivity) task).getEndDate());
				jsonObj.put("Frequency", ((RecurringTaskActivity) task).getFrequency());
				jsonTaskObj.add(jsonObj);
			}
			
			//It is a taskactivity or anti-task
			else {
				jsonObj.put("Name", task.getName());
				jsonObj.put("Type", task.getType());
				jsonObj.put("StartTime", task.getStartTime());
				jsonObj.put("Duration", task.getDuration());
				jsonObj.put("Date", task.getDate());
				jsonTaskObj.add(jsonObj);
			}
			
//			jsonScheduleObj.put("Date", tasks[0].getDate());
//			jsonScheduleObj.put("TaskList", jsonTaskObj);
		}
		String date = "";
		try {
			date = String.valueOf(tasks[0].getDate());
			FileWriter fw = new FileWriter(String.valueOf(tasks[0].getDate()) + ".json");
			String jsonString = jsonTaskObj.toJSONString();
			
			fw.append(jsonString);
			fw.close();
			//fw.write(jsonString);
			System.out.println(jsonString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Something went wrong creating file writer");
			e.printStackTrace();
		}
		
		//Read object
//		try {
//			FileReader fr = new FileReader(date + ".json");
//			JSONParser parser = new JSONParser();
//			JSONObject scheduleJson = (JSONObject) parser.parse(fr);
//			System.out.println(scheduleJson.toJSONString());
//			
//			JSONArray tasklistJson = (JSONArray) parser.parse(scheduleJson.toString());
//			System.out.println(tasklistJson.toJSONString());
//			for (int i=0; i<tasklistJson.size(); i++) {
//				JSONObject taskJson = (JSONObject) parser.parse(tasklistJson.toJSONString());
//				System.out.println(taskJson.toJSONString());
//			}
			
//		}
//		catch (Exception e){
//			e.printStackTrace();
//		}
	}

	public void displayTasksforDay(int date) {
		TaskActivity[] tasks;
		tasks = calendar.getTasksForDay(date);
		
		System.out.println(TaskActivity.getYear(date) + "/" + TaskActivity.getMonth(date) + "/" + TaskActivity.getDay(date));
		for (int i = 0; i < tasks.length; i++) {
			System.out.print(formatTaskPrintout(tasks[i]));
		}
	}

	public void displayTasksforWeek(int date) {
		Schedule[] weekSchedule;
		weekSchedule = calendar.getTasksForWeek(date);
		String weekString = formatWeekTasks(weekSchedule);
		System.out.print(weekString);

	}

	public void displayTasksforMonth(int date) {
		Schedule[] monthSchedule = calendar.getTasksForMonth(date);
		System.out.print(formatMonthTasks(monthSchedule));

	}

	// For use with formatMenu to display the full details of a task
	// Fills a column with spaces until its length is the same as the string
	// matchLengthString
	private static String formatTaskLine(String text, String matchLengthString, boolean useNewLine) {
		if (useNewLine)
			return text.concat(" ".repeat(matchLengthString.length() - text.length() - 2) + "|\n");
		else
			return text.concat(" ".repeat(matchLengthString.length() - text.length() - 2) + "|");
	}

	// Create a string representation of a task
	private static String formatTaskPrintout(TaskActivity task) {
		String startTime = numberTimeToString(task.getStartTime(), false);
		String title = task.getName();
		String type = task.getType();
		String endTime = numberTimeToString(task.getEndTime(), false);

		String separator = ("*----------------------------------------*\n");
		// Start time ~~Title~~
		String titleLine = String.format("|%-7s%3s~~%s~~", startTime, " ", title);
		titleLine = formatTaskLine(titleLine, separator, true);

		String typeLine = "|     (" + type + ")";
		typeLine = formatTaskLine(typeLine, separator, true);

		//String activityTypeLine = "|     (" + activityType + ")";
		//activityTypeLine = formatTaskLine(activityTypeLine, separator, true);

		//String descriptionLine = "";
		//String descBody = "";
		//int descLines = description.length() / separator.length() + 1;
		// StringCharacterIterator iterator = new StringCharacterIterator(description);
		// if (iterator.first() != StringCharacterIterator.DONE) {
		// 	descriptionLine = descriptionLine.concat("|     -" + String.valueOf(iterator.first()));
		// 	for (int i = 0; i <= descLines; i++) {
		// 		if (i != 0)
		// 			descriptionLine = descriptionLine.concat("|     ");
		// 		char c;
		// 		for (int j = 0; j < 33; j++) {
		// 			c = iterator.next();
		// 			if (c != StringCharacterIterator.DONE)
		// 				descriptionLine = descriptionLine.concat(String.valueOf(c));
		// 			else
		// 				j = 34;
		// 		}
		// 		descriptionLine = formatTaskLine(descriptionLine, separator, true);
		// 		descBody = descBody.concat(descriptionLine);
		// 		descriptionLine = "";
		// 	}
		// } else {
		// 	descBody = "|";
		// 	descBody = formatTaskLine(descBody, separator, true);
		// }
		// descriptionLine = formatTaskLine(descriptionLine, separator);

		String endTimeLine = "|" + endTime;
		endTimeLine = formatTaskLine(endTimeLine, separator, true);
		String s = separator + titleLine + typeLine + endTimeLine + separator;
		return s;
	}

	private static String formatWeekTasks(Schedule[] schedules) {
		Schedule schedule;

		final int MAXTASKSLISTED = 20;
		final int MAXTITLELENGTH = 8;
		ArrayList<ArrayList<String>> weekData = new ArrayList<ArrayList<String>>();

		// Go through each schedule
		StringBuilder weekHeadingSb = new StringBuilder("");

		// For use in formatting column width
		int[] headingLength = new int[schedules.length];

		// Get the length of the schedule with the most amount of tasks
		int knownMaxTasks = 0;

		for (int i = 0; i < schedules.length; i++) {
			// Record schedule's tasks
			ArrayList<String> scheduleData = new ArrayList<String>();

			schedule = schedules[i];
			ArrayList<TaskActivity> tasks = schedule.getTaskList();

			String day, month;
			day = schedules[i].getDateString().substring(6, 8);
			month = numberMonthToString(schedules[i].getDateString().substring(4, 6));
			String heading = "*[" + day + " " + month + "]-------------------";
			headingLength[i] = heading.length();
			weekHeadingSb.append(heading);

			if (tasks.size() > knownMaxTasks)
				knownMaxTasks = tasks.size();

			// Grab all pertinent data from each day's task list (up to 20 tasks for any
			// given day)
			String startTime, endTime, title;
			for (int j = 0; j < tasks.size() || j == MAXTASKSLISTED - 1; j++) {
				title = tasks.get(j).getName();
				// Get j'th task of the day's end and start time
				startTime = numberTimeToString(tasks.get(j).getStartTime(), false);
				endTime = numberTimeToString(tasks.get(j).getEndTime(), false);

				if (title.length() > MAXTITLELENGTH) {
					title = title.substring(0, MAXTITLELENGTH).concat("..");
				}
				scheduleData.add(j, startTime + " " + endTime + " " + title);
			}
			weekData.add(i, scheduleData);

		}
		weekHeadingSb.append("*\n");

		// Collate each line
		for (int row = 0; row < MAXTASKSLISTED && row < knownMaxTasks; row++) {
			String taskLine;
			// For each column

			for (int col = 0; col < weekData.size(); col++) {
				ArrayList<String> scheduleData = weekData.get(col);
				if (row < scheduleData.size()) {
					if (col == 0) {
						weekHeadingSb.append("|");
					}
					taskLine = scheduleData.get(row);
					taskLine = taskLine.concat(" ".repeat(headingLength[col] - taskLine.length() - 1) + "|");
					weekHeadingSb.append(taskLine);
				} else {
					// The schedule must have no more tasks to print
					taskLine = " ".repeat(headingLength[col] - 1) + "|";
					weekHeadingSb.append(taskLine);
					continue;
				}

			}
			weekHeadingSb.append("\n");
		}
		String bottomLine = "*---------------------------";
		for (int i = 0; i < schedules.length; i++) {
			weekHeadingSb.append(bottomLine);
		}
		weekHeadingSb.append("*\n");
		return weekHeadingSb.toString();
	}

	private static String formatMonthTasks(Schedule[] schedules) {

		Schedule schedule;

		final int MAXTASKSLISTED = 20;
		final int MAXTITLELENGTH = 8;
		ArrayList<ArrayList<String>> monthData = new ArrayList<ArrayList<String>>();

		// Go through each schedule
		StringBuilder monthHeadingSb = new StringBuilder("");

		// For use in formatting column width
		int[] headingLength = new int[schedules.length];

		// Get the length of the schedule with the most amount of tasks
		int knownMaxTasks = 0;
		int weeks = schedules.length / 7;
		int left = schedules.length - weeks * 7;
		int dayCount = 0;
		for (int a = 0; a < weeks; a++) {
			for (int i = 0; i < 7; i++) {
				dayCount++;
				// Record schedule's tasks
				ArrayList<String> scheduleData = new ArrayList<String>();

				schedule = schedules[a * 7 + i];
				ArrayList<TaskActivity> tasks = schedule.getTaskList();

				String day, month;
				day = schedules[a * 7 + i].getDateString().substring(6, 8);
				month = numberMonthToString(schedules[i].getDateString().substring(4, 6));
				String heading = "*[" + day + " " + month + "]-----------------";
				headingLength[i] = heading.length();
				monthHeadingSb.append(heading);

				if (tasks.size() > knownMaxTasks)
					knownMaxTasks = tasks.size();

				// Grab all pertinent data from each day's task list (up to 20 tasks for any
				// given day)
				String startTime, endTime, title;
				for (int j = 0; j < tasks.size() || j == MAXTASKSLISTED - 1; j++) {
					title = tasks.get(j).getName();
					// startTime = String.valueOf(tasks.get(j).getStartTime());
					// endTime = String.valueOf(tasks.get(j).getEndTime());
					startTime = numberTimeToString(tasks.get(j).getStartTime(), false);
					endTime = numberTimeToString(tasks.get(j).getEndTime(), false);

					if (title.length() > MAXTITLELENGTH) {
						title = title.substring(0, MAXTITLELENGTH).concat("..");
					}
					scheduleData.add(j, startTime + "-" + endTime + " " + title);
				}
				monthData.add(a * 7 + i, scheduleData);

			}
			monthHeadingSb.append("*\n");

			// Collate each line
			for (int i = 0; i < MAXTASKSLISTED && i < knownMaxTasks; i++) {
				String taskLine;
				// For each column

				for (int j = 0; j < monthData.size() - a * 7; j++) {
					ArrayList<String> scheduleData = monthData.get(a * 7 + j);
					if (i < scheduleData.size()) {
						if (j == 0) {
							monthHeadingSb.append("|");
						}
						taskLine = scheduleData.get(i);
						taskLine = taskLine.concat(" ".repeat(headingLength[i] - taskLine.length() - 1) + "|");
						monthHeadingSb.append(taskLine);
					} else {
						// The schedule must have no more tasks to print
						if (j == 0) {
							monthHeadingSb.append("|");
						}
						taskLine = " ".repeat(headingLength[j] - 1) + "|";
						monthHeadingSb.append(taskLine);
						continue;
					}

				}
				monthHeadingSb.append("\n");

			}
			String bottomLine = "*-------------------------";
			for (int b = 0; b < 7; b++) {
				monthHeadingSb.append(bottomLine);
			}
			monthHeadingSb.append("*\n\n");

		}

		if (left > 0) {
			for (int i = 0; i < left; i++) {
				// Record schedule's tasks
				ArrayList<String> scheduleData = new ArrayList<String>();

				schedule = schedules[dayCount + i];
				ArrayList<TaskActivity> tasks = schedule.getTaskList();

				String day, month;
				day = schedules[dayCount + i].getDateString().substring(6, 8);
				month = numberMonthToString(schedules[i].getDateString().substring(4, 6));
				String heading = "*[" + day + " " + month + "]-----------------";
				headingLength[i] = heading.length();
				monthHeadingSb.append(heading);

				if (tasks.size() > knownMaxTasks)
					knownMaxTasks = tasks.size();

				// Grab all pertinent data from each day's task list (up to 20 tasks for any
				// given day)
				String startTime, endTime, title;
				for (int j = 0; j < tasks.size() || j == MAXTASKSLISTED - 1; j++) {
					title = tasks.get(j).getName();
					// startTime = String.valueOf(tasks.get(j).getStartTime());
					// endTime = String.valueOf(tasks.get(j).getEndTime());
					startTime = numberTimeToString(tasks.get(j).getStartTime(), false);
					endTime = numberTimeToString(tasks.get(j).getEndTime(), false);

					if (title.length() > MAXTITLELENGTH) {
						title = title.substring(0, MAXTITLELENGTH).concat("..");
					}
					scheduleData.add(j, startTime + "-" + endTime + " " + title);
				}
				monthData.add(dayCount + i, scheduleData);

			}
			monthHeadingSb.append("*\n");

			// Collate each line
			for (int i = 0; i < MAXTASKSLISTED && i < knownMaxTasks; i++) {
				String taskLine;
				// For each column

				for (int j = 0; j < monthData.size() - dayCount; j++) {
					ArrayList<String> scheduleData = monthData.get(dayCount + j);
					if (i < scheduleData.size()) {
						if (j == 0) {
							monthHeadingSb.append("|");
						}
						taskLine = scheduleData.get(i);
						taskLine = taskLine.concat(" ".repeat(headingLength[i] - taskLine.length() - 1) + "|");
						monthHeadingSb.append(taskLine);
					} else {
						// The schedule must have no more tasks to print
						if (j == 0) {
							monthHeadingSb.append("|");
						}
						taskLine = " ".repeat(headingLength[j] - 1) + "|";
						monthHeadingSb.append(taskLine);
						continue;
					}

				}
				monthHeadingSb.append("\n");
			}
			String bottomLine = "*-------------------------";
			for (int b = 0; b < left; b++) {
				monthHeadingSb.append(bottomLine);
			}
			monthHeadingSb.append("*\n\n");

		}

		return monthHeadingSb.toString();

	}

	private static String numberTimeToString(double time, boolean twentyFourHour) {
		int hour = (int) time;
		String timeString;
		String sideOfNoon = "AM";
		// Is this 24-hour time or regular time?
		if (hour > 12 && !twentyFourHour) {
			hour -= 12;
			sideOfNoon = "PM";
		}
		timeString = String.valueOf(hour);

		// Minute part
		String fraction = time >= 10 ? String.valueOf(time).substring(3) : String.valueOf(time).substring(2);
		switch (fraction) {
		case ("25"):
			fraction = "15";
			break;
		case ("5"):
			fraction = "30";
			break;
		case ("75"):
			fraction = "45";
			break;
		case ("0"):
			fraction = "00";
			break;
		default:
			fraction = "??";
			break;
		}
		timeString = timeString.concat(":" + fraction);

		if (!twentyFourHour) {
			timeString = timeString.concat(sideOfNoon);
		}
		return timeString;
	}

	// takes in number month, returns abbreviation
	private static String numberMonthToString(String monthNum) {
		switch (monthNum) {
		case ("01"):
			return "JAN";
		case ("02"):
			return "FEB";
		case ("03"):
			return "MAR";
		case ("04"):
			return "APR";
		case ("05"):
			return "MAY";
		case ("06"):
			return "JUN";
		case ("07"):
			return "JUL";
		case ("08"):
			return "AUG";
		case ("09"):
			return "SEP";
		case ("10"):
			return "OCT";
		case ("11"):
			return "NOV";
		case ("12"):
			return "DEC";
		default:
			return "UNK";
		}
	}

//takes in month abbreviation, returns corresponding int version
	private static String monthStringToIntString(String monthString) {
		switch (monthString.toString()) {
		case ("jan"):
			return ("01");
		case ("feb"):
			return ("02");
		case ("mar"):
			return ("03");
		case ("apr"):
			return ("04");
		case ("may"):
			return ("05");
		case ("jun"):
			return ("06");
		case ("jul"):
			return ("07");
		case ("aug"):
			return ("08");
		case ("sep"):
			return ("09");
		case ("oct"):
			return ("10");
		case ("nov"):
			return ("11");
		case ("dec"):
			return ("12");
		}
		return "01";
	}
//if calender schedule list is empty
// no years in this calendar
//intialize year
//if we're at the end of the years listed
//prompt user to create year

	public void start() {
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		String choice = "";
		yearMenu(keyboard, choice);
	}

	// year menu
	public void yearMenu(BufferedReader keyboard, String choice) {

		// enters while loop that only ends when user presses q
		while (true) {

			// display year, if there are no years in calendar it will ask to create year
			displayYear();
			System.out.println("Press 1 to add an empty year to the calendar");
			System.out.println("Press 2 to move to previous year");
			System.out.println("Press 3 to move to next year");
			System.out.println("Type in the displayed abbreviation of a month to select that month");
			System.out.println("Press q to quit");
			System.out.println("\nEnter your choice\n\n");

			// ask for user input
			try {
				choice = keyboard.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// evaluate input
			// adds new empty year to calendar
			switch (choice.toLowerCase()) {
			case "1":
				this.createYear();
				break;

			// decrements to previous year if available
			case "2":
				if (currentYear != null && currentYear > 2020) {
					currentYear -= 1;
				}
				break;

			// increments to next year if available
			case "3":
				if (currentYear != null && currentYear - 2020 < this.calendar.scheduleList.size() - 1) {
					currentYear++;
				}
				break;

			// these enter the month menu
			case ("jan"):
				monthMenu(keyboard, choice, "jan");
				break;
			case ("feb"):
				monthMenu(keyboard, choice, "feb");

				break;
			case ("mar"):
				monthMenu(keyboard, choice, "mar");

				break;
			case ("apr"):
				monthMenu(keyboard, choice, "apr");

				break;
			case ("may"):
				monthMenu(keyboard, choice, "may");

				break;
			case ("jun"):
				monthMenu(keyboard, choice, "jun");

				break;
			case ("jul"):
				monthMenu(keyboard, choice, "jul");

				break;
			case ("aug"):
				monthMenu(keyboard, choice, "aug");

				break;
			case ("sep"):
				monthMenu(keyboard, choice, "sep");

				break;
			case ("oct"):
				monthMenu(keyboard, choice, "oct");

				break;
			case ("nov"):
				monthMenu(keyboard, choice, "nov");

				break;
			case ("dec"):
				monthMenu(keyboard, choice, "dec");

				break;

			// exits program
			case ("q"):
				System.exit(0);
			default:
				System.out.println("You've entered the wrong command, please try again\n\n");
			}

		}
	}

	// month menu
	public void monthMenu(BufferedReader keyboard, String choice, String month) {

		// convert month to integer string version
		String monthInt = Display.monthStringToIntString(month);
		// get date of beginning of month, use that as input to display month and tasks
		int date = Integer.valueOf(String.valueOf(currentYear) + monthInt + "01");
		int[] monthDays = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

		// enter while loop that can only be broken by going back to year view or
		// exiting program
		while (true) {

			// display current month

			displayMonth(date);

			System.out.println("Press 1 to enter week selection");
			System.out.println("Press 2 to select day selection");
			System.out.println("Press 3 to exit back to year view");
			System.out.println("Press q to exit program");

			// ask for user input
			try {
				choice = keyboard.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// evaluate user input
			switch (choice.toLowerCase()) {

			// week selection
			case ("1"):
				// get number of weeks
				int numWeeks = monthDays[Integer.valueOf(monthInt) - 1] / 7;
				// if there are remainder of days, then there's one more week
				if (monthDays[Integer.valueOf(monthInt) - 1] % 7 > 0) {
					numWeeks++;
				}

				String weekChoice = "";
				// ask for user input for week index

				System.out.println(
						"Enter a number between 1 and " + numWeeks + " to select the corresponding week to view");
				try {
					weekChoice = keyboard.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// input validation
				while (Integer.valueOf(weekChoice) < 1 || Integer.valueOf(weekChoice) > numWeeks) {
					System.out.println("That's an invaid week position. Try again");

					System.out.println(
							"Enter a number between 1 and " + numWeeks + " to select the corresponding week to view");
					try {
						weekChoice = keyboard.readLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				// at this point we have a valid week position
				int weekBeginningMonthDay = 7 * (Integer.valueOf(weekChoice) - 1) + 1;
				String weekBeginningString = ((weekBeginningMonthDay < 10) ? "0" : "")
						+ String.valueOf(weekBeginningMonthDay);
				System.out.println(weekBeginningString);

				int weekBeginningDate = Integer.valueOf(String.valueOf(currentYear) + monthInt + weekBeginningString);
				System.out.println("\n\n");
				this.displayTasksforWeek(weekBeginningDate);
				System.out.println("\n\n");
				break;

			// entering day selection
			case ("2"):
				int numDaysInMonth = monthDays[Integer.valueOf(monthInt) - 1];
				String dayChoice = "";
				// ask for user input for day index

				System.out.println("Enter a number between 1 and " + numDaysInMonth
						+ " to select the corresponding month day to view");
				try {
					dayChoice = keyboard.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// input validation
				while (Integer.valueOf(dayChoice) < 1 || Integer.valueOf(dayChoice) > numDaysInMonth) {
					System.out.println("That's an invaid day of month position. Try again");

					System.out.println("Enter a number between 1 and " + numDaysInMonth
							+ " to select the corresponding month day to view");
					try {
						dayChoice = keyboard.readLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				// formats day int, adds 0 if single digit
				String dayInt = ((Integer.valueOf(dayChoice) < 10) ? "0" : "") + String.valueOf(dayChoice);

				// enter day menu
				dayMenu(keyboard, monthInt, dayInt);
				break;
				// returning to year menu
			case ("3"):
				return;
			case ("q"):
				System.exit(0);
			}

		}

	}

	// day menu

	public void dayMenu(BufferedReader keyboard, String month, String day) {
		int date = Integer.valueOf(String.valueOf(currentYear) + month + day);
		//enter while loop that can only be exited by quitting program or exiting back to month view
		while(true) {
			//display the day and its tasks
			this.displayTasksforDay(date);
			
			System.out.println("Press 1 to create a task");
			System.out.println("Press 2 to update a task");
			System.out.println("Press 3 to delete a task");
			System.out.println("Press 4 to select a task");
			System.out.println("Press 5 to save day schedule to file");
			System.out.println("Press 6 to load day schedule from file");
			System.out.println("Press 7 to go back to month view");
			String choice = "";
			
			//ask for user input
			try {
				choice = keyboard.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//user input evaluation
			switch(choice) {
			
			case("1"):
				createTaskMenu(keyboard, month, date);
				break;
			case("3"):
				TaskActivity[] tasks;
				tasks = calendar.getTasksForDay(date);
				//if there are no tasks to delete, say so
				if(tasks.length == 0) {
					System.out.println("There are no tasks to delete");
				} 
				//if there are tasks to delete
				else {
					
					//ask user for position of task starting from 1 to delete
					System.out.println("Choose the position of the task that you want to remove within the list, in between 1 and " + tasks.length);
					
					String taskPosition ="";
					try {
					 taskPosition = keyboard.readLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//validate task pos input
					//if position is out of bounds
					while(Integer.parseInt(taskPosition) < 1 || Integer.parseInt(taskPosition) > tasks.length) {
						
						System.out.println("Task position needs to be in between 1 and "  + tasks.length);
						System.out.println("Choose it again");
						try {
							 taskPosition = keyboard.readLine();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						
					}
					
					//convert to task index
					
					int taskIndex = Integer.parseInt(taskPosition) -1;
					
					//get task
					TaskActivity task = tasks[taskIndex];
					
					//attempt to delete
					boolean deleteSuccess = this.calendar.deleteTask(task);
					
					if(deleteSuccess) {
						System.out.println("You've successfully deleted the task");
					} else {
						System.out.println("You were unable to delete this task");
					}
				}
				break;
			case("4"):
				TaskActivity[] tasks1;
				tasks1 = calendar.getTasksForDay(date);
				//if there are no tasks to delete, say so
				if(tasks1.length == 0) {
					System.out.println("There are no tasks to select");
				} 
				//if there are tasks to delete
				else {
				
					//ask user for position of task starting from 1 to delete
					System.out.println("Choose the position of the task that you want to select within the list, in between 1 and " + tasks1.length);
					
					String taskPosition ="";
					try {
					taskPosition = keyboard.readLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//validate task pos input
					//if position is out of bounds
					while(Integer.parseInt(taskPosition) < 1 || Integer.parseInt(taskPosition) > tasks1.length) {
						
						System.out.println("Task position needs to be in between 1 and "  + tasks1.length);
						System.out.println("Choose it again");
						try {
							taskPosition = keyboard.readLine();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						
					}
					
					//convert to task index
					
					int taskIndex = Integer.parseInt(taskPosition) -1;
					
					//get task
					TaskActivity task = tasks1[taskIndex];
					
					taskMenu(keyboard, task);
				}
			
			case("5"):
				TaskActivity[] tasks2 = calendar.getTasksForDay(date);
				writeScheduleToFile(tasks2);
				break;
			
			case("6"):
				loadScheduleFromFile();
				break;
				
			case("7"):
				return;
			}
		}
		
	}
	
	// this is for displaying one task and the possible actions 
	public void taskMenu(BufferedReader keyboard, TaskActivity task) {
		
		//enter while loop that can only be exited by exiting to day menu or quiting program
		while(true) {
			
			System.out.print(formatTaskPrintout(task));		
		
		System.out.println("Press 1 to update the task");
		System.out.println("Press 2 to delete the task");
		System.out.println("Press 3 to replace this task with an anti task (if it is recurring)");
		System.out.println("Press 4 to exit to day menu");
		System.out.println("Press q to quit program");
		
		
		System.out.println("Enter your choice");
		String choice = "";
		try {
			choice = keyboard.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		switch(choice.toLowerCase()) {
		case ("1"):
			break;
		//This deletes the task and returns the user back to the day menu
		case("2"):
			boolean deleteSuccess = this.calendar.deleteTask(task);
				if(deleteSuccess) {
				System.out.println("You've successfully deleted the task");
				} else {
				System.out.println("You were unable to delete this task");
				}
		return;
		case("3"):
			if (!task.isRecurringTask()){
				System.out.println("This task is not recurring.");
			}
			else {
				//Print out a list of days this
				
				try{
					System.out.println("Enter the name of the task that will replace this:");
					String titleStr = keyboard.readLine();
					boolean taskMade = calendar.createTask(titleStr, task.getStartTime(), task.getDuration(), task.getDate(), "Cancellation", 0, 0, 0);
					if (taskMade){
						System.out.println("Task " + task.getName() + " for this day has been replaced successfully");
						task = calendar.searchTask(titleStr);
					}
					else{
						System.out.println("The task could not be replaced.");
					}
				}
				catch (IOException e){
					System.out.println(e.getStackTrace());
				}

			}
			break;
		case("4"):
			return;
		case("q"):
			System.exit(0);
		default:
			System.out.println("Invalid choice");
			
		}
		
		
		}
		
	}
	
	//create task menu, this is only for when we're adding recurring or transient
	public void createTaskMenu(BufferedReader keyboard, String month, int date) {
		String name = ""; double startTime=0; double duration = 0; String typeString = ""; int frequency = 0;
		int startDate =0; int endDate =0;		
		
		int[] monthDays = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

		boolean isRecurring = false;
		boolean isTransient = false;
		String[] recurringTaskTypes = { "Class", "Study", "Sleep", "Exercise", "Work", "Meal" };

		String[] transientTaskTypes = { "Visit", "Shopping", "Appointment" };
		
		//ask until user enters a valid type
		while(!isRecurring && !isTransient) {
			System.out.println("Please input the type of the task, choose between recurring or transient types");
			System.out.println("Recurring Types: Class, Study, Sleep, Exercise, Work, Meal");
			System.out.println("Transient Types: Visit, Shopping, Appointment");
			
			//ask for type 
			try {
				typeString = keyboard.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//look if type is valid
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

			
		}
	
		
		//ask for the name
		System.out.println("Enter the name");
		try {
			name = keyboard.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//ask for the start time
		String startTimeString = "";
		System.out.println("Enter start time");
		try {
			startTimeString = keyboard.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//input validation, start time is in between 0 and 23.75
		while( !(Double.parseDouble(startTimeString) % .25 == 0) && (Double.parseDouble(startTimeString) < 0 || Double.parseDouble(startTimeString) > 23.75) ) {
			System.out.println("Start time needs to be in between 0 and 23.75");
			try {
				startTimeString = keyboard.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		//at this point, start time has been validated, so store start time in double form
		startTime = Double.parseDouble(startTimeString);
		
		
		
		//ask for duration
				String durationString = "";
				System.out.println("Enter duration time");
				try {
					durationString = keyboard.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//input validation, start time is in between 0 and 23.75
				while( !(Double.parseDouble(durationString) % .25 == 0) && (Double.parseDouble(durationString) < 0 || Double.parseDouble(durationString) > 23.75) ) {
					System.out.println("Duration needs to be in between 0 and 23.75");
					try {
						durationString = keyboard.readLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				
				//at this point, duration has been validated, so store duration in double form
				startTime = Double.parseDouble(durationString);
				
				
				
		//if its recurring, ask for frequency, start date, and end date;
		if(isRecurring) {
			
			
			//ask for frequency
			
			String frequencyString = "";
			
			System.out.println("Enter frequency, needs to be 1 or 7");
			try {
				frequencyString = keyboard.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//input validation
			
			while(Integer.parseInt(frequencyString) != 7 && Integer.parseInt(frequencyString) != 1) {
				System.out.println("That's an invalid frequency");
				System.out.println("Enter frequency, needs to be 1 or 7");
				try {
					frequencyString = keyboard.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			//at this point we've validated frequency, store in int form
			
			frequency = Integer.parseInt(frequencyString);
			
			//assume start date is the current date;
			
			//ask for end date
			System.out.println("Enter the date you want this recurring task to stop");
			
			//ask for end year
			System.out.println("Enter the year");
			String endYear = "";
			try {
				endYear = keyboard.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//validate year input
			
			while(Integer.parseInt(endYear) < 2020 || Integer.parseInt(endYear) < currentYear) {
				System.out.println("That's an invalid year, needs to be 2020 or later and equal or greater to the current year");
				endYear = "";
				try {
					endYear = keyboard.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			//ask for end month
			
			String endMonth = "";
			
			System.out.println("Enter the month of the end date, using the abbreviations below:"
					+ "\nJAN FEB MAR APR MAY JUN JUL AUG SEP OCT NOV DEC");
			try {
				endMonth = keyboard.readLine().toLowerCase();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//validate month input
			//error if end month is lower than current month and we're in same year
			if( (Integer.parseInt(endYear) == currentYear && Integer.parseInt(Display.monthStringToIntString(endMonth)) < Integer.parseInt(month)  )
					||
					(Integer.parseInt(Display.monthStringToIntString(endMonth)) <= 0 || Integer.parseInt(Display.monthStringToIntString(endMonth)) > 12)) {
				System.out.println("The month cannot lower than the current month if we're in the same year, and it needs to be in between 1 and 12");
				System.out.println("Enter the month of the end date, using the abbreviations below:"
						+ "\nJAN FEB MAR APR MAY JUN JUL AUG SEP OCT NOV DEC");		
				try {
					endMonth = keyboard.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//format the month string
			endMonth = (Integer.parseInt(Display.monthStringToIntString(endMonth)) < 10 ? "0" : "") + Integer.parseInt(Display.monthStringToIntString(endMonth));
			
			
			//ask for end day
			int numMonthDays = monthDays[Integer.parseInt(endMonth) -1];

			String endDay = "";
			System.out.println("Enter the day of the end month, needs to be inbetween 1 and " + numMonthDays);
			
			try {
				endDay = keyboard.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//validate day string
			//if we're in the same year and month, then day needs to be equal or greater to current day
			//day also needs to be in between 1 and the end month's last day
			
			while( (Integer.parseInt(endYear) == currentYear && endMonth.equals(month) && Integer.parseInt(endDay) < TaskActivity.getDay(date) )
					|| (Integer.parseInt(endDay) < 1 || Integer.parseInt(endDay) > numMonthDays)) {
				
				System.out.println("If we're in the same month and day then the day has to be the current day or after, and it needs to be in between 1 and the month's last day");
				System.out.println("Enter the day of the end month, needs to be inbetween 1 and " + numMonthDays);
				
				try {
					endDay = keyboard.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//format day string
			endDay = (Integer.parseInt(endDay) < 10 ? "0" : "") + endDay;
			
			//turn the end year, month, and day into the end date int
			endDate = Integer.parseInt(endYear + endMonth + endDay);
			
		}

		
		//attempt to create task
		
		
		boolean creationSuccess = this.calendar.createTask(name, startTime, duration,  date, typeString,frequency,
				date,  endDate);
		
		if(creationSuccess) {
			System.out.println("Task has been created successfully");
		} else {
			System.out.println("Task failed to be created");
		}
		
		
		
		
		
		
		
	}
//displays year and months

	public void displayYear() {

		if (currentYear == null) {
			System.out.println("No years in this current calendar, please create a year");
		} else {

			System.out.println("Year: " + currentYear);
			System.out.println("JAN FEB MAR APR MAY JUN JUL AUG SEP OCT NOV DEC\n\n");
		}
	}

//this just creates a new empty year
	public void createYear() {
		int calendarYearLength = this.calendar.scheduleList.size();
		this.calendar.createYear(calendarYearLength + 2020);

		if (this.calendar.scheduleList.size() == 1) {
			currentYear = 2020;
		}
	}

//displays month

	public void displayMonth(int date) {
		this.displayTasksforMonth(date);
	}

// Probably not staying
// \/ \/ \/ \/
	class Menu {
		String title;
		ArrayList<Menu> options;
		ArrayList<String> optionDescription;
		Menu previousMenu;

		public void testMenu() {
			Menu main = new Menu("Main Menu");
			Menu yearView = new Menu("Year View");
			main.addMenuOption(yearView, "Year view");
			main.linkPreviousMenu(null);
		}

		public Menu(String newTitle) {
			this.title = newTitle;
		}

		public void addMenuOption(Menu newMenuOption, String description) {
			options.add(newMenuOption);
			optionDescription.add(description);

		}

		public void removeMenuOption(int index) {
			options.remove(index);
			optionDescription.remove(index);
		}

		public void linkPreviousMenu(Menu prev) {
			this.previousMenu = prev;
		}

		public void changeTitle(String newTitle) {
			this.title = newTitle;
		}

	}

	class MenuOption {
		String description;
	}
}
