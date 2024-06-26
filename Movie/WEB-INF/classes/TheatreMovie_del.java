import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class TheatreMovie_del extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Statement state4 = null;
		ResultSet result = null;
		String query = "";
		Connection con = null;

		String t_m_id = request.getParameter("t_m_id");

		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "C##project", "project");
			System.out.println("Congratulations! You are connected successfully.");
		} catch (SQLException e) {
			System.out.println("Error: " + e);
		} catch (Exception e) {
			System.err.println("Exception while loading driver");
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

		query = "delete from theatremovie where t_m_id = '" + t_m_id + "'";

		out.println("<html><head><title>TheatreMovie has been deleted</title>");
		out.println("</head><body>");

		try {
			// For case where the movie table is empty.
			ResultSet countResult = state4.executeQuery("select count(*) from movie");
			countResult.next();
			int rowCount = countResult.getInt(1);

			// If the movie table is empty, state that it is so. If not, state the movie
			// entry has been deleted from the movie table.
			if (rowCount == 0) {
				out.print("<br /><b><center><font color=\"BLACK\"><H2>The TheatreMovie table is empty</H2></font>");
				out.println("</center><br />");
			} else {
				ResultSet test = state4.executeQuery("select t_m_id from theatremovie where t_m_id = '" + t_m_id + "'");
				if (test.next()) {
					// Movie with given m_id exists
					if (test.getInt(1) == Integer.parseInt(t_m_id)) {
						out.print(
								"<br /><b><center><font color=\"BLACK\"><H2>The theatremovie entry has been deleted.</H2></font>");
						out.println("</center><br />");
					}
				} else {
					// Movie with given m_id does not exist
					out.print("<br /><b><center><font color=\"BLACK\"><H2>There is no such entry</H2></font>");
					out.println("</center><br />");
				}
			}

			// For executing the query to delete the movie entry.
			result = state4.executeQuery(query);

		} catch (SQLException e) {
			// If the query fails to execute, catch exception.
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
