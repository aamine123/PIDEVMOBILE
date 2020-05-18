


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
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import gui.allProducts;
import gui.cart;
import entities.SessionUser;

/**
 * Base class for the forms with common functionality
 *
 * @author Shai Almog
 */
public class BaseForm extends Form {
    private Resources theme;
    Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
    private Resources a= UIManager.initFirstTheme("/theme");

    public BaseForm() {
    }

    public BaseForm(Layout contentPaneLayout) {
        super(contentPaneLayout);
    }

    public BaseForm(String title, Layout contentPaneLayout) {
        super(title, contentPaneLayout);
    }


    public Component createLineSeparator() {
        Label separator = new Label("", "WhiteSeparator");
        separator.setShowEvenIfBlank(true);
        return separator;
    }

    public Component createLineSeparator(int color) {
        Label separator = new Label("", "WhiteSeparator");
        separator.getUnselectedStyle().setBgColor(color);
        separator.getUnselectedStyle().setBgTransparency(255);
        separator.setShowEvenIfBlank(true);
        return separator;
    }

    protected void addSideMenu(Resources res) {

        Toolbar tb = getToolbar();
        Image img = res.getImage("profile-background.jpg");
        ImageViewer lbimg=new ImageViewer();
        EncodedImage placeholder = EncodedImage.createFromImage(a.getImage("load.png"), true);
        URLImage uRLImage = URLImage.createToStorage(placeholder, SessionUser.loggedUser.getPicture(), "http://localhost/TalandWEB/web/uploads/forum/"+SessionUser.loggedUser.getPicture());
        System.out.println(SessionUser.loggedUser.getPicture());
        lbimg.setImage(uRLImage);

        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);

        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        tb.addComponentToSideMenu(LayeredLayout.encloseIn(
                sl,
                FlowLayout.encloseCenterBottom(lbimg)
        ));

        lbimg.addPointerReleasedListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new com.codename1.uikit.cleanmodern.ProfileForm(res).show();

            }
        });

        tb.addMaterialCommandToSideMenu("Newsfeed", FontImage.MATERIAL_UPDATE, e -> new com.codename1.uikit.cleanmodern.NewsfeedForm(res).show());
        //tb.addMaterialCommandToSideMenu("Forum", FontImage.MATERIAL_UPDATE, e -> new com.codename1.uikit.cleanmodern.sujetslist(res).show());
        tb.addMaterialCommandToSideMenu("Posts", FontImage.MATERIAL_UPDATE, e -> new com.codename1.uikit.cleanmodern.newPosts(res).show());
        tb.addMaterialCommandToSideMenu("Competitions", FontImage.MATERIAL_UPDATE, e -> new com.codename1.uikit.cleanmodern.Competitions(res).show());
        tb.addMaterialCommandToSideMenu("Profile", FontImage.MATERIAL_SETTINGS, e -> new com.codename1.uikit.cleanmodern.ProfileForm(res).show());
        tb.addMaterialCommandToSideMenu("Store", FontImage.MATERIAL_STORE, e -> new allProducts(res).show());
        tb.addMaterialCommandToSideMenu("cart", FontImage.MATERIAL_SHOPPING_CART, e -> new cart(res,"").show());
        tb.addMaterialCommandToSideMenu("Forum", FontImage.MATERIAL_FORUM, e -> new allsujets(res).show());
        tb.addMaterialCommandToSideMenu("Artcile", FontImage.MATERIAL_FORUM, e -> new HomeArticle(res).show());
        tb.addMaterialCommandToSideMenu("Logout", FontImage.MATERIAL_EXIT_TO_APP, e -> new WalkthruForm(res).show());

    }
}
