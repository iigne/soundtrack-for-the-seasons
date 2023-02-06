# Soundtracks for the seasons ❄️🌸🌞🍂

A small CLI tool that generates statistics about your Last.fm listening habits based on the seasons!

Made for nostalgic data nerds who feel the seasons and music very, very deeply.

## Usage instructions 

1. Generate a Last.fm API key (follow docs https://www.last.fm/api)
2. Export API key to environment variables:
```shell
export LAST_FM_API_KEY="<your api key>"
```
3. Build
```shell
./gradlew build
```
4. Run and provide username as args, for example
```shell
./gradlew run --args="iigne"
```

## Next steps

* Refactoring...
* Testing...
* Improve the season aggregation algorithm
  * Currently, it's tooooooo primitive
  * Should exclude items that are too general (occur in high positions in every season and every year)
  * Should include items that are number 1 in a specific year
* Make this more general-purpose
  * Could use this to generate non-aggregated stats for every year
  * Could 
* Make this more than just a CLI tool - add a little frontend
* Deploy this somewhere (like, AWS)
  * This was build with AWS Lambdas in mind
  * FE can be a simple thingy hosted from S3 bucket
* Integration with Spotify
  * It would be cool to generate playlists!