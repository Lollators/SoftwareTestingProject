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
    @Mock
    AddressBook mockAddressBook = new AddressBook();

    @Mock
    AddressBookController mockAddressBookController = new AddressBookController(mockAddressBook);

    @Mock
    Person mockPerson = new Person("firstname", "lastname", "address",
            "city", "state", "zip", "phone");

    @DisplayName("Integration Test Save File")
    @Test
    void testSaveFile(){
        FileSystem fs = new FileSystem();
        assertDoesNotThrow(() -> fs.saveFile(mockAddressBook,testFile));
    }

    @DisplayName("Integration Test Read File")
    @Test
    void testReadFile(){
        FileSystem fs = new FileSystem();
        assertDoesNotThrow(() -> fs.readFile(mockAddressBook,testFile));
    }

    @DisplayName("Address Book Integration Test - getFirstName")
    @Test
    public void addressBookIntegrationTest() {
        AddressBook addressBook = mock(AddressBook.class);

        //stub
        when(addressBook.get(0)).thenReturn(mockPerson);

        AddressBookController adr = new AddressBookController(addressBook);
        assertEquals("firstname", adr.get(0).getFirstName());
    }


}