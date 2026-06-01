USE savethechildren_volunteer_db;

-- Insert Catalogs first

INSERT INTO skill_Catalog (skill_code, acquired_skills) VALUES
('S01', 'Accounting & Finance'),
('S02', 'Arts & Communication & Graphics'),
('S03', 'Management'),
('S04', 'Engineering'),
('S05', 'Human Resources'),
('S06', 'Law'),
('S07', 'Marketing'),
('S08', 'Livelihoods'),
('S09', 'Training and Facilitation'),
('S10', 'Networking, IT & Programming Skills'),
('S11', 'Emergency Response');

INSERT INTO interest_Catalog (interest_code, areas_of_interest) VALUES
('IC1', 'Program Quality and Development Support'),
('IC2', 'Project Implementation Support'),
('IC3', 'Office Support');


-- Insert Applicants (A001 to A010)

INSERT INTO applicant_Details (
    applicant_id, date_submitted, last_name, first_name, middle_name, nickname, address, phone_landline, mobile_phone, permanent_address,
    gender, email_address, birthdate, birthplace, citizenship, height, weight, employer_name, highest_educ_level, educ_school_and_address,
    educ_from, educ_to, educ_degree, availability_option, hours_per_day, willing_to_deploy, willing_humanitarian,
    reference_name_relationship, company_org_info, position, contact_person, contact_phone_no, contact_relationship, contact_address
) VALUES
('A001', '2023-01-10', 'Dope', 'Shawn', 'Ty', 'Shawn', '123 Pureza St. Brgy. Sta.Mesa Manila City', '02-8123-4567', '0994 098 0987', '123 Pureza St. Brgy. Sta.Mesa Manila City',
 'Male', 'ShawnDope@gmail.com', '1990-01-19', 'Manila', 'Filipino', 175.00, 70.00, NULL, 'Post Graduate', 'University of the Philippines',
 2012, 2016, 'MA Education', 'Weekends', 8, 1, 1,
 'Maria Santos-Colleague', 'Teach for the Philippines', 'Teacher', 'John Dope', '0994 098 0988', 'Brother', '123 Pureza St. Brgy. Sta.Mesa Manila City'),

('A002', '2023-01-11', 'Go', 'Tim', 'Eton', 'Tim', '124 Teresa St. Brgy. Sta. Mesa Manila City', '01-2132-4545', '0999 876 5642', '124 Teresa St. Brgy. Sta. Mesa Manila City',
 'Male', 'GoTimE@gmail.com', '1991-02-18', 'Makati', 'Filipino', 170.00, 73.00, 'Marcus Mendell', 'Post Graduate', 'De La Salle University',
 2013, 2017, 'MS Engineering', 'As Required', 4, 1, 1,
 'Jose Rizal-Manager', 'Build Right Corp', 'Engineer', 'Anna Go', '0999 876 5643', 'Sister', '124 Teresa St. Brgy. Sta. Mesa Manila City'),

('A003', '2023-01-12', 'Cruz', 'Maria', 'Santos', 'Maria', '456 Quezon Ave Quezon City', '02-8999-1111', '0917 111 2222', '456 Quezon Ave Quezon City',
 'Female', 'maria.cruz@gmail.com', '1992-05-14', 'Quezon City', 'Filipino', 160.00, 55.00, 'HealthFirst Clinic', 'Post Graduate', 'UP Diliman',
 2014, 2018, 'MD', 'Weekends', 12, 1, 1,
 'Dr. Reyes-Mentor', 'General Hospital', 'Doctor', 'Juan Cruz', '0917 222 3333', 'Father', '456 Quezon Ave Quezon City'),

('A004', '2023-01-13', 'Lim', 'David', 'Chua', 'Dave', '789 Roxas Blvd Manila', '02-8777-2222', '0918 333 4444', '789 Roxas Blvd Manila',
 'Male', 'dlim@yahoo.com', '1988-11-02', 'Manila', 'Filipino', 178.00, 80.00, NULL, 'Tertiary', 'Ateneo de Manila',
 2006, 2010, 'BS Business', 'After Office Hours', 4, 0, 1,
 'Mark Tan-Friend', 'XYZ Corp', 'Analyst', 'Susan Lim', '0918 444 5555', 'Mother', '789 Roxas Blvd Manila'),

('A005', '2023-01-14', 'Reyes', 'Ana', 'Bautista', 'Ana', '321 Taft Ave Pasay City', '02-8666-3333', '0919 555 6666', '321 Taft Ave Pasay City',
 'Female', 'ana.reyes@hotmail.com', '1995-07-22', 'Pasay', 'Filipino', 165.00, 60.00, 'Creative Studio', 'Tertiary', 'UST',
 2013, 2017, 'BFA Fine Arts', 'Days of the Week', 8, 1, 0,
 'Leo Cruz-Manager', 'Creative Studio', 'Designer', 'Peter Reyes', '0919 666 7777', 'Husband', '321 Taft Ave Pasay City'),

('A006', '2023-01-15', 'Tan', 'Kevin', 'Sy', 'Kev', '654 EDSA Mandaluyong', '02-8555-4444', '0920 777 8888', '654 EDSA Mandaluyong',
 'Male', 'kevintan@gmail.com', '1993-09-09', 'San Juan', 'Filipino', 172.00, 75.00, 'Tech Solutions', 'Tertiary', 'FEU',
 2011, 2015, 'BS IT', 'Holidays', 6, 1, 1,
 'Paul Gomez-Colleague', 'Tech Solutions', 'Dev', 'Linda Tan', '0920 888 9999', 'Sister', '654 EDSA Mandaluyong'),

('A007', '2023-01-16', 'Garcia', 'Luis', 'Perez', 'Luis', '987 Makati Ave Makati', '02-8444-5555', '0921 999 0000', '987 Makati Ave Makati',
 'Male', 'luis.g@yahoo.com', '1987-12-01', 'Makati', 'Filipino', 180.00, 85.00, 'Law Firm LLC', 'Post Graduate', 'Ateneo Law',
 2009, 2013, 'JD', 'As Required', 5, 0, 0,
 'Atty. Cruz-Partner', 'Law Firm LLC', 'Lawyer', 'Rosa Garcia', '0921 000 1111', 'Wife', '987 Makati Ave Makati'),

('A008', '2023-01-17', 'Mendoza', 'Elena', 'Roxas', 'Len', '159 C5 Road Taguig', '02-8333-6666', '0922 111 2222', '159 C5 Road Taguig',
 'Female', 'elena.m@gmail.com', '1996-03-30', 'Taguig', 'Filipino', 158.00, 52.00, NULL, 'Secondary', 'Taguig Science High',
 2008, 2012, 'High School', 'Weekends', 4, 1, 1,
 'Mr. Santos-Teacher', 'Taguig Science High', 'Teacher', 'Rico Mendoza', '0922 222 3333', 'Father', '159 C5 Road Taguig'),

('A009', '2023-01-18', 'Bautista', 'Chris', 'Luna', 'Topher', '753 Ortigas Pasig', '02-8222-7777', '0923 333 4444', '753 Ortigas Pasig',
 'Male', 'chrisb@hotmail.com', '1994-08-15', 'Pasig', 'Filipino', 176.00, 78.00, 'Marketing Pro', 'Tertiary', 'DLSU',
 2012, 2016, 'BS Marketing', 'After Office Hours', 3, 1, 0,
 'Jane Doe-Client', 'Marketing Pro', 'Manager', 'Sarah Bautista', '0923 444 5555', 'Mother', '753 Ortigas Pasig'),

('A010', '2023-01-19', 'Villanueva', 'Diana', 'Torres', 'Diane', '852 Alabang Muntinlupa', '02-8111-8888', '0924 555 6666', '852 Alabang Muntinlupa',
 'Female', 'dianav@gmail.com', '1989-04-25', 'Muntinlupa', 'Filipino', 162.00, 58.00, 'HR Solutions', 'Tertiary', 'UP Diliman',
 2007, 2011, 'BS Psychology', 'Holidays', 8, 0, 1,
 'Mark Reyes-Boss', 'HR Solutions', 'HR Head', 'Tom Villanueva', '0924 666 7777', 'Husband', '852 Alabang Muntinlupa');


-- Insert Languages
INSERT INTO applicant_Languages (applicant_id, languages_spoken) VALUES
('A001', 'English'),
('A001', 'Tagalog'),
('A002', 'English'),
('A002', 'Tagalog'),
('A003', 'English'),
('A003', 'Tagalog'),
('A004', 'English'),
('A004', 'Tagalog'),
('A004', 'Hokkien'),
('A005', 'English'),
('A005', 'Cebuano'),
('A006', 'English'),
('A006', 'Tagalog'),
('A007', 'English'),
('A007', 'Spanish'),
('A008', 'Tagalog'),
('A009', 'English'),
('A010', 'English'),
('A010', 'Tagalog');


-- Insert License and Certifications
INSERT INTO applicant_licenseCertification (applicant_id, licenses_certifications, date_issued) VALUES
('A001', 'PRC License', '2016-10-15'),
('A002', 'Civil Engineer', '2018-04-10'),
('A003', 'PRC Medical License', '2018-11-20'),
('A005', 'Graphic Design Cert', '2019-05-12'),
('A007', 'Bar Exam Passer', '2014-04-20');


-- Insert Volunteer Engagements
INSERT INTO previous_volunteer_Engagement (applicant_id, name_of_company, position_held, length_of_service_from, length_of_service_to, volunteer_engagement_summary) VALUES
('A001', 'Gawad Kalinga', 'Finance Volunteer', '2018-01-10', '2019-06-15', 'Assisted in auditing community funds.'),
('A002', 'Habitat for Humanity', 'Site Engineer', '2019-03-01', '2021-12-20', 'Supervised volunteer construction teams.'),
('A004', 'Red Cross', 'Relief Worker', '2015-08-01', '2016-11-30', 'Helped pack and distribute relief goods.'),
('A006', 'Code for PH', 'IT Mentor', '2020-06-15', '2022-03-30', 'Taught basic programming to public school students.'),
('A010', 'Save the Children', 'HR Volunteer', '2018-04-01', '2020-09-15', 'Assisted in screening and onboarding new volunteers.');


-- Insert Applicant Skills
INSERT INTO applicant_Skills (applicant_id, skill_code) VALUES
('A001', 'S01'),
('A001', 'S05'),
('A002', 'S04'),
('A002', 'S11'),
('A003', 'S09'),
('A003', 'S11'),
('A004', 'S03'),
('A005', 'S02'),
('A006', 'S10'),
('A007', 'S06'),
('A008', 'S09'),
('A009', 'S07'),
('A010', 'S05');


-- Insert Applicant Interests
INSERT INTO applicant_Interests (applicant_id, interest_code) VALUES
('A001', 'IC2'),
('A002', 'IC1'),
('A002', 'IC3'),
('A003', 'IC3'),
('A004', 'IC1'),
('A005', 'IC2'),
('A006', 'IC1'),
('A007', 'IC1'),
('A008', 'IC2'),
('A009', 'IC2'),
('A010', 'IC3');
