package sbg.rinoto.neo4j;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class Neo4jActorDB {

	private JsonFactory factory = new JsonFactory();
	private ObjectMapper mapper = new ObjectMapper(factory);

	public List<String> readActors() throws SQLException {
		String query = "MATCH (:Movie {title:{1}})<-[:ACTED_IN]-(a:Person) RETURN a.name as actor";
		try (Connection con = DriverManager
				.getConnection("jdbc:neo4j://localhost:7474/")) {
			try (PreparedStatement stmt = con.prepareStatement(query)) {
				stmt.setString(1, "The Matrix");
				try (ResultSet rs = stmt.executeQuery()) {
					List<String> actors = new ArrayList<>();
					while (rs.next()) {
						actors.add(rs.getString("actor"));
					}
					return actors;
				}
			}
		}
	}

	public boolean createMovie(Movie movie) throws SQLException {
		String query = "CREATE (m:Movie {title:{1}, year:{2}, description:{3}})";
		try (Connection con = DriverManager
				.getConnection("jdbc:neo4j://localhost:7474/")) {
			try (PreparedStatement stmt = con.prepareStatement(query)) {
				stmt.setString(1, movie.tile);
				stmt.setString(2, movie.year);
				stmt.setString(3, movie.description);
				return stmt.execute();
			}
		}

	}

	public boolean initDB() throws SQLException {

		String deleteQuery = "MATCH (n)\n" + "OPTIONAL MATCH (n)-[r]-()\n"
				+ "DELETE n,r";
		execute(deleteQuery);

		String initQuery = "CREATE (TheMatrix:Movie {title:'The Matrix', year:1999, description:'Welcome to the Real World'})\n"
				+ "CREATE (Keanu:Person {name:'Keanu Reeves', born:1964})\n"
				+ "CREATE (Carrie:Person {name:'Carrie-Anne Moss', born:1967})\n"
				+ "CREATE (Laurence:Person {name:'Laurence Fishburne', born:1961})\n"
				+ "CREATE (Hugo:Person {name:'Hugo Weaving', born:1960})\n"
				+ "CREATE (AndyW:Person {name:'Andy Wachowski', born:1967})";
		return execute(initQuery);
	}

	private boolean execute(String query) throws SQLException {
		try (Connection con = DriverManager
				.getConnection("jdbc:neo4j://localhost:7474/")) {
			try (PreparedStatement stmt = con.prepareStatement(query)) {
				return stmt.execute();
			}
		}
	}

	public Movie readMovieByTitle(String title) throws SQLException,
			JsonParseException, JsonMappingException, IOException {
		String query = "MATCH (movie {title:{1}}) RETURN movie";
		try (Connection con = DriverManager
				.getConnection("jdbc:neo4j://localhost:7474/")) {
			try (PreparedStatement stmt = con.prepareStatement(query)) {
				stmt.setString(1, title);
				try (ResultSet rs = stmt.executeQuery()) {
					if (rs.next()) {
						String movieString = rs.getString("movie");
						Map<String, String> movieRead = mapper.readValue(
								movieString,
								new TypeReference<Map<String, String>>() {
								});
						return Movie.fromMap(movieRead);
					}
					return null;
				}
			}
		}
	}

	public boolean createActor(Actor actor) throws SQLException {
		String createActorQuery = "CREATE (a:Actor {name:'" + actor.name
				+ "', born:'" + actor.year + "'})";
		return execute(createActorQuery);
	}

}
