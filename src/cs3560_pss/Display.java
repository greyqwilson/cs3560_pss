package cs3560_pss;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

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

//	public void loadScheduleFromFile() {
//		Schedule schedFromFile;
//		JSONParser parser = new JSONParser();
//
//		/*
//		 * TODO
//		 * 
//		 * 1.) Filepath Parameter 2.) Conditional Statements for different task types
//		 * 3.) Return type? a.) Attributes b.) Tasks c.) Array of Tasks 4.) Make sure
//		 * everyone can run it.
//		 * 
//		 */
//
//		try {
//			JSONArray a = (JSONArray) parser.parse(new FileReader("")); // need a method for filepath
//
//			for (int i = 0; i < a.size(); i++) {
//				JSONObject task = (JSONObject) a.get(i);
//
//				String taskName = (String) task.get("Name");
//
//				String taskType = (String) task.get("Type");
//
//				Long taskStartDate = (Long) task.get("StartDate");
//
//				Long taskStartTime = (Long) task.get("StartTime");
//
//				Double taskDuration = (Double) task.get("Duration");
//
//				Long taskEndDate = (Long) task.get("EndDate");
//
//				Long taskFrequency = (Long) task.get("Frequency");
//
//				System.out.println(taskName);
//				System.out.println(taskType);
//				System.out.println(taskStartDate);
//				System.out.println(taskStartTime);
//				System.out.println(taskDuration);
//				System.out.println(taskEndDate);
//				System.out.println(taskFrequency);
//			}
//
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		// Copy relevant data to a schedule object
//		// Pass into whoever takes it and display
//
//	}

	public void writeScheduleToFile() {

	}

//	public void displayTasksforDay() {
//		TaskActivity[] tasks;
//		tasks = calendar.getTasksForDay(currentDay);
//		for (int i = 0; i < tasks.length; i++) {
//			System.out.print(formatTaskPrintout((String.valueOf(tasks[i].getStartTime())), tasks[i].getName(),
//					tasks[i].getType(), "Description:", String.valueOf(tasks[i].getEndTime())));
//		}
//	}
//
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
	private static String formatTaskPrintout(String startTime, String title, String type, String description,
			String endTime) {
		String separator = ("*----------------------------------------*\n");
		// Start time ~~Title~~
		String titleLine = String.format("|%-7s%3s~~%s~~", startTime, " ", title);
		titleLine = formatTaskLine(titleLine, separator, true);

		String typeLine = "|     (" + type + ")";
		typeLine = formatTaskLine(typeLine, separator, true);

		String descriptionLine = "";
		String descBody = "";
		int descLines = description.length() / separator.length() + 1;
		StringCharacterIterator iterator = new StringCharacterIterator(description);
		if (iterator.first() != StringCharacterIterator.DONE) {
			descriptionLine = descriptionLine.concat("|     -" + String.valueOf(iterator.first()));
			for (int i = 0; i <= descLines; i++) {
				if (i != 0)
					descriptionLine = descriptionLine.concat("|     ");
				char c;
				for (int j = 0; j < 33; j++) {
					c = iterator.next();
					if (c != StringCharacterIterator.DONE)
						descriptionLine = descriptionLine.concat(String.valueOf(c));
					else
						j = 34;
				}
				descriptionLine = formatTaskLine(descriptionLine, separator, true);
				descBody = descBody.concat(descriptionLine);
				descriptionLine = "";
			}
		} else {
			descBody = "|";
			descBody = formatTaskLine(descBody, separator, true);
		}
		// descriptionLine = formatTaskLine(descriptionLine, separator);

		String endTimeLine = "|" + endTime;
		endTimeLine = formatTaskLine(endTimeLine, separator, true);
		String s = separator + titleLine + typeLine + descBody + endTimeLine + separator;
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
				endTime = numberTimeToString(tasks.get(j).getStartTime(), false);

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

	private static String monthStringToIntString(String monthNum) {
		switch (monthNum) {
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
				String weekBeginningString = ((weekBeginningMonthDay < 10) ? "0": "") + String.valueOf(weekBeginningMonthDay);
				System.out.println(weekBeginningString);

				int weekBeginningDate = Integer.valueOf(String.valueOf(currentYear) + monthInt + weekBeginningString);
				this.displayTasksforWeek(weekBeginningDate);
				break;
			}

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

//this creat
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
