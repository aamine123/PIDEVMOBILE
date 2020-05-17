/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.codename1.uikit.cleanmodern;

import com.codename1.components.ImageViewer;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.io.Util;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.*;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;

import entities.Posts;
import services.ServiceLikes;
import services.ServicePosts;
import com.codename1.ext.filechooser.FileChooser;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Random;

/**
 * The newsfeed form
 *
 * @author Shai Almog
 */
public class EditPost extends BaseForm {
    private ServicePosts SP;
    String GlobalPath = "";
    String GlobalExtension = "";
    public EditPost(Resources res, Posts current) {
        super(current.getDescription(), BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Add a new post");
        getContentPane().setScrollVisible(false);



        this.getToolbar().addMaterialCommandToLeftBar("Back", FontImage.MATERIAL_ARROW_BACK, (e) -> {
            newPosts l = new newPosts(res);
            l.show();
        });

        //super.addSideMenu(res);



        Tabs swipe = new Tabs();

        addTab(swipe, res.getImage("breadcumb.jpg"), new Label(), "", "", "");
        addTab(swipe, res.getImage("breadcumb3.jpg"), new Label(), "", "", "");

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

        Component.setSameSize(radioContainer, new Label(), new Label());
        add(LayeredLayout.encloseIn(swipe, radioContainer));


        String urlimage = "http://localhost/taland/web/uploads/posts/"+current.getImage_name();
        EncodedImage enc3 = EncodedImage.createFromImage(res.getImage("dog.jpg"), false);
        Image image = URLImage.createToStorage(enc3,"stock"+current.getImage_name()+".png", urlimage);

        Container single = addButton(current,image,current.getIdU(),current.getDescription(), false, current.getNbrlikes(), current.getNbrcomments());

        String urluser = "http://localhost/taland/web/uploads/posts/"+current.getIdU().get("imageName");
        Image imageuser = URLImage.createToStorage(enc3,"users"+current.getIdU().get("id")+".png", urluser);
        ImageViewer imguser = new ImageViewer(imageuser);

        Container cus = new Container(new BorderLayout());
        cus.add(BorderLayout.WEST,imguser);

        Label username = new Label(current.getIdU().get("firstname")+" "+current.getIdU().get("lastname"));
        username.getAllStyles().setFgColor(0x000000);
        cus.add(BorderLayout.CENTER,username);
        Label edit = new Label("edit");
        edit.addPointerPressedListener((e)->{
            ModifyPost mp = new ModifyPost(res,current);
            mp.show();
        });
        cus.add(BorderLayout.EAST,edit);
        add(cus);
        add(single);











    }
    private void addTab(Tabs swipe, Image img, Label spacer, String likesStr, String commentsStr, String text) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        if(img.getHeight() < size) {
            img = img.scaledHeight(size);
        }
        Label likes = new Label(likesStr);
        Style heartStyle = new Style(likes.getUnselectedStyle());
        heartStyle.setFgColor(0xff2d55);


        Label comments = new Label(commentsStr);

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
    private Container addButton(Posts p,Image img, LinkedHashMap<Object,Object> user, String title, boolean liked, int likeCount, int commentCount) {
        int height = Display.getInstance().convertToPixels(61.5f);
        int width = Display.getInstance().convertToPixels(60f);
        Button image = new Button(img.fill(width, height));
        image.setUIID("Label");
        Container cnt = BorderLayout.center(image);
        //cnt.setLeadComponent(image);
        TextArea ta = new TextArea(title);
        ta.setUIID("NewsTopLine");
        ta.setEditable(false);



        Label likes = new Label(likeCount + " Likes  ", "NewsBottomLine");
        likes.getAllStyles().setFgColor(0x5f5f5f);
        likes.setTextPosition(RIGHT);
        if(!liked) {
            FontImage.setMaterialIcon(likes, FontImage.MATERIAL_FAVORITE);
        } else {
            Style s = new Style(likes.getUnselectedStyle());
            s.setFgColor(0x5f5f5f);
            FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, s);
            likes.setIcon(heartImage);

        }
        Label comments = new Label(commentCount + " Comments", "NewsBottomLine");
        FontImage.setMaterialIcon(likes, FontImage.MATERIAL_CHAT);
        comments.getAllStyles().setFgColor(0x5f5f5f);

        Container cua = new Container(new BorderLayout());


        /*if ((double)(user.get("id"))==15){
            cua.add(BorderLayout.EAST,navigate);
        }*/

        Label spacer1 = new Label("aa");
        spacer1.getAllStyles().setFgColor(0xFFFFFF);
        cnt.add(BorderLayout.NORTH,cua);
        cnt.add(BorderLayout.SOUTH,
                BoxLayout.encloseY(
                        BoxLayout.encloseX(likes, comments),
                        ta,
                        spacer1
                ));

        //image.addActionListener(e -> ToastBar.showMessage((String) user.get("firstname")+" "+(String) user.get("lastname")+" : \n"+title, FontImage.MATERIAL_INFO));



        return cnt;

    }




}
