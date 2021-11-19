
INSERT INTO users(username, password, enabled)
VALUES ('user', '{noop}user1234', true),
       ('admin', '{noop}admin1234', true)
;

INSERT INTO authorities(username, authority)
VALUES ('user', 'ROLE_USER'),
       ('admin', 'ROLE_ADMIN')
;