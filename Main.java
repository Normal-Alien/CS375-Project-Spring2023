package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.json.simple.*;
import java.util.Scanner;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import org.json.simple.parser.JSONParser;



public class Main extends Application {
	
	public static List<Map<String, Object>> ITEMS;
	public static List<Map<String, Object>> ADDONS;
	public String ip;
	public String port;
	
	public static int order_id;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			order_id = 3;
			Scanner in = new Scanner(System.in);
			ip = in.nextLine();
			port = in.nextLine();
			in.close();
			ITEMS = getDatabase();
			//ITEMS = getItems();
			//ADDONS = getAddons();
			Parent root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
			Scene scene = new Scene(root);
			//use fxml as resource for root
			primaryStage.setTitle("GUI Thingy");
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static HashMap<String, Object> getItem(String buttonId) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		HashMap<String, Double> addons = new HashMap<String, Double>();
		data.put("Name", "Burritos");
		data.put("Store", "Tres Habaneros");
		data.put("Picture", "example");
		data.put("Cost", 6.75);
		data.put("Taxable", true);
		addons.put("Pickles", 0.30);
		addons.put("Xtra Cheese", 0.15);
		addons.put("Beans", 0.43 	);
		data.put("Addons", addons);
		data.put("Active", true);
		return data;
	}
	
	@SuppressWarnings({ "unchecked" })
	private List<Map<String, Object>> getItems() throws Exception{
		List<Map<String, Object>> db = new ArrayList<Map<String, Object>>();
		ClientCook client = new ClientCook();
		String s = client.sendRequest("http://" + ip + ":" + port + "/database/methods/print_table/Item", "", "GET");
		JSONParser parser = new JSONParser();
		Object jsonObj = parser.parse(s);
		JSONArray jsonArray = (JSONArray) jsonObj;
		Iterator<JSONArray> it = jsonArray.iterator();
		while (it.hasNext()) {
			JSONArray innerArray = it.next();
			Long first = (Long) innerArray.get(0);
			int id = first.intValue();
			String name = (String) innerArray.get(1);
			Long third = (Long) innerArray.get(2);
			int store = third.intValue();
			Long fourth = (Long) innerArray.get(3);
			int pic = fourth.intValue();
			Double Cost = (Double) innerArray.get(4);
			Long sixth = (Long) innerArray.get(5);
			boolean avail = sixth != 0 ? true : false;
			String seventh = (String) innerArray.get(6);
			Long eighth = (Long) innerArray.get(7);
			boolean active = eighth != 0 ? true : false;
			System.out.println(first);
			System.out.println(seventh);
			// parse "seventh" as a list
			Object seventhList = parser.parse(seventh);
			JSONArray seventhArray = (JSONArray) seventhList;
			Iterator<Long> it2 = seventhArray.iterator();
			List<Integer> l = new ArrayList<Integer>();
			while (it2.hasNext()) {
				Long val = it2.next();
				l.add(val.intValue());
			}
			HashMap<String, Object> hash = makeHash(id, name, store, pic, Cost, avail, l, active);
			db.add(hash);
			}
		return db;
	}
	
	@SuppressWarnings({ "unchecked" })
	private List<Map<String, Object>> getAddons() throws Exception{
		List<Map<String, Object>> add = new ArrayList<Map<String, Object>>();
		ClientCook client = new ClientCook();
		String s = client.sendRequest("http://" + ip + ":" + port + "/database/methods/print_table/Item", "", "GET");
		JSONParser parser = new JSONParser();
		Object jsonObj = parser.parse(s);
		JSONArray jsonArray = (JSONArray) jsonObj;
		Iterator<JSONArray> it = jsonArray.iterator();
		while (it.hasNext()) {
			JSONArray innerArray = it.next();
			Long first = (Long) innerArray.get(0);
			int id = first.intValue();
			String name = (String) innerArray.get(1);
			Long third = (Long) innerArray.get(2);
			int item_id = third.intValue();
			Double Cost = (Double) innerArray.get(3);
			HashMap<String, Object> hash = makeAdd(id, name, item_id, Cost);
			add.add(hash);
			}
		return add;
	}
	
	@SuppressWarnings("unused")
	private List<Map<String, Object>> getDatabase(){
		
		List<Integer> a = new ArrayList<Integer>(); a.add(1); a.add(2);
		List<Integer> b = new ArrayList<Integer>(); b.add(0); b.add(1);
		List<Integer> c = new ArrayList<Integer>(); c.add(2); c.add(3);
		List<Integer> d = new ArrayList<Integer>(); d.add(0); d.add(3);
		List<Map<String, Object>> addon = new ArrayList<Map<String, Object>>();
		addon.add(makeAdd(0, "add", 3, 3.0));
		addon.add(makeAdd(1, "sub", 2, 5.0));
		addon.add(makeAdd(2, "idk", 1, 1.5));
		addon.add(makeAdd(3, "thing", 0, 2.73));
		ADDONS = addon;

		List<Map<String, Object>> db = new ArrayList<Map<String, Object>>();
		db.add(makeHash(0, "examination", 0, 01010, 12.0, true, c, true));
		db.add(makeHash(1, "example", 1, 10101, 5.0, false, a, true));
		db.add(makeHash(2, "test", 2, 111, 8.0, true, b, true));
		db.add(makeHash(3, "check", 3, 0001, 10.65, true, d, true));
		
		return db;
	}
	
	@SuppressWarnings("rawtypes")
	private HashMap<String, Object> makeHash(int id, String Name, int Store, int Picture, double Cost, Boolean Taxable, List Addons, Boolean Availible) {
		HashMap<String, Object> hash = new HashMap<>();
		hash.put("ID", id);
		hash.put("Name", Name);
		hash.put("Store", Store);
		hash.put("Picture", Picture);
		hash.put("Cost", Cost);
		hash.put("Taxable", Taxable);
		hash.put("Addons", Addons);
		hash.put("Availible", Availible);
		return hash;
	}
	
	private HashMap<String, Object> makeAdd(int id, String Name, int Item_id, double Cost){
		HashMap<String, Object> add = new HashMap<>();
		add.put("ID", id);
		add.put("Name", Name);
		add.put("Item_id", Item_id);
		add.put("Cost", Cost);
		return add;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
