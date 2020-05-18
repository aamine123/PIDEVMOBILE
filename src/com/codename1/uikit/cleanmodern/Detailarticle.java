package com.codename1.uikit.cleanmodern;

import com.codename1.components.ImageViewer;
import com.codename1.components.ToastBar;
import com.codename1.components.WebBrowser;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.NetworkManager;
import com.codename1.io.rest.Response;
import com.codename1.io.rest.Rest;
import com.codename1.notifications.LocalNotification;
import com.codename1.notifications.LocalNotificationCallback;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.util.Base64;
import entities.Article;
import entities.SessionUser;
import services.ServiceArticle;
import services.Servicereview;
import utils.EmailShare;

import java.util.Map;
import java.util.Random;

import static com.codename1.uikit.cleanmodern.SignInForm.res;

public class Detailarticle extends Form {


        private Resources res;
    Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
    private Resources theme= UIManager.initFirstTheme("/theme");
    public Detailarticle(Article ar){

        Toolbar.setGlobalToolbar(true);

        this.getToolbar().addMaterialCommandToLeftBar("Back",FontImage.MATERIAL_ARROW_BACK,(e)-> {
            AffichageArticle w =new AffichageArticle(theme);
            w.show();
        });
        setTitle(ar.getNom_Article());
        this.setLayout(BoxLayout.y());
        ServiceArticle ser = new ServiceArticle();
        ser.nbvue(ar,res);
        Container image=new Container(new FlowLayout(CENTER,CENTER));
        Container other =new Container(BoxLayout.y());
        Container a=new Container(BoxLayout.x());
        Container b=new Container(BoxLayout.x());
        Container c=new Container(BoxLayout.x());

        Label n=new Label("Nom article:");
        Label x=new Label("Description:");
        Label y=new Label("Nbre vue:");
        Label nom=new Label(ar.getNom_Article());
        n.getUnselectedStyle().setFgColor(000255);
        x.getUnselectedStyle().setFgColor(000255);
        y.getUnselectedStyle().setFgColor(000255);
        Label contenu=new Label(ar.getContenu_Article());
        a.addAll(n,nom);
        Label nbvue=new Label(""+(ar.getNbrevue()+1));
        b.addAll(x,contenu);
        c.addAll(y,nbvue);
        ImageViewer lbimg=new ImageViewer();
        EncodedImage placeholder = EncodedImage.createFromImage(theme.getImage("load.png"), true);
        URLImage uRLImage = URLImage.createToStorage(placeholder, ar.getImage_Article(), "http://localhost/TalandWEB/public/uploads/"+ar.getImage_Article());
        lbimg.setImage(uRLImage);
        image.add(lbimg);
        other.addAll(a,b,c);
        addAll(image,other);


        Slider S=createStarRankSlider();
        add(FlowLayout.encloseCenter(S));
        Label la=new Label("Progress");

        S.addActionListener((e)->{
            la.setText("Rate: "+S.getProgress());
            Servicereview serr=new Servicereview();
            serr.rate(S.getProgress(), SessionUser.loggedUser.getId(),ar);
            Dialog.show("Rate","rate success","ok","null");
            S.setProgress(0);
            la.setText("Rate: "+0);
        });
        add(la);








     //   Slider moyenne=createStarRankSlider();
       // moyenne.setProgress(Integer.parseInt((l1.getText())));
        //add(FlowLayout.encloseCenter(moyenne));



    Button imprimer =new Button("Imprimer");
    Button share=new Button("share");

    imprimer.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {
           // ToastBar.showMessage("j'aime effectué ...", FontImage.MATERIAL_FAVORITE);
            ConnectionRequest req = new ConnectionRequest();
            Display.getInstance().execute("http://localhost/talandWEB/web/app_dev.php/Article/imprimerM/"+ ar.getId_Article());
        }
    });

        Container Buttons=new Container(new FlowLayout(CENTER,CENTER));
        Button modifier =new Button ("Modifier");
        Button supprimer =new Button ("Supprimer");
        Buttons.addAll(imprimer,modifier,supprimer,share);
        addAll(Buttons);
        modifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new modifArticle(ar).show();
            }
        });
        supprimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                ser.supprimerarticle(ar,res);
                new AffichageArticle(res).show();
            }
        });
        share.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String accountSID = "AC1f37afbad566e8db837a22e39bb1fe1d";
                String authToken = "f22ee3f103854b8a783006cbc0fcd0a4";
                String fromPhone = "+14806370734";
                Random r = new Random();
                String val = "" + r.nextInt(10000);
                while(val.length() < 4) {
                    val = "0" + val;
                }



                Response<Map> result = Rest.post("https://api.twilio.com/2010-04-01/Accounts/" + accountSID + "/Messages.json").
                        queryParam("To", fromPhone).
                        queryParam("From", fromPhone).
                        queryParam("Body", val).
                        basicAuth(accountSID, authToken).getAsJsonMap();
               // EmailShare s=new EmailShare();
               //String a= FileSystemStorage.getInstance().getAppHomePath()+ar.getImage_Article();
                //s.share(ar.getNom_Article(),a,ar.getContenu_Article());
            }
        });


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
