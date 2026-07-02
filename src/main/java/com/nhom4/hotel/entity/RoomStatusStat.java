package com.nhom4.hotel.entity;

public class RoomStatusStat {
    private String trangThai;
    private int soLuong;

    public RoomStatusStat(String trangThai, int soLuong) {
        this.trangThai = trangThai;
        this.soLuong = soLuong;
    }

    public String getTrangThai() { return trangThai; }
    public int getSoLuong() { return soLuong; }
}
