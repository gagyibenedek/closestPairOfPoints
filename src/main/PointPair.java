package main;

public class PointPair {
	
	private Point aPoint;
	
	private Point bPoint;
	
	private Float distance;
	
	public PointPair(Point a, Point b){
		this.aPoint = a;
		this.bPoint = b;
		distance = calculateDistance();
	}
	
	public PointPair(Point a){
		this.aPoint = a;
		this.bPoint = null;
		distance = null;
	}

	private Float calculateDistance(){
		Float result = 0f;
		for(int i=0; i<aPoint.getCoordList().size(); i++){
			// http://dhruba.name/2012/09/01/performance-pattern-multiplication-is-20x-faster-than-math-pow/
			result += (aPoint.getCoordinate(i) - bPoint.getCoordinate(i)) * (aPoint.getCoordinate(i) - bPoint.getCoordinate(i));
		}
		result = (float) Math.sqrt(result.doubleValue());
		
		return result;
	}

	public Float getDistance() {
		return distance;
	}

	public Point getaPoint() {
		return aPoint;
	}

	public Point getbPoint() {
		return bPoint;
	}
	
}
