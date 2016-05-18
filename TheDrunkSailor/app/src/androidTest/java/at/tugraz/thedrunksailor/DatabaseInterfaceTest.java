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
public class DatabaseInterfaceTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public DatabaseInterfaceTest(){
        super(MainActivity.class);
    }


    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCreateUser(){
        int user_id = DatabaseInterface.login("pope","habemus");
        assertTrue("User 'pope already exists' ", 0 == user_id);

        boolean success = DatabaseInterface.createUser("pope", "habemus", "John Dope", "m", 69, "Pope");
        assertEquals("Cant create user 'pope'", true,success);

        user_id = DatabaseInterface.login("pope", "habemus");
        assertTrue("Failure login ", 0 != user_id);

        success = DatabaseInterface.createUser("pope", "habemus", "John Dope", "m", 69, "Pope");
        assertEquals("Duplicate creation fail", false, success);

        success = DatabaseInterface.deleteUser("pope","habemus");
        assertEquals("can't delete user", success,true);

        user_id = DatabaseInterface.login("pope","habemus");
        assertTrue("Sucessful login after delete user ", 0 == user_id);
    }

    @Test
    public void testDeleteUser(){
        boolean success =  DatabaseInterface.createUser("worldsuck", "dftba", "World Suck", "m", 66, "Evildoer");

        int user_id = DatabaseInterface.login("worldsuck", "dftba");
        assertTrue("Failure login ", 0 != user_id);

        success = DatabaseInterface.deleteUser("worldsuck","dftba");
        assertEquals("can't delete user", true,success);

        user_id = DatabaseInterface.login("worldsuck","dftba");
        assertTrue("sucess login after user deletion ", 0 == user_id);
    }


    @Test
    public void testLoginUser(){
        //success =
        DatabaseInterface.createUser("testuser1", "password", "John Doe", "m", 14, "Coder");

        int user_id = DatabaseInterface.login("testuser1","password");
        assertTrue("Failure login ", 0 != user_id);
    }

    @Test
    public void testLoginUserWrongPass() {
        int user_id = DatabaseInterface.login("testuser1","bruteforce");
        assertTrue("Failure login wrong password ", 0 == user_id);
    }

    @Test
    public void testLoginUserNotExists(){
        int user_id = DatabaseInterface.login("god","ThereBeLight");
        assertTrue("Failure login user exists ", 0 == user_id);
    }
    @Test
    public void testCreatePlace(){
        boolean success = DatabaseInterface.createPlace("pizza asdads","big sad",2,"das 123","asdsda dd","8f8","asads");
        assertEquals("create Place failed",true, success);

    }

    @Test
    public void testSearchPlace() throws JSONException {


        JSONArray success = DatabaseInterface.searchPlace("",1,"","","","",0.0,0.0,0.0,0.0);


        System.out.println(success.length());
        for (int i=0; i < success.length();i++){

            assertEquals("test of sector id","1", success.getJSONObject(i).getString("sector_ID"));

        }
        JSONArray success2 = DatabaseInterface.searchPlace("mcdonalds",1,"","","","",0.0,4.0,0.0,0.0);


        System.out.println(success2.length());
        for (int i=0; i < success2.length();i++){
            if(success.getJSONObject(i).getString("name").equals("mcdonalds"))
            assertEquals("name search","mcdonalds", success2.getJSONObject(i).getString("name"));

        }
        success2 = DatabaseInterface.searchPlace("mcdonalds",1,"","","","",0.0,2.0,0.0,0.0);
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