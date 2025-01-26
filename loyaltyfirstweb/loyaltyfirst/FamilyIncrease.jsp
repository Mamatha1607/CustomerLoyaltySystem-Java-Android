<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update Family Points</title>
</head>
<body>
<%
    String famId = request.getParameter("fid");
    String custId = request.getParameter("cid");
    String pointsToAdd = request.getParameter("npoints");
    String output = "";

    if (familyId != null && customerId != null && pointsToAdd != null) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
            
            try (Connection conn = DriverManager.getConnection(url, "yjiang25", "nuchagef");
                 PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE Point_Accounts pa " +
                    "SET pa.num_of_points = pa.num_of_points + ? " +
                    "WHERE pa.family_id = ? AND pa.cid <> ? "
                    )) {
                   
                pstmt.setInt(1, Integer.parseInt(pointsToAdd));
                pstmt.setInt(2, Integer.parseInt(famId));
                pstmt.setInt(3, Integer.parseInt(custId));
                
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    output = "Successfully updated point accounts to the family members.";
                } else {
                    output = "No accounts were updated";
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            output = "No JDBC Driver found.";
        } 
         catch (NumberFormatException e) {
            e.printStackTrace();
            output = "Error" + e.getMessage();
        }
    }
    
    out.println("<p>" + output + "</p>");
%>
</body>
</html>
