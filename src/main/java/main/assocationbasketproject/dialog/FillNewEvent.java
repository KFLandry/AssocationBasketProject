package main.assocationbasketproject.dialog;

import db.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import manager.ClassManager;
import service.ClassGmail;
import service.Template;
import variables.ClassFieldFormat;

import javax.mail.internet.MimeMultipart;
import javax.swing.*;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

import static db.ImportanceEvent.*;
import static db.TypeEvent.*;

public class FillNewEvent {
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<String> cbEvents;
    @FXML
    private ComboBox<ClassTeam> cbTeams;
    @FXML
    private ChoiceBox<String> cbImportance;
    @FXML
    private TextField fSubject;
    @FXML
    private TextArea fDesc;
    @FXML
    private TextField fLocation;
    @FXML
    private Button btnSave;
    @FXML
    private ComboBox<Integer> cbMinutes;
    @FXML
    private ComboBox<Integer> cbHours;
    private ClassManager manager;
    private PreparedStatement statement;
    private Integer mId;
    public void initialise(LocalDate value,Integer id) throws Exception {
        // initialisation pour creation nouvel event
        datePicker.setValue(value);
        ObservableList<String> typeList = FXCollections.observableArrayList(Match.name(), Training.name(), Other.name());
        ObservableList<String> importanceList =  FXCollections.observableArrayList(Low.name(),Medium.name(),Hight.name());
        cbEvents.setItems(typeList);
        cbImportance.setItems(importanceList);

        cbEvents.getSelectionModel().select(1);
        cbImportance.getSelectionModel().select(2);
        //initialisastion pour  modification
        if (id != null){
            mId =  id;
            btnSave.setText("update");
            ClassEvent oneEvent =  new ClassEvent().getEvent(id);
            LocalDate initalDate = oneEvent.getDatePlanned().toLocalDate();
            datePicker.setValue(initalDate);
            fSubject.setText(oneEvent.getSubject());
            fSubject.setEditable(false);
            fDesc.setText(oneEvent.getDetails());
            ClassTeam team = new ClassTeam(oneEvent.getIdTeam());
            team.initialiseTeam();
            cbTeams.getSelectionModel().select(team);
            fLocation.setText(oneEvent.getLocation());
            cbImportance.getSelectionModel().select(oneEvent.getImportance().name());
            cbEvents.getSelectionModel().select(oneEvent.getType().name());
            cbEvents.setDisable(true);
            cbHours.getSelectionModel().select(oneEvent.getTime().getHours());
            cbMinutes.getSelectionModel().select(oneEvent.getTime().getMinutes());
        }
        //
        ObservableList<ClassTeam> listTeam =  FXCollections.observableArrayList();
        manager = ClassManager.getUniqueInstance();
        manager.loadCaterogies();
        for (ClassCategory category : manager.getCategories()) {
            category.initialise();
            for (ClassTeam team : category.getTeams()) {
                team.initialiseTeam();
                if (team.getPlayers().size() >= 2) listTeam.add(team);
            }
        }
        cbTeams.setItems(listTeam);

        custom();
    }
    @FXML
    void save(ActionEvent event) throws Exception {
        if (!(fDesc.getText().isEmpty() && fSubject.getText().isEmpty() && fLocation.getText().isEmpty())){
            LocalTime time =  LocalTime.of(cbHours.getSelectionModel().getSelectedItem(),cbMinutes.getSelectionModel().getSelectedItem());
            ClassTeam team =  cbTeams.getSelectionModel().getSelectedItem();
            if (mId==null){
                buildStatement("insert",0,team,time);
                if (statement.executeUpdate()>0){
                    ArrayList<String> toAddresses = team.getPlayers().stream().map(player -> {
                        try {
                            player.initialise();
                            return player.getEmail();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }).collect(Collectors.toCollection(ArrayList::new));
                    LocalDateTime dateTime =  LocalDateTime.of(datePicker.getValue(),time);
                    String from = ClassCoach.getInstance().getEmail();
                    String coachName =  ClassCoach.getInstance().getName();
                    notififyPlayers("Convocation de match",from,toAddresses,dateTime,fSubject.getText(),coachName,fLocation.getText());
                    JOptionPane.showConfirmDialog(null,"Operation effectuée avec succés!La fenêtre va se fermer");
                }else  JOptionPane.showMessageDialog(null,"L'operation a échoué!Consultez les logs pour plus de details");
            }else{
                buildStatement("update",1,team,time);
                if (statement.executeUpdate()>0){
                    ArrayList<String> toAddresses = team.getPlayers().stream().map(ClassPlayer::getEmail).collect(Collectors.toCollection(ArrayList::new));
                    LocalDateTime dateTime =  LocalDateTime.of(datePicker.getValue(),time);
                    String from = ClassCoach.getInstance().getEmail();
                    String coachName =  ClassCoach.getInstance().getName();
                    notififyPlayers("Motification du planning de match",from,toAddresses,dateTime,fSubject.getText(),coachName,fLocation.getText());
                    JOptionPane.showConfirmDialog(null,"Operation effectuée avec succés!La fenêtre va se fermer","",JOptionPane.YES_OPTION);
                }else JOptionPane.showConfirmDialog(null,"La modification a échouée!Consultez les logs pour plus de details","",JOptionPane.YES_OPTION);
            }
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }
    @FXML
    void formatFields(MouseEvent event){ ClassFieldFormat.formatField((TextField) event.getSource(),"text");}
    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
    private void buildStatement(String query,int id, ClassTeam team, LocalTime time) throws Exception {
        String sql;
        if (Objects.equals(query, "insert") || id==0) {
            sql = "INSERT INTO ba_event (idCoach,idTeam,type,subject,importance,datePlanned,time,location,description) VALUES (?,?,?,?,?,?,?,?,?);";
        }else sql = "UPDATE ba_event SET idCoach=?,idTeam=?,type=?,subject=?,importance=?,datePlanned=?,time=?,location=?,description=? WHERE id="+id+";";
        statement =  manager.getConnexionASdb().getConnection().prepareStatement(sql);
        statement.setInt(1,ClassCoach.getInstance().getId());
        statement.setInt(2,team.getId());
        statement.setString(3,cbEvents.getSelectionModel().getSelectedItem());
        statement.setString(4,fSubject.getText());
        statement.setString(5,cbImportance.getSelectionModel().getSelectedItem());
        statement.setDate(6, Date.valueOf(datePicker.getValue()));
        statement.setTime(7, Time.valueOf(time));
        statement.setString(8,fLocation.getText());
        statement.setString(9,fDesc.getText());
    }
    void custom(){
        // times fields
        for (int i = 0; i <= 23; i++) {cbHours.getItems().add(i);}
        cbHours.getSelectionModel().select(9);
        for (int i = 0; i <= 59; i++) { cbMinutes.getItems().add(i);}
        cbMinutes.getSelectionModel().select(29);
        // combo box
        //  cbTeams.setCellFactory(cell -> new ListCell<>() {
        //      @Override
        //      protected void updateItem(ClassTeam classTeam, boolean b) {
        //          super.updateItem(classTeam, b);
        //          manager.getCategories().forEach(category -> {
        //              category.getTeams().forEach(team -> {
        //                  if (classTeam.equals(team)) {
        //                      setText(category.getName() + " -> " + team.getName());
        //                  }
        //                  setGraphic(null);
        //              });
        //          });
        //      }
        //  });
    }
    private  void notififyPlayers(String subject,String from,ArrayList<String> toAddresses,LocalDateTime dateTime,String oppenent,String coach,String location) throws Exception {
        MimeMultipart mimeMultipart =  Template.notifyPlayer(dateTime,oppenent,coach,location);
        ClassGmail.sendEmail("notify",from,toAddresses,subject,mimeMultipart);
    }
}
