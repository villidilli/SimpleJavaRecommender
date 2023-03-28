package com.mindy.dossett;
/*
Look at similarity among users to calculate average ratings for all qualified movies
https://www.geeksforgeeks.org/user-based-collaborative-filtering/
 */

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.ArrayList;
import java.util.Collections;

public class UserBasedRating extends SimilarityRatingCal {

    public UserBasedRating(String id, int neighborSize, int minRater, Filter f) {
        super(id, neighborSize, minRater, f);
    }

    private double calUserSim(User user, User other) { // возвращает схожесть оценок (реакций) пользователей на фильмы
        double similarityScore = 0.0;
        double nomUser = 0.0;
        double nomOther = 0.0;
        int minNumCommon = 0;
        ArrayList<String> userMovies = user.getMoviesRated(); // получили список айди фильмов у первого юзера
        double userAvg = user.getAvgRating(); //получил ср.ариф.всех оценок первого юзера которые он поставил фильмам
        ArrayList<String> otherMovies = other.getMoviesRated(); // получили список айди фильмов у второго юзера
        double otherAvg = other.getAvgRating(); // аналогично ср.ариф.оценок у другого юзера
        for (String mid1: userMovies) { //берём каждый айди фильма первого юзера
            for (String mid2 : otherMovies) { //берём каждый айди фильма второго юзера
                if (mid1.equals(mid2)) { //если айди равны
                    minNumCommon++; // количество обоюдно просмотренных фильмов обоими юзерами
                    double userScore = user.getRating(mid1) - userAvg; //достали оценку первого юзера для этого фильма и вычли среднюю его оценку по просмотренным фильмам
                    double otherScore = other.getRating(mid2) - otherAvg; // аналогично для второго юзера получили аля "насколько сильно ему нравится фильм относительно всех его"
                    similarityScore += (userScore) * (otherScore); // todo походу это числитель для вычисления косинуса
                    nomUser += Math.pow(userScore, 2); // возвращает квадратный корень
                    nomOther += Math.pow(otherScore, 2);
                }
            }
        }
        //если мин.число совпадение >=2, и сходство оценок пользователей !=0, и ... хз как обозвать
        if (minNumCommon >= 2 && similarityScore != 0.0 && nomUser != 0.0 && nomOther != 0.0) {
                return similarityScore / (Math.sqrt(nomUser * nomOther)); // todo вычисление косинуса угла
        }
        return -100.0; //todo походу, когда у пользователей совпадений < 2, т.е. нельзя сказать схожи они или нет, т.к. данных мало
        }


    private ArrayList<RatingLookUp> getUserSimilarity() {
        ArrayList<RatingLookUp> similarityScore = new ArrayList<RatingLookUp>();
        User user = UserDatabase.getUser(userId);
        for (User other: UserDatabase.getUsers()){
            String otherId = other.getUserId();
            if (!otherId.equals(userId)){
                double cosineScore = calUserSim(user,other);
                if (cosineScore != -100.0) {
                    similarityScore.add(new RatingLookUp(otherId, cosineScore));
                }
            }
        }
        Collections.sort(similarityScore, Collections.reverseOrder());
        return similarityScore;
    }

    @Override
    public ArrayList<RatingLookUp> getSimilarRatings() {
        ArrayList<RatingLookUp> similarityScore = getUserSimilarity();
        int numNeighors = Math.min(similarityScore.size(),similarityNum);
        ArrayList<RatingLookUp> similarityRatingList = new ArrayList<RatingLookUp>();
        ArrayList<Movie> movieList= MovieDatabase.filterBy(filter);
        Double userAvg = UserDatabase.getUser(userId).getAvgRating();
        for (Movie movie:movieList){
            String movieId = movie.getId();
            int counter = 0;
            double norm = 0.0;
            double total = 0.0;
            for (int i=0; i < numNeighors; i++){
                RatingLookUp userRating = similarityScore.get(i);
                Double cosineScore = userRating.getRatingValue();
                String otherId = userRating.getLookUpId();
                User userOther = UserDatabase.getUser(otherId);
                double otherAvg = userOther.getAvgRating();
                double rating = userOther.getRating(movieId);
                if (rating != -1 ){
                    counter++;
                    norm += Math.abs(cosineScore);
                    total += (rating-otherAvg)*cosineScore;
                }
            }
            if (counter >= minimalRater) {
                double predRating = userAvg+(total/norm);
                similarityRatingList.add(new RatingLookUp(movieId, predRating));
            }
        }
        Collections.sort(similarityRatingList, Collections.reverseOrder());
        return similarityRatingList;
    }
}
