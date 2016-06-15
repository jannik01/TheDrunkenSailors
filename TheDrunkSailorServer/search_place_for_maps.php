<?php
include 'db_connect.php';

$response = array();
$response["success"] = 0;
$response["message"] = "Error";

$db_object = new DB_CONNECT();
$db_connection = $db_object->connection;
$db_connection->set_charset("utf8");

if (isset($_POST["zipcode"])) {
    $zipcode = mysqli_real_escape_string($db_connection, $_POST['zipcode']);

    $check="SELECT * FROM Places WHERE zipcode=\"".$zipcode."\"";
} else {
	
    $check="SELECT * FROM Places";
	
}


$checksql=mysqli_query($db_connection,$check);
        
if (mysqli_query($db_connection,$check)) {
	while($row = mysqli_fetch_assoc($checksql))
	$test[] = $row;

	$response["success"] = 1;
	$response["message"] = "Place search successful.";
	$response["place_string"] = $test;

} else {
	$response["success"] = 0;
	$response["message"] = "No entries.";
}

echo json_encode($response);

?>