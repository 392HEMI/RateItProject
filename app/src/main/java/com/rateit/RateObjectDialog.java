package com.rateit;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.rateit.api.FieldOperand;
import com.rateit.api.SelectQuery;
import com.rateit.http.HttpClient;
import com.rateit.http.SelectType;
import com.rateit.http.handlers.IJsonResponseHandler;
import com.rateit.models.SetUserCommentResult;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by alexander on 09.05.15.
 */
public class RateObjectDialog extends Dialog {

    private ObjectActivity activity;
    private RatingBar ratingBar;

    private void getObjectID()
    {
    }

    public RateObjectDialog(ObjectActivity a) {
        super(a);
        activity = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.rate_object_dialog_layout);

        Button okBtn = (Button)findViewById(R.id.okBtn);
        Button cancelBtn = (Button)findViewById(R.id.cancelBtn);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Log.e("click", "clicjk");
                activity.setObjectRating(ratingBar.getRating());
                RateObjectDialog.this.dismiss();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(lp);
        getWindow().getDecorView().setTop(50);
    }
}