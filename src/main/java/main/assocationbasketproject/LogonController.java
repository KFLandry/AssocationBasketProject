package main.assocationbasketproject;

import db.ConnexionASdb;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import service.ClassGmail;
import variables.ClassFieldFormat;
import variables.ToastMessage;

import javax.mail.MessagingException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogonController {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField fPostal;
    @FXML
    private ChoiceBox<?> radioAddress;
    @FXML
    private ChoiceBox<?> radioCity;
    @FXML
    private ChoiceBox<?> radioCodePhone;
    @FXML
    private ChoiceBox<?> radioCountry;
    @FXML
    private Button btnSignUp;
    @FXML
    private Button btnSubmit;
    @FXML
    private TextField fAddress;
    @FXML
    private TextField fCity;
    @FXML
    private TextField fCountry;
    @FXML
    private TextField fEmail;
    @FXML
    private TextField fLastName;
    @FXML
    private TextField fName;
    @FXML
    private TextField fPhone;
    @FXML
    private DatePicker fBirthday;
    @FXML
    private TextField fLogin;
    @FXML
    private PasswordField fPassword;
    @FXML
    private PasswordField fConfirm;
    @FXML
    private RadioButton intAgree;
    @FXML
    private PasswordField intCodeCheck;
    @FXML
    private Label labelTrace;
    @FXML
    private StackPane stackPane;
    @FXML
    private Circle circleProfile;

    private int code;
    private  int attemp;
    private ConnexionASdb connexionASdb;
    private  String pathImage = "../../../../../../../../../wamp64/www/Cour/Image/IMG_1106.JPG";
    @FXML
    void CheckEmail(ActionEvent event) throws MessagingException, IOException {
        if (!(Objects.equals(intCodeCheck.getText(), ""))){
            if (Integer.parseInt(intCodeCheck.getText()) == code){
                intCodeCheck.setStyle("-fx-border-color:green");
                labelTrace.setText("Code correct");
                labelTrace.setTextFill(Color.GREEN);
                btnSignUp.setDisable(false);
            }else if (attemp < 3){
                intCodeCheck.setStyle("-fx-border-color:red");
                labelTrace.setText("Code incorrect");
                attemp++;
            }else{
                labelTrace.setText("Changer votre email et refaite renvoyer un nouveau code");
            }
        }
    }
    @FXML
    void SendEmailCode(ActionEvent event) throws MessagingException, IOException {
        if (!fEmail.equals("")){
          code =  ClassGmail.checkEmail(fEmail.getText());
          btnSubmit.setDisable(false);
        }
    }
    @FXML
    void Discard(ActionEvent event) throws IOException {
        LinkHaveAccount(event);
    }
    @FXML
    void LinkHaveAccount(ActionEvent event) throws IOException {
        stackPane.getChildren().removeAll();
        stackPane.getChildren().clear();
        Parent fxml =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
        stackPane.getChildren().add(fxml);
    }
    @FXML
    void uploadProfile(ActionEvent event) throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & GIF Images", "jpg", "gif","jpeg");
        fileChooser.setFileFilter(filter);
        int returnChoose = fileChooser.showOpenDialog(null);
        if (returnChoose ==  JFileChooser.APPROVE_OPTION){
        pathImage = fileChooser.getSelectedFile().toURI().toString();
        Image image =  new Image(pathImage);
        circleProfile.setFill(new ImagePattern(image));
        }else ToastMessage.show("Info","Le fichier entré n'est pas pris en compte",3);
    }
    @FXML
    void signUp(ActionEvent event) throws Exception {
        if(intAgree.isSelected()){
            if (!(fName.getText().isEmpty() && fLastName.getText().isEmpty() && fEmail.getText().isEmpty() && fBirthday.getPromptText().isEmpty() && fPhone.getText().isEmpty() && fCountry.getText().isEmpty() && fCity.getText().isEmpty() && fAddress.getText().isEmpty() && fPostal.getText().isEmpty() && fLogin.getText().isEmpty() && fConfirm.getText().isEmpty())){
                if (labelTrace.getText().equals("Code correct")){
                    // Insertaion dans la table ba_coach
                    String[] fields = {"lastName","firstName","email","birth","phone","nationality","city","address","postal","login","password"};
                    String[] values = {fName.getText(),fLastName.getText(),fEmail.getText(), String.valueOf(fBirthday.getValue()),fPhone.getText(),fCountry.getText(),fCity.getText(),fAddress.getText(),fPostal.getText(),fLogin.getText(),fConfirm.getText()};
                    connexionASdb =  new ConnexionASdb();
                  if (connexionASdb.insert("ba_coach",fields,values)>0){
                      // Insert dans la table ba_media dans la table d'associaction
                      int idMedia =  0;
                      int idCoach =  connexionASdb.insert("ba_coach",fields,values);
                      if (!((ImagePattern) circleProfile.getFill()).getImage().isError()){
                          // Uploader l'image en question dans le repertoire image du Projet
                            // Path scr  = Paths.get(pathImage.substring(6))// Path desc =  Paths.get("..\\resources\\Image")// Path path = Files.copy(scr,desc.resolve(scr), StandardCopyOption.REPLACE_EXISTING);

                         fields = new String[]{"description", "typeMime", "dateCreation", "path"};
                          DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                         values = new String[]{"profile", "Image", String.valueOf((LocalDateTime.now()).format(f)), (((ImagePattern) circleProfile.getFill()).getImage()).getUrl()};
                         idMedia  =  connexionASdb.insert("ba_media",fields,values);
                         if (idMedia > 0){
                             fields =  new String[]{"idMedia","idCoach"};
                             values = new String[]{String.valueOf(idMedia), String.valueOf(idCoach)};
                             connexionASdb.insert("ba_middlemediacoach",fields,values);
                         }
                     }
                     Stage stage = new Stage();
                      FXMLLoader fxml =  new FXMLLoader(Objects.requireNonNull(getClass().getResource("demarrage.fxml")));
                     Scene scene  =  new Scene(fxml.load(),1198,740);
                     stage.setScene(scene);
                     stage.setTitle("Association basket App");
                     //stage.setFullScreen(true);
                     stage.show();
                     ((DemarrageController) fxml.getController()).initialise(idCoach);
                 }else System.out.println("L'insertion a échoué");
               }else labelTrace.setFocusTraversable(true);
           }else{
               Notifications.create()
                       .hideAfter(Duration.millis(3))
                       .text("Vérifiez que tous les champs soient remplies")
                       .hideCloseButton()
                       .position(Pos.CENTER)
                       .owner(null)
                       .show();
               fName.setFocusTraversable(true);
           }
       }else intAgree.setFocusTraversable(true);
    }
    @FXML
    void checkPassword(KeyEvent event) {
        PasswordField current = (PasswordField) event.getSource();
        if (current.getText().length() >= 6 && current.getText().length() <= 8){
            current.setStyle("-fx-border-color:green");
        }else current.setStyle("-fx-border-color:red");
    }
    @FXML
    void confirmPassword(KeyEvent event){
        PasswordField current = (PasswordField) event.getSource();
        if(!Objects.equals(current.getText(), current.getText())){
            current.setStyle("-fx-border-color:red");
        } current.setStyle("-fx-border-color:green");
    }
    @FXML
    void handleText(KeyEvent event){
        Pattern motif =  Pattern.compile("[^A-Za-z\\s]");
        TextField currentField  = (TextField) event.getSource();
        Matcher matcher = motif.matcher(currentField.getText());
        if (matcher.find()){
            currentField.setStyle("-fx-border-color:red");
        }else{
            currentField.setStyle("-fx-border-color:green");
        }
    }
    @FXML
    void ckeckInputPostal(KeyEvent event) {
        Pattern motif =  Pattern.compile("[0-9]");
        TextField currentField  = (TextField) event.getSource();
        Matcher matcher = motif.matcher(currentField.getText());
        if (matcher.find() && currentField.getText().length() == 5) {
            currentField.setStyle("-fx-border-color:green");
        } else {
            currentField.setStyle("-fx-border-color:red");
        }
    }
    @FXML
    void handleInputAddress(KeyEvent event){
        Pattern motif =  Pattern.compile("^/w");
        TextField currentField  = (TextField) event.getSource();
        Matcher matcher = motif.matcher(currentField.getText());
        if (matcher.find()){
            currentField.setStyle("-fx-border-color:red");
        }else{
            currentField.setStyle("-fx-border-color:green");
        }
    }
    @FXML
    void checkInputEmail(KeyEvent event) {
        Pattern motif =  Pattern.compile("[A-Za-z0-9\\@\\.+$]");
        TextField currentField  = (TextField) event.getSource();
        Matcher matcher = motif.matcher(currentField.getText());
        if (!matcher.find()){
            System.out.println(matcher);
            currentField.setStyle("-fx-border-color:red");
        }else{
            currentField.setStyle("-fx-border-color:green");
        }
    }
    @FXML
    void CheckInputPhone(KeyEvent event){
        Pattern motif =  Pattern.compile("/D");
        TextField currentField  = (TextField) event.getSource();
        Matcher matcher = motif.matcher(currentField.getText());
        if (!matcher.find() && currentField.getText().length() == 10){
            currentField.setStyle("-fx-border-color:green");
        }else{
            currentField.setStyle("-fx-border-color:red");
        }
    }
    @FXML
    void handleField(MouseEvent event){
        TextField currentField =  (TextField) event.getSource();
        ClassFieldFormat.formatField(currentField,getMotif(currentField));
    }
    private String getMotif(TextField currentField) {
        String motif = null;
        if(currentField.equals(fName) || currentField.equals(fLastName) || currentField.equals(fLogin) || currentField.equals(fCity) || currentField.equals(fCountry)){
            motif =  "label";
        }else if (currentField.equals(fEmail)){
            motif =  "email";
        } else if (currentField.equals(fPhone) || currentField.equals(fPostal)) {
            motif =  "number";
        }else if (currentField.equals(fAddress) || currentField.equals(fPassword) || currentField.equals(fConfirm) ){
            motif =  "text";
        }
        return motif;
    }
}