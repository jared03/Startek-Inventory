package inventory.employee;

import java.util.Date;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Assignation{
	private final StringProperty  idproduct;
	private final ObjectProperty<Date> assigndate;
	private final StringProperty  brand;
	private final StringProperty  model;
	private final StringProperty  price;

	public Assignation(String idproduct,Date assigndate,String brand,String model,String price){
		this.idproduct = new SimpleStringProperty(idproduct);
		this.assigndate = new SimpleObjectProperty<Date>(assigndate);
		this.brand = new SimpleStringProperty(brand);
		this.model = new SimpleStringProperty(model);
		this.price = new SimpleStringProperty(price);
	}
	public String getidproduct(){
		return idproduct.get();
	}
	public void setidproduct(String idproduct){
		this.idproduct.set(idproduct);;
	}
	public StringProperty idproductProperty() {
		return idproduct;
	}
	public Date getassigndate(){
		return assigndate.get();
	}
	public void setassigndate(Date assigndate){
		this.assigndate.set(assigndate);
	}
	public ObjectProperty<Date> assigndateProperty(){
		return assigndate;
	}
	public String getbrand(){
		return brand.get();
	}
	public void setbrand(String brand){
		this.brand.set(brand);
	}
	public StringProperty brandProperty() {
		return brand;
	}
	public String getmodel(){
		return model.get();
	}
	public void setmodel(String model){
		this.model.set(model);
	}
	public StringProperty modelProperty() {
		return model;
	}
	public String getprice(){
		return price.get();
	}
	public void setprice(String price){
		this.price.set(price);
	}
	public StringProperty priceProperty() {
		return price;
	}
}