package db;

import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;

public class ConnexionASdb {
    private Connection connection;
    private Statement statement;
    private  PreparedStatement preparedStatement;
    public ConnexionASdb() throws Exception {
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
    public ReturnCheck checkCredentials(String login, String passWord) throws NoSuchAlgorithmException {
        ReturnCheck ReturnCheck =  new ReturnCheck();
        try{
            String reqLogin =  "SELECT id,password FROM ba_coach WHERE login=?";
            preparedStatement = connection.prepareStatement(reqLogin);
            preparedStatement.setString(1,login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String mdp  = hash(passWord);
                if (resultSet.getString("password").equals(mdp)){
                    ReturnCheck.id =   resultSet.getInt("id");
                }else ReturnCheck.text = "Wrong password";
            }else  ReturnCheck.text =  "Don't exist";
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return ReturnCheck;
    }
    public int insert(String table,String[] fields, String[] values) throws SQLException, NoSuchAlgorithmException {
        StringBuilder setValues  = new StringBuilder();
        StringBuilder setFiels = new StringBuilder();
        for (int i=0;i<fields.length; i++){
            // Hachage du mot de passe pour table ba_coach
            if(Objects.equals(table, "ba_coach")){
                if(fields[i].equals("password")){
                   values[i] =  hash(values[i]);
                }
            }
            setFiels.append(fields[i]).append(",");
            setValues.append("'").append(values[i]).append("',");
        }
        setFiels.deleteCharAt((setFiels.length()-1));
        setValues.deleteCharAt((setValues.length()-1));
        String sqlReq = "INSERT INTO "+table+" ("+setFiels+") VALUES ("+setValues+");" ;

        PreparedStatement statement = connection.prepareStatement(sqlReq);
        return statement.executeUpdate();
    }
    public int  delete(String table,int idOcc) throws SQLException {
        String sqlReq = "DELETE FROM "+table+" WHERE id="+idOcc;
        preparedStatement =  connection.prepareStatement(sqlReq);
      // preparedStatement.setString(1,table);
      // preparedStatement.setInt(2,idOcc);
        return preparedStatement.executeUpdate();
    }
    public static String hash(String word ) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(word.getBytes());
        byte[] byteData = md.digest();
        StringBuilder hexString = new StringBuilder();
        for (int i=0;i<byteData.length;i++) {
            String hex=Integer.toHexString(0xff & byteData[i]);
            if(hex.length()==1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
    public int update(int idOcc, String table, String[] fields, String[] values) throws SQLException {
        StringBuilder setParameters = new StringBuilder();
        for (int i=0;i<values.length;i++){
            setParameters.append(fields[i]).append("='").append(values[i]).append("',");
        }
        setParameters.deleteCharAt((setParameters.length()-1));
        String sqlReq  =  "UPDATE "+table+" SET "+setParameters+" WHERE id ="+idOcc+";";
        preparedStatement =  connection.prepareStatement(sqlReq);
        return preparedStatement.executeUpdate();
    }
    public Statement getStatement(){return statement;}
}

