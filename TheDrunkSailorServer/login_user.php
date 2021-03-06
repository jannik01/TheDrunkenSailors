<?php
include 'db_connect.php';

$response = array();

if (isset($_POST["user_name"])&&isset($_POST["password"]) ) {
 
    $user_name = $_POST['user_name'];
    $password = $_POST['password'];

     

 	$db_object = new DB_CONNECT();
    $db = $db_object->connection;


	$sane_user_name=$db->real_escape_string($user_name);

    // mysql get data from db
	$logged_in = false;
    $sql = "Select user_name,password_hash FROM ".DB_DATABASE.".Users WHERE user_name=\"".$sane_user_name."\"";
	
	$sql_result = mysqli_query($db,$sql);
	
	while($row = mysqli_fetch_assoc($sql_result))
			{
			$credentials[] = $row;
			}

	if($sql_result)	
	{
	// does user exist?

		if(mysqli_num_rows($sql_result) >= 1){
			
			
			$password_from_db = $credentials[0];
			$password_hash_from_db = $password_from_db['password_hash'];
			
			
			// compare hash with hash from database
			if(password_verify($password,$password_hash_from_db)){
				$logged_in = true;
				$sql = "Select user_id FROM ".DB_DATABASE.".Users WHERE user_name=\"".$sane_user_name."\"";
				$sql_result = mysqli_query($db,$sql);
	
				while($row = mysqli_fetch_assoc($sql_result))
				{
					$uid[] = $row['user_id'];	
				}

			}
		}
	
	}
 
 ///////////////////////////////
    // check if row inserted or not
    if ($logged_in) {
        // successfully inserted into database
        $response["success"] = "1";
        $response["message"] = "Username and password valid.";
        $response["uid"] = $uid[0];
        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = "0";
        $response["message"] = "Username or password wrong.";
 
        // echoing JSON response
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = "0";
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
?>