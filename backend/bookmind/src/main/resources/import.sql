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

INSERT INTO bookmind_book (title, author) VALUES ('A Tale of Two Cities', 'Charles Dickens');
INSERT INTO bookmind_book (title, author) VALUES ('The Hobbit', 'J. R. R. Tolkien');
INSERT INTO bookmind_book (title, author) VALUES ('Harry Potter and the Philosopher''s Stone', 'J. K. Rowling');
INSERT INTO bookmind_book (title, author) VALUES ('The Little Prince', 'Antoine de Saint-Exupéry');
INSERT INTO bookmind_book (title, author) VALUES ('Dream of the Red Chamber', 'Cao Xueqin');
INSERT INTO bookmind_book (title, author) VALUES ('And Then There Were None', 'Agatha Christie');
INSERT INTO bookmind_book (title, author) VALUES ('The Lion, the Witch and the Wardrobe', 'C. S. Lewis');
INSERT INTO bookmind_book (title, author) VALUES ('The Adventures of Pinocchio', 'Carlo Collodi');
INSERT INTO bookmind_book (title, author) VALUES ('The Da Vinci Code', 'Dan Brown');
INSERT INTO bookmind_book (title, author) VALUES ('Harry Potter and the Chamber of Secrets', 'J. K. Rowling');
INSERT INTO bookmind_book (title, author) VALUES ('Harry Potter and the Prisoner of Azkaban', 'J. K. Rowling');
INSERT INTO bookmind_book (title, author) VALUES ('Harry Potter and the Goblet of Fire', 'J. K. Rowling');
INSERT INTO bookmind_book (title, author) VALUES ('Harry Potter and the Order of the Phoenix', 'J. K. Rowling');
INSERT INTO bookmind_book (title, author) VALUES ('Harry Potter and the Half-Blood Prince', 'J. K. Rowling');
INSERT INTO bookmind_book (title, author) VALUES ('Harry Potter and the Deathly Hallows', 'J. K. Rowling');
INSERT INTO bookmind_book (title, author) VALUES ('The Alchemist', 'Paulo Coelho');
INSERT INTO bookmind_book (title, author) VALUES ('The Catcher in the Rye', 'J. D. Salinger');
INSERT INTO bookmind_book (title, author) VALUES ('The Bridges of Madison County', 'Robert James Waller');
INSERT INTO bookmind_book (title, author) VALUES ('Ben-Hur: A Tale of the Christ', 'Lew Wallace');
INSERT INTO bookmind_book (title, author) VALUES ('You Can Heal Your Life', 'Louise Hay');

INSERT INTO bookmind_shelf (name, user_id) VALUES ('To read', 3);

INSERT INTO bookmind_shelf_books (shelf_id, books_id) VALUES (1, 2);
INSERT INTO bookmind_shelf_books (shelf_id, books_id) VALUES (1, 3);
