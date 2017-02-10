package com.shiksha.apnagang;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Parameter;
import java.net.HttpURLConnection;
import java.net.URL;
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

import com.shiksha.apnagang.ItemSearchHelper;


public class SnapdealItemSearchHelper implements ItemSearchHelper{
	private static final String SNPDL_ACCESS_ID = "101701";

	/*
	 * Your AWS Secret Key corresponding to the above ID, as taken from the AWS
	 * Your Account page.
	 */
	private static final String SNPDL_ACCESS_TOKEN = "6b87d26bba0e06655464fb5ca0f91d";

	/*
	 * Use one of the following end-points, according to the region you are
	 * interested in:
	 * 
	 * US: ecs.amazonaws.com CA: ecs.amazonaws.ca UK: ecs.amazonaws.co.uk DE:
	 * ecs.amazonaws.de FR: ecs.amazonaws.fr JP: ecs.amazonaws.jp
	 * 
	 */
	public static String URL = "http://staging-apigateway.snapdeal.com/seller-api/categories/search";
//	search?keyword=

	private static String availability = null;
	private static String brand = null;
	private static String keywords = null;
	private static String searchIndex = null;
	private static Integer minimumPrice = null;
	private static Integer maximumPrice = null;
	private static Integer itemPage = null;
	private static String compositePrice = "";
	private static HashMap<String, String> param = new HashMap<String, String>();
	
	public SnapdealItemSearchHelper() {

		param.put("products.productInfoList.productBaseInfo.productAttributes.color", "Color");
		param.put("products.productInfoList.productBaseInfo.productAttributes.inStock", "Availability");
		param.put("products.productInfoList.productBaseInfo.productAttributes.maximumRetailPrice.amount", "Price0");
		param.put("products.productInfoList.productBaseInfo.productAttributes.maximumRetailPrice.currency", "Price1");
		param.put("products.productInfoList.productBaseInfo.productAttributes.productUrl", "URL");
		param.put("products.productInfoList.productBaseInfo.productAttributes.title", "Title");
		param.put("products.productInfoList.productBaseInfo.productAttributes.productBrand", "Manufacturer");
	}
	
	public void setSearchString(String searchString){
		this.keywords = searchString;
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
	/*
	 * DetailPageURL ItemAttributes
	 */
	
	public void getItemData(Document doc){
/*		Element root = doc.getRootElement();
		root.node
		Items.Item.DetailPageURL
		Items.Item.ItemAttributes.Manufacturer
		Items.Item.ItemAttributes.OperatingSystem
		Items.Item.ItemAttributes.Size
		Items.Item.OfferSummary.LowestNewPrice.FormattedPrice
		*/
		

	}
	
	public void printRecursive(Element element, String location) {
	    for(int i = 0, size = element.nodeCount(); i < size; i++) {
	        Node node = element.node(i);
	        if(node instanceof Element) {
	            Element currentNode = (Element) node;
	            String tmpLocation = "";
	            if(location.equalsIgnoreCase("")){
	            	tmpLocation = currentNode.getName();
	            }else{
	            	tmpLocation = location + "." + currentNode.getName();
	            }
//            	System.out.println(location);
	            if(currentNode.isTextOnly()) {

	            	if(param.containsKey(tmpLocation)){
	            		if(param.get(tmpLocation).equalsIgnoreCase("Price0")){
	            			compositePrice = currentNode.getText()+" ";
	            		}else if(param.get(tmpLocation).equalsIgnoreCase("Price1")){
	            			compositePrice = compositePrice + currentNode.getText();
	            			System.out.println("Price="+compositePrice);
	            		}else{
	            			if(param.get(tmpLocation).equalsIgnoreCase("Title")){
	            				String pattern = "(.*)(\\()(.*)([\\d]{2}[\\s][G][B])(.*)";
	            				Pattern p = Pattern.compile(pattern);
	            				Matcher m = p.matcher(currentNode.getText());
	            				if(m.find()){
/*	            					System.out.println(m.group(0));
	            					System.out.println(m.group(1));
	            					System.out.println(m.group(2));
	            					System.out.println(m.group(3));
	            					System.out.println(m.group(4));
	            					System.out.println(m.group(5));*/
			            			System.out.println("Title="+m.group(1).trim());
			            			System.out.println("Size="+m.group(4).trim());
	            				}
	            					
	            			}else{
		            			System.out.println(param.get(tmpLocation)+"="+currentNode.getText());
	            			}
	            		}
	            	}
//	                System.out.println(tmpLocation + "=" +currentNode.getText());
	            }
	            printRecursive(currentNode, tmpLocation);
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
//			Document doc = db.parse(requestUrl).;
			System.out.println("-----------------------------------------------------------------");
			printRecursive(doc.getRootElement(),"");
			System.out.println("-----------------------------------------------------------------");
			/*System.out.println(doc);
			System.out.println(doc.getElementsByTagName("Title"));
			NodeList test = doc.getElementsByTagName("Title");
			System.out.println(doc.getNodeName());
			System.out.println(doc.getNodeType());
			// System.out.println(doc.);
			NamedNodeMap tmp0 = doc.getFirstChild().getAttributes();
			for (int i = 0; i < tmp0.getLength(); i++) {
				System.out.println(doc.getFirstChild().getUserData(tmp0.item(i).getTextContent()));
			}
			for (int i = 0; i < test.getLength(); i++) {
				System.out.println(getAttribute(doc, "DetailPageURL", i));
				System.out.println(getAttribute(doc, "ItemAttributes", i));
				System.out.println(getAttribute(doc, "Manufacturer", i));
				System.out.println(getAttribute(doc, "FormattedPrice", i));
				System.out.println(getAttribute(doc, "Title", i));
				System.out.println(getAttribute(doc, "Model", i));
				System.out.println(getAttribute(doc, "Type", i));
			}*/
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

/*	public String getAttribute(Document doc, String attr, Integer itemNo) {
		return doc.getElementsByTagName(attr).item(itemNo).getTextContent();
		// doc.getElementsByTagName(attr).item(itemNo)
		
		 * for(int i = 0; i<test.getLength(); i++){
		 * System.out.println(test.item(i).getTextContent()); } // Node
		 * titleNode = doc.getElementsByTagName("Title").item(0); try{ // title
		 * = titleNode.getTextContent(); } catch(Exception e){
		 * e.printStackTrace(); }
		 
	}*/

	public void searchItem() throws Exception {
//		AmazonSignedRequestsHelper helper;
		try {
			Boolean first = true;
			/*for(int i = 0; i<keywords.split(" ").length; i++){
				if(first){
					URL = URL + keywords.split(" ")[i].trim();
					first = false;
				}else{
					URL = URL + "+" + keywords.split(" ")[i].trim();
				}
			}
			URL = URL + "&resultCount=10";*/
			URL url = new URL(URL);
//			url.
			
			
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			//add request header
			con.setRequestProperty("searchKey", keywords);
			con.setRequestProperty("Snapdeal-Affiliate-Id", SNPDL_ACCESS_ID);
			con.setRequestProperty("Snapdeal-Token-Id", SNPDL_ACCESS_TOKEN);
			con.setRequestProperty("Accept", "application/xml");
			

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
			SAXReader reader = new SAXReader();
			Document doc = reader.read(con.getInputStream());
			System.out.println(doc.asXML());
			System.out.println(param);
			printRecursive(doc.getRootElement(),"");

			
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		String requestUrl = URL;
		/*String title = null;
		Map<String, String> params = new HashMap<String, String>();
		if (keywords != null) {
			params.put("Keywords", keywords);
		} else {
			throw new InvalidSearchFilterException();
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
		params.put("ResponseGroup", "Large");
		requestUrl = helper.sign(params);*/
//		populateData(requestUrl);
//		ItemLookupSample.test(requestUrl);
	}

	public void searchItem(Integer noOfResults) {

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SnapdealItemSearchHelper sish = new SnapdealItemSearchHelper();
		sish.setSearchString("sony mobiles");
//		ais.setCategory("electronics");
		try {
			sish.searchItem();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * AmazonSignedRequestsHelper helper; try { helper =
		 * AmazonSignedRequestsHelper.getInstance(ENDPOINT, AWS_ACCESS_KEY_ID,
		 * AWS_SECRET_KEY); } catch (Exception e) { e.printStackTrace(); return;
		 * }
		 * 
		 * String requestUrl = null; String title = null; Map<String, String>
		 * params = new HashMap<String, String>(); params.put("Availability",
		 * "Available"); params.put("Version", "2015-08-01");
		 * params.put("Operation", "ItemSearch"); params.put("Keywords",
		 * "OnePlus 3"); params.put("AssociateTag","apnagang-21"); //
		 * params.put("IdType", "EAN"); params.put("ResponseGroup", "Small");
		 * params.put("SearchIndex", "All");
		 * 
		 * try { DocumentBuilderFactory dbf =
		 * DocumentBuilderFactory.newInstance(); DocumentBuilder db =
		 * dbf.newDocumentBuilder(); Document doc = db.parse(requestUrl);
		 * System.out.println(doc);
		 * System.out.println(doc.getElementsByTagName("Title")); Node titleNode
		 * = doc.getElementsByTagName("Title").item(0); try{ title =
		 * titleNode.getTextContent(); } catch(Exception e){
		 * e.printStackTrace(); }
		 * 
		 * } catch (Exception e) { throw new RuntimeException(e); }
		 */

	}

}
