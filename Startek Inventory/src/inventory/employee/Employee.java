package inventory.employee;

import java.util.Date;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Employee{
	private final StringProperty  fname;
	private final StringProperty  lname;
	private final StringProperty  email;
	private final StringProperty  idemployee;
	private final StringProperty  status;
	private final ObjectProperty<Date> hiredate;
	private final StringProperty  supervisor;
	private final StringProperty  jobtitle;
	private final StringProperty  description;
	private final StringProperty  country;
	private final StringProperty  site;
	private final StringProperty  clockid;
	private final StringProperty  fullname;

	public Employee(String fname,
			String lname,
			String email,
			String idemployee,
			String status,
			Date hiredate,
			String supervisor,
			String jobtitle,
			String description,
			String country,
			String site,
			String clockid,
			String fullname){
		this.fname = new SimpleStringProperty(fname);
		this.lname = new SimpleStringProperty(lname);
		this.email = new SimpleStringProperty(email);
		this.idemployee = new SimpleStringProperty(idemployee);
		this.status = new SimpleStringProperty(status);
		this.hiredate = new SimpleObjectProperty<Date>(hiredate);
		this.supervisor = new SimpleStringProperty(supervisor);
		this.jobtitle = new SimpleStringProperty(jobtitle);
		this.description = new SimpleStringProperty(description);
		this.country = new SimpleStringProperty(country);
		this.site = new SimpleStringProperty(site);
		this.clockid = new SimpleStringProperty(clockid);
		this.fullname = new SimpleStringProperty(fullname);
	}
	public String getfname(){
		return fname.get();
	}
	public void setfname(String fname){
		this.fname.set(fname);;
	}
	public StringProperty fnameProperty() {
		return fname;
	}
	public String getlname(){
		return lname.get();
	}
	public void setlname(String lname){
		this.lname.set(lname);
	}
	public StringProperty lnameProperty() {
		return lname;
	}
	public String getemail(){
		return email.get();
	}
	public void setemail(String email){
		this.email.set(email);
	}
	public StringProperty emailProperty() {
		return email;
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
	public String getstatus(){
		return status.get();
	}
	public void setstatus(String status){
		this.status.set(status);
	}
	public StringProperty statusProperty() {
		return status;
	}
	public Date gethiredate(){
		return hiredate.get();
	}
	public void sethiredate(Date hiredate){
		this.hiredate.set(hiredate);
	}
	public ObjectProperty<Date> hiredateProperty(){
		return hiredate;
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
	public String getjobtitle(){
		return jobtitle.get();
	}
	public void setjobtitle(String jobtitle){
		this.jobtitle.set(jobtitle);
	}
	public StringProperty jobtitleProperty() {
		return jobtitle;
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
	public String getcountry(){
		return country.get();
	}
	public void setcountry(String country){
		this.country.set(country);
	}
	public StringProperty countryProperty() {
		return country;
	}
	public String getsite(){
		return site.get();
	}
	public void setsite(String site){
		this.site.set(site);
	}
	public StringProperty siteProperty() {
		return site;
	}
	public String getclockid(){
		return clockid.get();
	}
	public void setclockid(String clockid){
		this.clockid.set(clockid);
	}
	public StringProperty clockidProperty() {
		return clockid;
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
}