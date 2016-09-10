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
package br.edu.ufcg.tweet_annotator.web.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import br.edu.ufcg.tweet_annotator.bean.AnnotatedTerm;
import br.edu.ufcg.tweet_annotator.bean.AnnotatedTweet;
import br.edu.ufcg.tweet_annotator.bean.SemanticContext;
import br.edu.ufcg.tweet_annotator.bean.SentimentContext;
import br.edu.ufcg.tweet_annotator.bean.SpatialContext;
import br.edu.ufcg.tweet_annotator.bean.Tweet;
import br.edu.ufcg.tweet_annotator.bean.User;
import br.edu.ufcg.tweet_annotator.web.service.LoginService;


@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = -3731597641931604747L;

	private final LoginService service = LoginService.getService();
	
	private User currentUser;
	private String currentPage;
	private Tweet currentTweet;
	private AnnotatedTweet currentAnnotation;
	
	private List<AnnotatedTweet> tweetsAnnotated;
	
	private List<String> firstSemContList;
	private List<SemanticContext> semContList;
	private List<SpatialContext> spatContList;
	private List<SentimentContext> sentContList;
	
	private String inputUser;
	private String inputPass;
	
	private String tempSemanticContext;
	private String tempSpatialContext;
	private String tempSentimentContext;
	private String tempSentimentContextOpinion;
	private String tempSemanticTerms;
	private String tempSemanticTermsPos;
	private String tempSpatialTerms;
	private String tempSpatialTermsPos;
	private String tempSentimentTerms;
	private String tempSentimentTermsPos;
	private String tempAnnotationSequence;
	
	    
	@PostConstruct
	public void init() {
		
		initVars();
	}
	
	public LoginBean() {
		
	}
	
	private void initVars() {
		
		currentUser = null;
		currentPage = "Home";
		inputUser = "";
		inputPass = "";
		
		currentTweet = null;
		currentAnnotation = null;
		
		tweetsAnnotated = null;
		loadLists();
	}
	
	private void loadLists() {

		semContList = service.loadSemanticContexts();
		spatContList = service.loadSpatialContexts();
		sentContList = service.loadSentimentContexts();
		firstSemContList = service.loadFirstSemanticList();
	}
		
	public void doLogin(ActionEvent actionEvent) {

		User user;
		
		HttpServletRequest request = (HttpServletRequest) 
				FacesContext.getCurrentInstance().getExternalContext().getRequest();

		String ipAddress = request.getHeader("X-FORWARDED-FOR");  
		
		if (ipAddress == null) {  
			ipAddress = request.getRemoteAddr();  
		}
		
		if ((user = service.checkLogin(inputUser, inputPass, ipAddress)) != null) {
			currentUser = user;
			currentPage = "Home";
			loadLists();
			//addMessage("Welcome, "+currentUser.getName()+"!!!");
		} else {
			addMessage("Wrong e-mail/pass. Please, try again!");
		}
		
    }
	
	public void doLogout() {
		
		HttpServletRequest request = (HttpServletRequest) 
				FacesContext.getCurrentInstance().getExternalContext().getRequest();

		String ipAddress = request.getHeader("X-FORWARDED-FOR");  
		
		if (ipAddress == null) {  
			ipAddress = request.getRemoteAddr();  
		}
		
		service.saveLogout(currentUser.getId(), ipAddress);
		initVars();
	}
	
	public void goToManagePage() {
		
		tweetsAnnotated = service.loadAnnotations();
		currentPage = "Manage";
	}
	
	public void goToAnnotationPage() {
		
		currentTweet = service.getNextTweet(currentUser.getId());
		
		if (currentTweet != null) {
			currentAnnotation = service.startAnnotation(currentTweet, currentUser);
			
			if (currentAnnotation == null)
				currentTweet = null;
		}
		
		tempSemanticContext = "";
		tempSemanticTerms = "";
		tempSemanticTermsPos = "";
		tempSpatialContext = "";
		tempSpatialTerms = "";
		tempSpatialTermsPos = "";
		tempSentimentContext = "";
		tempSentimentContextOpinion = "";
		tempSentimentTerms = "";
		tempSentimentTermsPos = "";
		tempAnnotationSequence = "";
		
		currentPage = "Annotate";
	}
	
	public void saveAnnotation() {
		
		if ( !tempSemanticTerms.trim().isEmpty() && tempSemanticContext.trim().isEmpty() ) {
			return;
		}
		
		if ( !tempSpatialTerms.trim().isEmpty() && tempSpatialContext.trim().isEmpty() ) {
			return;
		}
		
		if ( !tempSentimentTerms.trim().isEmpty() && (tempSentimentContext.trim().isEmpty() || tempSentimentContextOpinion == null) ) {
			return;
		}
		
		if (tempSemanticContext != null && !tempSemanticContext.trim().equals("")) {
			for (SemanticContext sc : semContList) {
				if (sc.getId() == Integer.parseInt(tempSemanticContext)) {
					currentAnnotation.setSemContext(sc);
					currentAnnotation.setHasSemantics(true);
					break;
				}
			}
		}
		
		if (tempSpatialContext != null && !tempSpatialContext.trim().equals("")) {
			for (SpatialContext sc : spatContList) {
				if (sc.getId() == Integer.parseInt(tempSpatialContext)) {
					currentAnnotation.setSpatialContext(sc);
					currentAnnotation.setHasSpatial(true);
					break;
				}
			}
		}
		
		if (tempSentimentContext != null && !tempSentimentContext.trim().equals("") && tempSentimentContextOpinion != null) {
			for (SentimentContext sc : sentContList) {
				if (sc.getId() == Integer.parseInt(tempSentimentContext)) {
					currentAnnotation.setSentimentContext(sc);
					currentAnnotation.setHasSentiment(true);
					currentAnnotation.setIsOpinative(( tempSentimentContextOpinion.equals("1") ? true : false));
					break;
				}
			}
		}
		
		if (tempAnnotationSequence != null && !tempAnnotationSequence.trim().equals("")) {
		
			String[] annotationSequence = tempAnnotationSequence.split(",");
			
			List<Integer> sequenceList = new ArrayList<Integer>();
			sequenceList.add(0); //zero term
			for (String sequence : annotationSequence)
				sequenceList.add(Integer.parseInt(sequence));
			
			
			if (tempSemanticTerms != null && tempSemanticTermsPos != null &&
					!tempSemanticTerms.trim().equals("") && !tempSemanticTermsPos.trim().equals("")) {
				
				String[] semanticTermsPosition = tempSemanticTermsPos.split(",");
				String[] semanticTerms = tempSemanticTerms.split(",");
				
				for (int i=0; i<semanticTermsPosition.length; i++) {
					AnnotatedTerm at = new AnnotatedTerm(currentAnnotation.getId());
					at.setContextType(1); // Semantic
					at.setTermPosition(Integer.valueOf(semanticTermsPosition[i]));
					at.setSeqSelectByUser(sequenceList.indexOf(at.getTermPosition()));
					at.setTerm(semanticTerms[i]);
					currentAnnotation.appendAnnoTerm(at);
				}
			}
				
			if (tempSpatialTerms != null && tempSpatialTermsPos != null &&
					!tempSpatialTerms.trim().equals("") && !tempSpatialTermsPos.trim().equals("")) {
				
				String[] spatialTermsPosition = tempSpatialTermsPos.split(",");
				String[] spatialTerms = tempSpatialTerms.split(",");
				
				for (int i=0; i<spatialTermsPosition.length; i++) {
					AnnotatedTerm at = new AnnotatedTerm(currentAnnotation.getId());
					at.setContextType(2); // Spatial
					at.setTermPosition(Integer.valueOf(spatialTermsPosition[i]));
					at.setSeqSelectByUser(sequenceList.indexOf(at.getTermPosition()));
					at.setTerm(spatialTerms[i]);
					currentAnnotation.appendAnnoTerm(at);
				}
			}
			
			if (tempSentimentTerms != null && tempSentimentTermsPos != null &&
					!tempSentimentTerms.trim().equals("") && !tempSentimentTermsPos.trim().equals("")) {
				
				String[] sentimentTermsPosition = tempSentimentTermsPos.split(",");
				String[] sentimentTerms = tempSentimentTerms.split(",");
				
				for (int i=0; i<sentimentTermsPosition.length; i++) {
					AnnotatedTerm at = new AnnotatedTerm(currentAnnotation.getId());
					at.setContextType(3); // Sentiment
					at.setTermPosition(Integer.valueOf(sentimentTermsPosition[i]));
					at.setSeqSelectByUser(sequenceList.indexOf(at.getTermPosition()));
					at.setTerm(sentimentTerms[i]);
					currentAnnotation.appendAnnoTerm(at);
				}
			}
			
			
		
		}
		
		currentAnnotation.save();
		currentUser.setJobsCompleted(currentUser.getJobsCompleted()+1);
		goToAnnotationPage();
	}
	
	public void goToHomePage() {
		
		currentPage = "Home";
	}
	
	public void goToAboutPage() {
		
		currentPage = "About";
	}
	
	public void goToContactPage() {
		
		currentPage = "Contact";
	}
	
	public String getProgressClass() {
		
		int progress = getProgressBar();
		
		if (progress < 30)
			return "progress-bar-danger";
		else if (progress >= 30 && progress < 80)
			return "progress-bar-warning";
		else
			return "progress-bar-success";
	}
	
	public int getProgressBar() {
		
		if (getProgress() < 10) 
			return 10;
		else 
			return (int) getProgress();
	}

	public double getProgress() {
		
		if (currentUser != null)
			return ((double)currentUser.getJobsCompleted())/((double)currentUser.getMeta())*100.00;
		else
			return 0.0;
	}
	
	public String getProgressText() {
		return String.valueOf(((int)getProgress()));
	}

	public String getInputUser() {
		return inputUser;
	}

	public void setInputUser(String inputUser) {
		this.inputUser = inputUser;
	}

	public String getInputPass() {
		return inputPass;
	}

	public void setInputPass(String inputPass) {
		this.inputPass = inputPass;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
	
	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public Tweet getCurrentTweet() {
		return currentTweet;
	}

	public void setCurrentTweet(Tweet currentTweet) {
		this.currentTweet = currentTweet;
	}

	public AnnotatedTweet getCurrentAnnotation() {
		return currentAnnotation;
	}

	public void setCurrentAnnotation(AnnotatedTweet currentAnnotation) {
		this.currentAnnotation = currentAnnotation;
	}

	public List<SemanticContext> getSemContList() {
		return semContList;
	}

	public void setSemContList(List<SemanticContext> semContList) {
		this.semContList = semContList;
	}

	public List<SpatialContext> getSpatContList() {
		return spatContList;
	}

	public void setSpatContList(List<SpatialContext> spatContList) {
		this.spatContList = spatContList;
	}
	
	public String getTempSemanticContext() {
		return tempSemanticContext;
	}

	public void setTempSemanticContext(String tempSemanticContext) {
		this.tempSemanticContext = tempSemanticContext;
	}

	public String getTempSentimentContext() {
		return tempSentimentContext;
	}

	public void setTempSentimentContext(String tempSentimentContext) {
		this.tempSentimentContext = tempSentimentContext;
	}

	public String getTempSentimentContextOpinion() {
		return tempSentimentContextOpinion;
	}

	public void setTempSentimentContextOpinion(String tempSentimentContextOpinion) {
		this.tempSentimentContextOpinion = tempSentimentContextOpinion;
	}

	public String getTempSpatialContext() {
		return tempSpatialContext;
	}

	public void setTempSpatialContext(String tempSpatialContext) {
		this.tempSpatialContext = tempSpatialContext;
	}

	public String getTempSemanticTerms() {
		return tempSemanticTerms;
	}

	public void setTempSemanticTerms(String tempSemanticTerms) {
		this.tempSemanticTerms = tempSemanticTerms;
	}

	public String getTempSemanticTermsPos() {
		return tempSemanticTermsPos;
	}

	public void setTempSemanticTermsPos(String tempSemanticTermsPos) {
		this.tempSemanticTermsPos = tempSemanticTermsPos;
	}

	public String getTempSentimentTerms() {
		return tempSentimentTerms;
	}

	public void setTempSentimentTerms(String tempSentimentTerms) {
		this.tempSentimentTerms = tempSentimentTerms;
	}

	public String getTempSentimentTermsPos() {
		return tempSentimentTermsPos;
	}

	public void setTempSentimentTermsPos(String tempSentimentTermsPos) {
		this.tempSentimentTermsPos = tempSentimentTermsPos;
	}

	public String getTempSpatialTerms() {
		return tempSpatialTerms;
	}

	public void setTempSpatialTerms(String tempSpatialTerms) {
		this.tempSpatialTerms = tempSpatialTerms;
	}

	public String getTempSpatialTermsPos() {
		return tempSpatialTermsPos;
	}

	public void setTempSpatialTermsPos(String tempSpatialTermsPos) {
		this.tempSpatialTermsPos = tempSpatialTermsPos;
	}

	public String getTempAnnotationSequence() {
		return tempAnnotationSequence;
	}

	public void setTempAnnotationSequence(String tempAnnotationSequence) {
		this.tempAnnotationSequence = tempAnnotationSequence;
	}

	public List<String> getFirstSemContList() {
		return firstSemContList;
	}

	public void setFirstSemContList(List<String> firstSemContList) {
		this.firstSemContList = firstSemContList;
	}

	public List<AnnotatedTweet> getTweetsAnnotated() {
		return tweetsAnnotated;
	}

	public void setTweetsAnnotated(List<AnnotatedTweet> tweetsAnnotated) {
		this.tweetsAnnotated = tweetsAnnotated;
	}

	public List<SentimentContext> getSemtContList() {
		return sentContList;
	}

	public void setSentContList(List<SentimentContext> sentContList) {
		this.sentContList = sentContList;
	}

	public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}