
//import classes for later use
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentManagement {

//Total Student Variable and method
/*The varible totalStudents include all students including those that are not enrolled in any courses
 * This is not to be confused with the variables and methods that have to do with totalEnrolledStudent
 * which accounts for the total amount of students enrolled in classes. When a student is created they 
 * are not enrolled in any classes.
 */

// Private Static variable to keep track of total number of students
private static int totalStudents = 0;
    
//Method to get total students
public static int getTotalStudents() {
    return totalStudents;
}

     //Private Static variable to store list formatted as hashmap
     /*A hashmap that stores all enrolled students regardless of whether or not they are currently
      * taking any classes. The student's name is the key word to look up
      */
    public static  Map<String, Student> studentdatabase = new HashMap<>(); //initiate student database to store student info
    
    
    //Method to add a new student to studentdatabase
    /*Creates a new student in database prompting the user for the information. Asks user for Student name, Student ID number 
     * and Student grade. While loops are used to handle invalid input from user.*/
   
	 public boolean add_Student(String studentName, long studentID, int grade) {

		// Validate student information
		if (!validateStudentName(studentName) || !validateStudentID(studentID) || !validateGrade(grade)) {
			return false;
		}
	
		// Create student object
		Student student = new Student(studentName, studentID, new ArrayList<>(), grade, 0.0f);
	
		studentdatabase.put(studentName, student);
		// Add student to database
		if (!Student.isPresent(studentName)) {
			return false; // Handle potential duplicate key scenario (optional)
		}
	
		totalStudents++;
		CourseManagement.addStudentGradesMap(student);
	
		return true;
	}
	
	private static boolean validateStudentName(String name) {
		return name.contains(" ");
	}
	
	private static  boolean validateStudentID(long id) {
		return String.valueOf(id).length() == 10;
	}
	
	private static boolean validateGrade(int grade) {
		return grade >= 1 && grade <= 12;
	}
	


 

//find student methods

//find student based on name

//find student based on ID
 public static Student findStudentById(Map<Student, Map<Course, Integer>> studentGradesMap, long studentID) {
        for (Student student : studentGradesMap.keySet()) {
            if (student.getID() == studentID) {
                return student;
            }
        }
        return null; // No student found with the given ID
    }

//error checking methods

//check if student ID is 10 digits long
public boolean isValidID(String studentID) {
    return studentID.length() == 10 && isNumeric(studentID);
}

//checking to make sure a number has been typed. private and called in the is validID method
private boolean isNumeric(String str) {
    try {
        Long.parseLong(str);
        return true;
    } catch (NumberFormatException e) {
        return false;
    }
}

public static boolean update_Student(String studentName, long studentID, int grade) throws Exception {
	// Validate student information
	if (!validateStudentName(studentName) || !validateStudentID(studentID) || !validateGrade(grade)) {
		return false;
	}

	Student studentToUpdate = studentdatabase.get(studentName);

	studentToUpdate.setname(studentName);
	studentToUpdate.setID(studentID);
	studentToUpdate.setgradelevel(grade);

	return true;
}

public static boolean update_StudentName(Student student,String studentName){
	if (!validateStudentName(studentName)) {
		return false;
	}
	// Directly update the name on the provided student object
    student.setname(studentName);

	return true;
}

public static boolean update_StudentID(String studentName,long studentID){
		// Validate student information
		if ((!validateStudentName(studentName) ||!validateStudentID(studentID))) {
			return false;
		}

		Student studentToUpdate = studentdatabase.get(studentName);
		studentToUpdate.setID(studentID);

		return true;

}

public static boolean update_StudentGrade(Student student, int grade) {
		// Validate student information
		if (!validateGrade(grade)) {
			return false;
		}
		// Directly update the grade on the provided student object
		student.setgradelevel(grade);

		return true;
}

public static Student findStudentByName(String studentName) {
	// TODO Auto-generated method stub
	throw new UnsupportedOperationException("Unimplemented method 'findStudentByName'");
}
}
/*Old add student method
 *  public boolean add_Student (String studentName, long studentID, int Grade) {
		 // Validate student name
		 if (!studentName.contains(" ")) {
			return false; // Missing space in name
		}
	
		// Validate student ID
		if (String.valueOf(studentID).length() != 10) {
			return false; // ID is not 10 digits long
		}
	
		// Validate student grade
		if (Grade < 1 || Grade > 12) {
			return false; // Number out of valid grade range
		}
	
		// All validations passed, proceed with student creation and addition
		ArrayList<String> courses = new ArrayList<>(); //initiate empty course list
		float GPA = 0; //initiate GPA variable at zero
		Student student = new Student(studentName, studentID, courses, Grade, GPA); //creation of new student
	
		// Assuming studentdatabase is accessible here
		studentdatabase.put(studentName, student);
		totalStudents++; //add to total students
		CourseManagement.addStudentGradesMap(student); // Add student with an empty course-grade map 
	
		return true;
	
}//end of add_Student method


 
 */