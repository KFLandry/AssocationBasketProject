package service;

import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
/* class to demonstrate use of Gmail list messages */
public class ClassGmail{
    /**
     * Application name.
     */
    private static final String USER_TEST =  "kankeulandry26@gmail.com";

    private static final String APPLICATION_NAME = "test API Gmail";
    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    /**
     * Directory to store authorization tokens for this application.
     */
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_MODIFY);
    private static final String CREDENTIALS_FILE_PATH = "/client_secret.json";
    private static Gmail authorize () throws NullPointerException,IOException,GeneralSecurityException {
        // Load client secrets.
           final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
           InputStream in = ClassGmail.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
           GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,new InputStreamReader(in));
            // Build flow and trigger user authorization request.
            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                    .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                    .setAccessType("offline")
                    .build();
            LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();

            // Build a new authorized API client service with credentials loaded up
            return new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                    new AuthorizationCodeInstalledApp(flow, receiver).authorize("user"))
                    .setApplicationName(APPLICATION_NAME)
                    .build();
    }
    public List<Message> getMessages () throws IOException, GeneralSecurityException {
        // Print the labels in the user's account.
        List<Message> tempMessages = new ArrayList<Message>();
        String user = "kankeulandry26@gmail.com";
        List<String> LabelsID = new ArrayList<String>();
        LabelsID.add("SPAM");
        List<Message> messagesResponse = (List<Message>) Objects.requireNonNull(authorize()).users().messages().list(USER_TEST).setLabelIds(LabelsID).setMaxResults(8L).execute().getMessages();
        for(Message mail : messagesResponse){
            Message fullMail  = Objects.requireNonNull(authorize()).users().messages().get(USER_TEST,mail.getId()).execute();
            if (fullMail != null) {
                tempMessages.add(fullMail);
            }
        }
        return tempMessages;
    }
}