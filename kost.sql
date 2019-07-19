-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 19, 2019 at 06:40 AM
-- Server version: 10.1.25-MariaDB
-- PHP Version: 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `kost`
--

-- --------------------------------------------------------

--
-- Table structure for table `kamarkost`
--

CREATE TABLE `kamarkost` (
  `id_kost` varchar(20) NOT NULL,
  `nama` varchar(25) NOT NULL,
  `tarif` int(20) NOT NULL,
  `fasilitas` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kamarkost`
--

INSERT INTO `kamarkost` (`id_kost`, `nama`, `tarif`, `fasilitas`) VALUES
('KS-000001', 'KELAS A MEWAH', 600000, 'AC, KAMAR TIDUR, KAMAR MADI DALAM, LEMARI BAJU'),
('KS-000002', 'KELAS MEWAH NO 2', 600000, 'AC, KAMAR MANDI DALAM'),
('KS-000003', 'KELAS MEDIUM AC', 550000, 'KAMAR TIDUR, AC, KAMAR MANDI DALAM'),
('KS-000004', 'KELAS MEDIUM GOLD', 450000, 'KAMAR MANDI DALAM, KIPAS ANGIN');

-- --------------------------------------------------------

--
-- Table structure for table `penyewa`
--

CREATE TABLE `penyewa` (
  `id_penyewa` varchar(20) NOT NULL,
  `nama` varchar(25) NOT NULL,
  `dukuh` varchar(20) NOT NULL,
  `rt` varchar(4) NOT NULL,
  `rw` varchar(4) NOT NULL,
  `desa` varchar(20) NOT NULL,
  `kecamatan` varchar(20) NOT NULL,
  `kabupaten` varchar(20) NOT NULL,
  `propinsi` varchar(20) NOT NULL,
  `pekerjaan` varchar(25) NOT NULL,
  `no_hp` varchar(13) NOT NULL,
  `catatan` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `penyewa`
--

INSERT INTO `penyewa` (`id_penyewa`, `nama`, `dukuh`, `rt`, `rw`, `desa`, `kecamatan`, `kabupaten`, `propinsi`, `pekerjaan`, `no_hp`, `catatan`) VALUES
('PS-000001', 'ARIFKI DIMAS S', 'PRECET', '22', '0', 'GIRIMARGO', 'MIRI', 'SRAGEN', 'JAWA TENGAH', 'MAHASISWA', '08812842107', 'TINGGAL SENDIRI'),
('PS-000002', 'RATNA S', 'PRECET', '22', '0', 'GIRIMARGO', 'MIRI', 'SRAGEN', 'JAWA TENGAH', 'MAHASISWA', '00998874893', 'SUAMI - ISTRI'),
('PS-000003', 'TATA HIDAYAT', 'SINDANG', '22', '0', 'SINDANG', 'KARANG', 'TASIKMALAYA', 'JAWA BARAT', 'KARYAWAN PABRIK', '0009045347554', 'TINGGAL SENDIRI'),
('PS-000004', 'DEWI TRI WULAN SARI', 'GEMOLONG', '22', '0', 'GEMOLONG', 'FEMOLONG', 'SRAGEN', 'JAAWA TENGAH', 'MAHASISWA', '009898234747', 'BERDUA (SAUDARA)'),
('PS-000005', 'RISMA ARDANI', 'SOBA', '22', '0', 'SOBA', 'SOBA', 'SURAKARTA', 'JAWA TENGAH', 'TNI/ POLRI', '988989856565', 'SUAMI - ISTRI'),
('PS-000006', 'SUGIYANTO', 'GIMARGO', '12', '1', 'MIRI', 'MIRI', 'SRAGEN', 'JATENG', 'KARYAWAN PABRIK', '08888867', 'SUAMI - ISTRI');

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

CREATE TABLE `transaksi` (
  `id_transaksi` varchar(20) NOT NULL,
  `id_penyewa` varchar(8) NOT NULL,
  `nama_penyewa` varchar(25) NOT NULL,
  `id_kost` varchar(8) NOT NULL,
  `nama_kost` varchar(25) NOT NULL,
  `tarif` int(20) NOT NULL,
  `tgl_sewa` varchar(20) NOT NULL,
  `lama_sewa` int(5) NOT NULL,
  `harga_sewa` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transaksi`
--

INSERT INTO `transaksi` (`id_transaksi`, `id_penyewa`, `nama_penyewa`, `id_kost`, `nama_kost`, `tarif`, `tgl_sewa`, `lama_sewa`, `harga_sewa`) VALUES
('TR-000001', 'PS00001', 'ARIFKI DIMAS S', 'A001MW', 'KELAS A MEWAH', 600000, '7 JULI', 1, 600000);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id_user` varchar(8) NOT NULL,
  `nama` varchar(25) NOT NULL,
  `password` varchar(8) NOT NULL,
  `jabatan` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id_user`, `nama`, `password`, `jabatan`) VALUES
('USER01', 'ARIFKI DIMAS S', '22071996', 'ADMIN'),
('USER02', 'RATNA SUGIANINGSIH', '12345', 'OPERATOR');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `kamarkost`
--
ALTER TABLE `kamarkost`
  ADD PRIMARY KEY (`id_kost`);

--
-- Indexes for table `penyewa`
--
ALTER TABLE `penyewa`
  ADD PRIMARY KEY (`id_penyewa`);

--
-- Indexes for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`id_transaksi`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
