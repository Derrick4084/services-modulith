CREATE TABLE web.cart (
                               id BIGSERIAL PRIMARY KEY,
                               customer_email VARCHAR(255),
                               total_amount NUMERIC(19,2) NOT NULL DEFAULT 0.00
);

CREATE TABLE web.cart_item (
                                    id BIGSERIAL PRIMARY KEY,
                                    cart_id BIGINT NOT NULL,
                                    product_id BIGINT NOT NULL,
                                    quantity DOUBLE PRECISION NOT NULL,
                                    unit_price NUMERIC(19,2),
                                    total_price NUMERIC(19,2),
                                    CONSTRAINT fk_cart_item_cart
                                        FOREIGN KEY (cart_id)
                                            REFERENCES web.cart(id)
                                            ON DELETE CASCADE
);


CREATE INDEX idx_cart_item_cart_id
    ON web.cart_item(cart_id);

CREATE INDEX idx_cart_item_product_id
    ON web.cart_item(product_id);