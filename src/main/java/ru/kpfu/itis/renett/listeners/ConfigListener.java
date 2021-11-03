package ru.kpfu.itis.renett.listeners;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.kpfu.itis.renett.repository.*;
import ru.kpfu.itis.renett.service.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.util.Properties;

@WebListener
public class ConfigListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        Properties properties = new Properties();

        try {
            properties.load(servletContext.getResourceAsStream(Constants.PROPS_FILE_PATH));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        // Hikari Data Source
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(properties.getProperty(Constants.DB_DRIVER));
        hikariConfig.setJdbcUrl(properties.getProperty(Constants.DB_URL));
        hikariConfig.setUsername(properties.getProperty(Constants.DB_USERNAME));
        hikariConfig.setPassword(properties.getProperty(Constants.DB_PASSWORD));
        hikariConfig.setMaximumPoolSize(Integer.parseInt(properties.getProperty(Constants.DB_POOL_SIZE)));
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        // Repositories
        UserRepository userRepository = new UserRepositoryJDBCImpl(dataSource);
        ArticleRepository articleRepository = new ArticleRepositoryJDBCImpl(dataSource);
        TagRepository tagRepository = new TagRepositoryJDBCImpl(dataSource);
        AuthRepository authRepository = new AuthRepositoryJDBCImpl(dataSource);
        CommentRepository commentRepository = new CommentRepositoryJDBCImpl(dataSource);

        // Services Initialization
        servletContext.setAttribute(Constants.CNTX_SECURITY_SERVICE, new SecurityServiceImpl(userRepository, new AuthRepositoryJDBCImpl(dataSource)));
        servletContext.setAttribute(Constants.CNTX_ARTICLE_SERVICE, new ArticleServiceImpl(articleRepository, userRepository, commentRepository, tagRepository));
        servletContext.setAttribute(Constants.CNTX_USER_SERVICE, new UserServiceImpl(userRepository));
        servletContext.setAttribute(Constants.CNTX_FILE_SERVICE, new FileServiceImpl(Constants.STORAGE_URL));
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

}
