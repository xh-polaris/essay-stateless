FROM openjdk:17

ARG WORKDIR=/opt/run

WORKDIR ${WORKDIR}
COPY run/lib run/lib
COPY ./target/essay-stateless-1.0.0.jar essay-stateless.jar

EXPOSE 8888

ENV LANG=zh_CN.UTF-8
ENV LANGUAGE=zh_CN.UTF-8
ENV LC_ALL=zh_CN.UTF-8
ENV TZ=Asia/Shanghai
ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java", "-jar", "essay-stateless.jar"]