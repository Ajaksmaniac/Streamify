INSERT IGNORE INTO roles (role_id , name) VALUES (1, 'user'),  (2, 'content_creator'), (3, 'admin');
INSERT IGNORE INTO user (user_id, is_active, password, username, role_id)
VALUES (1, 1, '$2a$10$/Ewh3D0jDhgtzJvNHUqB6OPjeSxkyqOmaOFkD2Q9GyQAtPeThfJfG', 'testcafeUser', 2);
