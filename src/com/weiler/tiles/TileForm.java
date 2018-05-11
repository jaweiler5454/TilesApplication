package com.weiler.tiles;

/**
 * Created by jaweiler5454 on 5/2/18.
 */
import com.codename1.ui.*;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;


public class TileForm {

    public TileForm()
    {

    }

    private DataBaseHelper globalBase = new DataBaseHelper();

    public Form formCreated()
    {
        Form formReturned = new Form();
        ArrayList<Map<String,Object>> tilesByOrg = new ArrayList<Map<String,Object>>();
        ArrayList<Button> tilesButtons = new ArrayList<Button>();
        for(int i=0; i<globalBase.getPosts("milton_academy").size(); i++)
        {
            tilesByOrg.add(globalBase.getPosts("milton_academy").get(i));
            tilesButtons.add(new Button(tilesByOrg.get(i).get("Title").toString()));
            tilesButtons.get(i).addActionListener(evt -> {
                //Form.show
            });


        }

        formReturned.setLayout(new GridLayout(tilesByOrg.size() , 2)); //Create GridLayout
        for(int i=0; i<tilesButtons.size(); i++)
        {
            formReturned.add(tilesButtons.get(i));
        }
        return formReturned;
    }

}
