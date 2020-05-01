package gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import entities.Product;

public class productSingle extends Form {

Resources theme;
    public productSingle(Resources theme, Product p) {
        this.theme=theme;
        setLayout(BoxLayout.y());
        String urll="http://localhost/talandWEB/web/uploads/Products/"+p.getImgSrc();
        EncodedImage enc = EncodedImage.createFromImage(theme.getImage("news-item-1.jpg").scaled(500, 500), false);
        URLImage urlimg = URLImage.createToStorage(enc, p.getName(), urll);
        ImageViewer image = new ImageViewer(urlimg);
        Image im=image.getImage();
        add(image);


    }
}
