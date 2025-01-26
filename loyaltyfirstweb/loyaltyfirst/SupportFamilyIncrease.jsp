<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Support Family Increase</title>
</head>
<body>
<%/*Request input parameters: A customer id and a transaction reference
    Response output: The family id of the specified customer id, the Percent of points to provide to the customer family members, and the total number of points of the specified transaction reference.
    URL format: http://127.0.0.1:8080/loyaltyfirst/SupportFamilyIncrease.jsp?cid=1&tref=21
    */
    
    String cid = request.getParameter("cid");
    String tref = request.getParameter("tref");
    String output = "";

    try {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
        
        try (Connection conn = DriverManager.getConnection(url, "yjiang25", "nuchagef");
             PreparedStatement pstmt = conn.prepareStatement(
               "SELECT f.FAMILY_ID, pa.PERCENT_ADDED, rh.T_POINTS " +
               "FROM POINT_ACCOUNTS pa " +
               "JOIN TRANSACTIONS rh ON pa.POINT_ACCT_NO = rh.POINT_ACCT_NO " +
               "JOIN CUSTOMERS c ON c.CID = rh.CID " +
               "JOIN FAMILIES f ON f.FAMILY_ID = c.FAMILY_ID " +
               "WHERE c.CID = ? AND rh.tref = ?")) {
               
            pstmt.setString(1, cid);
            pstmt.setString(2, tref);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {                    
                    int famid = rs.getInt("FAMILY_ID");
                    int percent = rs.getInt("PERCENT_ADDED");
                    int points = rs.getInt("T_POINTS");

                    output += famid + ",";
                    output += percent + ",";
                    output += points+ "#";
                    
                }
                if (output.length() == 0){
                    output = "cid not matching";
                }
            }
        }
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
        output = "No JDBC Driver found.";
    } 

    out.println(output);
%>
</body>
</html>
