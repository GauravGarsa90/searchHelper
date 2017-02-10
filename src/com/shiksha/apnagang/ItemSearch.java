package com.shiksha.apnagang;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import org.reflections.Reflections;

import com.shiksha.apnagang.ItemSearchHelpers.*;

@XmlRootElement(name = "itemSearch")
public class ItemSearch implements Serializable{
	public static String searchString;
	public static String category;
	public static String minPrice;
	public static String maxPrice;
	public static String availability;
	public static HashMap<String, ArrayList<HashMap<String, String>>> data = new HashMap<String, ArrayList<HashMap<String, String>>>();
	public static ArrayList<HashMap<String, String>> combinedItems = new ArrayList<HashMap<String,String>>();
	public static HashMap<String, String> inputs = new HashMap<String, String>();
	public static Integer similarityThreshold = 70;
	public static HashMap<String, ArrayList<Integer>> completedItems = new HashMap<String, ArrayList<Integer>>();
	
	public ItemSearch(String searchString, String category, String minPrice, String maxPrice, String availability){
		inputs.put("setSearchString", searchString);
		inputs.put("setCategory", category);
		if(Integer.parseInt(minPrice)>0){
			inputs.put("setMinPrice", minPrice);
		}
		if(Integer.parseInt(maxPrice)>0 && Integer.parseInt(minPrice)<Integer.parseInt(maxPrice)){
			inputs.put("setMaxPrice", maxPrice);
		}
		inputs.put("setAvailability", availability);
		search();
//		this.searchString = searchString;
//		this.category = category;
//		this.minPrice = minPrice;
//		this.maxPrice = maxPrice;
//		this.availability = availability;
	}
	
	public static void search(){
		String packageName = "com.shiksha.apnagang.ItemSearchHelpers";
		try{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	        assert classLoader != null;
			String path = packageName.replaceAll("\\.", "/");
			File directory = new File(classLoader.getResource(path).getFile());
//			directory = classLoader.getResource(path).getFile();
			System.out.println(path);
	        File[] files = directory.listFiles();
	        for (File file : files) {
	        	if (file.getName().endsWith(".class")) {
	        		String className = Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)).getSimpleName();
	        		Class<?> tmp = Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6));
	        		System.out.println(className);
	        		Object obj = tmp.newInstance();
	        		Method[] methods = tmp.getMethods();
	        		for(Method method:methods){
//	        			System.out.println(method.getName());
	        			if(inputs.containsKey(method.getName())){
	        				method.setAccessible(true);
	        				tmp.getMethod(method.getName(), method.getParameterTypes()).setAccessible(true);
	        				tmp.getMethod(method.getName(), method.getParameterTypes()).invoke(obj, tmp.getMethod(method.getName(), method.getParameterTypes()).getParameterTypes()[0].cast(inputs.get(method.getName())));
	        			}
	        			/*if(method.getName().equalsIgnoreCase("setBrand")){
	        				method.setAccessible(true);
	        				tmp.getMethod(method.getName(), method.getParameterTypes()).setAccessible(true);
	        				tmp.getMethod(method.getName(), method.getParameterTypes()).invoke(obj, tmp.getMethod(method.getName(), method.getParameterTypes()).getParameterTypes()[0].cast("test"));
	        				method = tmp.getDeclaredMethod("getBrand", null);
	        				method.setAccessible(true);
	        				System.out.println(method.invoke(obj, null));
	        			}*/
	        			
	        		}
	        		Method method = tmp.getMethod("searchItem", null);
	        		tmp.getMethod(method.getName(), method.getParameterTypes()).setAccessible(true);
//    				tmp.getMethod(method.getName(), method.getParameterTypes()).invoke(obj, null);
    				System.out.println(file.getName().substring(0, file.getName().length() - 6).split("Item")[0]);
    				data.put(file.getName().substring(0, file.getName().length() - 6).split("Item")[0], (ArrayList<HashMap<String, String>>) tmp.getMethod(method.getName(), method.getParameterTypes()).invoke(obj, null));
//    				if(file.getName().substring(0, file.getName().length() - 6).split("Item"))
	        	}
	        }
			mergeResults();
			System.out.println(combinedItems);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static Boolean similarityIndex(HashMap<String, String> item1, HashMap<String, String> item2){
		Boolean sizeCheck = false;
		Boolean categoryCheck = false;
		Boolean departmentCheck = false;
//		Boolean sizeCheck = false;
//		Boolean sizeCheck = false;
		if(item1.containsKey("Size")){
//			if()
			System.out.println("size p1:"+item1.get("Size"));
			if(item2.containsKey("Size")){
//				if()
				System.out.println("size p2:"+item2.get("Size"));
				
			}else{
				sizeCheck=false;
			}
		}else{
			sizeCheck=true;
		}
		
		if(item1.containsKey("Category0") || item1.containsKey("CategoryInfo")){
//			if()
			if(item1.containsKey("Ctegory0")){
				System.out.println(item1.get("Category0 p1"));
			}else{
				System.out.println(item1.get("CategoryInfo p1"));
			}
			if(item2.containsKey("Category0") || item2.containsKey("CategoryInfo")){
//				if()
				if(item2.containsKey("Ctegory0")){
					System.out.println(item2.get("Category0 p2"));
				}else{
					System.out.println(item2.get("CategoryInfo p2"));
				}
				if(item1.containsKey("Category1") || (item1.containsKey("CategoryInfo") && item1.get("CategoryInfo").contains(">"))){
//				if()
					if(item2.containsKey("Category1") || (item2.containsKey("CategoryInfo") && item2.get("CategoryInfo").contains(">"))){
//					if()
						
					}else{
						departmentCheck=false;
					}
				}else{
					departmentCheck=true;
				}
			}else{
				categoryCheck=false;
			}
			
		}else{
			categoryCheck=true;
		}
		//title match
		String [] titleArrayP1 = item1.get("Title").split("\\W");
		String [] titleArrayP2 = item2.get("Title").split("\\W");
		Float total,tpm1, tpm2, tpm3;
		total = (float) 0;
		tpm1 = (float) 0;
		tpm2 = (float) 0;
		tpm3 = (float) 0;
		for(String tp1:titleArrayP1){
			for(String tp2:titleArrayP2){
				total++;
				if(tp1.equalsIgnoreCase(tp2)){
					tpm1++;
					System.out.println("title match type 1("+tpm1+"), values:["+tp1+"] and ["+tp2+"]");
				}else if(tp1.toLowerCase().contains(tp2.toLowerCase())){
					tpm2++;
					System.out.println("title match type 2("+tpm2+"), values:["+tp1+"] and ["+tp2+"]");
				}else if(tp2.toLowerCase().contains(tp1.toLowerCase())){
					tpm3++;
					System.out.println("title match type 3("+tpm3+"), values:["+tp1+"] and ["+tp2+"]");
				}
			}
		}
		System.out.println(tpm1+","+tpm2+","+tpm3+";"+total);
		System.out.println("result:\n\t type1:"+Float.valueOf((tpm1/total)*100)+"%\n\t type2:"+Float.valueOf((tpm2/total)*100)+"%\n\t type3:"+Float.valueOf((tpm3/total)*100)+"%\n\t combined:"+Float.valueOf(((tpm1+tpm2+tpm3)/total)*100));
		System.out.println("size bool:"+sizeCheck+"\ncategory bool:"+categoryCheck+"\ndepartment bool:"+departmentCheck);
		return false;
	}
	
	public static void mergeResults(){
		Set<String> sites = data.keySet();
		for(String baseSite:sites){
			for(int i=0; i<data.get(baseSite).size(); i++){
				for(String comparisionSite:sites){
					if(!comparisionSite.equalsIgnoreCase(baseSite)){
//						System.out.println("================================"+baseSite+"<=>"+comparisionSite);
						for(int j=0; j<data.get(comparisionSite).size(); j++){
							System.out.println("=>"+i+" and "+j+"\n++++++++++++++++++\t"+data.get(baseSite).get(i)+"\n++++++++++++++++++\t"+data.get(comparisionSite).get(j));
							if(data.get(baseSite).get(i).containsKey("combineItem")){
								String [] sameProd = data.get(baseSite).get(i).get("combineItem").split(":");
								for(String prod:sameProd){
									if(!(prod.split(";")[0].equalsIgnoreCase(comparisionSite) && prod.split(";")[1].equalsIgnoreCase(String.valueOf(j)))){
										System.out.println("p1:\n\t"+baseSite+"\n\t"+i);
										System.out.println("p2:\n\t"+comparisionSite+"\n\t"+j);
										if(similarityIndex(data.get(baseSite).get(i), data.get(comparisionSite).get(j))){
											ArrayList<HashMap<String, String>> arr = data.get(baseSite);
											HashMap<String, String> item = arr.get(i);
											if(item.containsKey("combineItem")){
												String tmp = item.get("combineItem");
												tmp = tmp + ":" + comparisionSite + ";" + j;
												item.put("combineItem", tmp);
											}else{
												item.put("combineItem", comparisionSite + ";" + j);
											}
											arr = data.get(comparisionSite);
											item = arr.get(j);
											if(item.containsKey("combineItem")){
												String tmp = item.get("combineItem");
												tmp = tmp + ":" + baseSite + ";" + i;
												item.put("combineItem", tmp);
											}else{
												item.put("combineItem", baseSite + ";" + i);
											}
										}
									}
								}
							}else{
								System.out.println("p1:\n\t"+baseSite+"\n\t"+i);
								System.out.println("p2:\n\t"+comparisionSite+"\n\t"+j);
								if(similarityIndex(data.get(baseSite).get(i), data.get(comparisionSite).get(j))){
									ArrayList<HashMap<String, String>> arr = data.get(baseSite);
									HashMap<String, String> item = arr.get(i);
									if(item.containsKey("combineItem")){
										String tmp = item.get("combineItem");
										tmp = tmp + ":" + comparisionSite + ";" + j;
										item.put("combineItem", tmp);
									}else{
										item.put("combineItem", comparisionSite + ";" + j);
									}
									arr = data.get(comparisionSite);
									item = arr.get(j);
									if(item.containsKey("combineItem")){
										String tmp = item.get("combineItem");
										tmp = tmp + ":" + baseSite + ";" + i;
										item.put("combineItem", tmp);
									}else{
										item.put("combineItem", baseSite + ";" + i);
									}
								}
							}
						}
					}
				}
			}
		}
		for(String site:sites){
			for(int i=0; i<data.get(site).size(); i++){
				if(!(completedItems.containsKey(site) && completedItems.get(site).contains(i))){
					combine(data.get(site).get(i),site,i, new HashMap<String, String>());
				}
			}
		}
	}
	
	private static void combine(HashMap<String, String> item, String site, Integer itemNo, HashMap<String, String> combinedItem){
		System.out.println(site+"->"+itemNo);
		Set<String> props = item.keySet();
		for(String prop:props){
			System.out.println(prop);
			if(!prop.equalsIgnoreCase("combineItem")){
				if(!combinedItem.containsKey(prop) && (!prop.equalsIgnoreCase("Price") || !prop.equalsIgnoreCase("Amount") || !prop.equalsIgnoreCase("CurrencyCode"))){
					combinedItem.put(prop, item.get(prop));
				}else if(prop.equalsIgnoreCase("Price") || prop.equalsIgnoreCase("Amount") || prop.equalsIgnoreCase("CurrencyCode")){
					combinedItem.put(site+"."+prop, item.get(prop));
				}
			}
		}
		if(completedItems.containsKey(site)){
			ArrayList<Integer> tmp = completedItems.get(site); 
			tmp.add(itemNo);
			completedItems.put(site, tmp);
		}else{
			ArrayList<Integer> tmp = new ArrayList<Integer>();
			tmp.add(itemNo);
			completedItems.put(site, tmp);
		}
		if(item.containsKey("combineItem")){
			if(!(completedItems.containsKey(item.get("combineItem").split(":")[0].trim()) && completedItems.get(item.get("combineItem").split(":")[0].trim()).contains(Integer.parseInt(item.get("combineItem").split(":")[1].trim())))){
				combine(data.get(item.get("combineItem").split(":")[0].trim()).get(Integer.parseInt(item.get("combineItem").split(":")[1].trim())),item.get("combineItem").split(":")[0].trim(),Integer.parseInt(item.get("combineItem").split(":")[1].trim()),combinedItem);
				
			}
		}
	}
	
    private static Class[] getClasses(String packageName)throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = (URL) resources.nextElement();
            System.out.println(resource.getFile());
            dirs.add(new File(resource.getFile()));
        }
        ArrayList classes = new ArrayList();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return (Class[]) classes.toArray(new Class[classes.size()]);
    }
    
    private static List findClasses(File directory, String packageName) throws ClassNotFoundException {
        List classes = new ArrayList();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
	
    public static void main(String[] args) {
		// TODO Auto-generated method stub
	/*	try {
			Class[] tmp = getClasses("com.shiksha.apnagang.ItemSearchHelpers");
			for(int i = 0; i<tmp.length; i++){
				System.out.println(tmp[i].getCanonicalName());
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		inputs.put("setSearchString", "sony vaio");
		inputs.put("setCategory", "All");
		inputs.put("setAvailability", "Available");
		search();
//		search("com.shiksha.apnagang.ItemSearchHelpers");
	}

}
