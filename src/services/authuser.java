/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;
import com.codename1.components.InteractionDialog;
import com.codename1.components.SpanLabel;
import com.codename1.io.*;
import com.codename1.l10n.DateFormat;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.messaging.Message;
import com.codename1.notifications.LocalNotification;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import entities.Article;
import entities.Sujet;
import entities.User;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.cleanmodern.NewsfeedForm;
import com.codename1.uikit.cleanmodern.SignInForm;
import com.codename1.uikit.cleanmodern.SignUpForm;
import javafx.scene.control.DatePicker;
import utils.Statics;
//import service.MD5;
/**
 *
 * @author Asus
 */
public class authuser {
    // public static User user = new User();
    //public static User ConnectedUser ;
    // public static User onlineId = new User();
    public static authuser instance = null;
    public boolean resultOK;
    private ConnectionRequest req;
    public ArrayList<User> users;

    public authuser() {
        req = new ConnectionRequest();

    }

    public static authuser getInstance() {
        if (instance == null) {
            instance = new authuser();
        }
        return instance;
    }
    //verifi user mawjoud wela yetasef
    public boolean checkLogin(String username, String password) {
        String url = "http://localhost/talandWEB/web/app_dev.php/User/loginMobile/"  + username + "/" + password;

        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>()  {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public ArrayList<User> parseUsers(String jsonText) {
        try {
            users = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");

            for (Map<String, Object> obj : list) {
                User user = new User();
                float id = Float.parseFloat(obj.get("id").toString());
                user.setId((int) id);
                user.setFirstname(obj.get("Firstname").toString());
                user.setLastname(obj.get("Lastname").toString());
                user.setPicture(obj.get("Image").toString());
                user.setUsername(obj.get("Username").toString());
                user.setEmail(obj.get("Email").toString());
                user.setBirthdate(obj.get("Birthdate").toString());
                user.setPassword(obj.get("Password").toString());
                users.add(user);
            }

        } catch (IOException ex) {

        }
        return users;
    }
    //user eli logina
    public ArrayList<User> getUser(String username, String password) {

        String url = "http://localhost/talandWEB/web/app_dev.php/User/loginMobile/"  + username + "/" + password;

        req.setUrl(url);
        // req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                users = parseUsers(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return users;
    }



    public void RegisterUser(Resources res) throws ParseException {
        User user=new User();
        //  String rol = "1";
        String userlog = SignUpForm.username.getText();
        String pass = SignUpForm.password.getText();
        String email = SignUpForm.email.getText();
        String conpass = SignUpForm.confirmPassword.getText();
        String nom = SignUpForm.nom.getText();
        String prenom = SignUpForm.prenom.getText();
        //String role = "a:1:{i:0;s:10:\"ROLE_ADMIN\";}";
        //String date="ad";
        //System.out.println(date);
        // Date date1=new SimpleDateFormat("dd/mm/yyyy").parse(date);
        //SimpleDateFormat formatter = new SimpleDateFormat("mm/dd/yy");
        // Date datee = formatter.parse(String.valueOf(date));
        // Date date = format.parse(string);
        //LocalDate myObj = LocalDate.now();
        //Date date2=new SimpleDateFormat("dd/MM/yyyy").parse(String.valueOf(myObj));
        //   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat sdfDate = new SimpleDateFormat("mm/dd/yy");//dd/MM/yyyy
        // Date now = new Date();
        //String strDate = sdfDate.format(now);
        //System.out.println("type"+strDate);
        // Date date2 = formatter.parse(String.valueOf(strDate));
        //LocalDateTime now = LocalDateTime.now();
        //Date dateee = formatter.parse(String.valueOf(now));
        //System.out.println("ano"+dateee);


        if (nom.equals("") ) {
            InteractionDialog dlg = new InteractionDialog("Notification");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new SpanLabel("Le champ nom est vide! Veuillez le remplir."));
            Button close = new Button("Close");
            close.addActionListener((ee) -> dlg.dispose());
            dlg.addComponent(BorderLayout.SOUTH, close);
            Dimension pre = dlg.getContentPane().getPreferredSize();
            dlg.show(50, 100, 30, 30);
            return;
        }


        if (userlog.equals(".")|| userlog.equals("&")|| userlog.equals("é")|| userlog.equals("'")   ) {
            Dialog.show("Champs Incorrecte", "Corriger le Nom svp  ", "Ok", null);
            return;
        }
        if (!pass.equals(conpass)) {
            Dialog.show("error", "please confirm your password ", "cancel", "ok");
            return;
        }
        if (!pass.equals(conpass)) {
            Dialog.show("error", "please confirm your password ", "cancel", "ok");
            return;
        }


        if(userlog.length()==0||pass.length()==0||email.length()==0||conpass.length()==0
                ||nom.length()==0||prenom.length()==0){
            Dialog.show("Champs Incorrecte", "Tous les champs sont obligatoires", "Ok", null);
            return;
        }

        else {
            User u =new User();

            user.setFirstname(nom);
            user.setPassword(pass);
            user.setEmail(email);
            user.setUsername(userlog);
            user.setLastname(prenom);
            // user.setStatus(rol);
            // user.setBirthdate(date);
        }
        ConnectionRequest connectionRequest;
        connectionRequest = new ConnectionRequest() {
            @Override
            protected void readResponse(InputStream input) throws IOException {
                System.out.println(input);
            }
            @Override
            protected void postResponse() {


            }
        };
//        connectionRequest.setUrl("http://localhost:8081/apijsonpi/web/app_dev.php/api/newuser?username=" + userlog + "&email=" + email + "&password=" + MD5.hash(pass) + "&role=" + rol + "&numtel=" + numtel + "&adresse=" + adresse);
        connectionRequest.setUrl("http://localhost/TalandWEB/web/app_dev.php/User/createUtulisateur/" + userlog + "/" + nom + "/" + prenom +  "/" + email + "/" + pass ) ;
        NetworkManager.getInstance().addToQueue(connectionRequest);
    }

    public void adduser(User ar) {
        ConnectionRequest con = new ConnectionRequest();
        // SimpleDateFormat tempss = new SimpleDateFormat("yyyy-MM-dd");
        // String datedeb = tempss.format(ev.getDATED_EVENT());

        String Url = "http://localhost/talandWEB/web/app_dev.php/User/createUtilisateur"
                +"/" + ar.getUsername()

                + "/" + ar.getFirstname()+
                "/" + ar.getLastname()+


                "/" + ar.getEmail()+

                "/" + ar.getPassword()


                ;
        String userlog = SignUpForm.username.getText();
        String pass = SignUpForm.password.getText();
        String email = SignUpForm.email.getText();
        String conpass = SignUpForm.confirmPassword.getText();
        String nom = SignUpForm.nom.getText();
        String prenom = SignUpForm.prenom.getText();
        if ( userlog.equals("")  ) {
            InteractionDialog dlg = new InteractionDialog("Notification");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new SpanLabel("Le champ username est vide"));
            Button close = new Button("Close");
            close.addActionListener((ee) -> dlg.dispose());
            dlg.addComponent(BorderLayout.SOUTH, close);
            Dimension pre = dlg.getContentPane().getPreferredSize();
            dlg.show(30, 30, 30, 30);
            return;
        }
        if ( userlog.contains("&") || userlog.contains("#") || userlog.contains("$")   ) {
            InteractionDialog dlg = new InteractionDialog("Notification");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new SpanLabel("Verifier le champ username "));
            Button close = new Button("Close");
            close.addActionListener((ee) -> dlg.dispose());
            dlg.addComponent(BorderLayout.SOUTH, close);
            Dimension pre = dlg.getContentPane().getPreferredSize();
            dlg.show(50, 50, 50, 50);
            return;
        }
        if (email.equals("") ) {
            InteractionDialog dlg = new InteractionDialog("Notification");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new SpanLabel("Remplir le champ email"));
            Button close = new Button("Close");
            close.addActionListener((ee) -> dlg.dispose());
            dlg.addComponent(BorderLayout.SOUTH, close);
            Dimension pre = dlg.getContentPane().getPreferredSize();
            dlg.show(50, 100, 30, 30);
            return;
        }

        if (pass.equals("") || conpass.equals("") ) {
            InteractionDialog dlg = new InteractionDialog("Notification");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new SpanLabel("Remplir le champ password"));
            Button close = new Button("Close");
            close.addActionListener((ee) -> dlg.dispose());
            dlg.addComponent(BorderLayout.SOUTH, close);
            Dimension pre = dlg.getContentPane().getPreferredSize();
            dlg.show(50, 100, 30, 30);
            return;
        }
        if (!pass.equals(conpass)) {
            Dialog.show("error", "Les mots de passes ne sont pas identiques", "cancel", "ok");
            return;
        }

        if (nom.equals("") ) {
            InteractionDialog dlg = new InteractionDialog("Notification");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new SpanLabel("Le champ nom est vide! Veuillez le remplir."));
            Button close = new Button("Close");
            close.addActionListener((ee) -> dlg.dispose());
            dlg.addComponent(BorderLayout.SOUTH, close);
            Dimension pre = dlg.getContentPane().getPreferredSize();
            dlg.show(50, 100, 30, 30);
            return;
        }
        if (prenom.equals("") ) {
            InteractionDialog dlg = new InteractionDialog("Notification");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new SpanLabel("Le champ prenom est vide! Veuillez le remplir."));
            Button close = new Button("Close");
            close.addActionListener((ee) -> dlg.dispose());
            dlg.addComponent(BorderLayout.SOUTH, close);
            Dimension pre = dlg.getContentPane().getPreferredSize();
            dlg.show(50, 100, 30, 30);
            return;
        }





        // System.out.println("L'URL est : : :" + Url);
        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion
        Message m = new Message("Body of message");

        Display.getInstance().sendMessage(new String[] {"mohamedamine.mbarki@esprit.tn"}, "Bienvenue a taland "
                +nom+"votre username est:"+userlog
                +" votre mot de passe est:"+pass, m);
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            //  System.out.println(str);//Affichage de la réponse serveur sur la console

        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
    }



}