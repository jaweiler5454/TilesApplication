package com.weiler.tiles;

/**
 * Created by jaweiler5454 on 5/2/18.
 */
import com.codename1.ui.*;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import java.util.ArrayList;


public class TileForm {

    public TileForm()
    {

    }

    public Form formCreated()
    {
        Form formReturned = new Form();
        ArrayList tileIDsByOrg = new ArrayList();
        ArrayList<String> textOfTile = new ArrayList<String>();
        ArrayList<TextField> titleOfTile = new ArrayList<TextField>();
        for(int i=0; i<titleOfTile.size(); i++)
        {

        }
        ArrayList<Button> tileButtons = new ArrayList<Button>();
        for(int i=0; i<tileIDsByOrg.size(); i++) {
        }
;       formReturned.setLayout(new GridLayout(tileIDsByOrg.size() , 2)); //Create GridLayout
        return formReturned;
    }
}
