-- phpMyAdmin SQL Dump
-- version 4.3.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jan 25, 2018 at 10:16 AM
-- Server version: 5.6.24
-- PHP Version: 5.6.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `dbfutsal`
--

-- --------------------------------------------------------

--
-- Table structure for table `alat`
--

CREATE TABLE IF NOT EXISTS `alat` (
  `kd_alat` varchar(10) NOT NULL,
  `sewa_alat` varchar(10) NOT NULL,
  `hrg_alat` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `data_sewa`
--

CREATE TABLE IF NOT EXISTS `data_sewa` (
  `no_trans` varchar(10) NOT NULL,
  `tgl_trans` date DEFAULT NULL,
  `kd_team` varchar(10) DEFAULT NULL,
  `kd_lap` varchar(10) DEFAULT NULL,
  `dp` double DEFAULT NULL,
  `tgl_masuk` date DEFAULT NULL,
  `jam_masuk` time DEFAULT NULL,
  `jam_keluar` time DEFAULT NULL,
  `kd_user` varchar(10) DEFAULT NULL,
  `keterangan` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `pembayaran`
--

CREATE TABLE IF NOT EXISTS `pembayaran` (
  `no_bayar` varchar(10) NOT NULL,
  `tgl_bayar` date DEFAULT NULL,
  `no_trans` varchar(10) DEFAULT NULL,
  `total_jam` double DEFAULT NULL,
  `total_lap` double DEFAULT NULL,
  `kd_alat` varchar(10) DEFAULT NULL,
  `jumlah_alat` int(11) DEFAULT NULL,
  `total_alat` double DEFAULT NULL,
  `total_bayar` double DEFAULT NULL,
  `sisa_bayar` double DEFAULT NULL,
  `ubay` double DEFAULT NULL,
  `ukem` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `sewa_lapangan`
--

CREATE TABLE IF NOT EXISTS `sewa_lapangan` (
  `kd_lap` varchar(10) NOT NULL,
  `jenis_lap` varchar(15) DEFAULT NULL,
  `hrg_lap` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `team`
--

CREATE TABLE IF NOT EXISTS `team` (
  `kd_team` varchar(10) NOT NULL,
  `tgl_daftar` date DEFAULT NULL,
  `nm_team` varchar(30) DEFAULT NULL,
  `kapten` varchar(30) DEFAULT NULL,
  `no_hp` varchar(15) DEFAULT NULL,
  `status` varchar(15) DEFAULT NULL,
  `keterangan` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `kd_user` varchar(10) NOT NULL,
  `nm_user` varchar(30) DEFAULT NULL,
  `password` varchar(10) DEFAULT NULL,
  `hak_user` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `alat`
--
ALTER TABLE `alat`
  ADD PRIMARY KEY (`kd_alat`);

--
-- Indexes for table `data_sewa`
--
ALTER TABLE `data_sewa`
  ADD PRIMARY KEY (`no_trans`);

--
-- Indexes for table `pembayaran`
--
ALTER TABLE `pembayaran`
  ADD PRIMARY KEY (`no_bayar`);

--
-- Indexes for table `sewa_lapangan`
--
ALTER TABLE `sewa_lapangan`
  ADD PRIMARY KEY (`kd_lap`);

--
-- Indexes for table `team`
--
ALTER TABLE `team`
  ADD PRIMARY KEY (`kd_team`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`kd_user`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
