package com.rateit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;

import com.rateit.api.CompositeSelector;
import com.rateit.api.ConstantOperand;
import com.rateit.api.FieldOperand;
import com.rateit.api.Operand;
import com.rateit.api.OrderRule;
import com.rateit.api.QueryField;
import com.rateit.api.SelectQuery;
import com.rateit.api.Selector;
import com.rateit.api.SelectorAction;
import com.rateit.api.SimpleSelector;
import com.rateit.http.HttpClient;
import com.rateit.http.SelectType;
import com.rateit.http.handlers.ApiJsonResponseHandler;
import com.rateit.http.handlers.IJsonResponseHandler;
import com.rateit.http.handlers.custom.IFileDownloadCompleteHandler;
import com.rateit.http.handlers.custom.ImageResponseHandler;
import com.rateit.models.Comment;
import com.rateit.models.Comments;
import com.rateit.models.CommentsModel;
import com.rateit.models.DeleteUserCommentResult;
import com.rateit.models.GetObjectRatingResult;
import com.rateit.models.ObjectModel;
import com.rateit.models.SetCommentRatingResult;
import com.rateit.models.SetObjectRatingResult;
import com.rateit.models.UserComment;
import com.rateit.models.ValidationUserResult;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.util.UUID;

public class ObjectActivity extends RateItActivity {
    TabHost tabhost;
    Resources resources;
    LayoutInflater inflater;
    RateItApplication app;
    LinearLayout userCommentLayout;
    LinearLayout commentsLayout;
    ImageButton addcomm;
    ImageButton editcomm;
    ImageButton deletecomm;
    TextView caption;
    RatingBar ratingBar;

    Drawable likeDrawable;
    Drawable notlikeDrawable;

    int objectID;
    User user;
    UserComment userComment;
    Comment[] comments;

    public int getObjectID()
    {
        return objectID;
    }

    public User getUser() {
        return user;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.object_layout);

        userCommentLayout = (LinearLayout)findViewById(R.id.userComment);
        commentsLayout = (LinearLayout)findViewById(R.id.comments);
        tabhost= (TabHost) findViewById(R.id.tabHost);
        inflater = getLayoutInflater();
        resources = getResources();
        app = getRateItApp();

        Intent intent = getIntent();
        objectID = intent.getIntExtra("objectID", -1);
        if (objectID == -1)
            this.finish();

        user = app.getUser();
        if (user == null)
            app.Autorize(true);

        // Setup tabhost
        tabhost.setup();

        likeDrawable = resources.getDrawable(R.drawable.like);
        notlikeDrawable = resources.getDrawable(R.drawable.like_disabled);

        Drawable descIcon = resources.getDrawable(R.drawable.infotabicon);
        Drawable commentIcon = resources.getDrawable(R.drawable.commentstabicon);
        Drawable imagesIcon = resources.getDrawable(R.drawable.cameratabicon);

        TabHost.TabSpec tabspec;
        tabspec = tabhost.newTabSpec("informationTab");
        tabspec.setIndicator("Описание", descIcon);
        tabspec.setContent(R.id.informationTab);
        tabhost.addTab(tabspec);

        tabspec = tabhost.newTabSpec("commentTab");
        tabspec.setIndicator("Отзывы", commentIcon);
        tabspec.setContent(R.id.commentTab);
        tabhost.addTab(tabspec);

        tabspec = tabhost.newTabSpec("imageTab");
        tabspec.setIndicator("Фото", imagesIcon);
        tabspec.setContent(R.id.imageTab);
        tabhost.addTab(tabspec);

        tabhost.setCurrentTabByTag("informationTab");

        tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equalsIgnoreCase("informationTab"))
                    showPropertiesTab();
                else if (tabId.equalsIgnoreCase("commentTab"))
                    showCommentsTab();
                else if (tabId.equalsIgnoreCase("imageTab"))
                    showImagesTab();
            }
        });

        caption = (TextView)findViewById(R.id.caption);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);

        addcomm = (ImageButton)findViewById(R.id.addcomm);
        editcomm = (ImageButton)findViewById(R.id.editcomm);
        deletecomm = (ImageButton)findViewById(R.id.deletecomm);

        addcomm.setEnabled(false);
        editcomm.setEnabled(false);
        deletecomm.setEnabled(false);

        ratingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RateObjectDialog dlg = new RateObjectDialog(ObjectActivity.this);
                dlg.show();
            }
        });

        addcomm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCommentDialog dlg = new AddCommentDialog(ObjectActivity.this);
                dlg.show();
            }
        });
        editcomm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCommentDialog dlg = new AddCommentDialog(ObjectActivity.this);
                dlg.show();
            }
        });
        deletecomm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ObjectActivity.this);
                builder.setMessage("Вы действительно хотите удалить свой комментарий?")
                        .setTitle("Подтверждение удаления")
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // delete comment
                                HttpClient client = getHttpClient();
                                final JSONObject obj = new JSONObject();
                                try {
                                    obj.put("CommentID", userComment.CommentID);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                IJsonResponseHandler<DeleteUserCommentResult> handler = new IJsonResponseHandler<DeleteUserCommentResult>() {
                                    @Override
                                    public void onStart() {

                                    }

                                    @Override
                                    public void onSuccess(int statusCode, DeleteUserCommentResult[] array) {

                                    }

                                    @Override
                                    public void onSuccess(int statusCode, DeleteUserCommentResult object) {
                                        if (object == null)
                                            return;

                                        if (object.state)
                                        {
                                            refreshUserComment();
                                            refreshComments();
                                        }
                                    }

                                    @Override
                                    public void onFailure(int statusCode, Throwable e, String response) {

                                    }

                                    @Override
                                    public void onFinish() {

                                    }
                                };
                                client.post(DeleteUserCommentResult.class, "data", "DeleteUserComment", "", obj, SelectType.Single, handler);
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        ratingBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                RateObjectDialog dlg = new RateObjectDialog(ObjectActivity.this);
                dlg.show();
                return false;
            }
        });

        refreshModel();
        Log.e("a", "refresh rating");
        refreshRating();
    }

    private void showCommentsTab() {
        refreshUserComment();
        refreshComments();
    }

    private void showPropertiesTab(){

    }

    private void showImagesTab() {

    }

    private void setLikeBtnDrawable(ImageButton btn, boolean isLike)
    {
        Drawable drawable;
        if (isLike)
            drawable = likeDrawable;
        else
            drawable = notlikeDrawable;
    }

    private void setModel(ObjectModel model)
    {
        if (model == null)
            finish();
        caption.setText(model.title);
    }

    private void setRating(double rating)
    {
        ratingBar.setRating((float) rating);
    }

    private void setUserComment(UserComment comment)
    {
        userCommentLayout.removeAllViews();

        Boolean commIsNull = comment == null;
        addcomm.setEnabled(commIsNull);
        editcomm.setEnabled(!commIsNull);
        deletecomm.setEnabled(!commIsNull);

        if (commIsNull)
            return;

        userComment = comment;

        addcomm.setEnabled(false);
        editcomm.setEnabled(true);
        deletecomm.setEnabled(true);

        if (userCommentLayout != null)
        {
            View v = inflater.inflate(R.layout.user_comment_layout, userCommentLayout, false);
            TextView name = (TextView)v.findViewById(R.id.Fname);
            TextView surname = (TextView)v.findViewById(R.id.Sname);
            TextView commText = (TextView)v.findViewById(R.id.commTxt);
            TextView commentRating = (TextView)v.findViewById(R.id.commentRating);

            name.setText(comment.UserName);
            surname.setText(comment.UserSurname);
            commText.setText(comment.CommentText);
            commentRating.setText(Integer.toString(comment.LikesCount));

            userCommentLayout.addView(v);
        }
    }

    private void setComments(Comment[] comments)
    {
        if (comments == null)
            return;

        commentsLayout.removeAllViews();
        ScrollView scrollView = (ScrollView)findViewById(R.id.scrollView);
        int scrollPos = scrollView.getVerticalScrollbarPosition();
        for (final Comment c : comments)
        {
            View v = inflater.inflate(R.layout.comment_layout, commentsLayout, false);
            TextView sname = (TextView)v.findViewById(R.id.Sname);
            TextView fname = (TextView)v.findViewById(R.id.Fname);
            TextView commTxt = (TextView)v.findViewById(R.id.commTxt);
            TextView commentRating = (TextView)v.findViewById(R.id.commentRating);
            final ImageView image = (ImageView)findViewById(R.id.Icon);
            final ImageButton like = (ImageButton)v.findViewById(R.id.Like);

            sname.setText(c.UserName);
            fname.setText(c.UserSurname);
            commTxt.setText(c.Text);
            commentRating.setText(Integer.toString(c.LikesCount));

            if (!c.UserID.equals(user.getID())) {
                if (c.IsLike == null)
                    c.IsLike = false;
                setLikeBtnDrawable(like, c.IsLike);
            }
            else like.setVisibility(Button.INVISIBLE);

            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    likeComment(like, c.ID, !c.IsLike);
                }
            });

            scrollView.setVerticalScrollbarPosition(scrollPos);
            commentsLayout.addView(v);
        }
    }

    private void refreshModel()
    {
        QueryField[] fields = { new QueryField("id"), new QueryField("title"), new QueryField("logo") };
        FieldOperand leftOperand = new FieldOperand(int.class, "id");
        ConstantOperand rightOperand = new ConstantOperand(int.class, Integer.toString(objectID));
        Selector selector = new SimpleSelector(leftOperand, rightOperand, SelectorAction.EQUALITY);
        SelectQuery query = new SelectQuery("object", fields, selector, null, 1, 0);
        HttpClient client = getHttpClient();
        IJsonResponseHandler<ObjectModel> handler = new IJsonResponseHandler<ObjectModel>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(int statusCode, ObjectModel[] array) {
            }

            @Override
            public void onSuccess(int statusCode, ObjectModel object) {
                setModel(object);
            }

            @Override
            public void onFailure(int statusCode, Throwable e, String response) {
                finish();
            }

            @Override
            public void onFinish() {

            }
        };
        client.ApiQuery(ObjectModel.class, query, SelectType.Single, handler);
    }

    private void refreshRating()
    {
        Log.e("refres", "rating");
        final JSONObject obj = new JSONObject();
        try {
            obj.put("ObjectID", objectID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        IJsonResponseHandler<GetObjectRatingResult> handler = new IJsonResponseHandler<GetObjectRatingResult>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(int statusCode, GetObjectRatingResult[] array) {

            }

            @Override
            public void onSuccess(int statusCode, GetObjectRatingResult object) {
                Log.e("rating", Double.toString(object.rating));
                ratingBar.setRating((float)object.rating);
            }

            @Override
            public void onFailure(int statusCode, Throwable e, String response) {
                Log.e("ratomg", "a");
            }

            @Override
            public void onFinish() {

            }
        };

        HttpClient client = getHttpClient();
        client.post(GetObjectRatingResult.class, "data", "GetObjectRating", "", obj, SelectType.Single, handler);
    }

    public void refreshUserComment()
    {
        HttpClient httpClient = getHttpClient();
        JSONObject obj = new JSONObject();
        try {
            obj.put("ObjectID", objectID);
            obj.put("UserID", user.getID());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        IJsonResponseHandler<UserComment> handler = new IJsonResponseHandler<UserComment>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int statusCode, UserComment[] array) {
            }

            @Override
            public void onSuccess(int statusCode, UserComment object) {
                setUserComment(object);
            }

            @Override
            public void onFailure(int statusCode, Throwable e, String response) {
            }

            @Override
            public void onFinish() {
            }
        };

        httpClient.post(UserComment.class, "data", "GetUserComment", "", obj, SelectType.Single, handler);
    }

    public void refreshComments()
    {
        final JSONObject obj = new JSONObject();
        try {
            obj.put("ObjectID", objectID);
            obj.put("UserID", getUser().getID());
            obj.put("Limit", 10);
            obj.put("Offset", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        IJsonResponseHandler<CommentsModel> handler = new IJsonResponseHandler<CommentsModel>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int statusCode, CommentsModel[] array) {
            }

            @Override
            public void onSuccess(int statusCode, CommentsModel obj) {
                setComments(obj.result);
            }

            @Override
            public void onFailure(int statusCode, Throwable e, String response) {

            }

            @Override
            public void onFinish() {

            }
        };
        getHttpClient().post(CommentsModel.class, "data", "GetComments", "", obj, SelectType.Single, handler);
    }

    public void likeComment(final ImageButton btn, int commentID, final boolean isLike)
    {
        Log.e("like", "comment");
        final HttpClient httpClient = getHttpClient();
        final JSONObject obj = new JSONObject();
        try {
            obj.put("CommentID", commentID);
            obj.put("UserID", user.getID());
            obj.put("IsLike", isLike);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        IJsonResponseHandler<SetCommentRatingResult> handler = new IJsonResponseHandler<SetCommentRatingResult>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(int statusCode, SetCommentRatingResult[] array) {

            }

            @Override
            public void onSuccess(int statusCode, SetCommentRatingResult object) {
                switch (object.state)
                {
                    // Unknown error
                    case 0:
                    {
                        break;
                    }
                    // Success
                    case 1:
                    {
                        Log.e("a", object.toString());
                        break;
                    }
                    // CommentNotFound
                    case 2:
                    {
                        break;
                    }
                    // UserNotFound
                    case 3:
                    {
                        getRateItApp().Autorize(true);
                        return;
                    }
                }
                refreshComments();
            }

            @Override
            public void onFailure(int statusCode, Throwable e, String response) {
                Log.e("error", Integer.toString(statusCode));
            }

            @Override
            public void onFinish() {

            }
        };
        httpClient.post(SetCommentRatingResult.class, "data", "SetCommentRating", "", obj, SelectType.Single, handler);
    }

    public void setObjectRating(double rating)
    {
        Log.e("rat", "clicjk");
        final JSONObject obj = new JSONObject();
        try {
            obj.put("ObjectID", objectID);
            obj.put("UserID", user.getID());
            obj.put("Mark", rating);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        IJsonResponseHandler<SetObjectRatingResult> handler = new IJsonResponseHandler<SetObjectRatingResult>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(int statusCode, SetObjectRatingResult[] array) {
            }

            @Override
            public void onSuccess(int statusCode, SetObjectRatingResult object) {
                Log.e("1sate", Integer.toString(object.state));
                if (object.state == 1)
                    refreshRating();
            }

            @Override
            public void onFailure(int statusCode, Throwable e, String response) {
            }

            @Override
            public void onFinish() {

            }
        };
        getHttpClient().post(SetObjectRatingResult.class, "data", "SetObjectRating", "", obj, SelectType.Single, handler);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
