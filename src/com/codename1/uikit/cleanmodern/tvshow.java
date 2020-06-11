package com.codename1.uikit.cleanmodern;


import DataBase.ServiceTvshow;
import DataBase.Tvshow;
import java.util.ArrayList;
import com.codename1.components.ImageViewer;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.components.SpanLabel;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Display;
import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import java.util.List;

import java.util.ArrayList;

/**
 *
 * @author Chekib Elhajji
 */
public class tvshow extends Form{
    private ArrayList<Tvshow> listtvshow;
    private Container listvshows;
    private ServiceTvshow service;
    private Resources theme;

    public tvshow(Resources theme ,Tvshow t){

        Display display = Display.getInstance();

        BrowserComponent browser = new BrowserComponent();



        int videoWidth = (int) ((double) display.getDisplayWidth());
        int videoHeight = (int) ((double) videoWidth*0.5625);

        String integrationCode= "<iframe src=" +t.getLink()+" scrolling=\"no\" frameborder=\"0\" width=\"100%\" height=\"100%\" allowfullscreen=\"true\" webkitallowfullscreen=\"true\" mozallowfullscreen=\"true\"></iframe>";
        browser.setPage(integrationCode, null);
        browser.getAllStyles().setPadding(0, 0, 0, 0);
        browser.getAllStyles().setMargin(0, 0, 0, 0);

        this.add(browser);
        setTitle("Taland");
        this.getToolbar().addCommandToLeftSideMenu("Home", null, (evt) -> {
        });
        this.getToolbar().addCommandToLeftSideMenu("Forum", null, (evt) -> {
        });
        this.getToolbar().addCommandToLeftSideMenu("Tv Show", null, (evt) -> {
        });

        this.setLayout(BoxLayout.y());
        this.theme = theme;

        service = new ServiceTvshow();
        listtvshow = new ArrayList<>();
        listtvshow = service.getAllTvshows();
        listvshows = new Container();

            listvshows.add(addContainer(t));
        add(listvshows);
    }


    public Container addContainer(Tvshow tvshiw) {
        Container holder = new Container(BoxLayout.x());
        Container ctDetails = new Container(BoxLayout.y());
        Label titre = new Label("Name : "+ tvshiw.getName());
        Label year = new Label("Year : "+ tvshiw.getYear().toString());
        Label nbvues = new Label("\uD83D\uDC40 : "+ tvshiw.getNbrvues().toString());
        ctDetails.addAll(titre,year,nbvues);
        holder.add(ctDetails);
        holder.setLeadComponent(titre);
        return holder;
    }
}

