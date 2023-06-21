package paf.visa.day22_pafworkshop.model;

import java.io.Serializable;
import java.sql.Date;

import jakarta.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rsvp implements Serializable{
    private Integer id;
    private String name;
    private String email;
    private Integer phone;
    private Date confirmation;
    private String comments;

    public static Rsvp createRsvpFromJson(JsonObject jsonObject) {
        Rsvp rsvp = new Rsvp();
        rsvp.setName(jsonObject.getString("name"));
        rsvp.setEmail(jsonObject.getString("email"));
        rsvp.setPhone(jsonObject.getInt("phone"));
        rsvp.setConfirmation(Date.valueOf(jsonObject.getString("confirmation_date")));
        rsvp.setComments(jsonObject.getString("comments"));

        return rsvp;
    }
}
