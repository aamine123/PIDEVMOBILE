package com.codename1.uikit.cleanmodern;
import com.codename1.components.*;
import com.codename1.io.ConnectionRequest;



import entities.*;
import services.ServiceArticle;
import services.ServiceCommentaire;
import services.Servicereview;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.*;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.codename1.io.rest.Response;
import com.codename1.io.rest.Rest;

import com.codename1.util.Base64;

import entities.SessionUser;




public class detailarticletemplate extends BaseForm {
    String accountSID="AC1f37afbad566e8db837a22e39bb1fe1d";
    String authToken="194af0df2307a9be4d74601dac3b3a30";
    String fromPhone="+14806370734";
    Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
    private ArrayList<Article> listarticle;
    private Container listvshows;
    private ServiceArticle service;

    private ArrayList<Commentaire> listcommentaire;
    private ArrayList<review> rate;
    private Container lisCommentaire;
    private ServiceCommentaire servicecomm;

    private Resources theme;
    private ShareButton shareBtn;
    Command back = new Command("Back") {
        @Override
        public void actionPerformed(ActionEvent evt) {
            showBack();
        }
    };

    public detailarticletemplate(Resources theme ,Article t){

        Display display = Display.getInstance();
       // BrowserComponent browser = new BrowserComponent();
        int videoWidth = (int) ((double) display.getDisplayWidth());
        int videoHeight = (int) ((double) videoWidth*0.5625);
      //  String integrationCode= "<iframe src=" +t.getLink()+" scrolling=\"no\" frameborder=\"0\" width=\"100%\" height=\"100%\" allowfullscreen=\"true\" webkitallowfullscreen=\"true\" mozallowfullscreen=\"true\"></iframe>";
       // browser.setPage(integrationCode, null);
       // browser.getAllStyles().setPadding(0, 0, 0, 0);
        //browser.getAllStyles().setMargin(0, 0, 0, 0);
        //this.add(browser);
        this.setLayout(BoxLayout.y());
        this.theme = theme;
        service = new ServiceArticle();
        listarticle = new ArrayList<>();
        listarticle = service.getList2();
      //  listcommentaire=ServiceCommentaire.getInstance().getAllcomments(t.getId_Article());
      //  rate=Servicereview.getInstance().(t.getId());
        listvshows = new Container(new FlowLayout(CENTER,CENTER));
        ImageViewer lbimg=new ImageViewer();
        EncodedImage placeholder = EncodedImage.createFromImage(theme.getImage("load.png"), true);
        URLImage uRLImage = URLImage.createToStorage(placeholder, t.getImage_Article(), "http://localhost/TalandWEB/public/uploads/"+t.getImage_Article());
        lbimg.setImage(uRLImage);
        listvshows.add(lbimg);
        service.nbvue(t,theme);

        add(listvshows);

      //  int idu =11;
      //  TextField commentaire = new TextField("");
     //   commentaire.getAllStyles().setFgColor(0x000000);
    //    Button con = new Button("Ajouter Commentaire");
        this.getToolbar().addMaterialCommandToLeftBar("",FontImage.MATERIAL_ARROW_BACK,(e)-> {
            Afficherarticle a=new Afficherarticle(theme);
            a.show();

        });

      //  con.addActionListener(new ActionListener() {
       //     @Override
         //   public void actionPerformed(ActionEvent evt) {
         //       service.insertcommentaire(idu,t.getId(),commentaire.getText());
           //     new Tvshowdetail(theme,t).show();
            //}
        //});
        Label titre = new Label("Name : "+ t.getNom_Article());
        Label desc = new Label("Description : "+ t.getContenu_Article());
        Label date = new Label("Ajoute le:  "+ t.getDate_Article().toString());
        Label nbvues = new Label("Nbre de vue : "+ (t.getNbrevue()+1));

     //   Label commentire = new Label("Commentaires :  ");

        //ShareButton shareBtn = new ShareButton();
        //shareBtn.setText("partager");
       // shareBtn.setTextToShare("regardez mon video:"+ "http://localhost/talandWEB/web/app_dev.php/tvshow/" + t.getId());

        addAll(titre,desc,date,nbvues);
        Slider S=createStarRankSlider();
        add(FlowLayout.encloseCenter(S));

        Label la=new Label("Progress");


        S.addActionListener((e)->{
            la.setText("Rate: "+S.getProgress());
            Servicereview serr=new Servicereview();
            serr.rate(S.getProgress(), SessionUser.loggedUser.getId(),t);
            Dialog.show("Rate","rate success","ok","null");
            S.setProgress(0);
            la.setText("Rate: "+0);
        });
        add(la);

        ShareButton shareBtn = new ShareButton();
        shareBtn.setText("Partager");
        shareBtn.setTextToShare("partager l'article :"+ "http://localhost/talandWEB/web/app_dev.php/tvshow/" + t.getId_Article());

        Button imprimer =new Button("Imprimer");
        Button supprimer =new Button ("Supprimer");
        Button modifier =new Button ("Modifier");
        imprimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // ToastBar.showMessage("j'aime effectué ...", FontImage.MATERIAL_FAVORITE);
                ConnectionRequest req = new ConnectionRequest();
                Display.getInstance().execute("http://localhost/talandWEB/web/app_dev.php/Article/imprimerM/"+ t.getId_Article());
            }
        });
        supprimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                service.supprimerarticle(t,theme);
                new Afficherarticle(theme).show();
                Response<Map> result = Rest.post("https://api.twilio.com/2010-04-01/Accounts/" + accountSID + "/Messages.json").
                        queryParam("To", "+21624614285").
                        queryParam("From", fromPhone).
                        queryParam("Body", "Lutulisateur "+SessionUser.loggedUser.getUsername()+" a supprimer larticle "+t.getNom_Article()).
                        header("Authorization", "Basic " + Base64.encodeNoNewline((accountSID + ":" + authToken).getBytes())).
                        getAsJsonMap();
            }
        });
        modifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               new updateArticle(theme,t).show();
            }
        });
        add(shareBtn);
        add(imprimer);
        add(supprimer);
        add(modifier);
       // Button sms=new Button("sms");



      //  addAll(commentaire,commentire);




    }
    public Container addContainer(Commentaire commentaireTvshiw) {
        Container holder = new Container(BoxLayout.x());
        Container ctDetails = new Container(BoxLayout.y());
        Label commentaire = new Label("Commentairess :  ");
        commentaire.getSelectedStyle().setFgColor(0x000000);
        Label lesusercomm = new Label( " " + commentaireTvshiw.getId_user());
        Label lecontenucomm = new Label( " " + commentaireTvshiw.getDescription_com());


        ctDetails.addAll(commentaire,lesusercomm,lecontenucomm);
        System.out.println(commentaireTvshiw.getId_user().getId());

        holder.add(ctDetails);

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
