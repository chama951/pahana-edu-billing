
    create table Bill (
        discountAmount float(53),
        netAmount float(53),
        totalAmount float(53),
        createdAt datetime(6),
        customerId bigint,
        id bigint not null auto_increment,
        userId bigint,
        primary key (id)
    ) engine=InnoDB;

    create table BillItem (
        discountAmount float(53) not null,
        quantity integer,
        subTotal float(53) not null,
        unitPrice float(53) not null,
        billId bigint,
        createdAt datetime(6),
        id bigint not null auto_increment,
        itemId bigint,
        primary key (id)
    ) engine=InnoDB;

    create table Customer (
        unitsConsumed integer,
        accountNumber bigint,
        createdAt datetime(6),
        id bigint not null auto_increment,
        updatedAt datetime(6),
        address varchar(255),
        email varchar(255),
        firstName varchar(255),
        lastName varchar(255),
        phoneNumber varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table Item (
        discountAmount float(53) not null,
        discountPercentage float(53) not null,
        price float(53) not null,
        publicationYear integer,
        quantityInStock integer,
        UserId bigint,
        createdAt datetime(6),
        id bigint not null auto_increment,
        updatedAt datetime(6),
        author varchar(255),
        description varchar(255),
        isbn varchar(255),
        publisher varchar(255),
        title varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table User (
        isActive bit,
        createdAt datetime(6),
        id bigint not null auto_increment,
        lastLogin datetime(6),
        updatedAt datetime(6),
        hashedPassword varchar(255),
        role enum ('ADMIN','CASHIER','INVENTORY_MANAGER'),
        username varchar(255),
        primary key (id)
    ) engine=InnoDB;

    alter table Customer 
       add constraint UK_e0io5hnj4fk3pci4bfrstmw2r unique (accountNumber);

    alter table Customer 
       add constraint UK_3qgg01qojcmbdp47dkaom9x45 unique (email);

    alter table Customer 
       add constraint UK_5q3ft6rvlr5mafopt4f6kb0bn unique (phoneNumber);

    alter table Item 
       add constraint UK_65yqvfgpn9cg1setjaihr08qf unique (isbn);

    alter table Item 
       add constraint UK_dp2x1lasb798ua1955wpae6ac unique (title);

    alter table User 
       add constraint UK_jreodf78a7pl5qidfh43axdfb unique (username);

    alter table Bill 
       add constraint FKb3a5w3ubttehxt3nfes3eonly 
       foreign key (customerId) 
       references Customer (id);

    alter table Bill 
       add constraint FKqagoykbe3r9tv5au2q99tgc0t 
       foreign key (userId) 
       references User (id);

    alter table BillItem 
       add constraint FKimtn3yyqcabiynowckrvih400 
       foreign key (billId) 
       references Bill (id);

    alter table BillItem 
       add constraint FK21jf0x67qfkbq4jobl7eb0jiu 
       foreign key (itemId) 
       references Item (id);

    alter table Item 
       add constraint FKltqgyaqs4368kkxoqk9edu4qo 
       foreign key (UserId) 
       references User (id);
