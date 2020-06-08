package gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.ScaleImageLabel;
import com.codename1.io.FileSystemStorage;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.*;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.cleanmodern.BaseForm;
import entities.Category;
import entities.Product;
import entities.User;
import services.ServiceCategory;
import services.ServiceProduct;

import java.io.IOException;

public class addProduct extends BaseForm {
    private Resources theme;
    private ServiceProduct SP;
    private String path;
    private ImageViewer l;
    private Container imgCtn;
    public addProduct(Resources theme) {
        this.theme = theme;

        //super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Profile");
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

        TextField productname = new TextField("","product name");
        productname.setUIID("TextFieldBlack");
        addStringValue("Product name", productname);

        TextField price = new TextField("", "0000",20, TextArea.NUMERIC);
        price.setUIID("TextFieldBlack");
        addStringValue("Price", price);

        ComboBox listens=new ComboBox();
        try {
            for (Category c:ServiceCategory.getInstance().allCategories()){
                listens.addItem(c.getName());
            }
        }catch (Exception e){}

        listens.setUIID("TextFieldBlack");
        addStringValue("Category", listens);
       // add(listens);
//*************************image

        Button getimage = new Button("Upload Image");
        getimage.addActionListener(e -> {
            Display.getInstance().openGallery(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    if (evt != null && evt.getSource() != null) {
                        path = (String) evt.getSource();
                        Image image = null;
                        try {
                            image = Image.createImage(FileSystemStorage.getInstance()
                                    .openInputStream(path)).fill(125, 175);
                            //imName=image.;
                        } catch (IOException ex) {
                            Dialog.show("Error", ex.getMessage(), "OK", null);
                        }
                        l = new ImageViewer(image);
                        l.getStyle().setMarginLeft(40);
                        imgCtn.add(l);
                    }
                }
            }, Display.GALLERY_IMAGE);
        });
//*****************************/image
      Button add=new Button("add");
        addStringValue("add", add);
      add.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent evt) {
              if((productname.getText().length()==0))
                  Dialog.show("Alert","Please fill the field",new Command("OK"));
              else{
                  try { Product p=new Product(0,productname.getText(),1,Integer.parseInt(price.getText()),
                          0,null,"h",0);

                      if(ServiceProduct.getInstance().addProduct(p,listens.getSelectedItem().toString()));
                  }catch (NumberFormatException e){}



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

