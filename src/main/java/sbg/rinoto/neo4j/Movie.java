package sbg.rinoto.neo4j;

import java.util.Map;

public class Movie {

	public String tile;
	public String year;
	public String description;

	public Movie(String tile, String year, String description) {
		this.tile = tile;
		this.year = year;
		this.description = description;
	}

	public Movie() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((tile == null) ? 0 : tile.hashCode());
		result = prime * result + ((year == null) ? 0 : year.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movie other = (Movie) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (tile == null) {
			if (other.tile != null)
				return false;
		} else if (!tile.equals(other.tile))
			return false;
		if (year == null) {
			if (other.year != null)
				return false;
		} else if (!year.equals(other.year))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Movie [tile=" + tile + ", year=" + year + ", description="
				+ description + "]";
	}

	public static Movie fromMap(Map<String, String> m) {
		return new Movie(m.get("title"), m.get("year"), m.get("description"));
	}

}
