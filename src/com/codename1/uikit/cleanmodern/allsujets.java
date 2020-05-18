package com.codename1.uikit.cleanmodern;
import com.codename1.components.ShareButton;
import com.codename1.io.FileSystemStorage;
import com.codename1.notifications.LocalNotification;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import entities.Sujet;
import services.ServiceSujet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class allsujets extends BaseForm {
    Resources reserveRes;



    Tabs swipe = new Tabs();

    Label spacer1 = new Label();
    Label spacer2 = new Label();
    private Resources theme;
    private ServiceSujet ser;
    private ArrayList<Sujet> Sujets;
    private Container listsujetscont;
    private ComboBox choix;
    private Container listsujet;

    public allsujets(Resources res){
        setTitle("Taland");

        reserveRes = res;
        Toolbar tb = new Toolbar(true);








        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("List Article");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);

        tb.addSearchCommand(e -> { });

        tb.getAllStyles().setBorder(Border.createEmpty());
        tb.getAllStyles().setBackgroundType(Style.BACKGROUND_NONE);
        tb.getAllStyles().setBgTransparency(255);
        tb.getAllStyles().setBgColor(0x99CCCC);

        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);


        Component.setSameSize(radioContainer, spacer1, spacer2);
        add(LayeredLayout.encloseIn(swipe));
        Label test = new Label("hello");
        Label test2 = new Label("hello");
        Label test3 = new Label("hello");
//

        test2.getAllStyles().setFgColor(0xFFFFFF);
        test.getAllStyles().setFgColor(0xFFFFFF);
        test3.getAllStyles().setFgColor(0xFFFFFF);


        add(test);
        add(test2);
        add(test3);
        this.getToolbar().addSearchCommand(e -> {

            String text = (String)e.getSource();

                List<Sujet> ss=ser.getAllTasks();
                ss =  ss.stream().filter((Sujet v) ->v.getDescription_f().toUpperCase().startsWith(text.toUpperCase())).collect(Collectors.toList());
                removeAll();
                add(choix);
                listsujet = new Container();
                for (int i = 0; i < ss.size(); i++) {
                    listsujet.add(addItem(ss.get(i)));
                }
                add(listsujet);

            Button ajouter=new Button("ajouter");
            ajouter.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    new ajoutsujet(theme).show();

                }
            });
            add(ajouter);
                refreshTheme();


        });

        this.setLayout(BoxLayout.y());
        this.theme=theme;

        /******************combobox de année******************/
        choix=new ComboBox();
        choix.addItem("All");
        choix.addItem("2002");
        choix.addItem("2003");
        choix.addItem("2004");
        choix.addItem("2005");
        choix.addItem("2006");
        choix.addItem("2007");
        choix.addItem("2008");
        choix.addItem("2009");
        choix.addItem("2010");
        choix.addItem("2011");
        choix.addItem("2012");

        if(choix.getSelectedItem().equals("tous"))
        {
            choix.setSelectedItem(true);
        }
        add(choix);
        choix.addActionListener(ev -> {
            List<Sujet> s=lesmetiers();
            removeAll();
            add(choix);
            listsujetscont = new Container();
            for (int i = 0; i < s.size(); i++) {
                listsujetscont.add(addItem(s.get(i)));
            }
            add(listsujetscont);

            refreshTheme();
        });





        /********afficher les sujets**********/
        ser = new ServiceSujet();
        Sujets = new ArrayList<>();
        Sujets = ser.getAllTasks();
        listsujetscont = new Container();
        for (int i = 0; i < Sujets.size(); i++) {
            listsujetscont.add(addItem(ser.getAllTasks().get(i)));
        }
        add(listsujetscont);
        /********end afficher les sujets**********/
        Form hi = new Form("ShareButton");

        ShareButton sb = new ShareButton();
        sb.setText ("Partager");
        //sb.setTextToShare ("Article"+AffichageArticle.TITRE);
      //  sb.setTextToShare ("   "+AffichageArticle.TITREcontenue);
  //      String imageFile = FileSystemStorage.getInstance().getAppHomePath() + AffichageArticle.TITREimage;

    //    sb.setImageToShare(imageFile, "image/png");

        add(sb);
        Button ajouter=new Button("ajouter");
        ajouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                 new ajoutsujet(theme).show();

            }
        });
        add(ajouter);


    }
    /****start filtre***/
    public List<Sujet> lesmetiers()
    {
        List<Sujet> s=ser.getAllTasks();
        if (choix.getSelectedIndex() != 0) {
            //  s =  s.stream().filter((Sujet v) ->(v.getDate().getYear().toString()).equals(choix.getSelectedIndex()+2001)).collect(Collectors.toList());
        }
        return s;
    }
    /****end filtre***/


    public Container addItem(Sujet vid) {
        Container holder = new Container(BoxLayout.x());
        Container ctDetails = new Container(BoxLayout.y());
        Label titre = new Label("Description : "+ vid.getDescription_f());
        Label lecontenucomm = new Label("   ");
       // System.out.println("aalsalalala"+vid.getId_f());
        titre.addPointerReleasedListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                detailforum s=new detailforum(theme,vid);
                s.show();
            }
        });
        //  ArrayList<Categorie> li= new ArrayList<>();
        ctDetails.addAll(titre,lecontenucomm);
               /* li.addAll(vid.getCategories());
                for(int i=0;i<li.size();i++)
                {
                    ctDetails.add(new Label("categorie: "+li.get(i).getNom()));
                }
*/
        //   EncodedImage enc = EncodedImage.createFromImage(theme.getImage("t.jpg"), false);
        //   Image image = URLImage.createToStorage(enc, vid.getCoverimage(), "http://localhost/TalandWEB/web/images" + vid.getCoverimage());
        //   ImageViewer img = new ImageViewer(image);
        //  holder.add(img);
        holder.add(ctDetails);
        // img.addPointerReleasedListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent evt) {

        //      }
        //   });


        holder.setLeadComponent(titre);
        return holder;
    }






}


