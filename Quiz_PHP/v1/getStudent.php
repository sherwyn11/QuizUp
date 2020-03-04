<?php

require_once '../includes/DbOperations.php';

$response = array();

if($_SERVER['REQUEST_METHOD']=='POST'){
    if(isset($_POST['roll_no']) and isset($_POST['password'])){
        $db = new DbOperations();
        $user = $db->getStudentDetails($_POST['roll_no'],$_POST['password']);
        if($user != null){
            $response['error'] = false;
            $response['roll_no'] = $user['roll_no'];
            $response['password'] = $user['password'];
            $response['branch'] = $user['branch'];
            $response['year'] = $user['year'];
        }else{
            $response['error'] = true;
            $response['message'] = "Cannot find user";
        }
    }else{
        $response['error'] = true;
        $response['message'] = "Empty fields";   
    }
}

echo json_encode($response);