package main.assocationbasketproject.dialog;

import db.ClassCoach;
import db.ClassEvent;
import db.ConnexionASdb;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import manager.ClassManager;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static db.ImportanceEvent.*;
import static db.TypeEvent.*;

public class FillNewEvent {
    @FXML
    private DatePicker datePicker;
    @FXML
    private ChoiceBox<String> cbEvents;
    @FXML
    private ChoiceBox<String> cbImportance;
    @FXML
    private TextField fSubject;
    @FXML
    private TextArea fDesc;
    @FXML
    private Button btnSave;
    @FXML
    private ComboBox<Integer> cbMinutes;
    @FXML
    private ComboBox<Integer> cbHours;
    private ClassEvent onePlan;
    private Integer mId;
    public void initialise(LocalDate value,Integer id) throws Exception {
        // initialisation pour creation nouvel event
        onePlan =  new ClassEvent();
        datePicker.setValue(value);
        ObservableList<String> typeList = FXCollections.observableArrayList(Match.name(), Training.name(), Other.name());
        ObservableList<String> importanceList =  FXCollections.observableArrayList(Low.name(),Medium.name(),Hight.name());
        cbEvents.setItems(typeList);
        cbImportance.setItems(importanceList);

        cbEvents.getSelectionModel().select(1);
        cbImportance.getSelectionModel().select(2);
        customTimeField();

        //initialisastion pour  modification
        if (id != null){
            mId =  id;
            btnSave.setText("update");
            onePlan =  new ClassEvent().getEvent(id);
            LocalDate initalDate = onePlan.getDatePlanned().toLocalDate();
            datePicker.setValue(initalDate);
            fSubject.setText(onePlan.getSubject());
            fSubject.setEditable(false);
            fDesc.setText(onePlan.getDetails());
            cbImportance.getSelectionModel().select(onePlan.getImportance().name());
            cbEvents.getSelectionModel().select(onePlan.getType().name());
            cbEvents.setDisable(true);
            cbHours.getSelectionModel().select(onePlan.getTime().getHours());
            cbMinutes.getSelectionModel().select(onePlan.getTime().getMinutes());
        }
    }
    @FXML
    void save(ActionEvent event) throws Exception {
        if (!fDesc.getText().isEmpty() && !fSubject.getText().isEmpty()){
            LocalTime time =  LocalTime.of(cbHours.getSelectionModel().getSelectedItem(),cbMinutes.getSelectionModel().getSelectedItem());
            String[] fields = {"idCoach","type","subject","importance","datePlanned","time","currentDate","description"};
            String[] values = {
                    String.valueOf(ClassCoach.getInstance().getId()),
                    cbEvents.getSelectionModel().getSelectedItem(),
                    fSubject.getText(),
                    cbImportance.getSelectionModel().getSelectedItem(),
                    String.valueOf(datePicker.getValue()),
                    time.toString(),
                    String.valueOf(LocalDateTime.now()),
                    fDesc.getText()
            };
            ConnexionASdb connexionASdb =  ClassManager.getUniqueInstance().getConnexionASdb();
            if (mId==null){
                if (connexionASdb.insert("ba_event",fields,values)  == 0){
                    JOptionPane.showMessageDialog(null,"La modification a échouée!");
                }
            }else{
                if (connexionASdb.update(mId,"ba_event",fields,values)  == 0){
                    JOptionPane.showMessageDialog(null,"La modification a échouée!");
                }
            }
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }
    void customTimeField(){
        for (int i = 0; i <= 23; i++) {cbHours.getItems().add(i);}
        cbHours.getSelectionModel().select(9);
        for (int i = 0; i <= 59; i++) { cbMinutes.getItems().add(i);}
        cbMinutes.getSelectionModel().select(29);
    }
    public void cancel(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
