package at.tugraz.thedrunksailor;

import android.provider.ContactsContract;

import junit.framework.TestCase;

import static org.junit.Assert.*;

/**
 * Created by robert on 20.04.2016.
 */
public class DatabaseInterfaceTest extends TestCase {

    public void testHabemusPapam(){
        boolean success = DatabaseInterface.login("pope","habemus");
        assertEquals(success, false);

        success = DatabaseInterface.createUser("pope", "habemus", "John Dope", "m", 69, "Pope");
        assertEquals(true,success);

        success = DatabaseInterface.login("pope", "habemus");
        assertEquals(true,success);

        success = DatabaseInterface.deleteUser("pope","habemus");
        assertEquals(success,true);

        success = DatabaseInterface.login("pope","habemus");
        assertEquals(success,false);
    }


    public void testHighlander(){
        boolean success = DatabaseInterface.login("Connor","MacLeod");
        assertEquals(success, false);

        success = DatabaseInterface.createUser("Connor", "MacLeod", "Connor MacLeod", "m", 999, "Highlander");
        assertEquals(true,success);

        success = DatabaseInterface.login("Connor", "MacLeod");
        assertEquals(true,success);

        success = DatabaseInterface.createUser("Connor", "MacLeod", "Connor MacLeod", "m", 999, "Highlander");
        assertEquals(false,success);


        success = DatabaseInterface.deleteUser("Connor", "MacLeod");
        assertEquals(success,true);

        success = DatabaseInterface.login("Connor", "MacLeod");
        assertEquals(success,false);
    }


    public void testLoginUser(){
    boolean success = DatabaseInterface.login("testuser1","password");
    assertEquals(true,success);
    }

    public void testLoginUserWrongPass() {
        boolean success = DatabaseInterface.login("testuser1","bruteforce");
        assertEquals(false,success);
    }

    public void testLoginUserNotExists(){
        boolean success = DatabaseInterface.login("god","ThereBeLight");
        assertEquals(false,success);
    }

}