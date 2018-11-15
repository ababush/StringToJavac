package com.example.demo_interpr.generated;

import com.example.demo_interpr.domain.Privilege;
import com.example.demo_interpr.domain.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.Arrays.asList;

public class MyClass {

    

    private static final List<Privilege> ALL_PRIVILEGES = asList(Privilege.values());

    private static List<User> sortByAgeDescAndNameAsc(final List<User> users) {

        List<User> listOfUsers = users;
        //First implementation
        Collections.sort(listOfUsers, Comparator.comparing(User::getFirstName));
        Collections.sort(listOfUsers, Comparator.comparingInt(User::getAge).reversed());

        return users;

    }


    private boolean testShouldSortUsersByAgeDescAndNameDesc() {
        final User user1 = new User(1L, "John", "Doe", 26, ALL_PRIVILEGES);
        final User user2 = new User(2L, "Greg", "Smith", 30, ALL_PRIVILEGES);
        final User user4 = new User(2L, "AGreg", "Smith", 30, ALL_PRIVILEGES);
        final User user3 = new User(3L, "Alex", "Smith", 13, ALL_PRIVILEGES);

        final List<User> sortedUsers =
                sortByAgeDescAndNameAsc(asList(user1, user2, user3, user4));

        return (sortedUsers).equals(asList(user4, user2, user1, user3));
    }

    private boolean testBool(){
        return false;
    }
}