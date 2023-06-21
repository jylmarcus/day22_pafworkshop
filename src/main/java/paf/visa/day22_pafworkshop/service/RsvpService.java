package paf.visa.day22_pafworkshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import paf.visa.day22_pafworkshop.model.Rsvp;
import paf.visa.day22_pafworkshop.repository.RsvpRepository;

@Service
public class RsvpService {
    
    @Autowired
    RsvpRepository rsvpRepository;

    public List<Rsvp> getAllRsvps() {
        return rsvpRepository.getAllRsvp();
    }

    public Optional<List<Rsvp>> getRsvpByName(String name) {
        return rsvpRepository.getRsvpByName(name);
    }

    public boolean addRsvp(Rsvp rsvp) {
        return rsvpRepository.upsertRsvp(rsvp);
    }

    public boolean updateRsvpByEmail(Rsvp rsvp) {
        return rsvpRepository.updateRsvpByEmail(rsvp);
    }

    public int countRsvps() {
        return rsvpRepository.countRsvps();
    }
}
