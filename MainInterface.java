//*****************************************************************
// MainInterface.java Author: Ravyar Sarbast
//
// The Main class when app start// it control most aspect of the applications
//*****************************************************************

package RavyarSarbastTahir;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.NoSuchElementException;


public class MainInterface extends CenteFrame {
    private final JPanel mainPanel;
    private final MainMenu mainMenuP;
    private final LoginPanel loginPanel;
    private final BorderLayout borderLayout;
    private MenuBar menuBar;
    private DefaultTableModel model, modelC;
    private JTable westT, details;
    private DBConnector dbConnector;
    private String selectedTable, selectedCatagory;
// Declaring this instance variable here so I can access them when ever I want
    public MainInterface() throws HeadlessException {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {// confirmation for exiting the app
                int confirm = JOptionPane.showConfirmDialog(MainInterface.this, "are you sure you want to close");
                if (confirm == JOptionPane.YES_OPTION) {
                    dispose();
                }

            }

        });


        mainMenuP = new MainMenu();
        loginPanel = new LoginPanel();
        loginPanel.getLogin().addActionListener(action());
        loginPanel.username.addActionListener(action());
        loginPanel.password.addActionListener(action());
        Container container = getContentPane();
        mainPanel = new JPanel();
        container.add(mainPanel);
        borderLayout = new BorderLayout();
        mainPanel.setLayout(borderLayout);

        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));
        bottom.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(""), BorderFactory.createLineBorder(Color.BLACK, 0)));
        JLabel copyrightlabel = new JLabel("All Right Reserved for KUST  ");

        bottom.add(Box.createHorizontalGlue());
        bottom.add(copyrightlabel);

        bottom.add(Box.createHorizontalGlue());
        container.add(bottom, BorderLayout.SOUTH);
        boolean close = false;// here I check for the connection to the data base
        try {
            dbConnector = new DBConnector();
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(MainInterface.this,"Cant find MariaDB Driver");// if it cant find driver  it will show you this message
            close= true;
            System.exit(0);

        }
        catch (Exception e){

            sqllogin();// if it find the driver but can not connect to the database it will open sql menu so you can enter database login detail
            close= true;
        }
        if(close){
            dispose();
        }else{

        login();}// if database connection succeed it will open login menu


    }

    public static void main(String[] args) {

        MainInterface mainInterface = new MainInterface();
        mainInterface.setVisible(true);
        mainInterface.setTitle("Chain Management System");
        mainInterface.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);// I setup confirm delete I setup close button to do nothing
        //this is where I main object of the app

    }

    void transit() {
        cleanscreenn();
        tables();
        mainPanel.remove(borderLayout.getLayoutComponent(BorderLayout.WEST));
        String[] header = {"#", "From", "to", "Departure", "Arrival"};
        modelC = new DefaultTableModel(header, 0);
        details.setModel(modelC);
        super.centerScreen();
        dbConnector.readTranset(modelC);

    }

    void allstatic() {
        cleanscreenn();
        tables();
        mainPanel.remove(borderLayout.getLayoutComponent(BorderLayout.WEST));
        String[] header = {"Name", "Count", "Place Name", "Part"};
        modelC = new DefaultTableModel(header, 0);
        details.setModel(modelC);
        super.centerScreen();
        dbConnector.stastricsall(modelC);

    }


    void shippmentP(JButton addData, int part) {// this is shipment page it change the table to shipment, I take the
        // button as parameter so I can modify it action listener
        String[] header = {"#", "Shipment", "Shipping Date", "Arrival Date"};
        modelC = new DefaultTableModel(header, 0);
        details.setModel(modelC);
        try{addData.removeActionListener(addData.getActionListeners()[0]);// removing older action
            }
        catch (Exception e){

        }

        addData.addActionListener(new ActionListener() {//adding new action
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (selectedTable != null) {//adding new shipment
                    AddShipment addShipment = new AddShipment(0, 0, MainInterface.this);

                    addShipment.adds.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");//the date format to show the user

                            String shipmentformer = formater.format(addShipment.shippmentdate.getValue());

                            String arrivalfromaeter = formater.format(addShipment.arrivaltime.getValue());
                            dbConnector.createShipment(addShipment.name.getText(), shipmentformer, arrivalfromaeter, selectedTable, modelC, MainInterface.this, addShipment,part);// create a Shipment
                        }
                    });


                } else {
                    JOptionPane.showMessageDialog(MainInterface.this, "Please Select a Place");// this message show if the user try to add data without selecting the place
                }
            }
        });
        try{
            dbConnector.readShipment(modelC, selectedTable, part);
        }
        catch (Exception e) {

        }



    }


    void productsP(JButton addData, int part) {

        String[] header = {"#", "Name", "Manufacturing Price", "SellingPrice", "Manufacturing Date"};
        modelC = new DefaultTableModel(header, 0);
        details.setModel(modelC);
        addData.removeActionListener(addData.getActionListeners()[0]);

        addData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (selectedTable != null) {
                    AddProduct addProduct = new AddProduct(0, 0, MainInterface.this);
                    addProduct.adds.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
                            String manDatefo = formater.format(addProduct.manDate.getValue());
                            dbConnector.createProduct(addProduct.name.getText(), Double.parseDouble(addProduct.mPrice.getText()), Double.valueOf(addProduct.sPrice.getText()), manDatefo, selectedTable, modelC, MainInterface.this, addProduct,part);
                        }
                    });


                } else {
                    JOptionPane.showMessageDialog(MainInterface.this, "Please Select a place");
                }
            }
        });

        try{
            dbConnector.readProduct(modelC, selectedTable, part);

        }catch (Exception e){

        }


    }

    void statstics(int part) {
        String[] header = {"Name", "Detail"};
        modelC = new DefaultTableModel(header, 0);
        details.setModel(modelC);
        dbConnector.stastrics(modelC, part);
    }

    void statsticsS(int part) {
        String[] header = {"Name", "Detail"};
        modelC = new DefaultTableModel(header, 0);
        details.setModel(modelC);
        dbConnector.stastricsS(modelC, part);
    }


    void mainPart(int part) {
        selectedCatagory = "em";// when open any part of the app it will come here first location usually is employee
        cleanscreenn();// this will remove all JPanels open on main Panel
        tables();// this will load west table and center table
        JPanel east = new JPanel();// East panel for the bottun
        east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));// box layout so button will be in order
        JPanel eastT = new JPanel();// panel for the button this layout so  I can have spacing and order of the button the way I want
        eastT.setLayout(new GridLayout(5, 1, 0, 0));
        JButton addDataf = new JButton("Add Place");
        JButton addData = new JButton("Add Data");
        JButton employee = new JButton("Employees");
        JButton product = new JButton("Products");
        JButton statistic = new JButton("Statistics");

        eastT.add(addDataf);
        eastT.add(addData);
        eastT.add(employee);
        eastT.add(product);

        eastT.add(statistic);
        east.add(eastT);
        east.add(Box.createRigidArea(new Dimension(0, 400)));
        east.add(Box.createVerticalGlue());
        model.setRowCount(0);
        String[] header = {"#", "Name", "Job", "Salary"};
        modelC = new DefaultTableModel(header, 0);
        details.setModel(modelC);
        mainPanel.add(east, BorderLayout.EAST);
        super.centerScreen();// to resize the app and center it on screen
        dbConnector.readPlaces(model, part);// this will load the places from the database and it use part for differ the places its factory or other


        details.addMouseListener(new MouseAdapter() {// this will open mouse listener on main table in the center
            @Override
            public void mouseReleased(MouseEvent e) {
                RightClickWestPanel r = new RightClickWestPanel();// this is pop up menu when user click on item on the table in center on screen
                r.delete.addActionListener(new ActionListener() {// when user click on delete
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        if (selectedCatagory.equalsIgnoreCase("em")) {// if user on employee it will delete employee
                            dbConnector.deleteEmployee(MainInterface.this, details);// delete selected user from
                        } else if (selectedCatagory.equalsIgnoreCase("pr")) {// this will delete from product
                            dbConnector.deleteProduct(MainInterface.this, details);
                        } else {// this one will delete from shipment
                            dbConnector.deleteShip(MainInterface.this, details);
                        }
                    }
                });
                r.edit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        if (selectedCatagory.equalsIgnoreCase("em")) {// this will read selected data from employee select it by user
                            AddEmployee addEmployee = new AddEmployee(0, 0, MainInterface.this);// and it the user change data
                            int text = Integer.parseInt((String) modelC.getValueAt(details.getSelectedRow(), 0));
                            String name = (String) modelC.getValueAt(details.getSelectedRow(), 1);
                            String job = (String) modelC.getValueAt(details.getSelectedRow(), 2);
                            String salary = String.valueOf(modelC.getValueAt(details.getSelectedRow(), 3));
                            addEmployee.name.setText(name);
                            addEmployee.job.setText(job);
                            addEmployee.salary.setText(salary);
                            addEmployee.adds.setText("Update");
                            addEmployee.adds.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent actionEvent) {
                                    long salaryt = 0l;
                                    int salary = 0;
                                    try {
                                        salaryt = (Long) NumberFormat.getNumberInstance(Locale.UK).parse(addEmployee.salary.getText());
                                        salary = Math.toIntExact(salaryt);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    String name = addEmployee.name.getText();
                                    String job = addEmployee.job.getText();

                                    dbConnector.editEmployee(text, name, job, salary, selectedTable, modelC, MainInterface.this, addEmployee, details.getSelectedRow());
                                }
                            });


                        } else if (selectedCatagory.equalsIgnoreCase("pr")) {
                            AddProduct addProduct = new AddProduct(0, 0, MainInterface.this);
                            int text = Integer.parseInt((String) modelC.getValueAt(details.getSelectedRow(), 0));
                            String name = (String) modelC.getValueAt(details.getSelectedRow(), 1);
                            String manprice = String.valueOf(modelC.getValueAt(details.getSelectedRow(), 2));
                            String selprice = String.valueOf(modelC.getValueAt(details.getSelectedRow(), 3));
                            String expire = (String) modelC.getValueAt(details.getSelectedRow(), 4);
                            Date date1 = null;
                            try {
                                date1 = new SimpleDateFormat("yyyy-MM-dd").parse(expire);
                            } catch (ParseException parseException) {
                                try{
                                    date1 = new SimpleDateFormat("yyyy/MM/dd").parse(expire);
                                }
                                catch (Exception e){

                                }
                            }
                            addProduct.name.setText(name);
                            addProduct.mPrice.setText(String.valueOf(manprice));
                            addProduct.sPrice.setText(String.valueOf(selprice));
                            addProduct.manDate.setValue(date1);

                            addProduct.adds.setText("Update");
                            addProduct.adds.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent actionEvent) {
                                    String name = addProduct.name.getText();
                                    double manprice = Double.parseDouble(addProduct.mPrice.getText());
                                    double selprice = Double.parseDouble(addProduct.sPrice.getText());
                                    SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
                                    String manDatefo = formater.format(addProduct.manDate.getValue());

                                    dbConnector.editProduct(text, name, manprice, selprice, manDatefo, selectedTable, modelC, MainInterface.this, addProduct, details.getSelectedRow());

                                }
                            });


                        } else {
                            AddShipment addShipment = new AddShipment(0, 0, MainInterface.this);
                            int text = Integer.parseInt((String) modelC.getValueAt(details.getSelectedRow(), 0));
                            String shipment = (String) modelC.getValueAt(details.getSelectedRow(), 1);
                            String shippingDate = (String) modelC.getValueAt(details.getSelectedRow(), 2);
                            String arrivalTime = (String) modelC.getValueAt(details.getSelectedRow(), 3);


                            Date date1 = null;
                            try {
                                date1 = new SimpleDateFormat("yyyy-MM-dd").parse(shippingDate);
                            } catch (ParseException parseException) {
                                //parseException.printStackTrace();
                                try{
                                    date1 = new SimpleDateFormat("yyyy/MM/dd").parse(shippingDate);
                                }
                                catch (Exception e){

                                }
                            }
                            Date date2 = null;
                            try {
                                date2 = new SimpleDateFormat("yyyy-MM-dd").parse(arrivalTime);
                            } catch (ParseException parseException) {
                                try{
                                    date2 = new SimpleDateFormat("yyyy/MM/dd").parse(arrivalTime);
                                }
                                catch (Exception e){

                                }
                            }


                            addShipment.placeholderName.setText("Shipment");
                            addShipment.name.setText(shipment);
                            addShipment.arrivaltime.setValue(date1);
                            addShipment.shippmentdate.setValue(date2);

                            addShipment.adds.setText("Update");
                            addShipment.adds.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent actionEvent) {
                                    String name = addShipment.name.getText();
                                    SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
                                    String ship = formater.format(addShipment.shippmentdate.getValue());
                                    String arrive = formater.format(addShipment.arrivaltime.getValue());


                                    dbConnector.editShipment(text, name, ship, arrive, selectedTable, modelC, MainInterface.this, addShipment, details.getSelectedRow());

                                }
                            });


                        }
                    }
                });
                if (details.getSelectedRow() > -1 && e.getButton() == MouseEvent.BUTTON3) {

                    r.show(westT, e.getX(), e.getY());


                }
            }
        });

        westT.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                RightClickWestPanel r = new RightClickWestPanel();// this is pop up menu on west table
                r.delete.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        dbConnector.deletePlaces(MainInterface.this, westT, part);
                    }
                });// this will delete selected item, how ever due mysql constrain it will not delete a place if there is data in it
                r.edit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        AddPlace addPlace = new AddPlace(e.getX(), e.getY(), MainInterface.this);
                        addPlace.adds.setText("Update");
                        String text = (String) model.getValueAt(westT.getSelectedRow(), westT.getSelectedColumn());
                        addPlace.factoryName.setText(text);
                        addPlace.setTitle(text);
                        addPlace.adds.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                dbConnector.editPlaces(addPlace.factoryName.getText().trim(), text, westT.getSelectedRow(), model, MainInterface.this, addPlace, part);

                            }
                        });
                    }
                });//
                int selected = westT.getSelectedRow();
                selectedTable = (String) model.getValueAt(westT.getSelectedRow(), westT.getSelectedColumn());
                if (selectedCatagory.equalsIgnoreCase("em")) {
                    dbConnector.readEmployee(modelC, selectedTable, part);
                } else if (selectedCatagory.equalsIgnoreCase("pr")) {
                    dbConnector.readProduct(modelC, selectedTable, part);
                } else {
                    dbConnector.readShipment(modelC, selectedTable, part);
                }
                if (e.getButton() == MouseEvent.BUTTON3 && selected > -1) {

                    r.show(westT, e.getX(), e.getY());
                }
            }
        });


        addDataf.addActionListener(new ActionListener() {// this will open window to insert place
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                int x = MainInterface.this.getSize().width / 4;
                int y = MainInterface.this.getSize().height / 4;
                AddPlace addPlace = new AddPlace(x, y, MainInterface.this);
                addPlace.adds.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        dbConnector.createPlace(addPlace.factoryName.getText().trim(), model, MainInterface.this, addPlace, part);

                    }
                });
            }
        });
        if (part == 2 || part == 4) {// stores and supplier have shipment instead of employee
            selectedCatagory = "sh";// select catagory as shipment
            employee.setText("Shipment");
            shippmentP(addData, part);// loading shipment table headers
            employee.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {

                    shippmentP(addData, part);// to load shipment when user click on the shipment button


                }
            });
            statistic.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    statsticsS(part);
                        //JOptionPane.showMessageDialog(MainInterface.this,"Coming soon in upcoming versions");
                }
            });
        } else { try{
            dbConnector.readEmployee(modelC, selectedTable, part);// read user data used try and catch to avoid if there is no data or nothing selected
        }
        catch (Exception e){

        }
            employee.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    mainPart(part);
                }
            });
            statistic.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    statstics(part);
                    //JOptionPane.showMessageDialog(MainInterface.this,"Coming soon in upcoming versions");
                }
            });
        }
        product.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                selectedCatagory = "pr";// to load factory when user click on factory

                // dbConnector.readFactoryEmThread(modelC,selectedTable,part);
                productsP(addData, part);
            }
        });

        addData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (selectedTable != null) {

                    AddEmployee addEmployee = new AddEmployee(0, 0, MainInterface.this);
                    addEmployee.adds.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {// adding employee but making user place is selected
                            String name = addEmployee.name.getText();
                            String job = addEmployee.job.getText();
                            Long salaryt = 0l;
                            int salary = 0;
                            try {
                                salaryt = (Long) NumberFormat.getNumberInstance(Locale.UK).parse(addEmployee.salary.getText());
                                salary = Math.toIntExact(salaryt);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                            dbConnector.createEmployee(name, job, salary, selectedTable, modelC, MainInterface.this, addEmployee,part);

                        }
                    });


                } else {
                    JOptionPane.showMessageDialog(MainInterface.this, "Please Select a place");
                }
            }


        });


    }

    void tables() {// creating tables so I only write this once and use it when I need it
        CompoundBorder myBorder = BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(""), BorderFactory.createLineBorder(Color.BLACK, 0));

        JPanel west = new JPanel();
        String[] columnNames = {"Name"};
        model = new DefaultTableModel(columnNames, 0);

        westT = new JTable(model);
        westT.setTableHeader(null);
        westT.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        westT.setDefaultEditor(Object.class, null);
        west.setLayout(new BoxLayout(west,BoxLayout.X_AXIS));
        west.setPreferredSize(new Dimension(120,west.getHeight()));
        JScrollPane scrollPane = new JScrollPane(westT,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        west.add(scrollPane);// adding scroll pane so I have more data user can scroll down the data


        mainPanel.add(west, BorderLayout.WEST);
        JPanel center = new JPanel();
        center.setLayout(new BorderLayout());
        details = new JTable();

        center.add(details.getTableHeader(), BorderLayout.NORTH);
        JScrollPane scrollPane1 = new JScrollPane(details,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        center.setLayout(new BoxLayout(center,BoxLayout.X_AXIS));

        center.add(scrollPane1, BorderLayout.CENTER);
        mainPanel.add(center, BorderLayout.CENTER);
        west.setBorder(myBorder);
        center.setBorder(myBorder);
        getContentPane().revalidate();

    }

    void mainMenu() {
        cleanscreenn();

        mainPanel.add(mainMenuP);
        menuBar = new MenuBar();
        setJMenuBar(menuBar);

        super.pack();
        setLocationRelativeTo(null);
        ActionListener openF = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainPart(1);// this open factories
            }
        };
        ActionListener openM = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainMenu();// open Main Menu
            }
        };
        ActionListener openS = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainPart(2);// Open Stores
            }
        };
        ActionListener openW = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainPart(3);// Open Warehouses
            }
        };
        ActionListener openT = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                transit(); // open transit
            }
        };
        ActionListener openSP = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainPart(4); //Open supplier
            }
        };
        ActionListener openSS = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                allstatic(); // open statistics

            }
        };
        menuBar.factories.addActionListener(openF);
        mainMenuP.getFactories().addActionListener(openF);
        mainMenuP.getStores().addActionListener(openS);
        menuBar.stores.addActionListener(openS);
        menuBar.mainmenu.addActionListener(openM);
        mainMenuP.getWarehouse().addActionListener(openW);
        menuBar.warehouse.addActionListener(openW);
        mainMenuP.getTransit().addActionListener(openT);
        menuBar.transit.addActionListener(openT);
        mainMenuP.getSupplier().addActionListener(openSP);
        menuBar.supplier.addActionListener(openSP);
        mainMenuP.getStatistics().addActionListener(openSS);
        menuBar.statistics.addActionListener(openSS);
        menuBar.logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                login();
            }
        });
        menuBar.aboutme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(MainInterface.this, "This software written by \n Ravyar Sarbast Tahir");
            }
        });
    }

    void cleanscreenn() throws NoSuchElementException {// this method will remove all panel from main panel
        try {
            mainPanel.remove(borderLayout.getLayoutComponent(BorderLayout.CENTER));
            mainPanel.remove(borderLayout.getLayoutComponent(BorderLayout.WEST));
            mainPanel.remove(borderLayout.getLayoutComponent(BorderLayout.EAST));
        } catch (Exception e) {

        }
    }

    boolean checkpassword(JTextField username, JPasswordField password) {// this method check for user login if it true it will return true to where it invoked from

        return dbConnector.login(username.getText().strip(), (String.valueOf(password.getPassword())).strip());

    }

    void sqllogin(){
        SQLScreen sqlScreen = new SQLScreen(MainInterface.this);
        setJMenuBar(null);
        cleanscreenn();


        mainPanel.add(sqlScreen, BorderLayout.CENTER);

        super.pack();// this will resize the application to match my elements on screen
        setLocationRelativeTo(null);// to stay in the middle
    }
    ActionListener action(){
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (checkpassword(loginPanel.username, loginPanel.password)) {
                    loginPanel.username.setText("");
                    loginPanel.password.setText("");
                    mainMenu();// if the password is correct it will open main menu of the app
                } else {
                    JOptionPane.showMessageDialog(MainInterface.this, "Either username or password is wrong");  // let user know either username or password is wrong
                }
            }
        };
        return actionListener;
    }
    void login() {

        setJMenuBar(null);
        cleanscreenn();


        mainPanel.add(loginPanel, BorderLayout.CENTER);

        super.pack();
        setLocationRelativeTo(null);



    }
}
