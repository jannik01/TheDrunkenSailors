package at.tugraz.thedrunksailor;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by robert on 20.04.2016.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityTest(){
        super(MainActivity.class);
    }


    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
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

    @Test
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

    @Test
    public void testLoginUser(){
        boolean success = DatabaseInterface.createUser("testuser1", "password", "John Doe", "m", 14, "Coder");
        success = DatabaseInterface.login("testuser1","password");
        assertEquals(true,success);
    }

    @Test
    public void testLoginUserWrongPass() {
        boolean success = DatabaseInterface.login("testuser1","bruteforce");
        assertEquals(false,success);
    }

    @Test
    public void testLoginUserNotExists(){
        boolean success = DatabaseInterface.login("god","ThereBeLight");
        assertEquals(false,success);
    }

}