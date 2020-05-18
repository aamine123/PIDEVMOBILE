package com.codename1.uikit.cleanmodern;

import com.codename1.location.GeofenceListener;
import com.codename1.notifications.LocalNotification;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.util.UITimer;

import java.util.Calendar;

public class GeofenceListenerImpl implements GeofenceListener {
    @Override
    public void onExit(String id) {
    }

    @Override
    public void onEntered(String id) {
        if (Display.getInstance().isMinimized()) {
            Display.getInstance().callSerially(() -> {
                Dialog.show("Welcome", "Thanks for arriving", "OK", null);
            });
        } else {
         //   Dialog.show("Welcomeeeeeeee", "Thanks for arriving", "OK", null);
            UITimer.timer(1000 * 60 * 5, true, () -> {
                long time = Calendar.getInstance().getTime().getTime() + 1000;

                LocalNotification ln = new LocalNotification();
                ln.setId("LnMessage");
                ln.setAlertTitle("Alert title");
                ln.setAlertBody("Alert message");
                ln.setAlertSound("/notification_sound_bell.mp3");

                Display.getInstance().scheduleLocalNotification(ln, time, LocalNotification.REPEAT_NONE);
            });
        }
    }
}