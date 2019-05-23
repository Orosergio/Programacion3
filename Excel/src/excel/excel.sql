-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 23-05-2019 a las 22:00:59
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

--
-- Volcado de datos para la tabla `tblalineacion`
--

INSERT INTO `tblalineacion` (`codalinea`, `codarch`, `nombre`) VALUES
('44d0tCBCSfg=', 'zJmiOTyQO68=', 'YHY/RnbPUkU='),
('tOYoOg9urvc=', 'zJmiOTyQO68=', 'MaeqrB4gvz0=');

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
('44d0tCBCSfg=', 'cdDf+n+JBwg=', 'p/NOl/pBDb8=', '0RtvS7Wa4no=', 'MH+X9TfXQds=', 'Szf3vo+OXZY=', 'Szf3vo+OXZY='),
('5wPxJYgQ+V0=', 'WX1LybXX2L0=', 'vdXg6UixQi0=', '2PmjDC8+RQo=', 'MH+X9TfXQds=', 'Szf3vo+OXZY=', 'Szf3vo+OXZY='),
('k4fOAOtFqIc=', '43JixKk5Zxc=', 'vdXg6UixQi0=', '2PmjDC8+RQo=', 'Szf3vo+OXZY=', 'Szf3vo+OXZY=', 'Szf3vo+OXZY='),
('MaeqrB4gvz0=', '67QCsJEkjCU=', 'vdXg6UixQi0=', '2PmjDC8+RQo=', 'Szf3vo+OXZY=', 'Szf3vo+OXZY=', 'Szf3vo+OXZY='),
('MH+X9TfXQds=', 'wBDd57LFpKQ=', 'vdXg6UixQi0=', '2PmjDC8+RQo=', 'Szf3vo+OXZY=', 'Szf3vo+OXZY=', 'Szf3vo+OXZY='),
('nFgw9WalTWo=', 'wBDd57LFpKQ=', 'vdXg6UixQi0=', '2PmjDC8+RQo=', 'Szf3vo+OXZY=', 'Szf3vo+OXZY=', 'Szf3vo+OXZY='),
('tOYoOg9urvc=', 'HwQxvGjXBI4=', 'vdXg6UixQi0=', '2PmjDC8+RQo=', 'Szf3vo+OXZY=', 'Szf3vo+OXZY=', 'Szf3vo+OXZY='),
('vdXg6UixQi0=', 'p0+mgk4Pztk=', 'vdXg6UixQi0=', '2PmjDC8+RQo=', 'Szf3vo+OXZY=', 'Szf3vo+OXZY=', 'MH+X9TfXQds='),
('Wfe1KQBrUmE=', 'xdhzsUKZhzc=', 'p/NOl/pBDb8=', '0RtvS7Wa4no=', 'Szf3vo+OXZY=', 'Szf3vo+OXZY=', 'Szf3vo+OXZY='),
('YHY/RnbPUkU=', 'ubfQGeViUBE=', 'vdXg6UixQi0=', 'uRtI/Xx2PWo=', 'Szf3vo+OXZY=', 'Szf3vo+OXZY=', 'MH+X9TfXQds='),
('yrMo357gAEA=', '7j9B+4zQEqhLozzooRcuDw==', 'vdXg6UixQi0=', '2PmjDC8+RQo=', 'Szf3vo+OXZY=', 'Szf3vo+OXZY=', 'Szf3vo+OXZY='),
('zJmiOTyQO68=', 'xfDSD693qSE=', 'vdXg6UixQi0=', '2PmjDC8+RQo=', 'Szf3vo+OXZY=', 'Szf3vo+OXZY=', 'Szf3vo+OXZY=');

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
  PRIMARY KEY (`fila`,`colum`,`codarch`),
  KEY `fk1` (`codarch`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tblcontenido`
--

INSERT INTO `tblcontenido` (`fila`, `colum`, `codarch`, `contenido`) VALUES
('44d0tCBCSfg=', '44d0tCBCSfg=', 'yrMo357gAEA=', 'q7GDsp8dzYk='),
('44d0tCBCSfg=', '5wPxJYgQ+V0=', 'tOYoOg9urvc=', 'RAC+GMoY9Gg='),
('44d0tCBCSfg=', 'nFgw9WalTWo=', '5wPxJYgQ+V0=', 'KkGS/RoBg1Q='),
('44d0tCBCSfg=', 'nFgw9WalTWo=', 'vdXg6UixQi0=', 'kLRhU/usSuM='),
('44d0tCBCSfg=', 'nFgw9WalTWo=', 'yrMo357gAEA=', 'KkGS/RoBg1Q='),
('4VobbdYZ7fw=', 'nFgw9WalTWo=', 'MaeqrB4gvz0=', 'OXVUzjDO8mg='),
('4VobbdYZ7fw=', 'nFgw9WalTWo=', 'yrMo357gAEA=', 'OXVUzjDO8mg='),
('5wPxJYgQ+V0=', '44d0tCBCSfg=', 'tOYoOg9urvc=', 'gfom/E0Px7o='),
('5wPxJYgQ+V0=', '44d0tCBCSfg=', 'Wfe1KQBrUmE=', 'by52IdKDFek='),
('5wPxJYgQ+V0=', '5wPxJYgQ+V0=', 'tOYoOg9urvc=', 'gfom/E0Px7o='),
('5wPxJYgQ+V0=', 'k4fOAOtFqIc=', '44d0tCBCSfg=', 'q7GDsp8dzYk='),
('5wPxJYgQ+V0=', 'k4fOAOtFqIc=', 'MH+X9TfXQds=', '8LMkPwmEPEY='),
('5wPxJYgQ+V0=', 'nFgw9WalTWo=', 'tOYoOg9urvc=', 'RAC+GMoY9Gg='),
('5wPxJYgQ+V0=', 'nFgw9WalTWo=', 'vdXg6UixQi0=', 'kLRhU/usSuM='),
('5wPxJYgQ+V0=', 'tOYoOg9urvc=', 'MH+X9TfXQds=', '8LMkPwmEPEY='),
('5wPxJYgQ+V0=', 'tOYoOg9urvc=', 'yrMo357gAEA=', 'Upzil2aRHM8='),
('5wPxJYgQ+V0=', 'tOYoOg9urvc=', 'zJmiOTyQO68=', 'H6TjPYdgAoJLozzooRcuDw=='),
('5wPxJYgQ+V0=', 'Wfe1KQBrUmE=', '5wPxJYgQ+V0=', '/AYU4eQ+zf8='),
('5wPxJYgQ+V0=', 'Wfe1KQBrUmE=', 'yrMo357gAEA=', '/AYU4eQ+zf8='),
('5wPxJYgQ+V0=', 'zJmiOTyQO68=', 'MH+X9TfXQds=', '8LMkPwmEPEY='),
('k4fOAOtFqIc=', 'nFgw9WalTWo=', 'zJmiOTyQO68=', 'D9NNOqy740c='),
('k4fOAOtFqIc=', 'vdXg6UixQi0=', '44d0tCBCSfg=', 'kLRhU/usSuM='),
('MaeqrB4gvz0=', 'k4fOAOtFqIc=', 'k4fOAOtFqIc=', '67QCsJEkjCU='),
('MaeqrB4gvz0=', 'nFgw9WalTWo=', 'vdXg6UixQi0=', 'kLRhU/usSuM='),
('MH+X9TfXQds=', '44d0tCBCSfg=', 'Wfe1KQBrUmE=', 'by52IdKDFek='),
('MH+X9TfXQds=', 'MaeqrB4gvz0=', 'Wfe1KQBrUmE=', 'qH/L+Lc8IpA='),
('nFgw9WalTWo=', '44d0tCBCSfg=', '44d0tCBCSfg=', 'kLRhU/usSuM='),
('nFgw9WalTWo=', '44d0tCBCSfg=', 'yrMo357gAEA=', 'q7GDsp8dzYk='),
('nFgw9WalTWo=', 'MaeqrB4gvz0=', 'zJmiOTyQO68=', 'xfDSD693qSE='),
('nFgw9WalTWo=', 'nFgw9WalTWo=', 'vdXg6UixQi0=', 'rChzVd5C+mM='),
('Szf3vo+OXZY=', '44d0tCBCSfg=', 'YHY/RnbPUkU=', '+K1AmoXcZZY='),
('Szf3vo+OXZY=', '5wPxJYgQ+V0=', 'YHY/RnbPUkU=', 'uczZGsd0GWI='),
('Szf3vo+OXZY=', 'MaeqrB4gvz0=', 'YHY/RnbPUkU=', 'lstbfn0gcmc='),
('Szf3vo+OXZY=', 'MH+X9TfXQds=', 'YHY/RnbPUkU=', 'klj7So9ykVk='),
('Szf3vo+OXZY=', 'YHY/RnbPUkU=', 'YHY/RnbPUkU=', 'r/Fwcrt4iX4='),
('Szf3vo+OXZY=', 'yrMo357gAEA=', 'YHY/RnbPUkU=', 'Ei6cevE4ISY='),
('tOYoOg9urvc=', 'k4fOAOtFqIc=', '44d0tCBCSfg=', 'kaiWG+cUAF4='),
('tOYoOg9urvc=', 'nFgw9WalTWo=', 'k4fOAOtFqIc=', 'ubfQGeViUBE='),
('tOYoOg9urvc=', 'nFgw9WalTWo=', 'MaeqrB4gvz0=', 'DOdIBUq5R5A='),
('tOYoOg9urvc=', 'nFgw9WalTWo=', 'yrMo357gAEA=', 'DOdIBUq5R5A='),
('tOYoOg9urvc=', 'tOYoOg9urvc=', 'vdXg6UixQi0=', 'q7GDsp8dzYk='),
('tOYoOg9urvc=', 'Wfe1KQBrUmE=', 'k4fOAOtFqIc=', 'nFUiX9hrDZU='),
('tOYoOg9urvc=', 'yrMo357gAEA=', 'vdXg6UixQi0=', 'q7GDsp8dzYk='),
('UeRWukgVzsk=', '44d0tCBCSfg=', 'zJmiOTyQO68=', '6ctJwL2l0Xs='),
('UeRWukgVzsk=', 'nFgw9WalTWo=', 'MaeqrB4gvz0=', 'OXVUzjDO8mg='),
('UeRWukgVzsk=', 'nFgw9WalTWo=', 'vdXg6UixQi0=', '8LMkPwmEPEY='),
('UeRWukgVzsk=', 'nFgw9WalTWo=', 'yrMo357gAEA=', 'OXVUzjDO8mg='),
('vdXg6UixQi0=', 'nFgw9WalTWo=', 'MaeqrB4gvz0=', 'OXVUzjDO8mg='),
('vdXg6UixQi0=', 'nFgw9WalTWo=', 'yrMo357gAEA=', 'OXVUzjDO8mg='),
('Wfe1KQBrUmE=', '44d0tCBCSfg=', 'yrMo357gAEA=', 'q7GDsp8dzYk='),
('Wfe1KQBrUmE=', '5wPxJYgQ+V0=', 'zJmiOTyQO68=', 'ubfQGeViUBE='),
('Wfe1KQBrUmE=', 'nFgw9WalTWo=', 'vdXg6UixQi0=', '8LMkPwmEPEY='),
('YHY/RnbPUkU=', '44d0tCBCSfg=', 'tOYoOg9urvc=', 'RAC+GMoY9Gg='),
('YHY/RnbPUkU=', '5wPxJYgQ+V0=', 'tOYoOg9urvc=', 'Y7bg+T+QsHk='),
('YHY/RnbPUkU=', '5wPxJYgQ+V0=', 'Wfe1KQBrUmE=', 'RnnaS2vufPE='),
('YHY/RnbPUkU=', 'nFgw9WalTWo=', 'tOYoOg9urvc=', 'q7GDsp8dzYk='),
('YHY/RnbPUkU=', 'nFgw9WalTWo=', 'vdXg6UixQi0=', 'kLRhU/usSuM='),
('YHY/RnbPUkU=', 'yrMo357gAEA=', 'k4fOAOtFqIc=', '55ylH3EMLU4='),
('yrMo357gAEA=', '44d0tCBCSfg=', 'tOYoOg9urvc=', 'n0VnS+lQN4g='),
('yrMo357gAEA=', '44d0tCBCSfg=', 'yrMo357gAEA=', 'q7GDsp8dzYk='),
('yrMo357gAEA=', '5wPxJYgQ+V0=', '44d0tCBCSfg=', 'F6ApSYdU+SI='),
('yrMo357gAEA=', '5wPxJYgQ+V0=', 'tOYoOg9urvc=', 'RAC+GMoY9Gg='),
('yrMo357gAEA=', 'nFgw9WalTWo=', 'vdXg6UixQi0=', 'kLRhU/usSuM='),
('yrMo357gAEA=', 'Wfe1KQBrUmE=', '44d0tCBCSfg=', '8LMkPwmEPEY=');

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
