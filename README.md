# Simple-Game

This is my 1st android game.
It's a very simple language game where the player have a word in English and a moving word in Spanish and he has to decide wether it's the equivelant word or not before it gets out of the screen.
- Player gets 1 point for each right answer.
- Player doesn't get any point for wrong answers or not answering.

# How much time was invested ?

I didn't keep count as i worked on different times during the weekend as i already had travel plans but rough estimate is around 4 to 5 hours.



# How was the time distributed ?

Most of the time went to concept and game mechanics. The architecture was pretty straight forward for a single page.
I used MVVM for presentation layer to utlize the arch components such as viewModel, LiveData and Lifecycle. 
I used coroutines for network calls as it's the fastest and the most light weight solution out there.


# decisions made to solve certain aspects of the game

I decided not to deduct any points from the player on choosing wrong or not answering as the goal is to encourage him to learn a new language.
I had to figure out the movement mechanism. And created a method to check whether the moving word is off screen or not yet.
Also About randomizing the game. I made it in 2 steps:
- A method deciding if i should put the equivlant word or not.
- Another method deciding a random index to get which word to put if not the equivalent.
- I kept the game infinite for the sake of showcase. so the index will reset once it reach the end of words.

# decisions made because of restricted time

- Design is very minimalstic. 
- Notifying user through a simple Toast.
- Made the game working with internet only. No caching (wanted to use Room)
- Simple scoring system. No combos or rewards.
- No user profile.
- No reset for the game.


# what would be the first thing to improve or add if there had been more time
As i didn't want to exceed the 4 hours limit i did the following. And i'm willing to improve it way way far with more few hours.
By order:

- Writing UI tests.
- Adding Room db (will take max 30 mins) to make the app work offline.
- enhance UI to be more appealing and interactive.
- Changing from Toast to more interactive way to notify user of his result.
- Adding more animations to make the app interactive.

