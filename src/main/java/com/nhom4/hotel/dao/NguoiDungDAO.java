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
        NguoiDung nd = new NguoiDung();

        nd.setId(rs.getInt("id"));
        nd.setHoTen(rs.getString("ho_ten"));
        nd.setEmail(rs.getString("email"));
        nd.setMatKhau(rs.getString("mat_khau"));
        nd.setSoDienThoai(rs.getString("so_dien_thoai"));
        nd.setVaiTro(rs.getString("vai_tro"));
        nd.setTrangThai(rs.getBoolean("trang_thai"));

        return nd;
    }

    public NguoiDung authenticate(String email, String password) {

        String sql = """
                SELECT *
                FROM NguoiDung
                WHERE email=?
                AND mat_khau=?
                AND trang_thai=1
                """;

        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return map(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public NguoiDung findByEmail(String email) {

        String sql = "SELECT * FROM NguoiDung WHERE email=?";

        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return map(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public NguoiDung findById(int id) {

        String sql = "SELECT * FROM NguoiDung WHERE id=?";

        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return map(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<NguoiDung> getAll() {

        List<NguoiDung> list = new ArrayList<>();

        String sql = """
                SELECT *
                FROM NguoiDung
                ORDER BY id DESC
                """;

        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean add(NguoiDung nd) {

        try {

            if (findByEmail(nd.getEmail()) != null) {
                System.out.println("Email đã tồn tại.");
                return false;
            }

            String sql = """
                    INSERT INTO NguoiDung
                    (
                        ho_ten,
                        email,
                        mat_khau,
                        so_dien_thoai,
                        vai_tro,
                        trang_thai
                    )
                    VALUES
                    (
                        ?,?,?,?,?,?
                    )
                    """;

            try (Connection conn = JdbcUtil.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, nd.getHoTen());
                ps.setString(2, nd.getEmail());
                ps.setString(3, nd.getMatKhau());
                ps.setString(4, nd.getSoDienThoai());
                ps.setString(5, nd.getVaiTro());
                ps.setBoolean(6, nd.isTrangThai());

                int rows = ps.executeUpdate();

                System.out.println("Insert rows = " + rows);

                return rows > 0;
            }

        } catch (Exception e) {

            System.out.println("===== ADD USER ERROR =====");
            e.printStackTrace();

            return false;
        }
    }

    public boolean update(NguoiDung nd) {

        String sql = """
                UPDATE NguoiDung
                SET
                    ho_ten=?,
                    email=?,
                    mat_khau=?,
                    so_dien_thoai=?,
                    vai_tro=?,
                    trang_thai=?
                WHERE id=?
                """;

        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nd.getHoTen());
            ps.setString(2, nd.getEmail());
            ps.setString(3, nd.getMatKhau());
            ps.setString(4, nd.getSoDienThoai());
            ps.setString(5, nd.getVaiTro());
            ps.setBoolean(6, nd.isTrangThai());
            ps.setInt(7, nd.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println("===== UPDATE USER ERROR =====");
            e.printStackTrace();

            return false;
        }
    }

    public boolean delete(int id) {

        String sql = "DELETE FROM NguoiDung WHERE id=?";

        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println("===== DELETE USER ERROR =====");
            e.printStackTrace();

            return false;
        }
    }

    public List<NguoiDung> searchByNameOrEmail(String keyword) {

        List<NguoiDung> list = new ArrayList<>();

        String sql = """
                SELECT *
                FROM NguoiDung
                WHERE ho_ten LIKE ?
                OR email LIKE ?
                ORDER BY id DESC
                """;

        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String key = "%" + keyword + "%";

            ps.setString(1, key);
            ps.setString(2, key);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void insertCustomer(NguoiDung user) {

        String sql = """
                INSERT INTO NguoiDung
                (
                    ho_ten,
                    email,
                    mat_khau,
                    so_dien_thoai,
                    vai_tro,
                    trang_thai
                )
                VALUES
                (
                    ?,?,?,?,N'Khách hàng',1
                )
                """;

        JdbcUtil.update(
                sql,
                user.getHoTen(),
                user.getEmail(),
                user.getMatKhau(),
                user.getSoDienThoai()
        );
    }

}