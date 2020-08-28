package core;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class PlanEntry implements Serializable {
	private int id;
	private int lessonId;
	private int length;
	private int start;
	private String title;
	private String goal = "";
	private String socialForm = "";
	private String color = "";
	private int parentPlanEntryId;
	private String comments = "";
	private boolean markUnfinished = false;
	private ArrayList<CustomField> customFields = new ArrayList<>();
	private String reserve = "";
	private String beschreibung;

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

	public PlanEntry() {}

	public PlanEntry(int lessonId, int track, int start, int length, String title) {
		this(0, lessonId, track, start, length, title);
	}

	public PlanEntry(int planEntryId, int lessonId, int track, int start, int length, String title) {
		this.id = planEntryId;
		this.lessonId = lessonId;
		this.start = start;
		this.length = length;
		this.title = title;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the planId
	 */
	public int getLessonId() {
		return lessonId;
	}

	/**
	 * @param lessonId 
	 */
	public void setLessonId(int lessonId) {
		this.lessonId = lessonId;
	}

	/**
	 * @return the length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * @return the start
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the goal
	 */
	public String getGoal() {
		return goal;
	}

	/**
	 * @param goal the goal to set
	 */
	public void setGoal(String goal) {
		this.goal = goal;
	}

	/**
	 * @return the socialForm
	 */
	public String getSocialForm() {
		return socialForm;
	}

	/**
	 * @param socialForm the socialForm to set
	 */
	public void setSocialForm(String socialForm) {
		this.socialForm = socialForm;
	}


	/**
	 * @return the parentPlanEntryId
	 */
	public int getParentPlanEntryId() {
		return parentPlanEntryId;
	}

	/**
	 * @param parentPlanEntryId the parentPlanEntryId to set
	 */
	public void setParentPlanEntryId(int parentPlanEntryId) {
		this.parentPlanEntryId = parentPlanEntryId;
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

	public PlanEntry clonePlanEntry() {
		PlanEntry clone = new PlanEntry();
		// lessonId, start, track, parentPlanEntry are not cloned
		clone.length = length;
		clone.title = title;
		clone.goal = goal;
		clone.socialForm = socialForm;
		clone.comments = comments;
		clone.markUnfinished = markUnfinished;
		clone.customFields.addAll(customFields);
		clone.color = color;
		return clone;
	}

	public ArrayList<CustomField> getCustomFields() {
		return customFields;
	}

	public void setCustomFields(ArrayList<CustomField> customFields) {
		this.customFields = customFields;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
