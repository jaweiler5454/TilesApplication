package com.weiler.tiles;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;

/**
 * Created by jaweiler5454 on 5/19/18.
 */
public class GlobalToolbar {

    public GlobalToolbar()
    {

    }

    public Toolbar toolBar(String title)
    {
        Toolbar tb = new Toolbar();;
        Container topBar = BorderLayout.east(new Label("HELLO"));
        topBar.add(BorderLayout.SOUTH, new Label("Cool App Tagline...", "SidemenuTagline"));
        topBar.setUIID("SideCommand");
        tb.addComponentToSideMenu(topBar);


        tb.addMaterialCommandToSideMenu("Home", FontImage.MATERIAL_HOME, e -> {});
        tb.addMaterialCommandToSideMenu("Website", FontImage.MATERIAL_WEB, e -> {});
        tb.addMaterialCommandToSideMenu("Settings", FontImage.MATERIAL_SETTINGS, e -> {});
        tb.addMaterialCommandToSideMenu("About", FontImage.MATERIAL_INFO, e -> {});
        return toolBar(title);
    }
}
