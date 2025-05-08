package controllers;

import models.User;
import play.mvc.*;
/* import views.html.*; */

import java.util.List;

//import javax.xml.transform.Result;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok (views.html.index.render());
    }

    public Result FAQ() {
        return ok(views.html.FAQ.render());
    }

    public Result about(){
        return ok(views.html.about.render());
    }

    public Result login(){return ok(views.html.login.render());}

    public Result signup(){return ok(views.html.signup.render());}

    public Result forgotPassword(){return ok(views.html.forgotPassword.render());}

    public Result resetPassword(){return ok(views.html.resetPassword.render());}

    public Result home(){return ok(views.html.home.render());}

    public Result learn(){return ok(views.html.learn.render());}

    public Result teach(){return ok(views.html.teach.render());}

    public Result notifications(){return ok(views.html.notifications.render());}

    public Result sessions(){return ok(views.html.sessions.render());}

    public Result settings(){
/*
    Static user addition to db example, save acts as insert if data doesn't exist & update if it does exist
        User u = new User();
        u.setId(1L);
        u.setEmail("mbuthiamoko@gmail.com");
        u.setName("Mbuthia Moko");
        u.setRole("Student");
        u.setPassword("C0nvincingPassw0rd");
        u.save();

        User u2 = new User(1L,"ALex", "alex@gmail.com", "student", "AlexL0v3sG0D");
        u2.save();
*/

        List<User> users = User.find.all();
//        User user1 = User.find.byId(1L);

        return ok(views.html.settings.render(users));
    }

    public Result pricing(){return ok(views.html.pricing.render());}

    public Result sessionDetails(){return ok(views.html.sessionDetails.render());}


}
