package com.shiksha.apnagang;

import java.util.ArrayList;
import java.util.HashMap;

import org.dom4j.Document;
import org.dom4j.Element;

public interface ItemSearchHelper {
	
	public void setSearchString(String searchString);
	
	public void setBrand(String brand);
		
	public String getBrand();

	public void setAvailability(String availability);

	public void setCategory(String searchIndex);

	public String validateCategory(String searchIndex);

	public void setMinimumPrice(Integer minimumPrice);

	public void setMaximumPrice(Integer maximumPrice);

	public void setItemPage(Integer itemPage);
		
	public void readRecursive(Element element, String location);

	public ArrayList<HashMap<String, String>> searchItem() throws Exception ;

	public void searchItem(Integer noOfResults);

}
