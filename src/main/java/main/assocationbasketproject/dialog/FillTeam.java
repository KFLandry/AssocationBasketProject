package main.assocationbasketproject.dialog;

import db.ClassCategory;
import db.ClassPlayer;
import db.ClassTeam;
import db.ConnexionASdb;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import manager.ClassManager;
import variables.ClassFieldFormat;
import variables.ToastMessage;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class FillTeam implements Initializable {
    @FXML
    public TextField fId;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnFill;
    @FXML
    private Button btnUndo;
    @FXML
    private Label Gender;
    @FXML
    private Circle circleProfile;
    @FXML
    private ChoiceBox<String> cbAddress;
    @FXML
    private ChoiceBox<String> cbCity;
    @FXML
    private ChoiceBox<Integer> cbCodeEPhone;
    @FXML
    private ChoiceBox<Integer> cbCodePhone;
    @FXML
    private ChoiceBox<Integer> cbCountry;
    @FXML
    private ComboBox<String> cbGender;
    @FXML
    private ComboBox<String> cbPosition;
    @FXML
    private ChoiceBox<Integer> cbPostal;
    @FXML
    private DatePicker dBirth;
    @FXML
    private TextField fAddress;
    @FXML
    private TextField fCity;
    @FXML
    private TextField fCountry;
    @FXML
    private TextArea fDesc;
    @FXML
    private TextField fEPhone;
    @FXML
    private TextField fWeight;
    @FXML
    private TextField fHeight;
    @FXML
    private TextField fEmail;
    @FXML
    private TextField fFName;
    @FXML
    private TextField fLName;
    @FXML
    private TextField fPhone;
    @FXML
    private TextField fPostal;
    @FXML
    private Label lDate;
    @FXML
    private Label lName;
    @FXML
    private Label lGamePlan;
    @FXML
    private Label lGamePriority;
    @FXML
    private Label lGender;
    @FXML
    private Label lTeam;
    @FXML
    private TableView<ClassPlayer> tabPlayer;
    @FXML
    public TableColumn<ClassPlayer, Integer> cId;
    @FXML
    private TableColumn<ClassPlayer,Integer> cIdTeam;
    @FXML
    private TableColumn<ClassPlayer,String> cFirstName;
    @FXML
    private TableColumn<ClassPlayer,String> cLastName;
    @FXML
    private TableColumn<ClassPlayer,String> cEmail;
    @FXML
    private TableColumn<ClassPlayer, LocalDate> cBirthDay;
    @FXML
    private TableColumn<ClassPlayer,String> cCountry;
    @FXML
    private TableColumn<ClassPlayer,String> cCity;
    @FXML
    private TableColumn<ClassPlayer,String> cAddress;
    @FXML
    private TableColumn<ClassPlayer,Integer> cPostal;
    @FXML
    private TableColumn<ClassPlayer,Integer> cPhone;
    @FXML
    private TableColumn<ClassPlayer,Integer> cPhoneEmergency;
    @FXML
    private TableColumn<ClassPlayer,Integer> cHeight;
    @FXML
    private TableColumn<ClassPlayer,Integer> cWeight;
    @FXML
    private TableColumn<ClassPlayer,String> cPosition;
    @FXML
    private TableColumn<ClassPlayer,String> cDescription;
    @FXML
    public TableColumn<ClassPlayer,String> cPathProfile;
    private ObservableList<ClassPlayer> listPlayer =  FXCollections.observableArrayList();
    private final ArrayList<ClassPlayer> cloneInitialPlayer =  new ArrayList<>();// Stocke le state des joueurs deja existant dans la base
    private  ConnexionASdb connexionASdb;
    private ClassTeam currentTeam;
    private ClassCategory currentCategory;
    private ClassPlayer currentPlayer;
    private final Queue<ClassPlayer> playerQueue =  new ArrayDeque<>();
    @FXML
    void editPlayer(){
        currentPlayer =  tabPlayer.getSelectionModel().getSelectedItem();
        formatInFields();
    }
    @FXML
    void add() {
        // Insertaion dans la table ba_coach
        if (!(fFName.getText().isEmpty() && fLName.getText().isEmpty() && fEmail.getText().isEmpty() && dBirth.getValue()==null && fCountry.getText().isEmpty() && fCity.getText().isEmpty() && fAddress.getText().isEmpty() && fPostal.getText().isEmpty() && fEPhone.getText().isEmpty() && fPhone.getText().isEmpty() && fHeight.getText().isEmpty() && fWeight.getText().isEmpty() && fDesc.getText().isEmpty())){
            Paint fill = circleProfile.getFill();
            Image image =  null;
            if (fill instanceof ImagePattern) image =  ((ImagePattern) fill).getImage();
            if (image != null){
                ClassPlayer temp  =  new ClassPlayer(
                        currentTeam.getId(),
                        cbGender.getSelectionModel().getSelectedItem(),
                        fFName.getText(),
                        fLName.getText(),
                        fEmail.getText(),
                        Date.valueOf(dBirth.getValue()),
                        fCountry.getText(),
                        fCity.getText(),
                        fAddress.getText(),
                        Integer.parseInt(fPostal.getText()),
                        Integer.parseInt(fEPhone.getText()),
                        Integer.parseInt(fPhone.getText()),
                        Integer.parseInt(fHeight.getText()),
                        Integer.parseInt(fWeight.getText()),
                        cbPosition.getSelectionModel().getSelectedItem(),
                        fDesc.getText(),
                        image.getUrl()
                );
                if(Integer.parseInt(fId.getText())>0) temp.setId(Integer.parseInt(fId.getText()));
                tabPlayer.getItems().add(temp);
                cbPosition.getItems().removeIf(position -> position.equals(temp.getPosition()));
                emptyFields();
            }else ToastMessage.show("Info","Veillez selectionner une photo de profile",3);
        }else ToastMessage.show("Info","Veillez remplir tous les champs avant de valider!", 3);
    }
    private void emptyFields(){
        fId.setText("");
        fFName.setText("");
        fLName.setText("");
        fEmail.setText("");
        dBirth.setValue(LocalDate.now());
        fCountry.setText("");
        fCity.setText("");
        fAddress.setText("");
        fPostal.setText("");
        fEPhone.setText("");
        fPhone.setText("");
        fHeight.setText("");
        fWeight.setText("");
        cbPosition.getSelectionModel().selectFirst();
        fDesc.setText("");
        circleProfile.setFill(null);

        btnFill.setDisable(false);
        btnClear.setDisable(false);
        btnDelete.setDisable(false);
        btnUndo.setDisable(false);


    }
    @FXML
    void save() throws SQLException, NoSuchAlgorithmException {
        if(!tabPlayer.getItems().isEmpty()){
            String[] fields = {"idTeam","gender","lastName","firstName","email","birthday","description","country","city","address","postal","phone","phoneEmergency","height","weight","position","hurt","available"};
            List<ClassPlayer> listInserts = tabPlayer.getItems().stream().filter(player -> player.getId() == 0).toList();
            //Insertions des joueurs
            String[] values  = listInserts.stream().map(ClassPlayer::toString).toArray(String[]::new);
            if (values.length>0){
                int idLastPlayerInsert =  connexionASdb.insert("ba_player",fields,values);
                if (idLastPlayerInsert > 0){
                    for (ClassPlayer player : listInserts){
                        player.setId(idLastPlayerInsert);
                        fields = new String[]{"description", "typeMime", "dateCreation", "path"};
                        values  = new String[]{"Image de profile","Image",String.valueOf(dBirth.getValue()),player.getPathProfile()};
                        int idLastMedia = connexionASdb.insert("ba_media",fields,values);

                        // Remplissage de la table d'association

                        fields =  new String[]{"idMedia","idPlayer"};
                        values = new String[]{String.valueOf(idLastMedia), String.valueOf(idLastPlayerInsert)};
                        connexionASdb.insert("ba_middlemediaplayer",fields,values);
                        idLastPlayerInsert--;
                    }
                }
            }

            List<ClassPlayer> listUpdates =  tabPlayer.getItems().stream().filter(player -> player.getId() > 0).toList();
            // Mise à jour des joueurs si changement
            if (!cloneInitialPlayer.containsAll(tabPlayer.getItems())){
                cloneInitialPlayer.clear();
                for (ClassPlayer player : listUpdates){
                    fields = new String[]{"gender", "lastName", "firstName", "email", "birthday", "description", "country", "city", "address", "postal", "phone", "phoneEmergency", "height", "weight", "position"};
                    String sValues =  player.toString(true);
                    values = sValues.split(",");

                    connexionASdb.update(player.getId(),"ba_player",fields,values);

                    fields = new String[]{"description", "typeMime", "path"};
                    values  = new String[]{"Image de profile","Image",player.getPathProfile()};
                    if(!player.getMedias().isEmpty()) connexionASdb.update(player.getMedias().getFirst().getId(),"ba_media",fields,values);

                    cloneInitialPlayer.add(player.clone());
                }
            }else ToastMessage.show("Info","Aucunes modifications n'a été faites",3);
        }
    }
    @FXML
    void saveTitles() {
    }
    @FXML
    void delete(){
        tabPlayer.getItems().forEach(player -> {
            try {
                if (player.equals(tabPlayer.getSelectionModel().getSelectedItem())){
                    if (player.getId()>0){
                        if (JOptionPane.showConfirmDialog(null,"Il s'agit d'un joueur deja persister dans votre base.Est vous sûr de vouloir le supprimmer definitivemenT??","Confirmer l'action",JOptionPane.YES_NO_OPTION)==0){
                            connexionASdb.delete("ba_player",player.getId());
                            playerQueue.offer(player);
                            tabPlayer.getItems().remove(player);
                        }
                    }else{
                        playerQueue.offer(player);
                        tabPlayer.getItems().remove(player);
                    }
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        });
    }
    @FXML
    void deleteAll(){
        playerQueue.addAll(tabPlayer.getItems());
        tabPlayer.getItems().removeAll();
    }
    @FXML
    void undo(){
        if (!playerQueue.isEmpty()){
            while(!playerQueue.isEmpty()){
                tabPlayer.getItems().add(playerQueue.poll());
            }
        }
    }
    @FXML
    private void formatInFields(){
        currentPlayer =  tabPlayer.getSelectionModel().getSelectedItem();
        if (currentPlayer != null){
            fId.setText(String.valueOf(currentPlayer.getId()));
            fFName.setText(currentPlayer.getFirstName());
            fLName.setText(currentPlayer.getLastName());
            fEmail.setText(currentPlayer.getEmail());
            dBirth.setValue(LocalDate.parse(currentPlayer.getBirthDay().toString()));
            fCountry.setText(currentPlayer.getCountry());
            fCity.setText(currentPlayer.getCity());
            fAddress.setText(currentPlayer.getAddress());
            fPostal.setText(String.valueOf(currentPlayer.getPostal()));
            fEPhone.setText(String.valueOf(currentPlayer.getPhoneEmergency()));
            fPhone.setText(String.valueOf(currentPlayer.getPhone()));
            fHeight.setText(String.valueOf(currentPlayer.getHeight()));
            fWeight.setText(String.valueOf(currentPlayer.getWeight()));
            cbPosition.getSelectionModel().select(currentPlayer.getPosition());
            fDesc.setText(currentPlayer.getDescription());
            if (!currentPlayer.getMedias().isEmpty()){
                Image image  =  new Image(currentPlayer.getMedias().getFirst().getPath());
                circleProfile.setFill(new ImagePattern(image));
            }
            tabPlayer.getItems().remove(currentPlayer);
        }
    }
    @FXML
    private void addProfile(MouseEvent event){
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & GIF Images", "jpg", "gif","jpeg");
        fileChooser.setFileFilter(filter);
        int returnChoose = fileChooser.showOpenDialog(null);
        if (returnChoose ==  JFileChooser.APPROVE_OPTION){
            String pathImage = fileChooser.getSelectedFile().toURI().toString();
            Image image =  new Image(pathImage);
            circleProfile.setFill(new ImagePattern(image));
        }else ToastMessage.show("Info","Le fichier entré n'est pas pris en compte",3);
    }
    @FXML
    void formatField(MouseEvent event) {
        TextField current  = (TextField) event.getSource();
        ClassFieldFormat.formatField(current,getFieldType(current));
    }
    private  String getFieldType(TextField textField){
        String motif = null;
        if(textField.equals(fFName) || textField.equals(fLName) || textField.equals(fCity) || textField.equals(fCountry)){
            motif =  "label";
        }else if (textField.equals(fEmail)){
            motif =  "email";
        } else if (textField.equals(fPhone) || textField.equals(fPostal) || textField.equals(fEPhone) || textField.equals(fHeight) || textField.equals(fWeight)) {
            motif =  "number";
        }else if (textField.equals(fAddress)){
            motif =  "text";
        }
        return motif;
    }
    private void BindTabClass(){
        cId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        cIdTeam.setCellValueFactory(new PropertyValueFactory<>("IdTeam"));
        cFirstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        cLastName.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        cEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        cBirthDay.setCellValueFactory(new PropertyValueFactory<>("BirthDay"));
        cCountry.setCellValueFactory(new PropertyValueFactory<>("Country"));
        cCity.setCellValueFactory(new PropertyValueFactory<>("City"));
        cAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        cPostal.setCellValueFactory(new PropertyValueFactory<>("Postal"));
        cPhone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        cPhoneEmergency.setCellValueFactory(new PropertyValueFactory<>("PhoneEmergency"));
        cHeight.setCellValueFactory(new PropertyValueFactory<>("Height"));
        cWeight.setCellValueFactory(new PropertyValueFactory<>("Weight"));
        cPosition.setCellValueFactory(new PropertyValueFactory<>("Position"));
        cDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        // Le chemin du fichier uploader lier
        cPathProfile.setCellValueFactory(new PropertyValueFactory<>("PathProfile"));
        //On rend editable toutes les colonnes du tableau TabPlayers
        cIdTeam.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        cFirstName.setCellFactory(TextFieldTableCell.forTableColumn());
        cLastName.setCellFactory(TextFieldTableCell.forTableColumn());
        cEmail.setCellFactory(TextFieldTableCell.forTableColumn());
        cBirthDay.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
        cCountry.setCellFactory(TextFieldTableCell.forTableColumn());
        cCity.setCellFactory(TextFieldTableCell.forTableColumn());
        cAddress.setCellFactory(TextFieldTableCell.forTableColumn());
        cPostal.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        cPhone.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter() ));
        cPhoneEmergency.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        cHeight.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        cWeight.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        cPosition.setCellFactory(TextFieldTableCell.forTableColumn());
        cDescription.setCellFactory(TextFieldTableCell.forTableColumn());
        //On recupere les valeurs après edition ...
        cFirstName.setOnEditCommit( item -> {
            ClassPlayer player = item.getRowValue();
            player.setFirstName(item.getNewValue());
        });
        cLastName.setOnEditCommit( item -> {
            ClassPlayer player = item.getRowValue();
            player.setLastName(item.getNewValue());
        });
        cEmail.setOnEditCommit( item -> {
            ClassPlayer player = item.getRowValue();
            player.setEmail(item.getNewValue());
        });
        cCountry.setOnEditCommit( item -> {
            ClassPlayer player = item.getRowValue();
            player.setCountry(item.getNewValue());
        });
        cCity.setOnEditCommit( item -> {
            ClassPlayer player = item.getRowValue();
            player.setCity(item.getNewValue());
        });

    }
    public void fillPage() throws Exception {
        lName.setText(currentCategory.getName());
        lDate.setText(currentCategory.getDateCreation().toString());
        lGender.setText(currentCategory.getGender());
        lTeam.setText(currentTeam.getName());
        lGamePriority.setText(currentTeam.getGamePriority());
        lGamePlan.setText(currentTeam.getGamePlan());

        ObservableList<String> listPosition = FXCollections.observableArrayList(new String[]{"PG - Point Guard", "SG - Shooting Guard"," SF - Small Forward", "PF - Power Forward", "C - Center"});
        cbPosition.setItems(listPosition);
        cbPosition.getSelectionModel().selectFirst();

        ObservableList<String> gender = FXCollections.observableArrayList(new String[]{"Man","Woman"});
        cbGender.setItems(gender);
        cbGender.getSelectionModel().select(currentCategory.getGender());

        if (!currentTeam.getPlayers().isEmpty()) {
            for (ClassPlayer player : currentTeam.getPlayers()) {
                player.initialise();
                cloneInitialPlayer.add(player.clone());
                listPlayer.add(player);
            }
        }else{
            btnFill.setDisable(true);
            btnClear.setDisable(true);
            btnDelete.setDisable(true);
            btnUndo.setDisable(true);
        }
        tabPlayer.setItems(listPlayer);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            listPlayer =  FXCollections.observableArrayList();
            currentCategory =  ClassManager.getUniqueInstance().getCurrentCategory();
            currentTeam = ClassManager.getUniqueInstance().getCurrentCategory().getCurrentTeam();
            connexionASdb =  new ConnexionASdb();
            BindTabClass();
            fillPage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

