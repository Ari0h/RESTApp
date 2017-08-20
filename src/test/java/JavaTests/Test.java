package JavaTests;

import RESTApp.DbHelper;
import RESTApp.model.User;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

    Logger logger;
    DbHelper db;

    public Test() {
        logger = Logger.getLogger(Test.class);

        db = new DbHelper(logger);
    }



    @org.junit.Test
    public void getUsersFromDB() {
        List<User> users = db.getUsers();
        logger.info(users);
    }

    @org.junit.Test
    public void insertUserToDB(){
        User user = new User("Петров Петр Петрович","111222",1);
    int res = db.insertUser(user);
    logger.info(res);
    }

    @org.junit.Test
    public void testMap(){
        Map<Integer, String> testMap = new HashMap<>();
        testMap.put(1,"1");
        testMap.put(1,"2");
        testMap.put(1,"3");
        testMap.put(1,"4");
        testMap.put(1,"5");
        logger.info(testMap);


    }
}
