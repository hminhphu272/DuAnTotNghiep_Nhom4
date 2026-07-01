package com.hotel.dao;

import com.hotel.util.XJdbc;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ThongKeDAO {
    public BigDecimal doanhThu() {
        Object value = XJdbc.value("SELECT ISNULL(SUM(tong_tien),0) FROM HoaDon WHERE trang_thai='DA_THANH_TOAN'");
        return value == null ? BigDecimal.ZERO : new BigDecimal(value.toString());
    }

    public Map<String, Integer> thongKePhong() {
        Map<String, Integer> map = new LinkedHashMap<>();
        String sql = "SELECT trang_thai, COUNT(*) AS so_luong FROM Phong GROUP BY trang_thai";
        try (Connection conn = XJdbc.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                map.put(rs.getString("trang_thai"), rs.getInt("so_luong"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    public Map<String, Integer> thongKeDatPhong() {
        Map<String, Integer> map = new LinkedHashMap<>();
        String sql = "SELECT trang_thai, COUNT(*) AS so_luong FROM DatPhong GROUP BY trang_thai";
        try (Connection conn = XJdbc.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                map.put(rs.getString("trang_thai"), rs.getInt("so_luong"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return map;
    }
}
