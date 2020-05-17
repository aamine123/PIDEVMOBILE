package services;

import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import entities.Competition;
import utils.Statics;

import java.io.IOException;
import java.util.*;

public class ServiceCompetition {
    public ArrayList<Competition> Competitions;
    public static ServiceCompetition  instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    public ServiceCompetition () {
        req = new ConnectionRequest();
    }

    public static ServiceCompetition  getInstance() {
        if (instance == null) {
            instance = new ServiceCompetition ();
        }
        return instance;
    }


    public ArrayList<Competition> parsePosts(String jsonText){
        try {
            Competitions=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> CompetitionListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String,Object>> list = (List<Map<String,Object>>)CompetitionListJson.get("root");

            for(Map<String,Object> obj : list){
                Competition p = new Competition();
                float id = Float.parseFloat(obj.get("idcomp").toString());
                p.setIdcomp((int)id);
                LinkedHashMap<Object,Object> user= (LinkedHashMap<Object, Object>) obj.get("idu");
                float idu = Float.parseFloat(user.get("id").toString());
                p.setIdU((int)idu);

                p.setNamecomp(obj.get("namecomp").toString());
                p.setDesccomp(obj.get("desccomp").toString());
                p.setLocation(obj.get("location").toString());

                float Nbrmaxpar = Float.parseFloat(obj.get("nbrmaxpar").toString());
                p.setNbrmaxpar((int)Nbrmaxpar);

                float Nbrmaxspec = Float.parseFloat(obj.get("nbrmaxspec").toString());
                p.setNbrmaxspec((int)Nbrmaxspec);

                float Nbrparticipant = Float.parseFloat(obj.get("nbrparticipant").toString());
                p.setNbrparticipant((int)Nbrparticipant);

                float price = Float.parseFloat(obj.get("pricecomp").toString());
                p.setPricecomp(price);

                float nbrspec = Float.parseFloat(obj.get("nbrspec").toString());
                p.setNbrspec((int)nbrspec);

                p.setImage_name(obj.get("imageName").toString());

                /*Date startingdate = (Date)obj.get("startingdate");
                System.out.println(startingdate);*/

                /*p.setNbrlikes((int)nbrlikes);
                float nbrcomments = Float.parseFloat(obj.get("nbrcomments").toString());
                p.setNbrcomments((int)nbrcomments);
                p.setDescription(obj.get("description").toString());
                p.setImage_name(obj.get("imageName").toString());
                float type = Float.parseFloat(obj.get("type").toString());
                p.setType((int)type);
                float archive = Float.parseFloat(obj.get("archive").toString());
                p.setArchive((int)archive);*/


                Competitions.add(p);
            }


        } catch (IOException ex) {

        }

        return Competitions;
    }

    public ArrayList<Competition> getAllCompetitions(){
        String url = Statics.BASE_URL+"Events"+"/GetAllEvents";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Competitions = parsePosts(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Competitions;
    }



    public boolean deleteCompetition(int id) {
        String url = Statics.BASE_URL + "Events/DeleteMobileEvents/" + id ;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public boolean addCompetition(Competition c) {

        Date start = c.getStartingdate();

        int syear = start.getYear()+1900;
        int smonth = start.getMonth()+1;
        int sday = start.getDate();

        Date end = c.getEndingdate();
        int eyear = end.getYear()+1900;
        int emonth = end.getMonth()+1;
        int eday = end.getDate();
        String url = Statics.BASE_URL + "Events/addCompetitionMobile/"+c.getNamecomp()+"/"+c.getDesccomp() +"/"+c.getNbrmaxspec()+"/"+c.getNbrmaxpar()+"/"+c.getLocation()+"/"+sday+"/"+smonth+"/"+syear+"/"+eday+"/"+emonth+"/"+eyear+"/"+c.getPricecomp()+"/"+c.getIdcat()+"/"+c.getImage_name()+"/"+c.getIdU();
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }



    public boolean EditCompetition(Competition c) {

        Date start = c.getStartingdate();

        int syear = start.getYear()+1900;
        int smonth = start.getMonth()+1;
        int sday = start.getDate();

        Date end = c.getEndingdate();
        int eyear = end.getYear()+1900;
        int emonth = end.getMonth()+1;
        int eday = end.getDate();
        String url = Statics.BASE_URL + "Events/editCompetitionMobile/"+ c.getIdcomp() +"/" +c.getNamecomp()+"/"+c.getDesccomp() +"/"+c.getNbrmaxspec()+"/"+c.getNbrmaxpar()+"/"+c.getLocation()+"/"+sday+"/"+smonth+"/"+syear+"/"+eday+"/"+emonth+"/"+eyear+"/"+c.getPricecomp()+"/"+c.getIdcat()+"/"+c.getImage_name()+"/"+c.getIdU();
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

}
