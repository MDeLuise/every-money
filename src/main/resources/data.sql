INSERT INTO users (id, username, password)
VALUES (0, 'admin', '$2a$12$oHF8QcxYvDkIDqIy9YjRauhxGxJEW4qpWiAC74mBBLQmqnG3kBCu6');

INSERT INTO roles (id, name) VALUES (0, 'ROLE_ADMIN');

INSERT INTO user_roles (user_id, role_id) VALUES (0, 0);
