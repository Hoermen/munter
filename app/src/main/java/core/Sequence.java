package core;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Sequence implements Serializable {
	private int id;
	private String title;
	private String subject;
	private int grade;
	private String preknowledge = "";
	private String goal = "";
	private int standard;
	private String comments = "";
	private boolean markUnfinished = false;
	private boolean markUnfinishedSubEntry = false;
	private ArrayList<CustomField> customFields = new ArrayList<>();
	private boolean isMine = false;
	private boolean isShared = false;
	private String sharedBy;

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public Sequence() {}

	public Sequence(int id, String title, String subject, int grade, String preknowledge, int standard) {
		this.id = id;
		this.title = title;
		this.subject = subject;
		this.grade = grade;
		this.preknowledge = preknowledge;
		this.standard = standard;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPreknowledge() {
		return preknowledge;
	}

	public void setPreknowledge(String preknowledge) {
		this.preknowledge = preknowledge;
	}

	public int getStandard() {
		return standard;
	}

	public void setStandard(int standard) {
		this.standard = standard;
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
	 * @return the markUnfinishedSubEntry
	 */
	public boolean isMarkUnfinishedSubEntry() {
		return markUnfinishedSubEntry;
	}

	/**
	 * @param markUnfinishedSubEntry the markUnfinishedSubEntry to set
	 */
	public void setMarkUnfinishedSubEntry(boolean markUnfinishedSubEntry) {
		this.markUnfinishedSubEntry = markUnfinishedSubEntry;
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

	public boolean isShared() {
		return isShared;
	}

	public void setShared(boolean isShared) {
		this.isShared = isShared;
	}

	public String getSharedBy() {
		return sharedBy;
	}

	public void setSharedBy(String sharedBy) {
		this.sharedBy = sharedBy;
	}
}
