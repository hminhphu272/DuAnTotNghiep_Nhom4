package com.hotel.dao;

import com.hotel.entity.HoaDon;
import com.hotel.util.XJdbc;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HoaDonDAO {
    private HoaDon read(ResultSet rs) throws SQLException {
        HoaDon hd = new HoaDon();
        hd.setId(rs.getInt("id"));
        hd.setDatPhongId(rs.getInt("dat_phong_id"));
        int nv = rs.getInt("nhan_vien_id");
        hd.setNhanVienId(rs.wasNull() ? null : nv);
        hd.setNgayLap(rs.getTimestamp("ngay_lap"));
        hd.setTongTien(rs.getBigDecimal("tong_tien"));
        hd.setPhuongThuc(rs.getString("phuong_thuc"));
        hd.setTrangThai(rs.getString("trang_thai"));
        hd.setSoPhong(rs.getString("so_phong"));
        hd.setTenKhach(rs.getString("ten_khach"));
        return hd;
    }

    public void insert(int datPhongId, int nhanVienId, BigDecimal tongTien, String phuongThuc) {
        String sql = "INSERT INTO HoaDon(dat_phong_id,nhan_vien_id,tong_tien,phuong_thuc,trang_thai) VALUES(?,?,?,?, 'DA_THANH_TOAN')";
        XJdbc.update(sql, datPhongId, nhanVienId, tongTien, phuongThuc);
    }

    public HoaDon findByDatPhongId(int datPhongId) {
        String sql = "SELECT hd.*, p.so_phong, nd.ho_ten AS ten_khach " +
                     "FROM HoaDon hd JOIN DatPhong dp ON hd.dat_phong_id=dp.id " +
                     "JOIN Phong p ON dp.phong_id=p.id JOIN NguoiDung nd ON dp.khach_hang_id=nd.id " +
                     "WHERE hd.dat_phong_id=?";
        try (Connection conn = XJdbc.getConnection(); PreparedStatement ps = XJdbc.prepare(conn, sql, datPhongId); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return read(rs);
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
