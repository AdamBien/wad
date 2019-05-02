# watch and deploy (WAD)

WAD watches changes in `src\main\java` folder, builds the project using the `pom.xml` and deploys the ThinWAR into the configured folder.

## usage

Launch WAD from within your ThinWAR-project created with [javaee8-essentials-archetype](http://www.adam-bien.com/roller/abien/entry/java_ee_8_essentials_archetype).

`[THIN_WAR]/java -jar wad.jar [DEPLOYMENT_DIR ANOTHER_DIR]`

e.g.

`[THIN_WAR]/java -jar wad.jar /openliberty/wlp/usr/servers/defaultServer/dropins/ dist`

wad can be also configured in `~/.wadrc`. Each deployment folder in an new line:

```bash
/Users/duke/payara/glassfish/domains/domain1/autodeploy 
/Users/duke/openliberty/usr/servers/defaultServer/dropins
/Users/duke/tomee/webapps
/Users/duke/wildfly/standalone/deployments
```

On each source change WAD will:

1. Use the current directory as the service name
2. Build the project using the `pom.xml` found in the directory
3. Copy the `./target/[name].war` into the path used as parameter

You only have to write code and save it frequently like this:

[![ WAD](https://i.ytimg.com/vi/_c8ZkSSpdWI/mqdefault.jpg)](https://www.youtube.com/embed/_c8ZkSSpdWI?rel=0)

## common deployment directories

[WildFly](http://wildfly.org)
wildfly-[VERSION].Final/standalone/deployments

[OpenLiberty](https://openliberty.io)
openliberty-[VERSION]/usr/servers/defaultServer/dropins

[Payara](https://www.payara.fish)
payara/glassfish/domains/domain1/autodeploy

[TomEE](http://tomee.apache.org)
tomee-[version]/webapps

## deploy'em all

WAD also supports simultaneous deployment to multiple servers

`[THIN_WAR]/java -jar wad.jar /openliberty/wlp/usr/servers/defaultServer/dropins/ wildfly/standalone/deployments payara/glassfish/domains/domain1/autodeploy tomee/webapps`

## run from anywhere

A a shell script:

```shell
#!/bin/bash
BASEDIR=$(dirname $0)
java -jar ${BASEDIR}/wad.jar "$@"%
```

will install WAD "globally". Now you can launch WAD from any directory you like e.g.

`wad.sh /openliberty/wlp/usr/servers/defaultServer/dropins/`

## articles

["Improved Java / Jakarta EE Productivity with wad.sh"](https://rieckpil.de/review-improved-java-jakarta-ee-productivity-with-adam-biens-wad-watch-and-deploy/) by [@rieckpil](https://twitter.com/rieckpil)

Any questions left? See you at [airhacks.tv](http://airhacks.tv).




