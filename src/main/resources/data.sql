-- 1. Create a User to own these properties
INSERT INTO user_entity (id, name, email, password, date_of_birth, gender)
VALUES (1, 'Manish Kumar', 'manishrajrnl@zohomail.in', 'password', '2004-05-15',
        'MALE') ON CONFLICT (id) DO NOTHING;

-- 2. Insert 5 Unique Hotels
INSERT INTO hotel (id, name, city, description, rating, active, owner_id, address, email,
                   location, phone_number, created_at, updated_at)
VALUES (1, 'Ocean Breeze Resort', 'Goa',
        'Luxury beachfront resort with infinity pools and private access to the shore.', 4.8,
        true, 1, '123 Beach Road, Calangute', 'stay@oceanbreeze.com', '15.54, 73.75',
        '+91-9876543210', NOW(), NOW()),
       (2, 'Mountain Echo Lodge', 'Manali',
        'Cozy wooden cabins surrounded by cedar forests and snow-capped peaks.', 4.5, true, 1,
        'Plot 45, Old Manali Road', 'info@mountainecho.in', '32.24, 77.18', '+91-9876543211',
        NOW(), NOW()),
       (3, 'The Royal Heritage', 'Jaipur',
        'A converted haveli offering a glimpse into the lifestyle of Rajasthani royalty.', 4.9,
        true, 1, 'Palace Square, Pink City', 'royal@jaipurheritage.com', '26.91, 75.78',
        '+91-9876543212', NOW(), NOW()),
       (4, 'Urban Chic Suites', 'Bangalore',
        'Modern, high-tech apartments designed for digital nomads and business travelers.',
        4.2, true, 1, '10 MG Road, Indiranagar', 'hello@urbanchic.com', '12.97, 77.59',
        '+91-9876543213', NOW(), NOW()),
       (5, 'Serene Lake House', 'Udaipur',
        'Peaceful retreat on the banks of Lake Pichola with breathtaking sunset views.', 4.7,
        true, 1, 'Lake Side Drive, Hanuman Ghat', 'bookings@serenelake.com', '24.58, 73.68',
        '+91-9876543214', NOW(), NOW()) ON CONFLICT (id) DO NOTHING;

-- 3. Insert 40 Rooms (8 per Hotel)
-- Format: (id, hotel_id, title, description, type, city, capacity, total_count, bedrooms, beds, bathrooms, base_price, rating, review_count, created_at, updated_at)

-- Hotel 1: Goa Rooms
INSERT INTO room (id, hotel_id, title, description, type, city, capacity, total_count,
                  bedrooms, beds, bathrooms, base_price, rating, review_count, created_at,
                  updated_at)
VALUES (1, 1, 'Sunset King Suite', 'Ocean view with a king-sized bed.', 'Suite', 'Goa', 2, 5,
        1, 1, 1, 7500.00, 4.9, 12, NOW(), NOW()),
       (2, 1, 'Twin Garden Room', 'Quiet room overlooking the tropical gardens.', 'Standard',
        'Goa', 2, 10, 1, 2, 1, 4500.00, 4.4, 45, NOW(), NOW()),
       (3, 1, 'Family Beach Villa', 'Private villa with direct beach access.', 'Villa', 'Goa',
        6, 2, 3, 4, 3, 25000.00, 5.0, 8, NOW(), NOW()),
       (4, 1, 'Deluxe Terrace Room', 'First-floor room with a large private balcony.',
        'Deluxe', 'Goa', 2, 8, 1, 1, 1, 6000.00, 4.6, 33, NOW(), NOW()),
       (5, 1, 'Honeymoon Studio', 'Romantic setting with a private jacuzzi.', 'Studio', 'Goa',
        2, 3, 1, 1, 1, 9500.00, 4.8, 20, NOW(), NOW()),
       (6, 1, 'Poolside Cabana', 'Quick access to the main pool area.', 'Standard', 'Goa', 2,
        5, 1, 1, 1, 5500.00, 4.3, 15, NOW(), NOW()),
       (7, 1, 'Executive Seafront', 'Premium amenities and dedicated concierge.', 'Executive',
        'Goa', 3, 4, 1, 2, 1, 8500.00, 4.7, 10, NOW(), NOW()),
       (8, 1, 'Economy Backpacker', 'Simple, clean, and budget-friendly.', 'Economy', 'Goa', 1,
        12, 1, 1, 1, 2500.00, 4.1, 60, NOW(), NOW()),

-- Hotel 2: Manali Rooms
       (9, 2, 'Pine View Attic', 'Wood-paneled room in the attic.', 'Deluxe', 'Manali', 2, 4,
        1, 1, 1, 3800.00, 4.6, 22, NOW(), NOW()),
       (10, 2, 'Cedar Log Cabin', 'Detached cabin with a fireplace.', 'Cabin', 'Manali', 4, 3,
        2, 2, 1, 8500.00, 4.8, 18, NOW(), NOW()),
       (11, 2, 'River Side Cottage', 'Hear the sound of the Beas River.', 'Cottage', 'Manali',
        3, 2, 1, 2, 1, 5500.00, 4.7, 14, NOW(), NOW()),
       (12, 2, 'Standard Mountain Room', 'Great views at an affordable price.', 'Standard',
        'Manali', 2, 15, 1, 1, 1, 2800.00, 4.2, 50, NOW(), NOW()),
       (13, 2, 'Luxury Peak Suite', 'Highest point in the lodge with 360 views.', 'Suite',
        'Manali', 2, 1, 1, 1, 1, 12000.00, 5.0, 5, NOW(), NOW()),
       (14, 2, 'Twin Forest Room', 'Two separate beds for friends/family.', 'Standard',
        'Manali', 2, 8, 1, 2, 1, 3000.00, 4.4, 30, NOW(), NOW()),
       (15, 2, 'Backpacker Dorm Bed', 'Shared space for solo trekkers.', 'Dorm', 'Manali', 1,
        20, 1, 1, 1, 800.00, 4.0, 105, NOW(), NOW()),
       (16, 2, 'Hiker Cozy Corner', 'Compact room for minimalists.', 'Economy', 'Manali', 1, 5,
        1, 1, 1, 1800.00, 4.3, 12, NOW(), NOW()),

-- Hotel 3: Jaipur Rooms
       (17, 3, 'Maharani Suite', 'Decorated with traditional frescoes.', 'Royal Suite',
        'Jaipur', 2, 2, 1, 1, 2, 18000.00, 4.9, 15, NOW(), NOW()),
       (18, 3, 'Courtyard View Room', 'Overlooking the central fountain.', 'Standard',
        'Jaipur', 2, 12, 1, 1, 1, 5200.00, 4.7, 40, NOW(), NOW()),
       (19, 3, 'Heritage Double', 'Large windows and high ceilings.', 'Deluxe', 'Jaipur', 3,
        10, 1, 2, 1, 6800.00, 4.6, 28, NOW(), NOW()),
       (20, 3, 'Saffron Luxury Room', 'Equipped with premium silk linens.', 'Premium',
        'Jaipur', 2, 5, 1, 1, 1, 9000.00, 4.8, 10, NOW(), NOW()),
       (21, 3, 'The Haveli Attic', 'Historic charm with modern comfort.', 'Boutique', 'Jaipur',
        2, 3, 1, 1, 1, 4500.00, 4.5, 20, NOW(), NOW()),
       (22, 3, 'Prince Family Room', 'Spacious enough for a family of four.', 'Family',
        'Jaipur', 4, 4, 2, 3, 2, 11000.00, 4.7, 18, NOW(), NOW()),
       (23, 3, 'Gateway Single', 'Perfect for solo history buffs.', 'Economy', 'Jaipur', 1, 6,
        1, 1, 1, 3500.00, 4.3, 35, NOW(), NOW()),
       (24, 3, 'Royal Guard Room', 'Standard room near the entrance.', 'Standard', 'Jaipur', 2,
        8, 1, 1, 1, 4800.00, 4.4, 25, NOW(), NOW()),

-- Hotel 4: Bangalore Rooms
       (25, 4, 'Cyber Studio', 'Ergonomic chair and high-speed Wi-Fi.', 'Studio', 'Bangalore',
        1, 20, 0, 1, 1, 4200.00, 4.5, 80, NOW(), NOW()),
       (26, 4, 'Tech Nomad Loft', 'Split-level living and working space.', 'Loft', 'Bangalore',
        2, 5, 1, 1, 1, 6500.00, 4.7, 34, NOW(), NOW()),
       (27, 4, 'Business King', 'Soundproof walls for quiet meetings.', 'Executive',
        'Bangalore', 2, 15, 1, 1, 1, 5800.00, 4.4, 55, NOW(), NOW()),
       (28, 4, 'Smart Twin Room', 'Voice-controlled lighting and AC.', 'Standard', 'Bangalore',
        2, 10, 1, 2, 1, 4800.00, 4.3, 42, NOW(), NOW()),
       (29, 4, 'Penthouse Boardroom', 'Luxury suite with its own meeting table.', 'Penthouse',
        'Bangalore', 4, 1, 2, 2, 2, 15000.00, 4.9, 3, NOW(), NOW()),
       (30, 4, 'Compact Pod', 'Privacy and comfort for short stays.', 'Pod', 'Bangalore', 1,
        30, 0, 1, 1, 1500.00, 4.1, 120, NOW(), NOW()),
       (31, 4, 'The Startup Suite', 'Modern aesthetics for team offsites.', 'Suite',
        'Bangalore', 3, 5, 1, 2, 1, 8000.00, 4.6, 15, NOW(), NOW()),
       (32, 4, 'Urban Balcony Room', 'View of the bustling city skyline.', 'Deluxe',
        'Bangalore', 2, 8, 1, 1, 1, 5500.00, 4.4, 22, NOW(), NOW()),

-- Hotel 5: Udaipur Rooms
       (33, 5, 'Lake View Marvel', 'Wake up to the shimmering lake.', 'Premium', 'Udaipur', 2,
        5, 1, 1, 1, 10500.00, 4.9, 50, NOW(), NOW()),
       (34, 5, 'Sunset Balcony', 'Perfect spot for tea at dusk.', 'Deluxe', 'Udaipur', 2, 8, 1,
        1, 1, 7800.00, 4.7, 33, NOW(), NOW()),
       (35, 5, 'Traditional Mewar Room', 'Hand-painted walls and carvings.', 'Heritage',
        'Udaipur', 2, 6, 1, 1, 1, 6500.00, 4.6, 25, NOW(), NOW()),
       (36, 5, 'Poolside Retreat', 'Just steps away from the pool.', 'Standard', 'Udaipur', 2,
        10, 1, 2, 1, 5500.00, 4.4, 40, NOW(), NOW()),
       (37, 5, 'Grand Lake Villa', 'The ultimate private luxury.', 'Villa', 'Udaipur', 5, 1, 3,
        3, 3, 35000.00, 5.0, 4, NOW(), NOW()),
       (38, 5, 'Artist Studio', 'Bright and airy with great natural light.', 'Studio',
        'Udaipur', 1, 3, 1, 1, 1, 4200.00, 4.5, 12, NOW(), NOW()),
       (39, 5, 'Cozy Courtyard', 'Inward facing, very quiet.', 'Economy', 'Udaipur', 2, 5, 1,
        1, 1, 3500.00, 4.2, 18, NOW(), NOW()),
       (40, 5, 'Royal Triple', 'Best for small groups or families.', 'Family', 'Udaipur', 3, 4,
        1, 3, 1, 8500.00, 4.7, 21, NOW(), NOW()) ON CONFLICT (id) DO NOTHING;


--- 1. HOTEL PHOTOS (4 for each of the 5 Hotels) ---
INSERT INTO hotel_photos (hotel_id, photo_url)
VALUES
-- Hotel 1 (Goa)
(1, 'https://images.unsplash.com/photo-1520250497591-112f2f40a3f4'),
(1, 'https://images.unsplash.com/photo-1507525428034-b723cf961d3e'),
(1, 'https://images.unsplash.com/photo-1499793983690-e29da59ef1c2'),
(1, 'https://images.unsplash.com/photo-1519046904884-53103b34b206'),
-- Hotel 2 (Manali)
(2, 'https://images.unsplash.com/photo-1464822759023-fed622ff2c3b'),
(2, 'https://images.unsplash.com/photo-1506748686214-e9df14d4d9d0'),
(2, 'https://images.unsplash.com/photo-1470770841072-f978cf4d019e'),
(2, 'https://images.unsplash.com/photo-1501785888041-af3ef285b470'),
-- Hotel 3 (Jaipur)
(3, 'https://images.unsplash.com/photo-1590050752117-23a9d7fc21a3'),
(3, 'https://images.unsplash.com/photo-1524230572899-a752b3835840'),
(3, 'https://images.unsplash.com/photo-1598977123418-45ec0d7a960b'),
(3, 'https://images.unsplash.com/photo-1602484252774-7d5fb3f4f0b0'),
-- Hotel 4 (Bangalore)
(4, 'https://images.unsplash.com/photo-1554995207-c18c203602cb'),
(4, 'https://images.unsplash.com/photo-1522708323590-d24dbb6b0267'),
(4, 'https://images.unsplash.com/photo-1502672260266-1c1ef2d93688'),
(4, 'https://images.unsplash.com/photo-1493809842364-78817add7ffb'),
-- Hotel 5 (Udaipur)
(5, 'https://images.unsplash.com/photo-1476514525535-07fb3b4ae5f1'),
(5, 'https://images.unsplash.com/photo-1518780664697-55e3ad937233'),
(5, 'https://images.unsplash.com/photo-1439066615861-d1af74d74000'),
(5, 'https://images.unsplash.com/photo-1501785888041-af3ef285b470');


--- 2. ROOM PHOTOS (4 per Room for the first 10 Rooms) ---

-- Hotel 1 Rooms (IDs 1 to 8)
INSERT INTO room_photos (room_id, photo_url)
VALUES (1, 'https://images.unsplash.com/photo-1590490360182-c33d57733427'),
       (1, 'https://images.unsplash.com/photo-1582719478250-c89cae4dc85b'),
       (1, 'https://images.unsplash.com/photo-1595576508898-0ad5c879a061'),
       (1, 'https://images.unsplash.com/photo-1566665797739-1674de7a421a'),

       (2, 'https://images.unsplash.com/photo-1598928506311-c55ded91a20c'),
       (2, 'https://images.unsplash.com/photo-1560448204-61dc36dc98c8'),
       (2, 'https://images.unsplash.com/photo-1594741160417-066e41442f23'),
       (2, 'https://images.unsplash.com/photo-1512918766775-d2632203698c'),

       (3, 'https://images.unsplash.com/photo-1576013551627-0cc20b96c2a7'),
       (3, 'https://images.unsplash.com/photo-1584132967334-10e028bd69f7'),
       (3, 'https://images.unsplash.com/photo-1590073242678-70ee3fc28e8e'),
       (3, 'https://images.unsplash.com/photo-1582719471384-894fbb16e024'),

       (4, 'https://images.unsplash.com/photo-1591088398332-8a77d399e843'),
       (4, 'https://images.unsplash.com/photo-1512917774080-9991f1c4c750'),
       (4, 'https://images.unsplash.com/photo-1574643156929-51fa098b0394'),
       (4, 'https://images.unsplash.com/photo-1596394516093-501ba68a0ba6'),

       (5, 'https://images.unsplash.com/photo-1616594039964-ae9021a400a0'),
       (5, 'https://images.unsplash.com/photo-1502672023488-70e25813efdf'),
       (5, 'https://images.unsplash.com/photo-1522708323590-d24dbb6b0267'),
       (5, 'https://images.unsplash.com/photo-1493809842364-78817add7ffb'),

       (6, 'https://images.unsplash.com/photo-1586023492125-27b2c045efd7'),
       (6, 'https://images.unsplash.com/photo-1560184897-af75bb79f053'),
       (6, 'https://images.unsplash.com/photo-1560185007-c5ca9d2c014d'),
       (6, 'https://images.unsplash.com/photo-1560185127-6ed189bf02f4'),

       (7, 'https://images.unsplash.com/photo-1596178060671-7a80dc8059ea'),
       (7, 'https://images.unsplash.com/photo-1590490359854-dfba19688d70'),
       (7, 'https://images.unsplash.com/photo-1595526114035-0d45ed16cfbf'),
       (7, 'https://images.unsplash.com/photo-1540518614846-7eded433c457'),

       (8, 'https://images.unsplash.com/photo-1554995207-c18c203602cb'),
       (8, 'https://images.unsplash.com/photo-1502672260266-1c1ef2d93688'),
       (8, 'https://images.unsplash.com/photo-1493809842364-78817add7ffb'),
       (8, 'https://images.unsplash.com/photo-1512917774080-9991f1c4c750'),

-- Hotel 2 Rooms (Starting IDs from 9)
       (9, 'https://images.unsplash.com/photo-1571508601891-ca5ee7a31cd2'),
       (9, 'https://images.unsplash.com/photo-1571508602690-3482598375c3'),
       (9, 'https://images.unsplash.com/photo-1571508602693-13833299c855'),
       (9, 'https://images.unsplash.com/photo-1571508602695-13d8098a58f4'),

       (10, 'https://images.unsplash.com/photo-1512918766775-d2632203698c'),
       (10, 'https://images.unsplash.com/photo-1512918583421-4d92408ed086'),
       (10, 'https://images.unsplash.com/photo-1512918582312-d24dbb6b0267'),
       (10, 'https://images.unsplash.com/photo-1512918642345-d24dbb6b0267');