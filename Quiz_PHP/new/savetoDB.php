<?php
require_once 'config.php';

if (isset($_POST["submit"])) {

    echo $_POST['topic'];
    
    if (isset($_FILES["file"])) {

        $topic = $_POST['topic'];
        $year = $_POST['year'];
        $branch = $_POST['branch'];
        $subject = $_POST['subject'];

        if ($_FILES["file"]["error"] > 0) {
            echo "Return Code: " . $_FILES["file"]["error"] . "<br />";
        } else {
            if (file_exists("upload/" . $_FILES["file"]["name"])) {
                echo $_FILES["file"]["name"] . " already exists. ";
            } else {
                $storagename = "uploaded_file.txt";
                move_uploaded_file($_FILES["file"]["tmp_name"], "upload/" . $storagename);
                echo "Stored in: " . "upload/" . $_FILES["file"]["name"] . "<br />";
            }
        }
    } else {
        echo "No file selected <br />";
    }
}else{
    echo "err";
}

if (isset($storagename) && $file = fopen("upload/" . $storagename, "r+")) {

    echo "File opened.<br />";

    $firstline = fgets($file, 4096);
    $num = strlen($firstline) - strlen(str_replace(",", "", $firstline));

    $fields = array();
    $fields = explode(",", $firstline, ($num + 1));

    $line = array();
    $i = 0;

    while ($line[$i] = fgets($file, 4096)) {

        $newRow[$i] = array();
        $newRow[$i] = explode(",", $line[$i], ($num + 1));

        $i++;
    }

    echo "<table>";
    echo "<tr>";
    for ($k = 0; $k != ($num + 1); $k++) { 
        echo "<td>" . $fields[$k] . "</td>";
    }
    echo "</tr>";
    foreach ($newRow as $key => $number) {
        
        echo "<tr>";
        $a = array();
        foreach ($number as $k => $content) {
            echo "<td>" . $content . "</td>";
            array_push($a, $content);
        }
        echo('here'.$a[0]);

        $val = $conn->query("select 1 from `$topic`");
        if($val !== FALSE)
        {
            $sql = "INSERT INTO `$topic`(`qno`, `question`, `A`, `B`, `C`, `D`, `answer`, `reason`) VALUES (NULL,'$a[1]','$a[2]','$a[3]','$a[4]','$a[5]','$a[6]','$a[7]');";
            if ($conn->query($sql) === TRUE) {
                echo "Successfully inserted <br>";
            } else {
                echo "Error while entering " . $conn->error . "<br>";
            }
            print("Exists");
        }else{
            $table = 'quiz_' . $year . '_' . $branch;
            $stmt = "INSERT INTO `$table` (`quiz_id`, `quiz_name`, `subject`, `code`, `teacher_id`, `quiz_start`, `quiz_end`) VALUES(NULL, '$topic', '$subject', '000000', '1', '0', '0')";
            // $sql = "INSERT INTO `QSTopics` (`ID`, `Topic`) VALUES(NULL, '$topic');";
            if ($conn->query($stmt) === TRUE) {
                echo "Successfully inserted <br>";
                $newSql = "CREATE TABLE `$topic` (
                    `qno` INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
                    `question` VARCHAR(1000) NOT NULL,
                    `A` VARCHAR(200) NOT NULL,
                    `B` VARCHAR(200) NOT NULL,
                    `C` VARCHAR(200) NOT NULL,
                    `D` VARCHAR(200) NOT NULL,
                    `answer` VARCHAR(500) NOT NULL,
                    `reason` VARCHAR(500) NOT NULL)";
                if ($conn->query($newSql) === TRUE) {
                    $sql = "INSERT INTO `$topic`(`qno`, `question`, `A`, `B`, `C`, `D`, `answer`, `reason`) VALUES (NULL,'$a[1]','$a[2]','$a[3]','$a[4]','$a[5]','$a[6]','$a[7]');";
                    if ($conn->query($sql) === TRUE) {
                        echo "Successfully inserted <br>";
                    } else {
                        echo "Error while entering " . $conn->error . "<br>";
                    }                
                } else {
                    echo "Error while entering " . $conn->error . "<br>";
                }
            } else {
                echo "Error while entering " . $conn->error . "<br>";
            }
            print("Doesn't exist");
        }
    }
    echo "</table>";

    $conn->close();
}
