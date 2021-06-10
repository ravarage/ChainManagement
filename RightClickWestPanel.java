//*****************************************************************
// RightClickWestPanel.java Author: Ravyar Sarbast
//
// Right click option to edit and delete item
//*****************************************************************

package RavyarSarbastTahir;

import javax.swing.*;

public class RightClickWestPanel extends JPopupMenu {
    JMenuItem delete, edit;

    public RightClickWestPanel() {
        edit = new JMenuItem("Edit");
        delete = new JMenuItem("Delete");

        add(edit);
        add(delete);
    }

}
