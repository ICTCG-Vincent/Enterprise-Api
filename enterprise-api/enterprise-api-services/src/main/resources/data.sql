INSERT INTO address (id, street, city)
VALUES
(1, 'Enterprise_street1', 'city1'),
(2, 'Enterprise_street2', 'city2'),
(3, 'Enterprise_street3', 'city3'),
(4, 'Contact_street4', 'city4'),
(5, 'Contact_street5', 'city5'),
(6, 'Contact_street6', 'city6'),
(7, 'Contact_street7', 'city7'),
(8, 'Contact_street7', 'city7');

INSERT INTO contact (id, name, firstname, status, tva, address_id)
VALUES
(1, 'Book', 'Paul', 'Freelance', 'BE0723600303', 5),
(2, 'Watch', 'Luc', 'Employee', null, 6),
(3, 'Shoes', 'Simon', 'Employee', null, 7),
(4, 'Van Uytven', 'Vincent', 'Freelance','BE0723600303', 8);

INSERT INTO enterprise (id, name, tva)
VALUES
(1, 'Genesis', 'BE0723600303'),
(2, 'RealDolmen', 'BE0723600303'),
(3, 'ICTCG', 'BE0723600303'),
(4, 'ICTCG-OTHER', 'BE0723600303');

INSERT INTO siege_address (enterprise_id, address_id)
VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 3)
;

INSERT INTO ENTERPRISE_ADDRESSES (enterprise_id, address_id)
VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4)
;

INSERT INTO enterprise_contact (contact_id, enterprise_id)
VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(4, 3),
(4, 1)


;

