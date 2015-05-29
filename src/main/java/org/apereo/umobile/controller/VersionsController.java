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

    File directories = new File(CLASSLOADER.getResource(PRE_FILE_LOCATION).getFile());
    File[] directoryList = directories.listFiles();

    for(File folder: directoryList){
      List<String> list = new ArrayList<String>();
      if(folder.isDirectory()){
        File subDirectories = new File(CLASSLOADER.getResource(PRE_FILE_LOCATION + "/" + folder.getName()).getFile());
        File[] subDirectoryList = subDirectories.listFiles();
        for(File subFolder: subDirectoryList){
          if(subFolder.isDirectory()){
            list.add(subFolder.getName());  
          }
        }
        map.put(folder.getName(), list);
      }
    }
    return map; 
  }

}
