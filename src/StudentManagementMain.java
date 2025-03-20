//import GUI builder classes with a star to include all needed methods
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

//import action listener to bring function to the buttons
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;

public class StudentManagementMain extends JFrame {

    //main method to run the GUI
    public static void main(String[] args){
       new StudentManagementMain();
    }

    JFrame frame;
    JPanel panel;
    StudentManagement studentManagement;
    CourseManagement courseManagement;
    JTextField nameField;
    JTextField idField;
    JTextField gradeField;
    long id;
    private Student Student;

//method to initialize main welcome frame
/*sets minimum size, clicking 'x' to exit frame, along wtih title and enter buttion*/
    public StudentManagementMain(){
        studentManagement = new StudentManagement(); // Create the object
        frame = new JFrame(); //make the frame object
        panel = new JPanel(); //make the panel object that goes inside the frame

        // Set up title label to go in the enter
        JLabel titleLabel = new JLabel("Apple School Student Management System");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center title

        // Create "Enter" button
        JButton enterButton = new JButton("Enter");

        //set panel size
        panel.setBorder(BorderFactory.createEmptyBorder(70, 60, 60, 70));
        panel.setLayout(new BorderLayout()); //to be able to position items in NORTH, CENTER, SOUTH, EAST or WEST

        //add items to panel
        panel.add(titleLabel, BorderLayout.CENTER);
        panel.add(enterButton, BorderLayout.SOUTH);

        frame.add(panel,BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Apple School District"); //title that will appear at the top (name of school district)
        frame.pack();
        frame.setVisible(true);

        // Add action listener to "Enter" button
    enterButton.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
            // Remove the current panel with title and button
            frame.remove(panel);

            JMenuBar menuBar = MainMenu(); // Call MainMenu()
            frame.setJMenuBar(menuBar); // Add menu bar to frame


            // Revalidate and repaint the frame to display the new panel
            frame.revalidate();
            frame.repaint();
            
        }
    });

    }
    


//Method for main menu of actions
/*this method include the menu for all the main functions of the Student management system. This includes Student Management, Course Enrollment,
and Grade management. Each button leads to the menu of actions that can be performed within each category.
 */
public JMenuBar MainMenu(){
    JMenuBar menuBar = new JMenuBar();

        // Create menus
        JMenu studentSubMenu = new JMenu("Student");
        JMenu enrollmentMenu = new JMenu("Course Enrollment");
        JMenu gradesMenu = new JMenu("Grades");
        

        // Create menu items

       // Create dropdown menu items for student management
        JMenuItem addStudentItem = new JMenuItem("Add Student");
        studentSubMenu.add(addStudentItem);
        addStudentItem.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {

        // Create and display the StudentMenuAdd panel when the item is clicked
        StudentMenuAdd addStudentPanel = new StudentMenuAdd(studentManagement);
        displayPanel(addStudentPanel);
    }

    private void displayPanel(StudentManagementMain.StudentMenuAdd addStudentPanel) {
       frame.getContentPane().add(addStudentPanel);
        frame.revalidate();
        frame.repaint();
        
    }
});
        JMenuItem updateStudentItem = new JMenuItem("Update Student");
        updateStudentItem.addActionListener(e -> updateStudentMenu());
        studentSubMenu.add(updateStudentItem);

        JMenuItem viewStudentDetailsItem = new JMenuItem("View Student Details");
        viewStudentDetailsItem.addActionListener(e -> viewStudentDetails());
        studentSubMenu.add(viewStudentDetailsItem);

        //Dropdown menu items for Course Enrollment
        JMenuItem viewCoursesItem = new JMenuItem("View Courses");
        viewCoursesItem.addActionListener(e -> courseList());
        enrollmentMenu.add(viewCoursesItem);

        JMenuItem addCoursesItem = new JMenuItem("Add Courses");
        enrollmentMenu.add(addCoursesItem);
        addCoursesItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create an instance of CoursesAdd panel
                CoursesAdd coursesAddPanel = new CoursesAdd(courseManagement); // Assuming you have an instance of CourseManagement
        
                // Display the CoursesAdd panel
                displayPanel(coursesAddPanel);
            }

            private void displayPanel(StudentManagementMain.CoursesAdd coursesAddPanel) {
                frame.getContentPane().add(coursesAddPanel);
                frame.revalidate();
                frame.repaint();
            }
        });
        
        

        JMenuItem enrollStudentItem = new JMenuItem("Enroll Student");
        enrollmentMenu.add(enrollStudentItem);

        //Dropdown menu items for Grade Managemenet
        JMenuItem manageGradesItem = new JMenuItem("Manage Grades");
        gradesMenu.add(manageGradesItem);

        JMenuItem viewGradesItem = new JMenuItem("View Grades");
        gradesMenu.add(viewGradesItem);

        // Add menus to the menu bar
        menuBar.add(studentSubMenu);
        menuBar.add(enrollmentMenu);
        menuBar.add(gradesMenu);

        setJMenuBar(menuBar); // Add menu bar to the frame
        return menuBar;

}

//method for add student option
public class StudentMenuAdd extends JPanel {
    private StudentManagement studentManagement; // Received through constructor
    
    public StudentMenuAdd(StudentManagement studentManagement) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Use BoxLayout for vertical stacking
       
    // Create labels and text fields
    JLabel nameLabel = new JLabel("Student Name: ");
    nameField = new JTextField(20); // Set width for readability

    JLabel idLabel = new JLabel("Student ID: ");
    idField = new JTextField(10); // Set width for readability

    JLabel gradeLabel = new JLabel("Grade Level: ");
    gradeField = new JTextField(2); // Set width for readability

    JButton addButton = new JButton("Add Student");
    

    // Add components to the panel
    add(nameLabel);
    add(nameField);
    add(idLabel);
    add(idField);
    add(gradeLabel);
    add(gradeField);
    add(new JLabel("")); // Spacer for layout
    add(addButton);
    
    
   

    JLabel successLabel = new JLabel("Student added successfully!");
    JLabel failureLabel = new JLabel("Failed to add student. Please check information and try again.");
    add(successLabel);
    add(failureLabel);
    successLabel.setVisible(false);
    failureLabel.setVisible(false);


    // Add action listener to button
    addButton.addActionListener(new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            // Handle button click (call StudentManagement method)
            String name = nameField.getText();
            int grade;
            long id;

            try {
                id = Long.parseLong(idField.getText());
            } catch (NumberFormatException e1) {
                // Handle invalid ID input
                JOptionPane.showMessageDialog(null, "Please enter a valid ID (number format required).");
                idField.setText(""); // Clear the invalid input
                id = -1; // Set a default value for ID
            }
            
            try {
                grade = Integer.parseInt(gradeField.getText());
            } catch (NumberFormatException e2) {
                // Handle invalid grade input
                JOptionPane.showMessageDialog(null, "Please enter a valid grade (number format required).");
                gradeField.setText(""); // Clear the invalid input
                grade = 0; // Set a default value for grade
            }

            boolean success = studentManagement.add_Student(name, id, grade);
            if (success) {
                successLabel.setVisible(true);
                failureLabel.setVisible(false);
                // Optionally, clear text fields or perform other actions
                nameField.setText("");
                idField.setText("");
                gradeField.setText("");
            } else {
                successLabel.setVisible(false);
                failureLabel.setVisible(true);
        }}
    });
}}


public void updateStudentMenu() {
    JDialog updateDialog = new JDialog(this, "Update Student");
    JPanel contentPanel = new JPanel(); // Create a content panel
    // 1. Create components for student selection

    // Create a list model to hold student names
    DefaultListModel<String> studentListModel = new DefaultListModel<>();
    
    if (StudentManagement.studentdatabase.isEmpty()) {
        // Handle the case where there are no students in the database
        JLabel noStudentsLabel = new JLabel("No students found in the database.");
        contentPanel.add(noStudentsLabel);
    } else {
    // Get all student names from the database
    for (Student student : StudentManagement.studentdatabase.values()) {
        studentListModel.addElement(student.getname());
        // Create a JList to display student names (allow single selection)
        JList<String> studentList = new JList<>(studentListModel);
        studentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    

        studentList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedIndex = studentList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String selectedName = studentList.getSelectedValue();
        
                    Student selectedstudent = StudentManagement.studentdatabase.get(selectedName); // Retrieve student by name
        
                        // Call createUpdateMenu to display update options
                        createUpdateMenu(selectedstudent, updateDialog, studentListModel);
                    } else {
                        JOptionPane.showMessageDialog(null, "Student not found!");
                    }
            }
        });
        
    // Layout components

   
    contentPanel.setLayout(new GridLayout(4, 2, 5, 5)); // Use GridLayout for example (adjust as needed)
    contentPanel.add(studentList); // Add the JList to the panel
    
 // Add content panel to the dialog
 updateDialog.add(contentPanel);

 updateDialog.pack(); // Adjust size based on components
 updateDialog.setVisible(true);}
    }

}

private <StudentList> void createUpdateMenu(Student Student, JDialog dialog,StudentList studentList) {
    // Clear existing menu bar (if applicable)
    dialog.setJMenuBar(null);
    
    this.Student = Student;
    String selectedName = Student.getname();

    // Create a JMenu and menu items
    JMenu updateMenu = new JMenu("Update " + selectedName);
    JMenuItem updateName = new JMenuItem("Name");
    JMenuItem updateId = new JMenuItem("ID Number");
    JMenuItem updateGrade = new JMenuItem("Grade");
    JMenuItem updateAll = new JMenuItem("All");

    // Add action listeners to menu items
    updateName.addActionListener(e -> navigateToUpdateName(Student,selectedName)); // Implement navigateToUpdateName method
    updateId.addActionListener(e -> navigateToUpdateId(selectedName));   // Implement navigateToUpdateId method
    updateGrade.addActionListener(e -> navigateToUpdateGrade(selectedName)); // Implement navigateToUpdateGrade method
    updateAll.addActionListener(e -> navigateToUpdateAll(Student, selectedName));  // Implement navigateToUpdateAll method

    // Add menu items to the update menu
    updateMenu.add(updateName);
    updateMenu.add(updateId);
    updateMenu.add(updateGrade);
    updateMenu.add(updateAll);

    // Create a JMenuBar and add the update menu
    JMenuBar menuBar = new JMenuBar();
    menuBar.add(updateMenu);

    // Set the dialog's menu bar and potentially remove student list
    dialog.setJMenuBar(menuBar);
}



private void navigateToUpdateAll(Student student, String selectedName) {
    JDialog updateAllDialog = new JDialog(this, "Update All Information");

    // Create form components
    JLabel currentNameLabel = new JLabel("Current Name: " + selectedName);
    JTextField newNameField = new JTextField(20);

    JLabel currentIdLabel = new JLabel("Current ID: " + student.getID());
    JTextField newIdField = new JTextField(10); // Assuming 10 digits
    newIdField.setEditable(false); // ID non-editable

    JLabel currentGradeLabel = new JLabel("Current Grade: " + student.getgradelevel());
    JTextField newGradeField = new JTextField(2);

    JButton updateAllButton = new JButton("Update All");

    // Add action listener for update button
    updateAllButton.addActionListener(e -> {
        String newName = newNameField.getText();
        int grade;
        try {
            grade = Integer.parseInt(newGradeField.getText());
        } catch (NumberFormatException ex) {
            // Display error message for invalid grade format
            return; // Exit action listener
        }

        boolean nameSuccess = StudentManagement.update_StudentName(student, newName);
        boolean gradeSuccess = StudentManagement.update_StudentGrade(student, grade);
        // ID update not allowed, so no success check for it

        JLabel failureLabel = new JLabel("Failed to update some information. Please check and try again.");
        JLabel successLabel = new JLabel("Student updated successfully!");
        boolean overallSuccess = nameSuccess && gradeSuccess; // Combine success states

        if (overallSuccess) {
            successLabel.setVisible(true);
            failureLabel.setVisible(false);
            // Optionally, clear text fields or perform other actions
            nameField.setText("");
            idField.setText("");
            gradeField.setText("");
        } else {
            successLabel.setVisible(false);
            failureLabel.setVisible(true);
        }
    });

    // Layout components and display dialog
    JPanel contentPanel = new JPanel();
    contentPanel.setLayout(new GridLayout(6, 1, 5, 5));
    contentPanel.add(currentNameLabel);
    contentPanel.add(newNameField);
    contentPanel.add(currentIdLabel);
    contentPanel.add(newIdField);
    contentPanel.add(currentGradeLabel);
    contentPanel.add(newGradeField);
    contentPanel.add(updateAllButton);

    updateAllDialog.add(contentPanel);
    updateAllDialog.pack();
    updateAllDialog.setVisible(true);
}




private void navigateToUpdateName(Student student,String selectedName) {
      // Create a new dialog for updating name (or reuse existing dialog)
      JDialog updateNameDialog = new JDialog(this, "Update Name");
      this.Student = student;

      // Create form components
      JLabel currentNameLabel = new JLabel("Current Name: " + selectedName);
      JTextField newNameField = new JTextField(20);
      JButton updateNameButton = new JButton("Update Name");
  
      // Add action listener for update button
      updateNameButton.addActionListener(e -> {
          String newName = newNameField.getText();

          boolean success = StudentManagement.update_StudentName(student,newName);
          JLabel failureLabel = new JLabel("Failed to update name. Please check information and try again.");
          JLabel successLabel = new JLabel("Student updated successfully!");
          add(successLabel);
          add(failureLabel);
          successLabel.setVisible(false);
          failureLabel.setVisible(false);

          if (success) {
              successLabel.setVisible(true);
              failureLabel.setVisible(false);
              // Optionally, clear text fields or perform other actions
              nameField.setText("");
              idField.setText("");
              gradeField.setText("");
          } else {
              successLabel.setVisible(false);
              failureLabel.setVisible(true);
      }
      });
  
      // Layout components and display dialog
      JPanel contentPanel = new JPanel();
      contentPanel.setLayout(new GridLayout(3, 1, 5, 5));
      contentPanel.add(currentNameLabel);
      contentPanel.add(newNameField);
      contentPanel.add(updateNameButton);
  
      updateNameDialog.add(contentPanel);
      updateNameDialog.pack();
      updateNameDialog.setVisible(true);
}

// navigateToUpdateId method (similar layout and display)
private void navigateToUpdateId(String studentName) {
    JDialog updateIdDialog = new JDialog(this, "Update ID Number");

    Student studentToUpdate = StudentManagement.studentdatabase.get(studentName);

    JLabel currentIdLabel = new JLabel("Current ID: " +  studentToUpdate.getID());
    JTextField newIdField = new JTextField(10); // Assuming ID has 10 digits
    newIdField.setEditable(false); // Make ID non-editable (likely unique)
    JButton updateIdButton = new JButton("Update ID (Not Allowed)"); // Inform user IDs can't be updated

     // Add action listener for update button
     updateIdButton.addActionListener(e -> {
        try {
          // Parse the new ID as a long value
          long newID = Long.parseLong(newIdField.getText());
          boolean success = StudentManagement.update_StudentID(studentName, newID);
          JLabel failureLabel = new JLabel("Failed to update Id. Please check information and try again.");
          JLabel successLabel = new JLabel("Student updated successfully!");
          if (success) {
              successLabel.setVisible(true);
              failureLabel.setVisible(false);
              // Optionally, clear text fields or perform other actions
              nameField.setText("");
              idField.setText("");
              gradeField.setText("");
          } else {
              successLabel.setVisible(false);
              failureLabel.setVisible(true);
      }
      

        } catch (NumberFormatException ex) {
          // Handle the case where the user enters a non-numeric value for ID
          System.out.println("Invalid ID format. Please enter a number.");
          // Consider displaying an error message on the UI as well

       }
      });
      

 

    JPanel contentPanel = new JPanel();
    contentPanel.setLayout(new GridLayout(3, 1, 5, 5));
    contentPanel.add(currentIdLabel);
    contentPanel.add(newIdField);
    contentPanel.add(updateIdButton); // Disable button (optional)

    updateIdDialog.add(contentPanel);
    updateIdDialog.pack();
    updateIdDialog.setVisible(true);
}

// navigateToUpdateGrade method (similar layout and display)
private void navigateToUpdateGrade(String studentName) {
    JDialog updateGradeDialog = new JDialog(this, "Update Grade");

    Student studentToUpdate = StudentManagement.studentdatabase.get(studentName);

    JLabel currentGradeLabel = new JLabel("Current Grade: " + studentToUpdate.getgradelevel());
    JTextField newGradeField = new JTextField(2); // Set width for readability
    JButton updateGradeButton = new JButton("Update Grade");
 // Add action listener for update button
 updateGradeButton.addActionListener(e -> {
    try {
      // Parse the new ID as a long value
      int grade = Integer.parseInt(newGradeField.getText());
      boolean success = StudentManagement.update_StudentGrade(studentToUpdate, grade);
      JLabel failureLabel = new JLabel("Failed to change grade. Please check information and try again.");
      JLabel successLabel = new JLabel("Student updated successfully!");
      if (success) {
          successLabel.setVisible(true);
          failureLabel.setVisible(false);
          // Optionally, clear text fields or perform other actions
          nameField.setText("");
          idField.setText("");
          gradeField.setText("");
      } else {
          successLabel.setVisible(false);
          failureLabel.setVisible(true);
  }
  

    } catch (NumberFormatException ex) {
      // Handle the case where the user enters a non-numeric value for ID
      System.out.println("Invalid ID format. Please enter a number.");
      // Consider displaying an error message on the UI as well

   }
  });
  
    JPanel contentPanel = new JPanel();
    contentPanel.setLayout(new GridLayout(3, 1, 5, 5));
    contentPanel.add(currentGradeLabel);
    contentPanel.add(newGradeField);
    contentPanel.add(updateGradeButton);

    updateGradeDialog.add(contentPanel);
    updateGradeDialog.pack();
    updateGradeDialog.setVisible(true);
}

public void viewStudentDetails() {
        JDialog detailsDialog = new JDialog(this, "View Student Details");
        JPanel contentPanel = new JPanel();

        // Create a JTable to display student details
        String[] columnNames = {"Name", "ID Number", "Grade Level"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable studentTable = new JTable(tableModel);

        // Retrieve student data from the database
        if (!StudentManagement.studentdatabase.isEmpty()) {
            for (Student student : StudentManagement.studentdatabase.values()) {
                String[] rowData = {student.getname(), String.valueOf(student.getID()), String.valueOf(student.getgradelevel())};
                tableModel.addRow(rowData);
            }
        } else {
            JLabel noStudentsLabel = new JLabel("No students found in the database.");
            contentPanel.add(noStudentsLabel);
        }

        // Add the table to the content panel
        JScrollPane scrollPane = new JScrollPane(studentTable); // Add a scroll pane for better viewing
        contentPanel.add(scrollPane);

        // Set content panel and display dialog
        detailsDialog.add(contentPanel);
        detailsDialog.pack(); // Adjust size based on components
        detailsDialog.setVisible(true);
    }

    public void courseList() {
        JDialog courseListDialog = new JDialog(this, "View Courses");
        JPanel contentPanel = new JPanel();
    
        // Create a list model to hold course names
        DefaultListModel<String> courseListModel = new DefaultListModel<>();
        for (Course course : CourseManagement.courses) {
            courseListModel.addElement(course.getcoursename());
        }
    
        // Create a JList to display course names (allow single selection)
        JList<String> courseList = new JList<>(courseListModel);
        courseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
        courseList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedIndex = courseList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String selectedCourseName = courseList.getSelectedValue();
                    Course selectedCourse = CourseManagement.courses.get(selectedIndex);
    
                    // Call courseInfo to display details and options for the selected course
                    courseInfo(selectedCourse);
    
                    // Close the course list dialog as the user has selected a course
                    courseListDialog.dispose();
                }
            }
        });
    
        // Layout components
        contentPanel.setLayout(new GridLayout(1, 1, 5, 5));
        contentPanel.add(courseList);
    
        // Add content panel to the dialog and display it
        courseListDialog.add(contentPanel);
        courseListDialog.pack();
        courseListDialog.setVisible(true);
    }

    public void courseInfo(Course course) {
        JDialog courseInfoDialog = new JDialog(this, "Course Information");
    
        // Create labels to display course details
        JLabel courseNameLabel = new JLabel("Course Name: " + course.getcoursename());
        JLabel courseCodeLabel = new JLabel("Course Code: " + course.getcoursecode());
        JLabel maxCapacityLabel = new JLabel("Max Capacity: " + course.getmaxcapacity());
    
        // Retrieve enrolled student information from CourseManagement
        Map<Course, Integer> studentGrades = CourseManagement.getStudentGrades().get(course);
    
        // Create a panel to display enrolled students (if any)
        JPanel studentListPanel = new JPanel();
        if (!studentGrades.isEmpty()) {
            JLabel enrolledStudentsLabel = new JLabel("Enrolled Students:");
            studentListPanel.add(enrolledStudentsLabel);
    
            for (Course student : studentGrades.keySet()) {
                JLabel studentLabel = new JLabel(student.getcoursename() + " (Grade: " + studentGrades.get(student) + ")");
                studentListPanel.add(studentLabel);
            }
        } else {
            JLabel noStudentsLabel = new JLabel("No students enrolled in this course yet.");
            studentListPanel.add(noStudentsLabel);
        }
    
        // Create buttons for available actions
        JButton viewCourseDetailsButton = new JButton("View More Details"); // Implement this action as needed
        JButton makeChangesButton = new JButton("Make Changes"); // Implement this action as needed
    
        // Add action listeners to buttons (implementation omitted for brevity)
        // ...
    
        // Layout components
        courseInfoDialog.setLayout(new GridLayout(4, 1, 5, 5)); // Adjust layout as needed
        courseInfoDialog.add(courseNameLabel);
        courseInfoDialog.add(courseCodeLabel);
        courseInfoDialog.add(maxCapacityLabel);
        courseInfoDialog.add(studentListPanel);
        courseInfoDialog.add(viewCourseDetailsButton);
        courseInfoDialog.add(makeChangesButton);
    
        // Display the dialog
        courseInfoDialog.pack();
        courseInfoDialog.setVisible(true);
    }
    
    public class CoursesAdd extends JPanel {
        private CourseManagement courseManagement; // Received through constructor
    
        private JTextField courseCodeField;
        private JTextField courseNameField;
        private JTextField maxCapacityField;
    
        public CoursesAdd(CourseManagement courseManagement) {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Use BoxLayout for vertical stacking
    
            // Create labels and text fields
            JLabel courseCodeLabel = new JLabel("Course Code (4 digits): ");
            courseCodeField = new JTextField(4); // Set width to match course code length
    
            JLabel courseNameLabel = new JLabel("Course Name: ");
            courseNameField = new JTextField(20);
    
            JLabel maxCapacityLabel = new JLabel("Max Capacity (1-30): ");
            maxCapacityField = new JTextField(2); // Set width for readability
    
            JButton addButton = new JButton("Add Course");
    
            // Add components to the panel
            add(courseCodeLabel);
            add(courseCodeField);
            add(courseNameLabel);
            add(courseNameField);
            add(maxCapacityLabel);
            add(maxCapacityField);
            add(new JLabel("")); // Spacer for layout
            add(addButton);
    
            JLabel successLabel = new JLabel("Course added successfully!");
            JLabel failureLabel = new JLabel("Failed to add course. Please check information and try again.");
            add(successLabel);
            add(failureLabel);
            successLabel.setVisible(false);
            failureLabel.setVisible(false);
    
            // Add action listener to button
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Gather input values
                    String courseCodeText = courseCodeField.getText();
                    String courseName = courseNameField.getText();
                    String maxCapacityText = maxCapacityField.getText();
    
                    int courseCode;
                    int maxCapacity;
    
                    try {
                        courseCode = Integer.parseInt(courseCodeText);
                        maxCapacity = Integer.parseInt(maxCapacityText);
                    } catch (NumberFormatException e1) {
                        // Handle invalid numerical input
                        JOptionPane.showMessageDialog(null, "Please enter valid numbers for course code and max capacity.");
                        return; // Exit the action listener to avoid further processing
                    }
    
                    // Attempt to create the course
                    try {
                        boolean success = CourseManagement.addCourse(courseCode, courseName, maxCapacity);
                        if (success) {
                            successLabel.setVisible(true);
                            failureLabel.setVisible(false);
                            // Optionally, clear text fields or perform other actions
                            courseCodeField.setText("");
                            courseNameField.setText("");
                            maxCapacityField.setText("");
                        } else {
                            successLabel.setVisible(false);
                            failureLabel.setVisible(true);
                        }
                    } catch (IllegalArgumentException e2) {
                        // Handle validation errors from CourseManagement.addCourse
                        JOptionPane.showMessageDialog(null, e2.getMessage());
                    }
                }
            });
        }
    }
    
    public void enrollStudent() {
        JDialog enrollDialog = new JDialog(this, "Enroll Student");
        JPanel contentPanel = new JPanel();
    
        // 1. Create a list to display student names
        DefaultListModel<String> studentListModel = new DefaultListModel<>();
        for (Student student : StudentManagement.studentdatabase.values()) {
            studentListModel.addElement(student.getname());
        }
    
        // 2. Create a list model to display courses
        DefaultListModel<String> courseListModel = new DefaultListModel<>();

        CourseManagement courseManagement = new CourseManagement();
        List<Course> courses = courseManagement.getCourses();
        // Retrieve courses from CourseManagement 
        for (Course course : courses) {
            // Access course name from the current Course object
            String courseName = course.getcoursename();  // Modify if name is stored differently
            courseListModel.addElement(courseName);
        }
    
        // 3. Create JLists for students and courses
        JList<String> studentList = new JList<>(studentListModel);
        JList<String> courseList = new JList<>(courseListModel);
    
        // 4. Add listeners to student and course lists
        studentList.addListSelectionListener(e -> {
            int selectedIndex = studentList.getSelectedIndex();
            if (selectedIndex != -1) {
                // Enable course list when a student is selected
                courseList.setEnabled(true);
            } else {
                // Disable course list if no student is selected
                courseList.setEnabled(false);
            }
        });
    
        // 5. Add enrollment button
        JButton enrollButton = new JButton("Enroll");
        enrollButton.addActionListener(e -> {
            int studentIndex = studentList.getSelectedIndex();
            int courseIndex = courseList.getSelectedIndex();
            String studentName;
            String courseName;
    
            if (studentIndex != -1 && courseIndex != -1) {
                studentName = studentListModel.getElementAt(studentIndex);
                courseName = courseListModel.getElementAt(courseIndex);
            }
            // 1. Find the Student object using studentName
                Student student = StudentManagement.findStudentByName(studentName);  // Assuming a find method
                Course course = CourseManagement.findCourseByName(courseName);
    
                // Call the enroll method in CourseManagement (adjust as needed)
                boolean success = CourseManagement.enrollStudent(student, course);
    
                if (success) {
                    JOptionPane.showMessageDialog(enrollDialog, "Student enrolled successfully!");
                    // Optionally, clear selections or perform other actions
                } else {
                    JOptionPane.showMessageDialog(enrollDialog, "Failed to enroll student. Please check course availability.");
                }
            {
                JOptionPane.showMessageDialog(enrollDialog, "Please select both a student and a course.");
            }
        });
    
        // 6. Layout components
        contentPanel.setLayout(new GridLayout(3, 1));
        contentPanel.add(new JLabel("Select Student:"));
        contentPanel.add(studentList);
        contentPanel.add(courseList);
        enrollButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center button
        contentPanel.add(enrollButton);
    
        // 7. Add content panel to dialog and display
        enrollDialog.add(contentPanel);
        enrollDialog.pack();
        enrollDialog.setVisible(true);
    }

    public void viewGrades() {
        JDialog viewGradesDialog = new JDialog(this, "View Grades");
        JPanel contentPanel = new JPanel();
    
        // 1. Create components for student selection
        DefaultListModel<String> studentListModel = new DefaultListModel<>();
        for (Student student : StudentManagement.studentdatabase.values()) {
            studentListModel.addElement(student.getname());
        }
        JList<String> studentList = new JList<>(studentListModel);
        studentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
        // 2. Create components for displaying courses and grades
        JLabel coursesLabel = new JLabel("Courses:");
        JTextArea coursesArea = new JTextArea(10, 20); // Adjust rows and cols as needed
        coursesArea.setEditable(false); // Make it non-editable
    
        JLabel gradesLabel = new JLabel("Grades:");
        JTextArea gradesArea = new JTextArea(10, 10); // Adjust rows and cols as needed
        gradesArea.setEditable(false); // Make it non-editable
    
        // Add action listener to student list
        studentList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedIndex = studentList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String selectedName = studentList.getSelectedValue();
                    Student selectedStudent = StudentManagement.studentdatabase.get(selectedName);
    
                    // Retrieve courses and grades from the student object
                    Map<String, String> coursesAndGrades = selectedStudent.getCoursesAndGrades();
    
                    // Display courses and grades in the text areas
                    StringBuilder coursesText = new StringBuilder();
                    StringBuilder gradesText = new StringBuilder();
                    for (Map.Entry<String, String> entry : coursesAndGrades.entrySet()) {
                        coursesText.append(entry.getKey()).append("\n");
                        gradesText.append(entry.getValue()).append("\n");
                    }
                    coursesArea.setText(coursesText.toString());
                    gradesArea.setText(gradesText.toString());
                }
            }
        });
    
        // Layout components
        contentPanel.setLayout(new GridLayout(3, 1, 5, 5));
        contentPanel.add(studentList);
        contentPanel.add(new JPanel(new BorderLayout()) {{
            add(coursesLabel, BorderLayout.NORTH);
            add(coursesArea, BorderLayout.CENTER);
        }});
        contentPanel.add(new JPanel(new BorderLayout()) {{
            add(gradesLabel, BorderLayout.NORTH);
            add(gradesArea, BorderLayout.CENTER);
        }});
    
        // Add content panel to the dialog and display
        viewGradesDialog.add(contentPanel);
        viewGradesDialog.pack();
        viewGradesDialog.setVisible(true);
    }
    
    
}