package practice.demo.Model;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class Pizza implements Serializable{
    
    @NotNull(message="please pick a pizza you ufck")
    private String pizzaName;

    @NotNull(message="pick a size you fuck")
    private String size;

    @Min(value = 1, message="you want 0 pizzas you order for fuck")
    private int quantity;

    public String getPizzaName() {
        return pizzaName;
    }
    public void setPizzaName(String pizzaName) {
        this.pizzaName = pizzaName;
    }
    public String getSize() {
        return size;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Pizza () {

    }

    public static Pizza fromJSON(JsonObject o) {

        //wrong, no need to create reader 
        Pizza p = new Pizza();
        p.setPizzaName(o.getString("pizzaName"));
        p.setSize(o.getString("size"));
        p.setQuantity(o.getJsonNumber("quantity").intValue());
        return p;

    }
    
}
