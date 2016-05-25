<?php
include 'db_connect.php';

$response = array();

      
	$db_object = new DB_CONNECT();
    $db_connection = $db_object->connection;
    $user_id = mysqli_real_escape_string($db_connection, $_POST['user_id']);



        $check ="SELECT name, sex, age, job  FROM ".DB_DATABASE.".Users  WHERE user_id=\"".$user_id."\"";
   
        $db_connection->set_charset("utf8");
            $checksql=mysqli_query($db_connection,$check);

    if (mysqli_query($db_connection,$check)) {
        while($row = mysqli_fetch_assoc($checksql))
        $test[] = $row;
        $response["success"] = 1;
        $response["message"] = "User Data aquisition complete.";
        $response["person_data"] = $test;

        echo json_encode($response);
    } else {

        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
 

        echo json_encode($response);
    }
  

?>