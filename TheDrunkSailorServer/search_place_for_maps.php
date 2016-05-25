<?php
include 'db_connect.php';

$response = array();

if (isset($_POST["zipcode"])) {
      
	$db_object = new DB_CONNECT();
    $db_connection = $db_object->connection;
 
    $zipcode = mysqli_real_escape_string($db_connection, $_POST['zipcode']);

	$db_connection->set_charset("utf8");
    $check="SELECT * FROM Places WHERE zipcode=\"".$zipcode."\"";

    //echo json_encode($check);
	$checksql=mysqli_query($db_connection,$check);
        
    if (mysqli_query($db_connection,$check)) {
        while($row = mysqli_fetch_assoc($checksql))
        $test[] = $row;
        
    
        $response["success"] = 1;
        $response["message"] = "Place search successful.";
        $response["place_string"] = $test;
 

        echo json_encode($response);
    } else {

        $response["success"] = 0;
        $response["message"] = "No entries.";
 

        echo json_encode($response);
    }
} else {

    $response["success"] = 0;
    $response["message"] = "No zip code provided.";
 	echo json_encode($response);
}
?>