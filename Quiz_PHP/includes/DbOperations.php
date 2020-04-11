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

        function save($rollno, $score, $quiz_name){
            $score_table = $quiz_name.'_score';
            $stmt = "INSERT INTO `$score_table` (`roll_no`, `score`) VALUES ($rollno, $score)";
            if($this->con->query($stmt)){
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
            $stmt = "SELECT DISTINCT(`subject`) FROM `quiz_3_Comps`";
            $res = $this->con->query($stmt);
            return $res;
        }

        function getQuiz($year, $branch, $subject, $code){
            $array = array();
            $i = 0;
            $table = 'quiz_'.$year.'_'.$branch;
            $stmt = "SELECT `quiz_name`FROM `$table` WHERE `subject` = '$subject' AND `code` = '$code';";    
            $res = $this->con->query($stmt);
            while($name = $res->fetch_assoc()){
                $quiz_name = $name['quiz_name'];
            }
            $new_stmt = "SELECT * FROM `$quiz_name`";
            $res = $this->con->query($new_stmt);
            $array[$i] = $quiz_name;
            $i++;
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

        function getQuizNames($year, $branch, $subject){
            $array = array();
            $i = 0;
            $table = 'quiz_'.$year.'_'.$branch;
            $stmt = "SELECT * FROM `$table` WHERE `subject` = '$subject'";
            $res = $this->con->query($stmt);
            while($name = $res->fetch_assoc()){
                $quiz_name = $name['quiz_name'];
                array_push($array, $quiz_name);
                $i++;
            }
            return $array;
        }

        function checkStatus($quizN){
            $stmt = "SELECT * FROM quiz_3_Comps WHERE quiz_name = '$quizN'";
            $result = mysqli_query($this->con,$stmt);
            $res = mysqli_fetch_assoc($result);
            $start = $res['quiz_start'];
            $end = $res['quiz_end'];
            $teacher_id = $res['teacher_id'];
            $stmt = "SELECT * FROM `teachers` WHERE `id` = '$teacher_id'";
            $result = mysqli_query($this->con,$stmt);
            $res = mysqli_fetch_assoc($result);
            $teacher_name = $res['name'];
            echo "Quiz ". $quizN . " is by teacher $teacher_name";
            echo "<br>";
            if($start==1){
                echo "Quiz ".$quizN." is in process";
            } 
            elseif ($end==1) {
                echo "Quiz ".$quizN." has ended";
            }
            else{
                echo "Quiz ".$quizN." has not begun yet";
            }
        }

        function getScores($roll_no, $quiz_name){
            $table = $quiz_name . '_score';
            $stmt = "SELECT * FROM `$table` WHERE `roll_no` = '$roll_no'";
            $res = $this->con->query($stmt);
            while($name = $res->fetch_assoc()){
                $score = $name['score'];
            }
            return $score;
        }

        function getNumberOfQuizes(){
            $stmt = "SELECT COUNT(*) FROM quiz_3_Comps";
            $result=mysqli_query($this->con,$stmt);
            $res =mysqli_fetch_assoc($result);
            return ($res['COUNT(*)']);
        }

        function getTeacher($email, $pass){
            $stmt = $this->con->prepare("SELECT * FROM `teachers` WHERE `email` = ? AND `password` = ?");
            $stmt->bind_param("ss", $email, $pass);
            $stmt->execute();
            return $stmt->get_result()->fetch_assoc();
        } 

        function getAllScores($quiz_name){
            $array = array();
            $score_table = $quiz_name . '_score';
            $stmt = "SELECT * FROM `$score_table`";
            $res = $this->con->query($stmt);
            while($row = $res->fetch_assoc()){
                $roll_no = $row['roll_no'];
                $score = $row['score'];
                array_push($array, array($roll_no, $score));
            }
            return $array;
        }


    }