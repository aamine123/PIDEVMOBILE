package gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.cleanmodern.BaseForm;
import entities.Product;

public class productSingle extends BaseForm {

Resources theme;
    public productSingle(Resources theme, Product p) {
        this.theme=theme;
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Our products");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(theme);

        tb.addSearchCommand(e -> {
        });


        Image img = theme.getImage("profile-background.jpg");
        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);


        add(LayeredLayout.encloseIn(sl, BorderLayout.south(GridLayout.encloseIn(3))));
        setLayout(BoxLayout.y());
        String urll="http://localhost/talandWEB/web/uploads/Products/"+p.getImgSrc();
        EncodedImage enc = EncodedImage.createFromImage(theme.getImage("news-item-1.jpg").scaled(500, 500), false);
        URLImage urlimg = URLImage.createToStorage(enc, p.getName(), urll);
        ImageViewer image = new ImageViewer(urlimg);
        Image im=image.getImage();
        add(image);


    }
}
