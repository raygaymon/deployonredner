package practice.demo.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import practice.demo.Model.Delivery;
import practice.demo.Model.Order;
import practice.demo.Model.Pizza;
import practice.demo.Service.PizzaSevice;

@Controller
@RequestMapping(path="/")
public class PizzaController {
    
    @Autowired
    private PizzaSevice service;

    //create httpsession to invalidate, model to add attribute and create a new pizza
    @GetMapping(path="/")
    public String homepage (Model m, HttpSession sess){
        
        sess.invalidate();
        //the object cast into model is for homepage data-th-object to inject values into
        m.addAttribute("pizza", new Pizza());
        return "homepage";
    }

    //POSTMAP twice, once for pizza once for delivery
    @PostMapping(path="/pizza")
    public String pizzaPage (Model m, HttpSession session, @Valid Pizza p, BindingResult br) {
        //check bindingerror for errors
        if(br.hasErrors()) {
            return "homepage";
        }

        List<ObjectError> loe = service.validateOrder(p);
        if (!loe.isEmpty()) {
            
            for (ObjectError oe : loe) {
                br.addError(oe);
            }

            return "homepage";
        }

        session.setAttribute("pizza", p);
        m.addAttribute("delivery", new Delivery());

        return "delivery";

        //run validation to check for errors\
        //add pizza to session atrtibute
        //create new delivery to add to model attrbiute
    }

    @PostMapping (path="/delivery")
    public String deliveryPage (Model m, HttpSession session, @Valid Delivery d, BindingResult br){

        //check br for errors
        if (br.hasErrors()) {
            m.addAttribute("error", "FUCK YOU" );
            return "delivery";
        }

        //get a pizza object from the session
        Pizza p = (Pizza) session.getAttribute("pizza");

        //save order with gotted pizza and curretn delivery
        Order o = service.savePizzaOrder(p, d);

        //add to model attribute
        m.addAttribute("order", o);

        return "order";

    }

    @GetMapping(path="/delivery/{orderID}")
    public String orderPage (@PathVariable String orderID, Model m, HttpSession session){

        //get order through id
        Optional<Order> finalOrder = service.getOrderDetails(orderID);

        //add it to model
        m.addAttribute("order", finalOrder.get());
        //return the page
        return "order";
    }
}
