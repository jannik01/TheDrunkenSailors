<?php
include 'db_connect.php';

$response = array();

if (isset($_POST["user_name"],$_POST["password"], $_POST["name"],$_POST["sex"],$_POST["age"],$_POST["job"])) {
      
	$db_object = new DB_CONNECT();
    $db_connection = $db_object->connection;
 
    $user_name = mysqli_real_escape_string($db_connection, $_POST['user_name']);
    $password = $_POST['password'];
    //$password_hash = mysqli_real_escape_string($db_connection, $_POST['password_hash']);
    $name = mysqli_real_escape_string($db_connection, $_POST['name']);
    $sex = mysqli_real_escape_string($db_connection, $_POST['sex']);
    $age = mysqli_real_escape_string($db_connection, $_POST['age']);
    $job = mysqli_real_escape_string($db_connection, $_POST['job']);

 

    $check ="SELECT * FROM ".DB_DATABASE.".Users WHERE user_name=\"".$user_name."\"";
    $checksql=mysqli_query($db_connection,$check);
    while($row = mysqli_fetch_assoc($checksql))
    	$test[] = $row;
    if($test)
    {
	$response["success"] = "0";
        $response["message"] = "Username already taken.";
 

        echo json_encode($response);
    }
	
	//$password_hash = password_hash($password , PASSWORD_BCRYPT );
	$password_hash=$password;
	
    $sql = "INSERT INTO ".DB_DATABASE.".Users(user_name, password_hash, name, sex, age, job) VALUES('$user_name', '$password_hash','$name','$sex','$age','$job')";

    if (mysqli_query($db_connection,$sql)) {

        $response["success"] = "1";
        $response["message"] = "User successfully created.";
 

        echo json_encode($response);
    } else {

        $response["success"] = "0";
        $response["message"] = "Oops! An error occurred.";
 

        echo json_encode($response);
    }
} else {

    $response["success"] = "0";
    $response["message"] = "Required field(s) is missing";
 

    echo json_encode($response);
}
?>