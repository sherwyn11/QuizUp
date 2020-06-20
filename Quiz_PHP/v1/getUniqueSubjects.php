<?php

require_once '../includes/DbOperations.php';

$response = array();
$temp = array();

if($_SERVER['REQUEST_METHOD']=='POST'){
    if(isset($_POST['year']) and isset($_POST['branch'])){
        $db = new DbOperations();
        $subs = $db->getSubjects($_POST['year'],$_POST['branch']);
        while($sub = mysqli_fetch_assoc($subs)){
            array_push($temp, $sub['subject']);
        }
        $i = 0;
        $length = count($temp);
        $response[$i++] = $length;
        $response[$i++] = false;
        if($temp != null){
            while($i < $length+2){
                $response[$i] = $temp[$i - 2];
                $i++;
            }
        }
    }else{
        $response[1] = true;
        $response[0] = "Empty fields";   
    }
}

echo json_encode($response);