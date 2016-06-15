<?php
include 'db_connect.php';
$response = array();
$db_object = new DB_CONNECT();
    $db_connection = $db_object->connection;
$db_connection->set_charset("utf8mb4");

if (isset($_POST["place_name"],$_POST["description"], $_POST["sector_ID"],$_POST["address"],$_POST["country"],$_POST["zipcode"])) {
      
	

    $place_name = mysqli_real_escape_string($db_connection, $_POST['place_name']);
        $place_name=utf8_encode($place_name);
    $description = $_POST['description'];
        $description=utf8_encode($description);
    $sector_ID = mysqli_real_escape_string($db_connection, $_POST['sector_ID']);
    $address = mysqli_real_escape_string($db_connection, $_POST['address']);
        $address=utf8_encode($address);
    $country = mysqli_real_escape_string($db_connection, $_POST['country']);
            $country=utf8_encode($country);
    $zipcode = mysqli_real_escape_string($db_connection, $_POST['zipcode']);
    $town = mysqli_real_escape_string($db_connection, $_POST['town']);        
            $town=utf8_encode($town);


    $check ="SELECT name,address FROM ".DB_DATABASE.".Places WHERE name=\"".$place_name."\"AND address=\"".$address."\"";
    $checksql=mysqli_query($db_connection,$check);
      

    while($row = mysqli_fetch_assoc($checksql))
    	$test[] = $row;
    if($test)
    {
	$response["success"] = 0;
        $response["message"] = "Placename already taken.";
 

        echo json_encode($response);
    }
	else
    {
	
    $sql = "INSERT INTO ".DB_DATABASE.".Places(name, description, sector_ID, address, country, zipcode, town) VALUES('$place_name', '$description','$sector_ID','$address','$country','$zipcode','$town')";

    if (mysqli_query($db_connection,$sql)) {
        $sql = "SELECT place_ID FROM ".DB_DATABASE.".Places WHERE name=\"".$place_name."\"AND address=\"".$address."\"";
        $checksql=mysqli_query($db_connection,$sql);

        while($row = mysqli_fetch_assoc($checksql))
        $test[] = $row['place_ID'];

        $sqlc = "INSERT INTO ".DB_DATABASE.".Current_Use(place_id,user_id,current_use) VALUES('$test[0]','0','0')";
        $sqlr = "INSERT INTO ".DB_DATABASE.".Ratings(place_ID,user_ID,rating) VALUES('$test[0]','0','0')";
        mysqli_query($db_connection,$sqlc);
        mysqli_query($db_connection,$sqlr);
        $response["success"] = 1;
        $response["message"] = "Place successfully created.";
 

        echo json_encode($response);
    } else {

        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
 

        echo json_encode($response);
    }
}} else {

    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 

    echo json_encode($response);
}
?>