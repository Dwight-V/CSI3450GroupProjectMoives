import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class person_reg extends HttpServlet {
    private PreparedStatement personPstmt, rolePstmt;
    private Connection conn;
    private PrintWriter out;

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

            conn = DriverManager.getConnection(url, user, password);

            personPstmt = conn.prepareStatement("INSERT INTO person (p_id, p_lastName, p_firstName, p_pay) VALUES (?, ?, ?, ?)");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        out = response.getWriter();

        String p_id = request.getParameter("p_id");
        String p_lastName = request.getParameter("p_lastName");
        String p_firstName = request.getParameter("p_firstName");
        String p_pay = request.getParameter("p_pay");
        String role_name = request.getParameter("role_name");
        String role_id = p_id;
        String position = request.getParameter("position");
        String m_id = request.getParameter("m_id");

        try {
            if (p_id == null || p_id.isEmpty()) {
                out.println("Please add an entry for all required values.");
                return;
            }

            //Notify user if there is a person with the ID entered that already exists, and that a role table entry addition is to be attempted.
            if (doesPersonExist(p_id)) {
                out.println("Person with ID: " +  p_id + " already exists. Attempting to create " + role_name + " entry for that person.");
            } else {
                storePerson(p_id, p_lastName, p_firstName, p_pay);
                out.println("Person with ID: " +  p_id + " created. Attempting to create " + role_name + " entry for that person.");
            }

            //Construct SQL statement for role insertion based on role_id
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

            //Movie with entered m_id must exist for the role entry to be added
            if (doesMovieExist(m_id)) {

                //If role entry exists already, notify user
                if(doesRoleEntryExist(m_id, p_id, role_name)) {

                    out.println("\n" + role_name + " entry with person ID = " + roleAttributeName_ID + ": " + p_id + " and movie ID: " + m_id + 
                    " already exists, and was not created.");

                } else {
                    //Insert new role table entry
                    storeRole(roleAttributeName_ID, roleAttributeName_Position, p_id, m_id, role_id, role_name, position);
                    out.println(p_id + ": " + p_firstName + " " + p_lastName + " is registered in the database role table " + role_name + ".");
                }
            } else {
                //Movie does not exist, and the role entry therefore cannot be added.
                out.println("Movie with ID: " + m_id + " does not exist.");
            }
            
        } catch (Exception ex) {
            out.println("\n Error: " + ex.getMessage());
        } finally {
            out.close();
        }
    }

    //Check if the person already exists
    private boolean doesPersonExist(String p_id) throws SQLException { 
        String query = "SELECT COUNT(*) FROM person WHERE p_ID = ?";
        PreparedStatement checkPersonExistsSQL = conn.prepareStatement(query);
        checkPersonExistsSQL.setString(1, p_id);
        
        ResultSet resultSet = checkPersonExistsSQL.executeQuery();
        resultSet.next();
        int personExistsCheck = resultSet.getInt(1);

        if (personExistsCheck > 0) {
            return true;
        } else {
            return false;
        }
    }

    //Inserts person entry into person table.
    private void storePerson(String p_id, String p_lastName, String p_firstName, String p_pay) throws SQLException {
        try 
        {
            personPstmt.setString(1, p_id);
            personPstmt.setString(2, p_lastName);
            personPstmt.setString(3, p_firstName);
            personPstmt.setString(4, p_pay);
            personPstmt.executeUpdate();
        } catch (SQLException e)
        {
            int errorCode = e.getErrorCode();
            String sqlState = e.getSQLState();

            out.println("Error code:" + errorCode + " SQL State: " + sqlState);
            //out.println("Error: " + e.getMessage() + ". A Person table entry already exists with p_ID: " + p_id + ". Please choose another.");
        }

    }

    //Checks if the movie with the m_id exists or not.
    private boolean doesMovieExist(String m_id) throws SQLException {
        
        String query = "SELECT COUNT(*) FROM movie WHERE m_ID = ?";
        PreparedStatement movieExistsSQL = conn.prepareStatement(query);
        movieExistsSQL.setString(1, m_id);
        
        ResultSet resultSet = movieExistsSQL.executeQuery();
        resultSet.next();
        int movieExistsCheck = resultSet.getInt(1);

        if (movieExistsCheck > 0) {
            return true;
        } else {
            return false;
        }
    }

    //Check if the the movie already exists 
    private boolean doesRoleEntryExist(String m_id, String p_id, String role_name) throws SQLException {
        
        String query = "SELECT COUNT(*) FROM " + role_name + " WHERE m_ID = ? AND p_ID = ?";
        PreparedStatement roleExistsSQL = conn.prepareStatement(query);
        roleExistsSQL.setString(1, m_id);
        roleExistsSQL.setString(2, p_id);
        
        ResultSet resultSet = roleExistsSQL.executeQuery();
        resultSet.next();
        int roleEntryExistsCheck = resultSet.getInt(1);

        if (roleEntryExistsCheck > 0) {
            return true;
        } else {
            return false;
        }
        
    }

    //Inserts role table entry
    private void storeRole(String roleAttributeName_ID, String roleAttributeName_Position, String p_id, String m_id, 
    String role_id, String role_name, String position) throws SQLException {
        
        try 
        {
            //Insert into role table determined by choice of role position.
            String roleInsertSQL = "INSERT INTO " + role_name + " (" + roleAttributeName_ID + ", " + roleAttributeName_Position + ", p_ID, m_ID) VALUES (?, ?, ?, ?)";
            rolePstmt = conn.prepareStatement(roleInsertSQL);
            rolePstmt.setString(1, role_id); 
            rolePstmt.setString(2, position);
            rolePstmt.setString(3, p_id);
            rolePstmt.setString(4, m_id);
            rolePstmt.executeUpdate();
   
        }
        catch (SQLException e) {//This should not happen if the check method worked prior...
            out.println("\n Error: " + e.getMessage());
        } 

    }
}
