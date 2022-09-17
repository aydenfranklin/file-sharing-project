DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `user_id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'user ID',
    `name` varchar(50) NOT NULL COMMENT 'user name',
    `email` varchar(20) NOT NULL COMMENT 'email',
    `password` varchar(100) NOT NULL COMMENT 'password',
    `salt` varchar(100) NOT NULL COMMENT 'salt',
    `description` varchar(100) NOT NULL COMMENT 'user description',
    `status` tinyint DEFAULT 1 COMMENT 'status 1=normal 0=disabled -1=deleted',
    `token_version` int unsigned DEFAULT 0 COMMENT 'token version',
    `last_login_time` datetime DEFAULT NULL COMMENT 'last login time',
    `created_time` datetime DEFAULT NULL COMMENT 'user created time',
    PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
