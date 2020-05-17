package services;

import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import entities.Category;
import entities.Product;
import utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceCategory {

    private ConnectionRequest req;
    public static ServiceCategory instance=null;
    private ArrayList<Category> categories;


    public ServiceCategory(){
        req=new ConnectionRequest();
    }

    public static ServiceCategory getInstance(){
        if(instance==null)
            instance=new ServiceCategory();
        return instance;
    }

    public ArrayList<Category> parseCategory(String jsonText){
        categories=new ArrayList<>();
        JSONParser j=new JSONParser();
        try {

            Map<String,Object> productsListJson=j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list=(List<Map<String, Object>>)productsListJson.get("root");
            for (Map<String,Object> obj:list){
                Category c=new Category();
                float id=Float.parseFloat(obj.get("id").toString());
                c.setId((int)id);
                c.setName(obj.get("name").toString());
                System.out.println(obj.get("idCategoryMother"));
                if (obj.get("idCategoryMother")!=null){
                    float idMother =Float.parseFloat(obj.get("idCategoryMother").toString());
                    c.setIdCategoryMother((int)idMother);

                }else{
                    c.setIdCategoryMother(-1);
                }

                System.out.println(c);
                categories.add(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public ArrayList<Category> allCategories(){
        String url = Statics.BASE_URL+"Products/getCategoriesMobile";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                categories = parseCategory(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return categories;
    }

}
