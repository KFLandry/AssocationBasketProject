package main.assocationbasketproject;

import db.ClassCoatch;
import db.ClassEvent;
import db.ConnexionASdb;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.assocationbasketproject.dialog.FillNewEvent;
import manager.ClassManager;
import variables.CardDay;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class HomePane implements Initializable {
    @FXML
    private DatePicker datePicker;
    @FXML
    private TableView<ClassEvent> otherTab;
    @FXML
    private TableView<ClassEvent> tabMatch;
    @FXML
    private TableColumn<ClassEvent, Integer> idColumn;
    @FXML
    private TableColumn<ClassEvent, String> commentColumn;
    @FXML
    private TableColumn<ClassEvent, String> opponentColumn;
    @FXML
    private TableColumn<ClassEvent, Date> dateColumn;
    @FXML
    private TableColumn<ClassEvent, Integer> oIdColumn;
    @FXML
    private TableColumn<ClassEvent, String> oCommentColumn;
    @FXML
    private TableColumn<ClassEvent, String> oSubjectColumn;
    @FXML
    private TableColumn<ClassEvent, Date> oDateColumn;
    @FXML
    private TableColumn<ClassEvent, Date> cId;
    @FXML
    private TableColumn<ClassEvent, Date> cDate;
    @FXML
    private TableColumn<ClassEvent, Date> cTime;
    @FXML
    private TableColumn<ClassEvent, Date> cScheduleAt;
    @FXML
    private TableColumn<ClassEvent, Date> cType;
    @FXML
    private TableColumn<ClassEvent, Date> cImportance;
    @FXML
    private TableColumn<ClassEvent, Date> cOppenent;
    @FXML
    private TableColumn<ClassEvent, Date> cDescription;
    @FXML
    private TableColumn<ClassEvent, Date> cClose;

    @FXML
    private  ChoiceBox<String> cbEvents;
    @FXML
    private  ChoiceBox<String> cbImportance;
    @FXML
    private  ChoiceBox<String> cbClose;
    @FXML
    private TextField fKeyWord;
    @FXML
    private DatePicker dateBegin;
    @FXML
    private DatePicker dateEnd;
    private Queue<ClassEvent> queueEvent;
    private ClassEvent oneSchedule;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        queueEvent = new ArrayDeque<>();
        try {
            oneSchedule = new ClassEvent();
            oneSchedule.loadEvents(ClassCoatch.getInstance().getId(),null,false);
            fillTabs(oneSchedule.getEvents());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        bindColumnsTables();
        custumDatePicker();
    }

    void custumDatePicker(){
        datePicker.show();
        // Personnaliser le DatePicker
        TextField customLabel = datePicker.getEditor();
        customLabel.setAlignment(Pos.CENTER);
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
                                    oneSchedule.loadEvents(ClassCoatch.getInstance().getId(),date,false);
                                    fillTabs(oneSchedule.getEvents());
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                                if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount()==2){
                                    try {
                                        FXMLLoader fxml =  new FXMLLoader(Objects.requireNonNull(getClass().getResource("dialog/dialogEvent.fxml")));
                                        DialogPane dialogPane =  fxml.load();
                                        FillNewEvent dialogEvent =  fxml.getController();
                                        dialogEvent.initialise(date,null);
                                        Stage stage =  new Stage();
                                        stage.setTitle("Fills infos of events");
                                        stage.setScene(new Scene(dialogPane));
                                        stage.showAndWait();
                                        //Refresh les tableaux
                                        oneSchedule.loadEvents(ClassCoatch.getInstance().getId(),date,false);
                                        fillTabs(oneSchedule.getEvents());

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
                            int nb = 0;
                            try {
                                nb  = oneSchedule.countEvent(ClassCoatch.getInstance().getId(), date);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            Label customLabel = new Label("("+nb+") Event(s)");
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
        ObservableList<ClassEvent> mList =FXCollections.observableArrayList();
        ObservableList<ClassEvent> oList =FXCollections.observableArrayList();
        for (ClassEvent event :  events){
            if (event.getType().toString().equals("Other")){
                oList.add(event);
            }else{
                mList.add(event);
            }
        }
        tabMatch.setItems(mList);
        otherTab.setItems(oList);

    }
    @FXML
    void addEvent() throws Exception {
        FXMLLoader fxml =  new FXMLLoader(Objects.requireNonNull(getClass().getResource("dialog/dialogEvent.fxml")));
        DialogPane dialogPane =  fxml.load();
        FillNewEvent dialogEvent =  fxml.getController();
        dialogEvent.initialise(datePicker.getValue(),null);
        Stage stage =  new Stage();
        stage.setTitle("Fills infos of events");
        stage.setScene(new Scene(dialogPane));
        stage.showAndWait();

        oneSchedule.loadEvents(ClassCoatch.getInstance().getId(),null,false);
        fillTabs(oneSchedule.getEvents());
    }
    @FXML
    void deleteEvent(ActionEvent event) throws Exception {
        TableView<ClassEvent> currentTable  = (TableView) event.getSource();
        String[] fields =  {"close"};
        String[] values = {"0"};
        ConnexionASdb connexionASdb = ClassManager.getUniqueInstance().getConnexionASdb();
        queueEvent.offer(oneSchedule.getEvent(currentTable.getSelectionModel().getSelectedItem().getId()));
        connexionASdb.update(currentTable.getSelectionModel().getSelectedItem().getId(),"ba_event",fields,values);

        oneSchedule.loadEvents(ClassCoatch.getInstance().getId(),null,false);
        fillTabs(oneSchedule.getEvents());
    }
    @FXML
    void updateEvent(ActionEvent event) throws Exception {
        int id = 0;
       //if (tabMatch.getSelectionModel().isSelected()){
       //    id =tabMatch.getSelectionModel().getSelectedItem().getId();
       //}else if(otherTab.getSelectionModel().isSelected()) {
       //    id =otherTab.getSelectionModel().getSelectedItem().getId();
       //}else {
       //    JOptionPane.showMessageDialog(null,"Vous devez selectionner une ligne  de tableau pour pouvoir la modifier...");
       //}
        // La mise a jour est faite pour une ligne en fonction de l'id
        FXMLLoader fxml =  new FXMLLoader(Objects.requireNonNull(getClass().getResource("dialog/dialogEvent.fxml")));
        DialogPane dialogPane =  fxml.load();
        FillNewEvent dialogEvent =  fxml.getController();
        // Je remplie la queue d'evenenement pour defaire les modifications au cas où!!
        queueEvent.offer(oneSchedule.getEvent(id));
        dialogEvent.initialise(datePicker.getValue(),id);
        Stage stage =  new Stage();
        stage.setTitle("Fills infos of events");
        stage.setScene(new Scene(dialogPane));
        stage.showAndWait();

        oneSchedule.loadEvents(ClassCoatch.getInstance().getId(),null,false);
        fillTabs(oneSchedule.getEvents());
    }
    @FXML
    void undo() throws Exception {
        if (!queueEvent.isEmpty()){
            ClassEvent lastEvent = queueEvent.peek();
            String[] fields =  {"close"};
            String[] values = {"0"};
            ConnexionASdb connexionASdb = ClassManager.getUniqueInstance().getConnexionASdb();
            connexionASdb.update(lastEvent.getId(),"ba_event",fields,values);

            oneSchedule.loadEvents(ClassCoatch.getInstance().getId(),null,false);
            fillTabs(oneSchedule.getEvents());
        }
    }
    @FXML
    void search(ActionEvent event) throws Exception {
        oneSchedule.search(dateBegin.getValue(),dateEnd.getValue(),Boolean.valueOf(cbClose.getSelectionModel().getSelectedItem()),fKeyWord.getText());
        fillTabs(oneSchedule.getEvents());
    }
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
    private  void bindColumnsTables(){
        // Je lie les colonnes des tableaux aux propriétes des classes par lesquelles elles seront remplies
        //tables Match and  training
        idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("datePlanned"));
        opponentColumn.setCellValueFactory(new PropertyValueFactory<>("Subject"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("Details"));
        // Table Other
        oIdColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        oDateColumn.setCellValueFactory(new PropertyValueFactory<>("datePlanned"));
        oSubjectColumn.setCellValueFactory(new PropertyValueFactory<>("Subject"));
        oCommentColumn.setCellValueFactory(new PropertyValueFactory<>("Details"));
        //Table Search
        cId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        cDate.setCellValueFactory(new PropertyValueFactory<>("DatePlanned"));
        cTime.setCellValueFactory(new PropertyValueFactory<>("Time"));
        cScheduleAt.setCellValueFactory(new PropertyValueFactory<>("Id"));
        cType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        cImportance.setCellValueFactory(new PropertyValueFactory<>("Importance"));
        cOppenent.setCellValueFactory(new PropertyValueFactory<>("Subject"));
        cDescription.setCellValueFactory(new PropertyValueFactory<>("Details"));
        cClose.setCellValueFactory(new PropertyValueFactory<>("Close"));
    }
}

