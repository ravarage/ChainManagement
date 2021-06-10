//*****************************************************************
// MainMenu.java Author: Ravyar Sarbast
//
// Jpanel that contain main menu panel
//*****************************************************************

package RavyarSarbastTahir;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;

public class MainMenu extends JPanel {
    private final JButton factories;
    private final JButton stores;
    private final JButton warehouse;
    private final JButton transit;
    private final JButton supplier;
    private final JButton statistics;


    public MainMenu() {

        setLayout(new GridLayout(3, 2, 10, 10));
        CompoundBorder myBorder = BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(""), BorderFactory.createLineBorder(Color.BLACK, 0));
        setBorder(myBorder);

        factories = new JButton("Factories");
        stores = new JButton("Stores");

        warehouse = new JButton("Warehouses");

        transit = new JButton("Transits");

        supplier = new JButton("Suppliers");

        statistics = new JButton("Statistics");


        add(factories);
        add(stores);
        add(warehouse);
        add(transit);
        add(supplier);
        add(statistics);


    }

    public JButton getFactories() {
        return factories;
    }

    public JButton getStores() {
        return stores;
    }

    public JButton getWarehouse() {
        return warehouse;
    }

    public JButton getTransit() {
        return transit;
    }

    public JButton getSupplier() {
        return supplier;
    }

    public JButton getStatistics() {
        return statistics;
    }
}
