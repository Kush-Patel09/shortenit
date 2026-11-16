package com.urlshortner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UrlRepository {

    private static final String URL = "jdbc:h2:./urlshortenerdb";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS urls (" +
                "short_code VARCHAR(20) PRIMARY KEY, " +
                "original_url VARCHAR(2048) NOT NULL)";

        try (Connection con = getConnection();
                java.sql.Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void save(String code, String originalUrl) throws SQLException {
        String sql = "INSERT INTO urls (short_code, original_url) VALUES (?, ?)";

        try (Connection con = getConnection();
                java.sql.PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, code);
            ps.setString(2, originalUrl);

            ps.executeUpdate();
        }
    }

    public String findByCode(String code) throws SQLException {
        String sql = "SELECT original_url FROM urls WHERE short_code = ?";

        try (Connection con = getConnection();
                java.sql.PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, code);

            try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("original_url");
                }
            }
        }

        return null;
    }
    
    public String findByOriginalUrl(String originalUrl) throws SQLException {
    String sql = "SELECT short_code FROM urls WHERE original_url = ?";

    try (Connection con = getConnection();
         java.sql.PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, originalUrl);

        try (java.sql.ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getString("short_code");
            }
        }
    }

    return null;
}
}
