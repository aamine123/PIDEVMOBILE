package com.codename1.uikit.cleanmodern;

import com.codename1.ui.Button;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import entities.Sujet;
import services.ServiceSujet;


public class modifiersujet extends BaseForm {
    private ServiceSujet ser;
    public modifiersujet(Sujet s,Resources theme){
        Label newdesc=new Label("Description");
        TextField desc=new TextField();
        Label aman=new Label();
        addAll(newdesc,desc);
        Button confirmer =new Button("confimer");
        System.out.println(desc.getText());
        confirmer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                ser.modifiersujet(s.getId_f(),desc.getText());
            }
        });

        add(confirmer);
    }
}
