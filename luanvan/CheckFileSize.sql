create database translate_vietnamese_chinese
use translate_vietnamese_chinese

create table fileChinese(
	filePathChinese nvarchar(255) primary key,
	size int
)

create table fileVietnamese(
id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
	filePathVietnamese nvarchar(255),
	size int,
	filePathChinese nvarchar(255) FOREIGN KEY REFERENCES fileChinese(filePathChinese)
)