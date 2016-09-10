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

import br.edu.ufcg.tweet_annotator.dao.TweetDAO;


public class Tweet implements Serializable {
	
	private static final long serialVersionUID = -8826942349777541730L;
	
	private Long statusId;
	private String message;
	private Timestamp dateTime;
	
	public Tweet(Long statusId) {
		this.statusId = statusId;
	}
	
	public static Tweet getInstance(Long statusId) {
		
		TweetDAO dao = new TweetDAO();
		return dao.getTweetById(statusId);
	}

	public Long getStatusId() {
		return statusId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Timestamp getDateTime() {
		return dateTime;
	}

	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}

	public List<String> getStemmedMessage() {
		
		List<String> result = new ArrayList<String>();
		
		if (this.message != null) {
			String[] terms = this.message.trim().split(" ");
			for (String term : terms)
				result.add(term);
		}
		
		return result;
	}
}
