import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class moviePrice extends HttpServlet 
{
    public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException,IOException
    {        
		
			Statement state4 = null;
			ResultSet result = null;
			String query="";        
			Connection con=null; 

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

	    try 
		{
			//Creates statement for executing SQL statements using Connection object con
        	state4 = con.createStatement();
		} 
		catch (SQLException e) 	
		{
			System.err.println("SQLException while creating statement");			
		}
		
		//Set content type of response 
		response.setContentType("text/html");
		PrintWriter out = null;

		try
		{
			//Gets HTTP responses, the entered information from the user into the HTTP fields
			out = response.getWriter();
		}
		catch (IOException e) 
		{
  			e.printStackTrace();
		}
		
		//Queries the database for all cast and crew members of a given movie, and then adds up the pay of each person to the total cost of the movie.
		query = "SELECT m.m_ID, m.m_title, SUM(p.p_pay) AS total_cost FROM movie m " +
        "LEFT JOIN producer pr ON m.m_ID = pr.m_ID " + 
        "LEFT JOIN actor a ON m.m_ID = a.m_ID " + 
        "LEFT JOIN actress ac ON m.m_ID = ac.m_ID " +
        "LEFT JOIN writer w ON m.m_ID = w.m_ID " +
        "LEFT JOIN director d ON m.m_ID = d.m_ID " +
        "LEFT JOIN person p ON pr.p_ID = p.p_ID OR a.p_ID = p.p_ID OR ac.p_ID = p.p_ID OR w.p_ID = p.p_ID OR d.p_ID = p.p_ID " +
        "GROUP BY m.m_ID, m.m_title " +
        "ORDER BY total_cost DESC " +
        "FETCH FIRST 1 ROW ONLY";
		
		out.println("<html><head><title>Most Expensive Movie</title>");	 
		out.println("</head><body>");
		
		out.print( "<br /><b><center><H2>Most Expensive Movie</H2></font>");
        out.println( "</center><br />" );
       	try 
		{ 
			//Executes SQL query to show the most expensive movie.
			result=state4.executeQuery(query);
	  	}
		catch (SQLException e) 
		{  
			System.err.println("SQLException while executing SQL Statement."); 
		}

		//Setup for the table to be presented. Attribute names are the names of the report table's columns.
		out.println("<center><table border=\"1\">"); 
		out.println("<tr BGCOLOR=\"#cccccc\">");
        out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">Movie ID</td>");
		out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">Title</td>");
        out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">Total Cost</td>");
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
                out.println("</tr>");
			} 
	    }
		catch (SQLException e) 
		{
			System.out.println("Resultset is not connected"); 
		}

		out.println("</table></CENTER>");
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
}