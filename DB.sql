# DB 생성
DROP DATABASE IF EXISTS `JSPAM`;
CREATE DATABASE `JSPAM`;
USE `JSPAM`;

# article 테이블 생성
CREATE TABLE article(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    title CHAR(100) NOT NULL,
    `body` TEXT NOT NULL
);

# member 테이블 생성
CREATE TABLE `member`(
    id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    loginId CHAR(100) NOT NULL,
    loginPw CHAR(200) NOT NULL,
    `name` CHAR(100) NOT NULL
    );

# article 테스트데이터 생성
INSERT INTO article
SET regDate = NOW(),
title = '제목 1',
`body` = '내용 1';

INSERT INTO article
SET regDate = NOW(),
title = '제목 2',
`body` = '내용 2';

INSERT INTO article
SET regDate = NOW(),
title = '제목 3',
`body` = '내용 3';

INSERT INTO article
SET regDate = NOW(),
title = '제목 4',
`body` = '내용 4';

# member 테스트 데이터 생성
INSERT INTO `member`
SET regDate = NOW(),
loginId = '아이디1',
loginPw = 'pw1',
`name` = '철수';

INSERT INTO `member`
SET regDate = NOW(),
loginId = '아이디2',
loginPw = 'pw2',
`name` = '영희';

INSERT INTO `member`
SET regDate = NOW(),
loginId = '아이디3',
loginPw = 'pw3',
`name` = '명수';

INSERT INTO `member`
SET regDate = NOW(),
loginId = '아이디4',
loginPw = 'pw4',
`name` = '재석';

SELECT * 
FROM article;

SELECT * 
FROM `member`;

######################################################################

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = CONCAT('TestId ',RAND()),
loginPw = CONCAT('TestPw ',RAND()),
`name` = CONCAT('TestName ',RAND());

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
title = CONCAT('TestTitle ',RAND()),
`body` = CONCAT('TestBody ',RAND());

