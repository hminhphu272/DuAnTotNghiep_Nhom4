IF DB_ID('DuAnTotNghiep_Nhom4') IS NOT NULL
BEGIN
    ALTER DATABASE DuAnTotNghiep_Nhom4 SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE DuAnTotNghiep_Nhom4;
END
GO

CREATE DATABASE DuAnTotNghiep_Nhom4;
GO

USE DuAnTotNghiep_Nhom4;
GO

CREATE TABLE NguoiDung (
    id INT IDENTITY(1,1) PRIMARY KEY,
    ho_ten NVARCHAR(100) NOT NULL,
    email NVARCHAR(150) NOT NULL UNIQUE,
    mat_khau NVARCHAR(255) NOT NULL,
    so_dien_thoai NVARCHAR(20) NULL,
    vai_tro NVARCHAR(30) NOT NULL,
    trang_thai BIT NOT NULL DEFAULT 1,
    CONSTRAINT CK_NguoiDung_VaiTro CHECK (vai_tro IN ('KHACH_HANG', 'LE_TAN', 'QUAN_LY'))
);
GO

CREATE TABLE LoaiPhong (
    id INT IDENTITY(1,1) PRIMARY KEY,
    ten_loai NVARCHAR(100) NOT NULL,
    mo_ta NVARCHAR(255) NULL,
    suc_chua INT NOT NULL,
    gia_co_ban DECIMAL(18,2) NOT NULL,
    trang_thai BIT NOT NULL DEFAULT 1
);
GO

CREATE TABLE Phong (
    id INT IDENTITY(1,1) PRIMARY KEY,
    so_phong NVARCHAR(20) NOT NULL UNIQUE,
    loai_phong_id INT NOT NULL,
    gia_phong DECIMAL(18,2) NOT NULL,
    trang_thai NVARCHAR(30) NOT NULL,
    ghi_chu NVARCHAR(255) NULL,
    CONSTRAINT FK_Phong_LoaiPhong FOREIGN KEY (loai_phong_id) REFERENCES LoaiPhong(id),
    CONSTRAINT CK_Phong_TrangThai CHECK (trang_thai IN ('TRONG', 'DA_DAT', 'DANG_SU_DUNG', 'BAO_TRI'))
);
GO

CREATE TABLE DatPhong (
    id INT IDENTITY(1,1) PRIMARY KEY,
    khach_hang_id INT NOT NULL,
    phong_id INT NOT NULL,
    ngay_nhan DATE NOT NULL,
    ngay_tra DATE NOT NULL,
    trang_thai NVARCHAR(30) NOT NULL,
    ngay_tao DATETIME NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_DatPhong_NguoiDung FOREIGN KEY (khach_hang_id) REFERENCES NguoiDung(id),
    CONSTRAINT FK_DatPhong_Phong FOREIGN KEY (phong_id) REFERENCES Phong(id),
    CONSTRAINT CK_DatPhong_Ngay CHECK (ngay_tra > ngay_nhan),
    CONSTRAINT CK_DatPhong_TrangThai CHECK (trang_thai IN ('CHO_XAC_NHAN', 'DA_XAC_NHAN', 'DA_NHAN_PHONG', 'DA_TRA_PHONG', 'DA_HUY'))
);
GO

CREATE TABLE HoaDon (
    id INT IDENTITY(1,1) PRIMARY KEY,
    dat_phong_id INT NOT NULL UNIQUE,
    nhan_vien_id INT NULL,
    ngay_lap DATETIME NOT NULL DEFAULT GETDATE(),
    tong_tien DECIMAL(18,2) NOT NULL,
    phuong_thuc NVARCHAR(50) NULL,
    trang_thai NVARCHAR(30) NOT NULL,
    CONSTRAINT FK_HoaDon_DatPhong FOREIGN KEY (dat_phong_id) REFERENCES DatPhong(id),
    CONSTRAINT FK_HoaDon_NguoiDung FOREIGN KEY (nhan_vien_id) REFERENCES NguoiDung(id),
    CONSTRAINT CK_HoaDon_TrangThai CHECK (trang_thai IN ('CHUA_THANH_TOAN', 'DA_THANH_TOAN'))
);
GO

INSERT INTO NguoiDung (ho_ten, email, mat_khau, so_dien_thoai, vai_tro, trang_thai)
VALUES
(N'Quản lý khách sạn', 'quanly@hotel.com', '123456', '0900000001', 'QUAN_LY', 1),
(N'Nhân viên lễ tân', 'letan@hotel.com', '123456', '0900000002', 'LE_TAN', 1),
(N'Khách hàng demo', 'khach@hotel.com', '123456', '0900000003', 'KHACH_HANG', 1);
GO

INSERT INTO LoaiPhong (ten_loai, mo_ta, suc_chua, gia_co_ban, trang_thai)
VALUES
(N'Phòng đơn', N'Phòng dành cho 1 người', 1, 300000, 1),
(N'Phòng đôi', N'Phòng dành cho 2 người', 2, 500000, 1),
(N'Phòng gia đình', N'Phòng dành cho gia đình', 4, 900000, 1),
(N'Phòng VIP', N'Phòng cao cấp', 2, 1200000, 1);
GO

INSERT INTO Phong (so_phong, loai_phong_id, gia_phong, trang_thai, ghi_chu)
VALUES
('101', 1, 300000, 'TRONG', N'Phòng tầng 1'),
('102', 2, 500000, 'TRONG', N'Phòng tầng 1'),
('201', 3, 900000, 'TRONG', N'Phòng tầng 2'),
('202', 4, 1200000, 'TRONG', N'Phòng VIP tầng 2'),
('301', 2, 550000, 'BAO_TRI', N'Đang bảo trì điều hòa');
GO
