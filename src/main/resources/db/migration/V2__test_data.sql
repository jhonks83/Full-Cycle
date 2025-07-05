-- Inserir Categorias para teste
INSERT INTO tb_category (name) VALUES ('Vidros Comuns');
INSERT INTO tb_category (name) VALUES ('Vidros Temperados');

-- Inserir Usuário Administrador com senha criptografada. A senha é: 123456
INSERT INTO tb_user (name, email, password, role) VALUES ('admin', 'admin@xvidros.com', '$2a$10$fP2.p4bJ2a2h8.wLiN3o7e.wL0j.N2aP6f3G4k5H1iG3j8l9k0l2', 'ADMIN');
INSERT INTO tb_user (name, email, password, role) VALUES ('cliente', 'cliente@email.com', '$2a$10$fP2.p4bJ2a2h8.wLiN3o7e.wL0j.N2aP6f3G4k5H1iG3j8l9k0l2', 'CLIENT');


-- Inserir Produto de Teste
INSERT INTO tb_product (name, description, active, category_id, created_at, updated_at) VALUES ('Vidro Liso Incolor', 'Vidro comum para janelas e portas.', TRUE, 1, NOW(), NOW());
INSERT INTO tb_product (name, description, active, category_id, created_at, updated_at) VALUES ('Vidro Temperado Fumê', 'Vidro de segurança para boxes e portas.', TRUE, 2, NOW(), NOW());


-- Inserir Variações para os Produtos de Teste
-- Variações do Vidro Liso Incolor (ID do produto: 1)
INSERT INTO tb_product_variation (product_id, size, color, price, quantity, sku) VALUES (1, '3mm', 'Incolor', 50.00, 100, 'VLI-3MM');
INSERT INTO tb_product_variation (product_id, size, color, price, quantity, sku) VALUES (1, '4mm', 'Incolor', 65.50, 80, 'VLI-4MM');

-- Variações do Vidro Temperado Fumê (ID do produto: 2)
INSERT INTO tb_product_variation (product_id, size, color, price, quantity, sku) VALUES (2, '8mm', 'Fumê', 120.00, 50, 'VTF-8MM');