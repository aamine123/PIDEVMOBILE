package gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.components.ToastBar;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.cleanmodern.BaseForm;
import entities.Category;
import entities.Product;
import services.ServiceCategory;
import services.ServiceProduct;

public class updateProduct extends BaseForm {
    private Resources theme;
    private ServiceProduct SP;

    public updateProduct(Resources theme, Product p, Form previous) {
        this.theme = theme;

        //super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("update");
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

        TextField productname = new TextField();
        productname.setText(p.getName());
        productname.setUIID("TextFieldBlack");
        addStringValue("Product name", productname);

        TextField price = new TextField();
        price.setText(String.valueOf(p.getPrice()));
        price.setUIID("TextFieldBlack");
        addStringValue("Price", price);

        ComboBox listens=new ComboBox();
        try {
            for (Category c: ServiceCategory.getInstance().allCategories()){
                listens.addItem(c.getName());
            }
        }catch (Exception e){}

        listens.setUIID("TextFieldBlack");
        addStringValue("Category", listens);
        // add(listens);
        CheckBox cbValidation=new CheckBox();
        cbValidation.setText("sell");

        addStringValue("Sell",cbValidation);
        Button add=new Button("update");
        addStringValue("update", add);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if((productname.getText().length()==0))
                    Dialog.show("Alert","Please fill the field",new Command("OK"));
                else{
                    try {p.setName(productname.getText());
                        p.setPrice(Integer.parseInt(price.getText()));
                        if (cbValidation.isSelected()){
                            p.setValidation(1);
                        }else{
                            p.setValidation(0);
                        }


                        if(ServiceProduct.getInstance().updateProduct(p,listens.getSelectedItem().toString(),p.getValidation())){

                            new allProducts(theme).show();
                        }
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }



//                  SP=new ServiceProduct();
//
//                  SP.addProduct(p);
                }
            }
        });



    }

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }
}
