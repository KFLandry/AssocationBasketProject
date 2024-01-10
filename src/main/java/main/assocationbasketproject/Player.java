package main.assocationbasketproject;

import db.ClassPlayer;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import manager.ClassManager;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Player implements Initializable {
    @FXML
    private Button btnGo;
    @FXML
    private TableColumn<ClassPlayer, Integer> cAssists;
    @FXML
  private TableColumn<ClassPlayer, LocalDate> cDate;
    @FXML
    private TableColumn<ClassPlayer, Integer> cFG;
    @FXML
    private TableColumn<ClassPlayer, Integer> cId;
    @FXML
    private TableColumn<ClassPlayer, Integer> cOppenent;
    @FXML
    private TableColumn<ClassPlayer, Integer> cPoints;
    @FXML
    private TableColumn<ClassPlayer, Integer> cRebounds;
    @FXML
    private TableColumn<ClassPlayer, Integer> cShots;
    @FXML
    private TableColumn<ClassPlayer, Integer> cTimeGame;
    @FXML
    private Circle circleProfile;
    @FXML
    private DatePicker dDate;
    @FXML
    private TextField fEnd;
    @FXML
    private TextField fSearch;
    @FXML
    private TextField fGEnd;
    @FXML
    private TextField fGStart;
    @FXML
    private TextField fOppenent;
    @FXML
    private TextField fStart;
    @FXML
    private Label lAge;
    @FXML
    private Label lAssits;
    @FXML
    private Label lBlocks;
    @FXML
    private Label lCategory;
    @FXML
    private Label lName;
    @FXML
    private Label lNationality;
    @FXML
    private Label lPoints;
    @FXML
    private Label lRebounds;
    @FXML
    private Label lSize;
    @FXML
    private Label lSteals;
    @FXML
    private ListView<String> listView;
    private FilteredList<String> filteredItems;
    private ClassPlayer currentPlayer;

    @FXML
    void fillStats() throws IOException {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dialog/fillStats.fxml"));
      DialogPane dialogPane =  fxmlLoader.load();
      Stage stage =  new Stage();
      stage.setScene( new Scene(dialogPane));
    }
    @FXML
    void search() throws SQLException {
      fSearch.textProperty().addListener((observable, oldValue, newValue) -> {
        filteredItems.setPredicate(item -> {
          if (newValue == null || newValue.isEmpty()) {
            return true;
          }
          String lowerCaseFilter = newValue.toLowerCase();
          return item.toLowerCase().contains(lowerCaseFilter);
        });
      });
      listView.setItems(filteredItems);
    }
    private  void fillPage(int id) throws Exception {
      currentPlayer =  new ClassPlayer(id);
      currentPlayer.initialise();

      lName.setText(currentPlayer.getName());
      lAge.setText(currentPlayer.getAge() + " years");
      lNationality.setText(currentPlayer.getCountry());
      lSize.setText(currentPlayer.getHeight() + " / "+ currentPlayer.getWeight());
      lCategory.setText( "");

      Image image =  new Image( currentPlayer.getPathProfile());
      circleProfile.setFill(new ImagePattern(image));

      //Charger  les stats
      //

    }
    private void bindTabletoClass(){
        cAssists.setCellValueFactory(new PropertyValueFactory<>(""));
        cDate.setCellValueFactory(new PropertyValueFactory<>(""));
        cFG.setCellValueFactory(new PropertyValueFactory<>(""));
        cId.setCellValueFactory(new PropertyValueFactory<>(""));
        cOppenent.setCellValueFactory(new PropertyValueFactory<>(""));
        cPoints.setCellValueFactory(new PropertyValueFactory<>(""));
        cRebounds.setCellValueFactory(new PropertyValueFactory<>(""));
        cShots.setCellValueFactory(new PropertyValueFactory<>(""));
        cTimeGame.setCellValueFactory(new PropertyValueFactory<>(""));
    }
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
      try {
          ClassManager manager = ClassManager.getUniqueInstance();
          listView.setItems(manager.allPlayers());
          customListView();
          filteredItems =  new FilteredList<>(manager.allPlayers(), p -> true);
      } catch (Exception e) {
          throw new RuntimeException(e);
      }
  }
  private  void customListView(){
      listView.setCellFactory( cell -> new ListCell<String>(){
        @Override
        protected void updateItem(String strings, boolean b) {
          super.updateItem(strings, b);
            if (strings!=null){
              int index = strings.indexOf(',');
              setText(strings.substring(index+1));

              // Redirection a la methode fillPage()
              setOnMouseClicked( event -> {
                  try {
                      fillPage(Integer.parseInt(strings.substring(0,index)));
                  } catch (Exception e) {
                      throw new RuntimeException(e);
                  }
              });
              setGraphic(null);
            }
        }
      });
  }
  @FXML
  void Search(ActionEvent event) {
  }
}
