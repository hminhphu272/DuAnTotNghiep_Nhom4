package com.hotel.dao;

import com.hotel.entity.DatPhong;
import com.hotel.util.XJdbc;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatPhongDAO {
    private DatPhong read(ResultSet rs) throws SQLException {
        DatPhong dp = new DatPhong();
        dp.setId(rs.getInt("id"));
        dp.setKhachHangId(rs.getInt("khach_hang_id"));
        dp.setTenKhach(rs.getString("ten_khach"));
        dp.setPhongId(rs.getInt("phong_id"));
        dp.setSoPhong(rs.getString("so_phong"));
        dp.setNgayNhan(rs.getDate("ngay_nhan"));
        dp.setNgayTra(rs.getDate("ngay_tra"));
        dp.setTrangThai(rs.getString("trang_thai"));
        dp.setNgayTao(rs.getTimestamp("ngay_tao"));
        return dp;
    }

    private String baseSql() {
        return "SELECT dp.*, nd.ho_ten AS ten_khach, p.so_phong " +
               "FROM DatPhong dp JOIN NguoiDung nd ON dp.khach_hang_id=nd.id " +
               "JOIN Phong p ON dp.phong_id=p.id ";
    }

    public List<DatPhong> findAll() {
        List<DatPhong> list = new ArrayList<>();
        String sql = baseSql() + "ORDER BY dp.id DESC";
        try (Connection conn = XJdbc.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(read(rs));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<DatPhong> findByUser(int userId) {
        List<DatPhong> list = new ArrayList<>();
        String sql = baseSql() + "WHERE dp.khach_hang_id=? ORDER BY dp.id DESC";
        try (Connection conn = XJdbc.getConnection(); PreparedStatement ps = XJdbc.prepare(conn, sql, userId); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(read(rs));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<DatPhong> findByStatus(String status) {
        List<DatPhong> list = new ArrayList<>();
        String sql = baseSql() + "WHERE dp.trang_thai=? ORDER BY dp.id DESC";
        try (Connection conn = XJdbc.getConnection(); PreparedStatement ps = XJdbc.prepare(conn, sql, status); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(read(rs));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public DatPhong findById(int id) {
        String sql = baseSql() + "WHERE dp.id=?";
        try (Connection conn = XJdbc.getConnection(); PreparedStatement ps = XJdbc.prepare(conn, sql, id); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return read(rs);
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isRoomAvailable(int phongId, Date ngayNhan, Date ngayTra) {
        String sql = "SELECT COUNT(*) FROM DatPhong WHERE phong_id=? " +
                     "AND trang_thai NOT IN ('DA_HUY','DA_TRA_PHONG') " +
                     "AND ngay_nhan < ? AND ngay_tra > ?";
        Object value = XJdbc.value(sql, phongId, ngayTra, ngayNhan);
        return value == null || Integer.parseInt(value.toString()) == 0;
    }

    public void insert(int khachHangId, int phongId, Date ngayNhan, Date ngayTra) {
        String sql = "INSERT INTO DatPhong(khach_hang_id,phong_id,ngay_nhan,ngay_tra,trang_thai) VALUES(?,?,?,?, 'CHO_XAC_NHAN')";
        XJdbc.update(sql, khachHangId, phongId, ngayNhan, ngayTra);
    }

    public void updateStatus(int id, String status) {
        XJdbc.update("UPDATE DatPhong SET trang_thai=? WHERE id=?", status, id);
    }
}
