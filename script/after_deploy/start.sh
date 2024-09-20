program="eneco-selenium"
path="."
port="8081"

kill -9 `ps x | grep java | grep $program | awk '{print $1}'` 2> /dev/null

cd ~/Prive/runeneco/


program_version=`ls -1 $program*.jar | tail -1`
java -jar $program_version --spring.profiles.active=prod --spring.config.name=application_prod --server.port=$port

#>> $program-log 2> $program-log &
