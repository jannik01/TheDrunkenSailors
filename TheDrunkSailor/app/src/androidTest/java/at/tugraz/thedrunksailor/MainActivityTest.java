package at.tugraz.thedrunksailor;

import android.provider.Settings;
import android.test.ActivityInstrumentationTestCase2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
        //assertEquals("Cant create user 'pope'", true,success);

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
        //assertEquals("Cant create user 'pope'", true,success);

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
    @Test
    public void testCreatePlace(){
        boolean success = DatabaseInterface.createPlace("pizza hut","big pizza",3,"asdfga 123","South Korea","71824");
        assertEquals("create Place failed",false, success);

    }

    @Test
    public void testSearchPlace() throws JSONException {


        JSONArray success = DatabaseInterface.searchPlace("",1,"","","",0.0,0.0,0.0,0.0);


        System.out.println(success.length());
        for (int i=0; i < success.length();i++){

            assertEquals("test of sector id","1", success.getJSONObject(i).getString("sector_ID"));

        }
        JSONArray success2 = DatabaseInterface.searchPlace("mcdonalds",1,"","","",0.0,4.0,0.0,0.0);


        System.out.println(success2.length());
        for (int i=0; i < success2.length();i++){
            if(success.getJSONObject(i).getString("name").equals("mcdonalds"))
            assertEquals("name search","mcdonalds", success2.getJSONObject(i).getString("name"));

        }
        success2 = DatabaseInterface.searchPlace("mcdonalds",1,"","","",0.0,2.0,0.0,0.0);



            assertNull(success2);


    }
    @Test
    public void testGetSectors(){
        JSONArray success = DatabaseInterface.getSectors();
        System.out.println(success);
        assertEquals("create Place failed",true, success.length()>=1);

    }
    @Test
    public void testStartPage() throws JSONException {


        JSONArray success = DatabaseInterface.startPagePlaces(16);

        System.out.println(success);
        for (int i = 0; i < success.length(); i++) {
            if(success.getJSONObject(i).getString("name").equals("Opera"))
            {
                assertEquals("", "Opera", success.getJSONObject(i).getString("name"));
                assertEquals( 1.6667, success.getJSONObject(i).getDouble("current_use"),0);
                assertEquals( 4, success.getJSONObject(i).getDouble("rating"),0);
            }


        }



    }
}