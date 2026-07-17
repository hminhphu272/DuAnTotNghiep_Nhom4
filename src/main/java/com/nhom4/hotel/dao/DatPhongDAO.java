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

public class DatPhongDAO implements CrudDAO<DatPhong> {

    private DatPhong map(ResultSet rs) throws Exception {
        DatPhong item = new DatPhong();
        item.setId(rs.getInt("id"));
        item.setKhachHangId(rs.getInt("khach_hang_id"));
        item.setPhongId(rs.getInt("phong_id"));
        item.setNgayNhan(rs.getDate("ngay_nhan"));
        item.setNgayTra(rs.getDate("ngay_tra"));
        item.setTrangThai(rs.getString("trang_thai"));
        item.setNgayTao(rs.getTimestamp("ngay_tao"));
        try { item.setHoTenKhach(rs.getString("ho_ten_khach")); } catch (Exception ignored) {}
        try { item.setEmailKhach(rs.getString("email_khach")); } catch (Exception ignored) {}
        try { item.setSoPhong(rs.getString("so_phong")); } catch (Exception ignored) {}
        try { item.setTenLoai(rs.getString("ten_loai")); } catch (Exception ignored) {}
        try { item.setGiaPhong(rs.getBigDecimal("gia_phong")); } catch (Exception ignored) {}
        return item;
    }

    private String baseSql() {
        return "SELECT dp.*, kh.ho_ten AS ho_ten_khach, kh.email AS email_khach, " +
               "p.so_phong, p.gia_phong, l.ten_loai " +
               "FROM DatPhong dp " +
               "JOIN NguoiDung kh ON dp.khach_hang_id = kh.id " +
               "JOIN Phong p ON dp.phong_id = p.id " +
               "JOIN LoaiPhong l ON p.loai_phong_id = l.id";
    }

    @Override
    public List<DatPhong> findAll() {
        return search(null, null);
    }

    /** Danh sách đặt phòng cho trang quản lý (lọc theo từ khóa + trạng thái). */
    public List<DatPhong> search(String keyword, String trangThai) {
        List<DatPhong> list = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder(baseSql() + " WHERE 1=1");
        if (keyword != null && !keyword.isBlank()) {
            sql.append(" AND (kh.ho_ten LIKE ? OR kh.email LIKE ? OR p.so_phong LIKE ?)");
            String kw = "%" + keyword.trim() + "%";
            params.add(kw); params.add(kw); params.add(kw);
        }
        if (trangThai != null && !trangThai.isBlank()) {
            sql.append(" AND dp.trang_thai = ?");
            params.add(trangThai);
        }
        sql.append(" ORDER BY dp.ngay_tao DESC");
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement ps = JdbcUtil.prepare(conn, sql.toString(), params.toArray());
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    /** Danh sách đặt phòng của riêng 1 khách hàng (trang "Đặt phòng của tôi"). */
    public List<DatPhong> findByKhachHang(int khachHangId) {
        String sql = baseSql() + " WHERE dp.khach_hang_id = ? ORDER BY dp.ngay_tao DESC";
        List<DatPhong> list = new ArrayList<>();
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement ps = JdbcUtil.prepare(conn, sql, khachHangId);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public DatPhong findById(int id) {
        String sql = baseSql() + " WHERE dp.id = ?";
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement ps = JdbcUtil.prepare(conn, sql, id);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? map(rs) : null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** Kiểm tra phòng đã có người đặt trùng khoảng ngày chưa (chỉ tính các đơn còn "sống"). */
    public boolean hasOverlap(int phongId, LocalDate ngayNhan, LocalDate ngayTra, Integer excludeId) {
        StringBuilder sql = new StringBuilder(
            "SELECT COUNT(*) FROM DatPhong WHERE phong_id = ? " +
            "AND trang_thai IN (N'Chờ xác nhận', N'Đã xác nhận', N'Đã nhận phòng') " +
            "AND ngay_nhan < ? AND ngay_tra > ?");
        List<Object> params = new ArrayList<>();
        params.add(phongId);
        params.add(DateUtil.sqlDate(ngayTra));
        params.add(DateUtil.sqlDate(ngayNhan));
        if (excludeId != null) {
            sql.append(" AND id <> ?");
            params.add(excludeId);
        }
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement ps = JdbcUtil.prepare(conn, sql.toString(), params.toArray());
             ResultSet rs = ps.executeQuery()) {
            return rs.next() && rs.getInt(1) > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(DatPhong e) {
        String sql = "INSERT INTO DatPhong(khach_hang_id, phong_id, ngay_nhan, ngay_tra, trang_thai, ngay_tao) " +
                     "VALUES(?,?,?,?,?, GETDATE())";
        JdbcUtil.update(sql, e.getKhachHangId(), e.getPhongId(), e.getNgayNhan(), e.getNgayTra(), e.getTrangThai());
    }

    @Override
    public void update(DatPhong e) {
        String sql = "UPDATE DatPhong SET ngay_nhan=?, ngay_tra=?, trang_thai=? WHERE id=?";
        JdbcUtil.update(sql, e.getNgayNhan(), e.getNgayTra(), e.getTrangThai(), e.getId());
    }

    public void updateStatus(int id, String trangThai) {
        JdbcUtil.update("UPDATE DatPhong SET trang_thai=? WHERE id=?", trangThai, id);
    }

    @Override
    public void delete(int id) {
        JdbcUtil.update("DELETE FROM DatPhong WHERE id=?", id);
    }
}