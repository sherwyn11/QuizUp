<?php

require_once '../includes/DbOperations.php';

$response = array();
$i = 0;

if($_SERVER['REQUEST_METHOD']=='POST'){
    if(isset($_POST['question']) and isset($_POST['optionA']) and isset($_POST['optionB']) and isset($_POST['optionC']) and isset($_POST['optionD']) and isset($_POST['answer'])){
        $rec_array0 = $_POST['question'];
        $questions = json_decode($rec_array0,true);
        $len = count($questions);
        $rec_array1 = $_POST['optionA'];
        $optA = json_decode($rec_array1,true);
        $rec_array2 = $_POST['optionB'];
        $optB = json_decode($rec_array2,true);
        $rec_array3 = $_POST['optionC'];
        $optC = json_decode($rec_array3,true);
        $rec_array4 = $_POST['optionD'];
        $optD = json_decode($rec_array4,true);
        $rec_array5 = $_POST['answer'];
        $answer = json_decode($rec_array5,true);
        $db = new DbOperations();
        for($i = 0; $i < $len; $i++){
            if($db->saveData($questions[$i],$optA[$i],$optB[$i],$optC[$i],$optD[$i],$answer[$i])){
                $response['error'] = false;
            }else{
                $response['error'] = true;
                $response['message'] = "Error in storage";
            }
        }    
    }else{
        $response['error'] = true;
        $response['message'] = "Empty fields";   
    }
}

echo json_encode($response);
