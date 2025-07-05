-- Dados de teste para vidraçaria

-- Usuários
INSERT INTO users (id, name, email, password, role) VALUES
(1, 'Admin', 'admin@vidracaria.com', '123456', 'ADMIN'),
(2, 'Cliente Teste', 'cliente@vidracaria.com', '123456', 'CUSTOMER');

-- Produtos
INSERT INTO products (id, name, description, price, created_at, updated_at) VALUES
(1, 'Vidro Temperado 8mm', 'Vidro de alta resistência para portas e janelas', 250.00, NOW(), NOW()),
(2, 'Espelho Lapidado 4mm', 'Espelho com acabamento lapidado nas bordas', 120.00, NOW(), NOW()),
(3, 'Janela de Alumínio 1,20x1,00', 'Janela com vidro e estrutura em alumínio branco', 480.00, NOW(), NOW());

INSERT INTO product_variations (id, product_id, size, color, stock) VALUES
(1, 1, '1,20x2,00', 'Incolor', 10),
(2, 2, '0,60x0,90', 'Prata', 20),
(3, 3, '1,20x1,00', 'Branca', 5);


-- Carrinho
INSERT INTO carts (id, user_id) VALUES
(1, 2);

-- Itens do Carrinho
INSERT INTO cart_items (id, cart_id, product_variation_id, quantity, subtotal) VALUES
(1, 1, 1, 2, 500.00),
(2, 1, 2, 1, 120.00);

-- Pedidos
INSERT INTO orders (id, user_id, status, total_price, tracking_code, created_at, updated_at) VALUES
(1, 2, 'PENDING', 620.00, 'VID-1234ABCD', NOW(), NOW());

-- Itens do Pedido
INSERT INTO order_items (id, order_id, product_variation_id, quantity, price) VALUES
(1, 1, 1, 2, 500.00),
(2, 1, 2, 1, 120.00);
