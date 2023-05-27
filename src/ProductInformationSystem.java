import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;


//s1tr
public class ProductInformationSystem {

    //initializing the java Database connectivity
    private static final String JDBC = "";
    //initializing the java Database url
    private static final String DB_URL = "";
    //initializing mysql user name
    private static final String USER = "root";
    //initializing mysql password
    private static final String PASSWORD = "";

    //initializing the connection
    static Connection con = null;
    //initializing the scanner
    static Scanner sc = new Scanner(System.in);

    //main method
    public static void main(String[] args) {


        boolean exit = false;

        //The program will be in while loop until the exit var be true which is in case 4
        while (!exit) {

            try {
                //print the menu to the user
                printOptions();
                //take the user input
                int option = Integer.parseInt(sc.next());
                //switch case on user option
                switch (option) {
                    case 1: //option 1, add product to database
                        addProduct();
                        System.out.println();
                        break;
                    case 2: //option 2, serach for product
                        searchForProduct();
                        System.out.println();
                        break;
                    case 3: //option 3, delete a product
                        deleteProduct();
                        System.out.println();
                        break;
                    case 4: //option 4, exit the program
                        System.out.println("finishing…");
                        exit = true; //make exit true to end the program
                        break;
                    default: //if the user enter another number
                        System.out.println("Invalid Option, try again\n\n");
                        break;

                }


            }catch (InputMismatchException | NumberFormatException e){
                // catch the error, this will run if the user enter non-number input
                System.out.println("Invalid Option, Please enter only number!!\n\n");
                sc.nextLine();

            }

        }

    }

    // add product method
    private static void addProduct(){

        //clean the input buffer
        sc.nextLine();
        // declare type var
        String type;
        while (true){
            System.out.println("--------------------------------");
            System.out.print("Enter the type of the product : ");
            //take the type from user
            type = sc.nextLine();
            //if the type length is grater than 20 characters
            if(type.length() > 20 ){
                System.out.println("Type must be less than or equal to 20 characters!\n");
            }
            //if the type is empty or spaces
            else if (type.replaceAll("\\s","").length() == 0) {
                System.out.println("type most be not empty!");
            }
            //if the type number
            else if (!type.matches("[a-zA-Z ]+")){
                System.out.println("Type must not contains numbers or signs!\n");
            }
            //if the type is correct, then the loop
            else{
                break;
            }

        }

        System.out.println();

        //declare model var
        String model;
        while (true) {
            System.out.println("--------------------------------");
            System.out.print("Enter the Model of the product : ");
            //take the model from user
            model = sc.nextLine();
            //if the model length is grater than 40 characters
            if (model.length() > 40) {
                System.out.println("Model must be less than or equal to 40 characters!\n");
            }
            //if the model is empty or spaces
            else if (model.replaceAll("\\s","").length() == 0) {
                System.out.println("type most be not empty!");
            }
            //if the type is correct, break the loop
            else {
                break;
            }

        }

        System.out.println();


        //declare price
        float price;
        while (true) {
            System.out.println("--------------------------------");
            System.out.print("Enter the Price of the product : ");
            try {
                //take the price from user
                price = sc.nextFloat();
                //if the price is less then 1 or grater than 1m
                if (price < 1 || price > 1000000) {
                    System.out.println("price most be not less than 1 and not more than 1000000!");
                }
                //if the price is correct, break the loop
                else {
                    break;
                }
            }catch (Exception e) { //catch the error if the user enter a non-number input
                System.out.println("price must be a number!");
                //again scan the uesr input
                sc.nextLine();
            }
        }

        System.out.println();


        //declare count
        int count;
        while (true) {
            System.out.println("--------------------------------");
            System.out.print("Enter the count of the product : ");

            try {
                //take the count number from user
                count = sc.nextInt();
                //if the count is less than 1 or grater than 1m
                if (count > 1000000 || count < 1) {
                    System.out.println("count must be not less than 1 and not more than 1000000");
                }
                else {
                    //if the count is correct, break the loop
                    break;
                }

            }catch (Exception e) { //catch the error if the user enter a non-number or floating number
                System.out.println("count must be a number and decimal number !");
                //scann the user input again
                sc.nextLine();
            }

        }

//        -------------------------------------------------------------------------------


        // try block to add the above info to the database
        try {

            //load the database driver
            Class.forName(JDBC);
            //setup the connection to mysql server
            con = DriverManager.getConnection(DB_URL,USER,PASSWORD);

            // setup the current date to insert it to specific field in database
            Date dNow = new Date( );
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
            String deliveryDate = ft.format(dNow);

            //setup the mysql query to insert above data to database
            String sqlQuery = "INSERT INTO productstbl_awadh (Type,Model,Price,Count,DeliveryDate) " +
                    "VALUES (?,?,?,?,?)";

            //setup the PreparedStatement to pass the sql query
            PreparedStatement ps = con.prepareStatement(sqlQuery);

            //use PreparedStatement to push each value to its corresponding field
            ps.setString(1,type);
            ps.setString(2,model);
            ps.setFloat(3,price);
            ps.setInt(4,count);
            ps.setString(5,deliveryDate);

            //save the value of executing query to k, if k=1 then the data pushed successfully
            int k = ps.executeUpdate();

            if(k == 1){//if the data pushed successfully
                System.out.println("product added successfully");
                //print the table
            }else{//if the data does not pushed successfully
                System.out.println("Couldn't add the product!");
            }

            //close the connection
            con.close();
            //close the PreparedStatement
            ps.close();


        }catch (SQLException | ClassNotFoundException e){ //catch the error if the data does not pushed successfully
            System.out.println("Couldn't add the product!");


            try { //force closing the connection
                if(con != null){
                    con.close();
                }
            }catch (SQLException ex) {//catch the error and print the error why doesn't the connection closed
                ex.printStackTrace();
            }
        }


    }


    //Read all product!


    //read all product from database method
    //this method accept one parameter, query,
    //query : if the query is empty, then i want just to print all data in the table
    //query : if the query is not empty, then i call this method from searchProduct method
    //i do all of that to make the code easy, not repeated, and readable, so this is the way i found
    private static void readAllProduct(String query) {


        try {

            Class.forName(JDBC);
            con = DriverManager.getConnection(DB_URL,USER,PASSWORD);
            String sqlQuery;

            //to make sure if the user use the search option and type nothing or spaces
            if(query.replaceAll("\\s","").length() == 0) {
                //the display all data in the table
                sqlQuery = "SELECT * FROM productstbl_awadh";
            }else{
                //if the user enter a letters to search
                //then make the sqlQuery contains user search word
                sqlQuery = query;
            }

            PreparedStatement ps = con.prepareStatement(sqlQuery);

            //save all table cells in resultset
            ResultSet res = ps.executeQuery();
            //get the metadata, so we can read the column names
            ResultSetMetaData metaData = res.getMetaData();

            //check if the user chose a search option and no matchs found,
            //the first condition is to check if the user enter an empty input and the resultSet is empty
            //the second condition is to chech if the user enter a word and the resultSet is empty
            boolean isThereData = res.next();
            if ((!isThereData && !(query.replaceAll("\\s","").length() == 0))){
                System.out.println("Couldn't find any related data of your search!");
                return;
            }else if ((!isThereData) && (query.replaceAll("\\s","").length() == 0)) {
                System.out.println("Couldn't find any related data of your search!");
                return;
            }

            //the number of column in the table
            int numberOfColumn = metaData.getColumnCount();

            //initializing array with size number of column
            int[] columnLength = new int[numberOfColumn];
            //loop to check and save each cell length of the header of the table
            for (int i = 1; i <= numberOfColumn; i++) {
                columnLength[i - 1] = metaData.getColumnName(i).length();
            }
            //while there is data in resultset ?
            while (res.next()) {
                //loop to check and save each cell length
                for (int i = 1; i <= numberOfColumn; i++) {
                    //take the maximum between the header or the current cell
                    columnLength[i - 1] = Math.max(columnLength[i - 1], res.getString(i).length());
                    /*here the loop goes around all cells and take the length of the cell and compare it by the column length,
                    why that ? to save the border of the table and make all table info readable, it is cost to much if the table
                    contains millions of rows but in this program it's good.

                    so here the columnLength array contain the max width for each column, for ex. if we have the
                    word (samsung TV newmodel2023 plus) in somewhere then when we print the table the model column
                    will be 28 place :
                                        | Model                       |
                                        |TV12                         |
                                        |samsung TV newmodel2023 plus |
                                        |                             |
                   and like that.

                    */
                }
            }


            //close the resultset because we use it before, so when we execute it again the cursor will be in the first cell
            res.close();
            //execute the query and save the result to the resultset
            res = ps.executeQuery();
            System.out.println();

            //print the header of the table, the header is the column in the database
            for (int i = 1; i <= numberOfColumn; i++) {
                //get column name by using metadata of the table
                String columnName = metaData.getColumnName(i);
                //print the header using format,
                System.out.printf("| %-"+ (columnLength[i - 1] + 2) + "s", columnName);
                /*here if the max length(largest word or numebr) of the 3rd column is 10,
                then (%-10+2s) will specify a 12 place (spaces+the text) and will align the text to left
                so it appear like this:
                                        |text       |
                                        |hello world|
                 */
            }
            System.out.println("|");


            //while there is data in the resultset
            while (res.next()) {
                for (int i = 1; i <= numberOfColumn; i++) {
                    //get the value of cell at i
                    String value = res.getString(i);
                    System.out.printf("| %-"+ (columnLength[i - 1] + 2) + "s", value);
                }
                System.out.println("|");
            }
            System.out.println();

            ps.close();
            res.close();
            con.close();



        } catch (SQLException | ClassNotFoundException e) {// catch the error from database connection and for Class.forName loading
            System.out.println("Couldn't read the products from the database!");
            try { //force closing the connection
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {//catch the error and print it
                ex.printStackTrace();
            }
        }

        }








    //Search for product !
    private static void searchForProduct() {

        sc.nextLine();
        String searchedWord;
        try {
            System.out.println("Search for product by Model or Type: ");
            searchedWord = sc.nextLine();

            String sqlQuery;
            //if the user enter an empty input or a spaced input
            if(searchedWord.replaceAll("\\s","").length() == 0){
                //make the sqlquery as nurmal, to show all field
                sqlQuery = "SELECT * FROM productstbl_awadh";
                //call readallproduct method with appropriate par
                readAllProduct("");
            }else {
                // select * from productstbl_awadh where type like %TV% or model like %TV%;
                sqlQuery = "SELECT * FROM productstbl_awadh WHERE Type LIKE '%"+searchedWord+"%' OR model LIKE '%"+searchedWord+"%'";
                //push the sqlquery to readallproduct method with appropriate index
                readAllProduct(sqlQuery);
            }


        }catch (Exception e) {//catch the error
            System.out.println("Couldn't serch for product, try again!");
        }

    }









    private static void deleteProduct() {

        int productID;
        boolean end = false;
        while(!end) {


            //this try block is to check if the whole database have a data or not, if not then print : not data. and then close the method.
            //the reason of doing this is when the database is empty and the user tries to delete a product, the user will be in infinite loop because there is no data to delete
//            try {
//                String sqlQuery = "SELECT * FROM productstbl_awadh";
//
//                con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
//                PreparedStatement ps = con.prepareStatement(sqlQuery);
//
//                ResultSet res = ps.executeQuery();
//
//                if (!res.next()) {// if there is no data in the dataset, print to the user: there is no data, and close the method
//                    System.out.println("There is no data in database!");
//                    con.close();
//                    res.close();
//                    return;
//                }
//            }catch (SQLException e) {
//                System.out.println("Something went wrong, try again!");
//            }




            try {


                System.out.println("Enter the ID of the product you want to delete: ");
                productID = sc.nextInt();

                //get the id that the user ask for
                //Here we'll use the select query to check if the ID is exist or not
                String sqlQuery = "SELECT * FROM productstbl_awadh WHERE id = ?";

                con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                PreparedStatement ps = con.prepareStatement(sqlQuery);

                ps.setInt(1, productID);

                ResultSet res = ps.executeQuery();

                if (!res.next()) {// if there is no data in the dataset, print to the user: there is no product with ID #
                    System.out.println("There is no product with ID " + productID);
                    System.out.println();
                    break;
                } else {
                    //clear the input buffer
                    sc.nextLine();
                    System.out.println("Are you sure you want to delete this product ?(Y/N)");
                    String conf = sc.nextLine().toLowerCase();

                    //if the user does not enter neither y nor n, then make him in loop until he answer
                    while(!end) {
                        if (conf.equals("y")) {
                            String sqlQueryDelete = "DELETE FROM productstbl_awadh WHERE id = ?";
                            ps = con.prepareStatement(sqlQueryDelete);
                            ps.setInt(1, productID);
                            int k = ps.executeUpdate();

                            //k=1 means success
                            if (k == 1) {
                                System.out.println("product " + productID + " deleted successfully");
                                end = true;
                            } else {
                                System.out.println("Couldn't delete the product");
                            }
                        } else if (conf.equals("n")) {
                            System.out.println("Delete are canceled");
                            end = true;
                        } else {
                            //if the user does not answer with n or y
                            System.out.println("Are you sure you want to delete this product ?(Y/N)");
                            conf = sc.nextLine().toLowerCase();

                        }
                    }
                }

        }catch (InputMismatchException e) {
            System.out.println("Please enter only numbers!\n");
            sc.nextLine();
        } catch (SQLException e) {
            System.out.println("Couldn't delete for product, try again!");
        }
    }
    }

    //print menu
    public static void printOptions(){
        System.out.println("Welcome To Products Information System By: ");
        System.out.println("Awadh almalki | 441006300 | Grup3 | s441006300@st.uqu.edu.sa");
        System.out.println("--------------------------------------");
        System.out.println("1 --> Add a Product");
        System.out.println("2 --> Search for Product");
        System.out.println("3 --> Delete a Product");
        System.out.println("4 --> Exit");
    }
//01
}
