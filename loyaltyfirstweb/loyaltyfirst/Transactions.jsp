<%@ page import="java.sql.*" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Customer Transactions</title>
</head>
<body>
<%/*
    Request input parameters: A customer id
    Response output: the transaction reference, transaction date, the transaction points, and amount for all the transactions belonging to the specified customer id.
    URL format: http://127.0.0.1:8080/loyaltyfirst/Transactions.jsp?cid=1*/
    
    String cid = request.getParameter("cid");
    String output = "";

    try {
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";

        try (Connection conn = DriverManager.getConnection(url, "yjiang25", "nuchagef");
             PreparedStatement pstmt = conn.prepareStatement(
               "SELECT TREF, T_DATE, T_POINTS, AMOUNT " +
               "FROM TRANSACTIONS WHERE CID = ?")) {

            pstmt.setString(1, cid);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String transref = rs.getString("TREF");
                    Date transDate = rs.getDate("T_DATE");
                    int transPoints = rs.getInt("T_POINTS");
                    BigDecimal amount = rs.getBigDecimal("AMOUNT");
                    output += transref + ",";
                    output += transDate + ",";
                    output += transPoints + ",";
                    output += amount + "#";
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        output = "Transaction retrieval failed.";
    }

    out.println(output);
%>
</body>
</html>
