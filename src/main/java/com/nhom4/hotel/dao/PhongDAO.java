package com.nhom4.hotel.dao;

import com.nhom4.hotel.entity.Phong;
import com.nhom4.hotel.util.DateUtil;
import com.nhom4.hotel.util.JdbcUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PhongDAO implements CrudDAO<Phong> {
    private Phong map(ResultSet rs) throws Exception {
        Phong item = new Phong();
        item.setId(rs.getInt("id"));
        item.setSoPhong(rs.getString("so_phong"));
        item.setLoaiPhongId(rs.getInt("loai_phong_id"));
        item.setGiaPhong(rs.getBigDecimal("gia_phong"));
        item.setTrangThai(rs.getString("trang_thai"));
        item.setGhiChu(rs.getString("ghi_chu"));
        try { item.setTenLoai(rs.getString("ten_loai")); } catch (Exception ignored) {}
        try { item.setMoTaLoai(rs.getString("mo_ta")); } catch (Exception ignored) {}
        try { item.setSucChua(rs.getInt("suc_chua")); } catch (Exception ignored) {}
        return item;
    }

    private String baseSql() {
        return "SELECT p.*, l.ten_loai, l.mo_ta, l.suc_chua FROM Phong p JOIN LoaiPhong l ON p.loai_phong_id=l.id";
    }

    @Override
    public List<Phong> findAll() {
        return search(null, null, null);
    }

    public List<Phong> search(String keyword, Integer loaiId, String trangThai) {
        List<Phong> list = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder(baseSql() + " WHERE 1=1");
        if (keyword != null && !keyword.isBlank()) {
            sql.append(" AND (p.so_phong LIKE ? OR l.ten_loai LIKE ? OR p.ghi_chu LIKE ?)");
            String kw = "%" + keyword.trim() + "%";
            params.add(kw); params.add(kw); params.add(kw);
        }
        if (loaiId != null && loaiId > 0) {
            sql.append(" AND p.loai_phong_id=?");
            params.add(loaiId);
        }
        if (trangThai != null && !trangThai.isBlank()) {
            sql.append(" AND p.trang_thai=?");
            params.add(trangThai);
        }
        sql.append(" ORDER BY p.so_phong");
        try (Connection conn = JdbcUtil.getConnection(); PreparedStatement ps = JdbcUtil.prepare(conn, sql.toString(), params.toArray()); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<Phong> findAvailable(LocalDate ngayNhan, LocalDate ngayTra, Integer loaiId) {
        List<Phong> list = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder(baseSql());
        sql.append(" WHERE p.trang_thai=N'Trống' AND l.trang_thai=1");
        if (loaiId != null && loaiId > 0) {
            sql.append(" AND p.loai_phong_id=?");
            params.add(loaiId);
        }
        if (ngayNhan != null && ngayTra != null) {
            sql.append(" AND NOT EXISTS (SELECT 1 FROM DatPhong dp WHERE dp.phong_id=p.id");
            sql.append(" AND dp.trang_thai IN (N'Chờ xác nhận',N'Đã xác nhận',N'Đã nhận phòng')");
            sql.append(" AND dp.ngay_nhan < ? AND dp.ngay_tra > ?)");
            params.add(DateUtil.sqlDate(ngayTra));
            params.add(DateUtil.sqlDate(ngayNhan));
        }
        sql.append(" ORDER BY p.gia_phong, p.so_phong");
        try (Connection conn = JdbcUtil.getConnection(); PreparedStatement ps = JdbcUtil.prepare(conn, sql.toString(), params.toArray()); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Phong findById(int id) {
        String sql = baseSql() + " WHERE p.id=?";
        try (Connection conn = JdbcUtil.getConnection(); PreparedStatement ps = JdbcUtil.prepare(conn, sql, id); ResultSet rs = ps.executeQuery()) {
            return rs.next() ? map(rs) : null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(Phong e) {
        String sql = "INSERT INTO Phong(so_phong,loai_phong_id,gia_phong,trang_thai,ghi_chu) VALUES(?,?,?,?,?)";
        JdbcUtil.update(sql, e.getSoPhong(), e.getLoaiPhongId(), e.getGiaPhong(), e.getTrangThai(), e.getGhiChu());
    }

    @Override
    public void update(Phong e) {
        String sql = "UPDATE Phong SET so_phong=?, loai_phong_id=?, gia_phong=?, trang_thai=?, ghi_chu=? WHERE id=?";
        JdbcUtil.update(sql, e.getSoPhong(), e.getLoaiPhongId(), e.getGiaPhong(), e.getTrangThai(), e.getGhiChu(), e.getId());
    }

    @Override
    public void delete(int id) {
        JdbcUtil.update("DELETE FROM Phong WHERE id=?", id);
    }

    public void updateStatus(int id, String status) {
        JdbcUtil.update("UPDATE Phong SET trang_thai=? WHERE id=?", status, id);
    }
}
