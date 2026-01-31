package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;



public class InsertDemo {

    static String url = "jdbc:oracle:thin:@localhost:1521:XE";
    static String username = "SYSTEM";
    static String password = "jaya0919";

    public static void main(String[] args) {

        try {
            // 1. Load driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // 2. Get connection
            Connection con = DriverManager.getConnection(url, username, password);

            // 3. INSERT using PreparedStatement
            String insertSql =
                "INSERT INTO EMP (EMP_ID, NAME, SALARY) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(insertSql);

            String[] names = {
                "Amit", "Ravi", "Suresh", "Kiran", "Anita",
                "Priya", "Rahul", "Neha", "Vijay", "Sunita"
            };

            int index = 0;
            for (int empId = 111; empId <= 120; empId++) {
                ps.setInt(1, empId);
                ps.setString(2, names[index]);
                ps.setInt(3, 5000 + empId);
                ps.executeUpdate();
                index++;
            }

            // 4. SELECT using Statement (IMPORTANT)
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM EMP");

            while (rs.next()) {
                System.out.println(
                    rs.getInt("EMP_ID") + "\t" +
                    rs.getString("NAME") + "\t" +
                    rs.getInt("SALARY")
                );
            }

            // 5. Close
            rs.close();
            st.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

