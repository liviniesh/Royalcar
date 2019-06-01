<?php
error_reporting(0);
include_once("dbconnect.php");
$foodid = $_POST['foodid'];
$userid = $_POST['userid'];
$quantity = $_POST['quantity'];
$price = $_POST['price'];
$foodname = $_POST['foodname'];
$status = "not complete";

$sqlsel = "SELECT * FROM FOODS WHERE FOODID = '$foodid'";
$result = $conn->query($sqlsel);
if ($result->num_rows > 0) {
    while ($row = $result ->fetch_assoc()){
        $qavail = $row["QUANTITY"];
    }
    $bal = $qavail - $quantity; 
}

$sqlsel = "SELECT * FROM FOODS WHERE FOODID = '$foodid'";
$result = $conn->query($sqlsel);
if ($result->num_rows > 0) {
    while ($row = $result ->fetch_assoc()){
        $qavail = $row["QUANTITY"];
    }
    $bal = $qavail - $quantity; 
    if ($bal>0){
        $sqlupdate = "UPDATE FOODS SET QUANTITY = '$bal' WHERE FOODID = '$foodid'";
        $conn->query($sqlupdate);
        $sqlinsert = "INSERT INTO CART(FOODID,USERID,QUANTITY,PRICE,FOODNAME,STATUS) VALUES ('$foodid','$userid','$quantity','$price','$foodname','$status')";
        if ($conn->query($sqlinsert) === TRUE){
            echo $bal."success";
        }else {
            echo "failed";
        }
    }
}else{
    echo "failed";
}



?>