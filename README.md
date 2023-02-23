# Soundtracks for the seasons 🌸🌞🍂❄️

A small Kotlin CLI tool that generates statistics about your Last.fm listening habits based on the seasons!
Made for nostalgic data and music nerds who really feel the seasons.

## Usage instructions

### To run the application:

1. Generate a Last.fm API key (follow docs https://www.last.fm/api)
2. Export API key to environment variables:

```shell
export LAST_FM_API_KEY="<your api key>"
```

3. Build

```shell
./gradlew build
```

4. Provide `args` in expected format (documented in `Main.kt`) and run. For example:

```shell
./gradlew run --args="iigne"
```
or if you want to specify optional arguments (limit and type):
```shell
./gradlew run --args="iigne 1 TRACK"
```

Output contains entry for each season and a list of 15 (or number specified in args) most listened to items in that season,
from highest to lowest. Format is JSON that looks like:

```json
{
  "SUMMER": [
    {
      "id": "",
      "artist": "HAIM",
      "name": "Women in Music Pt. III",
      "playcount": 962
    }
  ],
  "AUTUMN": [
    {
      "id": "56469ec6-b4ec-41b9-b19b-fc7369356807",
      "artist": "Vampire Weekend",
      "name": "Father of the Bride",
      "playcount": 1079
    }
  ],
  "WINTER": [
    {
      "id": "00945de3-0f0c-49ad-9709-0212c672042b",
      "artist": "Taylor Swift",
      "name": "evermore",
      "playcount": 463
    }
  ],
  "SPRING": [
    {
      "id": "2e0eb311-3691-4b9f-9c4e-9c0956b346af",
      "artist": "The Growlers",
      "name": "City Club",
      "playcount": 398
    }
  ]
}
```

### To run tests:

Currently, there's a few small JUnit tests of the most critical functionality, that can be run with:

```shell
./gradlew test
```

## Next steps

- [ ] Refactoring + resolve TODOs
- [ ] Being able to select season
- [ ] Turn this into API (Ktor) 
    - OR this could be turned into a serverless function?
- [ ]  Add a FE 
    - [ ] charts response needs to have some image URL (especially for albums) 
    - could be a React type thing or just a simple HTML page?
- [ ]  Deploy this somewhere (AWS, probably)
    - BE API on EC2 or something (or Lambda???)
    - FE can be hosted from S3 bucket

## Future work
* Improve the season aggregation algorithm
  * Currently, it's too primitive - only based on highest playcounts between all music listened to between start and end of season
  * Should introduce some scoring system?
  * Should exclude items that are too general (occur in high positions in every season and every year)
  * Should include items that are number 1 in a specific year
  * Handle start and end of seasons - start and end chart data is not as relevant as mid-season
* Make this more general-purpose
  * Could use this to generate non-aggregated stats for every year
  * Stuff like "what being 16/17/18" sounded like
  * Personal Christmas playlists if we only did songs around December!
* Integration with Spotify
  * It would be cool to generate playlists!!!
  * This would need FE - but clicking on some album/track/artist could redirect to spotify page
