<?php

require_once '../includes/DbOperations.php';

$response = array();

if($_SERVER['REQUEST_METHOD']=='POST'){
    if(isset($_POST['email']) and isset($_POST['password'])){
        $db = new DbOperations();
        $user = $db->getTeacher($_POST['email'],$_POST['password']);
        if($user != null){
            $response['message'] = "User found";
            $response['error'] = false;
            $response['id'] = $user['id'];
            $response['email'] = $user['email'];
            $response['password'] = $user['password'];
            $response['name'] = $user['name'];
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