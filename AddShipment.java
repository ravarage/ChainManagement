//*****************************************************************
// ThreeCellInsert.java Author: Ravyar Sarbast
//
// its for when I need three inputs
//*****************************************************************

package RavyarSarbastTahir;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;

public class AddShipment extends CenteFrame {
    JTextField name;
    TextPrompt placeholderName;
    JButton adds;
    JPanel fields;
    JSpinner shippmentdate,arrivaltime;

    public AddShipment(int x, int y, JFrame frame) throws HeadlessException {

        centerScreen(x, y, frame);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        Container c = getContentPane();
        JPanel main = new JPanel();
        c.add(main);
        fields = new JPanel();
        fields.setLayout(new BoxLayout(fields, BoxLayout.X_AXIS));


        main.setLayout(new GridLayout(2, 1, 10, 10));

        name = new JTextField(10);
        placeholderName = new TextPrompt("Shipment", name);



        Date date1 = new Date();


        SpinnerDateModel dobmodel = new SpinnerDateModel(date1, null, null, Calendar.YEAR);
        SpinnerDateModel dobmodel2 = new SpinnerDateModel(date1, null, null, Calendar.YEAR);

        dobmodel.setCalendarField(1);
        dobmodel2.setCalendarField(1);

         shippmentdate = new JSpinner(dobmodel);
         arrivaltime = new JSpinner(dobmodel2);

        JSpinner.DateEditor ded = new JSpinner.DateEditor(shippmentdate, "dd/MM/yyyy");
        JSpinner.DateEditor ded2 = new JSpinner.DateEditor(arrivaltime, "dd/MM/yyyy");
        arrivaltime.setEditor(ded2);
        shippmentdate.setEditor(ded);


        adds = new JButton("Add");


        fields.add(name);
        fields.add(new JLabel("Shipment Date"));
        fields.add(shippmentdate);
        fields.add(new JLabel("Arrival Date"));
        fields.add(arrivaltime);

        main.add(fields);
        main.add(adds);


        setMinimumSize(new Dimension(600, 100));
        //pack();

    }
}
