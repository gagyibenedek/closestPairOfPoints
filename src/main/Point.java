package main;

import java.util.List;

public class Point {
	
	private int lineId;
	
	private List<Float> coordList;
	
	public Point(int lId, List<Float> coords){
		this.lineId = lId;
		this.coordList = coords;
	}
	
	public Float getCoordinate(int coordNr){
		Float result;
		try{
			result = coordList.get(coordNr);
		} catch (IndexOutOfBoundsException indexOut){
			result = null;
		}
		return result;
	}

	public int getLineId() {
		return lineId;
	}

	public void setLineId(int lineId) {
		this.lineId = lineId;
	}

	public List<Float> getCoordList() {
		return coordList;
	}

	public void setCoordList(List<Float> coordList) {
		this.coordList = coordList;
	}
}
