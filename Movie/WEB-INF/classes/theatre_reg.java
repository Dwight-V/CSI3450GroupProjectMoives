import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class theatre_reg extends HttpServlet { 
  // Use a prepared statement to store a theatre into the database
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
    String t_id = request.getParameter("t_id"); 
    String t_name = request.getParameter("t_name");
    String t_street = request.getParameter("t_street");
    String t_city = request.getParameter("t_city");
    String t_state = request.getParameter("t_state");
    String t_zip = request.getParameter("t_zip");
    String t_country = request.getParameter("t_country");

    try {

		//out.println("First Name: " + firstName );
      if (t_id.length() == 0 || t_name.length() == 0) {
        out.println("Please: Theatre ID and name are required");
        return; // End the method
      }

      storeTheatre(t_id, t_name, t_street, t_city, t_state, t_zip,
        t_country);

      out.println(t_id + " " + t_name +
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

    String url = "jdbc:oracle:thin:@127.0.0.1:1521:eolson"; //the oracle driver's url - the source locator. port 1521. orcl is the default database name, so you will need to change that if you named it differently.
    String user = "c##project"; //this is the username for your database user 
    String password = "project"; //this is the password for your database user 

      // Connect to the sample database
      Connection conn = DriverManager.getConnection //the method getConnection() is defined within sql.
        (url, user, password);

      // Create a Statement - this is where the sql code is
      pstmt = conn.prepareStatement("insert into theatre " +
        "(t_id, t_name, t_street, t_city, t_state, t_zip, t_country)"
	+ " values (?, ?, ?, ?, ?, ?, ?)");
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /** Store a student record to the database */
  private void storeTheatre(String t_id, String t_name,
      String t_street, String t_city, String t_state, String t_zip,
      String t_country) throws SQLException {
    pstmt.setString(1, t_id); 
    pstmt.setString(2, t_name);
    pstmt.setString(3, t_street);
    pstmt.setString(4, t_city);
    pstmt.setString(5, t_state);
    pstmt.setString(6, t_zip);
    pstmt.setString(7, t_country);
    
    pstmt.executeUpdate();
  }
}
