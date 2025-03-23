package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.PersonsList;
import seedu.address.model.ReadOnlyPersonsList;
import seedu.address.model.Person;

/**
 * An Immutable PersonsList that is serializable to JSON format.
 */
@JsonRootName(value = "personslist")
class JsonSerializablePersonsList {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializablePersonsList} with the given persons.
     */
    @JsonCreator
    public JsonSerializablePersonsList(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyPersonsList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializablePersonsList}.
     */
    public JsonSerializablePersonsList(ReadOnlyPersonsList source) {
        persons.addAll(source.getPersonsList()
                .stream()
                .map(JsonAdaptedPerson::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this persons list into the model's {@code PersonsList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public PersonsList toModelType() throws IllegalValueException {
        PersonsList personsList = new PersonsList();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (personsList.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            personsList.addPerson(person);
        }
        return personsList;
    }
}