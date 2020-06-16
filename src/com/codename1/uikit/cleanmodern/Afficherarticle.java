package com.codename1.uikit.cleanmodern;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.plaf.Border;
import services.ServiceArticle;
import com.codename1.components.ImageViewer;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.ui.*;
import com.codename1.ui.layouts.*;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import entities.Article;
import entities.Article;

import services.ServiceArticle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Afficherarticle extends BaseForm {
    Resources res1;
    private ComboBox choix;
    private List<Article> searchpost;
    private ArrayList<Article> listarticles;
    private ServiceArticle ServiceArticle;
    Label edit = new Label("");
    TextArea ta = new TextArea("");
    public Afficherarticle(Resources res) {

        super("Newsfeed", BoxLayout.y());
        Resources t ;

        Toolbar tb = new Toolbar(true);
        Container searchusers = new Container(BoxLayout.y());
        ServiceArticle = new ServiceArticle();
        listarticles = new ArrayList<>();
        listarticles = ServiceArticle.getList2();
        searchpost=listarticles;
        setToolbar(tb);
        tb.addSearchCommand(e -> {

            String text = (String)e.getSource();

            if(text == null || text.length() == 0) {
                System.out.println("no text");
                searchpost = listarticles;
                searchusers.removeAll();

            }
            assert text != null;
            if (text.length() !=0){
                searchusers.removeAll();
                searchpost = searchpost.stream().filter((Article v) ->v.getNom_Article().toUpperCase().startsWith(text.toUpperCase())).collect(Collectors.toList());
                for (int o=0;o<searchpost.size();o++){
                    String urlimage = "http://localhost/talandWEB/web/images/thevoice.jpeg";
                    EncodedImage enc3 = EncodedImage.createFromImage(res.getImage("dog.jpg"), false);

                    Image image = URLImage.createToStorage(enc3,"deal"+listarticles.get(o).getImage_Article()+".png", urlimage);
                    Container single = new Container(new BorderLayout());
                    single.add(BorderLayout.WEST,new ImageViewer(image));
                    single.add(BorderLayout.CENTER,new Label(searchpost.get(o).getNom_Article()));
                    searchusers.add(single);
                }
//searchusers.removeAll();
            }
        });
        add(searchusers);


        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Newsfeed");
        getContentPane().setScrollVisible(false);


        super.addSideMenu(res);
        tb.addSearchCommand(e -> {});

        Tabs swipe = new Tabs();

        Label spacer1 = new Label();
        Label spacer2 = new Label();
        addTab(swipe, res.getImage("news-item.jpg"), spacer1, "15 Likes  ", "85 Comments", "Integer ut placerat purued non dignissim neque. ");
        addTab(swipe, res.getImage("dog.jpg"), spacer2, "100 Likes  ", "66 Comments", "Dogs are cute: story at 11");

        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();

        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for(int iter = 0 ; iter < rbs.length ; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }

        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if(!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, spacer1, spacer2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton all = RadioButton.createToggle("All", barGroup);
        all.setUIID("SelectBar");
        RadioButton featured = RadioButton.createToggle("Featured", barGroup);
        featured.setUIID("SelectBar");
        RadioButton popular = RadioButton.createToggle("Popular", barGroup);
        popular.setUIID("SelectBar");
        RadioButton myFavorite = RadioButton.createToggle("My Favorites", barGroup);
        myFavorite.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(4, all, featured, popular, myFavorite),
                FlowLayout.encloseBottom(arrow)
        ));

        all.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(all, arrow);
        });
        bindButtonSelection(all, arrow);
        bindButtonSelection(featured, arrow);
        bindButtonSelection(popular, arrow);
        bindButtonSelection(myFavorite, arrow);

        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        ServiceArticle = new ServiceArticle();
        listarticles = new ArrayList<>();
        listarticles = ServiceArticle.getList2();
        Button ajouter =new Button("Ajouter article");
        ajouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new HomeArticle(res).show();
            }
        });
        add(ajouter);
        Button stat =new Button("Statistique");
        stat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new ApiStat().createPieChartForm1(res).show();
            }
        });
        add(stat);

        for (int i=0;i<listarticles.size();i++) {
            String urlimage = "http://localhost/TalandWEB/public/uploads/";
            EncodedImage enc3 = EncodedImage.createFromImage(res.getImage("dog.jpg"), false);
            Image image = URLImage.createToStorage(enc3,"deal"+listarticles.get(i).getImage_Article()+".png", urlimage);

           // ImageViewer lbimg=new ImageViewer();
            EncodedImage placeholder = EncodedImage.createFromImage(res.getImage("load.png"), true);
            Image imagee = URLImage.createToStorage(placeholder, listarticles.get(i).getImage_Article(), "http://localhost/TalandWEB/public/uploads/"+listarticles.get(i).getImage_Article());
           // lbimg.setImage(uRLImage);
            String name = listarticles.get(i).getNom_Article();
            String contenu = listarticles.get(i).getContenu_Article();
            Label l1 = new Label("moy");
            ConnectionRequest con = new ConnectionRequest();
            String Url = "http://localhost/talandWEB/web/app_dev.php/Article/ratemoyenneM/"+listarticles.get(i).getId_Article();
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

               // System.out.println(l1.getText());

            });
            NetworkManager.getInstance().addToQueueAndWait(con);
            Slider moyenne=createStarRankSlider();
            moyenne.setProgress(Integer.parseInt((l1.getText())));
            System.out.println(l1.getText());
            //add(FlowLayout.encloseCenter(moyenne));


           // String datefin = listtvshows.get(i).getNbrvues().toString()+" \uD83D\uDC41";
            addButton(imagee, name, false, "",listarticles.get(i),moyenne,res);//null a remplacer avec deals.get(i)
           // String zt = listarticles.get(i).getYear().toString();


        }



    }




    private void updateArrowPosition(Button b, Label arrow) {
        arrow.getUnselectedStyle().setMargin(LEFT, b.getX() + b.getWidth() / 2 - arrow.getWidth() / 2);
        arrow.getParent().repaint();


    }

    private void addTab(Tabs swipe, Image img, Label spacer, String likesStr, String commentsStr, String text) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        if(img.getHeight() < size) {
            img = img.scaledHeight(size);
        }
        Label likes = new Label(likesStr);
        Style heartStyle = new Style(likes.getUnselectedStyle());
        heartStyle.setFgColor(0xff2d55);
        FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, heartStyle);
        likes.setIcon(heartImage);
        likes.setTextPosition(RIGHT);

        Label comments = new Label(commentsStr);
        FontImage.setMaterialIcon(comments, FontImage.MATERIAL_CHAT);
        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }
        ScaleImageLabel image = new ScaleImageLabel(img);
        image.setUIID("Container");
        image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Label overlay = new Label(" ", "ImageOverlay");

        Container page1 =
                LayeredLayout.encloseIn(
                        image,
                        overlay,
                        BorderLayout.south(
                                BoxLayout.encloseY(
                                        new SpanLabel(text, "LargeWhiteText"),
                                        FlowLayout.encloseIn(likes, comments),
                                        spacer
                                )
                        )
                );

        swipe.addTab("", page1);

    }

    private void addButton(Image img, String title, boolean liked,  String datefin, Article d,Slider slid,Resources res) {
        int height = Display.getInstance().convertToPixels(15.5f);
        int width = Display.getInstance().convertToPixels(20f);
        Button image = new Button(img.fill(width, height));
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);
        //cnt.setLeadComponent(image);
        TextArea ta = new TextArea("  "+title);
        ta.setUIID("NewsTopLine");
        ta.setEditable(false);
        ta.addPointerPressedListener((e)->{
            new detailarticletemplate(res,d).show();
         //   new Detailarticle(d).show();

        });

        Label comments = new Label(datefin);
        FontImage.setMaterialIcon(comments, FontImage.MATERIAL_VIEW_STREAM);
       edit.setText(title);
       ta.setText(title);

         edit.getAllStyles().setFgColor(0x000000);
        cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        BoxLayout.encloseX(ta),
                        FlowLayout.encloseCenter(slid)
                ));

        add(cnt);
        image.addActionListener(e -> ToastBar.showMessage(title, FontImage.MATERIAL_INFO));
    }

    private void bindButtonSelection(Button b, Label arrow) {
        b.addActionListener(e -> {
            if(b.isSelected()) {
                updateArrowPosition(b, arrow);
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
