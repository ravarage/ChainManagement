//*****************************************************************
// MenuBar.java Author: Ravyar Sarbast
//
// The Menu bar of the App when we have few extra options
//*****************************************************************

package RavyarSarbastTahir;

import javax.swing.*;

public class MenuBar extends JMenuBar {
    JMenuItem logout, factories, stores, warehouse, transit, supplier, statistics, mainmenu, aboutme;

    public MenuBar() {


        JMenu user = new JMenu("User");
        JMenu action = new JMenu("Action");
        JMenu about = new JMenu("About");


        logout = new JMenuItem("Logout");
        mainmenu = new JMenuItem("MainMenu");
        factories = new JMenuItem("Factories");
        stores = new JMenuItem("Stores");
        warehouse = new JMenuItem("Warehouses");
        transit = new JMenuItem("Transits");
        supplier = new JMenuItem("Supplier");
        statistics = new JMenuItem("Statistics");
        aboutme = new JMenuItem("about me");


        user.add(logout);
        action.add(mainmenu);
        action.add(factories);
        action.add(stores);
        action.add(warehouse);
        action.add(transit);
        action.add(supplier);
        action.add(statistics);
        about.add(aboutme);


        add(user);
        add(action);
        add(about);

    }
}
