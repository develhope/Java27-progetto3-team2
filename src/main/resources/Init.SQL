CREATE DATABASE project3;

CREATE TABLE `courier` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `surname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKpqhnql5qw4w1j3fldfsugrt6m` (`email`),
  UNIQUE KEY `UKikmradq4my41hpuvs5mcel9i5` (`phone_number`)
);

CREATE TABLE `menu_item` (
  `availability` bit(1) DEFAULT NULL,
  `gluten_free_item` bit(1) DEFAULT NULL,
  `item_price` double NOT NULL,
  `lactose_free_item` bit(1) DEFAULT NULL,
  `vegetarian_item` bit(1) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `restaurant_menu` bigint NOT NULL,
  `category_food` varchar(255) DEFAULT NULL,
  `item_description` varchar(255) DEFAULT NULL,
  `item_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfonkqotjuljtw1n4l9j5nyja6` (`restaurant_menu`),
  CONSTRAINT `FKfonkqotjuljtw1n4l9j5nyja6` FOREIGN KEY (`restaurant_menu`) REFERENCES `restaurant_menu` (`id`)
);

CREATE TABLE `order_entity` (
  `total_price` double NOT NULL,
  `courier_id` bigint NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_date` datetime(6) NOT NULL,
  `delivery_address` varchar(255) NOT NULL,
  `payment_method` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `restaurant` (
  `review` double DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `menu_restaurant_id` bigint DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `name_restaurant` varchar(255) DEFAULT NULL,
  `opening_hours` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKswyogi2gbt67mwr1y6twxsmq7` (`menu_restaurant_id`),
  CONSTRAINT `FK3otvex56hkfx6a6qg2rd1sk33` FOREIGN KEY (`menu_restaurant_id`) REFERENCES `restaurant_menu` (`id`)
);

CREATE TABLE `restaurant_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `restaurant_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKfwtoj578gnywm5m5wlqxci4bw` (`restaurant_id`),
  CONSTRAINT `FKcf3h9qggpuiewy6h21s2j4e1h` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`)
);

CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);