<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Transaction Details</title>
</head>
<body>
<%/*TransactionDetails.jsp
    Request input parameters: The transaction reference
    Response output: the transaction date, the transaction points, product names, product points, and quantities
    belonging to the specified transaction reference
    URL format: http://127.0.0.1:8080/loyaltyfirst/TransactionDetails.jsp?tref=21
    */
    
    String tref = request.getParameter("tref");
    String output = "";

    try {
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";

        try (Connection conn = DriverManager.getConnection(url, "yjiang25", "nuchagef");
             PreparedStatement pstmt = conn.prepareStatement(
               "SELECT t.T_DATE, t.T_POINTS, p.PROD_NAME, p.PROD_POINTS, tp.QUANTITY " +
               "FROM TRANSACTIONS t " +
               "JOIN TRANSACTIONS_PRODUCTS tp ON t.TREF = tp.TREF " +
               "JOIN PRODUCTS p ON tp.PROD_ID = p.PROD_ID " +
               "WHERE t.TREF = ?")) {

            pstmt.setString(1, tref);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Date transDate = rs.getDate("T_DATE");
                    int transPoints = rs.getInt("T_POINTS");
                    String productName = rs.getString("PROD_NAME");
                    int productPoints = rs.getInt("PROD_POINTS");
                    int quantity = rs.getInt("QUANTITY");

                    output += transDate + ",";
                    output += transPoints + ",";
                    output += productName + ",";
                    output += productPoints + ",";
                    output += quantity + "#";
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        output = "Transaction Details retrieval failed.";
    }

    out.println(output);
%>
</body>
</html>
