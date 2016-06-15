<?php
include 'db_connect.php';

$response = array();

      
	$db_object = new DB_CONNECT();
    $db_connection = $db_object->connection;
 
    $user_ID = mysqli_real_escape_string($db_connection, $_POST['user_ID']);


    $check ="SELECT P.place_ID, P.name, cast(AVG(T2.rating)as decimal(53,4))as rating, AVG(C.current_use) as current_use, C.timestamp
FROM ".DB_DATABASE.".Places P, ".DB_DATABASE.".Current_Use C, ".DB_DATABASE.".Ratings R
INNER JOIN
    (SELECT Places.place_ID, AVG(Ratings.rating) as rating
  FROM Places, Ratings
  WHERE Ratings.place_ID=Places.place_ID
  GROUP BY Places.place_ID) AS T2 
WHERE T2.place_ID=P.place_ID
AND C.current_use >0
AND C.place_id=P.place_ID
AND R.user_ID=\"".$user_ID."\"
GROUP BY P.name
ORDER BY R.timestamp DESC";
     $db_connection->set_charset("utf8");
    $checksql=mysqli_query($db_connection,$check);

    if (mysqli_query($db_connection,$check)) {
        while($row = mysqli_fetch_assoc($checksql))
        $test[] = $row;

        if(empty($test))
        {
            $response["success"] = 2;
            $response["message"] = "No places visisted.";
 

            echo json_encode($response);
        }
        else
        {
            $response["success"] = 1;
            $response["message"] = "getting latest places successful.";
            $response["last_places"] = $test; 

                    echo json_encode($response);

        }
            
    } else {

        $response["success"] = 0;
        $response["message"] = "No recently visisted.";
 

        echo json_encode($response);
    }
    
?>