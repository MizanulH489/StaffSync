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
import java.util.Objects;

public class registerNewStudent implements Handler {

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        // Get form data from the request
        String id = ctx.formParam("id");
        String firstname = ctx.formParam("firstname");
        String lastname = ctx.formParam("lastname");
        String title = ctx.formParam("title");
        String email = ctx.formParam("email");
        String telephone = ctx.formParam("telephone");
        int slots = Integer.parseInt(Objects.requireNonNull(ctx.formParam("selectedSlot")));

        // Check available slots
        int availableSlots = getAvailableSlotsFromDatabase(slots);

        List<StudentDTO> allStudents = getAllStudents();

        // If no available slots, return failure response
        if (availableSlots <= 0) {
            ctx.json(new RegistrationResponse(false, "No available slots. Registration failed."));
            return;
        }

        // Check if the entered ID is in the list of registered students
        StudentDTO existingStudent = findStudentById(allStudents, id);

        // If the student exists, update the record; otherwise, insert a new record
        boolean registrationSuccess;
        if (existingStudent != null) {
            registrationSuccess = updateStudent(id, firstname, lastname, title, email, telephone, slots);
        } else {
            registrationSuccess = registerStudent(id, firstname, lastname, title, email, telephone, slots);
        }

        // Prepare a JSON response
        ctx.json(new RegistrationResponse(registrationSuccess, registrationSuccess ? "Registration successful!" : "Registration failed."));
    }

    private boolean registerStudent(String id, String firstname, String lastname, String title, String email, String telephone, int slots) {
        try (Connection connection = DatabaseConfig.getConnection()) {
            String query = "INSERT INTO students (id, firstname, lastname, project_title, email, phone_number, slot) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, id);
                preparedStatement.setString(2, firstname);
                preparedStatement.setString(3, lastname);
                preparedStatement.setString(4, title);
                preparedStatement.setString(5, email);
                preparedStatement.setString(6, telephone);
                preparedStatement.setInt(7, slots);

                // Execute the query
                preparedStatement.executeUpdate();
                return true; // Registration successful
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors
        }

        return false; // Registration failed
    }

    private boolean updateStudent(String id, String firstname, String lastname, String title, String email, String telephone, int slots) {
        try (Connection connection = DatabaseConfig.getConnection()) {
            String query = "UPDATE students SET firstname = ?, lastname = ?, project_title = ?, email = ?, phone_number = ?, slot = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, firstname);
                preparedStatement.setString(2, lastname);
                preparedStatement.setString(3, title);
                preparedStatement.setString(4, email);
                preparedStatement.setString(5, telephone);
                preparedStatement.setInt(6, slots);
                preparedStatement.setString(7, id);

                // Execute the query
                preparedStatement.executeUpdate();
                return true; // Update successful
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors
        }

        return false; // Update failed
    }

    private StudentDTO findStudentById(List<StudentDTO> students, String id) {
        for (StudentDTO student : students) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
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

    private static class RegistrationResponse {
        private final boolean registrationSuccess;
        private final String message;

        public RegistrationResponse(boolean registrationSuccess, String message) {
            this.registrationSuccess = registrationSuccess;
            this.message = message;
        }

        public boolean isRegistrationSuccess() {
            return registrationSuccess;
        }

        public String getMessage() {
            return message;
        }
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
