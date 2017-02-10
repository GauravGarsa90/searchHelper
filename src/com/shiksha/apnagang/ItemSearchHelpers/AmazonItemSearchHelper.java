package com.shiksha.apnagang.ItemSearchHelpers;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.directory.InvalidSearchFilterException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.shiksha.apnagang.AmazonSignedRequestsHelper;
import com.shiksha.apnagang.ItemSearchHelper;


public class AmazonItemSearchHelper implements ItemSearchHelper{
	private static final String AWS_ACCESS_KEY_ID = "AKIAI5PKVRSBT7XJ2TNA";

	/*
	 * Your AWS Secret Key corresponding to the above ID, as taken from the AWS
	 * Your Account page.
	 */
	private static final String AWS_SECRET_KEY = "0bBdKffE3OIy4kvgoGMuo1ABdVg6qK5PwGjIZ+vX";

	/*
	 * Use one of the following end-points, according to the region you are
	 * interested in:
	 * 
	 * US: ecs.amazonaws.com CA: ecs.amazonaws.ca UK: ecs.amazonaws.co.uk DE:
	 * ecs.amazonaws.de FR: ecs.amazonaws.fr JP: ecs.amazonaws.jp
	 * 
	 */
	private static final String ENDPOINT = "webservices.amazon.in";

	private static String availability = null;
	private static String brand = null;
	private static String keywords = null;
	private static String searchIndex = null;
	private static Integer minimumPrice = null;
	private static Integer maximumPrice = null;
	private static Integer itemPage = null;
	private static HashMap<String, String> param = new HashMap<String, String>();
	private static String itemChangeString = "Items.Item";
	private static Integer itemNumber = -1;
	private static ArrayList<HashMap<String, String>> items = new ArrayList<HashMap<String,String>>();
	private static ArrayList<String> path = new ArrayList<String>();
	
	public AmazonItemSearchHelper() {
		//
		path.add("Items");
		path.add("Items.Item");
		path.add("Items.Item.ItemAttributes");
		path.add("Items.Item.OfferSummary.LowestNewPrice");
		path.add("Items.Item.OfferSummary");
		
		param.put("Items.Item.ItemAttributes.OperatingSystem", "OS");
		param.put("Items.Item.OfferSummary.LowestNewPrice.FormattedPrice", "Price");
		param.put("Items.Item.OfferSummary.LowestNewPrice.Amount", "Amount");
		param.put("Items.Item.OfferSummary.LowestNewPrice.CurrencyCode", "CurrencyCode");
		param.put("Items.Item.ItemAttributes.Size", "Size");
		param.put("Items.Item.DetailPageURL", "URL");
		param.put("Items.Item.ItemAttributes.Color", "Color");
		param.put("Items.Item.ItemAttributes.Title", "Title");
		param.put("Items.Item.ItemAttributes.Manufacturer", "Manufacturer");
		param.put("Items.Item.ItemAttributes.Binding", "Category0");
		param.put("Items.Item.ItemAttributes.Department", "Category1");
		param.put("Items.Item.ItemAttributes.ProductTypeName", "Category2");
		/*
		 * Items.Item.ItemAttributes.Binding  category
		 * Items.Item.ItemAttributes.ClothingSize
		 * Items.Item.ItemAttributes.Department (men women kids etc)
		 * 
		 * Items.Item.DetailPageURL
		 * Items.Item.ItemAttributes.Binding
		 * Items.Item.ItemAttributes.Brand
		 * Items.Item.ItemAttributes.Color
		 * Items.Item.ItemAttributes.Label
		 * Items.Item.ItemAttributes.ListPrice.Amount					X
		 * Items.Item.ItemAttributes.ListPrice.CurrencyCode				X
		 * Items.Item.ItemAttributes.ListPrice.FormattedPrice			X
		 * Items.Item.ItemAttributes.Manufacturer
		 * Items.Item.ItemAttributes.Model
		 * Items.Item.ItemAttributes.Size
		 * Items.Item.ItemAttributes.Title
		 * Items.Item.OfferSummary.LowestNewPrice.Amount
		 * Items.Item.OfferSummary.LowestNewPrice.CurrencyCode
		 * Items.Item.OfferSummary.LowestNewPrice.FormattedPrice
		 * 
		 * 
		 * */
	}
	
	public void setSearchString(String searchString){
		this.keywords = searchString.replaceAll(" ", "+");
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public String getBrand() {
		return this.brand;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public void setCategory(String searchIndex) {
		this.searchIndex = validateCategory(searchIndex);
	}

	public String validateCategory(String searchIndex) {
		if (searchIndex.equalsIgnoreCase("All")) {
			return "All";
		} else if (searchIndex.equalsIgnoreCase("Beauty")) {
			return "Beauty";
		} else if (searchIndex.equalsIgnoreCase("Grocery")) {
			return "Grocery";
		} else if (searchIndex.equalsIgnoreCase("Industrial")) {
			return "Industrial";
		} else if (searchIndex.equalsIgnoreCase("PetSupplies")) {
			return "PetSupplies";
		} else if (searchIndex.equalsIgnoreCase("OfficeProducts")) {
			return "OfficeProducts";
		} else if (searchIndex.equalsIgnoreCase("Electronics")) {
			return "Electronics";
		} else if (searchIndex.equalsIgnoreCase("Watches")) {
			return "Watches";
		} else if (searchIndex.equalsIgnoreCase("Jewelry")) {
			return "Jewelry";
		} else if (searchIndex.equalsIgnoreCase("Luggage")) {
			return "Luggage";
		} else if (searchIndex.equalsIgnoreCase("Shoes")) {
			return "Shoes";
		} else if (searchIndex.equalsIgnoreCase("Furniture")) {
			return "Furniture";
		} else if (searchIndex.equalsIgnoreCase("KindleStore")) {
			return "KindleStore";
		} else if (searchIndex.equalsIgnoreCase("Automotive")) {
			return "Automotive";
		} else if (searchIndex.equalsIgnoreCase("Pantry")) {
			return "Pantry";
		} else if (searchIndex.equalsIgnoreCase("MusicalInstruments")) {
			return "MusicalInstruments";
		} else if (searchIndex.equalsIgnoreCase("GiftCards")) {
			return "GiftCards";
		} else if (searchIndex.equalsIgnoreCase("Toys")) {
			return "Toys";
		} else if (searchIndex.equalsIgnoreCase("SportingGoods")) {
			return "SportingGoods";
		} else if (searchIndex.equalsIgnoreCase("PCHardware")) {
			return "PCHardware";
		} else if (searchIndex.equalsIgnoreCase("Books")) {
			return "Books";
		} else if (searchIndex.equalsIgnoreCase("LuxuryBeauty")) {
			return "LuxuryBeauty";
		} else if (searchIndex.equalsIgnoreCase("Baby")) {
			return "Baby";
		} else if (searchIndex.equalsIgnoreCase("HomeGarden")) {
			return "HomeGarden";
		} else if (searchIndex.equalsIgnoreCase("VideoGames")) {
			return "VideoGames";
		} else if (searchIndex.equalsIgnoreCase("Apparel")) {
			return "Apparel";
		} else if (searchIndex.equalsIgnoreCase("Marketplace")) {
			return "Marketplace";
		} else if (searchIndex.equalsIgnoreCase("DVD")) {
			return "DVD";
		} else if (searchIndex.equalsIgnoreCase("Appliances")) {
			return "Appliances";
		} else if (searchIndex.equalsIgnoreCase("Music")) {
			return "Music";
		} else if (searchIndex.equalsIgnoreCase("LawnAndGarden")) {
			return "LawnAndGarden";
		} else if (searchIndex.equalsIgnoreCase("HealthPersonalCare")) {
			return "HealthPersonalCare";
		} else if (searchIndex.equalsIgnoreCase("Software")) {
			return "Software";
		}else{
			return "All";
		}
	}

	public void setMinimumPrice(Integer minimumPrice) {
		this.minimumPrice = minimumPrice;
	}

	public void setMaximumPrice(Integer maximumPrice) {
		this.maximumPrice = maximumPrice;
	}

	public void setItemPage(Integer itemPage) {
		this.itemPage = itemPage;
	}
		
	public void readRecursive(Element element, String location) {
	    for(int i = 0, size = element.nodeCount(); i < size; i++) {
	        Node node = element.node(i);
	        if(node instanceof Element) {
	            Element currentNode = (Element) node;
	            String tmpLocation = null;
	            if(location.equalsIgnoreCase("")){
	            	tmpLocation = currentNode.getName();
	            }else{
	            	tmpLocation = location + "." + currentNode.getName();
	            }
            	if(tmpLocation.equalsIgnoreCase(itemChangeString)){
            		itemNumber++;
            	}
            	Boolean locatoinCheck = false;
            	for(String loc:path){
            		if(tmpLocation.contains(loc)){
//            			System.out.println(tmpLocation.split("\\.").length+"<->"+loc.split("\\.").length);
            			if((tmpLocation.split("\\.").length-1==loc.split("\\.").length) || (tmpLocation.split("\\.").length==loc.split("\\.").length)){
            				locatoinCheck = true;
            			}
            		}
            	}
            	if(locatoinCheck){
//            		System.out.println("-");
//            		System.out.println(tmpLocation);
            		if(currentNode.isTextOnly()) {
            			if(param.containsKey(tmpLocation)){
//	            		System.out.println(items.size() + " " + itemNumber);
            				if(items.size()==itemNumber){
//	            			System.out.println("new item found (item number:" + itemNumber +")");
            					HashMap<String, String> tmp = new HashMap<String, String>();
            					tmp.put(param.get(tmpLocation), currentNode.getText());
            					items.add(tmp);
            				}else{
//	            			System.out.println("adding to existing item (item number:" + itemNumber +")");
            					HashMap<String, String> tmp = items.get(itemNumber);
            					tmp.put(param.get(tmpLocation), currentNode.getText());
            					items.set(itemNumber, tmp);
            				}
//	            		System.out.println(param.get(tmpLocation)+"="+currentNode.getText());
            			}
//	                System.out.println(tmpLocation + "=" +currentNode.getText());
            		}
            	}
	            readRecursive(currentNode, tmpLocation);
	        }
	    }
	}

	public void populateData(String requestUrl) {
		try {
			System.out.println(requestUrl);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			SAXReader reader = new SAXReader();
			URL url = new URL(requestUrl);
			Document doc = reader.read(url);
			System.out.println(doc.asXML());
//			System.out.println("-----------------------------------------------------------------");
			readRecursive(doc.getRootElement(),"");
//			System.out.println("-----------------------------------------------------------------");
			System.out.println(items);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public ArrayList<HashMap<String, String>> searchItem() throws Exception {
		AmazonSignedRequestsHelper helper;
		try {
			helper = AmazonSignedRequestsHelper.getInstance(ENDPOINT, AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
			String requestUrl = null;
			String title = null;
			Map<String, String> params = new HashMap<String, String>();
			if (keywords != null) {
				params.put("Keywords", keywords);
			} else {
				throw new Exception();
			}
			if (availability != null) {
				params.put("Availability", availability);
			}
			if (brand != null) {
				params.put("Brand", brand);
			}
			if (searchIndex != null) {
				params.put("SearchIndex", searchIndex);
			} else {
				params.put("SearchIndex", "All");
			}
			if (minimumPrice != null) {
				params.put("MinimumPrice", minimumPrice.toString());
			}
			if (maximumPrice != null) {
				params.put("MaximumPrice", maximumPrice.toString());
			}
			if (itemPage != null) {
				params.put("ItemPage", itemPage.toString());
			}
			params.put("Version", "2015-08-01");
			params.put("Operation", "ItemSearch");
			params.put("AssociateTag", "apnagang-21");
			params.put("ResponseGroup", "Medium");
			requestUrl = helper.sign(params);
			populateData(requestUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return items;
	}

	public void searchItem(Integer noOfResults) {

	}

	public static void main(String[] args) {
		AmazonItemSearchHelper aish = new AmazonItemSearchHelper();
		aish.setCategory("All");
		aish.setSearchString("shirt");
		try {
			aish.searchItem();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("amazon end");
	}

}
