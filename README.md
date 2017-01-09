# Project 1 - *Flicks*

**Flicks** shows the latest movies currently playing in theaters. The app utilizes the Movie Database API to display images and basic information about these movies to the user.

Time spent: **24** hours spent in total

## User Stories

The following **required** functionality is completed:

* User can **scroll through current movies** from the Movie Database API
* Layout is optimized with the [ViewHolder](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView#improving-performance-with-the-viewholder-pattern) pattern.
* For each movie displayed, user can see the following details:
  * Title, Poster Image, Overview (Portrait mode)
  * Title, Backdrop Image, Overview (Landscape mode)

The following **optional** features are implemented:

* Display a nice default [placeholder graphic](http://guides.codepath.com/android/Displaying-Images-with-the-Picasso-Library#configuring-picasso) for each image during loading.
* Improved the user interface through styling and coloring.

The following **bonus** features are implemented:

* Allow user to view details of the movie including ratings and popularity within a separate activity or dialog fragment.
* When viewing a popular movie (i.e. a movie voted for more than 5 stars) the video should show the full backdrop image as the layout.  Uses [Heterogenous ListViews](http://guides.codepath.com/android/Implementing-a-Heterogenous-ListView) or [Heterogenous RecyclerView](http://guides.codepath.com/android/Heterogenous-Layouts-inside-RecyclerView) to show different layouts.
* Allow video trailers to be played in full-screen using the YouTubePlayerView.
    *  Overlay a play icon for videos that can be played.
    *  More popular movies should start a separate activity that plays the video immediately.
    *  Less popular videos rely on the detail page should show ratings and a YouTube preview.

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/nhYrekf.gif'/> <img src='http://i.imgur.com/j6wWdPd.gif'/>

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android

## License

    Copyright 2017 Ukyo Cheng

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.