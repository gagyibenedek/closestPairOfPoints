package main;

public class FloatComparator {
	
	private static final Float EPSILON = 0.0009f;
	
	public static boolean equals(Float a, Float b){
		boolean result = false;
		if(Math.abs(a - b) < EPSILON){
			result = true;
		}
		return result;
	}

}
