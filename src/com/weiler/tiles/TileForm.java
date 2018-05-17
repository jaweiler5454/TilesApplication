package com.weiler.tiles;

/**
 * Created by jaweiler5454 on 5/2/18.
 */
import com.codename1.io.URL;
import com.codename1.ui.*;

import com.codename1.ui.Button;
import com.codename1.ui.Image;
import com.codename1.ui.table.TableLayout;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import com.codename1.ui.plaf.Style;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Font;





public class TileForm {

    public TileForm()
    {
    }

    private DataBaseHelper globalBase = new DataBaseHelper();
    public int displayWidth = Display.getInstance().getDisplayWidth();
    public int displayHeight = Display.getInstance().getDisplayHeight();
    public Style tileUnselectedStyle = new Style();

    public Form formCreated()
    {
        TableLayout tl = new TableLayout( 4, 2);
        Form formReturned = new Form("Hello", tl);
        formReturned.setScrollable(true);
        ArrayList<Map<String,Object>> tilesByOrg = new ArrayList<Map<String,Object>>();
        ArrayList<Button> tilesButtons = new ArrayList<Button>();


        for(int i=0; i<globalBase.getPosts("milton_academy").size(); i++)
        {
            tilesByOrg.add(globalBase.getPosts("milton_academy").get(i));
            tilesButtons.add(new Button(tilesByOrg.get(i).get("Title").toString()));



        }

        /*
        formReturned.setLayout(new GridLayout(tilesByOrg.size() , 2)); //Create GridLayout
        for(int i=0; i<tilesButtons.size(); i++)
        {
            formReturned.add(tilesButtons.get(i));

        }
        formReturned.getContentPane().addPullToRefresh(() -> {
            for(int i=0; i<globalBase.getPosts("milton_academy").size(); i++)
            {
                tilesByOrg.remove(i);
                tilesButtons.remove(i);
                tilesByOrg.add(globalBase.getPosts("milton_academy").get(i));
                tilesButtons.add(new Button(tilesByOrg.get(i).get("Title").toString()));
                tilesButtons.get(i).setSize(new Dimension(displayWidth/2, displayHeight/3));
                Image image = globalBase.getImage(tilesByOrg.get(i).get("ImageURL").toString(), tilesButtons.get(i).getWidth(), tilesButtons.get(i).getHeight());
                tilesButtons.get(i).getUnselectedStyle().setBgImage(image);
                tilesButtons.get(i).addActionListener(evt -> {
                    //Form.show
                });


            }



        });



        */

        for(int i=0; i<tilesButtons.size(); i++) {

            System.out.println(tilesByOrg.get(i).get("Title").toString() + tilesByOrg.get(i).get("ImageURL").toString());

            Image image = globalBase.getImage(tilesByOrg.get(i).get("ImageURL").toString(), 100, 100);
            tileUnselectedStyle.setBgImage(image);
            tilesButtons.get(i).setUnselectedStyle(tileUnselectedStyle);

            formReturned.add(tl.createConstraint().widthPercentage(50).heightPercentage(25), tilesButtons.get(i));

        }




            //tilesButtons.get(i).getUnselectedStyle().setBgImage(image);

        return formReturned;
    }

    public int generateColor()
    {
        int hello=0;
        // create random object - reuse this as often as possible
        Random random = new Random();

        // create a big random number - maximum is ffffff (hex) = 16777215 (dez)
        int nextInt = random.nextInt(256*256*256);

        // format it as hexadecimal string (with hashtag and leading zeros)
        String colorCode = String.format("Ox", nextInt);

        // print it
        System.out.println(colorCode);
        return hello;
    }

}
