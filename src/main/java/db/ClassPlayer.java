package db;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class ClassPlayer implements  Cloneable{
    private int mId;
    private  int mIdTeam;
    public String getGender() {
        return mGender;
    }
    public void setGender(String mGender) {
        this.mGender = mGender;
    }
    private String mGender;
    private String mFirstName;
    private String mLastName;
    private java.sql.Date mBirthDay;
    private  String mEmail;
    private String mDescription;
    private String mCountry;
    private String mCity;
    private String mAddress;
    private int mPostal;
    private  int mPhone;
    private  int mPhoneEmergency;
    private  int mHeight;
    private  int mWeight;
    private  int mAge;
    private String mPathProfile;
    public ClassPlayer(Integer mId){ this.mId =  mId;}
    public ClassPlayer(int mIdTeam,String mGender,String mFirstName, String mLastName, String mEmail,java.sql.Date mBirthDay,String mCountry,String mCity, String mAddress, int mPostal, int mPhone, int mPhoneEmergency, int mHeight, int mWeight, String mPosition,String mDescription,String pathProfile) {
        this.mIdTeam        = mIdTeam;
        this.mGender        = mGender;
        this.mFirstName     = mFirstName;
        this.mLastName      = mLastName;
        this.mEmail         = mEmail;
        this.mBirthDay      = mBirthDay;
        this.mCountry       = mCountry;
        this.mCity          = mCity;
        this.mAddress       = mAddress;
        this.mPostal        = mPostal;
        this.mPhone         = mPhone;
        this.mPhoneEmergency = mPhoneEmergency;
        this.mHeight         = mHeight;
        this.mWeight         = mWeight;
        this.mPosition       = mPosition;
        this.mDescription    =  mDescription;
        this.mMedias.add(new ClassMedia(pathProfile));
        this.mAvailable = true;
    }
    public String getPosition() {return mPosition;}
    public void setPosition(String mPosition) {
        this.mPosition = mPosition;
    }
    private String mPosition;
    private  boolean mHurt;
    private  boolean mAvailable;
    private ConnexionASdb connexionASdb;
    private ArrayList<ClassMedia> mMedias = new ArrayList<>();
    public void setId(int mId) { this.mId = mId; }
    public void initialise() throws Exception {
        connexionASdb =  new ConnexionASdb();
        String sqlReqPlayer="SELECT * FROM ba_player WHERE id="+mId;
        Statement statementPlayer = connexionASdb.getStatement();
        ResultSet resultSet = statementPlayer.executeQuery(sqlReqPlayer);
        if(resultSet.next()){
            mId=resultSet.getInt("id");
            mIdTeam=resultSet.getInt("idTeam");
            mGender = resultSet.getString("gender");
            mFirstName=resultSet.getString("lastName");
            mLastName=resultSet.getString("firstName");
            mBirthDay=resultSet.getDate("birthday");
            mDescription=resultSet.getString("description");
            mPhone=resultSet.getInt("phone");
            mPhoneEmergency=resultSet.getInt("phoneEmergency");
            mEmail=resultSet.getString("email");
            mCountry=resultSet.getString("country");
            mAddress=resultSet.getString("address");
            mCity=resultSet.getString("city");
            mPostal=resultSet.getInt("postal");
            mHeight=resultSet.getInt("height");
            mWeight=resultSet.getInt("weight");
            mPosition=resultSet.getString("position");
            mHurt=resultSet.getBoolean("hurt");
            mAvailable=resultSet.getBoolean("available");
            //On charge les medias du joueur
            mMedias =  ClassMedia.loadMedia(mId,"player");
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
    public void setBirthDay(java.sql.Date mBirthDay) {
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
    public void setCity(String mCity) { this.mCity = mCity;}
    public void setCountry(String mCountry) { this.mCountry = mCountry;}
    public int getId() { return mId;}
    public int getIdTeam() {return mIdTeam; }
    public int getAge(){ return Period.between(mBirthDay.toLocalDate(), LocalDate.now()).getYears(); }
    public String getFirstName(){return mFirstName;}
    public String getLastName(){return mLastName;}
    public String getName() { return mFirstName +" "+ mLastName;}
    public LocalDate getBirthDay() {
        return mBirthDay.toLocalDate();
    }
    public String getEmail() {
        return mEmail;
    }
    public String getDescription() {
        return mDescription;
    }
    public String getAddress() {
        return mAddress;
    }
    public int getPostal() { return mPostal;}
    public int getPhone() {
        return mPhone;
    }
    public int getPhoneEmergency() {
        return mPhoneEmergency;
    }
    public int getHeight() {
        return mHeight;
    }
    public int getWeight() {
        return mWeight;
    }
    public String getCity() { return mCity;}
    public String getCountry() {return mCountry;}
    public Boolean isHurt() { return mHurt; }
    public void isHurt(Boolean b) { mHurt = b; }
    public Boolean isAvailable(){ return mAvailable;}
    public void isAvailable(Boolean b) { mAvailable = b;}
    public String getPathProfile() {  return (!mMedias.isEmpty()) ? mMedias.getFirst().getPath() : ""; }
    public ArrayList<ClassMedia> getMedias() {
        return mMedias;
    }
    @Override
    public String toString() {
        return "(" + mIdTeam +
                ",'" + mGender + '\'' +
                ",'" + mLastName + '\'' +
                ",'" + mFirstName + '\'' +
                ",'" + mEmail + '\'' +
                ",'" + mBirthDay +'\'' +
                ",'" + mDescription + '\'' +
                ",'" + mCountry + '\'' +
                ",'" + mCity + '\'' +
                ",'" + mAddress + '\'' +
                "," + mPostal +
                "," + mPhone +
                "," + mPhoneEmergency +
                "," + mHeight +
                "," + mWeight +
                ",'" + mPosition + '\'' +
                "," + mHurt +
                "," + mAvailable +
                ')';
    }
    public String toString(boolean toUpdate){
        return mGender + ','+ mLastName + ',' + mFirstName + ',' +mEmail + ',' +mBirthDay +',' +mDescription + ',' +
                 mCountry + ',' +mCity + ',' +mAddress + ',' + mPostal +"," + mPhone +"," + mPhoneEmergency +"," + mHeight +"," + mWeight +
                "," + mPosition;
    }
    @Override
    public ClassPlayer clone() {
        try {
            return  (ClassPlayer) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
