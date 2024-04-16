import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class actressNoWorkWithProducer extends HttpServlet 
{
	private Statement state4 = null;
	private ResultSet result = null;
	private String query="";        
	private Connection con=null; 
	private PrintWriter out = null;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException,IOException
    {        

			String prod_id = request.getParameter("prod_id");

		try
		{
			//Loads the driver for Oracle. 	
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver()); 

			//Connects to the database
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "C##CSI3450", "johnRing19841");
	       	System.out.println("Congratulations! You are connected successfully.");      
     	}
        catch(SQLException e)
		{	
			System.out.println("Error: "+e);	
		}
		catch(Exception e) 
		{
			System.err.println("Exception while loading driver");		
		}

		try {
			if (!producerExists(prod_id)) {
				out.println("<html><head><title>No Producer Found</title></head><body>");
				out.println("<h2>No Producer Found with such ID</h2>");
				out.println("</body></html>");
			} else {

				try {
					//Creates statement for executing SQL statements using Connection object con
					state4 = con.createStatement();
				} 
				catch (SQLException e) 	{
					System.err.println("SQLException while creating statement");			
				}
				
				//Set content type of response 
				response.setContentType("text/html");
				
				try
				{
					//Gets HTTP responses, the entered information from the user into the HTTP fields
					out = response.getWriter();
				}
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				
				//Queries the database 
				query = "SELECT p.p_ID, p.p_firstName, p.p_lastName, " +
					"m.m_ID, m.m_title " +
					"FROM actress a " +
					"JOIN person p ON a.p_ID = p.p_ID " +
					"JOIN movie m ON a.m_ID = m.m_ID " +
					"LEFT JOIN producer pr ON pr.m_ID = m.m_ID AND pr.prod_ID = '" + prod_id + "' " +
					"WHERE m.m_ID NOT IN (SELECT m_ID FROM producer WHERE prod_ID = '" + prod_id + "') " +
					"ORDER BY p.p_lastName, p.p_firstName";
				
				out.println("<html><head><title>Actressest</title>");	 
				out.println("</head><body>");
				
				out.print( "<br /><b><center><font color=\"RED\"><H2>Actresses who do not work with the Producer with ID: " + prod_id + "</H2></font>");
				out.println( "</center><br />" );

				try 
				{ 
					//Executes SQL query to show all of the Person table's entries
					result = state4.executeQuery(query);
				}
				catch (SQLException e) 
				{
					System.err.println("SQLException while executing SQL Statement."); 
				}

				//Setup for the table to be presented. Actor table attribute names are the names of the report table's columns.
				out.println("<center><table border=\"1\">"); 
				out.println("<tr BGCOLOR=\"#cccccc\">");
				out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">Actress ID</td>");
				out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">First Name</td>");
				out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">Last Name</td>");
				out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">Movie ID where the Actress Doesn't Work with the Producer</td>");
				out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">Movie Title</td>");
				out.println("</tr>");
				
				try 
				{ 
					//Loop through the result set, and print each row
					while(result.next()) 
					{ 
						out.println("<tr>");
						out.println("     <td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">"+result.getString(1)+"</td>");
						out.println("     <td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">"+result.getString(2)+"</td>");
						out.println("     <td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">"+result.getString(3)+"</td>");
						out.println("     <td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">"+result.getString(4)+"</td>");
						out.println("     <td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">"+result.getString(5)+"</td>");
						out.println("</tr>");
					} 
				}
				catch (SQLException e) 
				{
					System.out.println("Resutset is not connected"); 
				}

				out.println("</table></CENTER>");
			}
		} catch (SQLException e) {
			System.err.println("SQLException while finding no producer exists.");	
		}

		try 
		{ 
			//Close the result set, statement, and connection. Releases the resources required to connect to the database.
   			result.close(); 
			state4.close(); 	
			con.close();
    		System.out.println("Connection is closed successfully.");
 	   	}
		catch (SQLException e) 
		{
			e.printStackTrace();	
		}

  		out.println("</body></html>");

    } 
	
	private boolean producerExists(String prod_id) throws SQLException {
		String query = "SELECT COUNT(*) FROM producer WHERE prod_ID = ?";
		try (PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.setString(1, prod_id);
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				int count = result.getInt(1);
				return count > 0;
			}
		}
		return false;
	}

}
