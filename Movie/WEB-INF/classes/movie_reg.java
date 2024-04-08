import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class movie_reg extends HttpServlet { 
  // Use a prepared statement to store a student into the database
  private PreparedStatement pstmt;

  /** Initialize global variables */
  public void init() throws ServletException { 
    initializeJdbc(); //method, go to line 53
  }

  /** Process the HTTP Post request */
  public void doPost(HttpServletRequest request, HttpServletResponse
      response) throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    // Obtain parameters from the client
    String m_id = request.getParameter("m_id"); 
    String m_title = request.getParameter("m_title");
    String m_date = request.getParameter("m_date");
    String m_synopsis = request.getParameter("m_synopsis");
    String m_length = request.getParameter("m_length");
    String rating_id = request.getParameter("rating_id");
    String cat_id = request.getParameter("cat_id");

    try {

		//out.println("First Name: " + firstName );
      if (m_id.length() == 0 || m_title.length() == 0) {
        out.println("Please: Moive ID and title are required");
        return; // End the method
      }

      storeStudent(m_id, m_title,m_date, m_synopsis, m_length, rating_id,
        cat_id);

      out.println(m_id + " " + m_title +
        " is now registered in the database");
    }
    catch(Exception ex) {
      out.println("\n Error: " + ex.getMessage());
    }
    finally {
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

    String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl"; //the oracle driver's url - the source locator. port 1521. orcl is the default database name, so you will need to change that if you named it differently.
    String user = "project"; //this is the username for your database user 
    String password = "project"; //this is the password for your database user 

      // Connect to the sample database
      Connection conn = DriverManager.getConnection //the method getConnection() is defined within sql.
        (url, user, password);

      // Create a Statement - this is where the sql code is
      pstmt = conn.prepareStatement("insert into movie " +
        "(m_id, m_title, m_date, m_synopsis, m_length, rating_id, "
         + "cat_id) values (?, ?, ?, ?, ?, ?, ?)");
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /** Store a student record to the database */
  private void storeStudent(String m_id, String m_title,
      String m_date, String m_synopsis, String m_length, String rating_id,
      String cat_id) throws SQLException {
    pstmt.setString(1, m_id); 
    pstmt.setString(2, m_title);
    pstmt.setString(3, m_date);
    pstmt.setString(4, m_synopsis);
    pstmt.setString(5, m_length);
    pstmt.setString(6, rating_id);
    pstmt.setString(7, cat_id);
    
    pstmt.executeUpdate();
  }
}
