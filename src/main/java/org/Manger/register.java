package org.Manger;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class register implements Handler {
    public static final String URL = "/register";
    // Set to store registered IDs
    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        // Fetch the available slots from the database
        int availableSlots1 = getAvailableSlotsFromDatabase(1);
        int availableSlots2 = getAvailableSlotsFromDatabase(2);
        int availableSlots3 = getAvailableSlotsFromDatabase(3);
        int availableSlots4 = getAvailableSlotsFromDatabase(4);
        int availableSlots5 = getAvailableSlotsFromDatabase(5);
        int availableSlots6 = getAvailableSlotsFromDatabase(6);

        List<StudentDTO> allStudents = getAllStudents();

        String html = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "\t<title>Registration</title>\n" +
                "\t<link rel=\"stylesheet\" type=\"text/css\" href=\"all.min.css\">\n" +
                "\t<link rel=\"stylesheet\" type=\"text/css\" href=\"style_reg.css\">\n" +
                "</head>\n" +
                "<body>\n" +
                " <div class=\"container\">\n" +
                " \t<div class=\"header\">\n" +
                " \t\t<h1>Register</h1>\n" +
                " \t</div>\n" +
                " \t<div id='loginAcc' class=\"main\">\n" +
                " \t\t<form id='registerForm' action=\"/register\" method=\"post\">\n" +
                "                 <span>\n" +
                "                <i class=\"\"></i>\n" +
                "                <input type=\"text\" placeholder=\"ID\" name=\"id\">\n" +
                "                </span><br>\n" +
                "                <span>\n" +
                "                <i class=\"\"></i>\n" +
                "                <input type=\"text\" placeholder=\"firstname\" name=\"firstname\">\n" +
                "                </span><br>" +
                "                <span>\n" +
                "                <i class=\"\"></i>\n" +
                "                <input type=\"text\" placeholder=\"lastname\" name=\"lastname\">\n" +
                "                </span><br>" +
                "                <span>\n" +
                "                <i class=\"\"></i>\n" +
                "                <input type=\"text\" placeholder=\"Project Title \" name=\"title\">\n" +
                "                </span><br>" +
                "                <span>\n" +
                "                <i class=\"\"></i>\n" +
                "                <input type=\"email\" placeholder=\"Email Address \" name=\"email\">\n" +
                "                </span><br>" +
                "                <span>\n" +
                "                <i class=\"\"></i>\n" +
                "                <input type=\"number\" placeholder=\"Phone Number \" name=\"telephone\">\n" +
                "                </span><br>" +
                "                 <span>\n" +
                "                <label for=\"combo2\" class=\"combo-label\">Available Slots</label>\n" +
                "                <div class=\"combo js-combobox\">\n" +
                "                <select id=\"dataset\" class=\"combo-input\" role=\"combobox\">\n" +
                "                <option value=\"1\">4/19/2070,6.00AM-7:00AM, " +availableSlots1+" seats remaining"+
                "</option>\n" +
                "                <option value=\"2\">4/19/2070,7.00AM-8:00AM, " +availableSlots2+" seats remaining"+
                "</option>\n" +
                "                <option value=\"3\">4/19/2070,8.00AM-9:00AM, " +availableSlots3+" seats remaining"+
                "</option>\n" +
                "                <option value=\"4\">4/19/2070,10.00AM-11:00AM, " +availableSlots4+" seats remaining"+
                "</option>\n" +
                "                <option value=\"5\">4/19/2070,11.00AM-12:00AM, " +availableSlots5+" seats remaining"+
                "</option>\n" +
                "                <option value=\"6\">4/19/2070,1.00PM-2:00PM, " +availableSlots6+" seats remaining"+
                "</option>\n" +
                "                </select>\n" +
                "                <div class=\"combo-menu\" role=\"listbox\" id=\"listbox2\"></div></div>\n" +
                "                </span><div id=\"registrationStatus\" class='reg_status'></div><br>" +
                " <a  href='/'>Already Have an account</a>"+
                " \t\t\t\t<button>Submit</button>\n" +
                "\n" +
                " \t\t</form>\n</div>\n" +
                " </div>\n";

        String script = "<script>\n" +
                "    document.addEventListener('DOMContentLoaded', function () {\n" +
                "        // Reference to the registration form\n" +
                "        var registerForm = document.getElementById('registerForm');\n" +
                "\n" +
                "        // Event listener for form submission\n" +
                "        registerForm.addEventListener('submit', function (event) {\n" +
                "            event.preventDefault();\n" +
                "\n" +
                "            // Get the entered student ID and telephone number\n" +
                "            var enteredId = document.getElementsByName('id')[0].value;\n" +
                "            var enteredTelephone = document.getElementsByName('telephone')[0].value;\n" +
                "\n" +
                "            // Check the length of the entered ID\n" +
                "            if (enteredId.length !== 8) {\n" +
                "                alert('ID must be 8 characters long.');\n" +
                "                return;\n" +
                "            }\n" +
                "\n" +
                "            // Check the length of the entered telephone number\n" +
                "            if (enteredTelephone.length !== 10) {\n" +
                "                alert('Telephone number must be 10 characters long.');\n" +
                "                return;\n" +
                "            }\n" +
                "\n" +
                "            // Check if the entered ID is in the list of registered students\n" +
                "            var isRegistered = " + generateIsRegisteredScript(allStudents) + ";\n" +
                "\n" +
                "            if (isRegistered) {\n" +
                "                // Prompt user about updating\n" +
                "                var confirmUpdate = confirm('Student with ID ' + enteredId + ' is already registered. Do you want to update?');\n" +
                "\n" +
                "                if (!confirmUpdate) {\n" +
                "                    // If user chooses not to update, return from the function\n" +
                "                    return;\n" +
                "                }\n" +
                "            }\n" +
                "\n" +
                "            // Get the selected slot value\n" +
                "            var selectedSlot = document.getElementById('dataset').value;\n" +
                "\n" +
                "            // Fetch request with form data\n" +
                "            var formData = new FormData(registerForm);\n" +
                "            formData.append('selectedSlot', selectedSlot);\n" +
                "\n" +
                "            // Perform fetch request\n" +
                "            fetch('/register', {\n" +
                "                method: 'POST',\n" +
                "                body: formData\n" +
                "            })\n" +
                "            .then(response => response.json())\n" +
                "            .then(data => {\n" +
                "                var statusElement = document.getElementById('registrationStatus');\n" +
                "\n" +
                "                if (data.registrationSuccess) {\n" +
                "                    statusElement.innerHTML = 'Registration successful! Thank you for registering.';\n" +
                "                } else {\n" +
                "                    // Check if the registration failed due to no available slots\n" +
                "                    if (data.message.includes('No available slots')) {\n" +
                "                        statusElement.innerHTML = 'Registration failed. No available slots for the selected time.';\n" +
                "                    } else {\n" +
                "                        statusElement.innerHTML = 'Registration failed. Please try again.';\n" +
                "                    }\n" +
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

    private String generateIsRegisteredScript(List<StudentDTO> allStudents) {
        StringBuilder script = new StringBuilder();
        script.append("[");
        for (int i = 0; i < allStudents.size(); i++) {
            script.append("\"").append(allStudents.get(i).getId()).append("\"");
            if (i < allStudents.size() - 1) {
                script.append(", ");
            }
        }
        script.append("].includes(enteredId)");
        return script.toString();
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
                        StudentDTO st = new StudentDTO(id,firstname,lastname,title,email,telephone,selectedSlot);
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

    private int getAvailableSlotsFromDatabase(int i) {
        // Implement the logic to fetch available slots from the database
        // For demonstration purposes, I'll use a placeholder value (6), replace it with your actual database logic.

        int maxSlots = 6; // The maximum value for slots
        int bookedSlots = getBookedSlotsFromDatabase(i); // Replace with your logic to fetch booked slots
        return Math.max(0, maxSlots - bookedSlots); // Calculate available slots
    }

    private int getBookedSlotsFromDatabase(int i) {
        // Initialize the booked slots count
        int bookedSlots = 0;

        try (Connection connection = DatabaseConfig.getConnection()) {
            // Query to fetch the booked slots for the specified slotNumber
            String query = "SELECT COUNT(*) FROM students WHERE slot = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, i);

                // Execute the query and retrieve the result set
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        bookedSlots = resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors
        }

        return bookedSlots;
    }
}
