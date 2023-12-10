package db;

import java.util.Date;

enum Type{
    Training,
    Match,
    Other,
}
enum Importance{
    Low,
    Medium,
    Hight,
}
public class ClassEvent {
    private int mId;
    private  String mName;
    private Date currentDate;
    private  Type mType;
    private Importance mImportance;
    private String mDetails;

}
