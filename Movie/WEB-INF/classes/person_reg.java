import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class person_reg extends HttpServlet {
    private PreparedStatement pstmt;
    private Connection conn;

    public void init() throws ServletException {
        initializeJdbc();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

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
            out.println(p_id + ": " + p_firstName + " " + p_lastName + " is now registered in the database, check if relevant role table has an entry");

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

            // Insert into role table - MOVE THIS TO A METHOD storeRole() LATER!!!!
            String roleInsertSQL = "insert into " + role_name + " (" + roleAttributeName_ID + ", " + roleAttributeName_Position + ", p_id, m_id) VALUES (?, ?, ?, ?)";
            PreparedStatement roleStatement = conn.prepareStatement(roleInsertSQL);
            roleStatement.setString(1, role_id); 
            roleStatement.setString(2, position);
            roleStatement.setString(3, p_id);
            roleStatement.setString(4, m_id);
            roleStatement.executeUpdate();
            //roleStatement.close();
            
            /*COME BACK - need to have an if statement to check if the person and role they're in have their entries created.
              If not, delete the extra person.*/

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
            String user = "C##CSI3450";
            String password = "johnRing19841";

            conn = DriverManager.getConnection(url, user, password);

            pstmt = conn.prepareStatement("INSERT INTO person (p_id, p_lastName, p_firstName, p_pay) VALUES (?, ?, ?, ?)");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void storePerson(String p_id, String p_lastName, String p_firstName, String p_pay) throws SQLException {
        pstmt.setString(1, p_id);
        pstmt.setString(2, p_lastName);
        pstmt.setString(3, p_firstName);
        pstmt.setString(4, p_pay);
        pstmt.executeUpdate();
        //pstmt.close();
    }

    
}
