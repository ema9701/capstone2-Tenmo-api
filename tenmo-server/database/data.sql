INSERT INTO tenmo_user (username,password_hash) VALUES ('user1','$2a$10$A4EQFlbUdU1IV11aQc20jORTMYJ0/th12R4vk8Ybjjc7FrhfA9VRK') RETURNING user_id;
INSERT INTO account (user_id, balance) VALUES (1001, 1000.00) RETURNING account_id;

INSERT INTO tenmo_user (username,password_hash) VALUES ('user2','$2a$10$egA7JPET/TY55ZiXcGTygu4cGYk2HA7wsxLTYFczWwUT3BlNJ/ogq') RETURNING user_id;
INSERT INTO account (user_id, balance) VALUES (1002, 1000.00) RETURNING account_id;

INSERT INTO tenmo_user (username,password_hash) VALUES ('user3','$2a$10$TqCYRyEbZmovdUJyiuNe4..x3Ymdv7l/hkay9W/l/AJpodRhW0upy') RETURNING user_id; 
INSERT INTO account (user_id, balance) VALUES (1003, 1000.00) RETURNING account_id;

INSERT INTO tenmo_user (username,password_hash) VALUES ('user4','$2a$10$dJ.3je1GVmKWeZQyKUzPveCH/G3WGDzArlmFoWgcCkYg72GB7f59.') RETURNING user_id;
INSERT INTO account (user_id, balance) VALUES (1004, 1000.00) RETURNING account_id;

 
 

