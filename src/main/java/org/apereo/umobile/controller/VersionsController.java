package org.apereo.umobile.controller;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apereo.umobile.service.IPropertiesService;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class VersionsController {

    private final ClassLoader CLASSLOADER = getClass().getClassLoader();
    private final String PRE_FILE_LOCATION = "mobile"; 

	@Autowired
	private IPropertiesService propertiesService;

	@RequestMapping(value="/versions", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Map<String, List> getConfig (HttpServletRequest request, HttpServletResponse response) {
		
   Map<String, List> tester = new LinkedHashMap<String, List>();
   List<String> list = new ArrayList<String>();

    File dir = new File(CLASSLOADER.getResource(PRE_FILE_LOCATION).getFile());
    File[] files = dir.listFiles();

    for(File file: files){
            if(file.isDirectory()){
                File dir2 = new File(CLASSLOADER.getResource(PRE_FILE_LOCATION + "/" + file.getName()).getFile());
                File[] files2 = dir2.listFiles();
                for(File file2: files2){
                        if(file2.isDirectory()){
                                list.add(file2.getName());  
                            }
                            else{
                                System.out.println("Hello");   
                             }
                    }
                tester.put(file.getName(), list);
               }
            else{
                System.out.println("No"); 
              }
           list = new ArrayList<String>();
        }
        return tester; 
	}

}
