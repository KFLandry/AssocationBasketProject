package db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class ClassCoatch {
    private int mId;
    private String mName ;
    private int mPhone;
    private String mAddress;
    private  int mPostal;
    private  String mDescription;
    //
    // mReferences,mTeamCoatcheds,mAwards et mTitles sont des fields de type JSON en bd donc il faudra faire une deserialisation pour expoloiter leur different contenu
    //
    private String mReferences ;
    private String mTeamsCoatched;
    private String mAwards;
    private String mLogin;
    private String mPassword;
    private  String mtitles;
    private ConnexionASdb connexionASdb ;
    private static ClassCoatch instance = new ClassCoatch();
    private ClassCoatch() {}
    public static ClassCoatch getInstance() throws Exception {return instance;}
    public void initialiseCoatch(String login, String passWord) throws Exception {
         instance.connexionASdb =  new ConnexionASdb();
         String sqlReq =  "SELECT * FROM  WHERE login=? and password=?";
         PreparedStatement statement =  instance.connexionASdb.getConnection().prepareStatement(sqlReq);
         statement.setString(1,login);
         statement.setString(2,passWord);
         ResultSet resultSet = statement.executeQuery();
        if (resultSet != null){
            while ( resultSet.next()){
                instance.mId =  resultSet.getInt("id");
                instance.mName = resultSet.getString("name");
                instance.mPhone =  resultSet.getInt("phone");
                instance.mAddress = resultSet.getString("address");
                instance.mDescription =  resultSet.getString("description");
                instance.mPostal =  resultSet.getInt("postal");
                instance.mReferences =  resultSet.getString("referencesPlayer");
                instance.mAwards = resultSet.getString("teamsCoached");
                instance.mtitles =  resultSet.getString("titles");
            }
        }
    }
    public String hash() throws NoSuchAlgorithmException {
        String password = "123456789";
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());
        byte[] byteData = md.digest();
        StringBuilder hexString = new StringBuilder();
        for (int i=0;i<byteData.length;i++) {
            String hex=Integer.toHexString(0xff & byteData[i]);
            if(hex.length()==1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
    public void update(String[] fields, String[] values){
        StringBuilder setParameters = new StringBuilder();
        for (int i=1;i<values.length;i++){
                setParameters.append(fields[i]=values[i]+",");
            }
        String sqlReq  =  "UPDATE ba_coach SET ? WHERE id ="+instance.mId;
        try{
            Connection connection =  instance.connexionASdb.getConnection();
            PreparedStatement statement = connection.prepareStatement(sqlReq);
            statement.setString(1, setParameters.toString());
            statement.executeUpdate();
        }catch (SQLException ex){
            System.out.println(ex);
        }
    }
    public void delete() throws SQLException {
        Connection connection = instance.connexionASdb.getConnection();
        Statement statement = connection.createStatement();
        statement.executeQuery("DELETE FROM ba_coach WHERE id="+instance.mId);
        }
    public void createCoatch(String[] fields,String[] values){
        StringBuilder setValues  = new StringBuilder();
        StringBuilder setFiels = new StringBuilder();
        for (int i=1;i<values.length;i++){
            setFiels.append(fields[i]).append(",");
            setValues.append(values[i]).append(",");
            }
        String sqlReq = "INSERT INTO ba_coach (?) VALUES (?)" ;
        Connection connection =  instance.connexionASdb.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sqlReq);
            statement.setString(1, setFiels.toString());
            statement.setString(2, setValues.toString());
            statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        }
    public static void main(String[] args) throws Exception {
        ClassCoatch coatch = ClassCoatch.getInstance();
    }

}
