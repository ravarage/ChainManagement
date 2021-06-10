//*****************************************************************
// ThreeCellInsert.java Author: Ravyar Sarbast
//
// its for when I need three inputs
//*****************************************************************

package RavyarSarbastTahir;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;

public class AddEmployee extends CenteFrame {
    JTextField name, job, salary;
    TextPrompt placeholderName, placeholderJob, placeHolderSalary;
    JButton adds;
    JPanel fields;

    public AddEmployee(int x, int y, JFrame frame) throws HeadlessException {

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
        job = new JTextField(10);
        placeholderJob = new TextPrompt("Job", job);
        salary = new JFormattedTextField(NumberFormat.getNumberInstance());
        placeHolderSalary = new TextPrompt("Salary", salary);

        adds = new JButton("Add");

        fields.add(name);
        fields.add(job);
        fields.add(salary);


        main.add(fields);
        main.add(adds);
        setMinimumSize(new Dimension(600, 100));
        //pack();

    }
}
