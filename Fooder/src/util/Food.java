package util;

public class Food {
	String name;
	String rating;
	String address;
	String url;
	
	public Food(String name, String rating, String address, String url){
		this.name = name;
		this.rating = rating;
		this.address = address;
		this.url = url;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getRating(){
		return rating;
	}
	
	public void setRating(String rating){
		this.rating = rating;
	}
	
	public String getAddress(){
		return address;
	}
	
	public void setAddress(String address){
		this.address = address;
	}
	
	public String getUrl(){
		return url;
	}
	
	public void setUrl(String url){
		this.url = url;
	}
}
