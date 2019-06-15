Create Table person(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email_id TEXT);
Create Table passport_details(_id INTEGER PRIMARY KEY AUTOINCREMENT, passport_number TEXT, person_id INTEGER, foreign key (person_id) references person(_id) on delete cascade);
