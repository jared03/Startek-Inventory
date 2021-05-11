package inventory.dashboard;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Alert {
	private final StringProperty  idemployee;
	private final StringProperty  fullname;
	private final StringProperty  status;
	private final StringProperty  supervisor;
	private final StringProperty  description;
	private final StringProperty  alert_status;

	public Alert(String idemployee,
				String fullname,
				String status,
				String supervisor,
				String description,
				String alert_status){
		this.idemployee = new SimpleStringProperty(idemployee);
		this.fullname = new SimpleStringProperty(fullname);
		this.status = new SimpleStringProperty(status);
		this.supervisor = new SimpleStringProperty(supervisor);
		this.description = new SimpleStringProperty(description);
		this.alert_status = new SimpleStringProperty(alert_status);

	}

	public String getidemployee(){
		return idemployee.get();
	}
	public void setidemployee(String idemployee){
		this.idemployee.set(idemployee);
	}
	public StringProperty idemployeeProperty() {
		return idemployee;
	}
	public String getfullname(){
		return fullname.get();
	}
	public void setfullname(String fullname){
		this.fullname.set(fullname);
	}
	public StringProperty fullnameProperty() {
		return fullname;
	}
	public String getstatus(){
		return status.get();
	}
	public void setstatus(String status){
		this.status.set(status);
	}
	public StringProperty statusProperty() {
		return status;
	}
	public String getsupervisor(){
		return supervisor.get();
	}
	public void setsupervisor(String supervisor){
		this.supervisor.set(supervisor);
	}
	public StringProperty supervisorProperty() {
		return supervisor;
	}
	public String getdescription(){
		return description.get();
	}
	public void setdescription(String description){
		this.description.set(description);
	}
	public StringProperty descriptionProperty() {
		return description;
	}
	public String getalert_status(){
		return alert_status.get();
	}
	public void setalert_status(String alert_status){
		this.alert_status.set(alert_status);
	}
	public StringProperty alert_statusProperty() {
		return alert_status;
	}
}
