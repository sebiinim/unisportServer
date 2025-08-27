
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
INSERT INTO subscriptionentity (id, user_id, start_date, end_date, status)
VALUES
    (1, 1, '2025-09-01', '2025-09-30', 'ACTIVE'),
    (2, 4, '2025-09-01', '2025-09-15', 'EXPIRED');
