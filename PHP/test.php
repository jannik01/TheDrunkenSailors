<?php

//phpinfo();
$algos = hash_algos();
var_dump($algos);
exit();

$username="sql1258689"; 
$password="xh7xegj"; 
$dbname= "1258689db1"; 
$dbserver="mysqlsvr46.world4you.com"; 


$conn=mysql_connect($dbserver, $username, $password, $dbname);
// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}
    // mysql inserting a new row
    $sql = "INSERT INTO 1258689db1.Users".
	"(user_name, password_salt, password_hash, name, sex, age, job)".
	 "VALUES".
	"('test','asdf','asdf','dummy','m','22','party')";
 
   if (mysql_query( $sql,$conn)){
    echo "New record created successfully";
} else {
    echo "Error: " . $sql . "<br>" . mysql_error($conn);
}

mysql_close($conn);
?>