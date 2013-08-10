package main;

import java.util.ArrayList;
import java.util.List;

public class PointList {

	private List<Point> fullList;

	private Float left;

	private Float right;

	private int dominantCoord = 0;

	private final Float SMALL_MARGIN = 0.1f;

	public PointList(){
		this.fullList = new ArrayList<Point>();
		setBoundaries(dominantCoord); // X
	}

	public PointList(List<Point> points){
		this.fullList = points;
		setBoundaries(dominantCoord); // X
	}

	public PointList(List<Point> points, Float l, Float r){
		this.fullList = points;
		this.left = l;
		this.right = r;
	}

	private PointList getPointsFromRange(Float left, Float right, int coord){
		List<Point> points = new ArrayList<>();

		for(Point point : fullList){
			Float actualCoord = point.getCoordinate(coord);
			// the limits are interpreted the following way: (]
			if(actualCoord != null && actualCoord > left && actualCoord <= right){
				points.add(point);
			}
		}
		return new PointList(points);
	}

	private void setBoundaries(int coord){
		left = Float.MAX_VALUE;
		right = Float.MIN_VALUE;

		boolean sameDimension = true;
		Float dimensionValue = null;
		for(Point point : fullList){
			if(point.getCoordinate(coord) < this.left){
				this.left = point.getCoordinate(coord) - SMALL_MARGIN;
			} else if (point.getCoordinate(coord) > this.right){
				this.right = point.getCoordinate(coord);
			}

			if(dimensionValue == null){
				dimensionValue = point.getCoordinate(dominantCoord);
			} else if(!FloatComparator.equals(dimensionValue, point.getCoordinate(dominantCoord))){
				sameDimension = false;
			}
		}

		if(sameDimension && fullList.size() > 1){
			dominantCoord++;
			setBoundaries(dominantCoord);
		}
	}

	//Divide et impera solution
	public PointPair getClosestPointPair(){
		PointPair result = null;
		if(fullList != null && fullList.size() != 0){

			if(fullList.size() == 2){
				result =  new PointPair(fullList.get(0), fullList.get(1));
			} else if(fullList.size() == 1){
				result = null;
			} else {
				//divide area into two
				Float separator = (left+right)/2;

				PointList leftList = getPointsFromRange(this.left, separator,dominantCoord);
				PointList rightList = getPointsFromRange(separator, this.right, dominantCoord);

				PointPair leftMin = leftList.getClosestPointPair();
				PointPair rightMin = rightList.getClosestPointPair();

				PointPair min;
				if(leftMin == null){
					min = rightMin;
				} else if(rightMin == null){
					min = leftMin;
				} else if(leftMin.getDistance() < rightMin.getDistance()){
					min = leftMin;
				} else {
					min = rightMin;
				}

				PointList smallLeftList =  getPointsFromRange(separator - min.getDistance(), separator,dominantCoord);
				PointList smallRightList =  getPointsFromRange(separator, separator + min.getDistance(), dominantCoord);

				if(smallLeftList != null && smallRightList != null && !smallLeftList.getFullList().isEmpty() && !smallRightList.getFullList().isEmpty()){
					PointPair pair;
					for(Point leftp : smallLeftList.getFullList()){
						for(Point rightp : smallRightList.getFullList()){
							pair = new PointPair(leftp, rightp);
							if(pair.getDistance() < min.getDistance()){
								min = pair;
							}
						}
					}
				}

				result = min;

			}
		}

		return result;
	}

	//Brute force solution
	public PointPair getClosestPointPairBruteForce(){
		PointPair min = null;
		for(Point a : fullList){
			for(Point b : fullList){
				if(min == null && a != b){
					min = new PointPair(a, b);
				} else {
					PointPair pair = new PointPair(a, b);
					if(a != b && pair.getDistance() < min.getDistance()){
						min = pair;
					}
				}
			}
		}

		return min;
	}

	public void add(Point p){
		fullList.add(p);
		if(p.getCoordinate(dominantCoord) < left){
			left = p.getCoordinate(dominantCoord) - SMALL_MARGIN;
		} 
		if (p.getCoordinate(dominantCoord) > right){
			right = p.getCoordinate(dominantCoord);
		}
	}

	public List<Point> getFullList() {
		return fullList;
	}

	public void setFullList(List<Point> fullList) {
		this.fullList = fullList;
	}

	public Float getLeft() {
		return left;
	}

	public Float getRight() {
		return right;
	}

	public int getDominantCoord() {
		return dominantCoord;
	}

	public void setDominantCoord(int dominantCoord) {
		this.dominantCoord = dominantCoord;
	}

}
