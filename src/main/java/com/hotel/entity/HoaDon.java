package com.hotel.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class HoaDon {
    private int id;
    private int datPhongId;
    private Integer nhanVienId;
    private Timestamp ngayLap;
    private BigDecimal tongTien;
    private String phuongThuc;
    private String trangThai;
    private String soPhong;
    private String tenKhach;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getDatPhongId() { return datPhongId; }
    public void setDatPhongId(int datPhongId) { this.datPhongId = datPhongId; }
    public Integer getNhanVienId() { return nhanVienId; }
    public void setNhanVienId(Integer nhanVienId) { this.nhanVienId = nhanVienId; }
    public Timestamp getNgayLap() { return ngayLap; }
    public void setNgayLap(Timestamp ngayLap) { this.ngayLap = ngayLap; }
    public BigDecimal getTongTien() { return tongTien; }
    public void setTongTien(BigDecimal tongTien) { this.tongTien = tongTien; }
    public String getPhuongThuc() { return phuongThuc; }
    public void setPhuongThuc(String phuongThuc) { this.phuongThuc = phuongThuc; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public String getSoPhong() { return soPhong; }
    public void setSoPhong(String soPhong) { this.soPhong = soPhong; }
    public String getTenKhach() { return tenKhach; }
    public void setTenKhach(String tenKhach) { this.tenKhach = tenKhach; }
}
