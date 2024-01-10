package main.assocationbasketproject;

import db.ClassCoach;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import manager.ClassManager;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class DemarrageController{
    @FXML
    private StackPane stackPane;
    @FXML
    private Button btnPlayer;
    @FXML
    private Button btnCategories;
    @FXML
    private Button btnHome;
    @FXML
    private Button btnOverView;
    @FXML
    private Button btnStats;
    @FXML
    private Label lName;
    @FXML
    private Circle circleProfile;
    public void initialise(int id) throws Exception {
        ClassCoach coach = ClassCoach.getInstance();
        ClassManager manager = ClassManager.getUniqueInstance();
        coach.setId(id);
        manager.setId(id);

        String  path = coach.loadMedia("profile");
        Image image = new Image(new File(path).toURI().toURL().toExternalForm());
        circleProfile.setFill(new ImagePattern(image));

        coach.initialiseCoatch();
        lName.setText(coach.getName() +" "+ coach.getLastName());

        // Defintion du plan par defaut

        Parent defaultPane =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("homePane.fxml")));
        stackPane.getChildren().removeAll();
        stackPane.getChildren().setAll(defaultPane);
    }
    @FXML
    void handlePane(ActionEvent event) throws IOException {
        Button currentBtn =  (Button) event.getSource();
        FXMLLoader fxmlLoader = new FXMLLoader();
        if (currentBtn.equals(btnHome)){
            fxmlLoader.setLocation(getClass().getResource("homePane.fxml"));
        } else if (currentBtn.equals(btnCategories)) {
            fxmlLoader.setLocation(getClass().getResource("category.fxml"));
        } else if (currentBtn.equals(btnOverView)) {
            fxmlLoader.setLocation(getClass().getResource("overview.fxml"));
        } else if (currentBtn.equals(btnPlayer)) {
            fxmlLoader.setLocation(getClass().getResource("player.fxml"));
        } else if (currentBtn.equals(btnStats)) {
            fxmlLoader.setLocation(getClass().getResource("settings.fxml"));
        }
        Parent pane =  fxmlLoader.load();
        stackPane.getChildren().removeAll();
        stackPane.getChildren().clear();
        stackPane.setPrefSize(200,150);
        stackPane.getChildren().setAll(pane);
    }
    @FXML
    void handleButton(MouseEvent event){
    }
}
