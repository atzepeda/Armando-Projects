package edu.ncsu.csc216.pack_scheduler.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Manages the course catalog, student directory and login.
 * 
 * @author Yu yue
 *
 */
public class RegistrationManager {

	/** Registration Manager **/
	private static RegistrationManager instance;
	/** Course catalog **/
	private CourseCatalog courseCatalog;
	/** Student Directory **/
	private StudentDirectory studentDirectory;
	/** registrar **/
	private User registrar;
	/** current user **/
	private User currentUser;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	/** properties file **/
	private static final String PROP_FILE = "registrar.properties";
	/** directory of faculty members */
	private FacultyDirectory facultyDirectory;

	/**
	 * Constructor for Registration Manager
	 */
	private RegistrationManager() {
		createRegistrar();
		studentDirectory = new StudentDirectory();
		courseCatalog = new CourseCatalog();
		facultyDirectory = new FacultyDirectory();
	}

	/**
	 * Creates a Registrar to the properties file
	 */
	private void createRegistrar() {
		Properties prop = new Properties();

		try (InputStream input = new FileInputStream(PROP_FILE)) {
			prop.load(input);

			String hashPW = hashPW(prop.getProperty("pw"));

			registrar = new Registrar(prop.getProperty("first"), prop.getProperty("last"), prop.getProperty("id"),
					prop.getProperty("email"), hashPW);
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot create registrar.");
		}
	}

	/**
	 * hashes the password
	 * 
	 * @param pw
	 *            password
	 * @return String hashed password
	 */
	private String hashPW(String pw) {
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(pw.getBytes());
			return new String(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
	}

	/**
	 * gets the instance
	 * 
	 * @return instance
	 */

	public static RegistrationManager getInstance() {
		if (instance == null) {
			instance = new RegistrationManager();
		}
		return instance;
	}

	/**
	 * sets the course catalog
	 * 
	 * @param courseCatalog
	 *            course catalog
	 */
	public void setCourseCatalog(CourseCatalog courseCatalog) {
		this.courseCatalog = courseCatalog;
	}

	/**
	 * gets the course catalog
	 * 
	 * @return courseCatalog
	 */
	public CourseCatalog getCourseCatalog() {
		return courseCatalog;
	}

	/**
	 * sets the student directory
	 * 
	 * @param studentDirectory
	 *            student directory
	 */
	public void setStudentDirectory(StudentDirectory studentDirectory) {
		this.studentDirectory = studentDirectory;
	}

	/**
	 * gets the student directory
	 * 
	 * @return student directory
	 */
	public StudentDirectory getStudentDirectory() {
		return studentDirectory;
	}
	
	/**
	 * gets the faculty directory
	 * @return faculty directory
	 */
	public FacultyDirectory getFacultyDirectory() {
		return facultyDirectory;
	}

	/**
	 * Returns true or false if student or registrar can login
	 * 
	 * @param id
	 *            user id
	 * @param password
	 *            user password
	 * @return true if user can login
	 */
	public boolean login(String id, String password) {
		if (getCurrentUser() != null) { 
			return false;
		}
		Student s = studentDirectory.getStudentById(id);
		Faculty f = facultyDirectory.getFacultyById(id);
		if (s == null && f == null &&  !registrar.getId().equals(id)) {
			throw new IllegalArgumentException("User doesn't exist.");
		}
		try {
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(password.getBytes());
			String localHashPW = new String(digest.digest());
			if (s != null && s.getPassword().equals(localHashPW)) {
				currentUser = s;
				return true;
			}
			if (f != null && f.getPassword().equals(localHashPW)) {
				currentUser = f;
				return true;
			}
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException();
		}
		if (registrar.getId().equals(id)) {
			MessageDigest digest;
			try {
				digest = MessageDigest.getInstance(HASH_ALGORITHM);
				digest.update(password.getBytes());
				String localHashPW = new String(digest.digest());
				if (registrar.getPassword().equals(localHashPW)) {
					currentUser = registrar;
					return true;
				}
			} catch (NoSuchAlgorithmException e) {
				throw new IllegalArgumentException();
			}
		}

		return false;
	}

	/**
	 * sets current user equal to registar
	 */
	public void logout() {
		currentUser = null;
	}

	/**
	 * Get the User object current user
	 * 
	 * @return returns the current user
	 */
	public User getCurrentUser() {
		return currentUser;
	}

	/**
	 * Clears the data and creates empty course catalog and student directory
	 */
	public void clearData() {
		courseCatalog.newCourseCatalog();
		studentDirectory.newStudentDirectory();
		facultyDirectory.newFacultyDirectory();
	}

	/**
	 * Registrar class, child class of user
	 * 
	 * @author Yu
	 *
	 */
	private static class Registrar extends User {
		/**
		 * Create a registrar user with the user id and password in the
		 * registrar.properties file.
		 */
		public Registrar(String firstName, String lastName, String id, String email, String hashPW) {
			super(firstName, lastName, id, email, hashPW);

		}
	}
	
	/**
	 * Returns true if the logged in student can enroll in the given course.
	 * @param c Course to enroll in
	 * @return true if enrolled
	 */
	public boolean enrollStudentInCourse(Course c) {
	    if (currentUser == null || !(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        Schedule schedule = s.getSchedule();
	        CourseRoll roll = c.getCourseRoll();
	        
	        if (s.canAdd(c) && roll.canEnroll(s)) {
	            schedule.addCourseToSchedule(c);
	            roll.enroll(s);
	            return true;
	        }
	        
	    } catch (IllegalArgumentException e) {
	        return false;
	    }
	    return false;
	}

	/**
	 * Returns true if the logged in student can drop the given course.
	 * @param c Course to drop
	 * @return true if dropped
	 */
	public boolean dropStudentFromCourse(Course c) {
	    if (currentUser == null || !(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    
	    Student s = (Student)currentUser;
	    Schedule studentSchedule = s.getSchedule();
	    
	    int coursePartsMatching = 0;
	    
	    for (int i = 0; i < studentSchedule.getScheduledCourses().length; i++) {
	    		for (int j = 0; j < studentSchedule.getScheduledCourses()[0].length; j++) {
	    			if (studentSchedule.getScheduledCourses()[i][j].equals(c.getShortDisplayArray()[j])) {
	    				coursePartsMatching++;
	    			}
	    			
	    			if (coursePartsMatching == c.getShortDisplayArray().length) {
	    				try {
	    			        c.getCourseRoll().drop(s);
	    			        return s.getSchedule().removeCourseFromSchedule(c);
	    			    } catch (IllegalArgumentException e) {
	    			        return false; 
	    			    }
	    			}
	    		}
	    		coursePartsMatching = 0;
	    }
	    
	    return false; 
	}

	/**
	 * Resets the logged in student's schedule by dropping them
	 * from every course and then resetting the schedule.
	 */
	public void resetSchedule() {
	    if (currentUser == null || !(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        Schedule schedule = s.getSchedule();
	        String [][] scheduleArray = schedule.getScheduledCourses();
	        for (int i = 0; i < scheduleArray.length; i++) {
	            Course c = courseCatalog.getCourseFromCatalog(scheduleArray[i][0], scheduleArray[i][1]);
	            c.getCourseRoll().drop(s);
	        }
	        schedule.resetSchedule();
	    } catch (IllegalArgumentException e) {
	        //do nothing 
	    }
	}
	
	/**
	 * Adds a faculty member to the course
	 * @param course faculty is being added to
	 * @param faculty to be add
	 * @return true or false
	 */
	public boolean addFacultyToCourse(Course course, Faculty faculty) {
		if (currentUser == null || !currentUser.equals(registrar)) {
			throw new IllegalArgumentException();
		}
		if (currentUser != null && currentUser.equals(registrar)) {
			faculty.getSchedule().addCourseToSchedule(course);
			return true;
		}
		return false;
	}
	
	/**
	 * Removes faculty from a course
	 * @param course to remove faculty from
	 * @param faculty being removed
	 * @return true or false
	 */
	public boolean removeFacultyFromCourse(Course course, Faculty faculty) {
		if (currentUser == null || !currentUser.equals(registrar)) {
			throw new IllegalArgumentException();
		}
		if (currentUser != null && currentUser.equals(registrar)) {
			faculty.getSchedule().removeCourseFromSchedule(course);
			return true;
		}
		return false;
	}
	
	/**
	 * Resets the faculty schedule
	 * @param faculty to reset
	 */
	public void resetFacultySchedule(Faculty faculty) {
		if (currentUser == null || !currentUser.equals(registrar)) {
			throw new IllegalArgumentException();
		}
		if (currentUser != null && currentUser.equals(registrar)) {
			faculty.getSchedule().resetSchedule();
		}
	}
}