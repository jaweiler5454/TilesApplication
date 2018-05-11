package com.weiler.tiles;

import com.cloudinary.utils.ObjectUtils;
import com.codename1.io.*;
import com.codename1.ui.*;

import java.awt.*;
import java.awt.Font;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.io.InputStreamReader;
import java.io.ByteArrayInputStream;
import com.codename1.processing.Result;
import com.codename1.ui.Button;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.maps.Coord;
import java.util.ArrayList;
import com.codename1.ui.spinner.Picker;
import java.util.Date;
import com.codename1.ui.Container;
import com.codename1.ui.Component;
import com.codename1.ui.Label;

import com.codename1.ui.layouts.TextModeLayout;
import com.codename1.ui.PickerComponent;
import com.codename1.ui.TextComponent;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import com.codename1.ui.list.MultiList;
import java.util.Hashtable;
import java.util.Random;





/**
 * Created by jaweiler5454 on 5/3/18.
 */
public class addEventForm {

    private String HTML_API_KEY = "AIzaSyBKwXKAsfDSTiDHyWQ5126q6bnkuOiBNVc";
    private final String[] finalAddress = {""};
    private Coord coordinates = new Coord(0,0);
    private String formattedLng = "";
    private String formattedLat = "";
    private DataBaseHelper globalBase = new DataBaseHelper();
    private Map<String,String> params = new HashMap<String, String>();


    public addEventForm()
    {


    }

    public Form newForm()
    {
        TextModeLayout textModeLayout = new TextModeLayout(3,2);
        Form helloForm = new Form("Add an Event", textModeLayout);

        TextComponent title = new TextComponent().label("Title");
        TextComponent organization = new TextComponent().label("Organization");
        TextComponent description = new TextComponent().label("Description").multiline(true);
        PickerComponent timeComponent = PickerComponent.createDateTime(new Date()).label("Date and Time");
        Container forAc = new Container();


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


        ac.setMinimumElementsShownInPopup(5);
        Style so = UIManager.getInstance().getComponentStyle("TitleCommand");
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_SEARCH, so);
        String getAddress = new String();
        getAddress = ac.getText();
        System.out.println(getAddress);
        forAc.add(new Label("Location")).add(ac);




        Button addData = new Button("Add New Tile");
        addData.addActionListener(evt -> {
            System.out.println("hello");
            DataBaseHelper base = new DataBaseHelper();

            finalAddress[0] = ac.getText();
            final String finalAddressTrue = finalAddress[0];
            coordinates = geocode(finalAddressTrue);
            Double lng = coordinates.getLongitude();
            Double lat = coordinates.getLatitude();
            formattedLng = lng.toString();
            formattedLat = lat.toString();

            params.put("eventID", "1");
            params.put("title", title.getText());
            params.put("latitude", formattedLat);
            params.put("longitude", formattedLng);
            params.put("location", finalAddressTrue);
            params.put("description", description.getText());
            params.put("userPosted", "jaweiler5454@gmail.com");
            params.put("responses", "10");
            params.put("schoolID", "milton_academy");
            //params.put("imageURL", "milton.edu");
            params.put("date", "");
            params.put("time", "");
            params.put("organization", organization.getText());

        });


        helloForm.add(title).add(organization).add(timeComponent).add(description).add(forAc);

        return helloForm;

    }


    public Form imageCreation()
    {
        Form imageForm = new Form();
        Button browse = new Button();
        final Image[] formImg = {null};
        browse.addActionListener(evt -> {
            Display.getInstance().openGallery(event ->{
                if (event != null && event.getSource() != null) {
                    String filePath = (String) event.getSource();
                    System.out.println(filePath);
                    int fileNameIndex = filePath.lastIndexOf("/") + 1;
                    String fileName = filePath.substring(fileNameIndex);
                    params.put("imageURL", globalBase.uploadImage(filePath).get("url").toString());
                    Image img = null;
                    img = globalBase.getImage(params.get("imageURL"), Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());

                    formImg[0] = img;
                    // Do something, add to List
                }

            }, Display.GALLERY_IMAGE);
        });

        imageForm.add(formImg[0]).add(browse);


        return imageForm;
    }

  // HERE IS THE ID GENERATOR METHODS

    final String charList = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    final int idLength = 10;

    private int getRandomNumber() {
        int randomInt = 0;
        Random randomGenerator = new Random();
        randomInt = randomGenerator.nextInt(charList.length());
        if (randomInt - 1 == -1) {
            return randomInt;
        } else {
            return randomInt - 1;
        }
    }


    public String standIDGenerator()
    {
        StringBuffer randStr = new StringBuffer();
        for(int i=0; i<idLength; i++){
            int number = getRandomNumber();
            char ch = charList.charAt(number);
            randStr.append(ch);
        }

        return randStr.toString();
    }



    boolean checkID(String prelimID)
    {
        ArrayList basedIds = new ArrayList();
        for(int i=0; i<globalBase.getPosts("milton_academy").size(); i++) {
            basedIds.add(globalBase.getPosts("milton_academy").get(i).get("EventID").toString());
        }

          if(basedIds.contains(prelimID))
          {
              return false;
          }

          else
          {
            return true;
          }

    }




/// GEOCODING PROCEDURES BELOW
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
