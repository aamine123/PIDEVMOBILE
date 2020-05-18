package services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import entities.Sujet;
import utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class ServiceSujet {
    public ArrayList<Sujet> Sujets;
    public static ServiceSujet instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    public ServiceSujet() {
        req = new ConnectionRequest();
    }

    public static ServiceSujet getInstance() {
        if (instance == null) {
            instance = new ServiceSujet();
        }
        return instance;
    }


    public ArrayList<Sujet> parseTasks(String jsonText){
        try {
            Sujets=new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json


            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));


            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");

            //Parcourir la liste des tâches Json
            for(Map<String,Object> obj : list){
                //Création des tâches et récupération de leurs données
                Sujet t = new Sujet();
                float id = Float.parseFloat(obj.get("idF").toString());
                t.setId_f((int)id);
               //  float iduser = Float.parseFloat(obj.get("idUser").toString());
                //t.setId_f((int)iduser);
                float nbreJaime = Float.parseFloat(obj.get("nbreJaime").toString());
                t.setNbre_jaime((int)nbreJaime);
                t.setDescription_f(obj.get("descriptionF").toString());



                //Ajouter la tâche extraite de la réponse Json à la liste
                Sujets.add(t);
            }


        } catch (IOException ex) {

        }
         /*
            A ce niveau on a pu récupérer une liste des tâches à partir
        de la base de données à travers un service web

        */
        return Sujets;
    }

    public ArrayList<Sujet> parseTasks2(String jsonText){
        try {
            Sujets=new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json


            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));


            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");

            //Parcourir la liste des tâches Json
            for(Map<String,Object> obj : list){
                //Création des tâches et récupération de leurs données
                Sujet t = new Sujet();
                float id = Float.parseFloat(obj.get("id_f").toString());
                t.setId_f((int)id);
                //  float iduser = Float.parseFloat(obj.get("idUser").toString());
                //t.setId_f((int)iduser);
                float nbreJaime = Float.parseFloat(obj.get("nbre_jaime").toString());
                t.setNbre_jaime((int)nbreJaime);
                t.setDescription_f(obj.get("description_f").toString());



                //Ajouter la tâche extraite de la réponse Json à la liste
                Sujets.add(t);
            }


        } catch (IOException ex) {

        }
         /*
            A ce niveau on a pu récupérer une liste des tâches à partir
        de la base de données à travers un service web

        */
        return Sujets;
    }



    public ArrayList<Sujet> getAllTasks(){
        String url = Statics.BASE_URL+"Forum/"+"toussujetsM";
        System.out.println(url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Sujets = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Sujets;
    }

    public ArrayList<Sujet> top5sujetM(){
        String url = Statics.BASE_URL+"Forum/"+"top5sujetM";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Sujets = parseTasks2(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Sujets;
    }
    public void modifiersujet(int id,String desc) {
        String url = "http://localhost/talandWEB/web/app_dev.php/Forum/updatesujetM/"+id+"/"+desc;
        req.setUrl(url);
        req.setPost(false);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);

    }
    public void like(int id) {
        String url = "http://localhost/talandWEB/web/app_dev.php/Forum/likem/"+id;
        req.setUrl(url);
        req.setPost(false);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);

    }
    public void supprimersujet(int id) {
        //String url = Statics.BASE_URL + "Forum/" +"updatesujetM/"+id+"?"+ desc ; //création de l'URL
        String url = "http://localhost/talandWEB/web/app_dev.php/Forum/supprimersujetM/"+id;
        req.setUrl(url);
        req.setPost(false);

            req.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent evt) {
                    req.removeResponseListener(this);
                }
            });
            NetworkManager.getInstance().addToQueueAndWait(req);


        }

    public boolean addsujet(int idu,  String desc){
        String url = "http://localhost/talandWEB/web/app_dev.php/Forum/ajoutersujetM/"+idu+"/"+desc;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return true;
    }



}
