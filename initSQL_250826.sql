-- create tables
-- 1. users 테이블
CREATE TABLE users (
                       id BIGINT NOT NULL AUTO_INCREMENT,
                       login_id VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       name VARCHAR(255),
                       email VARCHAR(255),
                       university VARCHAR(255),
                       student_number VARCHAR(255),
                       is_instructor BOOLEAN,
                       rating INT,
                       review_count INT,
                       created_at DATETIME,
                       updated_at DATETIME,
                       PRIMARY KEY (id)
);

-- 2. lesson 테이블
CREATE TABLE lesson (
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        sport VARCHAR(255),
                        title VARCHAR(255),
                        description TEXT,
                        level INT,
                        location VARCHAR(255),
                        reserved_count INT,
                        capacity INT,
                        lesson_date DATE,
                        PRIMARY KEY (id)
);

-- 3. lesson_like 테이블
CREATE TABLE lesson_like (
                             id BIGINT NOT NULL AUTO_INCREMENT,
                             user_id BIGINT,
                             lesson_id BIGINT,
                             created_at DATETIME,
                             PRIMARY KEY (id),
                             CONSTRAINT uk_lesson_like_user_lesson UNIQUE (user_id, lesson_id),
                             KEY idx_lesson_like_leeon (lesson_id),
                             KEY idx_lesson_like_user (user_id),
                             CONSTRAINT fk_lesson_like_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                             CONSTRAINT fk_lesson_like_lesson FOREIGN KEY (lesson_id) REFERENCES lesson(id) ON DELETE CASCADE
);

-- 4. lesson_schedule 테이블
CREATE TABLE lesson_schedule (
                                 id BIGINT NOT NULL AUTO_INCREMENT,
                                 lesson_id BIGINT,
                                 date DATE,
                                 start_time TIME,
                                 end_time TIME,
                                 PRIMARY KEY (id),
                                 CONSTRAINT fk_schedule_lesson FOREIGN KEY (lesson_id) REFERENCES lesson(id) ON DELETE CASCADE
);

-- 5. reservation 테이블
CREATE TABLE reservation (
                             id BIGINT NOT NULL AUTO_INCREMENT,
                             user_id BIGINT,
                             lesson_id BIGINT,
                             created_at DATETIME,
                             PRIMARY KEY (id),
                             CONSTRAINT uk_reservation_user_lesson UNIQUE (user_id, lesson_id),
                             CONSTRAINT fk_reservation_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                             CONSTRAINT fk_reservation_lesson FOREIGN KEY (lesson_id) REFERENCES lesson(id) ON DELETE CASCADE
);

-- 6. review 테이블
CREATE TABLE review (
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        lesson_id BIGINT,
                        user_id BIGINT,
                        rating INT,
                        review_content TEXT,
                        created_at DATETIME,
                        PRIMARY KEY (id),
                        CONSTRAINT uk_review_lesson_student UNIQUE (lesson_id, user_id),
                        CONSTRAINT fk_review_lesson FOREIGN KEY (lesson_id) REFERENCES lesson(id) ON DELETE CASCADE,
                        CONSTRAINT fk_review_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 7. subscription 테이블
CREATE TABLE subscription (
                              id BIGINT NOT NULL AUTO_INCREMENT,
                              user_id BIGINT,
                              start_date DATE,
                              end_date DATE,
                              status VARCHAR(255),
                              PRIMARY KEY (id),
                              CONSTRAINT fk_subscription_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);


-- add values
-- Users
INSERT INTO users (id, login_id, password, name, email, university, student_number, is_instructor, rating, review_count, created_at, updated_at)
VALUES
    (1, 'john123', 'pw123', 'John Doe', 'john@example.com', 'Korea University', '2024001', false, 5, 2, NOW(), NOW()),
    (2, 'jane456', 'pw456', 'Jane Smith', 'jane@example.com', 'Korea University', '2024002', true, 4, 3, NOW(), NOW()),
    (3, 'mike789', 'pw789', 'Mike Lee', 'mike@example.com', 'Yonsei University', '2024003', true, 0, 0, NOW(), NOW()),
    (4, 'lucy234', 'pw234', 'Lucy Kim', 'lucy@example.com', 'Seoul National University', '2024004', false, 0, 0, NOW(), NOW());

-- Lessons
INSERT INTO lesson (id, sport, title, description, level, location, reserved_count, capacity, reservation_status, instructor_user_id, lesson_date, lesson_time)
VALUES
    (1, '축구', '중급 축구', '기초 전술 및 체력 훈련', 2, '서울 캠퍼스 운동장', 5, 10, 'AVAILABLE', 2, '2025-09-01', '15:00:00'),
    (2, '농구', '초급 농구', '드리블 및 패스 기초', 1, '고려대 체육관', 10, 10, 'FULL', 3, '2025-09-02', '18:00:00'),
    (3, '요가', '힐링 요가', '호흡과 스트레칭', 1, '강남 요가센터', 3, 12, 'AVAILABLE', 3, '2025-09-03', '10:00:00');

-- Reservations
INSERT INTO reservation (id, user_id, lesson_id, created_at)
VALUES
    (1, 1, 1, NOW()),
    (2, 4, 1, NOW()),
    (3, 1, 2, NOW());

-- Lesson Likes
INSERT INTO lesson_like (id, user_id, lesson_id, created_at)
VALUES
    (1, 1, 1, NOW()),
    (2, 4, 1, NOW()),
    (3, 1, 2, NOW());

-- Reviews
INSERT INTO review (id, lesson_id, user_id, rating, review_content, created_at)
VALUES
    (1, 1, 1, 5, '정말 유익한 수업이었어요!', NOW()),
    (2, 1, 4, 4, '강사님이 친절했어요.', NOW()),
    (3, 2, 1, 3, '기초 배우기엔 괜찮아요.', NOW());

-- Subscriptions
INSERT INTO subscription (id, user_id, start_date, end_date, status)
VALUES
    (1, 1, '2025-09-01', '2025-09-30', 'ACTIVE'),
    (2, 4, '2025-09-01', '2025-09-15', 'EXPIRED');


-- 추가 세팅

INSERT INTO users (
    id, email, login_id, name, rating, review_count,
    student_number, university, password, is_instructor, updated_at
) VALUES
      (6,  'inst6@gmail.com',  'inst6',  '강사6',  0, 0, '2023320006', '고려대학교', '$2b$10$UNpxTcsMGLSFoWfvAADo8uWo0Qx6t5/w5toKTt.TkMETkrsS8tM6u', 1, NOW()),
      (7,  'inst7@gmail.com',  'inst7',  '강사7',  0, 0, '2023320007', '연세대학교', '$2b$10$UNpxTcsMGLSFoWfvAADo8uWo0Qx6t5/w5toKTt.TkMETkrsS8tM6u', 1, NOW()),
      (8,  'inst8@gmail.com',  'inst8',  '강사8',  0, 0, '2023320008', '서강대학교', '$2b$10$UNpxTcsMGLSFoWfvAADo8uWo0Qx6t5/w5toKTt.TkMETkrsS8tM6u', 1, NOW()),
      (9,  'inst9@gmail.com',  'inst9',  '강사9',  0, 0, '2023320009', '성균관대학교', '$2b$10$UNpxTcsMGLSFoWfvAADo8uWo0Qx6t5/w5toKTt.TkMETkrsS8tM6u', 1, NOW()),
      (10, 'inst10@gmail.com', 'inst10', '강사10', 0, 0, '2023320010', '한양대학교', '$2b$10$UNpxTcsMGLSFoWfvAADo8uWo0Qx6t5/w5toKTt.TkMETkrsS8tM6u', 1, NOW()),
      (11, 'inst11@gmail.com', 'inst11', '강사11', 0, 0, '2023320011', '중앙대학교', '$2b$10$UNpxTcsMGLSFoWfvAADo8uWo0Qx6t5/w5toKTt.TkMETkrsS8tM6u', 1, NOW()),
      (12, 'inst12@gmail.com', 'inst12', '강사12', 0, 0, '2023320012', '경희대학교', '$2b$10$UNpxTcsMGLSFoWfvAADo8uWo0Qx6t5/w5toKTt.TkMETkrsS8tM6u', 1, NOW()),
      (13, 'inst13@gmail.com', 'inst13', '강사13', 0, 0, '2023320013', '건국대학교', '$2b$10$UNpxTcsMGLSFoWfvAADo8uWo0Qx6t5/w5toKTt.TkMETkrsS8tM6u', 1, NOW()),
      (14, 'inst14@gmail.com', 'inst14', '강사14', 0, 0, '2023320014', '홍익대학교', '$2b$10$UNpxTcsMGLSFoWfvAADo8uWo0Qx6t5/w5toKTt.TkMETkrsS8tM6u', 1, NOW()),
      (15, 'inst15@gmail.com', 'inst15', '강사15', 0, 0, '2023320015', '동국대학교', '$2b$10$UNpxTcsMGLSFoWfvAADo8uWo0Qx6t5/w5toKTt.TkMETkrsS8tM6u', 1, NOW()),
      (16, 'inst16@gmail.com', 'inst16', '강사16', 0, 0, '2023320016', '서울대학교', '$2b$10$UNpxTcsMGLSFoWfvAADo8uWo0Qx6t5/w5toKTt.TkMETkrsS8tM6u', 1, NOW()),
      (17, 'inst17@gmail.com', 'inst17', '강사17', 0, 0, '2023320017', 'KAIST', '$2b$10$UNpxTcsMGLSFoWfvAADo8uWo0Qx6t5/w5toKTt.TkMETkrsS8tM6u', 1, NOW()),
      (18, 'inst18@gmail.com', 'inst18', '강사18', 0, 0, '2023320018', '포항공대', '$2b$10$UNpxTcsMGLSFoWfvAADo8uWo0Qx6t5/w5toKTt.TkMETkrsS8tM6u', 1, NOW()),
      (19, 'inst19@gmail.com', 'inst19', '강사19', 0, 0, '2023320019', '부산대학교', '$2b$10$UNpxTcsMGLSFoWfvAADo8uWo0Qx6t5/w5toKTt.TkMETkrsS8tM6u', 1, NOW()),
      (20, 'inst20@gmail.com', 'inst20', '강사20', 0, 0, '2023320020', '이화여자대학교', '$2b$10$UNpxTcsMGLSFoWfvAADo8uWo0Qx6t5/w5toKTt.TkMETkrsS8tM6u', 1, NOW()),
      (21, 'inst21@gmail.com', 'inst21', '강사21', 0, 0, '2023320021', '숙명여자대학교', '$2b$10$UNpxTcsMGLSFoWfvAADo8uWo0Qx6t5/w5toKTt.TkMETkrsS8tM6u', 1, NOW()),
      (22, 'inst22@gmail.com', 'inst22', '강사22', 0, 0, '2023320022', '세종대학교', '$2b$10$UNpxTcsMGLSFoWfvAADo8uWo0Qx6t5/w5toKTt.TkMETkrsS8tM6u', 1, NOW()),
      (23, 'inst23@gmail.com', 'inst23', '강사23', 0, 0, '2023320023', '울산대학교', '$2b$10$UNpxTcsMGLSFoWfvAADo8uWo0Qx6t5/w5toKTt.TkMETkrsS8tM6u', 1, NOW()),
      (24, 'inst24@gmail.com', 'inst24', '강사24', 0, 0, '2023320024', '전북대학교', '$2b$10$UNpxTcsMGLSFoWfvAADo8uWo0Qx6t5/w5toKTt.TkMETkrsS8tM6u', 1, NOW()),
      (25, 'inst25@gmail.com', 'inst25', '강사25', 0, 0, '2023320025', '전남대학교', '$2b$10$UNpxTcsMGLSFoWfvAADo8uWo0Qx6t5/w5toKTt.TkMETkrsS8tM6u', 1, NOW());


INSERT INTO lesson (
    id, sport, title, description, level, location,
    capacity, reserved_count, reservation_status,
    instructor_user_id, lesson_date, lesson_time
) VALUES
      (6,  '축구',     '중급 축구',     '기초 체력과 팀 전술을 다지는 수업.',           3, '고려대 화정체육관', 16, 0, 'AVAILABLE', 6,  '2025-08-30', '10:00:00'),
      (7,  '농구',     '초급 농구',     '드리블/패스/레이업 기본기 집중.',               1, '성북구민체육센터',   12, 0, 'AVAILABLE', 7,  '2025-08-30', '13:00:00'),
      (8,  '테니스',   '중급 테니스',   '포핸드/백핸드 타점과 풋워크 교정.',             3, '홍릉코트',          8,  0, 'AVAILABLE', 8,  '2025-08-30', '16:00:00'),
      (9,  '요가',     '힐링 요가',     '호흡과 스트레칭으로 유연성/회복 강화.',         2, '안암동 스튜디오',   15, 0, 'AVAILABLE', 9,  '2025-08-30', '19:00:00'),
      (10, '필라테스', '기구 필라테스', '코어 안정성과 자세 교정 중심.',                 2, '종암 필라테스',     10, 0, 'AVAILABLE', 10, '2025-08-31', '10:00:00'),
      (11, '수영',     '중급 수영',     '자유형 효율/턴 연습 및 지구력 향상.',           3, '동대문구 수영장',   20, 0, 'AVAILABLE', 11, '2025-08-31', '13:00:00'),
      (12, '배드민턴', '더블스 전략',   '로테이션/넷플레이 집중 트레이닝.',              4, '청량리 체육관',     14, 0, 'AVAILABLE', 12, '2025-08-31', '16:00:00'),
      (13, '런닝',     '10K 준비반',    '페이스 조절과 보강운동, 부상 예방.',            2, '청계천 러닝코스',   25, 0, 'AVAILABLE', 13, '2025-08-31', '19:00:00'),
      (14, '클라이밍', '볼더링 중급',   '문제 읽기/무브 연계/그립 교정.',                3, '안암 볼더링짐',     10, 0, 'AVAILABLE', 14, '2025-09-01', '10:00:00'),
      (15, '복싱',     '복싱 피트니스', '스텝/잽·원투 콤비네이션 및 유산소.',           2, '제기동 복싱짐',     18, 0, 'AVAILABLE', 15, '2025-09-01', '13:00:00'),
      (16, '축구',     '고급 축구',     '빌드업/전술 전개 및 포지셔닝 디테일.',          5, '월곡 인조잔디구장', 18, 0, 'AVAILABLE', 16, '2025-09-01', '16:00:00'),
      (17, '농구',     '슈팅 클리닉',   '캐치앤슛/풀업/코너 3점 루틴 구축.',             4, '성북구민체육센터',   12, 0, 'AVAILABLE', 17, '2025-09-01', '19:00:00'),
      (18, '테니스',   '서브 집중반',   '토스 일관성/킥/슬라이스 서브 메커닉.',          4, '홍릉코트',          8,  0, 'AVAILABLE', 18, '2025-09-02', '10:00:00'),
      (19, '요가',     '파워 요가',     '근지구력과 밸런스 향상 시퀀스.',                3, '안암동 스튜디오',   15, 0, 'AVAILABLE', 19, '2025-09-02', '13:00:00'),
      (20, '필라테스', '매트 필라테스', '초급 매트 시퀀스로 코어·호흡 패턴 습득.',       1, '종암 필라테스',     10, 0, 'AVAILABLE', 20, '2025-09-02', '16:00:00'),
      (21, '수영',     '자유형 교정',   '스트로크 효율과 킥 타이밍 교정.',               2, '동대문구 수영장',   20, 0, 'AVAILABLE', 21, '2025-09-03', '10:00:00'),
      (22, '배드민턴', '스매시 강의',   '스윙 궤적/하체 체중이동/리커버리.',              3, '청량리 체육관',     14, 0, 'AVAILABLE', 22, '2025-09-03', '13:00:00'),
      (23, '클라이밍', '탑로프 입문',   '확보/매듭/하강 안전교육과 기본 무브.',           1, '안암 볼더링짐',     10, 0, 'AVAILABLE', 23, '2025-09-04', '10:00:00'),
      (24, '복싱',     '디펜스 집중',   '슬립/더킹/위빙 및 카운터 타이밍.',              4, '제기동 복싱짐',     18, 0, 'AVAILABLE', 24, '2025-09-04', '13:00:00'),
      (25, '런닝',     '인터벌 트레이닝','400m 인터벌과 회복 조절, 주법 교정.',           3, '청계천 러닝코스',   25, 0, 'AVAILABLE', 25, '2025-09-05', '19:00:00');
