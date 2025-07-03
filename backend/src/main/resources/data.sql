-- ===== Insertions initiales pour la table taille =====
INSERT INTO taille (id, libelle) VALUES
  (1, 'S'),
  (2, 'M'),
  (3, 'L');

-- ===== Insertions pour les catégories de cocktails =====
INSERT INTO categorie (id, nom) VALUES
  (1, 'Classiques'),
  (2, 'Tiki'),
  (3, 'Sans alcool'),
  (4, 'Signature');

-- ===== Insertions d’ingrédients de base =====
INSERT INTO ingredient (id, nom) VALUES
  (1, 'Rhum blanc'),
  (2, 'Jus de citron vert'),
  (3, 'Sirop de sucre'),
  (4, 'Eau gazeuse'),
  (5, 'Menthe');

-- ===== Un cocktail exemple =====
INSERT INTO cocktail (id, nom, description, image_url, id_categorie) VALUES
  (1,
   'Mojito',
   'Cocktail rafraîchissant à base de rhum, citron vert et menthe.',
   'https://example.com/images/mojito.jpg',
   1);

-- ===== Prix du Mojito selon la taille =====
INSERT INTO cocktail_taille_prix (id, id_cocktail, id_taille, prix) VALUES
  (1, 1, 1, 6),   -- S : 6€
  (2, 1, 2, 8),   -- M : 8€
  (3, 1, 3, 10);  -- L : 10€

-- ===== Ingrédients du Mojito =====
INSERT INTO cocktail_ingredient (id, id_cocktail, id_ingredient, quantite) VALUES
  (1, 1, 1, '5 cl'),   -- Rhum blanc
  (2, 1, 2, '3 cl'),   -- Jus de citron vert
  (3, 1, 3, '2 cl'),   -- Sirop de sucre
  (4, 1, 5, '6 feuilles'); -- Menthe

-- ===== Création de deux utilisateurs =====
INSERT INTO utilisateur (id, nom, email, mot_de_passe, role) VALUES
  -- mot_de_passe = bcrypt('client123')
  (1, 'Alice Dupond', 'alice@example.com', '$2a$10$XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX', 'CLIENT'),
  -- mot_de_passe = bcrypt('barman123')
  (2, 'Bob LeBarman', 'bob@barapp.com', '$2a$10$YYYYYYYYYYYYYYYYYYYYYYYYYYYYYY', 'BARMAN');

-- ===== (Optionnel) Quelques autres cocktails =====
INSERT INTO cocktail (id, nom, description, image_url, id_categorie) VALUES
  (2, 'Virgin Mojito', 'Version sans alcool du mojito, très frais.', 'https://example.com/images/virgin-mojito.jpg', 3),
  (3, 'Mai Tai', 'Cocktail tiki à base de rhum ambré et curaçao.', 'https://example.com/images/mai-tai.jpg', 2);

-- Prix Virgin Mojito
INSERT INTO cocktail_taille_prix (id, id_cocktail, id_taille, prix) VALUES
  (4, 2, 1, 5),
  (5, 2, 2, 7),
  (6, 2, 3, 9);
-- Ingrédients Virgin Mojito
INSERT INTO cocktail_ingredient (id, id_cocktail, id_ingredient, quantite) VALUES
  (5, 2, 2, '3 cl'),
  (6, 2, 3, '2 cl'),
  (7, 2, 4, 'top'),
  (8, 2, 5, '6 feuilles');

-- Prix Mai Tai
INSERT INTO cocktail_taille_prix (id, id_cocktail, id_taille, prix) VALUES
  (7, 3, 1, 7),
  (8, 3, 2, 9),
  (9, 3, 3, 11);
-- Ingrédients Mai Tai
INSERT INTO cocktail_ingredient (id, id_cocktail, id_ingredient, quantite) VALUES
  (9, 3, 1, '4 cl'),
  (10, 3, 4, '2 cl'),
  (11, 3, 3, '1.5 cl');

COMMIT;
