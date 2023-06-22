package paf.visa.day22_pafworkshop.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import paf.visa.day22_pafworkshop.model.Rsvp;

@Repository
public class RsvpRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String selectAllRsvpSql = "select rsvp_id, name, email, phone, confirmation_date, comments from rsvp";

    private final String selectRsvpByNameSql = "select * from rsvp where name = ?";

    private final String upsertRsvpSql = "replace into rsvp(name, email, phone, confirmation_date, comments) values (?, ?, ?, ?, ?)";

    private final String updateRsvpByEmailSql = "update rsvp set name = ?, phone = ?, confirmation_date = ?, comments = ? where email = ?";

    private final String countRsvpsSql = "select count(*) from rsvp";

    public List<Rsvp> getAllRsvp() {
        /*final List<Rsvp> result = jdbcTemplate.query(
                selectAllRsvpSql,
                (rs, rowNum) -> {
                    Rsvp rsvp = new Rsvp();
                    rsvp.setId(rs.getInt("rsvp_id"));
                    rsvp.setName(rs.getString("name"));
                    rsvp.setEmail(rs.getString("email"));
                    rsvp.setPhone(rs.getInt("phone"));
                    rsvp.setConfirmation(rs.getDate("confirmation_date"));
                    rsvp.setComments(rs.getString("comments")); 

                    
                });*/
        // return (Collections.unmodifiableList(result));
        List<Rsvp> rsvps = new ArrayList<>();
        rsvps = jdbcTemplate.query(selectAllRsvpSql, BeanPropertyRowMapper.newInstance(Rsvp.class));
        return rsvps;
    }

    public Optional<List<Rsvp>> getRsvpByName(String name) {
        /* final List<Rsvp> result = jdbcTemplate.query(
                selectRsvpByNameSql,
                (rs, rowNum) -> {
                    Rsvp rsvp = new Rsvp();
                    rsvp.setId(rs.getInt("rsvp_id"));
                    rsvp.setName(rs.getString("name"));
                    rsvp.setEmail(rs.getString("email"));
                    rsvp.setPhone(rs.getInt("phone"));
                    rsvp.setConfirmation(rs.getDate("confirmation_date"));
                    rsvp.setComments(rs.getString("comments"));
                    return rsvp;
                },
                name);
        return Optional.ofNullable(Collections.unmodifiableList(result)); */
        return Optional.ofNullable(jdbcTemplate.queryForList(selectRsvpByNameSql, Rsvp.class, name));
    }

    public boolean upsertRsvp(final Rsvp rsvp) {
        int added = jdbcTemplate.update(upsertRsvpSql,
                rsvp.getName(),
                rsvp.getEmail(),
                rsvp.getPhone(),
                rsvp.getConfirmation(),
                rsvp.getComments());
        return added > 0;
    }

    public boolean updateRsvpByEmail(final Rsvp rsvp) {
        int updated = jdbcTemplate.update(updateRsvpByEmailSql,
                rsvp.getName(),
                rsvp.getPhone(),
                rsvp.getConfirmation(),
                rsvp.getComments(),
                rsvp.getEmail());
        return updated > 0;
    }

    public int countRsvps() {
        int count = jdbcTemplate.queryForObject(countRsvpsSql, Integer.class);
        return count;
    }
}
