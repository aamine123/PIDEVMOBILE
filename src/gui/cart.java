package gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.db.Cursor;
import com.codename1.db.Database;
import com.codename1.db.Row;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.table.DefaultTableModel;
import com.codename1.ui.table.Table;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.table.TableModel;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.cleanmodern.BaseForm;
import entities.Product;
import entities.User;
import javafx.scene.control.Cell;
import services.ServiceProduct;
import utils.Email;

import javax.swing.*;
import java.io.IOException;

public class cart extends BaseForm {
    Resources theme;
    User loggedInUser;
    int totaal=0;
    public cart(Resources theme,String msg) {
        this.theme = theme;
        loggedInUser=new User(10,"eya","lookil","eyaloukil@gmail.com");
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Cart");
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

        try {
            Database db = Database.openOrCreate("taland");
            db.execute("create table if not exists cart (id INTEGER , name TEXT, category INTEGER" +
                    ", price FLOAT, userId INTEGER UNIQUE, date DATE, imgSrc TEXT, validation INTEGER);");
            Cursor c = db.executeQuery("select * from cart");

            Font mediumItalicSystemFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_ITALIC, Font.SIZE_MEDIUM);
            Label product = new Label("product");
            product.getUnselectedStyle().setFont(mediumItalicSystemFont);
            Label lprice = new Label("price");
            lprice.getUnselectedStyle().setFont(mediumItalicSystemFont);
            Label owner = new Label("owner");
            owner.getUnselectedStyle().setFont(mediumItalicSystemFont);
            Container tTable = TableLayout.encloseIn(4, product, lprice,
                    owner, new Label(""));


            try {
                while (c.next()) {
                    Row rw = c.getRow();
                    int id = rw.getInteger(0);
                    String name = rw.getString(1);
                    int price = rw.getInteger(3);
                    totaal+=price;
                    int idUser = rw.getInteger(4);
                    Product pr=new Product();
                    pr.setUserId(idUser);
                    User u= ServiceProduct.getInstance().getProductUserDetails(pr);
                    Button btnDelete = new Button();
                    btnDelete.setSize(new Dimension(25, 25));
                    btnDelete.setMaterialIcon(FontImage.MATERIAL_DELETE);
                    btnDelete.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            try {
                                Database db = Database.openOrCreate("taland");
                                db.execute("delete from cart where id=" + id);
                                new cart(theme,"").show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    tTable.addAll(new Label(name), new Label(String.valueOf(price)), new Label(u.getName()), btnDelete);


                }tTable.addAll(new Label("Total"),new Label(String.valueOf(totaal)),new Label(""),new Label(""));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            Label lTitle = new Label("Your cart");
            Font largeBoldSystemFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE);
            lTitle.getUnselectedStyle().setFont(largeBoldSystemFont);
            lTitle.setEndsWith3Points(true);
            add(lTitle);
            Cursor nbrProductInCart = db.executeQuery("select count(*) from cart");
            Row rNbr = nbrProductInCart.getRow();
            int nbr = rNbr.getInteger(0);
            if (nbr != 0) {
                add(tTable);
                Button btnValider = new Button("Buy");
                btnValider.setMaterialIcon(FontImage.MATERIAL_SHOP);
                btnValider.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        try {
                            Database db = Database.openOrCreate("taland");
                            db.execute("create table if not exists cart (id INTEGER , name TEXT, category INTEGER" +
                                    ", price FLOAT, userId INTEGER UNIQUE, date DATE, imgSrc TEXT, validation INTEGER);");
                            Cursor c = db.executeQuery("select * from cart");
                            while (c.next()) {
                                Row rw = c.getRow();
                                int id = rw.getInteger(0);
                                String name = rw.getString(1);
                                int price = rw.getInteger(3);
                                int idUser = rw.getInteger(4);
                                Product pr=new Product();
                                pr.setUserId(idUser);
                                pr.setName(name);
                                pr.setId(id);
                                User u= ServiceProduct.getInstance().getProductUserDetails(pr);
                                System.out.println(pr);
                                System.out.println(u);
                                String message="We are Taland team:\n" +
                                        "\n" +
                                        "Hi "+u.getName()+", we send you this mail to inform you that "+loggedInUser.getName()+"("+loggedInUser.getEmail()+") want to buy your product\n" +
                                        ""+pr.getName()+".\n" +
                                        "confirm  \"http://127.0.0.1:8000/Products/getProducts/"+pr.getId()+" Here";
                            //Email email=new Email( "talandpidev@gmail.com",  "Tal123and!",  u.getEmail(),  "Taland team",  message);

                            }
                            db.execute("delete from cart");
                            new cart(theme,"we sent mails to the owners").show();
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                    }
                });
                add(btnValider);
            } else {
                add(new Label("your cart is empty!!"+msg));
            }

            db.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
