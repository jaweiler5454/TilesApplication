package com.weiler.tiles;

import com.codename1.ui.Component;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Graphics;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ScrollListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;

/**
 * This Custom Toolbar changes the opacity of the Toolbar background, shifts the
 * title and changes its size upon scroll
 *
 * @author Chen
 */
public class CustomToolbar extends Toolbar implements ScrollListener {

    private int alpha;
    private Container bottomPadding = new Container();
    private Container topPadding = new Container();
    private int minimumHeight;
    private int topHeight;

    private Label title = new Label("SUP");

    public CustomToolbar() {
    }

    public CustomToolbar(boolean layered) {
        super(layered);
    }

    /**
     * Sets the initial height of this toolbar
     */

    @Override
    public void paintComponentBackground(Graphics g) {
        int a = g.getAlpha();
        g.setAlpha(alpha);
        super.paintComponentBackground(g);
        g.setAlpha(a);
    }

    @Override
    public void scrollChanged(int scrollX, int scrollY, int oldscrollX, int oldscrollY) {
        //modify the alpha value
        alpha = scrollY;
        alpha = Math.max(alpha, 0);
        alpha = Math.min(alpha, 255);

        //push the title to the right when the scroll moves up
        int padding = (int) ((float) alpha * (Display.getInstance().getDisplayWidth() * 40 / 100) / (float) 255);

        padding = Math.min((Display.getInstance().getDisplayWidth()) / 2, padding);
        title.getStyle().setPadding(LEFT, padding);

        //change the size of the tollbar
        bottomPadding.setPreferredH(Math.max(0, topHeight - minimumHeight - scrollY));
        topPadding.setPreferredH(bottomPadding.getPreferredH());
        revalidate();
    }

}