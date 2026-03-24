-- admin account: admin / admin
-- user account: user / user
-- guest account: guest / guest
INSERT IGNORE INTO users (id, username, password, email, role) VALUES
(1, 'admin', '$2a$12$nR9OOi1pqo1UioO1tccA0ewRHQVe1oX/AeW9d/WRyImBr4WfAhJdK', 'admin@example.com', 'ADMIN'),
(2, 'user', '$2a$12$RiVK6vsf4p5qNnHqGCeV7uz8a1qSChYt5lunmsNf5lcZSg9g3rNNG', 'user@example.com', 'USER'),
(3, 'guest', '$2a$12$TTj3lwVs77hlYEsuwatTteijL7UsWT3cT55lBuEmNEkTZ4GJoSznC', 'guest@example.com', 'GUEST');

INSERT IGNORE INTO categories (id, name, description) VALUES
(1, 'Adventure', 'Exciting tours for thrill-seekers'),
(2, 'Cultural', 'Tours that explore local culture and heritage'),
(3, 'Nature', 'Tours that immerse you in natural beauty'),
(4, 'Historical', 'Tours that take you through history'),
(5, 'Food & Drink', 'Tours that focus on culinary experiences');


INSERT IGNORE INTO tours (id, category_id, title, description, price, rating, reviews_count) VALUES
(1, 1, 'Tour A', 'Description for Tour A', 100.00, 4.5, 10),
(2, 1, 'Tour B', 'Description for Tour B', 150.00, 4.0, 8),
(3, 2, 'Tour C', 'Description for Tour C', 200.00, 4.8, 15),
(4, 2, 'Tour D', 'Description for Tour D', 250.00, 4.2, 12),
(5, 3, 'Tour E', 'Description for Tour E', 300.00, 4.7, 20),
(6, 3, 'Tour F', 'Description for Tour F', 350.00, 4.3, 18),
(7, 4, 'Tour G', 'Description for Tour G', 400.00, 4.6, 22),
(8, 4, 'Tour H', 'Description for Tour H', 450.00, 4.1, 14),
(9, 5, 'Tour I', 'Description for Tour I', 500.00, 4.9, 25),
(10, 5, 'Tour J', 'Description for Tour J', 550.00, 4.4, 16),
(11, 1, 'Tour K', 'Description for Tour K', 600.00, 4.5, 19),
(12, 1, 'Tour L', 'Description for Tour L', 650.00, 4.2, 13),
(13, 2, 'Tour M', 'Description for Tour M', 700.00, 4.7, 21),
(14, 2, 'Tour N', 'Description for Tour N', 750.00, 4.3, 17),
(15, 3, 'Tour O', 'Description for Tour O', 800.00, 4.6, 23),
(16, 3, 'Tour P', 'Description for Tour P', 850.00, 4.4, 19),
(17, 4, 'Tour Q', 'Description for Tour Q', 900.00, 4.5, 20),
(18, 4, 'Tour R', 'Description for Tour R', 950.00, 4.2, 18),
(19, 5, 'Tour S', 'Description for Tour S', 1000.00, 4.8, 22),
(20, 5, 'Tour T', 'Description for Tour T', 1050.00, 4.3, 16),
(21, 1, 'Tour U', 'Description for Tour U', 1100.00, 4.6, 21),
(22, 1, 'Tour V', 'Description for Tour V', 1150.00, 4.2, 17),
(23, 2, 'Tour W', 'Description for Tour W', 1200.00, 4.7, 23),
(24, 2, 'Tour X', 'Description for Tour X', 1250.00, 4.3, 19),
(25, 3, 'Tour Y', 'Description for Tour Y', 1300.00, 4.6, 22),
(26, 3, 'Tour Z', 'Description for Tour Z', 1350.00, 4.4, 18),
(27, 4, 'Tour AA', 'Description for Tour AA', 1400.00, 4.5, 20),
(28, 4, 'Tour AB', 'Description for Tour AB', 1450.00, 4.2, 17),
(29, 5, 'Tour AC', 'Description for Tour AC', 1500.00, 4.8, 22),
(30, 5, 'Tour AD', 'Description for Tour AD', 1550.00, 4.3, 16);
