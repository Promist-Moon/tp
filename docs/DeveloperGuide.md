---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# Tuiniverse Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

Our project is built based on the foundation design and documentation structure of [AddressBook-Level3](https://github.com/nus-cs2103-AY2526S1/tp) (AB3) by the SE-EDU team, as provided in the CS2103T module at the National University of Singapore (NUS).

We also referenced SE-EDU’s [AB3 Developer Guide](https://se-education.org/addressbook-level3/DeveloperGuide.html) and [Sample MarkBind sites](https://damithc.github.io/ab3-markbind/) given in the CS2103T module for documentation and diagram formatting conventions.

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams are in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2526S1-CS2103T-W08-2/tp/blob/master/src/main/java/tutman/tuiniverse/Main.java) and [`MainApp`](https://github.com/AY2526S1-CS2103T-W08-2/tp/blob/master/src/main/java/tutman/tuiniverse/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2526S1-CS2103T-W08-2/tp/blob/master/src/main/java/tutman/tuiniverse/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2526S1-CS2103T-W08-2/tp/blob/master/src/main/java/tutman/tuiniverse/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2526S1-CS2103T-W08-2/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2526S1-CS2103T-W08-2/tp/blob/master/src/main/java/tutman/tuiniverse/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/AY2526S1-CS2103T-W08-2/tp/blob/master/src/main/java/tutman/tuiniverse/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Student` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Student` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Student>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** Alternative (arguably, a more OOP) models are given below. The diagram below has a `Tag` list in the `AddressBook`, which `Student` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Student` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

To fully visualise `PaymentList`, the `Student` holds a `PaymentList`. The `PaymentList` is associated with a `PaymentStatus` enum. This allows for quick `PaymentStatus` checks for a `Student` object.
<puml src="diagrams/PaymentClassDiagram.puml" width="250" />

In the same vein `Student` holds a `LessonList`, which contains any amount of `Lesson` as shown in the diagram below
<puml src="diagrams/LessonClassDiagram.puml" width="400" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/AY2526S1-CS2103T-W08-2/tp/blob/master/src/main/java/tutman/tuiniverse/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `tutman.tuiniverse.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Implemented\] Payment feature

The Payment feature allows tutors to automatically track and manage tuition fees for each student.
Each student has a PaymentList containing monthly Payment objects, which record the total and unpaid amounts for that month. Refer to [Model](#model-component) class diagram for payment details.
Whenever lessons are added, edited, or deleted, Tuiniverse automatically recalculates the corresponding monthly payment to keep all financial records consistent.

1. **`pay` command**
   <puml src="diagrams/PaySequenceDiagram.puml" width="250" />

Tutor asks Student to pay; the Student delegates to PaymentList method `markAllPaid()`, which finds unpaid payments, and marks each one as paid. This is done by the `markPaid` method in `Payment`,
which zeroes out unpaid amount objects, and updates payment status. If there are no unpaids, it returns a message in the consolve instead.

Commands like PayCommand mark payments as settled, updating both the UI and underlying model in real time.

#### Design considerations:

* The PaymentList aggregates all payments per student and provides utility methods such as calculateUnpaidAmount() and getTotalAmountFloat().
* The ModelManager maintains observable properties (totalEarningsProperty, totalUnpaidProperty) that automatically update whenever any student’s payment data changes in UI.

### \[Implemented\] Monthly rollover feature

The Monthly Rollover feature automatically generates new monthly payment entries for each student when a new month begins.
This ensures tutors always start each month with up-to-date payment records without having to add them manually.

#### Proposed Implementation

* Implemented in ModelManager#rolloverMonthlyPayments() and invoked during app startup or when DateTimeUtil.currentYearMonth() differs from the last recorded month in UserPrefs.
* For each student, the system creates a new Payment entry for the current month based on their latest LessonList and carries over any unpaid balances from the previous month.
* The logic ensures idempotence (no duplicate months added).

{ to be updated with graph }

#### Design considerations:

{ to be updated }


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* has a need to manage a significant number of contacts
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: manage contacts faster than a typical mouse/GUI driven app


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                      | I want to …​                                            | So that I can…​                                                        |
|----------|----------------------------------------------|---------------------------------------------------------|------------------------------------------------------------------------|
| `* * *`  | new user                                     | see usage instructions                                  | refer to instructions when I forget how to use the App                 |
| `* * *`  | tutor                                        | add a new student                                       |                                                                        |
| `* * *`  | tutor                                        | delete a student                                        | remove entries that I no longer need                                   |
| `* * *`  | tutor                                        | edit a person's information                             | update their information                                               |
| `* * *`  | tutor                                        | find a person by name                                   | locate details of persons without having to go through the entire list |
| `* *`    | tutor with many students in the address book | sort students by name                                   | locate a student easily                                                |
| `* *`    | tutor                                        | see students' payment status                            | track who has and has not paid                                         |
| `* *`    | tutor                                        | list students who have not made payment                 | track students who have not paid                                       |
| `* *`    | tutor                                        | view my schedule for today                              | know which students I will be teaching today                           |
| `*`      | busy tutor                                   | view upcoming lessons for a certain day                 | plan, prepare and organise lesson materials in advance                 |
| `* * *`  | tutor                                        | add a new lesson                                        | view lesson details                                                    |
| `* *`    | forgetful tutor                              | be prevented from adding lessons of conflicting timings | avoid accidentally holding two lessons at the same time                |
| `* *`    | tutor                                        | see students' bill for the month                        | track each student's bill                                              |
| `*`      | tutor motivated to see students improve      | add a tag for a student before their lesson             | track lesson content for the student                                   |
| `*`      | tutor motivated to see students improve      | edit a student's tag after a lesson                     | check student's progress                                               |
| `*`      | tutor who teaches multiple subjects          | filter students by subject                              | prepare resources and reuse lesson materials for similar lessons       |
| `*`      | tutor who teaches multiple subjects          | organise students by subject                            | better plan lesson materials and lesson outlines                       |

### Use cases

---

## **Use Case List**

1. [UC-AddStudent](#uc-addstudent)
2. [UC-EditStudent](#uc-editstudent)
3. [UC-AddLesson](#uc-addlesson)
4. [UC-Pay](#uc-pay)
5. [UC-FindStudent](#uc-findstudent)
6. [UC-DeleteStudent](#uc-deletestudent)
7. [UC-DeleteLesson](#uc-deletelesson)
8. [UC-EditLesson](#uc-editlesson)
9. [UC-ListPaid](#uc-listpaid)
10. [UC-ListUnpaid](#uc-listunpaid)
11. [UC-ListOverdue](#uc-listoverdue)
12. [UC-ViewLessons](#uc-viewlessons)
13. [UC-AddAndBillStudent](#uc-addandbillstudent)
14. [UC-QuitStudent](#uc-quitstudent)
15. [UC-SeeSummaryForMonth](#uc-seesummaryformonth)
16. [UC-SeeNewMonthPayments](#uc-seenewmonthpayments)

---

## **UC-AddStudent**

**System:** Tuiniverse Student Management System (TSMS)
**Use case:** UC-AddStudent
**Actor:** Tutor

### **Guarantees:**
- Student appears in the student contact list with payment status **Paid** and amount unpaid **$0.00**.

### **MSS**
1. Tutor chooses to add a student.
2. Tutor enters all details in order.
3. TSMS validates the details:
    - Name, address, contact number, and email fields are non-blank.
    - Contact number must contain only numerical digits.
4. TSMS creates the student and displays a message confirming successful creation.
   **Use case ends.**

### **Extensions**
- **3a.** TSMS detects an error in a field.
    - **3a1.** TSMS returns an error message.
    - **3a2.** TSMS clears user input.
      **Use case ends.**

- **3b.** TSMS detects unknown command markers.
    - **3b1.** TSMS returns an error message.
    - **3b2.** TSMS clears user input.
      **Use case ends.**

---

## **UC-EditStudent**

**System:** Tuiniverse Student Management System (TSMS)
**Use case:** UC-EditStudent
**Actor:** Tutor

### **Preconditions**
- There is an existing student with the specified index.

### **Guarantees:**
- Student details change in the student contact list.

### **MSS**
1. Tutor chooses to edit a student detail (eg address, phone number, tag).
2. Tutor enters the student index followed by the amended detail.
3. TSMS validates the details:
    - Student currently exists.
    - Contact number must contain only numerical digits.
4. TSMS edits the student detail and displays a message confirming successful edition.
   **Use case ends.**

### **Extensions**
- **3a.** TSMS detects an error in a field.
    - **3a1.** TSMS returns an error message.
    - **3a2.** TSMS clears user input.
      **Use case ends.**

- **3b.** TSMS detects unknown command markers.
    - **3b1.** TSMS returns an error message.
    - **3b2.** TSMS clears user input.
      **Use case ends.**

- **3c.** TSMS cannot find a student with that index.
    - **2a1.** TSMS returns an error message.
    - **2a2.** TSMS clears user input.
      Steps **2a1–2a2** repeat until valid input is entered.
      **Use case ends.**

---

## **UC-AddLesson**

**System:** Tuiniverse Student Management System (TSMS)
**Use case:** UC-AddLesson
**Actor:** Tutor

### **Preconditions**
- There is an existing student with the specified index.

### **Guarantees:**
- Amount unpaid for the student changes.
- Student's payment status changes to **Unpaid**.

### **MSS**
1. User adds a lesson using the command.
2. TSMS checks that the student index exists.
3. TSMS validates the lesson details:
    - Subject matches existing subjects.
    - Level matches existing levels.
    - Day is a valid day.
    - Time is valid.
    - Hourly rate is a number.
4. TSMS checks for clashes in the timetable.
5. TSMS creates and saves the lesson, linking it to the student.
   **Use case ends.**

### **Extensions**
- **1a.** Tutor omits a field.
    - **1a1.** TSMS returns an error message.
    - **1a2.** TSMS clears user input.
      Steps **1a1–1a2** repeat until valid input is entered.
      **Use case ends.**

- **2a.** TSMS cannot find a student with that index.
    - **2a1.** TSMS returns an error message.
    - **2a2.** TSMS clears user input.
      Steps **2a1–2a2** repeat until valid input is entered.
      **Use case ends.**

- **4a.** TSMS detects a timetable clash.
    - **4a1.** TSMS returns an error message.
    - **4a2.** TSMS clears user input.
      **Use case ends.**

- **5a.** Tutor adds a student for the current day.
    - **5a1.** Lesson appears in the lesson list.
      **Use case ends.**

---

## **UC-Pay**

**System:** Tuiniverse Student Management System (TSMS)
**Use case:** UC-Pay
**Actor:** Tutor

### **Preconditions**
- There is an existing student with the specified index.

### **Guarantees:**
- Student's payment status changes to **Paid**.
- Student's amount unpaid changes to `$0.00`.
- Student shows up in list of paid students.
- Total unpaid in amount panel will decrease.

### **MSS**
1. Tutor chooses a student to mark as paid.
2. TSMS validates the student index.
3. TSMS verifies that the student has outstanding payment.
4. TSMS updates the student’s payment status to *Paid*.
5. TSMS updates the amount unpaid under student to `$0.00`.
6. TSMS decreases the amount unpaid in the amount panel.
   **Use case ends.**

### **Extensions**
- **1a.** Tutor omits input.
    - **1a1.** TSMS returns an error message.
    - **1a2.** TSMS clears user input.
      Steps **1a1–1a2** repeat until valid input is entered.
      **Use case ends.**

- **2a.** TSMS cannot find a student with that index.
    - **2a1.** TSMS returns an error message.
    - **2a2.** TSMS clears user input.
      Steps **2a1–2a2** repeat until valid input is entered.
      **Use case ends.**

- **3a.** Student has no outstanding payment.
    - **3a1.** TSMS returns an error message stating the student has paid.
      **Use case ends.**

---

## **UC-FindStudent**

**System:** Tuiniverse Student Management System (TSMS)
**Use case:** UC-FindStudent
**Actor:** Tutor

### **Guarantees:**
- Students whose name matching specified keyword shows up in contact list panel.

### **MSS**
1. Tutor chooses to find a student.
2. TSMS checks that the student with the given keyword exists.
3. TSMS lists the students with names matching the keywords.
   **Use case ends.**

### **Extensions**
- **1a.** Tutor does not enter a keyword.
    - **1a1.** TSMS returns an error message.
    - **1a2.** TSMS clears user input.
      Steps **1a1–1a2** repeat until valid input is entered.
      **Use case ends.**

---

## **UC-DeleteStudent**

**System:** Tuiniverse Student Management System (TSMS)
**Use case:** UC-DeleteStudent
**Actor:** Tutor

### **Preconditions**
- There is an existing student with the specified index.

### **Guarantees:**
- Student no longer appears in contact list panel.
- Student's lessons get removed from lesson list.
- Total earned for month and total unpaid will decrease.

### **MSS**
1. Tutor chooses to delete a student.
2. TSMS validates whether the student exists.
3. TSMS displays a confirmation prompt.
4. Tutor confirms the deletion.
5. TSMS deletes the student and all associated data (e.g., lessons).
   **Use case ends.**

### **Extensions**
- **1a.** Tutor omits the student index.
    - **1a1.** TSMS returns an error message.
    - **1a2.** TSMS clears user input.
      Steps **1a1–1a2** repeat until valid input is entered.
      **Use case ends.**

- **2a.** TSMS cannot find a student with that index.
    - **2a1.** TSMS returns an error message.
    - **2a2.** TSMS clears user input.
      Steps **2a1–2a2** repeat until valid input is entered.
      **Use case ends.**

- **3a.** Tutor chooses not to confirm deletion.
    - **3a1.** TSMS closes the prompt.
    - **3a2.** TSMS clears user input.
      **Use case ends.**

---

## **UC-DeleteLesson**

**System:** Tuiniverse Student Management System (TSMS)
**Use case:** UC-DeleteLesson
**Actor:** Tutor

### **Preconditions**
- There is an existing student with the specified index.
- There is an existing lesson with the specified index.

### **Guarantees:**
- Lesson no longer appears in lesson list panel.
- Total earned for month and total unpaid will decrease.

### **MSS**
1. Tutor deletes a lesson with the command.
2. TSMS checks that the student index exists.
3. TSMS removes the lesson from the timetable and unlinks it from the student.
   **Use case ends.**

### **Extensions**
- **1a.** Tutor omits a field.
    - **1a1.** TSMS returns an error message.
    - **1a2.** TSMS clears user input.
      Steps **1a1–1a2** repeat until valid input is entered.
      **Use case ends.**

- **2a.** TSMS cannot find a student with that index.
    - **2a1.** TSMS returns an error message.
    - **2a2.** TSMS clears user input.
      Steps **2a1–2a2** repeat until valid input is entered.
      **Use case ends.**

---

## **UC-EditLesson**

**System:** Tuiniverse Student Management System (TSMS)
**Use case:** UC-EditLesson
**Actor:** Tutor

### **Preconditions**
- There is an existing student with the specified index.
- There is an existing lesson with the specified index.

### **Guarantees:**
- Lesson details change.
- If rate is edited, student's amount unpaid will change.
- If rate is edited, total earned for month and total unpaid will change.
- 
### **MSS**
1. Tutor edits a lesson with the command.
2. TSMS checks that the student index exists.
3. TSMS edits the lesson from the timetable.
   **Use case ends.**

### **Extensions**
- **1a.** Tutor omits a field.
    - **1a1.** TSMS returns an error message.
    - **1a2.** TSMS clears user input.
      Steps **1a1–1a2** repeat until valid input is entered.
      **Use case ends.**

- **1b.** Tutor edits lesson time with a clash.
    - **1b1.** TSMS returns an error message.
      Steps **1b1–1b2** repeat until valid input is entered.
      **Use case ends.**

- **2a.** TSMS cannot find a student with that index.
    - **2a1.** TSMS returns an error message.
    - **2a2.** TSMS clears user input.
      Steps **2a1–2a2** repeat until valid input is entered.
      **Use case ends.**

- **3a.** Tutor edits rate of lesson.
    - **3a1.** TSMS recalculates amount unpaid for student.
    - **3a2.** TSMS recalculates amount unpaid for all students.
      **Use case ends.**

---

## **UC-ListPaid**

**System:** Tuiniverse Student Management System (TSMS)
**Use case:** UC-ListPaid
**Actor:** Tutor

**Guarantees:**
- Lists paid students who have paid for the current month in contact list panel.

### **MSS**
1. Tutor chooses to list paid students.
2. TSMS retrieves all students marked *Paid* for the current month.
3. TSMS displays the list of students and their amounts.
   **Use case ends.**

---

## **UC-ListUnpaid**

**System:** Tuiniverse Student Management System (TSMS)
**Use case:** UC-ListUnpaid
**Actor:** Tutor

**Guarantees:**
- Lists unpaid students who have not paid for the current month in contact list panel.
- Lists corresponding amount owed by each student.

### **MSS**
1. Tutor chooses to list unpaid students.
2. TSMS retrieves all students not marked *Paid* for the current month.
3. TSMS displays the list of students and their owed amounts.
   **Use case ends.**

---

## **UC-ListOverdue**

**System:** Tuiniverse Student Management System (TSMS)
**Use case:** UC-ListOverdue
**Actor:** Tutor

**Guarantees:**
- Lists overdue students who have not paid for previous months in contact list panel.
- Lists corresponding amounts owed.

### **MSS**
1. Tutor chooses to list overdue students.
2. TSMS retrieves all students who have unpaid fees from past months.
3. TSMS displays the list of overdue students and their owed amounts.
   **Use case ends.**

---

## **UC-ViewLessons**

**System:** Tuiniverse Student Management System (TSMS)
**Use case:** UC-ViewLessons
**Actor:** Tutor

**Guarantees:**
- Lists lessons belonging to a certain student in lesson list panel.

### **MSS**
1. Tutor chooses to list lessons belonging to a student.
2. TSMS validates the student index.
3. TSMS retrieves all lessons that belong to the student.
   **Use case ends.**

### **Extensions**
- **2a.** TSMS cannot find a student with that index.
    - **2a1.** TSMS returns an error message.
    - **2a2.** TSMS clears user input.
      Steps **2a1–2a2** repeat until valid input is entered.
      **Use case ends.**

- **3a.** The student has no lessons.
    - **3a1.** TSMS displays a message in the console.
      **Use case ends.**

---

## **UC-AddAndBillStudent**

**System:** Tuiniverse Student Management System (TSMS)
**Use case:** UC-AddAndBillStudent
**Actor:** Tutor, Student

### **Preconditions**
- No existing student with the same details.
- No lesson schedule clashes.

### **MSS**
1. Tutor takes in a new student.
2. Student confirms lesson details with the tutor.
3. Student pays their fees for the month.
4. Tutor <ins>adds a student (UC-AddStudent)</ins>.
5. Tutor <ins>adds a parent (UC-AddParent)</ins>.
6. Tutor <ins>adds a lesson for the student (UC-AddLesson)</ins>.
7. Tutor <ins>marks payment received (UC-Pay)</ins>.
   **Use case ends.**

---

## **UC-QuitStudent**

**System:** Tuiniverse Student Management System (TSMS)
**Use case:** UC-QuitStudent
**Actor:** Tutor, Student

### **Precondition**
- Student currently has lessons with the tutor.
- Student is quitting lessons with the tutor

**Guarantees:**
- Student no longer appears in contact list panel.
- Total earned for month will decrease.

### **MSS**
1. Student terminates lessons with the tutor.
2. Tutor <ins>deletes the student (UC-DeleteStudent)</ins>.
   **Use case ends.**

### **Extensions**
- **2b.** Student has unpaid fees.
    - **2b1.** TSMS decreases total unpaid.
      **Use case ends.**

---

## **UC-SeeSummaryForMonth**

**System:** Tuiniverse Student Management System (TSMS)
**Use case:** UC-SeeSummaryForMonth
**Actor:** Tutor

### **Precondition**
- Student currently has lessons with the tutor.

### **MSS**
1. Tutor <ins>views list of paid students (UC-ListPaid)</ins>.
2. Tutor <ins>views list of unpaid students (UC-ListUnpaid)</ins>.
3. Tutor <ins>views list of overdue students (UC-ListOverdue)</ins>.
   **Use case ends.**

---

## **UC-SeeNewMonthPayments**

**System:** Tuiniverse Student Management System (TSMS)
**Use case:** UC-SeeNewMonthPayments
**Actor:** Tutor, Student

### **Precondition**
- TSMS enter a new month.

**Guarantees:**
- All students with lessons becomes **Unpaid**.
- Total earnings and total unpaid are recalculated.

### **MSS**
1. TSMS enters a new month
2. TSMS adds a new payment corresponding to the year and month.
3. TSMS displays the new amount unpaid and updates to unpaid/overdue status.
4. TSMS recalculates and displays the new total earned for month and total unpaid.
5. Tutor <ins>views list of unpaid students (UC-ListUnpaid)</ins>.
6. Tutor <ins>views list of overdue students (UC-ListOverdue)</ins>.
7. Tutor <ins>updates students who have paid for the current month (UC-Pay)</ins>.
   **Use case ends.**

---

### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2. The app should be able to accomodate up to 1000 tuition teachers concurrently without any performance compromises
3. A tutor with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4. User actions should take less than 2 seconds
5. The system should include comprehensive unit and integration tests with ≥ 75% code coverage.
6. The software shall be fully functional without requiring installation; it should run as a standalone executable (e.g., via java -jar Tuiniverse.jar).

*{More to be added}*

### Glossary

| **Category**  | **Term**                          | **Definition**                                                                                                                                    |
|---------------|-----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------|
| **Technical** | **Mainstream OS**   | Windows, Linux, Unix, MacOS.                                                                           |
|               | **CLI**   | Command Line Interface.                                                                           |
|               | **GUI** | Graphical User Interface.                                  |
|               | **Terminal**                      | A command window found in all operating systems where you type instructions for the computer to run.                                              |
| **Actors**    | **Tutor**                         | The user - you, the person managing and teaching students.                                                                                        |
|               | **Student**                       | A person currently enrolled in secondary school who is being taught one-on-one tuition services by the tutor.                                     |
| **Payments**  | **Payment Status**                | Indicates whether a student has paid for their lessons within the month. Each student has a payment status which updates every month.             |
|               | **Paid**                          | The student has paid for all lessons within the current month.                                                                                    |
|               | **Unpaid**                        | The student has not paid the full amount for some lessons within the current month.                                                               |
|               | **Overdue**                       | The student has outstanding unpaid lessons from previous months.                                                                                  |
|               | **Amount unpaid**                 | The amount the student has yet to pay the tutor for both current and previous months.                                                             |
|               | **Total earned for month**        | The total amount to be earned for all lessons from all students in one month, when all students have paid their fees.                             |
|               | **Total unpaid**                  | The total unpaid amounts between all students that the tutor has yet to receive.                                                                  |
| **Lessons**   | **Lesson**                        | A session between a student and a tutor where the tutor teaches a student a subject.                                                              |
|               | **Schedule**                      | A timetable for classes containing the time, location, subject of the class and the student taking the class. |
|               | **Subjects**                      | The topics taught by the tutor (e.g., Math, English, Physics, Chemistry, Biology, Geography, History, Mother Tongue, Social Studies, Literature). |
|               | **Level**                         | The level of the student in secondary school, depending on age and academic stream (1, 2, 3, 4, 5).                                               |
|               | **Rate**                          | The hourly rate a student pays the tutor for a lesson.                                                                                            |


--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. **Initial launch**

    * Download the jar file and copy into an empty folder

    * Double-click the jar file

    Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.


2. **Saving window preferences**

   * Resize the window to an optimum size. Move the window to a different location. Close the window.

   * Re-launch the app by double-clicking the jar file.

   Expected: The most recent window size and location is retained.


### Adding a student
**Sample error message indicating invalid command format:** Invalid command format!

1. Adding a student with all the required details
   * Test case: `add n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25`
     Expected: New student added: Name: John Doe; Phone: 98765432; Email: johnd@example.com; Address: 311, Clementi Ave 2, #02-25; Lessons: [no lesson]; Tags:. Confirmation message is displayed with the student's details.

1. Adding a student with missing compulsory fields
   * Test case: `add n/John Doe p/98765432 e/johnd@example.com`
     Expected:  Error message indicating that invalid command format will output.

1. Adding a student with invalid phone number
   * Test case: `add n/John Doe p/INVALID_NUMBER e/johnd@example.com a/311, Clementi Ave 2, #02-25`
     Expected: Phone numbers should only contain numbers, and it should be between 3 and 8 digits long. Error message is displayed


### Deleting a student

1. Deleting a student while all persons are being shown

   * Prerequisites: List all student using the `list` command. Multiple students in the list.

   * Test case: `delete 1`<br>
     Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message.

   * Test case: `delete 0`<br>
     Expected: No Student is deleted. Error message indicating that invalid command format will output.

   * Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
     Expected: Similar to previous.


### Editing a student
<box seamless>

**Tip:** Confirmation message will also display the lessons associated to the edited student. LESSONS refer to the lessons that was previously added.

</box>
<box seamless>

**Prerequisite 1:** At least one student exists in the list (A student at index 1).

</box>

1. Editing a student's details
   * Test case: `edit 1 n/Dohn Joe p/23456789 e/dohnj@example.com a/313,Somerset`
     Expected: Edited Student: Name: Dohn Joe; Phone: 23456789; Email: dohnj@example.com; Address: 313,Somerset; Lessons: [LESSONS] Confirmation message is displayed with the updated details.

2. Editing a student with some fields missing
   * Test case: `edit 1 p/67676767 a/Yishun, yishun`
     Expected: Edited Student: Name: Dohn Joe; Phone: 67676767; Email: dohnj@example.com; Address: Yishun, yishun; Lessons: [LESSONS] Confirmation message is displayed with the updated details, other details remain unchanged

3. Editing a student with invalid index
   * Test case: `edit 0 n/Dohn Joe p/23456789 e/dohnj@example.com a/313,Somerset`
     Expected: No student is edited. Error message indicating that invalid command format will output.


### Adding a lesson to student

1. Adding a lesson to a student
   * Test case: `add.lesson i/1 s/English l/2 d/Monday st/10:00 et/12:00 r/80`
   Expected: New lesson added:  Subject: English; Secondary: 2; Day: MONDAY; Start: 10:00; End: 12:00; Address: 311, Clementi Ave 2, #02-25; Rate: $80.00
   <br>Confirmation message is displayed with the lesson details.

2. Adding a lesson to a student with missing compulsory fields
   * Test case: `add.lesson i/1 s/English l/2 d/Monday st/10:00`
   Expected: No lesson is added. Error message indicating that invalid command format will output.

3. Adding a lesson to a student with incorrect Subject field
   * Test case: `add.lesson i/1 s/Dragon l/2 d/Monday st/10:00 et/12:00 r/80`
   Expected: Subjects are not case-sensitive, and can only take these values: Math, English, Physics, Chemistry, Biology, Geography, History, Literature, Social Studies, Mother Tongue. No lesson is added, error message displayed.

4. Adding a lesson to a student with incorrect Level field
   * Test case: `add.lesson i/1 s/English l/6 d/Monday st/10:00 et/12:00 r/80 `
   Expected: Levels can only take these integer values: 1, 2, 3, 4, 5. No lesson is added, error message displayed.

5. Adding a lesson to a student with incorrect Day field
    * Test case: `add.lesson i/1 s/English l/2 d/Today st/10:00 et/12:00 r/80 `
      Expected: Day is an integer which corresponds to: [1: Monday], [2: Tuesday], [3: Wednesday], [4: Thursday], [5: Friday], [6: Saturday], [7: Sunday]. No lesson is added, error message displayed.

6. Adding a lesson to a student with incorrect start and end time
   * Test case: `add.lesson i/1 s/English l/2 d/Monday st/WRONG_TIME et/12:00 r/80 `
   Expected: Time must be given in the format HH:MM, and the start time should be before the end time. No lesson is added, error message displayed.


### Deleting a lesson from student
<box type="info" seamless>

**Prerequisite:** At least one student exists in the list (A student at index 1) and at least one lesson exists for that student (A lesson at index 1).

**Tip:** Add a student to test delete capabilities using `add n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25`,
and add a lesson to the student with `add.lesson i/1 s/English l/2 d/Monday st/10:00 et/12:00 r/80`.
</box>

1. Deleting a lesson from student
   * Test case: `delete.lesson i/1 c/1`
   Expected: Deleted Lesson:  Subject: English; Secondary: 2; Day: MONDAY; Start: 10:00; End: 12:00; Address: 311, Clementi Ave 2, #02-25; Rate: $80.00. Confirmation message is displayed with the updated details.

2. Deleting a lesson from student with invalid student index
   * Test case: `delete.lesson i/0 c/1`
     Expected: No lesson is deleted. Error message indicating that invalid command format will output.

3. Deleting a lesson from student with invalid lesson index
   * Test case: `delete.lesson i/1 c/0`
     Expected: No lesson is deleted. Error message indicating that invalid command format will output.


### Editing a lesson
<box type="info" seamless>

**Prerequisite:** At least one student exists in the list (A student at index 1) and at least one lesson exists for that student (A lesson at index 1).

**Tip:** Add a student to test delete capabilities using `add n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25`,
and add a lesson to the student with `add.lesson i/1 s/English l/2 d/Monday st/10:00 et/12:00 r/80`.

To view the exact changes in lesson, use `view 1` to see the student's lessons in the Lesson list panel on the right.
<br>After successfully editing a lesson, use `view 1` and check that the edited lesson details has been changed.
</box>

1. Edit a lesson's details
   * Test case: `edit.lesson i/1 c/1 s/Math l/3 d/Friday st/11:00 et/13:00 r/90`
     Expected: Edited Lesson:  Subject: Math; Secondary: 3; Day: FRIDAY; Start: 11:00; End: 13:00; Address: 311, Clementi Ave 2, #02-25; Rate: $90.00. Confirmation message is displayed with the updated details.

2. Edit a lesson's details with some field's missing
   * Test case: `edit.lesson i/1 c/1 s/Geography`
     Expected: Edited Lesson:  Subject: Geography; Secondary: 3; Day: FRIDAY; Start: 11:00; End: 13:00; Address: 311, Clementi Ave 2, #02-25; Rate: $90.00. Confirmation message is displayed with the updated details, other details remain unchanged.

3. Edit a lesson's details with invalid student index
   * Test case: `edit.lesson i/0 c/1 s/Geography`
     Expected: No lesson is edited. Error message indicating that invalid command format will output.

4. Edit a lesson's details with invalid lesson index
   * Test case: `edit.lesson i/1 c/0 s/Geography`
     Expected: No lesson is edited. Error message indicating that invalid command format will output.


### Saving data

1. Dealing with missing data files
    1. Simulate a missing data file:
       * Close the application.
       * Navigate to the **data** directory where the application stores its data files.
       * Delete the data file **tuiniverse.json**
    2. Re-launch the application
    * Expected: The application starts with a default data set.

2. Dealing with corrupted data files
   1. Simulate a corrupted tuiniverse.json data file:
      * Close the application.
      * Navigate to the **data** directory where the application stores its data files.
      * Open the data file **tuiniverse.json** with a text editor.
      * Introduce an invalid JSON syntax (delete a closing brace, or add random text).
      * save the file.
   2. Re-launch the application.
   * Expected: The application detects the corrupted data file and displays an error message in the terminal. It will then start with an empty data set.

3. Editing preferences.json (changing `lastOpened`)
   1. Simulate a month elapsing:
      * Close the application.
      * Navigate the root directory where the application stores the `preferences.json`
      * Find the `lastOpened` field and set it to a valid `YearMonth`
          * Example: `"lastOpened": "2025-9"`
   2. Save the file and relaunch the application.
   * Expected: The application reads the updated `lastOpened` value on startup and updates it to current `YearMonth`, simultaneously starting the time-based features such as the monthly rollover logic for payments.


--------------------------------------------------------------------------------------------------------------------

## **Appendix: Effort**
### Difficulty level
Tuiniverse goes beyond AB3 in scope and architectural complexity. While AB3 manages a single entity type (Person),
Tuiniverse extends this to three distinct domain models — Student, Lesson, and Payment - that are all connected to
each other. Each entity has its own data structure (LessonList, PaymentList) and lifecycle logic
(e.g., monthly payment rollovers, lesson scheduling, payment tracking). This resulted in higher coupling and the need for
careful abstraction.

### Challenges faced
1. **Synchronising data across Student, Lesson, and Payment.**<br>
The value, or the TotalAmount, of each payment is dependent on the student's lessons. In turn, the payment status of
a student is dependent on the status of the payment list, which is dependent on the number of unpaid payments in the payment
list. Hence, a lot of abstraction was required to make sure information was passed accurately while still enforcing SLAP.
Furthermore, listeners were needed to make sure changes in Lesson were detected in Payment and Student. This made use of the observer
pattern to make sure other objects were aware of any mutation in state.

2. **Time-based architecture**<br>
A lot of Tuiniverse's logic is tightly coupled with time. For instance, the MonthlyRollover logic was needed to make sure a new
Payment object was instantiated with each month. This required careful time-based checking (via YearMonth) and persistence management to prevent
duplicate rollovers while maintaining data integrity.

3. **Dynamic payment status updates**<br>
Students may decide to add lessons in the middle of the month, which initially made it difficult to change payment status after paid. Hence,
refactoring payment to include unpaid amounts made it easier for tutors to check the amount unpaid for these new students.

4. **Effective scheduling**<br>
We wanted to make sure scheduling reflected in real life time management, where deconflicts are necessary and one cannot participate in
two events simultaneously. Hence, we added a method to assess for clashes. Furthermore, a lesson list panel was added to view a tutor's schedule for
the day, which can help them to plan accordingly.

### Effort required
A lot of our code was written from scratch, especially the MonthlyRollover and TimeClash logic. That said, some effort was saved through reuse of code,
namely in the adding, deleting, and editing lessons which mirrored the design of the AB3 add/delete/edit feature. We can confidently say
only about 10% of code was borrowed.

We split the responsibilities between our members by teams. Rachel and Ray handled the Lesson implementations, while Emilia and Bing Hang
worked on Payment. Dickson, aside from UI, took the lead in bridging the two teams, and took the initiative to ensure the deliverables were done correctly and on time.

The effort required from all 5 of us was high. Aside from the features we wanted to implement, there were several other responsibilities
we made sure to be attentive of, namely:
* Testing
* Code Quality
* Documentation
* UI design
* Project/GitHub management

We made sure to segment this effectively among the 5 of us, with each of us heading a different aspect. This allowed one person to take
responsibility for ensuring the quality of our code across the codebase, while adhering to software engineering practices.

### Achievement of the project
Tuiniverse successfully evolved AB3 into a feature-complete tuition management application handling different aspects of tutor-pupil management.
It enables private tutors to manage lessons, payments, and students in one place - with real-time financial tracking and automated rollovers.
Beyond technical achievement, the project demonstrates our team’s mastery of:
* Software design principles (abstraction, low coupling, high cohesion).
* Event-driven programming via JavaFX bindings.
* Test-driven development across multiple entity types.
* Collaborative Git workflows (feature branches, PR reviews, CI testing).

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Planned Enhancements**
Team Size: 5

### 1. Granular payment handling
At present, Tuiniverse assumes that once a student begins lessons in a given month, they attend for the entire month.
Consequently, the system calculates the total and unpaid fees based on a full month’s worth of lessons, even if the student
joined midway through the month.
In future iterations, we plan to enhance this by supporting partial payment calculations, allowing tutors to accurately account for students who begin
or pause lessons mid-month. This will be done by taking in the current date, and implementing a new CountDaysFromNowUntilMonthEnd method
which counts the number of a certain day (eg Thursday) until the end of the month.

### 2. Unselect a selected student
Currently, when a student is pressed, the person card will turn from light blue to bright blue. However, one is unable to unselect
this person card, and only press another person card to turn the card back to light blue. Hence in the future, we can implement an unselect
by counting for the number of clicks.

### 3. Adding tags
When editing a student to add tags, they currently need to retype every single tag to preserve the tags that were set previously, as a new
tag list will be created. Hence, in the future, we can create an add.tag function that adds a tag to a person in case they have additional
information they want to store.

### 4. Viewing lessons in order
When viewing a student's lesson via the view command, the lessons are sorted by time. This is not optimal, as we can see Thursday 6am lessons
ranked before Monday 8am lesson even if it is Monday. Hence, we can sort the student's lesson list first by day, then by time, instead of
by time only.

### 5. Duplicate tags
Adding/editing tags will only register one tag for the same tag instance (ie edit 1 t/tag t/tag only creates one tag tag). However, no error or
message in usage indicates that duplicates are not allowed. Hence, the message usage can be amended.

### 6. Custom Payment amount
Currently payCommand assumes student has paid his due fees all at once. In a future enhancement, tutor would be able to decrement unpaidAmount with a custom amount.

### 7. Attendance list
Current implementation assumes that student attends all the lessons in the month, not accounting for public holidays and instances where student is absent. An attendance list where tutor can mark student's attendance will help solve the problem

### 8. Today's Schedule Not Showing Student Name
Currently, the lesson list panel displays the day’s lesson schedule but does not
indicate which student each lesson is associated with. We plan to enhance this feature
in the future by including the student’s name alongside each lesson, allowing users to
easily identify which students they will be teaching for the day.

### 9. Add, Edit, and Delete Lesson UI
At present, when users add, edit, or delete a lesson, only a confirmation message
appears in the message box to indicate that the command has been successfully executed.
In the future, we plan to improve the user experience by automatically switching the
lesson panel to display the specific student’s lesson list, enabling users to immediately
view the updates they have made.
