package com.nhom4.hotel.entity;

import java.math.BigDecimal;

public class LoaiPhong {
    private int id;
    private String tenLoai;
    private String moTa;
    private int sucChua;
    private BigDecimal giaCoBan;
    private boolean trangThai;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTenLoai() { return tenLoai; }
    public void setTenLoai(String tenLoai) { this.tenLoai = tenLoai; }
    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }
    public int getSucChua() { return sucChua; }
    public void setSucChua(int sucChua) { this.sucChua = sucChua; }
    public BigDecimal getGiaCoBan() { return giaCoBan; }
    public void setGiaCoBan(BigDecimal giaCoBan) { this.giaCoBan = giaCoBan; }
    public boolean isTrangThai() { return trangThai; }
    public void setTrangThai(boolean trangThai) { this.trangThai = trangThai; }
}
