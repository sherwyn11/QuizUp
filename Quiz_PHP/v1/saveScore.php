<?php

require_once '../includes/DbOperations.php';

$response = array();

if($_SERVER['REQUEST_METHOD']=='POST'){
    if(isset($_POST['roll_no']) and isset($_POST['score']) and isset($_POST['quiz_name'])){
        $db = new DbOperations();
        if($db->save($_POST['roll_no'],$_POST['score'], $_POST['quiz_name'])){
            $response['error'] = false;
        }else{
            $response['error'] = true;
            $response['message'] = "Error in storage";
        }
    }else{
        $response['error'] = true;
        $response['message'] = "Empty fields";   
    }
}

echo json_encode($response);
