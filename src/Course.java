/**Course class for representing courses within a course management system. Stores key course information like course code, name, 
 * and maximum capacity, and provides methods to access, modify, and manage course enrollment. It includes validation for course 
 * code length and capacity, tracks total enrolled students, and interacts with other parts of the system, such as the Student class, 
 * for enrollment and grade management. */

public class Course {
//private variables that will be associated with Course
private int courseCode;
private String courseName;
private int maxCapacity;

//public method to create a new Course
public Course(int courseCode,String courseName, int maxCapacity){
this.courseCode = courseCode;
this.courseName = courseName;
this.maxCapacity = maxCapacity;
}

//Get methods
/*Each method returns the variable associated with that student. This is to be
used in the main method functions. Methods include getting the course code, name
and the maximum capacity of the class.*/ 

public int getcoursecode(){
    return courseCode;
} //end of getcoursecode method

public String getcoursename(){
    return courseName;
} //end of getcoursename method

public int getmaxcapacity(){
    return maxCapacity;
} //end of get maxcapacity method

//Set methods

//Method to set course code with an error catch to make sure that the code is 5 digits long
public int setcoursecode(int newCoursecode){
    this.courseCode = newCoursecode;
    if (newCoursecode < 10000 || newCoursecode > 99999) {
        throw new IllegalArgumentException("Course code must be a 5-digit number.");
    }
    return courseCode;
}

//Method to set name of course
public String setcousename(String newcourseName){
    this.courseName = newcourseName;
    return courseName;
}

//method to set max capacity fo the course with error catch for if capacity exceeds 30
public int setmaxcapacity(int newmaxcapacity){
    this.maxCapacity = newmaxcapacity;
    if (newmaxcapacity <= 0 || newmaxcapacity > 30) {
        throw new IllegalArgumentException("Maximum capacity must be between 1 and 30.");
    }
    return maxCapacity;
}

//Static variale to track total enrolled students
private static int totalEnrolledStudents = 0;
public static Object course;

//Method to enroll a student 
public void enrollStudent(){
    totalEnrolledStudents++;
}

//Static method to retrieve total enrolled students
public static int getEnrolledStudents(){
    return totalEnrolledStudents;
}
} // end of class
