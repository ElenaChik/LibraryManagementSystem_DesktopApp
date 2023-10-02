-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: library_db
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `role` enum('librarian','student') DEFAULT NULL,
  `login` varchar(10) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `contact` varchar(100) DEFAULT NULL,
  `date_created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (4,'admin','librarian','0','Gyl7ox7iq8wE3K7cssZlMOsOuiHq7kPe/Wo7dg439kIIwBP046PwdUzxtKIGqfHx','514','2023-04-19 16:18:14'),(19,'admin2','librarian','admin','hr4wN0IzTbcd0RQlSgVRSFAdFdM+OiueLffAvtw0JYDyTlY/qfZb2VsjxM9OPCYz','514 111 0000','2023-04-26 22:42:45'),(20,'Tristan','student','tristan','976dD663J8jIBpEjgK5dyd4EsSnVsh9q9IRKGGMAeCyw2h6k7wQzlHf0wUjUR+P6','514 555 1111','2023-05-04 02:14:51'),(21,'Sharif','student','sharif','lZOfo6G4XMEKEOX7CzSxZI2xCGWqB/i7KKF2lsAhOS+fTSNOXE5k1adtglJIl3yF','514 555 2222','2023-05-04 02:15:32'),(22,'Hooman','student','hooman','qEA1KyGS2GC7chmd53y73H0LB0qhTOVteuj6gQtOMiJ2xnXXwpyC7CFZlrmYthKv','514 555 3333','2023-05-04 02:16:00'),(23,'Lee','student','lee','XDTZOQxXwaaO2vUABrJzbF3o0AT+jnIzaL8+sRT2gbS5PQ6g0z4vkT4vDxshhTjo','514 555 4444','2023-05-04 02:17:26'),(24,'Bilgin','student','bilgin','Nw5D3FNLLxH5PP6309+vMHz9rPaAXjBbUbiT1uIhooL/4hD6oS4R3eBq46AfdJY1','514 555 5555','2023-05-04 02:18:16'),(25,'Elena','student','elena','8XVimvX8IdZmbM/Nl6o9PibeZG19obexaJnpd+UArVhEgrJyxkcPHnQ3p42GtWpa','514 555 6666','2023-05-04 02:19:21'),(26,'Hany','student','hany','T4l7i06vd9woVtaOhtkwOQXk5rhd1kaZGqRQbi9h64tgmkUgQmHc/hTyb0LCrjhu','514 555 7777','2023-05-04 02:19:49'),(27,'Jahtawi','student','jahtawi','zTEA5063UjyBNzDP1gTL1c44YSRLTAP8jvsRi+PbYhdgtAXjOs7I5S9InWdG6DUP','514 555 8888','2023-05-04 02:20:20'),(28,'Javad','student','javad','H43U7Lpo9Us+wRcNx72+zo9nLXGVhw/Pdc0BNf38qwi8QnB3LC8WPGw0KdEW00Hs','514 555 9999','2023-05-04 02:20:43'),(29,'Adin','librarian','adin','L7kGoy54+LjolvFczBKVvr/Ca9O7UN67ouCuNkoIex0iTW5ttrfwXBpR9Efn5bl6','514 555 0101','2023-05-04 02:22:03'),(30,'She Phing','student','she','viS5PgwsHzFlX7NFwOhB1Eld3FMCXSNFphuWNgMtzjTS9OY/CaESVuv/4Qi0xnhZ','514 333 1111','2023-05-04 02:27:03');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-04 12:46:28
