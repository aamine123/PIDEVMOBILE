package gui;

import com.codename1.components.*;
import com.codename1.db.Cursor;
import com.codename1.db.Database;
import com.codename1.db.Row;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.*;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.cleanmodern.BaseForm;
import entities.Product;
import services.ServiceProduct;

import java.io.IOException;

public class allProducts extends BaseForm {

    private Resources res;
    private ServiceProduct SP;

    public allProducts(Resources theme) {
        this.res = theme;
        //super("Newsfeed", BoxLayout.y());
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
        //content
//        SpanLabel sp = new SpanLabel();
//        sp.setText(ServiceProduct.getInstance().allProducts().toString());
//        add(sp);


        for (Product p : ServiceProduct.getInstance().allProducts()) {
            //String urll = "http://127.0.0.1/aa.jpg";
            String urll = "http://localhost/talandWEB/web/uploads/Products/" + p.getImgSrc();
            EncodedImage enc = EncodedImage.createFromImage(theme.getImage("news-item-1.jpg").scaled(250, 250), false);
            URLImage urlimg = URLImage.createToStorage(enc, p.getName(), urll);
            ImageViewer image = new ImageViewer(urlimg);
            Image im = image.getImage();

            addButton(im, p.getName().toString(), false, 0, 0, p);

            Button Delete = new Button("Delete");
           //addStringValue("Delete", Delete);
            Delete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {

                    if (Dialog.show("Alert", "Delete "+p.getName()+" !!", "OK", "Cancel"))
                    {
                        try {
                            if (ServiceProduct.getInstance().deleteProduct(p))
                            {  ToastBar.showMessage("Deleted",FontImage.MATERIAL_WARNING);
                                new allProducts(theme).show();
                            }

                        } catch (NumberFormatException e) {
                        }

                    }


                }
            });
            Button btnAddToCart=new Button("add to cart");
            addStringValue("add to cart", btnAddToCart);
            btnAddToCart.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    try {
                        Database db=Database.openOrCreate("taland");
                        
                        db.execute("create table if not exists cart (id INTEGER UNIQUE, name TEXT, category INTEGER" +
                                ", price FLOAT, userId INTEGER , date DATE, imgSrc TEXT, validation INTEGER);");
                        db.execute("insert into cart (id,name,category,price,userId,date,imgSrc,validation)" +
                                "values("+p.getId()+",'" +
                                p.getName()+"',"
                                +1+","+
                                p.getPrice()+","+
                                1+","+
                                null+",'"+
                                p.getImgSrc()+"',"+
                                p.getValidation()+")");
                        System.out.println("here");
                        db.close();

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        ToastBar.showMessage("Already added!!",FontImage.MATERIAL_WARNING);
                    }
                }
            });
        }

        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        fab.addActionListener(e -> new addProduct(theme).show());
        fab.bindFabToContainer(this.getContentPane());


    }

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }

    private void updateArrowPosition(Button b, Label arrow) {
        arrow.getUnselectedStyle().setMargin(LEFT, b.getX() + b.getWidth() / 2 - arrow.getWidth() / 2);
        arrow.getParent().repaint();


    }

    private void addButton(Image img, String title, boolean liked, int likeCount, int commentCount, Product p) {
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        Button image = new Button(img.fill(width, height));
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);
        cnt.setLeadComponent(image);
        TextArea ta = new TextArea(title);
        ta.setUIID("NewsTopLine");
        ta.setEditable(false);

        Label likes = new Label(likeCount + " Likes  ", "NewsBottomLine");
        likes.setTextPosition(RIGHT);
        if (!liked) {
            FontImage.setMaterialIcon(likes, FontImage.MATERIAL_FAVORITE);
        } else {
            Style s = new Style(likes.getUnselectedStyle());
            s.setFgColor(0xff2d55);
            FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, s);
            likes.setIcon(heartImage);
        }
        Label comments = new Label(commentCount + " Comments", "NewsBottomLine");
        FontImage.setMaterialIcon(likes, FontImage.MATERIAL_CHAT);


        cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        ta,
                        BoxLayout.encloseX(likes, comments)
                ));


        // cnt.add(Delete);

        add(cnt);
        image.addActionListener(e -> {
            new productSingle(res, p,this).show();
        });
    }


    private void bindButtonSelection(Button b, Label arrow) {
        b.addActionListener(e -> {
            if (b.isSelected()) {
                updateArrowPosition(b, arrow);
            }
        });
    }
}
