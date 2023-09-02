package edu.ncsu.csc216.turn_taker.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import edu.ncsu.csc216.turn_taker.model.manager.TurnTakerManager;
import edu.ncsu.csc216.turn_taker.model.turns.TurnActivity;
import edu.ncsu.csc216.turn_taker.model.turns.TurnTaker;

/**
 * Turn Taker GUI. The GUI is made possible by provided helps from course
 * teaching fellows, reference from course lectures, and Java API with specific
 * Java GUI component document.
 * 
 * @author wnwang, atzepeda 
 */
public class TurnTakerGUI extends JFrame implements ActionListener, Observer {

	/**
	 * Class based on Oracle(c) reference and example from Java API and GUI
	 * component document
	 * 
	 * @author wnwang, atzepeda
	 */
	class SharedListSelectionHandler implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			ListSelectionModel lsm = (ListSelectionModel) e.getSource();

			// int index = e.getFirstIndex();
			// addTurnActivity = new JButton("Add Turn Activity");

			int minIndex = lsm.getMinSelectionIndex();
			int maxIndex = lsm.getMaxSelectionIndex();
			for (int i = minIndex; i <= maxIndex; i++) {
				if (lsm.isSelectedIndex(i)) {
					// addTurnActivity.setText(Integer.toString(i));
					turnActivityNameTextField.setText(turnActivityList.getModel().getElementAt(i).toString());
					turnActivityDescriptionTextArea.setText(
							((TurnActivity) turnActivityList.getModel().getElementAt(i)).getTurnActivityDescription());
					turnActivityNameTextFieldNextTurnPanel
							.setText(turnActivityList.getModel().getElementAt(i).toString());
					nextTurnTakerTextField.setText(((TurnActivity) turnActivityList.getModel().getElementAt(i))
							.getTurnTakers().nextTurnTaker());
					turnActivityNameTextFieldTop.setText(turnActivityList.getModel().getElementAt(i).toString());
					// Integer.toString(i));
					// output.append(" " + i);
					// String[][] abc = new String[((TurnActivity)
					// turnActivityList.getModel().getElementAt(i)).getTurnTakers().getTurnTakersAsArray();
					turnTakerTableModel.setDataVector(((TurnActivity) turnActivityList.getModel().getElementAt(i))
							.getTurnTakers().getTurnTakersAsArray(), columnNames);
					// turnTakerTableModel.setValueAt("ab", 1, 1);

				}
			}

			// int firstIndex = e.getFirstIndex();
			// int lastIndex = e.getLastIndex();
			// boolean isAdjusting = e.getValueIsAdjusting();
			// output.append("Event for indexes "
			// + firstIndex + " - " + lastIndex
			// + "; isAdjusting is " + isAdjusting
			// + "; selected indexes:");
			//
			// if (lsm.isSelectionEmpty()) {
			// output.append(" <none>");
			// } else {
			// // Find out which indexes are selected.
			// int minIndex = lsm.getMinSelectionIndex();
			// int maxIndex = lsm.getMaxSelectionIndex();
			// for (int i = minIndex; i <= maxIndex; i++) {
			// if (lsm.isSelectedIndex(i)) {
			// output.append(" " + i);
			// }
			// }
			// }
			// output.append(newline);
			// output.setCaretPosition(output.getDocument().getLength());

		}

	}

	/**
	 * The TurnTakersPanel in charge of the Turn Takers Panel in the GUI. Given the
	 * capability to modify the list of TurnTaker objects.
	 * 
	 * @author wnwang, atzepeda
	 *
	 */
	private class TurnTakersPanel extends JPanel implements ActionListener {

		/**
		 * Auto generated serialVersionUID.
		 */
		private static final long serialVersionUID = -8413601140287080900L;

		/**
		 * The constructor of the panel contains all compoenent and functionality of the
		 * panel.
		 */
		public TurnTakersPanel() {
			this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Turn Takers"));

			JPanel west = new JPanel();
			JPanel east = new JPanel();

			west.setPreferredSize(new Dimension(300, 244));
			east.setPreferredSize(new Dimension(172, 230));

			// west.setBackground(Color.WHITE);
			// east.setBackground(Color.BLUE);

			this.add(west, BorderLayout.WEST);
			this.add(east, BorderLayout.EAST);

			// TODO MODEL

			// JList turnActivityList = new JList();
			// turnTakerListModel.addElement(new TurnTaker("abc"));
			// turnTakerListModel.addElement(new TurnTaker("bcs"));
			// turnTakerListModel.addElement(new TurnTaker("bcs"));
			turnTakerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			JScrollPane jScrollPane = new JScrollPane(turnTakerList);
			jScrollPane.setPreferredSize(new Dimension(299, 239));
			west.add(jScrollPane);

			east.setLayout(new GridLayout(5, 0));

			east.add(addB);
			east.add(removeB);
			east.add(moveUpB);
			east.add(moveDownB);

			addB.addActionListener(this);
			removeB.addActionListener(this);
			moveUpB.addActionListener(this);
			moveDownB.addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TurnTakerManager instance = TurnTakerManager.getInstance();
			int listSelectedIdx = turnTakerList.getSelectedIndex();

			if (e.getSource().equals(moveUpB)) {
				if (turnTakerList.getSelectedIndex() != -1) {
					try {
						temp.getTurnTakers().moveUp(listSelectedIdx);
						String move = turnTakerListModel.getElementAt(listSelectedIdx);
						turnTakerListModel.setElementAt(turnTakerListModel.getElementAt(listSelectedIdx - 1),
								listSelectedIdx);
						turnTakerListModel.setElementAt(move, listSelectedIdx - 1);
						turnTakerList.setSelectedIndex(listSelectedIdx - 1);
					} catch (Exception exc) {

						JOptionPane.showMessageDialog(this, "Selected turn taker already on the top of the list.",
								"Warning", JOptionPane.WARNING_MESSAGE);
					}

				} else {
					JOptionPane.showMessageDialog(this, "No turn taker selected", "Error", JOptionPane.ERROR_MESSAGE);
				}

			}

			if (e.getSource().equals(moveDownB)) {
				if (turnTakerList.getSelectedIndex() != -1) {
					try {
						temp.getTurnTakers().moveDown(listSelectedIdx);
						String move = turnTakerListModel.getElementAt(listSelectedIdx);
						turnTakerListModel.setElementAt(turnTakerListModel.getElementAt(listSelectedIdx + 1),
								listSelectedIdx);
						turnTakerListModel.setElementAt(move, listSelectedIdx + 1);
						turnTakerList.setSelectedIndex(listSelectedIdx + 1);
					} catch (Exception exc) {

						JOptionPane.showMessageDialog(this, "Selected turn taker already on the buttom of the list.",
								"Warning", JOptionPane.WARNING_MESSAGE);
					}

				} else {
					JOptionPane.showMessageDialog(this, "No turn taker selected", "Error", JOptionPane.ERROR_MESSAGE);
				}

			}

			if (e.getSource().equals(removeB)) {
				if (turnTakerList.getSelectedIndex() != -1) {
					temp.removeTurnTaker(turnTakerListModel.getElementAt(listSelectedIdx));
					turnTakerListModel.removeElementAt(listSelectedIdx);
					// temp.removeTurnTaker((TurnTaker)(turnTakerList.getModel().getElementAt(listSelectedIdx)));
					// (TurnTaker)(turnTakerList.getModel().getElementAt(listSelectedIdx).
					if (!(temp.getTurnTakers().getTurnTakersAsArray().length >= 2))
						addTurnActivityB.setEnabled(false);
				} else {
					JOptionPane.showMessageDialog(this, "No turn taker selected", "Error", JOptionPane.ERROR_MESSAGE);
				}

			}
			if (e.getSource().equals(addB)) {
				// instance.getTurnActivityList().addTurnActivity(t);
				// NEW TURN ACTIVITY ADD TURN ACT
				boolean exception = false;
				if (temp == null)
					try {
						temp = new TurnActivity(turnActivityNameTFAddP.getText(),
								turnActivityDescriptionTAAddP.getText());
					} catch (Exception exc) {
						JOptionPane.showMessageDialog(this, "Activity name and description cannot be empty", "Warning",
								JOptionPane.WARNING_MESSAGE);
						temp = null;
						exception = true;
					}
				if (!exception) {

					try {
						String[] buttonInDilog = { "OK" };
						JPanel panel = new JPanel();
						JLabel pmpt = new JLabel("Enter the name of the turn taker: ");
						JTextField input = new JTextField(10);
						panel.add(pmpt);
						panel.add(input);
						int selectedOption = JOptionPane.showOptionDialog(this, panel, "Add Turn Taker",
								JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttonInDilog,
								buttonInDilog[0]);
						if (selectedOption == 0) {
							temp.getTurnTakers().addTurnTaker(new TurnTaker(input.getText()));
							turnTakerListModel.addElement(input.getText());
							// temp.addTurnTaker(new TurnTaker(input.getText()));
							// instance.getTurnActivityList().getTurnActivity(turnActivityList.getSelectedIndex())
							// .addTurnTaker(new TurnTaker(input.getText()));
							// input.getText().
						}
					} catch (Exception exc) {
						JOptionPane.showMessageDialog(this, "Turn taker name cannot be empty",
								"Empty/Duplicated turn taker name", JOptionPane.ERROR_MESSAGE);
					}
					if (temp.getTurnTakers().getTurnTakersAsArray().length >= 2)
						addTurnActivityB.setEnabled(true);
					// turnTakerListModel.addElement(new TurnTaker("gsadsaga"));

				}
				// turnTakerListModel.clear();
			}

		}

	}

	/**
	 * The private class includes all implementation of the panel with all component
	 * and action as necessary. The class is composed with references from lecture
	 * notes and other GUI components document from Java API.
	 * 
	 * @author wnwang, atzepeda
	 *
	 */
	private class TurnOrderPanel extends JPanel implements ActionListener {

		/**
		 * Auto generated serialVersionUID.
		 */
		private static final long serialVersionUID = -8397621500843840663L;

		/**
		 * The constructor of the panel contains all compoenent and functionality of the
		 * panel.
		 */
		public TurnOrderPanel() {
			this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Turn Order"));

			JPanel north = new JPanel();
			this.add(north, BorderLayout.NORTH);
			// north.setBackground(Color.RED);
			north.setPreferredSize(new Dimension(456, 30));

			JLabel turnActivityNameL = new JLabel("Turn Activity Name");

			turnActivityNameTextFieldTop.setPreferredSize(new Dimension(322, 25));

			north.add(turnActivityNameL);
			turnActivityNameTextFieldTop.setEditable(false);
			north.add(turnActivityNameTextFieldTop);

			JPanel south = new JPanel();
			this.add(south, BorderLayout.SOUTH);
			// south.setBackground(Color.GREEN);
			south.setPreferredSize(new Dimension(465, 270));

			// TODO MODEL

			turnTakerTableModel.setColumnIdentifiers(columnNames);
			// jTable.setEnabled(false);
			jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jTable.setCellSelectionEnabled(false);
			jTable.setFocusable(false);
			jTable.setRowSelectionAllowed(true);
			// jTable.ed

			JScrollPane jScrollPane = new JScrollPane(jTable);
			jScrollPane.setPreferredSize(new Dimension(329, 256));

			south.add(jScrollPane, BorderLayout.WEST);

			JPanel east = new JPanel();
			south.add(east, BorderLayout.EAST);
			east.setLayout(new GridLayout(9, 0));
			// east.setBackground(Color.GRAY);
			east.add(add);
			east.add(remove);
			east.add(moveUp);
			east.add(moveDown);

			add.addActionListener(this);
			remove.addActionListener(this);
			moveUp.addActionListener(this);
			moveDown.addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			TurnTakerManager instance = TurnTakerManager.getInstance();
			int selectedIdx = jTable.getSelectedRow();
			int idx = turnActivityList.getSelectedIndex();
			if (e.getSource().equals(moveUp)) {
				if (selectedIdx != -1) {
					try {
						instance.getTurnActivityList().getTurnActivity(turnActivityList.getSelectedIndex())
								.getTurnTakers().moveUp(selectedIdx);
						// turnActivityNameTextField.setText("");
						// turnActivityDescriptionTextArea.setText("");
						// turnActivityNameTF_NTP.setText("");
						// nextTurnTakerTextField.setText("");
						// turnActivityNameTF_TOP.setText("");
						// turnTakerTableModel.setDataVector(null, columnNames);
						// instance.getTurnActivityList().getTurnActivity(turnActivityList.getSelectedIndex()).takeTurn();
						// jTable.selec
						turnActivityList.setSelectedIndex(idx);
					} catch (Exception exc) {
						JOptionPane.showMessageDialog(this, "Selected turn taker already on the top of the list.",
								"Warning", JOptionPane.WARNING_MESSAGE);
					}
				} else
					JOptionPane.showMessageDialog(this, "No turn taker selected", "Error", JOptionPane.ERROR_MESSAGE);
				// }
			}

			if (e.getSource().equals(moveDown)) {
				if (selectedIdx != -1) {
					try {
						instance.getTurnActivityList().getTurnActivity(turnActivityList.getSelectedIndex())
								.getTurnTakers().moveDown(selectedIdx);
						turnActivityList.setSelectedIndex(idx);
					} catch (Exception exc) {
						JOptionPane.showMessageDialog(this, "Selected turn taker already on the buttom of the list.",
								"Warning", JOptionPane.WARNING_MESSAGE);
					}
				} else
					JOptionPane.showMessageDialog(this, "No turn taker selected", "Error", JOptionPane.ERROR_MESSAGE);
			}

			if (e.getSource().equals(add)) {
				if (idx != -1) {
					try {
						String[] buttonInDilog = { "OK" };
						JPanel panel = new JPanel();
						JLabel pmpt = new JLabel("Enter the name of the turn taker: ");
						JTextField input = new JTextField(10);
						panel.add(pmpt);
						panel.add(input);
						int selectedOption = JOptionPane.showOptionDialog(this, panel, "Add Turn Taker",
								JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttonInDilog,
								buttonInDilog[0]);
						if (selectedOption == 0) {
							instance.getTurnActivityList().getTurnActivity(turnActivityList.getSelectedIndex())
									.addTurnTaker(new TurnTaker(input.getText()));
							// input.getText().
						}
					} catch (Exception exc) {
						JOptionPane.showMessageDialog(this, "Turn taker name cannot be empty",
								"Empty/Duplicated turn taker name", JOptionPane.ERROR_MESSAGE);
					}
					turnActivityList.setSelectedIndex(idx);
				} else
					JOptionPane.showMessageDialog(this, "No activity selected to add the turn taker to", "Error",
							JOptionPane.ERROR_MESSAGE);
			}

			if (e.getSource().equals(remove)) {

				if (selectedIdx != -1) {
					if (instance.getTurnActivityList().getTurnActivity(turnActivityList.getSelectedIndex())
							.getTurnTakers().getTurnTakersAsArray().length <= 2) {
						JOptionPane.showMessageDialog(this,
								"Invalid data operation.\n\"[S3] A valid Turn Taker data file has at least one activity and each activity has at least two turn takers.\"",
								"Warning", JOptionPane.WARNING_MESSAGE);
					} else {
						instance.getTurnActivityList().getTurnActivity(turnActivityList.getSelectedIndex())
								.removeTurnTaker(instance.getTurnActivityList()
										.getTurnActivity(turnActivityList.getSelectedIndex()).getTurnTakers()
										.getTurnTakersAsArray()[selectedIdx][0]);
						turnActivityList.setSelectedIndex(idx);
					}
				} else
					JOptionPane.showMessageDialog(this, "No turn taker selected", "Error", JOptionPane.ERROR_MESSAGE);

			}

		}

	}

	/**
	 * The private class includes all implementation of the panel with all component
	 * and action as necessary. The class is composed with references from lecture
	 * notes and other GUI components document from Java API.
	 * 
	 * @author wnwang, atzepeda
	 *
	 */
	private class NextTurnPanel extends JPanel implements ActionListener {

		/**
		 * Auto generated serialVersionUID.
		 */
		private static final long serialVersionUID = -5048008212180383600L;

		/**
		 * The constructor of the panel contains all compoenent and functionality of the
		 * panel.
		 */
		public NextTurnPanel() {
			this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Next Turn"));

			JLabel turnActivityNameL = new JLabel("Turn Activity Name");

			turnActivityNameTextFieldNextTurnPanel.setPreferredSize(new Dimension(321, 25));
			nextTurnTakerTextField.setPreferredSize(new Dimension(230, 25));
			takeTurn.setPreferredSize(new Dimension(100, 30));

			// TODO MODEL
			this.add(turnActivityNameL);
			turnActivityNameTextFieldNextTurnPanel.setEditable(false);
			this.add(turnActivityNameTextFieldNextTurnPanel);
			this.add(nextTurnTakerLabel);
			nextTurnTakerTextField.setEditable(false);
			this.add(nextTurnTakerTextField);
			this.add(takeTurn);

			takeTurn.addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			TurnTakerManager instance = TurnTakerManager.getInstance();
			int idx = turnActivityList.getSelectedIndex();
			if (e.getSource().equals(takeTurn)) {
				if (turnActivityList.getSelectedIndex() != -1) {
					// turnActivityNameTextField.setText("");
					// turnActivityDescriptionTextArea.setText("");
					// turnActivityNameTF_NTP.setText("");
					// nextTurnTakerTextField.setText("");
					// turnActivityNameTF_TOP.setText("");
					// turnTakerTableModel.setDataVector(null, columnNames);
					instance.getTurnActivityList().getTurnActivity(turnActivityList.getSelectedIndex()).takeTurn();
					turnActivityList.setSelectedIndex(idx);
				} else
					JOptionPane.showMessageDialog(this, "No activity selected", "Error", JOptionPane.ERROR_MESSAGE);
			}

		}

	}

	/**
	 * The private class includes all implementation of the panel with all component
	 * and action as necessary. The class is composed with references from lecture
	 * notes and other GUI components document from Java API.
	 * 
	 * @author wnwang, atzepeda
	 *
	 */
	private class TurnActivityDetailsPanel extends JPanel implements ActionListener, FocusListener {

		/**
		 * Auto generated serialVersionUID.
		 */
		private static final long serialVersionUID = -275230554353863018L;

		/**
		 * The constructor of the panel contains all compoenent and functionality of the
		 * panel.
		 */
		public TurnActivityDetailsPanel() {
			this.setBorder(
					BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Turn Activity Details"));
			// this.setLayout(new GridLayout(2, 0));

			// TODO MODEL
			this.add(turnActivityNameLabel);
			this.add(turnActivityNameTextField);
			turnActivityNameTextField.setPreferredSize(new Dimension(358, 23));
			this.add(turnActivityDescriptionLabel);
			// this.add(turnActivityDescriptionTextArea);
			// turnActivityDescriptionTextArea.setPreferredSize(new Dimension(325, 23));
			JScrollPane jScrollPane = new JScrollPane(turnActivityDescriptionTextArea);
			this.add(jScrollPane);
			jScrollPane.setPreferredSize(new Dimension(325, 90));

			turnActivityNameTextField.addFocusListener(this);
			turnActivityDescriptionTextArea.addFocusListener(this);

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void focusGained(FocusEvent e) {
			if (turnActivityList.getSelectedIndex() != -1) {
				editTurnActivity.setEnabled(true);
			}
		}

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub

		}

	}

	/**
	 * The private class includes all implementation of the panel with all component
	 * and action as necessary. The class is composed with references from lecture
	 * notes and other GUI components document from Java API.
	 * 
	 * @author wnwang, atzepeda
	 *
	 */
	private class TurnActivitiesPanel extends JPanel implements ActionListener {

		/**
		 * Auto generated serialVersionUID.
		 */
		private static final long serialVersionUID = -6734794056215530238L;

		// private DefaultListModel<TurnActivity> turnActivityListModel = new
		// DefaultListModel<TurnActivity>();

		// TODO PUT ALL FIELD TO THE CLASS
		private JFrame jFrame;
		private JPanel north;
		private JPanel east;
		private JScrollPane jScrollPane;

		/**
		 * The constructor of the panel contains all compoenent and functionality of the
		 * panel.
		 */
		public TurnActivitiesPanel() {
			this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Turn Activities"));

			north = new JPanel();
			this.add(north, BorderLayout.NORTH);
			north.setPreferredSize(new Dimension(500, 250));
			// north.setBackground(Color.YELLOW);

			// TODO MODEL

			// updateData();
			turnActivityList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jScrollPane = new JScrollPane(turnActivityList);
			jScrollPane.setPreferredSize(new Dimension(330, 243));
			north.add(jScrollPane, BorderLayout.WEST);

			turnActivityListSelectionModel.addListSelectionListener(new SharedListSelectionHandler());

			east = new JPanel();
			north.add(east, BorderLayout.EAST);
			// east.setBackground(Color.BLUE);

			addTurnActivity.addActionListener(this);
			removeTurnActivity.addActionListener(this);
			editTurnActivity.addActionListener(this);

			editTurnActivity.setEnabled(false);

			east.setLayout(new GridLayout(8, 0));
			east.add(addTurnActivity);
			east.add(removeTurnActivity);
			east.add(editTurnActivity);

			turnActivityDetailsPanel = new TurnActivityDetailsPanel();
			this.add(turnActivityDetailsPanel, BorderLayout.SOUTH);
			turnActivityDetailsPanel.setPreferredSize(new Dimension(500, 156));
			// turnActivityDetailsPanel.setBackground(Color.RED);
			jFrame = new JFrame();
			jFrame.setResizable(false);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			TurnTakerManager instance = TurnTakerManager.getInstance();

			if (e.getSource().equals(addTurnActivity)) {
				if (jFrame.isVisible()) {
					jFrame.requestFocus();
				} else {
					jFrame = new JFrame();
					jFrame.setResizable(false);
					jFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
					jFrame.setSize(new Dimension(500, 500));
					jFrame.setLocation(550, 50);
					jFrame.setTitle("Add Turn Activity");
					jFrame.setVisible(true);

					JPanel northLocal = new JPanel();
					JPanel south = new JPanel();

					jFrame.add(northLocal, BorderLayout.NORTH);
					jFrame.add(south, BorderLayout.SOUTH);

					northLocal.setPreferredSize(new Dimension(500, 131));
					south.setPreferredSize(new Dimension(500, 330));

					// north.setBackground(Color.BLUE);
					// south.setBackground(Color.RED);

					JLabel turnActivityNameL = new JLabel("Turn Activity Name");
					turnTakerListModel.clear();
					turnActivityNameTFAddP = new JTextField("");
					turnActivityNameTFAddP.setPreferredSize(new Dimension(322, 25));
					turnActivityDescriptionTAAddP = new JTextArea("");
					northLocal.add(turnActivityNameL);
					northLocal.add(turnActivityNameTFAddP);

					JLabel turnActivityDescriptionL = new JLabel("Turn Activity Description");

					northLocal.add(turnActivityDescriptionL);
					JScrollPane jScrollPaneLocal = new JScrollPane(turnActivityDescriptionTAAddP);
					jScrollPaneLocal.setPreferredSize(new Dimension(290, 90));
					northLocal.add(jScrollPaneLocal);

					// turnTakersPanel = new TurnTakersPanel();
					south.add(turnTakersPanel);
					turnTakersPanel.setPreferredSize(new Dimension(490, 270));
					// turnTakersPanel.setBackground(Color.GREEN);

					JPanel jPanel = new JPanel();
					jPanel.setPreferredSize(new Dimension(500, 50));
					jPanel.setLayout(new GridLayout(0, 2));
					south.add(jPanel);
					// jPanel.setBackground(Color.ORANGE);
					addTurnActivityB.setEnabled(false);
					jPanel.add(addTurnActivityB);
					jPanel.add(cancelB);

					// try {
					addTurnActivityB.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							// turnTakersPanel.re
							try {
								instance.getTurnActivityList().addTurnActivity(temp);
								itemSaveFile.setEnabled(true);
							} catch (NullPointerException exp) {
								itemSaveFile.setEnabled(true);
							} catch (IllegalArgumentException exp) {
								turnActivityDescriptionTAAddP = new JTextArea();
								turnActivityNameTFAddP = new JTextField();
								turnTakerListModel.clear();
								temp = null;
								jFrame.removeAll();
								jFrame.setVisible(false);
								jFrame.dispose();
								// illAE = true;
								JOptionPane.showMessageDialog(turnActivitiesPanel, "Activity name must be unique",
										"Warning", JOptionPane.WARNING_MESSAGE);
							}

							turnActivityDescriptionTAAddP = new JTextArea();
							turnActivityNameTFAddP = new JTextField();
							turnTakerListModel.clear();
							temp = null;
							jFrame.removeAll();
							jFrame.setVisible(false);
							jFrame.dispose();
							// repaint();
						}
					});
					// if (illAE) {
					// illAE = false;
					// }
					// } catch (IllegalArgumentException exp) {
					// JOptionPane.showInternalMessageDialog(this, "Activity name must be unique",
					// "Warning",
					// JOptionPane.WARNING_MESSAGE);
					//
					// }

					cancelB.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							// turnTakersPanel.re
							turnActivityDescriptionTAAddP = new JTextArea();
							turnActivityNameTFAddP = new JTextField();
							turnTakerListModel.clear();
							temp = null;
							jFrame.removeAll();
							jFrame.setVisible(false);
							jFrame.dispose();
							// repaint();
						}
					});
				}
			}
			if (e.getSource().equals(editTurnActivity)) {

				int listSelectedIdx = turnActivityList.getSelectedIndex();
				boolean duplicated = false;
				if (turnActivityList.getSelectedIndex() != -1) {
					// if
					// (instance.getTurnActivityList().getTurnActivity(listSelectedIdx).compareTo(activity))
					try {
						for (int i = 0; i < instance.getTurnActivityList().size(); i++) {
							if (i != listSelectedIdx && (new TurnActivity(turnActivityNameTextField.getText(),
									turnActivityDescriptionTextArea.getText())
											.compareTo(instance.getTurnActivityList().getTurnActivity(i)) == 0))
								// if (new TurnActivity(turnActivityNameTextField.getText(),
								// turnActivityDescriptionTextArea.getText())
								// .compareTo(instance.getTurnActivityList().getTurnActivity(i)) == 0)
								duplicated = true;
						}
						if (duplicated)
							JOptionPane.showMessageDialog(this, "Activity name must be unique", "Warning",
									JOptionPane.WARNING_MESSAGE);
						else
							instance.getTurnActivityList().getTurnActivity(listSelectedIdx).editTurnActivity(
									turnActivityNameTextField.getText(), turnActivityDescriptionTextArea.getText());
						updateData();
						turnActivityList.setSelectedIndex(listSelectedIdx);
						turnActivityList.requestFocus();
						editTurnActivity.setEnabled(false);
					} catch (IllegalArgumentException iae) {
						JOptionPane.showMessageDialog(this, "Activity name and description cannot be empty", "Warning",
								JOptionPane.WARNING_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(this, "No activity selected", "Error", JOptionPane.ERROR_MESSAGE);
					editTurnActivity.setEnabled(false);
				}

			}

			if (e.getSource().equals(removeTurnActivity)) {
				if (turnActivityList.getSelectedIndex() != -1) {

					if (instance.getTurnActivityList().size() <= 1) {
						JOptionPane.showMessageDialog(this,
								"Invalid data operation.\n\"[S3] A valid Turn Taker data file has at least one activity and each activity has at least two turn takers.\"",
								"Warning", JOptionPane.WARNING_MESSAGE);
					} else {
						turnActivityNameTextField.setText("");
						turnActivityDescriptionTextArea.setText("");
						turnActivityNameTextFieldNextTurnPanel.setText("");
						nextTurnTakerTextField.setText("");
						turnActivityNameTextFieldTop.setText("");
						turnTakerTableModel.setDataVector(null, columnNames);
						instance.getTurnActivityList().removeTurnActivity(turnActivityList.getSelectedIndex());
						editTurnActivity.setEnabled(false);
						itemSaveFile.setEnabled(true);
					}
				} else
					JOptionPane.showMessageDialog(this, "No activity selected", "Error", JOptionPane.ERROR_MESSAGE);
			}

		}

		/**
		 * The method will update and refresh all data of all included model when
		 * called.
		 */
		public void updateData() {
			// TODO Auto-generated method stub
			// GET THE LIST- DO ^ turnActivityListModel = new
			// DefaultListModel<TurnActivity>();

			// turnActivityListModel = new DefaultListModel<TurnActivity>();
			// //UPDATEDATA METHOD TurnTakerManager instance =
			// TurnTakerManager.getInstance();
			// TurnTakerManager instance = TurnTakerManager.getInstance();
			// for (int i = 0; i < instance.getTurnActivityList().size(); i++) {
			// if
			// (!turnActivityListModel.contains(instance.getTurnActivityList().getTurnActivity(i)))
			// {
			// turnActivityListModel.addElement(instance.getTurnActivityList().getTurnActivity(i));
			// }
			//
			//
			//
			// }

			// turnActivityListModel = new DefaultListModel<String>();
			// UPDATEDATA METHOD TurnTakerManager instance = TurnTakerManager.getInstance();
			TurnTakerManager instance = TurnTakerManager.getInstance();
			turnActivityListModel.clear();
			for (int i = 0; i < instance.getTurnActivityList().size(); i++) {
				if (!turnActivityListModel.contains(instance.getTurnActivityList().getTurnActivity(i))) {
					turnActivityListModel.addElement(instance.getTurnActivityList().getTurnActivity(i));
				}
			}
			// turnTakerListModel.clear();

			// TODO LISTMODEL .ADD ELEMENT .GET ACTIVITLIST LOOP FOR ADD ELEMENT
			// ! CONTAINS DEFAULT LIST MODEL ! CONTAINS(L.GET.()){ADD NEW ACTIVITY}
		}

	}

	/** ID number used for object serialization. */
	private static final long serialVersionUID = 1L;
	/** Title for top of GUI. */
	private static final String APP_TITLE = "Turn Taker";
	/** Text for the File Menu. */
	private static final String FILE_MENU_TITLE = "File";
	/** Text for the New Issue XML menu item. */
	private static final String NEW_FILE_TITLE = "New";
	/** Text for the Load Issue XML menu item. */
	private static final String LOAD_FILE_TITLE = "Load";
	/** Text for the Save menu item. */
	private static final String SAVE_FILE_TITLE = "Save";
	/** Text for the Quit menu item. */
	private static final String QUIT_TITLE = "Quit";

	// Object[][] rowData = {};

	// private TurnTakerQueue ttl = new

	/**
	 * The temp TurnActivity withholding informations from Add Activity as temperary
	 * value.
	 */
	private TurnActivity temp;
	// private boolean illAE = false;
	/**
	 * The add component part of the panel implementation.
	 */
	private JButton addB = new JButton("Add");
	/**
	 * The remove component part of the panel implementation.
	 */
	private JButton removeB = new JButton("Remove");
	/**
	 * The move up component part of the panel implementation.
	 */
	private JButton moveUpB = new JButton("Move Up");
	/**
	 * The move down component part of the panel implementation.
	 */
	private JButton moveDownB = new JButton("Move Down");

	/**
	 * The turnActivityNameTFAddP component part of the panel implementation.
	 */
	private JTextField turnActivityNameTFAddP;
	/**
	 * The turnActivityDescriptionTAAddP component part of the panel implementation.
	 */
	private JTextArea turnActivityDescriptionTAAddP;

	/**
	 * The columnNames component part of the panel implementation.
	 */
	private String[] columnNames = { "Name", "Turns Taken" };
	/**
	 * The turnTakerTableModel component part of the panel implementation.
	 */
	private DefaultTableModel turnTakerTableModel = new DefaultTableModel();
	/**
	 * The jTable component part of the panel implementation.
	 */
	private JTable jTable = new JTable(turnTakerTableModel);

	/**
	 * The turnTakerListModel component part of the panel implementation.
	 */
	private DefaultListModel<String> turnTakerListModel = new DefaultListModel<String>();
	// @SuppressWarnings({ "rawtypes", "unchecked" })
	/**
	 * The turnTakerList component part of the panel implementation.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private JList turnTakerList = new JList(turnTakerListModel);
	// private ListSelectionModel turnTakerListSelectionModel =
	// turnTakerList.getSelectionModel();

	/**
	 * The turnActivityListModel component part of the panel implementation.
	 */
	private DefaultListModel<TurnActivity> turnActivityListModel = new DefaultListModel<TurnActivity>();
	/**
	 * The turnActivityList component part of the panel implementation.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private JList turnActivityList = new JList(turnActivityListModel);
	/**
	 * The turnActivityListSelectionModel component part of the panel
	 * implementation.
	 */
	private ListSelectionModel turnActivityListSelectionModel = turnActivityList.getSelectionModel();

	/** Menu bar for the GUI that contains Menus. */
	private JMenuBar menuBar;
	/** Menu for the GUI. */
	private JMenu menu;
	/** Menu item for creating a new list of TurnActitivies. */
	private JMenuItem itemNewFile;
	/** Menu item for loading a file. */
	private JMenuItem itemLoadFile;
	/** Menu item for saving the list to a file. */
	private JMenuItem itemSaveFile;
	/** Menu item for quitting the program. */
	private JMenuItem itemQuit;

	/**
	 * The addTurnActivity component part of the panel implementation.
	 */
	private JButton addTurnActivity = new JButton("Add Turn Activity");
	/**
	 * The removeTurnActivity component part of the panel implementation.
	 */
	private JButton removeTurnActivity = new JButton("Remove Turn Activity");
	/**
	 * The editTurnActivity component part of the panel implementation.
	 */
	private JButton editTurnActivity = new JButton("Edit Turn Activity");
	/**
	 * The takeTurn component part of the panel implementation.
	 */
	private JButton takeTurn = new JButton("Take Turn");
	/**
	 * The add component part of the panel implementation.
	 */
	private JButton add = new JButton("Add");
	/**
	 * The remove component part of the panel implementation.
	 */
	private JButton remove = new JButton("Remove");
	/**
	 * The moveUp component part of the panel implementation.
	 */
	private JButton moveUp = new JButton("Move Up");
	/**
	 * The moveDown component part of the panel implementation.
	 */
	private JButton moveDown = new JButton("Move Down");
	/**
	 * The addTurnActivityB component part of the panel implementation.
	 */
	private JButton addTurnActivityB = new JButton("Add Turn Activity");
	/**
	 * The cancelB component part of the panel implementation.
	 */
	private JButton cancelB = new JButton("Cancel");
	/**
	 * The turnActivityNameLabel component part of the panel implementation.
	 */
	private JLabel turnActivityNameLabel = new JLabel("Turn Activity Name");
	/**
	 * The turnActivityDescriptionLabel component part of the panel implementation.
	 */
	private JLabel turnActivityDescriptionLabel = new JLabel("Turn Activity Description");
	/**
	 * The nextTurnTakerLabel component part of the panel implementation.
	 */
	private JLabel nextTurnTakerLabel = new JLabel("Next Turn Taker");

	/**
	 * The turnActivityNameTextFieldNextTurnPanel component part of the panel
	 * implementation.
	 */
	private JTextField turnActivityNameTextFieldNextTurnPanel = new JTextField();

	/**
	 * The turnActivityNameTextField component part of the panel implementation.
	 */
	private JTextField turnActivityNameTextField = new JTextField();
	/**
	 * The nextTurnTakerTextField component part of the panel implementation.
	 */
	private JTextField nextTurnTakerTextField = new JTextField();

	/**
	 * The turnActivityDescriptionTextArea component part of the panel
	 * implementation.
	 */
	private JTextArea turnActivityDescriptionTextArea = new JTextArea();
	/**
	 * The turnActivityNameTextFieldTop component part of the panel implementation.
	 */
	private JTextField turnActivityNameTextFieldTop = new JTextField();

	/**
	 * The turnActivitiesPanel reference panel refrence as used by other non-local
	 * scoped class.
	 */
	private TurnActivitiesPanel turnActivitiesPanel;
	/**
	 * The turnActivityDetailsPanel reference as used by other non-local scoped
	 * class.
	 */
	private TurnActivityDetailsPanel turnActivityDetailsPanel;
	/**
	 * The nextTurnPanel reference as used by other non-local scoped class.
	 */
	private NextTurnPanel nextTurnPanel;
	/**
	 * The turnOrderPanel reference as used by other non-local scoped class.
	 */
	private TurnOrderPanel turnOrderPanel;
	/**
	 * The turnTakersPanel reference as used by other non-local scoped class.
	 */
	private TurnTakersPanel turnTakersPanel = new TurnTakersPanel();

	/**
	 * Constructs the GUI.
	 */
	public TurnTakerGUI() {
		super();

		// Observe Manager
		TurnTakerManager.getInstance().addObserver(this);

		// Set up general GUI info
		setSize(1000, 500);
		setLocation(50, 50);
		setTitle(APP_TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUpMenuBar();

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				doExit();
			}

		});

		initializeGUI();

		// Set the GUI visible
		setVisible(true);
	}

	private void initializeGUI() {
		setResizable(false);
		Container c = getContentPane();

		turnActivitiesPanel = new TurnActivitiesPanel();
		nextTurnPanel = new NextTurnPanel();
		turnOrderPanel = new TurnOrderPanel();

		c.add(turnActivitiesPanel, BorderLayout.WEST);
		turnActivitiesPanel.setPreferredSize(new Dimension(500, 500));
		// turnActivitiesPanel.setBackground(Color.RED);

		JPanel east = new JPanel();
		c.add(east, BorderLayout.EAST);
		east.setPreferredSize(new Dimension(500, 500));
		// east.setBackground(Color.GREEN);

		east.add(nextTurnPanel, BorderLayout.NORTH);
		nextTurnPanel.setPreferredSize(new Dimension(481, 95));
		// nextTurnPanel.setBackground(Color.PINK);

		east.add(turnOrderPanel, BorderLayout.SOUTH);
		turnOrderPanel.setPreferredSize(new Dimension(481, 331));
		// turnOrderPanel.setBackground(Color.ORANGE);
	}

	/**
	 * Makes the GUI Menu bar that contains options for loading a file containing
	 * issues or for quitting the application.
	 */
	private void setUpMenuBar() {
		// Construct Menu items
		menuBar = new JMenuBar();
		menu = new JMenu(FILE_MENU_TITLE);
		itemNewFile = new JMenuItem(NEW_FILE_TITLE);
		itemLoadFile = new JMenuItem(LOAD_FILE_TITLE);
		itemSaveFile = new JMenuItem(SAVE_FILE_TITLE);
		itemQuit = new JMenuItem(QUIT_TITLE);
		itemNewFile.addActionListener(this);
		itemLoadFile.addActionListener(this);
		itemSaveFile.addActionListener(this);
		itemQuit.addActionListener(this);

		// Start with save button disabled
		itemSaveFile.setEnabled(false);

		// Build Menu and add to GUI
		menu.add(itemNewFile);
		menu.add(itemLoadFile);
		menu.add(itemSaveFile);
		menu.add(itemQuit);
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
	}

	private void doExit() {
		if (TurnTakerManager.getInstance().isChanged()) {
			doSaveFile();
		}

		if (!TurnTakerManager.getInstance().isChanged()) {
			System.exit(NORMAL);
		} else { // Did NOT save when prompted to save
			JOptionPane.showMessageDialog(this,
					"Turn Taker changes have not been saved. " + "Your changes will not be saved.", "Saving Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void doSaveFile() {
		try {
			TurnTakerManager instance = TurnTakerManager.getInstance();
			JFileChooser chooser = new JFileChooser("./");
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Turn Taker files (md)", "md");
			chooser.setFileFilter(filter);
			chooser.setMultiSelectionEnabled(false);
			if (instance.getFilename() != null) {
				chooser.setSelectedFile(new File(instance.getFilename()));
			}
			int returnVal = chooser.showSaveDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String filename = chooser.getSelectedFile().getAbsolutePath();
				// if (filename.isEmpty() || filename.trim().length() == 0) {
				// JOptionPane.showMessageDialog(this, "File not saved.", "Saving Error",
				// JOptionPane.ERROR_MESSAGE);
				// }
				// try {
				instance.setFilename(filename);
				// }
				// catch (Exception e) {
				// JOptionPane.showMessageDialog(null, "File not saved.", "Saving Error",
				// JOptionPane.ERROR_MESSAGE);
				// }
				instance.saveFile(filename);
				itemSaveFile.setEnabled(false);
			}
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this, "File not saved.", "Saving Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void doLoadFile() {
		try {
			TurnTakerManager instance = TurnTakerManager.getInstance();
			JFileChooser chooser = new JFileChooser("./");
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Turn Taker files (md)", "md");
			chooser.setFileFilter(filter);
			chooser.setMultiSelectionEnabled(false);
			int returnVal = chooser.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				instance.loadFile(chooser.getSelectedFile().getAbsolutePath());
			}
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this, "Error opening file.", "Opening Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof TurnTakerManager) {
			// TODO Call methods to update the contents of the GUI
			turnActivitiesPanel.updateData();
			if (TurnTakerManager.getInstance().isChanged()) {
				itemSaveFile.setEnabled(true);
			}
			// else if (!TurnTakerManager.getInstance().isChanged()){
			// itemSaveFile.setEnabled(false);
			// }
			repaint();
			validate();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == itemNewFile) {
			doSaveFile();
			TurnTakerManager.getInstance().newList();
		} else if (e.getSource() == itemLoadFile) {
			doLoadFile();
		} else if (e.getSource() == itemSaveFile) {
			doSaveFile();
		} else if (e.getSource() == itemQuit) {
			doExit();
		}
	}

	/**
	 * Starts the application
	 * 
	 * @param args
	 *            command line args
	 */
	public static void main(String[] args) {
		new TurnTakerGUI();
	}

}
