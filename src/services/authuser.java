/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;
import com.codename1.components.InteractionDialog;
import com.codename1.components.SpanLabel;
import com.codename1.io.*;
import com.codename1.messaging.Message;
import com.codename1.notifications.LocalNotification;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import java.util.List;

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
import utils.Statics;
//import service.MD5;
/**
 *
 * @author Asus
 */
public class authuser {
    public static User user = new User();
    public static User ConnectedUser ;
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
        String url = "http://localhost/taland/web/app_dev.php/User/loginMobile/"  + username + "/" + password;

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
                users.add(user);
            }

        } catch (IOException ex) {

        }
        return users;
    }
    //user eli logina
    public ArrayList<User> getUser(String username, String password) {

        String url = "http://localhost/taland/web/app_dev.php/User/loginMobile/"  + username + "/" + password;

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



    public void RegisterUser(Resources res) {
        String rol = "1";
        String userlog = SignUpForm.username.getText();
        String pass = SignUpForm.password.getText();
        String email = SignUpForm.email.getText();
        String conpass = SignUpForm.confirmPassword.getText();
        String nom = SignUpForm.nom.getText();
        String prenom = SignUpForm.prenom.getText();
        String role = "a:1:{i:0;s:10:\"ROLE_ADMIN\";}";

        if (nom.equals("") ) {
            InteractionDialog dlg = new InteractionDialog("Notification");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new SpanLabel("Un champ est vide! Veuillez le remplir."));
            Button close = new Button("Close");
            close.addActionListener((ee) -> dlg.dispose());
            dlg.addComponent(BorderLayout.SOUTH, close);
            Dimension pre = dlg.getContentPane().getPreferredSize();
            dlg.show(50, 100, 30, 30);
            return;
        }

        if (!pass.equals(conpass)) {
            Dialog.show("error", "please confirm your password ", "cancel", "ok");
            return;
        }
        if (userlog.equals(".")|| userlog.equals("&")|| userlog.equals("é")|| userlog.equals("'")   ) {
            Dialog.show("Champs Incorrecte", "Corriger le Nom svp  ", "Ok", null);
            return;
        }
        if(userlog.length()==0||pass.length()==0||email.length()==0||conpass.length()==0
                ||nom.length()==0||prenom.length()==0){
            Dialog.show("Champs Incorrecte", "Tous les champs sont obligatoires", "Ok", null);
            return;
        }

        else {
            user.setFirstname(nom);
            user.setPassword(pass);
            user.setEmail(email);
            user.setUsername(userlog);
            user.setLastname(prenom);
            user.setStatus(rol);
        }
        ConnectionRequest connectionRequest;
        connectionRequest = new ConnectionRequest() {
            @Override
            protected void readResponse(InputStream input) throws IOException {
                System.out.println(input);
            }
            @Override
            protected void postResponse() {
                    new SignInForm(res).show();
                    Message m = new Message("Bienvenue dana Taland Mr " +user.getFirstname() + "  votre mot de pass est :" + user.getPassword() );
                    m.getAttachments().put("test", "votre compte à été créer avec succeé/plain");
                    //m.getAttachments().put(imageAttachmentUri, "image/png");
                    Display.getInstance().sendMessage(new String[]{user.getEmail()}, "Bienvenue ", m);
                    System.out.println("corect");
                LocalNotification n = new LocalNotification();
                n.setId("demo-notification");
                n.setAlertBody("It's time to take a break and look at me");
                n.setAlertTitle("Break Time!");
                n.setAlertSound("/notification_sound_bells.mp3"); //file name must begin with notification_sound


                Display.getInstance().scheduleLocalNotification(
                        n,
                        System.currentTimeMillis() + 10 * 1000, // fire date/time
                        LocalNotification.REPEAT_MINUTE  // Whether to repeat and what frequency
                );

            }
        };
//        connectionRequest.setUrl("http://localhost:8081/apijsonpi/web/app_dev.php/api/newuser?username=" + userlog + "&email=" + email + "&password=" + MD5.hash(pass) + "&role=" + rol + "&numtel=" + numtel + "&adresse=" + adresse);
        connectionRequest.setUrl("http://localhost/Taland/web/app_dev.php/User/newuser?username=" + userlog + "&email=" + email + "&password=" + pass +  "&firstname=" + nom + "&lastname=" + prenom);
        NetworkManager.getInstance().addToQueue(connectionRequest);
    }


}