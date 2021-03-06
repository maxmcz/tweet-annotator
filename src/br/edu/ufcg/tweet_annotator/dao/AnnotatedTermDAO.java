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
import br.edu.ufcg.tweet_annotator.bean.User;



public class AnnotatedTermDAO extends GenericDAO {
	
	public AnnotatedTermDAO() {	}
	
	public List<AnnotatedTerm> getTermsByAnnotationId(Long annotationId) {
		
		List<AnnotatedTerm> result = new ArrayList<AnnotatedTerm>();
		
		String query = "SELECT * FROM annotated_tweets_terms WHERE annotation_id = ? ";
		
		try {
			PreparedStatement stmt = getConnection().prepareStatement(query);
			stmt.setLong(1, annotationId);
			
			ResultSet res = stmt.executeQuery();
			
			if (res.next()) {
				AnnotatedTerm at = new AnnotatedTerm(annotationId);
				at.setContextType(res.getInt("context_type"));
				at.setSeqSelectByUser(res.getInt("seq_selected_by_user"));
				at.setTerm(res.getString("term"));
				at.setTermPosition(res.getInt("term_position"));
			}
			
			stmt.close();
			conn.close();
	        
		} catch (SQLException e) {
			System.err.println(query);
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void save(AnnotatedTerm term) {
		
		String query = "INSERT INTO annotated_tweets_terms(annotation_id, context_type, term_position, " +
				"term, seq_selected_by_user) VALUES (?, ?, ?, ?, ?)";
		
		try {
			PreparedStatement stmt = getConnection().prepareStatement(query);
			
			stmt.setLong(1, term.getAnnotationId());
			stmt.setInt(2, term.getContextType());
			stmt.setInt(3,  term.getTermPosition());
			stmt.setString(4, term.getTerm());
			stmt.setInt(5,  term.getSeqSelectByUser());
			
			stmt.executeUpdate();
			stmt.close();
			conn.close();
			
		} catch (SQLException ee) {
			System.err.println(query);
			ee.printStackTrace();
		}
	}
	
}
