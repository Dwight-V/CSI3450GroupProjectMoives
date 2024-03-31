import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class theatre_upd extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Statement state4 = null;
		ResultSet result = null;
		String query = "";
		Connection con = null;

		String m_id = request.getParameter("m_id");
		String t_id = request.getParameter("t_id");
		String t_name = request.getParameter("t_name");
		String t_street = request.getParameter("t_street");
		String t_city = request.getParameter("t_city");
		String t_state = request.getParameter("t_state");
		String t_zip = request.getParameter("t_zip");
		String t_country = request.getParameter("t_country");

		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl", "c##project", "project");
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

		query = "update  theatre set t_name = '" + t_name + "', t_street = '" + t_street + "', t_city = '" + t_city
				+ "', t_state = '" + t_state + "', t_zip = '" + t_zip + "', t_country = 					'"
				+ t_country + "'	where t_id = '" + t_id + "'";

		out.println("<html><head><title>Theatre has been updtated</title>");
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
		// size=\"4pt\">theatretitle</td>");
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
			System.out.println("Resultset is not connected");
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
