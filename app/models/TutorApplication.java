package models;

import io.ebean.Finder;
import io.ebean.Model;
import jakarta.persistence.*;

@Entity
@Table(name = "tutor_application")
public class TutorApplication extends Model {
    @Id
    public Long id;

    public String tutor_name;
    public Long user_id;
    public String subject_expertise;
    public String bio;
    public String tutor_email;
    public String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTutor_email() {
        return tutor_email;
    }

    public void setTutor_email(String tutor_email) {
        this.tutor_email = tutor_email;
    }


    public String getTutor_name() {
        return tutor_name;
    }

    public void setTutor_name(String tutor_name) {
        this.tutor_name = tutor_name;
    }

    public static Finder<Long, TutorApplication> find = new Finder<>(TutorApplication.class);
    public static Finder<Long, TutorApplication> getFind() {
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

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getSubject_expertise() {
        return subject_expertise;
    }

    public void setSubject_expertise(String subject_expertise) {
        this.subject_expertise = subject_expertise;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

}
