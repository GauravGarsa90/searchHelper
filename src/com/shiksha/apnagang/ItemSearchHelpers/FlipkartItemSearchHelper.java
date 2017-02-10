package com.shiksha.apnagang.ItemSearchHelpers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Parameter;
import java.net.HttpURLConnection;
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

import com.shiksha.apnagang.ItemSearchHelper;


public class FlipkartItemSearchHelper implements ItemSearchHelper{
	private static final String FLPKT_ACCESS_ID = "santoshku42";

	/*
	 * Your AWS Secret Key corresponding to the above ID, as taken from the AWS
	 * Your Account page.
	 */
	private static final String FLPKT_ACCESS_TOKEN = "e5043688b2b449aca1cebcddd57b1a84";

	/*
	 * Use one of the following end-points, according to the region you are
	 * interested in:
	 * 
	 * US: ecs.amazonaws.com CA: ecs.amazonaws.ca UK: ecs.amazonaws.co.uk DE:
	 * ecs.amazonaws.de FR: ecs.amazonaws.fr JP: ecs.amazonaws.jp
	 * 
	 */
	public static String URL = "https://affiliate-api.flipkart.net/affiliate/search/xml?query=";

	private static String availability = null;
	private static String brand = null;
	private static String keywords = null;
	private static String searchIndex = null;
	private static Integer minimumPrice = null;
	private static Integer maximumPrice = null;
	private static Integer itemPage = null;
	private static String compositePrice = "";
	private static HashMap<String, String> param = new HashMap<String, String>();
	private static String itemChangeString = "products.productInfoList";
	private static Integer itemNumber = -1;
	private static ArrayList<HashMap<String, String>> items = new ArrayList<HashMap<String,String>>();

	public FlipkartItemSearchHelper() {

		param.put("products.productInfoList.productBaseInfo.productAttributes.color", "Color");
		param.put("products.productInfoList.productBaseInfo.productAttributes.inStock", "Availability");
		param.put("products.productInfoList.productBaseInfo.productAttributes.maximumRetailPrice.amount", "Amount");
		param.put("products.productInfoList.productBaseInfo.productAttributes.maximumRetailPrice.currency", "CurrencyCode");
		param.put("products.productInfoList.productBaseInfo.productAttributes.productUrl", "URL");
		param.put("products.productInfoList.productBaseInfo.productAttributes.title", "Title");
		param.put("products.productInfoList.productBaseInfo.productAttributes.productBrand", "Manufacturer");
		param.put("products.productInfoList.productBaseInfo.productIdentifier.categoryPaths.categoryPath.item.title", "CategoryInfo");
		/*
		 * products.productInfoList.productBaseInfo.productAttributes.size
		 * products.productInfoList.productBaseInfo.productIdentifier.categoryPaths.categoryPath.item.title     Apparels&gt;Men&gt;Casual Shirts
		 * 
		 * 
		 * 
		 * 
		 * 
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
	
	public Boolean filterResults(){
		if(itemNumber>=0){
			if(availability!= null && items.get(itemNumber).containsKey("Availability")){
				if(items.get(itemNumber).get("Availability").equalsIgnoreCase("true")){
					if(brand!=null && items.get(itemNumber).containsKey("Manufacturer")){
						if(items.get(itemNumber).get("Manufacturer").equalsIgnoreCase(brand)){
							if(items.get(itemNumber).containsKey("Amount")){
								if(minimumPrice!=null && Integer.parseInt(items.get(itemNumber).get("Amount"))>minimumPrice){
									if(maximumPrice!=null && Integer.parseInt(items.get(itemNumber).get("Amount"))<maximumPrice){
										return true;
									}else if(maximumPrice==null){
										return true;
									}
								}else if(minimumPrice==null){
									if(maximumPrice!=null && Integer.parseInt(items.get(itemNumber).get("Amount"))<maximumPrice){
										return true;
									}else if(maximumPrice==null){
										return true;
									}
								}
							}
						}
					}else if(brand==null){
						if(items.get(itemNumber).containsKey("Amount")){
							if(minimumPrice!=null && Integer.parseInt(items.get(itemNumber).get("Amount"))>minimumPrice){
								if(maximumPrice!=null && Integer.parseInt(items.get(itemNumber).get("Amount"))<maximumPrice){
									return true;
								}else if(maximumPrice==null){
									return true;
								}
							}else if(minimumPrice==null){
								if(maximumPrice!=null && Integer.parseInt(items.get(itemNumber).get("Amount"))<maximumPrice){
									return true;
								}else if(maximumPrice==null){
									return true;
								}
							}
						}
					}
				}
			}else if(availability==null){
				if(brand!=null && items.get(itemNumber).containsKey("Manufacturer")){
					if(items.get(itemNumber).get("Manufacturer").equalsIgnoreCase(brand)){
						if(items.get(itemNumber).containsKey("Amount")){
							if(minimumPrice!=null && Integer.parseInt(items.get(itemNumber).get("Amount"))>minimumPrice){
								if(maximumPrice!=null && Integer.parseInt(items.get(itemNumber).get("Amount"))<maximumPrice){
									return true;
								}else if(maximumPrice==null){
									return true;
								}
							}else if(minimumPrice==null){
								if(maximumPrice!=null && Integer.parseInt(items.get(itemNumber).get("Amount"))<maximumPrice){
									return true;
								}else if(maximumPrice==null){
									return true;
								}
							}
						}
					}
				}else if(brand==null){
					if(items.get(itemNumber).containsKey("Amount")){
						if(minimumPrice!=null && Integer.parseInt(items.get(itemNumber).get("Amount"))>minimumPrice){
							if(maximumPrice!=null && Integer.parseInt(items.get(itemNumber).get("Amount"))<maximumPrice){
								return true;
							}else if(maximumPrice==null){
								return true;
							}
						}else if(minimumPrice==null){
							if(maximumPrice!=null && Integer.parseInt(items.get(itemNumber).get("Amount"))<maximumPrice){
								return true;
							}else if(maximumPrice==null){
								return true;
							}
						}
					}
				}
			}
			return false;
		}else{
			return true;
		}
	}
		
	public void readRecursive(Element element, String location) {
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
	            if(tmpLocation.equalsIgnoreCase(itemChangeString)){
	            	if(itemNumber>=0){
	            		/*if(filterResults()){
	            			System.out.println("item filtered out");
	            			items.remove(itemNumber);
	            		}else{*/
	            			itemNumber++;
	            		/*}*/
	            	}else{
	            		itemNumber++;
	            	}
            	}
	            if(currentNode.isTextOnly()) {

	            	if(param.containsKey(tmpLocation)){
	            		if(items.size()==itemNumber){
	            			System.out.println("new item found (item number:" + itemNumber +")");
	            			if(itemNumber>0){
	            				HashMap<String, String> tmp1 = items.get(itemNumber-1);
	            				tmp1.put("Price", tmp1.get("CurrencyCode") + " " + tmp1.get("Amount"));
	            				items.set(itemNumber-1, tmp1);
	            			}
	            			HashMap<String, String> tmp = new HashMap<String, String>();
	            			if(param.get(tmpLocation).equalsIgnoreCase("Title")){
	            				String pattern = "(.*)(\\()(.*)([\\d]{2}[\\s][G][B])(.*)";
	            				Pattern p = Pattern.compile(pattern);
	            				Matcher m = p.matcher(currentNode.getText());
	            				if(m.find()){
	            					tmp.put("Title", m.group(1));
	            					tmp.put("Size", m.group(4));
	            				}else{
	            					tmp.put("Title", currentNode.getText());
	            				}
	            			}else if(param.get(tmpLocation).equalsIgnoreCase("CategoryInfo")){
	            				String [] cat = currentNode.getText().split(">");
	            				for(int j =0; j<cat.length; j++){
	            					tmp.put("Category"+j, cat[j]);
	            				}
	            			}else{
	            				tmp.put(param.get(tmpLocation), currentNode.getText());
	            			}
	            			items.add(tmp);
	            		}else{
//	            			System.out.println("adding to existing item (item number:" + itemNumber +")");
	            			HashMap<String, String> tmp = items.get(itemNumber);
	            			if(param.get(tmpLocation).equalsIgnoreCase("Title")){
	            				String pattern = "(.*)(\\()(.*)([\\d]{2}[\\s][G][B])(.*)";
	            				Pattern p = Pattern.compile(pattern);
	            				Matcher m = p.matcher(currentNode.getText());
	            				if(m.find()){
	            					tmp.put("Title", m.group(1));
	            					tmp.put("Size", m.group(4));
	            				}else{
	            					tmp.put("Title", currentNode.getText());
	            				}
	            			}else{
	            				tmp.put(param.get(tmpLocation), currentNode.getText());
	            			}
	            			items.set(itemNumber, tmp);
	            		}
	            	}
//	                System.out.println(tmpLocation + "=" +currentNode.getText());
	            }
	            readRecursive(currentNode, tmpLocation);
	        }
	    }
	}

	public ArrayList<HashMap<String, String>> searchItem() throws Exception {
		try {
			Boolean first = true;
			for(int i = 0; i<keywords.split(" ").length; i++){
				if(first){
					URL = URL + keywords.split(" ")[i].trim();
					first = false;
				}else{
					URL = URL + "+" + keywords.split(" ")[i].trim();
				}
			}
			URL = URL + "&resultCount=10";
			URL url = new URL(URL);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setRequestMethod("GET");
			con.setRequestProperty("Fk-Affiliate-Id", FLPKT_ACCESS_ID);
			con.setRequestProperty("Fk-Affiliate-Token", FLPKT_ACCESS_TOKEN);

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
//			System.out.println("Response Code : " + responseCode);
			SAXReader reader = new SAXReader();
			Document doc = reader.read(con.getInputStream());
			System.out.println(doc.asXML());
//			System.out.println(param);
			readRecursive(doc.getRootElement(),"");
			System.out.println(items);
			con.disconnect();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}

	@Override
	public void searchItem(Integer noOfResults) {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FlipkartItemSearchHelper fish = new FlipkartItemSearchHelper();
		fish.setSearchString("batman shirt");
//		ais.setCategory("electronics");
		try {
			fish.searchItem();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("flipkart end");
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
