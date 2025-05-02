package controllers;

import play.mvc.*;

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

    public Result settings(){return ok(views.html.settings.render());}

    public Result pricing(){return ok(views.html.pricing.render());}
}
