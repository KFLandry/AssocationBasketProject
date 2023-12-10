package db;


import java.sql.Struct;
import java.util.ArrayList;
import java.util.HashMap;

public class ClassCategory {
    private  int mId;
    private String mName;
    private int mAge;
    private String mGender;
    private ClassStructure.Awards mAwards;
    private ArrayList<ClassTeam> mTeams;
    private ClassTeam mCurrentTeam;
    private ClassStatistique mStatistique;
    public ClassCategory(){
    }

}
