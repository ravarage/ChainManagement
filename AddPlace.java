//*****************************************************************
// OneCellInsert.java Author: Ravyar Sarbast
//
// for the places in the app when I need one input to get data from user
//*****************************************************************

package RavyarSarbastTahir;

import javax.swing.*;
import java.awt.*;

public class AddPlace extends CenteFrame {
    JTextField factoryName;
    TextPrompt placeholderID;
    JButton adds;

    public AddPlace(int x, int y, JFrame frame) throws HeadlessException {

        centerScreen(x, y, frame);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        Container c = getContentPane();
        JPanel main = new JPanel();
        c.add(main);

        main.setLayout(new GridLayout(2, 1));

        factoryName = new JTextField(10);
        placeholderID = new TextPrompt("Factory Name", factoryName);
        adds = new JButton("Add");
        main.add(factoryName);
        main.add(adds);

        // pack();
        setMinimumSize(new Dimension(200, 100));

    }
}
