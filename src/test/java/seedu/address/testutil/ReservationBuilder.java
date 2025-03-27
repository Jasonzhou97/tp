package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_BOB;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.reservation.Duration;
import seedu.address.model.reservation.Identification;
import seedu.address.model.reservation.Name;
import seedu.address.model.reservation.Pax;
import seedu.address.model.reservation.Phone;
import seedu.address.model.reservation.Remark;
import seedu.address.model.reservation.Reservation;
import seedu.address.model.reservation.StartDate;
import seedu.address.model.reservation.StartTime;
import seedu.address.model.reservation.Table;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class ReservationBuilder {
    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_DATE = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    public static final String DEFAULT_TIME = "1800";
    public static final String DEFAULT_DURATION = "2";
    public static final String DEFAULT_PAX = "2";
    public static final String DEFAULT_TABLE = "A1";
    public static final String DEFAULT_REMARK = "She likes aardvarks.";
    public static final boolean DEFAULT_ISPAID = false;

    // Auto Assigned ID after reservation is built, do not change this
    public static final Identification ID_AMY = new Identification(new StartDate(DEFAULT_DATE),
            new Phone(DEFAULT_PHONE), new StartTime(DEFAULT_TIME));

    public static final Identification ID_BOB = new Identification(new StartDate(VALID_DATE_BOB),
            new Phone(VALID_PHONE_BOB), new StartTime(VALID_TIME_BOB));


    private Name name;
    private Phone phone;
    private StartDate date;
    private StartTime time;
    private Duration duration;
    private Pax pax;
    private Table table;
    private Remark remark;
    private Set<Tag> tags;
    private Identification id;

    private boolean isPaid;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public ReservationBuilder(Name name, Phone phone, StartDate date,
                              StartTime time, Duration duration, Pax pax,
                              Table table, Remark remark, HashSet<Tag> tags) {
        this.name = name;
        this.phone = phone;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.pax = pax;
        this.table = table;
        this.remark = remark;
        this.tags = tags;
        this.id = new Identification(this.date, this.phone, this.time);
        this.isPaid = DEFAULT_ISPAID;
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public ReservationBuilder(Reservation reservationToCopy) {
        name = reservationToCopy.getName();
        phone = reservationToCopy.getPhone();
        date = reservationToCopy.getDate();
        time = reservationToCopy.getTime();
        duration = reservationToCopy.getDuration();
        pax = reservationToCopy.getPax();
        table = reservationToCopy.getTable();
        remark = reservationToCopy.getRemark();
        tags = new HashSet<>(reservationToCopy.getTags());
        id = reservationToCopy.getId();
        isPaid = reservationToCopy.getIsPaid();
    }


    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public ReservationBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public ReservationBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public ReservationBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code StartDate} of the {@code Person} that we are building.
     */
    public ReservationBuilder withDate(String date) {
        this.date = new StartDate(date);
        return this;
    }

    /**
     * Sets the {@code StartTime} of the {@code Person} that we are building.
     */
    public ReservationBuilder withTime(String time) {
        this.time = new StartTime(time);
        return this;
    }

    /**
     * Sets the {@code Duration} of the {@code Person} that we are building.
     */
    public ReservationBuilder withDuration(String duration) {
        this.duration = new Duration(duration);
        return this;
    }

    /**
     * Sets the {@code Pax} of the {@code Person} that we are building.
     */
    public ReservationBuilder withPax(String pax) {
        this.pax = new Pax(pax);
        return this;
    }

    /**
     * Sets the {@code Table} of the {@code Person} that we are building.
     */
    public ReservationBuilder withTable(String table) {
        this.table = new Table(table);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public ReservationBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    /**
     * Sets the {@code Identification} of the {@code Person} that we are building.
     */
    public ReservationBuilder withId(StartDate date, Phone phone, StartTime time) {
        this.id = new Identification(date, phone, time);
        return this;
    }

    /**
     * Sets the payment status.
     *
     * @param isPaid
     * @return
     */
    public ReservationBuilder withPaymentStatus(Boolean isPaid) {
        this.isPaid = isPaid;
        return this;
    }

    /**
     * @return a sample reservation
     */
    public Reservation build() {
        return new Reservation(name, phone, date, time, duration, pax, table, remark, tags, id, isPaid);

    }

    //    Has specify this to be ID_AMY as the test samples has been
    //    reduced to just AMY AND BOB for simplicity and easy testing
    //    public Reservation build1() {
    //        return new Reservation(name, phone, date, time, duration, pax, table, remark, tags, ID_AMY, false);
    //    }
    //
    //    public Reservation build2() {
    //        return new Reservation(name, phone, date, time, duration, pax, table, remark, tags, ID_BOB, false);
    //    }

}
