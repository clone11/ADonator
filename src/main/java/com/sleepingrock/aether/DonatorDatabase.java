package com.sleepingrock.aether;

import javax.xml.transform.Result;
import java.sql.*;

/**
 * Created by Chris on 2/9/14.
 */
//TODO add error checking for all querys
//TODO make connect use a file
public class DonatorDatabase {
    public Connection sqlConnection = null;

    public DonatorDatabase(String host, String port, String user, String pass, String database) {
        String tmp = "jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + user + "&password=" + pass;
        if ((sqlConnection = Connect(tmp)) == null) {
            //Throw some kind of error
        }
    }

    public Connection Connect(String str) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            return DriverManager.getConnection(str);
        } catch (Exception e) {

        }
        return null;
    }

    public void addPlayer(String playerName) {
        if (!playerExists(playerName)) {
            try {
                PreparedStatement prep = sqlConnection.prepareStatement("INSERT INTO players (name) VALUES (?)");
                prep.setString(1, playerName);
                prep.execute();
                ADonate.log.info(playerName + " Added to the database");
            } catch (Exception e) {

            }
        }
    }

    public boolean playerExists(String playerName) {
        try {
            PreparedStatement prep = sqlConnection.prepareStatement("SELECT * FROM players WHERE name = ?");
            prep.setString(1, playerName);
            ResultSet rs = prep.executeQuery();
            if (rs.next()) return true;
        } catch (Exception e) {

        }
        return false;
    }

    public void setPlayerVipLevel(String playerName) {
        try {
            PreparedStatement prep = sqlConnection.prepareStatement("UPDATE players SET viplevel=? WHERE name=?");
            prep.setInt(1, 1);
            prep.setString(2, playerName);
            prep.execute();
        } catch (Exception e) {
            if (e instanceof SQLException) {
                ADonate.log.info(e.getMessage());
            }

        }
    }

    public int getPlayerVipLevel(String playerName) {
        try {
            PreparedStatement prep = sqlConnection.prepareStatement("SELECT viplevel FROM players WHERE name=?");
            prep.setString(1, playerName);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {

        }
        return 0;
    }

    public void setPlayerPoints(String playerName, double amount) {
        try {
            PreparedStatement prep = sqlConnection.prepareStatement("UPDATE players SET points=? WHERE name=?");
            prep.setDouble(1, amount);
            prep.setString(2, playerName);
            prep.execute();
        } catch (Exception e) {
            ADonate.log.info(e.getMessage());
        }

    }

    public int getPlayerPoints(String playerName) {
        try {
            PreparedStatement prep = sqlConnection.prepareStatement("SELECT points FROM players WHERE name=?");
            prep.setString(1, playerName);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {

        }
        return 0;
    }
}
