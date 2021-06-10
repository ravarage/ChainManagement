//*****************************************************************
// DBconnection.java Author: Ravyar Sarbast
//
// a main connection to database
//*****************************************************************

package RavyarSarbastTahir;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    Connection conncetion;

    public DBConnection() throws SQLException, ClassNotFoundException, IOException, SAXException, ParserConfigurationException {
        String[] login = new XMLFile().read();
        String  url = "jdbc:mysql://" +login[3]+ ":"+ login[4]+"/" +login[2];// making connection to the database from setting read from xml file
        String user = login[0];
        String password = login[1];
        //login detail for MySQL or MariaDB

            Class.forName("org.mariadb.jdbc.Driver");//Maria DB Driver can be replaced with MySQL driver

            conncetion = DriverManager.getConnection(url, user, password);//connecting to data

            conncetion.setAutoCommit(false);// disabling auto commit so I it false happen it roll back to older state

    }


    Connection getConncetion() {
        return conncetion;
    }// method to connect to database from other objects
}
