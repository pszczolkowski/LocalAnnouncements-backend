ALTER TABLE user ADD COLUMN email VARCHAR(255);

UPDATE user SET email = login + "@foo.bar";

ALTER TABLE user MODIFY COLUMN email VARCHAR(255) NOT NULL;

ALTER TABLE user ADD CONSTRAINT u_user_email UNIQUE KEY (email);