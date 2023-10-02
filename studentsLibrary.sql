-- CREATE database library_db;
USE library_db;

-- Create table Books (
-- book_id INT auto_increment primary key,
-- isbn varchar(20) unique not null,
-- title varchar(50) not null,
-- author varchar(50) not null,
-- publisher varchar(50) not null,
-- price decimal(5,2) not null,
-- quantity INT,
-- date_added TIMESTAMP default current_timestamp
-- );

INSERT INTO `library_db`.`books` (`isbn`, `title`, `author`, `publisher`, `price`, `quantity`) VALUES ( 'isb3', 'Word', 'Jesus', 'Gift', '100.00', '100');


 Create table Issued (
 issued_id INT auto_increment primary key,
 book_id INT,
 user_id INT,
 status ENUM('issued','returned'),
 date_issued TIMESTAMP DEFAULT current_timestamp,
 date_returned TIMESTAMP ON UPDATE current_timestamp
 );

 INSERT INTO `library_db`.`issued` (`book_id`, `user_id`, `status`) VALUES ('3', '2', 'issued');



-- create table Stock (
-- stock_id INT auto_increment primary key,
-- book_id INT,
-- quantity INT
-- );

INSERT INTO `library_db`.`stock` (`book_id`, `quantity`) VALUES ('2', '100');

-- create table Users (
-- user_id INT auto_increment primary key,
-- name varchar(50) not null,
-- role enum('librarian','student'),
-- login varchar(10),
-- password varchar(20),
-- contact varchar(100),
-- date_created TIMESTAMP default current_timestamp
-- );

INSERT INTO `library_db`.`users` (`name`, `role`, `login`, `password`, `contact`) VALUES ('admin', 'librarian', 'admin', null, '514');



-- View catalog
Select isbn, title, author,publisher 
from Books
order by isbn;

-- Search by ...
Delimiter //
Create procedure search_title
(
IN search_title varchar(50)
-- OUT isbn varchar(20), title varchar(50), author varchar(50), publisher varchar(50)
)
Begin 
	Select b.isbn, b.title, b.author, b.publisher
	from Books b, Bookstock s
	Where b.book_id=s.book_id and title=search_title and s.quantity>0
	order by b.isbn;
END//
Delimiter;
call search_title('Word');

-- View Issued books
Select b.isbn, b.title, b.author, b.publisher, s.student_id, s.name, s.contact, i.date_issued
from Issued i
Left join Books b
On i.book_id=b.book_id
Left join Users s
On i.student_id=s.student_id;
Where i.status='issued'

-- View Available books
Select b.isbn, b.title, b.author, b.publisher, s.quantity, i.status
from Books b
Left join Stock s
On b.book_id=s.book_id
Left join Issued i 
On b.book_id=i.book_id
Where

-- count issued books
With Issued_only AS(
Select book_id, COUNT(book_id) as issued
from Issued
Where status='issued'
Group by book_id
)

-- Catalog available without Stock

SELECT b.book_id,b.isbn, b.title, b.author, b.publisher, b.quantity, (b.quantity-IFNULL(i.issued, 0)) as available
FROM Books b 
	LEFT JOIN (
		Select book_id, COUNT(book_id) as issued
		from Issued
		Where status='issued'
		Group by book_id
        ) as i 
		ON b.book_id = i.book_id
HAVING available > 0


-- Catalog available
SELECT *
FROM Books b 
	JOIN (
		SELECT s.book_id, (s.quantity-IFNULL(i.issued, 0)) as available
		FROM Stock s LEFT JOIN (
			Select book_id, COUNT(book_id) as issued
			from Issued
			Where status='issued'
			Group by book_id) as i ON i.book_id = s.book_id
            ) s ON s.book_id = b.book_id
WHERE available > 0



-- to return by user ID
Delimiter //
Create procedure to_return (
IN stud_id INT
)
begin
	Select i.issued_id, i.status, i.user_id,  i.book_id, b.isbn,  b.title, b.author, b.publisher,  i.date_issued
	from Issued i
	Left join Books b
	On i.book_id=b.book_id
	-- Left join Users u
-- 	On i.user_id=u.user_id
	Where i.user_id=1 AND i.status='issued';
End//

Delimiter;
call to_return(3);

Select MAX(book_id) from Books;

UPDATE BOOKS SET ISBN='879797979', TITLE='Interesting', 
				AUTHOR='Alexei', PUBLISHER='Elena', PRICE=10.00 , QUANTITY=12
				WHERE BOOK_ID=2;
                
SELECT B.BOOK_ID, B.ISBN, B.TITLE, B.AUTHOR, B.PUBLISHER, B.PRICE, B.QUANTITY 
				(B.QUANTITY - IFNULL(I.ISSUED,0)) AS AVAILABLE 
				FROM BOOKS B LEFT JOIN ( 
				SELECT BOOK_ID, COUNT(BOOK_ID) AS ISSUED 
				FROM ISSUED 
				WHERE STATUS = 'ISSUED' 
				GROUP BY BOOK_ID 
				) AS I 
				ON B.BOOK_ID = I.BOOK_ID 
				HAVING AVAILABLE > 0 
                
SELECT I.ISSUED_ID, I.STATUS, I.USER_ID, I.BOOK_ID,  B.ISBN, B.TITLE, B.AUTHOR, B.PUBLISHER, I.DATE_ISSUED 
				FROM ISSUED I LEFT JOIN BOOKS B ON I.BOOK_ID=B.BOOK_ID 
				WHERE I.USER_ID= AND I.STATUS='ISSUED';
                
--  Filter Books
SELECT BOOK_ID,ISBN,TITLE,AUTHOR,PUBLISHER,PRICE,QUANTITY,DATE_ADDED 
FROM BOOKS
WHERE ISBN like '%is%' AND TITLE like '%Lo%' AND AUTHOR like '%%' AND PUBLISHER like '%%';

SELECT B.BOOK_ID, B.ISBN AS RISBN, B.TITLE AS RTITLE, B.AUTHOR AS RAUTHOR, B.PUBLISHER AS RPUBLISHER, 
	B.PRICE, (B.QUANTITY - IFNULL(I.ISSUED,0)) AS AVAILABLE 
FROM BOOKS B LEFT JOIN ( 
	SELECT BOOK_ID, COUNT(BOOK_ID) AS ISSUED 
    FROM ISSUED 
    WHERE STATUS = 'ISSUED' 
    GROUP BY BOOK_ID ) AS I 
    ON B.BOOK_ID = I.BOOK_ID 
HAVING AVAILABLE > 0 AND RISBN LIKE '%sb%' AND RTITLE LIKE '%%' AND RAUTHOR LIKE '%%' AND RPUBLISHER LIKE '%%'

-- TEST Available Books Issue
SELECT B.BOOK_ID, B.ISBN, B.TITLE, B.AUTHOR, B.PUBLISHER, B.PRICE, (B.QUANTITY - IFNULL(I.ISSUED,0)) AS AVAILABLE 
FROM BOOKS B LEFT JOIN ( 
			SELECT BOOK_ID, COUNT(BOOK_ID) AS ISSUED 
			FROM ISSUED 
			WHERE STATUS = 'ISSUED' 
			GROUP BY BOOK_ID 
		) AS I 
		ON B.BOOK_ID = I.BOOK_ID 
HAVING AVAILABLE > 0 
AND ISBN LIKE '%%' AND TITLE LIKE '%%' AND AUTHOR LIKE '%%' AND PUBLISHER LIKE '%%';