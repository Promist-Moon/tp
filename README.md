[![CI Status](https://github.com/se-edu/addressbook-level3/workflows/Java%20CI/badge.svg)](https://github.com/se-edu/addressbook-level3/actions)
[![codecov](https://codecov.io/github/AY2526S1-CS2103T-W08-2/tp/graph/badge.svg?token=A35KPO3UXB)](https://codecov.io/github/AY2526S1-CS2103T-W08-2/tp)

# Tuiniverse

---

## Table of Contents

- [About](#about)
- [UI](#ui)
- [Features](#features)
- [Commands](#commands)
- [Acknowledgements](#acknowledgements)

---

## About
*Tuiniverse* is built for freelance secondary school tutors to stay organized. 

What it does
- Manage student and parent contacts
- Track attendance, student's progress and payments 
- Schedule classes and prevent clashes
- Check scheduled lessons

*Tuiniverse* saves time and spares tutors of the confusion that can arise from manual searching and tracking of students and payments.

---

## UI

Our UI is readable and concise, providing tutors only with information they need and want.

![Ui](docs/images/Ui.png)

---

## Features
1. Add Contact
2. Delete Contact
3. Add Class
4. Delete Class
5. Make Payment
6. List all payments
7. List all unpaid
8. List all overdue
9. Find student
10. Filter students

---

## Commands
### Add Contact
Add a new contact with the necessary details

`add /n <name> /c <contactNumber> /a <address> /p <parentName>
`

### Delete Contact
Delete an existing contact

`delete /i <student index>`

### Add Class
Adds a class to the specific student

`add.class /i <student index> /s <subject> /l <level> /d <day> /s <start time> /e <end time> /r <hourly rate>
`

### Delete Class
Deletes an existing class from a specific student

`delete.class /i <student index> /c <class index>`

### Make Payment
Tracks that a student has made payment

`pay <student index>`

### List all payments
Lists all students that have paid the fees

`list.paid`

### List all unpaid
Lists all students that have unpaid fees

`list.unpaid`

### List all overdue
Lists all students that have overdue fees

`list.overdue`

### Find Student
Finds existing student

`find /n <name> /s <subject> /l <level>`

### Filter Students
Filters existing students by subject and/or level

`filter /s <subject> /l <level>`

---

## Acknowledgements
This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).
