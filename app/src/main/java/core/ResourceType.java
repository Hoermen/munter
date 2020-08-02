package core;

public enum ResourceType {
	BLACKBOARD("Tafelbild/Präsentation", true), WORKSHEET("Arbeitsblatt", true), PUPIL_CREATED("Schüler erstellt", false), BOOK("Buch", false), OTHER_UPLOAD("Sonstiges (Upload)", true), OTHER("Sonstiges", false);

	private String readableString;
	private boolean isUpload = false;

	private ResourceType(String readableString, boolean isUpload) {
		this.readableString = readableString;
		this.isUpload = isUpload;
	}

	@Override
	public String toString() {
		return readableString;
	}

	public boolean isUpload() {
		return isUpload;
	}
}
