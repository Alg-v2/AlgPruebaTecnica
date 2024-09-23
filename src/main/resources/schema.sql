CREATE TABLE prices(
id UUID PRIMARY KEY,
product_id INT,
brand_id INT,
start_date TIMESTAMP,
end_date TIMESTAMP,
price_list INT,
priority INT,
price DOUBLE,
curr VARCHAR(3)
);