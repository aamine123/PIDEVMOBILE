package services;

import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import entities.Product;
import utils.Statics;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceProduct {
    public ArrayList<Product> products;
    public static ServiceProduct instance=null;
    private ConnectionRequest req;
    public boolean resultOK;
    public ServiceProduct(){
        req=new ConnectionRequest();
    }

    public static ServiceProduct getInstance(){
        if(instance==null)
            instance=new ServiceProduct();
        return instance;
    }

    public boolean addProduct(Product p){
        String url= Statics.BASE_URL+"Products/addProductMobile/"+p.getName()+"/"+p.getPrice();
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
                float id=Float.parseFloat(obj.get("id").toString());
                p.setId((int)id);
                p.setName(obj.get("name").toString());
                p.setCategory(0);
                p.setDate(null);
                p.setImgSrc(obj.get("imgsrc").toString());
                p.setPrice((float) Float.parseFloat(obj.get("price").toString()));
                p.setUserId(1);
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
      //  NetworkManager.getInstance().addToQueueAndWait(req);
        return products;
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
