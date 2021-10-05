package com.cfm.sacc.util;

import java.util.UUID;

public final class GUIDGenerator {

	private GUIDGenerator() {}
	
	public static final String generateGUID() {
		UUID uuid = UUID.randomUUID();
        return uuid.toString();
	}
}