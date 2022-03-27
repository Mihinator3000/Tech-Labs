import enums.Color;
import models.Cat;
import models.Owner;
import services.CatService;
import services.OwnerService;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        var ownerService = new OwnerService();
        var catService = new CatService();

        var cat = new Cat("Name1");
        var cat2 = new Cat("Name2");
        cat.setFriends(List.of(cat2));

        catService.add(cat);

        var all = catService.getAll();
        var lastCat = all.get(all.size() - 1);
        var lastLastCat = all.get(all.size() - 2);
        System.out.println(lastCat.getFriends().size() + " " + lastLastCat.getFriends().size());
        /*
        var owner = new Owner("some name");
        service.add(owner);*/
        /*var owner = service.get(5);
        owner.setName("Huh yuyevich");
        service.update(owner);
        service.getAll().forEach(u -> System.out.println(u.getId() + " " + u.getName()));*/


/*

        var all = service.getAll();

        var cat2 = new Cat("Kot3");
        cat2.setOwner(all.get(all.size() - 1));
        cat2.setColor(Color.BLACK);
        cat2.setBirthDate(LocalDate.now());
        cat2.setBreed("Some breed");

        new CatService().add(cat2);*/


        /*
        var owner = new Owner("Mikhail3");
        var cat = new Cat("Koshka3");
        cat.setColor(Color.CHOCOLATE);

        //new CatService().add(cat);
        owner.setCats(List.of(cat));
        service.add(owner);
        var all = service.getAll();
        for (var a: all
             ) {
            System.out.println(a.getId() + a.getName());
        }
        service.delete(all.get(all.size() - 1).getId());

        */


/*
        var all = service.getAll();
        owner = all.get(all.size() - 1);
        owner.getCats().remove(0);
        service.update(owner);*/
        /*service.delete();*/

    }
}
