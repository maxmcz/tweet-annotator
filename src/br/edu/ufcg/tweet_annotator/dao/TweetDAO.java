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

import br.edu.ufcg.tweet_annotator.bean.Tweet;
import br.edu.ufcg.tweet_annotator.bean.User;



public class TweetDAO extends GenericDAO {
	
	public TweetDAO() {	}

	public List<Tweet> getAllTweets() {
		
		List<Tweet> result = new ArrayList<Tweet>();
		
		String query = "SELECT * FROM tweets";
		
		try {
			PreparedStatement stmt = getConnection().prepareStatement(query);
			
			ResultSet res = stmt.executeQuery();
			
			while (res.next()) {
				Tweet t = new Tweet(res.getLong("status_id"));
				t.setDateTime(res.getTimestamp("datetime"));
				t.setMessage(res.getString("message"));
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
	
	public Tweet getTweetById(Long statusId) {
		
		Tweet result = null;
		
		String query = "SELECT * FROM tweets WHERE status_id = ? ";
		
		try {
			PreparedStatement stmt = getConnection().prepareStatement(query);
			stmt.setLong(1, statusId);
			
			ResultSet res = stmt.executeQuery();
			
			if (res.next()) {
				result = new Tweet(statusId);
				result.setDateTime(res.getTimestamp("datetime"));
				result.setMessage(res.getString("message"));
			}
			
			stmt.close();
			conn.close();
	        
		} catch (SQLException e) {
			System.err.println(query);
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Tweet loadNextTweet(Integer userId) {
		
		Tweet result = null;
		
		String query =  "( SELECT t.* " +
				   		"    FROM tweets t " +
				   		"   WHERE t.status_id NOT IN ( " +
				   		"                SELECT status_id " +
				   		"                  FROM annotated_tweets " +
				   		"                 WHERE user_annotated_id NOT IN (99) " + // 99 is guest user
				   		"                   AND spent_time_secs IS NOT NULL " +
				   		"         ) " +
				   		"   ORDER BY RANDOM() " +
				   		") ";
		
		try {
			PreparedStatement stmt = getConnection().prepareStatement(query);		
			ResultSet res = stmt.executeQuery();
			
			if (res.next()) {
				result = new Tweet(res.getLong("status_id"));
				result.setDateTime(res.getTimestamp("datetime"));
				result.setMessage(res.getString("message"));
			} else {
		
				stmt.close();
				
				query = "( SELECT t.* " +
					   "    FROM tweets t " +
					   "   WHERE t.status_id IN ( " +
					   "                SELECT status_id " +
					   "                  FROM annotated_tweets " +
					   "                 WHERE user_annotated_id NOT IN (99) " + // 99 is guest user
					   "                   AND spent_time_secs IS NOT NULL " +
					   "                   AND status_id NOT IN (" +
					   "								SELECT at.status_id " +
					   "								  FROM annotated_tweets at " +
					   "								 WHERE at.user_annotated_id = ? " +
					   "                                   AND at.spent_time_secs IS NOT NULL " +
					   "						) " +
					   "                GROUP BY status_id " +
					   "                HAVING count(*) < 3 " +
					   "                ORDER BY count(*) DESC, RANDOM() " +
					   "                LIMIT 1 " +
					   "         ) " +
					   ") ";
		
				/* +
					   " UNION " +
					   "( SELECT t.* " +
					   "    FROM tweets t " +
					   "   WHERE t.status_id NOT IN ( " +
					   "				SELECT at.status_id " +
					   "				  FROM annotated_tweets at " +
					   "  				 WHERE at.user_annotated_id = ? " +
					   "				   AND at.spent_time_secs IS NOT NULL " +
					   "		 ) " +
					   "  LIMIT 1 " +
					   ")";
				 */
		
				stmt = getConnection().prepareStatement(query);
				stmt.setInt(1, userId);
				//stmt.setInt(2, userId);
			
				//System.out.println(query);
			
				res = stmt.executeQuery();
			
				if (res.next()) {
					result = new Tweet(res.getLong("status_id"));
					result.setDateTime(res.getTimestamp("datetime"));
					result.setMessage(res.getString("message"));
				} else {
					
					stmt.close();
									
					query = "SELECT t.* " +
							   "    FROM tweets t " +
							   "   WHERE t.status_id IN ( " +
							   "				SELECT at.status_id " +
							   "				  FROM annotated_tweets at " +
							   "  				 WHERE at.user_annotated_id = ? " +
							   "				   AND at.spent_time_secs IS NULL " +
							   "		 ) " +
							   "  LIMIT 1 ";
					
					stmt = getConnection().prepareStatement(query);
					stmt.setInt(1, userId);
					
					res = stmt.executeQuery();
					
					if (res.next()) {
						result = new Tweet(res.getLong("status_id"));
						result.setDateTime(res.getTimestamp("datetime"));
						result.setMessage(res.getString("message"));
					} else {
						
						stmt.close();
						
						query = "( SELECT t.* " +
								   "    FROM tweets t " +
								   "   WHERE t.status_id IN ( " +
								   "                SELECT status_id " +
								   "                  FROM annotated_tweets " +
								   "                 WHERE user_annotated_id NOT IN (99) " + // 99 is guest user
								   "                   AND spent_time_secs IS NOT NULL " +
								   "                   AND status_id NOT IN (" +
								   "								SELECT at.status_id " +
								   "								  FROM annotated_tweets at " +
								   "								 WHERE at.user_annotated_id = ? " +
								   "                                   AND at.spent_time_secs IS NOT NULL " +
								   "						) " +
								   "                GROUP BY status_id " +
								   "                HAVING count(*) < 4 " +
								   "                ORDER BY count(*) DESC, RANDOM() " +
								   "                LIMIT 1 " +
								   "         ) " +
								   ") ";
					
						stmt = getConnection().prepareStatement(query);
						stmt.setInt(1, userId);
						
						res = stmt.executeQuery();
						
						if (res.next()) {
							result = new Tweet(res.getLong("status_id"));
							result.setDateTime(res.getTimestamp("datetime"));
							result.setMessage(res.getString("message"));
						}
						
					
						/* else {
							
							stmt.close();
						
							query = "( SELECT t.* " +
									   "    FROM tweets t " +
									   "   WHERE t.status_id IN ( " +
									   "                SELECT status_id " +
									   "                  FROM annotated_tweets " +
									   "                 WHERE user_annotated_id NOT IN (99) " + // 99 is guest user
									   "                   AND spent_time_secs IS NOT NULL " +
									   "                   AND status_id NOT IN (" +
									   "								SELECT at.status_id " +
									   "								  FROM annotated_tweets at " +
									   "								 WHERE at.user_annotated_id = ? " +
									   "                                   AND at.spent_time_secs IS NOT NULL " +
									   "						) " +
									   "                GROUP BY status_id " +
									   "                HAVING count(*) < 5 " +
									   "                ORDER BY count(*) DESC, RANDOM() " +
									   "                LIMIT 1 " +
									   "         ) " +
									   ") ";
							
							stmt = getConnection().prepareStatement(query);
							stmt.setInt(1, userId);
							
							res = stmt.executeQuery();
						
							if (res.next()) {
								result = new Tweet(res.getLong("status_id"));
								result.setDateTime(res.getTimestamp("datetime"));
								result.setMessage(res.getString("message"));
							} else {
								
								stmt.close();
								query = "( SELECT t.* " +
										   "    FROM tweets t " +
										   "   WHERE t.status_id NOT IN ( " +
										   "				SELECT at.status_id " +
										   "				  FROM annotated_tweets at " +
										   "  				 WHERE at.user_annotated_id = ? " +
										   "				   AND at.spent_time_secs IS NOT NULL " +
										   "		 ) " +
										   "  ORDER BY RANDOM() " +
										   "  LIMIT 1 " +
										   ")";
								
								stmt = getConnection().prepareStatement(query);
								stmt.setInt(1, userId);
								
								res = stmt.executeQuery();
								
								if (res.next()) {
									result = new Tweet(res.getLong("status_id"));
									result.setDateTime(res.getTimestamp("datetime"));
									result.setMessage(res.getString("message"));
								}
							}
						}
						*/

					}
				}
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
