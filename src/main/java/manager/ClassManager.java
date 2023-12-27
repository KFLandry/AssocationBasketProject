package manager;

import db.ClassCategory;
import db.ConnexionASdb;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class ClassManager {
    private static volatile ClassManager uniqueInstance;
    private int idCoach;
    private ArrayList<ClassCategory> categories =  new ArrayList<ClassCategory>();

    public ConnexionASdb getConnexionASdb() { return connexionASdb;}

    private ConnexionASdb connexionASdb;
    private ClassCategory currentCategorie;
    private ClassManager(){}
    public void setId(int id){idCoach=id;}
    public void loadCaterogies() throws Exception {
        String sqlReq= "SELECT id FROM ba_category WHERE idCoach="+idCoach;
        ResultSet resultSet = connexionASdb.getStatement().executeQuery(sqlReq);
        if(resultSet!= null){
            while (resultSet.next()){
                categories.add(new ClassCategory(resultSet.getInt("id")));
            }
        }
    }
    public void add(String []fields,String[] values) throws Exception {
        int id = connexionASdb.insert("ba_category",fields,values);
        categories.add(new ClassCategory(id));
    }
    public void update(String[] fields,String[] values) throws SQLException {
        connexionASdb.update(currentCategorie.getId(),"ba_category",fields,values);
    }
    public void delete(int id) throws SQLException {
        categories.remove(currentCategorie);
        connexionASdb.delete("ba_category",id);
    }

    public void setCurrentCategorie(ClassCategory current){ currentCategorie =  current; }
    public static ClassManager getUniqueInstance() throws Exception {
        if (uniqueInstance == null){
            synchronized(ClassManager.class){
                if (uniqueInstance == null){
                    uniqueInstance =  new ClassManager();
                    uniqueInstance.setConnexionASdb(new ConnexionASdb());
                }
            }
        }
        return uniqueInstance;
    }
    public void setConnexionASdb(ConnexionASdb connexionASdb) { this.connexionASdb = connexionASdb;}
}
