package com.example.remotecontrollertutorial.models;

import android.graphics.Point;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class InputModel {
    private Point axes;

    public InputModel() {
        axes = new Point(0, 0);
    }

    public Point getAxes() {
        return axes;
    }

    public void setAxes(Point axes) {
        this.axes = axes;
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("XAxis", axes.x);
            jsonObject.put("YAxis", axes.y);
        } catch (JSONException e) {
            Log.e("JSON", "Error building JSON");
        }
        return jsonObject;
    }
}
