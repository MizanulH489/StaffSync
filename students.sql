-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 10, 2023 at 04:17 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `studentmanager_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `id` varchar(10) NOT NULL,
  `firstname` text NOT NULL,
  `lastname` text NOT NULL,
  `project_title` text NOT NULL,
  `email` varchar(80) NOT NULL,
  `phone_number` varchar(15) NOT NULL,
  `slot` int(7) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`id`, `firstname`, `lastname`, `project_title`, `email`, `phone_number`, `slot`) VALUES
('54678984', 'Donald', 'Terry', 'Python Efficiency', 'terry@gmail.com', '8746274383', 1),
('63737223', 'john', 'Doe', '', 'jos@gmail.com', '1234567898', 3),
('73733737', 'Janet', 'Kennedy', 'Ethical Hacking', 'ken@gmail.com', '9027483647', 1),
('r8765432', 'Nicholas', 'Tesla', 'DSA_Efficiency', 'nicholas@gmail.com', '9999979999', 1),
('t1234562', 'john', 'Doe', 'Oracle Bim', 'hd@gmail.com', '1234567893', 3),
('t8765432', 'John', 'Bell', 'Machine Learning', 'bell@gmail.com', '9999999999', 1),
('w9876543', 'Jane', 'Wish', 'Java Programming', 'wish@gmail.com', '1673625223', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
