package sbg.rinoto.neo4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {

	public static void main(String[] args) throws SQLException {
		Connection con = DriverManager
				.getConnection("jdbc:neo4j://localhost:7474/");

		String query = "MATCH (:Movie {title:{1}})<-[:ACTED_IN]-(a:Person) RETURN a.name as actor";

		try (PreparedStatement stmt = con.prepareStatement(query)) {

			stmt.setString(1, "The Matrix");

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					System.out.println(rs.getString("actor"));
				}
			}
		}
	}

}
