package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class ClassCategory {
    private  int mId;
    private  int mIdCoach;
    private String mName;
    private int mAge;
    private String mGender;
    private String mAwards;
    private Date mDateCreation;
    private String mStory;
    private ArrayList<ClassTeam> mTeams;
    private ClassTeam mCurrentTeam;
    private ClassStatistique mStatistique;
    private ConnexionASdb connexionASdb;
    public ClassCategory(int id) throws Exception { mId = id;connexionASdb = new ConnexionASdb();}
    public void initialise(String idCoach) throws Exception {
        String sqlReq =  "SELECT * FROM ba_category WHERE ba_category.idCoach="+idCoach;
        Statement statement = connexionASdb.getStatement();
        ResultSet resultSet = statement.executeQuery(sqlReq);
        if (resultSet != null){
                mId=resultSet.getInt("id");
                mIdCoach=resultSet.getInt("idCoach");
                mName=resultSet.getString("name");
                mGender=resultSet.getString("gender");
                mAge=resultSet.getInt("age");
                mAwards=resultSet.getString("awards");
                mDateCreation=resultSet.getDate("dateCreation");
                mStory=resultSet.getString("story");
                // On ne charge par entierement l'equipe ici pour optimiser l'app.
                String sqlReqTeam = "SELECT id FROM ba_team WHERE idCategory="+resultSet.getInt("idCategory");
                ResultSet resTeam =  connexionASdb.getStatement().executeQuery(sqlReqTeam);
                if (resTeam != null){
                    ArrayList<ClassTeam> teams =  new ArrayList<ClassTeam>();
                    while (resTeam.next()){
                        teams.add(new ClassTeam(resTeam.getInt("id")));
                    }
                    mTeams = teams;
            }
        }
    }
    public void add(String []fields,String[] values) throws Exception {
        int id = connexionASdb.insert("ba_team",fields,values);
        mTeams.add(new ClassTeam(id));
    }
    public void update(String[] fields,String[] values) throws SQLException {
        connexionASdb.update(mCurrentTeam.getId(),"ba_team",fields,values);
    }
    public void delete(int id) throws SQLException {
        mTeams.remove(mCurrentTeam);
        connexionASdb.delete("ba_team",id);
    }
    public void setAwards(String mAwards) {
        this.mAwards = mAwards;
    }
    public void setId(int mId) { this.mId = mId;}
    public void setName(String mName) {this.mName = mName;}
    public void setCurrentTeam(ClassTeam mCurrentTeam) { this.mCurrentTeam = mCurrentTeam; }
    public void setAge(int mAge) {  this.mAge = mAge; }
    public void setGender(String mGender) {  this.mGender = mGender;  }
    public void setStatistique(ClassStatistique mStatistique) { this.mStatistique = mStatistique; }
    public void setDateCreation(Date mDateCreation) {  this.mDateCreation = mDateCreation; }
    public void setStory(String mStory) { this.mStory = mStory; }
    public void setIdCoach(int mIdCoach) {this.mIdCoach = mIdCoach; }
    public int getId() { return mId; }
    public void setmCurrentTeam(ClassTeam mCurrentTeam) {  this.mCurrentTeam = mCurrentTeam; }
}
