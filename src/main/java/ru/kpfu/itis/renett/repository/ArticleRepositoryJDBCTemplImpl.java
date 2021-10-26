package ru.kpfu.itis.renett.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.kpfu.itis.renett.exceptions.DataBaseException;
import ru.kpfu.itis.renett.models.Article;
import ru.kpfu.itis.renett.models.Comment;
import ru.kpfu.itis.renett.models.Tag;
import ru.kpfu.itis.renett.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.function.Function;

public class ArticleRepositoryJDBCTemplImpl implements ArticleRepository {
    //language=sql
    private static final String SQL_SELECT_ALL = "SELECT article.id AS article_id, article.title AS article_title, article.body AS article_body, article.author_id AS article_author_id, article.published_at AS article_published_at, article.view_count AS article_view_count, tag.id AS tag_id, tag.title AS tag_title, comment.id AS comment_id, comment.body AS comment_body, comment.author_id AS comment_author_id, comment.article_id AS comment_article_id, comment.parent_comment_id AS comment_parent_comment_id, comment.published_at AS comment_published_at, \"user\".id AS user_id, \"user\".first_name AS user_first_name, \"user\".second_name AS user_second_name, \"user\".email AS user_email, \"user\".login AS user_login, \"user\".password_hash AS  user_password_hash FROM\n" +
            "article_tag left join article on article.id = article_tag.article_id\n" +
            "            left join tag on tag.id = article_tag.tag_id\n" +
            "            left join comment on article.id = comment.article_id\n" +
            "            left join \"user\" on \"user\".id = article.author_id\n" +
            "ORDER BY article.id;";
    //language=sql
    private static final String SQL_FIND_ALL_BY_AUTHOR_ID = "SELECT article.id AS article_id, article.title AS article_title, article.body AS article_body, article.author_id AS article_author_id, article.published_at AS article_published_at, article.view_count AS article_view_count, tag.id AS tag_id, tag.title AS tag_title, comment.id AS comment_id, comment.body AS comment_body, comment.author_id AS comment_author_id, comment.article_id AS comment_article_id, comment.parent_comment_id AS comment_parent_comment_id, comment.published_at AS comment_published_at, \"user\".id AS user_id, \"user\".first_name AS user_first_name, \"user\".second_name AS user_second_name, \"user\".email AS user_email, \"user\".login AS user_login, \"user\".password_hash AS  user_password_hash FROM\n" +
            "article_tag left join article on article.id = article_tag.article_id\n" +
            "            left join tag on tag.id = article_tag.tag_id\n" +
            "            left join comment on article.id = comment.article_id\n" +
            "            left join \"user\" on \"user\".id = article.author_id\n" +
            "WHERE article.author_id = ?;";
    //language=sql
    private static final String SQL_INSERT_ARTICLE = "INSERT INTO article(title, body, author_id) VALUES (?, ?, ?);";
    //language=sql
    private static final String SQL_FIND_BY_ID = "SELECT article.id AS article_id, article.title AS article_title, article.body AS article_body, article.author_id AS article_author_id, article.published_at AS article_published_at, article.view_count AS article_view_count, tag.id AS tag_id, tag.title AS tag_title, comment.id AS comment_id, comment.body AS comment_body, comment.author_id AS comment_author_id, comment.article_id AS comment_article_id, comment.parent_comment_id AS comment_parent_comment_id, comment.published_at AS comment_published_at, \"user\".id AS user_id, \"user\".first_name AS user_first_name, \"user\".second_name AS user_second_name, \"user\".email AS user_email, \"user\".login AS user_login, \"user\".password_hash AS  user_password_hash FROM\n" +
            "article_tag left join article on article.id = article_tag.article_id\n" +
            "            left join tag on tag.id = article_tag.tag_id\n" +
            "            left join comment on article.id = comment.article_id\n" +
            "            left join \"user\" on \"user\".id = article.author_id\n" +
            "WHERE article.id = ?;";
    //language=sql
    private static final String SQL_UPDATE_BY_ID = "UPDATE article set title = ?, body = ?, view_count = ? WHERE id=?";

    //article columns
    private static final String id = "article_id";
    private static final String title = "article_title";
    private static final String body = "article_body";
    private static final String authorId = "article_author_id";
    private static final String publishedAt = "article_published_at";
    private static final String viewCount = "article_view_count";

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public ArticleRepositoryJDBCTemplImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final Function<ResultSet, Article> getArticleFromRow = row -> {
        try {
            return Article.builder()
                    .id(row.getInt(id))
                    .title(row.getString(title))
                    .body(row.getString(body))
                    .publishedAt(row.getTimestamp(publishedAt))
                    .view_count(row.getLong(viewCount))
                    .commentList(new ArrayList<>())
                    .tagList(new ArrayList<>())
                    .build();
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
//    private Integer id;
//    private String title;
//    private String body;
//    private User author;   -- обращение за юзером
//    private Date publishedAt;
//    private List<Comment> commentList;   -- обращение за комментариями
//    private List<Tag> tagList;    -- обращение за тэгами
//    private Long view_count;
//    private int commentAmount;

    };

    private final Function<ResultSet, Comment> getCommentFromRow = row -> {
        try {
            Comment parentComment = null;
            int parentComId = row.getInt("comment_parent_comment_id");
            if (parentComId > 0) {
                parentComment = new Comment(parentComId);
            }

            return Comment.builder()
                    .id(row.getInt("comment_id"))
                    .body(row.getString("comment_body"))
                    .author(new User(row.getInt("comment_author_id")))             // todo get USER, get PARENT COMMENT, get NESTED COMMENTS
                    .parentComment(parentComment)
                    .publishedAt(row.getTimestamp("comment_published_at"))
                    .build();
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    };

    private final Function<ResultSet, User> getUserFromRow = row -> {
        try {
            return User.builder()
                    .id(row.getInt("user_id"))
                    .firstName(row.getString("user_first_name"))
                    .secondName(row.getString("user_second_name"))
                    .email(row.getString("user_email"))
                    .login(row.getString("user_login"))
                    .passwordHash(row.getString("user_password_hash"))
                    .build();
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    };

    private final Function<ResultSet, Tag> getTagFromRow = row -> {
        try {
            return Tag.builder()
                    .id(row.getInt("tag_id"))
                    .title("tag_title")
                    .build();
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    };

    @Override
    public List<Article> findAllByOwnerId(int ownerId) {
        return null;
    }

    @Override
    public void save(Article entity) {

    }

    @Override
    public void update(Article article) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_BY_ID)) {
            int j = 1;
            preparedStatement.setString(j++, article.getTitle());
            preparedStatement.setString(j++, article.getBody());
            preparedStatement.setString(j++, String.valueOf(article.getView_count()));

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows != 1) {
                throw new SQLException("Problem with update");
            }
        } catch (SQLException e) {
            throw new DataBaseException("Problem with processing query to get all users", e);
        }

    }

    @Override
    public void delete(Article entity) {

    }

    @Override
    public Optional<Article> findById(int id) {
        Article searchedArticle = null;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_ID);) {

            preparedStatement.setInt(1, id);

            try (ResultSet row = preparedStatement.executeQuery();) {
                if (row.next()) {
                    searchedArticle = getArticleFromRow.apply(row);

                    do {
                        Comment comment = getCommentFromRow.apply(row);
                        comment.setArticle(searchedArticle);
                        searchedArticle.getCommentList().add(comment);

                        Tag tag = getTagFromRow.apply(row);
                        searchedArticle.getTagList().add(tag);

                        User author = getUserFromRow.apply(row);
                        searchedArticle.setAuthor(author);

                    } while (row.next());
                    searchedArticle.setCommentAmount(searchedArticle.getCommentList().size());

                    return Optional.of(searchedArticle);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Problem with processing query to get all users", e);
        }
    }

    @Override
    public List<Article> findAll() {
        List<Article> articleList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                    ResultSet rows = statement.executeQuery(SQL_SELECT_ALL)) {

            Set<Integer> processedArticles = new HashSet<>();
            Article newArticle = null;

            while (rows.next()) {

                if (!processedArticles.contains(rows.getInt(id))) {
                    newArticle = getArticleFromRow.apply(rows);
                    articleList.add(newArticle);
                }

                if (rows.getObject("comment_id", Integer.class) != null) {
                    Comment comment = getCommentFromRow.apply(rows);
                    comment.setArticle(newArticle);
                    newArticle.getCommentList().add(comment);
                }

                if (rows.getObject("tag_id", Integer.class) != null) {
                    Tag tag = getTagFromRow.apply(rows);
                    newArticle.getTagList().add(tag);
                }

                User author = getUserFromRow.apply(rows);
                newArticle.setAuthor(author);

                processedArticles.add(newArticle.getId());
            }
        } catch (SQLException e) {
            throw new DataBaseException("Problem with processing query to get all users", e);
        }

        return articleList;
    }
}
