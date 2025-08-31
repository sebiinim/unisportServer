-- ============================================
-- Unisport sample seed data (MySQL 8.0)
-- ============================================

-- (선택) 깨끗한 초기화를 원하면 주석 해제
-- SET FOREIGN_KEY_CHECKS = 0;
-- TRUNCATE TABLE attendance;
-- TRUNCATE TABLE reservation;
-- TRUNCATE TABLE lesson_like;
-- TRUNCATE TABLE review;
-- TRUNCATE TABLE subscription;
-- TRUNCATE TABLE lesson_schedule;
-- TRUNCATE TABLE lesson;
-- TRUNCATE TABLE users;
-- SET FOREIGN_KEY_CHECKS = 1;

-- 1) USERS
INSERT INTO users
(id, email, login_id, name, password, student_number, university,
 rating, review_count, is_instructor, created_at, updated_at)
VALUES
    (1, 'inst.alice@example.com', 'inst_alice', 'Alice Kim',
     '$2b$10$UNpxTcsMGLSFoWfvAADo8uWo0Qx6t5/w5toKTt.TkMETkrsS8tM6u', '20230001', 'Korea Univ',
     5, 2, b'1', '2025-08-31 05:00:00.000000', '2025-08-31 05:00:00.000000'),
    (2, 'inst.bob@example.com',   'inst_bob',   'Bob Lee',
     '$2b$10$UNpxTcsMGLSFoWfvAADo8uWo0Qx6t5/w5toKTt.TkMETkrsS8tM6u', '20230002', 'Korea Univ',
     4, 1, b'1', '2025-08-31 05:01:00.000000', '2025-08-31 05:01:00.000000'),
    (3, 'charlie@example.com',    'charlie',    'Charlie Park',
     '$2b$10$UNpxTcsMGLSFoWfvAADo8uWo0Qx6t5/w5toKTt.TkMETkrsS8tM6u', '202412345', 'Korea Univ',
     0, 0, b'0', '2025-08-31 05:02:00.000000', '2025-08-31 05:02:00.000000'),
    (4, 'dana@example.com',       'dana',       'Dana Choi',
     '$2b$10$UNpxTcsMGLSFoWfvAADo8uWo0Qx6t5/w5toKTt.TkMETkrsS8tM6u', '202412346', 'Korea Univ',
     0, 0, b'0', '2025-08-31 05:03:00.000000', '2025-08-31 05:03:00.000000'),
    (5, 'evan@example.com',       'evan',       'Evan Jung',
     '$2b$10$UNpxTcsMGLSFoWfvAADo8uWo0Qx6t5/w5toKTt.TkMETkrsS8tM6u', '202412347', 'Korea Univ',
     0, 0, b'0', '2025-08-31 05:04:00.000000', '2025-08-31 05:04:00.000000');

-- 2) LESSON (instructor_user_id는 논리적 참조; FK는 없지만 users.id를 맞춰 둠)
INSERT INTO lesson
(id, sport, title, description, level, location, capacity, reserved_count,
 reservation_status, image_path, is_every_week, instructor_user_id,
 day_of_the_week, lesson_date, lesson_time)
VALUES
    (10, '축구', '초급 축구', '기초 패스/드리블', 1, '화정체육관', 10, 3,
     'AVAILABLE', '/img/lesson10.jpg', b'1', 1,
     'TUESDAY', NULL, '14:00:00.000000'),

    (11, '요가', '중급 요가', '밸런스 & 코어', 2, 'KU 피트니스', 8, 2,
     'AVAILABLE', '/img/lesson11.jpg', b'1', 2,
     'THURSDAY', NULL, '18:00:00.000000'),

    (12, '농구', '원데이 농구 클리닉', '슛 폼 교정', 1, '체육관 A', 5, 1,
     'AVAILABLE', '/img/lesson12.jpg', b'0', 2,
     'WEDNESDAY', '2025-09-03', '16:00:00.000000');

-- 3) LESSON_SCHEDULE (각 lesson에 1~2개 세션)
INSERT INTO lesson_schedule
(id, lesson_id, date, start_time, end_time, reserved_count,
 capacity_override, location_override, day_of_week)
VALUES
    -- L10 (축구) 화요일 세션 2개
    (100, 10, '2025-09-02', '14:00:00.000000', '15:30:00.000000', 2, NULL, NULL, 'TUESDAY'),
    (101, 10, '2025-09-09', '14:00:00.000000', '15:30:00.000000', 1, NULL, NULL, 'TUESDAY'),

    -- L11 (요가) 목요일 세션 2개
    (102, 11, '2025-09-04', '18:00:00.000000', '19:00:00.000000', 2, NULL, NULL, 'THURSDAY'),
    (103, 11, '2025-09-11', '18:00:00.000000', '19:00:00.000000', 0, NULL, NULL, 'THURSDAY'),

    -- L12 (농구) 원데이
    (104, 12, '2025-09-03', '16:00:00.000000', '17:30:00.000000', 1, NULL, NULL, 'WEDNESDAY');

-- 4) ATTENDANCE (유일키: (lesson_id, user_id) 주의)
INSERT INTO attendance
(id, user_id, lesson_id, lesson_schedule_id, is_attended, created_at, updated_at)
VALUES
    -- L10
    (200, 3, 10, 100, b'0', '2025-08-31 05:10:00.000000', '2025-08-31 05:10:00.000000'),
    (201, 4, 10, 100, b'0', '2025-08-31 05:10:10.000000', '2025-08-31 05:10:10.000000'),
    (202, 5, 10, 101, b'0', '2025-08-31 05:10:20.000000', '2025-08-31 05:10:20.000000'),

    -- L11
    (203, 3, 11, 102, b'0', '2025-08-31 05:10:30.000000', '2025-08-31 05:10:30.000000'),
    (204, 5, 11, 102, b'0', '2025-08-31 05:10:40.000000', '2025-08-31 05:10:40.000000'),

    -- L12
    (205, 4, 12, 104, b'0', '2025-08-31 05:10:50.000000', '2025-08-31 05:10:50.000000');

-- 5) RESERVATION (유일키: (user_id, lesson_id) 주의)
-- attendance_id를 FK로 걸었다면 아래와 같이 참조; 아니면 NULL 허용
INSERT INTO reservation
(id, user_id, lesson_id, lesson_schedule_id, attendance_id, created_at, updated_at)
VALUES
    -- L10
    (300, 3, 10, 100, 200, '2025-08-31 05:20:00.000000', '2025-08-31 05:20:00.000000'),
    (301, 4, 10, 100, 201, '2025-08-31 05:20:10.000000', '2025-08-31 05:20:10.000000'),
    (302, 5, 10, 101, 202, '2025-08-31 05:20:20.000000', '2025-08-31 05:20:20.000000'),

    -- L11
    (303, 3, 11, 102, 203, '2025-08-31 05:20:30.000000', '2025-08-31 05:20:30.000000'),
    (304, 5, 11, 102, 204, '2025-08-31 05:20:40.000000', '2025-08-31 05:20:40.000000'),

    -- L12
    (305, 4, 12, 104, 205, '2025-08-31 05:20:50.000000', '2025-08-31 05:20:50.000000');

-- 6) REVIEW (수강생 일부가 후기 남김)
INSERT INTO review
(id, user_id, lesson_id, rating, review_content, created_at)
VALUES
    (500, 3, 10, 5, '처음 배우기 딱 좋아요!', '2025-08-31 05:30:00.000000'),
    (501, 5, 11, 4, '코어 자극 제대로 됨',   '2025-08-31 05:30:10.000000');

-- 7) LESSON_LIKE (유일키: (user_id, lesson_id))
INSERT INTO lesson_like
(id, user_id, lesson_id, created_at)
VALUES
    (400, 3, 10, '2025-08-31 05:40:00.000000'),
    (401, 4, 11, '2025-08-31 05:40:10.000000'),
    (402, 5, 10, '2025-08-31 05:40:20.000000');

-- 8) SUBSCRIPTION
INSERT INTO subscription
(id, user_id, start_date, end_date, status)
VALUES
    (600, 3, '2025-08-01', '2025-09-30', 'ACTIVE'),
    (601, 5, '2025-07-15', '2025-08-31', 'EXPIRED');
