<?php
error_reporting(0);
include_once("dbconnect.php");
$userid = $_POST['userid'];

$sql = "SELECT * FROM CART WHERE USERID = '$userid' AND STATUS = 'not paid'";
$result = $conn->query($sql);
if ($result->num_rows > 0) {
    $response["cart"] = array();
    while ($row = $result ->fetch_assoc()){
        $cartlist = array();
        $cartlist[foodid] = $row["FOODID"];
        $cartlist[foodname] = $row["FOODNAME"];
        $cartlist[foodprice] = $row["PRICE"];
        $cartlist[quantity] = $row["QUANTITY"];
        $cartlist[status] = $row["STATUS"];
        $cartlist[restid] = $row["RESTID"];
        $cartlist[orderid] = $row["ORDERID"];
        array_push($response["cart"], $cartlist);
    }
    echo json_encode($response);
}else{
    echo "nodata";
}
?>