package services;

import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import entities.Product;
import entities.User;
import jdk.nashorn.internal.runtime.JSONListAdapter;
import utils.Statics;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceProduct {
    public ArrayList<Product> products;
    public static ServiceProduct instance=null;
    private ConnectionRequest req;
    public boolean resultOK;
    private User loggedInUser;
    private User user;
    public ServiceProduct(){
        req=new ConnectionRequest();
        loggedInUser=new User(10,"eya","lookil","eya");

    }

    public static ServiceProduct getInstance(){
        if(instance==null)
            instance=new ServiceProduct();
        return instance;
    }

    public boolean addProduct(Product p,String categoryName){
        String url= Statics.BASE_URL+"Products/addProductMobile/"+p.getName()+"/"+p.getPrice()+"/"+
                loggedInUser.getId()+"/"+categoryName;
        System.out.println(url);
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK=req.getResponseCode()==200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public ArrayList<Product> parseProducts(String jsonText){

        products=new ArrayList<>();
        JSONParser j=new JSONParser();
        try {
            Map<String,Object> productsListJson=j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list=(List<Map<String, Object>>)productsListJson.get("root");
            for (Map<String,Object> obj:list){
                Product p=new Product();
                Map<String,Object> userObj= (Map<String, Object>) obj.get("userid");
                float userIdF= Float.parseFloat(userObj.get("id").toString());
                int userId= (int) userIdF;
                float id=Float.parseFloat(obj.get("id").toString());
                Map<String,Object> categoryObj= (Map<String, Object>) obj.get("category");
                float categoryIdF= Float.parseFloat(userObj.get("id").toString());
                int categoryId= (int) categoryIdF;
                p.setId((int)id);
                p.setName(obj.get("name").toString());
                p.setCategory(categoryId);
                p.setDate(null);
                p.setImgSrc(obj.get("imgsrc").toString());
                p.setPrice((float) Float.parseFloat(obj.get("price").toString()));
                p.setUserId(userId);
                float validation =Float.parseFloat(obj.get("validation").toString());
                p.setValidation((int)validation);
                products.add(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }


    public ArrayList<Product> allProducts(){
        String url = Statics.BASE_URL+"Products/allProductsMobile";
        System.out.println("get all"+url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                products = parseProducts(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return products;
    }
    public User parseUser(String jsonText){
        user=new User();
        JSONParser j=new JSONParser();
        try {
            Map<String,Object> productsListJson=j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            //List<Map<String,Object>> list=(List<Map<String, Object>>)productsListJson.get("root");

           // List<Map<String,Object>> list=(List<Map<String, Object>>)productsListJson.get("root");




                float id=Float.parseFloat(productsListJson.get("id").toString());

                user.setId((int) id);
                user.setName(productsListJson.get("firstname").toString());

                user.setLastName(productsListJson.get("lastname").toString());
                user.setEmail(productsListJson.get("email").toString());

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return user;
    }

    public User getProductUserDetails(Product p){
        String url = Statics.BASE_URL+"Products/getUser/"+p.getUserId();
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                user = parseUser(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);

        return user;

    }

    public boolean deleteProduct(Product p){
        String url=Statics.BASE_URL+"Products/deleteProductMobile/"+p.getId();
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK=req.getResponseCode()==200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }


}
