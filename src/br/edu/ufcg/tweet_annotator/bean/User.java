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

import br.edu.ufcg.tweet_annotator.dao.TweetDAO;
import br.edu.ufcg.tweet_annotator.dao.UserDAO;


public class User implements Serializable {

	private static final long serialVersionUID = -8466675466436627114L;
	
	private Integer id;
	private String name;
	private String email;
	private String pass;
	private Double trustLevel;
	private Integer jobsCompleted;
	private Integer meta;
	
	public User(Integer id) {
		this.id = id;
		this.meta = 0;
	}
	
	public static User getInstance(Integer id) {
		
		UserDAO dao = new UserDAO();
		return dao.getUserById(id);
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPass() {
		return pass;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setPass(String pass) {
		this.pass = pass;
	}

	public Double getTrustLevel() {
		return trustLevel;
	}

	public void setTrustLevel(Double trustLevel) {
		this.trustLevel = trustLevel;
	}

	public Integer getJobsCompleted() {
		return jobsCompleted;
	}

	public void setJobsCompleted(Integer jobsCompleted) {
		this.jobsCompleted = jobsCompleted;
	}

	public Integer getMeta() {
		return meta;
	}

	public void setMeta(Integer meta) {
		this.meta = meta;
	}
	
	
}
