<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Redemption Details</title>
</head>
<body>
<%/*RedemptionDetails.jsp
    Request input parameters: a prize id and a customer id
    Response output: the prize description, the number of points needed, redemption date, and exchange center name for the specified (prize id, customer id) pair.
    URL format:http://127.0.0.1:8080/loyaltyfirst/RedemptionDetails.jsp?prizeid=1001&cid=1 */
    
    String prizeId = request.getParameter("prizeid");
    String cid = request.getParameter("cid");
    String output = "";

    try {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
        
        try (Connection conn = DriverManager.getConnection(url, "yjiang25", "nuchagef");
             PreparedStatement pstmt = conn.prepareStatement(
               "SELECT p.P_DESCRIPTION, p.POINTS_NEEDED, rh.R_DATE, ec.CENTER_NAME " +
               "FROM PRIZES p " +
               "JOIN REDEMPTION_HISTORY rh ON p.PRIZE_ID = rh.PRIZE_ID " +
               "JOIN EXCHGCENTERS ec ON rh.CENTER_ID = ec.CENTER_ID " +
               "WHERE rh.PRIZE_ID = ? AND rh.CID = ?")) {

            pstmt.setString(1, prizeId);
            pstmt.setString(2, cid);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String prDescription = rs.getString("P_DESCRIPTION");
                    int pNeeded = rs.getInt("POINTS_NEEDED");
                    Date redDate = rs.getDate("R_DATE");
                    String cName = rs.getString("CENTER_NAME");

                    output += prDescription + ",";
                    output += pNeeded + ",";
                    output += redDate + ",";
                    output += cName + "#";
                    
                }
                if (output.length() == 0){
                    output = "pid，cid does not match";
                }
            }
        }
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
        output = "No JDBC Driver found.";
    } catch (SQLException e) {
        e.printStackTrace();
        output = "Redemption details retrieval failed.";
    }

    out.println(output);
%>
</body>
</html>
