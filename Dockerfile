FROM oracle/graalvm-ce:1.0.0-rc9
ENV OPTIONS ""
COPY ./language/target/spl-1.0-SNAPSHOT.jar /spl/language/target/spl-1.0-SNAPSHOT.jar
COPY ./launcher/target/launcher-1.0-SNAPSHOT.jar /spl/launcher/target/launcher-1.0-SNAPSHOT.jar
COPY ./native/splnative /spl/native/splnative
COPY ./spl /spl/spl
COPY run.spl /run.spl
CMD /spl/spl $OPTIONS run.spl
