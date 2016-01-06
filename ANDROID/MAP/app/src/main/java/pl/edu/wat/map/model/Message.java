package pl.edu.wat.map.model;


/**
 * Model class for message
 * @author Hubert Faszcza
 * @version 1
 * @since 17/12/2015
 */
public class Message {
	private Author author;
	private String content;
	private String date;

	public Message(){

	}
	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Message(Author author, String content, String date) {

		this.author = author;
		this.content = content;
		this.date = date;
	}
}
