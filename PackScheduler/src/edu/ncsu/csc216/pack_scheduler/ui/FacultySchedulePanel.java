package edu.ncsu.csc216.pack_scheduler.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager;
//import edu.ncsu.csc216.pack_scheduler.ui.CourseCatalogPanel.CourseCatalogTableModel;
//import edu.ncsu.csc216.pack_scheduler.ui.StudentRegistrationPanel.CourseTableModel;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;

/**
 * Creates the faculty schedule panel
 * @author namacon
 * @author atzep
 */
public class FacultySchedulePanel extends JPanel implements ActionListener {
	/** ID number used for object serialization. */
	private static final long serialVersionUID = 1L;
	/** JTable for displaying the schedule of Courses */
	private JTable tableSchedule;
	/** JTable for displaying the course roll */
	private JTable tableRoll;
	/** TableModel for schedule */
	private CourseTableModel scheduleTableModel;
	/** TableModel for course roll */
	private StudentTableModel rollTableModel;
	
	//private Border lowerEtched;
	
	private JScrollPane scrollSchedule;
	
	private JScrollPane scrollCourseRoll;
	
	//private JScrollPane scrollRoll;
	
	private JPanel pnlCourseDetails;
	
	private JLabel lblNameTitle = new JLabel ("Name: ");
	
	private JLabel lblSectionTitle = new JLabel ("Section: ");
	
	private JLabel lblTitleTitle = new JLabel ("Title: ");
	
	private JLabel lblInstructorTitle = new JLabel ("Instructor: ");

	private JLabel lblCreditsTitle = new JLabel ("Credits: ");
	
	private JLabel lblMeetingTitle = new JLabel ("Meeting: ");
	
	private JLabel lblEnrollmentCapTitle = new JLabel ("Enrollment Cap: ");
	
	private JLabel lblOpenSeatsTitle = new JLabel ("Open Seats: ");
	
	private JLabel lblWaitlistTitle = new JLabel ("Number on Waitlist: ");
	
	private JLabel lblName = new JLabel ("");
	
	private JLabel lblSection = new JLabel ("");
	
	private JLabel lblTitle = new JLabel ("");
	
	private JLabel lblInstructor = new JLabel ("");
	
	private JLabel lblCredits = new JLabel ("");
	
	private JLabel lblMeeting = new JLabel ("");
	
	private JLabel lblEnrollmentCap = new JLabel ("");
	
	private JLabel lblOpenSeats = new JLabel ("");
	
	private JLabel lblWaitlist = new JLabel ("");
	
	//private Faculty currentUser;
	
	private JTextField txtScheduleTitle;
	
	//private TitledBorder borderSchedule;
	
	private FacultySchedule schedule;
	
	/**
	 * Creates the faculty schedule panel
	 */
	public FacultySchedulePanel() {
		super(new GridBagLayout());
		
		//Set up Faculty table
		txtScheduleTitle = new JTextField("", 20);
		scheduleTableModel = new CourseTableModel();
		tableSchedule = new JTable(scheduleTableModel);
		
		
		tableSchedule.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableSchedule.setPreferredScrollableViewportSize(new Dimension(500, 500));
		tableSchedule.setFillsViewportHeight(true);
		tableSchedule.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				String name = tableSchedule.getValueAt(tableSchedule.getSelectedRow(), 0).toString();
				String section = tableSchedule.getValueAt(tableSchedule.getSelectedRow(), 1).toString();
				Course c = RegistrationManager.getInstance().getCourseCatalog().getCourseFromCatalog(name, section);
				updateCourseDetails(c);
			}
		});
		
		
		
		scrollSchedule = new JScrollPane(tableSchedule, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		Border lowerEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder border = BorderFactory.createTitledBorder(lowerEtched, "Faculty Schedule");
		scrollSchedule.setBorder(border);
		scrollSchedule.setToolTipText("Faculty Schedule");
		
		//Set up the course details panel
		pnlCourseDetails = new JPanel();
		pnlCourseDetails.setLayout(new GridLayout(6, 1));
		JPanel pnlName = new JPanel(new GridLayout(1, 4));
		pnlName.add(lblNameTitle);
		pnlName.add(lblName);
		pnlName.add(lblSectionTitle);
		pnlName.add(lblSection);
		
		JPanel pnlTitle = new JPanel(new GridLayout(1, 1));
		pnlTitle.add(lblTitleTitle);
		pnlTitle.add(lblTitle);
		
		JPanel pnlInstructor = new JPanel(new GridLayout(1, 4));
		pnlInstructor.add(lblInstructorTitle);
		pnlInstructor.add(lblInstructor);
		pnlInstructor.add(lblCreditsTitle);
		pnlInstructor.add(lblCredits);
		
		JPanel pnlMeeting = new JPanel(new GridLayout(1, 1));
		pnlMeeting.add(lblMeetingTitle);
		pnlMeeting.add(lblMeeting);
		
		JPanel pnlEnrollment = new JPanel(new GridLayout(1, 4));
		pnlEnrollment.add(lblEnrollmentCapTitle);
		pnlEnrollment.add(lblEnrollmentCap);
		pnlEnrollment.add(lblOpenSeatsTitle);
		pnlEnrollment.add(lblOpenSeats);
		
		JPanel pnlWaitlist = new JPanel(new GridLayout(1, 2));
		pnlWaitlist.add(lblWaitlistTitle);
		pnlWaitlist.add(lblWaitlist);
		
		pnlCourseDetails.add(pnlName);
		pnlCourseDetails.add(pnlTitle);
		pnlCourseDetails.add(pnlInstructor);
		pnlCourseDetails.add(pnlMeeting);
		pnlCourseDetails.add(pnlEnrollment);
		pnlCourseDetails.add(pnlWaitlist);
		
		TitledBorder borderCourseDetails = BorderFactory.createTitledBorder(lowerEtched, "Course Details");
		pnlCourseDetails.setBorder(borderCourseDetails);
		pnlCourseDetails.setToolTipText("Course Details");
		
		//Set up Course Roll table
		txtScheduleTitle = new JTextField("", 20);
		rollTableModel = new StudentTableModel(/** course */);
		tableRoll = new JTable(rollTableModel);
		tableRoll.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableRoll.setPreferredScrollableViewportSize(new Dimension(500, 500));
		tableRoll.setFillsViewportHeight(true);
				
		scrollCourseRoll = new JScrollPane(tableRoll, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				
		Border lowerEtchedTwo = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder borderTwo = BorderFactory.createTitledBorder(lowerEtchedTwo, "Course Roll");
		scrollCourseRoll.setBorder(borderTwo);
		scrollCourseRoll.setToolTipText("Course Roll");
		
		updateTables();
		
		//add to window
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.BOTH;
		this.add(scrollSchedule, c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.BOTH;
		add(pnlCourseDetails, c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.BOTH;
		add(scrollCourseRoll, c);
	}
	
	/**
	 * Creates the course table 
	 * @author nmaco
	 * @author atzep
	 */
	private class CourseTableModel extends AbstractTableModel {

		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		/** Column names for the table */
		private String [] columnNames = {"Name", "Section", "Title", "Meeting Days", "Open Seats"};
		/** Data stored in the table */
		private Object [][] data;
		
		/**
		 * Constructs the {@link CourseCatalogTableModel} by requesting the latest information
		 * from the {@link RequirementTrackerModel}.
		 */
		public CourseTableModel() {
			updateData();
		}
		
		/**
		 * Returns the number of columns in the table.
		 * @return the number of columns in the table.
		 */
		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		/**
		 * Returns the number of rows in the table.
		 * @return the number of rows in the table.
		 */
		@Override
		public int getRowCount() {
			if (data == null) 
				return 0;
			return data.length;
		}
		
		/**
		 * Returns the column name at the given index.
		 * @return the column name at the given column.
		 */
		public String getColumnName(int col) {
			return columnNames[col];
		}

		/**
		 * Returns the data at the given {row, col} index.
		 * @return the data at the given location.
		 */
		@Override
		public Object getValueAt(int row, int col) {
			if (data == null)
				return null;
			return data[row][col];
		}
		
		/**
		 * Sets the given value to the given {row, col} location.
		 * @param value Object to modify in the data.
		 * @param row location to modify the data.
		 * @param column location to modify the data.
		 */
		public void setValueAt(Object value, int row, int col) {
			data[row][col] = value;
			fireTableCellUpdated(row, col);
		}
		
		/**
		 * Updates the given model with {@link Course} information from the {@link CourseCatalog}.
		 */
		public void updateData() {
			Faculty currentUser = (Faculty)RegistrationManager.getInstance().getCurrentUser();
			if(currentUser != null) {
				schedule = currentUser.getSchedule();
				txtScheduleTitle.setText("Faculty Schedule");
//				borderSchedule = BorderFactory.createTitledBorder(lowerEtched, "Faculty Schedule");
//				scrollSchedule = new JScrollPane(tableSchedule, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				data = schedule.getScheduledCourses();
				
				FacultySchedulePanel.this.repaint();
				FacultySchedulePanel.this.validate();
			}
		}
	}
	
	/**
	 * Creates the student table
	 * @author nmaco
	 * @author atzep
	 */
	private class StudentTableModel extends AbstractTableModel {

		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		/** Column names for the table */
		private String [] columnNames = {"First Name", "Last Name", "Student ID"};
		/** Data stored in the table */
		private Object [][] data;
		
		/**
		 * Constructs the {@link CourseCatalogTableModel} by requesting the latest information
		 * from the {@link RequirementTrackerModel}.
		 */
		public StudentTableModel(/** Course c */) {
			//this.updateData(c);
		}
		
		/**
		 * Returns the number of columns in the table.
		 * @return the number of columns in the table.
		 */
		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		/**
		 * Returns the number of rows in the table.
		 * @return the number of rows in the table.
		 */
		@Override
		public int getRowCount() {
			if (data == null) 
				return 0;
			return data.length;
		}
		
		/**
		 * Returns the column name at the given index.
		 * @return the column name at the given column.
		 */
		public String getColumnName(int col) {
			return columnNames[col];
		}

		/**
		 * Returns the data at the given {row, col} index.
		 * @return the data at the given location.
		 */
		@Override
		public Object getValueAt(int row, int col) {
			if (data == null)
				return null;
			return data[row][col];
		}
		
		/**
		 * Sets the given value to the given {row, col} location.
		 * @param value Object to modify in the data.
		 * @param row location to modify the data.
		 * @param column location to modify the data.
		 */
		public void setValueAt(Object value, int row, int col) {
			data[row][col] = value;
			fireTableCellUpdated(row, col);
		}
		
		/**
		 * Updates the given model with {@link Course} information from the {@link CourseCatalog}.
		 */
		public void updateData( Course c ) {
			data = c.getCourseRoll().get2DArrayRoll();
			
			FacultySchedulePanel.this.repaint();
			FacultySchedulePanel.this.validate();
		}
	}

	/**
	 * performs the action
	 * @param event the action event
	 */
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * updates the tables in the gui
	 */
	public void updateTables() {
		scheduleTableModel.updateData();
	}
	
	private void updateCourseDetails(Course c) {
		if (c != null) {
			lblName.setText(c.getName());
			lblSection.setText(c.getSection());
			lblTitle.setText(c.getTitle());
			lblInstructor.setText(c.getInstructorId());
			lblCredits.setText("" + c.getCredits());
			lblMeeting.setText(c.getMeetingString());
			lblEnrollmentCap.setText("" + c.getCourseRoll().getEnrollmentCap());
			lblOpenSeats.setText("" + c.getCourseRoll().getOpenSeats());
			lblWaitlist.setText("" + c.getCourseRoll().getNumberOnWaitlist());
			rollTableModel.updateData(c);
		}
	}
}
