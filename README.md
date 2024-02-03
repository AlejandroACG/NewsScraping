![NewsScraping](https://github.com/AlejandroACG/NewsScraping/assets/125238248/3fc72172-c3c9-457d-96e6-563af4840451)

Click Launch to start the application. It will automatically read two text files, one with a list of urls and one with a list of words. The application will open a tab for each url and web scrap its paragraphs for the words on the list. Each tab will operate on a different thread.

In each tab, a progress bar will show the progress towards counting a particular word (that will be named above it), and the total count of matches will be updated on real time under it.

Every time an invalid url is introduced by mistake, the application will ignore it and log it in a log.txt. If said log.txt doesn't exist, the application will create it on its own.
