# Watch and Deploy (WAD)

WAD watches changes in `src\main\java` folder, builds the project using the `pom.xml` and deploys the ThinWAR into the configured folder.

## usage

Launch WAD from within your ThinWAR-project created with [javaee8-essentials-archetype](http://www.adam-bien.com/roller/abien/entry/java_ee_8_essentials_archetype).

`[THIN_WAR]/java -jar wad.jar [DEPLOYMENT_DIR]`

e.g.

`[THIN_WAR]/java -jar wad.jar /openliberty/wlp/usr/servers/defaultServer/dropins/`

On each source change WAD will:

1. Use the current directory as the service name
2. Build the project using the `pom.xml` found in the directory
3. Copy the `./target/[name].war` into the path used as parameter

You only have to write code and save it frequently like this:

[![ WAD](https://i.ytimg.com/vi/_c8ZkSSpdWI/mqdefault.jpg)](https://www.youtube.com/embed/_c8ZkSSpdWI?rel=0)

