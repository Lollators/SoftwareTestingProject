import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Presentation2Tests {

    //An arbitrary person object used to test
    Person testPerson = new Person("firstname", "lastname", "address",
            "city", "state", "zip", "phone");

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

    // an arbitrary address book used to test
    AddressBook testBook = new AddressBook();

    //another arbitrary person used to test
    Person testPerson2 = new Person("Tyler", "Marlow", "601 East Tropical Way", "Plantation", "" +
            "Florida", "33317", "9546217953");

    //this annotation is used to perform this function first
    @BeforeEach
    void populateBook() {
        testBook.add(testPerson2);
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
        assertEquals("Tyler", testBook.getValueAt(0, 1));
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
        testBook.remove(0);
        //checks if the values are equal
        assertEquals(0, 0, "Testing removing the person");
    }

    //arbitrary address book object
    AddressBook myAddressBook = new AddressBook();

    //more arbitrary people
    Person TestPerson = new Person("Jordan", "Sasek", "2253 Carnaby Ct", "Lehigh Acres ", "Florida", "33973", "9414563576");
    Person TestPerson1 = new Person("Matthew", "Williams", "3162 Cordova Ter.", "Nort Port", "Florida", "34291", "9414563577");

    //This test gets the number of rows in the address book gui
    @Test
    @DisplayName("Test the Row Count")
    public void getRowCountTest() {
        //adds people to the address book
        myAddressBook.add(TestPerson);
        myAddressBook.add(TestPerson1);

        //checks if the values are equal
        assertEquals(2, myAddressBook.getRowCount());
    }

    //return the expected number of columns given 7 attributes
    @Test
    @DisplayName("Test the Column Count")
    public void getColumnCountTest() {
        //checks if the values are equal
        assertEquals(7, TestPerson.fields.length);
    }

}
