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

    Person testPerson = new Person("firstname", "lastname", "address",
        "city", "state", "33965", "1234567890");
    @Mock
    AddressBook addressBook = new AddressBook();
    AddressBookController mockAddressBookController;
    public static FrameFixture window = null;
    AddressBookController guiAddressBookController;
    AddressBook guiAddressBook;
    //this annotation is used to perform this function first
    @BeforeEach
    void initializeBook() {
        addressBook.add(testPerson);
        mockAddressBookController = new AddressBookController(addressBook);
        AddressBookGUI gui= GuiActionRunner
            .execute(() -> new AddressBookGUI(new AddressBookController(addressBook),addressBook));
        window = new FrameFixture(gui);
        window.show();
        guiAddressBookController= ((AddressBookGUI)window.target()).getAddressBookController();
        guiAddressBook=guiAddressBookController.getModel();
    }
    @AfterEach
    void cleanBook() {
        for (int i = 0; i < addressBook.getRowCount(); i++) {
            addressBook.remove(i);
        }
        window.cleanUp();
    }
    @Test
    @DisplayName("Delete Person GUI Test")
    public void testDeletePersonGUI() {
        window.table().cell("firstname").click();
        window.button("delete").click();
        assertEquals(0, addressBook.getRowCount());
    }
    @Test
    @DisplayName("Add Person GUI Test")
    public void testAddPersonGUI() {
        int initialCount=guiAddressBook.getRowCount();
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
        addressBook =((AddressBookGUI)window.target()).getAddressBookController().getModel();
        assertEquals(initialCount+1, addressBook.getRowCount());
    }
    @Test
    @DisplayName("Edit Person GUI Test")
    public void testEditPersonGUI() {
        window.table().cell("firstname").click();
        window.button("edit").click();
        DialogFixture dialog = window.dialog();
        dialog.textBox("firstName").setText("Luca");
        dialog.button(JButtonMatcher.withName("ok")).click();
        addressBook =((AddressBookGUI)window.target()).getAddressBookController().getModel();
        assertEquals("Luca", addressBook.get(0).getFirstName());
    }
    @Test
    @DisplayName("Test the AddressBookController get function ")
    void testAddressBookControllerGet() {
        //creates controller with specified book
        assertEquals(testPerson, mockAddressBookController.get(0));
    }

    @Test
    @DisplayName("Test the AddressBookController add function ")
    void testAddressBookControllerAdd() {
        int initialCount= mockAddressBookController.getModel().getRowCount();
        Person testPerson2 = new Person("Tyler", "Marlow",
            "601 East Tropical Way", "Plantation", "" +
            "Florida", "33317", "9546217953");
        int count= mockAddressBookController.getModel().getRowCount();
        mockAddressBookController.add(testPerson);
        assertEquals( count+1, mockAddressBookController.getModel().getRowCount());
    }

    @Test
    @DisplayName("Test the GUI save file function ")
    void testSaveFile_GUI() {
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

        window.menuItem("save").requireEnabled();
        window.menuItem("saveAs").requireEnabled();
        window.menuItem("file").click();
        window.menuItem("save").click();
        window.fileChooser().selectFile(testFile);
        window.fileChooser().approve();
        window.dialog().button(JButtonMatcher.withText("Yes")).click();
    }


}