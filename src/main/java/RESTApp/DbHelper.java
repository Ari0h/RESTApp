package RESTApp;

import RESTApp.model.Tarif;
import RESTApp.model.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;

public class DbHelper {

    private Logger logger;
    private static Map<Integer, Tarif> tarifCash;

    public DbHelper(Logger log) {
        this.logger = log;
        tarifCash = new HashMap<>();
        AmountChangeThread changeThread = new AmountChangeThread();
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            logger.error(ex, ex);
        }
    }

    private Connection getConnection() {
        Connection conn = null;
        try {
            Properties p = new Properties();
            p.setProperty("user", "root");
            p.setProperty("password", "123");
            p.setProperty("useUnicode", "true");
            p.setProperty("characterEncoding", "utf-8");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/new_schema", p);
        } catch (Exception ex) {
            logger.error(ex, ex);
        }
        return conn;
    }

    public List<User> getUsers() {
        String query = "SELECT * FROM users";
        List<User> result = new ArrayList<>();
        try (Connection connection = getConnection()) {
            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                result.add(new User(rs.getInt("id"),
                        rs.getString("fio"),
                        rs.getString("phone"),
                        rs.getInt("tarifId")));
            }
        } catch (SQLException e) {
            logger.error(e, e);
        }
        return result;
    }

    public int insertUser(User user) {
        try (Connection connection = getConnection()) {
            String query = "INSERT INTO users(fio,phone,tarifId) VALUES(?,?,?)";
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, user.getFio());
            st.setString(2, user.getPhone());
            st.setInt(3, user.getTarifId());
            int result = st.executeUpdate();
            return result;
        } catch (Exception ex) {
            logger.error(ex, ex);
        }
        return 0;
    }

    public List<Tarif> getTarifs() {
        String query = "SELECT * FROM tarif";
        List<Tarif> result = new ArrayList<>();
        try (Connection connection = getConnection()) {
            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Tarif tarif = new Tarif(rs.getInt("tarifId"),
                        rs.getString("tarifName"),
                        rs.getInt("amount"),
                        rs.getInt("amountChange"),
                        rs.getInt("timeChange"));
                result.add(tarif);
                tarifCash.put(tarif.getTarifId(), tarif);
            }

        } catch (Exception ex) {
            logger.error(ex, ex);
        }
        return result;
    }

    public int insertTarif(Tarif tarif) {
        try (Connection connection = getConnection()) {
            String query = "INSERT INTO tarif(tarifName,amount,amountChange,timeChange) VALUES(?,?,?,?)";
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, tarif.getTarifName());
            st.setInt(2, tarif.getAmount());
            st.setInt(3, tarif.getAmountChange());
            st.setInt(4, tarif.getTimeChange());
            int result = st.executeUpdate();
            if (result == 1) {
                refreshCash();
            }
            return result;
        } catch (Exception ex) {
            logger.error(ex, ex);
        }
        return 0;
    }

    public int updateTarif(Tarif tarif) {
        try (Connection connection = getConnection()) {
            String query = "UPDATE tarif SET tarifName=?, amount=?, amountChange=?, timeChange=? WHERE tarifId=?";
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, tarif.getTarifName());
            st.setInt(2, tarif.getAmount());
            st.setInt(3, tarif.getAmountChange());
            st.setInt(4, tarif.getTimeChange());
            st.setInt(5, tarif.getTarifId());
            int result = st.executeUpdate();
            if (result == 1) {
                tarifCash.put(tarif.getTarifId(), tarif);
            }
            return result;
        } catch (Exception ex) {
            logger.error(ex, ex);
        }
        return 0;
    }

    public void refreshCash() {
        List<Tarif> tarifs = this.getTarifs();
        if (!tarifs.isEmpty()) {
            for (Tarif tarif : tarifs) {
                tarifCash.put(tarif.getTarifId(), tarif);
            }
        } else {
            logger.info("Tarifs not found");
        }

    }

    private boolean updateAmount(Tarif tarif) {
        boolean result = false;

        String query = "UPDATE tarif SET amount=? where tarifId=?";
        try (Connection connection = getConnection()) {
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(2, tarif.getTarifId());
            st.setInt(1, tarif.getAmount() + tarif.getAmountChange());
            st.addBatch();
            st.executeUpdate();
            result = true;
        } catch (Exception ex) {
            logger.error(ex, ex);
        }
        return result;
    }

    public class AmountChangeThread extends Thread {

        AmountChangeThread() {
            super();
            start();
        }

        @Override
        public void run() {
            try {
                logger.info("Start change thread");
                long startTime = System.currentTimeMillis();
                Map<Integer, Long> updateMap = new HashMap<>();
                logger.info(updateMap);
                while (!isInterrupted()) {
                    if (!tarifCash.isEmpty()) {
                        Set<Integer> keySet = tarifCash.keySet();
                        for (int key : keySet) {
                            Tarif tarif = tarifCash.get(key);
                            logger.info(updateMap);
                            long timeUpdate;
                            if (updateMap.get(tarif.getTarifId()) == null) {
                                timeUpdate = startTime + (tarif.getTimeChange() * 1000);
                                updateMap.put(tarif.getTarifId(), timeUpdate);
                            } else {
                                timeUpdate = updateMap.get(tarif.getTarifId());
                            }
                            logger.info(updateMap);
                            if (System.currentTimeMillis() >= timeUpdate) {
                                updateAmount(tarif);
                                updateMap.put(tarif.getTarifId(), timeUpdate + (tarif.getTimeChange() * 1000));
                                logger.info(updateMap);
                            }
                        }
                    }
                    Thread.sleep(5000);
                }
            } catch (Exception ex) {
                logger.error(ex, ex);
            }
        }
    }


}
