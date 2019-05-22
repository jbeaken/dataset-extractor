package org.jack;

import com.mysql.cj.jdbc.MysqlDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

@Slf4j
public class Repository {

    private MysqlDataSource dataSource;
    private Properties props;
    private Connection connection;

    public Repository(Properties props) throws IOException, SQLException {

        this.props = props;

        init();
    }



    public ResultSet getResultSet() throws SQLException {

        connection = dataSource.getConnection();
        connection.setReadOnly( true );

        String tableName = props.getProperty( "tablename" );

        log.info("Using table name {}", tableName);

        String sanitisedTableName = whitelist(tableName);

        log.info("Sanitised table name {}", sanitisedTableName);

        PreparedStatement preparedStatement = connection.prepareStatement("select * from " + sanitisedTableName);

        ResultSet rs = preparedStatement.executeQuery();

        return rs;
    }

    private String whitelist(String tableName) {
        return tableName.replaceAll("/[^0-9a-zA-Z_]/", "");
    }


    private void init() throws IOException, SQLException {

        dataSource = new MysqlDataSource();

        dataSource.setURL(props.getProperty("url"));
        dataSource.setUser(props.getProperty("user"));
        dataSource.setPassword(props.getProperty("password"));

        dataSource.setReadOnlyPropagatesToServer(true);
    }

    public void close() throws SQLException {

        connection.close();
    }
}
