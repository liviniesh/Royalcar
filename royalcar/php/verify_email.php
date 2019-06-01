<?php
$to = "slumberjer@gmail.com";
$subject = "My subject";
$txt = "Hello world!";
$headers = "From: noreply@uumresearch.com" . "\r\n" .
"CC: ahmadhanis@uum.edu.my";

mail($to,$subject,$txt,$headers);
?>