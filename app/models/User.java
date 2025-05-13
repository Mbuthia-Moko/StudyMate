package models;

import io.ebean.Finder;
import io.ebean.Model;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User extends Model {
    @Id
    public Long id;
    @Column(name = "full_name")
    public String name;
    public String email;
    public  String role;
    public String password;
    public String bio;
    public String subscription;

    @OneToMany(mappedBy = "tutor")
    public List<Session> tutorSessions;

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }


    @OneToMany(mappedBy = "student_id")
    public List<Session> studentSessions;

    public static Finder<Long, User> find = new Finder<>(User.class);

    //Constructor
    public User(){
    }

    public User(Long id, String name, String email, String role, String password, String bio){
        this.id =id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.password = password;
        this.bio = bio;
    }

    //Getters and Setters
    public Long getId(){
        return this.id;
    }
    public void setId(Long id){
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return this.role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static Finder<Long, User> getFind() {
        return find;
    }

    public static void setFind(Finder<Long, User> find) {
        User.find = find;
    }

    public static User findUser(String email, String password) {
        return User.find.query().where().eq("email", email).eq("password", password).setMaxRows(1).findOne();
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<Session> getTutorSessions() {
        return tutorSessions;
    }

    public void setTutorSessions(List<Session> tutorSessions) {
        this.tutorSessions = tutorSessions;
    }

    public List<Session> getStudentSessions() {
        return studentSessions;
    }

    public void setStudentSessions(List<Session> studentSessions) {
        this.studentSessions = studentSessions;
    }
}
