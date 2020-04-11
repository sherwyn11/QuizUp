<?php

require_once '../includes/DbOperations.php';

$response = array();
$temp = array();

if($_SERVER['REQUEST_METHOD'] == 'POST'){
    if(isset($_POST['quiz_name'])){
        $db = new DbOperations();
        $res = $db->getAllScores($_POST['quiz_name']);
        $length = count($res);
        $i = 0;
        $response[$i++] = $length;
        $response[$i++] = false;
        if ($res != null) {
            while ($i < $length + 2) {
                $response[$i] = $res[$i - 2];
                $i++;
            }
        } else {
            $i = 0;
            $response[$i++] = $length;
            $response[$i++] = true;
            $response[$i++] = "No Questions!";
        }
    } else {
        $response['error'] = true;
        $response['message'] = "Invalid Request";
    }
}

echo json_encode($response);