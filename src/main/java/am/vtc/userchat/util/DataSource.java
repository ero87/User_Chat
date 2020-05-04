package am.vtc.userchat.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    private static final HikariDataSource hikariDataSource;
    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(Settings.getInstance().getString("db.url"));
        config.setDriverClassName(Settings.getInstance().getString("db.driver"));
        config.setUsername(Settings.getInstance().getString("db.user"));
        config.setPassword(Settings.getInstance().getString("db.password"));
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setConnectionTimeout(5000);
        config.setMaximumPoolSize(150);
        config.setMinimumIdle(10);
        config.setMaxLifetime(300000);
        config.setIdleTimeout(150000);
        hikariDataSource = new HikariDataSource(config);
    }

    private DataSource() {

    }

    public static Connection getConnection() throws SQLException {
        long start = System.nanoTime();
        Connection connection = hikariDataSource.getConnection();
        long end = System.nanoTime();
        time += (end - start);
        System.out.println((end - start) / 1000000.0);
        return connection;
    }
    public static long time = 0;
 /*   private DataSource() {
        try {
            Class.forName(Settings.getInstance().getString("db.driver"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DataSource getInstance() {
        return Helper.FACTORY;
    }

    public Connection openConnection() throws SQLException {
        return DriverManager.getConnection(Settings.getInstance().getString("db.url"),
                Settings.getInstance().getString("db.user"),
                Settings.getInstance().getString("db.password"));
    }

    private static class Helper {
        private static final DataSource FACTORY = new
                DataSource();
    }

  */
}
