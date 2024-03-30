import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class movie_upd extends HttpServlet 
{
    public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException,IOException
    {        
			Statement state4 = null;
			ResultSet result = null;
			String query="";        
			Connection con=null; 
          
			String m_id = request.getParameter("m_id");
			String m_title = request.getParameter("m_title");
			String m_releaseDate = request.getParameter("m_releaseDate");
			String m_synopsis = request.getParameter("m_synopsis");
			String m_length = request.getParameter("m_length");
			String m_rating = request.getParameter("m_rating");
			String cat_id = request.getParameter("cat_id");
			String productionStatus = request.getParameter("productionStatus");

		try
		{			
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver()); 
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "C##Project", "project");
	       	System.out.println("Congratulations! You are connected successfully.");      
     	}
        catch(SQLException e)
		{	
			System.out.println("Error: "+ e);	
		}
		catch(Exception e) 
		{
			System.err.println("Exception while loading  driver");		
		}

	    try 
		{
        	state4 = con.createStatement();
		} 
		catch (SQLException e) 	
		{
			System.err.println("SQLException while creating statement");			
		}
		
		response.setContentType("text/html");
		PrintWriter out = null ;

		try
		{
			out =  response.getWriter();
		}
		catch (IOException e) 
		{
  			e.printStackTrace();
		}
		
		query = "update movie set m_title = '"+m_title+"', m_releaseDate = DATE '"+m_releaseDate+"', m_synopsis = '"+m_synopsis+"', m_length = '"+m_length+"', m_rating = '"+m_rating+"', cat_id = '"+cat_id+"', productionStatus = '"+productionStatus+"' where m_id = '"+m_id+"'";

	
		out.println("<html><head><title>Movie has been updated</title>");	 
		out.println("</head><body>");
       	
		try 
		{ 
			//For case where the movie table is empty.
			ResultSet countResult = state4.executeQuery("select count(*) from movie");
			countResult.next();
			int rowCount = countResult.getInt(1);

			//For executing the query, and recieving the result.
			result = state4.executeQuery(query);

			//If the movie table is empty, state that it is so. If not, state the movie entry has been deleted from the movie table.
			if (rowCount == 0) {
				out.print( "<br /><b><center><font color=\"BLACK\"><H2>The movie table is empty</H2></font>");
				out.println( "</center><br />" );
			} else {
				out.print( "<br /><b><center><font color=\"BLACK\"><H2>The movie entry has been updated</H2></font>");
				out.println( "</center><br />" );
			}	
	  	}
		catch (SQLException e) 
		{
			System.err.println("SQLException while executing SQL Statement."); 
		}

		out.println("<center><table border=\"1\">"); 
		out.println("<tr BGCOLOR=\"#cccccc\">");
          out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\"> </td>");
       // out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">movtitle</td>");
        out.println("</tr>");

		try 
		{ 
            while(result.next()) 
			{ 
		    		out.println("<tr>");
                		out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">"+result.getString(1)+"</td>");
		    		//out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">"+result.getString(2)+"</td>");
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
