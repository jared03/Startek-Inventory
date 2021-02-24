package inventory.headset;

import java.util.Date;

public class Headset{

	private String idproduct;
	private Date purchasedate;
	private String status;
	private String model;
	private String supplier;
	private String assigned_name;
	private String brand;

	public Headset(){
	}
	public String getassigned(){
		return assigned_name;
	}
	public void setassigned(String assigned_name){
		this.assigned_name = assigned_name;
	}
	public String getidproduct(){
		return idproduct;
	}
	public void setidproduct(String idproduct){
		this.idproduct = idproduct;
	}
	public Date getpurchasedate(){
		return purchasedate;
	}
	public void setpurchasedate(Date purchasedate){
		this.purchasedate = purchasedate;
	}
	public String getstatus(){
		return status;
	}
	public void setstatus(String status){
		this.status = status;
	}
	public String getbrand(){
		return brand;
	}
	public void setbrand(String brand){
		this.brand = brand;
	}
	public String getmodel(){
		return model;
	}
	public void setmodel(String model){
		this.model = model;
	}
	public String getsupplier(){
		return supplier;
	}
	public void setsupplier(String supplier){
		this.supplier = supplier;
	}
}