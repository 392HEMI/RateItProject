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
public class AddCommentDialog extends Dialog {

    private ObjectActivity activity;

    private void getObjectID()
    {
    }

    public AddCommentDialog(ObjectActivity a) {
        super(a);
        activity = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_comment_dialog);

        final EditText commentText = (EditText)findViewById(R.id.commentText);
        final Button okBtn = (Button)findViewById(R.id.okBtn);
        Button cancelBtn = (Button)findViewById(R.id.cancelBtn);

        final Dialog dlg = this;
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("dialog", "set comment");
                String text = commentText.getText().toString();
                if (text.isEmpty())
                    return;
                RateItApplication app = (RateItApplication) activity.getApplication();

                HttpClient client = app.getHttpClient();
                IJsonResponseHandler<SetUserCommentResult> handler = new IJsonResponseHandler<SetUserCommentResult>() {
                    @Override
                    public void onStart() {
                        activity.lock();
                    }

                    @Override
                    public void onSuccess(int statusCode, SetUserCommentResult[] array) {
                    }

                    @Override
                    public void onSuccess(int statusCode, SetUserCommentResult object) {
                        Log.e("dialog", "success");
                        Log.e("isnull", Boolean.toString(object == null));
                        if (object.state == 1) {
                            activity.refreshUserComment();
                            activity.refreshComments();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Throwable e, String response) {
                        Log.e("dialog", "failure");
                    }

                    @Override
                    public void onFinish() {
                        activity.unlock();
                        dlg.dismiss();
                    }
                };
                JSONObject pack = new JSONObject();
                try {
                    Log.e("user_id", activity.getUser().getID().toString());
                    pack.put("ObjectID", activity.getObjectID());
                    pack.put("UserID", activity.getUser().getID().toString());
                    pack.put("Text", text);
                } catch (JSONException ex) {
                }
                client.post(SetUserCommentResult.class, "data", "SetComment", "", pack, SelectType.Single, handler);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(lp);
        getWindow().getDecorView().setTop(50);
    }
}