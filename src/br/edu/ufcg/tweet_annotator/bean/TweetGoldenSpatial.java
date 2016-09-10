/**
 * This software and database is being provided to you, the LICENSEE, by 
 * Federal University of Campina Grande (UFCG-Brazil) under the following 
 * license. By obtaining, using and/or copying this software and database, 
 * you agree that you have read, understood, and will comply with these 
 * terms and conditions.
 * 
 * Permission to use, copy, modify and distribute this software and database 
 * and its documentation for any purpose and without fee or royalty is 
 * hereby granted, provided that you agree to comply with the following 
 * copyright notice and statements, including the disclaimer, and that the 
 * same appear on ALL copies of the software, database and documentation, 
 * including modifications that you make for internal use or for distribution.
 * 
 * TweetAnnotator 1.0 Copyright 2015 by Federal University of Campina Grande 
 * (UFCG-Brazil). All rights reserved. THIS SOFTWARE AND DATABASE IS PROVIDED 
 * "AS IS" AND FEDERAL UNIVERSITY OF CAMPINA GRANDE MAKES NO REPRESENTATIONS 
 * OR WARRANTIES, EXPRESS OR IMPLIED. BY WAY OF EXAMPLE, BUT NOT LIMITATION, 
 * FEDERAL UNIVERSITY OF CAMPINA GRANDE MAKES NO REPRESENTATIONS OR WARRANTIES 
 * OF MERCHANT- ABILITY OR FITNESS FOR ANY PARTICULAR PURPOSE OR THAT THE USE 
 * OF THE LICENSED SOFTWARE, DATABASE OR DOCUMENTATION WILL NOT INFRINGE ANY 
 * THIRD PARTY PATENTS, COPYRIGHTS, TRADEMARKS OR OTHER RIGHTS. The name of 
 * Federal University of Campina Grande or UFCG may not be used in advertising 
 * or publicity pertaining to distribution of the software and/or database. 
 * Title to copyright in this software, database and any associated 
 * documentation shall at all times remain with Federal University of Campina 
 * Grande and LICENSEE agrees to preserve same.
 * 
 * @version 1.0
 * @author Maxwell G. de Oliveira (maxwell@ufcg.edu.br)
 * @copyright 2015 (UFCG, Brazil)
 * 
 * In scientific publications, technical reports, news or any other document 
 * that mention the TweetAnnotator, the authors must give appropriate credit 
 * by including the updated reference available in: 
 * 
 * https://github.com/maxmcz/tweet-annotator
 */
package br.edu.ufcg.tweet_annotator.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import br.edu.ufcg.tweet_annotator.dao.TweetsGoldenSpatialDAO;


public class TweetGoldenSpatial implements Serializable {

	private static final long serialVersionUID = 3011098521194685799L;
	
	private Long id;
	private String message;
	
	private String place;
	private String coordinates;
	private String geo;
	private String userLocationString;
	
	private String geoAnnotation;
	private String geoAnnotationTerm;
	
	private String userLocationLat;
	private String userLocationLng;
	private String geoAnnotationLat;
	private String geoAnnotationLng;
	
	private double distanceGeoTagUserLoc;
	private double distanceGeoTagMentionLoc;
	private double distanceUserLocMentionLoc;

	private double spheroidDistanceGeoTagUserLoc;
	private double spheroidDistanceGeoTagMentionLoc;
	private double spheroidDistanceUserLocMentionLoc;
	
	public TweetGoldenSpatial() {
		
	}

	public void save2D() {
		TweetsGoldenSpatialDAO dao = new TweetsGoldenSpatialDAO();
		dao.save2D(this);
	}
	
	public void saveSpheroid() {
		TweetsGoldenSpatialDAO dao = new TweetsGoldenSpatialDAO();
		dao.saveSpheroid(this);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public String getGeo() {
		return geo;
	}

	public void setGeo(String geo) {
		this.geo = geo;
	}

	public String getUserLocationString() {
		return userLocationString;
	}

	public void setUserLocationString(String userLocationString) {
		this.userLocationString = userLocationString;
	}

	public String getGeoAnnotation() {
		return geoAnnotation;
	}

	public void setGeoAnnotation(String geoAnnotation) {
		this.geoAnnotation = geoAnnotation;
	}

	public String getGeoAnnotationTerm() {
		return geoAnnotationTerm;
	}

	public void setGeoAnnotationTerm(String geoAnnotationTerm) {
		this.geoAnnotationTerm = geoAnnotationTerm;
	}

	public String getUserLocationLat() {
		return userLocationLat;
	}

	public void setUserLocationLat(String userLocationLat) {
		this.userLocationLat = userLocationLat;
	}

	public String getUserLocationLng() {
		return userLocationLng;
	}

	public void setUserLocationLng(String userLocationLng) {
		this.userLocationLng = userLocationLng;
	}

	public String getGeoAnnotationLat() {
		return geoAnnotationLat;
	}

	public void setGeoAnnotationLat(String geoAnnotationLat) {
		this.geoAnnotationLat = geoAnnotationLat;
	}

	public String getGeoAnnotationLng() {
		return geoAnnotationLng;
	}

	public void setGeoAnnotationLng(String geoAnnotationLng) {
		this.geoAnnotationLng = geoAnnotationLng;
	}

	public double getDistanceGeoTagUserLoc() {
		return distanceGeoTagUserLoc;
	}

	public void setDistanceGeoTagUserLoc(double distanceGeoTagUserLoc) {
		this.distanceGeoTagUserLoc = distanceGeoTagUserLoc;
	}

	public double getDistanceGeoTagMentionLoc() {
		return distanceGeoTagMentionLoc;
	}

	public void setDistanceGeoTagMentionLoc(double distanceGeoTagMentionLoc) {
		this.distanceGeoTagMentionLoc = distanceGeoTagMentionLoc;
	}

	public double getDistanceUserLocMentionLoc() {
		return distanceUserLocMentionLoc;
	}

	public void setDistanceUserLocMentionLoc(double distanceUserLocMentionLoc) {
		this.distanceUserLocMentionLoc = distanceUserLocMentionLoc;
	}
	
	public double getSpheroidDistanceGeoTagUserLoc() {
		return spheroidDistanceGeoTagUserLoc;
	}

	public void setSpheroidDistanceGeoTagUserLoc(
			double spheroidDistanceGeoTagUserLoc) {
		this.spheroidDistanceGeoTagUserLoc = spheroidDistanceGeoTagUserLoc;
	}

	public double getSpheroidDistanceGeoTagMentionLoc() {
		return spheroidDistanceGeoTagMentionLoc;
	}

	public void setSpheroidDistanceGeoTagMentionLoc(
			double spheroidDistanceGeoTagMentionLoc) {
		this.spheroidDistanceGeoTagMentionLoc = spheroidDistanceGeoTagMentionLoc;
	}

	public double getSpheroidDistanceUserLocMentionLoc() {
		return spheroidDistanceUserLocMentionLoc;
	}

	public void setSpheroidDistanceUserLocMentionLoc(
			double spheroidDistanceUserLocMentionLoc) {
		this.spheroidDistanceUserLocMentionLoc = spheroidDistanceUserLocMentionLoc;
	}

	public Point getGeoTagLocation() {
		
		double lat = 0;
		double lng = 0;
		
		if (getCoordinates() != null && !getCoordinates().trim().equals("") &&
				!getCoordinates().trim().equals("null")) {
			
			String[] coordinates = getCoordinates().split("\\[");
			coordinates[1] = coordinates[1].replace("]}","");
			coordinates = coordinates[1].split(",");
			
			lat = Double.valueOf(coordinates[1]);
			lng = Double.valueOf(coordinates[0]);
					
		} else {
			
			String[] coordinates = getPlace().split(":\\[\\[\\[");
			coordinates = coordinates[1].split("]]]}");
			coordinates = coordinates[0].split("],\\[");
			
			String polygon = "ST_AsText(ST_Centroid(ST_GeomFromText('POLYGON(("; //"ST_GeomFromText('POLYGON(( ))',4326)";
					
			polygon += coordinates[0].replace(","," ")+",";
			polygon += coordinates[1].replace(","," ")+",";
			polygon += coordinates[2].replace(","," ")+",";
			polygon += coordinates[3].replace(","," ")+",";
			polygon += coordinates[0].replace(","," ");
			
			polygon += "))',4326)))";
			
			// get from PostGIS
			TweetsGoldenSpatialDAO dao = new TweetsGoldenSpatialDAO();
			String centroid = dao.getGeoQuery(polygon);
			centroid = centroid.replace(")", "").replace("POINT(", "");
			
			String[] points = centroid.split(" ");
			lat = Double.valueOf(points[1]);
			lng = Double.valueOf(points[0]);
		}
		
		return new Point(lat,lng);
	}
	
	public Point getUserHomeLocation() {
		
		if (getUserLocationLat() == null || getUserLocationLat().trim().equals("null") || getUserLocationLat().trim().equals("") ||
				getUserLocationLng() == null || getUserLocationLng().trim().equals("null") || getUserLocationLng().trim().equals("")) {
			return null;
		}
		
		double lat = Double.valueOf(getUserLocationLat());
		double lng = Double.valueOf(getUserLocationLng());
		
		return new Point(lat,lng);
	}
	
	public Point getMentionLocation() {
		
		if (getGeoAnnotationLat() == null || getGeoAnnotationLat().trim().equals("null") || getGeoAnnotationLat().trim().equals("") ||
				getGeoAnnotationLng() == null || getGeoAnnotationLng().trim().equals("null") || getGeoAnnotationLng().trim().equals("")) {
			return null;
		}
		
		double lat = Double.valueOf(getGeoAnnotationLat());
		double lng = Double.valueOf(getGeoAnnotationLng());
		
		return new Point(lat,lng);
	}

	public double distance(Point a, Point b) {
	
		if (a == null || b == null) {
			return -1;
		}
		
		TweetsGoldenSpatialDAO dao = new TweetsGoldenSpatialDAO();
		
		String query = "ST_Distance(";
		
		query += "ST_Transform(ST_GeomFromText('POINT("+a.getLng()+" "+a.getLat()+")',4326),26986),";
		query += "ST_Transform(ST_GeomFromText('POINT("+b.getLng()+" "+b.getLat()+")',4326),26986)";
		query += ")";
		
		String distance = dao.getGeoQuery(query);
		return Double.valueOf(distance);
	}
	
	public double spheroidDistance(Point a, Point b) {
		
		if (a == null || b == null) {
			return -1;
		}
		
		TweetsGoldenSpatialDAO dao = new TweetsGoldenSpatialDAO();
		
		String query = "round(CAST(" +
				       "ST_Distance_Spheroid(ST_GeomFromText('POINT("+a.getLng()+" "+a.getLat()+")',4326), " +
				       		               "ST_GeomFromText('POINT("+b.getLng()+" "+b.getLat()+")',4326), " +
				       		               "'SPHEROID[\"WGS 84\",6378137,298.257223563]') " +
				       "AS numeric),2)";
		
		String distance = dao.getGeoQuery(query);
		return Double.valueOf(distance);
	}

	@Override
	public String toString() {
		return "TweetGoldenSpatial [id=" + id + ", message=" + message
				+ ", place=" + place + ", coordinates=" + coordinates
				+ ", geo=" + geo + ", userLocationString=" + userLocationString
				+ ", geoAnnotation=" + geoAnnotation + ", geoAnnotationTerm="
				+ geoAnnotationTerm + ", userLocationLat=" + userLocationLat
				+ ", userLocationLng=" + userLocationLng
				+ ", geoAnnotationLat=" + geoAnnotationLat
				+ ", geoAnnotationLng=" + geoAnnotationLng
				+ ", distanceGeoTagUserLoc=" + distanceGeoTagUserLoc
				+ ", distanceGeoTagMentionLoc=" + distanceGeoTagMentionLoc
				+ ", distanceUserLocMentionLoc=" + distanceUserLocMentionLoc
				+ ", spheroidDistanceGeoTagUserLoc="
				+ spheroidDistanceGeoTagUserLoc
				+ ", spheroidDistanceGeoTagMentionLoc="
				+ spheroidDistanceGeoTagMentionLoc
				+ ", spheroidDistanceUserLocMentionLoc="
				+ spheroidDistanceUserLocMentionLoc + "]";
	}
	
}
