package dao;

import util.DbUtil;
import util.FileUtil;
import util.PropertyLoader;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SetupDao {
    String dbUrl = PropertyLoader.getProperty("javax.persistence.jdbc.url");

    public void createSchema() {
        String statements = FileUtil.readFileFromClasspath("schema.sql");

        try {
            //TODO: if this changes to Postgres or MySQL, this needs to change so
            //TODO: that when connection opened, it will use correct credentials
            DbUtil.insertFromFile(DriverManager.getConnection(dbUrl), statements);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
