drop table if exists user;
drop table if exists event;
drop table if exists venue;
drop table if exists venue_property;
drop table if exists occurrence;
drop table if exists category;
drop table if exists event_category;
drop table if exists attendee;
drop table if exists event_comment;
drop table if exists occurrence_comment;
drop table if exists venue_comment;

create table user (
  id integer not null,
  firstName varchar(512),
  lastName varchar(512),
  email varchar(512),
  nickname varchar(512),
  primary key(id)
);

create table event (
  id integer not null,
  name varchar(512),
  description varchar(512),
  ownerId integer,
  primary key(id),
  foreign key(ownerId) references user(id)
);

create table venue (
  id integer not null,
  name varchar(512),
  address varchar(512),
  latitude double,
  longitude double,
  primary key(id)
);

create table venue_property (
  venueId integer not null,
  key varchar(128),
  value varchar(1024),
  primary key(venueId, key),
  foreign key(venueId) references venue(Id)
);

create table occurrence (
  id integer not null,
  venueId integer,
  start datetime,
  end datetime,
  primary key(id),
  foreign key(venueId) references venue(id)
);

create table category (
  id integer not null,
  name varchar(512),
  primary key(id),
  parentId integer,
  foreign key(parentId) references category(id)
);

create table event_category (
  eventId integer not null,
  categoryId integer not null,
  primary key(eventId, categoryId),
  foreign key(eventId) references occurrence(id),
  foreign key(categoryId) references user(id)
);

create table attendee (
  occurrenceId integer not null,
  userId integer not null,
  primary key(occurrenceId, userId),
  foreign key(occurrenceId) references occurrence(id),
  foreign key(userId) references user(id)
);

create table event_comment (
  id integer not null,
  eventId integer,
  authorId integer,
  text varchar(2048),
  timestamp datetime,
  primary key(id),
  foreign key(eventId) references event(id),
  foreign key(authorId) references user(id)
);

create table occurrence_comment (
  id integer not null,
  occurrenceId integer,
  authorId integer,
  text varchar(2048),
  timestamp datetime,
  primary key(id),
  foreign key(occurrenceId) references occurrence(id),
  foreign key(authorId) references user(id)
);

create table venue_comment (
  id integer not null,
  venueId integer,
  authorId integer,
  text varchar(2048),
  timestamp datetime,
  primary key(id),
  foreign key(venueId) references venue(id),
  foreign key(authorId) references user(id)
);

