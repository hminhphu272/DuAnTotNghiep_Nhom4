package com.hotel.dao;

import com.hotel.entity.NguoiDung;
import com.hotel.util.XJdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NguoiDungDAO {
    private NguoiDung read(ResultSet rs) throws SQLException {
        return new NguoiDung(
            rs.getInt("id"),
            rs.getString("ho_ten"),
            rs.getString("email"),
            rs.getString("mat_khau"),
            rs.getString("so_dien_thoai"),
            rs.getString("vai_tro"),
            rs.getBoolean("trang_thai")
        );
    }

    public NguoiDung login(String email, String matKhau) {
        String sql = "SELECT * FROM NguoiDung WHERE email=? AND mat_khau=? AND trang_thai=1";
        try (Connection conn = XJdbc.getConnection(); PreparedStatement ps = XJdbc.prepare(conn, sql, email, matKhau); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return read(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existsEmail(String email) {
        Object value = XJdbc.value("SELECT COUNT(*) FROM NguoiDung WHERE email=?", email);
        return value != null && Integer.parseInt(value.toString()) > 0;
    }

    public void register(String hoTen, String email, String matKhau, String soDienThoai) {
        String sql = "INSERT INTO NguoiDung(ho_ten,email,mat_khau,so_dien_thoai,vai_tro,trang_thai) VALUES(?,?,?,?, 'KHACH_HANG', 1)";
        XJdbc.update(sql, hoTen, email, matKhau, soDienThoai);
    }
}
