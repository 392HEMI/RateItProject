package com.rateit;

import android.app.Application;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.provider.Telephony;
import android.support.v7.app.ActionBarActivity;

import com.rateit.http.HttpClient;

/**
 * Created by alexander on 22.04.15.
 */
public class RateItActivity extends ActionBarActivity {
    private ProgressDialog dlg;

    public HttpClient getHttpClient()
    {
        RateItApplication app = (RateItApplication)getApplication();
        HttpClient httpClient = app.getHttpClient();
        return httpClient;
    }

    public RateItApplication getRateItApp()
    {
        Application app = getApplication();
        if (app instanceof RateItApplication)
            return (RateItApplication)app;
        return null;
    }

    public void lock()
    {
        if (dlg == null) {
            dlg = new ProgressDialog(this);
            dlg.setMessage("Загрузка...");
        }
        dlg.show();
    }

    public void unlock()
    {
        if (dlg != null)
            dlg.hide();
    }
}