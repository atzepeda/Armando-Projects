package edu.ncsu.csc216.pack_scheduler.course;
/**
 * Creates the Activity Object
 * 
 * @author Shalini Balagopal
 */
public abstract class Activity implements Conflict {
	/** Maximum value for military time */
	private static final int UPPER_TIME = 2400;
	/** Maximum value for hour */
	private static final int UPPER_HOUR = 60;

	/** Activity title. */
	private String title;
	/** Activity meeting days */
	private String meetingDays;
	/** Activity starting time */
	private int startTime;
	/** Activity ending time */
	private int endTime;
	
	/**
	 * Creates an Activity object with values for all fields
	 * @param title title of Activity
	 * @param meetingDays days Activity occurs
	 * @param startTime start time of Activity
	 * @param endTime end time of Activity
	 */
	public Activity(String title, String meetingDays, int startTime, int endTime) {
		setTitle(title);
		setMeetingDays(meetingDays);
		setActivityTime(startTime, endTime);
	}

	/**
	 * Gets the Activity title
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the Activity title. If the title is null or
	 * an empty string then an IllegalArgumentException is thrown.
	 * @param title the title to set
	 * @throws IllegalArgumentException if title is null or empty
	 */
	public void setTitle(String title) {
	    if (title == null) {
	        throw new IllegalArgumentException("Invalid course title");
	    }
	    if (title.length() == 0) {
	        throw new IllegalArgumentException("Invalid course title");
	    }
		this.title = title;
	}

	/**
	 * Gets the Activity's meeting days
	 * @return the meetingDays
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/**
	 * Sets the Activity's meeting days.
	 * @param meetingDays days Activity takes place
	 */
	public void setMeetingDays(String meetingDays) {
		this.meetingDays = meetingDays;
	}

	/**
	 * Gets the Activity start time
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Gets the Activity end time
	 * @return the endTime
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * Sets the Activity's start and end time. If the start time or end time
	 * are not between 0000 and 2359 or their minutes are not between 00 and 59
	 * or if the times imply the class is overnight or the start time and/or
	 * end time are listed when meeting days is 'A', then an IllegalArgumentException 
	 * is thrown.
	 * @param startTime the startTime to set
	 * @param endTime the endTime to set
	 * @throws IllegalArgumentException if startTime or endTime are less than 0000
	 * or greater than 2359 or their minutes are not between 00 and 59 or endTime is
	 * greater than startTime or either has a value other than 0 when meetingDays is A
	 */
	public void setActivityTime(int startTime, int endTime) {
		if (startTime < 0 || startTime >= UPPER_TIME) {
			throw new IllegalArgumentException("Invalid meeting times");
		}
		if (endTime < 0 || endTime >= UPPER_TIME) {
			throw new IllegalArgumentException("Invalid meeting times");
		}
		if (endTime < startTime) {
			throw new IllegalArgumentException("Invalid meeting times");
		}
		
		if (startTime % 100 >= UPPER_HOUR || endTime % 100 >= UPPER_HOUR) {
			throw new IllegalArgumentException("Invalid meeting times");
		}
		
		if (meetingDays.equals("A") && startTime != endTime) {
			throw new IllegalArgumentException("Invalid meeting times");
		}
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * Returns a String of the days the Activity takes place as well as the start and end time
	 * of the Activity in standard time format. If a Course takes place every day of the week,
	 * then "Arranged" is returned.
	 * @return the meeting day(s) and time
	 */
	public String getMeetingString() {
		if (meetingDays.equals("A")) {
			return "Arranged";
		}
        String timeOfDay = "AM";

        int startHour = startTime / 100;
        if (startTime >= 1200) {
            timeOfDay = "PM";
        }
        if (startHour >= 13) {
            startHour -= 12;
        }
        if (startHour == 0) {
            startHour += 12;
        }
        String startMin = Integer.toString(startTime % 100);
        if (Integer.valueOf(startMin) < 10) {
            startMin = "0" + startMin;
        }
        String start = startHour + ":" + startMin + timeOfDay;
        
        int endHour = endTime / 100;
        if (endTime >= 1200) {
            timeOfDay = "PM";
        }
        if (endHour >= 13) {
            endHour -= 12;
        }
        if (endHour == 0) {
            endHour += 12;
        }
        String endMin = Integer.toString(endTime % 100);
        if (Integer.valueOf(endMin) < 10) {
            endMin = "0" + endMin;
        }
        String end = endHour + ":" + endMin + timeOfDay;
		
		return meetingDays + " " + start + "-" + end;
	}
	
	/**
	 * Returns a short String array of the Activity details
	 * @return String array of Activity details
	 */
	public abstract String[] getShortDisplayArray();

	/**
	 * Returns a long String array of the Activity details
	 * @return String array of Activity details
	 */
	public abstract String[] getLongDisplayArray();
	
	/**
	 * Checks to see if an Activity exists in a catalog or schedule
	 * and returns true if it does, false otherwise.
	 * @param activity Activity to compare against existing records
	 * @return true if Activity already exists in records
	 *         false otherwise
	 */
	public abstract boolean isDuplicate(Activity activity);
	
	/**
	 * Checks for conflict and throws a ConflictException if the Activity conflicts with
	 * an Activity that already exists. Conflict exists if they share at least one day with
	 * start time and end time values overlapping.
	 * @param a Possibly Conflicting Activity that may conflict with another Activity
	 * @throws ConflictException if the two Activity Objects conflict
	 */
	@Override
	public void checkConflict(Activity a) throws ConflictException {
		String a1Days = this.getMeetingDays();
		String a2Days = a.getMeetingDays();
		int a1StartTime = this.getStartTime();
		int a2StartTime = a.getStartTime();
		int a1EndTime = this.getEndTime();
		int a2EndTime = a.getEndTime();
		
		if (a1Days.equals("A") || a2Days.equals("A")) {
			return; // exit method if meeting days are Arranged
		}
		
		for (int i = 0; i < a1Days.length(); i++) { // a1 days
	    		char c = a1Days.charAt(i);
	    		for (int j = 0; j < a2Days.length(); j++) { // a2 days
	    			if (c == a2Days.charAt(j) && a1StartTime <= a2EndTime && a2StartTime <= a1EndTime) {
	    				throw new ConflictException(); // times overlap in EITHER direction
	    			}
	    		}
	    }
	}

	/***
	 * Generates a hashCode for Activity using all fields
	 * @return hashCode for Activity
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endTime;
		result = prime * result + ((meetingDays == null) ? 0 : meetingDays.hashCode());
		result = prime * result + startTime;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/**
	 * Compares a given object to this object for equality on all fields.
	 * @param obj the Object to compare
	 * @return true if objects are the same on all fields
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (endTime != other.endTime)
			return false;
		if (meetingDays == null) {
			if (other.meetingDays != null)
				return false;
		} else if (!meetingDays.equals(other.meetingDays))
			return false;
		if (startTime != other.startTime)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}