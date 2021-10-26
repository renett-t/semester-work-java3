package ru.kpfu.itis.renett.repository;

import ru.kpfu.itis.renett.exceptions.DataBaseException;
import ru.kpfu.itis.renett.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class UserRepositoryJDBCImpl implements UserRepository {
    //language=sql
    private static final String SQL_SELECT_ALL = "select * from \"user\" order by id";
    //language=sql
    private static final String SQL_FIND_BY_LOGIN = "SELECT * FROM \"user\" WHERE login=?";
    //language=sql
    private static final String SQL_INSERT_USER = "INSERT INTO \"user\"(first_name, second_name, email, login, password_hash) VALUES (?, ?, ?, ?, ?);";
    //language=sql
    private static final String SQL_FIND_BY_ID = "SELECT * FROM \"user\" WHERE id=?";
    //language=sql
    private static final String SQL_UPDATE_BY_ID = "UPDATE \"user\" set first_name = ?, second_name = ?, email = ?, login = ?, password_hash = ? WHERE id=?";

    //user columns
    private static final String id = "id";
    private static final String firstName = "first_name";
    private static final String secondName = "second_name";
    private static final String email = "email";
    private static final String login = "login";
    private static final String password = "password_hash";

    private DataSource dataSource;

    public UserRepositoryJDBCImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private final Function<ResultSet, User> getUserFromRow = rows -> {
        try {
            return User.builder()
                    .id(rows.getInt(id))
                    .firstName(rows.getString(firstName))
                    .secondName(rows.getString(secondName))
                    .email(rows.getString(email))
                    .login(rows.getString(login))
                    .passwordHash(rows.getString(password))
                    .build();
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    };

    @Override
    public List<User> findAll() throws DataBaseException {
        List<User> userList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                    ResultSet rows = statement.executeQuery(SQL_SELECT_ALL)) {

            while (rows.next()) {
                User newUser = getUserFromRow.apply(rows);
                userList.add(newUser);
            }
        } catch (SQLException e) {
            throw new DataBaseException("Problem with processing query to get all users", e);
        }

        return userList;
    }

    @Override
    public void save(User user) throws DataBaseException {
        ResultSet resultSet = null;
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_USER);) {
            int j = 1;
            preparedStatement.setString(j++, user.getFirstName());
            preparedStatement.setString(j++, user.getSecondName());
            preparedStatement.setString(j++, user.getEmail());
            preparedStatement.setString(j++, user.getLogin());
            preparedStatement.setString(j++, user.getPasswordHash());

            preparedStatement.executeQuery();

        } catch (SQLException e) {
            throw new DataBaseException("Problem with processing query to get all users", e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ignored) {
                }
            }
        }

    }

    @Override
    public void update(User user) throws DataBaseException {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_BY_ID)) {
            int j = 1;
            preparedStatement.setString(j++, user.getFirstName());
            preparedStatement.setString(j++, user.getSecondName());
            preparedStatement.setString(j++, user.getEmail());
            preparedStatement.setString(j++, user.getLogin());
            preparedStatement.setString(j++, user.getPasswordHash());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows != 1) {
                throw new SQLException("Problem with update");
            }
        } catch (SQLException e) {
            throw new DataBaseException("Problem with processing query to get all users", e);
        }

    }

    @Override
    public void delete(User entity) {
        //todo
    }

    @Override
    public Optional<User> findByLogin(String login) throws DataBaseException {
        Optional<User> searchedUser = Optional.empty();
        ResultSet rows = null;
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_LOGIN);
                ) {

            preparedStatement.setString(1, login);
            rows = preparedStatement.executeQuery();
            if (rows.next()) {
                searchedUser = Optional.of(getUserFromRow.apply(rows));
            }
        } catch (SQLException e) {
            throw new DataBaseException("Problem with processing query to get all users", e);
        } finally {
            if (rows != null) {
                try {
                    rows.close();
                } catch (SQLException ignored) {
                }
            }
        }

        return searchedUser;
    }

    @Override
    public Optional<User> findById(int id) throws DataBaseException {
        Optional<User> searchedUser = Optional.empty();

        ResultSet row = null;
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_ID);) {

            preparedStatement.setInt(1, id);
            row = preparedStatement.executeQuery();
            if (row.next()) {
                searchedUser = Optional.of(getUserFromRow.apply(row));
            }
        } catch (SQLException e) {
            throw new DataBaseException("Problem with processing query to get all users", e);
        }
        finally {
            if (row != null) {
                try {
                    row.close();
                } catch (SQLException ignored) {
                }
            }
        }

        return searchedUser;
    }
}
