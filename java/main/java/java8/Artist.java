package java8;

import java.util.List;

public class Artist {

	private String name;
	private String nation;
	private List<Album> albumList;
	public String getName() {
		return name;
	}
	public String getNation() {
		return nation;
	}
	public List<Album> getAlbumList() {
		return albumList;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public void setAlbumList(List<Album> albumList) {
		this.albumList = albumList;
	}
}
