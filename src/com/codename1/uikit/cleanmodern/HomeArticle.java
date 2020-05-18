/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.cleanmodern;


import com.codename1.capture.Capture;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.plaf.Style;
import entities.Article;
import com.codename1.io.FileSystemStorage;
import entities.SessionUser;
import rest.file.uploader.tn.FileUploader;
import services.ServiceArticle;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.layouts.BoxLayout;

import com.codename1.ui.util.Resources;
import com.codename1.ui.plaf.UIManager;

import java.io.IOException;
import java.util.Date;

/**
 *
 * @author Administrator
 */
public class HomeArticle extends Form{
    //Form hi = new Form();
    //Form f;
    private Label titreEror;
    TextField tnomarticle;
    TextField tncontenueArticle;
    TextField combo;
    private Resources res;
    private String im ;

    Container descriptionContainer;
    Button btajout,btnaff,btsupp;

    public HomeArticle(Resources theme)  {
        Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
        FontImage icone = FontImage.createMaterial(FontImage.MATERIAL_IMAGE, s);
        Button img = new Button("Upload Image",icone);

        ///     f = new Form("home");
        tnomarticle = new TextField("","titre");
        tnomarticle.getUnselectedStyle().setFgColor(000255);
        tncontenueArticle = new TextField("","contenue Article");
        tncontenueArticle.getUnselectedStyle().setFgColor(000255);
        combo = new TextField("","titre Event");
        combo.getUnselectedStyle().setFgColor(000255);

        btajout = new Button("ajouter");
        btnaff=new Button("Affichage");

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


        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");


       // Label photoLabel = new Label("Photo");
        //Button selectPhoto = new Button("parcourir");
        //TextField photoField = new TextField("", "Importer une photo", 10, TextArea.ANY);
        //photoField.setEditable(false);
        Container photoContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
        //photoContainer.add(photoLabel);
        //photoContainer.add(photoField);
        //photoContainer.add(selectPhoto);
        Date d=new Date();
        add(tnomarticle);
        add(tncontenueArticle);
        add(combo);
        add(img);


        add(photoContainer);
        add(btajout);
        add(btnaff);


        btajout.addActionListener((e) -> {

            if (tnomarticle.getText().equals("")) {

                Dialog.show("ERREUR SAISIE","TITRE VIDE","OK","ANNULER");
            }





            else{


                ServiceArticle ser = new ServiceArticle();




                try{
                    Article article = new Article(SessionUser.loggedUser.getId(),tnomarticle.getText(),tncontenueArticle.getText(),im,combo.getText(),
                            0,d.toString() );

                    ser.ajoutArticle(article);
                    Dialog.show("felicitation", " votre Article a ete ajoute", "ok", null);
                }catch(Exception ex){
                    ex.getMessage();
                }
            }
        });

        btnaff.addActionListener((e)->{
            AffichageArticle a =new AffichageArticle(res);
            a.show();
        });

        Button stat=new Button("Stat");
        add(stat);
        stat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new ApiStat().createPieChartForm1(theme).show();
            }
        });
    }



}