INSERT INTO users (id, username, password)
VALUES (0, 'admin', '$2a$12$oHF8QcxYvDkIDqIy9YjRauhxGxJEW4qpWiAC74mBBLQmqnG3kBCu6');
INSERT INTO users (id, username, password)
VALUES (1, 'user', '$2a$12$4M1g7Tfk.A2hcDjCOJ5cweu.A8eqw/zl2fOUgrNdL8Vi8yUNioOqW');


INSERT INTO roles (id, name) VALUES (0, 'ROLE_ADMIN');

INSERT INTO user_roles (user_id, role_id) VALUES (0, 0);

INSERT INTO wallets (id, starting_amount, name, description) VALUES (0, 0, 'Bank 1', 'Primary used bank');
INSERT INTO wallets (id, starting_amount, name, description) VALUES (1, 0, 'Bank 2', 'Secondary used bank');
INSERT INTO wallets (id, starting_amount, name, description) VALUES (2, 0, 'Cash', 'Money I have under the bed');