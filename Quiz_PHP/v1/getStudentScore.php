<?php

require_once '../includes/DbOperations.php';

$response = array();
$students = array();

if($_SERVER['REQUEST_METHOD']=='POST'){

    if(isset($_POST['roll_no'])){
        $db =  new DbOperations();
        $students = $db->getStudentScore($_POST['roll_no']);
        $length = count($students);
        $i=0;
        $response[$i++] = $length;
        $response[$i++] = false;
        if($students != null){
            while($i < $length+2){
                $response[$i] = $students[$i - 2];
                $i++;
            }

        }else{
            $i=0;
            $response[$i++] = $length;
            $response[$i++] = true;
        }
    }else{
        $response['error'] = true;
        $response['message'] = "Empty field";
    }
}else{
    $response['error'] = true;
    $response['message'] = "Invalid Request";
}

echo json_encode($response);