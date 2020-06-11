/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.io.rest.Rest;
import com.codename1.ui.events.ActionListener;
import DataBase.Tvshow;
import com.codename1.uikit.cleanmodern.tvshow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Chekib Elhajji
 */
public class ServiceTvshow {


    public ArrayList<Tvshow> Tvshows;

    public static ServiceTvshow instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    public ServiceTvshow() {
        req = new ConnectionRequest();
    }

    public static ServiceTvshow getInstance() {
        if (instance == null) {
            instance = new ServiceTvshow();
        }
        return instance;
    }


    public ArrayList<Tvshow> parseTvshows(String jsonText){
        try {
            Tvshows=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> TvshowsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String,Object>> list = (List<Map<String,Object>>)TvshowsListJson.get("root");
            for(Map<String,Object> obj : list){
                Tvshow t = new Tvshow();
                float id = Float.parseFloat(obj.get("id").toString());
                float episodenum = Float.parseFloat(obj.get("episodenum").toString());
                float Duree = Float.parseFloat(obj.get("duree").toString());
                float year = Float.parseFloat(obj.get("year").toString());
                float nbvues = Float.parseFloat(obj.get("nbvues").toString());
                t.setId((int)id);
                t.setName((String)obj.get("name").toString());
                t.setEpisodenum((int)episodenum);
                t.setDescription((String)obj.get("description").toString());
                t.setType((String)obj.get("type").toString());
                t.setDuree((int)Duree);
                t.setYear((int)year);
                t.setNbrvues((int)nbvues);
                t.setLink((String)obj.get("link").toString());
                t.setCoverimage((String)obj.get("coverimage").toString());
              /*  t.setGaleryimage1((String)obj.get("galeryimage1").toString());
                t.setGaleryimage2((String)obj.get("galeryimage2").toString());
                t.setGaleryimage3((String)obj.get("galeryimage3").toString());
                t.setGaleryimage4((String)obj.get("galeryimage4").toString());
                t.setGaleryimage5((String)obj.get("galeryimage5").toString());*/
                Tvshows.add(t);
            }


        } catch (IOException ex) {

        }
        return Tvshows;
    }


    public ArrayList<Tvshow> getAllTvshows(){
        String url = "http://localhost/talandWEB/web/app_dev.php/tvshow/aff";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Tvshows = parseTvshows(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Tvshows;
    }

}
