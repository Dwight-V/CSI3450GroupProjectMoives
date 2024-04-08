import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class TheatreMovie_upd extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Statement state4 = null;
		ResultSet result = null;
		String query = "";
		Connection con = null;

		String t_m_id = request.getParameter("t_m_id");
		String t_m_start = request.getParameter("t_m_start");
		String t_m_end = request.getParameter("t_m_end");
		String m_ID = request.getParameter("m_ID");
		String t_ID = request.getParameter("t_ID");

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

		t_m_start = t_m_start.replace("T", " ");
		t_m_end = t_m_end.replace("T", " ");

		query = "update theatremovie set " +
				"t_m_start = TO_TIMESTAMP('" + t_m_start + "', 'YYYY_MM_DD HH24:MI'), " +
				"t_m_end = TO_TIMESTAMP('" + t_m_end + "', 'YYYY_MM_DD HH24:MI'), " +
				"m_ID = '" + m_ID + "', " +
				"t_ID = '" + t_ID + "' " +
				"where t_m_id = '" + t_m_id + "'";
		out.println("<html><head><title>Movie has been updtated</title>");
		out.println(query);
		out.println("</head><body>");

		out.print("<br /><b><center><font color=\"BLACK\"><H2>One Record has updated</H2></font>");
		out.println("</center><br />");
		try {
			result = state4.executeQuery(query);

		} catch (SQLException e) {
			out.println("SQLException while executing SQL Statement:");
			out.println(e);
		}
		// out.println("<center><table border=\"1\">");
		// out.println("<tr BGCOLOR=\"#cccccc\">");
		// out.println("<td align = \"justify\"><font face =\"times new roman\"
		// size=\"4pt\"> </td>");
		// // out.println("<td align = \"justify\"><font face =\"times new roman\"
		// // size=\"4pt\">movtitle</td>");
		// out.println("</tr>");
		// try {
		// while (result.next()) {
		// out.println("<tr>");
		// out.println("<td align = \"justify\"><font face =\"times new roman\"
		// size=\"4pt\">"
		// + result.getString(1) + "</td>");
		// // out.println("<td align = \"justify\"><font face =\"times new roman\"
		// // size=\"4pt\">"+result.getString(2)+"</td>");
		// out.println("</tr>");
		// }
		// } catch (SQLException e) {
		// System.out.println("Resutset is not connected");
		// }

		// out.println("</table></CENTER>");
		// try {
		// result.close();
		// state4.close();
		// con.close();
		// System.out.println("Connection is closed successfully.");
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		out.println("</body></html>");
	}
}
