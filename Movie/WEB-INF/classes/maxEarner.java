import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class maxEarner extends HttpServlet 
{
	private Statement state4 = null;
	private ResultSet result = null;
	private String query="";        
	private Connection con=null; 
	private PrintWriter out = null;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException,IOException
    {        

		String role_name = request.getParameter("role_name");
        
        //Set content type of response 
        response.setContentType("text/html");

		try {
			//Loads the driver for Oracle. 	
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver()); 

			//Connects to the database
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "C##project", "project");
	       	System.out.println("Congratulations! You are connected successfully.");      
     	}
        catch(SQLException e) {	
			System.out.println("Error: "+e);	
		}
		catch(Exception e) {
			System.err.println("Exception while loading driver");		
		}

        try
        {
            //Gets HTTP responses, the entered information from the user into the HTTP fields
            out = response.getWriter();
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }

        
        //Construct SQL statement for role insertion based on role_id
        String roleIDPrefix = "";
        switch(role_name) {
            case "Producer":
                roleIDPrefix = "prod";
                break;
            case "Actor":
                roleIDPrefix = "actor";
                break;
            case "Actress":
                roleIDPrefix = "actress";
                break;
            case "Writer":
                roleIDPrefix = "writ";
                break;
            case "Director":
                roleIDPrefix = "dir";
                break;
            default:
                out.println("\n Error in passing attribute role_name.");
        }

        try {
            //Creates statement for executing SQL statements using Connection object con
            state4 = con.createStatement();
        } 
        catch (SQLException e) 	{
            System.err.println("SQLException while creating statement");			
        }
            
        //Queries the database 
        query = "SELECT p.p_ID AS " + roleIDPrefix + "_ID, p.p_firstName AS FirstName, p.p_lastName AS LastName, MAX(p.p_pay) AS Max_Pay FROM person p " +
        "JOIN " + role_name + " r ON p.p_ID = r.p_ID " +
        "GROUP BY p.p_ID, p.p_firstName, p.p_lastName " + 
        "ORDER BY Max_Pay DESC " +
        "FETCH FIRST 1 ROW ONLY";
        
        out.println("<html><head><title>Max Earning " + role_name + "</title>");	 
        out.println("</head><body>");
        
        out.print( "<br /><b><center><font color=\"RED\"><H2>Max Earning " + role_name + "</H2></font>");
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

        //Setup for the table to be presented. 
        out.println("<center><table border=\"1\">"); 
        out.println("<tr BGCOLOR=\"#cccccc\">");
        out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">ID</td>");
        out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">First Name</td>");
        out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">Last Name</td>");
        out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">Max_Pay</td>");
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
                out.println("</tr>");
            } 
        }
        catch (SQLException e) 
        {
            System.out.println("Resutset is not connected"); 
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
