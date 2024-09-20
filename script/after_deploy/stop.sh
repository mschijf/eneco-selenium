program="eneco-selenium"

kill -9 `ps x | grep java | grep $program | awk '{print $1}'` 2> /dev/null

