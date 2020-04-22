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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IntegrationTesting {

    @Mock
    File testFile = new File("testFile.txt");

    AddressBookController mockAddressBookController = mock(AddressBookController.class);
    AddressBook mockAddressBook = mock(AddressBook.class);

    Person testPerson = new Person("firstname", "lastname", "address",
            "city", "state", "33965", "1234567890");
    @Mock
    AddressBook addressBook = new AddressBook();

    public static FrameFixture window = null;

    @BeforeEach
    @Test
    void initialize() {
        addressBook.add(testPerson);
    }

    @AfterEach
    void cleanBook() {
        addressBook = new AddressBook();
    }
//    @Test
//    @DisplayName("Add Person GUI Test")
//    public void testAddPersonGUI() {
//
//        int initialCount=((AddressBookGUI)window.target()).getAddressBookController().getModel().
//                window.button("add").click();
//        DialogFixture dialog = window.dialog();
//        dialog.textBox("firstName").setText("Jordin");
//        dialog.textBox("lastName").setText("Medina");
//        dialog.textBox("address").setText("525 street drive");
//        dialog.textBox("city").setText("Naples");
//        dialog.textBox("state").setText("Florida");
//        dialog.textBox("zip").setText("34112");
//        dialog.textBox("phone").setText("2395721111");
//        dialog.button(JButtonMatcher.withName("ok")).click();
//
//        testBook =((AddressBookGUI)window.target()).getAddressBookController().getModel();
//        assertEquals("Jordin", testBook.get(2).getFirstName());    }

    @Test
    @DisplayName("Test the AddressBookController get function ")
    void testAddressBookControllerGet() {
        //creates controller with specified book
        assertEquals(testPerson.getFirstName(), mockAddressBookController.get(1).getFirstName());
    }


    @Test
    @DisplayName("Test the AddressBookController add function ")
    void testAddressBookControllerAdd() {
        Person testPerson2 = new Person("Tyler", "Marlow",
                "601 East Tropical Way", "Plantation", "" +
                "Florida", "33317", "9546217953");
        int count= mockAddressBookController.getModel().getRowCount();
        mockAddressBookController.add(testPerson);
        assertEquals( count+1, mockAddressBookController.getModel().getRowCount());
    }

    @Mock
    Person mockPerson = new Person("firstname", "lastname", "address",
            "city", "state", "34112", "2395721111");

    @DisplayName("Integration Test Save File")
    @Test
    void testSaveFile() {
        FileSystem fs = new FileSystem();
        assertDoesNotThrow(() -> fs.saveFile(addressBook, testFile));
    }

    @DisplayName("Integration Test Read File")
    @Test
    void testReadFile() {
        FileSystem fs = new FileSystem();
        assertDoesNotThrow(() -> fs.readFile(addressBook, testFile));
    }


}