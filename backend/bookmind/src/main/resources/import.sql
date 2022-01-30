INSERT INTO bookmind_access_level (authority)
VALUES ('ROLE_ADMIN'),
       ('ROLE_MODERATOR'),
       ('ROLE_READER');

INSERT INTO bookmind_user (email, username, password, enabled)
VALUES ('a@domain.com', 'admin', '$2a$10$AFMFchLCnBP3ai7tGfO0MOY9XoIhehJRV0mnx0QXn5Uz8j9U0l2y.', true),
       ('m@domain.com', 'moderator', '$2a$10$AFMFchLCnBP3ai7tGfO0MOY9XoIhehJRV0mnx0QXn5Uz8j9U0l2y.', true),
       ('r@domain.com', 'reader', '$2a$10$AFMFchLCnBP3ai7tGfO0MOY9XoIhehJRV0mnx0QXn5Uz8j9U0l2y.', true),
       ('s@domain.com', 'superadmin', '$2a$10$AFMFchLCnBP3ai7tGfO0MOY9XoIhehJRV0mnx0QXn5Uz8j9U0l2y.', true);

INSERT INTO bookmind_user_authorities (user_id, authorities_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 1),
       (4, 2),
       (4, 3);

INSERT INTO bookmind_book (title, author)
VALUES ('A Tale of Two Cities', 'Charles Dickens'),
       ('The Hobbit', 'J. R. R. Tolkien'),
       ('Harry Potter and the Philosopher''s Stone', 'J. K. Rowling'),
       ('The Little Prince', 'Antoine de Saint-Exupéry'),
       ('Dream of the Red Chamber', 'Cao Xueqin'),
       ('And Then There Were None', 'Agatha Christie'),
       ('The Lion, the Witch and the Wardrobe', 'C. S. Lewis'),
       ('The Adventures of Pinocchio', 'Carlo Collodi'),
       ('The Da Vinci Code', 'Dan Brown'),
       ('Harry Potter and the Chamber of Secrets', 'J. K. Rowling'),
       ('Harry Potter and the Prisoner of Azkaban', 'J. K. Rowling'),
       ('Harry Potter and the Goblet of Fire', 'J. K. Rowling'),
       ('Harry Potter and the Order of the Phoenix', 'J. K. Rowling'),
       ('Harry Potter and the Half-Blood Prince', 'J. K. Rowling'),
       ('Harry Potter and the Deathly Hallows', 'J. K. Rowling'),
       ('The Alchemist', 'Paulo Coelho'),
       ('The Catcher in the Rye', 'J. D. Salinger'),
       ('The Bridges of Madison County', 'Robert James Waller'),
       ('Ben-Hur: A Tale of the Christ', 'Lew Wallace'),
       ('You Can Heal Your Life', 'Louise Hay');

INSERT INTO bookmind_shelf (name, user_id)
VALUES ('To read', 3);

INSERT INTO bookmind_shelf_books (shelf_id, books_id)
VALUES (1, 2),
       (1, 3);
