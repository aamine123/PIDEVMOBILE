package com.codename1.uikit.cleanmodern;

import com.codename1.messaging.Message;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import entities.Sujet;
import services.ServiceSujet;

import java.util.Date;

public class ajoutsujet extends BaseForm {
    private ServiceSujet ser;
    public  ajoutsujet(Resources theme){
        setTitle("ajouter sujet");
        //Date date = new Date();
        TextField desc = new TextField("","Description");
        Button ajouter=new Button("Ajouter");
        ajouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((desc.getText().length()==0))
                    Dialog.show("Alert", "Please fill the field desciption", new Command("OK"));
                else
                {
                        ser=new ServiceSujet();
                        ser.addsujet(11,desc.getText());
                            Dialog.show("Success","Connection accepted",new Command("OK"));
                    Message m = new Message("Forum Created");

                    Display.getInstance().sendMessage(new String[] {"mohamedamine.mbarki@esprit.tn"}, "New sujet"+desc.getText(), m);
                    new allsujets(theme).show();
                }
            }
        });
        addAll(desc,ajouter);

    }
}
