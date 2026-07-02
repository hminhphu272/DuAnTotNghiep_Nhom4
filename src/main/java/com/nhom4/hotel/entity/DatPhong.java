package com.nhom4.hotel.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class DatPhong {
    private int id;
    private int khachHangId;
    private int phongId;
    private Date ngayNhan;
    private Date ngayTra;
    private String trangThai;
    private Timestamp ngayTao;
    private String hoTenKhach;
    private String emailKhach;
    private String soPhong;
    private String tenLoai;
    private BigDecimal giaPhong;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getKhachHangId() { return khachHangId; }
    public void setKhachHangId(int khachHangId) { this.khachHangId = khachHangId; }
    public int getPhongId() { return phongId; }
    public void setPhongId(int phongId) { this.phongId = phongId; }
    public Date getNgayNhan() { return ngayNhan; }
    public void setNgayNhan(Date ngayNhan) { this.ngayNhan = ngayNhan; }
    public Date getNgayTra() { return ngayTra; }
    public void setNgayTra(Date ngayTra) { this.ngayTra = ngayTra; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public Timestamp getNgayTao() { return ngayTao; }
    public void setNgayTao(Timestamp ngayTao) { this.ngayTao = ngayTao; }
    public String getHoTenKhach() { return hoTenKhach; }
    public void setHoTenKhach(String hoTenKhach) { this.hoTenKhach = hoTenKhach; }
    public String getEmailKhach() { return emailKhach; }
    public void setEmailKhach(String emailKhach) { this.emailKhach = emailKhach; }
    public String getSoPhong() { return soPhong; }
    public void setSoPhong(String soPhong) { this.soPhong = soPhong; }
    public String getTenLoai() { return tenLoai; }
    public void setTenLoai(String tenLoai) { this.tenLoai = tenLoai; }
    public BigDecimal getGiaPhong() { return giaPhong; }
    public void setGiaPhong(BigDecimal giaPhong) { this.giaPhong = giaPhong; }
}
