package org.Manger;

import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;


public class App {
    public static final int         JAVALIN_PORT    = 7001;
    public static final String      CSS_DIR         = "css/";
    public static final String      IMAGES_DIR      = "images/";
    public static void main(String[] args) {
        // Create our HTTP server and listen in port 7000
        Javalin app = Javalin.create(config -> {
            config.registerPlugin(new RouteOverviewPlugin("/help/routes"));

            // Uncomment this if you have files in the CSS Directory
            config.addStaticFiles(CSS_DIR);

            // Uncomment this if you have files in the Images Directory
            config.addStaticFiles(IMAGES_DIR);
        }).start(JAVALIN_PORT);


        // Configure Web Routes
        configureRoutes(app);
    }
    public static void configureRoutes(Javalin app) {
        // All webpages are listed here as GET pages
        app.get(login.URL, new login());
        app.get(register.URL, new register());
        app.get("/allStudents", new allStudents());

        // Add / uncomment POST commands for any pages that need web form POSTS
         app.post("/authenticate", new loginAuth());
         app.post(register.URL, new registerNewStudent());
        // app.post(PageST2A.URL, new PageST2A());
        // app.post(PageST2B.URL, new PageST2B());
        // app.post(PageST3A.URL, new PageST3A());
        // app.post(PageST3B.URL, new PageST3B());


    }
}
