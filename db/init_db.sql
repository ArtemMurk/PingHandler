DROP TABLE IF EXISTS project_user_handler;
DROP TABLE IF EXISTS module_handler;
DROP TABLE IF EXISTS project_handler;
DROP TABLE IF EXISTS user_handler;

CREATE TABLE IF NOT EXISTS project_handler
(
	id INTEGER NOT NULL,
	name VARCHAR(100) NOT NULL,
	constraint pk_project_handler PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS user_handler
(
	id INTEGER NOT NULL,
	tel_id INTEGER NOT NULL,
	name INTEGER NOT NULL,
	constraint pk_user_handler PRIMARY KEY(id)
);

CREATE TABLE project_user_handler
(
	p_id INTEGER NOT NULL ,
	u_id INTEGER NOT NULL,
	permission SMALLINT NOT NULL,
	CONSTRAINT pk_project_user PRIMARY KEY (p_id,u_id),
	CONSTRAINT fk_project_handler FOREIGN KEY (p_id) REFERENCES project_handler(id),
	CONSTRAINT fk_user_handler FOREIGN KEY (u_id) REFERENCES  user_handler(id)
);

CREATE TABLE IF NOT EXISTS module_handler
(
	key VARCHAR(32) NOT NULL,
	p_id INTEGER NOT NULL,
	name VARCHAR(100) NOT NULL,
	ping_time BIGINT,
	mute BOOLEAN DEFAULT FALSE,
	constraint pk_module PRIMARY KEY(key),
	constraint fk_project FOREIGN KEY(p_id) REFERENCES project_handler(id)
)