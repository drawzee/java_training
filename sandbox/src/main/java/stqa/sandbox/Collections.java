package stqa.sandbox;

import java.util.Arrays;
import java.util.List;

public class Collections {

    public static void main(String[] args) {
        String[] langs = {"Java", "C++", "Python", "Python"};

        List<String> languages = Arrays.asList("Java", "C++", "Python", "Python");

        for (String l : languages) {
            System.out.println("I'm picking " + l);
        }
    }

}
