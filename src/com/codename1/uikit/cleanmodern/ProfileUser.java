package com.codename1.uikit.cleanmodern;

import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import entities.SessionUser;

public class ProfileUser extends BaseForm{
    Resources reserveRes;
    Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
    private Resources a= UIManager.initFirstTheme("/theme");
    public ProfileUser(Resources res){
        Toolbar tb = new Toolbar(true);







        Tabs swipe = new Tabs();

        Label spacer1 = new Label();
        Label spacer2 = new Label();
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle(SessionUser.loggedUser.getFirstname()+ "'s profile");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);


        tb.getAllStyles().setBorder(Border.createEmpty());
        tb.getAllStyles().setBackgroundType(Style.BACKGROUND_NONE);
        tb.getAllStyles().setBgTransparency(255);
        tb.getAllStyles().setBgColor(0x99CCCC);

        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);


        Component.setSameSize(radioContainer, spacer1, spacer2);
        add(LayeredLayout.encloseIn(swipe));
        Label test = new Label("hello");
        Label test2 = new Label("hello");
        Label test3 = new Label("hello");
//

        test2.getAllStyles().setFgColor(0xFFFFFF);
        test.getAllStyles().setFgColor(0xFFFFFF);
        test3.getAllStyles().setFgColor(0xFFFFFF);


        add(test);
        add(test2);
        add(test3);
        this.setLayout(BoxLayout.y());
        Container image=new Container(new FlowLayout(CENTER,CENTER));
        Container other =new Container(BoxLayout.y());



        addAll(image,other);



    }
}
