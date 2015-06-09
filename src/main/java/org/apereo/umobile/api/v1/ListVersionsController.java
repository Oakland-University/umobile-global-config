package org.apereo.umobile.api.v1;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apereo.umobile.service.IPropertiesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/v1/")
public class ApiV1Controller {

	@Autowired
	private IPropertiesService propertiesService;

  private final ClassLoader CLASSLOADER = getClass().getClassLoader();
	private final String PRE_PROPERTIES_FILE_LOCATION = "mobile";
	private final String POST_PROPERTIES_FILE_LOCATION = "app.properties";


	@RequestMapping(value="/versions", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Map<String, List> getConfig (HttpServletRequest request, HttpServletResponse response) {
		
    Map<String, List> map = new LinkedHashMap<String, List>();

    File os = new File(CLASSLOADER.getResource(PRE_PROPERTIES_FILE_LOCATION).getFile());
    File[] osList = os.listFiles();

    for(File osName: osList){
      List<String> list = new ArrayList<String>();
      if(osName.isDirectory()){
        File version = new File(CLASSLOADER.getResource(PRE_PROPERTIES_FILE_LOCATION + "/" + osName.getName()).getFile());
        File[] versionList = version.listFiles();
        for(File versionName: versionList){
          if(versionName.isDirectory()){
            list.add(versionName.getName());  
          }
        }
        map.put(osName.getName(), list);
      }
    }
    return map; 
  }

  @RequestMapping(value="/{os}/{version}/config", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Map<String, Object> getConfig (@PathVariable String os, @PathVariable String version, 
        HttpServletRequest request, HttpServletResponse response) {
      final String fileName = String.format("%s/%s/%s/%s", 
          PRE_PROPERTIES_FILE_LOCATION, os, version, POST_PROPERTIES_FILE_LOCATION);

      Map<String, Object> properties = new LinkedHashMap<String, Object>();

      if (propertiesService.loadPropertiesFile(fileName)) {
        properties.putAll(propertiesService.getAllProperties());
      } else {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST); //return 400 Status Code
      }

      propertiesService.terminate();

      return properties;
    }

}
