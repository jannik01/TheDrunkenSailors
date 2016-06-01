<?php
include 'db_connect.php';

$response = array();

      
	$db_object = new DB_CONNECT();
    $db_connection = $db_object->connection;

    $user_id = mysqli_real_escape_string($db_connection, $_POST['user_id']);
    
    $db_connection->set_charset("utf8");

    $check="SELECT U.user_id, U.name, U.sex, U.age, U.job 
    FROM ".DB_DATABASE.".Users U, ".DB_DATABASE.".User_Links UL WHERE U.user_id = UL.user_id_leader AND UL.user_id_follower=\"".$user_id."\"";
    
    $checksql=mysqli_query($db_connection,$check);
        
    if (mysqli_query($db_connection,$check)) {
        while($row = mysqli_fetch_assoc($checksql))
        $test[] = $row;
        
    
        $response["success"] = 1;
        $response["message"] = "Person search successful.";
        $response["person_list"] = $test;
 

        echo json_encode($response);
    } else {

        $response["success"] = 0;
        $response["message"] = "No entries.";
 

        echo json_encode($response);
    }
  
?>