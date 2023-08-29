CREATE TABLE `t_user`
(
    `id`          int(11)   NOT NULL auto_increment,
    `username`    VARCHAR(50)    DEFAULT NULL,
    `password`    VARCHAR(50)    default null,
    `salt`        VARCHAR(50)    DEFAULT NULL,
    `email`       VARCHAR(100)   DEFAULT NULL,
    `type`        int(11)        DEFAULT NULL COMMENT '0-普通用户；1-超级管理员；2-版主',
    `status`      int(11)        DEFAULT NULL COMMENT '0-未激活；1-已激活',
    `activation`  VARCHAR(100)   DEFAULT NULL,
    `header_url`  VARCHAR(200)   DEFAULT NULL,
    `create_time` timestamp NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `index_username` (`username`(20)),
    KEY `index_email` (`email`(20))
) ENGINE = INNODB
  AUTO_INCREMENT = 150
  DEFAULT CHARSET = utf8;


-- 测试数据
INSERT INTO `t_user` (`username`, `password`, `salt`, `email`, `type`, `status`, `activation`, `header_url`,
                      `create_time`)
VALUES ('pp', MD5('123123'), 'salt1', 'upp419@example.com', 0, 1, 'activation', 'header_url1', CURRENT_TIMESTAMP),
       ('user2', MD5('password2'), 'salt2', 'user2@example.com', 0, 1, 'activation2', 'header_url2', CURRENT_TIMESTAMP),
       ('user3', MD5('password3'), 'salt3', 'user3@example.com', 1, 0, 'activation3', 'header_url3', CURRENT_TIMESTAMP),
       ('user4', MD5('password4'), 'salt4', 'user4@example.com', 2, 1, 'activation4', 'header_url4', CURRENT_TIMESTAMP);

-- 帖子表
CREATE TABLE `t_discuss_post`
(
    `id`            int(11)   NOT NULL AUTO_INCREMENT,
    `user_id`       VARCHAR(45)    DEFAULT NULL,
    `title`         VARCHAR(100)   DEFAULT NULL,
    `content`       text,
    `type`          int(11)        DEFAULT NULL COMMENT '0-普通；1-置顶',
    `status`        int(11)        DEFAULT NULL COMMENT '0-正常；1-精华；2-拉黑',
    `create_time`   timestamp NULL DEFAULT NULL,
    `comment_count` int(11)        DEFAULT NULL,
    `score`         double         DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `index_user_id` (`user_id`)
) ENGINE = INNODB
  AUTO_INCREMENT = 281
  DEFAULT CHARSET = utf8;

INSERT INTO `t_discuss_post` (`user_id`, `title`, `content`, `type`, `status`, `create_time`, `comment_count`, `score`)
VALUES ('user1', 'Breaking News: Earthquake Hits City Center',
        'A strong earthquake measuring 7.5 on the Richter scale struck the city center today, causing widespread panic and damage.',
        0, 0, CURRENT_TIMESTAMP, 5, 4.5),
       ('user2', 'New Study Reveals Alarming Rise in Pollution Levels',
        'According to a recent study conducted by environmental experts, pollution levels in the city have reached dangerous levels, posing a serious health risk to residents.',
        1, 0, CURRENT_TIMESTAMP, 3, 3.8),
       ('user3', 'Local Sports Team Wins Championship Title',
        'In an exciting match that kept fans on the edge of their seats, the local sports team emerged victorious and clinched the championship title for the first time in their history.',
        0, 1, CURRENT_TIMESTAMP, 10, 4.2),
       ('user4', 'Breaking News: Political Scandal Rocks the Nation',
        'A major political scandal has erupted, involving high-ranking officials and allegations of corruption. The scandal has sent shockwaves through the nation and raised questions about the integrity of the government.',
        0, 2, CURRENT_TIMESTAMP, 7, 3.5),
       ('user5', 'New Technology Promises Breakthrough in Renewable Energy',
        'Scientists have developed a revolutionary new technology that harnesses solar energy more efficiently, offering hope for a greener and more sustainable future.',
        1, 0, CURRENT_TIMESTAMP, 8, 4.0),
       ('user6', 'Health Alert: Outbreak of Viral Infection Reported',
        'Health authorities have issued a warning following an outbreak of a highly contagious viral infection. The public is advised to take necessary precautions to prevent the spread of the disease.',
        0, 0, CURRENT_TIMESTAMP, 2, 3.2),
       ('user7', 'Business News: Company Announces Record Profits',
        'In a press release, a leading company has announced record-breaking profits for the fiscal year, surpassing market expectations and demonstrating strong growth.',
        1, 1, CURRENT_TIMESTAMP, 12, 4.7),
       ('user8', 'Entertainment Update: Blockbuster Movie Hits Theaters',
        'The highly anticipated blockbuster movie has finally hit theaters, drawing massive crowds and receiving rave reviews from both critics and audiences.',
        0, 0, CURRENT_TIMESTAMP, 6, 4.1),
       ('user9', 'Sports News: Athlete Breaks World Record',
        'In a stunning display of athletic prowess, an accomplished athlete has shattered a long-standing world record, cementing their place in sporting history.',
        1, 0, CURRENT_TIMESTAMP, 9, 4.3),
       ('user10', 'Technology Innovation: New Device Revolutionizes Industry',
        'A groundbreaking new device has been unveiled, promising to revolutionize the industry with its advanced features and unprecedented capabilities.',
        0, 1, CURRENT_TIMESTAMP, 4, 3.7);

-- 登录信息表
CREATE TABLE IF NOT EXISTS `login_ticket`
(
    `id`      int(11)     NOT NULL AUTO_INCREMENT,
    `user_id` int(11)     NOT NULL,
    `ticket`  varchar(45) NOT NULL,
    `status`  int(11) DEFAULT '0' COMMENT '0-有效；1-无效',
    `expired` timestamp   NOT NULL,
    PRIMARY KEY (`id`),
    KEY `index_ticket` (`ticket`(20))

) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;