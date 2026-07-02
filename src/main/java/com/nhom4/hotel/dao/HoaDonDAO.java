package com.nhom4.hotel.dao;

import com.nhom4.hotel.entity.HoaDon;
import com.nhom4.hotel.util.JdbcUtil;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO {
    private HoaDon map(ResultSet rs) throws Exception {
        HoaDon item = new HoaDon();
        item.setId(rs.getInt("id"));
        item.setDatPhongId(rs.getInt("dat_phong_id"));
        int nv = rs.getInt("nhan_vien_id");
        item.setNhanVienId(rs.wasNull() ? null : nv);
        item.setNgayLap(rs.getTimestamp("ngay_lap"));
        item.setTongTien(rs.getBigDecimal("tong_tien"));
        item.setPhuongThuc(rs.getString("phuong_thuc"));
        item.setTrangThai(rs.getString("trang_thai"));
        try { item.setTenNhanVien(rs.getString("ten_nhan_vien")); } catch (Exception ignored) {}
        try { item.setTenKhach(rs.getString("ten_khach")); } catch (Exception ignored) {}
        try { item.setSoPhong(rs.getString("so_phong")); } catch (Exception ignored) {}
        return item;
    }

    public void insert(HoaDon e) {
        String sql = "INSERT INTO HoaDon(dat_phong_id,nhan_vien_id,ngay_lap,tong_tien,phuong_thuc,trang_thai) VALUES(?,?,GETDATE(),?,?,N'Đã thanh toán')";
        JdbcUtil.update(sql, e.getDatPhongId(), e.getNhanVienId(), e.getTongTien(), e.getPhuongThuc());
    }

    public HoaDon findByDatPhongId(int datPhongId) {
        String sql = "SELECT hd.*, nv.ho_ten AS ten_nhan_vien, kh.ho_ten AS ten_khach, p.so_phong " +
                "FROM HoaDon hd LEFT JOIN NguoiDung nv ON hd.nhan_vien_id=nv.id " +
                "JOIN DatPhong dp ON hd.dat_phong_id=dp.id JOIN NguoiDung kh ON dp.khach_hang_id=kh.id JOIN Phong p ON dp.phong_id=p.id " +
                "WHERE hd.dat_phong_id=?";
        try (Connection conn = JdbcUtil.getConnection(); PreparedStatement ps = JdbcUtil.prepare(conn, sql, datPhongId); ResultSet rs = ps.executeQuery()) {
            return rs.next() ? map(rs) : null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<HoaDon> findAll() {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT hd.*, nv.ho_ten AS ten_nhan_vien, kh.ho_ten AS ten_khach, p.so_phong " +
                "FROM HoaDon hd LEFT JOIN NguoiDung nv ON hd.nhan_vien_id=nv.id " +
                "JOIN DatPhong dp ON hd.dat_phong_id=dp.id JOIN NguoiDung kh ON dp.khach_hang_id=kh.id JOIN Phong p ON dp.phong_id=p.id " +
                "ORDER BY hd.ngay_lap DESC";
        try (Connection conn = JdbcUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public BigDecimal revenue(LocalDate from, LocalDate to) {
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT COALESCE(SUM(tong_tien),0) FROM HoaDon WHERE trang_thai=N'Đã thanh toán'");
        if (from != null) { sql.append(" AND CAST(ngay_lap AS DATE)>=?"); params.add(java.sql.Date.valueOf(from)); }
        if (to != null) { sql.append(" AND CAST(ngay_lap AS DATE)<=?"); params.add(java.sql.Date.valueOf(to)); }
        try (Connection conn = JdbcUtil.getConnection(); PreparedStatement ps = JdbcUtil.prepare(conn, sql.toString(), params.toArray()); ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getBigDecimal(1) : BigDecimal.ZERO;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
