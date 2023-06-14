import javafx.beans.property.*;
//01
//crate Product class
public class Product {

    //initilaze product variables of type property
    SimpleIntegerProperty id;
    SimpleStringProperty type;
    SimpleStringProperty model;
    SimpleFloatProperty price;
    SimpleIntegerProperty count;
    SimpleStringProperty date;


    //create Product constructor to assign retrieved data from database to its specific variable
    public Product(int id,String type, String model, float price, int count, String date) {
        //create bew instance of property and assign it to each var
        this.id = new SimpleIntegerProperty(id);
        this.type = new SimpleStringProperty(type);
        this.model = new SimpleStringProperty(model);
        this.price = new SimpleFloatProperty(price);
        this.count = new SimpleIntegerProperty(count);
        this.date = new SimpleStringProperty(date);
    }

    //get the id
    public int getId() {
        return id.get();
    }

    //....
    public String getType() {
        return type.get();
    }


    //....
    public String getModel() {
        return model.get();
    }


    public float getPrice() {
        return price.get();
    }


    public int getCount() {
        return count.get();
    }

    public String getDate() {
        return date.get();
    }

}
