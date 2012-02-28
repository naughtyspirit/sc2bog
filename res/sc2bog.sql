-- phpMyAdmin SQL Dump
-- version 3.4.5deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Feb 28, 2012 at 04:25 PM
-- Server version: 5.1.58
-- PHP Version: 5.3.6-13ubuntu3.6

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `sc2bog`
--

-- --------------------------------------------------------

--
-- Table structure for table `ability`
--

CREATE TABLE IF NOT EXISTS `ability` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `race_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `building`
--

CREATE TABLE IF NOT EXISTS `building` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `race_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `minerals` int(11) NOT NULL,
  `gas` int(11) NOT NULL,
  `build_time` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=48 ;

--
-- Dumping data for table `building`
--

INSERT INTO `building` (`id`, `race_id`, `name`, `minerals`, `gas`, `build_time`) VALUES
(1, 1, 'Command Center', 400, 0, 100),
(2, 1, 'Supply Depot', 100, 0, 30),
(3, 1, 'Refinery', 75, 0, 30),
(4, 1, 'Barracks', 150, 0, 65),
(5, 1, 'Engineering Bay', 125, 0, 35),
(6, 1, 'Bunker', 100, 0, 40),
(7, 1, 'Missile Turret', 100, 0, 25),
(8, 1, 'Sensor Tower', 125, 100, 25),
(9, 1, 'Factory', 150, 100, 60),
(10, 1, 'Ghost Academy', 150, 50, 40),
(11, 1, 'Armory', 150, 100, 65),
(12, 1, 'Starport', 150, 100, 50),
(13, 1, 'Fusion Core', 150, 150, 65),
(14, 1, 'Tech Lab', 50, 25, 25),
(15, 1, 'Reactor', 50, 50, 50),
(16, 2, 'Nexus', 400, 0, 100),
(17, 2, 'Pylon', 100, 0, 25),
(18, 2, 'Assimilator', 75, 0, 30),
(19, 2, 'Geteway', 150, 0, 65),
(20, 2, 'Forge', 150, 0, 45),
(21, 2, 'Photon Cannon', 150, 0, 40),
(22, 2, 'Cybernetics Core', 150, 0, 50),
(23, 2, 'Twikight Council', 150, 100, 50),
(24, 2, 'Robotics Facility', 200, 100, 65),
(25, 2, 'Stargate ', 150, 150, 60),
(26, 2, 'Temolar Archives ', 150, 200, 50),
(27, 2, 'Dark Archives', 150, 250, 100),
(28, 2, 'Robotics Bay', 200, 200, 65),
(29, 2, 'Fleet Beacon', 300, 200, 60),
(30, 3, 'Hatchery', 300, 0, 100),
(31, 3, 'Extractor', 25, 0, 30),
(32, 3, 'Spawning Pool', 200, 0, 65),
(33, 3, 'Evolution Chamber', 75, 0, 35),
(34, 3, 'Spine Crawler', 100, 0, 50),
(35, 3, 'Spore Crawler', 75, 0, 30),
(36, 3, 'Roach Warren', 150, 0, 55),
(37, 3, 'Baneling Nest', 100, 50, 60),
(38, 3, 'Lair', 150, 100, 80),
(39, 3, 'Hydralisk Den', 100, 100, 40),
(40, 3, 'Infestation Pit', 100, 100, 50),
(41, 3, 'Spire', 200, 200, 100),
(42, 3, 'Nydus Network', 150, 200, 50),
(43, 3, 'Hive', 200, 150, 100),
(44, 3, 'Ultralisk Cavern', 150, 200, 65),
(45, 3, 'Greater Spire', 100, 150, 100),
(46, 3, 'Creep Tumor', 0, 0, 15),
(47, 2, 'Warpgate', 0, 0, 10);

-- --------------------------------------------------------

--
-- Table structure for table `build_item`
--

CREATE TABLE IF NOT EXISTS `build_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `build_order_id` int(11) NOT NULL,
  `game_object_id` int(11) NOT NULL,
  `game_object_type` varchar(100) NOT NULL,
  `quantity` int(11) NOT NULL,
  `time` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=29 ;

--
-- Dumping data for table `build_item`
--

INSERT INTO `build_item` (`id`, `build_order_id`, `game_object_id`, `game_object_type`, `quantity`, `time`) VALUES
(21, 21, 1, 'UNIT', 1, 1),
(22, 21, 1, 'UNIT', 1, 8),
(23, 21, 2, 'BUILDING', 1, 15),
(27, 23, 1, 'UNIT', 1, 1),
(28, 23, 4, 'BUILDING', 1, 5);

-- --------------------------------------------------------

--
-- Table structure for table `build_order`
--

CREATE TABLE IF NOT EXISTS `build_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `race_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=24 ;

--
-- Dumping data for table `build_order`
--

INSERT INTO `build_order` (`id`, `race_id`, `name`) VALUES
(21, 1, 'MVP''s TvP 3 Banshee'),
(23, 1, 'Order');

-- --------------------------------------------------------

--
-- Table structure for table `race`
--

CREATE TABLE IF NOT EXISTS `race` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `race`
--

INSERT INTO `race` (`id`, `name`) VALUES
(2, 'Protoss'),
(1, 'Terran'),
(3, 'Zerg');

-- --------------------------------------------------------

--
-- Table structure for table `unit`
--

CREATE TABLE IF NOT EXISTS `unit` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `race_id` int(10) unsigned NOT NULL,
  `name` varchar(100) NOT NULL,
  `minerals` int(11) NOT NULL,
  `gas` int(11) NOT NULL,
  `supply` int(11) NOT NULL,
  `build_time` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=44 ;

--
-- Dumping data for table `unit`
--

INSERT INTO `unit` (`id`, `race_id`, `name`, `minerals`, `gas`, `supply`, `build_time`) VALUES
(1, 1, 'SCV', 50, 0, 1, 17),
(2, 1, 'Marine', 50, 0, 1, 25),
(3, 1, 'Marauder', 100, 25, 2, 30),
(4, 1, 'Reaper', 50, 50, 1, 45),
(5, 1, 'Ghost', 200, 100, 2, 40),
(6, 1, 'Helion', 100, 0, 2, 30),
(7, 1, 'Siege Tank', 150, 125, 3, 45),
(8, 1, 'Thor', 300, 200, 6, 60),
(9, 1, 'Viking', 150, 75, 2, 42),
(10, 1, 'Medivac', 100, 100, 2, 42),
(11, 1, 'Raven', 100, 200, 2, 60),
(12, 1, 'Banshee', 150, 100, 3, 60),
(13, 1, 'Battlecruiser', 400, 300, 6, 90),
(14, 2, 'Probe', 50, 0, 1, 17),
(15, 2, 'Zealot', 100, 0, 2, 38),
(16, 2, 'Stalker', 125, 50, 2, 42),
(17, 2, 'Sentry', 50, 100, 2, 37),
(18, 2, 'Observer', 25, 75, 1, 40),
(19, 2, 'Immortal', 250, 100, 4, 55),
(20, 2, 'Warp Prism', 200, 0, 2, 50),
(21, 2, 'Colossus', 300, 200, 6, 75),
(22, 2, 'Phoenix', 150, 100, 2, 35),
(23, 2, 'Void Ray', 250, 150, 3, 60),
(24, 2, 'High Templar', 50, 150, 3, 60),
(25, 2, 'Darck Templar', 125, 125, 2, 55),
(26, 2, 'Archon', 0, 0, 0, 12),
(27, 2, 'Carrier', 350, 250, 6, 120),
(28, 2, 'Mothership', 400, 400, 8, 160),
(29, 3, 'Larva', 0, 0, 0, 0),
(30, 3, 'Drone', 50, 0, 1, 17),
(31, 3, 'Overlord', 100, 0, 0, 25),
(32, 3, 'Zergling', 50, 0, 1, 25),
(33, 3, 'Queen', 150, 0, 2, 50),
(34, 3, 'Hydralisk', 100, 50, 2, 38),
(35, 3, 'Beneling', 25, 25, 0, 20),
(36, 3, 'Overseer', 50, 50, 0, 17),
(37, 3, 'Roach', 75, 25, 2, 27),
(38, 3, 'Infestor', 100, 150, 2, 50),
(39, 3, 'Mutalisk', 100, 100, 2, 33),
(40, 3, 'Corruptor', 150, 100, 2, 40),
(41, 3, 'Nydus Worm', 100, 100, 0, 20),
(42, 3, 'Ultralisk', 300, 200, 6, 55),
(43, 3, 'Brood Lord', 150, 150, 2, 34);

-- --------------------------------------------------------

--
-- Table structure for table `upgrade`
--

CREATE TABLE IF NOT EXISTS `upgrade` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `race_id` int(10) unsigned NOT NULL,
  `name` varchar(100) NOT NULL,
  `minerals` int(11) NOT NULL,
  `gas` int(11) NOT NULL,
  `build_time` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=89 ;

--
-- Dumping data for table `upgrade`
--

INSERT INTO `upgrade` (`id`, `race_id`, `name`, `minerals`, `gas`, `build_time`) VALUES
(1, 2, 'Ground Weapons Level 1', 100, 100, 160),
(2, 1, 'Infantry Weapons Level 1', 100, 100, 160),
(3, 1, 'Infantry Weapons Level 2', 175, 175, 190),
(4, 1, 'Infantry Weapons Level 3', 250, 250, 220),
(5, 1, 'Vehicle Weapons Level 1', 100, 100, 160),
(6, 1, 'Vehicle Weapons Level 2', 175, 175, 190),
(7, 1, 'Vehicle Weapons Level 3', 250, 250, 220),
(8, 1, 'Ship Weapons Level 1', 100, 100, 160),
(9, 1, 'Ship Weapons Level 2', 175, 175, 190),
(10, 1, 'Ship Weapons Level 3', 250, 250, 220),
(11, 1, 'Infantry Armor Level 1', 100, 100, 160),
(12, 1, 'Infantry Armor Level 2', 175, 175, 190),
(13, 1, 'Infantry Armor Level 3', 250, 250, 220),
(14, 1, 'Vehicle Plating Level 1', 100, 100, 160),
(15, 1, 'Vehicle Plating Level 2', 175, 175, 190),
(16, 1, 'Vehicle Plating Level 3', 250, 250, 220),
(17, 1, 'Ship Plating Level 1', 150, 150, 160),
(18, 1, 'Ship Plating Level 3', 300, 300, 220),
(19, 1, 'Nitro Packs', 50, 50, 100),
(20, 1, 'Hi-Sec Auto Tracking', 100, 100, 80),
(21, 1, 'Personal Cloacking', 150, 150, 120),
(22, 1, 'Cloacking Field', 200, 200, 110),
(23, 1, '250mm Strike Cannons', 150, 150, 110),
(24, 1, 'Seeker Missile', 150, 150, 110),
(25, 1, 'Weapon Refit', 150, 150, 60),
(26, 1, 'Siege Tech', 100, 100, 80),
(27, 1, 'Stimpack', 100, 100, 170),
(28, 1, 'Concussive Shells', 50, 50, 60),
(29, 1, 'Moebius Reactor', 100, 100, 80),
(30, 1, 'Cadeceus Reactor', 100, 100, 80),
(31, 1, 'Corvid Reactor', 150, 150, 110),
(32, 1, 'Behemoth Reactor', 150, 150, 80),
(33, 1, 'Neosteel Frame', 100, 100, 110),
(34, 1, 'Building Armor', 150, 150, 140),
(35, 1, 'Durable Materials', 150, 150, 110),
(36, 1, 'Combat Shield', 100, 100, 110),
(37, 1, 'Infernal Pre-Igniter', 150, 150, 110),
(38, 1, 'Arm Silo With Nuke', 100, 100, 60),
(39, 1, 'Ship Plating Level 2', 225, 225, 190),
(40, 2, 'Ground Weapons Level 2', 150, 150, 190),
(41, 2, 'Ground Weapons Level 3', 200, 200, 220),
(42, 2, 'Air Weapons Level 1', 100, 100, 160),
(43, 2, 'Air Weapons Level 2', 175, 175, 190),
(44, 2, 'Air Weapons Level 3', 250, 250, 220),
(45, 2, 'Ground Armor Level 1', 100, 100, 160),
(46, 2, 'Ground Armor Level 2', 150, 150, 190),
(47, 2, 'Ground Armor Level 3', 200, 200, 220),
(48, 2, 'Air Aromr Level 1', 150, 150, 160),
(49, 2, 'Air Aromr Level 2', 225, 225, 190),
(50, 2, 'Air Aromr Level 3', 300, 300, 220),
(51, 2, 'Shields Level 1', 150, 150, 160),
(52, 2, 'Shields Level 2', 225, 225, 190),
(53, 2, 'Shields Level 3', 300, 300, 220),
(54, 2, 'Charge', 200, 200, 140),
(55, 2, 'Gravitic Boosters', 100, 100, 80),
(56, 2, 'Gravitiv Drive', 100, 100, 80),
(57, 2, 'Extended Thermal Lance', 200, 200, 140),
(58, 2, 'Psionic Storm', 200, 200, 110),
(59, 2, 'Hallucination', 100, 100, 80),
(60, 2, 'Blink', 150, 150, 140),
(61, 2, 'Graviton Catapult', 150, 150, 80),
(62, 3, 'Melee Attacks Level 1', 100, 100, 160),
(63, 3, 'Melee Attacks Level 2', 150, 150, 190),
(64, 3, 'Melee Attacks Level 3', 200, 200, 220),
(65, 3, 'Missile Attacks Level 1', 100, 100, 160),
(66, 3, 'Missile Attacks Level 2', 150, 150, 190),
(67, 3, 'Missile Attacks Level 3', 200, 200, 220),
(68, 3, 'Flyer Attacks Level 1', 100, 100, 160),
(69, 3, 'Flyer Attacks Level 2', 175, 175, 190),
(70, 3, 'Flyer Attacks Level 3', 250, 250, 220),
(71, 3, 'Ground Carapace Level 1', 150, 150, 160),
(72, 3, 'Ground Carapace Level 2', 225, 225, 190),
(73, 3, 'Ground Carapace Level 3', 300, 300, 220),
(74, 3, 'Flyer Carapace Level 1', 150, 150, 160),
(75, 3, 'Flyer Carapace Level 2', 225, 225, 190),
(76, 3, 'Flyer Carapace Level 3', 300, 300, 220),
(77, 3, 'Centrifugal Hooks', 150, 150, 110),
(78, 3, 'Glial Reconstitution', 100, 100, 110),
(79, 3, 'Metebolic Boost', 100, 100, 110),
(80, 3, 'Pneumatized Carapace', 100, 100, 60),
(81, 3, 'Grooved Spines', 150, 150, 80),
(82, 3, 'Neural Parasite', 150, 150, 110),
(83, 3, 'Burrow', 100, 100, 100),
(84, 3, 'Pathogen Glands', 150, 150, 80),
(85, 3, 'Adrenal Glands', 200, 200, 130),
(86, 3, 'Ventral Sacs', 200, 200, 130),
(87, 3, 'Tunneling Claws', 150, 150, 110),
(88, 3, 'Chitinous Plating', 150, 150, 110);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
