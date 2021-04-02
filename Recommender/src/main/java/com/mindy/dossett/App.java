package com.mindy.dossett;


/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
//        UserBasedRating userR = new UserBasedRating("9", 2, 1,
//                new RepeatFilter(UserDatabase.getUser("9")));
//        System.out.println(userR.getSimilarRatings());
//        ItemBasedRating userM = new ItemBasedRating("9", 3, 1,
//                new RepeatFilter(UserDatabase.getUser("9")));
//        System.out.println(userM.getSimilarRatings());
        UserInfoInitializer initializer = new UserInfoInitializer();
        System.out.println(initializer.getMovieForRating());
    }
}
