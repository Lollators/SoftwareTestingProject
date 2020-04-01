import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

//add two people to a list and make list length 2

public class AddressBookTest {
    AddressBook myAddressBook = new AddressBook();
    Person TestPerson = new Person("Jordan", "Sasek", "2253 Carnaby Ct", "Lehigh Acres ", "Florida", "33973", "9414563576");
    Person TestPerson1 = new Person( "Matthew", "Williams", "3162 Cordova Ter.", "Nort Port", "Florida", "34291", "9414563577");

    //return the expected number of columns given 7 attributes
    @Test
    @DisplayName ("Test the Column Count")
    public void getColumnCountTest() {
     assertEquals(7, TestPerson.fields.length);
    }

    @Test
    @DisplayName("Test the Row Count")
    public void getRowCountTest() {
        myAddressBook.add(TestPerson);
        myAddressBook.add(TestPerson1);
        assertEquals(2, myAddressBook.getRowCount());
    }

}



