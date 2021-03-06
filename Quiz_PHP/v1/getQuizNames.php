<?php

require_once '../includes/DbOperations.php';

$response = array();
$temp = array();

if($_SERVER['REQUEST_METHOD']=='POST'){
    if(isset($_POST['year']) and isset($_POST['branch']) and isset($_POST['subject'])){
        $db = new DbOperations();
        $res = $db->getQuizNames($_POST['year'], $_POST['branch'], $_POST['subject']);
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