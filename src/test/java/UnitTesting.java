import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.awt.Frame;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JFileChooser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class UnitTesting {
    // an arbitrary address book used to test
    AddressBook testBook = new AddressBook();
    AddressBookController testBookController = new AddressBookController(testBook);
    //An arbitrary person object used to test
    Person testPerson = new Person("firstname", "lastname", "address",
            "city", "state", "zip", "1234567890");
    //another arbitrary person used to test
    Person testPerson2 = new Person("Tyler", "Marlow",
            "601 East Tropical Way", "Plantation", "" +
            "Florida", "33317", "9546217953");

    AddressBookController testController = new AddressBookController(testBook);

    //this annotation is used to perform this function first
    @BeforeEach
    void populateBook() {
        testBook.add(testPerson);
        testBook.add(testPerson2);
    }

    @AfterEach
    void cleanBook() {
        for (int i = 0; i < testBook.getRowCount(); i++) {
            testBook.remove(i);
        }
    }

    //test the Main function of the AddressBookGUI class
    @Test
    @DisplayName("Test the AddressBookGUI main function")
    void testAddressBookGUIMain() {
        //assumes that calling the main of the function does not throw exceptions
        assertDoesNotThrow(() -> AddressBookGUI.main(null));
    }

//    @BeforeEach
//    void dismissErrorMessage() {
//        AddressBookGUI gui = mock(AddressBookGUI.class);
//        Object message = mock(Object.class);
//        JOptionPane jo = mock(JOptionPane.class);
//        when(JOptionPane .showOptionDialog())
//        doNothing().when(JOptionPane.showMessageDialog(gui,message));
//        when(jo.isShowing()).then(jo.)
//    }

    @Test
    @DisplayName("Test the AddressBookGUI button actions")
    void testActionListener() {
        AddressBookGUI gui = new AddressBookGUI(testBookController, testBook);
        JFileChooser fc = mock(JFileChooser.class);
        when(fc.showOpenDialog(gui)).thenReturn(0);
        gui.setJfc(fc);
        assertDoesNotThrow(() -> gui.getOpenItem().doClick());
    }

//    @Test
//    @DisplayName("Test the AddressBookGUI button actions when the fule chooser is null")
//    void testActionListenerFail() {
//        AddressBookGUI gui = new AddressBookGUI(testBookController, testBook);
//        JFileChooser fc = null;
//        when(fc.showOpenDialog(gui)).thenReturn(JFileChooser.ABORT);
//        gui.setJfc(fc);
//      assertThrows(NullPointerException.class, () ->  gui.getJfc().showOpenDialog(gui));
//    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6})
    void testGetFieldValidVals(int num) {
        assertDoesNotThrow(() -> testPerson.getField(num));
    }

    @Test
    void testGetFieldInvalidVal() {
        assertThrows(IllegalArgumentException.class, () -> testPerson.getField(7));
    }

    //test the return of the first name and last name using the toString() function for the class Person
    @Test
    @DisplayName("Test the Person toString() function")
    void testToString() {
        //checks if the values are equal
        assertEquals("lastname, firstname", testPerson.toString());
    }

    //This tests checks the last name of the person object
    @Test
    @DisplayName("Test the Person getField() function - returns last name")
    void testReturnLastName() {
        //checks if the values are equal
        assertEquals("lastname", testPerson.getField(0));
    }

    //This test checks the first name of the person object
    @Test
    @DisplayName("Test the Person getField() function - returns first name")
    void testReturnFirstName() {
        //checks if the values are equal
        assertEquals("firstname", testPerson.getField(1));
    }

    //This test makes sure that the returning the number of rows works
    @Test
    @DisplayName("Testing row count")
    void getRowCount() {
        //checks if the values are equal
        assertEquals(2, testBook.getRowCount(), "Testing the rows");
    }

    //This test checks the number of columns in the address book
    @Test
    @DisplayName("Testing column count")
    void getColumnCount() {
        //checks if the values are equal
        assertEquals(7, testBook.getColumnCount(), "Testing the columns");
    }

    //This test checks that you can get the value at a specific cell in the address book
    @Test
    @DisplayName("Testing getting value at")
    void getValueAt() {
        //checks if the values are equal
        assertEquals("Tyler", testBook.getValueAt(1, 1));
    }

    @Test
    @DisplayName("Getting the column name")
    void getColumnName() {
        //checks if the values are equal
        assertEquals("Last Name", testBook.getColumnName(0));
    }

    //This tests removes a person object entry from the address book
    @Test
    @DisplayName("Testing removing person")
    void remove() {
        //removes person at specified index
        testBookController.remove(0);
        //checks if the values are equal
        assertEquals(1, testBook.getRowCount(), "Testing removing the person");
    }

    //This test gets the number of rows in the address book gui
    @Test
    @DisplayName("Test the Row Count")
    public void getRowCountTest() {
        //checks if the values are equal
        assertEquals(2, testBook.getRowCount());
    }

    //return the expected number of columns given 7 attributes
    @Test
    @DisplayName("Test the Column Count")
    public void getColumnCountTest() {
        //checks if the values are equal
        assertEquals(7, testBook.getColumnCount());
    }

    @DisplayName("Test Person - Create with null first name ")
    @Test
    void testPerson_FirstName_Null() {
        assertThrows(IllegalArgumentException.class, () -> new Person(null,
                "lastname", "address",
                "city", "state", "zip", "1234567890"));
    }

    @DisplayName("Test Person - Create with null last name ")
    @Test
    void testPerson_LastName_Null() {
        assertThrows(IllegalArgumentException.class, () -> new Person("firstname",
                null, "address",
                "city", "state", "zip", "1234567890"));
    }

    @DisplayName("Test Person - getPersons() ")
    @Test
    void testPerson_getPersons() {
        Person[] people;
        people = testBook.getPersons();
        assertEquals(2, people.length);
    }

    @DisplayName("Test Person - getPersons() ")
    @Test
    void testPerson_set() {
        testBookController.set(0, testPerson2);
        assertEquals("Tyler", testBook.getValueAt(0, 1));
    }

    @Test
    @DisplayName("Test the AddressBookController constructor with parameter ")
    void testAddressBookControllerConstructor() {
        //creates controller with specified book
        assertDoesNotThrow(() -> new AddressBookController(testBook));
    }

    @Test
    @DisplayName("Test the AddressBookController get function ")
    void testAddressBookControllerGet() {
        //creates controller with specified book
        assertEquals(testPerson2.getFirstName(), testBookController.get(1).getFirstName());
    }

    @Test
    @DisplayName("Test the AddressBookController add function ")
    void testAddressBookControllerAdd() {
        //creates controller with specified book
        testBookController.add(new Person("test",
                "test", "address",
                "city", "state", "zip", "1234567890"));
        assertEquals(3, testBook.getRowCount());
    }

    @Test
    @DisplayName("Test the AddressBookController clear function ")
    void testAddressBookControllerClear() {
        //creates controller with specified book
        testBookController.clear();
        assertEquals(0, testBook.getRowCount());
    }

    @Test
    @DisplayName("Test the AddressBookController getModel function ")
    void testAddressBookController_getModel() {
        //creates controller with specified book
        AddressBook test = testBookController.getModel();
        assertEquals(testBook.getValueAt(0, 1), test.getValueAt(0, 1));
    }

    @DisplayName("Test the AddressBookController save function")
    @Test
    void testAddressBookControllerSaveFile() {
        File testFile = new File("textFile.txt");
        assertDoesNotThrow(() -> testBookController.save(testFile));
    }

    @DisplayName("Test the AddressBookController open function")
    @Test
    void testReadFile() {
        File testFile = new File("textFile.txt");
        assertDoesNotThrow(() -> testBookController.open(testFile));
    }

    @DisplayName("Test the AddressBookController open function")
    @Test
    void testReadFile_notExisting(){
        File testFile = new File("textFile2.java");
        assertThrows(FileNotFoundException.class, () -> testBookController.open(testFile));
    }

    @DisplayName("Test Person - Create with empty first name ")
    @Test
    void testPerson_FirstName_Empty() {
        assertThrows(IllegalArgumentException.class, () -> new Person("",
                "lastname", "address",
                "city", "state", "zip", "1234567890"));
    }

    @DisplayName("Test Person - Create with empty last name")
    @Test
    void testPerson_LastName_Empty() {
        assertThrows(IllegalArgumentException.class, () -> new Person("firstname",
                "", "address",
                "city", "state", "zip", "1234567890"));
    }

    @DisplayName("Test person contains method")
    @Test
    void testPerson_contains() {
        for (int i = 0; i < 7; i++) {
            assertEquals(true, testPerson.containsString(testPerson.getField(i)));
        }
    }

    @DisplayName("Test getField method with invalid index")
    @Test
    void testPerson_getField_Invalid() {
        assertThrows(IllegalArgumentException.class, () -> testPerson.getField(7));
    }

    @DisplayName("Test Person's getLastName method")
    @Test
    void testPerson_getLastName() {
        assertEquals("lastname", testPerson.getLastName());
    }

    @DisplayName("Test Person's getAddress method")
    @Test
    void testPerson_getAddress() {
        assertEquals("address", testPerson.getAddress());
    }

    @DisplayName("Test Person's getCity method")
    @Test
    void testPerson_getCity() {
        assertEquals("city", testPerson.getCity());
    }

    @DisplayName("Test Person's getState method")
    @Test
    void testPerson_getState() {
        assertEquals("state", testPerson.getState());
    }

    @DisplayName("Test Person's getZip method")
    @Test
    void testPerson_getZip() {
        assertEquals("zip", testPerson.getZip());
    }

    @DisplayName("Test Person's getPhone method")
    @Test
    void testPerson_getPhone() {
        assertEquals("1234567890", testPerson.getPhone());
    }

    @DisplayName("Test PersonDialog")
    @Test
    void testPersonDialog(){
        assertDoesNotThrow(() -> new PersonDialog(new Frame()));
    }
    @DisplayName("Test PersonDialog")
    @Test
    void testPersonDialog_getPerson_Null(){
        PersonDialog pd = new PersonDialog(new Frame());

        assertEquals(null, pd.getPerson());
    }



    @Test
    @DisplayName("Test the AddressBookGUI button actions")
    void testActionListener() {
        AddressBookGUI gui = new AddressBookGUI(testBookController, testBook);
        JFileChooser fc = mock(JFileChooser.class);
        when(fc.showOpenDialog(gui)).thenReturn(JFileChooser.ABORT);
        gui.setJfc(fc);
        gui.getOpenItem().doClick();
    }
}