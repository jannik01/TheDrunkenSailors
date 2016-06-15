package at.tugraz.thedrunksailor;

import android.provider.ContactsContract;
import android.provider.Settings;
import android.test.ActivityInstrumentationTestCase2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

/**
 * Created by robert on 20.04.2016.
 */
public class DatabaseInterfaceTest extends ActivityInstrumentationTestCase2<FragmentManagerActivity> {

    public DatabaseInterfaceTest(){
        super(FragmentManagerActivity.class);
    }


    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCreateUser() throws JSONException {
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
    public void testDeleteUser() throws JSONException {
        boolean success =  DatabaseInterface.createUser("worldsuck", "dftba", "World Suck", "m", 66, "Evildoer");

        int user_id = DatabaseInterface.login("worldsuck", "dftba");
        assertTrue("Failure login ", 0 != user_id);

        success = DatabaseInterface.deleteUser("worldsuck","dftba");
        assertEquals("can't delete user", true,success);

        user_id = DatabaseInterface.login("worldsuck","dftba");
        assertTrue("sucess login after user deletion ", 0 == user_id);
    }


    @Test
    public void testLoginUser() throws JSONException {
        //success =
        DatabaseInterface.createUser("testuser1", "password", "John Doe", "m", 14, "Coder");

        int user_id = DatabaseInterface.login("testuser1","password");
        assertTrue("Failure login ", 0 != user_id);
        assertEquals("Logged in as wrong user.",user_id,Globals.uid);
        DatabaseInterface.logout();
        assertEquals("Logout doesn't work.",0,Globals.uid);
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
    public void testPlaceAlreadyExists() throws JSONException {
        DatabaseInterface.createPlace("pizza hut","big sad",2,"das 123","asdsda dd","8010","asads");
        boolean success = DatabaseInterface.createPlace("pizza hut","big sad",2,"das 123","asdsda dd","8010","asads");
        assertEquals("create Place failed",false, success);

    }

    @Test
    public void testSearchPlace() throws JSONException {


        JSONArray success = DatabaseInterface.searchPlace("","1","","","","","","");


        System.out.println(success.length());
        for (int i=0; i < success.length();i++){

            assertEquals("test of sector id","1", success.getJSONObject(i).getString("sector_ID"));

        }
        JSONArray success2 = DatabaseInterface.searchPlace("Mensa","1","","","","","","");


        System.out.println(success2.length());
        for (int i=0; i < success2.length();i++){
            if(success.getJSONObject(i).getString("name").equals("Mensa"))
            assertEquals("name search","Mensa", success2.getJSONObject(i).getString("name"));

        }
        success2 = DatabaseInterface.searchPlace("Mens2a","","","","","2","","");
        assertEquals("result not empty",0,success2.length());
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


    public void testGetData() throws JSONException {

        JSONArray details = DatabaseInterface.getPlaceData(25);


        System.out.println(details.length());
        for (int i=0; i < details.length();i++){

            assertEquals("test of place details","mjam", details.getJSONObject(i).getString("description"));

        }

    }

    public void testGetPlacesByPostal_no_Places() {
        JSONArray extensive_place_list = DatabaseInterface.getPlacesByPostal("H666");
        assertEquals("FAIL: Received places for non exisiting zip code.",extensive_place_list, new JSONArray() );
    }

    public void testGetPlacesByPostal() {
        JSONArray extensive_place_list = DatabaseInterface.getPlacesByPostal("8010");
        assertNotEquals("FAIL: Empty result.",extensive_place_list, new JSONArray() );

        try {
            assertNotNull("DatabaseInterface.getPlaces : no name", extensive_place_list.getJSONObject(0).getString("name"));

        } catch (JSONException e) {
            e.printStackTrace();
            assertTrue("FAILED: could not read name",false);
        }



        try {
            assertNotNull("DatabaseInterface.getPlaces : id", extensive_place_list.getJSONObject(0).getString("place_ID"));

        } catch (JSONException e) {
            e.printStackTrace();
            assertTrue("FAILED: could not read place_id",false);
        }
        try {
            assertNotNull("DatabaseInterface.getPlaces : no address", extensive_place_list.getJSONObject(0).getString("address"));

        } catch (JSONException e) {
            e.printStackTrace();
            assertTrue("FAILED: could not read address",false);
        }
        try {
            assertNotNull("DatabaseInterface.getPlaces : no zipcode", extensive_place_list.getJSONObject(0).getString("zipcode"));

        } catch (JSONException e) {
            e.printStackTrace();
            assertTrue("FAILED: could not read zip code",false);
        }
        try {
            assertNotNull("DatabaseInterface.getPlaces : no sector", extensive_place_list.getJSONObject(0).getString("sector_ID"));

        } catch (JSONException e) {
            e.printStackTrace();
            assertTrue("FAILED: could not read sector ID",false);
        }
        try {
            assertNotNull("DatabaseInterface.getPlaces : town", extensive_place_list.getJSONObject(0).getString("town"));

        } catch (JSONException e) {
            e.printStackTrace();
            assertTrue("FAILED: could not read town",false);
        }
        try {
            assertNotNull("DatabaseInterface.getPlaces : country", extensive_place_list.getJSONObject(0).getString("country"));

        } catch (JSONException e) {
            e.printStackTrace();
            assertTrue("FAILED: could not read country",false);
        }
    }

    public void testSearchPersonsIdIsFollowing() {
        JSONArray person_list = DatabaseInterface.searchPersonsIdIsFollowing(16);
        System.out.println(person_list);
        assertEquals("Wrong number of following persons.", 2 , person_list.length());


    }

    public void testGetPersonData() {
        JSONArray person_data = DatabaseInterface.getPersonData(94);
        assertTrue("There should be exactly one test user.",1==person_data.length());
        String name,age,sex,job;
        name = age = sex = job = "";
        try {
            name = person_data.getJSONObject(0).getString("name");
        } catch (JSONException e) {
            assertTrue("Could not get name",false);
        }
        try {
            age = person_data.getJSONObject(0).getString("age");
        } catch (JSONException e) {
            assertTrue("Could note get age",false);
        }
        try {
            sex = person_data.getJSONObject(0).getString("sex");
        } catch (JSONException e) {
            assertTrue("Could not get sex",false);
        }
        try {
            job = person_data.getJSONObject(0).getString("job");
        } catch (JSONException e) {
            assertTrue("Could not get job",false);
        }

        assertEquals("Wrong name","John Doe",name);
        assertEquals("Wrong sex","m",sex);
        assertEquals("Wrong age","14",age);
        assertEquals("Wrong job","Coder",job);
    }

    public void testSearchPerson() {
        JSONArray person_data = DatabaseInterface.searchPerson("John Doe","14","m","Coder","");

        int user_id = 0;
        String name,age,sex,job;
        name = age = sex = job = "";

        try {
            user_id = person_data.getJSONObject(0).getInt("user_id");
        } catch (JSONException e) {
            assertTrue("Could not get user id",false);
        }

        try {
            name = person_data.getJSONObject(0).getString("name");
        } catch (JSONException e) {
            assertTrue("Could not get name",false);
        }
        try {
            age = person_data.getJSONObject(0).getString("age");
        } catch (JSONException e) {
            assertTrue("Could note get age",false);
        }
        try {
            sex = person_data.getJSONObject(0).getString("sex");
        } catch (JSONException e) {
            assertTrue("Could not get sex",false);
        }
        try {
            job = person_data.getJSONObject(0).getString("job");
        } catch (JSONException e) {
            assertTrue("Could not get job",false);
        }

        assertEquals("Wrong user id",94,user_id);
        assertEquals("Wrong name","John Doe",name);
        assertEquals("Wrong sex","m",sex);
        assertEquals("Wrong age","14",age);
        assertEquals("Wrong job","Coder",job);

    }

    public void testRatePlace () {
        boolean success = DatabaseInterface.ratePlace("1","1");
        assertTrue(success);
    }
}