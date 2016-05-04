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
        assertEquals("User 'pope already exists' ", false, success);

        success = DatabaseInterface.createUser("pope", "habemus", "John Dope", "m", 69, "Pope");
        assertEquals("Cant create user 'pope'", true,success);

        success = DatabaseInterface.login("pope", "habemus");
        assertEquals("can't login", true,success);

        success = DatabaseInterface.deleteUser("pope","habemus");
        assertEquals("can't delete user", success,true);

        success = DatabaseInterface.login("pope","habemus");
        assertEquals("successful login after delete", false, success);
    }

    @Test
    public void testHighlander(){
        boolean success = DatabaseInterface.login("Connor","MacLeod");
        assertEquals("User doesn't exist.",false,success);

        success = DatabaseInterface.createUser("Connor", "MacLeod", "Connor MacLeod", "m", 999, "Highlander");
        assertEquals("Create user failed.",true,success);

        success = DatabaseInterface.login("Connor", "MacLeod");
        assertEquals("Login failed.",true,success);

        success = DatabaseInterface.createUser("Connor", "MacLeod", "Connor MacLeod", "m", 999, "Highlander");
        assertEquals("There can be only one.",false,success);


        success = DatabaseInterface.deleteUser("Connor", "MacLeod");
        assertEquals("Delete user failed.",true,success);

        success = DatabaseInterface.login("Connor", "MacLeod");
        assertEquals(success,false);
    }

    @Test
    public void testDeleteUser(){
        boolean success =  DatabaseInterface.createUser("worldsuck", "dftba", "World Suck", "m", 66, "Evildoer");
        assertEquals("Cant create user 'worldsuck'", true,success);

        success = DatabaseInterface.login("worldsuck", "dftba");
        assertEquals("can't login", true,success);

        success = DatabaseInterface.deleteUser("worldsuck","dftba");
        assertEquals("can't delete user", true,success);

        success = DatabaseInterface.login("worldsuck","dftba");
        assertEquals("successful login after delete", false, success);
    }


    @Test
    public void testLoginUser(){
        //success =
        DatabaseInterface.createUser("testuser1", "password", "John Doe", "m", 14, "Coder");
        boolean success = DatabaseInterface.login("testuser1","password");
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