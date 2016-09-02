package utils;

import java.util.Comparator;

public class MyComparator implements Comparator {

	@Override
	public int compare(Object o1, Object o2) {

		Object[] obj1 = (Object[]) o1;
		Object[] obj2 = (Object[]) o2;
		int v1 = Integer.valueOf(String.valueOf(obj1[0]));
		int v2 = Integer.valueOf(String.valueOf(obj2[0]));
		if (v1 > v2) {
			return 1;
		} else if (v1 < v2) {
			return -1;
		}
		return 0;
	}

}
