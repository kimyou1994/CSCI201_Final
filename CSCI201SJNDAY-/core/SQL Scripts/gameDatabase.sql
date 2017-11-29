DROP DATABASE IF EXISTS Game;
CREATE DATABASE Game;
USE Game;

CREATE TABLE Armor (
	armorID INT(11) PRIMARY KEY AUTO_INCREMENT,
    levels INT(11) NOT NULL
--     userID INT(11) NOT NULL,
    -- FOREIGN KEY fk1(userID) REFERENCES UserInfo(userID)
);

INSERT INTO Armor (levels)
	VALUES	(5),
					(10),
					(15),
					(20),
                    (25),
                    (30),
                    (35),
                    (40),
                    (45),
                    (50);

CREATE TABLE Color (
	colorID INT(11) PRIMARY KEY AUTO_INCREMENT,
	colorType LONGTEXT NOT NULL
    -- FOREIGN KEY fk1(userID) REFERENCES UserInfo(userID)
);

INSERT INTO Color (colorType)
	VALUES	('blue'),
					('red'),
                    ('green'),
                    ('orange'),
                    ('pink'),
                    ('white'),
                    ('yellow');
    
    

CREATE TABLE Weapon (
	weaponID INT(11) PRIMARY KEY AUTO_INCREMENT,
    power INT(11) NOT NULL
    -- FOREIGN KEY fk1(userID) REFERENCES UserInfo(userID)
);

INSERT INTO Weapon (power)
	VALUES	(5),
					(10),
                    (15),
                    (20),
                    (25),
                    (30),
                    (35),
                    (40),
                    (45),
                    (50);
-- 
CREATE TABLE UserInfo (		-- create tables that dont refer to other tables first then extrapolate
    -- always name of column followed by the type. reversed compared to other languages like type then name
    userID INT(11) PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL,
--     fname VARCHAR(4) NOT NULL,
--     lname VARCHAR(4) NOT NULL,
	armorID INT(11) NOT NULL,
	colorID INT(4) NULL,
	weaponID INT(11) NOT NULL,
	score INT(11) NOT NULL,
    passwords VARCHAR(100) NOT NULL,
    colors VARCHAR(100) NOT NULL
    -- FOREIGN KEY fk1(armorID) REFERENCES Armor(armorID),-- add foreign key for color and weapon and score
-- 	FOREIGN KEY fk2(weaponID) REFERENCES Weapon(weaponID),
-- 	FOREIGN KEY fk3(colorID) REFERENCES Color(colorID)
);

INSERT INTO UserInfo (username, passwords, armorID, colorID, colors, weaponID, score)
	VALUES	('ddaher', 'devin1', 0,0, 'T.F.F.F.F.F',0,500);
    
-- CREATE TABLE Score (
-- 	scoreID INT(11) PRIMARY KEY AUTO_INCREMENT,
--     scores INT(11) NOT NULL
-- -- 	FOREIGN KEY fk1(userID) REFERENCES UserInfo(userID)
-- );
-- 
-- INSERT INTO Score (scores)
-- 	VALUES	(0);

-- 
-- CREATE TABLE Grades (
-- 	gradeID INT(11) PRIMARY KEY AUTO_INCREMENT, -- creating primary key
-- 	classID INT(11) NOT NULL,
--     studentID INT(11) NOT NULL,
--     grade VARCHAR(2),
--     FOREIGN KEY fk1(classID) REFERENCES Class(classID), -- creating foreign key and setting it to refer to class table with key classID
-- 	FOREIGN KEY fk2(studentID) REFERENCES Student(studentID)
-- );
-- INSERT INTO Grades (classID, studentID, grade)
-- 	VALUES	(1,1,'A'),
-- 					(2,3,'A-'),
--                     (3,2,'A'),
--                     (3,3,'B'),
--                     -- (7,3,'B-'); -- this is error because maintaining foreign key constraint since foreign key classID of 7 does not exist
--                     (5,3,'B-');
                    
