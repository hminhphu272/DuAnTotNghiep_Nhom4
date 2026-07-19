package com.nhom4.hotel.dao;

import com.nhom4.hotel.entity.HoaDon;
import com.nhom4.hotel.util.JdbcUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO implements CrudDAO<HoaDon> {

    private HoaDon map(ResultSet rs) throws Exception {
        HoaDon item = new HoaDon();
        item.setId(rs.getInt("id"));
        item.setDatPhongId(rs.getInt("dat_phong_id"));
        int nhanVienId = rs.getInt("nhan_vien_id");
        item.setNhanVienId(rs.wasNull() ? null : nhanVienId);
        item.setNgayLap(rs.getTimestamp("ngay_lap"));
        item.setTongTien(rs.getBigDecimal("tong_tien"));
        item.setPhuongThuc(rs.getString("phuong_thuc"));
        item.setTrangThai(rs.getString("trang_thai"));
        try { item.setTenNhanVien(rs.getString("ten_nhan_vien")); } catch (Exception ignored) {}
        try { item.setTenKhach(rs.getString("ten_khach")); } catch (Exception ignored) {}
        try { item.setSoPhong(rs.getString("so_phong")); } catch (Exception ignored) {}
        return item;
    }

    private String baseSql() {
        return "SELECT hd.*, nv.ho_ten AS ten_nhan_vien, kh.ho_ten AS ten_khach, p.so_phong " +
               "FROM HoaDon hd " +
               "JOIN DatPhong dp ON hd.dat_phong_id = dp.id " +
               "JOIN NguoiDung kh ON dp.khach_hang_id = kh.id " +
               "JOIN Phong p ON dp.phong_id = p.id " +
               "LEFT JOIN NguoiDung nv ON hd.nhan_vien_id = nv.id";
    }

    @Override
    public List<HoaDon> findAll() {
        return search(null, null);
    }

    /** Danh sách hóa đơn cho trang thanh toán (lọc theo từ khóa + trạng thái). */
    public List<HoaDon> search(String keyword, String trangThai) {
        List<HoaDon> list = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder(baseSql() + " WHERE 1=1");
        if (keyword != null && !keyword.isBlank()) {
            sql.append(" AND (kh.ho_ten LIKE ? OR p.so_phong LIKE ?)");
            String kw = "%" + keyword.trim() + "%";
            params.add(kw); params.add(kw);
        }
        if (trangThai != null && !trangThai.isBlank()) {
            sql.append(" AND hd.trang_thai = ?");
            params.add(trangThai);
        }
        sql.append(" ORDER BY hd.ngay_lap DESC");
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement ps = JdbcUtil.prepare(conn, sql.toString(), params.toArray());
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public HoaDon findById(int id) {
        String sql = baseSql() + " WHERE hd.id = ?";
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement ps = JdbcUtil.prepare(conn, sql, id);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? map(rs) : null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** Kiểm tra 1 đơn đặt phòng đã được lập hóa đơn (trả phòng) hay chưa. */
    public HoaDon findByDatPhongId(int datPhongId) {
        String sql = baseSql() + " WHERE hd.dat_phong_id = ?";
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement ps = JdbcUtil.prepare(conn, sql, datPhongId);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? map(rs) : null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(HoaDon e) {
        String sql = "INSERT INTO HoaDon(dat_phong_id, nhan_vien_id, tong_tien, phuong_thuc, trang_thai) " +
                     "VALUES(?,?,?,?,?)";
        JdbcUtil.update(sql, e.getDatPhongId(), e.getNhanVienId(), e.getTongTien(), e.getPhuongThuc(), e.getTrangThai());
    }

    @Override
    public void update(HoaDon e) {
        String sql = "UPDATE HoaDon SET tong_tien=?, phuong_thuc=?, trang_thai=? WHERE id=?";
        JdbcUtil.update(sql, e.getTongTien(), e.getPhuongThuc(), e.getTrangThai(), e.getId());
    }

    /** Xác nhận đã thu tiền cho 1 hóa đơn. */
    public void markPaid(int id, String phuongThuc) {
        JdbcUtil.update("UPDATE HoaDon SET trang_thai=N'Đã thanh toán', phuong_thuc=? WHERE id=?", phuongThuc, id);
    }

    @Override
    public void delete(int id) {
        JdbcUtil.update("DELETE FROM HoaDon WHERE id=?", id);
    }
}