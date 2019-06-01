<?php
$servername = "localhost";
$username   = "uumresea_foodninjaadmin";
$password   = "y7tdO1d1gT?+";
$dbname     = "uumresea_foodninja";

$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
?>