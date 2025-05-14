package controllers;

import models.Session;
import models.TutorApplication;
import models.User;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.*;
/* import views.html.*; */

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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

        List<Session> sessions = Session.find.query().where().eq("student_id", user).findList();
//        List<User> users = User.find.all();
//        List<Session> sessions = Session.find.all();

        return ok(views.html.home.render(user,sessions, request));
    }

    public Result learn(){
        String userType = "tutor";
        List <User> tutors = User.find.query().where().eq("role", userType).findList();

//        List<Session> sessions = Session.find.query().where().eq("user.id", user.id).findList();

        return ok(views.html.learn.render(tutors));
    }

    public Result teach(Http.Request request){
        String email = request.session().get("user").get();
        TutorApplication tutor = TutorApplication.find.query().where().eq("tutor_email", email).setMaxRows(1).findOne();
        User user = User.find.query().where().eq("email", email).findOne();
        List<Session> sessions = Session.find.query().fetch("student_id") .where().eq("tutor_id", user.id).findList();

        return ok(views.html.teach.render(request, tutor, user, sessions));
    }

    public Result notifications(Http.Request request){
        String email = request.session().get("user").get();
        User user = User.find.query().where().eq("email", email).setMaxRows(1).findOne();

        List<Session> sessions = Session.find.query().where().eq("student_id", user).findList();
        return ok(views.html.notifications.render(sessions));
    }

    public Result sessions(Http.Request request){
        String email = request.session().get("user").get();
        User user = User.find.query().where().eq("email", email).setMaxRows(1).findOne();

        List<Session> sessions = Session.find.query().where().eq("student_id", user).findList();
        List <Session> teacherSessions = Session.find.query().where().eq("tutor_id", user.getId()).findList();
        return ok(views.html.sessions.render(sessions, teacherSessions));
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

//        List<Session> sessions = Session.find.query().where().eq("user.id", user.id).findList();
//        List<User> users = User.find.all();
//        User user1 = User.find.byId(1L);

        return ok(views.html.settings.render(user));
    }

    public Result pricing(Http.Request request){
        String email = request.session().get("user").get();
        User user = User.find.query().where().eq("email", email).setMaxRows(1).findOne();
        return ok(views.html.pricing.render(user));
    }
    public Result standardSubscription(Http.Request request){
        String email = request.session().get("user").get();
        User user = User.find.query().where().eq("email", email).setMaxRows(1).findOne();

        user.setSubscription("standard, Ksh700/=");
        user.update();
        return redirect(routes.HomeController.home()).flashing("standardSubscription", "You are now on standard subscription");
    }
    public Result freeSubscription(Http.Request request){
        DynamicForm data = formFactory.form().bindFromRequest(request);
        String email = request.session().get("user").get();
        User user = User.find.query().where().eq("email", email).setMaxRows(1).findOne();

        user.setSubscription("free, Ksh 0/=");
        user.update();
        return redirect(routes.HomeController.home()).flashing("freeSubscription", "You are now on free subscription");
    }
    public Result premiumSubscription(Http.Request request){
        DynamicForm data = formFactory.form().bindFromRequest(request);
        String email = request.session().get("user").get();
        User user = User.find.query().where().eq("email", email).setMaxRows(1).findOne();

        user.setSubscription("premium, Ksh1,500/=");
        user.update();
        return redirect(routes.HomeController.home()).flashing("premiumSubscription", "You are now on premium subscription");
    }

    public Result sessionDetails(Long SessionId, Http.Request request){
        String email = request.session().get("user").get();
        User user = User.find.query().where().eq("email", email).setMaxRows(1).findOne();
        Session sessionDetails = Session.find.query().fetch("tutor").fetch("student_id").where().eq("id", SessionId).findOne();

        return ok(views.html.sessionDetails.render(sessionDetails));
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
            if (Objects.equals(user.role, "tutor")){
                return redirect(routes.HomeController.home()).addingToSession(request,"user", user.email);
            }
            if (Objects.equals(user.role, "admin")){
                return redirect(routes.HomeController.admin()).addingToSession(request,"user", user.email);
            }
            if (Objects.equals(user.role, "student")){
                return redirect(routes.HomeController.home()).addingToSession(request,"user", user.email);
            }
        }

        return redirect(routes.HomeController.login()).flashing("error", "Wrong Username or Password");
    }

    public Result signupUser(Http.Request request) {
        DynamicForm data = formFactory.form().bindFromRequest(request);

        String name = data.get("name");
        String email = data.get("email");
        String password = data.get("password");
        String userType = data.get("userType");
        String bio = data.get("bio");
        if(email.isEmpty() || password.isEmpty()) {
            return redirect(routes.HomeController.signup()).flashing("error", "Empty Username or Password");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(userType);
        user.setBio(bio);
        user.save();


//        User user1 = User.find.byId(1L);
//        List<User> users = User.find.query().where().eq("role", "student").findList();

        return redirect(routes.HomeController.pricing()).addingToSession(request,"user", user.email);

    }
    public Result tutorApplication(Http.Request request){
        DynamicForm data = formFactory.form().bindFromRequest(request);
        String email = request.session().get("user").get();
        User user = User.find.query().where().eq("email", email).setMaxRows(1).findOne();
        List <Session> sessions = Session.find.query().where().eq("tutor_id", user.id).findList();


        Long User_id = user.id;
        String name = user.name;
        String subjects = data.get("subjects");
        String bio = data.get("tutorBio");
        String email1 = user.email;


        TutorApplication tutor = new TutorApplication();

        tutor.setTutor_name(name);
        tutor.setUser_id(User_id);
        tutor.setSubject_expertise(subjects);
        tutor.setBio(bio);
        tutor.setTutor_email(email1);
        tutor.setStatus("pending");
        tutor.save();
        return ok(views.html.teach.render(request, tutor, user, sessions));
    }
    public Result admin(){
        List<User> users = User.find.all();
        List<TutorApplication> tutors = TutorApplication.find.all();
        return ok(views.html.admin.render(users, tutors));
    }
    public Result createSession(Long tutorId, Http.Request request){
        //User tutor = User.find.byId(tutorId);

        String userType = "tutor";
         User tutor = User.find.query().where().eq("id", tutorId).findOne();
        return  ok(views.html.createSession.render(request,tutor));
    }

    public Result submitSession(Http.Request request){
        DynamicForm data = formFactory.form().bindFromRequest(request);

        String email = request.session().get("user").get();
        User user = User.find.query().where().eq("email", email).setMaxRows(1).findOne();

        Long student_id = user.id;
        Long tutor_id = Long.valueOf(data.get("tutorId"));
        String subjectDescription = data.get("topic");
        String location = data.get("location");
        LocalDateTime date_time = LocalDateTime.parse(data.get("dateTime"));
        String status = data.get("status");
        Integer duration = Integer.valueOf(data.get("duration"));

        Session session = new Session();

        session.setStudent_id(user);
        session.setTutor_id(tutor_id);
        session.setSubjectDescription(subjectDescription);
        session.setLocation(location);
        session.setDate_time(date_time);
        session.setStatus(status);
        session.setDuration(duration);

        session.save();

        List<Session> sessions = Session.find.query().where().eq("student_id", user).findList();
//        return ok(views.html.sessions.render(sessions));
        return redirect(routes.HomeController.sessions());
    }
    public Result approveTutor(Long tutorId){
        List<User> users = User.find.all();
        List<TutorApplication> tutors = TutorApplication.find.all();
        User user = User.find.query().where().eq("id", tutorId).findOne();
//        Long currentTutorId = TutorApplication.find.byId(tutorId);
        TutorApplication tutor = TutorApplication.find.query().where().eq("user_id", tutorId).findOne();
        String tutorStatus = "approved";

        tutor.setStatus(tutorStatus);
        tutor.update();

        String role = "tutor";
        user.setRole(role);

        user.update();
//        return ok(views.html.admin.render(users, tutors));
        return redirect(routes.HomeController.admin());
    }
    public Result rejectTutor(Long tutorId){
        List<User> users = User.find.all();
        List<TutorApplication> tutors = TutorApplication.find.all();
        User user = User.find.query().where().eq("id", tutorId).findOne();
//        Long currentTutorId = TutorApplication.find.byId(tutorId);
        TutorApplication tutor = TutorApplication.find.query().where().eq("user_id", tutorId).findOne();
        String tutorStatus = "rejected";

        tutor.setStatus(tutorStatus);
        tutor.update();
        String role = "student";
        user.setRole(role);
        user.update();
//        return ok(views.html.admin.render(users, tutors));
        return redirect(routes.HomeController.admin());
    }
    public Result denyTutor(Long tutorId){
        List<User> users = User.find.all();
        List<TutorApplication> tutors = TutorApplication.find.all();
        User user = User.find.query().where().eq("id", tutorId).findOne();
//        Long currentTutorId = TutorApplication.find.byId(tutorId);
        TutorApplication tutor = TutorApplication.find.query().where().eq("user_id", tutorId).findOne();
        String tutorStatus = "denied";

        tutor.setStatus(tutorStatus);
        tutor.update();
        String role = "student";
        user.setRole(role);
        user.update();
//        return ok(views.html.admin.render(users, tutors));
        return redirect(routes.HomeController.admin());
    }
    public Result makeAdmin(Long userId){
        List<User> users = User.find.all();
        List<TutorApplication> tutors = TutorApplication.find.all();
        User user = User.find.query().where().eq("id", userId).findOne();

        String role = "admin";
        user.setRole(role);
        user.update();
//        return ok(views.html.admin.render(users, tutors));
        return redirect(routes.HomeController.admin());
    }
    public Result deleteUser(Long userId){
        List<User> users = User.find.all();
        List<TutorApplication> tutors = TutorApplication.find.all();

        User user = User.find.query().where().eq("id", userId).findOne();
        user.delete();
//        return ok(views.html.admin.render(users, tutors));
        return redirect(routes.HomeController.admin());
    }
    public Result adminSettings(Http.Request request){
        String email = request.session().get("user").get();
        User user = User.find.query().where().eq("email", email).setMaxRows(1).findOne();
        return ok(views.html.adminSettings.render(user));
    }
    public Result acceptRequest(Long sessionId){
        List<Session> sessions = Session.find.all();
        Session session = Session.find.query().where().eq("id", sessionId).setMaxRows(1).findOne();

        session.setStatus("confirmed");
        session.update();

        return redirect(routes.HomeController.teach());

    }
    public Result refuseRequest(Long sessionId){
        List<Session> sessions = Session.find.all();
        Session session = Session.find.query().where().eq("id", sessionId).setMaxRows(1).findOne();

        session.setStatus("rejected");
        session.update();

        return redirect(routes.HomeController.teach());

    }
    public Result sessionDetailsTutorView(Long SessionId, Http.Request request){
        String email = request.session().get("user").get();
        User user = User.find.query().where().eq("email", email).setMaxRows(1).findOne();
        Session sessionDetails = Session.find.query().fetch("tutor").fetch("student_id").where().eq("id", SessionId).findOne();
        return ok(views.html.sessionDetailsTutorView.render(sessionDetails));
    }

    public Result cancelRequestTutor(Long sessionId){
        List<Session> sessions = Session.find.all();
        Session session = Session.find.query().where().eq("id", sessionId).setMaxRows(1).findOne();

        session.setStatus("cancelled");
        session.update();

        return redirect(routes.HomeController.sessionDetailsTutorView(sessionId));
    }
    public Result completeRequestTutor(Long sessionId){
        List<Session> sessions = Session.find.all();
        Session session = Session.find.query().where().eq("id", sessionId).setMaxRows(1).findOne();

        session.setStatus("completed");
        session.update();

        return redirect(routes.HomeController.sessionDetailsTutorView(sessionId));
    }

    public Result acceptRequestTutor(Long sessionId){
        List<Session> sessions = Session.find.all();
        Session session = Session.find.query().where().eq("id", sessionId).setMaxRows(1).findOne();

        session.setStatus("confirmed");
        session.update();

        return redirect(routes.HomeController.sessionDetailsTutorView(sessionId));

    }

    public Result rejectRequestTutor(Long sessionId){
        List<Session> sessions = Session.find.all();
        Session session = Session.find.query().where().eq("id", sessionId).setMaxRows(1).findOne();

        session.setStatus("rejected");
        session.update();

        return redirect(routes.HomeController.sessionDetailsTutorView(sessionId));
    }

    public Result cancelRequestStudent(Long sessionId){
        List<Session> sessions = Session.find.all();
        Session session = Session.find.query().where().eq("id", sessionId).setMaxRows(1).findOne();

        session.setStatus("cancelled");
        session.update();

        return redirect(routes.HomeController.sessionDetails(sessionId));
    }
}
