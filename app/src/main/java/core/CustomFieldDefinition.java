package core;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CustomFieldDefinition implements Serializable {
	private int id;
	private String name;
	private Type type;
	private String data;
	private Layer layer;

	static public enum Layer {
		SEQUENCE("sequence", "Sequenz"), LESSON("lesson", "Stunde"), PLANENTRY("planentry", "Aktion");

		private String code;
		private String value;

		private Layer(String code, String value) {
			this.code = code;
			this.value = value;
		}

		@Override
		public String toString() {
			return value;
		}

		public String getCode() {
			return code;
		}

		/**
		 * @return the value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * @param value the value to set
		 */
		public void setValue(String value) {
			this.value = value;
		}

		/**
		 * @param code the code to set
		 */
		public void setCode(String code) {
			this.code = code;
		}
	}

	static public enum Type {
		TEXTAREA("Mehrzeiliges Textfeld"),
		TEXTFIELD("Einzeiliges Textfeld"),
		TEXTFIELD_SUGGEST("Einzeiliges Textfeld mit Vorschlägen"),
		DROPDOWN("DropDown-Auswahlfeld"),
		FILEUPLOAD("Datei up-/download");

		private String value;

		private Type(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return value;
		}
	}

	public CustomFieldDefinition() {}

	public CustomFieldDefinition(int id, String name, Type type, String data, Layer layer) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.data = data;
		this.layer = layer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	/**
	 * @return the layer
	 */
	public Layer getLayer() {
		return layer;
	}

	/**
	 * @param layer the layer to set
	 */
	public void setLayer(Layer layer) {
		this.layer = layer;
	}
}
