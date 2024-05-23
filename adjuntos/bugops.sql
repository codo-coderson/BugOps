-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 10-05-2024 a las 01:47:56
-- Versión del servidor: 10.4.18-MariaDB
-- Versión de PHP: 7.3.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bugops`
--
CREATE DATABASE IF NOT EXISTS `bugops` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `bugops`;

--
-- La aplicación se va a loguear con BugOpsAdmin / BugOpsAdmin
--
-- CREATE USER 'BugOpsAdmin'@'localhost' IDENTIFIED BY 'BugOpsAdmin';
-- GRANT ALL PRIVILEGES ON bugops.* TO 'BugOpsAdmin'@'localhost';

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `adjunto`
--

CREATE TABLE `adjunto` (
  `ID` int(11) NOT NULL,
  `ID_Comentario` int(11) NOT NULL,
  `Nombre` varchar(255) NOT NULL,
  `Ruta` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `adjunto`
--

INSERT INTO `adjunto` (`ID`, `ID_Comentario`, `Nombre`, `Ruta`) VALUES
(21, 41, 'Matar proceso por nombre.txt', 'adjuntos/Matar proceso por nombre.txt'),
(22, 47, 'Estructuras de datos.docx', 'adjuntos/Estructuras de datos.docx'),
(23, 54, 'Primer Informe de error imprimible BugOps.pdf', 'adjuntos/Primer Informe de error imprimible BugOps.pdf'),
(24, 59, 'mariarenovada.webp', 'adjuntos/mariarenovada.webp'),
(25, 60, 'Horario_Examenes_Febrero_2023.pdf', 'adjuntos/Horario_Examenes_Febrero_2023.pdf'),
(30, 65, '!!mariarenovada.webp', 'adjuntos/!!mariarenovada.webp'),
(31, 66, 'TURNOS_DE_OFICINA_V17.1.pdf', 'adjuntos/TURNOS_DE_OFICINA_V17.1.pdf'),
(33, 73, 'borrame.py', 'adjuntos/borrame.py'),
(34, 75, 'borrame.py', 'adjuntos/borrame.py'),
(35, 76, 'borrame.py', 'adjuntos/borrame.py'),
(36, 78, 'borrame.py', 'adjuntos/borrame.py'),
(37, 82, 'borrame.py', 'adjuntos/borrame.py'),
(38, 83, 'borrame.py', 'adjuntos/borrame.py'),
(39, 84, 'DebuggingBugOps.jpg', 'adjuntos/DebuggingBugOps.jpg'),
(40, 85, 'Codo en el Latino branded.png', 'adjuntos/Codo en el Latino branded.png'),
(41, 87, 'Pasted image 20240506133855.png', 'adjuntos/Pasted image 20240506133855.png'),
(42, 90, 'Pasted image 20240507183411.png', 'adjuntos/Pasted image 20240507183411.png'),
(43, 94, 'DebuggingBugOps.jpg', 'adjuntos/DebuggingBugOps.jpg'),
(44, 96, 'Sandboxie.ini', 'adjuntos/Sandboxie.ini'),
(45, 97, 'WindowsUpdate.log', 'adjuntos/WindowsUpdate.log');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `colabora`
--

CREATE TABLE `colabora` (
  `Colaborador` varchar(30) NOT NULL,
  `ID_Proyecto` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `colabora`
--

INSERT INTO `colabora` (`Colaborador`, `ID_Proyecto`) VALUES
('azarithdev', 1),
('azarithdev', 3),
('azarithdev', 5),
('azarithdev', 11),
('belendev', 1),
('belendev', 3),
('belendev', 4),
('belendev', 11),
('cododev', 1),
('cododev', 2),
('cododev', 3),
('cododev', 5),
('cododev', 6),
('cododev', 8),
('cododev', 9),
('cododev', 11),
('mariadev', 2),
('mariadev', 6),
('mariadev', 11),
('programador', 2),
('programador', 7),
('programador', 8),
('programador', 11);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `comentario`
--

CREATE TABLE `comentario` (
  `ID` int(11) NOT NULL,
  `ID_Informe_Error` int(11) NOT NULL,
  `NombreUsuario` varchar(30) NOT NULL,
  `Contenido` text NOT NULL,
  `FechaCreacion` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `comentario`
--

INSERT INTO `comentario` (`ID`, `ID_Informe_Error`, `NombreUsuario`, `Contenido`, `FechaCreacion`) VALUES
(1, 1, 'reportador', 'The early years section seems to be incomplete, mates. If you look into it, you\'ll see that Pete Best is barely mentioned. We all agree that he was not Ringo, but he definitely was part of the story. I\'ve come to know that the info is already there - it\'s just not on display.', '2021-09-02 00:00:00'),
(2, 2, 'cododev', 'Alerta! El personaje puede atravesar paredes en ciertos puntos :O', '2020-07-16 00:00:00'),
(3, 3, 'isaacgest', 'Las imágenes de adopción tardan mucho en aparecer...', '2022-03-11 00:00:00'),
(4, 4, 'mamaadmin', '¡Hice una puntuación perfecta y no se sincronizó!', '2023-05-21 00:00:00'),
(5, 5, 'azarithdev', 'Los enlaces a algunos álbumes no funcionan.', '2021-10-06 00:00:00'),
(6, 6, 'belendev', 'Gente, las notificaciones de nuevo rescate no se están enviando.', '2022-04-16 00:00:00'),
(7, 7, 'cododev', 'Primero con seguridad FUNCIONANDO!', '2024-03-25 01:02:53'),
(15, 15, 'prueba', 'Pepe prueba crea error de prueba sin adjunto', '2024-03-26 23:58:11'),
(16, 16, 'prueba', 'Pepe prueba crea error de prueba con adjunto', '2024-03-26 23:58:30'),
(17, 17, 'administrador', 'CSS Mola!', '2024-03-27 16:35:43'),
(18, 18, 'cododev', 'BugOps mola', '2024-03-28 16:08:48'),
(20, 20, 'administrador', 'AppRaiser a pelo', '2024-04-10 22:53:17'),
(21, 21, 'administrador', 'Probando nuevo informe rediseñado', '2024-04-10 23:46:59'),
(22, 22, 'administrador', 'Descripción del PROBLEMA BEATLE!', '2024-04-11 00:51:35'),
(23, 23, 'administrador', 'Descr de Sin programador asignado, podré?', '2024-04-11 01:01:26'),
(24, 24, 'administrador', 'Prueba con adjunto y sin asignar', '2024-04-14 15:07:32'),
(25, 25, 'administrador', 'oTRA Prueba sin asignar', '2024-04-14 15:22:55'),
(26, 26, 'administrador', 'Prueba de archivo pequeño y sin asignar', '2024-04-14 15:25:09'),
(27, 27, 'administrador', 'Viene un comentario con adjunto para probar el límite', '2024-04-14 15:30:21'),
(28, 28, 'administrador', 'Nuevo informe para el proyecto parado', '2024-04-14 16:20:52'),
(29, 29, 'administrador', 'Hay que rescatar a esa mastina del cuello sangrante.', '2024-04-17 17:59:49'),
(30, 30, 'administrador', 'Prueba traducción prioridad', '2024-04-17 19:15:45'),
(31, 31, 'administrador', 'Probando traduccion prioridad newbug', '2024-04-17 19:22:23'),
(32, 32, 'administrador', 'PPPPPPPPPPPPPPPrueba traducción estado', '2024-04-17 19:30:14'),
(33, 33, 'administrador', 'Prueba traducción projectName en singular', '2024-04-17 19:42:26'),
(34, 34, 'probando', 'Porbando probando', '2024-04-17 19:57:06'),
(35, 35, 'administrador', 'Traduciendo a tope', '2024-04-17 20:07:25'),
(36, 36, 'administrador', '!!!!!!!Probando todo el programa después de traducir todas las variables de model', '2024-04-17 20:26:36'),
(37, 20, 'gestor', 'A pelo piqué?', '2024-04-19 21:37:05'),
(38, 37, 'gestor', 'No hay fallo, es el primer día', '2024-04-21 06:25:51'),
(39, 37, 'gestor', 'Estamos de acuerdo.', '2024-04-21 06:26:22'),
(40, 38, 'reportador', 'Soy un reportador decepcionado', '2024-04-22 19:08:29'),
(41, 19, 'programador', 'Soy programador, cambiando el estado de este informe. Es lo único que puedo cambiar. Y añadir comentarios como este.', '2024-04-22 19:50:18'),
(42, 19, 'programador', 'Este informe no sé cómo se llegó a hacer! No tenía descripción!', '2024-04-22 19:51:05'),
(43, 19, 'administrador', 'Probando nuevo informe rediseñado', '2024-04-10 23:46:59'),
(44, 19, 'reportador', 'Descripción del informe 19.', '2023-09-02 00:00:00'),
(45, 19, 'programador', 'Más comentarios! Más comentarios!', '2024-04-22 19:59:34'),
(46, 19, 'programador', 'Y maaaaaaaaaas', '2024-04-22 19:59:40'),
(47, 19, 'programador', 'El último, va', '2024-04-22 19:59:55'),
(48, 22, 'probando', 'Probando comentarios...', '2024-05-01 02:12:51'),
(49, 39, 'administrador', 'Descripción de prueba.', '2024-05-01 21:40:16'),
(50, 40, 'administrador', 'Quisiera dejar constancia de que no hay errores.', '2024-05-02 02:54:20'),
(51, 40, 'administrador', 'Nada que objetar.', '2024-05-02 02:54:41'),
(52, 19, 'prueba', 'seguimos haciendo pruebas', '2024-05-02 03:30:27'),
(53, 18, 'gestor', 'Desarchivado por seguir vigente.', '2024-05-03 01:37:07'),
(54, 38, 'gestor', 'asdasdasd', '2024-05-03 03:17:46'),
(55, 41, 'reportador', 'Reportador reportando error, reportador reportando error.', '2024-05-03 04:34:00'),
(56, 42, 'reportador', 'Reportador reportando nuevo error, Reportador reportando nuevo error', '2024-05-03 04:38:10'),
(57, 42, 'gestor', 'das', '2024-05-03 04:38:47'),
(58, 42, 'gestor', 'ryt', '2024-05-03 04:38:51'),
(59, 43, 'gestor', 'Trivial descripción de un insignificante informe de un proyecto archivado.', '2024-05-08 01:08:19'),
(60, 44, 'gestor', 'Probando a adjuntar un nuevo archivo tras haber cambiado String uploadsDir = \"adjuntos/\" por String uploadsDir = \"./adjuntos/\"', '2024-05-08 02:35:05'),
(65, 1, 'gestor', 'Attaching', '2024-05-08 03:40:34'),
(66, 41, 'reportador', 'Probando a dejar un comentario con adjunto.', '2024-05-08 16:03:16'),
(67, 39, 'reportador', 'Pues aquí hace muy buen día.', '2024-05-08 16:06:01'),
(68, 45, 'gestor', 'Probando si se crea una fila en la tabla seasigna al crear un informe de error con usuario gestor.', '2024-05-08 16:07:53'),
(69, 45, 'gestor', 'Sí, se crea.', '2024-05-08 16:08:23'),
(70, 46, 'reportador', 'Probando si se crea una fila en la tabla seasigna al crear un informe de error con usuario reportador', '2024-05-08 16:09:09'),
(71, 46, 'reportador', 'Sí, también se crea.', '2024-05-08 16:09:30'),
(72, 47, 'gestor', 'Probando la nueva columna \"asignado a\"', '2024-05-08 19:56:43'),
(73, 48, 'gestor', 'Probando por segunda vez la nueva columna \"asignado a\"', '2024-05-08 19:57:34'),
(74, 49, 'gestor', 'Probando por tercera vez la nueva columna \"asignado a\"', '2024-05-08 19:59:53'),
(75, 50, 'gestor', 'Probando por tercera vez la nueva columna \"asignado a\"', '2024-05-08 20:00:46'),
(76, 51, 'gestor', 'Probando por cuarta vez la nueva columna \"asignado a\"', '2024-05-08 20:06:27'),
(77, 52, 'gestor', 'Lo que dice el título, literalmente.', '2024-05-08 20:16:37'),
(78, 53, 'gestor', 'Cuando creo nuevo informe con gestor y lo asigno, aparece como asignado a NULL', '2024-05-08 20:19:34'),
(79, 54, 'gestor', 'Cuando creo nuevo informe con gestor y lo asigno, aparece como asignado a NULL.', '2024-05-08 20:20:06'),
(80, 55, 'gestor', 'Cuando creo nuevo informe con gestor y lo asigno, aparece como asignado a NULL', '2024-05-08 20:30:22'),
(81, 56, 'gestor', 'Cuando creo nuevo informe con gestor y lo asigno, aparece como asignado a NULL', '2024-05-08 20:57:10'),
(82, 57, 'gestor', 'Cuando creo nuevo informe con gestor y lo asigno, aparece como asignado a NULL', '2024-05-08 21:03:21'),
(83, 58, 'gestor', 'Prueba para ver si se resolvió el bug.', '2024-05-08 21:11:54'),
(84, 16, 'gestor', 'Debuggeando BugOps...', '2024-05-08 23:12:49'),
(85, 27, 'isaacgest', 'Aquí va la carga 🚚', '2024-05-08 23:47:14'),
(86, 44, 'gestor', '¡Ya funciona! El problema fue que ejecuté el proyecto en VS Code desde la carpeta padre, no desde la carpeta del proyecto.', '2024-05-09 02:04:22'),
(87, 59, 'gestor', 'En \"Nuevo informe de error\", tras seleccionar un proyecto, no aparece ningún programador para asignar a un informe de error asociado a ese proyecto.', '2024-05-09 02:24:03'),
(88, 59, 'cododev', 'Arreglado: El problema era que este código JS se ejecutaba antes de que el DOM estuviera cargado.\r\n\r\n        document.querySelector(\'.printDate\').innerText = new Date().toLocaleString();\r\n\r\n        function toggleNav() {\r\n            let guiaUsuario = document.getElementById(\"guiaUsuario\");\r\n            if (guiaUsuario.style.width === \"375px\") {\r\n                guiaUsuario.style.width = \"0\";\r\n                document.getElementById(\"contenidoPagina\").style.marginRight = \"0\";\r\n            } else {\r\n                guiaUsuario.style.width = \"375px\";\r\n                document.getElementById(\"contenidoPagina\").style.marginRight = \"375px\";\r\n            }\r\n        }\r\n\r\n        function closeNav() {\r\n            document.getElementById(\"guiaUsuario\").style.width = \"0\";\r\n            document.getElementById(\"contenidoPagina\").style.marginRight = \"0\";\r\n        }\r\n\r\n        document.getElementById(\'guiaUsuario\').addEventListener(\'wheel\', function (e) {\r\n            e.preventDefault();\r\n            this.scrollTop += e.deltaY;\r\n        });', '2024-05-09 02:27:08'),
(89, 54, 'cododev', 'Solución encontrada: Desde la vista no se pasaba correctamente la variable del programador que se había asignado (se hacía, pero con un nombre equivocado). El código ha sido ya corregido.', '2024-05-09 02:38:23'),
(90, 60, 'gestor', 'En la ficha de informe de error aparecen todos los programadores del sistema para asignar, en lugar de aparecer solo los colaboradores del proyecto:', '2024-05-09 02:43:39'),
(91, 60, 'gestor', 'Creo haber encontrado la solución: se estaba llamando a una función del servicio que recuperaba todos los programadores, sin filtrar por proyecto. El código ya ha sido corregido.', '2024-05-09 03:03:37'),
(92, 61, 'gestor', 'He cambiado el código en DatabaseService.createBugReport() para ver si ya solo se guarda la relativa. Probemos con un adjunto...', '2024-05-09 17:22:38'),
(93, 62, 'gestor', 'He cambiado el código en DatabaseService.createBugReport() para ver si ya solo se guarda la relativa. Probemos con un adjunto...', '2024-05-09 17:23:15'),
(94, 63, 'gestor', 'He cambiado el código en DatabaseService.createBugReport() para ver si ya solo se guarda la relativa. Probemos con un adjunto...', '2024-05-09 17:27:37'),
(95, 64, 'gestor', 'Los archivos que se nos han entregado para el diseño de la web de Transportes Salamanca están en Canva, y la especificación es menos precisa de lo que acostrumbramos con nuestro querido Figma. Programador, contacta con el cliente para asegurarnos de si seguimos con los archivos Canva o si esperamos a otros de otro tipo.', '2024-05-09 17:45:45'),
(96, 65, 'administrador', 'Probando Nuevo informe de error con adjunto para Transportes Salamanca desde un administrador', '2024-05-09 20:07:22'),
(97, 66, 'gestor', 'Probando Nuevo informe de error con adjunto para Transportes Salamanca desde un gestor', '2024-05-09 20:10:04');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `informe_error`
--

CREATE TABLE `informe_error` (
  `ID` int(11) NOT NULL,
  `ID_Proyecto` int(11) NOT NULL,
  `Reportador` varchar(30) NOT NULL,
  `Titulo` varchar(255) NOT NULL,
  `Prioridad` enum('Baja','Media','Alta','Crítica') NOT NULL,
  `Estado` enum('Recibido','En cola','En progreso','En revisión','Solucionado','Descartado') NOT NULL,
  `FechaCreacion` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `informe_error`
--

INSERT INTO `informe_error` (`ID`, `ID_Proyecto`, `Reportador`, `Titulo`, `Prioridad`, `Estado`, `FechaCreacion`) VALUES
(1, 2, 'lupinrep', 'Falta contenido en sección Early Years', 'Baja', 'Recibido', '2021-09-01 10:13:11'),
(2, 2, 'cododev', 'Error de colisión en el nivel subterráneo', 'Media', 'En cola', '2024-02-15 13:24:28'),
(3, 3, 'isaacgest', 'Retraso en la carga de imágenes de perfiles de rescate', 'Alta', 'En cola', '2022-03-10 19:31:12'),
(4, 4, 'mamaadmin', 'Falla la sincronización de puntuaciones', 'Baja', 'En revisión', '2023-05-20 12:06:49'),
(5, 2, 'cododev', 'Enlace roto en la discografía', 'Alta', 'En revisión', '2021-10-05 05:47:45'),
(6, 3, 'azarithdev', 'Fallo de notificaciones', 'Media', 'En revisión', '2022-04-15 15:54:11'),
(7, 3, 'cododev', 'Primero con seguridad FUNCIONANDO!', 'Crítica', 'Recibido', '2024-03-25 01:02:53'),
(15, 4, 'prueba', 'Pepe prueba crea error de prueba sin adjunto', 'Alta', 'En progreso', '2024-03-26 23:58:11'),
(16, 2, 'prueba', 'Pepe prueba crea error de prueba con adjunto', 'Baja', 'Recibido', '2024-03-26 23:58:30'),
(17, 2, 'administrador', 'CSS Mola!', 'Baja', 'En revisión', '2024-03-27 16:35:43'),
(18, 2, 'cododev', 'BugOps mola', 'Alta', 'En progreso', '2024-03-28 16:08:48'),
(19, 1, 'reportador', 'Se ve el archivo adjunto y se puede quitar!', 'Baja', 'Solucionado', '2024-04-03 23:18:42'),
(20, 2, 'administrador', 'AppRaiser a pelo', 'Crítica', 'Recibido', '2024-04-10 22:53:17'),
(21, 3, 'administrador', 'Probando nuevo informe rediseñado', 'Alta', 'En progreso', '2024-04-10 23:46:59'),
(22, 2, 'administrador', 'PROBLEMA BEATLE!', 'Crítica', 'Recibido', '2024-04-11 00:51:35'),
(23, 2, 'administrador', 'Sin programador asignado, podré?', 'Media', 'Recibido', '2024-04-11 01:01:26'),
(24, 4, 'administrador', 'Prueba sin asignar', 'Media', 'Recibido', '2024-04-14 15:07:32'),
(25, 1, 'administrador', 'oTRA Prueba sin asignar', 'Baja', 'Recibido', '2024-04-14 15:22:55'),
(26, 4, 'administrador', 'Prueba de archivo pequeño y sin asignar', 'Media', 'Recibido', '2024-04-14 15:25:09'),
(27, 4, 'administrador', 'Archivo JUSTITO DE GRANDE', 'Media', 'Recibido', '2024-04-14 15:30:21'),
(28, 1, 'administrador', 'Nuevo informe para el proyecto parado', 'Media', 'Recibido', '2024-04-14 16:20:52'),
(29, 3, 'administrador', 'Rescatar a Mastina', 'Crítica', 'En progreso', '2024-04-17 17:59:49'),
(30, 3, 'administrador', 'Prueba traducción prioridad', 'Alta', 'Recibido', '2024-04-17 19:15:45'),
(31, 2, 'administrador', 'Probando traduccion prioridad newbug', 'Media', 'Recibido', '2024-04-17 19:22:23'),
(32, 2, 'administrador', 'Prueba traducción estado', 'Alta', 'Descartado', '2024-04-17 19:30:14'),
(33, 5, 'administrador', 'Prueba traducción projectName en singular', 'Baja', 'Descartado', '2024-04-17 19:42:26'),
(34, 5, 'probando', 'Porbando probando', 'Crítica', 'Solucionado', '2024-04-17 19:57:06'),
(35, 1, 'administrador', 'Traduciendo a tope', 'Crítica', 'En revisión', '2024-04-17 20:07:25'),
(36, 1, 'administrador', '!!!!!!!Probando todo el programa después de traducir todas las variables de model', 'Baja', 'Solucionado', '2024-04-17 20:26:36'),
(37, 6, 'gestor', 'No hay fallo, es el primer día', 'Alta', 'En progreso', '2024-04-21 06:25:51'),
(38, 2, 'reportador', 'Soy un reportador decepcionado', 'Media', 'Recibido', '2024-04-22 19:08:29'),
(39, 3, 'administrador', 'al crear un informe...', 'Media', 'Recibido', '2024-05-01 21:40:16'),
(40, 6, 'administrador', 'Informe de no error', 'Media', 'Recibido', '2024-05-02 02:54:20'),
(41, 2, 'reportador', 'Reportador reportando error', 'Media', 'Recibido', '2024-05-03 04:34:00'),
(42, 8, 'reportador', 'Reportador reportando nuevo error', 'Media', 'Recibido', '2024-05-03 04:38:10'),
(43, 5, 'gestor', 'Insignificante informe de un proyecto archivado', 'Baja', 'En cola', '2024-05-08 01:08:19'),
(44, 9, 'gestor', 'Los adjuntos se están guardando en una carperta paralela al proyecto, en lugar de dentro', 'Alta', 'En revisión', '2024-05-06 02:35:05'),
(45, 2, 'gestor', 'Probando si se crea una fila en la tabla seasigna al crear un informe de error con usuario gestor', 'Media', 'Recibido', '2024-05-08 16:07:53'),
(46, 3, 'reportador', 'Probando si se crea una fila en la tabla seasigna al crear un informe de error con usuario reportador', 'Media', 'Descartado', '2024-05-08 16:09:09'),
(47, 3, 'gestor', 'Probando la nueva columna \"asignado a\"', 'Baja', 'En revisión', '2024-05-08 19:56:43'),
(48, 3, 'gestor', 'Probando por segunda vez la nueva columna \"asignado a\"', 'Baja', 'Descartado', '2024-05-08 19:57:34'),
(49, 2, 'gestor', 'Probando por tercera vez la nueva columna \"asignado a\"', 'Crítica', 'Descartado', '2024-05-08 19:59:53'),
(50, 2, 'gestor', 'Probando por tercera vez la nueva columna \"asignado a\"', 'Crítica', 'Recibido', '2024-05-08 20:00:46'),
(51, 3, 'gestor', 'Probando por cuarta vez la nueva columna \"asignado a\"', 'Baja', 'En revisión', '2024-05-08 20:06:27'),
(52, 2, 'gestor', 'Cuando creo nuevo informe con gestor y lo asigno, aparece como asignado a NULL', 'Media', 'Descartado', '2024-05-08 20:16:37'),
(53, 3, 'gestor', 'Cuando creo nuevo informe con gestor y lo asigno, aparece como asignado a NULL', 'Baja', 'Descartado', '2024-05-08 20:19:34'),
(54, 9, 'gestor', 'Cuando creo nuevo informe con gestor y lo asigno, aparece como asignado a NULL', 'Alta', 'En revisión', '2024-05-07 20:20:06'),
(55, 3, 'gestor', 'Cuando creo nuevo informe con gestor y lo asigno, aparece como asignado a NULL', 'Baja', 'Descartado', '2024-05-08 20:30:22'),
(56, 3, 'gestor', 'Cuando creo nuevo informe con gestor y lo asigno, aparece como asignado a NULL', 'Baja', 'Descartado', '2024-05-08 20:57:10'),
(57, 3, 'gestor', 'Cuando creo nuevo informe con gestor y lo asigno, aparece como asignado a NULL', 'Baja', 'Descartado', '2024-05-08 21:03:21'),
(58, 3, 'gestor', 'Probando de nuevo a crear un informe y asignarlo en el momento', 'Baja', 'En revisión', '2024-05-08 21:11:54'),
(59, 9, 'gestor', 'No hay programadores para seleccionar', 'Crítica', 'En revisión', '2024-05-08 02:24:03'),
(60, 9, 'gestor', 'Conjunto erróneo de programadores disponibles al consultar un informe', 'Crítica', 'Recibido', '2024-05-09 02:43:39'),
(61, 9, 'gestor', 'Cuando adjunto un archivo, la ruta que se guarda en la BD es absoulta', 'Crítica', 'Descartado', '2024-05-09 17:22:38'),
(62, 9, 'gestor', 'Cuando adjunto un archivo, la ruta que se guarda en la BD es absoulta', 'Crítica', 'Descartado', '2024-05-09 17:23:15'),
(63, 9, 'gestor', 'Cuando adjunto un archivo, la ruta que se guarda en la BD es absoulta', 'Crítica', 'En revisión', '2024-05-09 17:27:37'),
(64, 11, 'gestor', 'Formato de los archivos del diseño', 'Alta', 'En cola', '2024-05-09 17:45:45'),
(65, 11, 'administrador', 'Probando Nuevo informe de error con adjunto para Transportes Salamanca desde un administrador', 'Media', 'Recibido', '2024-05-09 20:07:22'),
(66, 11, 'gestor', 'Probando Nuevo informe de error con adjunto para Transportes Salamanca desde un gestor', 'Alta', 'En cola', '2024-05-09 20:10:04');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proyecto`
--

CREATE TABLE `proyecto` (
  `ID` int(11) NOT NULL,
  `Nombre` varchar(100) NOT NULL,
  `FechaCreacion` datetime NOT NULL,
  `Archivado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `proyecto`
--

INSERT INTO `proyecto` (`ID`, `Nombre`, `FechaCreacion`, `Archivado`) VALUES
(1, 'Undertale', '2020-06-01 00:34:21', 1),
(2, 'BeatlesFiles', '2021-08-15 15:16:32', 0),
(3, 'Animal Rescue', '2022-02-20 16:11:37', 0),
(4, 'Psychosomnium', '2023-04-25 02:16:00', 0),
(5, 'Proyecto archivado', '2023-12-01 11:04:04', 1),
(6, 'María 20 de abril', '2024-04-21 05:31:09', 0),
(7, 'Proyecto vacío', '2024-04-21 21:17:42', 0),
(8, 'Another world', '2024-05-01 22:32:23', 0),
(9, 'BugOps', '2024-05-08 02:34:34', 0),
(10, 'Proyecto vacío sin colaboradores', '2024-05-08 16:10:31', 1),
(11, 'Transportes Salamanca', '2024-05-09 17:42:29', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `seasigna`
--

CREATE TABLE `seasigna` (
  `ID_Informe_Error` int(11) NOT NULL,
  `Programador` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `seasigna`
--

INSERT INTO `seasigna` (`ID_Informe_Error`, `Programador`) VALUES
(17, NULL),
(23, NULL),
(24, NULL),
(26, NULL),
(30, NULL),
(31, NULL),
(35, NULL),
(38, NULL),
(39, NULL),
(41, NULL),
(42, NULL),
(43, NULL),
(45, NULL),
(46, NULL),
(48, NULL),
(49, NULL),
(51, NULL),
(53, NULL),
(55, NULL),
(56, NULL),
(63, NULL),
(65, NULL),
(3, 'azarithdev'),
(6, 'azarithdev'),
(7, 'azarithdev'),
(19, 'azarithdev'),
(21, 'azarithdev'),
(4, 'belendev'),
(28, 'belendev'),
(36, 'belendev'),
(47, 'belendev'),
(57, 'belendev'),
(58, 'belendev'),
(2, 'cododev'),
(15, 'cododev'),
(16, 'cododev'),
(18, 'cododev'),
(20, 'cododev'),
(22, 'cododev'),
(25, 'cododev'),
(27, 'cododev'),
(29, 'cododev'),
(32, 'cododev'),
(33, 'cododev'),
(34, 'cododev'),
(37, 'cododev'),
(40, 'cododev'),
(44, 'cododev'),
(52, 'cododev'),
(54, 'cododev'),
(59, 'cododev'),
(60, 'cododev'),
(1, 'mariadev'),
(5, 'programador'),
(50, 'programador'),
(64, 'programador'),
(66, 'programador');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `NombreUsuario` varchar(30) NOT NULL,
  `Password` varchar(60) DEFAULT NULL,
  `rol` enum('ADMINISTRADOR','GESTOR','PROGRAMADOR','REPORTADOR') NOT NULL,
  `Nombre` varchar(50) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `FechaAlta` datetime NOT NULL,
  `Eliminado` tinyint(1) NOT NULL,
  `needsPasswordChange` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`NombreUsuario`, `Password`, `rol`, `Nombre`, `Email`, `FechaAlta`, `Eliminado`, `needsPasswordChange`) VALUES
('administrador', '$2a$10$pJ60r8l56Hmo1ABqJMuSJ.UnkAhUzcAn93rrv0KsVMgcFhGyVajm2', 'ADMINISTRADOR', 'Administrador García', 'administrador@gmail.com', '2024-03-25 23:28:47', 0, 0),
('azarithdev', '$2a$10$8jg9Bx.jTkXu0xYBJo1cOer7wMxIoA/MKV9JICzDlM2SHIqU157oa', 'PROGRAMADOR', 'Azarith Moreno', 'azarithdev@gmail.com', '2024-03-26 00:03:52', 0, 0),
('belendev', '$2a$10$tHqJxvoyG1M6mB3blaybd.Ay0O.MMF6U1qql.v7g4vpwik1YbFjKm', 'PROGRAMADOR', 'Belén Bembé', 'belendev@gmail.com', '2024-03-26 00:46:15', 0, 0),
('borrame', '$2a$10$AoybQHN72Ymt6CHNfQvkNux/pP0K/Y31Z4Jl.xSKTumfr4FbKYsQS', 'ADMINISTRADOR', 'Borrame García', 'borrame@gmail.com', '2024-05-03 04:03:54', 1, 0),
('borrameya', '$2a$10$.a5TKOHRo/B2IvE7Id8RQehiZPuChbk7732FF4u3VdpTYo.6JXr8i', 'ADMINISTRADOR', 'Prueba García', 'borrameya@gmail.com', '2024-04-03 22:23:03', 1, 0),
('cododev', '$2a$10$.52rXKY3/hDmLTLLPDXVSO5XB9AtJZm5QEwmW.biHz7I8pF2Yzqbe', 'PROGRAMADOR', 'Adolfo Fernández', 'cododev@gmail.com', '2024-03-26 00:03:56', 0, 0),
('fakeuser', '$2a$10$GNKAzCpqt4qlrnnlOhFdh.l9oflN2ReabHKcloGORBDRrpgQGu3ra', 'REPORTADOR', 'Fakeuser García', 'fakeuser@gmail.com', '2024-05-09 20:13:03', 0, 0),
('foouser', '$2a$10$OT5d2xcepwnGx4lc5L9FWOp1oAlcNcFH.G9DfVUzwjLCXUActA06O', 'REPORTADOR', 'Foouser García', 'foouser@gmail.com', '2024-05-08 16:38:17', 0, 0),
('gestor', '$2a$10$kuXt.QvNTgD3PFc.eQNkPeJJ6TwA3k1vnIMR80fJbLC7DC8MKrUhO', 'GESTOR', 'Gestor García', 'gestor@gmail.com', '2024-03-25 23:29:45', 0, 0),
('isaacgest', '$2a$10$pi5GYtyX1usRt8c4.XAMB.A1sKABo8ixeoMkkcq2IED9FFfA0HGtS', 'GESTOR', 'Isaac Moreno', 'isaacgest@gmail.com', '2024-03-26 00:04:05', 0, 0),
('lupinrep', '$2a$10$eTp/DEsf96eG.Wg5tyjrkOyGMvfmRa3KBwDT0xE9jjrTMYGTraRe2', 'REPORTADOR', 'Lupín Podenco', 'lupinrep@gmail.com', '2024-03-26 00:04:10', 0, 0),
('mamaadmin', '$2a$10$/i6IYpV.Rt3hzG5GP4YxeO7IPxeOht2y3DFZiCNfbXt2XjiPq2LJ2', 'ADMINISTRADOR', 'Elena Álvarez', 'mamaadmin@gmail.com', '2024-03-26 00:04:07', 0, 0),
('mariadev', '$2a$10$1IaTMdWzTGAPWwW6Ovsv5ufdeUj/qSJcr06JH95.XSVcKjbThNGfq', 'PROGRAMADOR', 'María Altuna', 'mariadev@gmail.com', '2024-05-07 18:51:32', 0, 0),
('probando', '$2a$10$K.UwcKf9qLJ6Uo6tAIUrQOdmwC3zdcLyoGBPIsVYl0HCCM8EjsBfK', 'GESTOR', 'Probando García', 'probando@gmail.com', '2024-04-17 19:51:57', 0, 0),
('programador', '$2a$10$o2AQhFZ2/YuF7q9Mn1i4XO7WBIc593LASXkSCx6P8npI3pKUQ3Syu', 'PROGRAMADOR', 'Programador García', 'programador@gmail.com', '2024-03-25 23:42:39', 0, 0),
('prueba', '$2a$10$aTCGsx9Xb2CHncbENwxhNeec6tUjhklaN5ztw6ILAMNvxc8kwuoOi', 'PROGRAMADOR', 'Prueba García', 'prueba@gmail.com', '2024-03-26 23:56:21', 1, 0),
('reportador', '$2a$10$l2/3lVJxHb4AmUlZ18XsiOGOvips9.DSUHwDoRizU9R6WN4.uJ7WS', 'REPORTADOR', 'Reportador García', 'reportador@gmail.com', '2024-03-25 23:30:34', 0, 0);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `adjunto`
--
ALTER TABLE `adjunto`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_Comentario` (`ID_Comentario`);

--
-- Indices de la tabla `colabora`
--
ALTER TABLE `colabora`
  ADD PRIMARY KEY (`Colaborador`,`ID_Proyecto`),
  ADD KEY `ID_Proyecto` (`ID_Proyecto`);

--
-- Indices de la tabla `comentario`
--
ALTER TABLE `comentario`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_Informe_Error` (`ID_Informe_Error`),
  ADD KEY `NombreUsuario` (`NombreUsuario`),
  ADD KEY `idx_fecha_creacion` (`FechaCreacion`);

--
-- Indices de la tabla `informe_error`
--
ALTER TABLE `informe_error`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_Proyecto` (`ID_Proyecto`),
  ADD KEY `Reportador` (`Reportador`);

--
-- Indices de la tabla `proyecto`
--
ALTER TABLE `proyecto`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `seasigna`
--
ALTER TABLE `seasigna`
  ADD PRIMARY KEY (`ID_Informe_Error`) USING BTREE,
  ADD KEY `Programador` (`Programador`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`NombreUsuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `adjunto`
--
ALTER TABLE `adjunto`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;

--
-- AUTO_INCREMENT de la tabla `comentario`
--
ALTER TABLE `comentario`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=98;

--
-- AUTO_INCREMENT de la tabla `informe_error`
--
ALTER TABLE `informe_error`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=67;

--
-- AUTO_INCREMENT de la tabla `proyecto`
--
ALTER TABLE `proyecto`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `adjunto`
--
ALTER TABLE `adjunto`
  ADD CONSTRAINT `adjunto_ibfk_1` FOREIGN KEY (`ID_Comentario`) REFERENCES `comentario` (`ID`);

--
-- Filtros para la tabla `colabora`
--
ALTER TABLE `colabora`
  ADD CONSTRAINT `colabora_ibfk_1` FOREIGN KEY (`Colaborador`) REFERENCES `usuario` (`NombreUsuario`),
  ADD CONSTRAINT `colabora_ibfk_2` FOREIGN KEY (`ID_Proyecto`) REFERENCES `proyecto` (`ID`);

--
-- Filtros para la tabla `comentario`
--
ALTER TABLE `comentario`
  ADD CONSTRAINT `comentario_ibfk_1` FOREIGN KEY (`ID_Informe_Error`) REFERENCES `informe_error` (`ID`),
  ADD CONSTRAINT `comentario_ibfk_2` FOREIGN KEY (`NombreUsuario`) REFERENCES `usuario` (`NombreUsuario`);

--
-- Filtros para la tabla `informe_error`
--
ALTER TABLE `informe_error`
  ADD CONSTRAINT `informe_error_ibfk_1` FOREIGN KEY (`ID_Proyecto`) REFERENCES `proyecto` (`ID`),
  ADD CONSTRAINT `informe_error_ibfk_2` FOREIGN KEY (`Reportador`) REFERENCES `usuario` (`NombreUsuario`);

--
-- Filtros para la tabla `seasigna`
--
ALTER TABLE `seasigna`
  ADD CONSTRAINT `seasigna_ibfk_1` FOREIGN KEY (`ID_Informe_Error`) REFERENCES `informe_error` (`ID`),
  ADD CONSTRAINT `seasigna_ibfk_2` FOREIGN KEY (`Programador`) REFERENCES `usuario` (`NombreUsuario`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
