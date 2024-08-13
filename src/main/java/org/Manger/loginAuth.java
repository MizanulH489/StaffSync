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

public class loginAuth implements Handler {
    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        List<StudentDTO> allStudents = getAllStudents();
        String userName = ctx.formParam("username");
        String password = ctx.formParam("password");


        // Check if the provided credentials match any student
        boolean authenticationSuccess = authenticateUser(userName, password, allStudents);

        // Prepare response based on authentication result
        if (authenticationSuccess) {
            ctx.result("Authentication successful for user: " + userName);
        } else {
            ctx.result("Error logging user: " + userName);
        }
    }

    private boolean authenticateUser(String userName, String password, List<StudentDTO> allStudents) {
        for (StudentDTO student : allStudents) {
            if (student.getEmail().equals(userName) && student.getId().equals(password)) {
                return true; // Authentication successful
            }
        }
        return false; // Authentication failed
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

}
