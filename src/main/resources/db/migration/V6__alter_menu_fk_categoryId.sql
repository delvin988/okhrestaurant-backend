ALTER TABLE menus
DROP COLUMN category;

ALTER TABLE menus
ADD COLUMN category_id BIGINT;

ALTER TABLE menus
ADD CONSTRAINT fk_menu_category
FOREIGN KEY (category_id)
REFERENCES menu_categories(id);