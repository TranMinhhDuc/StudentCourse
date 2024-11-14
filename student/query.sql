use student;

-- Bảng Course
-- Bảng Course
CREATE TABLE Course (
    id INT PRIMARY KEY AUTO_INCREMENT,
    courseName VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL
);
CREATE TABLE Student (
    id INT PRIMARY KEY AUTO_INCREMENT,
    semester INT NOT NULL,
    studentName VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL
);

-- Bảng StudentCourse (liên kết giữa Student và Course)
CREATE TABLE studentcourse (
    id INT PRIMARY KEY AUTO_INCREMENT,
    studentId INT NOT NULL,
    courseId INT NOT NULL,
    FOREIGN KEY (studentId) REFERENCES Student(id) ON DELETE CASCADE,
    FOREIGN KEY (courseId) REFERENCES Course(id) ON DELETE CASCADE
);


INSERT INTO Student (semester, studentName) VALUES
    (1, 'Nguyễn Văn Anh'),
    (1, 'Trần Thị Bích'),
    (2,'Lê Văn Cường'),
    (1, 'Phạm Thị Dung'),
    (2, 'Đỗ Văn Hải'),
    (1, 'Vũ Thị Lan'),
    (2, 'Nguyễn Thị Mai'),
    (2, 'Hoàng Văn Hùng'),
    (1, 'Phạm Thị Ngọc'),
    (1, 'Lê Văn Minh'),
    (2, 'Trần Văn Tài'),
    (2, 'Nguyễn Thị Thu'),
    (2, 'Phạm Văn Toàn'),
    (1, 'Lê Thị Yến'),
    (1, 'Nguyễn Văn Quân'),
    (1, 'Đỗ Thị Hoa'),
    (1, 'Phạm Văn Hoàng'),
    (2, 'Nguyễn Thị Liên'),
    (2, 'Trần Văn Nam'),
    (1, 'Lê Thị Nguyệt'),
    (2, 'Hoàng Văn Quang'),
    (1, 'Nguyễn Văn Thắng'),
    (2, 'Phạm Văn Hòa'),
    (1, 'Đỗ Thị Xuân'),
    (2, 'Trần Thị Yến'),
    (1, 'Lê Văn Khánh'),
    (2, 'Nguyễn Thị Trang'),
    (1, 'Phạm Thị Tâm'),
    (2, 'Vũ Văn Việt'),
    (1, 'Hoàng Văn Bình');

INSERT INTO Course (courseName) VALUES
    ('Toán cao cấp'),
    ('Lập trình Java'),
    ('Cơ sở dữ liệu'),
    ('Mạng máy tính'),
    ('Hệ thống thông tin');

INSERT INTO StudentCourse (studentId, courseId) VALUES
    (1, 1),
    (2, 3),
    (3, 2),
    (4, 4),
    (5, 5),
    (6, 1),
    (7, 2),
    (8, 3),
    (9, 4),
    (10, 5),
    (11, 1),
    (12, 3),
    (13, 2),
    (14, 4),
    (15, 5),
    (16, 1),
    (17, 2),
    (18, 3),
    (19, 4),
    (20, 5),
    (21, 1),
    (22, 3),
    (23, 2),
    (24, 4),
    (25, 5),
    (26, 1),
    (27, 2),
    (28, 3),
    (29, 4),
    (30, 5);
    
select * from student limit 100;