SET FOREIGN_KEY_CHECKS = 0;

-- admin account: admin / admin
-- user account: user / user
-- guest account: guest / guest
INSERT IGNORE INTO users (id, username, password, email, role) VALUES
(1, 'admin', '$2a$12$nR9OOi1pqo1UioO1tccA0ewRHQVe1oX/AeW9d/WRyImBr4WfAhJdK', 'admin@example.com', 'ADMIN'),
(2, 'user', '$2a$12$RiVK6vsf4p5qNnHqGCeV7uz8a1qSChYt5lunmsNf5lcZSg9g3rNNG', 'user@example.com', 'USER'),
(3, 'guest', '$2a$12$TTj3lwVs77hlYEsuwatTteijL7UsWT3cT55lBuEmNEkTZ4GJoSznC', 'guest@example.com', 'GUEST');


TRUNCATE TABLE categories;
INSERT IGNORE INTO categories (id, name, description) VALUES
(1, 'Adventure', 'Exciting tours for thrill-seekers'),
(2, 'Cultural', 'Tours that explore local culture and heritage'),
(3, 'Nature', 'Tours that immerse you in natural beauty'),
(4, 'Historical', 'Tours that take you through history'),
(5, 'Food & Drink', 'Tours that focus on culinary experiences');


TRUNCATE TABLE tours;
INSERT IGNORE INTO tours (id, category_id, title, description, price, rating, reviews_count, image_url) VALUES
(1, 1, 'Tour A', 'Description for Tour A', 100.00, 4.5, 10, 'https://picsum.photos/id/1/400/300'),
(2, 1, 'Tour B', 'Description for Tour B', 150.00, 4.0, 8, 'https://picsum.photos/id/2/400/300'),
(3, 2, 'Tour C', 'Description for Tour C', 200.00, 4.8, 15, 'https://picsum.photos/id/3/400/300'),
(4, 2, 'Tour D', 'Description for Tour D', 250.00, 4.2, 12, 'https://picsum.photos/id/4/400/300'),
(5, 3, 'Tour E', 'Description for Tour E', 300.00, 4.7, 20, 'https://picsum.photos/id/5/400/300'),
(6, 3, 'Tour F', 'Description for Tour F', 350.00, 4.3, 18, 'https://picsum.photos/id/6/400/300'),
(7, 4, 'Tour G', 'Description for Tour G', 400.00, 4.6, 22, 'https://picsum.photos/id/7/400/300'),
(8, 4, 'Tour H', 'Description for Tour H', 450.00, 4.1, 14, 'https://picsum.photos/id/8/400/300'),
(9, 5, 'Tour I', 'Description for Tour I', 500.00, 4.9, 25, 'https://picsum.photos/id/9/400/300'),
(10, 5, 'Tour J', 'Description for Tour J', 550.00, 4.4, 16, 'https://picsum.photos/id/10/400/300'),
(11, 1, 'Tour K', 'Description for Tour K', 600.00, 4.5, 19, 'https://picsum.photos/id/11/400/300'),
(12, 1, 'Tour L', 'Description for Tour L', 650.00, 4.2, 13, 'https://picsum.photos/id/12/400/300'),
(13, 2, 'Tour M', 'Description for Tour M', 700.00, 4.7, 21, 'https://picsum.photos/id/13/400/300'),
(14, 2, 'Tour N', 'Description for Tour N', 750.00, 4.3, 17, 'https://picsum.photos/id/14/400/300'),
(15, 3, 'Tour O', 'Description for Tour O', 800.00, 4.6, 23, 'https://picsum.photos/id/15/400/300'),
(16, 3, 'Tour P', 'Description for Tour P', 850.00, 4.4, 19, 'https://picsum.photos/id/16/400/300'),
(17, 4, 'Tour Q', 'Description for Tour Q', 900.00, 4.5, 20, 'https://picsum.photos/id/17/400/300'),
(18, 4, 'Tour R', 'Description for Tour R', 950.00, 4.2, 18, 'https://picsum.photos/id/18/400/300'),
(19, 5, 'Tour S', 'Description for Tour S', 1000.00, 4.8, 22, 'https://picsum.photos/id/19/400/300'),
(20, 5, 'Tour T', 'Description for Tour T', 1050.00, 4.3, 16, 'https://picsum.photos/id/20/400/300'),
(21, 1, 'Tour U', 'Description for Tour U', 1100.00, 4.6, 21, 'https://picsum.photos/id/21/400/300'),
(22, 1, 'Tour V', 'Description for Tour V', 1150.00, 4.2, 17, 'https://picsum.photos/id/22/400/300'),
(23, 2, 'Tour W', 'Description for Tour W', 1200.00, 4.7, 23, 'https://picsum.photos/id/23/400/300'),
(24, 2, 'Tour X', 'Description for Tour X', 1250.00, 4.3, 19, 'https://picsum.photos/id/24/400/300'),
(25, 3, 'Tour Y', 'Description for Tour Y', 1300.00, 4.6, 22, 'https://picsum.photos/id/25/400/300'),
(26, 3, 'Tour Z', 'Description for Tour Z', 1350.00, 4.4, 18, 'https://picsum.photos/id/26/400/300'),
(27, 4, 'Tour AA', 'Description for Tour AA', 1400.00, 4.5, 20, 'https://picsum.photos/id/27/400/300'),
(28, 4, 'Tour AB', 'Description for Tour AB', 1450.00, 4.2, 17, 'https://picsum.photos/id/28/400/300'),
(29, 5, 'Tour AC', 'Description for Tour AC', 1500.00, 4.8, 22, 'https://picsum.photos/id/29/400/300'),
(30, 5, 'Tour AD', 'Description for Tour AD', 1550.00, 4.3, 18, 'https://picsum.photos/id/30/400/300');

TRUNCATE TABLE user_reviews;
INSERT IGNORE INTO user_reviews (id, user_id, tour_id, rating, content) VALUES
(1, 2, 1, 5, 'Amazing tour! Highly recommend.'),
(2, 2, 2, 4, 'Great experience, but could be better.'),
(3, 2, 2, 5, 'Absolutely loved it!'),
(4, 2, 2, 3, 'It was okay, not the best.'),
(5, 2, 2, 4, 'Good value for the price.'),
(6, 2, 2, 5, 'Fantastic tour with great guides!'),
(7, 2, 2, 4, 'Enjoyed it but had some issues.'),
(8, 2, 2, 5, 'Best tour I have ever been on!'),
(9, 2, 2, 4, 'Very good experience overall.'),
(10, 2, 2, 5, 'Exceeded my expectations!'),
(11, 2, 2, 4, 'Great tour but a bit pricey.'),
(12, 2, 2, 5, 'Amazing guides and beautiful locations!'),
(13, 2, 2, 4, 'Had a wonderful time on this tour.'),
(14, 2, 2, 5, 'Highly recommend this tour to everyone!'),
(15, 2, 2, 4, 'Good tour but could use some improvements.'),
(16, 2, 2, 5, 'Fantastic experience with great service!'),
(17, 2, 2, 4, 'Enjoyed the tour but had some minor issues.'),
(18, 2, 2, 5, 'Best tour I have ever taken!'),
(19, 2, 2, 4, 'Very good experience overall.'),
(20, 2, 2, 5, 'Exceeded my expectations in every way!'),
(21, 17, 17, 4, 'Great tour but a bit expensive.'),
(22, 17, 17, 5, 'Amazing guides and stunning locations!'),
(23, 17, 17, 4, 'Had a fantastic time on this tour.'),
(24, 17, 17, 5, 'Highly recommend this tour to everyone!'),
(25, 17, 17, 4, 'Good tour but could use some improvements.'),
(26, 17, 17, 5, 'Fantastic experience with great service!'),
(27, 17, 17, 4, 'Enjoyed the tour but had some minor issues.'),
(28, 17, 17, 5, 'Best tour I have ever taken!'),
(29, 17, 17, 4, 'Very good experience overall.'),
(30, 17, 17, 5, 'Exceeded my expectations in every way!');

SET FOREIGN_KEY_CHECKS = 1;
