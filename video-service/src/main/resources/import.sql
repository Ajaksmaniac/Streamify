INSERT INTO roles (name) VALUES ('admin'), ('content_creator'), ('user');
INSERT INTO user (username, password, isActive, role_id) VALUES ('test','test',true,1);
