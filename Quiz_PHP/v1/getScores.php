<?php

require_once '../includes/DbOperations.php';

$response = array();
$temp = array();

if($_SERVER['REQUEST_METHOD']=='POST'){
    if(isset($_POST['roll_no']) and isset($_POST['quiz_name'])){
        $db = new DbOperations();
        $res = $db->getScores($_POST['roll_no'], $_POST['quiz_name']);
        $response['error'] = false;
        $response['score'] = $res;
    } else {
        $response['error'] = true;
        $response['message'] = "Invalid Request";
    }
}

echo json_encode($response);