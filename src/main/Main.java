package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

	private static final String INPUT_FILE = "in.txt";

	private static final String OUTPUT_FILE = "out.txt";

	private static final String FORMAT_PATTERN = "##########.###";

	private static final String TAB = "	";

	private static final String SPACE = " ";
	
	private static final String NEW_LINE = "\n";
	
	private static final String FILLER = ". :";

	public static void main(String[] args) {
		try {
			PointList resultList = new PointList(readCoordinates(INPUT_FILE));

			long startTime = System.nanoTime();
			PointPair result = resultList.getClosestPointPair();
			long endTime = System.nanoTime();

			long duration = endTime - startTime;

			StringBuffer content = new StringBuffer();
			content.append(result.getaPoint().getLineId());
			content.append(FILLER);
			for(Float f : result.getaPoint().getCoordList()){
				content.append(SPACE);
				content.append(f);
			}
			content.append(NEW_LINE);
			content.append(result.getbPoint().getLineId());
			content.append(FILLER);
			for(Float f : result.getbPoint().getCoordList()){
				content.append(SPACE);
				content.append(f);
			}
			content.append(NEW_LINE);
			content.append(" Divide et impera duration:	");
			content.append(duration);
			content.append(NEW_LINE);
		

			startTime = System.nanoTime();
			result = resultList.getClosestPointPairBruteForce();
			endTime = System.nanoTime();

			duration = endTime - startTime;

			content.append(result.getaPoint().getLineId());
			content.append(FILLER);
			for(Float f : result.getaPoint().getCoordList()){
				content.append(SPACE);
				content.append(f);
			}
			content.append(NEW_LINE);
			content.append(result.getbPoint().getLineId());
			content.append(FILLER);
			for(Float f : result.getbPoint().getCoordList()){
				content.append(SPACE);
				content.append(f);
			}
			content.append(NEW_LINE);
			content.append(" Brute force duration:	");
			content.append(duration);
			
			printToFile(OUTPUT_FILE, content.toString());
		} catch (IOException e) {
			System.err.println("IO error!:(");
			e.printStackTrace();
		} 
	}

	private static void printToFile(String filename, String content) throws IOException{
		File file = new File(OUTPUT_FILE);

		if (!file.exists()) {
			file.createNewFile();
		}

		PrintWriter fw = new PrintWriter(file.getAbsoluteFile());
		fw.println(content);
		
		fw.close();

	}

	private static List<Point> readCoordinates(String filename) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line;
		//Line numbering starts from 1 (not 0)
		int lineNr = 1;
		
		List<Point> points = new ArrayList<Point>();
		int coordCount = 0;
		DecimalFormat format = new DecimalFormat(FORMAT_PATTERN);
		format.setRoundingMode(RoundingMode.DOWN);

		HashSet<String> pointSet = new HashSet<String>();

		while((line = br.readLine()) != null){
			StringTokenizer st = new StringTokenizer(line, TAB);
			List<Float> coords = new ArrayList<Float>();
			boolean readableNumbers = true;
			while(st.hasMoreTokens() && readableNumbers){	
				String s = st.nextToken();
				try{
					coords.add(Float.parseFloat(format.format(Float.parseFloat(s))));
				} catch (NumberFormatException nfe){
					readableNumbers = false;
				}
			}
			if(!coords.isEmpty()){
				if(coordCount == 0){
					coordCount = coords.size();
				}

				//add points only if they have the sufficient number of coordinates, and if it isn't already there
				if(coordCount == coords.size()){
					StringBuffer keyForPointSet = new StringBuffer();
					for(Float f : coords){
						keyForPointSet.append(f);
						keyForPointSet.append(SPACE);
					}
					if(!pointSet.contains(keyForPointSet.toString())){
						points.add(new Point(lineNr, coords));
						lineNr ++;
						pointSet.add(keyForPointSet.toString());
					}
				}

			}
		}
		br.close();
		return points;
	}

}
