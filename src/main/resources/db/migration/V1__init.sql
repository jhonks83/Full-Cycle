-- Tabela de Usuários
CREATE TABLE tb_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

-- Tabela de Categorias de Produto
CREATE TABLE tb_category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Tabela de Produtos
CREATE TABLE tb_product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    active BOOLEAN DEFAULT TRUE,
    category_id BIGINT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES tb_category(id)
);

-- Tabela de Variações de Produto
CREATE TABLE tb_product_variation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    size VARCHAR(100),
    color VARCHAR(100),
    price FLOAT,
    quantity INT,
    sku VARCHAR(255) UNIQUE,
    FOREIGN KEY (product_id) REFERENCES tb_product(id)
);

-- Tabela de Carrinho de Compras
CREATE TABLE tb_cart (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES tb_user(id)
);

-- Tabela de Itens do Carrinho
CREATE TABLE tb_cart_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cart_id BIGINT NOT NULL,
    product_variation_id BIGINT NOT NULL,
    quantity INT,
    subtotal FLOAT,
    FOREIGN KEY (cart_id) REFERENCES tb_cart(id),
    FOREIGN KEY (product_variation_id) REFERENCES tb_product_variation(id)
);

-- Tabela de Pedidos
CREATE TABLE tb_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    total_price FLOAT,
    status ENUM('PENDING', 'PAID', 'SHIPPED', 'DELIVERED', 'CANCELLED'), -- Status corrigidos
    tracking_code VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES tb_user(id)
);

-- Tabela de Itens do Pedido
CREATE TABLE tb_order_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_variation_id BIGINT NOT NULL,
    quantity INT,
    price FLOAT,
    FOREIGN KEY (order_id) REFERENCES tb_order(id),
    FOREIGN KEY (product_variation_id) REFERENCES tb_product_variation(id)
);