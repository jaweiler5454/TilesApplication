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
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.maps.Coord;
import java.util.ArrayList;
import com.codename1.ui.spinner.Picker;
import java.util.Date;





/**
 * Created by jaweiler5454 on 5/3/18.
 */
public class addEventForm {

    private String HTML_API_KEY = "AIzaSyBKwXKAsfDSTiDHyWQ5126q6bnkuOiBNVc";
    private final String[] finalAddress = {""};
    private Coord coordinates = new Coord(0,0);
    private String formattedLng = "";
    private String formattedLat = "";

    public addEventForm()
    {

    }

    public Form newForm()
    {
        Form helloForm = new Form();
        helloForm.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        final DefaultListModel<String> options = new DefaultListModel<>();
        AutoCompleteTextField ac = new AutoCompleteTextField(options) {
            @Override
            protected boolean filter(String text) {
                if (text.length() == 0) {
                    return false;
                }
                String[] l = searchLocations(text);
                if (l == null || l.length == 0) {
                    return false;
                }

                options.removeAll();
                for (String s : l) {
                    options.addItem(s);
                }
                return true;
            }

        };

        TextField addTitle = new TextField("Add a Title");
        TextArea addDescription = new TextArea("Describe your event");
        TextField addOrganization = new TextField("What is your organization?");

        String titleText = addTitle.getText();
        String descriptionText = addDescription.getText();
        String organizationText = addOrganization.getText();

        ac.setMinimumElementsShownInPopup(5);
        Style so = UIManager.getInstance().getComponentStyle("TitleCommand");
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_SEARCH, so);
        String getAddress = new String();
        getAddress = ac.getText();
        System.out.println(getAddress);

        finalAddress[0] = ac.getText();
        final String finalAddressTrue = finalAddress[0];
        //coordinates = geocode(finalAddressTrue);
        Double lng = coordinates.getLongitude();
        Double lat = coordinates.getLatitude();
        formattedLng = lng.toString();
        formattedLat = lat.toString();

        Picker datePicker = new Picker();
        datePicker.setType(Display.PICKER_TYPE_DATE);
        Picker timePicker = new Picker();
        timePicker.setType(Display.PICKER_TYPE_TIME);
        datePicker.setDate(new Date());
        System.out.println(datePicker.getValue());


        Button addData = new Button("Add New Tile");
        addData.addActionListener(evt -> {
            System.out.println("hello");
            DataBaseHelper base = new DataBaseHelper();
            Map<String,String> params = new HashMap<String, String>();
            params.put("eventID", "1234567");
            //params.put("title", titleText);
            params.put("title", "a god");
            //params.put("latitude", formattedLat);
            params.put("latitude", "20");
            //params.put("longitude", formattedLng);
            params.put("longitude", "20");
            //params.put("location", finalAddressTrue);
            params.put("location", "the spot");
            //params.put("description", descriptionText);
            params.put("description", "sup my dudes");
            params.put("userPosted", "null for now");
            params.put("ifResponded", "true");
            params.put("responders", "");
            params.put("imageURL", "");
           // params.put("date", datePicker.getValue().toString());
            params.put("date", "oclock");
            //params.put("time", timePicker.getValue().toString());
            params.put("time", "hello");
            //params.put("organization", organizationText);
            params.put("organization", "hello");
            params.put("schoolID", "milton_academy");
            base.addPost(params);
        });


        helloForm.add(addTitle).add(addOrganization).add(addDescription).add(ac).add(datePicker).add(timePicker).add(addData);

        return helloForm;

    }



    public String[] searchLocations(String input) {
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

    public Coord geocode(String fullAddress){
        String text="";
        Coord ret = null;
        try {
            final String link = "https://maps.googleapis.com/maps/api/geocode/json";
            // URL url = new URL(link + "?address=" + URLEncoder.encode(fullAddress, "UTF-8")+ "&sensor=false");
            // Open the Connection
            ConnectionRequest req = new ConnectionRequest();
            req.setPost(false);
            req.setUrl(link);
            req.addArgument("address", fullAddress);
            req.addArgument("key", HTML_API_KEY);


            //https://gist.github.com/ahmedengu/0869b8c0644eee0d57d3891bc51a00dc
            NetworkManager.getInstance().addToQueueAndWait(req);
            Map<String, Object> response = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(req.getResponseData()), "UTF-8"));
            if (response.get("results") != null) {
                ArrayList results = (ArrayList) response.get("results");
                if (results.size() > 0) {
                    LinkedHashMap location = (LinkedHashMap) ((LinkedHashMap) ((LinkedHashMap) results.get(0)).get("geometry")).get("location");
                    ret = new Coord((double) location.get("lat"), (double) location.get("lng"));



                }
            }
            return ret;

        }
        catch(Exception ex){
            Log.e(ex);
        }

        return null;



    }
}
