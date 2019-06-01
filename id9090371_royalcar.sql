-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: May 28, 2019 at 07:18 PM
-- Server version: 10.3.14-MariaDB
-- PHP Version: 7.3.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id9090371_royalcar`
--

-- --------------------------------------------------------

--
-- Table structure for table `BOOK`
--

CREATE TABLE `BOOK` (
  `CARID` varchar(10) NOT NULL,
  `USERID` varchar(40) NOT NULL,
  `HOURS` varchar(10) NOT NULL,
  `RENTALPERHOUR` varchar(10) NOT NULL,
  `DATE` varchar(40) NOT NULL,
  `TIME` varchar(40) NOT NULL,
  `CARNAME` varchar(30) NOT NULL,
  `STATUS` varchar(20) NOT NULL,
  `COMPANYID` varchar(10) NOT NULL,
  `BOOKID` varchar(20) NOT NULL,
  `TOTAL` varchar(10) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `BOOK`
--

INSERT INTO `BOOK` (`CARID`, `USERID`, `HOURS`, `RENTALPERHOUR`, `DATE`, `TIME`, `CARNAME`, `STATUS`, `COMPANYID`, `BOOKID`, `TOTAL`) VALUES
('1', 'liviniesh@gmail.com', '3', '7.00', '02/12/19', '03:20pm', 'ALZA', 'paid', '1', '28052019-8SnEnXF', '21.0'),
('4', 'liviniesh@gmail.com', '2', '5.00', '31/06/19', '09:30am', 'AXIA', 'paid', '1', '28052019-iVAdkVk', '10.0'),
('1', 'liviniesh@gmail.com', '4', '7.00', '31/06/19', '08:00pm', 'ALZA', 'paid', '1', '28052019-0H9x13T', '28.0'),
('1', 'liviniesh@gmail.com', '3', '7.00', '12/06/19', '02:30pm', 'ALZA', 'not paid', '1', '28052019-Jo0ogfd', '');

-- --------------------------------------------------------

--
-- Table structure for table `CARS`
--

CREATE TABLE `CARS` (
  `CARID` int(5) NOT NULL,
  `CARNAME` varchar(50) NOT NULL,
  `RENTALPERHOUR` varchar(5) NOT NULL,
  `HOURS` varchar(5) NOT NULL,
  `COMPANYID` varchar(5) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `CARS`
--

INSERT INTO `CARS` (`CARID`, `CARNAME`, `RENTALPERHOUR`, `HOURS`, `COMPANYID`) VALUES
(1, 'ALZA', '7.00', '24', '1'),
(2, 'MYVI', '5.00', '24', '3'),
(3, 'CAMRY', '9.00', '24', '2'),
(4, 'AXIA', '5.00', '24', '4'),
(2, 'MYVI', '5.00', '24', '5'),
(4, 'AXIA', '5.00', '24', '1'),
(4, 'AXIA', '5.00', '24', '3'),
(1, 'ALZA', '7.00', '24', '5'),
(1, 'ALZA', '7.00', '24', '2'),
(2, 'MYVI', '5.00', '24', '4'),
(2, 'MYVI', '5.00', '24', '1'),
(4, 'AXIA', '5.00', '24', '2'),
(1, 'ALZA', '7.00', '24', '3'),
(3, 'CAMRY', '9.00', '24', '4'),
(3, 'CAMRY', '9.00', '24', '5'),
(3, 'CAMRY', '9.00', '24', '1'),
(2, 'MYVI', '5.00', '24', '2'),
(3, 'CAMRY', '9.00', '24', '3'),
(1, 'ALZA', '7.00', '24', '4'),
(4, 'AXIA', '5.00', '24', '5');

-- --------------------------------------------------------

--
-- Table structure for table `COMPANY`
--

CREATE TABLE `COMPANY` (
  `COMPANYID` int(10) NOT NULL,
  `NAME` varchar(50) NOT NULL,
  `PHONE` varchar(20) NOT NULL,
  `ADDRESS` varchar(200) NOT NULL,
  `LOCATION` varchar(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `COMPANY`
--

INSERT INTO `COMPANY` (`COMPANYID`, `NAME`, `PHONE`, `ADDRESS`, `LOCATION`) VALUES
(1, 'Enterprise', '01934558488', 'Jalan Changlun', 'Changlun'),
(2, 'Hertz', '0156766456', 'Jalan Sintok', 'Sintok'),
(3, 'Sixth', '01944674445', 'Jalan Bukit Kayu Hitam', 'Bukit Kayu Hitam'),
(4, 'Avis', '01323774445', 'Pekan Lama Changlun', 'Changlun'),
(5, 'Thrifty', '01789209432', 'Taman Permatang Sintok', 'Sintok');

-- --------------------------------------------------------

--
-- Table structure for table `USER`
--

CREATE TABLE `USER` (
  `EMAIL` varchar(100) NOT NULL,
  `PASSWORD` varchar(60) NOT NULL,
  `PHONE` varchar(15) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `LOCATION` varchar(15) NOT NULL,
  `LATITUDE` varchar(30) NOT NULL,
  `LONGITUDE` varchar(30) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `USER`
--

INSERT INTO `USER` (`EMAIL`, `PASSWORD`, `PHONE`, `NAME`, `LOCATION`, `LATITUDE`, `LONGITUDE`) VALUES
('liviniesh@gmail.com', '970728ln', '0103827132', 'liviniesh', 'Sintok', '6.477511882135145', '100.49541112035513'),
('harith@gmail.com', '12345', '0124566789', 'Harith', 'Changlun', '6.4211648224317655', '100.4306860640645'),
('nurul@gmail.com', '123456', '0104612378', 'nurul', 'Changlun', '6.431843899111683', '100.43205332010984'),
('livin@gmail.com', '1455', '1455', 'livin', 'Changlun', '6.431460091923289', '100.4281533882022'),
('jack@yahoo.com', '123456', '0124551234', 'jack', 'Sintok', '6.440258932112268', '100.51024876534937');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `USER`
--
ALTER TABLE `USER`
  ADD PRIMARY KEY (`EMAIL`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
