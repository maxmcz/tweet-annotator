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
package br.edu.ufcg.tweet_annotator.web.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import br.edu.ufcg.tweet_annotator.bean.AnnotatedTweet;
import br.edu.ufcg.tweet_annotator.bean.SemanticContext;
import br.edu.ufcg.tweet_annotator.bean.SentimentContext;
import br.edu.ufcg.tweet_annotator.bean.SpatialContext;
import br.edu.ufcg.tweet_annotator.bean.Tweet;
import br.edu.ufcg.tweet_annotator.bean.User;
import br.edu.ufcg.tweet_annotator.dao.AnnotatedTweetDAO;
import br.edu.ufcg.tweet_annotator.dao.SemanticContextDAO;
import br.edu.ufcg.tweet_annotator.dao.SentimentContextDAO;
import br.edu.ufcg.tweet_annotator.dao.SpatialContextDAO;
import br.edu.ufcg.tweet_annotator.dao.TweetDAO;
import br.edu.ufcg.tweet_annotator.dao.UserDAO;
import br.edu.ufcg.tweet_annotator.dao.UserHistoryDAO;

 
@ManagedBean(name = "loginService")
@ApplicationScoped
public class LoginService implements Serializable {
			
	private static final long serialVersionUID = -8305715125074386998L;
	
	private UserDAO userDao;
	private UserHistoryDAO userHistoryDao;
	private SemanticContextDAO semanticDao;
	private SpatialContextDAO spatialDao;
	private SentimentContextDAO sentimentDao;
	private TweetDAO tweetDao;
	private AnnotatedTweetDAO annotateDao;
     
	public static LoginService getService() {
		LoginService s = new LoginService();
		s.setUserDao(new UserDAO());
		s.setSemanticDao(new SemanticContextDAO());
		s.setSpatialDao(new SpatialContextDAO());
		s.setSentimentDao(new SentimentContextDAO());
		s.setTweetDao(new TweetDAO());
		s.setAnnotateDao(new AnnotatedTweetDAO());
		s.setUserHistoryDao(new UserHistoryDAO());
		return s;
	}
	
	public User checkLogin(String user, String pass, String ip) {
		return userDao.doLogin(user, pass, ip);
	}
	
	public void saveLogout(Integer userId, String ip) {
		userHistoryDao.doLogout(userId, ip);
	}
	
	public Tweet getNextTweet(Integer userId) {
		return tweetDao.loadNextTweet(userId);
	}
	
	public AnnotatedTweet startAnnotation(Tweet tweet, User user) {
		return annotateDao.startAnnotation(tweet, user);
	}
	
	public List<AnnotatedTweet> loadAnnotations() {
		return annotateDao.getAnnotatedTweets();
	}
	
	public List<SemanticContext> loadSemanticContexts() {
		return semanticDao.loadContexts();
	}
	
	public List<SentimentContext> loadSentimentContexts(){
		return sentimentDao.loadContexts();
	}
	
	@Deprecated
	public List<String> loadFirstSemanticList() {
		
		List<String> list = new ArrayList<String>();
		list.add("Complaining");
		list.add("Fixed Complaint");
		
		return list;
	}
	
	public List<SpatialContext> loadSpatialContexts() {
		return spatialDao.loadContexts();
	}
	
	public UserDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDAO dao) {
		this.userDao = dao;
	}

	public SemanticContextDAO getSemanticDao() {
		return semanticDao;
	}

	public void setSemanticDao(SemanticContextDAO semanticDao) {
		this.semanticDao = semanticDao;
	}

	public SpatialContextDAO getSpatialDao() {
		return spatialDao;
	}

	public void setSpatialDao(SpatialContextDAO spatialDao) {
		this.spatialDao = spatialDao;
	}

	public TweetDAO getTweetDao() {
		return tweetDao;
	}

	public void setTweetDao(TweetDAO tweetDao) {
		this.tweetDao = tweetDao;
	}

	public AnnotatedTweetDAO getAnnotateDao() {
		return annotateDao;
	}

	public void setAnnotateDao(AnnotatedTweetDAO annotateDao) {
		this.annotateDao = annotateDao;
	}

	public UserHistoryDAO getUserHistoryDao() {
		return userHistoryDao;
	}

	public void setUserHistoryDao(UserHistoryDAO userHistoryDao) {
		this.userHistoryDao = userHistoryDao;
	}

	public SentimentContextDAO getSentimentDao() {
		return sentimentDao;
	}

	public void setSentimentDao(SentimentContextDAO sentimentDao) {
		this.sentimentDao = sentimentDao;
	}
	
}