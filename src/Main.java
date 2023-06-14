import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;

public class Main extends Application {

   
    private Stage primaryStage;
   
    ProductInformationSystem pis = new ProductInformationSystem();
   
    private static final int width = 1000;
    private static final int height = 800;
   
    Label myInfo = new Label("""
            A B C
                      4
                          3
            """);

   
    static String type ;
    static String model;
    static float price;
    static int count;
    static String date;

    static int id;

   
    boolean validatetype, validatemodel, validateprice, validatecount, validatedate, validateall,validateid= false;

   
   
    File file = new File("image/logo.png");
    String absolutePath = file.toURI().toString();




   
    @Override
    public void start(Stage ps){

       
        BorderPane root;
       
       
        this.primaryStage = ps;
       
        primaryStage.setResizable(false);
       
        primaryStage.getIcons().add(new Image(absolutePath));
       
        primaryStage.setTitle("Product Information System");

       
        root = new BorderPane();


       
        root.setTop(menuBarTitle());
       
        root.setCenter(message());




       
        Scene scene = new Scene(root, width, height);
       
        primaryStage.setScene(scene);
       
        primaryStage.show();


    }

   
    public static void main(String[] args) {
        launch(args);
    }

   
    public VBox message(){
       
        VBox messages = new VBox(80);

       
        Label label1 = new Label();
       
        label1.setText("Welcome to Product Information System");
       
        label1.setFont(Font.font("Georgia", FontWeight.BOLD,44));
       
        label1.setTextFill(Color.BLUE);

       
        myInfo.setFont(Font.font("Trebuchet MS",FontWeight.BOLD,23));
       
        myInfo.setTextFill(Color.BROWN);
       
        Image image = new Image(absolutePath);
       
        ImageView iv = new ImageView(image);

       
        messages.getChildren().addAll(label1,myInfo,iv);
       
        messages.setAlignment(Pos.CENTER);
       
        return messages;
    }





   
    public MenuBar menuBarTitle(){
       
        MenuBar menuBar = new MenuBar();
       
        Menu productsMenu = new Menu("Products");
       
        Menu productMenu = new Menu("Product");
       
        MenuItem addProduictMenu = new MenuItem("Add Product");
       
        MenuItem searchForProductMenu = new MenuItem("Search For Product");
       
        MenuItem deleteProductMenu = new MenuItem("Delete Product");
       
        MenuItem exitMenu = new MenuItem("Exit");

        /*
        the diffrence between menu and menuitem is that the menu item is the child of the menu so it will be is this:
        Products --> parent of Product and exit children
            Product --> parent of add product, search for product, delete product children
                add product
                search for product
                delete product
            exit
         */


       
        productMenu.getItems().addAll(addProduictMenu, new SeparatorMenuItem(), searchForProductMenu,new SeparatorMenuItem(), deleteProductMenu);
       
        productsMenu.getItems().addAll(productMenu,new SeparatorMenuItem(),exitMenu);

       
        addProduictMenu.setOnAction(e -> addProductScene());
        searchForProductMenu.setOnAction(e -> searchForProduct());
        deleteProductMenu.setOnAction(e -> deleteProductScene());
       
        exitMenu.setOnAction(e -> {
            Platform.exit();
        });
       
        menuBar.getMenus().addAll(productsMenu);
       
        return menuBar;
    }


   
    public void addProductScene(){

        BorderPane borderContainer = new BorderPane();

        VBox vBoxpane = new VBox();

        VBox stInfo = new VBox();

        GridPane pane = new GridPane();

       
        pane.setPadding(new Insets(10));
       
        pane.setHgap(10);
       
        pane.setAlignment(Pos.CENTER);



       
        stInfo.getChildren().add(myInfo);
       
        stInfo.setAlignment(Pos.CENTER);
       
        stInfo.setPadding(new Insets(5,0,100,0));



       
        Label typeL = new Label("Enter the type : ");
        Label modelL = new Label("Enter the Model : ");
        Label priceL = new Label("Enter the Price : ");
        Label countL = new Label("Enter the count : ");
        Label dateL = new Label("Enter the date : ");

       
       
        ChoiceBox<String> enteredType = new ChoiceBox<>();
       
        TextField enteredModel = new TextField();
       
        TextField enteredPrice = new TextField();
       
        Slider enteredCount = new Slider (0,10,0);
       
        DatePicker entereDate = new DatePicker();
       
        Button submit = new Button("Sumbit");

       
        Insets labset = new Insets(15,10,20,0);
       
        Font f = new Font("Tahoma",20);
        Font f2 = new Font("Segoe UI",15);

       
        typeL.setPadding(labset);
       
        typeL.setFont(f);
       
        enteredType.setPrefWidth(200);
        enteredType.setPrefHeight(30);

       
        modelL.setPadding(labset);
       
        modelL.setFont(f);
       
        enteredModel.setFont(f2);

       
        priceL.setPadding(labset);
       
        priceL.setFont(f);
       
        enteredPrice.setFont(f2);

       
        countL.setPadding(labset);
        countL.setFont(f);
        enteredCount.setPrefWidth(200);
        enteredCount.setPrefHeight(30);

       
        dateL.setPadding(labset);
        dateL.setFont(f);
        entereDate.setPrefWidth(200);
        entereDate.setPrefHeight(30);

       
        submit.setFont(f);



       
        enteredType.getItems().addAll("PC","REFRIGERATOR","WASHING MACHINE","TV");

        /*handling the choiceBox value changing, so when the user chose product,
        here we used addlistener with three parameter observable,oldValue,newValue. where observable is a selectedItemProperty
        that triggered the change,oldvalue and newvalue represent the old and new values of the observable property
         */
        enteredType.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->{
           
            type = newValue;
           
            validatetype = type.isEmpty()?false:true;
        });






       
        enteredModel.setOnKeyReleased(e ->{
           
            Label l = new Label("a");
           
            l = validateModel(enteredModel.getText());
           
            VBox container = setupTheErrorStyle(l);

           
            borderContainer.setBottom(container);
        });

       
        enteredPrice.setOnKeyReleased(e ->{
            /*make sure the validateprice to be false, so if the user insert product successfully and try to insert another
            product, then the var will be true and error will happen
             */
            validateprice = false;
            VBox container;
            Label l = new Label("a");
            /*
            if the user enter non-empty input and this input is a number like this format : -# or -#.# or # or #.# then...
             */
            if((!enteredPrice.getText().isEmpty()) && (enteredPrice.getText().matches("-?[0-9]*\\.?[0-9]+"))){
               
               
                l = validatePrice(Float.parseFloat(enteredPrice.getText()));
               
            }else if(enteredPrice.getText().isEmpty()) {
               
                l.setText("Price must be not empty!");
               
            }else{
               
                l.setText("Price must be a number!");
            }
           
            container = setupTheErrorStyle(l);
           
            borderContainer.setBottom(container);
        });




       
       
        enteredCount.setBlockIncrement(1);
       
        enteredCount.setMajorTickUnit(1);
       
        enteredCount.setMinorTickCount(0);
       
        enteredCount.setShowTickLabels(true);
       
        enteredCount.setSnapToTicks(true);


       
         enteredCount.valueProperty().addListener(e ->{
             VBox container;
             Label l;
            
             l = validateCount((int)enteredCount.getValue());

             container = setupTheErrorStyle(l);
             borderContainer.setBottom(container);
         });



        
         entereDate.valueProperty().addListener((observable,oldValue,newValue) ->{
             VBox container;
             Label l;
             l = validateDate(newValue);
             container = setupTheErrorStyle(l);
             borderContainer.setBottom(container);

         });

        
         submit.setOnAction(e ->{
             VBox container = validateSubmit();
             borderContainer.setBottom(container);

            
             if(validateall == true){
                
                
                
                 VBox container2 = pis.addProduct(type,model,price,count,date);
                
                 borderContainer.setBottom(container2);
             }

         });



        
        pane.add(typeL,0,0);
        pane.add(enteredType,1,0);
        pane.add(modelL,0,2);
        pane.add(enteredModel,1,2);
        pane.add(priceL,0,3);
        pane.add(enteredPrice,1,3);
        pane.add(countL,0,4);
        pane.add(enteredCount,1,4);
        pane.add(dateL,0,5);
        pane.add(entereDate,1,5);
        pane.add(new Label(""),0,6);

        pane.add(submit, 0, 7,2,7);
       
        submit.setPrefSize(140,40);
       
        pane.setHalignment(submit, HPos.CENTER);

       
        borderContainer.setTop(menuBarTitle());
       
        vBoxpane.getChildren().addAll(stInfo,pane);
       
        borderContainer.setCenter(vBoxpane);



       
        Scene sc = new Scene(borderContainer,width,height);
       
        primaryStage.setScene(sc);


    }

   

   
   
    public VBox setupTheErrorStyle(Label label){
        Label l = label;
        VBox container = new VBox();
       
        l.setTextFill(Color.RED);
       
        l.setFont(Font.font("Monospaced",FontWeight.BOLD,26));
       
        container.getChildren().add(l);
       
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(0,0,15,0));

       
        return container;
    }

   
   
    public VBox setupTheSuccessStyle(Label label){
        Label l = label;
        VBox container = new VBox();
        l.setTextFill(Color.GREEN);
        l.setFont(Font.font("Monospaced",FontWeight.BOLD,26));
        container.getChildren().add(l);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(0,0,15,0));

        return container;
    }



   
    public Label validateModel(String m){
       
        validatemodel = false;
        Label label = new Label();
       
        if (m.length() > 40) {
            label.setText("Model must be less than or equal to 40 characters!");
        }
       
        else if (m.replaceAll("\\s","").length() == 0) {
            label.setText("Model most be not empty!");
        }
       
        else {
            validatemodel = true;
            model = m;
        }

        return label;
    }

   
    public Label validatePrice(float p){
        validateprice = false;
        Label label = new Label();

       
        if (p < 1 || p > 1000000) {
            label.setText("price most be not less than 1 and not more than 1000000!");
        }
       
        else {
            validateprice = true;
            price = p;
        }

        return label;
    }

   
    public Label validateCount(int c){
        validatecount = false;
        Label label = new Label();

       
        if(c <= 1){
            label.setText("coutn must be grater than 0!");
        }else{
            validatecount = true;
            count = c;
        }
        return label;
    }


   
    public Label validateDate(LocalDate d){
        validatedate = false;
        Label label = new Label();

       
        if(d.getYear() < 1980 || d.getYear() > 2023){
            label.setText("Date must be between 1980 and 2023");
        }else {
            validatedate = true;
            date = d.toString();
        }
        return label;
    }


   
   
    public VBox validateSubmit(){
        validateall = false;
        Label label = new Label();
        VBox vBox = new VBox();

        if((validatetype && validatemodel && validateprice && validatecount && validatedate) == false){
            label.setText("Provided Information is wrong, Pleas Check It Again!!");
            vBox = setupTheErrorStyle(label);
        }else {
            validateall = true;
            label.setText("Submitted Successfully");
            vBox = setupTheSuccessStyle(label);
        }

        return vBox;
    }



   


   
    ObservableList<Product> productList = FXCollections.observableArrayList();
   
    TableView<Product> productTable = new TableView<>();

   
    String searchedWord = "";

   
    public void searchForProduct(){
        BorderPane containerPane = new BorderPane();
       
        productList.clear();
       
        productTable.refresh();

        searchedWord = "";

       
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
       

       
        if(productTable.getColumns().isEmpty()) {

           
           
            TableColumn<Product, Integer> idColumn = new TableColumn<>("ID");
           
           
           
           
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));


           
            TableColumn<Product, String> typeColumn = new TableColumn<>("Type");
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
           

            TableColumn<Product, String> modelColumn = new TableColumn<>("Model");
            modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
           

            TableColumn<Product, Float> priceColumn = new TableColumn<>("Price");
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
           


            TableColumn<Product, Integer> countColumn = new TableColumn<>("Count");
            countColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
           

            TableColumn<Product, String> dateColumn = new TableColumn<>("Date");
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
           

           
            productTable.getColumns().addAll(idColumn,typeColumn, modelColumn, priceColumn, countColumn, dateColumn);
           
           
           
            productTable.setPrefHeight(height - 300);
        }



        VBox fieldContainer = new VBox();
       
        fieldContainer.setMaxWidth(400);
        TextField searchField = new TextField();
        searchField.setPadding(new Insets(10,0,10,0));
       
       
       
        searchField.setPrefHeight(30);
       
        searchField.setPromptText("Search For Product by Type or Model...");
        searchField.setFont(new Font(14));
        searchField.setAlignment(Pos.CENTER);
       
        fieldContainer.getChildren().add(searchField);

        Button searchButton = new Button("Search");
        searchButton.setFont(new Font(15));
        searchButton.setPrefWidth(100);

        Button refreshButton = new Button("Refresh");
        refreshButton.setFont(new Font(15));
        refreshButton.setPrefWidth(100);

       
        searchField.setOnKeyReleased(e ->{
           
            searchedWord = searchField.getText();
        });

       
        searchField.setOnKeyPressed(e ->{
           
            if(e.getCode() == KeyCode.ENTER){
               
               
                productTable = pis.searchForProduct(productTable,productList,searchedWord);
               
                containerPane.setBottom(pis.getmessage());
            }
        });

       
        searchButton.setOnAction(e ->{
           
            productTable = pis.searchForProduct(productTable,productList,searchedWord);
            containerPane.setBottom(pis.getmessage());
        });

        refreshButton.setOnAction(e ->{
           
            productList.clear();
            productTable.refresh();
            productTable = pis.searchForProduct(productTable,productList,searchedWord);
            containerPane.setBottom(pis.getmessage());
        });

       
       
        productTable = pis.searchForProduct(productTable,productList,"");
        containerPane.setBottom(pis.getmessage());

        HBox searchAndRefreshButtonsContainer = new HBox();
        searchAndRefreshButtonsContainer.setAlignment(Pos.CENTER);
        searchAndRefreshButtonsContainer.setSpacing(50);
        searchAndRefreshButtonsContainer.getChildren().addAll(searchButton,refreshButton);

        VBox setInfo = new VBox();
        setInfo.getChildren().add(myInfo);
        setInfo.setAlignment(Pos.CENTER);


        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(myInfo,productTable,fieldContainer, searchAndRefreshButtonsContainer);

        containerPane.setCenter(vbox);
        containerPane.setTop(menuBarTitle());
       
        Scene scene = new Scene(containerPane, width, height);

        primaryStage.setScene(scene);
    }



   

   
    public void deleteProductScene(){

       
        BorderPane borderContainer = new BorderPane();

        VBox lableTextFieldContainer = new VBox();

        VBox topInfo = new VBox();

        VBox textFieldButtonContainer = new VBox();

        Label label = new Label("Enter the ID of the product");

        TextField idTextField = new TextField("");

        Button deleteButton = new Button("Delete");


        myInfo.setPadding(new Insets(5));

        topInfo.getChildren().addAll(menuBarTitle(),myInfo);
        topInfo.setAlignment(Pos.CENTER);

        borderContainer.setTop(topInfo);


        label.setFont(new Font("Tahoma",26));


        idTextField.setMaxWidth(200);
        idTextField.setFont(new Font("Segoe UI",23));


        deleteButton.setTextFill(Color.RED);
        deleteButton.setFont(new Font(18));


       
        deleteButton.setOnAction(e ->{
            validateid = false;
            VBox container;
            Label l = new Label("");
           
            if((!idTextField.getText().isEmpty()) && (idTextField.getText().matches("[0-9]+"))){
               
                id = Integer.parseInt(idTextField.getText());
               
                validateid = validateChoiceDelete(id);
               
                if(validateid == true){
                    container = pis.deleteProduct(id);
                    borderContainer.setBottom(container);
                   
                    idTextField.setText("");
                   
                    return;
                }else{
                   
                    l.setText("Deletion Canceled!");
                }
            }else if(idTextField.getText().isEmpty()) {
                l.setText("id must be not empty!");
            }else{
                l.setText("id must be a nmber!");
            }
            idTextField.setText("");
            container = setupTheErrorStyle(l);
            borderContainer.setBottom(container);
        });




        textFieldButtonContainer.setSpacing(20);
        textFieldButtonContainer.getChildren().addAll(label,idTextField,deleteButton);
        textFieldButtonContainer.setAlignment(Pos.CENTER);

        lableTextFieldContainer.getChildren().addAll(textFieldButtonContainer);
        lableTextFieldContainer.setAlignment(Pos.CENTER);



        borderContainer.setCenter(lableTextFieldContainer);


       
        Scene scene = new Scene(borderContainer,width,height);

       
        primaryStage.setScene(scene);




    }

   
    boolean choice;
    public boolean validateChoiceDelete(int id){
        choice = false;
       
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
       
        alert.setTitle("confirmation deletion");
       
        alert.setHeaderText("Are you sure you want to delete ID "+id);

       
        ButtonType yes = new ButtonType("Yes");
        ButtonType no = new ButtonType("No");

       
        alert.getButtonTypes().setAll(yes,no);

       
        alert.showAndWait().ifPresent(b ->{
           
            if(b == yes){
               
                choice = true;
               
            }else if(b == no){
               
                choice = false;
            }
        });

       
        return choice;
    }


}





























