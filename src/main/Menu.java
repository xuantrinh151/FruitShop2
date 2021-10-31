/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import entity.Fruit;
import entity.Order;
import java.util.ArrayList;
import manage.ManagerFruit;
import java.util.Hashtable;
import validation.Validation;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class Menu {

    public static void main(String[] args) {
        ManagerFruit managerFruit = new ManagerFruit();
        while (true) {
            managerFruit.menu();
            int choice = Validation.checkInputIntLimit(1, 5);
            switch (choice) {
                case 1:
                    managerFruit.createFruit();
                    managerFruit.displayAllFruit();
                    break;
                case 2:
                    managerFruit.updateFruit();
                    managerFruit.displayAllFruit();
                    break;
                case 3:
                    managerFruit.viewOrder();
                    break;
                case 4:
                    managerFruit.shoppingFruit();
                    break;
                case 5:
                    return;
            }
        }
    }
}
