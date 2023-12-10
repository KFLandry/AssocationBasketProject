package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.HashMap;

public class ClassTeam {
    private int mId;
    private  String mName;
    private  String  mGamePriority;
    private int mTotalPlayer;
    private String mGamePlane;
    private ClassStructure.Awards mAwards;
    public ArrayList<ClassPlayer> mPlayers;
    private  ClassPlayer mCurrentPlayer;
    private  ConnexionASdb connexionASdb;
    public ClassTeam() throws Exception {
        connexionASdb = new ConnexionASdb();
    }
    public void initialiseTeam() throws SQLException {
        ClassPlayer tempPlayer = new ClassPlayer();
        String sqlReqCoach = "SELECT * FROM ba_Team WHERE id"+mId;
        Statement statementCoach= connexionASdb.getStatement();
        ResultSet res =  statementCoach.executeQuery(sqlReqCoach);
        if(res != null){
        //
            this.mId =  res.getInt("id");
            this.mGamePlane = res.getString("gamePlan");
            this.mGamePriority = res.getString("gamePriority");
        // Filling of  mPlayers...
            String sqlReqPlayer="SELECT * ba_player JOIN ba_team ON (ba_player.idTeam = ba_team.id) WHERE ba_Player.idPlayer="+res.getInt("idPlayer");
            Statement statementPlayer = connexionASdb.getStatement();
            ResultSet resultSet = statementPlayer.executeQuery(sqlReqPlayer);
            while(resultSet.next()){
                tempPlayer.setId(resultSet.getInt("id"));
                tempPlayer.setIdTeam(resultSet.getInt("idTeam"));
                tempPlayer.setFirstName(resultSet.getString("LastName"));
                tempPlayer.setLastName(resultSet.getString("firstName"));
                tempPlayer.setBirthDay(resultSet.getDate("birthday"));
                tempPlayer.setDescription(resultSet.getString("description"));
                tempPlayer.setPhone(resultSet.getInt("phone"));
                tempPlayer.setPhoneEmergency(resultSet.getInt("phoneEmergency"));
                tempPlayer.setEmail(resultSet.getString("email"));
                tempPlayer.setAddress(resultSet.getString("address"));
                tempPlayer.setPostal(resultSet.getInt("address"));
                tempPlayer.setHeight(resultSet.getInt("height"));
                tempPlayer.setWeight(resultSet.getInt("weight"));
                tempPlayer.setHurt(resultSet.getBoolean("hurt"));
                tempPlayer.setAvailable(resultSet.getBoolean("available"));
                mPlayers.add(tempPlayer);
            }
        }
    }
}
