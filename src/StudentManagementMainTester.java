import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import java.awt.*;

import org.junit.*;
public class StudentManagementMainTester {
@Test
public void testUpdateStudentMenuEmptyDatabase() {
  // Mock or create a StudentManagement object with an empty studentdatabase
  StudentManagement studentManagement = new StudentManagement(); // Assuming default constructor creates an empty database
  
  // Call the method under test
  updateStudentMenu(studentManagement);
  
  // Assert that the content panel contains a label indicating no students
  // This might involve traversing the component hierarchy of contentPanel
  // Depending on your implementation, locate the JLabel with the message
  JLabel noStudentsLabel = findLabelWithText(contentPanel, "No students found in the database.");
  assertNotNull(noStudentsLabel);
}

private JLabel findLabelWithText(JPanel panel, String expectedText) {
  for (Component component : panel.getComponents()) {
    if (component instanceof JLabel && ((JLabel) component).getText().equals(expectedText)) {
      return (JLabel) component;
    } else if (component instanceof JPanel) {
      JLabel label = findLabelWithText((JPanel) component, expectedText);
      if (label != null) {
        return label;
      }
    }
  }
  return null;
}

}
