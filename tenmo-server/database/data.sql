INSERT INTO tenmo_user(username, password_hash)
VALUES ('user1', '$2a$10$A4EQFlbUdU1IV11aQc20jORTMYJ0/th12R4vk8Ybjjc7FrhfA9VRK'),
       ('user2', '$2a$10$egA7JPET/TY55ZiXcGTygu4cGYk2HA7wsxLTYFczWwUT3BlNJ/ogq'),
       ('user3', '$2a$10$TqCYRyEbZmovdUJyiuNe4..x3Ymdv7l/hkay9W/l/AJpodRhW0upy');

INSERT INTO account (user_id)
VALUES ((SELECT user_id FROM tenmo_user WHERE username = 'user1')),
       ((SELECT user_id FROM tenmo_user WHERE username = 'user2')),
       ((SELECT user_id FROM tenmo_user WHERE username = 'user3'));
