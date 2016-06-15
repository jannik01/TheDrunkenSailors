<?php
include 'db_connect.php';

$response = array();

      
	$db_object = new DB_CONNECT();
    $db_connection = $db_object->connection;
 


        $check ="SELECT name FROM ".DB_DATABASE.".Sector GROUP BY sector_ID ASC";
   
        $db_connection->set_charset("utf8");

            $checksql=mysqli_query($db_connection,$check);

    if (mysqli_query($db_connection,$check)) {
        while($row = mysqli_fetch_assoc($checksql))
        $test[] = $row;
        $response["success"] = 1;
        $response["message"] = "Sector aquisition.";
        $response["sector_name"] = $test;

 

        echo json_encode($response);
    } else {

        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
 

        echo json_encode($response);
    }
  

?>