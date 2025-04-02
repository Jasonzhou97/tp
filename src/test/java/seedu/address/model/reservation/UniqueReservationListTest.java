package seedu.address.model.reservation;

public class UniqueReservationListTest {
    /*

    private final UniqueReservationList uniqueReservationList = new UniqueReservationList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReservationList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueReservationList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueReservationList.add(ALICE);
        assertTrue(uniqueReservationList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReservationList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueReservationList.add(ALICE);
        assertThrows(DuplicateReservationException.class, () -> uniqueReservationList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReservationList.setReservation(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReservationList.setReservation(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(ReservationNotFoundException.class, () -> uniqueReservationList.setReservation(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueReservationList.add(ALICE);
        uniqueReservationList.setReservation(ALICE, ALICE);
        UniqueReservationList expectedUniqueReservationList = new UniqueReservationList();
        expectedUniqueReservationList.add(ALICE);
        assertEquals(expectedUniqueReservationList, uniqueReservationList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueReservationList.add(ALICE);
        Reservation editedAlice = new ReservationBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueReservationList.setReservation(ALICE, editedAlice);
        UniqueReservationList expectedUniqueReservationList = new UniqueReservationList();
        expectedUniqueReservationList.add(editedAlice);
        assertEquals(expectedUniqueReservationList, uniqueReservationList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueReservationList.add(ALICE);
        uniqueReservationList.setReservation(ALICE, BOB);
        UniqueReservationList expectedUniqueReservationList = new UniqueReservationList();
        expectedUniqueReservationList.add(BOB);
        assertEquals(expectedUniqueReservationList, uniqueReservationList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueReservationList.add(ALICE);
        uniqueReservationList.add(BOB);
        assertThrows(DuplicateReservationException.class, () -> uniqueReservationList.setReservation(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReservationList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(ReservationNotFoundException.class, () -> uniqueReservationList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueReservationList.add(ALICE);
        uniqueReservationList.remove(ALICE);
        UniqueReservationList expectedUniqueReservationList = new UniqueReservationList();
        assertEquals(expectedUniqueReservationList, uniqueReservationList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReservationList
        .setReservations((UniqueReservationList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueReservationList.add(ALICE);
        UniqueReservationList expectedUniqueReservationList = new UniqueReservationList();
        expectedUniqueReservationList.add(BOB);
        uniqueReservationList.setReservations(expectedUniqueReservationList);
        assertEquals(expectedUniqueReservationList, uniqueReservationList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReservationList.setReservations((List<Reservation>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueReservationList.add(ALICE);
        List<Reservation> reservationList = Collections.singletonList(BOB);
        uniqueReservationList.setReservations(reservationList);
        UniqueReservationList expectedUniqueReservationList = new UniqueReservationList();
        expectedUniqueReservationList.add(BOB);
        assertEquals(expectedUniqueReservationList, uniqueReservationList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Reservation> listWithDuplicateReservations = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateReservationException.class, () -> uniqueReservationList
        .setReservations(listWithDuplicateReservations));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueReservationList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueReservationList.asUnmodifiableObservableList().toString(), uniqueReservationList.toString());
    }

     */
}
