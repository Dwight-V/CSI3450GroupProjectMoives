import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class TheatreMovie_reg extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String t_m_id = request.getParameter("t_m_id");
        String t_m_start = request.getParameter("t_m_start");
        String t_m_end = request.getParameter("t_m_end");
        String m_ID = request.getParameter("m_ID");
        String t_ID = request.getParameter("t_ID");

        try {
            // Load the JDBC driver and establish a connection.
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:orcl", "C##CSI3450", "johnRing19841");

            // Prepare SQL statement to insert the theatre-movie association.
            String sql = "INSERT INTO theatremovie (t_m_id, t_m_start, t_m_end, m_ID, t_ID) VALUES (?, TO_TIMESTAMP(?, 'YYYY_MM_DD HH24:MI'), TO_TIMESTAMP(?, 'YYYY_MM_DD HH24:MI')"
            + ", ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            // Set parameters for the prepared statement.
            pstmt.setString(1, t_m_id);
            pstmt.setString(2, formatDateForOracle(t_m_start));
            pstmt.setString(3, formatDateForOracle(t_m_end));
            pstmt.setString(4, m_ID);
            pstmt.setString(5, t_ID);

            // Execute the statement.
            int result = pstmt.executeUpdate();
            if (result > 0) {
                out.println("<p>Theatre-Movie created successfully.</p>");
            } else {
                out.println("<p>Error creating Theatre-Movie.</p>");
            }

            // Close connections.
            pstmt.close();
            con.close();
        } catch (Exception e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    // Implement doGet to handle form resubmission issues.
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            
        // Redirect POST request to POST method.
        doPost(request, response);
    }

    // Method to format date string for Oracle
    private String formatDateForOracle(String dateString) {
        // Replace the 'T' character with a space
        return dateString.replace("T", " ");
    }
}
