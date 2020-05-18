/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.cleanmodern;

import com.codename1.components.ShareButton;
import com.codename1.ui.*;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import entities.Article;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;


import com.codename1.components.ImageViewer;
import com.codename1.components.ScaleImageLabel;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;

import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;

import entities.Sujet;
import services.ServiceArticle;

import com.codename1.ui.util.Resources;
import static com.codename1.uikit.cleanmodern.SignInForm.res;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import services.ServiceSujet;
import services.authuser;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import javax.swing.*;


public class AffichageArticle extends Form {

   // private Resources theme= UIManager.in;
    private Resources theme= UIManager.initFirstTheme("/theme");
    private ServiceArticle ser;
    private ArrayList<Article> Sujets;
    private Container listsujetscont;
    private ComboBox choix;
    private Container listsujet;

    public AffichageArticle(Resources res) {
        setTitle("Article");
        this.setLayout(BoxLayout.y());
        this.theme = theme;
        /********afficher les sujets**********/
        ser = new ServiceArticle();
        Sujets = new ArrayList<>();
        Sujets = ser.getList2();
        listsujetscont = new Container();
        for (int i = 0; i < Sujets.size(); i++) {
            listsujetscont.add(addItem(ser.getList2().get(i)));
        }
        add(listsujetscont);
        /********end afficher les sujets**********/


        this.getToolbar().addSearchCommand(e -> {
          //  String text = (String) e.getSource();
            //List<Article> ss=ser.Chercher(text);
            //listsujet = new Container();

            //listsujet.add(addItem((Article) ss));

            //add(listsujet);


        });

    }







    public Container addItem(Article vid) {
        Container holder = new Container(BoxLayout.x());
        Container ctDetails = new Container(BoxLayout.y());
        Label a = new Label("  ");
        Label aa = new Label("  ");
        Label titre = new Label("Nom : "+ vid.getNom_Article());

        ctDetails.addAll(a,aa,titre);

        EncodedImage enc = EncodedImage.createFromImage(theme.getImage("load.png"), true);
      //    EncodedImage enc = EncodedImage.createFromImage(theme.getImage("heart.png"), true);
          Image image = URLImage.createToStorage(enc, vid.getImage_Article(), "http://localhost/taland/web/uploads/article/" + vid.getImage_Article());
           ImageViewer img = new ImageViewer(image);
          holder.add(img);

        titre.addPointerReleasedListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                new Detailarticle(vid).show();
            }
        });
         img.addPointerReleasedListener(new ActionListener() {
             @Override
            public void actionPerformed(ActionEvent evt) {

                 new Detailarticle(vid).show();

              }
           });

        Label l1 = new Label("moy");
        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://localhost/taland/web/app_dev.php/Article/ratemoyenneM/"+vid.getId_Article();
        con.setUrl(Url);

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            //System.out.println(str);
            System.out.println("Moyenne"+str);
            try{
                l1.setText(str);
               // System.out.println("intetetet"+Integer.parseInt(str));
            } catch(NumberFormatException ex){

                ex.getMessage();

            }

            System.out.println(l1.getText());

        });
        NetworkManager.getInstance().addToQueueAndWait(con);
         Slider moyenne=createStarRankSlider();
         moyenne.setProgress(Integer.parseInt((l1.getText())));
        System.out.println(l1.getText());
        ctDetails.add(FlowLayout.encloseCenter(moyenne));
        holder.add(ctDetails);
        holder.setLeadComponent(titre);
        return holder;
    }







    private void initStarRankStyle(Style s, Image star) {
        s.setBackgroundType(Style.BACKGROUND_IMAGE_TILE_BOTH);
        s.setBorder(Border.createEmpty());
        s.setBgImage(star);
        s.setBgTransparency(0);
    }

    private Slider createStarRankSlider() {
        Slider starRank = new Slider();
        starRank.setEditable(true);
        starRank.setMinValue(0);
        starRank.setMaxValue(5);
        Font fnt = Font.createTrueTypeFont("native:MainLight", "native:MainLight").
                derive(Display.getInstance().convertToPixels(5, true), Font.STYLE_PLAIN);
        Style s = new Style(0xffff33, 0, fnt, (byte)0);
        Image fullStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
        s.setOpacity(100);
        s.setFgColor(0);
        Image emptyStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
        initStarRankStyle(starRank.getSliderEmptySelectedStyle(), emptyStar);
        initStarRankStyle(starRank.getSliderEmptyUnselectedStyle(), emptyStar);
        initStarRankStyle(starRank.getSliderFullSelectedStyle(), fullStar);
        initStarRankStyle(starRank.getSliderFullUnselectedStyle(), fullStar);
        starRank.setPreferredSize(new Dimension(fullStar.getWidth() * 5, fullStar.getHeight()));
        return starRank;
    }













}