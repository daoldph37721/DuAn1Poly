CREATE DATABASE DB_502689_pro
GO

--DROP DATABASE DB_502689_pro

USE DB_502689_pro
GO

CREATE TABLE ThuongHieu(
	MaThuongHieu varchar(10) PRIMARY KEY,
	TenThuongHieu nvarchar(100),
	TrangThai int
)

CREATE TABLE ChatLieu(
	MaChatLieu varchar(10) PRIMARY KEY,
	TenChatLieu nvarchar(100),
	MoTaCL nvarchar(255),
	TrangThai int
)

CREATE TABLE KieuDang(
	MaKieuDang varchar(10) PRIMARY KEY,
	TenKieuDang nvarchar(100),
	MoTaKD nvarchar(255),
	TrangThai int
)

CREATE TABLE SizeGiay(
	MaSize varchar(10) PRIMARY KEY,
	SoSize int,
	TrangThai int
)

CREATE TABLE MauSac(
	MaMau varchar(10) PRIMARY KEY,
	TenMau nvarchar(50),
	DoTuongPhan int,
	DoBaoHoa int,
	TrangThai int
)

CREATE TABLE SanPham(
	MaSanPham varchar(10) PRIMARY KEY,
	TenSanPham nvarchar(100),
	XuatXu nvarchar(100),
	NgayTao date,
	NgaySua date,
	TrangThai int,
	MaThuongHieu varchar(10) FOREIGN KEY REFERENCES ThuongHieu(MaThuongHieu)
)

CREATE TABLE SanPhamChiTiet(
	MaSPCT varchar(10) PRIMARY KEY,
	MaSanPham varchar(10) FOREIGN KEY REFERENCES SanPham(MaSanPham),
	MaMau varchar(10) FOREIGN KEY REFERENCES MauSac(MaMau),
	MaSize varchar(10) FOREIGN KEY REFERENCES SizeGiay(MaSize),
	MaChatLieu varchar(10) FOREIGN KEY REFERENCES ChatLieu(MaChatLieu),
	MaKieuDang varchar(10) FOREIGN KEY REFERENCES KieuDang(MaKieuDang),
	SoLuong int,
	DonGia money,
	NgayTao date,
	NgaySua date,
	TrangThai int
)

CREATE TABLE KhachHang(
	MaKH int PRIMARY KEY IDENTITY(1,1),
	HoTen nvarchar(100),
	SoDienThoai varchar(15),
	DiaChi nvarchar(255),
	TrangThai int,
	NgayTao date
)

CREATE TABLE GioHang(
	MaGioHang int PRIMARY KEY IDENTITY(1,1),
	MaKH int FOREIGN KEY REFERENCES KhachHang(MaKH),
	NgayTao date,
	NgaySua date,
	TrangThai int
)

CREATE TABLE GioHangChiTiet(
	MaGioHang int FOREIGN KEY REFERENCES GioHang(MaGioHang),
	MaSPCT varchar(10) FOREIGN KEY REFERENCES SanPhamChiTiet(MaSPCT),
	SoLuong int,
	TrangThai int,
	PRIMARY KEY(MaGioHang, MaSPCT)
)

CREATE TABLE KhuyenMai(
	MaKhuyenMai varchar(10) PRIMARY KEY,
	TenKhuyenMai nvarchar(100),
	SoHoaDonApDung int,
	HinhThuc int,
	MucGiamGia int,
	ThoiGianBatDau date,
	ThoiGianKetThuc date,
	MoTa nvarchar(100),
	NgayTao date,
	NgaySua date,
	TrangThai int,
	SoTienMin money,
	SoLuong int
)

CREATE TABLE HinhThucThanhToan(
	MaHTTT varchar(10) PRIMARY KEY,
	TenHTTT nvarchar(100),
	TrangThai int,
)

CREATE TABLE ChucVu(
	MaCV varchar(10),
	TenCV nvarchar(100),
	PRIMARY KEY(MaCV)
)

CREATE TABLE NhanVien(
	MaNV varchar(10) PRIMARY KEY,
	MatKhau varchar(100),
	HoTen nvarchar(100),
	GioiTinh int,
	DiaChi nvarchar(100),
	SoDienThoai varchar(15),
	CCCD varchar(100),
	NgayVaoLam date,
	NgayNghi date,
	NgayTao date,
	NgaySua date,
	TrangThai int,
	MaCV varchar(10) FOREIGN KEY REFERENCES ChucVu(MaCV),
	Email varchar(100)
)

CREATE TABLE HoaDon(
	MaHoaDon int PRIMARY KEY IDENTITY(1,1),
	MaNV varchar(10) FOREIGN KEY REFERENCES NhanVien(MaNV),
	MaKH int FOREIGN KEY REFERENCES KhachHang(MaKH),
	MaKhuyenMai varchar(10) FOREIGN KEY REFERENCES KhuyenMai(MaKhuyenMai),
	MaHTTT varchar(10) FOREIGN KEY REFERENCES HinhThucThanhToan(MaHTTT),
	NgayTao date,
	NgaySua date,
	NgayThanhToan date,
	TongTien money,
	GiamGia money,
	ThanhTien money,
	TrangThai int
)

CREATE TABLE HoaDonChiTiet(
	MaHoaDon int FOREIGN KEY REFERENCES HoaDon(MaHoaDon),
	MaCTSP varchar(10) FOREIGN KEY REFERENCES SanPhamChiTiet(MaSPCT),
	SoLuong int,
	GiaHienHanh money,
	TrangThai int
)

----Tao Procedure
--CREATE PROCEDURE Update_TrangThaiKM
--	@MaKhuyenMai varchar(10),
--	@NgayBatDau date,
--	@NgayKetThuc date
--AS BEGIN
--	IF @NgayBatDau>GETDATE()
--	BEGIN
--		PRINT N'Cập nhật trạng tháng là chưa hoạt động'
--		UPDATE KhuyenMai SET TrangThai=2
--				WHERE MaKhuyenMai=@MaKhuyenMai
--		RETURN;
--	END	
--	IF @NgayBatDau>=GETDATE() AND GETDATE()<=@NgayKetThuc
--	BEGIN
--		PRINT N'Cập nhật trạng tháng là đang hoạt động'
--		UPDATE KhuyenMai SET TrangThai=1
--				WHERE MaKhuyenMai=@MaKhuyenMai
--		RETURN;
--	END
--	IF @NgayKetThuc<GETDATE()
--	BEGIN
--		PRINT N'Cập nhật trạng tháng là không còn HSD'
--		UPDATE KhuyenMai SET TrangThai=0
--			WHERE MaKhuyenMai=@MaKhuyenMai
--		RETURN;
--	END
--END

--Theem du lieu bang ThuongHieu
SELECT * FROM ThuongHieu
INSERT ThuongHieu VALUES('TH001','Nike',1)
INSERT ThuongHieu VALUES('TH002','Adidas',1)
INSERT ThuongHieu VALUES('TH003','Converse',1)
INSERT ThuongHieu VALUES('TH004','MLB',1)
INSERT ThuongHieu VALUES('TH005','Puma',1)

--Them du lieu bang Chatieu
SELECT * FROM ChatLieu
INSERT ChatLieu VALUES('CL001','Da',N'Hạn chế đi nước',1)
INSERT ChatLieu VALUES('CL002',N'Vải Cotton',N'N/A',1)
INSERT ChatLieu VALUES('CL003',N'Nhựa Catton',N'N/A',1)
INSERT ChatLieu VALUES('CL004',N'Vải Thái',N'Mỏng giày',1)

--Thêm dữ liệu bảng Kiểu Dáng
SELECT * FROM KieuDang
INSERT KieuDang VALUES('KD001',N'Cao',N'N/A',1)
INSERT KieuDang VALUES('KD002',N'Thon Gọn',N'N/A',1)
INSERT KieuDang VALUES('KD003',N'To Bè',N'Phù hợp với chân toooooo',1)
INSERT KieuDang VALUES('KD004',N'Thấp bó chân',N'Phù hợp với chân nhỏ',1)

--Them du lieu bang SizeGiay
SELECT * FROM SizeGiay
INSERT SizeGiay VALUES('SIZE35',35,1)
INSERT SizeGiay VALUES('SIZE36',36,1)
INSERT SizeGiay VALUES('SIZE37',37,1)
INSERT SizeGiay VALUES('SIZE38',38,1)
INSERT SizeGiay VALUES('SIZE39',39,1)
INSERT SizeGiay VALUES('SIZE40',40,1)
INSERT SizeGiay VALUES('SIZE41',41,1)
INSERT SizeGiay VALUES('SIZE42',42,1)
INSERT SizeGiay VALUES('SIZE43',43,1)
INSERT SizeGiay VALUES('SIZE44',44,1)
INSERT SizeGiay VALUES('SIZE45',45,1)
INSERT SizeGiay VALUES('SIZE46',46,1)

--Them du lieu bang MauSac
SELECT * FROM MauSac
INSERT MauSac VALUES('MS001',N'Đỏ cam',23,43,1)
INSERT MauSac VALUES('MS002',N'Đen',18,50,1)
INSERT MauSac VALUES('MS003',N'Hồng Nhạt',10,61,1)
INSERT MauSac VALUES('MS004',N'Xanh Đen',30,45,1)
INSERT MauSac VALUES('MS005',N'Đen xì',57,46,1)

--Them du lieu o bang SanPham
SELECT * FROM SanPham
INSERT SanPham VALUES('SP001',N'Nike Jordan',N'Italy','2022-07-19','2023-11-21',1,'TH001')
INSERT SanPham VALUES('SP002',N'Adidas Ultraboost',N'Italy','2022-08-28',NULL,1,'TH002')
INSERT SanPham VALUES('SP003',N'Converse Chuck Taylor 1970s',N'Pháp','2022-01-08',NULL,1,'TH003')
INSERT SanPham VALUES('SP004',N'MLB Big Ball',N'Anh','2022-12-21','2022-12-28',1,'TH004')
INSERT SanPham VALUES('SP005',N'Puma Ember Trail',N'Đức','2022-02-18',NULL,1,'TH005')
INSERT SanPham VALUES('SP006',N'Nike airport 7',N'Anh','2023-11-21',NULL,1,'TH001')
INSERT SanPham VALUES('SP007',N'MLB 500X',N'Việt Nam','2023-11-21',NULL,1,'TH004')
INSERT SanPham VALUES('SP008',N'Puma Z1000',N'Dai Loan','2023-11-21','2023-11-22',1,'TH005')
INSERT SanPham VALUES('SP009',N'Converse P1023',N'Việt Nam','2023-11-21','2023-11-21',1,'TH003')

--Them moi vao bang SanPhamChiTiet
SELECT * FROM SanPhamChiTiet
INSERT SanPhamChiTiet VALUES('SPCT001','SP001','MS001','SIZE37','CL001','KD001',60,350000,'2022-07-20',NULL,1)
INSERT SanPhamChiTiet VALUES('SPCT002','SP002','MS002','SIZE40','CL002','KD002',77,450000,'2022-08-30',NULL,1)
INSERT SanPhamChiTiet VALUES('SPCT003','SP003','MS003','SIZE41','CL003','KD003',64,780000,'2022-01-29',NULL,1)
INSERT SanPhamChiTiet VALUES('SPCT004','SP004','MS001','SIZE39','CL003','KD002',34,349000,'2022-01-02',NULL,1)
INSERT SanPhamChiTiet VALUES('SPCT005','SP005','MS003','SIZE42','CL001','KD003',59,899000,'2022-02-27',NULL,1)

--Them moi vao bang KhachHang
SELECT * FROM KhachHang
INSERT KhachHang VALUES(N'NguyenVV6','0987654321',N'Ba Đình, Hà Nội',1,'2022-10-10')
INSERT KhachHang VALUES(N'PhongTT35','0123456789',N'Tây Hồ, Hà Nội',1,'2022-09-15')
INSERT KhachHang VALUES(N'DungNA29','0456789321',N'Từ Liêm, Hà Nội',1,'2022-10-15')
INSERT KhachHang VALUES(N'HangNT169','0874561238',N'Ba Đình, Hà Nội',1,'2022-11-14')
INSERT KhachHang VALUES(N'HuyenNK68','0947895368',N'Thanh Xuân, Hà Nội',1,'2022-11-16')

--Them du lieu vao GioHang va GioHangChiTiet
SELECT * FROM GioHang
SELECT * FROM GioHangChiTiet

--Them du lieu vao KhuyenMai
SELECT * FROM KhuyenMai
INSERT KhuyenMai VALUES('KM001',N'Mừng sinh nhât khách',0,0,100000,'2023-11-01','2023-11-13',NULL,'2023-11-15','2023-11-16',0,200000,40)
INSERT KhuyenMai VALUES('KM002',N'Mừng 20/11',0,0,100000,'2022-11-10','2022-11-14',NULL,'2022-11-15','2022-11-16',0,150000,40)
INSERT KhuyenMai VALUES('KM003',N'Chào Tân Sinh Viên',5,1,20,'2023-11-15','2023-12-25',NULL,'2023-11-15','2023-11-18',1,100000,50)
INSERT KhuyenMai VALUES('KM004',N'Mừng giáng sinh',6,0,100000,'2023-11-15','2023-12-25',NULL,'2023-11-15','2023-11-18',1,100000,57)

--Them du lieu vao HinhThucThanhToan
SELECT * FROM HinhThucThanhToan
INSERT HinhThucThanhToan VALUES('HTTT001',N'Tiền mặt',1)
INSERT HinhThucThanhToan VALUES('HTTT002',N'Chuyển Khoản',1)

--Them du lieu vao ChucVu
SELECT * FROM ChucVu
INSERT ChucVu VALUES('CV001',N'Quản lý')
INSERT ChucVu VALUES('CV002',N'Nhân Viên')

--Them du lieu bang NhanVien
SELECT * FROM NhanVien
INSERT NhanVien VALUES('NV001','abc123',N'Hoài Sơn',0,N'Ba Vi, Hà Nội','0874159362','038204007568','2022-10-19',NULL,'2022-10-10',NULL,1,'CV001','sonnhph37101@fpt.edu.vn')
INSERT NhanVien VALUES('NV002','abcd1234',N'Duy Tiến',1,N'Thach That, Hà Nội','0874598123','038589246874','2022-03-10',NULL,'2022-03-02',NULL,1,'CV002','nguyenduytien88888@gmail.com')
INSERT NhanVien VALUES('NV003','xyz487',N'Hải Phong',1,N'Hải Phòng','0756981476','087451894756','2022-05-19',NULL,'2022-05-19',NULL,1,'CV002','ndhaiphong1206@gmail.com')
INSERT NhanVien VALUES('NV004','aaa123bbb',N'Tuấn Anh',0,N'Quảng Linh','087956875','038402785988','2022-05-19',NULL,'2022-05-10',NULL,1,'CV002','tuananhnguyen.qn1109@gmail.com')
INSERT NhanVien VALUES('NV005','dinhdaoa123',N'Đình Đạo',0,N'Hoằng Hóa, Thanh Hóa','0348703211','03840278598','2022-05-10',NULL,'2022-05-10',NULL,1,'CV002','ledinhdao18012004@gmail.com')

SELECT * FROM HoaDon
INSERT HoaDon(MaNV,MaKH,MaHTTT,NgayTao,NgayThanhToan,TongTien,GiamGia,ThanhTien,TrangThai)
VALUES('NV001',1,'HTTT001','2023-01-01','2023-01-01',8000000,0,8000000,1)
INSERT HoaDon(MaNV,MaKH,MaHTTT,NgayTao,NgayThanhToan,TongTien,GiamGia,ThanhTien,TrangThai)
VALUES('NV002',2,'HTTT002','2023-02-02','2023-02-02',9000000,0,9000000,1)
INSERT HoaDon(MaNV,MaKH,MaHTTT,NgayTao,NgayThanhToan,TongTien,GiamGia,ThanhTien,TrangThai)
VALUES('NV003',1,'HTTT001','2023-03-03','2023-03-03',17000000,0,17000000,1)
INSERT HoaDon(MaNV,MaKH,MaHTTT,NgayTao,NgayThanhToan,TongTien,GiamGia,ThanhTien,TrangThai)
VALUES('NV004',3,'HTTT002','2023-04-04','2023-04-04',13000000,0,13000000,1)
INSERT HoaDon(MaNV,MaKH,MaHTTT,NgayTao,NgayThanhToan,TongTien,GiamGia,ThanhTien,TrangThai)
VALUES('NV005',2,'HTTT001','2023-05-05','2023-05-05',17000000,0,17000000,1)
INSERT HoaDon(MaNV,MaKH,MaHTTT,NgayTao,NgayThanhToan,TongTien,GiamGia,ThanhTien,TrangThai)
VALUES('NV001',4,'HTTT002','2023-06-06','2023-06-06',15000000,0,15000000,1)
INSERT HoaDon(MaNV,MaKH,MaHTTT,NgayTao,NgayThanhToan,TongTien,GiamGia,ThanhTien,TrangThai)
VALUES('NV005',3,'HTTT001','2023-07-07','2023-07-07',7000000,0,7000000,1)
INSERT HoaDon(MaNV,MaKH,MaHTTT,NgayTao,NgayThanhToan,TongTien,GiamGia,ThanhTien,TrangThai)
VALUES('NV005',2,'HTTT002','2023-08-08','2023-08-08',21000000,0,21000000,1)
INSERT HoaDon(MaNV,MaKH,MaHTTT,NgayTao,NgayThanhToan,TongTien,GiamGia,ThanhTien,TrangThai)
VALUES('NV002',1,'HTTT001','2023-09-09','2023-09-09',11000000,0,11000000,1)
INSERT HoaDon(MaNV,MaKH,MaHTTT,NgayTao,NgayThanhToan,TongTien,GiamGia,ThanhTien,TrangThai)
VALUES('NV003',1,'HTTT002','2023-10-10','2023-10-10',9000000,0,9000000,1)
INSERT HoaDon(MaNV,MaKH,MaHTTT,NgayTao,NgayThanhToan,TongTien,GiamGia,ThanhTien,TrangThai)
VALUES('NV004',1,'HTTT001','2023-11-11','2023-11-11',16000000,0,16000000,1)
INSERT HoaDon(MaNV,MaKH,MaHTTT,NgayTao,NgayThanhToan,TongTien,GiamGia,ThanhTien,TrangThai)
VALUES('NV002',1,'HTTT001','2023-12-01','2023-12-01',14000000,0,14000000,1)

SELECT * FROM HoaDonChiTiet
INSERT HoaDonChiTiet(MaHoaDon,MaCTSP,SoLuong,TrangThai) VALUES(1,'SPCT001',7,1)
INSERT HoaDonChiTiet(MaHoaDon,MaCTSP,SoLuong,TrangThai) VALUES(2,'SPCT002',6,1)
INSERT HoaDonChiTiet(MaHoaDon,MaCTSP,SoLuong,TrangThai) VALUES(3,'SPCT003',7,1)
INSERT HoaDonChiTiet(MaHoaDon,MaCTSP,SoLuong,TrangThai) VALUES(4,'SPCT001',3,1)
INSERT HoaDonChiTiet(MaHoaDon,MaCTSP,SoLuong,TrangThai) VALUES(5,'SPCT005',9,1)
INSERT HoaDonChiTiet(MaHoaDon,MaCTSP,SoLuong,TrangThai) VALUES(6,'SPCT003',4,1)
INSERT HoaDonChiTiet(MaHoaDon,MaCTSP,SoLuong,TrangThai) VALUES(7,'SPCT004',6,1)
INSERT HoaDonChiTiet(MaHoaDon,MaCTSP,SoLuong,TrangThai) VALUES(8,'SPCT003',3,1)
INSERT HoaDonChiTiet(MaHoaDon,MaCTSP,SoLuong,TrangThai) VALUES(9,'SPCT001',8,1)
INSERT HoaDonChiTiet(MaHoaDon,MaCTSP,SoLuong,TrangThai) VALUES(10,'SPCT003',7,1)
INSERT HoaDonChiTiet(MaHoaDon,MaCTSP,SoLuong,TrangThai) VALUES(11,'SPCT004',9,1)
INSERT HoaDonChiTiet(MaHoaDon,MaCTSP,SoLuong,TrangThai) VALUES(12,'SPCT002',8,1)




--DELETE FROM HoaDon;
--DBCC CHECKIDENT ('HoaDon', RESEED, 0);
