/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.codename1.uikit.cleanmodern.detailarticletemplate;
import entities.Article;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.cleanmodern.AffichageArticle;
import entities.Commentaire;
import entities.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public class ServiceArticle {


    public ArrayList<Article> Articles;
    public static ServiceArticle instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    public ServiceArticle() {
        req = new ConnectionRequest();
    }

    public static ServiceArticle getInstance() {
        if (instance == null) {
            instance = new ServiceArticle();
        }
        return instance;
    }


    public void ajoutArticle(Article ar) {
        ConnectionRequest con = new ConnectionRequest();
        SimpleDateFormat tempss = new SimpleDateFormat("yyyy-MM-dd");
        // String datedeb = tempss.format(ev.getDATED_EVENT());

        String Url = "http://localhost/talandWEB/web/app_dev.php/Article/newM"
                +"?Nom_Article=" + ar.getNom_Article()

                + "&Contenu_Article=" + ar.getContenu_Article()+
                "&Image_Article=" + ar.getImage_Article()+
                // "&Date_Article=" + ar.getDate_Article()+

                "&Id_User=" + ar.getId_User()+

                "&Titre_Event=" + ar.getTitre_Event()+

                "&nbrevue=" + ar.getNbrevue()
                ;

        System.out.println("L'URL est : : :" + Url);
        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);//Affichage de la réponse serveur sur la console

        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
    }







    public ArrayList<Article> parseListTaskJson(String json) {
        System.out.println("json"+json);
        ArrayList<Article> listTasks = new ArrayList<>();

        try {
            JSONParser j = new JSONParser();

            Map<String, Object> Articles = j.parseJSON(new CharArrayReader(json.toCharArray()));


            List<Map<String, Object>> list = (List<Map<String, Object>>) Articles.get("root");

            for (Map<String, Object> obj : list) {

                Article ar = new Article();

                float id = Float.parseFloat(obj.get("idArticle").toString());

                SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                // String datedeb = formater.format(ar.getDate_Article());


                ar.setId_Article((int) id);
                ar.setNom_Article(obj.get("nomArticle").toString());

                ar.setDate_Article(obj.get("dateArticle").toString());
                ar.setContenu_Article(obj.get("contenuArticle").toString());


                float nbp = Float.parseFloat(obj.get("nbrevue").toString());
                ar.setNbrevue((int) nbp);


                float us = Float.parseFloat(obj.get("idUser").toString());
                ar.setId_User((int) us);

                ar.setTitre_Event(obj.get("titreEvent").toString());
                ar.setImage_Article(obj.get("imageArticle").toString());

                System.out.println(ar);

                listTasks.add(ar);

            }

        } catch (IOException ex) {
        }

        System.out.println(listTasks);
        return listTasks;

    }

    ArrayList<Article> listTasks = new ArrayList<>();
    public ArrayList<Article> getList2(){
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/talandWEB/web/app_dev.php/Article/allArticle");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceArticle ser = new ServiceArticle();
                listTasks = ser.parseListTaskJson(new String(con.getResponseData()));
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(con);
        return listTasks;
    }


    public boolean modifierArticle(int id,String nom,String description,Resources res) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://localhost/talandWEB/web/app_dev.php/Article/updateArticle/"+ id+"/" + nom
                + "/" + description;
        con.setUrl(Url);

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
          //  System.out.println(str);
            Dialog.show("Succés", "Article modifié", "ok", null);

        //    detailarticletemplate a =new detailarticletemplate(res,ta);
          //  a.show();

        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return true;
    }

    public void nbvue(Article ta,Resources res) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://localhost/talandWEB/web/app_dev.php/Article/nbvue/"+ ta.getId_Article();
        con.setUrl(Url);

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);



        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }

    public void findarticle(Article ta,Resources res) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://localhost/talandWEB/web/app_dev.php/Article/findarticle/"+ ta.getNom_Article();
        con.setUrl(Url);

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);



        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }

    public void imprimer(Article ta,Resources res) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://localhost/talandWEB/web/app_dev.php/Article/imprimerM/"+ ta.getId_Article();
        con.setUrl(Url);

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            Dialog.show("Succés", "Article Imprimer", "ok", null);
            System.out.println(str);


        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }

    public void supprimerarticle(Article ta,Resources res) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://localhost/talandWEB/web/app_dev.php/Article/supparticle/"+ ta.getId_Article();
        con.setUrl(Url);

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            Dialog.show("Succés", "Article Supprimer", "ok", null);
            System.out.println(str);


        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }

    public String stat() {
        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://localhost/talandWEB/web/app_dev.php/Article/Statmobile";
        con.setUrl(Url);
        String a=new String(con.getResponseData());
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
           // Dialog.show("Succés", "Article", "ok", null);
            System.out.println(str);


        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return a;
    }


    public ArrayList<Article> Chercher(String desc) {
        ArrayList<Article> listArt = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/talandWEB/web/app_dev.php/Article/findArticle/" + desc);
        con.addResponseListener((NetworkEvent evt) -> {
            JSONParser jsonp = new JSONParser();
            try {
                Map<String, Object> tasks = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                System.out.println(tasks);
                List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");
                for (Map<String, Object> obj : list) {
                    Article ar = new Article();

                    float id = Float.parseFloat(obj.get("idArticle").toString());

                    SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                    // String datedeb = formater.format(ar.getDate_Article());


                    ar.setId_Article((int) id);
                    ar.setNom_Article(obj.get("nomArticle").toString());

                    ar.setDate_Article(obj.get("dateArticle").toString());
                    ar.setContenu_Article(obj.get("contenuArticle").toString());


                    float nbp = Float.parseFloat(obj.get("nbrevue").toString());
                    ar.setNbrevue((int) nbp);


                    float us = Float.parseFloat(obj.get("idUser").toString());
                    ar.setId_User((int) us);

                    ar.setTitre_Event(obj.get("titreEvent").toString());
                    ar.setImage_Article(obj.get("imageArticle").toString());



                    listArt.add(ar);
                }
            } catch (IOException ex) {
            }

        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listArt;
    }

}