package edu.lmu.cs.wutup.ws.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;

public class EventTest {

    @Test
    public void fieldsSetByConstructorCanBeRead() {
        Event e = new Event(3, "Pool Party", "Party at Brous House", "Pacific Palisades");
        assertThat(e.getId(), is(3));
        assertThat(e.getName(), is("Pool Party"));
        assertThat(e.getDescription(), is("Party at Brous House"));
        assertThat(e.getLocation(), is("Pacific Palisades"));
    }

    @Test
    public void fieldsSetBySettersCanBeRead() {
        Event e = new Event();
        User eventOwner = new User("Bohnam", "John", "jbohnan@gmail.com", "John");
        DateTime timeToStart = new DateTime("2011-12-13T21:39:45.618-08:00");
        DateTime timeToEnd = new DateTime("2011-12-14T21:39:45.618-08:00");
        List<User> someUsers = new ArrayList<User>();
        someUsers.add(new User("Plant", "Robert", "rplant@gmail.com", "Rob"));
        someUsers.add(new User("Page", "Jimmy", "jpage@gmail.com", "Jim"));
        e.setId(5);
        e.setName("Programming Contest");
        e.setDescription("Student Programming Contest");
        e.setLocation("LMU");
        e.setCategory("Premium");
        e.setOwner(eventOwner);
        //e.setStart(new DateTime("2011-12-13T21:39:45.618-08:00")); // also passed test
        //e.setEnd(new DateTime("2011-12-14T21:39:45.618-08:00")); // also passed test
        e.setStart(timeToStart);
        e.setEnd(timeToEnd);        
        e.setAttendees(someUsers);
        assertThat(e.getId(), is(5));
        assertThat(e.getName(), is("Programming Contest"));
        assertThat(e.getDescription(), is("Student Programming Contest"));
        assertThat(e.getLocation(), is("LMU"));
        assertThat(e.getCategory(), is("Premium"));
        assertThat(e.getOwner(), is(eventOwner));
        assertThat(e.getStart(), is(timeToStart));
        assertThat(e.getEnd(), is(timeToEnd));
        assertThat(e.getAttendees(), is(someUsers));
    }

    @Test
    public void equalsUsesIdAndNameOnly() {
        assertThat(new Event(7, "Pool Party"), equalTo(new Event(7, "Pool Party")));
        assertThat(new Event(7, "Pool Party"), not(equalTo(new Event(17, "Pool Party"))));
        assertThat(new Event(7, "Pool Party"), not(equalTo(new Event(7, "Target Practice"))));
        assertFalse(new Event(7, "Pool Party").equals("some string"));
        assertFalse(new Event(7, "Pool Party").equals(null));
    }

    @Test
    public void hashCodeProducesId() {
        assertThat(new Event(7, "Pool Party").hashCode(), is(7));
    }
}
