package com.lambdaschool.foundation;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import com.lambdaschool.foundation.models.*;
import com.lambdaschool.foundation.services.CategoryService;
import com.lambdaschool.foundation.services.RecipeService;
import com.lambdaschool.foundation.services.RoleService;
import com.lambdaschool.foundation.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * SeedData puts both known and random data into the database. It implements CommandLineRunner.
 * <p>
 * CoomandLineRunner: Spring Boot automatically runs the run method once and only once
 * after the application context has been loaded.
 */
@Transactional
@ConditionalOnProperty(
    prefix = "command.line.runner",
    value = "enabled",
    havingValue = "true",
    matchIfMissing = true)
@Component
public class SeedData
    implements CommandLineRunner
{
    /**
     * Connects the Role Service to this process
     */
    @Autowired
    RoleService roleService;
    /**
     * Connects the user service to this process
     */
    @Autowired
    UserService userService;

    @Autowired
    RecipeService recipeService;

    @Autowired
    CategoryService categoryService;
    /**
     * Generates test, seed data for our application
     * First a set of known data is seeded into our database.
     * Second a random set of data using Java Faker is seeded into our database.
     * Note this process does not remove data from the database. So if data exists in the database
     * prior to running this process, that data remains in the database.
     *
     * @param args The parameter is required by the parent interface but is not used in this process.
     */
    @Transactional
    @Override
    public void run(String[] args) throws
                                   Exception
    {
        userService.deleteAll();
        roleService.deleteAll();
        recipeService.deleteAll();
        categoryService.deleteAll();

        Role r1 = new Role("admin");
        Role r2 = new Role("user");
        Role r3 = new Role("data");

        r1 = roleService.save(r1);
        r2 = roleService.save(r2);
        r3 = roleService.save(r3);

        // admin, data, user
        User u1 = new User("admin",
            "password",
            "admin@lambdaschool.local");
        u1.getRoles()
            .add(new UserRoles(u1,
                r1));
        u1.getRoles()
            .add(new UserRoles(u1,
                r2));
        u1.getRoles()
            .add(new UserRoles(u1,
                r3));
        u1.getUseremails()
            .add(new Useremail(u1,
                "admin@email.local"));
        u1.getUseremails()
            .add(new Useremail(u1,
                "admin@mymail.local"));

        userService.save(u1);

        // data, user
        User u2 = new User("cinnamon",
            "1234567",
            "cinnamon@lambdaschool.local");
        u2.getRoles()
            .add(new UserRoles(u2,
                r2));
        u2.getRoles()
            .add(new UserRoles(u2,
                r3));
        u2.getUseremails()
            .add(new Useremail(u2,
                "cinnamon@mymail.local"));
        u2.getUseremails()
            .add(new Useremail(u2,
                "hops@mymail.local"));
        u2.getUseremails()
            .add(new Useremail(u2,
                "bunny@email.local"));
        userService.save(u2);

        // user
        User u3 = new User("barnbarn",
            "ILuvM4th!",
            "barnbarn@lambdaschool.local");
        u3.getRoles()
            .add(new UserRoles(u3,
                r2));
        u3.getUseremails()
            .add(new Useremail(u3,
                "barnbarn@email.local"));
        userService.save(u3);

        User u4 = new User("puttat",
            "password",
            "puttat@school.lambda");
        u4.getRoles()
            .add(new UserRoles(u4,
                r2));
        userService.save(u4);

        User u5 = new User("misskitty",
            "password",
            "misskitty@school.lambda");
        u5.getRoles()
            .add(new UserRoles(u5,
                r2));
        userService.save(u5);


        /************
         * Seed Recipes
         ************/

//        Author a1 = new Author("John", "Mitchell");
//        Author a2 = new Author("Dan", "Brown");
//        Author a3 = new Author("Jerry", "Poe");
//        Author a4 = new Author("Wells", "Teague");
//        Author a5 = new Author("George", "Gallinger");
//        Author a6 = new Author("Ian", "Stewart");

//        a1 = authorService.save(a1);
//        a2 = authorService.save(a2);
//        a3 = authorService.save(a3);
//        a4 = authorService.save(a4);
//        a5 = authorService.save(a5);
//        a6 = authorService.save(a6);

        Category c1 = new Category("French");
        Category c2 = new Category("Japanese");
        Category c3 = new Category("Chinese");
        Category c4 = new Category("Spanish");
        Category c5 = new Category("Italian");

        c1 = categoryService.save(c1);
        c2 = categoryService.save(c2);
        c3 = categoryService.save(c3);
        c4 = categoryService.save(c4);
        c5 = categoryService.save(c5);


        Recipe rp1 = new Recipe("Cassoulet", "France", "6 Duck Legs, 3tbps kosher salt, spring thyme, 4 garlic cloves,...+more", "Step1: CureDuckLegs, Step2: spice rub legs, Step3: do the rest", c1);
        rp1 = recipeService.save(rp1);

        Recipe rp2 = new Recipe("Hoto", "Japan", "Tofu-kabocha-carrots-negi-daikon-potato-napa-cabbage-seasonal-veggies-more", "Step1: Dashi, Step2: Mix Hoto, Step3: do the rest", c2);
        rp2 = recipeService.save(rp2);

        Recipe rp3 = new Recipe("Xiaolongbao", "China", "Pork Aspic, Dough stuff, pork filling,...+more", "Step1: Assemble, Step2: Steam, Step3: do the rest", c3);
        rp3 = recipeService.save(rp3);

        Recipe rp4 = new Recipe("Fideua", "Spanish", "Squid, Pasta, olive oil, prawns,...+more", "Step1: Pasta, Step2: Squid n Prawn fry, Step3: do the rest", c4);
        rp4 = recipeService.save(rp4);

        Recipe rp5 = new Recipe("Veal Milanese", "Italy", "4 eggs, a truck of basil, veal scallopine, bread crumbs,...+more", "Step1: preheat oven, Step2: spice the veal, Step3: do the rest", c5);
        rp5 = recipeService.save(rp5);


        if (false)
        {
            // using JavaFaker create a bunch of regular users
            // https://www.baeldung.com/java-faker
            // https://www.baeldung.com/regular-expressions-java

            FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-US"),
                new RandomService());
            Faker nameFaker = new Faker(new Locale("en-US"));

            for (int i = 0; i < 25; i++)
            {
                new User();
                User fakeUser;

                fakeUser = new User(nameFaker.name()
                    .username(),
                    "password",
                    nameFaker.internet()
                        .emailAddress());
                fakeUser.getRoles()
                    .add(new UserRoles(fakeUser,
                        r2));
                fakeUser.getUseremails()
                    .add(new Useremail(fakeUser,
                        fakeValuesService.bothify("????##@gmail.com")));
                userService.save(fakeUser);
            }
        }
    }
}