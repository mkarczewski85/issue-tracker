-- mock departments
INSERT INTO departments (name, location)
VALUES ('Dział wsparcia technicznego', 'Budynek C, piętro 2');

INSERT INTO departments (name, location)
VALUES ('Dział księgowości', 'Budynek A, parter');


-- mock users for test profile (default password: 'password')
-- user with ADMIN role
INSERT INTO user_profiles (uuid, first_name, last_name, email, is_active, role, department_id)
VALUES ('1116041a-40b7-4419-848a-78e8c9d5b09f', 'Jan', 'Kowalski', 'admin@company.com', true, 'ADMINISTRATOR', 1);

INSERT INTO user_credentials (user_id, password_hash, salt)
VALUES (1, 'FdWbzJDMjC6kM1bY8w4KFxw6m410BBUcmgOQCw1JERk=', 'e9Cu8+ZDmqzJjN/C4xYNp8VpT4rs5pk9fv7octafP+U=');

-- user with TECHNICIAN role
INSERT INTO user_profiles (uuid, first_name, last_name, email, is_active, role, department_id)
VALUES ('cc4fa18d-8d87-4b4d-9128-1d25bbe561d1', 'Jan', 'Nowak', 'jan.nowak@company.com', true, 'TECHNICIAN', 1);

INSERT INTO user_credentials (user_id, password_hash, salt)
VALUES (2, 'FdWbzJDMjC6kM1bY8w4KFxw6m410BBUcmgOQCw1JERk=', 'e9Cu8+ZDmqzJjN/C4xYNp8VpT4rs5pk9fv7octafP+U=');

-- user with REPORTER role
INSERT INTO user_profiles (uuid, first_name, last_name, email, is_active, role, department_id)
VALUES ('57679399-2b5f-470b-a512-acc2e6940feb', 'Ewa', 'Kaczmarek', 'ewa.kaczmarek@company.com', true, 'REPORTER', 2);

INSERT INTO user_credentials (user_id, password_hash, salt)
VALUES (3, 'FdWbzJDMjC6kM1bY8w4KFxw6m410BBUcmgOQCw1JERk=', 'e9Cu8+ZDmqzJjN/C4xYNp8VpT4rs5pk9fv7octafP+U=');