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
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.*;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import entities.Posts;
import entities.SessionUser;
import entities.User;
import services.ServiceLikes;
import services.ServicePosts;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The newsfeed form
 *
 * @author Shai Almog
 */
public class newPosts extends BaseForm {
    private ServicePosts SP;
    private ArrayList<Posts> posts;
    Resources res;
    public newPosts(Resources res) {
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Newsfeed");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);


        Container searchusers = new Container(BoxLayout.y());



        tb.addSearchCommand(e -> {
            String text = (String)e.getSource();
            if(text == null || text.length() == 0) {
                System.out.println("no text");
                searchusers.removeAll();
            }else{
                searchusers.removeAll();
            }
        });
        add(searchusers);

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




        Container Cpost = new Container(BoxLayout.y());

        Tabs Cstories = new Tabs();
        Cstories.setSwipeActivated(true);
        Cstories.setAnimateTabSelection(true);
        Cstories.setEagerSwipeMode(true);
        Cstories.setTabsContentGap(CN.convertToPixels(20, true));
        Cstories.setUIID("Container");
        Cstories.setTabUIID("Container");
        Cstories.getTabsContainer().setUIID("Container");
        Cstories.getContentPane().setUIID("Container");



        SP = new ServicePosts();
        posts = new ArrayList<>();
        posts = SP.getAllPosts();






        for (int i = 0; i < posts.size(); i++) {
            Posts current = posts.get(i);

            String urlimage = "http://localhost/talandWEB/web/uploads/posts/"+posts.get(i).getImage_name();
            String extension =posts.get(i).getImage_name().charAt(posts.get(i).getImage_name().length()-3)+""+posts.get(i).getImage_name().charAt(posts.get(i).getImage_name().length()-2)+""+posts.get(i).getImage_name().charAt(posts.get(i).getImage_name().length()-1);
            //System.out.println("image :"+posts.get(i).getImage_name()+" extension :"+extension.toLowerCase());
            if (posts.get(i).getType()==0 && posts.get(i).getArchive()==0){
                if (extension.equals("jpg") || extension.toLowerCase().equals("png")){

                    EncodedImage enc3 = EncodedImage.createFromImage(res.getImage("dog.jpg"), false);
                    Image image = URLImage.createToStorage(enc3,"stock"+posts.get(i).getImage_name()+".png", urlimage);
                    Label navigate = new Label("...");
                    navigate.getAllStyles().setFgColor(0xC12222);
                    EditPost ep = new EditPost(res,current);
                    ModifyPost mp = new ModifyPost(res,current);
                    /*navigate.addPointerPressedListener((ev)-> {
                        ep.show();
                    });*/
                    navigate.addPointerPressedListener((ev)-> {
                        Dialog d = new Dialog(current.getIdU().get("firstname")+" "+current.getIdU().get("lastname")+"'s post");
                        Label supp = new Label("Delete");
                        supp.getAllStyles().setFgColor(0xC12222);
                        Label edits = new Label("Edit");
                        Label open = new Label("Details");
                        edits.getAllStyles().setFgColor(0x000000);
                        open.getAllStyles().setFgColor(0x000000);

                        open.addPointerPressedListener((r)->{
                            ep.show();
                        });
                        edits.addPointerPressedListener((r)->{
                            mp.show();
                        });
                        d.setLayout(BoxLayout.y());
                        float cidu = Float.parseFloat(current.getIdU().get("id").toString());
                        int postidu = ((int)cidu);
                        if (SessionUser.loggedUser.getId()==postidu){
                            d.add(supp);
                            d.add(edits);
                        }

                        d.add(open);



                        d.showPopupDialog(navigate);
                    });
                    Container single = addButton(posts.get(i),image,posts.get(i).getIdU(),posts.get(i).getDescription(), false, posts.get(i).getNbrlikes(), posts.get(i).getNbrcomments(),navigate);
                    Cpost.add(single);
                }
            }
            if (posts.get(i).getType()==1 && posts.get(i).getArchive()==0){
                if (extension.equals("jpg") || extension.toLowerCase().equals("png")) {
                    EncodedImage enc3 = EncodedImage.createFromImage(res.getImage("dog.jpg"), false);
                    Image image = URLImage.createToStorage(enc3,"stock"+posts.get(i).getImage_name()+".png", urlimage);
                    //Cstories.add(addButtonStory(image,posts.get(i).getIdU(),posts.get(i).getDescription(), false, posts.get(i).getNbrlikes(), posts.get(i).getNbrcomments()));
                    Cstories.addTab("", addButtonStory(image,posts.get(i).getIdU(),posts.get(i).getDescription(), false, posts.get(i).getNbrlikes(), posts.get(i).getNbrcomments()));
                    //addTabStorie(stories, image, posts.get(i).getIdU());
                }
            }
        }




















        Cstories.setSelectedIndex(1);
        //add(stories);
        Label lsee = new Label("See what’s new") ;
        lsee.getAllStyles().setFgColor(0x5f5f5f);



        Container h = new Container(new BorderLayout());
        Container f = new Container(BoxLayout.y());
        Button add = new Button("Add a new post !");

        add.addPointerPressedListener((e)->{
            AddPost n = new AddPost(res,posts.size()) ;
            n.show();
        });

        Label lstorie = new Label("S  T  O  R  I  E  S");
        lstorie.getAllStyles().setFgColor(0x000000);
        f.add(lsee);
        f.add(lstorie);
        h.add(BorderLayout.WEST,f);
        h.add(BorderLayout.EAST,add);

        Label ltrend = new Label("T R E N D I N G");
        ltrend.getAllStyles().setFgColor(0x000000);

        Label lwatch = new Label("Watch the latest posts from our community");
        lwatch.getAllStyles().setFgColor(0x5f5f5f);






        add(h);
        add(Cstories);
        add(new Label(""));
        add(ltrend);
        add(lwatch);
        add(Cpost);

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
    private void addTabStorie(Tabs swipe, Image img, LinkedHashMap<Object,Object> user) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        if(img.getHeight() < size) {
            img = img.scaledHeight(size-10);
        }
        Label likes = new Label("");
        Style heartStyle = new Style(likes.getUnselectedStyle());
        heartStyle.setFgColor(0xff2d55);


        Label comments = new Label("");

        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            img = img.scaledHeight((Display.getInstance().getDisplayHeight() / 2));
        }
        img = img.scaledHeight(700);
        ScaleImageLabel image = new ScaleImageLabel(img);
        //image.setHeight(10);
        image.setUIID("Container");

        image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Label overlay = new Label(" ", "ImageOverlay");
        Label lusername= new Label((String) user.get("username"));
        lusername.getAllStyles().setFgColor(0x000000);
        Container page1 =
                LayeredLayout.encloseIn(
                        image,
                        overlay,
                        BorderLayout.south(
                                BoxLayout.encloseY(
                                        lusername,
                                        FlowLayout.encloseIn(likes, comments),
                                        new Label()
                                )
                        )
                );

        swipe.addTab("", page1);
    }


    private Container addButton(Posts p,Image img, LinkedHashMap<Object,Object> user, String title, boolean liked, int likeCount, int commentCount,Label navigate) {
        int height = Display.getInstance().convertToPixels(61.5f);
        int width = Display.getInstance().convertToPixels(60f);
        Button image = new Button(img.fill(width, height));
        image.setUIID("Label");
        Container cnt = BorderLayout.center(image);
        //cnt.setLeadComponent(image);
        TextArea ta = new TextArea(title);
        ta.setUIID("NewsTopLine");
        ta.setEditable(false);


        TextArea ua = new TextArea((String) user.get("firstname")+" "+(String) user.get("lastname"));
        ua.setUIID("NewsTopLine");
        ua.setEditable(false);

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

        cua.add(BorderLayout.WEST,ua);

            cua.add(BorderLayout.EAST,navigate);


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


    private Container addButtonStory(Image img, LinkedHashMap<Object,Object> user, String title, boolean liked, int likeCount, int commentCount) {
        int height = Display.getInstance().convertToPixels(34.5f);
        int width = Display.getInstance().convertToPixels(28f);
        Button image = new Button(img.fill(width, height));
        image.setUIID("Label");
        //Container cnt = BorderLayout.west(image);
        Container cnt = new Container(BoxLayout.y());
        cnt.add(image);
        Label lusername= new Label((String) user.get("username"));
        lusername.getAllStyles().setFgColor(0x5f5f5f);
        cnt.add(lusername);
        cnt.setLeadComponent(image);
        TextArea ta = new TextArea(title);
        ta.setUIID("NewsTopLine");
        ta.setEditable(false);


        TextArea ua = new TextArea((String) user.get("firstname")+" "+(String) user.get("lastname"));
        ua.setUIID("NewsTopLine");
        ua.setEditable(false);

        Label likes = new Label(likeCount + " Likes  ", "NewsBottomLine");
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


        /*cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        ua,
                        ta,
                        BoxLayout.encloseX(likes, comments)
                ));*/

        image.addActionListener(e -> ToastBar.showMessage((String) user.get("firstname")+" "+(String) user.get("lastname")+" : \n"+title, FontImage.MATERIAL_INFO));
        return cnt;

    }


}
