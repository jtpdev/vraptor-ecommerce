create table category(
    id numeric not null,
    name varchar(50),
    categoryup_id numeric,
	PRIMARY KEY (id)
);

create table product (
	id numeric not null,
    name varchar(50),
    description varchar(255),
    category_id numeric,
    PRIMARY KEY (id),
    FOREIGN KEY (category_id) REFERENCES category(id)
);

create table price (
	id numeric not null,
    datestart date,
    dateFinish date,
    pricevalue decimal(18,2),
    product_id numeric not null,
    primary key (id),
    FOREIGN KEY (product_id) REFERENCES product(id)

);

create table special (
	id numeric not null,
    datestart date,
    dateFinish date,
    pricevalue decimal(18,2),
    product_id numeric not null,
    primary key (id),
    FOREIGN KEY (product_id) REFERENCES product(id)

);

create table shipping (
	id numeric not null,
    name varchar(50),
    description varchar(255),
    primary key (id)

);

create table users (
    id numeric not null,
    name varchar(50) not null,
    email varchar(70) not null,
    password varchar(70) not null,
    type numeric,
    primary key (id)
);

create table sale (
	id numeric not null,
    status numeric,
    user_id numeric,
    datesale date,
    shippingsale_id numeric,
    totalvalue decimal(18,2),
    finishdate date,
    primary key (id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

create table shippingsale (
    id numeric not null,
    sale_id numeric not null,
    shipping_id numeric not null,
    shippingvalue decimal(18,2) not null,
    primary key (id),
    FOREIGN KEY (sale_id) REFERENCES sale(id),
    FOREIGN KEY (shipping_id) REFERENCES shipping(id)
);

alter table sale ADD CONSTRAINT fkshippingsale_id 
FOREIGN KEY(shippingsale_id ) REFERENCES shippingsale(id);


create table productsale (
	id numeric not null,
    product_id numeric not null,
    sale_id numeric not null,
    salevalue decimal(18,2) not null,
    PRIMARY KEY (id),
    FOREIGN KEY (sale_id) REFERENCES sale(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);
