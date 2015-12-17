package pl.edu.wat.map.model;


/**
 * Model class for author
 * @author Marcel Paduch
 * @version 1
 * @since 17/12/2015
 */
public class Author {
	private String avatarUrl;
	private String name;
	private String uid;
	public Author(){

	}
	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Author(String avatarUrl, String name, String uid) {

		this.avatarUrl = avatarUrl;
		this.name = name;
		this.uid = uid;
	}
}
