package sbg.rinoto.neo4j;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Before;
import org.junit.Test;

public class Neo4jTest {

	private Neo4jActorDB neo4jActorDB;

	@Before
	public void setup() throws SQLException {
		neo4jActorDB = new Neo4jActorDB();
		neo4jActorDB.initDB();
	}

	@Test
	public void shouldReadActors() throws SQLException {

		// when
		List<String> actors = neo4jActorDB.readActors();

		// then
		assertThat(actors, hasSize(5));
		assertThat(actors, hasItem("Laurence Fishburne"));
	}

	@Test
	public void shouldCreateMovie() throws SQLException, JsonParseException,
			JsonMappingException, IOException {
		// when
		Movie movie = new Movie("BBT", "1945", "good one");
		neo4jActorDB.createMovie(movie);
		// then
		Movie movieRead = neo4jActorDB.readMovieByTitle("BBT");
		assertThat(movieRead, is(movie));
	}

	@Test
	public void shouldCreateActor() throws SQLException, JsonParseException,
			JsonMappingException, IOException {
		// when
		Actor actor = new Actor("Actor Name", "1945");
		neo4jActorDB.createActor(actor);
		// then
		// Movie movieRead = neo4jActorDB.readMovieByTitle("BBT");
		// assertThat(movieRead, is(movie));
	}

}
