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
import br.edu.ufcg.tweet_annotator.bean.User;



public class AnnotatedTweetDAO extends GenericDAO {

	public AnnotatedTweetDAO() { }

	public void persist(AnnotatedTweet annotation) {
		
		String query = "INSERT INTO annotated_tweets(status_id, user_annotated_id, datetime, spent_time_secs, has_semantics, has_spatial, semantic_context, spatial_context, has_sentiment, sentiment, is_opinative) " +
				   		"VALUES (?, ?, NOW(), ?, ?, ?, ?, ?, ?, ?, ?) ";
		
		try {
			PreparedStatement stmt = getConnection().prepareStatement(query);
			
			stmt.setLong(1, annotation.getTweet().getStatusId());
			stmt.setInt(2, annotation.getUser().getId());
			stmt.setLong(3,  annotation.getSpentTimeSecs());
			stmt.setBoolean(4, annotation.getHasSemantics());
			stmt.setBoolean(5, annotation.getHasSpatial());
			
			if (annotation.getHasSemantics())
				stmt.setInt(6, annotation.getSemContext().getId());
			else
				stmt.setNull(6, java.sql.Types.INTEGER);
			
			if (annotation.getHasSpatial())
				stmt.setInt(7, annotation.getSpatialContext().getId());
			else
				stmt.setNull(7, java.sql.Types.INTEGER);
			
			if (annotation.getHasSentiment() != null)
				stmt.setBoolean(8, annotation.getHasSentiment());
			else
				stmt.setNull(8, java.sql.Types.BOOLEAN);
						
			if (annotation.getHasSentiment() != null)
				stmt.setInt(9, annotation.getSentimentContext().getId());
			else
				stmt.setNull(9, java.sql.Types.INTEGER);
			
			if (annotation.getIsOpinative() != null)
				stmt.setBoolean(10, annotation.getIsOpinative());
			else
				stmt.setNull(10, java.sql.Types.BOOLEAN);
				
			
			stmt.executeUpdate();
			stmt.close();
			conn.close();
			
		} catch (SQLException ee) {
			System.err.println(query);
			ee.printStackTrace();
		}
	}
	
	public void save(AnnotatedTweet annotation) {
		
		String query = "UPDATE annotated_tweets SET spent_time_secs=date_part('second',(NOW()-datetime)), " +
				" has_semantics=?, has_spatial=?, semantic_context=?, spatial_context=?, has_sentiment=?, sentiment=?, is_opinative=? " +
				" WHERE status_id = ? AND user_annotated_id = ? ";
		
		try {
			PreparedStatement stmt = getConnection().prepareStatement(query);
			
			stmt.setBoolean(1, annotation.getHasSemantics());
			stmt.setBoolean(2, annotation.getHasSpatial());
			
			if (annotation.getHasSemantics())
				stmt.setInt(3, annotation.getSemContext().getId());
			else
				stmt.setNull(3, java.sql.Types.INTEGER);
			
			if (annotation.getHasSpatial())
				stmt.setInt(4, annotation.getSpatialContext().getId());
			else
				stmt.setNull(4, java.sql.Types.INTEGER);
			
			stmt.setBoolean(5, annotation.getHasSentiment());
			
			if (annotation.getHasSentiment())
				stmt.setInt(6, annotation.getSentimentContext().getId());
			else
				stmt.setNull(6, java.sql.Types.INTEGER);
			
			stmt.setBoolean(7, annotation.getIsOpinative());
			
			stmt.setLong(8, annotation.getTweet().getStatusId());
			stmt.setInt(9, annotation.getUser().getId());
			
			stmt.executeUpdate();
			stmt.close();
			conn.close();
	        
			// save all terms
			AnnotatedTermDAO termDao = new AnnotatedTermDAO();
			for (AnnotatedTerm term : annotation.getAnnoTerms())
				termDao.save(term);
			
			// update user jobs
			UserDAO userDao = new UserDAO();
			userDao.incrementUserJobs(annotation.getUser().getId());
			
		} catch (SQLException ee) {
			System.err.println(query);
			ee.printStackTrace();
		}
	}
	
	public AnnotatedTweet startAnnotation(Tweet tweet, User user) {
		
		AnnotatedTweet result = null;
		
		String query = "INSERT INTO annotated_tweets(status_id, user_annotated_id, datetime) " +
					   "VALUES (?, ?, NOW()) ";
		
		try {
			PreparedStatement stmt = getConnection().prepareStatement(query);
			stmt.setLong(1, tweet.getStatusId());
			stmt.setInt(2, user.getId());
			
			stmt.executeUpdate();
			stmt.close();
			conn.close();
	        
		} catch (SQLException e) {
			
			query = "UPDATE annotated_tweets SET datetime = NOW() WHERE status_id = ? AND user_annotated_id = ? AND spent_time_secs IS NULL ";
			
			try {
				PreparedStatement stmt = getConnection().prepareStatement(query);
				stmt.setLong(1, tweet.getStatusId());
				stmt.setInt(2, user.getId());
				
				stmt.executeUpdate();
				stmt.close();
				conn.close();
		        
			} catch (SQLException ee) {
				System.err.println(query);
				ee.printStackTrace();
			}
		}
		
		query = "SELECT annotation_id, datetime FROM annotated_tweets WHERE status_id = ? AND user_annotated_id = ? AND spent_time_secs IS NULL ";
		
		try {
			PreparedStatement stmt = getConnection().prepareStatement(query);
			stmt.setLong(1, tweet.getStatusId());
			stmt.setInt(2, user.getId());
			
			ResultSet res = stmt.executeQuery();
		
			if (res.next()) {
				result = new AnnotatedTweet(tweet, user);
				result.setId(res.getLong("annotation_id"));
				result.setDateTime(res.getTimestamp("datetime"));
			}
			
			stmt.close();
			conn.close();
	        
		} catch (SQLException e) {
			System.err.println(query);
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	public int getJobsCompleted(User user) {
		
		int jobs = 0;
		
		String query = "SELECT count(*) FROM annotated_tweets WHERE user_annotated_id = ? AND spent_time_secs IS NOT NULL";
		
		try {
			PreparedStatement stmt = getConnection().prepareStatement(query);
			stmt.setInt(1, user.getId());
			
			ResultSet res = stmt.executeQuery();
			
			if (res.next()) {
				jobs = res.getInt(1);
			}
			
			stmt.close();
			conn.close();
	        
		} catch (SQLException e) {
			System.err.println(query);
			e.printStackTrace();
		}
		
		return jobs;
	}
	
	public List<AnnotatedTweet> getAnnotatedTweets() {
		
		List<AnnotatedTweet> result = new ArrayList<AnnotatedTweet>();
		
		//String query = "SELECT * FROM annotated_tweets WHERE spent_time_secs IS NOT NULL ORDER BY datetime DESC";
		
		String query = "SELECT * FROM annotated_tweets WHERE status_id IN (SELECT status_id FROM annotated_tweets WHERE spent_time_secs IS NOT NULL " +
					   " AND user_annotated_id NOT IN (2,13) GROUP BY status_id ORDER BY count(*) DESC) AND user_annotated_id NOT IN (2,13) ORDER BY status_id ";
		
		try {
			PreparedStatement stmt = getConnection().prepareStatement(query);
			
			ResultSet res = stmt.executeQuery();
			
			while (res.next()) {
				AnnotatedTweet at = new AnnotatedTweet(
						Tweet.getInstance(res.getLong("status_id")),
						User.getInstance(res.getInt("user_annotated_id")));
				at.setId(res.getLong("annotation_id"));
				at.setDateTime(res.getTimestamp("datetime"));
				at.setSpentTimeSecs(res.getLong("spent_time_secs"));
				at.setHasSemantics(res.getBoolean("has_semantics"));
				at.setHasSpatial(res.getBoolean("has_spatial"));
				at.setSemContext(SemanticContext.getInstance(res.getInt("semantic_context")));
				at.setSpatialContext(SpatialContext.getInstance(res.getInt("spatial_context")));
				//at.setAnnoTerms(AnnotatedTerm.getTermsByAnnotationId(res.getLong("annotation_id")));
				
				result.add(at);
			}
			
			stmt.close();
			conn.close();
	        
		} catch (SQLException e) {
			System.err.println(query);
			e.printStackTrace();
		}
		
		return result;
	}
	
	public List<AnnotatedTweet> getAnnotationsFromTweet(Long statusId) {
		
		List<AnnotatedTweet> result = new ArrayList<AnnotatedTweet>();
		
		String query = "SELECT * FROM annotated_tweets WHERE spent_time_secs IS NOT NULL AND status_id = ? AND user_annotated_id NOT IN (2,13,22,24) ";
		
		try {
			PreparedStatement stmt = getConnection().prepareStatement(query);
			stmt.setLong(1, statusId);
			
			ResultSet res = stmt.executeQuery();
			
			while (res.next()) {
				
				AnnotatedTweet at = new AnnotatedTweet(
						Tweet.getInstance(res.getLong("status_id")),
						User.getInstance(res.getInt("user_annotated_id")));
				
				at.setId(res.getLong("annotation_id"));
				at.setDateTime(res.getTimestamp("datetime"));
				at.setSpentTimeSecs(res.getLong("spent_time_secs"));
				at.setHasSemantics(res.getBoolean("has_semantics"));
				at.setHasSpatial(res.getBoolean("has_spatial"));
				at.setSemContext(SemanticContext.getInstance(res.getInt("semantic_context")));
				at.setSpatialContext(SpatialContext.getInstance(res.getInt("spatial_context")));
				//at.setAnnoTerms(AnnotatedTerm.getTermsByAnnotationId(res.getLong("annotation_id")));
				
				result.add(at);
			}
			
			stmt.close();
			conn.close();
	        
		} catch (SQLException e) {
			System.err.println(query);
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long getAvgAnnotationType(Long statusId, Integer semanticContext) {
		
		Long result = 0L;
		
		String query = "SELECT AVG(spent_time_secs)::bigint FROM annotated_tweets WHERE spent_time_secs IS NOT NULL AND status_id = ? AND user_annotated_id NOT IN (2,13,22,24) ";
		
		if (semanticContext == null)
			query += "AND semantic_context IS NULL";
		else
			query += "AND semantic_context = ? ";
		
		try {
			PreparedStatement stmt = getConnection().prepareStatement(query);
			stmt.setLong(1, statusId);
			
			if (semanticContext != null)
				stmt.setInt(2, semanticContext);
			
			ResultSet res = stmt.executeQuery();
			
			if (res.next()) {
				result = res.getLong(1);
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
