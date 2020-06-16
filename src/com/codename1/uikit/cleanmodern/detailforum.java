package com.codename1.uikit.cleanmodern;

import com.codename1.components.ImageViewer;
import com.codename1.l10n.DateFormat;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Tabs;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.*;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import entities.Commentaire;
import entities.Sujet;
import services.ServiceCommentaire;
import services.ServiceSujet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.codename1.ui.plaf.UIManager;

public class detailforum extends BaseForm {
    private Resources res;
    private Resources theme;
    private ServiceSujet ser;
    private ServiceCommentaire com;
    private ArrayList<Sujet> Sujets;
    private ArrayList<Commentaire> Comments;
    private Container listsujetscont;
    private Container listcommentscont;
    private ComboBox choix;
    private Container listsujet;
    private Container listcomment;
    private Resources a= UIManager.initFirstTheme("/theme");


    public detailforum(Resources theme,Sujet s) {

        //this.getToolbar().addCommandToLeftSideMenu("Home", null, (evt) -> {
        //});
        this.setLayout(BoxLayout.y());
        this.theme=theme;
        /********afficher le sujet**********/
        ser = new ServiceSujet();
        Sujets = new ArrayList<>();
        Sujets = ser.getAllTasks();
        listsujetscont = new Container();
        listsujetscont.add(addItem(s));
        add(listsujetscont);
        Sujet r=s;
        /********end afficher le sujet**********/
        Button supp=new Button("Supprimer");
        Button modifier=new Button("modifier");
        modifier.addActionListener(new ActionListener() {
                                       @Override
                                       public void actionPerformed(ActionEvent evt) {

                                           // modifiersujet s=new modifiersujet(vid,theme);
                                           //s.show();
                                       }
                                   }

        );

        supp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               ser.supprimersujet(r.getId_f());
               new allsujets(theme).show();

            }
        });

        add(supp);

        /********afficher les comments**********/
        com = new ServiceCommentaire ();
        Comments = new ArrayList<>();
        Comments = com.getAllcomments(r.getId_f());
        listcommentscont = new Container();
        for (int i = 0; i < Comments.size(); i++) {
            Label lesusercomm = new Label("@" + Comments.get(i).getId_user().getUsername() + " : " + Comments.get(i).getDescription_com());
            Label lecontenucomm = new Label(" ");
            addAll(lesusercomm,lecontenucomm);
        }

        add(listcommentscont);
        /********end afficher les comments**********/

    }
    public Container addItem(Sujet vid) {
        Container cntjaime =new Container(BoxLayout.x());
        Container holder = new Container(BoxLayout.x());
        Container ctDetails = new Container(BoxLayout.y());
        Label titre = new Label("Description : "+ vid.getDescription_f());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(vid.getDate());
        Label date = new Label("Ajouteé le : "+strDate);
      //  Label a = new Label("  "+ vid.getNbre_jaime());
       ImageViewer imjaime = new ImageViewer(a.getImage("heart.png"));
        Label nbjaime = new Label("Nombre Jaime:"+ vid.getNbre_jaime());
       nbjaime.addPointerPressedListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent evt) {
               ser.like(vid.getId_f());
               new allsujets(res).show();
           }
       });
        cntjaime.addAll(imjaime,nbjaime);





        titre.addPointerReleasedListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
              //  modifiersujet s=new modifiersujet(vid,theme);
               //s.show();

            }
        });
        //  ArrayList<Categorie> li= new ArrayList<>();
        ctDetails.addAll(titre,date,cntjaime);
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
    public Container addItem1(Commentaire vid) {
        for (int i = 0; i < Comments.size(); i++) {
            Label lesusercomm = new Label("@" + Comments.get(i).getId_user().getUsername() + " : " + Comments.get(i).getDescription_com());
            Label lecontenucomm = new Label(" ");
            addAll(lesusercomm,lecontenucomm);
        }

        Container holder = new Container(BoxLayout.x());
        //Container ctDetails = new Container(BoxLayout.y());
        //Label titre = new Label("Description : "+ vid.getDescription_com());
        //ctDetails.addAll(titre);
        //holder.add(ctDetails);
        //holder.setLeadComponent(titre);
        return holder;
    }





}
