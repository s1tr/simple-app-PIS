import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.*;


import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;


//s1tr
public class ProductInformationSystem {

    private static final String JDBC = "";

    private static final String DB_URL = "";

    private static final String USER = "";

    private static final String PASSWORD = "";

    //initializing the connection
    static Connection con = null;

    static Main mn = new Main();

    // add product method
    public static VBox addProduct(String t, String m, float p, int c, String d){

        Label label = new Label();
        VBox container = new VBox();
        String type = t;
        String model = m;
        float price = p;
        int count = c;
        String date = d;


        try {

            //load the database driver
            Class.forName(JDBC);
            //setup the connection to mysql server
            con = DriverManager.getConnection(DB_URL,USER,PASSWORD);


            //setup the mysql query to insert above data to database
            String sqlQuery = "INSERT INTO yourTable (Type,Model,Price,Count,DeliveryDate) " +
                    "VALUES (?,?,?,?,?)";

            //setup the PreparedStatement to pass the sql query
            PreparedStatement ps = con.prepareStatement(sqlQuery);

            //use PreparedStatement to push each value to its corresponding field
            ps.setString(1,type);
            ps.setString(2,model);
            ps.setFloat(3,price);
            ps.setInt(4,count);
            ps.setString(5,date);

            //save the value of executing query to k, if k=1 then the data pushed successfully
            int k = ps.executeUpdate();

            if(k == 1){//if the data pushed successfully
                label.setText("product added successfully");
                container = mn.setupTheSuccessStyle(label);

            }else{//if the data does not pushed successfully
                label.setText("Couldn't add the product!");
                container = mn.setupTheErrorStyle(label);
            }
            //close the connection
            con.close();
            //close the PreparedStatement
            ps.close();


        }catch (SQLException | ClassNotFoundException e){ //catch the error if the data does not pushed successfully
            label.setText("Couldn't add the product!");
            container = mn.setupTheErrorStyle(label);


            try { //force closing the connection
                if(con != null){
                    con.close();
                }
            }catch (SQLException ex) {
                label.setText(ex.getMessage());
                container = mn.setupTheErrorStyle(label);
            }
        }

        //return the vbox container
        return container;
    }



    //Read all product!

    static Label label = new Label();
    static VBox container = new VBox();


    public VBox getmessage(){
        return container;
    }
    public static void readAllProduct(TableView<Product> tv, ObservableList<Product> ol,String query) {


        try {


            Class.forName(JDBC);
            con = DriverManager.getConnection(DB_URL,USER,PASSWORD);
            String sqlQuery;

            if(query.replaceAll("\\s","").length() == 0) {
                sqlQuery = "SELECT * FROM yourTable";
            }else{
                sqlQuery = query;
            }

            PreparedStatement ps = con.prepareStatement(sqlQuery);

            ResultSet res = ps.executeQuery();

            ResultSetMetaData metaData = res.getMetaData();

            boolean isThereData = res.isBeforeFirst();
            if ((!isThereData && !(query.replaceAll("\\s","").length() == 0))){
                label.setText("No records available for this search criteria!");
                container = mn.setupTheErrorStyle(label);
                ol.clear();
                return;
            }else if ((!isThereData) && (query.replaceAll("\\s","").length() == 0)) {
                label.setText("No records available for this search criteria!");
                container = mn.setupTheErrorStyle(label);
                ol.clear();
                return;
            }
            ol.clear();
            //res.beforeFirst();
            while (res.next()){
                int id = res.getInt("ID");
                String type = res.getString("Type");
                String model = res.getString("Model");
                float price = res.getFloat("Price");
                int count = res.getInt("Count");
                String date = res.getString("DeliveryDate");

                Product product = new Product(id,type,model,price,count,date);

                ol.add(product);

            }
            tv.setItems(ol);

            ps.close();
            res.close();
            con.close();



        } catch (SQLException | ClassNotFoundException e) {// catch the error from database connection and for Class.forName loading
            label.setText("Couldn't read the products from the database!");
            container = mn.setupTheErrorStyle(label);
//            mn.containerPane.setBottom(container);
            try { //force closing the connection
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {//catch the error and print it
                label.setText(ex.getMessage());
                container = mn.setupTheErrorStyle(label);
            }
            return;
        }
        label.setText("Success, All Data Up-To-Date");
        container = mn.setupTheSuccessStyle(label);

        }








    //Search for product !
    public static TableView<Product> searchForProduct(TableView<Product> tv, ObservableList<Product> ol, String s) {

        TableView<Product> tabv = tv;

        String searchedWord = s;
        try {
            String sqlQuery;
            if(searchedWord.replaceAll("\\s","").length() == 0){
                readAllProduct(tabv,ol,"");
            }else {
                sqlQuery = "SELECT * FROM yourYable WHERE Type LIKE '%"+searchedWord+"%' OR model LIKE '%"+searchedWord+"%'";
                readAllProduct(tabv,ol,sqlQuery);
            }


        }catch (Exception e) {//catch the error
            label.setText("Couldn't search for product, try again!");
            container = mn.setupTheErrorStyle(label);
        }

//        mn.containerPane.setBottom(container);

        return tabv;
    }




    public static VBox deleteProduct(int id) {

        int productID = id;
        Label label = new Label();
        VBox container = new VBox();


        try {
            //get the id that the user ask for
            //Here we'll use the select query to check if the ID is exist or not
            String sqlQuery = "SELECT * FROM yourTable WHERE id = ?";

            con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement(sqlQuery);

            ps.setInt(1, productID);

            ResultSet res = ps.executeQuery();

            if (!res.next()) {// if there is no data in the dataset, display to the user: there is no product with ID #
                label.setText("There is no product with ID " + productID);
                container = mn.setupTheErrorStyle(label);
            } else {
                //previously explained...
                String sqlQueryDelete = "DELETE FROM YourTable WHERE id = ?";
                ps = con.prepareStatement(sqlQueryDelete);
                ps.setInt(1, productID);
                int k = ps.executeUpdate();

                //k=1 means success
                if (k == 1) {
                    label.setText("product " + productID + " deleted successfully");
                    container = mn.setupTheSuccessStyle(label);
                } else {
                    label.setText("Couldn't delete the product");
                    container = mn.setupTheErrorStyle(label);
                }
            }

            }catch(SQLException e){
                label.setText("Couldn't delete for product, try again!");
                container = mn.setupTheErrorStyle(label);
            }

        return container;
        }

//01
}
