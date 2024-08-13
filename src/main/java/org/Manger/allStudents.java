package org.Manger;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class allStudents implements Handler {
    @Override
    public void handle(@NotNull Context ctx) throws Exception {

        List<StudentDTO> allStudents = getAllStudents();
        String slot1 = "4/19/2070,6.00AM-7:00AM";
        String slot2 = "4/19/2070,7.00AM-8:00AM";
        String slot3 = "4/19/2070,8.00AM-9:00AM";
        String slot4 = "4/19/2070,10.00AM-11:00AM";
        String slot5 = "4/19/2070,11.00AM-12:00AM";
        String slot6 = "4/19/2070,1.00PM-2:00PM";
        String html = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <style>\n" +
                "        body { background:#00688B; align-items:center; }\n" +
                "        table {\n" +
                "            width: 100%;\n" +
                "            border-collapse: collapse;\n" +
                "            margin-top: 20px;\n" +
                "            background-color: #FFFFFF;\n" +
                "        }\n" +
                "\n" +
                "        th, td {\n" +
                "            border: 1px solid #ddd;\n" +
                "            padding: 8px;\n" +
                "            text-align: left;\n" +
                "        }\n" +
                "\n" +
                "        th {\n" +
                "            background-color: #f2f2f2;\n" +
                "        }\n" +
                "\n" +
                "        tr:hover {\n" +
                "            background-color: #f5f5f5;\n" +
                "        }\n" +
                "    </style>\n" +
                "    <title>Students Registered</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<button onclick='back()'>Project Demo Registration</button>\n" +
                " <button class='cornFlower' onclick='changeColor(\"#6495ED\")'>Cornflower Blue Style</button>\n" +
                " <button class='default' onclick='changeColor(\"#00688B\")'>Light Blue Style</button>\n" +
                "\n" +
                "<h2>Students Registered</h2>\n" +
                "\n" +
                "<table>\n" +
                "    <thead>\n" +
                "        <tr>\n" +
                "            <th>ID</th>\n" +
                "            <th>First Name</th>\n" +
                "            <th>Last Name</th>\n" +
                "            <th>Project Title</th>\n" +
                "            <th>Email</th>\n" +
                "            <th>Phone Number</th>\n" +
                "            <th>Selected Slot</th>\n" +
                "        </tr>\n" +
                "    </thead>\n" +
                "    <tbody>\n";
        for (int i = 0; i < allStudents.size(); i++) {
            html = html + "<tr>\n" +
                    " <td>" + allStudents.get(i).getId() + "</td>\n" +
                    " <td>" + allStudents.get(i).getFirstname() + "</td>\n" +
                    " <td>" + allStudents.get(i).getLastname() + "</td>\n" +
                    " <td>" + allStudents.get(i).getTitle() + "</td>\n" +
                    " <td>" + allStudents.get(i).getEmail() + "</td>\n" +
                    " <td>" + allStudents.get(i).getTelephone() + "</td>\n";
            if (allStudents.get(i).getSelectedSlot() == 1) {
                html = html + " <td>" + slot1 + "</td>\n";
            } else if (allStudents.get(i).getSelectedSlot() == 2) {
                html = html + " <td>" + slot2 + "</td>\n";
            } else if (allStudents.get(i).getSelectedSlot() == 3) {
                html = html + " <td>" + slot3 + "</td>\n";
            } else if (allStudents.get(i).getSelectedSlot() == 4) {
                html = html + " <td>" + slot4 + "</td>\n";
            } else if (allStudents.get(i).getSelectedSlot() == 5) {
                html = html + " <td>" + slot5 + "</td>\n";
            } else if (allStudents.get(i).getSelectedSlot() == 6) {
                html = html + " <td>" + slot6 + "</td>\n";
            }
            html = html + "</tr>\n";
        }
        html = html +
                "    </tbody>\n" +
                "</table>\n" +
                "\n";
        String script = "<script>" +
                " function back(){" +
                "window.location.href = '/';\n}" +
                " function changeColor(color){" +
                "document.body.style.backgroundColor = color;\n}" +
                "</script>";
        html = html + script;
        html = html +
                "</body>\n" +
                "</html>\n";
        ctx.html(html);
    }

    private List<StudentDTO> getAllStudents() {
        List<StudentDTO> allStudents = new ArrayList<>(50);
        String id;
        String firstname;
        String lastname;
        String title;
        String email;
        String telephone;
        int selectedSlot;

        try (Connection connection = DatabaseConfig.getConnection()) {
            // Query to fetch all students from the database
            String query = "SELECT * FROM students"; // Replace 'id' with the actual column name in your database
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                // Execute the query and retrieve the result set
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        // Add each student ID to the list
                        id = resultSet.getString("id");
                        firstname = resultSet.getString("firstname");
                        lastname = resultSet.getString("lastname");
                        title = resultSet.getString("project_title");
                        email = resultSet.getString("email");
                        telephone = resultSet.getString("phone_number");
                        selectedSlot = resultSet.getInt("slot");
                        StudentDTO st = new StudentDTO(id, firstname, lastname, title, email, telephone, selectedSlot);
                        allStudents.add(st);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors
        }

        return allStudents;
    }
}
