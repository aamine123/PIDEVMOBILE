package com.codename1.uikit.cleanmodern;

import com.codename1.components.SpanLabel;
import com.codename1.ui.util.Resources;
import entities.Sujet;
import services.ServiceCommentaire;

public class comments extends BaseForm{
    public comments (Resources theme, Sujet s){
        SpanLabel sp=new SpanLabel();
        System.out.println(s.toString());
        sp.setText(ServiceCommentaire.getInstance().getAllcomments(s.getId_f()).toString());
        add(sp);
    }
}
