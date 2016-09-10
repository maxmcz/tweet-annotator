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
package br.edu.ufcg.tweet_annotator.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ufcg.tweet_annotator.bean.AnnotatedTerm;
import br.edu.ufcg.tweet_annotator.bean.AnnotatedTweet;
import br.edu.ufcg.tweet_annotator.bean.SemanticContext;
import br.edu.ufcg.tweet_annotator.bean.SpatialContext;
import br.edu.ufcg.tweet_annotator.bean.Tweet;
import br.edu.ufcg.tweet_annotator.bean.TweetGoldenSpatial;
import br.edu.ufcg.tweet_annotator.bean.User;



public class TweetsGoldenSpatialDAO extends GenericDAO {

	public TweetsGoldenSpatialDAO() { }

	public void save2D(TweetGoldenSpatial goldenTweet) {
		
		String query = "UPDATE tweets_golden_spatial SET dd_distance_geotag_user_loc=?, " +
				" dd_distance_geotag_mention=?, dd_distance_user_loc_mention=? " +
				" WHERE id = ? ";
		
		try {
			PreparedStatement stmt = getConnection().prepareStatement(query);
			
			stmt.setDouble(1, goldenTweet.getDistanceGeoTagUserLoc());
			stmt.setDouble(2, goldenTweet.getDistanceGeoTagMentionLoc());
			stmt.setDouble(3, goldenTweet.getDistanceUserLocMentionLoc());
			stmt.setLong(4, goldenTweet.getId());
			
			stmt.executeUpdate();
			stmt.close();
			conn.close();
			
		} catch (SQLException ee) {
			System.err.println(query);
			ee.printStackTrace();
		}
	}
	
	public void saveSpheroid(TweetGoldenSpatial goldenTweet) {
		
		String query = "UPDATE tweets_golden_spatial SET sp_distance_geotag_user_loc=?, " +
				" sp_distance_geotag_mention=?, sp_distance_user_loc_mention=? " +
				" WHERE id = ? ";
		
		try {
			PreparedStatement stmt = getConnection().prepareStatement(query);
			
			stmt.setDouble(1, goldenTweet.getSpheroidDistanceGeoTagUserLoc());
			stmt.setDouble(2, goldenTweet.getSpheroidDistanceGeoTagMentionLoc());
			stmt.setDouble(3, goldenTweet.getSpheroidDistanceUserLocMentionLoc());
			stmt.setLong(4, goldenTweet.getId());
			
			stmt.executeUpdate();
			stmt.close();
			conn.close();
			
		} catch (SQLException ee) {
			System.err.println(query);
			ee.printStackTrace();
		}
	}
	
	public List<TweetGoldenSpatial> getTweetsGolden() {
		
		List<TweetGoldenSpatial> result = new ArrayList<TweetGoldenSpatial>();
		
		String query = "SELECT * FROM tweets_golden_spatial ";
		
		try {
			PreparedStatement stmt = getConnection().prepareStatement(query);
			
			ResultSet res = stmt.executeQuery();
			
			while (res.next()) {
				
				TweetGoldenSpatial t = new TweetGoldenSpatial();
				
				t.setId(res.getLong("id"));
				t.setMessage(res.getString("text"));
				t.setPlace(res.getString("place"));
				t.setCoordinates(res.getString("coordinates"));
				t.setGeo(res.getString("geo"));
				t.setUserLocationString(res.getString("user_location"));
				t.setGeoAnnotation(res.getString("geo_annotation"));
				t.setGeoAnnotationTerm(res.getString("geo_annotation_term"));
				t.setUserLocationLat(res.getString("user_location_lat"));
				t.setUserLocationLng(res.getString("user_location_lng"));
				t.setGeoAnnotationLat(res.getString("geo_annotation_lat"));
				t.setGeoAnnotationLng(res.getString("geo_annotation_lng"));
				t.setDistanceGeoTagUserLoc(res.getDouble("dd_distance_geotag_user_loc"));
				t.setDistanceGeoTagMentionLoc(res.getDouble("dd_distance_geotag_mention"));
				t.setDistanceUserLocMentionLoc(res.getDouble("dd_distance_user_loc_mention"));
				
				result.add(t);
			}
			
			stmt.close();
			conn.close();
	        
		} catch (SQLException e) {
			System.err.println(query);
			e.printStackTrace();
		}
		
		return result;
	}

	public String getGeoQuery(String geoQuery) {
		
		String result = "";
		String query = "SELECT "+geoQuery;
		
		try {
			PreparedStatement stmt = getGeoConnection().prepareStatement(query);
			ResultSet res = stmt.executeQuery();
			
			if (res.next()) {
				result = res.getString(1);
			}
			
			stmt.close();
			conn.close();
	        
		} catch (SQLException e) {
			System.err.println(query);
			e.printStackTrace();
		}
		
		return result;
	}
}
