package db;

import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class ClassCoach {
    private int mId;
    private String mName;
    private String mLastName;

    public String getEmail() {return mEmail; }

    private String mEmail;
    private Date mBirthday;
    private int mPhone;
    private String mNationality;
    private String mCity;
    private String mAddress;
    private  int mPostal;
    private  String mDescription;
    //
    // mReferences, mTeamCoatcheds, mAwards et mTitles sont des fields de type JSON en bd donc il faudra faire une deserialisation pour exploiter leur different contenu
    //
    private String mReferences;
    private String mTeamsCoatched;
    private String mAwards;
    private String mLogin;
    private String mPassword;
    private  String mtitles;
    private ArrayList<ClassMedia> mMedias;
    private ConnexionASdb connexionASdb;
    private volatile  static ClassCoach instance;
    private ClassCoach() {}
    public static ClassCoach getInstance() throws Exception {
        if(instance == null){
            synchronized (ClassCoach.class){
                if (instance == null){
                    instance = new ClassCoach();
                    instance.setConnexionASdb(new ConnexionASdb());
                }
            }
        }
        return instance;
    }
    public void initialiseCoatch() throws Exception {
         String sqlReq =  "SELECT * FROM ba_coach WHERE id="+mId;
         PreparedStatement statement =  connexionASdb.getConnection().prepareStatement(sqlReq);
         ResultSet resultSet = statement.executeQuery();
        if (resultSet != null){
            while ( resultSet.next()){
                mId =  resultSet.getInt("id");
                mName = resultSet.getString("firstName");
                mLastName = resultSet.getString("lastName");
                mEmail =  resultSet.getString("email");
                mBirthday = resultSet.getDate("birth");
                mPhone =  resultSet.getInt("phone");
                mNationality = resultSet.getString("nationality");
                mCity = resultSet.getString("city");
                mAddress = resultSet.getString("address");
                mDescription =  resultSet.getString("description");
                mPostal =  resultSet.getInt("postal");
                mReferences =  resultSet.getString("referencesPlayers");
                mAwards = resultSet.getString("teamsCoached");
                mtitles =  resultSet.getString("titles");
            }
        }
        mMedias = ClassMedia.loadMedia(this.mId,"coach");
    }
    public String loadMedia(String name) throws Exception {
        String path = "";
        mMedias =  ClassMedia.loadMedia(mId,"coach");
        for(ClassMedia occ : mMedias){
            if (occ.getDescription().equals(name)){
                path = occ.getPath();
                break;
            }
        }
        return path;
    }
    public void update(String[] fields, String[] values) throws SQLException {
        connexionASdb.update(instance.mId, "ba_coach",fields,values);
    }
    public void delete() throws SQLException {
        connexionASdb.delete("ba_coach", mId);
    }
    public void createCoatch(String[] fields,String[] values) throws SQLException, NoSuchAlgorithmException {
        connexionASdb.insert("ba_coach",fields,values);
    }
    public void setId(int mId) { this.mId = mId; }
    public int getId() {return mId;}
    public void setConnexionASdb(ConnexionASdb connexionASdb) {this.connexionASdb = connexionASdb;}

    public static void main(String[] args) throws Exception {
        ClassCoach coatch = ClassCoach.getInstance();
        String[] fields =  {"lastName","firstName","birth","phone","email", "address","login","password"};
        String[] values = {"Poppovitch","gregs","19450112","0623542354","gregs@vitch.fr","24 Rue Houston","Pgregs","AZERTY"};
       // coatch.createCoatch(fields,values);

        fields = new String[]{"description"};
        values =  new String[]{"I am a dynamic coach"};
        coatch.update(fields,values);
    }
    public String getName() { return mName;}
    public String getLastName(){return  mLastName;}
}
