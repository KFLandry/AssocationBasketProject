package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class ClassPlayer {
    private int mId;
    private  int mIdTeam;
    private String mFirstName;
    private String mLastName;
    private Date mBirthDay;
    private  String mEmail;
    private String mDescription;
    private String mAddress;
    private int mPostal;
    private  int mPhone;
    private  int mPhoneEmergency;
    private  int mHeight;
    private  int mWeight;
    private  boolean mHurt;
    private  boolean mAvailable;
    private ConnexionASdb connexionASdb;
    private ArrayList<ClassMedia> mMedias;
    public ClassPlayer(int id){}
    public void setId(int mId) {
        this.mId = mId;
    }
    public void initialise() throws SQLException {
        String sqlReqPlayer="SELECT * FROM ba_player WHERE id="+mId;
        Statement statementPlayer = connexionASdb.getStatement();
        ResultSet resultSet = statementPlayer.executeQuery(sqlReqPlayer);
        if(resultSet.next()){
            mId=resultSet.getInt("id");
            mIdTeam=resultSet.getInt("idTeam");
            mFirstName=resultSet.getString("LastName");
            mLastName=resultSet.getString("firstName");
            mBirthDay=resultSet.getDate("birthday");
            mDescription=resultSet.getString("description");
            mPhone=resultSet.getInt("phone");
            mPhoneEmergency=resultSet.getInt("phoneEmergency");
            mEmail=resultSet.getString("email");
            mAddress=resultSet.getString("address");
            mPostal=resultSet.getInt("address");
            mHeight=resultSet.getInt("height");
            mWeight=resultSet.getInt("weight");
            mHurt=resultSet.getBoolean("hurt");
            mAvailable=resultSet.getBoolean("available");
        }
    }
    public void setIdTeam(int mIdTeam) {
        this.mIdTeam = mIdTeam;
    }
    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
    }
    public void setAvailable(boolean mAvailable) {
        this.mAvailable = mAvailable;
    }
    public void setBirthDay(Date mBirthDay) {
        this.mBirthDay = mBirthDay;
    }
    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }
    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }
    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }
    public void setHeight(int mHeight) {
        this.mHeight = mHeight;
    }
    public void setHurt(boolean mHurt) {
        this.mHurt = mHurt;
    }
    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }
    public void setPhone(int mPhone) {
        this.mPhone = mPhone;
    }
    public void setPhoneEmergency(int mPhoneEmergency) {
        this.mPhoneEmergency = mPhoneEmergency;
    }
    public void setPostal(int mPostal) {
        this.mPostal = mPostal;
    }
    public void setWeight(int mWeight) {
        this.mWeight = mWeight;
    }
    public void setMedias() throws Exception { mMedias =  ClassMedia.loadMedia(this.mId,"Team");}

    public int getId() { return mId;}
}
