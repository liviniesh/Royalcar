<?php
error_reporting(0);
include_once("dbconnect.php");
$foodid = $_POST['foodid'];
$userid = $_POST['userid'];
$quantity = $_POST['quantity'];
$price = $_POST['foodprice'];
$foodname = $_POST['foodname'];
$restid = $_POST['restid'];
$status = "not paid";
    
$sqlsel = "SELECT * FROM FOODS WHERE FOODID = '$foodid'";
$result = $conn->query($sqlsel);
if ($result->num_rows > 0) {
    while ($row = $result ->fetch_assoc()){
        $qavail = $row["QUANTITY"];
    }
    $bal = $qavail - $quantity; 
}
if ($bal>0){
    $sqlgetid = "SELECT * FROM CART WHERE USERID = '$userid'";
    $result = $conn->query($sqlgetid);
    $sqlupdate = "UPDATE FOODS SET QUANTITY = '$bal' WHERE FOODID = '$foodid'";
        $conn->query($sqlupdate);
        
if ($result->num_rows > 0) {
    while ($row = $result ->fetch_assoc()){
        $orderid = $row["ORDERID"];
    }
     $sqlinsert = "INSERT INTO CART(FOODID,USERID,QUANTITY,PRICE,FOODNAME,STATUS,RESTID,ORDERID) VALUES ('$foodid','$userid','$quantity','$price','$foodname','$status','$restid','$orderid')";
     
    if ($conn->query($sqlinsert) === TRUE){
       echo "success";
    }
}else{
    $orderid = generateRandomString();
   $sqlinsert = "INSERT INTO CART(FOODID,USERID,QUANTITY,PRICE,FOODNAME,STATUS,RESTID,ORDERID) VALUES ('$foodid','$userid','$quantity','$price','$foodname','$status','$restid','$orderid')";
    if ($conn->query($sqlinsert) === TRUE){
       echo "success";
    }
}
}



function generateRandomString($length = 7) {
    $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    $charactersLength = strlen($characters);
    $randomString = '';
    for ($i = 0; $i < $length; $i++) {
        $randomString .= $characters[rand(0, $charactersLength - 1)];
    }
    return date('dmY')."-".$randomString;
}

?>