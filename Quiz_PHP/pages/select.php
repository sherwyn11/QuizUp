<?php
session_start();

if($_SERVER['REQUEST_METHOD'] == 'POST'){
    $_SESSION['year'] = $_POST['year'];
    $_SESSION['branch'] = $_POST['branch'];
    $_SESSION['subject'] = $_POST['subject'];
    print_r($_SESSION);
    header('Location: ./admin.php');
}

?>





<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</head>
<body>
    <form action='select.php' method='POST'>
        <label>Select Year</label>
        <select name="year">
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
        </select>
        <br>
        <br>
        <label>Select Branch</label>
        <select name="branch">
            <option value="Comps">Comps</option>
            <option value="IT">IT</option>
            <option value="Elex">Elex</option>
            <option value="Prod">Prod</option>
        </select>
        <br>
        <br>
        <label>Select Subject</label>
        <select name="subject">
            <option value="DWM">DWM</option>
            <option value="SPCC">SPCC</option>
        </select>
        <br>
        <br>
        <button type="submit">Submit</button>
    </form>
</body>
</html>