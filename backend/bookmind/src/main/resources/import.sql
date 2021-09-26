INSERT INTO bookmind_access_level (level) VALUES ('ADMIN');
INSERT INTO bookmind_access_level (level) VALUES ('MODERATOR');
INSERT INTO bookmind_access_level (level) VALUES ('READER');

INSERT INTO bookmind_user (email, login, password) VALUES ('a@domain.com', 'admin', 'admin_password');
INSERT INTO bookmind_user (email, login, password) VALUES ('m@domain.com', 'moderator', 'moderator_password');
INSERT INTO bookmind_user (email, login, password) VALUES ('r@domain.com', 'reader', 'reader_password');

INSERT INTO bookmind_user_access_levels (user_id, access_levels_id) VALUES (1, 1);
INSERT INTO bookmind_user_access_levels (user_id, access_levels_id) VALUES (2, 2);
INSERT INTO bookmind_user_access_levels (user_id, access_levels_id) VALUES (3, 3);
