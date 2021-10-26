package ru.kpfu.itis.renett.listeners;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.kpfu.itis.renett.repository.AuthRepositoryJDBCImpl;
import ru.kpfu.itis.renett.repository.UserRepository;
import ru.kpfu.itis.renett.repository.UserRepositoryJDBCImpl;
import ru.kpfu.itis.renett.service.Constants;
import ru.kpfu.itis.renett.service.SecurityServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.util.Properties;

@WebListener
public class InitServletContextListener implements ServletContextListener {

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

        servletContext.setAttribute(Constants.CNTX_DATA_SOURCE, dataSource);

        // SecurityService
        UserRepository userRepository = new UserRepositoryJDBCImpl(dataSource);
        servletContext.setAttribute(Constants.CNTX_SECURITY_SERVICE, new SecurityServiceImpl(userRepository, new AuthRepositoryJDBCImpl(dataSource)));
        servletContext.setAttribute(Constants.CNTX_USER_SERVICE, userRepository);


        // todo : another repos and services classes
        // update constants path
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

}