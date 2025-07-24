
    create table Bill (
        billDate date,
        createdAt date,
        discountAmount decimal(38,2),
        dueDate date,
        taxAmount decimal(38,2),
        totalAmount decimal(38,2),
        updatedAt date,
        customerId bigint,
        id bigint not null auto_increment,
        primary key (id)
    ) engine=InnoDB;

    create table BillItem (
        createdAt date,
        discountPercentage float(53) not null,
        quantity integer,
        subTotal float(53) not null,
        taxAmount float(53) not null,
        unitPrice float(53) not null,
        updatedAt date,
        billId bigint,
        id bigint not null auto_increment,
        itemId bigint,
        description varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table Customer (
        createdAt date,
        unitsConsumed integer,
        updatedAt date,
        accountNumber bigint,
        id bigint not null auto_increment,
        address varchar(255),
        email varchar(255),
        firstName varchar(255),
        lastName varchar(255),
        phoneNumber varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table Item (
        createdAt date,
        price float(53) not null,
        publicationYear integer,
        quantityInStock integer,
        updatedAt date,
        id bigint not null auto_increment,
        author varchar(255),
        description varchar(255),
        isbn varchar(255),
        publisher varchar(255),
        title varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table Payment (
        amount float(53) not null,
        createdAt date,
        paymentDate date,
        updatedAt date,
        billId bigint,
        id bigint not null auto_increment,
        notes varchar(255),
        paymentMethod enum ('CARD','CASH','ONLINE'),
        transactionReference varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table User (
        createdAt date,
        isActive bit,
        lastLogin date,
        updatedAt date,
        id bigint not null auto_increment,
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
