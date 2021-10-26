package ru.kpfu.itis.renett.service;

public class Constants {
    public static final String PROPS_FILE_PATH = "WEB-INF/properties/appdb.properties";
    public static final String PROPS_FILE_PATH2 = "/home/renett/Repositories/3rd-sem/семестровка/semester-work-java3/src/main/webapp/WEB-INF/properties/appdb.properties";

    public static final String DB_URL ="db.url";
    public static final String DB_USERNAME = "db.username";
    public static final String DB_PASSWORD = "db.password";
    public static final String DB_DRIVER = "db.driver";
    public static final String DB_POOL_SIZE = "db.hikari.max-pool-size";

    public static final String CNTX_SECURITY_SERVICE = "securityService";
    public static final String CNTX_DATA_SOURCE = "dataSource";

    public static final String COOKIE_AUTHORIZED_NAME = "uuid";
    public static final String SESSION_USER_ATTRIBUTE_NAME = "auth_user";

    public static final String CNTX_USER_SERVICE = "usersRepo";
}
