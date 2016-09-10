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
package br.edu.ufcg.tweet_annotator.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.edu.ufcg.tweet_annotator.bean.AnnotatedTweet;
import br.edu.ufcg.tweet_annotator.bean.SemanticContext;
import br.edu.ufcg.tweet_annotator.bean.SpatialContext;
import br.edu.ufcg.tweet_annotator.bean.Tweet;
import br.edu.ufcg.tweet_annotator.bean.User;
import br.edu.ufcg.tweet_annotator.dao.AnnotatedTweetDAO;
import br.edu.ufcg.tweet_annotator.dao.TweetDAO;


public class AnnotationAnalysis {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		double semantic_threshould = 0.666666;//0.01;//0.5;
		double spatial_threshould = 0.666666;//0.01;//0.5;
		
		boolean show_context_sets = true;
		int total_selected_tweets = 0;
		int tweet = 0;
		
		User user = User.getInstance(25);
		
		TweetDAO tDAO = new TweetDAO();
		AnnotatedTweetDAO atDAO = new AnnotatedTweetDAO();
		
		for (Tweet t : tDAO.getAllTweets()) {
			
			tweet++;
			int annotations = 0;
			List<Integer> semantic_contexts = new ArrayList<Integer>();
			List<Integer> semantic_contexts_weight = new ArrayList<Integer>();
			List<Integer> spatial_contexts = new ArrayList<Integer>();
			List<Integer> spatial_contexts_weight = new ArrayList<Integer>();
			
			int max_sem_weight = 0;
			int max_spa_weight = 0;
			
			Integer selected_semantic = null;
			Integer selected_spatial  = null;
			
			for (AnnotatedTweet at : atDAO.getAnnotationsFromTweet(t.getStatusId())) {
				
				annotations++;
				
				Integer sem_context = null;
				if (at.getHasSemantics())
					sem_context = at.getSemContext().getId();
				
				Integer spa_context = null;
				if (at.getHasSpatial())
					spa_context = at.getSpatialContext().getId();
				
				int sem_weight 	= 0;
				int spa_weight  = 0;
				
				if (semantic_contexts.contains(sem_context)) {
					sem_weight = semantic_contexts_weight.get(semantic_contexts.indexOf(sem_context))+1;
					semantic_contexts_weight.set(
							semantic_contexts.indexOf(sem_context), 
							sem_weight);
				} else {
					semantic_contexts.add(sem_context);
					semantic_contexts_weight.add(1);
					sem_weight = 1;
				}
				
				if (spatial_contexts.contains(spa_context)) {
					spa_weight = spatial_contexts_weight.get(spatial_contexts.indexOf(spa_context))+1;
					spatial_contexts_weight.set(
							spatial_contexts.indexOf(spa_context), 
							spa_weight);
				} else {
					spatial_contexts.add(spa_context);
					spatial_contexts_weight.add(1);
					spa_weight = 1;
				}
				
				if (sem_weight > max_sem_weight)
					max_sem_weight = sem_weight;
				
				if (spa_weight > max_spa_weight)
					max_spa_weight = spa_weight;
			}
			
			if (show_context_sets) {
				
				System.out.println("Tweet n. ("+tweet+") : "+annotations+" annotations ");
				System.out.print("Atributted semantic set: \t");
				for (Integer x : semantic_contexts)
					System.out.print("[ "+x+" ] ");
				
				System.out.println();
				System.out.print("Atributted semantic weight: \t");
				for (Integer x : semantic_contexts_weight)
					System.out.print("[ "+x+" ] ");
				
				System.out.println();
				System.out.print("Atributted spatial set: \t");
				for (Integer x : spatial_contexts)
					System.out.print("[ "+x+" ] ");
				
				System.out.println();
				System.out.print("Atributted spatial weight: \t");
				for (Integer x : spatial_contexts_weight)
					System.out.print("[ "+x+" ] ");
				
				System.out.println();
			}
			
			if (max_sem_weight >= annotations*semantic_threshould) {
				selected_semantic = semantic_contexts.get(semantic_contexts_weight.indexOf(max_sem_weight));
				System.out.println("Semantic ID: "+selected_semantic);
			} else {
				selected_semantic = -1;
				System.out.println("No semantics agreement");
			}
			
			if (max_spa_weight >= annotations*spatial_threshould) {
				selected_spatial = spatial_contexts.get(spatial_contexts_weight.indexOf(max_spa_weight));
				System.out.println("Spatial ID: "+selected_spatial);
			} else {
				selected_spatial = -1;
				System.out.println("No spatial agreement");
			}
			
			if ((selected_semantic == null || (selected_semantic != null && selected_semantic != -1)) && 
				(selected_spatial == null  || (selected_spatial != null && selected_spatial != -1))) {
				
				System.out.println("-- Selected --");
				total_selected_tweets++;
				
				AnnotatedTweet at = new AnnotatedTweet(t, user);
				
				if (selected_semantic == null) {
					at.setHasSemantics(false);
					at.setSemContext(null);
				} else {
					at.setHasSemantics(true);
					at.setSemContext(SemanticContext.getInstance(selected_semantic));
				}
				
				if (selected_spatial == null) {
					at.setHasSpatial(false);
					at.setSpatialContext(null);
				} else {
					at.setHasSpatial(true);
					at.setSpatialContext(SpatialContext.getInstance(selected_spatial));
				}
				
				at.setSpentTimeSecs(atDAO.getAvgAnnotationType(t.getStatusId(), selected_semantic));
				at.insert();
				
				//System.out.println(at);
				
			} else {
				System.out.println("-- NOT Selected --");
			}
			
			System.out.println();
			//break;
		}
		
		System.out.println();
		System.out.println("Total selected tweets: "+total_selected_tweets+"/"+tweet);
		System.out.println((((double)total_selected_tweets/(double)tweet)*100.00)+" %");
	}

}
