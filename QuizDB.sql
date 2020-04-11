-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Apr 11, 2020 at 08:48 AM
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
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `username`, `password`) VALUES
(1, 'admin', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `QuizBranch`
--

CREATE TABLE `QuizBranch` (
  `branch_id` int(11) NOT NULL,
  `year` varchar(10) NOT NULL,
  `branch` varchar(20) NOT NULL,
  `quiz_db_id` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `QuizBranch`
--

INSERT INTO `QuizBranch` (`branch_id`, `year`, `branch`, `quiz_db_id`) VALUES
(1, '3', 'Comps', 'quiz_3_Comps');

-- --------------------------------------------------------

--
-- Table structure for table `quiz_3_Comps`
--

CREATE TABLE `quiz_3_Comps` (
  `quiz_id` int(11) NOT NULL,
  `quiz_name` varchar(50) NOT NULL,
  `subject` varchar(100) NOT NULL,
  `code` varchar(10) NOT NULL DEFAULT '000000',
  `teacher_id` int(11) NOT NULL,
  `quiz_start` int(1) NOT NULL DEFAULT 0,
  `quiz_end` int(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `quiz_3_Comps`
--

INSERT INTO `quiz_3_Comps` (`quiz_id`, `quiz_name`, `subject`, `code`, `teacher_id`, `quiz_start`, `quiz_end`) VALUES
(1, 'test', 'DWM', '000000', 1, 1, 0),
(2, 'test2', 'DWM', '000001', 1, 0, 0),
(4, 'test_new', 'DWM', '000002', 1, 0, 0);

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
-- Table structure for table `teachers`
--

CREATE TABLE `teachers` (
  `id` int(11) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `teachers`
--

INSERT INTO `teachers` (`id`, `email`, `password`, `name`) VALUES
(1, 'test@gmail.com', '12345', 'test');

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

-- --------------------------------------------------------

--
-- Table structure for table `test_new`
--

CREATE TABLE `test_new` (
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
-- Dumping data for table `test_new`
--

INSERT INTO `test_new` (`qno`, `question`, `A`, `B`, `C`, `D`, `answer`, `reason`) VALUES
(1, 'Whats the capital of India?', 'Delhi', 'Mumbai', 'Kolkata', 'Chennai', 'Delhi', 'No\r\n'),
(2, 'Whats the capital of India?', 'Delhi', 'Mumbai', 'Kolkata', 'Chennai', 'Delhi', 'No\r\n'),
(3, 'Whats the capital of India?', 'Delhi', 'Mumbai', 'Kolkata', 'Chennai', 'Delhi', 'No\r\n'),
(4, 'Whats the capital of India?', 'Delhi', 'Mumbai', 'Kolkata', 'Chennai', 'Delhi', 'No\r\n'),
(5, 'Whats the capital of India?', 'Delhi', 'Mumbai', 'Kolkata', 'Chennai', 'Delhi', 'No\r\n'),
(6, 'Whats the capital of India?', 'Delhi', 'Mumbai', 'Kolkata', 'Chennai', 'Delhi', 'No\r\n'),
(7, 'Whats the capital of India?', 'Delhi', 'Mumbai', 'Kolkata', 'Chennai', 'Delhi', 'No\r\n'),
(8, 'Whats the capital of India?', 'Delhi', 'Mumbai', 'Kolkata', 'Chennai', 'Delhi', 'No\r\n'),
(9, 'Whats the capital of India?', 'Delhi', 'Mumbai', 'Kolkata', 'Chennai', 'Delhi', 'No\n');

-- --------------------------------------------------------

--
-- Table structure for table `test_score`
--

CREATE TABLE `test_score` (
  `roll_no` int(6) NOT NULL,
  `score` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `test_score`
--

INSERT INTO `test_score` (`roll_no`, `score`) VALUES
(8334, '35'),
(8353, '36');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `QuizBranch`
--
ALTER TABLE `QuizBranch`
  ADD PRIMARY KEY (`branch_id`);

--
-- Indexes for table `quiz_3_Comps`
--
ALTER TABLE `quiz_3_Comps`
  ADD PRIMARY KEY (`quiz_id`),
  ADD KEY `teacher_id` (`teacher_id`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`roll_no`);

--
-- Indexes for table `teachers`
--
ALTER TABLE `teachers`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `test`
--
ALTER TABLE `test`
  ADD PRIMARY KEY (`qno`);

--
-- Indexes for table `test_new`
--
ALTER TABLE `test_new`
  ADD PRIMARY KEY (`qno`);

--
-- Indexes for table `test_score`
--
ALTER TABLE `test_score`
  ADD PRIMARY KEY (`roll_no`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `QuizBranch`
--
ALTER TABLE `QuizBranch`
  MODIFY `branch_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `quiz_3_Comps`
--
ALTER TABLE `quiz_3_Comps`
  MODIFY `quiz_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `teachers`
--
ALTER TABLE `teachers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `test`
--
ALTER TABLE `test`
  MODIFY `qno` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT for table `test_new`
--
ALTER TABLE `test_new`
  MODIFY `qno` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `quiz_3_Comps`
--
ALTER TABLE `quiz_3_Comps`
  ADD CONSTRAINT `quiz_3_comps_ibfk_1` FOREIGN KEY (`teacher_id`) REFERENCES `teachers` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
