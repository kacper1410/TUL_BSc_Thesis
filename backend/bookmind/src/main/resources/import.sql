INSERT INTO bookmind_access_level (authority) VALUES ('ADMIN');
INSERT INTO bookmind_access_level (authority) VALUES ('MODERATOR');
INSERT INTO bookmind_access_level (authority) VALUES ('READER');

INSERT INTO bookmind_user (email, username, password) VALUES ('a@domain.com', 'admin', '$2a$10$AFMFchLCnBP3ai7tGfO0MOY9XoIhehJRV0mnx0QXn5Uz8j9U0l2y.');
INSERT INTO bookmind_user (email, username, password) VALUES ('m@domain.com', 'moderator', '$2a$10$AFMFchLCnBP3ai7tGfO0MOY9XoIhehJRV0mnx0QXn5Uz8j9U0l2y.');
INSERT INTO bookmind_user (email, username, password) VALUES ('r@domain.com', 'reader', '$2a$10$AFMFchLCnBP3ai7tGfO0MOY9XoIhehJRV0mnx0QXn5Uz8j9U0l2y.');

INSERT INTO bookmind_user_access_levels (user_id, access_levels_id) VALUES (1, 1);
INSERT INTO bookmind_user_access_levels (user_id, access_levels_id) VALUES (2, 2);
INSERT INTO bookmind_user_access_levels (user_id, access_levels_id) VALUES (3, 3);

INSERT INTO bookmind_book (title) VALUES ('Biblia');
INSERT INTO bookmind_book (title) VALUES ('Nad Niemnem');
INSERT INTO bookmind_book (title) VALUES ('Poppy war');
INSERT INTO bookmind_book (title) VALUES ('Chłopi');
