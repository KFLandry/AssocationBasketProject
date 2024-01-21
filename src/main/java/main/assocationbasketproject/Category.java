package main.assocationbasketproject;

import db.ClassCategory;
import db.ClassPlayer;
import db.ClassTeam;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.BooleanStringConverter;
import manager.ClassManager;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javax.swing.JOptionPane.YES_NO_OPTION;

public class Category implements Initializable {
    @FXML
    private Button btnAddCategory;
    @FXML
    private Button btnDeleteCategory;
    @FXML
    private Button btnUpdateTeam;
    @FXML
    private Button btnDeleteTeam;
    @FXML
    private Button btnUpdateCategory;
    @FXML
    private TableColumn<ClassPlayer, Integer> cAge;
    @FXML
    private TableColumn<ClassPlayer, String> cCountry;
    @FXML
    private TableColumn<ClassPlayer, Boolean> cIsAvailable;
    @FXML
    private TableColumn<ClassPlayer,Boolean> cIsHurt;
    @FXML
    private TableColumn<ClassPlayer, Integer> cId;
    @FXML
    private TableColumn<ClassPlayer, String> cName;
    @FXML
    private TableColumn<ClassPlayer, String> cPosition;
    @FXML
    private ComboBox<String> cbCategory;
    @FXML
    private ComboBox<String> cbTeam;
    @FXML
    private Label lDate;
    @FXML
    private Label lGender;
    @FXML
    private Label lName;
    @FXML
    private TextArea lStory;
    @FXML
    private TableView<ClassPlayer> tabPlayers;
    private ClassTeam currentTeam;
    private ClassManager manager;
    private ClassCategory currentCategory;
    private ArrayList<ClassCategory> categories;
    private void fillPage() throws SQLException {
        lName.setText(currentCategory.getName());
        lDate.setText(currentCategory.getDateCreation().toString());
        lGender.setText(currentCategory.getGender());
        lStory.setText(currentCategory.getStory());

        if (!currentCategory.getTeams().isEmpty()){
            ObservableList<String> teamNames  = FXCollections.observableArrayList();
            for(ClassTeam team :  currentCategory.getTeams()){
                team.initialiseTeam();
                teamNames.add(team.getName());
            }
            cbTeam.setItems(null);
            cbTeam.setItems(teamNames);
            cbTeam.getSelectionModel().selectFirst();
            currentCategory.getTeams().forEach(team -> {
                if (team.getName().equals(cbTeam.getSelectionModel().getSelectedItem())){
                    currentTeam =  team;
                    currentCategory.setCurrentTeam(team);
                    fillTab();
                }
            });
            disableControls(false);
        }else {
            cbTeam.setItems(null);
            btnUpdateTeam.setDisable(true);
            btnDeleteTeam.setDisable(true);
        }
    }
    @FXML
    void addCategory(ActionEvent event) throws Exception {
        manager.setCurrentCategory(null);
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("dialog/fillCategory.fxml"));
        DialogPane dialogPane  =  fxml.load();
        Scene scene =  new Scene(dialogPane);
        Stage stage =  new Stage();
        stage.setScene(scene);
        stage.showAndWait();
        fillPage();
    }
    @FXML
    void deleteCategory() throws SQLException {
        if(JOptionPane.showConfirmDialog(null,"Etes-vous sûre de vouloir supprimer la categorie "+currentCategory.getName() +"?","Confirmer l'action",YES_NO_OPTION )==0){
            if (manager.getCurrentCategory()==null){
                manager.getCategories().forEach( category -> {
                    if (category.getName().equals(cbCategory.getSelectionModel().getSelectedItem())){
                        manager.setCurrentCategory(category);
                    };
                });
            }
            manager.delete();
            cbCategory.getItems().removeIf( item -> item.equals(currentCategory.getName()));
            fillPage();
        }
    }
    @FXML
    void deleteTeam() throws SQLException {
        if(JOptionPane.showConfirmDialog(null,"Etes-vous sûre de vouloir supprimer l'equipe "+cbTeam.getSelectionModel().getSelectedItem()+ " et par la même occasion toutes les relations qu'elle a avec les joueurs??","", YES_NO_OPTION)==0){
            cbTeam.getItems().removeIf(item -> item.equals(currentCategory.getCurrentTeam().getName()));
            currentCategory.deleteTeam();
            cbTeam.getSelectionModel().selectFirst();
            currentCategory.getTeams().forEach( team -> {
                if (team.getName().equals(cbTeam.getSelectionModel().getSelectedItem())){
                    currentTeam =  team;
                    currentCategory.setCurrentTeam(team);
                }
            });
            fillTab();
        }
    }
    @FXML
    void updateCategory() throws IOException {
        categories.forEach( category -> {
            if (category.getName().equals(cbCategory.getSelectionModel().getSelectedItem())){
                manager.setCurrentCategory(category);
            }
        });
        FXMLLoader fxml =  new FXMLLoader(getClass().getResource("dialog/fillCategory.fxml"));
        DialogPane dialogPane =  fxml.load();
        Stage stage =  new Stage();
        stage.setScene(new Scene(dialogPane));
        stage.showAndWait();
    }
    @FXML
    void updateTeam() throws IOException {
        FXMLLoader fxml =  new FXMLLoader(getClass().getResource("dialog/fillTeam.fxml"));
        DialogPane dialogPane = fxml.load();
        Stage stage =  new Stage();
        stage.setScene(new Scene(dialogPane));
        stage.showAndWait();
    }
    @FXML
    void changeCategory() throws SQLException {
        String nameSelected =cbCategory.getSelectionModel().getSelectedItem();
        if (!nameSelected.isEmpty()){
            categories.forEach(category -> {
                if (category.getName().equals(nameSelected)){currentCategory  =  category;}
            });
        }
        fillPage();
    }
    private void bindsTabsToDatas(){
        cId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        cName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        cPosition.setCellValueFactory(new PropertyValueFactory<>("Position"));
        cAge.setCellValueFactory(new PropertyValueFactory<>("Age"));
        cCountry.setCellValueFactory(new PropertyValueFactory<>("Country"));
        cIsHurt.setCellValueFactory(new PropertyValueFactory<>("Hurt"));
        cIsAvailable.setCellValueFactory(new PropertyValueFactory<>("Available"));

        cIsHurt.setCellFactory(TextFieldTableCell.forTableColumn(new BooleanStringConverter()));
        cIsAvailable.setCellFactory(TextFieldTableCell.forTableColumn(new BooleanStringConverter()));

        cIsHurt.setOnEditCommit( item -> {
            ClassPlayer player  = item.getRowValue();
            player.isHurt(item.getNewValue());
        });

        cIsAvailable.setOnEditCommit( item -> {
            ClassPlayer player  = item.getRowValue();
            player.isAvailable(item.getNewValue());
        });
    }
    private void disableControls(Boolean b){
        btnUpdateTeam.setDisable(b);
        btnDeleteTeam.setDisable(b);
        btnDeleteCategory.setDisable(b);
        btnUpdateCategory.setDisable(b);
        cbCategory.setDisable(b);
        cbTeam.setDisable(b);
    }
    @FXML
    void fillTab(){
        String teamSelected = cbTeam.getSelectionModel().getSelectedItem();
        if (teamSelected != null){
            currentCategory.getTeams().forEach( team ->{
                if(team.getName().equals(teamSelected)){
                    ObservableList<ClassPlayer> listPlayers =  FXCollections.observableArrayList();
                    currentTeam =  team;
                    currentCategory.setCurrentTeam(team);
                    currentTeam.getPlayers().forEach( player ->{
                        try {
                            player.initialise();
                            listPlayers.add(player);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                    tabPlayers.setItems(null);
                    tabPlayers.setItems(listPlayers);
                }
            });
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            manager =  ClassManager.getUniqueInstance();
            manager.loadCaterogies();
            categories =  manager.getCategories();
            if (!categories.isEmpty()){
                ObservableList<String> categoriesName = FXCollections.observableArrayList();
                for (ClassCategory category : categories) {
                    category.initialise();
                    categoriesName.add(category.getName());
                }
                cbCategory.setItems(categoriesName);
                cbCategory.getSelectionModel().selectFirst();
                // Je set la categorie du moment
                currentCategory = categories.getFirst();
                manager.setCurrentCategory(currentCategory);
                bindsTabsToDatas();
                fillPage();
            }else disableControls(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
