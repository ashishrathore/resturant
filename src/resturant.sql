
create database testdb_intern;
use testdb_intern;
create table orders(
  -- primary key
  id bigint primary key not null auto_increment,
  costumer_name varchar(255) not null,
  item_ordered varchar(255) not null
);

INSERT INTO orders (costumer_name, item_ordered) values ('John', 'sandwitch');

INSERT INTO orders (costumer_name, item_ordered) values ('Mark', 'burger');

INSERT INTO orders (costumer_name, item_ordered) values ('Michael', 'Fries');

INSERT INTO orders (costumer_name, item_ordered) values ('Bobby', 'Cola');