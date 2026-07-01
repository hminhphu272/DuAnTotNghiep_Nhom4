package com.hotel.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class DatPhong {
    private int id;
    private int khachHangId;
    private String tenKhach;
    private int phongId;
    private String soPhong;
    private Date ngayNhan;
    private Date ngayTra;
    private String trangThai;
    private Timestamp ngayTao;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getKhachHangId() { return khachHangId; }
    public void setKhachHangId(int khachHangId) { this.khachHangId = khachHangId; }
    public String getTenKhach() { return tenKhach; }
    public void setTenKhach(String tenKhach) { this.tenKhach = tenKhach; }
    public int getPhongId() { return phongId; }
    public void setPhongId(int phongId) { this.phongId = phongId; }
    public String getSoPhong() { return soPhong; }
    public void setSoPhong(String soPhong) { this.soPhong = soPhong; }
    public Date getNgayNhan() { return ngayNhan; }
    public void setNgayNhan(Date ngayNhan) { this.ngayNhan = ngayNhan; }
    public Date getNgayTra() { return ngayTra; }
    public void setNgayTra(Date ngayTra) { this.ngayTra = ngayTra; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public Timestamp getNgayTao() { return ngayTao; }
    public void setNgayTao(Timestamp ngayTao) { this.ngayTao = ngayTao; }
}
