//*****************************************************************
// ThreeCellInsert.java Author: Ravyar Sarbast
//
// its for when I need three inputs
//*****************************************************************

package RavyarSarbastTahir;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;

public class AddProduct extends CenteFrame {
    JTextField name, mPrice, sPrice;
    TextPrompt placeholderName, placeholderJob, placeHolderSalary;
    JButton adds;
    JPanel fields;
    JSpinner manDate;

    public AddProduct(int x, int y, JFrame frame) throws HeadlessException {

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
        placeholderName = new TextPrompt("Name", name);
        NumberFormat numberFormat = NumberFormat.getIntegerInstance();
        numberFormat.setGroupingUsed(false);
        mPrice = new JFormattedTextField(numberFormat);
        sPrice = new JFormattedTextField(numberFormat);
        placeHolderSalary = new TextPrompt("SellingPrice", sPrice);
        placeholderJob = new TextPrompt("Manufacturing Price", mPrice);

        Date date1 = new Date();
        SpinnerDateModel dobmodel = new SpinnerDateModel(date1, null, null, Calendar.YEAR);
        dobmodel.setCalendarField(1);
        manDate = new JSpinner(dobmodel);
        JSpinner.DateEditor ded = new JSpinner.DateEditor(manDate, "dd/MM/yyyy");
        manDate.setEditor(ded);


        adds = new JButton("Add");


        fields.add(name);
        fields.add(mPrice);
        fields.add(sPrice);
        fields.add(manDate);

        main.add(fields);
        main.add(adds);


        setMinimumSize(new Dimension(600, 100));
        //pack();

    }
}
