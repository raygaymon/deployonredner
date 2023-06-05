package practice.demo.Repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import practice.demo.Model.Order;

@Repository
public class PizzaRepo {
    
    @Autowired 
    RedisTemplate<String, String> template;

    public void savePizza(Order o) {
        //half correct, 2nd thing add the json of order
        template.opsForValue().set(o.getOrderId(), o.toJSON().toString());
    }

    //return optional order of a json
    public Optional<Order> getOrderDetails(String orderID) {
        String json = template.opsForValue().get(orderID);

        if (json == null || json.trim().length() <= 1) {

            return Optional.empty();
        }

        return Optional.of(Order.fromJSON(json));
    }
}
