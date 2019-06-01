<?php
error_reporting(0);
include_once("dbconnect.php");
$email = $_POST['email'];
$password = sha1($_POST['password']);

$sql = "SELECT * FROM USER WHERE EMAIL = '$email' AND PASSWORD = '$password'";

if ($conn->query($sql) === TRUE){
       echo "success";
    }else {
        echo "failed";
    }
}else{
    echo "nodata";
}
?>