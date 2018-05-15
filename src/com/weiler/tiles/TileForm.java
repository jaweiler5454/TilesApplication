package com.weiler.tiles;

/**
 * Created by jaweiler5454 on 5/2/18.
 */
import com.codename1.io.URL;
import com.codename1.ui.*;

import com.codename1.ui.table.TableLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import com.codename1.ui.geom.*;
import javax.imageio.ImageIO;


public class TileForm {

    public TileForm()
    {

    }

    private DataBaseHelper globalBase = new DataBaseHelper();
    public int displayWidth = Display.getInstance().getDisplayWidth();
    public int displayHeight = Display.getInstance().getDisplayHeight();

    public Form formCreated()
    {
        ArrayList<Map<String,Object>> tilesByOrg = new ArrayList<Map<String,Object>>();

        ArrayList<Button> tilesButtons = new ArrayList<Button>();


        for(int i=0; i<globalBase.getPosts("milton_academy").size(); i++)
        {
            tilesByOrg.add(globalBase.getPosts("milton_academy").get(i));
            tilesButtons.add(new Button(tilesByOrg.get(i).get("Title").toString()));
            tilesButtons.get(i).setSize(new Dimension(displayWidth/2, displayHeight/3));
            Image image = globalBase.getImage(tilesByOrg.get(i).get("ImageURL").toString(), tilesButtons.get(i).getWidth(), tilesButtons.get(i).getHeight());
            if(image!=null) {
                tilesButtons.get(i).getUnselectedStyle().setBgImage(image);
            }
            tilesButtons.get(i).addActionListener(evt -> {
                //Form.show
            });


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
        TableLayout tl = new TableLayout( 4, 3);
        Form formReturned = new Form("Hello", tl);
        formReturned.setScrollable(true);
        for(int i=0; i<tilesButtons.size(); i++) {
            formReturned.add(tl.createConstraint().widthPercentage(50).heightPercentage(25), tilesButtons.get(i));
        }
        return formReturned;
    }

}
