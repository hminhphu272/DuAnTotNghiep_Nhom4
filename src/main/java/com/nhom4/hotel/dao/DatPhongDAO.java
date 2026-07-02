package com.nhom4.hotel.dao;

import com.nhom4.hotel.entity.DatPhong;
import com.nhom4.hotel.util.DateUtil;
import com.nhom4.hotel.util.JdbcUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatPhongDAO {
    private DatPhong map(ResultSet rs) throws Exception {
        DatPhong item = new DatPhong();
        item.setId(rs.getInt("id"));
        item.setKhachHangId(rs.getInt("khach_hang_id"));
        item.setPhongId(rs.getInt("phong_id"));
        item.setNgayNhan(rs.getDate("ngay_nhan"));
        item.setNgayTra(rs.getDate("ngay_tra"));
        item.setTrangThai(rs.getString("trang_thai"));
        item.setNgayTao(rs.getTimestamp("ngay_tao"));
        try { item.setHoTenKhach(rs.getString("ho_ten")); } catch (Exception ignored) {}
        try { item.setEmailKhach(rs.getString("email")); } catch (Exception ignored) {}
        try { item.setSoPhong(rs.getString("so_phong")); } catch (Exception ignored) {}
        try { item.setTenLoai(rs.getString("ten_loai")); } catch (Exception ignored) {}
        try { item.setGiaPhong(rs.getBigDecimal("gia_phong")); } catch (Exception ignored) {}
        return item;
    }

    private String baseSql() {
        return "SELECT dp.*, nd.ho_ten, nd.email, p.so_phong, p.gia_phong, l.ten_loai " +
               "FROM DatPhong dp JOIN NguoiDung nd ON dp.khach_hang_id=nd.id " +
               "JOIN Phong p ON dp.phong_id=p.id JOIN LoaiPhong l ON p.loai_phong_id=l.id";
    }

    public void insert(DatPhong e) {
        String sql = "INSERT INTO DatPhong(khach_hang_id,phong_id,ngay_nhan,ngay_tra,trang_thai,ngay_tao) VALUES(?,?,?,?,N'Chờ xác nhận',GETDATE())";
        JdbcUtil.update(sql, e.getKhachHangId(), e.getPhongId(), e.getNgayNhan(), e.getNgayTra());
    }

    public boolean hasOverlap(int phongId, LocalDate ngayNhan, LocalDate ngayTra) {
        String sql = "SELECT COUNT(*) FROM DatPhong WHERE phong_id=? AND trang_thai IN (N'Chờ xác nhận',N'Đã xác nhận',N'Đã nhận phòng') AND ngay_nhan < ? AND ngay_tra > ?";
        try (Connection conn = JdbcUtil.getConnection(); PreparedStatement ps = JdbcUtil.prepare(conn, sql, phongId, DateUtil.sqlDate(ngayTra), DateUtil.sqlDate(ngayNhan)); ResultSet rs = ps.executeQuery()) {
            return rs.next() && rs.getInt(1) > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<DatPhong> findByCustomer(int customerId) {
        List<DatPhong> list = new ArrayList<>();
        String sql = baseSql() + " WHERE dp.khach_hang_id=? ORDER BY dp.ngay_tao DESC";
        try (Connection conn = JdbcUtil.getConnection(); PreparedStatement ps = JdbcUtil.prepare(conn, sql, customerId); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<DatPhong> findAll(String keyword, String status) {
        List<DatPhong> list = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder(baseSql() + " WHERE 1=1");
        if (keyword != null && !keyword.isBlank()) {
            sql.append(" AND (nd.ho_ten LIKE ? OR nd.email LIKE ? OR p.so_phong LIKE ?)");
            String kw = "%" + keyword.trim() + "%";
            params.add(kw); params.add(kw); params.add(kw);
        }
        if (status != null && !status.isBlank()) {
            sql.append(" AND dp.trang_thai=?");
            params.add(status);
        }
        sql.append(" ORDER BY dp.ngay_tao DESC");
        try (Connection conn = JdbcUtil.getConnection(); PreparedStatement ps = JdbcUtil.prepare(conn, sql.toString(), params.toArray()); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public DatPhong findById(int id) {
        String sql = baseSql() + " WHERE dp.id=?";
        try (Connection conn = JdbcUtil.getConnection(); PreparedStatement ps = JdbcUtil.prepare(conn, sql, id); ResultSet rs = ps.executeQuery()) {
            return rs.next() ? map(rs) : null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateStatus(int id, String status) {
        JdbcUtil.update("UPDATE DatPhong SET trang_thai=? WHERE id=?", status, id);
    }
}
