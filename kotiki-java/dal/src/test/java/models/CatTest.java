package models;

import enums.Color;
import org.junit.jupiter.api.Test;
import tools.DalException;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CatTest {

    @Test
    public void testEqualsById() {
        var cat1 = Cat.builder()
                .id(1)
                .name("Name")
                .color(Color.CREAM)
                .build();

        var cat2 = Cat.builder()
                .id(1)
                .name("Other Name")
                .color(Color.BLUE)
                .build();

        assertEquals(cat1, cat2);
    }

    @Test
    public void getFriendsReturnsListConcat() {
        var friendCat1 = Cat.builder()
                .id(1)
                .name("Name1")
                .build();

        var friendCat2 = Cat.builder()
                .id(2)
                .name("Name2")
                .build();

        var cat = Cat.builder()
                .id(3)
                .name("Name")
                .friends(List.of(friendCat1))
                .build();

        assertDoesNotThrow(() -> {
            Field field = Cat.class.getDeclaredField("friendsOf");
            field.setAccessible(true);
            field.set(cat, List.of(friendCat2));
        });

        List<Cat> friendsList = cat.getFriends();

        assertEquals(2, friendsList.size());

        assertArrayEquals(new Cat[]{friendCat1, friendCat2},
                friendsList.toArray());
    }

    @Test
    public void getFriendsCannotModifyList_ThrowException() {
        var friendCat = Cat.builder()
                .id(1)
                .name("Name")
                .build();

        var cat = Cat.builder()
                .id(2)
                .name("Name")
                .friends(List.of(friendCat))
                .build();

        List<Cat> friendsList = cat.getFriends();

        assertThrows(UnsupportedOperationException.class, () ->
                friendsList.add(new Cat("Some name")));

        assertThrows(UnsupportedOperationException.class, () ->
                friendsList.remove(friendCat));
    }

    @Test
    public void createCatWithoutAName_ThrowException() {
        assertThrows(NullPointerException.class, () ->
                Cat.builder().build());
    }

    @Test
    public void addFriend_CatAlreadyAFriend_ThrowException() {
        var cat = Cat.builder()
                .id(1)
                .name("Name")
                .build();

        var friendCat = Cat.builder()
                .id(2)
                .name("Friend")
                .build();

        cat.addFriend(friendCat);

        assertThrows(DalException.class, () ->
                cat.addFriend(friendCat));
    }

    @Test
    public void deleteFromFriends_CatIsNotAFriend_ThrowException() {
        var friendCat = Cat.builder()
                .id(2)
                .name("SussyCat")
                .build();

        var cat = Cat.builder()
                .id(1)
                .name("Name")
                .friends(List.of(friendCat))
                .build();

        cat.deleteFriend(2);

        assertThrows(DalException.class, () ->
                cat.deleteFriend(2));
    }
}
