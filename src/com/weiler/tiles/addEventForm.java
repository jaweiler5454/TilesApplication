package com.weiler.tiles;

import com.codename1.io.Log;
import com.codename1.ui.*;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.HashMap;
import com.codename1.io.JSONParser;
import java.io.InputStreamReader;
import java.io.ByteArrayInputStream;
import com.codename1.processing.Result;




/**
 * Created by jaweiler5454 on 5/3/18.
 */
public class addEventForm {

    private String HTML_API_KEY = "AIzaSyBKwXKAsfDSTiDHyWQ5126q6bnkuOiBNVc";
    public addEventForm()
    {

    }

    public Form newForm()
    {
        Form helloForm = new Form();
        return helloForm;
    }

    String[] searchLocations(String input) {
        try {
            if(input.length() > 0) {
                ConnectionRequest r = new ConnectionRequest();
                r.setPost(false);
                r.setUrl("https://maps.googleapis.com/maps/api/place/autocomplete/json");
                r.addArgument("key", HTML_API_KEY);
                r.addArgument("input", input);
                NetworkManager.getInstance().addToQueueAndWait(r);
                Map<String,Object> result = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));
                String[] res = Result.fromContent(result).getAsStringArray("//description");
                return res;
            }
        } catch(Exception err) {
            Log.e(err);
        }
        return null;
    }
}
