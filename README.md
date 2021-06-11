This is just class project, just wanna show my coding skill in java, the app is not that usefull


MySQL query to create tables

create TABLE login
(
    userID   int AUTO_INCREMENT PRIMARY KEY,
    username varchar(25),
    pass     TEXT
);
create TABLE place
(
    placeID   int auto_increment primary key,
    placeName varchar(25),
    part      int
);
create table employee
(
    fem     int auto_increment primary key,
    placeID int,
    name    varchar(25),
    job     varchar(25),
    salary  double,
    foreign key (placeID) references place (placeID)
);
create table product
(
    fpr          int auto_increment primary key,
    placeID      int,
    name         varchar(25),
    manPrice     double,
    sellingPrice double,
    manDate      datetime,
    foreign key (placeID) references place (placeID)
);
create table shipment
(
    sshID        int auto_increment primary key,
    placeID      int,
    shipment     varchar(25),
    shippingDate DATETIME,
    arrivalTime  DATETIME,
    foreign key (placeID) references place (placeID)
);


INSERT INTO login (username, pass)
VALUES ('ravyar',
        '1000:5b42403533316265336335:cb625212cbacd8fe3285b135ec2f71765fe6e6f297836129cf2792b2d30ab0da363a1c9895406dc5e6cd80d2752788d49915ef7bc0f1663c2b5b2d87193cfde3');

generate password using password class
