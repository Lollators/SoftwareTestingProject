import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import AddressBook.*;
import GUI.AddressBookGUI;
import GUI.PersonDialog;
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
    AddressBook testBook = new AddressBook();
    AddressBookController testBookController = new AddressBookController(testBook);
    //An arbitrary person object used to test
    Person testPerson = new Person("firstname", "lastname", "address",
            "city", "state", "33965", "1234567890");
    //another arbitrary person used to test
    File testFile = new File("testFile.txt");
    public static FrameFixture window = null;
    AddressBookController guiAddressBookController;
    AddressBook guiAddressBook;

    //this annotation is used to perform this function first
    @BeforeEach
    void initializeBook() {
        testBook.add(testPerson);
        AddressBookGUI gui = GuiActionRunner
                .execute(() -> new AddressBookGUI(new AddressBookController(testBook), testBook));
        window = new FrameFixture(gui);
        window.show();
        guiAddressBookController = ((AddressBookGUI) window.target()).getAddressBookController();
        guiAddressBook = guiAddressBookController.getModel();
    }

    @AfterEach
    void cleanBook() {
        for (int i = 0; i < testBook.getRowCount(); i++) {
            testBook.remove(i);
        }
        window.cleanUp();
    }

    @Test
    @DisplayName("Test AddressBookGUI add function - Unit test")
    public void testAddressBookGUI_Add_Unit() {
        int tableRowCount = window.table().rowCount();
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
        window.table().requireRowCount(tableRowCount + 1);
    }

    @Test
    @DisplayName("Test putting in an empty last name in theAddressBookGUI add dialog - Unit test")
    public void testAddressBookGUI_Empty_Last_Add_Unit() {
        int tableRowCount = window.table().rowCount();
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
        window.table().requireRowCount(tableRowCount);
    }

    @Test
    @DisplayName("Test putting in an empty first name in theAddressBookGUI add dialog - Unit test")
    public void testAddressBookGUI_Empty_First_Add_Unit() {
        int tableRowCount = window.table().rowCount();
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
        window.table().requireRowCount(tableRowCount);
    }

    @Test
    @DisplayName("Test putting in a null first name in theAddressBookGUI add dialog - Unit test")
    public void testAddressBookGUI_Null_First_Add_Unit() {
        int tableRowCount = window.table().rowCount();
        window.button("add").click();
        DialogFixture dialog = window.dialog();
        dialog.textBox("firstName").setText(null);
        dialog.textBox("lastName").setText("Medina");
        dialog.textBox("address").setText("525 street drive");
        dialog.textBox("city").setText("Naples");
        dialog.textBox("state").setText("Florida");
        dialog.textBox("zip").setText("34112");
        dialog.textBox("phone").setText("2395721111");
        dialog.button(JButtonMatcher.withName("cancel")).click();
        window.table().requireRowCount(tableRowCount);
    }

    @Test
    @DisplayName("Test puttin in a null last name in theAddressBookGUI add dialog - Unit test")
    public void testAddressBookGUI_Null_Last_Add_Unit() {
        int tableRowCount = window.table().rowCount();
        window.button("add").click();
        DialogFixture dialog = window.dialog();
        dialog.textBox("firstName").setText("Jordin");
        dialog.textBox("lastName").setText(null);
        dialog.textBox("address").setText("525 street drive");
        dialog.textBox("city").setText("Naples");
        dialog.textBox("state").setText("Florida");
        dialog.textBox("zip").setText("34112");
        dialog.textBox("phone").setText("2395721111");
        dialog.button(JButtonMatcher.withName("ok")).click();
        window.table().requireRowCount(tableRowCount);
    }

    @Test
    @DisplayName("Test canceling out of AddressBookGUI add dialog - Unit test")
    public void testAddressBookGUI_Cancel_Add_Unit() {
        int tableRowCount = window.table().rowCount();
        window.button("add").click();
        DialogFixture dialog = window.dialog();
        assertDoesNotThrow(() -> dialog.button(JButtonMatcher.withName("cancel")).click());
    }

    @Test
    @DisplayName("Test AddressBookGUI remove function - Unit test")
    public void testAddressBookGUI_Remove_Unit() {
        int tableRowCount = window.table().rowCount();
        window.table().cell("firstname").click();
        window.button("delete").click();
        window.table().requireRowCount(tableRowCount - 1);
    }

//    @Test
//    @DisplayName("Test AddressBookGUI edit function with null first name - Unit test")
//    public void testAddressBookGUI_Edit_Null_First_Name_Unit() {
//        window.table().cell("firstname").click();
//        window.button("edit").click();
//        DialogFixture dialog = window.dialog();
//        dialog.textBox("firstName").setText(null);
//        dialog.button(JButtonMatcher.withName("ok")).click();
//        window.table().requireContents(new String[][]{{"lastname", null, "address", "city", "state", "33965", "1234567890"}});
//    }

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
    void remove() {
        //removes person at specified index
        testBookController.remove(0);
        //checks if the values are equal
        assertEquals(0, testBook.getRowCount(), "Testing removing the person");
    }

    @Test
    @DisplayName("Test the AddressBook add function ")
    void testAddressBookAdd() {
        int count = testBook.getRowCount();
        Person testPerson2 = new Person("Tyler", "Marlow",
                "601 East Tropical Way", "Plantation", "" +
                "Florida", "33317", "9546217953");
        testBook.add(testPerson2);
        assertEquals(count + 1, testBook.getRowCount());
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

    @DisplayName("Test Person - Create with null first name ")
    @Test
    void testPerson_FirstName_Null() {
        assertThrows(IllegalArgumentException.class, () -> new Person(null,
                "lastname", "address",
                "city", "state", "34112", "1234567890"));
    }

    @DisplayName("Test Person - Create with null last name ")
    @Test
    void testPerson_LastName_Null() {
        assertThrows(IllegalArgumentException.class, () -> new Person("firstname",
                null, "address",
                "city", "state", "34112", "1234567890"));
    }

    @DisplayName("Test Person - getPersons() ")
    @Test
    void testPerson_getPersons() {
        Person[] people;
        people = testBook.getPersons();
        assertEquals(1, people.length);
    }

    @DisplayName("Test Person - getPersons() ")
    @Test
    void testPerson_set() {
        Person testPerson2 = new Person("Tyler", "Marlow",
                "601 East Tropical Way", "Plantation", "" +
                "Florida", "33317", "9546217953");
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
        assertDoesNotThrow(() -> testBookController.save(testFile));
    }

    @DisplayName("Test the AddressBookController open function")
    @Test
    void testReadFile() {
        assertDoesNotThrow(() -> testBookController.open(testFile));
    }

    @DisplayName("Test the AddressBookController open function")
    @Test
    void testReadFile_notExisting() {
        File testFile = new File("testFile2.java");
        assertThrows(FileNotFoundException.class, () -> testBookController.open(testFile));
    }

    @DisplayName("Integration Test Save File")
    @Test
    void testSaveFile_FileSys() {
        FileSystem fs = new FileSystem();
        assertDoesNotThrow(() -> fs.saveFile(testBook, testFile));
    }

    @DisplayName("Integration Test Read File")
    @Test
    void testReadFile_FileSys() {
        FileSystem fs = new FileSystem();
        assertDoesNotThrow(() -> fs.readFile(testBook, testFile));
    }

    @DisplayName("Integration Test Read File not readable")
    @Test
    void testReadFile_FileSys_unreadable_file() {
        FileSystem fs = new FileSystem();
        File unreadableFile = mock(File.class);
        when(unreadableFile.canRead()).thenReturn(false);
        when(unreadableFile.exists()).thenReturn(true);

        assertThrows(FileNotFoundException.class, () -> fs.readFile(testBook, unreadableFile));    }

    @DisplayName("Test Person - Create with empty first name ")
    @Test
    void testPerson_FirstName_Empty() {
        assertThrows(IllegalArgumentException.class, () -> new Person("",
                "lastname", "address",
                "city", "state", "34112", "1234567890"));
    }

    @DisplayName("Test Person - Create with empty last name")
    @Test
    void testPerson_LastName_Empty() {
        assertThrows(IllegalArgumentException.class, () -> new Person("firstname",
                "", "address",
                "city", "state", "34112", "1234567890"));
    }

    @DisplayName("Test Person - Create with invalid zipcode")
    @Test
    void testPerson_InvalidZip() {
        assertThrows(IllegalArgumentException.class, () -> new Person("firstname",
                "lastname", "address",
                "city", "state", "zip", "1234567890"));
    }

    @DisplayName("Test Person - Create with invalid phone number")
    @Test
    void testPerson_InvalidPhone() {
        assertThrows(IllegalArgumentException.class, () -> new Person("firstname",
                "lastname", "address",
                "city", "state", "33965", "phone"));
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
        assertEquals("33965", testPerson.getZip());
    }

    @DisplayName("Test Person's getPhone method")
    @Test
    void testPerson_getPhone() {
        assertEquals("1234567890", testPerson.getPhone());
    }

    @Test
    void addressBook_clear_empty_AddressBook() {
        testBook.clear();

        assertDoesNotThrow(() -> testBook.clear());
    }


}