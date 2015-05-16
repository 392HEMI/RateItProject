package com.rateit;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.rateit.adapters.CategoryAdapter;
import com.rateit.adapters.ObjectsAdapter;
import com.rateit.adapters.TypeAdapter;
import com.rateit.api.ConstantOperand;
import com.rateit.api.FieldOperand;
import com.rateit.api.Operand;
import com.rateit.api.OrderRule;
import com.rateit.api.OrderType;
import com.rateit.api.QueryField;
import com.rateit.api.SelectQuery;
import com.rateit.api.Selector;
import com.rateit.api.SelectorAction;
import com.rateit.api.SimpleSelector;
import com.rateit.http.HttpClient;
import com.rateit.http.SelectType;
import com.rateit.http.handlers.IJsonResponseHandler;
import com.rateit.models.Category;
import com.rateit.models.GeneralObject;
import com.rateit.models.Type;

import java.util.Stack;

public class MainActivity extends RateItActivity {
    private HttpClient httpClient;
    private ListView listView;
    private Stack<IMethod> actionSeq;
    private IMethod backMethod;


    private class GoToCategory implements IMethod
    {
        private int categoryID;
        private boolean saveLastState;
        public GoToCategory(int categoryID, boolean saveLastState)
        {
            this.categoryID = categoryID;
            this.saveLastState = saveLastState;
        }

        @Override
        public void Execute()
        {
            getCategories(categoryID, saveLastState);
        }
    }

    private class GoToType implements IMethod
    {
        private int typeID;
        private boolean saveLastState;
        public GoToType(int typeID, boolean saveLastState)
        {
            this.typeID = typeID;
            this.saveLastState = saveLastState;
        }

        @Override
        public void Execute()
        {
            getTypes(typeID, saveLastState);
        }
    }

    private class GoToObjects implements IMethod
    {
        private int typeID;
        private boolean saveLastState;
        public GoToObjects(int typeID, boolean saveLastState)
        {
            this.typeID = typeID;
            this.saveLastState = saveLastState;
        }

        @Override
        public void Execute()
        {
            getObjects(typeID, saveLastState);
        }
    }


    private void InitializeComponent()
    {
        listView = (ListView)findViewById(R.id.menuListView);
    }

    private void getCategories(final int parentID, final boolean saveLastState)
    {
        String parent_id = Integer.toString(parentID);

        QueryField[] fields = {new QueryField("id"), new QueryField("title"), new QueryField("parent_id")};
        Operand leftOperand = new FieldOperand(int.class, "parent_id");
        Operand rightOperand = new ConstantOperand(int.class, parent_id);
        Selector selector = new SimpleSelector(leftOperand, rightOperand, SelectorAction.EQUALITY);
        OrderRule orderRule = new OrderRule("title", OrderType.Ascending);
        SelectQuery query = new SelectQuery("category", fields, selector, orderRule, 10, 0);

        final MainActivity activity = this;
        IJsonResponseHandler<Category> handler = new IJsonResponseHandler<Category>() {
            @Override
            public void onStart() {
                lock();
            }

            @Override
            public void onSuccess(int statusCode, Category[] array) {
                categoriesLoaded(parentID, array, saveLastState);
            }

            @Override
            public void onSuccess(int statusCode, Category object) {
            }

            @Override
            public void onFailure(int statusCode, Throwable e, String response) {
            }

            @Override
            public void onFinish() {
            }
        };
        httpClient.ApiQuery(Category.class, query, SelectType.Array, handler);
    }

    private void getTypes(final int categoryID, final boolean saveLastState)
    {
        String categoryId = Integer.toString(categoryID);
        QueryField[] fields = {new QueryField("id"), new QueryField("title"), new QueryField("category_id")};
        Operand leftOperand = new FieldOperand(int.class, "category_id");
        Operand rightOperand = new ConstantOperand(int.class, categoryId);
        Selector selector = new SimpleSelector(leftOperand, rightOperand, SelectorAction.EQUALITY);
        OrderRule orderRule = new OrderRule("title", OrderType.Ascending);
        SelectQuery query = new SelectQuery("object_type", fields, selector, orderRule, 10, 0);
        IJsonResponseHandler<Type> handler = new IJsonResponseHandler<Type>() {
            @Override
            public void onStart() {
                lock();
            }

            @Override
            public void onSuccess(int statusCode, Type[] array) {
                typesLoaded(categoryID, array, saveLastState);
            }

            @Override
            public void onSuccess(int statusCode, Type object) {

            }

            @Override
            public void onFailure(int statusCode, Throwable e, String response) {

            }

            @Override
            public void onFinish() {

            }
        };
        httpClient.ApiQuery(Type.class, query, SelectType.Array, handler);
    }

    private void getObjects(final int typeID, final boolean saveLastState)
    {
        String typeId = Integer.toString(typeID);
        QueryField[] fields = {new QueryField("id"), new QueryField("title"), new QueryField("type_id")};
        Operand leftOperand = new FieldOperand(int.class, "type_id");
        Operand rightOperand = new ConstantOperand(int.class, typeId);
        Selector selector = new SimpleSelector(leftOperand, rightOperand, SelectorAction.EQUALITY);
        OrderRule orderRule = new OrderRule("title", OrderType.Ascending);
        SelectQuery query = new SelectQuery("object", fields, selector, orderRule, 10, 0);
        IJsonResponseHandler<GeneralObject> handler = new IJsonResponseHandler<GeneralObject>() {
            @Override
            public void onStart() {
                lock();
            }

            @Override
            public void onSuccess(int statusCode, GeneralObject[] array) {
                objectsLoaded(typeID, array, saveLastState);
            }

            @Override
            public void onSuccess(int statusCode, GeneralObject object) {

            }

            @Override
            public void onFailure(int statusCode, Throwable e, String response) {

            }

            @Override
            public void onFinish() {

            }
        };
        httpClient.ApiQuery(GeneralObject.class, query, SelectType.Array, handler);
    }

    public final void categoriesLoaded(int parentID, Category[] categories, boolean saveLastState)
    {
        if (categories.length == 0)
        {
            getTypes(parentID, true);
            return;
        }

        if (saveLastState && backMethod != null) {
            actionSeq.push(backMethod);
        }
        CategoryAdapter adapter = new CategoryAdapter(this, categories);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category category = (Category) parent.getItemAtPosition(position);
                getCategories(category.id, true);
            }
        });
        backMethod = new GoToCategory(parentID, false);
        unlock();
    }

    public final void typesLoaded(int categoryID, Type[] types, boolean saveLastState)
    {
        if (types.length == 0)
        {
            unlock();
            return;
        }

        if (saveLastState)
            actionSeq.push(backMethod);
        TypeAdapter adapter = new TypeAdapter(this, types);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Type type = (Type) parent.getItemAtPosition(position);
                getObjects(type.id, true);
            }
        });
        backMethod = new GoToType(categoryID, false);
        unlock();
    }

    public final void objectsLoaded(int typeID, GeneralObject[] objects, boolean saveLastState)
    {
        if (objects.length == 0)
        {
            unlock();
            return;
        }

        if (saveLastState)
            actionSeq.push(backMethod);
        ObjectsAdapter adapter = new ObjectsAdapter(this, objects);
        listView.setAdapter(adapter);
        final MainActivity activity = this;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GeneralObject obj = (GeneralObject)parent.getItemAtPosition(position);
                if (obj == null)
                    return;
                Intent intent = new Intent(activity, ObjectActivity.class);
                intent.putExtra("objectID", obj.id);
                startActivity(intent);
            }
        });
        backMethod = new GoToObjects(typeID, false);
        unlock();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_menu_layout);

        httpClient = getHttpClient();
        actionSeq = new Stack<IMethod>();
        InitializeComponent();
        lock();
        //backMethod = new GoToCategory(1, false);
        getCategories(1, true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed()
    {
        if (actionSeq.empty())
            return;
        backMethod = actionSeq.pop();
        backMethod.Execute();
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
