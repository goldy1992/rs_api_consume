package calculator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {
	
	private Integer id;
	private String name;
	private Current current;
	private String icon;
	private String icon_large;
	private String type;
	private String description;
	private boolean members;
	
	public Item() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Current getCurrent() {
		return current;
	}

	public void setCurrent(Current current) {
		this.current = current;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIcon_large() {
		return icon_large;
	}

	public void setIcon_large(String icon_large) {
		this.icon_large = icon_large;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isMembers() {
		return members;
	}

	public void setMembers(boolean members) {
		this.members = members;
	}
	
	

}
