package ru.kpfu.itis.renett.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.kpfu.itis.renett.models.AuthModel;
import ru.kpfu.itis.renett.models.User;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AuthRepositoryJDBCImpl implements AuthRepository {
    //language=sql
    private static final String SQL_SELECT_ALL = "SELECT * FROM auth";
    //language=sql
    private static final String SQL_UPDATE_BY_ID = "UPDATE auth set uuid = ?, created_at = current_timestamp WHERE login = ?";
    //language=sql
    private static final String SQL_INSERT_AUTH = "INSERT INTO auth(login, uuid) VALUES (?, ?);";
    //language=sql
    private static final String SQL_FIND_USER_BY_UUID = "SELECT * FROM\n" +
            "    auth left join \"user\" on auth.login = \"user\".login\n" +
            "WHERE \"user\".login = ?;\n";

    //auth model columns
    private static final String login = "login";
    private static final String uuid = "uuid";
    private static final String createdAt = "created_at";

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public AuthRepositoryJDBCImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<AuthModel> authRowMapper = (row, rowNumber) -> AuthModel.builder()
            .login(row.getString(login))
            .uuid(UUID.fromString(row.getString(uuid)))
            .createdAt(row.getTimestamp(createdAt))
            .build();

    @Override
    public Optional<User> findUserByUUID(UUID uuid) {
        List<User> userList = jdbcTemplate.query(SQL_FIND_USER_BY_UUID,
                (row, rowNum) ->
                    User.builder()
                    .id(row.getInt("\"user\".id"))
                    .firstName(row.getString("\"user\".firstName"))
                    .secondName(row.getString("\"user\".secondName"))
                    .email(row.getString("\"user\".email"))
                    .login(row.getString("\"user\".login"))
                    .passwordHash(row.getString("\"user\".password"))
                    .build(),
                uuid);

        if (userList.size() == 0) {
            return Optional.empty();
        } else {
            return Optional.of(userList.get(0));
        }
    }

    @Override
    public void save(AuthModel entity) {
        jdbcTemplate.update(SQL_INSERT_AUTH, entity.getLogin(), entity.getUuid());
    }

    @Override
    public void update(AuthModel entity) {
        jdbcTemplate.update(SQL_UPDATE_BY_ID, entity.getUuid(), entity.getLogin());
    }

    @Override
    public void delete(AuthModel entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<AuthModel> findById(int id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<AuthModel> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, authRowMapper);
    }
}
