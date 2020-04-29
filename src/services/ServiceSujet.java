package services;

import com.codename1.io.*;
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
                float id = Float.parseFloat(obj.get("etat").toString());
                t.setId_f((int)id);
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

    public ArrayList<Sujet> getAllTasks(){
        String url = Statics.BASE_URL+"Forum/"+"toussujetsM";
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

    public ArrayList<Sujet> getsubject(Sujet s){
        String url = Statics.BASE_URL+"Forum/"+"subjectidM/"+s.getId_f();
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


}
