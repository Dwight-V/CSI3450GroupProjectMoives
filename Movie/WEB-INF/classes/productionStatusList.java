import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class productionStatusList extends HttpServlet {
	
	private Statement stmtProdStatusList;
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
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String productionStatus = request.getParameter("productionStatus");

		String queryProdStatus = "SELECT m_id, m_title, m_releaseDate, m_synopsis, m_rating, m_length, cat_id FROM movie where productionStatus = '" + productionStatus + "'";

		try 
		{
			//Creates statement for executing SQL statements using Connection object con
        	stmtProdStatusList = con.createStatement();
		} 
		catch (SQLException e) 	
		{
			System.err.println("SQLException while creating statement");			
		}

		try 
		{
			result = stmtProdStatusList.executeQuery(queryProdStatus);
		} 
		catch (SQLException e) {
			System.err.println("SQLException while executing SQL Statement: " + e.getMessage());
			e.printStackTrace();
		}


		out.println("<html><head><title>All Movies that are " + productionStatus + "</title>");
		out.println("</head><body>");

		out.print( "<br /><b><center><font color=\"RED\"><H2>All Movies that are " + productionStatus + "</H2></font>");
        out.println( "</center><br />" );


		//Setup for the table to be presented. Producer table attribute names are the names of the report table's columns.
		out.println("<center><table border=\"1\">"); 
		out.println("<tr BGCOLOR=\"#cccccc\">");
        out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">Movie ID</td>");
		out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">Title</td>");
        out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">Release Date</td>");
        out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">Synopsis</td>");
		out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">Rating</td>");
        out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">Length</td>");
        out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">Category</td>");
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
                out.println("     <td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">"+result.getString(6)+"</td>");
                out.println("     <td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">"+result.getString(7)+"</td>");
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
