package models;

import io.ebean.Finder;
import io.ebean.Model;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name ="sessions")
public class Session extends Model {
    @Id
    public  Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    public User student_id;

    public Long tutor_id;
    @Column(name = "subjectDescription")
    public String subjectDescription;
    public String location;
    public LocalDateTime date_time;
    public String status;
    public Integer duration;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    public User tutor;


    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }


    public static Finder<Long, Session> find = new Finder<>(Session.class);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getStudent_id() {
        return student_id;
    }

    public void setStudent_id(User student_id) {
        this.student_id = student_id;
    }

    public Long getTutor_id() {
        return tutor_id;
    }

    public void setTutor_id(Long tutor_id) {
        this.tutor_id = tutor_id;
    }

    public String getSubjectDescription() {
        return subjectDescription;
    }

    public void setSubjectDescription(String subjectDescription) {
        this.subjectDescription = subjectDescription;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getDate_time() {
        return date_time;
    }

    public void setDate_time(LocalDateTime date_time) {
        this.date_time = date_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //    public static Finder<Long, Session> find = new Finder<>(Session.class);
//
    public static Finder<Long, Session> getFind() {
        return find;
    }

    public static void setFind(Finder<Long, Session> find) {
        Session.find = find;
    }

    public User getTutor() {
        return tutor;
    }

    public void setTutor(User tutor) {
        this.tutor = tutor;
    }
}
