import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import java.util.Map;

import org.junit.*;
public class StudentManagementTest {
@Test
public void testAddStudent_ValidData() {
    // Arrange
    String validName = "John Doe";
    long validID = 1234567890L;
    int validGrade = 10;
    StudentManagement studentManagement = new StudentManagement(); // Assuming StudentManagement object creation

    // Act
    boolean result = studentManagement.add_Student(validName, validID, validGrade);

    // Assert
    assertTrue("Student should be added to the database", result);

    // Optional: Verify student presence using isPresent method
    assertTrue("Student should be present in the database", Student.isPresent(validName));

}
}
