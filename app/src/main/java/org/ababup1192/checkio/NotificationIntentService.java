package org.ababup1192.checkio;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class NotificationIntentService extends IntentService {


    public NotificationIntentService() {
        super("NotificationIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        NotificationUtil.notify(this, new Intent(this, MainActivity.class), "通知ー", "タイトル", "内容");
    }

}
