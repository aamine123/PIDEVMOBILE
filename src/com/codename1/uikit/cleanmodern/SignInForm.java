/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */

package com.codename1.uikit.cleanmodern;

import com.codename1.components.FloatingHint;
import com.codename1.components.ToastBar;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.Constraint;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import entities.SessionUser;
import entities.User;
import services.authuser;
import utils.Statics;


public class SignInForm extends BaseForm {

  //controle
    boolean result=false;
    static User connectedUser=new User();
    authuser auth = new authuser();
    public static TextField username,password;
    public static Resources res;
    public SignInForm(Resources res) {
        super(new BorderLayout());
        if(!Display.getInstance().isTablet()) {
            BorderLayout bl = (BorderLayout)getLayout();
            bl.defineLandscapeSwap(BorderLayout.NORTH, BorderLayout.EAST);
            bl.defineLandscapeSwap(BorderLayout.SOUTH, BorderLayout.CENTER);
        }
        getTitleArea().setUIID("Container");
        setUIID("SignIn");



        TextField username = new TextField("", "Username", 20, TextField.ANY);
        TextField password = new TextField("", "Password", 20, TextField.PASSWORD);
        username.setSingleLineTextArea(false);
        password.setSingleLineTextArea(false);

       // username.getUnselectedStyle().setFgColor(621200);
        //password.getUnselectedStyle().setFgColor(251340);
        Button signIn = new Button("Sign In");
        Button signUp = new Button("Sign Up");


        //signIn.getUnselectedStyle().setFgColor(100000);
        //signUp.getUnselectedStyle().setFgColor(654111);




        signUp.addActionListener(e -> new SignUpForm(res).show());
        signUp.setUIID("Link");
        Label doneHaveAnAccount = new Label("Don't have an account?");
        doneHaveAnAccount.getUnselectedStyle().setFgColor(0xff0000);
        Validator val = new Validator();


        val.addConstraint(password, new LengthConstraint(4));
        val.addConstraint(username, RegexConstraint.validEmail());

        Container content = BoxLayout.encloseY(
                new FloatingHint(username),
                createLineSeparator(),
                new FloatingHint(password),
                createLineSeparator(),
                signIn,
                FlowLayout.encloseCenter(doneHaveAnAccount, signUp)
        );
        content.setScrollableY(true);
        add(BorderLayout.SOUTH, content);
        signIn.requestFocus();
        //e -> new NewsfeedForm(res).show()
        signIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                authuser auth = authuser.getInstance();

                if (!checkCredentials(username, password)) {
                    if (auth.checkLogin(username.getText(), password.getText())) {
                        User currentLoggedUser = auth.getUser(username.getText(), password.getText()).get(0);
                        SessionUser.loggedUser = currentLoggedUser;
                        new NewsfeedForm(res).show();

                    }

                    else {

                        ToastBar.showMessage("verifier les champs ", FontImage.MATERIAL_INFO);

                    }
                }

            }
        });
    }

    public boolean checkCredentials(TextField username,TextField password){

        Validator validator = new Validator();
        validator.setShowErrorMessageForFocusedComponent(true);
        validator.addConstraint(username, new Constraint() {
            @Override
            public boolean isValid(Object value) {
                //  boolean res = false;
                if (value.equals("")) {
                    username.setUIID("Invalid Username");

                    result = true;


                }else{
                    result = false;
                }
                return result;
            }

            @Override
            public String getDefaultFailMessage() {
                return "Please Enter a valid username";
            }

        });


        validator.addConstraint(password, new Constraint() {
            @Override
            public boolean isValid(Object value) {
                // boolean res = false;
                if (value.equals("")) {
                    password.setUIID("Invalid Password");
                    System.out.println("invalid Password");
                //    Dialog.show("Erreur", "Champs password Obligatoire", "Ok", null);

                    result = true;
                }else{
                    result = false;
                }
                return result;
            }

            @Override
            public String getDefaultFailMessage() {
                return "Please Enter a valid password";
            }

        });
        System.out.println(res);
        return result;
    }


}



