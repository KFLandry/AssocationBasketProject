package main.assocationbasketproject.dialog;

import db.ClassCoatch;
import db.ClassEvent;
import db.ConnexionASdb;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import manager.ClassManager;

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
    private ComboBox<Integer> cbMinutes;
    @FXML
    private ComboBox<Integer> cbHours;
    private ClassEvent events;
    public void initialise(LocalDate value) throws Exception {
        events =  new ClassEvent();
        datePicker.setValue(value);
        ObservableList<String> typeList = FXCollections.observableArrayList(Match.name(), Training.name(), Other.name());
        ObservableList<String> importanceList =  FXCollections.observableArrayList(Low.name(),Medium.name(),Hight.name());
        cbEvents.setItems(typeList);
        cbImportance.setItems(importanceList);

        cbEvents.getSelectionModel().select(1);
        cbImportance.getSelectionModel().select(2);
        customTimeField();
    }
    @FXML
    void save(ActionEvent event) throws Exception {
        if (!fDesc.getText().isEmpty() && !fSubject.getText().isEmpty()){
            LocalTime time =  LocalTime.of(cbHours.getSelectionModel().getSelectedItem(),cbMinutes.getSelectionModel().getSelectedItem());
            LocalDateTime dateTime =  LocalDateTime.of(datePicker.getValue(),time);
            String[] fields = {"idCoach","type","subject","importance","datePlanned","currentDate","description"};
            String[] values = {String.valueOf(ClassCoatch.getInstance().getId()), (String) cbEvents.getSelectionModel().getSelectedItem(),fSubject.getText(),(String )cbImportance.getSelectionModel().getSelectedItem(), String.valueOf(dateTime), String.valueOf(LocalDateTime.now()),fDesc.getText()};

            ConnexionASdb connexionASdb =  ClassManager.getUniqueInstance().getConnexionASdb();
            if (connexionASdb.insert("ba_event",fields,values)> 0){
                ((Stage) event.getSource()).close();
            };
        }
    }
    void customTimeField(){
        for (int i = 0; i <= 23; i++) {cbHours.getItems().add(i);}
        cbHours.getSelectionModel().select(9);
        for (int i = 0; i <= 59; i++) { cbMinutes.getItems().add(i);}
        cbMinutes.getSelectionModel().select(29);
    }
    public void cancel(ActionEvent event) {
        ((Stage) event.getSource()).close();
    }
}
