package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLOutput;

public class Main {
    public static void main(String[] args) {

        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Ivan", "Ivanov", (byte) 32);
        userService.saveUser("Jamie", "Oliver", (byte) 23);
        userService.saveUser("James", "Newman", (byte) 32);
        userService.saveUser("Gordon", "Ramsay", (byte) 32);

        System.out.println("All users");
        userService.getAllUsers().forEach(p -> System.out.println(p.toString()));

        System.out.println("Removed id 2");
        userService.removeUserById(2);
        userService.getAllUsers().forEach(p -> System.out.println(p.toString()));

        System.out.println("Cleared table");
        userService.cleanUsersTable();
        userService.getAllUsers().forEach(p -> System.out.println(p.toString()));

        System.out.println("Delete table");
        userService.dropUsersTable();

    }
}
