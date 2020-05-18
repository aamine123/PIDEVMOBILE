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
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.*;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import entities.SessionUser;

/**
 * The user profile form
 *
 * @author Shai Almog
 */
public class ProfileForm extends BaseForm {
    Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
    private Resources a= UIManager.initFirstTheme("/theme");

    public ProfileForm(Resources res) {
        super(SessionUser.loggedUser.getUsername()+"'s profile'", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Profile");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);

        // tb.addSearchCommand(e -> {});

        ImageViewer lbimg=new ImageViewer();
        EncodedImage placeholder = EncodedImage.createFromImage(a.getImage("load.png"), true);
        URLImage uRLImage = URLImage.createToStorage(placeholder, SessionUser.loggedUser.getPicture(), "http://localhost/TalandWEB/web/uploads/forum/"+SessionUser.loggedUser.getPicture());
        System.out.println(SessionUser.loggedUser.getPicture());
        lbimg.setImage(uRLImage);
        Image img = res.getImage("profile-background.jpg");
        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        Label facebook = new Label("", res.getImage(""), "");
        Label twitter = new Label("", res.getImage(""), "");
        facebook.setTextPosition(BOTTOM);
        twitter.setTextPosition(BOTTOM);
        facebook.getAllStyles().setFgColor(0xFFFFFF);
        twitter.getAllStyles().setFgColor(0xFFFFFF);

        add(LayeredLayout.encloseIn(
                sl,
                BorderLayout.south(
                        GridLayout.encloseIn(3,
                                facebook,
                                FlowLayout.encloseCenterBottom(lbimg),
                                twitter
                        )
                )
        ));

        TextField email = new TextField(SessionUser.loggedUser.getEmail(), "Email", 20, TextField.EMAILADDR);
        email.setUIID("TextFieldBlack");
        addStringValue("Email", email);

        TextField username =  new TextField(SessionUser.loggedUser.getUsername(), "BB", 20, TextField.EMAILADDR);
        username.setUIID("TextFieldBlack");
        addStringValue("Username",username);
      //  System.out.println(SessionUser.loggedUser.getFirstname());


        TextField lastnamee = new TextField(SessionUser.loggedUser.getFirstname(), "Firstname", 20, TextField.EMAILADDR);
        lastnamee.setUIID("TextFieldBlack");
        addStringValue("Firstname", lastnamee);

        TextField lastname = new TextField(SessionUser.loggedUser.getLastname(), "Lastname", 20, TextField.EMAILADDR);
        lastname.setUIID("TextFieldBlack");
        addStringValue("Lastname", lastname);


    }

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }
}
