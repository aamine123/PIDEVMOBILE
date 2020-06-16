package com.codename1.uikit.cleanmodern;

import com.codename1.capture.Capture;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import entities.Article;
import org.omg.CORBA.Any;
import services.ServiceArticle;
import services.ServiceSujet;

import java.io.IOException;
import java.util.Date;

public class modifArticle extends Form {
    private Resources res;
    private ServiceArticle ser;
    private String im ;
    public modifArticle(Article ar){
        Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
        FontImage icone = FontImage.createMaterial(FontImage.MATERIAL_IMAGE, s);
        Button img = new Button("Upload Image",icone);
        img.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                try {


                    String fileNameInServer = "";
                    MultipartRequest cr = new MultipartRequest();
                    String filepath = Capture.capturePhoto(-1, -1);
                    cr.setUrl("http://localhost/uploadimage.php");
                    cr.setPost(true);
                    String mime = "image/jpeg";
                    cr.addData("file", filepath, mime);;
                    String out = new com.codename1.l10n.SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
                    cr.setFilename("file", out + ".jpg");//any unique name you want

                    fileNameInServer += out + ".jpg";
                    System.err.println("path2 =" + fileNameInServer);
                    im =fileNameInServer ;
                    InfiniteProgress prog = new InfiniteProgress();
                    Dialog dlg = prog.showInifiniteBlocking();
                    cr.setDisposeOnCompletion(dlg);
                    NetworkManager.getInstance().addToQueueAndWait(cr);
                } catch ( IOException ex) {
                }
            }

        });




        TextField nomarticle = new TextField(ar.getNom_Article());
        TextField contenu = new TextField(ar.getContenu_Article());
        TextField titreevent = new TextField(ar.getTitre_Event());
        nomarticle.getUnselectedStyle().setFgColor(000255);
        contenu.getUnselectedStyle().setFgColor(000255);
        titreevent.getUnselectedStyle().setFgColor(000255);



        Button confirmer =new Button("modifier");
        confirmer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                ar.setContenu_Article(contenu.getText());
                ar.setImage_Article(ar.getImage_Article());
                ar.setNom_Article(ar.getNom_Article());
                ar.setTitre_Event(ar.getTitre_Event());
              //  ser.modifierArticle(ar.getId_Article(),ar.getNom_Article(),ar.getContenu_Article(),res);
            }
        });
        addAll(nomarticle,contenu,titreevent,confirmer);
        add(img);

    }
}
