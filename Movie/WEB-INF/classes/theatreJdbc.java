import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class theatreJdbc extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Statement state4 = null;
		ResultSet result = null;
		String query = "";
		Connection con = null;

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

		// query = "select m_title, m_date, m_length, cat_name, rating_name from movie,
		// category, rating where movie.rating_id = rating.rating_id AND movie.cat_id =
		// category.cat_id";
		query = "select t_id, t_name, t_street, t_city, t_state, t_zip, t_country from theatre";

		out.println("<html><head><title>Moive Table Report</title>");
		out.println("</head><body>");

		out.print("<br /><b><center><font color=\"RED\"><H2>Movie Table Report</H2></font>");
		out.println("</center><br />");
		try {
			result = state4.executeQuery(query);
		} catch (SQLException e) {
			System.err.println("SQLException while executing SQL Statement.");
		}
		out.println("<center><table border=\"1\">");
		out.println("<tr BGCOLOR=\"#cccccc\">");
		out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">Theatre ID</td>");
		out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">Name</td>");
		out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">Street</td>");
		out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">City</td>");
		out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">State</td>");
		out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">Zip Code</td>");
		out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">Country</td>");
		out.println("</tr>");
		try {
			while (result.next()) {
				out.println("<tr>");
				out.println("     <td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">"
						+ result.getString(1) + "</td>");
				out.println("     <td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">"
						+ result.getString(2) + "</td>");
				out.println("     <td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">"
						+ result.getString(3) + "</td>");
				out.println("     <td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">"
						+ result.getString(4) + "</td>");
				out.println("     <td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">"
						+ result.getString(5) + "</td>");
				out.println("     <td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">"
						+ result.getString(6) + "</td>");
				out.println("     <td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">"
						+ result.getString(7) + "</td>");
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
