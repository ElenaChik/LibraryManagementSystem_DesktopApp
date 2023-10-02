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
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `books` (
  `book_id` int NOT NULL AUTO_INCREMENT,
  `isbn` varchar(20) NOT NULL,
  `title` varchar(50) NOT NULL,
  `author` varchar(50) NOT NULL,
  `publisher` varchar(50) NOT NULL,
  `price` decimal(5,2) NOT NULL,
  `quantity` int DEFAULT NULL,
  `date_added` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`book_id`),
  UNIQUE KEY `isbn` (`isbn`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES (8,'978-1524740917','Love','God','Good News',10.00,3,'2023-05-04 02:30:11'),(9,'978-1452139975','Joy','Friends','Good News',25.00,3,'2023-05-04 02:31:32'),(10,'1452139970','Peace','God','Good News',20.00,3,'2023-05-04 02:32:50'),(11,'978-0062741578','Wisdom','Father','Family',100.00,3,'2023-05-04 02:34:18'),(12,'0062741578','Care','Mother','Family',0.00,3,'2023-05-04 02:36:31'),(13,'0711264228','Prosperity','Father','Gift to Receive',100.00,3,'2023-05-04 02:40:32'),(14,'978-0711264229','Help me','Friend','Gift to Receive',50.00,3,'2023-05-04 02:41:47'),(15,'9781419713101','Share with me','Friend','Gift to Receive',15.00,5,'2023-05-04 02:49:33'),(16,'978-1419713101','Patience','Beloved','Life Lessons',100.00,1,'2023-05-04 02:53:12'),(17,'978-1734346459','Faithfulness','Beloved','Family',12.00,3,'2023-05-04 02:55:19'),(18,'1734346450','Faith','God','Good News',12.00,3,'2023-05-04 02:56:18'),(19,'979-8750427901','Love','Mather','Family',0.00,5,'2023-05-04 02:57:23'),(20,'978-1955520270','Love me','Brothers','Family',55.00,3,'2023-05-04 02:58:38');
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
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
