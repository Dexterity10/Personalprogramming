package Whodunnit;

import java.util.ArrayList;
import java.util.List;

import Suspect;

public class Main {

    private Suspect[] getList() {
        List<Suspect>[] susList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Suspect person = new Suspect("Person " + i);
            susList.add(person);
        }
        return susList;
    }

    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}