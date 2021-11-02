-- to get everything about article
SELECT article.id AS article_id, article.title AS article_title, article.body AS article_body, article.author_id AS article_author_id, article.published_at AS article_published_at, article.view_count AS article_view_count, tag.id AS tag_id, tag.title AS tag_title, comment.id AS comment_id, comment.body AS comment_body, comment.author_id AS comment_author_id, comment.article_id AS comment_article_id, comment.parent_comment_id AS comment_parent_comment_id, comment.published_at AS comment_published_at, "user".id AS user_id, "user".first_name AS user_first_name, "user".second_name AS user_second_name, "user".email AS user_email, "user".login AS user_login, "user".password_hash AS  user_password_hash FROM
            article_tag left join article on article.id = article_tag.article_id
                        left join tag on tag.id = article_tag.tag_id
                        left join comment on article.id = comment.article_id
                        left join "user" on "user".id = article.author_id
            ORDER BY article.id;


-- get all tags of some article
SELECT * FROM
    tag left join article_tag on tag.id = article_tag.tag_id
WHERE article_id = ?;



-- get all comments by article id + nested  RECURSIVE QUERY............
----------------- NEW
WITH RECURSIVE _comment AS
                   (SELECT id, body, author_id, article_id, parent_comment_id, published_at,
                           1 AS level
                    FROM comment
                    WHERE parent_comment_id IS NULL AND article_id = ?
                    UNION
                    SELECT comment.id, comment.body, comment.author_id, comment.article_id, comment.parent_comment_id, comment.published_at,
                           (level + 1) AS level
                    FROM comment INNER JOIN _comment ON _comment.id = comment.parent_comment_id)
SELECT * FROM _comment ORDER BY level;


----------------- OLD
WITH parent AS
         (SELECT parent.id AS parent_id, parent.body AS parent_body, parent.author_id AS parent_author_id, parent.article_id AS parent_article_id, parent.parent_comment_id AS parent_comment_id, parent.published_at AS parent_published_at, author.login AS parent_author_login, author.first_name AS parent_author_name FROM
             comment parent left join "user" author on author.id = parent.author_id)
SELECT * FROM
    parent LEFT JOIN
    (SELECT child.id AS child_id, child.body AS child_body, child.author_id AS child_author_id, child.article_id AS child_article_id, child.parent_comment_id AS child_parent_comment_id, child.published_at AS child_published_at, ch_author.login AS child_author_login, ch_author.first_name AS child_author_name
     FROM comment child left join "user" ch_author on child.author_id = ch_author.id) AS child on child_parent_comment_id = parent_id
WHERE parent_article_id = 7
ORDER BY parent_id;
