Android Sample App For Fetching  Github Repository 




1. This project consists of multiple modules
2. Dynamic Feature Module. (details repository)
3. MVVM Design Pattern




## Requirement:                                                                                       Status

Application which shows the top 50 top most starred github repositories by searching with             Done
the "Android" keyword.

 A list of repositories page where list of repositories showed.                                      Done
 List fetched from https://api.github.com/ api using "Android" as query keyword.                     Done
 List can be sorted by either last update date time or star count.                                   Partially Done (Only fetching data start count )
 Selected sorting option persists in further app sessions.                                           Not Done
 A repo details page, to which navigated by clicking on an item from the list.                       Done
 Details page shows repo owner's name, photo, repository's description, last update date             Done
 time in month-day-year hour:seconds format, each field in 2 digit numbers.                          Done (Have a issue for device bellow android 8)
 The repository which loaded once, is saved for offline browsing.                                    Done








## Used Libraries        Reason Used In this Project
 Jetpack Compose         Design Showing Repository List and Details of specific Repository
 Navigation              Used for Fragment transitions)
 View Binding            For Bind views
 ViewModel               hold Repository Data for providing to UI
 LiveData                Observe data 
 Kotlin Coroutine        Fetching  Server Data
 Dagger2                 Dependency Injection)
 Hilt                    Dependency Injection for Android)
 Room Database           Save Repository Data explore from list
 Retrofit                HTTP Request


 


## Build HTTP Client
Retrofit.Builder()
      .client(okHttpClient)
      .baseUrl("https://api.github.com/")
      .addConverterFactory(converterFactory)
      .build()

## Build Request  APi
  interface Service {
    @GET("search/repositories")
    suspend fun getHotRepos(
      @Query("q") query: String="Android",
      @Query("sort") sort: String = "stars",
      @Query("order") order: String = "desc",
      @Query("page") page: Int = 1,
      @Query("per_page") per_page: Int = 50
    ): ListResponse<RepositoryResponse>

  }

#Feature 

1. Show github repository list
2. Show Details For each repository 
3. Save Browse data
4. Delete Browse data on Long press

