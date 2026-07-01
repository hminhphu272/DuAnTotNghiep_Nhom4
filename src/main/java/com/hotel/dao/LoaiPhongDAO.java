package com.hotel.dao;

import com.hotel.entity.LoaiPhong;
import com.hotel.util.XJdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoaiPhongDAO {
    private LoaiPhong read(ResultSet rs) throws SQLException {
        LoaiPhong lp = new LoaiPhong();
        lp.setId(rs.getInt("id"));
        lp.setTenLoai(rs.getString("ten_loai"));
        lp.setMoTa(rs.getString("mo_ta"));
        lp.setSucChua(rs.getInt("suc_chua"));
        lp.setGiaCoBan(rs.getBigDecimal("gia_co_ban"));
        lp.setTrangThai(rs.getBoolean("trang_thai"));
        return lp;
    }

    public List<LoaiPhong> findAll() {
        List<LoaiPhong> list = new ArrayList<>();
        String sql = "SELECT * FROM LoaiPhong ORDER BY id DESC";
        try (Connection conn = XJdbc.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(read(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void insert(String tenLoai, String moTa, int sucChua, java.math.BigDecimal giaCoBan) {
        String sql = "INSERT INTO LoaiPhong(ten_loai,mo_ta,suc_chua,gia_co_ban,trang_thai) VALUES(?,?,?,?,1)";
        XJdbc.update(sql, tenLoai, moTa, sucChua, giaCoBan);
    }
}
