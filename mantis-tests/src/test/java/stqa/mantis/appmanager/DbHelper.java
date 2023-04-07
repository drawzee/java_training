package stqa.mantis.appmanager;

import stqa.mantis.model.UserData;
import stqa.mantis.model.Users;

import java.sql.*;

public class DbHelper {

    public DbHelper() {
    }

    public Users selectAllUsers() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bugtracker?user=root&password=");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(String.format(
                    "SELECT id, username, realname, email, password FROM mantis_user_table WHERE username != 'administrator';"
            ));
            Users users = new Users();

            while (rs.next()) {
                users.add(new UserData()
                        .withId(rs.getInt("id"))
                        .withUsername(rs.getString("username"))
                        .withRealName(rs.getString("realname"))
                        .withEmail(rs.getString("email"))
                        .withPass(rs.getString("password"))
                );
            }

            rs.close();
            st.close();
            conn.close();

            return users;

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return null;
    }

}
