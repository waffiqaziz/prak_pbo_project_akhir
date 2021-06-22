-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 22, 2021 at 04:02 PM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.4.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `javaconnec`
--

-- --------------------------------------------------------

--
-- Table structure for table `log`
--

CREATE TABLE `log` (
  `id_log` varchar(10) NOT NULL,
  `type` varchar(15) NOT NULL,
  `id_pengirim` int(10) NOT NULL,
  `id_penerima` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `nasabah`
--

CREATE TABLE `nasabah` (
  `user_id` int(11) NOT NULL,
  `acc_number` int(15) NOT NULL,
  `pin` int(4) NOT NULL,
  `full_name` varchar(25) NOT NULL,
  `user` varchar(15) NOT NULL,
  `email` varchar(20) NOT NULL,
  `telp` varchar(15) NOT NULL,
  `dateOfBirth` date DEFAULT NULL,
  `saldo` int(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `nasabah`
--

INSERT INTO `nasabah` (`user_id`, `acc_number`, `pin`, `full_name`, `user`, `email`, `telp`, `dateOfBirth`, `saldo`) VALUES
(22, 2, 123, 'Waffiq', '2222', '2', '2', '2021-06-17', 99975),
(23, 123190070, 1111, 'Waffiq Aziz', 'waffiqa', 'waffiq.a@gmail.com', '081234567', '2021-06-22', 98000),
(24, 1, 1, 'Aziz', 'aziz', 'a@gmail.com', '0822', NULL, 102000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `nasabah`
--
ALTER TABLE `nasabah`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `nasabah`
--
ALTER TABLE `nasabah`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
