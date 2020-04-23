import GUI.PersonDialog;
import java.io.*;
import AddressBook.*;
import GUI.AddressBookGUI;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.DialogFixture;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UnitTesting {
    // an arbitrary address book used to test
    AddressBook testBook;
    AddressBookController testBookController;
    //An arbitrary person object used to test
    Person testPerson = new Person("firstname", "lastname", "address",
            "city", "state", "33965", "1234567890");
    //another arbitrary person used to test
    File testFile = new File("testFile.txt");
    public static FrameFixture window = null;

    //number of people in address book in the beginning
    int initialCount;

    //this annotation is used to perform this function first
    @BeforeEach
    void initializeBook() {
        //initializes addressBook
        testBook= new AddressBook();
        testBook.add(testPerson);

        //gets address book size at start of test
        initialCount =testBook.getRowCount();

        //initializes the controller
        testBookController = new AddressBookController(testBook);

        //initializes the GUI and runs it in a window
        AddressBookGUI gui= GuiActionRunner
                .execute(() -> new AddressBookGUI(new AddressBookController(testBook),testBook));

        //assigns GUI window to a variable to allow for testing
        window = new FrameFixture(gui);
        window.show();
    }

    @AfterEach
    void cleanBook() {
        window.cleanUp();
    }

    /*
        Mimic the GUI process to add a person utilizing AssertJ. In order to properly test
        utilize the initial count of rows present in the GUI table, and compare it to the new
        rowCount. If everything went through, it should be initialCount + 1 (for the newly added
        person.
     */
    @Test
    @DisplayName("Test AddressBookGUI add function - Unit test")
    public void testAddressBookGUI_Add_Unit() {
        window.button("add").click();
        DialogFixture dialog = window.dialog();
        dialog.textBox("firstName").setText("Jordin");
        dialog.textBox("lastName").setText("Medina");
        dialog.textBox("address").setText("525 street drive");
        dialog.textBox("city").setText("Naples");
        dialog.textBox("state").setText("Florida");
        dialog.textBox("zip").setText("34112");
        dialog.textBox("phone").setText("2395721111");
        dialog.button(JButtonMatcher.withName("ok")).click();
        window.table().requireRowCount(initialCount + 1);
    }

    /*
        Mimic the GUI process to add a person with an empty last name utilizing AssertJ.
        In this case, no person should be added to the window table, thus the row count should still
        equal the inital row count.
     */
    @Test
    @DisplayName("Test putting in an empty last name in theAddressBookGUI add dialog - Unit test")
    public void testAddressBookGUI_Empty_Last_Add_Unit() {
        window.button("add").click();
        DialogFixture dialog = window.dialog();
        dialog.textBox("firstName").setText("Jordin");
        dialog.textBox("lastName").setText("");
        dialog.textBox("address").setText("525 street drive");
        dialog.textBox("city").setText("Naples");
        dialog.textBox("state").setText("Florida");
        dialog.textBox("zip").setText("34112");
        dialog.textBox("phone").setText("2395721111");
        dialog.button(JButtonMatcher.withName("ok")).click();
        window.table().requireRowCount(initialCount);
    }

    /*
        Mimic the GUI process to add a person with an empty first name utilizing AssertJ.
        In this case, no person should be added to the window table, thus the row count should still
        equal the inital row count.
     */
    @Test
    @DisplayName("Test putting in an empty first name in theAddressBookGUI add dialog - Unit test")
    public void testAddressBookGUI_Empty_First_Add_Unit() {
        window.button("add").click();
        DialogFixture dialog = window.dialog();
        dialog.textBox("firstName").setText("");
        dialog.textBox("lastName").setText("Medina");
        dialog.textBox("address").setText("525 street drive");
        dialog.textBox("city").setText("Naples");
        dialog.textBox("state").setText("Florida");
        dialog.textBox("zip").setText("34112");
        dialog.textBox("phone").setText("2395721111");
        dialog.button(JButtonMatcher.withName("ok")).click();
        window.table().requireRowCount(initialCount);
    }

    /*
        Mimic the GUI process to add a person with an invalid zipcode utilizing AssertJ.
        In this case, no person should be added to the window table, thus the row count should still
        equal the inital row count.
     */
    @Test
    @DisplayName("Test putting in wrong zipcode in theAddressBookGUI add dialog - Unit test")
    public void testAddressBookGUI_wrong_zip() {
        window.button("add").click();
        DialogFixture dialog = window.dialog();
        dialog.textBox("firstName").setText("Jordin");
        dialog.textBox("lastName").setText("Medina");
        dialog.textBox("address").setText("525 street drive");
        dialog.textBox("city").setText("Naples");
        dialog.textBox("state").setText("Florida");
        dialog.textBox("zip").setText("zip");
        dialog.textBox("phone").setText("2395721111");
        dialog.button(JButtonMatcher.withName("ok")).click();
        window.table().requireRowCount(initialCount);
    }

    /*
        Mimic the GUI process to add a person with an invalid phone number utilizing AssertJ.
        In this case, no person should be added to the window table, thus the row count should still
        equal the inital row count.
     */
    @Test
    @DisplayName("Test putting an invalid phone number in theAddressBookGUI add dialog - Unit test")
    public void testAddressBookGUI_wrong_phone() {
        window.button("add").click();
        DialogFixture dialog = window.dialog();
        dialog.textBox("firstName").setText("Jordin");
        dialog.textBox("lastName").setText("Medina");
        dialog.textBox("address").setText("525 street drive");
        dialog.textBox("city").setText("Naples");
        dialog.textBox("state").setText("Florida");
        dialog.textBox("zip").setText("34112");
        dialog.textBox("phone").setText("test");
        dialog.button(JButtonMatcher.withName("ok")).click();
        window.table().requireRowCount(initialCount);
    }

    /*
        Mimic the GUI process to click on the add button of the window, then the cancel button
        of the newly opened dialog. Ensure that no exceptions are thrown
     */
    @Test
    @DisplayName("Test canceling out of AddressBookGUI add dialog - Unit test")
    public void testAddressBookGUI_Cancel_Add_Unit() {
        window.button("add").click();
        DialogFixture dialog = window.dialog();
        assertDoesNotThrow(() -> dialog.button(JButtonMatcher.withName("cancel")).click());
    }


    /*
        Mimic the GUI process to delete a person utilizing AssertJ. In order to properly test
        utilize the inital count of rows present in the GUI table, and compare it to the new
        rowCount. If everything went through, it should be initialCount - 1 (to reflect the person
        that has been removed.
    */
    @Test
    @DisplayName("Test AddressBookGUI remove function - Unit test")
    public void testAddressBookGUI_Remove_Unit() {
        window.table().cell("firstname").click();
        window.button("delete").click();
        window.table().requireRowCount(initialCount - 1);
    }

    /*
       Mimic the GUI process to edit a person utilizing AssertJ. In order to properly test check
       that there is a row containing the newly edited data in the GUI table.
    */
    @Test
    @DisplayName("Test AddressBookGUI edit function - Unit test")
    public void testAddressBookGUI_Edit_Unit() {
        window.table().cell("firstname").click();
        window.button("edit").click();
        DialogFixture dialog = window.dialog();
        dialog.textBox("firstName").setText("Luca");
        dialog.button(JButtonMatcher.withName("ok")).click();
        window.table().requireContents(new String[][]{{"lastname", "Luca", "address", "city", "state", "33965", "1234567890"}});
    }

    /*
       Mimic the GUI process to edit a person utilizing AssertJ.
    */
    @Test
    @DisplayName("Test AddressBookGUI edit function without a selection - Unit test")
    public void testAddressBookGUI_Edit_Unit_noSelection() {
        window.button("edit").click();
        window.button("edit").requireFocused();
    }

    @Test
    @DisplayName("Test AddressBookGUI save being disabled")
    public void testAddressBookGUI_save_disabled() {
        window.menuItem("save").requireDisabled();
    }

    /*
       Mimic the GUI process to remove a person utilizing AssertJ.
    */
    @Test
    @DisplayName("Test AddressBookGUI remove function without a selection - Unit test")
    public void testAddressBookGUI_remove_Unit_noSelection() {
        window.button("delete").click();
        window.button("delete").requireFocused();
    }

    /*
        Mimic the GUI process to click on the edit button of the window, then the cancel button
        of the newly opened dialog. Ensure that no exceptions are thrown
     */
    @Test
    @DisplayName("Test canceling out of AddressBookGUI edit dialog - Unit test")
    public void testAddressBookGUI_Cancel_Edit_Unit() {
        window.table().cell("firstname").click();
        window.button("edit").click();
        DialogFixture dialog = window.dialog();
        assertDoesNotThrow(() -> dialog.button(JButtonMatcher.withName("cancel")).click());
    }

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
        assertEquals(1, testBook.getRowCount(), "Testing the rows");
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
        assertEquals("firstname", testBook.getValueAt(0, 1));
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
    void testAddressBookRemove() {

        //removes person at specified index
        testBook.remove(0);
        //checks if the values are equal
        assertEquals(initialCount-1, testBook.getRowCount(), "Testing removing the person");
    }

    @Test
    @DisplayName("Test the AddressBook add function ")
    void testAddressBookAdd() {
        Person testPerson2 = new Person("Tyler", "Marlow",
                "601 East Tropical Way", "Plantation", "" +
                "Florida", "33317", "9546217953");
        testBook.add(testPerson2);
        assertEquals(initialCount + 1, testBook.getRowCount());
    }

    //This test gets the number of rows in the address book gui
    @Test
    @DisplayName("Test the Row Count")
    public void getRowCountTest() {
        //checks if the values are equal
        assertEquals(1, testBook.getRowCount());
    }

    //return the expected number of columns given 7 attributes
    @Test
    @DisplayName("Test the Column Count")
    public void getColumnCountTest() {
        //checks if the values are equal
        assertEquals(7, testBook.getColumnCount());
    }

    //create a person with null first name
    @DisplayName("Test Person - Create with null first name ")
    @Test
    void testPerson_FirstName_Null() {
        assertThrows(IllegalArgumentException.class, () -> new Person(null,
                "lastname", "address",
                "city", "state", "34112", "1234567890"));
    }

    //create a person with null last name
    @DisplayName("Test Person - Create with null last name ")
    @Test
    void testPerson_LastName_Null() {
        assertThrows(IllegalArgumentException.class, () -> new Person("firstname",
                null, "address",
                "city", "state", "34112", "1234567890"));
    }

    //get people array from the addressbook
    @DisplayName("Test Person - getPersons() ")
    @Test
    void testPerson_getPersons() {
        Person[] people;
        people = testBook.getPersons();
        assertEquals(1, people.length);
    }

    //test editing information regarding a person in the addressbook (check with old value
    //to ensure test passes)
    @Test
    void testPersonSet() {
        String oldName = testBook.getValueAt(0, 1).toString();
        testBook.set(0, new Person("Tyler", "Marlow",
            "601 East Tropical Way", "Plantation", "" +
            "Florida", "33317", "9546217953"));
        assertNotEquals(oldName, (String) testBook.getValueAt(0, 1));
    }

    //test addressbook constructor
    @Test
    @DisplayName("Test the AddressBookController constructor with parameter ")
    void testAddressBookControllerConstructor() {
        //creates controller with specified book
        assertDoesNotThrow(() -> new AddressBookController(testBook));
    }

    //clear out address book of any contact
    @Test
    @DisplayName("Test the AddressBookController clear function ")
    void testAddressBookControllerClear() {
        //creates controller with specified book
        testBookController.clear();
        assertEquals(0, testBook.getRowCount());
    }

    //check that the addressbook can be exported to model - check its data for validation
    @Test
    @DisplayName("Test the AddressBookController getModel function ")
    void testAddressBookController_getModel() {
        //creates controller with specified book
        AddressBook test = testBookController.getModel();
        assertEquals(testBook.getValueAt(0, 1), test.getValueAt(0, 1));
    }

    //test save function of the address book controller
    @DisplayName("Test the AddressBookController save function")
    @Test
    void testAddressBookControllerSaveFile() {
        assertDoesNotThrow(() -> testBookController.save(testFile));
    }

    //test opening a file (importing data) into an addressbook
    @DisplayName("Test the AddressBookController open function")
    @Test
    void testReadFile() {
        assertDoesNotThrow(() -> testBookController.open(testFile));
    }

    //test addressbook controller read file function when a file does not exist
    @DisplayName("Test the AddressBookController open function")
    @Test
    void testReadFile_notExisting() {
        File testFile = new File("testFile2.java");
        assertThrows(FileNotFoundException.class, () -> testBookController.open(testFile));
    }

    //test Filesystem save file function
    @DisplayName("Test Save File")
    @Test
    void testSaveFile_FileSys() {
        FileSystem fs = new FileSystem();
        assertDoesNotThrow(() -> fs.saveFile(testBook, testFile));
    }

    //test Filesystem read file function
    @DisplayName("Test Read File")
    @Test
    void testReadFile_FileSys() {
        FileSystem fs = new FileSystem();
        assertDoesNotThrow(() -> fs.readFile(testBook, testFile));
    }

    //test Filesystem read file function when file is not readable
    @DisplayName("Test Read File not readable")
    @Test
    void testReadFile_FileSys_unreadable_file() {
        FileSystem fs = new FileSystem();
        File unreadableFile = mock(File.class);
        //stub that file cannot be read
        when(unreadableFile.canRead()).thenReturn(false);
        //stub that the file exists
        when(unreadableFile.exists()).thenReturn(true);

        assertThrows(FileNotFoundException.class, () -> fs.readFile(testBook, unreadableFile));    }

    //create Person with empty first name
    @DisplayName("Test Person - Create with empty first name ")
    @Test
    void testPerson_FirstName_Empty() {
        assertThrows(IllegalArgumentException.class, () -> new Person("",
                "lastname", "address",
                "city", "state", "34112", "1234567890"));
    }
    //create Person with empty last name
    @DisplayName("Test Person - Create with empty last name")
    @Test
    void testPerson_LastName_Empty() {
        assertThrows(IllegalArgumentException.class, () -> new Person("firstname",
                "", "address",
                "city", "state", "34112", "1234567890"));
    }

    //create Person with invalid zipcode - i.e. does not match regex pattern of
    // 5 digits or 5 digits with extension (33965, or 33965-1123)
    @DisplayName("Test Person - Create with invalid zipcode")
    @Test
    void testPerson_InvalidZip() {
        assertThrows(IllegalArgumentException.class, () -> new Person("firstname",
                "lastname", "address",
                "city", "state", "zip", "1234567890"));
    }

    //create Person with invalid zipcode - i.e. does not match regex pattern defined in Person's class
    @DisplayName("Test Person - Create with invalid phone number")
    @Test
    void testPerson_InvalidPhone() {
        assertThrows(IllegalArgumentException.class, () -> new Person("firstname",
                "lastname", "address",
                "city", "state", "33965", "phone"));
    }

    //test the search function of Person, this returns the field when the string matches one of its fields
    @DisplayName("Test person contains method")
    @Test
    void testPerson_contains() {
        for (int i = 0; i < 7; i++) {
            assertEquals(true, testPerson.containsString(testPerson.getField(i)));
        }
    }

    //get an invalid field of a Person object
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
        assertEquals("33965", testPerson.getZip());
    }

    @DisplayName("Test Person's getPhone method")
    @Test
    void testPerson_getPhone() {
        assertEquals("1234567890", testPerson.getPhone());
    }

    //test clearing an addressbook that's already empty
    @Test
    void addressBook_clear_empty_AddressBook() {
        testBook.clear();

        assertDoesNotThrow(() -> testBook.clear());
    }

    //test clearing an addressbook that's already empty
    @Test
    void persondialogNull() {
        assertDoesNotThrow(() -> new PersonDialog(window.target(), null));
    }

}