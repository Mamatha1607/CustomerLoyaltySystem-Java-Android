<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Customer Information</title>
</head>
<body>

<%//retrieves customer information from a database based on the provided customer ID

    //Request input parameters: A customer id
    //Response output: The customer name, the number of points collected by the customer.
    //URL format: http://127.0.0.1:8080/loyaltyfirst/Info.jsp?cid=1

    // Get the customer ID from the request parameter
    String customerId = request.getParameter("cid");

    // Check if customer ID is provided
    if (customerId == null || customerId.isEmpty()) {
        out.println("<p>No customer ID provided.</p>");
    } else {
        try {
            // Load the Oracle JDBC driver
            Class.forName("oracle.jdbc.OracleDriver");

            // Establish a database connection
            try (Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu", "yjiang25", "nuchagef");
                 PreparedStatement stmt = conn.prepareStatement("SELECT * FROM CUSTOMERS a JOIN TRANSACTIONS b on a.cid=b.cid WHERE a.cid = ?")) {

                // Set the customer ID parameter
                stmt.setString(1, customerId);

                // Execute the query
                try (ResultSet rs = stmt.executeQuery()) {
                    // Check if the customer exists
                    if (rs.next()) {
                        // Retrieve customer information
                        String customerName = rs.getString("CNAME");
                        String customerEmail = rs.getString("EMAIL");
                        String customerPoints = rs.getString("T_POINTS");

                        // Display customer information
                        out.println("<h2>Customer Information</h2>");
                        out.println("<p>ID: " + customerId + "</p>");
                        out.println("<p>Name: " + customerName + "</p>");
                        out.println("<p>Email: " + customerEmail + "</p>");
                        out.println("<p>Rewarded Points: " + customerPoints + "</p>");
                    } else {
                        out.println("<p>No customer found with ID: " + customerId + "</p>");
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(); // Log or handle the exception appropriately
            out.println("<p>Error retrieving customer information.</p>");
        }
    }
%>

</body>
</html>

