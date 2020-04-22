import AddressBook.*;
import GUI.AddressBookGUI;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.DialogFixture;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;
public class IntegrationTesting {

    //an arbitrary file used to test the file system
    @Mock
    File testFile = new File("testFile.txt");

    //an arbitrary person object with valid attribute values
    Person testPerson = new Person("firstname", "lastname", "address",
            "city", "state", "33965", "1234567890");

    // address book object used for testing
    @Mock
    AddressBook addressBook;

    // address book controller object used for testing
    @Mock
    AddressBookController mockAddressBookController;

    //AssertJ window used for testing the GUI.
    public static FrameFixture window = null;

    //number of people in address book in the beginning
    int initialCount;

    //this annotation is used to perform this function first befor each test runs
    @BeforeEach
    void initializeBook() {

        //initializes addressBook
        addressBook= new AddressBook();
        addressBook.add(testPerson);

        //gets address book size at start of test
        initialCount =addressBook.getRowCount();

        //initializes the controller
        mockAddressBookController = new AddressBookController(addressBook);

        //initializes the GUI and runs it in a window
        AddressBookGUI gui= GuiActionRunner
                .execute(() -> new AddressBookGUI(new AddressBookController(addressBook),addressBook));

        //assigns GUI window to a variable to allow for testing
        window = new FrameFixture(gui);
        window.show();
    }

    //this test is performed after each test
    @AfterEach
    void cleanBook() {
        //resets the GUI window
        window.cleanUp();
    }

    //Tests the delete button on the gui and that its functionality properly integrates with the AddressBook
    @Test
    @DisplayName("Delete Person GUI Test")
    public void testDeletePersonGUI() {
        //clicks the first row with the testPersonData
        window.table().cell("firstname").click();

        //clicks delete button
        window.button("delete").click();

        //assumes that there is now one less person in the address book
        assertEquals(initialCount-1, addressBook.getRowCount());
    }

    //this test adds a person using the GUI's add button
    @Test
    @DisplayName("Add Person GUI Test")
    public void testAddPersonGUI() {
        //clicks add button
        window.button("add").click();

        //gets dialog and inserts values into text boxes
        DialogFixture dialog = window.dialog();
        dialog.textBox("firstName").setText("Jordin");
        dialog.textBox("lastName").setText("Medina");
        dialog.textBox("address").setText("525 street drive");
        dialog.textBox("city").setText("Naples");
        dialog.textBox("state").setText("Florida");
        dialog.textBox("zip").setText("34112");
        dialog.textBox("phone").setText("2395721111");

        //clicks dialog ok button
        dialog.button(JButtonMatcher.withName("ok")).click();

        //assumes person count is now one higher
        assertEquals(initialCount+1, addressBook.getRowCount());
    }

    //this test tests saving the address book via the GUI
    @Test
    @DisplayName("Test the GUI save file function ")
    void testSaveFile_GUI() {
        //clicks add button
        window.button("add").click();

        //gets dialog and inserts values into text boxes
        DialogFixture dialog = window.dialog();
        dialog.textBox("firstName").setText("Jordin");
        dialog.textBox("lastName").setText("Medina");
        dialog.textBox("address").setText("525 street drive");
        dialog.textBox("city").setText("Naples");
        dialog.textBox("state").setText("Florida");
        dialog.textBox("zip").setText("34112");
        dialog.textBox("phone").setText("2395721111");

        //clicks dialog ok button
        dialog.button(JButtonMatcher.withName("ok")).click();

        //requires that the save menu items are enables
        window.menuItem("save").requireEnabled();
        window.menuItem("saveAs").requireEnabled();

        //clicks file then save
        window.menuItem("file").click();
        window.menuItem("save").click();

        //selects our test file to save address book to
        window.fileChooser().selectFile(testFile);

        //approves save
        window.fileChooser().approve();
        window.dialog().button(JButtonMatcher.withText("Yes")).click();
    }

    //this test validates the edit person button on the GUI
    @Test
    @DisplayName("Edit Person GUI Test")
    public void testEditPersonGUI() {
        //clicks the first row with the testPersonData
        window.table().cell("firstname").click();

        //clicks edit button
        window.button("edit").click();

        //gets dialog and inserts values into text nw value into first name text box
        DialogFixture dialog = window.dialog();
        dialog.textBox("firstName").setText("Luca");

        //clicks dialog ok button
        dialog.button(JButtonMatcher.withName("ok")).click();

        //assumes name in address book has changed
        assertEquals("Luca", addressBook.get(0).getFirstName());
    }

    //this test tests the controllers get function
    @Test
    @DisplayName("Test the AddressBookController get function ")
    void testAddressBookControllerGet() {

        //assumes that what the controller returns is the same as the test person
        assertEquals(testPerson, mockAddressBookController.get(0));
    }

    //tests adding a person through the controller
    @Test
    @DisplayName("Test the AddressBookController add function ")
    void testAddressBookControllerAdd() {

        //adds another person to the address book
        mockAddressBookController.add(new Person("Tyler", "Marlow",
                "601 East Tropical Way", "Plantation", "" +
                "Florida", "33317", "9546217953"));

        //assumes person count is now one higher
        assertEquals( initialCount+1, addressBook.getRowCount());
    }


}