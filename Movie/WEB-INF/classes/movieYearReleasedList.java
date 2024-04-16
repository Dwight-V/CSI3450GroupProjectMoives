import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class movieYearReleasedList extends HttpServlet {
	
	private Statement stmt;
    private Connection con;
    private PrintWriter out;
	private ResultSet result;

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
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}

        String movieReleaseYear = request.getParameter("movieReleaseYear");

		String queryCast = "SELECT m_title FROM movie WHERE EXTRACT(YEAR FROM m_releaseDate) = '" + movieReleaseYear + "'";		

		try 
		{
			//Creates statement for executing SQL statements using Connection object con
        	stmt = con.createStatement();
		} 
		catch (SQLException e) 	
		{
			System.err.println("SQLException while creating statement");			
		}

		try 
		{
			result = stmt.executeQuery(queryCast);
		} 
		catch (SQLException e) {
			System.err.println("SQLException while executing SQL Statement: " + e.getMessage());
			e.printStackTrace();
		}


		out.println("<html><head><title>Movies Released in the Year of " + movieReleaseYear + "</title>");
		out.println("</head><body>");

		out.print( "<br /><b><center><font color=\"RED\"><H2>Movies Released in the Year of " + movieReleaseYear + "</H2></font>");
        out.println( "</center><br />" );


		//Setup for the table to be presented. Producer table attribute names are the names of the report table's columns.
		out.println("<center><table border=\"1\">"); 
		out.println("<tr BGCOLOR=\"#cccccc\">");
        out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">Movie Name</td>");
        out.println("</tr>");
		
		try 
		{ 
			//Loop through the result set, and print each row
            while(result.next()) 
			{ 
		    	out.println("<tr>");
                out.println("     <td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">"+result.getString(1)+"</td>");
                out.println("</tr>");
			} 
	    }
		catch (SQLException e) 
		{
			System.out.println("Resutset is not connected"); 
		}

		out.println("</table></CENTER>");

		out.println("</body></html>");
	}
}
