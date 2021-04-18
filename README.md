# SimpleJavaRecommender
> This simple Java Recommender Project is an extension of the [Movie Recommendation System Capstone Project](https://www.coursera.org/learn/java-programming-recommender/home/welcome), which is the final course of the([Java Programming and Software Engineering Fundamentals Specialization](https://www.coursera.org/specializations/java-programming) by the Duke University.

> Over the years, there have been many advanced algorithms for recommendation systems such as matrix factorization, cluster-based approaches, probabilistic methods, and more. This simple project focuses on nearest neighbor collaborative filtering algorithm. 

## Data
* Since nearest neighbor collaborative filtering either movie based or user based tends to not scale well with large data, we will be using movie and user rating data from the [DukeLearnToProgram](https://www.dukelearntoprogram.com/course5/data/ratingsdata.zip).  

  * `movies.csv`: 3,143 rows of movie information with the following columns: id, title, country, genre, director, poster as `String` and year and minutes as `int`. 
  
  * `ratings.csv`: 10,000 rows of rating information with the following columns: rater_id and movie_id as type `String` and rating and time as `int`. The time column uses Unix Timestamp.
  
## Algorithm Approach: 

> As mentioned earlier, we will be using collaborative filtering based on either movies or users. There are a few ways to define similarity among users or among movies and we will be focusing on: 

* Pearson Correlation  
* Cosine Similarity  

> A few key areas to improve based upon the existing Duke project:
  1. cold start: asks the new user to rate 20 randomly sampled movies with a set of criteria. 
  2. remove individual user baseline bias by subtracting the average rating. 
  3. Incorporate both item based and user based collaborative filters. 

## Blog post:

> link: https://mindy-dossett.com/2021/04/18/collaborative-filtering-in-Java/
