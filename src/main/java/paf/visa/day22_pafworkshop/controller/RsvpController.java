package paf.visa.day22_pafworkshop.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import paf.visa.day22_pafworkshop.exception.ResourceNotFoundException;
import paf.visa.day22_pafworkshop.model.Rsvp;
import paf.visa.day22_pafworkshop.service.RsvpService;

@RestController
@RequestMapping("/api")
public class RsvpController {

    @Autowired
    RsvpService rsvpService;
    
    @GetMapping("/rsvps")
    public ResponseEntity<List<Rsvp>> getAllRsvp() {
        List<Rsvp> rsvps = rsvpService.getAllRsvps();

        if(rsvps.isEmpty()) {
            throw new ResourceNotFoundException("No RSVPs found");
        }
        return ResponseEntity.ok().body(rsvps);
    }

    @GetMapping(path = "/rsvp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Rsvp>> getRsvpByName(@RequestParam(name = "q") String name) {
        Optional<List<Rsvp>> rsvpopt = rsvpService.getRsvpByName(name);
        if(!rsvpopt.isPresent()) {
            throw new ResourceNotFoundException("No RSVP under that name");
        }

        return ResponseEntity.ok().body(rsvpopt.get());
    }

    @PostMapping(path = "/rsvp", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addRsvp(@RequestBody String payload) {
        JsonObject body;
        try (InputStream is = new ByteArrayInputStream(payload.getBytes())) {
            JsonReader jr = Json.createReader(is);
            body = jr.readObject();
        }   catch (Exception ex) {
            body = Json.createObjectBuilder().add("error", ex.getMessage()).build();
            return ResponseEntity.internalServerError().body(body.toString());
        }

        Rsvp rsvp = Rsvp.createRsvpFromJson(body);
        boolean created = rsvpService.addRsvp(rsvp);
        if(!created) {
            return new ResponseEntity<String>("Encountered an error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<String>("Resource created successfully", HttpStatus.CREATED);
    }

    @PutMapping(path = "/rsvp/{email}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateRsvp(@PathVariable String email, @RequestBody String payload) {
        JsonObject body;
        try (InputStream is = new ByteArrayInputStream(payload.getBytes())) {
            JsonReader jr = Json.createReader(is);
            body = jr.readObject();
        }   catch (Exception ex) {
            body = Json.createObjectBuilder().add("error", ex.getMessage()).build();
            return ResponseEntity.internalServerError().body(body.toString());
        }

        Rsvp rsvp = Rsvp.createRsvpFromJson(body);
        boolean updated = rsvpService.updateRsvpByEmail(rsvp);
        if(!updated) {
            throw new ResourceNotFoundException("Email not found");
        }

        return new ResponseEntity<String>("RSVP updated", HttpStatus.CREATED);
    }

    @GetMapping("/rsvps/count")
    public ResponseEntity<String> countRsvps() {
        Integer count = rsvpService.countRsvps();
        return new ResponseEntity<String>("There are " + count + " RSVPs", HttpStatus.CREATED);
    }
}
