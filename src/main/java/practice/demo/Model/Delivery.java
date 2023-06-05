package practice.demo.Model;

import java.io.Serializable;

import jakarta.json.JsonObject;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;



public class Delivery implements Serializable {
    
    @NotNull(message="you cant have no name")
    private String name;

    @NotNull(message="you homeless fuck")
    private String address;

    @NotNull(message="dont make me find you")
    @Size(min = 8, message="i will slap you")
    private String phone;

    private boolean isRush;
    
    private String comments;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public boolean isRush() {
        return isRush;
    }
    public void setRush(boolean isRush) {
        this.isRush = isRush;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

    public static Delivery fromJSON (JsonObject o) {
        
        Delivery d = new Delivery();
        d.setAddress(o.getString("address"));
        d.setComments(o.getString("comments"));
        d.setName(o.getString("name"));
        d.setPhone(o.getString("phone"));
        d.setRush(o.getBoolean("rush"));

        return d;
    }
}
