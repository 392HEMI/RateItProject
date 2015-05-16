package com.rateit.api;

import android.app.Notification;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by alexander on 21.04.15.
 */
public class CompositeSelector extends Selector  {
    private ArrayList<Selector> operands;
    private ArrayList<SelectorAction> actions;

    public CompositeSelector(Selector selector, int selectorsCount)
    {
        operands = new ArrayList<Selector>(selectorsCount);
        actions = new ArrayList<SelectorAction>(selectorsCount - 1);

        if (selector != null)
            operands.add(selector);
    }



    public void add(Selector selector, SelectorAction action)
    {
        // only logical actions
        operands.add(selector);
        actions.add(action);
    }

    @Override
    public JSONObject serialize() {
        if (operands.size() == 1)
            return operands.get(0).serialize();
        else
        {
            JSONObject obj = new JSONObject();
            JSONArray selectors = new JSONArray();
            JSONArray acts = new JSONArray();

            JSONObject selector;
            for (Selector s : operands)
            {
                selector = s.serialize();
                selectors.put(selector);
            }

            String action;
            for (SelectorAction a : actions)
            {
                action = a.toString();
                acts.put(a);
            }

            try
            {
                obj.put("ops", selectors);
                obj.put("actions", acts);
            }
            catch (JSONException e)
            {

            }
            return obj;
        }
    }
}