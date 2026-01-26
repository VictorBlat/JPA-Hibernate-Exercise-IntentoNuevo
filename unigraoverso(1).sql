-- phpMyAdmin SQL Dump
-- version 5.2.1deb3
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 26-01-2026 a las 12:52:09
-- Versión del servidor: 10.11.13-MariaDB-0ubuntu0.24.04.1
-- Versión de PHP: 8.3.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `unigraoverso`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `planets`
--

CREATE TABLE `planets` (
  `id` int(11) NOT NULL,
  `gravity` double NOT NULL,
  `has_rings` bit(1) NOT NULL,
  `last_albedo_measurement` date NOT NULL,
  `mass` double NOT NULL,
  `name` varchar(255) NOT NULL,
  `number_of_moons` int(11) NOT NULL,
  `radius` double NOT NULL,
  `solar_system_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `planets`
--

INSERT INTO `planets` (`id`, `gravity`, `has_rings`, `last_albedo_measurement`, `mass`, `name`, `number_of_moons`, `radius`, `solar_system_id`) VALUES
(1, 3.7, b'0', '2023-01-15', 3.3011e23, 'Mercurio', 0, 2439.7, 1),
(2, 8.87, b'0', '2023-02-20', 4.8675e24, 'Venus', 0, 6051.8, 1),
(3, 9.8, b'0', '2024-01-01', 5.972e24, 'Tierra', 1, 6371, 1),
(4, 3.71, b'0', '2023-05-05', 6.4171e23, 'Marte', 2, 3389.5, 1),
(5, 24.79, b'1', '2022-11-30', 1.898e27, 'Júpiter', 95, 69911, 1),
(6, 10.44, b'1', '2023-10-10', 5.683e26, 'Saturno', 146, 58232, 1),
(7, 8.69, b'1', '2023-09-25', 8.681e25, 'Urano', 27, 25362, 1),
(8, 11.15, b'1', '2023-07-18', 1.024e26, 'Neptuno', 16, 24622, 1),
(9, 0.62, b'0', '2023-04-12', 1.309e22, 'Plutón', 5, 1188.3, 1),
(10, 0.45, b'0', '2012-01-21', 1.194e23, 'Draugr', 0, 1400, 2),
(11, 12.8, b'0', '2013-01-21', 2.568e25, 'Poltergeist', 1, 9400, 2),
(12, 11.2, b'0', '2014-01-21', 2.329e25, 'Phobetor', 2, 9100, 2),
(13, 0.08, b'0', '2002-02-10', 2.38e21, 'Lich-e', 3, 450, 2),
(14, 3.7, b'0', '2023-01-15', 3.3011e23, 'Tatooine', 0, 2439.7, 3),
(15, 8.87, b'0', '2023-02-20', 4.8675e24, 'Arrakis', 0, 6051.8, 3),
(16, 9.8, b'0', '2024-01-01', 5.972e24, 'Pandora', 1, 6371, 3),
(17, 3.71, b'0', '2023-05-05', 6.4171e23, 'Solaris', 2, 3389.5, 3),
(52, 100, b'0', '2026-01-26', 1e24, 'PlanetSql', 0, 560, 52);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `solar_systems`
--

CREATE TABLE `solar_systems` (
  `id` int(11) NOT NULL,
  `radius` double NOT NULL,
  `name` varchar(255) NOT NULL,
  `star_distance` double NOT NULL,
  `star_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `solar_systems`
--

INSERT INTO `solar_systems` (`id`, `radius`, `name`, `star_distance`, `star_name`) VALUES
(1, 40, 'Sistema Solar', 0, 'Sol'),
(2, 0.46, 'Lich system', 700, 'Lich'),
(3, 55, 'Sistema Inventado', 100, 'Aitana'),
(52, 150, 'SQL', 60, 'Prueba2'),
(53, 65.3, 'P', 50.2, 'A');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `planets`
--
ALTER TABLE `planets`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKh0g29s5m8jlmc03lmtipnh8w7` (`solar_system_id`);

--
-- Indices de la tabla `solar_systems`
--
ALTER TABLE `solar_systems`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `planets`
--
ALTER TABLE `planets`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=53;

--
-- AUTO_INCREMENT de la tabla `solar_systems`
--
ALTER TABLE `solar_systems`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=54;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `planets`
--
ALTER TABLE `planets`
  ADD CONSTRAINT `FKh0g29s5m8jlmc03lmtipnh8w7` FOREIGN KEY (`solar_system_id`) REFERENCES `solar_systems` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
