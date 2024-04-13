import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class person_upd extends HttpServlet 
{
    private Statement state4 = null;
	private String queryUpdatePerson ="";
    private String queryUpdateRoleEntry ="";          
	private Connection con = null; 
    private PrintWriter out = null ;

    public void init() throws ServletException {
        initializeJdbc();
    }

    //Initialize connection and Oracle Jdbc driver
    private void initializeJdbc() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "C##project", "project");

        } catch(SQLException e) {	

			System.out.println("Error: "+ e);	

		} catch (Exception ex) {

            ex.printStackTrace();
            System.err.println("Exception while loading driver");	

        } 
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException,IOException
    {
        response.setContentType("text/html");

        try
		{
			out =  response.getWriter();
		}
		catch (IOException e) 
		{
  			e.printStackTrace();
		}

        //For updating a person entry
        String p_id = request.getParameter("p_id");
        String p_lastName = request.getParameter("p_lastName");
        String p_firstName = request.getParameter("p_firstName");
        String p_pay = request.getParameter("p_pay");

        //For updating a role entry
        String role_id = request.getParameter("p_id2");
        String role_name = request.getParameter("role_name");
        String position = request.getParameter("position");
        String m_id = request.getParameter("m_id");
		
        //Determine based upon person ID input whether to update a person or role table entry.
		if (!p_id.isEmpty() && role_id.isEmpty()) {
            personUpdate(p_lastName, p_firstName, p_pay, p_id);
        } else if (p_id.isEmpty() && !role_id.isEmpty()) {
            roleUpdate(role_name, position, m_id, role_id);
        } else if (!p_id.isEmpty() && !role_id.isEmpty()) {
            out.println("Please choose only to update a person, or a role entry.");
        } else { //p_id.isEmpty() && role_id.isEmpty()
            out.println("Please enter a person ID or role ID.");
        }

  		out.println("</body></html>");
    } 

    private void personUpdate(String p_lastName, String p_firstName, String p_pay, String p_id) {
        //Updates the person entry
        try {
            
            //Person entry must exist to be edited
            if (doesPersonExist(p_id)) {
                
                try 
                {
                    state4 = con.createStatement();
                } 
                catch (SQLException e) 	
                {
                    System.err.println("SQLException while creating statement");			
                }
                
                queryUpdatePerson = "update person set p_lastName = '" + p_lastName + "', p_firstName = '" + p_firstName + 
                    "', p_pay = '" + p_pay + "' where p_id = '" + p_id + "'";

                state4.executeQuery(queryUpdatePerson);

                out.println("Person has been updated.");
    
            } else {
                out.println("No person with ID: " + p_id + " exists.");
            }

        } catch (SQLException e) {
            out.println("Error: " + e.getMessage());
        }

    }

    private void roleUpdate(String role_name, String position, String m_id, String role_id) {
        //Updates the role entry
        try {
            
            //movie entered must exist
            if (doesMovieExist(m_id)) { 
                
                //role entry must exist for it to be edited
                if(doesRoleEntryExist(m_id, role_id, role_name)) {

                    try 
                    {
                        state4 = con.createStatement();
                    } 
                    catch (SQLException e) 	
                    {
                        System.err.println("SQLException while creating statement");			
                    }

                    //For role entry position name
                    String roleAttributeName_Position = "";
                    switch(role_name) {
                        case "Producer":
                            roleAttributeName_Position = "prod_position";
                            break;
                        case "Actor":
                            roleAttributeName_Position = "actor_role";
                            break;
                        case "Actress":
                            roleAttributeName_Position = "actress_role";
                            break;
                        case "Writer":
                            roleAttributeName_Position = "writ_contr";
                            break;
                        case "Director":
                            roleAttributeName_Position = "dir_position";
                            break;
                        default:
                            out.println("\n Error in passing attribute role_name.");
                    }

                    //Due to constraint the p_id and role_id for a given role must be identical, we use role_id as the method argument.
                    queryUpdateRoleEntry = "update " + role_name + " set " + roleAttributeName_Position + " = '" + position + "', m_id = '" + m_id + 
                        "' where p_id = '" + role_id + "' AND m_id = '" + m_id + "'";
    
                    state4.executeQuery(queryUpdateRoleEntry);
                    out.println("The " + role_name + " entry has been updated.");

                } else {
                   out.println("Pre-existing " + role_name + " entry with movie ID: " + m_id + ", role ID/person ID = " + role_id + " does not exist.");
                }
            } else {
                out.println("Movie with ID: " + m_id + " does not exist.");
            }
        } catch (SQLException e) {
            out.println("Error: " + e.getMessage());
        }

    }

    private boolean doesPersonExist(String p_id) throws SQLException {
        //Check if the person already exists 
        String query = "SELECT COUNT(*) FROM person WHERE p_ID = ?";
        PreparedStatement checkPersonExistsSQL = con.prepareStatement(query);
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

    private boolean doesMovieExist(String m_id) throws SQLException {
        //Checks if the movie already exists
        String query = "SELECT COUNT(*) FROM movie WHERE m_ID = ?";
        PreparedStatement movieExistsSQL = con.prepareStatement(query);
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

    private boolean doesRoleEntryExist(String m_id, String p_id, String role_name) throws SQLException {
        //Check if the role entry already exists 
        try {
            String query = "SELECT COUNT(*) FROM " + role_name + " WHERE m_ID = ? AND p_ID = ?";
            PreparedStatement roleExistsSQL = con.prepareStatement(query);
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
        } catch (SQLException e) {
            out.println(e.getMessage());
            return true;
        }
        
    }

}
