package controllers;

import models.Session;
import models.User;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.*;
/* import views.html.*; */

import javax.inject.Inject;
import java.util.List;

//import javax.xml.transform.Result;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {
    private FormFactory formFactory;
    @Inject
    public HomeController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok (views.html.index.render());
    }

    public Result logout(Http.Request request) {
        return redirect(routes.HomeController.index()).withNewSession();
    }

    public Result FAQ() {
        return ok(views.html.FAQ.render());
    }

    public Result about(){
        return ok(views.html.about.render());
    }

    public Result login(Http.Request request){return ok(views.html.login.render(request));}

    public Result signup(Http.Request request){return ok(views.html.signup.render(request));}

    public Result forgotPassword(){return ok(views.html.forgotPassword.render());}

    public Result resetPassword(){return ok(views.html.resetPassword.render());}

    public Result home(Http.Request request){
        String email = request.session().get("user").get();
        User user = User.find.query().where().eq("email", email).setMaxRows(1).findOne();

        List<Session> sessions = Session.find.query().where().eq("user.id", user.id).findList();
//        List<User> users = User.find.all();
//        List<Session> sessions = Session.find.all();

        return ok(views.html.home.render(user,sessions));
    }

    public Result learn(){
        String userType = "tutor";
        List <User> tutors = User.find.query().where().eq("role", userType).findList();

//        List<Session> sessions = Session.find.query().where().eq("user.id", user.id).findList();

        return ok(views.html.learn.render(tutors));
    }

    public Result teach(){return ok(views.html.teach.render());}

    public Result notifications(Http.Request request){
        String email = request.session().get("user").get();
        User user = User.find.query().where().eq("email", email).setMaxRows(1).findOne();

        List<Session> sessions = Session.find.query().where().eq("user.id", user.id).findList();
        return ok(views.html.notifications.render(sessions));
    }

    public Result sessions(Http.Request request){
        String email = request.session().get("user").get();
        User user = User.find.query().where().eq("email", email).setMaxRows(1).findOne();

        List<Session> sessions = Session.find.query().where().eq("user.id", user.id).findList();
        return ok(views.html.sessions.render(sessions));
    }

    public Result settings(Http.Request request){
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
        String email = request.session().get("user").get();
        User user = User.find.query().where().eq("email", email).setMaxRows(1).findOne();

        List<Session> sessions = Session.find.query().where().eq("user.id", user.id).findList();
        List<User> users = User.find.all();
//        User user1 = User.find.byId(1L);

        return ok(views.html.settings.render(user));
    }

    public Result pricing(){return ok(views.html.pricing.render());}

    public Result sessionDetails(Http.Request request){
        String email = request.session().get("user").get();
        User user = User.find.query().where().eq("email", email).setMaxRows(1).findOne();

        List<Session> sessions = Session.find.query().where().eq("user.id", user.id).findList();
        return ok(views.html.sessionDetails.render(sessions));
    }

    public Result loginUser(Http.Request request) {
        DynamicForm data = formFactory.form().bindFromRequest(request);
        String email = data.get("email");
        String password = data.get("password");
        if(email.isEmpty() || password.isEmpty()) {
            return badRequest(views.html.login.render(request)).flashing("error", "Wrong Username or Password");
        }

        User user = User.findUser(email, password);
        if (user != null) {
            return redirect(routes.HomeController.home()).addingToSession(request,"user", user.email);
        }

        return badRequest(views.html.login.render(request)).flashing("error", "Wrong Username or Password");
    }

    public Result signupUser(Http.Request request) {
        DynamicForm data = formFactory.form().bindFromRequest(request);

        String name = data.get("name");
        String email = data.get("email");
        String password = data.get("password");
        String userType = data.get("userType");
        if(email.isEmpty() || password.isEmpty()) {
            return badRequest(views.html.signup.render(request)).flashing("error", "Wrong Username or Password");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(userType);
        user.save();


//        User user1 = User.find.byId(1L);
//        List<User> users = User.find.query().where().eq("role", "student").findList();

        return redirect(routes.HomeController.login());

    }
}
