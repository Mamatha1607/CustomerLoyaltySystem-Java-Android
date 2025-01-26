<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Distinct Prize IDs</title>
</head>
<body>
<%/*Request input parameters: A customer id
    Response output: the distinct prize ids belonging to the specified customer id.
    URL format: http://127.0.0.1:8080/loyaltyfirst/PrizeIds.jsp?cid=1*/
    
    String cid = request.getParameter("cid");
    String output = "";

    try {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
        
        try (Connection conn = DriverManager.getConnection(url, "yjiang25", "nuchagef");
             PreparedStatement pstmt = conn.prepareStatement(
               "SELECT DISTINCT r.PRIZE_ID " +
               "FROM REDEMPTION_HISTORY r " +
               "JOIN PRIZES p ON r.PRIZE_ID = p.PRIZE_ID " +
               "WHERE r.CID = ?")) {

            pstmt.setString(1, cid);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    output += rs.getString("PRIZE_ID") + "#";
                }
            }
        }
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
        output = "No JDBC Driver found..";
    } catch (SQLException e) {
        e.printStackTrace();
        output = "Prize ID details retrieval failed.";
    }

    out.println(output);
%>
</body>
</html>
