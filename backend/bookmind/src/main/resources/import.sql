INSERT INTO bookmind_access_level (authority) VALUES ('ROLE_ADMIN');
INSERT INTO bookmind_access_level (authority) VALUES ('ROLE_MODERATOR');
INSERT INTO bookmind_access_level (authority) VALUES ('ROLE_READER');

INSERT INTO bookmind_user (email, username, password, enabled) VALUES ('a@domain.com', 'admin', '$2a$10$AFMFchLCnBP3ai7tGfO0MOY9XoIhehJRV0mnx0QXn5Uz8j9U0l2y.', true);
INSERT INTO bookmind_user (email, username, password, enabled) VALUES ('m@domain.com', 'moderator', '$2a$10$AFMFchLCnBP3ai7tGfO0MOY9XoIhehJRV0mnx0QXn5Uz8j9U0l2y.', true);
INSERT INTO bookmind_user (email, username, password, enabled) VALUES ('r@domain.com', 'reader', '$2a$10$AFMFchLCnBP3ai7tGfO0MOY9XoIhehJRV0mnx0QXn5Uz8j9U0l2y.', true);
INSERT INTO bookmind_user (email, username, password, enabled) VALUES ('s@domain.com', 'superadmin', '$2a$10$AFMFchLCnBP3ai7tGfO0MOY9XoIhehJRV0mnx0QXn5Uz8j9U0l2y.', true);

INSERT INTO bookmind_user_authorities (user_id, authorities_id) VALUES (1, 1);
INSERT INTO bookmind_user_authorities (user_id, authorities_id) VALUES (2, 2);
INSERT INTO bookmind_user_authorities (user_id, authorities_id) VALUES (3, 3);
INSERT INTO bookmind_user_authorities (user_id, authorities_id) VALUES (4, 1);
INSERT INTO bookmind_user_authorities (user_id, authorities_id) VALUES (4, 2);
INSERT INTO bookmind_user_authorities (user_id, authorities_id) VALUES (4, 3);

INSERT INTO bookmind_book (title) VALUES ('Biblia');
INSERT INTO bookmind_book (title) VALUES ('Nad Niemnem');
INSERT INTO bookmind_book (title) VALUES ('Poppy war');
INSERT INTO bookmind_book (title) VALUES ('Chłopi');
