-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 22-05-2019 a las 23:57:12
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
  `codalinea` varchar(40) NOT NULL,
  `codarch` varchar(40) NOT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`codalinea`,`codarch`),
  KEY `codarch` (`codarch`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tblarchivo`
--

DROP TABLE IF EXISTS `tblarchivo`;
CREATE TABLE IF NOT EXISTS `tblarchivo` (
  `codarch` varchar(40) NOT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `tamano` varchar(40) DEFAULT NULL,
  `tipoletra` varchar(100) DEFAULT NULL,
  `negrita` varchar(40) DEFAULT NULL,
  `cursiva` varchar(40) DEFAULT NULL,
  `subrayada` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`codarch`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tblarchivo`
--

INSERT INTO `tblarchivo` (`codarch`, `nombre`, `tamano`, `tipoletra`, `negrita`, `cursiva`, `subrayada`) VALUES
('5wPxJYgQ+V0=', 'WX1LybXX2L0=', 'vdXg6UixQi0=', '2PmjDC8+RQo=', 'MH+X9TfXQds=', 'Szf3vo+OXZY=', 'Szf3vo+OXZY='),
('MaeqrB4gvz0=', '67QCsJEkjCU=', 'vdXg6UixQi0=', '2PmjDC8+RQo=', 'Szf3vo+OXZY=', 'Szf3vo+OXZY=', 'Szf3vo+OXZY='),
('MH+X9TfXQds=', 'wBDd57LFpKQ=', 'vdXg6UixQi0=', '2PmjDC8+RQo=', 'Szf3vo+OXZY=', 'Szf3vo+OXZY=', 'Szf3vo+OXZY='),
('YHY/RnbPUkU=', 'ubfQGeViUBE=', 'vdXg6UixQi0=', 'uRtI/Xx2PWo=', 'Szf3vo+OXZY=', 'Szf3vo+OXZY=', 'MH+X9TfXQds='),
('yrMo357gAEA=', '7j9B+4zQEqhLozzooRcuDw==', 'vdXg6UixQi0=', '2PmjDC8+RQo=', 'Szf3vo+OXZY=', 'Szf3vo+OXZY=', 'Szf3vo+OXZY=');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tblcontenido`
--

DROP TABLE IF EXISTS `tblcontenido`;
CREATE TABLE IF NOT EXISTS `tblcontenido` (
  `fila` varchar(40) NOT NULL,
  `colum` varchar(40) NOT NULL,
  `codarch` varchar(40) NOT NULL,
  `contenido` varchar(100) DEFAULT NULL,
  `codtipod` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`fila`,`colum`,`codarch`),
  KEY `fk1` (`codarch`),
  KEY `fk2` (`codtipod`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tblcontenido`
--

INSERT INTO `tblcontenido` (`fila`, `colum`, `codarch`, `contenido`, `codtipod`) VALUES
('44d0tCBCSfg=', '44d0tCBCSfg=', 'yrMo357gAEA=', 'q7GDsp8dzYk=', NULL),
('44d0tCBCSfg=', 'nFgw9WalTWo=', '5wPxJYgQ+V0=', 'KkGS/RoBg1Q=', NULL),
('4VobbdYZ7fw=', 'nFgw9WalTWo=', 'MaeqrB4gvz0=', 'OXVUzjDO8mg=', NULL),
('5wPxJYgQ+V0=', 'k4fOAOtFqIc=', 'MH+X9TfXQds=', '8LMkPwmEPEY=', NULL),
('5wPxJYgQ+V0=', 'tOYoOg9urvc=', 'MH+X9TfXQds=', '8LMkPwmEPEY=', NULL),
('5wPxJYgQ+V0=', 'tOYoOg9urvc=', 'yrMo357gAEA=', 'Upzil2aRHM8=', NULL),
('5wPxJYgQ+V0=', 'Wfe1KQBrUmE=', '5wPxJYgQ+V0=', '/AYU4eQ+zf8=', NULL),
('5wPxJYgQ+V0=', 'zJmiOTyQO68=', 'MH+X9TfXQds=', '8LMkPwmEPEY=', NULL),
('nFgw9WalTWo=', '44d0tCBCSfg=', 'yrMo357gAEA=', 'q7GDsp8dzYk=', NULL),
('Szf3vo+OXZY=', '44d0tCBCSfg=', 'YHY/RnbPUkU=', '+K1AmoXcZZY=', NULL),
('Szf3vo+OXZY=', '5wPxJYgQ+V0=', 'YHY/RnbPUkU=', 'uczZGsd0GWI=', NULL),
('Szf3vo+OXZY=', 'MaeqrB4gvz0=', 'YHY/RnbPUkU=', 'lstbfn0gcmc=', NULL),
('Szf3vo+OXZY=', 'MH+X9TfXQds=', 'YHY/RnbPUkU=', 'klj7So9ykVk=', NULL),
('Szf3vo+OXZY=', 'YHY/RnbPUkU=', 'YHY/RnbPUkU=', 'r/Fwcrt4iX4=', NULL),
('Szf3vo+OXZY=', 'yrMo357gAEA=', 'YHY/RnbPUkU=', 'Ei6cevE4ISY=', NULL),
('tOYoOg9urvc=', 'nFgw9WalTWo=', 'MaeqrB4gvz0=', 'DOdIBUq5R5A=', NULL),
('UeRWukgVzsk=', 'nFgw9WalTWo=', 'MaeqrB4gvz0=', 'OXVUzjDO8mg=', NULL),
('vdXg6UixQi0=', 'nFgw9WalTWo=', 'MaeqrB4gvz0=', 'OXVUzjDO8mg=', NULL),
('Wfe1KQBrUmE=', '44d0tCBCSfg=', 'yrMo357gAEA=', 'q7GDsp8dzYk=', NULL),
('yrMo357gAEA=', '44d0tCBCSfg=', 'yrMo357gAEA=', 'q7GDsp8dzYk=', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbltipodato`
--

DROP TABLE IF EXISTS `tbltipodato`;
CREATE TABLE IF NOT EXISTS `tbltipodato` (
  `codtipod` varchar(40) NOT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`codtipod`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
  ADD CONSTRAINT `tblcontenido_ibfk_1` FOREIGN KEY (`codarch`) REFERENCES `tblarchivo` (`codarch`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
