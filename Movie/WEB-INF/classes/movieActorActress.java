import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class movieActorActress extends HttpServlet {
	
	private Statement stmtCast;
    private Connection con;
    private PrintWriter out;
	private ResultSet resultCast;

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

		String m_id = request.getParameter("m_id");

		String queryCast = "SELECT p.p_ID, p.p_firstName, p.p_lastName, 'Actor' AS role FROM person p " +
        "INNER JOIN actor a ON p.p_ID = a.p_ID " +
        "WHERE a.m_ID = '" + m_id + "' " +
        "UNION " +
        "SELECT p.p_ID, p.p_firstName, p.p_lastName, 'Actress' AS role " +
        "FROM person p " +
        "INNER JOIN actress ac ON p.p_ID = ac.p_ID " +
        "WHERE ac.m_ID = '" + m_id + "' " +
        "ORDER BY role";

		try 
		{
			//Creates statement for executing SQL statements using Connection object con
        	stmtCast = con.createStatement();
		} 
		catch (SQLException e) 	
		{
			System.err.println("SQLException while creating statement");			
		}

		try 
		{
			resultCast = stmtCast.executeQuery(queryCast);
		} 
		catch (SQLException e) {
			System.err.println("SQLException while executing SQL Statement: " + e.getMessage());
			e.printStackTrace();
		}


		out.println("<html><head><title>All of Movie " + m_id + "'s Cast</title>");
		out.println("</head><body>");

		out.print( "<br /><b><center><font color=\"RED\"><H2>All of Movie " + m_id + "'s Cast</H2></font>");
        out.println( "</center><br />" );


		//Setup for the table to be presented. Producer table attribute names are the names of the report table's columns.
		out.println("<center><table border=\"1\">"); 
		out.println("<tr BGCOLOR=\"#cccccc\">");
        out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">Person ID</td>");
		out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">Last Name</td>");
        out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">First Name</td>");
		out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">Role Name</td>");
        out.println("</tr>");
		
		try 
		{ 
			//Loop through the result set, and print each row
            while(resultCast.next()) 
			{ 
		    	out.println("<tr>");
                out.println("     <td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">"+resultCast.getString(1)+"</td>");
		    	out.println("     <td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">"+resultCast.getString(2)+"</td>");
				out.println("     <td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">"+resultCast.getString(3)+"</td>");
				out.println("     <td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">"+resultCast.getString(4)+"</td>");
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
