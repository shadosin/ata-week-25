package com.kenzie.streams.drills;

import com.kenzie.streams.drills.resources.Card;
import com.kenzie.streams.drills.resources.Dish;
import com.kenzie.streams.drills.resources.Rank;
import com.kenzie.streams.drills.resources.Suit;
import com.kenzie.streams.drills.resources.User;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static com.kenzie.streams.drills.resources.Dish.Type.FISH;
import static com.kenzie.streams.drills.resources.Dish.Type.MEAT;
import static com.kenzie.streams.drills.resources.Dish.Type.OTHER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ConvertToStreamsDrillsTest {
    private static final User USER1 = User.builder().withName("Sonia").withAge(29).build();
    private static final User USER2 = User.builder().withName("Halston").withAge(67).build();
    private static final User USER3 = User.builder().withName("Navid").withAge(29).build();
    private static final User USER4 = User.builder().withName("Asim").withAge(18).build();
    private static final User USER5 = User.builder().withName("Jude").withAge(18).build();

    private static final Dish SUSHI = Dish.builder()
            .withCalories(400)
            .withDishType(FISH)
            .withIsVegetarian(false)
            .withName("Sushi Plate")
            .build();

    private static final Dish STEAK = Dish.builder()
            .withCalories(900)
            .withDishType(MEAT)
            .withIsVegetarian(false)
            .withName("Steak And Potatoes")
            .build();

    private static final Dish CHICKEN = Dish.builder()
            .withCalories(1100)
            .withDishType(MEAT)
            .withIsVegetarian(false)
            .withName("Fried Chicken")
            .build();

    private static final Dish SALAD = Dish.builder()
            .withCalories(300)
            .withDishType(OTHER)
            .withIsVegetarian(true)
            .withName("Harvest Salad")
            .build();

    @Test
    public void returnSquareRootTest_emptyList_returnsEmptyList() {
        assertReturnSquareRoot(Collections.emptyList(), Collections.emptyList());
    }

    @Test
    public void returnSquareRootTest_nullList_returnsEmptyList() {
        assertReturnSquareRoot(null, Collections.emptyList());
    }

    @Test
    public void returnSquareRootTest_zero_returnsZero() {
        assertReturnSquareRoot(Arrays.asList(0), Arrays.asList((double) 0));
    }

    @Test
    public void returnSquareRootTest_withSingleNumbers_returnsSquareRoot() {
        assertReturnSquareRoot(Arrays.asList(16), Arrays.asList((double) 4));

        assertReturnSquareRoot(Arrays.asList(5), Arrays.asList(Math.sqrt(5)));
    }

    @Test
    public void returnSquareRootTest_multipleNumbers_returnsSquareRoots() {
        assertReturnSquareRoot(Arrays.asList(3, 9, -4, 29),
            Arrays.asList(Math.sqrt(3), Math.sqrt(9), Math.sqrt(-4), Math.sqrt(29)));

        assertReturnSquareRoot(Arrays.asList(3, 3, 3, 3),
            Arrays.asList(Math.sqrt(3), Math.sqrt(3), Math.sqrt(3), Math.sqrt(3)));
    }

    @Test
    public void getAgeFromUsersTest_emptyList_returnsEmptyList() {
        assertGetAgeFromUsers(Collections.emptyList(), Collections.emptyList());
    }

    @Test
    public void getAgeFromUsersTest_nullList_returnsEmptyList() {
        assertGetAgeFromUsers(null, Collections.emptyList());
    }

    @Test
    public void getAgeFromUsersTest_singleUser_returnsAge() {
        assertGetAgeFromUsers(Arrays.asList(USER1), Arrays.asList(29));
    }

    @Test
    public void getAgeFromUsersTest_multipleUsers_returnsAges() {
        assertGetAgeFromUsers(Arrays.asList(USER1, USER1, USER1), Arrays.asList(29, 29, 29));
        assertGetAgeFromUsers(Arrays.asList(USER1, USER2, USER3, USER4, USER5), Arrays.asList(29, 67, 29, 18, 18));
    }

    @Test
    public void getDistinctAges_emptyList_returnsEmptyList() {
        assertGetDistinctAges(Collections.emptyList(), Collections.emptyList());
    }

    @Test
    public void getDistinctAges_nullList_returnsEmptyList() {
        assertGetDistinctAges(null, Collections.emptyList());
    }

    @Test
    public void getDistinctAgesTest_singleUser_returnsAge() {
        assertGetDistinctAges(Arrays.asList(USER1), Arrays.asList(29));
    }

    @Test
    public void getDistinctAgesTest_duplicateUser_returnsOneAge() {
        assertGetDistinctAges(Arrays.asList(USER1, USER1), Arrays.asList(29));
    }

    @Test
    public void getDistinctAgesTest_sharedAge_returnsOneAge() {
        assertGetDistinctAges(Arrays.asList(USER1, USER3), Arrays.asList(29));
    }

    @Test
    public void getDistinctAgesTest_differentAges_returnsAllAges() {
        assertGetDistinctAges(Arrays.asList(USER1, USER2), Arrays.asList(29, 67));
    }

    @Test
    public void getDistinctAgesTest_mixedAges_returnsUniqueAges() {
        assertGetDistinctAges(Arrays.asList(USER1, USER2, USER3, USER4, USER5), Arrays.asList(29, 67, 18));
    }

    @Test
    public void getLimitedUserListTest_emptyList_returnsEmptyList() {
        assertGetLimitedUserList(Collections.emptyList(), 5, Collections.emptyList());
    }

    @Test
    public void getLimitedUserListTest_nullList_returnsEmptyList() {
        assertGetLimitedUserList(null, 5, Collections.emptyList());
    }

    @Test
    public void getLimitedUserListTest_singleUserLimit1_returnsUser() {
        assertGetLimitedUserList(Arrays.asList(USER1), 1, Arrays.asList(USER1));
    }

    @Test
    public void getLimitedUserListTest_singleUserLimit0_returnsEmptyList() {
        assertGetLimitedUserList(Arrays.asList(USER1), 0, Collections.emptyList());
    }

    @Test
    public void getLimitedUserListTest_singleUserLimit5_returnsUser() {
        assertGetLimitedUserList(Arrays.asList(USER1), 5, Arrays.asList(USER1));
    }

    @Test
    public void getLimitedUserListTest_multipleUsersLimit0_returnsEmptyList() {
        assertGetLimitedUserList(Arrays.asList(USER1, USER1, USER1), 0, Collections.emptyList());
    }

    @Test
    public void getLimitedUserListTest_multipleUsersLimit1_returnsOneUser() {
        assertGetLimitedUserList(Arrays.asList(USER1, USER1, USER1), 1, Arrays.asList(USER1));
    }

    @Test
    public void getLimitedUserListTest_multipleUserLimit2_returnsTwoUsers() {
        assertGetLimitedUserList(Arrays.asList(USER1, USER1, USER1), 2, Arrays.asList(USER1, USER1));
    }

    @Test
    public void getLimitedUserListTest_multipleUsersLimit3_returnsThreeUsers() {
        assertGetLimitedUserList(Arrays.asList(USER1, USER1, USER1), 3, Arrays.asList(USER1, USER1, USER1));
    }

    @Test
    public void getLimitedUserListTest_threeUserLimit5_returnsThreeUsers() {
        assertGetLimitedUserList(Arrays.asList(USER1, USER1, USER1), 5, Arrays.asList(USER1, USER1, USER1));
    }

    @Test
    public void countUserOlderThan25_emptyList_returnsZero() {
        assertCountUsersOlderThen25(Collections.emptyList(), 0);
    }

    @Test
    public void countUserOlderThan25_singleOlderUser_returnsOne() {
        assertCountUsersOlderThen25(Arrays.asList(USER1), 1);
    }

    @Test
    public void countUserOlderThan25_oneYoungerUser_returnsZero() {
        assertCountUsersOlderThen25(Arrays.asList(USER4), 0);
    }

    @Test
    public void countUserOlderThan25_twoOlderUsers_returnsTwo() {
        assertCountUsersOlderThen25(Arrays.asList(USER1, USER1), 2);
    }

    @Test
    public void countUserOlderThan25_twoYoungerUsers_returnsZero() {
        assertCountUsersOlderThen25(Arrays.asList(USER4, USER4), 0);
    }

    @Test
    public void countUserOlderThan25_mixedUser_countsOlderUsers() {
        assertCountUsersOlderThen25(Arrays.asList(USER1, USER2, USER3, USER4, USER5), 3);
    }

    @Test
    public void countUserOlderThan25_nullUserList_returnsZero() {
        assertCountUsersOlderThen25(null, 0);
    }

    @Test
    public void findAny_noUsersWithName_isEmpty() {
        List<User> names = Arrays.asList(USER1, USER2, USER3, USER4);
        assertFalse(ConvertToStreamsDrills.findAny(names, "Joe").isPresent());
    }

    @Test
    public void findAny_emptyList_isEmpty() {
        List<User> names = Collections.emptyList();
        assertFalse(ConvertToStreamsDrills.findAny(names, "Joe").isPresent());
    }

    @Test
    public void findAny_nullList_isEmpty() {
        Optional<User> user = ConvertToStreamsDrills.findAny(null, "Joe");
        assertNotNull(user);
        assertFalse(user.isPresent());
    }

    @Test
    public void findAny_oneUserWithName_returnsUserWithName() {
        List<User> names = Arrays.asList(USER1, USER2, USER3, USER4);
        assertTrue(ConvertToStreamsDrills.findAny(names, "Navid").isPresent());
    }

    @Test
    public void findAny_manyUsersWithName_returnsAUserWithName() {
        List<User> names = Arrays.asList(USER1, USER3, USER2, USER3, USER4, USER3, USER3);
        assertTrue(ConvertToStreamsDrills.findAny(names, "Navid").isPresent());
    }

    @Test
    public void sortDishesByCalories_emptyMenu_returnsEmptyList() {
        List<Dish> dishes = Collections.emptyList();
        List<String> result = ConvertToStreamsDrills.sortDishesByCalories(dishes);
        assertTrue(result.isEmpty());
    }

    @Test
    public void sortDishesByCalories_nullMenu_returnsEmptyList() {
        List<String> result = ConvertToStreamsDrills.sortDishesByCalories(null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void sortDishesByCalories_allUnder400_returnsAllDishes() {
        List<Dish> dishes = Arrays.asList(SALAD, SALAD, SALAD);
        List<String> result = ConvertToStreamsDrills.sortDishesByCalories(dishes);
        assertEquals(Arrays.asList("Harvest Salad", "Harvest Salad", "Harvest Salad"), result);
    }

    @Test
    public void sortDishesByCalories_allOver400_returnsEmptyList() {
        List<Dish> dishes = Arrays.asList(SUSHI, CHICKEN, STEAK);
        List<String> result = ConvertToStreamsDrills.sortDishesByCalories(dishes);
        assertTrue(result.isEmpty());
    }

    @Test
    public void sortDishesByCalories_randomDishes_returnsSortedList() {
        List<Dish> dishes = Arrays.asList(SUSHI, SALAD, SALAD, STEAK, SALAD);
        List<String> result = ConvertToStreamsDrills.sortDishesByCalories(dishes);
        assertEquals(Arrays.asList("Harvest Salad", "Harvest Salad", "Harvest Salad"), result);
    }

    @Test
    public void newDeck_containsAllCards() {
        List<Suit> suits = Arrays.asList(Suit.values());
        List<Rank> ranks = Arrays.asList(Rank.values());
        List<Card> deck = ConvertToStreamsDrills.newDeck(suits, ranks);
        assertEquals(52, deck.size());
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                assertTrue(deck.contains(new Card(suit, rank)));
            }
        }
    }

    public void assertReturnSquareRoot(List<Integer> initial, List<Double> expected) {
        List<Double> actual = ConvertToStreamsDrills.returnSquareRoot(initial);
        assertEquals(expected,
            actual,
            String.format("When calling returnSquareRoot on %s, expected response of %s, but received %s.",
                initial,
                expected,
                actual));
    }

    public void assertGetAgeFromUsers(List<User> initial, List<Integer> expected) {
        List<Integer> actual = ConvertToStreamsDrills.getAgeFromUsers(initial);
        assertEquals(expected,
            actual,
            String.format("When calling getAgeFromUsers on %s, expected response of %s, but received %s.",
                initial,
                expected,
                actual));
    }

    public void assertGetDistinctAges(List<User> initial, List<Integer> expected) {
        List<Integer> actual = ConvertToStreamsDrills.getDistinctAges(initial);
        assertEquals(expected.stream().sorted().collect(Collectors.toList()),
            actual.stream().sorted().collect(Collectors.toList()),
            String.format("When calling getDistinctAges on %s, expected response of %s, but received %s.",
                initial,
                expected,
                actual));
    }

    public void assertGetLimitedUserList(List<User> initial, int limit, List<User> expected) {
        List<User> actual = ConvertToStreamsDrills.getLimitedUserList(initial, limit);
        assertEquals(expected,
            actual,
            String.format("When calling getLimitedUserList on %s, expected response of %s, but received %s.",
                initial,
                expected,
                actual));
    }

    public void assertCountUsersOlderThen25(List<User> initial, long expected) {
        long actual = ConvertToStreamsDrills.countUsersOlderThan25(initial);
        assertEquals(expected,
            actual,
            String.format("When calling countUsersOlderThan25 on %s, expected response of %s, but received %s.",
                initial,
                expected,
                actual));
    }

    //<----------------------- Testing for stream implementation ------------------------------>

    @Test
    public void returnSquareRoot_usesStreams(){
        List<Integer> list = new ArrayList<>(Arrays.asList(1,3,5,7));
        List<Integer> spylist = spy(list);

        ConvertToStreamsDrills.returnSquareRoot(spylist);

        assertDoesNotThrow(() -> verify(spylist).stream(),
                ".stream() and stream methods should be called on the list.\n\n");
        //testing that the for-each loop is no longer being used
        assertDoesNotThrow(() -> verify(spylist, never()).iterator(),
                "A for loop should not be used on the list,"
                        + ".stream() and stream methods should be used instead.\n\n");
    }

    @Test
    public void getAgeFromUsers_usesStreams(){
        List<User> list = new ArrayList<>(Arrays.asList(USER1, USER1, USER1));
        List<User> spylist = spy(list);

        ConvertToStreamsDrills.getAgeFromUsers(spylist);

        assertDoesNotThrow(() -> verify(spylist).stream(),
                ".stream() and stream methods should be called on the list.\n\n");
        //testing that the for-each loop is no longer being used
        assertDoesNotThrow(() -> verify(spylist, never()).iterator(),
                "A for loop should not be used on the list,"
                        + ".stream() and stream methods should be used instead.\n\n");
    }

    @Test
    public void getDistinctAges_usesStreams(){
        List<User> list = new ArrayList<>(Arrays.asList(USER1, USER1, USER1));
        List<User> spylist = spy(list);

        ConvertToStreamsDrills.getDistinctAges(spylist);

        assertDoesNotThrow(() -> verify(spylist).stream(),
                ".stream() and stream methods should be called on the list.\n\n");
        //testing that the for-each loop is no longer being used
        assertDoesNotThrow(() -> verify(spylist, never()).iterator(),
                "A for loop should not be used on the list,"
                        + ".stream() and stream methods should be used instead.\n\n");
    }

    @Test
    public void getLimitedUserList_usesStreams(){
        List<User> list = new ArrayList<>(Arrays.asList(USER1, USER1, USER1));
        List<User> spylist = spy(list);

        ConvertToStreamsDrills.getLimitedUserList(spylist,2);

        assertDoesNotThrow(() -> verify(spylist).stream(),
                ".stream() and stream methods should be called on the list.\n\n");
        //testing that the for-each loop is no longer being used
        assertDoesNotThrow(() -> verify(spylist, never()).iterator(),
                "A for loop should not be used on the list,"
                        + ".stream() and stream methods should be used instead.\n\n");
    }

    @Test
    public void countUsersOlderThan25_usesStreams(){
        List<User> list = new ArrayList<>(Arrays.asList(USER1, USER1, USER1));
        List<User> spylist = spy(list);

        ConvertToStreamsDrills.countUsersOlderThan25(spylist);

        assertDoesNotThrow(() -> verify(spylist).stream(),
                ".stream() and stream methods should be called on the list.\n\n");
        //testing that the for-each loop is no longer being used
        assertDoesNotThrow(() -> verify(spylist, never()).iterator(),
                "A for loop should not be used on the list,"
                        + ".stream() and stream methods should be used instead.\n\n");
    }

    @Test
    public void findAny_usesStreams(){
        List<User> list = new ArrayList<>(Arrays.asList(USER1, USER1, USER1));
        List<User> spylist = spy(list);

        ConvertToStreamsDrills.findAny(spylist,"name");

        assertDoesNotThrow(() -> verify(spylist).stream(),
                ".stream() and stream methods should be called on the list.\n\n");
        //testing that the for-each loop is no longer being used
        assertDoesNotThrow(() -> verify(spylist, never()).iterator(),
                "A for loop should not be used on the list,"
                        + ".stream() and stream methods should be used instead.\n\n");
    }

    @Test
    public void sortDishesByCalories_usesStreams(){
        List<Dish> dishes = new ArrayList<>(Arrays.asList(SUSHI, SALAD, SALAD, STEAK, SALAD));
        List<Dish> spylist = spy(dishes);

        ConvertToStreamsDrills.sortDishesByCalories(spylist);

        assertDoesNotThrow(() -> verify(spylist).stream(),
                ".stream() and stream methods should be called on the list.\n\n");
        //testing that the for-each loop is no longer being used
        assertDoesNotThrow(() -> verify(spylist, never()).iterator(),
                "A for loop should not be used on the list,"
                        + ".stream() and stream methods should be used instead.\n\n");
    }

    @Test
    public void newDeck_usesStream(){
        List<Suit> suits = new ArrayList<>(Arrays.asList(Suit.values()));
        List<Rank> ranks = new ArrayList<>(Arrays.asList(Rank.values()));
        List<Suit> spySuits = spy(suits);
        List<Rank> spyRanks = spy(ranks);

        ConvertToStreamsDrills.newDeck(spySuits,spyRanks);

        assertDoesNotThrow(() -> verify(spySuits, atLeast(1)).stream(),
                ".stream() and stream methods should be called on Suits.\n\n");
        assertDoesNotThrow(() -> verify(spyRanks, atLeast(1)).stream(),
                ".stream() and stream methods should be called on Ranks.\n\n");
        //testing that the for-each loops are no longer being used
        assertDoesNotThrow(() -> verify(spySuits, never()).iterator(),
                "A for loop should not be used on the Suits, .stream() and stream methods should be used instead.\n\n");
        assertDoesNotThrow(() -> verify(spyRanks, never()).iterator(),
                "A for loop should not be used on the Ranks, .stream() and stream methods should be used instead.\n\n");
    }

}
