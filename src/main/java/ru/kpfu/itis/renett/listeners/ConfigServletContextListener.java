package ru.kpfu.itis.renett.listeners;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.kpfu.itis.renett.repository.UsersRepository;
import ru.kpfu.itis.renett.repository.UsersRepositoryJDBCImpl;
import ru.kpfu.itis.renett.service.Constants;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

public class ConfigServletContextListener implements ServletContextListener {
    private Properties properties;
    private ServletContext servletContext;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        this.servletContext = servletContextEvent.getServletContext();
        this.properties = loadPropertiesResource();

        // Hikari Data Source
        DataSource dataSource = initializeDataSource();
        servletContext.setAttribute(Constants.CNTX_DATA_SOURCE, dataSource);

        // UsersRepository
        servletContext.setAttribute(Constants.CNTX_USERS_REPOSITRY, initializeUsersRepository(dataSource));
    }

    private DataSource initializeDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(properties.getProperty(Constants.DB_DRIVER));
        hikariConfig.setJdbcUrl(properties.getProperty(Constants.DB_URL));
        hikariConfig.setUsername(properties.getProperty(Constants.DB_USERNAME));
        hikariConfig.setPassword(properties.getProperty(Constants.DB_PASSWORD));
        hikariConfig.setMaximumPoolSize(Integer.parseInt(properties.getProperty(Constants.DB_POOL_SIZE)));

        return new HikariDataSource(hikariConfig);
    }

    private UsersRepository initializeUsersRepository(DataSource dataSource) {
        return new UsersRepositoryJDBCImpl(dataSource);
    }

    private Properties loadPropertiesResource() {
        Properties properties = new Properties();
        try {
            properties.load(servletContext.getResourceAsStream("/WEB-INF/properties/app_db.properties"));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return properties;
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
