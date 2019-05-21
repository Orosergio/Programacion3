-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 21-05-2019 a las 07:15:40
-- Versión del servidor: 10.1.37-MariaDB
-- Versión de PHP: 7.3.1

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

CREATE TABLE `tblalineacion` (
  `codalinea` int(3) NOT NULL,
  `codarch` int(3) NOT NULL,
  `nombre` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tblalineacion`
--

INSERT INTO `tblalineacion` (`codalinea`, `codarch`, `nombre`) VALUES
(5, 11, '3'),
(5, 12, '2');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tblarchivo`
--

CREATE TABLE `tblarchivo` (
  `codarch` int(3) NOT NULL,
  `nombre` varchar(15) DEFAULT NULL,
  `tamano` int(2) DEFAULT NULL,
  `tipoletra` varchar(10) DEFAULT NULL,
  `colorletra` varchar(10) DEFAULT NULL,
  `colorcelda` varchar(10) DEFAULT NULL,
  `negrita` int(2) DEFAULT NULL,
  `cursiva` int(2) DEFAULT NULL,
  `subrayada` int(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tblarchivo`
--

INSERT INTO `tblarchivo` (`codarch`, `nombre`, `tamano`, `tipoletra`, `colorletra`, `colorcelda`, `negrita`, `cursiva`, `subrayada`) VALUES
(1, 'notocar', 1, '1', '1', '1', 0, 0, 0),
(4, 'negri', NULL, NULL, NULL, NULL, 1, 0, 0),
(5, 'prueba', NULL, NULL, NULL, NULL, 1, 0, 0),
(6, 'alineadopreuba', NULL, NULL, NULL, NULL, 0, 0, 0),
(8, 'prueba 2', NULL, NULL, NULL, NULL, 0, 0, 1),
(9, 'nose', NULL, NULL, NULL, NULL, 0, 0, 0),
(10, 'nose1', NULL, NULL, NULL, NULL, 0, 0, 0),
(11, '1', NULL, NULL, NULL, NULL, 0, 0, 0),
(12, '2', NULL, NULL, NULL, NULL, 0, 0, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tblcontenido`
--

CREATE TABLE `tblcontenido` (
  `fila` int(3) NOT NULL,
  `colum` int(3) NOT NULL,
  `codarch` int(3) NOT NULL,
  `contenido` varchar(50) DEFAULT NULL,
  `codtipod` int(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tblcontenido`
--

INSERT INTO `tblcontenido` (`fila`, `colum`, `codarch`, `contenido`, `codtipod`) VALUES
(0, 5, 12, 'df', NULL),
(1, 11, 6, 'fsdf', NULL),
(2, 3, 8, 'hola', NULL),
(2, 5, 5, 'ad', NULL),
(2, 8, 6, 'fsdf', NULL),
(3, 2, 5, 'asd', NULL),
(3, 5, 9, 'gg', NULL),
(3, 5, 12, 'fs', NULL),
(3, 6, 4, 'null', NULL),
(3, 8, 6, 'sdf', NULL),
(3, 8, 8, 'hola', NULL),
(4, 5, 9, 'gg', NULL),
(4, 8, 6, 'sdf', NULL),
(4, 8, 9, 'gg', NULL),
(4, 11, 5, 'ad', NULL),
(5, 0, 5, '5', NULL),
(7, 7, 4, 'negrita', NULL),
(8, 8, 8, 'no pos gg', NULL),
(8, 8, 9, 'gg', NULL),
(11, 14, 4, 'null', NULL),
(12, 5, 12, 'sdf', NULL),
(12, 10, 10, 'f', NULL),
(12, 11, 8, 'hola', NULL),
(12, 13, 4, 'negrtia', NULL),
(13, 4, 10, 'fgh', NULL),
(13, 13, 9, 'gg', NULL),
(15, 11, 8, 'hola', NULL),
(16, 3, 8, 'hola', NULL),
(16, 4, 10, 'fgd', NULL),
(18, 6, 8, 'hola', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbltipodato`
--

CREATE TABLE `tbltipodato` (
  `codtipod` int(2) NOT NULL,
  `nombre` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tbltipodato`
--

INSERT INTO `tbltipodato` (`codtipod`, `nombre`) VALUES
(1, 'numero'),
(2, 'texto');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `tblalineacion`
--
ALTER TABLE `tblalineacion`
  ADD PRIMARY KEY (`codalinea`,`codarch`),
  ADD KEY `codarch` (`codarch`);

--
-- Indices de la tabla `tblarchivo`
--
ALTER TABLE `tblarchivo`
  ADD PRIMARY KEY (`codarch`);

--
-- Indices de la tabla `tblcontenido`
--
ALTER TABLE `tblcontenido`
  ADD PRIMARY KEY (`fila`,`colum`,`codarch`),
  ADD KEY `fk1` (`codarch`),
  ADD KEY `fk2` (`codtipod`);

--
-- Indices de la tabla `tbltipodato`
--
ALTER TABLE `tbltipodato`
  ADD PRIMARY KEY (`codtipod`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `tblalineacion`
--
ALTER TABLE `tblalineacion`
  ADD CONSTRAINT `tblalineacion_ibfk_1` FOREIGN KEY (`codarch`) REFERENCES `tblarchivo` (`codarch`);

--
-- Filtros para la tabla `tblcontenido`
--
ALTER TABLE `tblcontenido`
  ADD CONSTRAINT `fk1` FOREIGN KEY (`codarch`) REFERENCES `tblarchivo` (`codarch`),
  ADD CONSTRAINT `fk2` FOREIGN KEY (`codtipod`) REFERENCES `tbltipodato` (`codtipod`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
