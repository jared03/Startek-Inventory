package inventory.employee;

import java.util.Date;

public class Employee{
	private String fname;
	private String lname;
	private String email;
	private String idemployee;
	private String status;
	private Date hiredate;
	private String supervisor;
	private String jobtitle;
	private String description;
	private String country;
	private String site;
	private String clockid;

	public Employee(){
	}
	public String getfname(){
		return fname;
	}
	public void setfname(String fname){
		this.fname = fname;
	}
	public String getlname(){
		return lname;
	}
	public void setlname(String lname){
		this.lname = lname;
	}
	public String getemail(){
		return email;
	}
	public void setemail(String email){
		this.email = email;
	}
	public String getidemployee(){
		return idemployee;
	}
	public void setidemployee(String idemployee){
		this.idemployee = idemployee;
	}
	public String getstatus(){
		return status;
	}
	public void setstatus(String status){
		this.status = status;
	}
	public Date gethiredate(){
		return hiredate;
	}
	public void sethiredate(Date hiredate){
		this.hiredate = hiredate;
	}
	public String getsupervisor(){
		return supervisor;
	}
	public void setsupervisor(String supervisor){
		this.supervisor = supervisor;
	}
	public String getjobtitle(){
		return jobtitle;
	}
	public void setjobtitle(String jobtitle){
		this.jobtitle = jobtitle;
	}
	public String getdescription(){
		return description;
	}
	public void setdescription(String description){
		this.description = description;
	}
	public String getcountry(){
		return country;
	}
	public void setcountry(String country){
		this.country = country;
	}
	public String getsite(){
		return site;
	}
	public void setsite(String site){
		this.site = site;
	}
	public String getclockid(){
		return clockid;
	}
	public void setclockid(String clockid){
		this.clockid = clockid;
	}
}