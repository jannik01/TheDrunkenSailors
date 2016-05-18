<?php
include 'db_connect.php';

$response = array();

      
	$db_object = new DB_CONNECT();
    $db_connection = $db_object->connection;
    $place_ID = mysqli_real_escape_string($db_connection, $_POST['place_id']);



        $check ="SELECT P.address, P.description, P.zipcode, P.town,P.country, P.name, AVG(R.rating)as rating, AVG(C.current_use) as current_use FROM ".DB_DATABASE.".Ratings R, ".DB_DATABASE.".Places P, ".DB_DATABASE.".Current_Use C WHERE R.place_ID=P.place_ID AND C.place_id=P.place_ID AND R.place_ID =\"".$place_ID."\"";
   

            $checksql=mysqli_query($db_connection,$check);

    if (mysqli_query($db_connection,$check)) {
        while($row = mysqli_fetch_assoc($checksql))
        $test[] = $row;
        $response["success"] = 1;
        $response["message"] = "Place Data aquisition complete.";
        $response["place_data"] = $test;

 

        echo json_encode($response);
    } else {

        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
 

        echo json_encode($response);
    }
  

?>