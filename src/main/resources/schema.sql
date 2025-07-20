
    create table users (
       id INT AUTO_INCREMENT not null,
        hashedPassword varchar(255) not null,
        role varchar(255) not null,
        username varchar(255) not null,
        primary key (id)
    );

    alter table users 
       add constraint UK_r43af9ap4edm43mmtq01oddj6 unique (username);
