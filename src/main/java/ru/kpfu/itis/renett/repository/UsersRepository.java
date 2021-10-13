package ru.kpfu.itis.renett.repository;

import ru.kpfu.itis.renett.exceptions.DataBaseException;
import ru.kpfu.itis.renett.models.User;

import java.util.Optional;

public interface UsersRepository extends  CRUDRepository<User> {
    Optional<User> findByLogin(String login) throws DataBaseException;
}
