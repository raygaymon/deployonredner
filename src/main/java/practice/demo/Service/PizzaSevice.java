package practice.demo.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.annotation.ObjectIdGenerators.UUIDGenerator;

import practice.demo.Model.Delivery;
import practice.demo.Model.Order;
import practice.demo.Model.Pizza;
import practice.demo.Repo.PizzaRepo;

@Service
public class PizzaSevice {
    
    @Autowired
    private PizzaRepo repo;

    //create final lists for pizza types and sizes
    //THESE MUST BE STATIC 
    private static final String[] PIZZA_NAMES = { "bella", "margherita", "marinara", "spianatacalabrese", "trioformaggio"};
    private static final String[] PIZZA_SIZES = { "sm", "md", "lg"};
    
    //create them as sets to instantiate later in pizzaservice constructor
    private final Set<String> pizzaNames;
    private final Set<String> pizzaSize;

    //the final arrays are converted into lists then hashsets
    public PizzaSevice() {
        pizzaNames = new HashSet<String>(Arrays.asList(PIZZA_NAMES));
        pizzaSize = new HashSet<String>(Arrays.asList(PIZZA_SIZES));
    }

    //getorder from redis
    public Optional<Order> getOrderDetails (String orderId) {
        return this.repo.getOrderDetails(orderId);
    }

    //createorder method by creating a new order object and generating a new ID
    public Order createPizzaOrder(Pizza p, Delivery d){
        Order o = new Order();
        String id = UUID.randomUUID().toString();
        o.setOrderId(id.substring(0, 8));
        return o;
        
    }
    //calculate the cost of pizzas
    public static double calculateCost (Order o) {

        double totalCost = 0;
        String pizzaName = o.getPizzaName();
        switch (pizzaName) {
            case "bella":
                totalCost += 22;
                break;
            case "margarhita":
                totalCost += 25;
                break;
            case "marinara":
                totalCost += 30;
                break;
            case "spianatacalabrese":
                totalCost += 30;
                break;
            case "trioformaggio":
                totalCost += 30;
                break;
        }
        switch (o.getSize()) {
            case "md":
                totalCost *= 1.2;
                break;
            case "lg":
                totalCost *= 1.5;
                break;
        }

        //stupid you forgot to multiply by quantity


        totalCost *= o.getQuantity();

        //remember to set the totalcost of pizza after calc
        o.setTotalCost(totalCost);
        return totalCost;

    }

    //save the order by creating a new order and saving it into redis
    //save order requires a pizza and a delivery to save stupid
    public Order savePizzaOrder (Pizza p, Delivery d) {
        Order o = createPizzaOrder(p , d);
        calculateCost(o);
        repo.savePizza(o);
        return o;
    }

    //order validation with objecterror list and fielderror
    public List<ObjectError> validateOrder (Pizza p) {
        //create a new list of oes then check
        List<ObjectError> loe = new LinkedList<>();
        FieldError fe;

        if(!pizzaNames.contains(p.getPizzaName())) {
            fe = new FieldError("pizza", "pizzaName", "no such pizza dickhead");
            loe.add(fe);
        }
        if(!pizzaSize.contains(p.getSize())) {
            fe = new FieldError("pizza", "size", "no such size dickhead"); 
            loe.add(fe);
        }   

        return loe;
    }
    //->check the service constructor lists to see if the pizza tpyes and sizes are invalid


}
