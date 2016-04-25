package com.renton.mediasync;

import java.util.Comparator;

public class DateComparator implements Comparator<GroupStatusEntity> {

	@Override
	public int compare(GroupStatusEntity lhs, GroupStatusEntity rhs) {
		return rhs.getGroupName().compareTo(lhs.getGroupName());
	}

}
