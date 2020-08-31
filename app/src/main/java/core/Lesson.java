package core;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Lesson implements Serializable {
	private int id;
	private String title;
	private int length;
	private int order;
	private int sequenceid;
	private String goal = "";
	private String homeworks = "";
	private String comments = "";
	private boolean markUnfinished = false;
	private boolean markUnfinishedPlanEntries = false;
	private ArrayList<CustomField> customFields = new ArrayList<>();
	private boolean isMine = false;
	private String beschreibung = "";
	private String feedback = "";


	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}


	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public Lesson() {}

	public Lesson(int id, String title, int length, int order, int sequenceid) {
		this.id = id;
		this.title = title;
		this.length = length;
		this.order = order;
		this.sequenceid = sequenceid;
	}

	public int getSequenceid() {
		return sequenceid;
	}

	public void setSequenceid(int sequenceid) {
		this.sequenceid = sequenceid;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
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
	 * @return the homeworks
	 */
	public String getHomeworks() {
		return homeworks;
	}

	/**
	 * @param homeworks the homeworks to set
	 */
	public void setHomeworks(String homeworks) {
		this.homeworks = homeworks;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the markUnfinished
	 */
	public boolean isMarkUnfinished() {
		return markUnfinished;
	}

	/**
	 * @param markUnfinished the markUnfinished to set
	 */
	public void setMarkUnfinished(boolean markUnfinished) {
		this.markUnfinished = markUnfinished;
	}

	/**
	 * @return the markUnfinishedPlanEntries
	 */
	public boolean isMarkUnfinishedPlanEntries() {
		return markUnfinishedPlanEntries;
	}

	/**
	 * @param markUnfinishedPlanEntries the markUnfinishedPlanEntries to set
	 */
	public void setMarkUnfinishedPlanEntries(boolean markUnfinishedPlanEntries) {
		this.markUnfinishedPlanEntries = markUnfinishedPlanEntries;
	}

	public ArrayList<CustomField> getCustomFields() {
		return customFields;
	}

	public void setCustomFields(ArrayList<CustomField> customFields) {
		this.customFields = customFields;
	}

	public boolean isMine() {
		return isMine;
	}

	public void setMine(boolean isMine) {
		this.isMine = isMine;
	}
}
