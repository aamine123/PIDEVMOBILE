package services;

import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import entities.Likes;
import entities.Posts;
import utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ServiceLikes {

    public ArrayList<entities.Likes> likes;
    public static ServiceLikes instance=null;
    public boolean resultOK;
    private ConnectionRequest req;


    public ServiceLikes() {
        req = new ConnectionRequest();
    }

    public static ServiceLikes getInstance() {
        if (instance == null) {
            instance = new ServiceLikes();
        }
        return instance;
    }


    public ArrayList<Likes> parseLikes(String jsonText){
        try {
            likes=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> PostsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String,Object>> list = (List<Map<String,Object>>)PostsListJson.get("root");

            for(Map<String,Object> obj : list){
                Likes p = new Likes();

                float idLike = Float.parseFloat(obj.get("idlike").toString());
                p.setIdLike((int)idLike);
                System.out.println("idlike :"+idLike);

                likes.add(p);
            }


        } catch (IOException ex) {

        }

        return likes;
    }



    public ArrayList<Likes> getPostLikes(int idPost){
        String url = Statics.BASE_URL+"Posts/"+"GetPostLikesMobile"+"/"+idPost;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                likes = parseLikes(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return likes;
    }

}
