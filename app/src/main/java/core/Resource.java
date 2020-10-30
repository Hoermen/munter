package core;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Resource implements Serializable {
	private int id;
	private String title;
	private String textContent;
	private String filename;
	private String type;
	private int lessonid;
	private int planentryid;

	public int getLessonid() {
		return lessonid;
	}

	public void setLessonid(int lessonid) {
		this.lessonid = lessonid;
	}

	public int getPlanentryid() {
		return planentryid;
	}

	public void setPlanentryid(int planentryid) {
		this.planentryid = planentryid;
	}

	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public Resource() {}

	public Resource(int id, String title, String type) {
		this.id = id;
		this.title = title;
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

}
