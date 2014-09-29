package org.apereo.umobile.service;

import java.util.Map;
import java.util.List;

public interface IPropertiesService {

	public boolean loadPropertiesFile(String fileName);

	public Object getProperty(String property);

	public Map<String, Object> getAllProperties();
	
}
