package com.hotel.entity;

import java.math.BigDecimal;

public class Phong {
    private int id;
    private String soPhong;
    private int loaiPhongId;
    private String tenLoai;
    private BigDecimal giaPhong;
    private String trangThai;
    private String ghiChu;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getSoPhong() { return soPhong; }
    public void setSoPhong(String soPhong) { this.soPhong = soPhong; }
    public int getLoaiPhongId() { return loaiPhongId; }
    public void setLoaiPhongId(int loaiPhongId) { this.loaiPhongId = loaiPhongId; }
    public String getTenLoai() { return tenLoai; }
    public void setTenLoai(String tenLoai) { this.tenLoai = tenLoai; }
    public BigDecimal getGiaPhong() { return giaPhong; }
    public void setGiaPhong(BigDecimal giaPhong) { this.giaPhong = giaPhong; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
}
