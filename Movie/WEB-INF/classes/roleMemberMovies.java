import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class roleMemberMovies extends HttpServlet {
	
	private Statement pstmtMovies;
	private Statement pstmtPersonName;
    private Connection con;
    private PrintWriter out;
	private ResultSet resultMovies;
	private ResultSet resultName;

    public void init() throws ServletException {
        initializeJdbc();
    }

    private void initializeJdbc() {
        try {
            String driver = "oracle.jdbc.driver.OracleDriver";
            Class.forName(driver);

            String url = "jdbc:oracle:thin:@localhost:1521:orcl";
            String user = "C##project";
            String password = "project";

            con = DriverManager.getConnection(url, user, password);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String role_id = request.getParameter("role_id");
		String role_name = request.getParameter("role_name");

		String roleAttributeName_ID = "";
		switch(role_name) {
			case "Producer":
				roleAttributeName_ID = "prod_ID";    
				break;
			case "Actor":
				roleAttributeName_ID = "actor_ID";
				break;
			case "Actress":
				roleAttributeName_ID = "actress_ID";
				break;
			case "Writer":
				roleAttributeName_ID = "writ_ID";
				break;
			case "Director":
				roleAttributeName_ID = "dir_ID";
				break;
			default:
				out.println("\n Error in passing attribute role_name.");
		}

		String queryMovies = "SELECT m.* FROM " + role_name + " r JOIN movie m ON r.m_ID = m.m_ID WHERE r." + roleAttributeName_ID + " = '" + role_id + "'";

		try 
		{
			//Creates statement for executing SQL statements using Connection object con
        	pstmtMovies = con.createStatement();
		} 
		catch (SQLException e) 	
		{
			System.err.println("SQLException while creating statement");			
		}

		try 
		{
			resultMovies = pstmtMovies.executeQuery(queryMovies);
		} 
		catch (SQLException e) {
			System.err.println("SQLException while executing SQL Statement: " + e.getMessage());
			e.printStackTrace();
		}
		


		String queryName = "select p_firstName, p_lastName from person where p_id = '" + role_id + "'";

		try 
		{
			//Creates statement for executing SQL statements using Connection object con
        	pstmtPersonName = con.createStatement();
		} 
		catch (SQLException e) 	
		{
			System.err.println("SQLException while creating statement");			
		}

		try 
		{
			resultName = pstmtPersonName.executeQuery(queryName);
		} 
		catch (SQLException e) {
			System.err.println("SQLException while executing SQL Statement: " + e.getMessage());
			e.printStackTrace();
		}


		out.println("<html><head><title>All Relevant Movies</title>");
		out.println("</head><body>");

		String firstName = "";
		String lastName = "";

		try { 
			//Used to get the first name and last name
			resultName.next(); // Move the cursor to the first row
			firstName = resultName.getString("p_firstName");
			lastName = resultName.getString("p_lastName");

			out.print("<br /><b><center><H2>All movies the given " + role_name.toLowerCase() + ", " + firstName + " " + lastName + ", with role_id " + role_id + " is in is shown below.</H2></font>");
			out.println("</center><br />");
		}
		catch (SQLException e) {
			System.out.println("ResultSet resultName is not connected"); 
		}

		//Setup for the table to be presented. Movie table attribute names are the names of the report table's columns.
		out.println("<center><table border=\"1\">"); 
		out.println("<tr BGCOLOR=\"#cccccc\">");
        out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">Movie ID</td>");
		out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">Title</td>");
        out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">Release Date</td>");
		out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">Synopsis</td>");
		out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">Rating</td>");
		out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">Duration</td>");
        out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">Category</td>");
		out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">Production Status</td>");
        out.println("</tr>");
		
		try 
		{ 
			//Loop through the result set, and print each row
            while(resultMovies.next()) 
			{ 
		    	out.println("<tr>");
                out.println("     <td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">"+resultMovies.getString(1)+"</td>");
		    	out.println("     <td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">"+resultMovies.getString(2)+"</td>");
				out.println("     <td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">"+resultMovies.getString(3)+"</td>");
				out.println("     <td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">"+resultMovies.getString(4)+"</td>");
				out.println("     <td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">"+resultMovies.getString(5)+"</td>");
				out.println("     <td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">"+resultMovies.getString(6)+"</td>");
				out.println("     <td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">"+resultMovies.getString(7)+"</td>");
				out.println("     <td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">"+resultMovies.getString(8)+"</td>");
                out.println("</tr>");
			} 
	    }
		catch (SQLException e) 
		{
			System.out.println("Resutset is not connected"); 
		}

		out.println("</table></CENTER>");

		/*try {
			result.close();
			pstmt.close();
			con.close();
			System.out.println("Connection is closed successfully.");
		} catch (SQLException e) {
			e.printStackTrace();
		}*/

		out.println("</body></html>");
	}
}
