-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3307
-- Generation Time: Feb 27, 2024 at 11:09 AM
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
-- Database: `lms`
--

-- --------------------------------------------------------

--
-- Table structure for table `customer_details`
--

CREATE TABLE `customer_details` (
  `Customer_Name` varchar(80) NOT NULL,
  `Mobile_No` varchar(12) NOT NULL,
  `Customer_Id` varchar(50) NOT NULL,
  `Book_Id` varchar(50) NOT NULL,
  `Borrowed_Date` varchar(30) NOT NULL,
  `Return_Date` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer_details`
--

INSERT INTO `customer_details` (`Customer_Name`, `Mobile_No`, `Customer_Id`, `Book_Id`, `Borrowed_Date`, `Return_Date`) VALUES
('Smaran N G', '9876543210', 'ABC003', 'AAA001', '2024-02-19', '2024-03-05'),
('Vijay D', '8976532140', 'ABC004', 'AAA002', '2024-02-19', '2024-03-05');

-- --------------------------------------------------------

--
-- Table structure for table `customer_feedback`
--

CREATE TABLE `customer_feedback` (
  `Customer_Name` varchar(80) NOT NULL,
  `Customer_Id` varchar(50) NOT NULL,
  `Feedback` text NOT NULL,
  `Date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer_feedback`
--

INSERT INTO `customer_feedback` (`Customer_Name`, `Customer_Id`, `Feedback`, `Date`) VALUES
('Vishruth M V', 'ABC001', 'Excellent', '2024-02-16 05:23:40'),
('Shreyas S T', 'ABC002', 'Excellent', '2024-02-18 16:48:50');

-- --------------------------------------------------------

--
-- Table structure for table `library`
--

CREATE TABLE `library` (
  `Sl_No` varchar(1000) NOT NULL,
  `Book_Id` varchar(35) NOT NULL,
  `Book_Name` varchar(80) NOT NULL,
  `Book_Author` varchar(60) NOT NULL,
  `Cost` double NOT NULL,
  `Status` int(11) NOT NULL,
  `Section` text NOT NULL,
  `Row_Number` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `library`
--

INSERT INTO `library` (`Sl_No`, `Book_Id`, `Book_Name`, `Book_Author`, `Cost`, `Status`, `Section`, `Row_Number`) VALUES
('12', '45NP6', 'MG', 'OG', 400.58, 0, 'H', 'C99'),
('1', 'AAA001', 'Ramayana: The Legend', 'Valmiki Srivatsav', 600, 1, 'Mythology', 'MY02'),
('2', 'AAA002', 'Mahabharatam', 'Vyasaraj', 899, 1, 'Mythology', 'MY02'),
('3', 'AAA003', 'Advance Engineering Mathematics', 'B V Ramana', 400, 0, 'Mathematics', 'MA05'),
('4', 'AAA004', 'Programming in C', 'Reema Thareja', 399, 0, 'Computer Science', 'CS09'),
('5', 'AAA005', 'The Ghost ', 'Karl Jneffy', 200, 0, 'Comics', 'C10'),
('6', 'AAA006', 'Analog and Digital Electrnics', 'Charles Roth', 350, 0, 'Electronics', 'E12'),
('7', 'AAA007', 'Lord of the Rings', 'JRR Tolkein', 990, 0, 'Fantasy', 'F15'),
('8', 'AAA008', 'Technical Communication Skills', 'Vishruth M V', 99, 0, 'Language Skills', 'LS03'),
('9', 'AAA009', 'Panchatantra', 'Vishnu Sharma', 550, 0, 'Fables', 'FB09'),
('10', 'AAA010', 'Engineering Physics', 'G K Shivakumar', 240, 0, 'Physics', 'P07');

-- --------------------------------------------------------

--
-- Table structure for table `password_details`
--

CREATE TABLE `password_details` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `password_details`
--

INSERT INTO `password_details` (`username`, `password`) VALUES
('admin123', '654321');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customer_details`
--
ALTER TABLE `customer_details`
  ADD UNIQUE KEY `Customer_Id` (`Customer_Id`),
  ADD UNIQUE KEY `Book_Id` (`Book_Id`);

--
-- Indexes for table `customer_feedback`
--
ALTER TABLE `customer_feedback`
  ADD UNIQUE KEY `Customer_Id` (`Customer_Id`);

--
-- Indexes for table `library`
--
ALTER TABLE `library`
  ADD UNIQUE KEY `Book_Id` (`Book_Id`);

--
-- Indexes for table `password_details`
--
ALTER TABLE `password_details`
  ADD PRIMARY KEY (`password`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
