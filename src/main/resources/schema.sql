
    create table Bill (
        discountAmount decimal(38,2),
        taxAmount decimal(38,2),
        totalAmount decimal(38,2),
        billDate datetime(6),
        createdAt datetime(6),
        customerId bigint,
        id bigint not null auto_increment,
        updatedAt datetime(6),
        primary key (id)
    ) engine=InnoDB;

    create table BillItem (
        discountPercentage float(53) not null,
        quantity integer,
        subTotal float(53) not null,
        taxAmount float(53) not null,
        unitPrice float(53) not null,
        billId bigint,
        createdAt datetime(6),
        id bigint not null auto_increment,
        itemId bigint,
        updatedAt datetime(6),
        description varchar(255),
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
        price float(53) not null,
        publicationYear integer,
        quantityInStock integer,
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

    create table Payment (
        amount float(53) not null,
        billId bigint,
        createdAt datetime(6),
        id bigint not null auto_increment,
        paymentDate datetime(6),
        updatedAt datetime(6),
        notes varchar(255),
        paymentMethod enum ('CARD','CASH','ONLINE'),
        transactionReference varchar(255),
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

    alter table Payment 
       add constraint UK_nfkx8kvj9pwq3clkqt43f4vam unique (billId);

    alter table Bill 
       add constraint FKb3a5w3ubttehxt3nfes3eonly 
       foreign key (customerId) 
       references Customer (id);

    alter table BillItem 
       add constraint FKimtn3yyqcabiynowckrvih400 
       foreign key (billId) 
       references Bill (id);

    alter table BillItem 
       add constraint FK21jf0x67qfkbq4jobl7eb0jiu 
       foreign key (itemId) 
       references Item (id);

    alter table Payment 
       add constraint FKb26aqxdooedml0gir1oqxuh5d 
       foreign key (billId) 
       references Bill (id);
