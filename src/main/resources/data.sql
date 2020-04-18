insert into LOGS (USER_ID, SESSION_ID) values ('user1', 'session1')
insert into LOGS (USER_ID, SESSION_ID) values ('user2', 'session2')
insert into LOGS (USER_ID, SESSION_ID) values ('user3', 'session3')
insert into LOGS (USER_ID, SESSION_ID) values ('user4', 'session4')
insert into LOGS (USER_ID, SESSION_ID) values ('user1', 'session11')


insert into ACTIONS (ACT_ID, USER_ID,SESSION_ID, TIME, TYPE) values ('1' ,'user1','session1', '2018-10-18T21:37:28-06:00', 'click')
insert into ACTIONS (ACT_ID,USER_ID,SESSION_ID, TIME, TYPE) values ('2' ,'user1','session1', '2018-10-18T21:37:28-06:00', 'navigate')
insert into ACTIONS (ACT_ID, USER_ID,SESSION_ID, TIME, TYPE) values ('3' ,'user1','session1', '2018-10-18T21:37:28-06:00', 'view')

insert into ACTIONS (ACT_ID, USER_ID,SESSION_ID, TIME, TYPE) values ('4' ,'user2','session2', '2018-10-18T21:37:28-06:00', 'click')
insert into ACTIONS (ACT_ID, USER_ID,SESSION_ID, TIME, TYPE) values ('5' ,'user2','session2', '2018-10-18T21:37:28-06:00', 'navigate')
insert into ACTIONS (ACT_ID, USER_ID,SESSION_ID, TIME, TYPE) values ('6' ,'user2','session2', '2018-10-18T21:37:28-06:00', 'view')

insert into ACTIONS (ACT_ID, USER_ID,SESSION_ID, TIME, TYPE) values ('7' ,'user3','session3', '2018-10-18T21:37:28-06:00', 'click')
insert into ACTIONS (ACT_ID, USER_ID,SESSION_ID, TIME, TYPE) values ('8' ,'user3','session3', '2018-10-18T21:37:28-06:00', 'navigate')
insert into ACTIONS (ACT_ID, USER_ID,SESSION_ID, TIME, TYPE) values ('9' ,'user3','session3', '2018-10-18T21:37:28-06:00', 'view')

insert into ACTIONS (ACT_ID, USER_ID,SESSION_ID, TIME, TYPE) values ('10' ,'user4','session4', '2018-10-18T21:37:28-06:00', 'click')
insert into ACTIONS (ACT_ID, USER_ID,SESSION_ID, TIME, TYPE) values ('11' ,'user4','session4', '2018-10-18T21:37:28-06:00', 'navigate')
insert into ACTIONS (ACT_ID, USER_ID,SESSION_ID, TIME, TYPE) values ('12' ,'user4', 'session4','2018-10-18T21:37:28-06:00', 'view')

insert into ACTIONS (ACT_ID, USER_ID,SESSION_ID, TIME, TYPE) values ('13' ,'user1', 'session11', '2018-11-18T21:37:28-07:00', 'click')
insert into ACTIONS (ACT_ID, USER_ID,SESSION_ID, TIME, TYPE) values ('14' ,'user1','session11', '2018-11-18T21:37:28-07:00', 'navigate')


insert into PROPERTIES (ACT_ID ,TYPE, LOCATION_X, LOCATION_Y) values('1' ,'click','52', '42')
insert into PROPERTIES (ACT_ID ,TYPE,  VIEWED_ID) values('2' ,'view','viewid1')
insert into PROPERTIES (ACT_ID ,TYPE,  PAGE_FROM, PAGE_TO) values('3', 'navigate','page1', 'page2')

insert into PROPERTIES (ACT_ID ,TYPE, LOCATION_X, LOCATION_Y) values('4', 'click','52', '42')
insert into PROPERTIES (ACT_ID ,TYPE,  VIEWED_ID) values('5','view','viewid12')
insert into PROPERTIES (ACT_ID ,TYPE,  PAGE_FROM, PAGE_TO) values('6','navigate','page12', 'page22')

insert into PROPERTIES (ACT_ID ,TYPE, LOCATION_X, LOCATION_Y) values( '7','click','52', '42')
insert into PROPERTIES (ACT_ID ,TYPE,  VIEWED_ID) values('8' ,'view','viewid123')
insert into PROPERTIES (ACT_ID ,TYPE,  PAGE_FROM, PAGE_TO) values('9' ,'navigate','page123', 'page223')

insert into PROPERTIES (ACT_ID ,TYPE, LOCATION_X, LOCATION_Y) values('10', 'click','52', '42')
insert into PROPERTIES (ACT_ID ,TYPE,  VIEWED_ID) values('11', 'view','viewid1234')
insert into PROPERTIES (ACT_ID ,TYPE,  PAGE_FROM, PAGE_TO) values('12','navigate','page1234', 'page2234')

insert into PROPERTIES (ACT_ID ,TYPE, LOCATION_X, LOCATION_Y) values('13', 'click','521', '421')
insert into PROPERTIES (ACT_ID ,TYPE,  VIEWED_ID) values('14', 'view','viewid12341')


