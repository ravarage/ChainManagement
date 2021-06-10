//*****************************************************************
// CenteFrame.java Author: Ravyar Sarbast
//
// The Center Frame where we put all of widgets in it
//*****************************************************************

package RavyarSarbastTahir;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.*;

public class CenteFrame extends JFrame {
    public CenteFrame() throws HeadlessException {

        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {// setting layout to nimbus as it look great
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    UIManager.put("nimbusBase", Color.white);// setting global setting instead of item by item
                    // it look greater and the app will have uniform look
                    UIManager.put("control", Color.white);


                    UIManager.put("MenuItem[MouseOver].textForeground", Color.BLUE);
                    UIManager.put("MenuBar:Menu[Selected].textForeground", Color.lightGray);


                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }


    }

    public void centerScreen() { // normal way to center app on screen
        Toolkit tookkit = getToolkit();

        Dimension dimensionOfScreen = tookkit.getScreenSize();
        setSize(dimensionOfScreen.width / 2, dimensionOfScreen.height / 2);//we setup the frame to be half of screen size
        setLocation(new Point(dimensionOfScreen.width / 4, dimensionOfScreen.height / 4));// we setup the frame to be in middle of screen

    }

    public void centerScreen(int x, int y, JFrame oldframe) {// for smaller window that I create later in the app
        Toolkit tookkit = getToolkit();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Dimension dimensionOfScreen = tookkit.getScreenSize();
        setSize(dimensionOfScreen.width / 6, dimensionOfScreen.height / 6);//we setup the frame to be half of screen size
        x = (oldframe.getLocation().x) + x;
        y = (oldframe.getLocation().y) + y;
        setLocation(new Point(x, y));// we setup the frame to be in middle of screen

    }

}