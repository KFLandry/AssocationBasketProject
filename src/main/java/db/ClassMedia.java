package db;

import java.sql.*;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Date;

public class ClassMedia {
    private  int mId;
    private  String mName;
    private  String mDescritption;
    private Date mDateCreation;
    private int mSize;
    private String mTypeMime;
    private  String mPath;
    private ConnexionASdb connexionASdb;
    public ClassMedia() throws Exception {
        connexionASdb = new ConnexionASdb();
    }
    public ArrayList<ClassMedia> loadMedia(int id,String assosTable) throws Exception {
        ArrayList<ClassMedia> tempMedias =  new ArrayList<ClassMedia>();
        String sqlReq = "SELECT * FROM ba_media JOIN ba_middlemedia"+assosTable+" ON (ba_media.id=ba_middlemedia"+assosTable+".idmedia) WHERE ba_middlemedia"+assosTable+".id"+assosTable +"=?" ;
        Connection connection = connexionASdb.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet =  statement.executeQuery(sqlReq);
        if (resultSet != null){
            while (resultSet.next()){
                ClassMedia temp = new ClassMedia();
                temp.setId(resultSet.getInt("id"));
                temp.setDescritption(resultSet.getString("Description"));
                temp.setDateCreation(resultSet.getDate("dateCreation"));
                temp.setName(resultSet.getString("name"));
                temp.setSize(resultSet.getInt("size"));
                temp.setPath(resultSet.getString("path"));
                tempMedias.add(temp);
            }
        }
        return  tempMedias;
    }
    public void delete(int id) throws SQLException {
        String sqlReq = "DELETE FROM ba_media WHERE id ="+id;
        Connection connection = connexionASdb.getConnection();
        Statement statement = connection.createStatement();
        statement.executeQuery(sqlReq);
    }
    public  void create(String[] fields, String[] values) throws SQLException {
        StringBuilder setValues  = new StringBuilder();
        StringBuilder setFiels = new StringBuilder();
        for (int i=1;i<values.length;i++){
            setFiels.append(fields[i]).append(",");
            setValues.append(values[i]).append(",");
        }
        String sqlReq = "INSERT INTO ba_media (?) VALUES (?)" ;
        Connection connection = connexionASdb.getConnection();
        PreparedStatement statement = connection.prepareStatement(sqlReq);
        statement.setString(1,setFiels.toString());
        statement.setString(2,setValues.toString());
        statement.executeQuery();
    }
    public  void update(int id, String[] fields, String[] values){

    }
    public void setId(int mId) {
        this.mId = mId;
    }
    public void setName(String mName) {
        this.mName = mName;
    }
    public void setDateCreation(Date mdateCreation) {
        this.mDateCreation = mdateCreation;
    }
    public void setDescritption(String mDescritption) {
        this.mDescritption = mDescritption;
    }
    public void setTypeMime(String mTypeMime) {
        this.mTypeMime = mTypeMime;
    }
    public void setSize(int mSize) {
        this.mSize = mSize;
    }
    public void setPath(String mPath) {
        this.mPath = mPath;
    }
}
