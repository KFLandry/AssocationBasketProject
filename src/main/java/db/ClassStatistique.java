package db;

import java.sql.*;
import java.util.ArrayList;

public class ClassStatistique {
    private  int mId;
    private int mEfficientRatio;
    private int mScore;
    private int mBlocks;
    private int mDecisivePass;
    private Time mPlayedTime;
    private int mMatchPlayed;
    private int mVictory;
    private  ConnexionASdb connexionASdb;
    private ArrayList<ClassStatistique> statistiques;
    public ClassStatistique() throws Exception {connexionASdb =  new ConnexionASdb();}

    public  void initaliseStatistique(int id,String fieldName) throws Exception {
        String sqlReq =  "SELECT * FROM ba_statistique WHERE "+fieldName+"="+id;ResultSet resultSet =  connexionASdb.getStatement().executeQuery(sqlReq);
        while(resultSet != null && resultSet.next()){
            ClassStatistique temp =  new ClassStatistique();
            temp.setId(resultSet.getInt("id"));
            temp.setEfficientRatio(resultSet.getInt("efficiencyRatio"));
            temp.setScore(resultSet.getInt("score"));
            temp.setDecisivePass(resultSet.getInt("decisivePass"));
            temp.setPlayedTime(resultSet.getTime("PlayedTime"));
            temp.setMatchPlayed(resultSet.getInt("MatchPlayed"));
            temp.setBlocks(resultSet.getInt("block"));
            temp.setVictory(resultSet.getInt("victory"));
            statistiques.add(temp);
        }
    }
    public void setId(int mId) {this.mId = mId; }
    public void setBlocks(int mBlocks) {this.mBlocks = mBlocks;}
    public void setDecisivePass(int mDecisivePass) {this.mDecisivePass = mDecisivePass;}
    public void setEfficientRatio(int mEfficientRatio) {this.mEfficientRatio = mEfficientRatio;}
    public void setMatchPlayed(int mMatchPlayed) {this.mMatchPlayed = mMatchPlayed;}
    public void setPlayedTime(Time mPlayedTime) {this.mPlayedTime = mPlayedTime;}
    public void setScore(int mScore) {this.mScore = mScore;}
    public void setVictory(int mVictory) {this.mVictory = mVictory;}
}
