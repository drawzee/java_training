package stqa.addressbook.appmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import stqa.addressbook.model.ContactData;
import stqa.addressbook.model.Contacts;
import stqa.addressbook.model.GroupData;
import stqa.addressbook.model.Groups;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Properties;

public class DbHelper {

    private final Properties properties;
    private final SessionFactory sessionFactory;

    public DbHelper() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        properties = new Properties();
    }

    public Groups groups() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<GroupData> result = session.createQuery( "from GroupData" ).list();
        session.getTransaction().commit();
        session.close();
        return new Groups(result);
    }

    public Contacts contacts() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<ContactData> result = session.createQuery( "from ContactData" ).list();
        session.getTransaction().commit();
        session.close();
        return new Contacts(result);
    }

    public Groups contactHasGroup(int contactId, int groupId) {
        Connection conn = null;
        try {
            String target = System.getProperty("target", "local");
            properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
            conn = DriverManager.getConnection(properties.getProperty("db.mysql"));
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(String.format("SELECT * FROM address_in_groups where id = %s and group_id = %s", contactId, groupId));
            Groups groups = new Groups();

            while (rs.next()) {
                groups.add(new GroupData().withId(rs.getInt("group_id")));
            }

            rs.close();
            st.close();
            conn.close();

            return groups;

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
