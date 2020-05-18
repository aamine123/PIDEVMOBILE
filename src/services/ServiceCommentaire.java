package services;

import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import entities.Commentaire;
import entities.Sujet;
import entities.User;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceCommentaire {
    public ArrayList<Commentaire> Comments;
    public static ServiceCommentaire instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    public ServiceCommentaire() {
        req = new ConnectionRequest();
    }

    public static ServiceCommentaire getInstance() {
        if (instance == null) {
            instance = new ServiceCommentaire();
        }
        return instance;
    }

    public ArrayList<Commentaire> parseTasks(String jsonText){
        try {
            Comments=new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json


            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));


            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");

            //Parcourir la liste des tâches Json
            for(Map<String,Object> obj : list){
                //Création des tâches et récupération de leurs données
                Commentaire t = new Commentaire();
                float id = Float.parseFloat(obj.get("idCom").toString());
                System.out.println("azdazdazdazd"+id);
                t.setId_com((int)id);
                Map<String, Object> o = (Map)obj.get("idUser");

                User test=new User();

                String idus = (o.get("username").toString());
                test.setUsername((String) idus);
                t.setId_user(test);
                t.setDescription_com(obj.get("descriptionCom").toString());




             //    float id = Float.parseFloat(obj.get("idCom").toString());
              //  t.setId_com((int)id);
                //float idf = Float.parseFloat(obj.get("idF").toString());
                //t.setId_f((int)idf);
                //t.setDescription_com(obj.get("descriptionCom").toString());



                //Ajouter la tâche extraite de la réponse Json à la liste
                Comments.add(t);
            }


        } catch (IOException ex) {

        }
         /*
            A ce niveau on a pu récupérer une liste des tâches à partir
        de la base de données à travers un service web
        */
        return Comments;
    }

    public ArrayList<Commentaire> getAllcomments( int id ){
        String url = "http://localhost/talandWEB/web/app_dev.php/Forum/affichicommentaireM/"+id;
        System.out.println(url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Comments = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Comments;
    }




}
