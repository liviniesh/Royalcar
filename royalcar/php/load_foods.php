<?php
error_reporting(0);
include_once("dbconnect.php");
$restid = $_POST['restid'];

$sql = "SELECT * FROM FOODS WHERE RESTID = '$restid'";
$result = $conn->query($sql);
if ($result->num_rows > 0) {
    $response["food"] = array();
    while ($row = $result ->fetch_assoc()){
        $foodlist = array();
        $foodlist[foodid] = $row["FOODID"];
        $foodlist[foodname] = $row["FOODNAME"];
        $foodlist[foodprice] = $row["FOODPRICE"];
        $foodlist[quantity] = $row["QUANTITY"];
        array_push($response["food"], $foodlist);
    }
    echo json_encode($response);
}else{
    echo "nodata";
}
?>