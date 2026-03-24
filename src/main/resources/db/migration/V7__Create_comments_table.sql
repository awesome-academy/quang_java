CREATE TABLE IF NOT EXISTS comments (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  review_id BIGINT NOT NULL,
  parent_comment_id BIGINT,
  content TEXT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (review_id) REFERENCES user_reviews(id),
  FOREIGN KEY (parent_comment_id) REFERENCES comments(id)
);
