package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class ClassEvent {
    private int mId;
    private  String mName;
    private Date currentDate;

    public String getSubject() {return mSubject;}

    public void setSubject(String mSubject) { this.mSubject = mSubject;}

    public void setDatePlanned(Date datePlanned) { this.datePlanned = datePlanned;}

    private String mSubject;

    public Date getDatePlanned() { return datePlanned; }

    private  Date datePlanned;
    private  TypeEvent mType;
    private ImportanceEvent mImportance;
    private String mDetails;
    private ConnexionASdb connexionASdb;

    public ArrayList<ClassEvent> getEvents() { return events; }

    private ArrayList<ClassEvent> events;
    public int getId() {return mId;}
    public String getName() {return mName;}
    public TypeEvent getType() { return mType;}
    public ImportanceEvent getImportance() {return mImportance;}
    public String getDetails() {return mDetails;}
    public ClassEvent() throws Exception {
        events =  new ArrayList<ClassEvent>();
        connexionASdb = new ConnexionASdb();}
    public  void loadEvents(int idCoach, LocalDate date) throws Exception {
        LocalDateTime dateTime = null;
        String sqlReq = "";
        String sDate = "";
        if (date == null){
            sqlReq = "SELECT * FROM ba_event WHERE idCoach="+idCoach+" LIMIT 30 ";
        }else{
            DateTimeFormatter  formatter =  DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            dateTime  =  LocalDateTime.of(date, LocalTime.ofSecondOfDay(0));
            sDate =  dateTime.format(formatter);
            sqlReq = "SELECT * FROM ba_event WHERE idCoach="+idCoach+" AND datePlanned='"+sDate+"' LIMIT 30 ";
        }
        PreparedStatement statement =  connexionASdb.getConnection().prepareStatement(sqlReq);
        ResultSet resultSet = statement.executeQuery();
       events.clear();
           while(resultSet.next()){
               ClassEvent temp  = new ClassEvent();
               temp.setId(resultSet.getInt("id"));
               temp.setImportance(ImportanceEvent.valueOf(resultSet.getString("importance")));
               temp.setType(TypeEvent.valueOf(resultSet.getString("type")));
               temp.setSubject(resultSet.getString("subject"));
               temp.setDatePlanned(resultSet.getDate("datePlanned"));
               temp.setCurrentDate(resultSet.getDate("currentDate"));
               temp.setDetails(resultSet.getString("description"));
               events.add(temp);
           }
    }
    private void addEvent(ClassEvent event){events.add(event);}
    public void setCurrentDate(Date currentDate) {this.currentDate = currentDate;}
    public void setType(TypeEvent mType) {this.mType = mType;}
    public void setDetails(String mDetails) {this.mDetails = mDetails;}
    public void setId(int mId) {this.mId = mId;}
    public void setImportance(ImportanceEvent mImportance) {this.mImportance = mImportance;}
}
