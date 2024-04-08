import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class movie_reg extends HttpServlet {
  // Use a prepared statement to store a student into the database
  private PreparedStatement pstmt;

  /** Initialize global variables */
  public void init() throws ServletException {
    initializeJdbc();
  }

  /** Process the HTTP Post request */
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    // Obtain parameters from the client
    String m_id = request.getParameter("m_id");
    String m_title = request.getParameter("m_title");
    String m_releaseDate = request.getParameter("m_releaseDate");
    String m_synopsis = request.getParameter("m_synopsis");
    String m_length = request.getParameter("m_length");
    String m_rating = request.getParameter("m_rating");
    String cat_id = request.getParameter("cat_id");
    String productionStatus = request.getParameter("productionStatus");

    // Rid of the DATE at the start of the string
    m_releaseDate = m_releaseDate.replaceAll("DATE", "");

    try {

      if (m_id.length() == 0 || m_title.length() == 0) {
        out.println("Movie ID and title are required to add a movie.");
        return; // End the method
      }

      storeMovie(m_id, m_title, m_releaseDate, m_synopsis, m_length, m_rating, cat_id, productionStatus);

      out.println(m_id + " " + m_title + " is now registered in the database");
    } catch (Exception ex) {
      out.println("\n Error: " + ex.getMessage());
    } finally {
      out.close(); // Close stream
    }
  }

  /** Initialize database connection */
  private void initializeJdbc() {
    try {
      // Declare driver and connection string
      // String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
      // String connectionString = "jdbc:odbc:exampleMDBDataSource";
      // For MySQL
      // String driver = "com.mysql.jdbc.Driver";
      // String connectionString = "jdbc:mysql://localhost/test";
      // For Oracle
      String driver = "oracle.jdbc.driver.OracleDriver";

      // Load the driver
      Class.forName(driver);

      String url = "jdbc:oracle:thin:@localhost:1521:orcl";
      String user = "c##project";
      String password = "project";

      // Connect to the database
      Connection conn = DriverManager.getConnection(url, user, password);

      // Create a Statement
      pstmt = conn.prepareStatement("insert into movie " +
          "(m_id, m_title, m_releaseDate, m_synopsis, m_length, m_rating, "
          + "cat_id, productionStatus) values (?, ?, ? , ?, ?, ?, ?, ?)");
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /** Store a record to the database */
  private void storeMovie(String m_id, String m_title,
      String m_releaseDate, String m_synopsis, String m_length, String m_rating,
      String cat_id, String productionStatus) throws SQLException {
    pstmt.setString(1, m_id);
    pstmt.setString(2, m_title);
    pstmt.setDate(3, java.sql.Date.valueOf(m_releaseDate));
    pstmt.setString(4, m_synopsis);
    pstmt.setString(5, m_length);
    pstmt.setString(6, m_rating);
    pstmt.setString(7, cat_id);
    pstmt.setString(8, productionStatus);

    pstmt.executeUpdate();
  }
}