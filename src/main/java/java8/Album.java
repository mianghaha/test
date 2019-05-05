package java8;

import java.util.List;

public class Album {
	
	public Album(String name, Artist artist, List<Song> songList){
		this.name = name;
		this.artist = artist;
		this.songList = songList;
	}

	private String name;
	private Artist artist;
	private List<Song> songList;
	public String getName() {
		return name;
	}
	public Artist getArtist() {
		return artist;
	}
	public List<Song> getSongList() {
		return songList;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setArtist(Artist artist) {
		this.artist = artist;
	}
	public void setSongList(List<Song> songList) {
		this.songList = songList;
	}
}
