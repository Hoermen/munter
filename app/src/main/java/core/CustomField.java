package core;

import java.io.Serializable;

import core.CustomFieldDefinition.Type;

@SuppressWarnings("serial")
public class CustomField implements Serializable {
	private int id;
	private String name;
	private Type type;
	private String data;
	private String value;

	public CustomField() {}

	public CustomField(int id, String name, Type type, String data, String value) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.data = data;
		this.value = value;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
