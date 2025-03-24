package seedu.address.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.reservation.Person;
import seedu.address.model.reservation.Name;
import seedu.address.model.reservation.Phone;

/**
 * Represents a list of persons who have made reservations.
 * Tracks booking frequency and regular customer status.
 */
public class PersonsList {
    private static final Logger logger = LogsCenter.getLogger(PersonsList.class);
    private static final Path PERSONS_FILE_PATH = Paths.get("data", "personslist.json");
    private static final int REGULAR_CUSTOMER_THRESHOLD = 5;

    private ArrayList<Person> personsList;

    /**
     * Initializes a PersonsList.
     */
    public PersonsList() {
        // Create data directory if it doesn't exist
        try {
            Files.createDirectories(PERSONS_FILE_PATH.getParent());
        } catch (IOException e) {
            logger.warning("Could not create data directory: " + e.getMessage());
        }

        // Initialize with empty list
        this.personsList = new ArrayList<>();

        // Load from file if it exists
        loadListFromFile();
    }

    /**
     * Loads the persons list from file.
     * For entries with the same phone number, picks the one with the highest counter.
     */
    private void loadListFromFile() {
        // Load persons list
        try {
            File personsFile = PERSONS_FILE_PATH.toFile();
            if (personsFile.exists() && personsFile.length() > 0) {
                ObjectMapper mapper = new ObjectMapper();
                CollectionType listType = mapper.getTypeFactory()
                        .constructCollectionType(ArrayList.class, Person.class);

                ArrayList<Person> loadedPersons = mapper.readValue(personsFile, listType);
                if (loadedPersons != null) {
                    // For each phone number, keep the entry with the highest counter
                    java.util.Map<String, Person> phoneToPersonMap = new java.util.HashMap<>();

                    for (Person p : loadedPersons) {
                        String phoneValue = p.getPhone().value;

                        // Add if not seen before, or if this has a higher counter
                        if (!phoneToPersonMap.containsKey(phoneValue) ||
                                p.getCounter() > phoneToPersonMap.get(phoneValue).getCounter()) {
                            phoneToPersonMap.put(phoneValue, p);
                        }
                    }

                    // Convert the map values to our list
                    this.personsList = new ArrayList<>(phoneToPersonMap.values());

                    logger.info("Loaded " + personsList.size() + " unique persons from file, prioritizing highest counter values");
                }
            } else {
                // Create empty file if it doesn't exist
                if (!personsFile.exists()) {
                    personsFile.createNewFile();

                    // Initialize with empty array
                    FileWriter writer = new FileWriter(personsFile);
                    writer.write("[]");
                    writer.close();

                    logger.info("Created new empty persons file with empty array");
                }
            }
        } catch (IOException e) {
            logger.warning("Error loading persons list: " + e.getMessage());
        }
    }

    /**
     * Completely saves the persons list to file.
     * This will overwrite the file with the current in-memory list.
     * This should be used during application shutdown to ensure all data is properly saved.
     */
    public void savePersonsToFile() {
        try {
            logger.info("Doing final save of persons list with " + personsList.size() + " entries");

            // Create the file if it doesn't exist
            File file = PERSONS_FILE_PATH.toFile();
            if (!file.exists()) {
                file.createNewFile();
                logger.info("Created new persons file for final save");
            }

            // Write the current in-memory list to file (complete overwrite)
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(file, personsList);

            logger.info("Successfully performed final save of persons list");
        } catch (IOException e) {
            logger.severe("Failed to perform final save of persons list: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Appends a person to the JSON file in append mode
     * This maintains the JSON array structure by appending to the end of the file.
     */
    private void appendPersonToFile(Person person) {
        try {
            // First check if file is empty or not properly initialized
            File file = PERSONS_FILE_PATH.toFile();
            if (!file.exists() || file.length() == 0) {
                // Create new file with an array containing just this person
                ObjectMapper mapper = new ObjectMapper();
                mapper.enable(SerializationFeature.INDENT_OUTPUT);

                ArrayList<Person> newList = new ArrayList<>();
                newList.add(person);

                mapper.writeValue(file, newList);
                logger.info("Created new persons file with first entry");
                return;
            }

            // Read the file content
            String content = new String(Files.readAllBytes(PERSONS_FILE_PATH));

            // If the file doesn't contain a valid JSON array, reset it
            if (!content.trim().startsWith("[") || !content.trim().endsWith("]")) {
                // Create new file with an array containing just this person
                ObjectMapper mapper = new ObjectMapper();
                mapper.enable(SerializationFeature.INDENT_OUTPUT);

                ArrayList<Person> newList = new ArrayList<>();
                newList.add(person);

                mapper.writeValue(file, newList);
                logger.info("Reset invalid persons file with new entry");
                return;
            }

            // Convert person to JSON
            ObjectMapper mapper = new ObjectMapper();
            String personJson = mapper.writeValueAsString(person);

            // Format for appending
            StringBuilder newContent = new StringBuilder(content);

            // Remove the closing bracket
            newContent.deleteCharAt(newContent.length() - 1);

            // If there are existing items, add a comma
            if (newContent.toString().trim().length() > 1) {
                newContent.append(",\n");
            }

            // Add the new person JSON and close the array
            newContent.append(personJson).append("]");

            // Write back to file
            Files.write(PERSONS_FILE_PATH, newContent.toString().getBytes());

            logger.info("Appended person to file: " + person.getName().fullName);
        } catch (IOException e) {
            logger.severe("Failed to append person to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Returns true if a person with the same phone number exists in the list.
     */
    public boolean hasPerson(Person person) {
        if (person == null) {
            throw new NullPointerException();
        }

        for (Person p : personsList) {
            if (p.getPhone().equals(person.getPhone())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if a person is a regular customer (has isRegular set to true).
     */
    public boolean isRegularCustomer(Person person) {
        if (person == null) {
            throw new NullPointerException();
        }

        // First check if this exact person is marked as regular
        if (person.isRegular()) {
            return true;
        }

        // Otherwise, check if a person with this phone exists in our list and is marked as regular
        for (Person p : personsList) {
            if (p.getPhone().equals(person.getPhone()) && p.isRegular()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a person to the persons list and appends to file.
     */
    public void addPerson(Person p) {
        personsList.add(p);
        appendPersonToFile(p);
    }

    /**
     * Updates an existing person in the file or appends a new one if they don't exist.
     * This modifies the file in place for existing persons rather than appending duplicates.
     */
    private void updateOrAppendPersonToFile(Person person, boolean isNew) {
        try {
            // First check if file is empty or not properly initialized
            File file = PERSONS_FILE_PATH.toFile();
            if (!file.exists() || file.length() == 0) {
                // Create new file with an array containing just this person
                ObjectMapper mapper = new ObjectMapper();
                mapper.enable(SerializationFeature.INDENT_OUTPUT);

                ArrayList<Person> newList = new ArrayList<>();
                newList.add(person);

                mapper.writeValue(file, newList);
                logger.info("Created new persons file with first entry");
                return;
            }

            // Read the existing data
            ObjectMapper mapper = new ObjectMapper();
            CollectionType listType = mapper.getTypeFactory()
                    .constructCollectionType(ArrayList.class, Person.class);

            ArrayList<Person> existingPersons;
            try {
                existingPersons = mapper.readValue(file, listType);
            } catch (Exception e) {
                // If file is corrupted, reset it
                logger.warning("Could not parse persons file, resetting: " + e.getMessage());
                ArrayList<Person> newList = new ArrayList<>();
                newList.add(person);
                mapper.enable(SerializationFeature.INDENT_OUTPUT);
                mapper.writeValue(file, newList);
                return;
            }

            if (isNew) {
                // For new persons, just append
                existingPersons.add(person);
                logger.info("Appending new person to file: " + person.getName().fullName);
            } else {
                // update their entry by adding a new entry for existing person
                existingPersons.add(person);
                logger.info("Added updated person to file: " + person.getName().fullName);
            }

            // Write back the updated list
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(file, existingPersons);

        } catch (IOException e) {
            logger.severe("Failed to update or append person to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Records a booking for a person, updating their counter and regular status if needed.
     */
    public Person recordBooking(Name name, Phone phone) {
        logger.info("Recording booking for " + name.fullName + " with phone " + phone.value);

        // Check if person already exists
        Person existingPerson = null;
        int existingIndex = -1;

        for (int i = 0; i < personsList.size(); i++) {
            if (personsList.get(i).getPhone().equals(phone)) {
                existingPerson = personsList.get(i);
                existingIndex = i;
                break;
            }
        }

        if (existingPerson != null) {
            // Update existing person
            int newCount = existingPerson.getCounter() + 1;
            boolean isNowRegular = newCount >= REGULAR_CUSTOMER_THRESHOLD;

            // Create updated person
            Person updatedPerson = new Person(name, phone);
            updatedPerson.setCounter(newCount);
            updatedPerson.setIsRegular(isNowRegular);

            // Replace in list
            personsList.set(existingIndex, updatedPerson);

            // Update existing entry in file (not append)
            updateOrAppendPersonToFile(updatedPerson, false);

            logger.info("Updated existing person, new count: " + newCount +
                    ", regular status: " + (isNowRegular ? "Yes" : "No"));

            return updatedPerson;
        } else {
            // Create new person
            Person newPerson = new Person(name, phone);
            newPerson.setCounter(1);
            newPerson.setIsRegular(false);

            // Add to memory
            personsList.add(newPerson);

            // Append to file (this is a new person)
            updateOrAppendPersonToFile(newPerson, true);

            logger.info("Added new person with first booking");

            return newPerson;
        }
    }

    /**
     * Removes a person from the list.
     */
    public void removePerson(Person key) {
        boolean removed = personsList.remove(key);

        if (removed) {
            // Rewrite persons file
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.enable(SerializationFeature.INDENT_OUTPUT);
                mapper.writeValue(PERSONS_FILE_PATH.toFile(), personsList);
                logger.info("Removed person and rewrote persons file: " + key.getName().fullName);
            } catch (IOException e) {
                logger.severe("Failed to rewrite persons file after removal: " + e.getMessage());
            }
        }
    }

    /**
     * Returns a copy of the persons list.
     */
    public ArrayList<Person> getPersonsList() {
        return new ArrayList<>(personsList);
    }

    /**
     * Returns a list of all regular customers (persons with isRegular set to true).
     */
    public ArrayList<Person> getRegularCustomers() {
        ArrayList<Person> regulars = new ArrayList<>();
        for (Person person : personsList) {
            if (person.isRegular()) {
                regulars.add(person);
            }
        }
        return regulars;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PersonsList)) {
            return false;
        }

        PersonsList otherPersonsList = (PersonsList) other;
        return personsList.equals(otherPersonsList.personsList);
    }

    @Override
    public String toString() {
        int regularCount = 0;
        for (Person p : personsList) {
            if (p.isRegular()) {
                regularCount++;
            }
        }
        return personsList.size() + " persons, " + regularCount + " regulars";
    }
}