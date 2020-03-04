-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Mar 04, 2020 at 06:49 AM
-- Server version: 10.4.8-MariaDB
-- PHP Version: 7.3.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `QuizDB`
--

-- --------------------------------------------------------

--
-- Table structure for table `QuizBranch`
--

CREATE TABLE `QuizBranch` (
  `branch_id` int(11) NOT NULL,
  `year` varchar(10) NOT NULL,
  `branch` varchar(20) NOT NULL,
  `subject` varchar(100) NOT NULL,
  `quiz_db_id` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `QuizBranch`
--

INSERT INTO `QuizBranch` (`branch_id`, `year`, `branch`, `subject`, `quiz_db_id`) VALUES
(1, '3', 'Comps', 'DWM', 'quiz_3_Comps');

-- --------------------------------------------------------

--
-- Table structure for table `quiz_3_Comps`
--

CREATE TABLE `quiz_3_Comps` (
  `quiz_id` int(11) NOT NULL,
  `quiz_name` varchar(50) NOT NULL,
  `subject` varchar(100) NOT NULL,
  `code` varchar(10) NOT NULL DEFAULT '000000'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `quiz_3_Comps`
--

INSERT INTO `quiz_3_Comps` (`quiz_id`, `quiz_name`, `subject`, `code`) VALUES
(1, 'test', 'DWM', '000000');

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `roll_no` int(6) NOT NULL,
  `year` varchar(5) NOT NULL,
  `branch` varchar(10) NOT NULL,
  `password` varchar(2000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`roll_no`, `year`, `branch`, `password`) VALUES
(8334, '3', 'Comps', '1628'),
(8335, '3', 'Comps', '1234'),
(8342, '3', 'Comps', '9876');

-- --------------------------------------------------------

--
-- Table structure for table `test`
--

CREATE TABLE `test` (
  `qno` int(11) NOT NULL,
  `question` varchar(1000) NOT NULL,
  `A` varchar(200) NOT NULL,
  `B` varchar(200) NOT NULL,
  `C` varchar(200) NOT NULL,
  `D` varchar(200) NOT NULL,
  `answer` varchar(500) NOT NULL,
  `reason` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `test`
--

INSERT INTO `test` (`qno`, `question`, `A`, `B`, `C`, `D`, `answer`, `reason`) VALUES
(1, 'Whats the capital of India?', 'Delhi', 'Mumbai', 'Kolkata', 'Chennai', 'Delhi', 'No\r\n'),
(2, 'Whats the capital of India?', 'Delhi', 'Mumbai', 'Kolkata', 'Chennai', 'Delhi', 'No\r\n'),
(3, 'Whats the capital of India?', 'Delhi', 'Mumbai', 'Kolkata', 'Chennai', 'Delhi', 'No\r\n'),
(4, 'Whats the capital of India?', 'Delhi', 'Mumbai', 'Kolkata', 'Chennai', 'Delhi', 'No\r\n'),
(5, 'Whats the capital of India?', 'Delhi', 'Mumbai', 'Kolkata', 'Chennai', 'Delhi', 'No\r\n'),
(6, 'Whats the capital of India?', 'Delhi', 'Mumbai', 'Kolkata', 'Chennai', 'Delhi', 'No\r\n'),
(7, 'Whats the capital of India?', 'Delhi', 'Mumbai', 'Kolkata', 'Chennai', 'Delhi', 'No\r\n'),
(8, 'Whats the capital of India?', 'Delhi', 'Mumbai', 'Kolkata', 'Chennai', 'Delhi', 'No\r\n'),
(9, 'Whats the capital of India?', 'Delhi', 'Mumbai', 'Kolkata', 'Chennai', 'Delhi', 'No\r\n'),
(10, 'Whats the capital of India?', 'Delhi', 'Mumbai', 'Kolkata', 'Chennai', 'Delhi', 'No');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `QuizBranch`
--
ALTER TABLE `QuizBranch`
  ADD PRIMARY KEY (`branch_id`);

--
-- Indexes for table `quiz_3_Comps`
--
ALTER TABLE `quiz_3_Comps`
  ADD PRIMARY KEY (`quiz_id`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`roll_no`);

--
-- Indexes for table `test`
--
ALTER TABLE `test`
  ADD PRIMARY KEY (`qno`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `QuizBranch`
--
ALTER TABLE `QuizBranch`
  MODIFY `branch_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `quiz_3_Comps`
--
ALTER TABLE `quiz_3_Comps`
  MODIFY `quiz_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `test`
--
ALTER TABLE `test`
  MODIFY `qno` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
