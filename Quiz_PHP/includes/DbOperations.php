<?php

    class DbOperations{

        private $con;

        function __construct(){
            require_once dirname(__FILE__).'/DbConnect.php';

            $db = new DbConnect();

            $this->con = $db->connect();

        }

        function getStudentDetails($rollno,$pswd){
            $stmt = $this->con->prepare("SELECT * FROM students WHERE `roll_no` = ? AND `password` = ?");
            $stmt->bind_param("ss",$rollno,$pswd);
            $stmt->execute();
            return $stmt->get_result()->fetch_assoc();
        }

        function save($rollno,$score){
            $stmt = $this->con->prepare("INSERT INTO `score` (`id`, `roll_no`, `score`) VALUES (NULL, ?, ?)");
            $stmt->bind_param("ss",$rollno,$score);
            if($stmt->execute()){
                return 1;
            }else{
                return 0;
            }
            
        }

        function getStudentScore($rollno){
            $array = array();
            $stmt = "SELECT * FROM `score` WHERE roll_no = $rollno";
            $result=mysqli_query($this->con,$stmt);
            if(mysqli_num_rows($result) > 0){
                $i=0;
                while($row = mysqli_fetch_assoc($result)){
                    $roll_no = $row['roll_no'];           //0
                    $score = $row['score'];               //1

                    $array[$i] = array($roll_no,$score);
                    $i++;
                }
                $length = count($array);
            }
            return $array;
        }

        function getAllData(){
            $array = array();
            $stmt = "SELECT * FROM `score`";
            $result=mysqli_query($this->con,$stmt);
            if(mysqli_num_rows($result) > 0){
                $i=0;
                while($row = mysqli_fetch_assoc($result)){
                    $roll_no = $row['roll_no'];           //0
                    $score = $row['score'];               //1

                    $array[$i] = array($roll_no,$score);
                    $i++;
                }
                $length = count($array);
            }
            return $array;
        }

        function saveData($question,$optA,$optB,$optC,$optD,$ans){
            $stmt = $this->con->prepare("INSERT INTO `Dynamic`(`id`, `questions`, `A`, `B`, `C`, `D`, `Answer`) VALUES (NULL, ?, ?, ?, ?, ?, ?)");
            $stmt->bind_param("ssssss",$question,$optA,$optB,$optC,$optD,$ans);
            if($stmt->execute()){
                return 1;
            }else{
                return 0;
            }
        }

        function getSubjects($year, $branch){
            $stmt = "SELECT DISTINCT(`subject`) FROM `QuizBranch` WHERE `year` = '$year' AND `branch` = '$branch';";
            $res = $this->con->query($stmt);
            return $res;
        }

        function getQuiz($year, $branch, $subject, $code){
            $array = array();
            $i = 0;
            $table = 'quiz_'.$year.'_'.$branch;
            $stmt = "SELECT `quiz_name`FROM `quiz_3_Comps` WHERE `subject` = '$subject' AND `code` = '$code';";    
            $res = $this->con->query($stmt);
            while($name = $res->fetch_assoc()){
                $quiz_name = $name['quiz_name'];
            }
            $new_stmt = "SELECT * FROM `$quiz_name`";
            $res = $this->con->query($new_stmt);
            while($name = $res->fetch_assoc()){
                $question = $name['question'];
                $optA = $name['A'];
                $optB = $name['B'];
                $optC = $name['C'];
                $optD = $name['D'];
                $answer = $name['answer'];
                $reason = $name['reason'];
                $array[$i] = array($question, $optA, $optB, $optC, $optD, $answer, $reason);
                $i++;
            }
            return $array;
        }
    }