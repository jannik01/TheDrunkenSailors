<?php
include 'db_connect.php';

$response = array();

if (isset($_POST["sector_ID"])) {
      
	$db_object = new DB_CONNECT();
    $db_connection = $db_object->connection;
 
    $place_name = mysqli_real_escape_string($db_connection, $_POST['place_name']);
            $place_name=utf8_encode($place_name);
    $sector_ID = mysqli_real_escape_string($db_connection, $_POST['sector_ID']);
    $address = mysqli_real_escape_string($db_connection, $_POST['address']);
            $address=utf8_encode($address);
    $country = mysqli_real_escape_string($db_connection, $_POST['country']);
            $country=utf8_encode($country);
    $zipcode = mysqli_real_escape_string($db_connection, $_POST['zipcode']);
    $town = mysqli_real_escape_string($db_connection, $_POST['town']);
            $town=utf8_encode($town);

    $min_use = mysqli_real_escape_string($db_connection, $_POST['min_use']);
    $max_use = mysqli_real_escape_string($db_connection, $_POST['max_use']);
    $min_rating = mysqli_real_escape_string($db_connection, $_POST['min_rating']);
    $max_rating = mysqli_real_escape_string($db_connection, $_POST['max_rating']);

     $db_connection->set_charset("utf8");
     if ($min_use!="" or $max_use != ""){

     
    $check="SELECT T.place_ID, result , current_use, P.sector_ID, P.name
    FROM Places P, (
        SELECT R.place_ID, AVG(R.rating) as result , T2.currentuse as current_use 
        FROM Ratings R, (
            SELECT C.place_id as c_place_id, AVG(C.current_use) as currentuse 
            FROM Current_Use C WHERE C.timestamp BETWEEN NOW() - INTERVAL 3 HOUR AND NOW() GROUP BY C.place_id) AS T2 
        WHERE R.place_ID = T2.c_place_id 
        GROUP BY R.place_ID) as T 
    WHERE P.place_ID = T.place_ID AND P.sector_ID=\"".$sector_ID."\"";
}
else
{
    $check="SELECT T.place_ID, result , current_use, P.sector_ID, P.name
    FROM Places P, (
        SELECT R.place_ID, AVG(R.rating) as result , T2.currentuse as current_use 
        FROM Ratings R, (
            SELECT C.place_id as c_place_id, AVG(C.current_use) as currentuse 
            FROM Current_Use C GROUP BY C.place_id) AS T2 
        WHERE R.place_ID = T2.c_place_id 
        GROUP BY R.place_ID) as T 
    WHERE P.place_ID = T.place_ID AND P.sector_ID=\"".$sector_ID."\"";
}
    if($min_rating!="")
        $check.=" AND result >= ".$min_rating;
    if($max_rating!="")
        $check.=" AND result <= ".$max_rating;
    if($min_use!="")
        $check.=" AND current_use >= ".$min_use;
    if($max_use!="")
        $check.=" AND current_use <= ".$max_use;
    if($place_name!="") { $check.=" AND name=\"".$place_name."\""; }
    if($address!="") { $check.=" AND address=\"".$address."\""; }
    if($country!="") { $check.=" AND country=\"".$country."\""; }
    if($zipcode!="") { $check.=" AND zipcode=\"".$zipcode."\""; }
    if($town!="") { $check.=" AND town=\"".$town."\""; }


    //echo json_encode($check);
            $checksql=mysqli_query($db_connection,$check);
        
    if (mysqli_query($db_connection,$check)) {
        while($row = mysqli_fetch_assoc($checksql))
        $test[] = $row;


    //--------------------------------------------------------------------------------------------
    
    $check="SELECT T.place_ID, result , current_use, P.sector_ID, P.name
    FROM Places P, (
        SELECT R.place_ID, AVG(R.rating) as result , T2.currentuse as current_use 
        FROM Ratings R, (
            SELECT C.place_id as c_place_id, AVG(C.current_use) as currentuse 
            FROM Current_Use C WHERE C.timestamp BETWEEN NOW() - INTERVAL 3 HOUR AND NOW() GROUP BY C.place_id) AS T2 
        WHERE R.place_ID = T2.c_place_id 
        GROUP BY R.place_ID) as T 
    WHERE P.place_ID = T.place_ID AND P.sector_ID=\"".$sector_ID."\"";
    if($min_rating!="")
        $check.=" AND result >= ".$min_rating;
    if($max_rating!="")
        $check.=" AND result <= ".$max_rating;
    if($min_use!="")
        $check.=" AND current_use >= ".$min_use;
    if($max_use!="")
        $check.=" AND current_use <= ".$max_use;
    if($place_name!="") { $check.=" AND name=\"".$place_name."\""; }
    if($address!="") { $check.=" AND address=\"".$address."\""; }
    if($country!="") { $check.=" AND country=\"".$country."\""; }
    if($zipcode!="") { $check.=" AND zipcode=\"".$zipcode."\""; }

    if($town!="") { $check.=" AND town=\"".$town."\""; }
                $checksql=mysqli_query($db_connection,$check);

     while($row = mysqli_fetch_assoc($checksql))
        $current_use[] = $row;




    //--------------------------------------------------------------------------------------------

        $response["success"] = 1;
        $response["message"] = "Place search successful.";
        $response["place_string"] = $test;
        $response["current_use"] =$current_use;
 

        echo json_encode($response);
    } else {

        $response["success"] = 0;
        $response["message"] = "No entries.";
 

        echo json_encode($response);
    }}
    else {

    $response["success"] = 0;
    $response["message"] = "No sector selected";
 	echo json_encode($response);

}
?>