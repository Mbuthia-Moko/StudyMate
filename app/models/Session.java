package models;

import io.ebean.Finder;
import io.ebean.Model;
import jakarta.persistence.*;
@Entity
@Table(name ="sessions")
public class Session extends Model {
    @Id
    public  Long id;

    @ManyToOne
    public User user;

    public String title;
    public String description;
    public String session_datetime;
    public Long duration_minutes;

    public static Finder<Long, Session> find = new Finder<>(Session.class);

    public static Finder<Long, Session> getFind() {
        return find;
    }

    public static void setFind(Finder<Long, Session> find) {
        Session.find = find;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSession_datetime() {
        return session_datetime;
    }

    public void setSession_datetime(String session_datetime) {
        this.session_datetime = session_datetime;
    }

    public Long getDuration_minutes() {
        return duration_minutes;
    }

    public void setDuration_minutes(Long duration_minutes) {
        this.duration_minutes = duration_minutes;
    }


}
