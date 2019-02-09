# cs5200ProjectRandom

Application demo ->
https://youtu.be/pwqx6k-jeMk

# Problem Statement
Listening to music is one of the most popular features of a mobile phone.Previously the norm was to physically download and store songs in the phone, but this occupies a lot of storage and often slows down the phone performance.To address this problem,we are building an android app which will allow its users to stream their songs without having to store them in their phone.Another aspect we are trying to facilitate is a free platform for budding artists to share their content and get reviews on them.
# Proposed Solution
To address the above mentioned problem, our app enables the user to listen to a huge variety of songs by streaming them on the fly.This app provides various functionality for a normal user, such as listen to songs based on a particular artist or based on particular album and moreover,the user can create various playlists.The users can also follow a particular artist and like their songs or albums.The artists can be a part of this application by authorizing the share their content and in turn they can see their followers, likes on their songs and albums, and also see reviews from reviewers.
# Potential Domain objects:
Users : People using this app.
Artists : Any one wishes to share there content through this app.
Admin : Admins manages other human users
Reviewers : Can register themselves as reviewers and if authorized the admin,gets the privilege to write reviews for contents
# Goals For Human Users
# User
Users can listen to songs,search for songs/albums/artists
Make playlist
Like a song/album/artist
See reviews by reviewers
# Artists
Can register themselves as artists
Can share their contents(songs/albums)
Can see their contents being liked and reviewed
# Admin
Can perform various actions on users, such as add,delete, modify from database
Can perform various actions on artists, such as add,delete, modify from database
Can perform various actions on reviewers, such as add,delete, modify from database
# Reviewers
Can write reviews for songs from multiple artists
Can write reviews for albums from multiple artists
Can register themselves as reviewers

# Relation Between Human Users and Domain objects
# Users
Users can like a song
Users can make play lists
# Artists
Can view likes
Can view reviews
# Reviewers
Can write reviews for songs
Can write reviews for albums

# Relation Between Domain objects
# Songs
Can be added to playlist
Can be liked/reviewed
# Albums
Can add songs
Can be liked/reviewed
# Likes
Can be associated with a song
Can be associated with a album
# Reviews
Can be associated with a song
Can be associated with a album

# Usage of API
The goal of this project is to build an android app which would allow the user to play songs of their preference without downloading them into their phone and thus saving a lot space.The app provides the user a lot of options to choose from and all of these songs will be fetched from an external API.The app tries to fetch a chunk of data from the API and cache it in the app.This caching mechanism helps in minimizing the number of API calls an thus reduces the overhead of frequent network calls.In case of a scenario where a particular song is not cached, then another API call has to be made with the new request.This requests can be further customized based on few factors such as user's chosen language,user's search history, etc.
Based on a preliminary search, we have selected "last.fm" as our API choice.
