import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class Presentation3Test {
    @DisplayName("First Name")
    @Test
    void controllerOpenTest() {

        assertDoesNotThrow (testFile.createNewFile());
        assertThrows(SQLException.class, () -> mockAddressBookController.open(testFile));


    }

    @Mock
    File testFile = new File("testFile.txt");
    @Mock
    AddressBook mockAddressBook = new AddressBook();

    @Mock
    AddressBookController mockAddressBookController = new AddressBookController(mockAddressBook);

    @Mock
    Person mockPerson = new Person("firstname", "lastname", "address",
            "city", "state", "zip", "phone");
}