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

import br.edu.ufcg.tweet_annotator.bean.Tweet;
import br.edu.ufcg.tweet_annotator.bean.User;
import br.edu.ufcg.tweet_annotator.bean.UserHistory;



public class UserDAO extends GenericDAO {
	
	public UserDAO() {	}

	public User getUserById(Integer userId) {
		
		User result = null;
		
		String query = "SELECT * FROM users WHERE user_id = ? ";
		
		try {
			PreparedStatement stmt = getConnection().prepareStatement(query);
			stmt.setInt(1, userId);
			
			ResultSet res = stmt.executeQuery();
			
			if (res.next()) {
				result = new User(userId);
				result.setEmail(res.getString("email"));
				result.setJobsCompleted(res.getInt("jobs"));
				result.setName(res.getString("name"));
				result.setPass(res.getString("password"));
				result.setTrustLevel(res.getDouble("trust_level"));
				result.setMeta(res.getInt("meta"));
			}
			
			stmt.close();
			conn.close();
	        
		} catch (SQLException e) {
			System.err.println(query);
			e.printStackTrace();
		}
		
		return result;
	}
	
	public User doLogin(String email, String password, String ip) {
		
		User result = null;
		
		String query = "SELECT * FROM users WHERE email = ? AND password = ?";
		
		try {
			PreparedStatement stmt = getConnection().prepareStatement(query);
			stmt.setString(1, email);
			stmt.setString(2, password);
			
			ResultSet res = stmt.executeQuery();
			
			if (res.next()) {
				result = new User(res.getInt("user_id"));
				result.setEmail(email);
				result.setPass(password);
				result.setName(res.getString("name"));
				result.setTrustLevel(res.getDouble("trust_level"));
				result.setJobsCompleted(res.getInt("jobs"));
				result.setMeta(res.getInt("meta"));
				
				UserHistory log = new UserHistory();
				log.setUserId(result.getId());
				log.setIp(ip);
				log.setType("IN");
				log.persistNew();
			}
			
			stmt.close();
			conn.close();
	        
		} catch (SQLException e) {
			System.err.println(query);
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void incrementUserJobs(Integer userId) {
		
		String query = "UPDATE users SET jobs=jobs+1 WHERE user_id = ? ";
		
		try {
			PreparedStatement stmt = getConnection().prepareStatement(query);
			
			stmt.setInt(1, userId);
			
			stmt.executeUpdate();
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			System.err.println(query);
			e.printStackTrace();
		}
	}
}
