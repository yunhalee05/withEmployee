DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
  `company_id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `description` varchar(150) NOT NULL,
  `name` varchar(40) NOT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`company_id`),
  UNIQUE KEY `UK_niu8sfil2gxywcru9ah3r4ec5` (`name`),
  KEY `FKdy4v2yb46hefqicjpyj7b7e4s` (`user_id`),
  CONSTRAINT `FKdy4v2yb46hefqicjpyj7b7e4s` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `company` WRITE;
INSERT INTO `company` VALUES (1,'2022-06-04 13:09:46.848138','2022-06-04 13:09:46.848138','Share your memory.','Picshare 📸',2),(2,'2022-06-04 13:12:20.379339','2022-06-04 13:12:20.379339','Communicate with your friends.','SocialMedia',3),(3,'2022-06-04 13:16:32.332553','2022-06-04 13:16:32.332553','Sale fruits.','Peach 🍑',3),(4,'2022-06-04 13:17:36.274347','2022-06-04 13:17:36.274347','Make B-phone, B-mac, etc.','Banana',3),(5,'2022-06-04 13:23:28.439467','2022-06-04 13:23:28.439467','Make electronics.','RG',3),(6,'2022-06-04 13:28:25.835588','2022-06-04 13:28:25.835588','Collaborate with other companies & members.','WithEmployee',8),(7,'2022-06-04 13:30:31.350209','2022-06-04 13:30:31.350209','Be a no.1.','No.1 ',8),(8,'2022-06-04 13:31:37.583740','2022-06-04 13:31:37.583740','Produce music.','Music',8),(9,'2022-06-04 13:37:01.349242','2022-06-04 13:37:01.349242','Reserve space for you','Space',2);
UNLOCK TABLES;

DROP TABLE IF EXISTS `conversation`;
CREATE TABLE `conversation` (
  `conversation_id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `is_other_company` bit(1) DEFAULT NULL,
  `is_same_company` bit(1) DEFAULT NULL,
  `is_team_member` bit(1) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`conversation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `conversation` WRITE;
INSERT INTO `conversation` VALUES (2,'2022-06-04 13:34:26.175761','2022-06-04 13:34:26.175761','',_binary '',_binary '\0',_binary '\0','It is awesome application!'),(3,'2022-06-04 13:35:53.314377','2022-06-04 13:35:53.314377','',_binary '\0',_binary '\0',_binary '','That was great display, user4.'),(4,'2022-06-04 13:36:14.065667','2022-06-04 13:36:14.065667','',_binary '\0',_binary '\0',_binary '','Could you work on friday?'),(5,'2022-06-04 13:38:37.896812','2022-06-04 13:38:37.896812','',_binary '\0',_binary '\0',_binary '','Can you have a meeting with ceo3 on friday?');
UNLOCK TABLES;

DROP TABLE IF EXISTS `conversation_user`;
CREATE TABLE `conversation_user` (
  `conversation_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`conversation_id`,`user_id`),
  KEY `FKhjie8c93f6ctc27ujqg84lx0f` (`user_id`),
  CONSTRAINT `FKb71b5q60yd0bfc1eb8fgwm4sk` FOREIGN KEY (`conversation_id`) REFERENCES `conversation` (`conversation_id`),
  CONSTRAINT `FKhjie8c93f6ctc27ujqg84lx0f` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `conversation_user` WRITE;
INSERT INTO `conversation_user` VALUES (2,2),(3,2),(4,2),(5,2),(5,3),(3,6),(4,7),(2,8);
UNLOCK TABLES;

DROP TABLE IF EXISTS `member_team`;
CREATE TABLE `member_team` (
  `member_id` int NOT NULL,
  `team_id` int NOT NULL,
  PRIMARY KEY (`member_id`,`team_id`),
  KEY `FKfly863tmgmm8wnj0u1sqgqu5u` (`team_id`),
  CONSTRAINT `FK59om7jbawj6obneunne8mj6ql` FOREIGN KEY (`member_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKfly863tmgmm8wnj0u1sqgqu5u` FOREIGN KEY (`team_id`) REFERENCES `team` (`team_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `member_team` WRITE;
INSERT INTO `member_team` VALUES (1,1),(3,1),(1,2),(2,2),(3,3),(2,4),(3,4),(1,5),(3,5),(5,6),(6,6),(7,6),(8,6),(5,7),(7,7),(7,8),(8,9),(1,10),(4,10),(5,10),(6,10),(7,10),(2,11),(3,11),(8,11);
UNLOCK TABLES;

DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `message_id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `conversation_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`message_id`),
  KEY `FK6yskk3hxw5sklwgi25y6d5u1l` (`conversation_id`),
  KEY `FKb3y6etti1cfougkdr0qiiemgv` (`user_id`),
  CONSTRAINT `FK6yskk3hxw5sklwgi25y6d5u1l` FOREIGN KEY (`conversation_id`) REFERENCES `conversation` (`conversation_id`),
  CONSTRAINT `FKb3y6etti1cfougkdr0qiiemgv` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `message` WRITE;
INSERT INTO `message` VALUES (2,'2022-06-04 13:34:26.268168','2022-06-04 13:34:26.268168','It is awesome application!','',2,2),(3,'2022-06-04 13:35:53.378519','2022-06-04 13:35:53.378519','That was great display, user4.','',3,2),(4,'2022-06-04 13:36:14.130538','2022-06-04 13:36:14.130538','Could you work on friday?','',4,2),(5,'2022-06-04 13:38:37.949564','2022-06-04 13:38:37.949564','Can you have a meeting with ceo3 on friday?','',5,2);
UNLOCK TABLES;

DROP TABLE IF EXISTS `team`;
CREATE TABLE `team` (
  `team_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `company_id` int DEFAULT NULL,
  PRIMARY KEY (`team_id`),
  KEY `FKq7becd703i1w0ry1jxy15qxid` (`company_id`),
  CONSTRAINT `FKq7becd703i1w0ry1jxy15qxid` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `team` WRITE;
INSERT INTO `team` VALUES (1,'MarketingTeam1',2),(2,'Collaborate Team',2),(3,'marketing',3),(4,'Produce',4),(5,'producer',5),(6,'Development',6),(7,'collaborate',7),(8,'Rap',8),(9,'R&B',8),(10,'Photographer',1),(11,'team',9);
UNLOCK TABLES;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `image_name` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `name` varchar(40) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `user` WRITE;
INSERT INTO `user` VALUES (1,'This is user1.','user1@example.com',NULL,'/profileUploads/1.jpeg','user1','$2a$10$DW6x8iHocUvtPutJB0elYOx4FNyRuRnt7E8FfXgP4c4sMK7s3uoGO','01000000000','MEMBER'),(2,'This is CEO of Picshare','ceo1@example.com',NULL,'/profileUploads/2.jpeg','ceo1','$2a$10$iHgeUnVlBCav/M6AZfKfs.j1ZkbfDmDz.F9oarQ0c5OhH2nfoOVtm','01000000000','CEO'),(3,'This is Ceo of SocialMedia.','ceo2@example.com',NULL,'/profileUploads/3.jpeg','ceo2','$2a$10$Dz5w2wbaDejUU4g6gnu4N.flbHN2k0xvRmEahSNQevTzwkegvWJn.','0100000000','CEO'),(4,'This is user2.','user2@example.com',NULL,'/profileUploads/4.jpeg','user2','$2a$10$bVIzEjBH3qvNS1Lybe0rzeROzS4P/wMJu.uT5J8igK.kHMH8pGNr.','01000000','MEMBER'),(5,'This is user3.','user3@example.com',NULL,'/profileUploads/5.jpeg','user3','$2a$10$7zuEuzGAQJ4BZmnTVQrhcesYq9chioHvA4vCOlr3mJH/G1JkZYW/u','010000000','MEMBER'),(6,'This is user4.','user4@example.com',NULL,'/profileUploads/6.jpeg','user4','$2a$10$U3QAHUbVXB9tXqeLAcZGfO8Foy0v/qoOctvLDoinSu5BwAG1co3ny','010000000','MEMBER'),(7,'This is user6.','user6@example.com',NULL,'/profileUploads/7.jpeg','user6','$2a$10$Hep36fpvNgAF2EP0B6ZvNu3GaWA3VmUV/w5xN.lm4kEiYjK2Z5bom','010000000','MEMBER'),(8,'This is CEO of withEmployee.','ceo3@example.com',NULL,'/profileUploads/8.jpeg','ceo3','$2a$10$OE4tO6lup6s9tIhb2xgm8OXleFYWhuD.EzWTih/ri9qyxPLqg917K','0100000000','CEO');
UNLOCK TABLES;
