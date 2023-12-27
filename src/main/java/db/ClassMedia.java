package db;

import java.sql.*;
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
    private static  ConnexionASdb connexionASdb;
    public ClassMedia(){
    }
    public static ArrayList<ClassMedia> loadMedia(int id,String assosTable) throws Exception {
        ConnexionASdb connexionASdb = new ConnexionASdb();
        ArrayList<ClassMedia> tempMedias =  new ArrayList<ClassMedia>();
        String sqlReq = "SELECT * FROM ba_media JOIN ba_middlemedia"+assosTable+" ON (ba_media.id=ba_middlemedia"+assosTable+".idmedia) WHERE ba_middlemedia"+assosTable+".id"+assosTable +"="+id;
        Connection connection = connexionASdb.getConnection();
        PreparedStatement statement = connection.prepareStatement(sqlReq);
        ResultSet resultSet =  statement.executeQuery();
        if (resultSet != null){
            while (resultSet.next()){
                ClassMedia temp = new ClassMedia();
                temp.setId(resultSet.getInt("id"));
                temp.setDescritption(resultSet.getString("Description"));
                temp.setDateCreation(resultSet.getDate("dateCreation"));
                temp.setTypeMime(resultSet.getString("typeMime"));
                temp.setName(resultSet.getString("Description"));
                temp.setSize(resultSet.getInt("size"));
                temp.setPath(resultSet.getString("path"));
                tempMedias.add(temp);
            }
        }
        return  tempMedias;
    }
    public void create(String[] fields, String[] values) throws SQLException {
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
    public void setId(int mId){this.mId = mId;}
    public void setName(String mName) {this.mName = mName;}
    public void setDateCreation(Date mdateCreation){this.mDateCreation = mdateCreation;}
    public void setDescritption(String mDescritption){this.mDescritption = mDescritption;}
    public void setTypeMime(String mTypeMime){this.mTypeMime = mTypeMime;}
    public void setSize(int mSize){this.mSize = mSize;}
    public void setPath(String mPath) {this.mPath = mPath;}

    public String getDescritption() {return mDescritption;}

    public String getPath() { return mPath; }
}
