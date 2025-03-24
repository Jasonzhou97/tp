---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
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

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Logic.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

Similarly, how an undo operation goes through the `Model` component is shown below:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Model.png)

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


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

### **Target User Profile**

* **Type**: F&B/restaurant managers and staff
* **Proficiency**: Comfortable using Command-Line Interface (CLI) apps
* **Needs**:
    * Ability to manage a large number of reservations
    * Speed and efficiency with typing, prefers keyboard over mouse
    * Streamlined reservation management to ensure smooth operations
* **Environment**: Works in a fast-paced restaurant environment where reservations and customer service are key

### **Value Proposition**

GastroBook streamlines and collates restaurant reservations, allowing managers to efficiently manage customer bookings, track reservation details, and handle special requests through a fast, command-line interface. The application improves reservation workflow, enhances operational efficiency, and allows for better resource planning.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| **Priority** | **As a …​** | **I want to …​**                            | **So that I can…​**                                                           |
|--------------|-------------|---------------------------------------------|-------------------------------------------------------------------------------|
| `***`        | Admin       | Add reservations                            | Add bookings to the schedule for planning                                     |
| `***`        | Admin       | Delete reservations                         | Cancel a reservation when no longer needed                                    |
| `***`        | Admin       | Mark a reservation as paid                  | Keep track of each reservation’s payment status                               |
| `***`        | Admin       | Unmark a reservation as paid                | Update status if payment is deleted or an error occurred                      |
| `***`        | Admin       | Exit the application                        | Close the app after use                                                       |
| `***`        | Admin       | Edit reservation details                    | Update booking details (e.g., name, duration) without deleting and recreating |
| `***`        | Admin       | View all reservations of tomorrow           | Prepare the ingredients for tomorrow reservation in advance                   |
| `***`        | Admin       | View all reservations for the day           | Access the daily reservation schedule for planning resources                  |
| `***`        | Admin       | View all reservations of today only         | To be always ready and prepare to welcome incoming guest                      |
| `***`        | Admin       | See user manual                             | To learn the new app effectively                                              |
| `**`         | Admin       | Find specific reservations                  | Locate specific reservations by name or other parameters                      |
| `**`         | Admin       | Mark arrival status for customers           | Track customer arrivals and handle no-shows                                   |
| `**`         | Admin       | Unmark arrival status for customers         | Update arrival status when customers don't show up within the designated time |
| `**`         | Admin       | Set reservation time limits                 | Automatically release tables when customers do not arrive on time             |
| `**`         | Admin       | Add tables of various sizes                 | Accommodate different types of reservations (group bookings, etc.)            |
| `**`         | Admin       | View/Tag reservations with special requests | Easily track special requests per reservation                                 |
| `**`         | Admin       | Filter reservations by tags                 | View reservations with specific requests at a glance                          |
| `**`         | Admin       | Apply discounts on bills                    | Automate pricing adjustments for discounts                                    | |
| `**`         | Admin       | Clear all reservations                      | Reset the schedule for a new reservation plan                                 |
| `**`         | Admin       | Find available slots                        | Add new reservations into free slots                                          |
| `**`         | Admin       | Add multiple tables at once                 | Handle large group bookings more efficiently                                  |
| `**`         | Admin       | View customer details                       | Know their booking time, allocated table, dishes ordered, spending, etc.      |
| `**`         | Admin       | Set reservation parameters                  | Ensure reservations fit within the table/group limitations                    |
| `*`          | Admin       | Edit menu                                   | Modify the restaurant menu to accommodate customer requests and dietary needs |
| `*`          | Admin       | Find duplicate reservations                 | Remove unnecessary or repeated reservations                                   |
| `*`          | New admin   | View history of past payments               | Keep track of payment history for better management                           |
| `*`          | Admin       | Tag reservations for special requests       | Track and manage special requests efficiently                                 |
| `*`          | Admin       | View menu offered by restaurant             | Help customers with menu questions and options                                |
| `*`          | Admin       | Set parameters for reservations             | Ensure proper handling based on table/group size and special requests         |
| `*`          | Admin       | View reservation count by time              | Track reservations over different time periods for business insights          |


*{More to be added}*

### Use cases

For all use cases below: <br>
**System**: `GastroBook` <br>
**Actor**: `admin` <br>
unless specified otherwise

### **U1: Add a reservation**

**MSS** (Main Success Scenario)
1. User inputs reservation details (name, table number, timeslot, special requests).
2. System confirms if the reservation slot is available.
3. User is prompted to confirm the reservation.
4. System adds the reservation to the schedule.

Use case ends.

**Extensions** <br>
2a. The provided timeslot is not available. <br>
&ensp; 2a1. System indicates that the provided timeslot is not available. <br>
&ensp; 2a2. System shows available slots. <br>
&ensp; Use case resumes at step 1.

3a. The user rejects the confirmation of the reservation <br>
&ensp; 3a1. Confirmation prompt closes. <br>
&ensp; Use case resumes at step 1.

---

### **U2: Delete a reservation**

**MSS**
1. User inputs the reservation they want to cancel.
2. System asks for confirmation to delete the reservation.
3. User confirms the cancellation.
4. System removes the reservation from the schedule.

Use case ends.

**Extensions** <br>
1a. The reservation does not exist. <br>
&ensp; 1a1. System displays an error message. <br>
&ensp; Use case resumes at step 1.

3a. The user rejects the confirmation of the deletion <br>
&ensp; 3a1. Confirmation prompt closes. <br>
&ensp; Use case resumes at step 1.

---

### **U3: Mark a reservation as paid**

**MSS**
1. User finds the reservation they want to mark.
2. User marks the reservation as paid.
3. System updates the reservation's payment status as paid.

Use case ends.

**Extensions** <br>
1a. The reservation does not exist. <br>
&ensp; 1a1. System displays an error message. <br>
&ensp; Use case resumes at step 1. <br>

2a. The reservation has already been marked as paid. <br>
&ensp; 2a1. System indicates that reservation has already been marked as paid. <br>
&ensp; Use case resumes at step1.

---

### **U4: Unmark a reservation as paid**

**MSS**
1. User finds the reservation they want to unmark.
2. User unmarks the reservation as paid.
3. System updates the reservation's payment status as unpaid.

Use case ends.

**Extensions** <br>
1a. The reservation does not exist. <br>
&ensp; 1a1. System displays an error message. <br>
&ensp; Use case resumes at step 1.

2a. The reservation has already been marked as unpaid. <br>
&ensp; 2a1. System indicates that reservation has already been marked as paid. <br>
&ensp; Use case resumes at step1.

---
### **U5: Exit the application**

**MSS**
1. User inputs exit command.
2. System saves all changes into storage.
3. System exits.

Use case ends.

---
### **U6: Find a reservation by name**

**MSS**
1. User finds the reservation by name.
2. System displays all reservations that are made under the queried name.
<The following steps can be included as a seperate use case that can be used for other find functions>
3. User selects relevant reservation.
4. System displays all relevant information on the reservation.

Use case ends.

**Extensions** <br>
1a. The name does not exist. <br>
&ensp; 1a1. System displays an error message. <br>
&ensp; Use case resumes at step 1.

3a. The user enters an invalid index.<br>
&ensp; 3a1. System displays an error message <br>
&ensp; Use case resumes at step 2.

---
### **U7: List all reservations for the day**

**MSS**
1. User queries to list all reservations for the day
2. System displays all reservations that are made on the system date.

Use case ends.

**Extensions** <br>
1a. No reservations exist on the day queried. <br>
&ensp; 1a1. System indicates that there are no reservations on given day. <br>
&ensp; Use case resumes at step 1.

### **U8: Edit specific reservations**

**MSS**
1. User queries to edit the reservation with valid ID.
2. System displays all reservations of today and tomorrow with the edited reservation inside a reservation edited message. [Could be with active fxml element]
Use case ends.

**Extensions** <br>
1a. Reservations to be edited queried with valid ID but not of today or tomorrow reservation. <br>
&ensp; 1a1. System displays all reservations of today and tomorrow with a reservation not exist message. <br>
Use case ends.

1b. Reservations to be edited queried with invalid ID <br>
&ensp; 1b1. System displays invalid command message and output proper example of edit use.  <br>
Use case ends.

### **U9: Track Customer Booking Frequency and Regular Customer Status**

**MSS**
1. Customer makes a new reservation.
2. System records the reservation details including customer's name and phone number.
3. System checks if the customer already exists in the PersonsList.
4. If the customer exists, system increments their booking count.
5. If the booking count exceeds the threshold for regular customer status, system marks the customer as a regular customer.
6. System saves the updated customer information.

Use case ends.

**Extensions** <br>
3a. The customer is new (not in the PersonsList). <br>
&ensp; 3a1. System creates a new customer record with booking count of 1. <br>
&ensp; 3a2. System saves the new customer record to the PersonsList. <br>
&ensp; Use case continues at step 6.

4a. The phone number in the reservation matches a different name in the system. <br>
&ensp; 4a1. System displays a warning about the name mismatch. <br>
&ensp; 4a2. User confirms whether this is the same customer or a different person using the same phone. <br>
&ensp; 4a3. If confirmed as same customer, system updates the name and increments booking count. <br>
&ensp; 4a4. If confirmed as different customer, system treats as a new customer. <br>
&ensp; Use case continues at appropriate step based on the decision.

5a. The customer has already been marked as a regular customer. <br>
&ensp; 5a1. System maintains the regular customer status and increments the booking count. <br>
&ensp; Use case continues at step 6.

---

### **U10: View Regular Customers**

**MSS**
1. User requests to see all regular customers.
2. System retrieves all customers marked as "regular" from the PersonsList.
3. System displays the list of regular customers with their booking counts and other relevant details.

Use case ends.

**Extensions** <br>
2a. No regular customers exist in the system. <br>
&ensp; 2a1. System indicates that there are no regular customers currently. <br>
&ensp; Use case ends.

---

### **U11: List Previous Reservations**

**MSS**
1. User inputs the command to list previous reservations.
2. System retrieves all reservations with dates before the current date.
3. System displays the list of previous reservations sorted by date and time.

Use case ends.

**Extensions** <br>
2a. No previous reservations exist in the system. <br>
&ensp; 2a1. System indicates that there are no previous reservations. <br>
&ensp; Use case ends.

---

### Non-Functional Requirements

1. **Platform Compatibility**: The application must run on any mainstream OS (Windows, Linux, macOS) with Java 17 or above.
2. **Performance**: The system should handle up to 1000 reservations without noticeable performance degradation during typical usage.
3. **Usability**: A user with above-average typing speed for regular English text should be able to accomplish key tasks faster using the command-line interface than a GUI-based app.
4. **Response Time**: The system should respond to commands within 1 second for typical tasks such as adding, editing, or deleting reservations.
5. **Storage**: The system storage should be able to store and maintain at least 100 reservations with all necessary details.
6. **Storage Access**: The system should be able to retrieve data with the given storage requirements in under 1 second.
7. **User Access**: The system should be able to run locally with no more than 1 user with 1 database.
8. **Phone Number**: Last 4 digits of phone number of every customer input to the system must be of unique combination.

*{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **Reservation ID**: A unique identifier (e.g., R1) assigned to each reservation.
* **CLI (Command-Line Interface)**: A text-based interface that allows users to interact with the system using typed commands.
* **Timeslot**: The reserved period for a customer's table reservation, usually in 1-hour or 2-hour increments.
* **Special Request**: Specific requirements or preferences made by the customer for their reservation (e.g., dietary needs, specific table preferences).
* **Max Reservation Hour**: The maximum duration for a reservation, typically set to 2 hours.
* **Valid Command**: A command that the system recognizes and processes correctly.
* **Invalid Command**: A command that is unrecognized or improperly formatted by the system.
* **Reservation Duration**: The time for which a reservation is valid, either 1 hour or 2 hours.
* **Valid ID**: The id for which edit, mark, unmark, delete, remark take as parameter which has a form of < [dateOfTodayOrTomorrow(ddMMyyyy)] + [UNIQUE last4DigitsOfPhoneNumber(xxxx)] >"

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
