package com.acadgild.movieratings.task1;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class CaseStudyIUseCasesReducer extends
			Reducer<Text, Text, Text, Text> {
		private ArrayList<Text> listMovies = new ArrayList<Text>();
		private ArrayList<Text> listRating = new ArrayList<Text>();
		
		public void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {
			listMovies.clear();
			listRating.clear();
			
			for(Text text:values) {
				if(text.charAt(0)=='M') {
					listMovies.add(new Text(text.toString().substring(1)));
				}else if(text.charAt(0)=='R') {
					listRating.add(new Text(text.toString().substring(1)));
				}
			}
			
			executeJoinLogic(context);
					
			/*String titles = "";
			double total = 0.0;
			int count = 0;
			System.out.println("Text Key    =>"+key.toString());
			for (Text t : values) {
				String parts[] = t.toString().split("\t");
				System.out.println("Text values =>"+t.toString());
				if (parts[0].equals("ratings")) {
					count++;
					String rating = parts[1].trim();
					System.out.println("Rating is =>"+rating);
					total += Double.parseDouble(rating);
				} else if (parts[0].equals("movies")) {
					titles = parts[1];
				}
			}
			
			double average = total / count;
			String str = String.format("%d\t%f", count, average);
			context.write(new Text(titles), new Text(str));*/
		}
		private void executeJoinLogic(Context context) throws IOException,InterruptedException{
			if(!listMovies.isEmpty()&&!listRating.isEmpty()) {
				for(Text moviesData:listMovies) {
					context.write(moviesData,new Text(" has been rated by user"));
				}
			}
		}
 }
