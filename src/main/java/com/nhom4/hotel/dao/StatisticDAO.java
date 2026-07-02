package com.nhom4.hotel.dao;

import com.nhom4.hotel.entity.BookingStatusStat;
import com.nhom4.hotel.entity.RoomStatusStat;
import com.nhom4.hotel.util.JdbcUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StatisticDAO {
    public List<RoomStatusStat> roomStatus() {
        List<RoomStatusStat> list = new ArrayList<>();
        String sql = "SELECT trang_thai, COUNT(*) AS so_luong FROM Phong GROUP BY trang_thai ORDER BY trang_thai";
        try (Connection conn = JdbcUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(new RoomStatusStat(rs.getString("trang_thai"), rs.getInt("so_luong")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<BookingStatusStat> bookingStatus() {
        List<BookingStatusStat> list = new ArrayList<>();
        String sql = "SELECT trang_thai, COUNT(*) AS so_luong FROM DatPhong GROUP BY trang_thai ORDER BY trang_thai";
        try (Connection conn = JdbcUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(new BookingStatusStat(rs.getString("trang_thai"), rs.getInt("so_luong")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
