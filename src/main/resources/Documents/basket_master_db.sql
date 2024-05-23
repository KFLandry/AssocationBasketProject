-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : jeu. 23 mai 2024 à 21:24
-- Version du serveur : 8.3.0
-- Version de PHP : 8.3.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `basket_master_db`
--

-- --------------------------------------------------------

--
-- Structure de la table `ba_category`
--

DROP TABLE IF EXISTS `ba_category`;
CREATE TABLE IF NOT EXISTS `ba_category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idCoach` int NOT NULL,
  `name` varchar(25) NOT NULL,
  `averageAge` int NOT NULL,
  `gender` varchar(20) NOT NULL,
  `dateCreation` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `story` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  PRIMARY KEY (`id`),
  KEY `ba_category_ibfk_1` (`idCoach`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `ba_category`
--

INSERT INTO `ba_category` (`id`, `idCoach`, `name`, `averageAge`, `gender`, `dateCreation`, `story`) VALUES
(13, 13, 'Senior', 20, 'Man', '2024-05-23 21:34:14', 'Les joueurs de basket en catégorie Senior doivent démontrer une maîtrise approfondie des \ncompétences techniques, une compréhension avancée des aspects tactiques, une condition \nphysique optimale et une force mentale robuste. Les attentes incluent non seulement des\n performances élevées sur le terrain mais aussi un comportement exemplaire et un engagement\n continu envers l amélioration personnelle et collective.');

-- --------------------------------------------------------

--
-- Structure de la table `ba_coach`
--

DROP TABLE IF EXISTS `ba_coach`;
CREATE TABLE IF NOT EXISTS `ba_coach` (
  `id` int NOT NULL AUTO_INCREMENT,
  `lastName` varchar(30) NOT NULL,
  `firstName` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `login` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `ba_coach`
--

INSERT INTO `ba_coach` (`id`, `lastName`, `firstName`, `email`, `login`, `password`) VALUES
(13, 'Dave', 'Popovich', 'wilfrieddev0@gmail.com', 'PDave', 'f2d81a260dea8a100dd517984e53c56a7523d96942a834b9cdc249bd4e8c7aa9');

-- --------------------------------------------------------

--
-- Structure de la table `ba_event`
--

DROP TABLE IF EXISTS `ba_event`;
CREATE TABLE IF NOT EXISTS `ba_event` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `idCoach` int DEFAULT NULL,
  `idTeam` int DEFAULT NULL,
  `type` varchar(10) NOT NULL,
  `subject` varchar(50) NOT NULL,
  `importance` varchar(10) NOT NULL,
  `datePlanned` date NOT NULL,
  `time` time DEFAULT NULL,
  `currentDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `location` varchar(40) NOT NULL,
  `description` text,
  `close` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `idCoach` (`idCoach`),
  KEY `idTeam` (`idTeam`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `ba_event`
--

INSERT INTO `ba_event` (`ID`, `idCoach`, `idTeam`, `type`, `subject`, `importance`, `datePlanned`, `time`, `currentDate`, `location`, `description`, `close`) VALUES
(1, 13, 13, 'Match', 'Vauban Club', 'Hight', '2024-05-24', '09:30:00', '2024-05-23 22:54:30', 'vauban, Nice', 'Rencontre très importante, il va falloir tout donné', 1),
(2, 13, 13, 'Match', 'Cagnes sur mer', 'Hight', '2024-05-24', '09:30:00', '2024-05-23 23:17:27', 'Câgnes sur mer', 'Match très important envu de notre qualification au play-off', 0);

-- --------------------------------------------------------

--
-- Structure de la table `ba_media`
--

DROP TABLE IF EXISTS `ba_media`;
CREATE TABLE IF NOT EXISTS `ba_media` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `dateCreation` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `size` int DEFAULT NULL,
  `typeMime` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `path` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `ba_media`
--

INSERT INTO `ba_media` (`id`, `description`, `dateCreation`, `size`, `typeMime`, `path`) VALUES
(31, 'profile', '2024-05-23 19:30:03', NULL, 'Image', 'src/main/resources/Image/Basket_master.jpg'),
(32, 'Image de profile', '2024-05-22 22:00:00', NULL, 'Image', 'src/main/resources/Image/OIP.jpg'),
(33, 'Image de profile', '2024-05-22 22:00:00', NULL, 'Image', 'src/main/resources/Image/OIP (5).jpg'),
(34, 'Image de profile', '2024-05-22 22:00:00', NULL, 'Image', 'src/main/resources/Image/OIP (8).jpg'),
(35, 'Image de profile', '2024-05-22 22:00:00', NULL, 'Image', 'src/main/resources/Image/OIP.jpg'),
(36, 'Image de profile', '2024-05-22 22:00:00', NULL, 'Image', 'src/main/resources/Image/OIP (9).jpg');

-- --------------------------------------------------------

--
-- Structure de la table `ba_middlemediacategory`
--

DROP TABLE IF EXISTS `ba_middlemediacategory`;
CREATE TABLE IF NOT EXISTS `ba_middlemediacategory` (
  `id` int NOT NULL,
  `idMedia` int NOT NULL,
  `idCategory` int NOT NULL,
  KEY `idCategory` (`idCategory`),
  KEY `idMedia` (`idMedia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `ba_middlemediacoach`
--

DROP TABLE IF EXISTS `ba_middlemediacoach`;
CREATE TABLE IF NOT EXISTS `ba_middlemediacoach` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idMedia` int NOT NULL,
  `idCoach` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idCoach` (`idCoach`),
  KEY `idMedia` (`idMedia`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `ba_middlemediacoach`
--

INSERT INTO `ba_middlemediacoach` (`id`, `idMedia`, `idCoach`) VALUES
(1, 31, 13);

-- --------------------------------------------------------

--
-- Structure de la table `ba_middlemediaplayer`
--

DROP TABLE IF EXISTS `ba_middlemediaplayer`;
CREATE TABLE IF NOT EXISTS `ba_middlemediaplayer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idMedia` int NOT NULL,
  `idPlayer` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idMedia` (`idMedia`),
  KEY `idPlayer` (`idPlayer`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `ba_middlemediaplayer`
--

INSERT INTO `ba_middlemediaplayer` (`id`, `idMedia`, `idPlayer`) VALUES
(1, 32, 31),
(2, 33, 30),
(3, 34, 29),
(4, 35, 28),
(5, 36, 27);

-- --------------------------------------------------------

--
-- Structure de la table `ba_player`
--

DROP TABLE IF EXISTS `ba_player`;
CREATE TABLE IF NOT EXISTS `ba_player` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idTeam` int DEFAULT NULL,
  `gender` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `firstName` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `lastName` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `birthday` date NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `phone` int NOT NULL,
  `phoneEmergency` int NOT NULL,
  `email` varchar(40) NOT NULL,
  `height` int NOT NULL,
  `weight` int NOT NULL,
  `position` varchar(25) NOT NULL,
  `hurt` tinyint(1) NOT NULL DEFAULT '0',
  `available` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idTeam` (`idTeam`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `ba_player`
--

INSERT INTO `ba_player` (`id`, `idTeam`, `gender`, `firstName`, `lastName`, `birthday`, `description`, `phone`, `phoneEmergency`, `email`, `height`, `weight`, `position`, `hurt`, `available`) VALUES
(27, 13, 'Man', 'Tedy', 'Blaise', '2003-04-12', 'Blaise est un point guard moyen fiable et efficace connu pour sa bonne maitrise du dribble et des\npasses une compréhension correcte des schémas\ntactiques une endurance physique adequate et des\ncompetences mentales solides contribuant de maniere\n stable et consistante a l equipe', 612322343, 612322343, 'wilfrieddev0@gmail.com', 96, 194, 'PG - Point Guard', 0, 0),
(28, 13, 'Man', 'mckerly', 'Xavier', '2002-02-18', 'Xavier est un shooting guard moyen\ndote d une bonne capacite de tir a mi-distance et\na trois points capable de se creer des opportunites \nde tir avec une defense individuelle solide et\nune comprehension correcte des schemas \noffensifs et defensifs.', 23892343, 78873242, 'kankeulandry22@gmail.com', 95, 192, 'SG - Shooting Guard', 0, 0),
(29, 13, 'Man', 'Johnson', 'Michieal ', '2005-05-27', 'Marc est un small forward moyen  polyvalent  avec\n une bonne capacite  a marquer des points  a partir\n de diverses positions sur le terrain  un bon sens \ndu rebond et une defense individuelle et \ncollective efficace  contribuant régulierement en\n attaque et en defense.', 782354231, 782354431, 'kankeulandry26@gmail.com', 105, 205, ' SF - Small Forward', 0, 0),
(30, 13, 'Man', 'Williams', 'Brown', '2004-05-19', 'Antoine est un power forward moyen,\n fort physiquement et capable de marquer près\n du panier, bon rebondeur et défenseur, avec\n une\n capacité à tirer à mi-distance, contribuant de\n manière régulière dans la raquette et au\n périmètre.', 97898324, 73243233, 'wilfrieddev0@gmail.com', 115, 206, 'PF - Power Forward', 0, 0),
(31, 13, 'Man', 'Davis', 'Devis', '2007-05-17', 'Louis est un center moyen, dominant dans la\n raquette avec une bonne capacité à bloquer les\n tirs et à attraper les rebonds, solide en défense,\n et\n capable de marquer près du panier, contribuant\n de manière significative en attaque et en \ndéfense.', 87587698, 897898678, 'kankeulandry26@gmail.com', 128, 216, 'C - Center', 0, 0);

-- --------------------------------------------------------

--
-- Structure de la table `ba_statistique`
--

DROP TABLE IF EXISTS `ba_statistique`;
CREATE TABLE IF NOT EXISTS `ba_statistique` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idTeam` int NOT NULL,
  `idPlayer` int DEFAULT NULL,
  `date` datetime NOT NULL,
  `oppenent` varchar(35) NOT NULL,
  `score` varchar(15) DEFAULT NULL,
  `timeGame` int DEFAULT NULL,
  `points` int DEFAULT NULL,
  `rebounds` int DEFAULT NULL,
  `assists` int DEFAULT NULL,
  `steals` int DEFAULT NULL,
  `blocks` int DEFAULT NULL,
  `attempts` int DEFAULT NULL,
  `3pointsShotsAttempts` int DEFAULT NULL,
  `3pointsPlay` int DEFAULT NULL,
  `Victory` varchar(5) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idTeam` (`idTeam`),
  KEY `ba_statistique_ibfk_1` (`idPlayer`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `ba_statistique`
--

INSERT INTO `ba_statistique` (`id`, `idTeam`, `idPlayer`, `date`, `oppenent`, `score`, `timeGame`, `points`, `rebounds`, `assists`, `steals`, `blocks`, `attempts`, `3pointsShotsAttempts`, `3pointsPlay`, `Victory`) VALUES
(1, 13, 27, '2024-05-24 00:00:00', 'Vauban Club', '54:39', 40, 7, 2, 4, 7, 1, 13, 6, 2, 'true'),
(2, 13, 28, '2024-05-24 00:00:00', 'Vauban Club', '54:39', 40, 14, 1, 1, 3, 0, 20, 14, 4, 'true'),
(3, 13, 29, '2024-05-24 00:00:00', 'Vauban Club', '54:39', 40, 13, 8, 2, 1, 7, 11, 2, 1, 'true'),
(4, 13, 30, '2024-05-24 00:00:00', 'Vauban Club', '54:39', 40, 16, 9, 2, 1, 12, 16, 3, 2, 'true'),
(5, 13, 31, '2024-05-24 00:00:00', 'Vauban Club', '54:39', 40, 7, 7, 13, 8, 10, 18, 8, 2, 'true'),
(6, 13, NULL, '2024-05-24 00:00:00', 'Vauban Club', '54:39', 40, 54, 7, 13, 8, 10, 18, 8, 2, 'true');

-- --------------------------------------------------------

--
-- Structure de la table `ba_team`
--

DROP TABLE IF EXISTS `ba_team`;
CREATE TABLE IF NOT EXISTS `ba_team` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idCategory` int NOT NULL,
  `name` varchar(30) NOT NULL,
  `gamePriority` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `gamePlan` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ba_team_ibfk_1` (`idCategory`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `ba_team`
--

INSERT INTO `ba_team` (`id`, `idCategory`, `name`, `gamePriority`, `gamePlan`) VALUES
(13, 13, 'Team A', 'Main', 'Jeu Interieur/Exterieur');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `ba_event`
--
ALTER TABLE `ba_event` ADD FULLTEXT KEY `description` (`type`,`location`,`subject`,`importance`,`description`);

--
-- Index pour la table `ba_player`
--
ALTER TABLE `ba_player` ADD FULLTEXT KEY `firstName` (`firstName`,`lastName`,`email`,`position`);

--
-- Index pour la table `ba_statistique`
--
ALTER TABLE `ba_statistique` ADD FULLTEXT KEY `oppenent` (`oppenent`);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `ba_category`
--
ALTER TABLE `ba_category`
  ADD CONSTRAINT `ba_category_ibfk_1` FOREIGN KEY (`idCoach`) REFERENCES `ba_coach` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `ba_event`
--
ALTER TABLE `ba_event`
  ADD CONSTRAINT `ba_event_ibfk_2` FOREIGN KEY (`idTeam`) REFERENCES `ba_team` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  ADD CONSTRAINT `ba_event_ibfk_3` FOREIGN KEY (`idCoach`) REFERENCES `ba_coach` (`id`) ON DELETE SET NULL ON UPDATE SET NULL;

--
-- Contraintes pour la table `ba_middlemediacategory`
--
ALTER TABLE `ba_middlemediacategory`
  ADD CONSTRAINT `ba_middlemediacategory_ibfk_1` FOREIGN KEY (`idCategory`) REFERENCES `ba_category` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ba_middlemediacategory_ibfk_2` FOREIGN KEY (`idMedia`) REFERENCES `ba_media` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `ba_middlemediacoach`
--
ALTER TABLE `ba_middlemediacoach`
  ADD CONSTRAINT `ba_middlemediacoach_ibfk_1` FOREIGN KEY (`idCoach`) REFERENCES `ba_coach` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ba_middlemediacoach_ibfk_2` FOREIGN KEY (`idMedia`) REFERENCES `ba_media` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `ba_middlemediaplayer`
--
ALTER TABLE `ba_middlemediaplayer`
  ADD CONSTRAINT `ba_middlemediaplayer_ibfk_1` FOREIGN KEY (`idMedia`) REFERENCES `ba_media` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ba_middlemediaplayer_ibfk_2` FOREIGN KEY (`idPlayer`) REFERENCES `ba_player` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `ba_player`
--
ALTER TABLE `ba_player`
  ADD CONSTRAINT `ba_player_ibfk_1` FOREIGN KEY (`idTeam`) REFERENCES `ba_team` (`id`) ON DELETE SET NULL ON UPDATE SET NULL;

--
-- Contraintes pour la table `ba_statistique`
--
ALTER TABLE `ba_statistique`
  ADD CONSTRAINT `ba_statistique_ibfk_1` FOREIGN KEY (`idPlayer`) REFERENCES `ba_player` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  ADD CONSTRAINT `ba_statistique_ibfk_2` FOREIGN KEY (`idTeam`) REFERENCES `ba_team` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
