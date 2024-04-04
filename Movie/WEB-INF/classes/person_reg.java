import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class person_reg extends HttpServlet {
    private PreparedStatement personPstmt, rolePstmt;
    private Connection conn;
    private Statement extraPersonDelete;
    private PrintWriter out;

    public void init() throws ServletException {
        initializeJdbc();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        out = response.getWriter();

        String p_id = request.getParameter("p_id");
        String p_lastName = request.getParameter("p_lastName");
        String p_firstName = request.getParameter("p_firstName");
        String p_pay = request.getParameter("p_pay");
        String role_name = request.getParameter("role_name");
        String role_id = request.getParameter("role_id");
        String position = request.getParameter("position");
        String m_id = request.getParameter("m_id");

        try {
            if (p_id == null || p_id.isEmpty()) {
                out.println("Please add an entry for all required values.");
                return;
            }

            storePerson(p_id, p_lastName, p_firstName, p_pay);
        
            // Construct SQL statement for role insertion based on role_id
            String roleAttributeName_ID = "";
            String roleAttributeName_Position = "";
            switch(role_name) {
                case "Producer":
                    roleAttributeName_ID = "prod_ID";
                    roleAttributeName_Position = "prod_position";
                    break;
                case "Actor":
                    roleAttributeName_ID = "actor_ID";
                    roleAttributeName_Position = "actor_role";
                    break;
                case "Actress":
                    roleAttributeName_ID = "actress_ID";
                    roleAttributeName_Position = "actress_role";
                    break;
                case "Writer":
                    roleAttributeName_ID = "writ_ID";
                    roleAttributeName_Position = "writ_contr";
                    break;
                case "Director":
                    roleAttributeName_ID = "dir_ID";
                    roleAttributeName_Position = "dir_position";
                    break;
                default:
                    out.println("\n Error in passing attribute role_name.");
            }

            storeRole(roleAttributeName_ID, roleAttributeName_Position, p_id, m_id, role_id, role_name, position);

            out.println(p_id + ": " + p_firstName + " " + p_lastName + " is now registered in the database in tables Person and role table " + role_name + ".");

        } catch (Exception ex) {
            out.println("\n Error: " + ex.getMessage());
        } finally {
            out.close();
        }
    }

    private void initializeJdbc() {
        try {
            String driver = "oracle.jdbc.driver.OracleDriver";
            Class.forName(driver);

            String url = "jdbc:oracle:thin:@localhost:1521:orcl";
            String user = "C##project";
            String password = "project";

            conn = DriverManager.getConnection(url, user, password);

            personPstmt = conn.prepareStatement("INSERT INTO person (p_id, p_lastName, p_firstName, p_pay) VALUES (?, ?, ?, ?)");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void storePerson(String p_id, String p_lastName, String p_firstName, String p_pay) throws SQLException {
        personPstmt.setString(1, p_id);
        personPstmt.setString(2, p_lastName);
        personPstmt.setString(3, p_firstName);
        personPstmt.setString(4, p_pay);
        personPstmt.executeUpdate();

    }

    private void storeRole(String roleAttributeName_ID, String roleAttributeName_Position, String p_id, String m_id, 
    String role_id, String role_name, String position) throws SQLException {

        try 
        {
           // Start a transaction
            conn.setAutoCommit(false);

            // Insert into role table determined by choice of role position.
            String roleInsertSQL = "INSERT INTO " + role_name + " (" + roleAttributeName_ID + ", " + roleAttributeName_Position + ", p_ID, m_ID) VALUES (?, ?, ?, ?)";
            rolePstmt = conn.prepareStatement(roleInsertSQL);
            rolePstmt.setString(1, role_id); 
            rolePstmt.setString(2, position);
            rolePstmt.setString(3, p_id);
            rolePstmt.setString(4, m_id);
            int roleTableRowsAffected = rolePstmt.executeUpdate();

            if (roleTableRowsAffected > 0) {
                conn.commit();
            }
        }
        catch (SQLException e)
        {
            conn.rollback();
            // Delete the person entry
            extraPersonDelete = conn.createStatement();
            extraPersonDelete.executeUpdate("DELETE FROM person WHERE p_id = " + p_id);
            conn.commit();

            out.println("\n Error: " + e.getMessage() + ". " + role_name + " table addition has failed. " +
                "\n Person entry with p_ID = " + p_id + " has been deleted.");
        } finally {
            // Set auto-commit to true to revert to default behavior
            conn.setAutoCommit(true);
        }

    }
}
