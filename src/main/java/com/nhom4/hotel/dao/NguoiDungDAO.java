package com.nhom4.hotel.dao;

import com.nhom4.hotel.entity.NguoiDung;
import com.nhom4.hotel.util.JdbcUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NguoiDungDAO {
    private NguoiDung map(ResultSet rs) throws Exception {
        NguoiDung user = new NguoiDung();
        user.setId(rs.getInt("id"));
        user.setHoTen(rs.getString("ho_ten"));
        user.setEmail(rs.getString("email"));
        user.setMatKhau(rs.getString("mat_khau"));
        user.setSoDienThoai(rs.getString("so_dien_thoai"));
        user.setVaiTro(rs.getString("vai_tro"));
        user.setTrangThai(rs.getBoolean("trang_thai"));
        return user;
    }

    public NguoiDung authenticate(String email, String password) {
        String sql = "SELECT * FROM NguoiDung WHERE email=? AND mat_khau=? AND trang_thai=1";
        try (Connection conn = JdbcUtil.getConnection(); PreparedStatement ps = JdbcUtil.prepare(conn, sql, email, password); ResultSet rs = ps.executeQuery()) {
            return rs.next() ? map(rs) : null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public NguoiDung findByEmail(String email) {
        String sql = "SELECT * FROM NguoiDung WHERE email=?";
        try (Connection conn = JdbcUtil.getConnection(); PreparedStatement ps = JdbcUtil.prepare(conn, sql, email); ResultSet rs = ps.executeQuery()) {
            return rs.next() ? map(rs) : null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public NguoiDung findById(int id) {
        String sql = "SELECT * FROM NguoiDung WHERE id=?";
        try (Connection conn = JdbcUtil.getConnection(); PreparedStatement ps = JdbcUtil.prepare(conn, sql, id); ResultSet rs = ps.executeQuery()) {
            return rs.next() ? map(rs) : null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<NguoiDung> findAll() {
        List<NguoiDung> list = new ArrayList<>();
        String sql = "SELECT * FROM NguoiDung ORDER BY id DESC";
        try (Connection conn = JdbcUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void insertCustomer(NguoiDung user) {
        String sql = "INSERT INTO NguoiDung(ho_ten,email,mat_khau,so_dien_thoai,vai_tro,trang_thai) VALUES(?,?,?,?,N'Khách hàng',1)";
        JdbcUtil.update(sql, user.getHoTen(), user.getEmail(), user.getMatKhau(), user.getSoDienThoai());
    }
    
 // 1. Lấy danh sách tất cả người dùng
    public List<NguoiDung> getAll() {
        List<NguoiDung> list = new ArrayList<>();
        String sql = "SELECT * FROM NguoiDung";
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                NguoiDung nd = new NguoiDung();
                nd.setId(rs.getInt("id"));
                nd.setHoTen(rs.getString("ho_ten")); // Chú ý sửa đúng tên setter trong Entity của bạn
                nd.setEmail(rs.getString("email"));
                nd.setMatKhau(rs.getString("mat_khau"));
                nd.setSoDienThoai(rs.getString("so_dien_thoai"));
                nd.setVaiTro(rs.getString("vai_tro"));
                nd.setTrangThai(rs.getBoolean("trang_thai"));
                list.add(nd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean add(NguoiDung nd) {
        String sql = "INSERT INTO NguoiDung (ho_ten, email, mat_khau, so_dien_thoai, vai_tro, trang_thai) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nd.getHoTen());
            ps.setString(2, nd.getEmail());
            ps.setString(3, nd.getMatKhau());
            ps.setString(4, nd.getSoDienThoai());
            ps.setString(5, nd.getVaiTro());
            ps.setBoolean(6, nd.isTrangThai()); // Hoặc mặc định truyền true/1 tùy logic
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(NguoiDung nd) {
        String sql = "UPDATE NguoiDung SET ho_ten = ?, email = ?, vai_tro = ? WHERE id = ?";
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nd.getHoTen());
            ps.setString(2, nd.getEmail());
            ps.setString(3, nd.getVaiTro());
            ps.setInt(4, nd.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM NguoiDung WHERE id = ?";
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
