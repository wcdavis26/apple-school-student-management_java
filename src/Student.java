/** Student class for a GUI Student Management System. It stores a student's name, ID, grade level, GPA, and a list of enrolled courses. 
 * The class uses private variables for data protection and provides public methods to access and modify student information. 
 * Students can have up to 5 courses, with validation for grade levels (1-12) and GPA (up to 4.0). Methods manage courses by adding, 
 * changing, enrolling, and assigning grades while handling invalid inputs and course capacity limitations Type of most methods are
 * boolean for ease of integration into GUI. */

//import classes for later use
import java.util.ArrayList; //for course list
import java.util.HashMap;
import java.util.Map;

public class Student{

//private variables that will be associated with creation of a new Student
  private String name;
  private long ID;
  private ArrayList<String> courses = new ArrayList<>(); //store the list of courses the student is enrolled in
  private int gradelevel;
  private float GPA;

  //public method to create a new student 
  /*Information included is all of the private variables defined above. */
  public Student(String name, long ID, ArrayList<String> courses, int gradelevel, float GPA){
    this.name = name;
    this.ID = ID;
    this.courses = courses;
    this.gradelevel = gradelevel;
    this.GPA = GPA;
  }

  //Get methods
  /*The following methods are to return corresponding student informaiton. Methods include
   * getting the student's name, ID, gradelevel and GPA.
  */
  public String getname(){
    return name;
  }

  public long getID(){
    return ID;
  }

  public int getgradelevel(){
    return gradelevel;
  }

  public float getGPA(){
    return GPA;
  }

//Get courses methods
/*These methods are to update student information regarding courses. One method returns the 
 * full list of courses while the other one returns a specific course from the list or tells
 * the user that the student is not enrolled in that course */

//get the entire course list
  public ArrayList<String> getcourseList(){
    return courses;
  }

//get an individual course
public String getCourse(String courseName){
    int index = courses.indexOf(courseName);
    if (index != -1){
        return courses.get(index);
    } else{
        System.out.println("Course not found in the list. Please add course to list. Returning ot main menu... ");
        return null; //return to main menu 
    }
}

 

//Set methods
/*methods for arrays should include only allowing 5 courses on the list at a time
methods for gradelevel should not go above grade 12, GPA not above 4.0, ID is ten digit*/
public String setname(String newName){
    this.name = newName;
    return name;
}

public long setID(long newID){
    this.ID = newID;
    return ID;
}

//Method for setting grade level of the student with newgradelevel as the parameter
/*Method checks if the user input for grade level is within the valid range (1-12). If valid, it applies the change and returns the new level. 
Otherwise, it politely prompts the user to re-enter a valid grade level (between 1 and 12) until a correct value is provided. */

public boolean setgradelevel(int newGradeLevel) {
  if (newGradeLevel <= 12) {
      this.gradelevel = newGradeLevel;
      return true; // Indicate success
  } else {
      return false; // Indicate failure without throwing an exception
  }
}



//Method to set GPA with new GPA as the parameter
public float setGPA(float newGPA){
    this.GPA = newGPA;
    return GPA;
}

//course methods
//add course to list
public boolean addCourse(String courseName) {
  if (courses.size() < 5) {
    return true; // Course can be added
  } else {
    return false; // Course list is full
  }
}





//change a course in the list
public boolean changeCourse(int index, String newCourseName) {
  if (0 <= index && index < courses.size()) {
      courses.set(index, newCourseName);
      return true; // Indicate success
  } else {
      return false; // Indicate failure due to invalid index
  }
}


//Method to enroll student in course
/*Updates the student's grade level if valid (1-12), setting the internal gradelevel variable 
and returning true. If invalid, returns false without changing gradelevel.*/

public boolean enrollInCourse(Course course) {
  if (courses.size() < 5 && !courses.contains(course.getcoursename())) {
    courses.add(course.getcoursename());
    course.enrollStudent(); // Assuming enrollStudent handles course capacity
    return true; // Enrollment successful
  } else {
    return false; // Enrollment failed
  }
}

  //Method to assign grade to student in a course taking course and grade as parameters
  /*Assigns a grade (0-100) to a course if enrolled (checks by name). Updates course name 
  in list to include grade if valid. Returns true on success, false otherwise. */

  public boolean assignGrade(Course course, int grade) {
    int index = courses.indexOf(course.getcoursename());
    if (index != -1 && grade >= 0 && grade <= 100) {
        courses.set(index, course.getcoursename() + ":" + grade);
        return true; // Grade assigned successfully
    } else {
        return false; // Error encountered
    }
 }
 

  public static boolean isPresent(String studentName) {
    return StudentManagement.studentdatabase.containsKey(studentName);
}

 public Map<String, String> getCoursesAndGrades() {
  Map<String, String> coursesAndGrades = new HashMap<>();
  for (String course : courses) {
    int colonIndex = course.indexOf(":");
    if (colonIndex != -1) { // Check if grade is present
      String courseName = course.substring(0, colonIndex);
      String grade = course.substring(colonIndex + 1);
      coursesAndGrades.put(courseName, grade);
    } else {
      coursesAndGrades.put(course, "Grade Not Assigned"); // Add placeholder for missing grades
    }
  }
  return coursesAndGrades;
}



}//end of class