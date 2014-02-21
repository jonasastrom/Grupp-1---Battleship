package Game;

public class Score {
	long points;
	String name;
	
	public Score(long points, String name) {
		this.points = points;
		this.name = name;
	}
	
	public void setPoints(long newScore) {
		this.points = newScore;
	}
	
	public void setName(String newName) {
		this.name = newName;
	}
	
	public long getPoints() {
		return this.points;
	}
	
	public String getName() {
		return this.name;
	}
}
