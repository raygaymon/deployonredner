package practice.demo.Model;

import java.io.Serializable;
import java.io.StringReader;


import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;

public class Order implements Serializable {

    //total cost of order is here also
    public static final long serialVersionUID = 1L;
    private double totalCost;
    private String orderId;
    private Pizza pizza;
    private Delivery delivery;

    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public Pizza getPizza() {
        return pizza;
    }
    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }
    public Delivery getDelivery() {
        return delivery;
    }
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }
        public double getTotalCost() {
        return totalCost;
    }
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public Order (Pizza p, Delivery d){
        this.pizza = p;
        this.delivery = d;
    }

    public Order (){

    }

    //generate methods for getting other models' attributes here
    //use double getter method and theres no input field
    public String getName () {return this.getDelivery().getName();}
    public String getAddress () {return this.getDelivery().getAddress();}
    public String getPhone () {return this.getDelivery().getPhone();}
    public boolean getRush () {return this.getDelivery().isRush();}
    public String getComments () {return this.getDelivery().getComments();}
    public String getPizzaName () {return this.getPizza().getPizzaName();}
    public String getSize () {return this.getPizza().getSize();}
    public int getQuantity () {return this.getPizza().getQuantity();}

    //calculate the totalcost here
    public double getPizaCost(){

        //prof calculated cost in service
        double pizzaCost = this.getTotalCost();

        this.setTotalCost(pizzaCost);
        if (getRush()) {
            pizzaCost += 2;
            this.setTotalCost(pizzaCost);
        }
        return pizzaCost;
    }

    //jsonreader method creates json from input string to translate into object in fromjson method

    public static JsonObject JSONReader(String json) {
        //create json reader by adding stringreader inside
        //returns a jsonobject
        JsonReader jor = (JsonReader) Json.createReader(new StringReader(json));
        return jor.readObject();
    }

    //input parameter is string
    public static Order fromJSON(String s) {

        //wrong, create new order that takes in both pizza and delivery after getting them from json
        
        JsonObject o = JSONReader(s);
        Pizza p = Pizza.fromJSON(o);
        Delivery d = Delivery.fromJSON(o);
        Order ord = new Order(p , d);
        ord.setDelivery(d);
        ord.setPizza(p);
        ord.setOrderId(o.getString("orderId"));
        ord.setTotalCost(o.getJsonNumber("totalCost").doubleValue());
        return ord;
    }

    public JsonObject toJSON () {

        //wrongly done
        //for this, add every single attribute from all the models into the json project here
        //dont add order as argument, use this.get...
        return Json.createObjectBuilder()
            .add("name", this.getName())
            .add("address", this.getAddress())
            .add("phone", this.getPhone())
            .add("rush", this.getRush())
            .add("comments", this.getComments())
            .add("pizzaName", this.getPizzaName())
            .add("size", this.getSize())
            .add("quantity", this.getQuantity())
            .add("totalCost", this.getTotalCost())
            .build();

    }

}

