package services;

import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import entities.Posts;
import utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServicePosts {
    public ArrayList<Posts> Posts;
    public static ServicePosts instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    public ServicePosts() {
        req = new ConnectionRequest();
    }

    public static ServicePosts getInstance() {
        if (instance == null) {
            instance = new ServicePosts();
        }
        return instance;
    }


    public ArrayList<Posts> parsePosts(String jsonText){
        try {
            Posts=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> PostsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String,Object>> list = (List<Map<String,Object>>)PostsListJson.get("root");

            for(Map<String,Object> obj : list){
                Posts p = new Posts();

                /*float id = Float.parseFloat(obj.get("idPost").toString());
                p.setIdPost((int)id);
                float idU = Float.parseFloat(obj.get("idU").toString());
                p.setIdU((int)idU);
                float nbrlikes = Float.parseFloat(obj.get("nbrlikes").toString());
                p.setNbrlikes((int)nbrlikes);
                float nbrcomments = Float.parseFloat(obj.get("nbrcomments").toString());
                p.setNbrcomments((int)nbrcomments);*/
                p.setDescription(obj.get("description").toString());
                p.setImage_name(obj.get("image_name").toString());
                float type = Float.parseFloat(obj.get("type").toString());
                p.setType((int)type);
                float archive = Float.parseFloat(obj.get("archive").toString());
                p.setArchive((int)archive);


                Posts.add(p);
            }


        } catch (IOException ex) {

        }

        return Posts;
    }

    public ArrayList<Posts> getAllPosts(){
        String url = Statics.BASE_URL+"Posts/"+"GetAllPosts";
        System.out.print("url :"+url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Posts = parsePosts(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Posts;
    }
}
