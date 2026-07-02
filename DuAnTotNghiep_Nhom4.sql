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
    CONSTRAINT CK_NguoiDung_VaiTro CHECK (vai_tro IN (N'Khách hàng', N'Lễ tân', N'Quản lý'))
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
    trang_thai NVARCHAR(30) NOT NULL DEFAULT N'Trống',
    ghi_chu NVARCHAR(255) NULL,
    CONSTRAINT FK_Phong_LoaiPhong FOREIGN KEY (loai_phong_id) REFERENCES LoaiPhong(id),
    CONSTRAINT CK_Phong_TrangThai CHECK (trang_thai IN (N'Trống', N'Đã đặt', N'Đang sử dụng', N'Bảo trì'))
);
GO

CREATE TABLE DatPhong (
    id INT IDENTITY(1,1) PRIMARY KEY,
    khach_hang_id INT NOT NULL,
    phong_id INT NOT NULL,
    ngay_nhan DATE NOT NULL,
    ngay_tra DATE NOT NULL,
    trang_thai NVARCHAR(30) NOT NULL DEFAULT N'Chờ xác nhận',
    ngay_tao DATETIME NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_DatPhong_NguoiDung FOREIGN KEY (khach_hang_id) REFERENCES NguoiDung(id),
    CONSTRAINT FK_DatPhong_Phong FOREIGN KEY (phong_id) REFERENCES Phong(id),
    CONSTRAINT CK_DatPhong_Ngay CHECK (ngay_tra > ngay_nhan),
    CONSTRAINT CK_DatPhong_TrangThai CHECK (trang_thai IN (N'Chờ xác nhận', N'Đã xác nhận', N'Đã nhận phòng', N'Đã hủy', N'Đã thanh toán'))
);
GO

CREATE TABLE HoaDon (
    id INT IDENTITY(1,1) PRIMARY KEY,
    dat_phong_id INT NOT NULL,
    nhan_vien_id INT NULL,
    ngay_lap DATETIME NOT NULL DEFAULT GETDATE(),
    tong_tien DECIMAL(18,2) NOT NULL,
    phuong_thuc NVARCHAR(50) NULL,
    trang_thai NVARCHAR(30) NOT NULL DEFAULT N'Đã thanh toán',
    CONSTRAINT FK_HoaDon_DatPhong FOREIGN KEY (dat_phong_id) REFERENCES DatPhong(id),
    CONSTRAINT FK_HoaDon_NhanVien FOREIGN KEY (nhan_vien_id) REFERENCES NguoiDung(id),
    CONSTRAINT CK_HoaDon_TrangThai CHECK (trang_thai IN (N'Đã thanh toán', N'Chưa thanh toán'))
);
GO

CREATE INDEX IX_DatPhong_Phong_Ngay ON DatPhong(phong_id, ngay_nhan, ngay_tra, trang_thai);
GO

INSERT INTO NguoiDung (ho_ten, email, mat_khau, so_dien_thoai, vai_tro, trang_thai) VALUES
(N'Quản lý Nhóm 4', 'admin@hotel.vn', '123456', '0900000001', N'Quản lý', 1),
(N'Lễ tân Nhóm 4', 'letan@hotel.vn', '123456', '0900000002', N'Lễ tân', 1),
(N'Khách hàng Demo', 'khach@hotel.vn', '123456', '0900000003', N'Khách hàng', 1);
GO

INSERT INTO LoaiPhong (ten_loai, mo_ta, suc_chua, gia_co_ban, trang_thai) VALUES
(N'Standard Green', N'Phòng tiêu chuẩn, tiện nghi cơ bản, phù hợp nghỉ ngắn ngày.', 2, 450000, 1),
(N'Deluxe Moon', N'Phòng rộng, thiết kế hiện đại, view thành phố.', 3, 750000, 1),
(N'Family Sky', N'Phòng gia đình, diện tích lớn, phù hợp nhóm bạn hoặc gia đình.', 5, 1200000, 1),
(N'Suite Star', N'Phòng cao cấp, không gian riêng tư và dịch vụ tốt hơn.', 2, 1800000, 1);
GO

INSERT INTO Phong (so_phong, loai_phong_id, gia_phong, trang_thai, ghi_chu) VALUES
('101', 1, 450000, N'Trống', N'Tầng 1, gần sảnh'),
('102', 1, 450000, N'Trống', N'Tầng 1'),
('201', 2, 750000, N'Trống', N'Tầng 2, view phố'),
('202', 2, 790000, N'Bảo trì', N'Đang vệ sinh máy lạnh'),
('301', 3, 1200000, N'Trống', N'Tầng 3, phòng gia đình'),
('401', 4, 1800000, N'Trống', N'Suite cao cấp');
GO

INSERT INTO DatPhong (khach_hang_id, phong_id, ngay_nhan, ngay_tra, trang_thai, ngay_tao) VALUES
(3, 1, DATEADD(DAY, 1, CAST(GETDATE() AS DATE)), DATEADD(DAY, 3, CAST(GETDATE() AS DATE)), N'Chờ xác nhận', GETDATE());
GO
