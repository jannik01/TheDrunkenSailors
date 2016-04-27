<?php
include 'db_connect.php';

$response = array();

if (isset($_POST["place_name"],$_POST["description"], $_POST["sector_ID"],$_POST["address"],$_POST["country"],$_POST["zipcode"])) {
      
	$db_object = new DB_CONNECT();
    $db_connection = $db_object->connection;
 
    $place_name = mysqli_real_escape_string($db_connection, $_POST['place_name']);
    $description = $_POST['description'];
    //$password_hash = mysqli_real_escape_string($db_connection, $_POST['password_hash']);
    $sector_ID = mysqli_real_escape_string($db_connection, $_POST['sector_ID']);
    $address = mysqli_real_escape_string($db_connection, $_POST['address']);
    $country = mysqli_real_escape_string($db_connection, $_POST['country']);
    $zipcode = mysqli_real_escape_string($db_connection, $_POST['zipcode']);

 

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
	
	//$password_hash = password_hash($password , PASSWORD_BCRYPT );
	$password_hash=$password;
	
    $sql = "INSERT INTO ".DB_DATABASE.".Places(name, description, sector_ID, address, country, zipcode) VALUES('$place_name', '$description','$sector_ID','$address','$country','$zipcode')";

    if (mysqli_query($db_connection,$sql)) {

        $response["success"] = 1;
        $response["message"] = "Place successfully created.";
 

        echo json_encode($response);
    } else {

        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
 

        echo json_encode($response);
    }
} else {

    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 

    echo json_encode($response);
}
?>