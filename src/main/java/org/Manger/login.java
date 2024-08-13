package org.Manger;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class login implements Handler {
    public static final String URL = "/";
    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        String html = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "\t<title>Login</title>\n" +
                "\t<link rel=\"stylesheet\" type=\"text/css\" href=\"all.min.css\">\n" +
                "\t<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">\n" +
                "</head>\n" +
                "<body>\n" +
                " <div class=\"container\">\n" +
                " \t<div class=\"header\">\n" +
                " \t\t<h1>Login</h1>\n" +
                " \t</div>\n" +
                " \t<div id='loginAcc' class=\"main\">\n" +
                " \t\t<form id='loginForm' action=\"/authenticate\" method=\"post\">\n" +
                " \t\t\t<span>\n" +
                " \t\t\t\t<i class=\"\"></i>\n" +
                " \t\t\t\t<input type=\"email\" placeholder=\"Email\" name=\"username\">\n" +
                " \t\t\t</span><br>\n" +
                " \t\t\t<span>\n" +
                " \t\t\t\t<i class=\"\"></i>\n" +
                " \t\t\t\t<input type=\"password\" placeholder=\"Id\" name=\"password\">\n" +
                " \t\t\t</span><br>\n" +
                " <a  href='/register'>Create new Student</a>"+
                " \t\t\t\t<button id='loginButton'>Login</button>\n" +
                " \t\t</form>\n" +
                " \t\t<div id='loginStatus'></div>\n" +
                " </div>\n" +
                "</div>\n";

        String script = "<script>\n" +
                "    document.addEventListener('DOMContentLoaded', function () {\n" +
                "        var loginForm = document.getElementById('loginForm');\n" +
                "        var loginStatusElement = document.getElementById('loginStatus');\n" +
                "        loginForm.addEventListener('submit', function (event) {\n" +
                "            event.preventDefault();\n" +
                "            var formData = new FormData(loginForm);\n" +
                "            fetch('/authenticate', {\n" +
                "                method: 'POST',\n" +
                "                body: formData\n" +
                "            })\n" +
                "            .then(response => response.text())\n" +
                "            .then(data => {\n" +
                "                if (data.includes('Authentication successful')) {\n" +
                "                    loginStatusElement.innerHTML = 'Authentication successful!';\n" +
                "                    window.location.href = '/allStudents';\n" +
                "                } else {\n" +
                "                    loginStatusElement.innerHTML = 'Error logging in. Please check your credentials.';\n" +
                "                }\n" +
                "            })\n" +
                "            .catch(error => {\n" +
                "                console.error('Error:', error);\n" +
                "            });\n" +
                "        });\n" +
                "    });\n" +
                "</script>\n";
        html = html + script;
        html = html +
                "</body>\n" +
                "</html>";
        ctx.html(html);
    }
}
