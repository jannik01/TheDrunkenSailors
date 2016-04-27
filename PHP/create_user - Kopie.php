<?php

 
$response = array();
$response = $_POST;
echo json_encode($response);
// check for required fields
if (isset($_POST["user_name"]) && isset($_POST["password_salt"]) && isset($_POST["password_hash"]) && isset($_POST["name"]) && isset($_POST["sex"]) && isset($_POST["age"]) && isset($_POST["job"])) {
 
    $user_name = $_POST['user_name'];
    $password_salt = $_POST['password_salt'];
    $password_hash = $_POST['password_hash'];
    $name = $_POST['name'];
    $sex = $_POST['sex'];
    $age = $_POST['age'];
    $job = $_POST['job'];

     require_once __DIR__ . '/db_connect.php';
 
    $db = new DB_CONNECT();
 

    // mysql inserting a new row
    $sql = "INSERT INTO 1258689db1.Users(user_name, password_salt, password_hash, name, sex, age, job) VALUES('$user_name', '$password_salt', '$password_hash','$name','$sex','$age','$job')";
 
    // check if row inserted or not
    if (mysql_query( $sql,$db)) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "User successfully created.";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
 
        // echoing JSON response
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required fiadjkadjsdjknasdjkkjndeld(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
?>