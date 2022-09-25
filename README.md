# GreedyGame_Sample

This is the completed Sample application which uses follows following architechture and libraries:- 

* MVVM architecture
* Room Database
* Picasso
* Retrofit
* View binding


1. MVVM architecture 

    it follows MVVM architecture. it has viewModel classes such as ArticleViewModel and NewsViewModel. using MVVM architecture has many advantages. Some
of them are:- 
     * Maintainability
          it helps in maintaining a clean code, which later helps in making future updates in code easily.
     * Extensible
          it makes easy to reuse the code.
     * Testability
          it makes the testing of the code easy due to seperation of functionality in the code.
       
  here in this application views dosen't directly call api for collecting News Data, rather it is viewModel which does this work. later viewModel provides
the updated data to the views.

2. Room Database

    Here I have used Room library which is a wrapper around the SQLite database support which android gives. I have used it to store the article in the
persistent fomat. It requires the interface called DAO or data access object. it requires a class which extends by RoomDatabase. it is used to create object
of database in a singleton pattern. also an ArticleItem data class is defined which uses the Entity in the room database.

3. Picasso 

    It is a library to fill a image with some source image present at a given web url.
    
4. Retrofit

    It is a library for network calls. I Have used it with Kotlin Coroutine so as to make GET request call to fetch the data from the api.

5. View Binding

   I have used View Binding in the complete project. It provides features such as null safety etc.
   
