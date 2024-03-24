import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class person_upd extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Statement state4 = null;
		ResultSet result = null;
		String query = "";
		Connection con = null;

		String m_id = request.getParameter("m_id");
		String m_title = request.getParameter("m_title");
		String m_date = request.getParameter("m_date");
		String m_synopsis = request.getParameter("m_synopsis");
		String m_length = request.getParameter("m_length");
		String rating_id = request.getParameter("rating_id");
		String cat_id = request.getParameter("cat_id");

		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl", "project", "project");
			System.out.println("Congratulations! You are connected successfully.");
		} catch (SQLException e) {
			System.out.println("Error: " + e);
		} catch (Exception e) {
			System.err.println("Exception while loading  driver");
		}
		try {
			state4 = con.createStatement();
		} catch (SQLException e) {
			System.err.println("SQLException while creating statement");
		}

		response.setContentType("text/html");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}

		query = "update  movie set m_title = '" + m_title + "', m_date = '" + m_date + "', m_length = '" + m_length
				+ "', rating_id = '" + rating_id + "', cat_id = '" + cat_id + "', m_synopsis = '" + m_synopsis
				+ "'	where m_id = '" + m_id + "'";

		out.println("<html><head><title>Movie has been updtated</title>");
		out.println("</head><body>");

		out.print("<br /><b><center><font color=\"BLACK\"><H2>One Record has updated</H2></font>");
		out.println("</center><br />");
		try {
			result = state4.executeQuery(query);

		} catch (SQLException e) {
			System.err.println("SQLException while executing SQL Statement.");
		}
		out.println("<center><table border=\"1\">");
		out.println("<tr BGCOLOR=\"#cccccc\">");
		out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\"> </td>");
		// out.println("<td align = \"justify\"><font face =\"times new roman\"
		// size=\"4pt\">movtitle</td>");
		out.println("</tr>");
		try {
			while (result.next()) {
				out.println("<tr>");
				out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">"
						+ result.getString(1) + "</td>");
				// out.println("<td align = \"justify\"><font face =\"times new roman\"
				// size=\"4pt\">"+result.getString(2)+"</td>");
				out.println("</tr>");
			}
		} catch (SQLException e) {
			System.out.println("Resutset is not connected");
		}

		out.println("</table></CENTER>");
		try {
			result.close();
			state4.close();
			con.close();
			System.out.println("Connection is closed successfully.");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		out.println("</body></html>");
	}
}
