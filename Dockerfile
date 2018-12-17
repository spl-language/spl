FROM alpine
#COPY ./native/slnative /sl
RUN mkdir /programs
#COPY ./HelloWorld.sl /programs/
CMD ["./te/sl", "/programs/HelloWorld.sl"]
