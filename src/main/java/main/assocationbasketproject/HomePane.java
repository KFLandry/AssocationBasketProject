package main.assocationbasketproject;

import db.ClassCoatch;
import db.ClassEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.assocationbasketproject.dialog.FillNewEvent;
import variables.CardDay;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomePane implements Initializable {
    @FXML
    private DatePicker datePicker;
    @FXML
    private TableView otherTab;
    @FXML
    private TableView tabMatch;
    private ClassEvent events;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            events = new ClassEvent();
            events.loadEvents(ClassCoatch.getInstance().getId(),null);
            fillTabs(events.getEvents());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        custumDatePicker();
    }
    void custumDatePicker(){
        datePicker.show();
        // Personnaliser le DatePicker
        // On commence par le text Field
        TextField customLabel = datePicker.getEditor();
        customLabel.setAlignment(Pos.CENTER);
        // Toutes les cellules du calendrier
        datePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate date, boolean empty) {
                        super.updateItem(date, empty);
                        setText(null);
                        if (!empty) {
                            //On empecher la fermeture du calendrier
                            setOnMouseClicked(event -> {
                                handleAction(event);
                                datePicker.setValue(date);
                                datePicker.setPromptText(date.toString());
                                datePicker.show();
                                try {
                                    events.loadEvents(ClassCoatch.getInstance().getId(),date);
                                    fillTabs(events.getEvents());
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                                if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount()==2){
                                    try {
                                        FXMLLoader fxml =  new FXMLLoader(Objects.requireNonNull(getClass().getResource("dialog/dialogEvent.fxml")));
                                        DialogPane dialogPane =  fxml.load();
                                        FillNewEvent dialogEvent =  fxml.getController();
                                        dialogEvent.initialise(date);
                                        Stage stage =  new Stage();
                                        stage.setTitle("Fills infos of events");
                                        stage.setScene(new Scene(dialogPane));
                                        stage.showAndWait();
                                        //Refresh les tableaux
                                        events.loadEvents(ClassCoatch.getInstance().getId(),date);
                                        fillTabs(events.getEvents());

                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            });
                            // Créer une carte personnalisée dans chaque cellule
                            VBox card = new VBox();
                            card.setAlignment(Pos.CENTER);
                            card.getStyleClass().add("date-cell");

                            // Créer un AnchorPane en haut avec la date
                            AnchorPane topAnchor = new AnchorPane();
                            Label dateLabel = new Label(String.valueOf(date.getDayOfMonth()));
                            dateLabel.setAlignment(Pos.CENTER);
                            topAnchor.getChildren().add(dateLabel);
                            AnchorPane.setTopAnchor(dateLabel, 10.0);
                            AnchorPane.setLeftAnchor(dateLabel, 30.0);

                            // Créer un AnchorPane en bas avec un label quelconque
                            AnchorPane bottomAnchor = new AnchorPane();
                            Label customLabel = new Label("(0) Event(s)");
                            customLabel.setAlignment(Pos.CENTER);
                            bottomAnchor.getChildren().add(customLabel);
                            AnchorPane.setBottomAnchor(customLabel, 10.0);
                            AnchorPane.setLeftAnchor(customLabel, 10.0);

                            // Ajouter les AnchorPane à la carte
                            card.getChildren().addAll(topAnchor, bottomAnchor);
                            card.setPrefSize(50,50);
                            // Afficher la carte personnalisée dans la cellule
                            setGraphic(card);
                        }
                    }
                };
            }
        });
    }
    void handleAction(MouseEvent event){}
    void fillTabs(ArrayList<ClassEvent> events){
        for (ClassEvent event :  events){
            if (event.getType().equals("Other")){
                ObservableList<? extends Serializable> list = FXCollections.observableArrayList(event.getId(),event.getDatePlanned(),event.getSubject(),event.getDetails());
                otherTab.setItems((ObservableList<String>) list);
            }else{
                ObservableList<? extends Serializable> list = FXCollections.observableArrayList(event.getId(),event.getDatePlanned(),event.getSubject(),event.getDetails());
                tabMatch.setItems((ObservableList<String>) list);
            }
        }
    }
    @FXML
    void addEvent(){}
    @FXML
    void deleteEvent(){}
    @FXML
    void updateEvent(){}
    void loadGrid() throws IOException {
        int day = 0;
        int row  =  1;
        int cols = 0;
        for (int i = 0; i < 35; i++) {
            FXMLLoader fxml =  new FXMLLoader(getClass().getResource("cards/cardDay.fxml"));
            VBox tempCard = fxml.load();
            tempCard.setOnMouseEntered(e -> tempCard.setScaleX(1.1));
            tempCard.setOnMouseEntered(e -> tempCard.setScaleY(1.1));
            tempCard.setOnMouseExited(e -> tempCard.setScaleX(1));
            tempCard.setOnMouseExited(e -> tempCard.setScaleY(1));
            CardDay controller = fxml.getController();

            tempCard.setOnMouseClicked(event -> {
                try {
                    Dialog eventDialog =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("dialog/dialogEvent.fxml")));
                    eventDialog.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            if (i == 31){ day = 0;}
            day++;
            controller.initialise(day, 0);
            if (i>31) tempCard.setDisable(true);
            //gridPane.add(tempCard, cols, row);
            cols++;
            if (cols==7){
                cols = 0;
                row ++;
            }
        }
    }
}

