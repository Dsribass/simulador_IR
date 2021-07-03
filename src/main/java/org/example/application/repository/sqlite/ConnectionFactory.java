package org.example.application.repository.sqlite;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import org.sqlite.SQLiteDataSource;

public class ConnectionFactory implements AutoCloseable{
    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static Statement statement;

    public static Connection createConnection() {
        try {
            instantiateConnection();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return connection;
    }

    public static void instantiateConnection() throws SQLException {
        SQLiteDataSource sqlite = new SQLiteDataSource();
        sqlite.setUrl("jdbc:sqlite:database.db");
        if(connection == null) {
            connection = sqlite.getConnection();
        }
    }

    public static PreparedStatement preparedStatement(String sql){
        try {
            preparedStatement = createConnection().prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }

    public static Statement statement(){
        try {
            statement = createConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

    @Override
    public void close() throws Exception {
        if(preparedStatement != null) preparedStatement.close();
        if(statement != null) statement.close();
        if(connection != null) connection.close();
    }
}
