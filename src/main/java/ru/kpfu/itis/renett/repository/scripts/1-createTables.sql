CREATE TABLE "user"
(
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    second_name VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE,
    login VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(64) NOT NULL
);

CREATE TABLE auth
(
    login VARCHAR(255) NOT NULL UNIQUE,
    uuid UUID NOT NULL,
    created_at TIMESTAMP
);

CREATE TABLE article
(
    id SERIAL PRIMARY KEY,
    title VARCHAR NOT NULL,
    body TEXT NOT NULL,
    author_id INTEGER REFERENCES "user"(id),
    published_at TIMESTAMP default current_timestamp,
    thumbnail_path VARCHAR,
    view_count BIGINT default 0
);

CREATE TABLE comment
(
    id SERIAL PRIMARY KEY,
    body TEXT NOT NULL,
    author_id INTEGER REFERENCES "user"(id),
    article_id INTEGER REFERENCES article(id),
    parent_comment_id INTEGER REFERENCES comment(id),
    published_at TIMESTAMP default current_timestamp
);

CREATE TABLE tag
(
    id SERIAL PRIMARY KEY,
    title VARCHAR NOT NULL UNIQUE
);

CREATE TABLE article_tag
(
    article_id INTEGER REFERENCES article(id),
    tag_id INTEGER REFERENCES tag(id),
    PRIMARY KEY(tag_id, article_id)
);

CREATE TABLE like_article
(
    user_id INTEGER REFERENCES "user"(id),
    article_id INTEGER REFERENCES article(id),
    PRIMARY KEY (user_id, article_id)
);
