# Soundtracks for the seasons 🌸🌞🍂❄️

A small CLI tool that generates statistics about your Last.fm listening habits based on the seasons!

Made for nostalgic data and music nerds who really feel the seasons.

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

4. Provide `args` in expected format (documented in [Main.kt]) and run. For example:

```shell
./gradlew run --args="iigne"
```
or
```shell
./gradlew run --args="iigne 5 TRACK"
```

Output contains entry for each season and a list of most listened to items in that season,
from highest to lowest, limited to 15 (currently - TODO).
Format is JSON that looks like:

```json
[
  {
    "season": "SUMMER",
    "list": [
      {
        "id": "",
        "artist": "",
        "name": "",
        "playcount": 0
      },
      ...
    ]
  },
  {
    "season": "AUTUMN",
    "list": [...]
  },
  {
    "season": "WINTER",
    "list": [...]
  },
  {
    "season": "SPRING",
    "list": [...]
  }
]
```

## Next steps

- [ ] Testing
- [ ] Refactoring + resolve TODOs
- [ ] Select season
- [ ]  Turn this into API?
     -  OR this could be a lambda function + API gateway
- [ ]  Add a FE
     - [ ] charts response needs to have some image URL (especially for albums) 
     - could be a React type thing or just a simple HTML page?
- [ ]  Deploy this somewhere (like AWS)
    - This was build with AWS Lambdas in mind, sorta
    - FE can be a simple thingy hosted from S3 bucket

## Future work
* Improve the season aggregation algorithm
  * Currently, it's too primitive - only based on highest playcounts between all music listened to between start and end of season
  * Should introduce some scoring system?
  * Should exclude items that are too general (occur in high positions in every season and every year)
  * Should include items that are number 1 in a specific year
  * Handle start and end of seasons - start and end chart data is not as relevant as mid-season
* Make this more general-purpose
  * Could use this to generate non-aggregated stats for every year
  * Personal Christmas playlists if we only did songs around December!
* Integration with Spotify
  * It would be cool to generate playlists!!!
  * This would need FE - but clicking on some album/track/artist could redirect to spotify page
