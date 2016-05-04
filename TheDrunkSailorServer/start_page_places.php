<?php
include 'db_connect.php';

$response = array();

      
	$db_object = new DB_CONNECT();
    $db_connection = $db_object->connection;
 
    $user_ID = mysqli_real_escape_string($db_connection, $_POST['user_ID']);


    $check ="SELECT P.place_ID, P.name, AVG(R.rating)as rating, AVG(C.current_use) as current_use, R.timestamp FROM ".DB_DATABASE.".Ratings R, ".DB_DATABASE.".Places P, ".DB_DATABASE.".Current_Use C WHERE R.place_ID=P.place_ID AND C.place_id=P.place_ID AND R.user_ID =\"".$user_ID."\" GROUP BY P.name ORDER BY R.timestamp DESC";
    $checksql=mysqli_query($db_connection,$check);

    if (mysqli_query($db_connection,$check)) {
        while($row = mysqli_fetch_assoc($checksql))
        $test[] = $row;
        
        
    
        $response["success"] = 1;
        $response["message"] = "getting latest places successful.";
        $response["last_places"] = $test;
 

        echo json_encode($response);
    } else {

        $response["success"] = 0;
        $response["message"] = "No recently visisted.";
 

        echo json_encode($response);
    }
    
?>