package db;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

public class ConnexionASdb {
    private Connection connection;
    private Statement statement;
    private  PreparedStatement preparedStatement;
    private ResultSet resultSet;
    public ConnexionASdb() throws Exception{
        Properties properties =  new Properties();
        try(FileInputStream file = new FileInputStream("src/main/resources/Config/conf.properties")) {
            properties.load(file);
        }
        Class.forName(properties.getProperty("jdbc.driver.class"));
        String url =  properties.getProperty("jdbc.url");
        String login =  properties.getProperty("jdbc.login");
        String password =  properties.getProperty("jdbc.password");
        this.connection = DriverManager.getConnection(url,login,password);
        this.statement = connection.createStatement();

    }
    public Connection getConnection(){return this.connection;}
    public void login(){}
    public void logon(){}
    public void delete(String table,int idOcc) throws SQLException {
        String sqlReq = "DELETE FROM ? WHERE id=?";
        preparedStatement =  connection.prepareStatement(sqlReq);
        preparedStatement.setString(1,table);
        preparedStatement.setInt(2,idOcc);
        preparedStatement.executeQuery();
    }
    public void update(int idOcc, String table,String[] fields, String[] values) throws SQLException {
        StringBuilder setParameters = new StringBuilder();
        for (int i=1;i<values.length;i++){
            setParameters.append(fields[i]=values[i]+",");
        }
        String sqlReq  =  "UPDATE ? SET ? WHERE id =?";
        preparedStatement =  connection.prepareStatement(sqlReq);
        preparedStatement.setString(1,table);
        preparedStatement.setString(2,setParameters.toString());
        preparedStatement.setInt(3,idOcc);
        preparedStatement.executeQuery();
    }
    public Statement getStatement(){return statement;}
}

