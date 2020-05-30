package services;

import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import entities.Posts;
import entities.Sujet;
import entities.User;
import utils.Statics;

import java.io.IOException;
import java.util.*;

public class ServicePosts {
    public ArrayList<Posts> Posts;
    public ArrayList<User> users = new ArrayList<>();
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

                float idPost = Float.parseFloat(obj.get("idpost").toString());
                p.setIdPost((int)idPost);
                Object idu = obj.get("idu");
                LinkedHashMap<Object,Object> lhm = new LinkedHashMap<>();
                lhm=(LinkedHashMap<Object,Object>)obj.get("idu");
                p.setIdU(lhm);
                float nbrlikes = Float.parseFloat(obj.get("nbrlikes").toString());
                p.setNbrlikes((int)nbrlikes);
                float nbrcomments = Float.parseFloat(obj.get("nbrcomments").toString());
                p.setNbrcomments((int)nbrcomments);
                p.setDescription(obj.get("description").toString());
                p.setImage_name(obj.get("imageName").toString());
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

    public boolean addPost(Posts p) {

        String url = Statics.BASE_URL + "Posts/AddPostsMobile/" + p.getDescription() + "/" + 15 + "/" + p.getImage_name() + "/" + p.getType() + "/" +  p.getArchive();
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

    public boolean EditPost(Posts p) {

        String url = Statics.BASE_URL + "Posts/EditPostsMobile/" + p.getIdPost() +"/"+ p.getDescription() + "/" + 15 + "/" + p.getImage_name() + "/" + p.getType() + "/" +  p.getArchive();
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

    public boolean deletePost(int id) {

        String url = Statics.BASE_URL + "Posts/deletePostsMobile/" + id ;
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
