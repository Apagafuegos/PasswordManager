-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 23-04-2024 a las 13:39:33
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `passwords`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `passwords`
--

CREATE TABLE `passwords` (
  `id` int(11) NOT NULL,
  `idUsuario` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish2_ci NOT NULL,
  `usuario` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `password` varchar(100) NOT NULL,
  `servicio` varchar(100) CHARACTER SET utf8 COLLATE utf8_spanish2_ci NOT NULL DEFAULT 'Servicio no establecido'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `passwords`
--

INSERT INTO `passwords` (`id`, `idUsuario`, `usuario`, `password`, `servicio`) VALUES
(1, 'Extinthor17', 'Apagafuegos17', 'Curie747474:TT', 'TWITCH'),
(2, 'Extinthor17', 'extinthor17', 'holaholaCaracola', 'TWITTER'),
(4, 'Extinthor17', 'extinthor17', 'fagocitador33', 'YOUTUBE'),
(7, 'Extinthor17', 'carletronix', 'curie747474:', 'GOOGLE'),
(8, 'youyousie45', 'youyousie', 'vivaCadiz23', 'US.ES'),
(9, 'pepito', 'hoolahoola', '1234567', 'TWITCH'),
(10, 'anamaexco', 'anamaexco', '34343434', 'KIABI'),
(11, 'Extinthor17', 'csantos6952', 'Curie747474:CEU', 'CEU'),
(16, 'Extinthor17', 'sexadorDePollas', 'jejegodddd', 'XXNX');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `passwords`
--
ALTER TABLE `passwords`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `passwords`
--
ALTER TABLE `passwords`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
