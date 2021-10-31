package ru.kpfu.itis.renett.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.kpfu.itis.renett.exceptions.DataBaseException;
import ru.kpfu.itis.renett.models.Article;
import ru.kpfu.itis.renett.models.Comment;
import ru.kpfu.itis.renett.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.function.Function;

public class CommentRepositoryJDBCImpl implements CommentRepository{
    //language=sql
    private static final String SQL_SELECT_ALL_COMMENTS = "SELECT * FROM\n" +
            "    comment parent LEFT JOIN comment child on child.parent_comment_id = parent.id ORDER BY parent.id;";
    //language=sql
    private static final String SQL_FIND_COMMENT_BY_ID = "SELECT * FROM\n" +
            "    comment parent LEFT JOIN comment child on child.parent_comment_id = parent.id\n" +
            "WHERE parent.id = ?;";
    //language=sql
    private static final String SQL_UPDATE_COMMENT_BY_ID = "UPDATE comment set body = ? WHERE id = ?";
    //language=sql
    private static final String SQL_DELETE_COMMENT_BY_ID = "DELETE FROM comment WHERE id = ?;";
    //language=sql
    private static final String SQL_SAVE_COMMENT = "INSERT INTO comment(body, author_id, article_id, parent_comment_id) VALUES (?, ?, ?, ?);";
    //language=sql
    private static final String SQL_FIND_ALL_COMMENTS_BY_ARTICLE_ID = "WITH parent AS\n" +
            "    (SELECT parent.id AS parent_id, parent.body AS parent_body, parent.author_id AS parent_author_id, parent.article_id AS parent_article_id, parent.parent_comment_id AS parent_comment_id, parent.published_at AS parent_published_at, author.login AS parent_author_login, author.first_name AS parent_author_name FROM\n" +
            "        comment parent left join \"user\" author on author.id = parent.author_id)\n" +
            "SELECT * FROM\n" +
            "    parent LEFT JOIN\n" +
            "        (SELECT child.id AS child_id, child.body AS child_body, child.author_id AS child_author_id, child.article_id AS child_article_id, child.parent_comment_id AS child_parent_comment_id, child.published_at AS child_published_at, ch_author.login AS child_author_login, ch_author.first_name AS child_author_name\n" +
            "            FROM comment child left join \"user\" ch_author on child.author_id = ch_author.id) AS child on child_parent_comment_id = parent_id\n" +
            "WHERE parent_article_id = ?\n" +
            "ORDER BY parent_id;";

    //comment columns
    private static final String id = "id";
    private static final String body = "body";
    private static final String authorId = "author_id";
    private static final String articleId = "article_id";
    private static final String parentCommentId = "parent_comment_id";
    private static final String publishedAt = "published_at";


    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public CommentRepositoryJDBCImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final Function<ResultSet, Comment> commentRowMapper = row -> {
        try {
            int parentId = row.getInt(parentCommentId);
            Comment parentComment = null;

            if (parentId > 0) {
                parentComment = new Comment(parentId);
            }

            return Comment.builder()
                    .id(row.getInt(id))
                    .body(row.getString(body))
                    .article(new Article(row.getInt(articleId)))
                    .author(new User(row.getInt(authorId)))
                    .publishedAt(row.getTimestamp(publishedAt))
                    .parentComment(parentComment)
                    .childComments(new ArrayList<>())
                    .build();
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    };


    @Override
    public void save(Comment entity) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_COMMENT, Statement.RETURN_GENERATED_KEYS);) {
            int j = 1;
            preparedStatement.setString(j++, entity.getBody());
            preparedStatement.setString(j++, String.valueOf(entity.getAuthor().getId()));
            preparedStatement.setString(j++, String.valueOf(entity.getArticle().getId()));
            preparedStatement.setInt(j++, entity.getParentComment().getId());;

            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                resultSet.next();
                Integer id = resultSet.getInt(1);
                entity.setId(id);
            }
        } catch (SQLException e) {
            throw new DataBaseException("Problem with saving comment", e);
        }
    }

    @Override
    public void update(Comment entity) {
        jdbcTemplate.update(SQL_UPDATE_COMMENT_BY_ID, entity.getBody(), entity.getBody());
    }

    @Override
    public void delete(Comment entity) {
        jdbcTemplate.update(SQL_DELETE_COMMENT_BY_ID, entity.getId());
    }

    @Override
    public Optional<Comment> findById(int id) {
        Comment searchedComment = null;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_COMMENT_BY_ID);) {

            preparedStatement.setInt(1, id);

            try (ResultSet row = preparedStatement.executeQuery();) {
                if (row.next()) {
                    searchedComment = commentRowMapper.apply(row);

                    do {
                        // TODO

                    } while (row.next());

                    return Optional.of(searchedComment);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Problem with processing query to get a comment", e);
        }
    }

    // TODO
    @Override
    public List<Comment> findAll() {
        List<Comment> commentList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                    ResultSet rows = statement.executeQuery(SQL_SELECT_ALL_COMMENTS)) {

            Set<Integer> processedComments = new HashSet<>();
            Comment newComment = null;

            while (rows.next()) {
                if (!processedComments.contains(rows.getInt(id))) {
                    newComment = parentCommentRowMapper.apply(rows);
                    commentList.add(newComment);
                }

                if (rows.getObject("parent.parent_comment_id", Integer.class) != null) {
                    Comment comment = parentCommentRowMapper.apply(rows);
                    newComment.setParentComment(comment);
                }

                if (rows.getObject("child.id", Integer.class) != null) {
                    Comment comment = childCommentRowMapper.apply(rows);
                    newComment.getChildComments().add(comment);
                }

                processedComments.add(newComment.getId());
            }
        } catch (SQLException e) {
            throw new DataBaseException("Problem with processing query to get all articles", e);
        }

        return commentList;
    }


    @Override
    public List<Comment> findAllArticleComments(int articleId) {
        List<Comment> commentList = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_COMMENTS_BY_ARTICLE_ID);) {

            preparedStatement.setInt(1, articleId);

            try (ResultSet rows = preparedStatement.executeQuery();) {
                Set<Integer> processedComments = new HashSet<>();
                Comment newComment = null;

                while (rows.next()) {
                    if (!processedComments.contains(rows.getInt("parent_id"))) {
                        newComment = parentCommentRowMapper.apply(rows);
                        commentList.add(newComment);
                    }

                    if (rows.getObject("child_id", Integer.class) != null) {
                        Comment comment = childCommentRowMapper.apply(rows);
                        comment.setParentComment(newComment);
                        comment.getAuthor().setLogin(rows.getString("child_author_login"));
                        comment.getAuthor().setFirstName(rows.getString("child_author_name"));
                        newComment.getChildComments().add(comment);
                    }
                    newComment.getAuthor().setLogin(rows.getString("parent_author_login"));
                    newComment.getAuthor().setFirstName(rows.getString("parent_author_name"));


                    processedComments.add(newComment.getId());
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Problem with processing query to get all article's comments", e);
        }

        return commentList;
    }



    private final Function<ResultSet, Comment> childCommentRowMapper = row -> {
        try {
            return Comment.builder()
                    .id(row.getInt("child_" + id))
                    .body(row.getString("child_" + body))
                    .article(new Article(row.getInt("child_" + articleId)))
                    .author(new User(row.getInt("child_" + authorId)))
                    .publishedAt(row.getTimestamp("child_" + publishedAt))
                    .childComments(new ArrayList<>())
                    .build();
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    };

    private final Function<ResultSet, Comment> parentCommentRowMapper = row -> {
        try {
            int parentId = row.getInt(parentCommentId);
            Comment parentComment = null;

            if (parentId > 0) {
                parentComment = new Comment(parentId);
            }

            return Comment.builder()
                    .id(row.getInt("parent_" + id))
                    .body(row.getString("parent_" + body))
                    .article(new Article(row.getInt("parent_" + articleId)))
                    .author(new User(row.getInt("parent_" + authorId)))
                    .publishedAt(row.getTimestamp("parent_" + publishedAt))
                    .parentComment(parentComment)
                    .childComments(new ArrayList<>())
                    .build();
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    };
}
