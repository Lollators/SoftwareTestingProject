import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.File;
import java.io.FileNotFoundException;
import AddressBook.*;
import AddressBook.Person;
import GUI.AddressBookGUI;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.DialogFixture;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Rule;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.rules.TemporaryFolder;

class UnitTesting extends AssertJSwingJUnitTestCase {
    // an arbitrary address book used to test
    AddressBook testBook = new AddressBook();
    AddressBookController testBookController = new AddressBookController(testBook);
    //An arbitrary person object used to test
    Person testPerson = new Person("firstname", "lastname", "address",
            "city", "state", "33965", "1234567890");
    //another arbitrary person used to test
    Person testPerson2 = new Person("Tyler", "Marlow",
            "601 East Tropical Way", "Plantation", "" +
            "Florida", "33317", "9546217953");

    File testFile = new File("testFile.txt");
    public static FrameFixture window = null;

    @Override
    protected void onSetUp() {
        FailOnThreadViolationRepaintManager.install();
    }

    //this annotation is used to perform this function first
    @BeforeEach
    void initializeBook() {
        testBook.add(testPerson);
        testBook.add(testPerson2);
        AddressBookGUI gui= GuiActionRunner
            .execute(() -> new AddressBookGUI(testBookController,testBook));
        window = new FrameFixture(gui);
        window.show();
    }

    @AfterEach
    void cleanBook() {
        for (int i = 0; i < testBook.getRowCount(); i++) {
            testBook.remove(i);
        }
        window.cleanUp();
    }

    @Test
    @DisplayName("Edit Person GUI Test")
    public void testEditPersonGUI() {
        window.table().cell("Tyler").click();
        window.button("edit").click();

        DialogFixture dialog = window.dialog();
        dialog.textBox("firstName").setText("Luca");
        dialog.button(JButtonMatcher.withName("ok")).click();

        assertEquals("Luca", testBookController.get(1).getFirstName());
    }

    @Test
    @DisplayName("Delete Person GUI Test")
    public void testDeletePersonGUI() {
        window.table().cell("Tyler").click();
        window.button("delete").click();

        assertEquals(1, testBook.getRowCount());
    }

    @Test
    @DisplayName("Add Person GUI Test")
    public void testAddPersonGUI() {
        window.button("add").click();
        DialogFixture dialog = window.dialog();
        dialog.button(JButtonMatcher.withName("cancel"));
    }


//    //test the Main function of the GUI.AddressBookGUI class
//    @Test
//    @DisplayName("Test the GUI.AddressBookGUI main function")
//    void testAddressBookGUIMain() {
//        //assumes that calling the main of the function does not throw exceptions
//        assertDoesNotThrow(() -> AddressBookGUI.main(null));
//    }

//    @Test
//    @DisplayName("x")
//    @AfterEach()
//    void x() {
////        GUI.AddressBookGUI gui = new GUI.AddressBookGUI(testBookController, testBook);
////
////        assertDoesNotThrow(() -> gui.dispatchEvent(new WindowEvent(gui, WindowEvent.WINDOW_CLOSING)));
//
//
//    }
//    @Test
//    @DisplayName("x")
//    void x() {
//        GUI.AddressBookGUI gui = new GUI.AddressBookGUI(testBookController, testBook);
//        GUI.PersonDialog pd = mock(GUI.PersonDialog.class);//new GUI.PersonDialog(new Frame(), testPerson);
//
//        JFileChooser fc = mock(JFileChooser.class);
//        JButton mockAddButton = gui.getAddButton();
//        when(pd.showDialog()).thenReturn(GUI.PersonDialog.Result.OK);
//        when(pd.getPerson()).thenReturn(testPerson3);
//        mockAddButton.doClick();
//
//        AddressBook.AddressBook book = gui.getAddressBook();
//        assertEquals(testPerson3, book.get(book.getRowCount()-1));
//
//
//    }
//    @Test
//    @DisplayName("Test the GUI.AddressBookGUI open file function when an irrelevant action is selected")
//    void testOpenFileWrongAction() {
//        GUI.AddressBookGUI gui = new GUI.AddressBookGUI(testBookController, testBook);
//        JFileChooser fc = mock(JFileChooser.class);
//
//        when(gui.getOpenItem().doClick()).then(); thenThrow(Exception.class);
////        when(fc.getSelectedFile()).thenReturn(testFile);
//
//        assertDoesNotThrow(() -> gui.getOpenItem().doClick());
//
//    }
//
//    @Test
//    @DisplayName("Test the GUI.AddressBookGUI save file as function when an irrelevant action is selected")
//    void testSaveAsWrongAction() {
//        GUI.AddressBookGUI gui = new GUI.AddressBookGUI(testBookController, testBook);
//
//        JFileChooser fc = mock(JFileChooser.class);
//
//        when(fc.showSaveDialog(gui)).thenReturn(1);
//
//        assertDoesNotThrow(() -> gui.saveAsItemAction(fc));
//
//    }
//    @Test
//    @DisplayName("Test the GUI.AddressBookGUI save file as function when an irrelevant action is selected")
//    void x() {
//        GUI.AddressBookGUI gui = new GUI.AddressBookGUI(testBookController, testBook);
//        try {
//            gui.main(null);
//
//            JMenuItem saveOption = gui.getSaveItem();
//            assertDoesNotThrow(() -> saveOption.doClick());
//
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
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

    //test the return of the first name and last name using the toString() function for the class AddressBook.Person
    @Test
    @DisplayName("Test the AddressBook.Person toString() function")
    void testToString() {
        //checks if the values are equal
        assertEquals("lastname, firstname", testPerson.toString());
    }

    //This tests checks the last name of the person object
    @Test
    @DisplayName("Test the AddressBook.Person getField() function - returns last name")
    void testReturnLastName() {
        //checks if the values are equal
        assertEquals("lastname", testPerson.getField(0));
    }

    //This test checks the first name of the person object
    @Test
    @DisplayName("Test the AddressBook.Person getField() function - returns first name")
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

    @DisplayName("Test AddressBook.Person - Create with null first name ")
    @Test
    void testPerson_FirstName_Null() {
        assertThrows(IllegalArgumentException.class, () -> new Person(null,
                "lastname", "address",
                "city", "state", "zip", "1234567890"));
    }

    @DisplayName("Test AddressBook.Person - Create with null last name ")
    @Test
    void testPerson_LastName_Null() {
        assertThrows(IllegalArgumentException.class, () -> new Person("firstname",
                null, "address",
                "city", "state", "zip", "1234567890"));
    }

    @DisplayName("Test AddressBook.Person - getPersons() ")
    @Test
    void testPerson_getPersons() {
        Person[] people;
        people = testBook.getPersons();
        assertEquals(2, people.length);
    }

    @DisplayName("Test AddressBook.Person - getPersons() ")
    @Test
    void testPerson_set() {
        testBookController.set(0, testPerson2);
        assertEquals("Tyler", testBook.getValueAt(0, 1));
    }

    @Test
    @DisplayName("Test the AddressBook.AddressBookController constructor with parameter ")
    void testAddressBookControllerConstructor() {
        //creates controller with specified book
        assertDoesNotThrow(() -> new AddressBookController(testBook));
    }

    @Test
    @DisplayName("Test the AddressBook.AddressBookController get function ")
    void testAddressBookControllerGet() {
        //creates controller with specified book
        assertEquals(testPerson2.getFirstName(), testBookController.get(1).getFirstName());
    }

    @Test
    @DisplayName("Test the AddressBook.AddressBookController add function ")
    void testAddressBookControllerAdd() {
        //creates controller with specified book
        testBookController.add(testPerson);
        assertEquals(3, testBook.getRowCount());
    }

    @Test
    @DisplayName("Test the AddressBook.AddressBookController clear function ")
    void testAddressBookControllerClear() {
        //creates controller with specified book
        testBookController.clear();
        assertEquals(0, testBook.getRowCount());
    }

    @Test
    @DisplayName("Test the AddressBook.AddressBookController getModel function ")
    void testAddressBookController_getModel() {
        //creates controller with specified book
        AddressBook test = testBookController.getModel();
        assertEquals(testBook.getValueAt(0, 1), test.getValueAt(0, 1));
    }

    @DisplayName("Test the AddressBook.AddressBookController save function")
    @Test
    void testAddressBookControllerSaveFile() {
        assertDoesNotThrow(() -> testBookController.save(testFile));
    }

    @DisplayName("Test the AddressBook.AddressBookController open function")
    @Test
    void testReadFile() {
        assertDoesNotThrow(() -> testBookController.open(testFile));
    }

    @DisplayName("Test the AddressBook.AddressBookController open function")
    @Test
    void testReadFile_notExisting() {
        File testFile = new File("testFile2.java");
        assertThrows(FileNotFoundException.class, () -> testBookController.open(testFile));
    }

    @DisplayName("Test AddressBook.Person - Create with empty first name ")
    @Test
    void testPerson_FirstName_Empty() {
        assertThrows(IllegalArgumentException.class, () -> new Person("",
                "lastname", "address",
                "city", "state", "zip", "1234567890"));
    }

    @DisplayName("Test AddressBook.Person - Create with empty last name")
    @Test
    void testPerson_LastName_Empty() {
        assertThrows(IllegalArgumentException.class, () -> new Person("firstname",
                "", "address",
                "city", "state", "zip", "1234567890"));
    }

    @DisplayName("Test AddressBook.Person - Create with invalid zipcode")
    @Test
    void testPerson_InvalidZip() {
        assertThrows(IllegalArgumentException.class, () -> new Person("firstname",
            "lastname", "address",
            "city", "state", "zip", "1234567890"));
    }

    @DisplayName("Test AddressBook.Person - Create with invalid phone number")
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
    void testPerson_getLastName(){
        assertEquals("lastname", testPerson.getLastName());
    }
    @DisplayName("Test Person's getAddress method")
    @Test
    void testPerson_getAddress(){
        assertEquals("address", testPerson.getAddress());
    }
    @DisplayName("Test Person's getCity method")
    @Test
    void testPerson_getCity(){
        assertEquals("city", testPerson.getCity());
    }
    @DisplayName("Test Person's getState method")
    @Test
    void testPerson_getState(){
        assertEquals("state", testPerson.getState());
    }
    @DisplayName("Test Person's getZip method")
    @Test
    void testPerson_getZip(){
        assertEquals("33965", testPerson.getZip());
    }
    @DisplayName("Test Person's getPhone method")
    @Test
    void testPerson_getPhone(){
        assertEquals("1234567890", testPerson.getPhone());
    }

//    @DisplayName("Test GUI.PersonDialog")
//    @Test
//    void testPersonDialog() {
//        GUI.PersonDialog pd = new GUI.PersonDialog(new Frame());
//        GUI.PersonDialog.Result showResult = pd.showDialog();
//
//        assertEquals(GUI.PersonDialog.Result.CANCEL, showResult);
//    }

//    @DisplayName("Test GUI.PersonDialog")
//    @Test
//    void testPersonDialog_getPerson_Null() {
//        PersonDialog pd = new PersonDialog(new Frame());
//
//        assertEquals(null, pd.getPerson());
//    }
//
//    @DisplayName("Test GUI.PersonDialog")
//    @Test
//    void testPersonDialog_getPerson_emptyFirstName() {
//        //another arbitrary person used to test
//        Person testPerson3 = mock(Person.class);
////        AddressBook.Person testPerson3 = new AddressBook.Person("", "fakelastname", "address",
////                "city", "state", "zip", "1234567890");
//        when(testPerson3.getLastName()).thenReturn("lastname");
//        PersonDialog pd = new PersonDialog(new Frame(), testPerson3);
//
//        assertEquals("lastname", pd.getPerson().getLastName());
//    }

}