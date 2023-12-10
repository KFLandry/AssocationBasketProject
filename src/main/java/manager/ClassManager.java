package manager;

import db.ClassCategory;

import java.util.ArrayList;

public class ClassManager {
    private static ClassManager uniqueInstance;
    private ArrayList<ClassCategory> categories;
    private ClassCategory currentCategorie;

    private ClassManager(){
        categories  = new ArrayList<ClassCategory>();
        uniqueInstance  =  new ClassManager();
    }
    public static ClassManager getUniqueInstance(){
        return uniqueInstance;
    }


}
