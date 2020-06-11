package com.codename1.uikit.cleanmodern;

import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Tabs;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.codename1.components.ImageViewer;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.*;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.cleanmodern.tvshow;
import DataBase.Tvshow;
import DataBase.ServiceTvshow;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class accueil extends BaseForm {
    private ArrayList<Tvshow> listSerie;
    private Container lisvideos;
    private ServiceTvshow ser;
    private Resources theme;
    private ComboBox choix;

    public accueil(Resources res){


        super("TvShow", BoxLayout.y());




        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("TvShow");
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
        RadioButton Forum = RadioButton.createToggle("Forum", barGroup);
        Forum.setUIID("SelectBar");
        // RadioButton featured = RadioButton.createToggle("Featured", barGroup);
        //featured.setUIID("SelectBar");
        //RadioButton popular = RadioButton.createToggle("Popular", barGroup);
        //popular.setUIID("SelectBar");
        //RadioButton myFavorite = RadioButton.createToggle("My Favorites", barGroup);
        //myFavorite.setUIID("SelectBar");
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
        choix.addItem("2013");
        choix.addItem("2014");
        choix.addItem("2015");
        choix.addItem("2016");
        choix.addItem("2017");
        choix.addItem("2018");
        choix.addItem("2019");
        choix.addItem("2020");
        if(choix.getSelectedItem().equals("tous"))
        {
            choix.setSelectedItem(true);
        }
        add(choix);

        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(4, all, Forum),
                FlowLayout.encloseBottom(arrow)
        ));

        all.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(all, arrow);
        });
        bindButtonSelection(all, arrow);
        bindButtonSelection(Forum, arrow);
        //bindButtonSelection(featured, arrow);
        //bindButtonSelection(popular, arrow);
        //bindButtonSelection(myFavorite, arrow);
        all.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new NewsfeedForm(res).show();
            }
        });
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        choix.addActionListener(ev -> {
            List<Tvshow> s=lesmetiers();
            removeAll();
            add(choix);
            lisvideos = new Container();
            for (int i = 0; i < s.size(); i++) {
                lisvideos.add(addItem(s.get(i)));
            }
            add(lisvideos);

            refreshTheme();
        });

        ser = new ServiceTvshow();
        listSerie = new ArrayList<>();
        listSerie = ser.getAllTvshows();
        lisvideos = new Container();
        for (int i = 0; i < listSerie.size(); i++) {
            lisvideos.add(addItem(ser.getAllTvshows().get(i)));;
        }
        add(lisvideos);

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


    public List<Tvshow> lesmetiers() {
        List<Tvshow> s=ser.getAllTvshows();
        if (choix.getSelectedIndex() != 0) {
            s =  s.stream().filter((Tvshow v) ->v.getYear().equals(choix.getSelectedIndex()+2001)).collect(Collectors.toList());
        }
        return s;
    }

    public Container addItem(Tvshow vid) {
        Container holder = new Container(BoxLayout.x());
        Container ctDetails = new Container(BoxLayout.y());
        Label titre = new Label("Name : "+ vid.getName());
        Label year = new Label("Year : "+ vid.getYear().toString());
        titre.addPointerReleasedListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                tvshow v=new tvshow(theme,vid);
                v.show();
            }
        });
        ctDetails.addAll(titre,year);
        EncodedImage enc = EncodedImage.createFromImage(theme.getImage("t.jpg"), false);
        Image image = URLImage.createToStorage(enc, vid.getCoverimage(), "http://localhost/TalandWEB/web/images" + vid.getCoverimage());
        ImageViewer img = new ImageViewer(image);
        holder.add(img);
        holder.add(ctDetails);
        img.addPointerReleasedListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                tvshow v=new tvshow(theme,vid);
                v.show();
            }
        });


        holder.setLeadComponent(titre);
        return holder;
    }
    private void addButton(Image img, String title, boolean liked, int likeCount, int commentCount) {
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        Button image = new Button(img.fill(width, height));
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);
        cnt.setLeadComponent(image);
        TextArea ta = new TextArea(title);
        ta.setUIID("NewsTopLine");
        ta.setEditable(false);

        Label likes = new Label(likeCount + " Likes  ", "NewsBottomLine");
        likes.setTextPosition(RIGHT);
        if(!liked) {
            FontImage.setMaterialIcon(likes, FontImage.MATERIAL_FAVORITE);
        } else {
            Style s = new Style(likes.getUnselectedStyle());
            s.setFgColor(0xff2d55);
            FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, s);
            likes.setIcon(heartImage);
        }
        Label comments = new Label(commentCount + " Comments", "NewsBottomLine");
        FontImage.setMaterialIcon(likes, FontImage.MATERIAL_CHAT);


        cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        ta,
                        BoxLayout.encloseX(likes, comments)
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






}