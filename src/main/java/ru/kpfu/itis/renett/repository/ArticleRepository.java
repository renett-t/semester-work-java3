package ru.kpfu.itis.renett.repository;

import ru.kpfu.itis.renett.models.Article;

import java.util.List;

public interface ArticleRepository extends CRUDRepository<Article> {
    List<Article> findAllByOwnerId(int ownerId);
}
