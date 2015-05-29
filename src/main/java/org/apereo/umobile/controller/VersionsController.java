package org.apereo.umobile.controller;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class VersionsController {

    private final ClassLoader CLASSLOADER = getClass().getClassLoader();
    private final String PRE_FILE_LOCATION = "mobile"; 

	@RequestMapping(value="/versions", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Map<String, List> getConfig (HttpServletRequest request, HttpServletResponse response) {
		
    Map<String, List> map = new LinkedHashMap<String, List>();

    File os = new File(CLASSLOADER.getResource(PRE_FILE_LOCATION).getFile());
    File[] osList = os.listFiles();

    for(File osName: osList){
      List<String> list = new ArrayList<String>();
      if(osName.isDirectory()){
        File version = new File(CLASSLOADER.getResource(PRE_FILE_LOCATION + "/" + osName.getName()).getFile());
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

}
