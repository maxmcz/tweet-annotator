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

import br.edu.ufcg.tweet_annotator.bean.TweetGoldenSpatial;
import br.edu.ufcg.tweet_annotator.dao.TweetsGoldenSpatialDAO;

public class GeoAnnotatedAnalysis {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		TweetsGoldenSpatialDAO tDAO = new TweetsGoldenSpatialDAO();
		
		int n=1;
		for (TweetGoldenSpatial t : tDAO.getTweetsGolden()) {
		
			//System.out.println(t);
			System.out.print((n)+". \t");
			System.out.println(t.getGeoTagLocation() + "\t" + t.getUserHomeLocation() + "\t" + t.getMentionLocation());
			
			// 2D-Distance function
			
			//System.out.println("\t Distance (GeoTag-UserHome) = " + t.distance(t.getGeoTagLocation(), t.getUserHomeLocation()));
			//System.out.println("\t Distance (GeoTag-Mention) = " + t.distance(t.getGeoTagLocation(), t.getMentionLocation()));
			//System.out.println("\t Distance (UserHome-Mention) = " + t.distance(t.getUserHomeLocation(), t.getMentionLocation()));
			
			//t.setDistanceGeoTagMentionLoc( t.distance(t.getGeoTagLocation(), t.getMentionLocation()) );
			//t.setDistanceGeoTagUserLoc( t.distance(t.getGeoTagLocation(), t.getUserHomeLocation()) );
			//t.setDistanceUserLocMentionLoc( t.distance(t.getUserHomeLocation(), t.getMentionLocation()) );
			
			//t.save2D();
			
			// Spheroid Distance
			
			System.out.println("\t Distance (GeoTag-UserHome) = " + t.spheroidDistance(t.getGeoTagLocation(), t.getUserHomeLocation()) );
			System.out.println("\t Distance (GeoTag-Mention) = " + t.spheroidDistance(t.getGeoTagLocation(), t.getMentionLocation()) );
			System.out.println("\t Distance (UserHome-Mention) = " + t.spheroidDistance(t.getUserHomeLocation(), t.getMentionLocation()) );
			
			t.setSpheroidDistanceGeoTagMentionLoc( t.spheroidDistance(t.getGeoTagLocation(), t.getMentionLocation()) );
			t.setSpheroidDistanceGeoTagUserLoc( t.spheroidDistance(t.getGeoTagLocation(), t.getUserHomeLocation()) );
			t.setSpheroidDistanceUserLocMentionLoc( t.spheroidDistance(t.getUserHomeLocation(), t.getMentionLocation()) );
			
			t.saveSpheroid();
			n++;
		}
	}

}
