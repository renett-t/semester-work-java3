-- to get everything about article
SELECT article.id AS article_id, article.title AS article_title, article.body AS article_body, article.author_id AS article_author_id, article.published_at AS article_published_at, article.view_count AS article_view_count, tag.id AS tag_id, tag.title AS tag_title, comment.id AS comment_id, comment.body AS comment_body, comment.author_id AS comment_author_id, comment.article_id AS comment_article_id, comment.parent_comment_id AS comment_parent_comment_id, comment.published_at AS comment_published_at, "user".id AS user_id, "user".first_name AS user_first_name, "user".second_name AS user_second_name, "user".email AS user_email, "user".login AS user_login, "user".password_hash AS  user_password_hash FROM
            article_tag left join article on article.id = article_tag.article_id
                        left join tag on tag.id = article_tag.tag_id
                        left join comment on article.id = comment.article_id
                        left join "user" on "user".id = article.author_id
            ORDER BY article.id;
