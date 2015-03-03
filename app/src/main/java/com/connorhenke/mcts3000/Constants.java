package com.connorhenke.mcts3000;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import android.util.Log;

public class Constants {
	
	public static final String API_KEY = "wHRcbttmWX6FFh9t25u8Ea6K9";
	public static final String API = "http://realtime.ridemcts.com/bustime/api/v2/";
	public static final String GOOGLE_DIRECTIONS = "maps.google.com/maps/api/directions/json";
	public static final String ROUTES = "getroutes";
	public static final String VEHICLES = "getvehicles";
	public static final String STOPS = "getstops";
	public static final String DIRECTIONS = "getdirections";
	public static final String PREDICTIONS = "getpredictions";
	
	public static JSONObject getVehicles(String route) throws RouteException {
		String params = "?key=" + API_KEY + "&rt=" + route + "&format=json";
		HttpResponse response = httpGet(API + VEHICLES + params);
		JSONObject jsonObj = null;
		if(response == null) {
			throw new RouteException("Could not get route");
		}
		if(response.getStatusLine().getStatusCode() == 200) {
			String body = "";
			try {
				BufferedReader content = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				int val;
				while((val = content.read()) !=  -1) {
					char character = (char)val;
					if(!Character.isWhitespace(character))
						body += ((char)val);
				}
				jsonObj = new JSONObject(body);
			} catch(IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return jsonObj;
	}

	public static JSONObject getPredictions(String stop, String route) {
		String params = "?key=" + API_KEY + "&rt=" + route + "&stpid=" + stop + "&format=json";
		Log.d("request", API + PREDICTIONS + params);
		HttpResponse response = httpGet(API + PREDICTIONS + params);
		JSONObject j = null;
		if(response.getStatusLine().getStatusCode() == 200) {
			String body = "";
			try {
				BufferedReader content = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				int val;
				while((val = content.read()) !=  -1) {
					char character = (char)val;
					if(!Character.isWhitespace(character))
						body += ((char)val);
				}
				Log.d("response", body);
				j = new JSONObject(body);
			} catch(IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return j;
	}
	
	public static JSONObject getStops(String route, String dir) {
		String params = "?key=" + API_KEY + "&rt=" + route + "&dir=" + dir + "&format=json";
		Log.d("request", API + STOPS + params);
		HttpResponse response = httpGet(API + STOPS + params);
		JSONObject jsonObj = null;
		if(response.getStatusLine().getStatusCode() == 200) {
			String body = "";
			try {
				BufferedReader content = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				int val;
				while((val = content.read()) !=  -1) {
					char character = (char)val;
					if(!Character.isWhitespace(character))
						body += ((char)val);
				}
				Log.d("response", body);
				jsonObj = new JSONObject(body);
			} catch(IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return jsonObj;
	}
	
	public static JSONObject getRoutes() {
		String params = "?key=" + API_KEY + "&format=json";
		HttpResponse response = httpGet(API + ROUTES + params);
		JSONObject jsonObj = null;
		if(response.getStatusLine().getStatusCode() == 200) {
			String body = "";
			try {
				BufferedReader content = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				int val;
				while((val = content.read()) !=  -1) {
					char character = (char)val;
					if(!Character.isWhitespace(character))
						body += ((char)val);
				}
				jsonObj = new JSONObject(body);
			} catch(IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return jsonObj;
	}

	public static JSONObject getDirections(String route) throws RouteException {
		String params = "?key=" + API_KEY + "&rt=" + route + "&format=json";
		HttpResponse response = httpGet(API + DIRECTIONS + params);
		JSONObject jsonObj = null;
		if(response == null) {
			throw new RouteException("Could not get route");
		}
		if(response.getStatusLine().getStatusCode() == 200) {
			String body = "";
			try {
				BufferedReader content = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				int val;
				while((val = content.read()) !=  -1) {
					char character = (char)val;
					if(!Character.isWhitespace(character))
						body += ((char)val);
				}
				jsonObj = new JSONObject(body);
			} catch(IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return jsonObj;
	}
	
	public static Document loadXml(String xml) throws Exception {
	   DocumentBuilderFactory fctr = DocumentBuilderFactory.newInstance();
	   DocumentBuilder bldr = fctr.newDocumentBuilder();
	   InputSource src = new InputSource(new StringReader(xml));
	   return bldr.parse(src);
	}
	
	public static HttpResponse httpGet(String url) {
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		HttpResponse response;
		try {
			response = client.execute(get);
		} catch (ClientProtocolException e) {
			response = null;
			e.printStackTrace();
		} catch (IOException e) {
			response = null;
			e.printStackTrace();
		}
		return response;
	}
	
}