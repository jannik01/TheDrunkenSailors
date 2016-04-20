<?php


$response = array();

if (isset($_POST["user_name"])&&isset($_POST["password"]) ) {
 
    $user_name = $_POST['user_name'];
    $password = $_POST['password'];

     require_once __DIR__ . '/db_connect.php';
 
 	$db_object = new DB_CONNECT();
    $db = $db_object->connection;


	$sane_user_name=mysqli_real_escape_string($db, $user_name);

    // mysql get data from db
    $sql = "Select user_name,password_hash FROM ".DB_DATABASE.".Users WHERE user_name==".$sane_user_name;
 
	$logged_in = false;
	$sql_result = mysqli_query($db,$sql);
	if($sql_result)
	{		
	// does user exist?
		if(mysqli_num_rows($sql_result) >= 1){
			
			$row = mysqli_fetch_row($sql_result);
			
			$username_from_db = $row[0];
			$password_hash_from_db = $row[1];
			
			// compare hash with hash from database
			if(password_verify($password,$password_hash)){
				$logged_in = true;
			}
		}
	
	}
 
 ///////////////////////////////
    // check if row inserted or not
    if ($logged_in) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Username and password valid.";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Username or password wrong.";
 
        // echoing JSON response
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
?>