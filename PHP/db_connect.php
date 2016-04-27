<?php
require_once __DIR__ . '/db_config.php';


class DB_CONNECT {
	public $connection =  NULL;
	
    function __construct() {
        $this->connection  =  mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE);// or die(mysql_error());
		if ($mysqli->connect_errno) {
			die( "Failed to connect to MySQL: (" . $mysqli->connect_errno . ") " . $mysqli->connect_error);
		}
 
		mysqli_set_charset ( $this->connection, DB_SERVER_CONNECTION_CHARSET);

	}
 
    function __destruct() {
        $this->close();
    }
 


    function close() {
        mysqli_close($this->connection);
    }
 
}
 
?>