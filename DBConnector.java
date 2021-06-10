//*****************************************************************
// DBConnetor.java Author: Ravyar Sarbast
//
// A connection between database and the application
// I used concurrency on the database so my application wont stop responding on slow connection
//*****************************************************************

package RavyarSarbastTahir;

import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class DBConnector  {
    Connection connection;


    public DBConnector() throws SQLException, ClassNotFoundException, ParserConfigurationException, SAXException, IOException {
        this.connection = new DBConnection().getConncetion();
    }

    private void commit() {
        try {
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }// in order to type commit instead of whole code, since auto commit is off I have to commit the changes

    private void rollback() {

        try {
            connection.rollback();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }// roll back is like commit but it roll back changes when error happen

    void readPlaces(DefaultTableModel model, int part) {
        String[] row = new String[1];
        Collection<Thread> ts = new ArrayList<Thread>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String query = "SELECT placeName  FROM place WHERE part = ? ";
                try {

                    PreparedStatement statement1 = connection.prepareStatement(query);

                    statement1.setInt(1, part);
                    ResultSet resultSet = statement1.executeQuery();


                    while (resultSet.next()) {
                        row[0] = resultSet.getString("placeName");

                        model.addRow(row);


                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
        ts.add(t);
        t.start();


    }// it read the places

    void deletePlaces(JFrame main, JTable myTable, int part) {
        Collection<Thread> ts = new ArrayList<Thread>();
        int selected = myTable.getSelectedRow();
        if (selected == -1) {
            JOptionPane.showMessageDialog(main, "Nothing is selected");
        } else {

            int confirm = JOptionPane.showConfirmDialog(main, "are you sure you to delete " + myTable.getValueAt(selected, 0));
            if (confirm == JOptionPane.YES_OPTION) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (deletePlaces(myTable, part)) {
                            ((DefaultTableModel) myTable.getModel()).removeRow(selected);
                        } else {
                            JOptionPane.showMessageDialog(main, "Error Occurred");
                        }
                    }
                });
                ts.add(t);
                t.start();
            }
        }

    }// it will delete the places

    void deleteEmployee(JFrame main, JTable myTable) {
        Collection<Thread> ts = new ArrayList<Thread>();
        int selected = myTable.getSelectedRow();
        if (selected == -1) {
            JOptionPane.showMessageDialog(main, "Nothing is selected");
        } else {

            int confirm = JOptionPane.showConfirmDialog(main, "are you sure you to delete " + myTable.getValueAt(selected, 1));
            if (confirm == JOptionPane.YES_OPTION) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (deleteEmployee(myTable)) {
                            ((DefaultTableModel) myTable.getModel()).removeRow(selected);
                        } else {
                            JOptionPane.showMessageDialog(main, "Error Occurred");
                        }
                    }
                });
                ts.add(t);
                t.start();
            }
        }

    }// delete employee concurrncy used for all database connection

    void deleteProduct(JFrame main, JTable myTable) {
        Collection<Thread> ts = new ArrayList<Thread>();
        int selected = myTable.getSelectedRow();
        if (selected == -1) {
            JOptionPane.showMessageDialog(main, "Nothing is selected");
        } else {

            int confirm = JOptionPane.showConfirmDialog(main, "are you sure you to delete " + myTable.getValueAt(selected, 1));
            if (confirm == JOptionPane.YES_OPTION) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (deleteProduct(myTable)) {
                            ((DefaultTableModel) myTable.getModel()).removeRow(selected);
                        } else {
                            JOptionPane.showMessageDialog(main, "Error Occurred");
                        }
                    }
                });
                ts.add(t);
                t.start();
            }
        }

    }

    void deleteShip(JFrame main, JTable myTable) {
        Collection<Thread> ts = new ArrayList<Thread>();
        int selected = myTable.getSelectedRow();
        if (selected == -1) {
            JOptionPane.showMessageDialog(main, "Nothing is selected");
        } else {

            int confirm = JOptionPane.showConfirmDialog(main, "are you sure you to delete " + myTable.getValueAt(selected, 1));
            if (confirm == JOptionPane.YES_OPTION) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (deleteShip(myTable)) {
                            ((DefaultTableModel) myTable.getModel()).removeRow(selected);
                        } else {
                            JOptionPane.showMessageDialog(main, "Error Occurred");
                        }
                    }
                });
                ts.add(t);
                t.start();
            }
        }

    }

    void createPlace(String name, DefaultTableModel model, JFrame main, JFrame frame, int part) {

        Collection<Thread> ts = new ArrayList<Thread>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                int finished = createPlace(name, part);
                if (finished == 1) {
                    String[] insertdata = {name};
                    model.addRow(insertdata);
                    commit();
                    JOptionPane.showMessageDialog(main, "Data inserted successfully");
                    frame.dispose();
                } else if (finished == 3) {
                    JOptionPane.showMessageDialog(main, "The Place is Already there");
                    frame.toFront();
                } else if (finished == 4) {
                    JOptionPane.showMessageDialog(main, "The Place is Already there");
                    frame.toFront();
                } else {
                    JOptionPane.showMessageDialog(main, "Something went wrong");
                    frame.toFront();
                }
            }
        });
        ts.add(t);
        t.start();
    }

    void editPlaces(String newName, String oldName, int row, DefaultTableModel model, JFrame main, JFrame frame, int part) {
        Collection<Thread> ts = new ArrayList<Thread>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                int finished = editPlaces(oldName, newName, part);
                if (finished == 1) {

                    model.setValueAt(newName, row, 0);
                    commit();
                    JOptionPane.showMessageDialog(main, "Data Edited successfully");
                    frame.dispose();
                } else if (finished == 3) {
                    JOptionPane.showMessageDialog(main, "The factory is Already there");
                    frame.toFront();
                } else {
                    JOptionPane.showMessageDialog(main, "Something went wrong");
                    frame.toFront();
                }
            }
        });
        ts.add(t);
        t.start();
    }

    int createPlace(String name, int part) {
        if (name.strip() == "" || name == null) {

            return 2;

        }

        String query2 = "select count('1') as 'counter' from place where placeName = ? and part = ? ";// to avoid duplicate
        String query = "INSERT INTO place(placeName,part) VALUES (?,?)";
        try {
            PreparedStatement statement1 = connection.prepareStatement(query);
            PreparedStatement statement2 = connection.prepareStatement(query2);

            statement1.setString(1, name);
            statement1.setInt(2, part);
            statement2.setString(1, name);
            statement2.setInt(2, part);
            ResultSet resultSet2 = statement2.executeQuery();
            int count = 0;
            while (resultSet2.next()) {
                count = Integer.parseInt(resultSet2.getString("counter"));
            }
            if (count == 0) {
                statement1.execute();

                statement1.close();
                return 1;
            } else {
                return 4;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            rollback();
            if (throwables.getErrorCode() == 1062) {
                return 3;
            } else {
                return 2;
            }


        }


    }

    boolean deletePlaces(JTable myTable, int part) {
        String data = (String) myTable.getModel().getValueAt(myTable.getSelectedRow(), myTable.getSelectedColumn());
        String dedactquery = "DELETE FROM place WHERE placeName = ?  and part = ?";
        try {
            PreparedStatement statement1 = connection.prepareStatement(dedactquery);
            statement1.setString(1, data);
            statement1.setInt(2, part);

            int rowAffected = statement1.executeUpdate();
            statement1.close();
            if (rowAffected == 1) {
                commit();
                return true;
            } else {
                rollback();
                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();

            rollback();
            return false;
        }
    }

    boolean deleteEmployee(JTable myTable) {

        int fem = Integer.parseInt((String) myTable.getModel().getValueAt(myTable.getSelectedRow(), 0));


        String dedactquery = "DELETE FROM employee WHERE fem = ?";
        try {
            PreparedStatement statement1 = connection.prepareStatement(dedactquery);
            statement1.setInt(1, fem);


            int rowAffected = statement1.executeUpdate();
            statement1.close();
            if (rowAffected == 1) {
                commit();
                return true;
            } else {
                rollback();
                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            rollback();
            return false;
        }
    }

    boolean deleteProduct(JTable myTable) {
        int fpr = Integer.parseInt((String) myTable.getModel().getValueAt(myTable.getSelectedRow(), 0));


        String dedactquery = "DELETE FROM product WHERE fpr = ?";
        try {
            PreparedStatement statement1 = connection.prepareStatement(dedactquery);
            statement1.setInt(1, fpr);


            int rowAffected = statement1.executeUpdate();
            statement1.close();
            if (rowAffected == 1) {
                commit();
                return true;
            } else {
                rollback();
                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            rollback();
            return false;
        }
    }

    boolean deleteShip(JTable myTable) {
        int fpr = Integer.parseInt((String) myTable.getModel().getValueAt(myTable.getSelectedRow(), 0));


        String dedactquery = "DELETE FROM shipment WHERE  sshID = ?";
        try {
            PreparedStatement statement1 = connection.prepareStatement(dedactquery);
            statement1.setInt(1, fpr);


            int rowAffected = statement1.executeUpdate();
            statement1.close();
            if (rowAffected == 1) {
                commit();
                return true;
            } else {
                rollback();
                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            rollback();
            return false;
        }
    }

    int editPlaces(String oldName, String newName, int part) {
        if (newName.strip() == "" || newName == null) {

            return 2;

        }


        String query = "UPDATE place  set placeName = ? where placeName = ? and part = ?";
        try {
            PreparedStatement statement1 = connection.prepareStatement(query);
            statement1.setString(1, newName);
            statement1.setString(2, oldName);
            statement1.setInt(3, part);
            statement1.execute();
            statement1.close();
            return 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            rollback();
            if (throwables.getErrorCode() == 1062) {
                return 3;
            } else {
                return 2;
            }


        }

    }

    void editProduct(int id, String name, double manprice, double sellprice, String date, String cat, DefaultTableModel model, JFrame main, JFrame frame, int row) {

        Collection<Thread> ts = new ArrayList<Thread>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                int finished = editProduct(id, name, manprice, sellprice, date);
                if (finished == 1) {
                    model.setValueAt(name, row, 1);
                    model.setValueAt(manprice, row, 2);
                    model.setValueAt(sellprice, row, 3);
                    model.setValueAt(date, row, 4);


                    commit();
                    JOptionPane.showMessageDialog(main, "Data Updated successfully");
                    frame.dispose();
                } else if (finished == 3) {
                    JOptionPane.showMessageDialog(main, "The Data is Already there");
                    frame.toFront();
                } else {
                    JOptionPane.showMessageDialog(main, "Something went wrong");
                    frame.toFront();
                }
            }
        });
        ts.add(t);
        t.start();
    }

    void editShipment(int id, String shipment, String shippingDate, String arrivalTime, String cat, DefaultTableModel model, JFrame main, JFrame frame, int row) {

        Collection<Thread> ts = new ArrayList<Thread>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                int finished = editShipment(id, shipment, shippingDate, arrivalTime);
                if (finished == 1) {
                    model.setValueAt(shipment, row, 1);
                    model.setValueAt(shippingDate, row, 2);
                    model.setValueAt(arrivalTime, row, 3);



                    commit();
                    JOptionPane.showMessageDialog(main, "Data Updated successfully");
                    frame.dispose();
                } else if (finished == 3) {
                    JOptionPane.showMessageDialog(main, "The Data is Already there");
                    frame.toFront();
                } else {
                    JOptionPane.showMessageDialog(main, "Something went wrong");
                    frame.toFront();
                }
            }
        });
        ts.add(t);
        t.start();
    }

    void editEmployee(int id, String name, String job, int salary, String cat, DefaultTableModel model, JFrame main, JFrame frame, int row) {

        Collection<Thread> ts = new ArrayList<Thread>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                int finished = editEmployee(id, name, job, salary);
                if (finished == 1) {
                    model.setValueAt(name, row, 1);
                    model.setValueAt(job, row, 2);
                    model.setValueAt(salary, row, 3);

                    commit();
                    JOptionPane.showMessageDialog(main, "Data Updated successfully");
                    frame.dispose();
                } else if (finished == 3) {
                    JOptionPane.showMessageDialog(main, "The Data is Already there");
                    frame.toFront();
                } else {
                    JOptionPane.showMessageDialog(main, "Something went wrong");
                    frame.toFront();
                }
            }
        });
        ts.add(t);
        t.start();
    }

    void createEmployee(String name, String job, int salary, String cat, DefaultTableModel model, JFrame main, JFrame frame,int part) {

        Collection<Thread> ts = new ArrayList<Thread>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                int finished = createEmployee(name, job, salary, cat, model,part);
                if (finished == 1) {

                    commit();
                    JOptionPane.showMessageDialog(main, "Data inserted successfully");
                    frame.dispose();
                } else if (finished == 3) {
                    JOptionPane.showMessageDialog(main, "The Data is Already there");
                    frame.toFront();
                } else {
                    JOptionPane.showMessageDialog(main, "Something went wrong");
                    frame.toFront();
                }
            }
        });
        ts.add(t);
        t.start();
    }

    void createProduct(String name, double manPrice, double sellingPrice, String manDate, String cat, DefaultTableModel model, JFrame main, JFrame frame,int part) {

        Collection<Thread> ts = new ArrayList<Thread>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                int finished = createProduct(name, manPrice, sellingPrice, manDate, cat, model,part);
                if (finished == 1) {

                    commit();
                    JOptionPane.showMessageDialog(main, "Data inserted successfully");
                    frame.dispose();
                } else if (finished == 3) {
                    JOptionPane.showMessageDialog(main, "The Data is Already there");
                    frame.toFront();
                } else {
                    JOptionPane.showMessageDialog(main, "Something went wrong");
                    frame.toFront();
                }
            }
        });
        ts.add(t);
        t.start();
    }

    void createShipment(String shipment, String shippingDate, String arrivalTime, String cat, DefaultTableModel model, JFrame main, JFrame frame,int part) {

        Collection<Thread> ts = new ArrayList<Thread>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                int finished = createShipment(shipment, shippingDate, arrivalTime, cat, model,part);
                if (finished == 1) {

                    commit();
                    JOptionPane.showMessageDialog(main, "Data inserted successfully");
                    frame.dispose();
                } else if (finished == 3) {
                    JOptionPane.showMessageDialog(main, "The Data is Already there");
                    frame.toFront();
                } else {
                    JOptionPane.showMessageDialog(main, "Something went wrong");
                    frame.toFront();
                }
            }
        });
        ts.add(t);
        t.start();
    }

    void readEmployee(DefaultTableModel model, String cat, int part) {
        model.setRowCount(0);

        Collection<Thread> ts = new ArrayList<Thread>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int factoryID = 0;
                    String query2 = "SELECT placeID from place where placeName = ? and part = ?";
                    String query = "SELECT fem,name, job, salary FROM employee where placeID = ?";
                    PreparedStatement statement2 = connection.prepareStatement(query2);
                    statement2.setString(1, cat);
                    statement2.setInt(2, part);

                    ResultSet resultSet2 = statement2.executeQuery();
                    while (resultSet2.next()) {
                        factoryID = Integer.parseInt(resultSet2.getString("placeID"));



                    }

                    PreparedStatement statement1 = connection.prepareStatement(query);
                    statement1.setInt(1, factoryID);
                    ResultSet resultSet = statement1.executeQuery();

                    while (resultSet.next()) {
                        String[] row = {resultSet.getString("fem"), resultSet.getString("name"), resultSet.getString("job"), resultSet.getString("salary")};
                        model.addRow(row);


                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
        ts.add(t);
        t.start();

    }

    void readProduct(DefaultTableModel model, String cat,int part) {
        model.setRowCount(0);

        Collection<Thread> ts = new ArrayList<Thread>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int factoryID = 0;
                    String query2 = "SELECT placeID from place where placeName = ? and part = ?";
                    String query = "SELECT fpr, name, manPrice, sellingPrice, DATE_FORMAT(manDate, \"%Y/%m/%d\") as manDate  FROM product where placeID = ?";
                    PreparedStatement statement2 = connection.prepareStatement(query2);
                    statement2.setString(1, cat);
                    statement2.setInt(2, part);

                    ResultSet resultSet2 = statement2.executeQuery();
                    while (resultSet2.next()) {
                        factoryID = Integer.parseInt(resultSet2.getString("placeID"));

                    }

                    PreparedStatement statement1 = connection.prepareStatement(query);
                    statement1.setInt(1, factoryID);
                    ResultSet resultSet = statement1.executeQuery();

                    while (resultSet.next()) {
                        String[] row = {resultSet.getString("fpr"), resultSet.getString("name"), resultSet.getString("manPrice"), resultSet.getString("sellingPrice"), resultSet.getString("manDate")};
                        model.addRow(row);


                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
        ts.add(t);
        t.start();

    }

    void readShipment(DefaultTableModel model, String cat,int part) {
        model.setRowCount(0);

        Collection<Thread> ts = new ArrayList<Thread>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int factoryID = 0;
                    String query2 = "SELECT placeID from place where placeName = ? and part = ?";
                    String query = "SELECT sshID, shipment,  DATE_FORMAT(shippingDate, \"%Y/%m/%d\") as   shippingDate, DATE_FORMAT(arrivalTime, \"%Y/%m/%d\") as   arrivalTime FROM shipment where placeID = ?";
                    PreparedStatement statement2 = connection.prepareStatement(query2);
                    statement2.setString(1, cat);
                    statement2.setInt(2, part);

                    ResultSet resultSet2 = statement2.executeQuery();
                    while (resultSet2.next()) {
                        factoryID = Integer.parseInt(resultSet2.getString("placeID"));

                    }

                    PreparedStatement statement1 = connection.prepareStatement(query);
                    statement1.setInt(1, factoryID);
                    ResultSet resultSet = statement1.executeQuery();

                    while (resultSet.next()) {
                        String[] row = {resultSet.getString("sshID"), resultSet.getString("shipment"), resultSet.getString("shippingDate"), resultSet.getString("arrivalTime")};
                        model.addRow(row);


                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
        ts.add(t);
        t.start();

    }


    void readTranset(DefaultTableModel model) {
        model.setRowCount(0);

        Collection<Thread> ts = new ArrayList<Thread>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int factoryID = 0;

                    String query = "SELECT sshID,if(p.part =2,\"US\",p.placeName) as froms, if(p.part =4,\"US\",p.placeName) as too, shipment,  DATE_FORMAT(shippingDate, \"%Y/%m/%d\") as    shippingDate,  DATE_FORMAT(arrivalTime, \"%Y/%m/%d\") as    arrivalTime FROM shipment LEFT JOIN place p USING (placeID)";


                    PreparedStatement statement1 = connection.prepareStatement(query);

                    ResultSet resultSet = statement1.executeQuery();

                    while (resultSet.next()) {
                        String[] row = {resultSet.getString("sshID"), resultSet.getString("froms"), resultSet.getString("too"), resultSet.getString("shipment"), resultSet.getString("shippingDate"), resultSet.getString("arrivalTime")};
                        model.addRow(row);


                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
        ts.add(t);
        t.start();

    }

    boolean login(String username,String pasword) {
        boolean value = false;
                try {


                    String query = "SELECT  pass FROM login WHERE username = ? ";


                    PreparedStatement statement1 = connection.prepareStatement(query);
                    statement1.setString(1,username);



                    ResultSet resultSet = statement1.executeQuery();

                    while (resultSet.next()) {
                        value = Password.validatePassword(pasword,resultSet.getString("pass"));


                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                }


      return value;


    }

    int createEmployee(String name, String job, int salary, String cat, DefaultTableModel model,int part) {
        int factoryID = 0;
        if (name.strip() == "" || job == null || job.strip() == "" || name == null || salary == 0) {

            return 2;

        }

        String query2 = "SELECT placeID from place where placeName = ? and part = ?";
        String query = "INSERT INTO employee( placeID, name, job, salary) VALUES (?,?,?,?)";
        try {

            PreparedStatement statement2 = connection.prepareStatement(query2);
            statement2.setString(1, cat);
            statement2.setInt(2, part);

            ResultSet resultSet = statement2.executeQuery();
            while (resultSet.next()) {
                factoryID = Integer.parseInt(resultSet.getString("placeID"));

            }

            PreparedStatement statement1 = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement1.setInt(1, factoryID);
            statement1.setString(2, name);
            statement1.setString(3, job);
            statement1.setInt(4, salary);


            statement1.executeUpdate();
            ResultSet rs = statement1.getGeneratedKeys();
            if (rs.next()) {
                int last_inserted_id = rs.getInt(1);
                String[] insertdata = {String.valueOf(last_inserted_id), name, job, String.valueOf(salary), cat};
                model.addRow(insertdata);
            }
            rs.close();
            statement1.close();
            return 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            rollback();
            if (throwables.getErrorCode() == 1062) {
                return 3;
            } else {
                return 2;
            }


        }


    }

    int createProduct(String name, double manPrice, double sellingPrice, String manDate, String cat, DefaultTableModel model,int part) {
        int factoryID = 0;
        if (name.strip() == "" || manDate == null || manDate.strip() == "" || name == null || sellingPrice == 0) {

            return 2;

        }

        String query2 = "SELECT placeID from place where placeName = ? and part = ?";
        String query = "INSERT INTO product(placeID, name, manPrice, sellingPrice, manDate) VALUES (?,?,?,?,?)";
        try {

            PreparedStatement statement2 = connection.prepareStatement(query2);
            statement2.setString(1, cat);
            statement2.setInt(2, part);

            ResultSet resultSet = statement2.executeQuery();
            while (resultSet.next()) {
                factoryID = Integer.parseInt(resultSet.getString("placeID"));

            }

            PreparedStatement statement1 = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement1.setInt(1, factoryID);
            statement1.setString(2, name);
            statement1.setDouble(3, manPrice);
            statement1.setDouble(4, sellingPrice);
            statement1.setString(5, manDate);


            statement1.execute();
            ResultSet rs = statement1.getGeneratedKeys();
            if (rs.next()) {
                int last_inserted_id = rs.getInt(1);
                String[] insertdata = {String.valueOf(last_inserted_id), name, String.valueOf(manPrice), String.valueOf(sellingPrice), manDate, cat};
                model.addRow(insertdata);

            }
            rs.close();
            statement1.close();
            return 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            rollback();
            if (throwables.getErrorCode() == 1062) {
                return 3;
            } else {
                return 2;
            }


        }


    }

    int createShipment(String shipment, String shippingDate, String arrivalTime, String cat, DefaultTableModel model,int part) {
        int factoryID = 0;
        if (shipment.strip() == "" || shippingDate == null || shippingDate.strip() == "" || arrivalTime == null ) {

            return 2;

        }

        String query2 = "SELECT placeID from place where placeName = ? and part = ?";
        String query = "INSERT INTO shipment( placeID, shipment, shippingDate, arrivalTime) VALUES   (?,?,?,?)";
        try {

            PreparedStatement statement2 = connection.prepareStatement(query2);
            statement2.setString(1, cat);
            statement2.setInt(2, part);

            ResultSet resultSet = statement2.executeQuery();
            while (resultSet.next()) {
                factoryID = Integer.parseInt(resultSet.getString("placeID"));

            }

            PreparedStatement statement1 = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement1.setInt(1, factoryID);
            statement1.setString(2, shipment);
            statement1.setString(3, shippingDate);
            statement1.setString(4, arrivalTime);



            statement1.execute();
            ResultSet rs = statement1.getGeneratedKeys();
            if (rs.next()) {
                int last_inserted_id = rs.getInt(1);
                String[] insertdata = {String.valueOf(last_inserted_id), shipment, String.valueOf(shippingDate), String.valueOf(arrivalTime), cat};
                model.addRow(insertdata);

            }
            rs.close();
            statement1.close();
            return 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            rollback();
            if (throwables.getErrorCode() == 1062) {
                return 3;
            } else {
                return 2;
            }


        }


    }

    int editEmployee(int id, String newName, String job, int salary) {
        if (newName.strip() == "" || newName == null || job.strip() == "" || job == null) {

            return 2;

        }


        String query = "UPDATE employee set name = ?,job = ?, salary = ? where fem = ?";
        try {
            PreparedStatement statement1 = connection.prepareStatement(query);
            statement1.setString(1, newName);
            statement1.setString(2, job);
            statement1.setInt(3, salary);
            statement1.setInt(4, id);
            statement1.executeUpdate();

            statement1.close();
            return 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            rollback();
            if (throwables.getErrorCode() == 1062) {
                return 3;
            } else {
                return 2;
            }


        }

    }

    int editProduct(int id, String newName, double manprice, double sellprice, String date) {
        if (newName.strip() == "" || newName == null || date.strip() == "" || date == null) {

            return 2;

        }


        String query = "UPDATE product set name = ?,manPrice = ?, sellingPrice = ?,manDate = ? where fpr = ?";
        try {
            PreparedStatement statement1 = connection.prepareStatement(query);
            statement1.setString(1, newName);
            statement1.setDouble(2, manprice);
            statement1.setDouble(3, sellprice);
            statement1.setString(4, date);
            statement1.setInt(5, id);
            statement1.executeUpdate();

            statement1.close();
            return 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            rollback();
            if (throwables.getErrorCode() == 1062) {
                return 3;
            } else {
                return 2;
            }


        }

    }

    int editShipment(int id, String shipment, String shippingDate, String arrivalTime) {
        if (shipment.strip() == "" || shipment == null || shippingDate.strip() == "" || arrivalTime == null) {

            return 2;

        }


        String query = "UPDATE shipment SET shipment= ?,shippingDate= ?,arrivalTime= ?  where sshID = ?";
        try {
            PreparedStatement statement1 = connection.prepareStatement(query);
            statement1.setString(1, shipment);
            statement1.setString(2, shippingDate);
            statement1.setString(3, arrivalTime);
            statement1.setInt(4, id);
            statement1.executeUpdate();

            statement1.close();
            return 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            rollback();
            if (throwables.getErrorCode() == 1062) {
                return 3;
            } else {
                return 2;
            }


        }

    }

    void stastricsall(DefaultTableModel model) {
        String[] row = new String[4];
        Collection<Thread> ts = new ArrayList<Thread>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
               // String query1 = "SELECT count('1') as places,placeName   FROM place group by part";
                String query2 = "select count('1') as totalproduct,pp.placename,if(part=1,'Factory',if(part=2,'Store',if(part=3,'Warehouse','Supplier'))) as part from product p inner  join place pp USING (placeID) group by placeID";
                String query3 = "select count('1') as totalemployee,pp.placename,if(part=1,'Factory',if(part=2,'Store',if(part=3,'Warehouse','Supplier'))) as part from employee p inner  join place pp USING (placeID)  group by placeID";

                try {

                 //   PreparedStatement statement1 = connection.prepareStatement(query1);
                    PreparedStatement statement2 = connection.prepareStatement(query2);
                    PreparedStatement statement3 = connection.prepareStatement(query3);

                   // ResultSet resultSet = statement1.executeQuery();
                   // while (resultSet.next() ) {

                  //      row[0] = "Number of places";
                  //      row[1] = resultSet.getString("places");
                 //       row[2] = resultSet.getString("placename");
                 //       model.addRow(row);
                  //  }


                    ResultSet resultSet2 = statement2.executeQuery();

                    while (resultSet2.next()) {

                        row[0] = "Number of Product";
                        row[1] = resultSet2.getString("totalproduct");

                        row[2] = resultSet2.getString("placename");
                        row[3] = resultSet2.getString("part");


                        model.addRow(row);

                    }

                    ResultSet resultSet3 = statement3.executeQuery();
                    while (resultSet3.next()) {
                        row[0] = "Number of Employee";
                        row[1] = resultSet3.getString("totalemployee");
                        row[2] = resultSet3.getString("placename");
                        row[3] = resultSet3.getString("part");

                        model.addRow(row);
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
        ts.add(t);
        t.start();


    }

    void stastrics(DefaultTableModel model, int part) {
        String[] row = new String[2];
        Collection<Thread> ts = new ArrayList<Thread>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String query1 = "SELECT count('1') as places  FROM place WHERE part = ? ";
                String query2 = "select count('1') as totalproduct from product p inner  join place pp USING (placeID) where placeID = ?";
                String query3 = "select count('1') as totalemployee from employee p inner  join place pp USING (placeID) where placeID = ?";

                try {

                    PreparedStatement statement1 = connection.prepareStatement(query1);
                    PreparedStatement statement2 = connection.prepareStatement(query2);
                    PreparedStatement statement3 = connection.prepareStatement(query3);
                    statement1.setInt(1, part);
                    ResultSet resultSet = statement1.executeQuery();
                    while (resultSet.next()) {
                        row[0] = "Number of places";
                        row[1] = resultSet.getString("places");
                        model.addRow(row);
                    }
                    statement2.setInt(1, part);
                    ResultSet resultSet2 = statement2.executeQuery();
                    while (resultSet2.next()) {
                        row[0] = "Number of Product";
                        row[1] = resultSet2.getString("totalproduct");
                        model.addRow(row);
                    }
                    statement3.setInt(1, part);
                    ResultSet resultSet3 = statement3.executeQuery();
                    while (resultSet3.next()) {
                        row[0] = "Number of Employee";
                        row[1] = resultSet3.getString("totalemployee");
                        model.addRow(row);
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
        ts.add(t);
        t.start();


    }

    void stastricsS(DefaultTableModel model, int part) {
        String[] row = new String[2];
        Collection<Thread> ts = new ArrayList<Thread>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String query1 = "SELECT count('1') as places  FROM place WHERE part = ? ";
                String query2 = "select count('1') as totalproduct from product p inner  join place pp USING (placeID) where placeID = ?";
                String query3 = "select count('1') as totalship from shipment p inner  join place pp USING (placeID) where placeID = ?";

                try {

                    PreparedStatement statement1 = connection.prepareStatement(query1);
                    PreparedStatement statement2 = connection.prepareStatement(query2);
                    PreparedStatement statement3 = connection.prepareStatement(query3);
                    statement1.setInt(1, part);
                    ResultSet resultSet = statement1.executeQuery();
                    while (resultSet.next()) {
                        row[0] = "Number of places";
                        row[1] = resultSet.getString("places");
                        model.addRow(row);
                    }
                    statement2.setInt(1, part);
                    ResultSet resultSet2 = statement2.executeQuery();
                    while (resultSet2.next()) {
                        row[0] = "Number of Product";
                        row[1] = resultSet2.getString("totalproduct");
                        model.addRow(row);
                    }
                    statement3.setInt(1, part);
                    ResultSet resultSet3 = statement3.executeQuery();
                    while (resultSet3.next()) {
                        row[0] = "Number of Shipment";
                        row[1] = resultSet3.getString("totalship");
                        model.addRow(row);
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
        ts.add(t);
        t.start();


    }

}
