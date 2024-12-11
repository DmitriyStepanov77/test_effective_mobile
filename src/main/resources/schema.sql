CREATE TABLE IF NOT EXISTS users (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  password VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  username VARCHAR(50) NOT NULL UNIQUE,
  role VARCHAR(20) NOT NULL,
  CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tasks (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  title VARCHAR(50) NOT NULL,
  description VARCHAR(255) NOT NULL,
  author_id BIGINT NOT NULL,
  performer_id BIGINT NOT NULL,
  priority VARCHAR(255) NOT NULL,
  state VARCHAR(255) NOT NULL,
  created TIMESTAMP NOT NULL,
  CONSTRAINT pk_tasks PRIMARY KEY (id),
  CONSTRAINT fk_author_task FOREIGN KEY (author_id) REFERENCES users (id),
  CONSTRAINT fk_performer_task FOREIGN KEY (performer_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS comments (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  description VARCHAR(255) NOT NULL,
  author_id BIGINT NOT NULL,
  task_id BIGINT NOT NULL,
  created TIMESTAMP NOT NULL,
  CONSTRAINT pk_comments PRIMARY KEY (id),
  CONSTRAINT fk_author_comment FOREIGN KEY (author_id) REFERENCES users (id),
  CONSTRAINT fk_task FOREIGN KEY (task_id) REFERENCES tasks (id)
);