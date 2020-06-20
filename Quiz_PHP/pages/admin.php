<!DOCTYPE html>
<head>
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</head>
<body>
    <h1>Quizzes in DB</h1>
    <?php 
            session_start();
            require_once '../includes/DbOperations.php';
            $db = new DbOperations();
            $number = $db->getNumberOfQuizes();
            $QuizNames = $db->getQuizNames($_SESSION['year'], $_SESSION['branch'], $_SESSION['subject']);
            echo "<form action=admin.php method=POST>";
            if($number == 0){
                echo "No quizes created";
            }
            else{
                for($i=0;$i<$number;$i++){
                    echo "<input type=submit name=".$QuizNames[$i]." value=".$QuizNames[$i].">";
                    echo "<br>";
                }
            }
            echo "</form>";
            for($i=0;$i<$number;$i++){
                if(isset($_POST[$QuizNames[$i]])){
                    $db = new DbOperations();
                    $result = $db->checkStatus($QuizNames[$i]);
            }
        }
    ?>  
</body>
</html>
