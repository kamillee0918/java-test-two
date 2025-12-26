-- 모든 데이터베이스 조회
SHOW DATABASES;

-- 특정 데이터베이스 사용 선언(예: test)
USE test;

-- 해당 데이터베이스의 모든 테이블 조회
SHOW TABLES;

-- 시험문제의 정답을 저장하는 테이블
CREATE TABLE answer
(
    question_num   INT NOT NULL PRIMARY KEY COMMENT '문제번호',
    correct_answer INT NOT NULL COMMENT '정답 (1~4)'
) COMMENT '시험 정답 테이블';

-- 시험에 응시한 학생이 입력한 개인정보와 답안을 저장할 테이블
CREATE TABLE student_exam
(
    exam_id   INT          NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '응시정보 일련번호',
    name      VARCHAR(50)  NOT NULL COMMENT '이름',
    email     VARCHAR(100) NOT NULL UNIQUE COMMENT '이메일 (중복 불가)',
    password  VARCHAR(100) NOT NULL COMMENT '비밀번호',
    answer1   INT          NOT NULL COMMENT '1번 문제 답안',
    answer2   INT          NOT NULL COMMENT '2번 문제 답안',
    answer3   INT          NOT NULL COMMENT '3번 문제 답안',
    answer4   VARCHAR(50)  NOT NULL COMMENT '4번 문제 답안 (주관식)',
    answer5   VARCHAR(50)  NOT NULL COMMENT '5번 문제 답안 (주관식)',
    score     INT          NOT NULL COMMENT '점수 (100점 만점)',
    exam_date DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '응시일시'
) COMMENT '학생 응시정보 테이블';

-- 해당 테이블이 정상적으로 작성되었는지를 확인
SHOW TABLES;

-- 정답 테이블에 저장할 정답데이터 입력 (객관식 1~3번만)
-- 1번: equals() = 2번 선택지
-- 2번: 매개변수 목록이 다르면 = 3번 선택지
-- 3번: arr.length = 1번 선택지
INSERT INTO answer (question_num, correct_answer)
VALUES (1, 2),
       (2, 3),
       (3, 1);

-- 학생 응시정보 테이블에 저장할 테스트용 데이터 5개 입력
-- 정답: 1번=2(equals), 2번=3, 3번=1(arr.length), 4번=extends, 5번=try
INSERT INTO student_exam (name, email, password, answer1, answer2, answer3, answer4, answer5, score, exam_date)
VALUES ('홍길동', 'hong@test.com', '1234', 2, 3, 1, 'extends', 'try', 100, '2025-12-20 09:00:00'),
       ('김철수', 'kim@test.com', '1234', 2, 3, 1, 'extends', 'catch', 80, '2025-12-21 10:30:00'),
       ('이영희', 'lee@test.com', '1234', 2, 1, 1, 'implements', 'try', 60, '2025-12-22 14:00:00'),
       ('박민수', 'park@test.com', '1234', 1, 3, 2, 'extends', 'try', 60, '2025-12-23 11:00:00'),
       ('최지은', 'choi@test.com', '1234', 2, 3, 1, 'extend', 'try', 80, '2025-12-24 16:30:00');

-- 데이터 확인
SELECT *
FROM answer;
SELECT *
FROM student_exam;
