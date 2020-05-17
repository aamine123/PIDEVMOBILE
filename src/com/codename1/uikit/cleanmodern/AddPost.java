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
public class AddPost extends BaseForm {
    private ServicePosts SP;
    String GlobalPath = "";
    String GlobalExtension = "";
    public AddPost(Resources res,int length) {
        super("Add a new post number "+length+1, BoxLayout.y());
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


        Container f = new Container(BoxLayout.y());




        TextField description = new TextField("","whats on your mind ,"+"Youssef"+"?", 50, TextField.ANY);
        description.getAllStyles().setFgColor(0x5f5f5f);
        description.getAllStyles().setPaddingBottom(10);
        description.getAllStyles().setBorder(RoundRectBorder.create().strokeColor(0).
                strokeOpacity(50).
                stroke(new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1)));
        description.getAllStyles().setMarginLeft(1);
        description.getAllStyles().setMarginRight(1);
        description.getAllStyles().setMarginTop(1);
        TextField tup = new TextField("","Path");

        tup.getAllStyles().setFgColor(0x5f5f5f);
        Button upload=new Button("upload");
        Label limport = new Label("No file selected");
        upload.addPointerPressedListener((ei)->{
            if (FileChooser.isAvailable()) {
                FileChooser.showOpenDialog(".pdf,application/pdf,.gif,image/gif,.png,image/png,.jpg,image/jpg,.tif,image/tif,.jpeg", e2-> {
                    String file = (String)e2.getSource();
                    if (file == null) {
                        System.out.println("No file was selected");
                    } else {
                        String extension = null;
                        if (file.lastIndexOf(".") > 0) {
                            extension = file.substring(file.lastIndexOf(".")+1);
                        }
                        if ("txt".equals(extension)) {
                            FileSystemStorage fs = FileSystemStorage.getInstance();
                            try {
                                InputStream fis = fs.openInputStream(file);
                                System.out.println(Util.readToString(fis));
                            } catch (Exception ex) {
                                Log.e(ex);
                            }
                        } else {
                            //moveFile(file,)
                            String path = file.substring(7);
                            System.out.println("Selected file :"+file.substring(44)+"\n"+"path :"+path);
                            limport.setText("file imported");
                            limport.getAllStyles().setFgColor(0x69E781);
                            tup.setText(path);
                            GlobalPath=path;
                            GlobalExtension=file.substring(file.lastIndexOf(".")+1);
                        }
                    }
                });
            }
        });
        Button submitPost = new Button("  Post  ");

        submitPost.addPointerPressedListener((e)->{

            int subname = length+1;
            Random rand = new Random();
            int upperbound = 7483647;
            int int_random = rand.nextInt(upperbound);
            String Fullname = "MobileGenerated_"+java.time.LocalDate.now()+"_"+subname+"_"+int_random+"."+GlobalExtension;
            System.out.println(Fullname);

            boolean moving = moveFile(GlobalPath,"C:/wamp64/www/Taland/web/uploads/posts/"+Fullname);
            System.out.println("moved? :"+moving);

            entities.Posts p = new entities.Posts();
            p.setImage_name(Fullname);
            p.setArchive(0);
            p.setType(0);
            p.setDescription(description.getText());

            if(ServicePosts.getInstance().addPost(p)){

                ToastBar.Status status = ToastBar.getInstance().createStatus();
                status.setMessage("Post added");
                status.show();

                newPosts l = new newPosts(res);
                l.show();
            };
        }
        );

        Button submitStory = new Button("  Add to your story",res.getImage("photo.png"));
        Container csub = new Container(BoxLayout.x());
        Label archiv = new Label("Archiver");
        archiv.getAllStyles().setFgColor(0xC12222);

        csub.addAll(submitStory,submitPost,archiv);


        Container cimport = new Container(new BorderLayout());


        cimport.add(BorderLayout.WEST,limport);
        cimport.add(BorderLayout.EAST,upload);



        f.addAll(description,cimport);

        add(f);
        csub.getAllStyles().setMarginTop(750);
        add(csub);
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
    private Container addButtonStory(Image img, String title, boolean liked, int likeCount, int commentCount) {
        int height = Display.getInstance().convertToPixels(34.5f);
        int width = Display.getInstance().convertToPixels(28f);
        Button image = new Button(img.fill(width, height));
        image.setUIID("Label");
        //Container cnt = BorderLayout.west(image);
        Container cnt = new Container(BoxLayout.y());
        cnt.add(image);

        cnt.setLeadComponent(image);
        TextArea ta = new TextArea(title);
        ta.setUIID("NewsTopLine");
        ta.setEditable(false);


        TextArea ua = new TextArea("");
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

        image.addActionListener(e -> ToastBar.showMessage(title, FontImage.MATERIAL_INFO));
        return cnt;

    }

    public boolean moveFile(String sourcePath, String targetPath) {
        File fileToMove = new File(sourcePath);
        return fileToMove.renameTo(new File(targetPath));
    }


}
