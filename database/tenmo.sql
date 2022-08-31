BEGIN TRANSACTION;

-- ROLLBACK; 

DROP TABLE IF EXISTS tenmo_user, account, transfers, requests;

DROP SEQUENCE IF EXISTS seq_user_id, seq_account_id;

-- Sequence to start user_id values at 1001 instead of 1
CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  START WITH 1001
  NO MAXVALUE;

CREATE TABLE tenmo_user (
	user_id int NOT NULL DEFAULT nextval('seq_user_id'),
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	CONSTRAINT PK_tenmo_user PRIMARY KEY (user_id),
	CONSTRAINT UQ_username UNIQUE (username)
); 
-- Sequence to start account_id values at 2001 instead of 1
-- Note: Use similar sequences with unique starting values for additional tables
CREATE SEQUENCE seq_account_id
  INCREMENT BY 1
  START WITH 2001
  NO MAXVALUE;
  
CREATE TABLE account (
	account_id int NOT NULL DEFAULT nextval('seq_account_id'),
	user_id int NOT NULL,
	balance decimal(13, 2) NOT NULL,
	CONSTRAINT PK_account PRIMARY KEY (account_id),
	CONSTRAINT FK_account_tenmo_user FOREIGN KEY (user_id) REFERENCES tenmo_user (user_id)
);

CREATE TABLE transfers (
	transfer_id serial NOT NULL,
	transfer_date timestamp NOT NULL DEFAULT NOW(), 
	account_from int NOT NULL, 
	account_to int NOT NULL,
	amount decimal (13, 2) NOT NULL,
	status varchar(10) NOT NULL,	
	CONSTRAINT PK_transfers PRIMARY KEY (transfer_id)
);
  
CREATE TABLE requests (
	request_id serial NOT NULL,
	request_date timestamp not null DEFAULT NOW(),
	account_from int NOT NULL,
	account_to int NOT NULL,
	amount decimal (13, 2) NOT NULL,
	approve_request boolean,
	status varchar(10) NOT NULL,
	CONSTRAINT PK_requests PRIMARY KEY (request_id)
);

ALTER TABLE transfers ADD CONSTRAINT FK_transfers_account_from FOREIGN KEY (account_from) REFERENCES account(account_id); 
ALTER TABLE transfers ADD CONSTRAINT FK_transfers_account_to FOREIGN KEY (account_to) REFERENCES account(account_id);

ALTER TABLE requests ADD CONSTRAINT FK_requests_account_from FOREIGN KEY (account_from) REFERENCES account(account_id);
ALTER TABLE requests ADD CONSTRAINT FK_requests_account_to FOREIGN KEY (account_to) REFERENCES account(account_id);

INSERT INTO tenmo_user (username,password_hash) VALUES ('user1','$2a$10$A4EQFlbUdU1IV11aQc20jORTMYJ0/th12R4vk8Ybjjc7FrhfA9VRK') RETURNING user_id;
INSERT INTO account (user_id, balance) VALUES (1001, 1000.00) RETURNING account_id;

INSERT INTO tenmo_user (username,password_hash) VALUES ('user2','$2a$10$egA7JPET/TY55ZiXcGTygu4cGYk2HA7wsxLTYFczWwUT3BlNJ/ogq') RETURNING user_id;
INSERT INTO account (user_id, balance) VALUES (1002, 1000.00) RETURNING account_id;

INSERT INTO tenmo_user (username,password_hash) VALUES ('user3','$2a$10$TqCYRyEbZmovdUJyiuNe4..x3Ymdv7l/hkay9W/l/AJpodRhW0upy') RETURNING user_id; 
INSERT INTO account (user_id, balance) VALUES (1003, 1000.00) RETURNING account_id;

INSERT INTO tenmo_user (username,password_hash) VALUES ('user4','$2a$10$dJ.3je1GVmKWeZQyKUzPveCH/G3WGDzArlmFoWgcCkYg72GB7f59.') RETURNING user_id;
INSERT INTO account (user_id, balance) VALUES (1004, 1000.00) RETURNING account_id;

COMMIT;
