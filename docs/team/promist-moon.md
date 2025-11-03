# Promist-Moon
Emilia Ayu Binti Yuhasnor Affandy
Y2, Computer Science

## Overview
Tuiniverse is a **desktop app** which <u>expedites</u>:
* Contact storing and upkeep
* Payment tracking
* Lesson scheduling

## Summary of Contributions
### Code contributed
The link to my code contribution is listed [here](https://nus-cs2103-ay2526s1.github.io/tp-dashboard/?search=promist-moon&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2025-09-19T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=) and collated by RepoSense.

### Enhancements implemented
A summary of the enhancements I implemented were as follows:<br>
**Commands**
* Pay Command
* View Command

In addition to these commands and their parsers, I implemented the utility parser IndexCommandParser. Instead of duplicating the same parsing logic
(such as verifying index validity, handling out-of-bounds errors, and converting strings to Index objects) across multiple individual
command parsers, we extracted this functionality into IndexCommandParser. This is in accordance to the DRY principle.

**Payment**
I was in charge of designing and implementing the Payment package, which handles the financial tracking aspect of Tuiniverse. This includes logic
for calculating monthly bills based on each student’s scheduled lessons, tracking paid and unpaid amounts, and determining overall payment statuses
(e.g., Paid, Unpaid, Overdue).

My contributions included:
* Implementing the PaymentList and Payment classes, enabling each student to store monthly payment records and track their total and unpaid amounts.
* Developing the Amount Panel UI, which dynamically displays the total earnings for the current month and the total unpaid balance across all students.
* Integrating payment logic with the Lesson and Student models through JavaFX listeners and property bindings, ensuring that changes in lesson schedules,
rates, or student edits are automatically reflected in the computed payments.

This end-to-end integration between lessons, payments, and students allows tutors to view their monthly revenue and outstanding dues in real time

### Contributions to the User Guide
I added the glossary to the User Guide.

### Contributions to the Developer Guide
