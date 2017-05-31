package preh.alexmaftei.editor.views;

import java.util.LinkedHashMap;

import TreeModel.Model;

public interface ViewSetter {    
    void setMessage(String message); 
    void setPortValue(String portValue);

    void sendHashMap(LinkedHashMap <String,String> hmap);

    void sedModel(Model model);
  
  
}