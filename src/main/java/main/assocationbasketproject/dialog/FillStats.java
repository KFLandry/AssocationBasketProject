package main.assocationbasketproject.dialog;

import db.ClassPlayer;
import db.ConnexionASdb;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import manager.ClassManager;
import variables.ToastMessage;

import java.net.URL;
import java.util.ResourceBundle;

public class FillStats implements Initializable {
    @FXML
    private Button btnSave;
    @FXML
    private ComboBox<ClassPlayer> cbWho;
    @FXML
    private DatePicker dDate;
    @FXML
    private TextField fAssists;
    @FXML
    private TextField fBlocks;
    @FXML
    private TextField fOppenent;
    @FXML
    private TextField fPoints;
    @FXML
    private TextField fRebounds;
    @FXML
    private TextField fScore;
    @FXML
    private TextField fScore1;
    @FXML
    private TextField fSteals;
    private ConnexionASdb connexionASdb;
    @FXML
    void cancel(ActionEvent event) {
    }
    @FXML
    void save() throws Exception {
        if (!(fScore.getText().isEmpty() && fScore1.getText().isEmpty() && fOppenent.getText().isEmpty() && fBlocks.getText().isEmpty() && fPoints.getText().isEmpty() && fAssists.getText().isEmpty() && fRebounds.getText().isEmpty() && fSteals.getText().isEmpty() && fBlocks.getText().isEmpty())){
            ClassPlayer player =  cbWho.getSelectionModel().getSelectedItem();
            String[] fields =  {"idTeam","idPlayer","dateGame","oppenent","timeGame","points","rebounds","assist","steals","3pointsShotsAttempts","3pointsAwarded","decisivePass","victory"};
            String[] values =  {};
            if (connexionASdb.insert("ba_statistique",fields,values)==0){
                ToastMessage.show("Error","L'insertion a echouée! Veillez consulter vos logs",3);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            connexionASdb  = ClassManager.getUniqueInstance().getConnexionASdb();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
