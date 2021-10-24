CREATE TABLE user
(
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    second_name VARCHAR(100),
    email VARCHAR(100) NOT NULL UNIQUE,
    login VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(32) NOT NULL
);

CREATE TABLE auth
(
    login VARCHAR(100) NOT NULL UNIQUE,
    uuid UUID NOT NULL,
    createdAt TIMESTAMP
);

CREATE TABLE article
(
    id SERIAL PRIMARY KEY,
    title VARCHAR NOT NULL,
    body TEXT NOT NULL,
    author_id INTEGER REFERENCES user(id),
    publishedAt TIMESTAMP,
    view_count BIGINTEGER
);

CREATE TABLE comment
(
    id SERIAL PRIMARY KEY,
    body TEXT NOT NULL,
    author_id INTEGER REFERENCES user(id),
    article_id INTEGER REFERENCES article(id),
    parent_comment_id INTEGER REFERENCES comment(id),
    publishedAt TIMESTAMP
);

CREATE TABLE tag
(
    id SERIAL PRIMARY KEY,
    title VARCHAR NOT NULL UNIQUE
);

CREATE TABLE article_tag
(
    tag_id INTEGER REFERENCES tag(id),
    article_id INTEGER REFERENCES article_id(id),
    PRIMARY KEY(tag_id, article_id)
);

-- необязательный функционал
CREATE TABLE chord
(
    id SERIAL PRIMARY KEY,
    title VARCHAR(10) NOT NULL UNIQUE,
    type VARCHAR(10) NOT NULL UNIQUE,
    position JSON NOT NULL,
    audio_file_path VARCHAR NOT NULL,
    picture_file_path VARCHAR
);
