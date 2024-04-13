import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;


public class person_del extends HttpServlet {
    
    private Connection con; 
    private PreparedStatement prepStatement; 
    private Statement statement;
    private PrintWriter out;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        //Get parameters from person_reg.html
        String p_ID = request.getParameter("p_ID");
        String role_name = request.getParameter("role_name");
        String role_ID = request.getParameter("role_ID");

        try {
            //Load driver for Oracle, and connect to the Oracle database
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "C##project", "project");
            
            String roleAttributeName_ID = "";
            
            //Delete from a selected role table
            if (!role_ID.isEmpty() && p_ID.isEmpty()) { 

                switch(role_name) {
                    case "Producer":
                        roleAttributeName_ID = "prod_ID";    
                        break;
                    case "Actor":
                        roleAttributeName_ID = "actor_ID";
                        break;
                    case "Actress":
                        roleAttributeName_ID = "actress_ID";
                        break;
                    case "Writer":
                        roleAttributeName_ID = "writ_ID";
                        break;
                    case "Director":
                        roleAttributeName_ID = "dir_ID";
                        break;
                    default:
                        out.println("\n Error in passing attribute role_name.");
                }

                //Checks if role table select is empty or not. 
                if (checkIfRoleTableEmpty(role_name)) {
                    //The role table select is empty - output message stating so
                    response.setContentType("text/html");
                    out = response.getWriter();
                    out.println("<html><head><title>Empty table</title></head><body>");
                    out.println("<br /><b><center><font color=\"RED\"><H2>Role table " + role_name + " IS empty.</H2></font></center><br />");
                    out.println("</body></html>");
                } else {
                    //The role table selected is NOT empty - delete the entry with the entered role ID
                    deleteRoleTableEntry(role_name, roleAttributeName_ID, role_ID);
                    
                    response.setContentType("text/html");
                    out = response.getWriter();
                    out.println("<html><head><title>Empty table</title></head><body>");
                    out.println("<br /><b><center><font color=\"RED\"><H2>Role table " + role_name + " entry with role_ID: " + role_ID + " has been removed.</H2></font></center><br />");
                    out.println("</body></html>");
                }

            } else if(!(p_ID.isEmpty()) && role_ID.isEmpty()) { //delete a person and all of their role table entries
                
                boolean checkPersonTable = checkIfPersonTableEmpty();
                boolean checkIfPersonExists = checkIfPersonExists(p_ID);

                if (!checkPersonTable) {
                    
                    if (checkIfPersonExists) {
                        // Perform actions when person doesn't exist
                        response.setContentType("text/html");
                        out = response.getWriter();
                        out.println("<html><head><title>Error</title></head><body>");
                        out.println("<br /><b><center><font color=\"RED\"><H2>No such person exists.</H2></font></center><br />");
                        out.println("</body></html>");
                    } else {
                        deleteAllRoleTableEntries(p_ID); //To cycle through all role tables, and delete any entry that has the entered p_ID
                        deleteFromPersonTable(p_ID); //To delete the person with such p_ID
                        //Output if person entry has been succesfully deleted
                        response.setContentType("text/html");
                        out = response.getWriter();
                        out.println("<html><head><title>Person has been deleted</title></head><body>");
                        out.println("<br /><b><center><font color=\"BLACK\"><H2>The Person entry has been deleted.</H2></font></center><br />");
                        out.println("</body></html>");
                    }
                   
                } else if (checkPersonTable) {
                    response.setContentType("text/html");
                    out = response.getWriter();
                    out.println("<html><head><title>Person table is empty</title></head><body>");
                    out.println("<br /><b><center><font color=\"BLACK\"><H2>The Person table is empty.</H2></font></center><br />");
                    out.println("</body></html>");
                } 
                
            } else if (!p_ID.isEmpty() && !role_ID.isEmpty()) {
                response.setContentType("text/html");
                out = response.getWriter();
                out.println("<html><head><title>Person table is empty</title></head><body>");
                out.println("<br /><b><center><font color=\"BLACK\"><H2>Please choose to only delete a role entry or all of a person's entries.</H2></font></center><br />");
                out.println("</body></html>");
            } else {
                response.setContentType("text/html");
                out = response.getWriter();
                out.println("<html><head><title>Person table is empty</title></head><body>");
                out.println("<br /><b><center><font color=\"BLACK\"><H2>Please enter a person ID or role ID and role type value.</H2></font></center><br />");
                out.println("</body></html>");
            }

        } catch (SQLException e) {
            //Output if person entry fails to delete
            e.printStackTrace();
            System.err.println("SQL Exception Message: " + e.getMessage()); 
        } finally {
            //Close connection
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //Deletes role table entry
    private void deleteRoleTableEntry(String role_name, String roleTableID, String p_ID) throws SQLException {
        statement = con.createStatement();
        statement.executeUpdate("DELETE FROM " + role_name + " WHERE " + roleTableID + " = " + p_ID);
    }
    
    //Return true if the role table is empty, otherwise false.
    private boolean checkIfRoleTableEmpty(String roleTableName) throws SQLException {
        //For case where the role table is empty.
        String query = "select count(*) from " + roleTableName;
        prepStatement = con.prepareStatement(query);
        ResultSet countResult = prepStatement.executeQuery();
    
        countResult.next();
        int rowCount = countResult.getInt(1);
    
        // If the selected role table is empty, return true. If not, state the table is not empty.
        return rowCount == 0;
    }
    
    //Deletes the role table entries should the person entry with the p_ID be deleted - a safeguard.
    private void deleteAllRoleTableEntries(String p_ID) throws SQLException {
        String[] roleTables = {"actor", "actress", "director", "writer", "producer"};
        String query;
        for (String tableName : roleTables) {
            //Cycle through all role tables, then delete any entries that have their p_ID equal to what the user inserted
            query = "DELETE FROM " + tableName + " WHERE p_ID = ?";
            prepStatement = con.prepareStatement(query);
            prepStatement.setString(1, p_ID);
            prepStatement.executeUpdate();
        }
    }

    //Checks if the person table is empty
    private boolean checkIfPersonTableEmpty() throws SQLException {
        String query = "select count(*) from person";
        prepStatement = con.prepareStatement(query);
        ResultSet countResult = prepStatement.executeQuery();

        countResult.next();
        int rowCount = countResult.getInt(1);

        //If the person is empty, return true. If not, state the table is not empty.
        if (rowCount == 0) {
            return true;
        } else {
            return false;
        }	
    }

    //Checks if the person entry with the p_ID exists or not
    private boolean checkIfPersonExists(String p_ID) throws SQLException {
        String query = "SELECT COUNT(*) FROM person WHERE p_ID = ?";
        try (PreparedStatement prepStatement = con.prepareStatement(query)) {
            prepStatement.setString(1, p_ID);
            ResultSet resultSet = prepStatement.executeQuery();
            resultSet.next(); // Move cursor to first row
            if (resultSet.getInt(1) > 0 ) {
                return false;
            } else {
                return true;
            }
        }
    }

    //Delete person entry with the p_ID entered - executed AFTER all role table entries with said p_ID are deleted.
    private void deleteFromPersonTable(String p_ID) throws SQLException {
        String query = "DELETE FROM person WHERE p_ID = ?";
        prepStatement = con.prepareStatement(query);
        prepStatement.setString(1, p_ID);
        prepStatement.executeUpdate();
    }
}
