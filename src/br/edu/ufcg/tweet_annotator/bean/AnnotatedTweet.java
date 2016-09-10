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

import br.edu.ufcg.tweet_annotator.dao.AnnotatedTweetDAO;


public class AnnotatedTweet implements Serializable {

	private static final long serialVersionUID = 68637982767179714L;
	
	private Long id;
	private Tweet tweet;
	private User user;
	private Timestamp dateTime;
	private Long spentTimeSecs;
	private Boolean hasSemantics;
	private Boolean hasSpatial;
	private Boolean hasSentiment;
	private Boolean isOpinative;
	private SemanticContext semContext;
	private SpatialContext spatialContext;
	private SentimentContext sentimentContext;
	private List<AnnotatedTerm> annoTerms;
	
	public AnnotatedTweet(Tweet tweet, User user) {
		
		this.tweet = tweet;
		this.user = user;
		this.hasSemantics = false;
		this.hasSpatial = false;
		this.hasSemantics = false;
		this.isOpinative = null;
		this.annoTerms = new ArrayList<AnnotatedTerm>();
	}

	public void save() {
		AnnotatedTweetDAO dao = new AnnotatedTweetDAO();
		dao.save(this);
	}
	
	public void insert() {
		AnnotatedTweetDAO dao = new AnnotatedTweetDAO();
		dao.persist(this);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Tweet getTweet() {
		return tweet;
	}

	public void setTweet(Tweet tweet) {
		this.tweet = tweet;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Timestamp getDateTime() {
		return dateTime;
	}

	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}

	public Long getSpentTimeSecs() {
		return spentTimeSecs;
	}

	public void setSpentTimeSecs(Long spentTimeSecs) {
		this.spentTimeSecs = spentTimeSecs;
	}

	public Boolean getHasSemantics() {
		return hasSemantics;
	}

	public void setHasSemantics(Boolean hasSemantics) {
		this.hasSemantics = hasSemantics;
	}

	public Boolean getHasSpatial() {
		return hasSpatial;
	}

	public void setHasSpatial(Boolean hasSpatial) {
		this.hasSpatial = hasSpatial;
	}
	
	public Boolean getHasSentiment() {
		return hasSentiment;
	}

	public void setHasSentiment(Boolean hasSentiment) {
		this.hasSentiment = hasSentiment;
	}
	
	public Boolean getIsOpinative() {
		return isOpinative;
	}

	public void setIsOpinative(Boolean isOpinative) {
		this.isOpinative = isOpinative;
	}

	public SemanticContext getSemContext() {
		return semContext;
	}

	public void setSemContext(SemanticContext semContext) {
		this.semContext = semContext;
	}

	public SpatialContext getSpatialContext() {
		return spatialContext;
	}

	public void setSpatialContext(SpatialContext spatialContext) {
		this.spatialContext = spatialContext;
	}
	
	public SentimentContext getSentimentContext() {
		return sentimentContext;
	}

	public void setSentimentContext(SentimentContext sentimentContext) {
		this.sentimentContext = sentimentContext;
	}

	public List<AnnotatedTerm> getAnnoTerms() {
		return annoTerms;
	}

	public void setAnnoTerms(List<AnnotatedTerm> annoTerms) {
		this.annoTerms = annoTerms;
	}
	
	public void appendAnnoTerm(AnnotatedTerm annoTerm) {
		this.annoTerms.add(annoTerm);
	}

	@Override
	public String toString() {
		return "AnnotatedTweet [id=" + id + ", tweet=" + tweet + ", user="
				+ user + ", dateTime=" + dateTime + ", spentTimeSecs="
				+ spentTimeSecs + ", hasSemantics=" + hasSemantics
				+ ", hasSpatial=" + hasSpatial + ", hasSentiment="
				+ hasSentiment + ", isOpinative=" + isOpinative
				+ ", semContext=" + semContext + ", spatialContext="
				+ spatialContext + ", sentimentContext=" + sentimentContext
				+ ", annoTerms=" + annoTerms + "]";
	}
	
}
