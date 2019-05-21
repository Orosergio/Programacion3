-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 21-05-2019 a las 04:08:50
-- Versión del servidor: 5.7.21
-- Versión de PHP: 5.6.35

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `excel`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tblalineacion`
--

DROP TABLE IF EXISTS `tblalineacion`;
CREATE TABLE IF NOT EXISTS `tblalineacion` (
  `codalinea` int(2) NOT NULL,
  `nombre` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`codalinea`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tblalineacion`
--

INSERT INTO `tblalineacion` (`codalinea`, `nombre`) VALUES
(1, 'derecha'),
(2, 'centrada');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tblarchivo`
--

DROP TABLE IF EXISTS `tblarchivo`;
CREATE TABLE IF NOT EXISTS `tblarchivo` (
  `codarch` int(3) NOT NULL,
  `nombre` varchar(15) DEFAULT NULL,
  `tamano` int(2) DEFAULT NULL,
  `tipoletra` varchar(10) DEFAULT NULL,
  `colorletra` varchar(10) DEFAULT NULL,
  `colorcelda` varchar(10) DEFAULT NULL,
  `negrita` int(2) DEFAULT NULL,
  `cursiva` int(2) DEFAULT NULL,
  `subrayada` int(2) DEFAULT NULL,
  PRIMARY KEY (`codarch`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tblarchivo`
--

INSERT INTO `tblarchivo` (`codarch`, `nombre`, `tamano`, `tipoletra`, `colorletra`, `colorcelda`, `negrita`, `cursiva`, `subrayada`) VALUES
(1, 'notocar', 1, '1', '1', '1', 0, 0, 0),
(4, 'negri', NULL, NULL, NULL, NULL, 1, 0, 0),
(5, 'prueba', NULL, NULL, NULL, NULL, 1, 0, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tblcontenido`
--

DROP TABLE IF EXISTS `tblcontenido`;
CREATE TABLE IF NOT EXISTS `tblcontenido` (
  `fila` int(3) NOT NULL,
  `colum` int(3) NOT NULL,
  `codarch` int(3) NOT NULL,
  `contenido` varchar(50) DEFAULT NULL,
  `codtipod` int(2) DEFAULT NULL,
  `codalinea` int(2) DEFAULT NULL,
  PRIMARY KEY (`fila`,`colum`,`codarch`),
  KEY `fk1` (`codarch`),
  KEY `fk2` (`codtipod`),
  KEY `fk3` (`codalinea`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tblcontenido`
--

INSERT INTO `tblcontenido` (`fila`, `colum`, `codarch`, `contenido`, `codtipod`, `codalinea`) VALUES
(2, 5, 5, 'ad', NULL, NULL),
(3, 2, 5, 'asd', NULL, NULL),
(3, 6, 4, 'null', NULL, NULL),
(4, 11, 5, 'ad', NULL, NULL),
(5, 0, 5, '5', NULL, NULL),
(7, 7, 4, 'negrita', NULL, NULL),
(11, 14, 4, 'null', NULL, NULL),
(12, 13, 4, 'negrtia', NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbltipodato`
--

DROP TABLE IF EXISTS `tbltipodato`;
CREATE TABLE IF NOT EXISTS `tbltipodato` (
  `codtipod` int(2) NOT NULL,
  `nombre` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`codtipod`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tbltipodato`
--

INSERT INTO `tbltipodato` (`codtipod`, `nombre`) VALUES
(1, 'numero'),
(2, 'texto');

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `tblcontenido`
--
ALTER TABLE `tblcontenido`
  ADD CONSTRAINT `fk1` FOREIGN KEY (`codarch`) REFERENCES `tblarchivo` (`codarch`),
  ADD CONSTRAINT `fk2` FOREIGN KEY (`codtipod`) REFERENCES `tbltipodato` (`codtipod`),
  ADD CONSTRAINT `fk3` FOREIGN KEY (`codalinea`) REFERENCES `tblalineacion` (`codalinea`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
