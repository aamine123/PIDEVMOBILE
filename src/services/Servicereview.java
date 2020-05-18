package services;

import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.sun.org.apache.bcel.internal.generic.ARETURN;
import entities.Article;
import entities.Commentaire;
import entities.User;
import entities.review;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Servicereview {
    public ArrayList<review> Reviews;
    public static Servicereview instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    public Servicereview() {
        req = new ConnectionRequest();
    }

    public static Servicereview getInstance() {
        if (instance == null) {
            instance = new Servicereview();
        }
        return instance;
    }
    public ArrayList<review> parseTasks(String jsonText){
        try {
            Reviews=new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json


            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));


            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");

            //Parcourir la liste des tâches Json
            for(Map<String,Object> obj : list){
                //Création des tâches et récupération de leurs données
                review t = new review();
                float id = Float.parseFloat(obj.get("id").toString());
                System.out.println("azdazdazdazd"+id);
                t.setId((int)id);
                Map<String, Object> o = (Map)obj.get("iduser");
                User test=new User();
                String idus = (o.get("username").toString());
                test.setUsername((String) idus);
                float idd = Float.parseFloat(o.get("id").toString());
                test.setId((int)idd);
                t.setIduser(test);
                Map<String, Object> a = (Map)obj.get("idarticle");
                Article ar=new Article();
                float ida = Float.parseFloat(o.get("idArticle").toString());
                t.setIdarticle(ar);




                //    float id = Float.parseFloat(obj.get("idCom").toString());
                //  t.setId_com((int)id);
                //float idf = Float.parseFloat(obj.get("idF").toString());
                //t.setId_f((int)idf);
                //t.setDescription_com(obj.get("descriptionCom").toString());



                //Ajouter la tâche extraite de la réponse Json à la liste
                Reviews.add(t);
            }


        } catch (IOException ex) {

        }
         /*
            A ce niveau on a pu récupérer une liste des tâches à partir
        de la base de données à travers un service web

        */
        return Reviews;
    }

    public void rate(int rate,int idu,Article ar) {
        String url = "http://localhost/talandWEB/web/app_dev.php/Article/rateM/"+rate+"/"+idu+"/"+ar.getId_Article();
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
    public void moyrate(Article ar) {
        String url = "http://localhost/talandWEB/web/app_dev.php/Article/ratemoyenneM/"+ar.getId_Article();
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

}
