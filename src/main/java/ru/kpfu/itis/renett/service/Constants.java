package ru.kpfu.itis.renett.service;

public class Constants {
    public static final String PROPS_FILE_PATH = "WEB-INF/properties/appdb.properties";

    public static  final String HASHING_ALGORITHM_NAME = "SHA-256";

    public static final String DB_URL ="db.url";
    public static final String DB_USERNAME = "db.username";
    public static final String DB_PASSWORD = "db.password";
    public static final String DB_DRIVER = "db.driver";
    public static final String DB_POOL_SIZE = "db.hikari.max-pool-size";

    public static final String CNTX_SECURITY_SERVICE = "securityService";
    public static final String CNTX_ARTICLE_SERVICE = "articleService";
    public static final String CNTX_USER_SERVICE = "userService";
    public static final String CNTX_FILE_SERVICE = "fileService";

    public static final String COOKIE_AUTHORIZED_NAME = "uuid-user";
    public static final String SESSION_USER_ATTRIBUTE_NAME = "auth_user";
    public static final String REQUEST_ATTRIBUTE_AUTHORIZED = "authorized";  // renaming here is dangerous!! then edit jsp files

    public static final String STORAGE_URL = "somehow/webapp/resources/articles/";
}
