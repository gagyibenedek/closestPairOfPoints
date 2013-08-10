package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
	
	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE));
			String line;
			int lineNr = 0;
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
			
			PointPair result = (new PointList(points)).getClosestPointPair();
			System.out.println(result.getaPoint().getLineId() + " " + result.getbPoint().getLineId());
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
