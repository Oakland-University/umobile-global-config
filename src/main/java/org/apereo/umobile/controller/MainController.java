package org.apereo.umobile.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Arrays;
import java.util.Properties;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class MainController {

	private final String PRE_PROPERTIES_FILE_LOCATION = "mobile";
	private final String POST_PROPERTIES_FILE_LOCATION = "app.properties";
	private final List<String> ARRAY_PROPERTY_LIST = Arrays.asList("disabledPortlets");
	private Properties props = new Properties();

	@RequestMapping(value="/{os}/{version}/config", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getConfig (@PathVariable String os, @PathVariable String version) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		try{
			final ClassLoader classLoader = getClass().getClassLoader();
			final String fileName = String.format("%s/%s/%s/%s", PRE_PROPERTIES_FILE_LOCATION,
					os, version, POST_PROPERTIES_FILE_LOCATION);
			final File file = new File(classLoader.getResource(fileName).getFile());

			props.load(new FileInputStream(file));

			for (String key : props.stringPropertyNames()) {
				if(ARRAY_PROPERTY_LIST.contains(key)) {
					final String arrayProperties = props.getProperty(key);
					map.put(key, Arrays.asList(arrayProperties.split(",")));
				} else {
					map.put(key, props.getProperty(key));
				}
			}
		} catch(Exception e) {
			//TO-DO Add Proper Logging
			System.out.println(e);
		} finally {
			return map;
		}
	}

}
