BEGIN TRANSACTION;

DROP TABLE IF EXISTS tenmo_user, account, transactions, transaction_account;

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

CREATE TABLE transactions (
	transaction_id serial NOT NULL,
	amount decimal (13, 2) NOT NULL,
	is_requesting boolean NOT NULL,
	status varchar (10) NOT NULL DEFAULT 'True',
	CONSTRAINT PK_transactions PRIMARY KEY (transaction_id)
);

CREATE TABLE transaction_account (
	transaction_id serial NOT NULL,
	account_id_out int NOT NULL,
	account_id_in int NOT NULL,
	CONSTRAINT PK_transaction_account PRIMARY KEY (transaction_id),
	CONSTRAINT FK_transaction_account FOREIGN KEY (transaction_id) REFERENCES transactions (transaction_id),
	CONSTRAINT FK_transaction_account_out FOREIGN KEY (account_id_out) REFERENCES account (account_id),
	CONSTRAINT FK_transaction_account_in FOREIGN KEY (account_id_in) REFERENCES account (account_id)
);

COMMIT;
