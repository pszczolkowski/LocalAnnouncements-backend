CREATE TABLE announcement (
	id BIGINT(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL,
	description VARCHAR(5000),
	latitude FLOAT(10, 6) NOT NULL,
	longitude FLOAT(10, 6) NOT NULL,
	status VARCHAR(255) NOT NULL,
	start_time TIMESTAMP NULL,
	duration INT UNSIGNED,
	creator_id BIGINT(20) NOT NULL
	) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
	
ALTER TABLE announcement ADD CONSTRAINT fk_announcement_user_creator FOREIGN KEY (creator_id) REFERENCES user (id);