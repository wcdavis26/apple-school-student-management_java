/**Course Management class acts as the core for a course management. It manages courses (storing, searching, creating new ones) 
 * and student grades (tracking, adding, calculating overall grades). It also facilitates student enrollment by interacting with 
 * other classes. */

//import classes for later use
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseManagement {
 // Private static variables for storing courses and student grades
    static List<Course> courses = new ArrayList<>();
    private static Map<Student, Map<Course, Integer>> studentGrades = new HashMap<>();

public List<Course> getCourses() {
        // Return an unmodifiable copy of the courses list
        return (List<Course>) Collections.unmodifiableList(new ArrayList<>(courses));
    }


 // Add a student with an empty course-grade map
 public static void addStudentGradesMap(Student student) {
    studentGrades.put(student, new HashMap<Course, Integer>());
}

// Add a grade for a specific student and course
public static boolean addGrade(Student student, Course course, int grade) {
    Map<Course, Integer> courseGrades = studentGrades.get(student);
    if (courseGrades == null) {
        return false;
    }
    courseGrades.put(course, grade);
    return true;
}
    public static Map<Student, Map<Course, Integer>> getStudentGrades() {
        // Create a new HashMap to store the copy
        Map<Student, Map<Course, Integer>> studentGradesCopy = new HashMap<>();

        // Iterate through the original map and copy key-value pairs
        for (Student student : studentGrades.keySet()) {
            Map<Course, Integer> courseGradesMap = new HashMap<>(); // Create a new map for course grades
            for (Map.Entry<Course, Integer> entry : studentGrades.get(student).entrySet()) {
                courseGradesMap.put(entry.getKey(), entry.getValue()); // Copy course and grade
            }
            studentGradesCopy.put(student, courseGradesMap); // Add student and copied course grades
        }

        return studentGradesCopy;
    }

//Method to look up course by the name and return null if course is not found
    public static Course findCourseByName(String courseName) {
        // Implement logic to search for a course based on name
        // For example, using a Map or List to store courses:
        for (Course course : courses) { // Assuming courses are stored in a Map
            if (course.getcoursename().equals(courseName)) {
                return course;
            }
        }
        return null; // Course not found
    }



    // Static method to add a new course
    public static boolean addCourse(int courseCode, String courseName, int maxCapacity) {
         // Error checks:
 if (String.valueOf(courseCode).length() != 4) {
    // Throw a descriptive exception for clarity
    throw new IllegalArgumentException("Course code must be 4 digits long.");
  }
 
  if (courseName.isBlank()) {
    throw new IllegalArgumentException("Course name cannot be blank.");
  }
 
  if (maxCapacity > 30) {
    throw new IllegalArgumentException("Max capacity cannot exceed 30.");
  }
 
  // If all checks pass, proceed with course creation
  Course newCourse = new Course(courseCode, courseName, maxCapacity);
  courses.add(newCourse);
  return true;
    }

    // Static method to enroll a student in a course
    public static boolean enrollStudent(Student student, Course course) {
        // Error checks
        if (student == null) {
          throw new IllegalArgumentException("Student cannot be null.");
        }
      
        if (course == null) {
          throw new IllegalArgumentException("Course cannot be null.");
        }
      
        // Attempt enrollment logic (assuming student.enrollInCourse(course) handles checks)
        boolean enrolled = student.enrollInCourse(course);
      
        if (enrolled) {
          // Add student and course to map with default grade of 0
          Map<Course, Integer> courseGrades = studentGrades.get(student);
          if (courseGrades == null) {
            courseGrades = new HashMap<>();
            studentGrades.put(student, courseGrades);
          }
          courseGrades.put(course, 0);
          return true;
        } else {
          // Enrollment failed (handled by student.enrollInCourse(course))
          return false;
        }
      }
      
      

    // Static method to assign a grade to a student in a course
    public static void assignGrade(Student student, Course course, int grade) {
        Map<Course, Integer> studentGradesMap = studentGrades.get(student);
        if (studentGradesMap == null) {
            studentGradesMap = new HashMap<>();
            studentGrades.put(student, studentGradesMap);
        }
        studentGradesMap.put(course, grade);
    }

    // Static method to calculate overall course grade for a student
    public static double calculateOverallGrade(Student student) {
        Map<Course, Integer> studentGradesMap = studentGrades.get(student);
        if (studentGradesMap == null || studentGradesMap.isEmpty()) {
            return 0.0; // No grades available, return 0.0 as default
        }

        double totalPoints = 0; //initiate toal points variable
        int totalCourses = studentGradesMap.size(); //initiate total courses variable by looking up in course map
        for (Integer grade : studentGradesMap.values()) {
            totalPoints += grade; //iterate through grades on course list and add them to total points
        }
        return totalPoints / totalCourses; //return grade
    }
}
