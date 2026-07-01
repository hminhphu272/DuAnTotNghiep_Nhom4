package com.hotel.dao;

import com.hotel.entity.Phong;
import com.hotel.util.XJdbc;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhongDAO {
    private Phong read(ResultSet rs) throws SQLException {
        Phong p = new Phong();
        p.setId(rs.getInt("id"));
        p.setSoPhong(rs.getString("so_phong"));
        p.setLoaiPhongId(rs.getInt("loai_phong_id"));
        p.setTenLoai(rs.getString("ten_loai"));
        p.setGiaPhong(rs.getBigDecimal("gia_phong"));
        p.setTrangThai(rs.getString("trang_thai"));
        p.setGhiChu(rs.getString("ghi_chu"));
        return p;
    }

    public List<Phong> findAll(String keyword) {
        List<Phong> list = new ArrayList<>();
        String sql = "SELECT p.*, lp.ten_loai FROM Phong p JOIN LoaiPhong lp ON p.loai_phong_id=lp.id " +
                     "WHERE (?='' OR p.so_phong LIKE ? OR lp.ten_loai LIKE ? OR p.trang_thai LIKE ?) ORDER BY p.id DESC";
        String k = keyword == null ? "" : keyword.trim();
        String like = "%" + k + "%";
        try (Connection conn = XJdbc.getConnection(); PreparedStatement ps = XJdbc.prepare(conn, sql, k, like, like, like); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(read(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public Phong findById(int id) {
        String sql = "SELECT p.*, lp.ten_loai FROM Phong p JOIN LoaiPhong lp ON p.loai_phong_id=lp.id WHERE p.id=?";
        try (Connection conn = XJdbc.getConnection(); PreparedStatement ps = XJdbc.prepare(conn, sql, id); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return read(rs);
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insert(String soPhong, int loaiPhongId, BigDecimal giaPhong, String trangThai, String ghiChu) {
        String sql = "INSERT INTO Phong(so_phong,loai_phong_id,gia_phong,trang_thai,ghi_chu) VALUES(?,?,?,?,?)";
        XJdbc.update(sql, soPhong, loaiPhongId, giaPhong, trangThai, ghiChu);
    }

    public void update(int id, String soPhong, int loaiPhongId, BigDecimal giaPhong, String trangThai, String ghiChu) {
        String sql = "UPDATE Phong SET so_phong=?, loai_phong_id=?, gia_phong=?, trang_thai=?, ghi_chu=? WHERE id=?";
        XJdbc.update(sql, soPhong, loaiPhongId, giaPhong, trangThai, ghiChu, id);
    }

    public void delete(int id) {
        XJdbc.update("DELETE FROM Phong WHERE id=?", id);
    }

    public void updateStatus(int id, String status) {
        XJdbc.update("UPDATE Phong SET trang_thai=? WHERE id=?", status, id);
    }

    public boolean hasBooking(int id) {
        Object value = XJdbc.value("SELECT COUNT(*) FROM DatPhong WHERE phong_id=?", id);
        return value != null && Integer.parseInt(value.toString()) > 0;
    }
}
