package org.apereo.umobile.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import java.util.List;
import java.util.Arrays;
import java.util.Properties;
import java.util.LinkedHashMap;

import org.apache.commons.io.IOUtils;

import org.springframework.stereotype.Service;

@Service
public class LocalPropertyServiceImpl implements IPropertiesService {

  private final ClassLoader classLoader = getClass().getClassLoader();

  private final String CUSTOM_DELIMITER = ",";
  private final List<String> ARRAY_PROPERTY_LIST = Arrays.asList("disabledPortlets", "disabledFolders");
  private final List<String> BOOLEAN_PROPERTY_LIST = Arrays.asList("upgradeRequired", "upgradeRecommended");

  private Properties props;
  private FileInputStream fis;

  public boolean loadPropertiesFile(String fileName) {
    if ((props != null) || (fis != null)){
      this.terminate();
    }

    try {
      final File file = new File(classLoader.getResource(fileName).getFile());
      fis = new FileInputStream(file);
      props = new Properties();
      props.load(fis);
    } catch (Exception e) {
      System.err.println(e);
      return false;
    }

    return true;
  }

  public Object getProperty(String property) {
    if (!isFileLoaded()) throw new IllegalArgumentException("Property file not loaded!");

    Object temp;

    if(ARRAY_PROPERTY_LIST.contains(property)) {

      final String arrayProperties = props.getProperty(property);
      temp = Arrays.asList(arrayProperties.split(CUSTOM_DELIMITER));

    } else if (BOOLEAN_PROPERTY_LIST.contains(property)) {

       final String booleanProperty = props.getProperty(property);
       temp = Boolean.parseBoolean(booleanProperty);

    } else {

      temp = props.getProperty(property);

    }

    return temp;
  }

  public Map<String, Object> getAllProperties() {
    if (!isFileLoaded()) throw new IllegalArgumentException("Property file not loaded!");

    final Map<String, Object> map = new LinkedHashMap<String, Object>();

    for (String key : props.stringPropertyNames()) {
      map.put(key, getProperty(key));
    }

    return map;
  }

  public void terminate() {
    IOUtils.closeQuietly(fis);
    fis = null;
    props = null;
  }

  private boolean isFileLoaded() {
    return props != null;
  }

}
