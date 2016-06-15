<?php
include 'db_connect.php';

$response = array();

      
	$db_object = new DB_CONNECT();
    $db_connection = $db_object->connection;
 
    $name = mysqli_real_escape_string($db_connection, $_POST['name']);
            $name=utf8_encode($name);

    $age = mysqli_real_escape_string($db_connection, $_POST['age']);
    $sex = mysqli_real_escape_string($db_connection, $_POST['sex']);
            $sex=utf8_encode($sex);

    $job = mysqli_real_escape_string($db_connection, $_POST['job']);
            $job=utf8_encode($job);

    $lastly_visited = mysqli_real_escape_string($db_connection, $_POST['lastly_visited']);
            $lastly_visited=utf8_encode($lastly_visited);

    

     $db_connection->set_charset("utf8");
     if ($lastly_visited=="") {
         $check="SELECT P.user_id, P.name , P.age, P.sex, P.job 
        FROM ".DB_DATABASE.".Users P WHERE user_id!=0";
        if($name!="") { $check.=" AND name=\"".$name."\""; }
        if($age!="") { $check.=" AND age=\"".$age."\""; }
        if($sex!="") { $check.=" AND sex=\"".$sex."\""; }
        if($job!="") { $check.=" AND job=\"".$job."\""; }
     }
     else
     {
        $check="SELECT U.user_id, U.name, U.sex, U.age, U.job FROM ".DB_DATABASE.".Ratings R, ".DB_DATABASE.".Places P, ".DB_DATABASE.".Users U WHERE P.name = \"".$lastly_visited."\"AND P.place_ID = R.place_ID AND U.user_id = R.user_ID";
        if($name!="") { $check.=" AND U.name=\"".$name."\""; }
        if($age!="") { $check.=" AND U.age=\"".$age."\""; }
        if($sex!="") { $check.=" AND U.sex=\"".$sex."\""; }
        if($job!="") { $check.=" AND U.job=\"".$job."\""; }
     }
    
            $checksql=mysqli_query($db_connection,$check);
        
    if (mysqli_query($db_connection,$check)) {
        while($row = mysqli_fetch_assoc($checksql))
        $test[] = $row;
        
    
        $response["success"] = 1;
        $response["message"] = "Person search successful.";
        $response["person_list"] = $test;
 

        echo json_encode($response);
    } else {

        $response["success"] = 0;
        $response["message"] = "No entries.";
 

        echo json_encode($response);
    }
  
?>