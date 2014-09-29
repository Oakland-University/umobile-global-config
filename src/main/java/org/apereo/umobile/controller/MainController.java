package org.apereo.umobile.controller;

import java.util.Map;

import org.apereo.umobile.service.IPropertiesService;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class MainController {

	@Autowired
	private IPropertiesService propertiesService;

	private final String PRE_PROPERTIES_FILE_LOCATION = "mobile";
	private final String POST_PROPERTIES_FILE_LOCATION = "app.properties";

	@RequestMapping(value="/{os}/{version}/config", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getConfig (@PathVariable String os, @PathVariable String version) {
		final String fileName = String.format("%s/%s/%s/%s", PRE_PROPERTIES_FILE_LOCATION, os, version, 
				POST_PROPERTIES_FILE_LOCATION);

		if (propertiesService.loadPropertiesFile(fileName)) {
			return propertiesService.getAllProperties();
		}

		return null;
	}

}
