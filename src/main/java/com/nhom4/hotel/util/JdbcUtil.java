package com.nhom4.hotel.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUtil {
    private static final String DEFAULT_URL = "jdbc:sqlserver://localhost:1433;databaseName=DuAnTotNghiep_Nhom4;encrypt=true;trustServerCertificate=true;";
    private static final String URL = System.getenv().getOrDefault("HOTEL_DB_URL", DEFAULT_URL);
    private static final String USER = System.getenv().getOrDefault("HOTEL_DB_USER", "sa");
    private static final String PASSWORD = System.getenv().getOrDefault("HOTEL_DB_PASSWORD", "12345");

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Không tìm thấy SQL Server JDBC Driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static PreparedStatement prepare(Connection conn, String sql, Object... params) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i + 1, params[i]);
        }
        return ps;
    }

    public static int update(String sql, Object... params) {
        try (Connection conn = getConnection(); PreparedStatement ps = prepare(conn, sql, params)) {
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet query(Connection conn, String sql, Object... params) throws SQLException {
        return prepare(conn, sql, params).executeQuery();
    }
}
