<?php
include 'db_connect.php';

$response = array();

      
	$db_object = new DB_CONNECT();
    $db_connection = $db_object->connection;
 
    $place_id = mysqli_real_escape_string($db_connection, $_POST['place_id']);
    $user_id = mysqli_real_escape_string($db_connection, $_POST['user_id']);
    $rating = mysqli_real_escape_string($db_connection, $_POST['rating']);
    $current_use = mysqli_real_escape_string($db_connection, $_POST['current_use']);


    
    //$check="SELECT rating FROM ".DB_DATABASE.".Ratings WHERE place_id=\"".$place_id" AND user_id=\"0\"";
    
        
    //if (mysqli_query($db_connection,$check)!=null) {
        $check="DELETE FROM ".DB_DATABASE.".Ratings WHERE `Ratings`.`place_ID` = \"".$place_id."\" AND `Ratings`.`user_ID` = 0" ;
        mysqli_query($db_connection,$check);
        $check="DELETE FROM ".DB_DATABASE.".Current_Use WHERE `Current_Use`.`place_id` = \"".$place_id."\" AND `Current_Use`.`user_id` = 0" ;
        mysqli_query($db_connection,$check);
        $check="SELECT rating FROM ".DB_DATABASE.".Ratings WHERE place_ID=\"".$place_id."\" AND user_ID =\"".$user_id."\"";
        $checksql=mysqli_query($db_connection,$check);
        while($row = mysqli_fetch_assoc($checksql))
        $test[] = $row['rating'];
        if(empty($test[0]))
        {
             $check="INSERT INTO ".DB_DATABASE.".Ratings(place_ID,user_ID,rating) VALUES ('$place_id','$user_id','$rating')" ;
            mysqli_query($db_connection,$check);
            $check="INSERT INTO ".DB_DATABASE.".Current_Use(place_id,user_id,current_use) VALUES ('$place_id','$user_id','$current_use')" ;
            mysqli_query($db_connection,$check); 
        }
        else
        {   $check="UPDATE ".DB_DATABASE.".Ratings SET rating =\"".$rating."\" WHERE user_ID=\"".$user_id."\" AND place_ID=\"".$place_id."\"" ;
            mysqli_query($db_connection,$check);
            $check="UPDATE ".DB_DATABASE.".Current_Use SET current_use =\"".$current_use."\" WHERE user_id=\"".$user_id."\" AND place_id=\"".$place_id."\"";
            mysqli_query($db_connection,$check);
           
        }
        


    
        $response["success"] = 1;
        $response["message"] = "Rating successful.";
 

        echo json_encode($response);
    
    
?>