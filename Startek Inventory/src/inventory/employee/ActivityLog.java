package inventory.employee;

import java.util.Date;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ActivityLog{
	private final StringProperty  comment;
	private final ObjectProperty<Date> date;
	private final StringProperty  type;
	private final StringProperty  performed;


	public ActivityLog(Date date,String comment,String type,String performed){
		this.date = new SimpleObjectProperty<Date>(date);
		this.comment = new SimpleStringProperty(comment);
		this.type = new SimpleStringProperty(type);
		this.performed = new SimpleStringProperty(performed);
	}
	public String getcomment(){
		return comment.get();
	}
	public void setcomment(String comment){
		this.comment.set(comment);
	}
	public StringProperty commentProperty() {
		return comment;
	}
	public Date getdate(){
		return date.get();
	}
	public void setdate(Date date){
		this.date.set(date);
	}
	public ObjectProperty<Date> dateProperty(){
		return date;
	}
	public String gettype(){
		return type.get();
	}
	public void settype(String type){
		this.type.set(type);
	}
	public StringProperty typeProperty() {
		return type;
	}
	public String getperformed(){
		return performed.get();
	}
	public void setperformed(String performed){
		this.performed.set(performed);
	}
	public StringProperty performedProperty() {
		return performed;
	}
}