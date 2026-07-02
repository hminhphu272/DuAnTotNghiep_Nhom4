package com.nhom4.hotel.dao;

import com.nhom4.hotel.entity.LoaiPhong;
import com.nhom4.hotel.util.JdbcUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LoaiPhongDAO implements CrudDAO<LoaiPhong> {
    private LoaiPhong map(ResultSet rs) throws Exception {
        LoaiPhong item = new LoaiPhong();
        item.setId(rs.getInt("id"));
        item.setTenLoai(rs.getString("ten_loai"));
        item.setMoTa(rs.getString("mo_ta"));
        item.setSucChua(rs.getInt("suc_chua"));
        item.setGiaCoBan(rs.getBigDecimal("gia_co_ban"));
        item.setTrangThai(rs.getBoolean("trang_thai"));
        return item;
    }

    @Override
    public List<LoaiPhong> findAll() {
        List<LoaiPhong> list = new ArrayList<>();
        String sql = "SELECT * FROM LoaiPhong ORDER BY id DESC";
        try (Connection conn = JdbcUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<LoaiPhong> findActive() {
        List<LoaiPhong> list = new ArrayList<>();
        String sql = "SELECT * FROM LoaiPhong WHERE trang_thai=1 ORDER BY ten_loai";
        try (Connection conn = JdbcUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public LoaiPhong findById(int id) {
        String sql = "SELECT * FROM LoaiPhong WHERE id=?";
        try (Connection conn = JdbcUtil.getConnection(); PreparedStatement ps = JdbcUtil.prepare(conn, sql, id); ResultSet rs = ps.executeQuery()) {
            return rs.next() ? map(rs) : null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(LoaiPhong e) {
        String sql = "INSERT INTO LoaiPhong(ten_loai,mo_ta,suc_chua,gia_co_ban,trang_thai) VALUES(?,?,?,?,?)";
        JdbcUtil.update(sql, e.getTenLoai(), e.getMoTa(), e.getSucChua(), e.getGiaCoBan(), e.isTrangThai());
    }

    @Override
    public void update(LoaiPhong e) {
        String sql = "UPDATE LoaiPhong SET ten_loai=?, mo_ta=?, suc_chua=?, gia_co_ban=?, trang_thai=? WHERE id=?";
        JdbcUtil.update(sql, e.getTenLoai(), e.getMoTa(), e.getSucChua(), e.getGiaCoBan(), e.isTrangThai(), e.getId());
    }

    @Override
    public void delete(int id) {
        JdbcUtil.update("UPDATE LoaiPhong SET trang_thai=0 WHERE id=?", id);
    }
}
