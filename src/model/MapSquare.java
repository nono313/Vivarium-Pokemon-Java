package model;
public class MapSquare {
	private Coord coord;
	private Element content;
	private boolean sea;
	public MapSquare(Coord coord, Element content, boolean sea) {
		super();
		this.coord = coord;
		this.content = content;
		this.setSea(sea);
	}
	public Coord getCoord() {
		return coord;
	}
	public void setCoord(Coord coord) {
		this.coord = coord;
	}
	public Element getContent() {
		return content;
	}
	public void setContent(Element content) {
		this.content = content;
	}
	public boolean isSea() {
		return sea;
	}
	public void setSea(boolean sea) {
		this.sea = sea;
	}
}
