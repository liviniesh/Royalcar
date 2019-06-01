<?php
error_reporting(0);
include_once("dbconnect.php");
$location = $_POST['location'];
if (strcasecmp($location, "All") == 0){
    $sql = "SELECT * FROM RESTAURANT"; 
}else{
    $sql = "SELECT * FROM RESTAURANT WHERE LOCATION = '$location'";
}
$result = $conn->query($sql);
if ($result->num_rows > 0) {
    $response["rest"] = array();
    while ($row = $result ->fetch_assoc()){
        $restlist = array();
        $restlist[restid] = $row["RESTID"];
        $restlist[name] = $row["NAME"];
        $restlist[phone] = $row["PHONE"];
        $restlist[address] = $row["ADDRESS"];
        $restlist[location] = $row["LOCATION"];
        array_push($response["rest"], $restlist);
    }
    echo json_encode($response);
}else{
    echo "nodata";
}
?>